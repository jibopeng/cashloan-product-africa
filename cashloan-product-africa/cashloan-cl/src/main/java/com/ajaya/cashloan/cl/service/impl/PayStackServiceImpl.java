package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.*;
import com.ajaya.cashloan.cl.mapper.BankCardMapper;
import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.config.AsyncProcessQueue;
import com.ajaya.cashloan.cl.mapper.BankAccountMapper;
import com.ajaya.cashloan.cl.mapper.BorrowFlagMapper;
import com.ajaya.cashloan.cl.model.BorrowRepayModel;
import com.ajaya.cashloan.cl.model.BorrowRepayParamModel;
import com.ajaya.cashloan.cl.model.PayLogModel;
import com.ajaya.cashloan.cl.model.PayRespLogModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.common.util.HttpClientUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import tool.util.BigDecimalUtil;
import tool.util.StringUtil;

/**
 * 功能说明：payTm支付相关业务
 *
 * @author yanzhiqiang
 * @since 2020-06-11 15:22
 **/
@Service("payStackService")
public class PayStackServiceImpl implements PayStackService {


    private static final String REPAY_URL = "https://api.paystack.co/transaction/charge_authorization";
    private static final String CHECK_URL = "https://api.paystack.co/transaction/check_authorization";
    private static final Logger logger = LoggerFactory.getLogger(ResponseWrapper.class);

    @Resource
    private ClBorrowService clBorrowServiceImpl;
    @Resource
    private BorrowRepayService borrowRepayServiceImpl;
    @Resource
    private PayLogService payLogServiceImpl;
    @Resource
    private PayReqLogService payReqLogServiceImpl;
    @Resource
    private BorrowProgressService progressServiceImpl;
    @Resource
    private PayRespLogService payReSPLogServiceImpl;
    @Resource
    private ClBorrowRiskBusinessService clBorrowRiskBusinessService;
    @Resource
    private BorrowFlagMapper borrowFlagMapper;
    @Resource
    private UserAppFlyerLogService userAppFlyerLogService;
    @Resource
    private UserFirstCentCreditReportService userFirstCentCreditReportService;
    @Resource
    private ClSmsService clSmsServiceImpl;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private BorrowRepayFqService borrowRepayFqServiceImpl;
    @Resource
    private UserVirtualAccountService userVirtualAccountServiceImpl;


    @Resource BankCardMapper bankCardMapper;
    @Override
    public void dealRepaymentCallBack(JSONObject data, String event) {
        String resultStr = data.toJSONString();
        logger.info("PayStack还款异步处理数据：" + resultStr);
        //修改start
        String orderNo = data.getString("reference");
        PayLog payLog = payLogServiceImpl.findByOrderNo(orderNo);
        Long borrowId = payLog.getBorrowId();
        Borrow borrow = clBorrowServiceImpl.findByPrimary(borrowId);
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("borrowId", payLog.getBorrowId());
        BorrowRepay borrowRepay = borrowRepayServiceImpl.findSelective(paramMap);
        //还款事件处理
        if (event.contains("charge.success")) {
            // 处理异步回调业务
            PayReqLog payReqLog = payReqLogServiceImpl.findByOrderNo(orderNo);
            if (null != payReqLog) {
                // 保存respLog
                logger.info("订单 {} 还款成功异步处理-保存respLog", borrowId);
                PayRespLog payRespLog = new PayRespLog(orderNo, PayRespLogModel.RESP_LOG_TYPE_NOTIFY, resultStr);
                payReSPLogServiceImpl.save(payRespLog);
                // 更新reqLog
                payReqLog.setResult(1);
                logger.info("订单 {} 还款成功异步处理-更新reqLog", borrowId);
                modifyPayReqLog(payReqLog, resultStr);
            }
            // 更新支付记录状态40
            modifyPayLog(payLog.getId(), PayLogModel.STATE_PAYMENT_SUCCESS);

            // 首先判断是否已经处理完毕
            if (BorrowRepayModel.STATE_REPAY_YES.equals(borrowRepay.getState())) {
                logger.info("订单 {} 还款成功异步处理-还款业务已经处理完毕", borrowId);
                return;
            }
            logger.info("订单 {} 还款成功异步处理-更新payLog40", borrowId);
            //更新订单状态
            borrow.setState(BorrowModel.STATE_FINISH);
            int updateById = clBorrowServiceImpl.updateById(borrow);
            logger.info("订单 {} 还款成功异步处理-更新borrow40", borrowId + ",结果:" + updateById);
            // 还款方式payStack 线上还款
            String repayWay = PayLogModel.TYPE_COLLECT;
            //支付费用
            double payFee = 0;
            borrowRepayServiceImpl.repaymentNotify(payLog, PayLogModel.STATE_PAYMENT_SUCCESS, StringUtil.isNull(payLog.getAmount()), repayWay, payLog.getCardNo(), payFee);

            logger.info("订单 {} 还款成功异步处理-更新borrowRepay", borrowId);
            //增加还款风控业务处理
            clBorrowRiskBusinessService.saveBorrowRiskBusinessForRepay(borrowId);
            //发送还款成功短信
            try {
                UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(borrow.getUserId());
                clSmsServiceImpl.sendSms(userBaseInfo.getPhone(),"sms_repayment_success");
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (event.contains("charge.failed")) {
            //说明还款失败
            logger.info("订单 " + payLog.getBorrowId() + "还款失败处理");
            //订单状态恢复
            Integer penaltyDay = Integer.parseInt(borrowRepay.getPenaltyDay());
            paramMap.clear();
            if (penaltyDay > 0) {
                //逾期状态
                paramMap.put("state", BorrowModel.STATE_DELAY);
            } else {
                //还款
                paramMap.put("state", BorrowModel.STATE_REPAY);
            }
            paramMap.put("id", payLog.getBorrowId());
            clBorrowServiceImpl.updatePayState(paramMap);
            paramMap.clear();
            paramMap.put("state", PayLogModel.STATE_PAYMENT_FAILED);
            paramMap.put("updateTime", DateUtil.getNow());
            paramMap.put("id", payLog.getId());
            paramMap.put("remark", "failed");
            payLogServiceImpl.updateSelective(paramMap);
        } else {
            logger.info("订单 " + payLog.getBorrowId() + "事件错误 " + event);

        }
    }


    private void modifyPayReqLog(PayReqLog payReqLog, String params) {
        payReqLog.setNotifyParams(params);
        payReqLog.setNotifyTime(DateUtil.getNow());
        payReqLogServiceImpl.updateById(payReqLog);
    }

    private void modifyPayLog(Long payLogId, String payState) {
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("state", payState);
        paramMap.put("updateTime", DateUtil.getNow());
        if (PayLogModel.STATE_PAYMENT_FAILED.equals(payState)) {
            paramMap.put("remark", "failed");
        } else if (PayLogModel.STATE_PAYMENT_SUCCESS.equals(payState)) {
            paramMap.put("remark", "success");
        }
        paramMap.put("id", payLogId);
        payLogServiceImpl.updateSelective(paramMap);
    }

    @Resource
    private BankAccountMapper bankAccountMapper;

    @Override
    public synchronized ResponseWrapper payout(Long userId, Long borrowId) {
        ResponseWrapper responseWrapper;
        Borrow borrow = clBorrowServiceImpl.findByPrimary(borrowId);
        logger.info("订单 {} 开始设置放款状态:", borrowId);
        HashMap<String, Object> params = new HashMap<>(8);
        params.put("status", "1");
        params.put("userId", userId);
        BankAccount bankAccount = bankAccountMapper.findSelective(params);
        borrow.setAccountId(bankAccount.getId());
        //进行放款 20 26 31
        if (BorrowModel.STATE_AUTO_PASS.equals(borrow.getState()) || BorrowModel.STATE_PASS.equals(borrow.getState())
                || BorrowModel.STATE_REPAY_FAIL.equals(borrow.getState()) || BorrowModel.AUDIT_LOAN_PASS.equals(borrow.getState())) {
            //防止重复放款问题
            BorrowFlag borrowFlag = new BorrowFlag();
            borrowFlag.setBorrowId(borrow.getId());
            borrowFlag.setState("29");
            borrowFlag.setCreateTime(new Date());
            int save = borrowFlagMapper.save(borrowFlag);
            logger.info("borrowId:" + borrow.getId() + ",保存放款borrowFlag日志记录表,结果:" + save);
            clBorrowServiceImpl.borrowLoan(borrow, new Date());
            responseWrapper = ResponseWrapper.success();
        } else {
            logger.error("订单 {} 不是待放款状态", borrow.getId());
            responseWrapper = ResponseWrapper.error("Order status does not belong to the pay out status.");
        }
        //设置borrow表为29,放款中
        borrow.setState(BorrowModel.DAKUAN_ING);
        int updateById = clBorrowServiceImpl.updateById(borrow);
        //保存用户订单进度
        clBorrowServiceImpl.savePressState(borrow, BorrowModel.DAKUAN_ING, "");
        logger.info("订单 " + borrowId + " 设置还款中,结果:" + updateById);
        return responseWrapper;
    }

    @Override
    public void dealPayOutCallBack(JSONObject data, String event) {
        String dataStr = data.toJSONString();
        logger.info("PayStack放款异步处理事件 {} 结果：{}", event, dataStr);
        //放款事件处理
        String reference = data.getString("reference");
        String transNo = data.getString("transfer_code");
        PayLog payLog = payLogServiceImpl.findByOrderNo(reference);
        if (null == payLog) {
            logger.warn("未查询到对应的支付订单");
            return;
        }
        final Long borrowId = payLog.getBorrowId();
        final  Borrow borrow = clBorrowServiceImpl.getById(borrowId);
        //查询业务是否已经处理完毕
        Map<String, Object> paramMapRepay = new HashMap<>();
        paramMapRepay.put("borrowId", borrowId);
        paramMapRepay.put("state", BorrowRepayModel.STATE_REPAY_NO);
        BorrowRepay borrowRepay = borrowRepayServiceImpl.findSelective(paramMapRepay);
        if (borrowRepay != null) {
            //业务已经处理完毕
            logger.info("订单" + borrowId + " 放款业务已经处理完毕");
            return;
        }
        //开始操作业务
        String params = JSON.toJSONString(dataStr);
        PayReqLog payReqLog = payReqLogServiceImpl.findByOrderNo(reference);
        if (payReqLog != null) {
            // 保存respLog
            PayRespLog payRespLog = new PayRespLog(reference, PayRespLogModel.RESP_LOG_TYPE_NOTIFY, params);
            payReSPLogServiceImpl.save(payRespLog);
            // 更新reqLog
            modifyPayReqLog(payReqLog, params);
        }

        //说明放款成功
        if ("transfer.success".equals(event)) {
            logger.info("订单 " + payLog.getBorrowId() + "PayStack放款异步处理中.....");
            //修改借款状态
            Map<String, Object> map = new HashMap<>();
            map.put("id", payLog.getBorrowId());
            map.put("state", BorrowModel.STATE_REPAY);
            clBorrowServiceImpl.updatePayState(map);
            logger.info("订单 " + payLog.getBorrowId() + " 修改订单状态");
            // 放款进度添加
            BorrowProgress bp = new BorrowProgress();
            bp.setUserId(payLog.getUserId());
            bp.setBorrowId(payLog.getBorrowId());
            bp.setState(BorrowModel.STATE_REPAY);
            bp.setRemark(BorrowModel.convertBorrowRemark(bp.getState()));
            bp.setCreateTime(DateUtil.getNow());
            progressServiceImpl.save(bp);
            logger.info("订单 " + payLog.getBorrowId() + " 添加进度表");
            // 生成还款计划并授权
            logger.info("订单 " + payLog.getBorrowId() + " 生成还款计划");
            borrowRepayServiceImpl.genRepayPlan(borrow);
            // 更新payLog订单状态
            Map<String, Object> paramMap = new HashMap<>(8);
            paramMap.put("state", PayLogModel.STATE_PAYMENT_SUCCESS);
            paramMap.put("updateTime", DateUtil.getNow());

            paramMap.put("tradeNo", transNo);
            paramMap.put("id", payLog.getId());
            paramMap.put("remark", "success");
            payLogServiceImpl.updateSelective(paramMap);
            logger.info("订单 " + payLog.getBorrowId() + " 修改payLog支付订单状态");

            try {
                userVirtualAccountServiceImpl.createBankTransferInfo(payLog.getUserId(),Global.getValue("title"));
            }catch (Exception e){
                logger.info("订单 " + payLog.getBorrowId() + " 生成虚拟账号失败");

            }
            
            AsyncProcessQueue.execute(new Runnable() {
				@Override
				public void run() {
					logger.info("borrowId {} , 异步处理放款后的业务", borrowId);
					//操作风控业务放款处理
		            clBorrowRiskBusinessService.saveBorrowRiskBusinessForPay(borrowId);
//		            adJustEnventMonitoServiceImpl.adjustEnventMonito(borrow, 4);
		            userAppFlyerLogService.appFlyerEnventMonito(borrow, 4);
		            //获取FC报告
		            userFirstCentCreditReportService.saveFirstCentCreditReport(borrowId);
				}
            });
        } else if ("transfer.failed".equals(event)) {
            //说明放款失败
            logger.info("订单 " + payLog.getBorrowId() + " 放款失败处理");
            Map<String, Object> borrowMap = new HashMap<>(8);
            borrowMap.put("id", payLog.getBorrowId());
            borrowMap.put("state", BorrowModel.STATE_REPAY_FAIL);
            clBorrowServiceImpl.updatePayState(borrowMap);
            Map<String, Object> paramMap = new HashMap<>(8);
            paramMap.put("state", PayLogModel.STATE_PAYMENT_FAILED);
            paramMap.put("tradeNo", transNo);
            paramMap.put("updateTime", DateUtil.getNow());
            paramMap.put("id", payLog.getId());
            paramMap.put("remark", "failed");
            payLogServiceImpl.updateSelective(paramMap);
        } else {
            logger.info("订单 " + payLog.getBorrowId() + "事件错误" + event);

        }

    }



    @Override
    public ResponseWrapper bankCardRepay(Long cardId, Long borrowId,String type) {
        BorrowRepayParamModel borrowRepayParamModel = borrowRepayServiceImpl.findRepayParam(borrowId, cardId);
        if (borrowRepayParamModel == null) {
            return ResponseWrapper.error("The order has been repaid.");
        }
        //查询要还款金额
        Double currentNeedRepayMoney = borrowRepayFqServiceImpl.getCurrentNeedReapyMoney(borrowId);
        borrowRepayParamModel.setRepayTotal(currentNeedRepayMoney);

        String orderNo = "rep" + System.currentTimeMillis() + "b" + borrowId;

        HashMap<String, String> header2 = getHeader2();
        HashMap<String, String> params = new HashMap<>(3);
        Double repayTotal = borrowRepayParamModel.getRepayTotal();

        params.put("amount", BigDecimalUtil.mul(repayTotal, 100) + "");
        params.put("email", borrowRepayParamModel.getEmail());
        params.put("authorization_code", borrowRepayParamModel.getAuthorizationCode() + "");
        params.put("reference", orderNo);
        String param = JSONObject.toJSONString(params);
        String result = HttpClientUtil.sendPost(REPAY_URL, header2, param, "utf-8");
        //插入调取支付请求日志
        PayReqLog payReqLog = new PayReqLog();
        payReqLog.setCreateTime(new Date());
        payReqLog.setReqDetailParams(param);
        payReqLog.setOrderNo(orderNo);
        payReqLog.setReturnParams(result);
        payReqLog.setReturnTime(new Date());
        payReqLogServiceImpl.save(payReqLog);
        logger.info("订单 {} 调用银行卡还款返回结果： {}", borrowId, result);
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            String message = resultObj.getString("message");
            Boolean status = resultObj.getBoolean("status");
            JSONObject data = resultObj.getJSONObject("data");
            if (status) {
                String status1 = data.getString("status");
                //时时返回失败
                if ("failed".equals(status1)){
                    logger.info("订单 {} 调用还款返回交易失败，原因 {}"+data.getString("gateway_response"), borrowId);
                    return ResponseWrapper.error("Transaction failed,  Please change bank card.");
                }
                int i = payLogServiceImpl.savePayLog(orderNo, borrowRepayParamModel.getUserId(), borrowId, repayTotal, "last4(" + borrowRepayParamModel.getLast4() + ")", borrowRepayParamModel.getBank(), "20", null,type);
                logger.info("订单 {} 插入还款记录表：{}", borrowId, i > 0);
                //变更支付状态
                Borrow borrow = clBorrowServiceImpl.findByPrimary(borrowId);
                borrow.setState(BorrowModel.STATE_REPAY_PROCESSING);
                int i1 = clBorrowServiceImpl.updateById(borrow);
                logger.info("订单 {} 调用银行卡还款还款中状态修改，结果 {}", borrowId, i1 > 0);
                //保存用户订单进度
                clBorrowServiceImpl.savePressState(borrow, BorrowModel.STATE_REPAY_PROCESSING, "repay processing");
                return ResponseWrapper.success("The repayment has been submitted.");
            } else {
                logger.info("订单 {} 调用银行卡还款，返回 {}", borrowId, message);
                return ResponseWrapper.error(message);
            }
        } else {
            logger.info("订单 {} 调用银行卡还款，返回空", borrowId);
            return ResponseWrapper.error("System abnormal, please try again later.");
        }

    }


    private HashMap<String, String> getHeader2() {
        HashMap<String, String> header = new HashMap<>(1);
        header.put("Authorization",Global.getValue("pay_stack_secret_key"));
        header.put("Content-Type", "application/json");
        return header;
    }


    @Override
    public void doWithholdingByBorrowId(Long borrowId) {
        Borrow borrow = clBorrowServiceImpl.getById(borrowId);
        String state = borrow.getState();
        Long userId = borrow.getUserId();
        if (!BorrowModel.STATE_DELAY.equals(state)) {
            logger.info("订单 {} 进行代扣失败-订单状态 {}",borrowId, state);
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("status","1");
        BankCard bankCard = bankCardMapper.findSelective(param);
        if (bankCard==null ){
            logger.info("订单 {} 进行代扣失败-未找到邦定的银行卡 ",borrowId);
            return;
        }
        logger.info("订单 {} 进行代扣开始 ",borrowId);
        bankCardRepay(bankCard.getId(),borrowId,PayLogModel.TYPE_PAYMENT);
        logger.info("订单 {} 进行代扣结束 ",borrowId);
    }

    @Override
    public boolean checkAuthorization(Long cardId, Long borrowId) {
        BorrowRepayParamModel borrowRepayParamModel = borrowRepayServiceImpl.findRepayParam(borrowId, cardId);
        //查询要还款金额
        Double currentNeedRepayMoney = borrowRepayFqServiceImpl.getCurrentNeedReapyMoney(borrowId);
        borrowRepayParamModel.setRepayTotal(currentNeedRepayMoney);
        HashMap<String, String> header2 = getHeader2();
        HashMap<String, String> params = new HashMap<>(3);
        Double repayTotal = borrowRepayParamModel.getRepayTotal();

        params.put("amount", BigDecimalUtil.mul(repayTotal, 100) + "");
        params.put("email", borrowRepayParamModel.getEmail());
        params.put("authorization_code", borrowRepayParamModel.getAuthorizationCode());
        String result = HttpClientUtil.sendPost(CHECK_URL, header2, params, "utf-8");
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            String message = resultObj.getString("message");
            Boolean status = resultObj.getBoolean("status");
            logger.info("订单 {} 调用权限检验接口，status : {} ,message: {}", borrowId,status,message);
            return status;
        } else {
            logger.info("订单 {} 调用权限检验接口失败，返回空", borrowId);
            return false;
        }
    }

}
