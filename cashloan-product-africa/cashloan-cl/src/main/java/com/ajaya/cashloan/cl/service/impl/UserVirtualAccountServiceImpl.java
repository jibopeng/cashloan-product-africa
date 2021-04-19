package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.*;
import com.ajaya.cashloan.cl.mapper.BorrowFlagMapper;
import com.ajaya.cashloan.cl.mapper.PayLogMapper;
import com.ajaya.cashloan.cl.mapper.UserVirtualAccountMapper;
import com.ajaya.cashloan.cl.model.*;
import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.common.util.HttpClientUtil;
import com.ajaya.cashloan.core.common.util.OrderNoUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.system.domain.SysConfig;
import com.ajaya.cashloan.system.service.SysConfigService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.regexp.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tool.util.StringUtil;

import java.util.*;

/**
 * 用户虚拟账户表ServiceImpl
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-03 10:42:48
 */

@Service("userVirtualAccountService")
public class UserVirtualAccountServiceImpl extends BaseServiceImpl<UserVirtualAccount, Long> implements UserVirtualAccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserVirtualAccountServiceImpl.class);

    @Resource
    private BorrowRepayFqService borrowServiceImpl;
    @Resource
    private UserVirtualAccountMapper userVirtualAccountMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private ClBorrowService clBorrowServiceImpl;
    @Resource
    private BorrowRepayService borrowRepayServiceImpl;
    @Resource
    private ClBorrowRiskBusinessService clBorrowRiskBusinessService;

    @Resource
    private BorrowRepayFqService borrowRepayFbService;
    @Resource
    private PayLogMapper payLogMapper;
    @Resource
    private ClSmsService clSmsServiceImpl;
    @Override
    public BaseMapper<UserVirtualAccount, Long> getMapper() {
        return userVirtualAccountMapper;
    }

    @Override
    public ResponseWrapper getBankTransferInfo(Long userId, String appFlag, Long borrowId) {
        UserVirtualAccount userVirtualAccount = userVirtualAccountMapper.findByUserId(userId);
        //生成或激活账号
        if (userVirtualAccount == null || isAfter6Month(userVirtualAccount.getLastUseTime())) {
            UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
            HashMap<String, String> param = new HashMap<>();
            param.put("accountReference", "vac" + OrderNoUtil.getSerialNumber());
            param.put("accountName", userBaseInfo.getRealName() + " - " + appFlag);
            param.put("currencyCode", "NGN");

            param.put("contractCode", Global.getValue("teamapt_contract_code"));
            param.put("customerEmail", userBaseInfo.getEmail());
            param.put("customerBvn", userBaseInfo.getBvnNo());
            param.put("customerName", userBaseInfo.getRealName());
            HashMap<String, String> header = getHeader2();
            String result = HttpClientUtil.sendPost(Global.getValue("teamapt_create_account_url"), header, JSONObject.toJSONString(param), "utf-8");

            if (result != null) {
                JSONObject resultObj = JSONObject.parseObject(result);
                Boolean requestSuccessful = resultObj.getBoolean("requestSuccessful");
                String responseMessage = resultObj.getString("responseMessage");
                if (requestSuccessful) {
                    JSONObject responseBody = resultObj.getJSONObject("responseBody");
                    userVirtualAccount = saveOrUpdate(responseBody, userVirtualAccount, userId, userBaseInfo.getBvnNo());
                } else {
                    logger.info("用户 {} 调用生成虚拟账户接口，返回错误 {} ", userId, responseMessage);
                    return ResponseWrapper.error("System abnormal, please try again later.");
                }
            } else {
                logger.info("用户 {} 调用生成虚拟账户接口，返回空", userId);
                return ResponseWrapper.error("System abnormal, please try again later.");
            }
        }


        UserVirtualAccountModel userVirtualAccountModel = new UserVirtualAccountModel();
        BeanUtils.copyProperties(userVirtualAccount, userVirtualAccountModel);
        //查询要还款金额
        Double currentNeedRepayMoney = borrowServiceImpl.getCurrentNeedReapyMoney(borrowId);
        userVirtualAccountModel.setTotalAmount(currentNeedRepayMoney);
        return ResponseWrapper.success(userVirtualAccountModel);
    }


    @Override
    public void createBankTransferInfo(Long userId, String appFlag) {
        UserVirtualAccount userVirtualAccount = userVirtualAccountMapper.findByUserId(userId);
        //生成或激活账号
        if (userVirtualAccount == null || isAfter6Month(userVirtualAccount.getLastUseTime())) {
            UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
            HashMap<String, String> param = new HashMap<>();
            param.put("accountReference", "vac" + OrderNoUtil.getSerialNumber());
            param.put("currencyCode", "NGN");
            param.put("accountName", userBaseInfo.getRealName() + " - " + appFlag);
            param.put("contractCode", Global.getValue("teamapt_contract_code"));
            param.put("customerEmail", userBaseInfo.getEmail());
            param.put("customerBvn", userBaseInfo.getBvnNo());
            param.put("customerName", userBaseInfo.getRealName());
            HashMap<String, String> header = getHeader2();
            String result = HttpClientUtil.sendPost(Global.getValue("teamapt_create_account_url"), header, JSONObject.toJSONString(param), "utf-8");
            if (result != null) {
                JSONObject resultObj = JSONObject.parseObject(result);
                Boolean requestSuccessful = resultObj.getBoolean("requestSuccessful");
                String responseMessage = resultObj.getString("responseMessage");
                if (requestSuccessful) {
                    JSONObject responseBody = resultObj.getJSONObject("responseBody");
                    saveOrUpdate(responseBody, userVirtualAccount, userId, userBaseInfo.getBvnNo());
                } else {
                    logger.info("用户 {} 调用生成虚拟账户接口，返回错误 {} ", userId, responseMessage);
                }
            } else {
                logger.info("用户 {} 调用生成虚拟账户接口，返回空", userId);
            }
        }
    }

    @Override
    public ResponseWrapper getUssdListInfo(Long userId, String appFlag, Long borrowId) {
        //调用账号生成或激活，拿到账号信息以及还款金额
        ResponseWrapper bankTransferInfo = getBankTransferInfo(userId, appFlag, borrowId);
        if (bankTransferInfo.getCode() == 400) {
            return bankTransferInfo;
        }
        UserVirtualAccountModel data = (UserVirtualAccountModel) bankTransferInfo.getData();

        String result = HttpClientUtil.sentGet(Global.getValue("teamapt_get_bank_list_url"), null, null, "utf-8");
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            String responseMessage = resultObj.getString("responseMessage");
            Boolean requestSuccessful = resultObj.getBoolean("requestSuccessful");
            ArrayList<UssdModel> ussdModels = new ArrayList<>();
            if (requestSuccessful) {
                List<UssdModel> list = JSONObject.parseArray(resultObj.getString("responseBody"), UssdModel.class);
                for (UssdModel ussdModel : list) {
                    String ussdTemplate = ussdModel.getUssdTemplate();
                    ussdModel.setUssdTemplate(ussdTemplate.replace("Amount", Double.toString(data.getTotalAmount())).replace("AccountNumber", data.getAccountNumber()));
                    //排查Rubies Micro-finance Bank
                    if (ussdTemplate.contains("090175")) {
                        continue;
                    }
                    ussdModels.add(ussdModel);
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("totalAmount", data.getTotalAmount());
                hashMap.put("bankName", data.getBankName());
                hashMap.put("bankList", ussdModels);
                return ResponseWrapper.success(hashMap);
            } else {
                logger.info("用户 {} 调用拉取ussd银行列表接口，返回错误 {} ", userId, responseMessage);
                return ResponseWrapper.error("System abnormal, please try again later.");
            }
        } else {
            logger.info("用户 {} 调用拉取ussd银行列表接口，返回空", userId);
            return ResponseWrapper.error("System abnormal, please try again later.");
        }
    }


    @Override
    public void dealTransferCallBack(JSONObject payOutDetail) {

        String resultStr = payOutDetail.toJSONString();
        logger.info("TeamApt线下还款还款异步处理数据：" + resultStr);

        JSONObject product = payOutDetail.getJSONObject("product");
        String paymentReference = payOutDetail.getString("paymentReference");
        String accountReference = product.getString("reference");
        double amountPaid = payOutDetail.getDouble("amountPaid");
        UserVirtualAccount userVirtualAccount = userVirtualAccountMapper.findByAccountReference(accountReference);
        Long userId = userVirtualAccount.getUserId();

        Borrow borrow = clBorrowServiceImpl.findLastBorrow(userId);
        Long borrowId = borrow.getId();
        if (!(BorrowModel.STATE_DELAY.equals(borrow.getState()) || BorrowModel.STATE_REPAY.equals(borrow.getState()))) {
            logger.info("订单 {} 业务已经处理完毕", borrowId);
            return;
        }
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("borrowId", borrowId);
        BorrowRepay borrowRepay = borrowRepayServiceImpl.findSelective(paramMap);
        if (BorrowRepayModel.STATE_REPAY_YES.equals(borrowRepay.getState())) {
            logger.info("订单 {} 还款成功异步处理-还款业务已经处理完毕", borrowId);
            return;
        }
        //检查交易状态
        if (!checkTransactionStatus(amountPaid, paymentReference))  {
            logger.info("订单 {} 还款成功异步处理-交易状态检查是失败", borrowId);
            return;
        }
        //如果还款金额小于需要还款金额则进行分期处理
        Double currentNeedReapyMoney = borrowRepayFbService.getCurrentNeedReapyMoney(borrowId);
        if (amountPaid < currentNeedReapyMoney) {
            //插入分期数据
            borrowRepayFbService.saveOrUpdateBorrowRepayFqNew(borrowId, amountPaid, BorrowRepayModel.STATE_REPAY_YES,
                    userVirtualAccount.getAccountNumber(), userVirtualAccount.getBankName(), paymentReference);
        } else {
            //还款事件处理
            //插入payLog
            PayLog payLog = savePayLog(paymentReference, amountPaid, userVirtualAccount, borrow, borrowId);
            logger.info("订单 {} 还款成功异步处理-更新payLog40", borrowId);
            //更新订单状态
            borrow.setState(BorrowModel.STATE_FINISH);
            int updateById = clBorrowServiceImpl.updateById(borrow);
            logger.info("订单 {} 还款成功异步处理-更新borrow40", borrowId + ",结果:" + updateById);
            // 还款方式payStack
            String repayWay = "20";
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
        }
    }

    private boolean checkTransactionStatus(double amountPaid, String paymentReference) {
        HashMap<String, String> header = getHeader();
        String transactionsStatusUrl = Global.getValue("teamapt_transactions_status_url");
        HashMap<String, String> params = new HashMap<>(8);
        params.put("paymentReference", paymentReference);
        String result = HttpClientUtil.sentGet(transactionsStatusUrl, header, params, "utf-8");
        boolean flag = false;
        if (result != null) {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            boolean requestSuccessful = jsonObject.getBoolean("requestSuccessful");
            if (requestSuccessful) {
                JSONObject responseBody = jsonObject.getJSONObject("responseBody");
                String paymentStatus = responseBody.getString("paymentStatus");
                String paymentReference2 = responseBody.getString("paymentReference");
                Double amount = responseBody.getDouble("amount");
                logger.info("流水号 {} 支付状态 {} 金额 {}", paymentReference2, paymentStatus, amount);
                if (!Constant.TEAMAPT_TRANSACTIONS_STATUS_PAID.equals(paymentStatus) || amountPaid != amount) {
                    logger.info("流水号 {} 支付状态 {} 金额 {} --支付状态不正确，或支付金额不匹配", paymentReference2, paymentStatus, amount);
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private PayLog savePayLog(String paymentReference, double amountPaid, UserVirtualAccount userVirtualAccount, Borrow borrow, Long borrowId) {
        PayLog payLog = new PayLog();
        String orderNo = "RPT" + System.currentTimeMillis() + "B" + borrowId;
        payLog.setOrderNo(orderNo);
        payLog.setUserId(borrow.getUserId());
        payLog.setBorrowId(borrowId);
        payLog.setAmount(amountPaid);
        payLog.setCardNo(userVirtualAccount.getAccountNumber());
        payLog.setBank(userVirtualAccount.getBankName());
        payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
        payLog.setType(PayLogModel.TYPE_OFFLINE_PAYMENT);
        payLog.setScenes("20");
        payLog.setState(PayLogModel.STATE_PAYMENT_SUCCESS);
        payLog.setRemark("success");
        payLog.setPayReqTime(tool.util.DateUtil.getNow());
        payLog.setCreateTime(tool.util.DateUtil.getNow());
        payLog.setTradeNo(paymentReference);
        int result = payLogMapper.save(payLog);
        logger.info("borrowId:" + borrowId + "线下还款创保存pay_log记录结果" + result);
        return payLog;
    }

    private UserVirtualAccount saveOrUpdate(JSONObject responseBody, UserVirtualAccount userVirtualAccount, Long userId, String bvn) {

        boolean save = false;
        if (userVirtualAccount == null) {
            userVirtualAccount = new UserVirtualAccount();
            userVirtualAccount.setCreateTime(new Date());
            userVirtualAccount.setUserId(userId);
            save = true;
        }
        userVirtualAccount.setAccountName(responseBody.getString("accountName"));
        userVirtualAccount.setAccountNumber(responseBody.getString("accountNumber"));
        userVirtualAccount.setAccountReference(responseBody.getString("accountReference"));
        userVirtualAccount.setBankName(responseBody.getString("bankName"));
        userVirtualAccount.setBankCode(responseBody.getString("bankCode"));
        userVirtualAccount.setReservationReference(responseBody.getString("reservationReference"));
        userVirtualAccount.setCreatedOn(responseBody.getString("createdOn"));
        userVirtualAccount.setCurrencyCode(responseBody.getString("currencyCode"));
        userVirtualAccount.setCustomerEmail(responseBody.getString("customerEmail"));
        userVirtualAccount.setStatus(responseBody.getString("status"));
        userVirtualAccount.setLastUseTime(new Date());
        userVirtualAccount.setCustomerBvn(bvn);
        if (save) {
            userVirtualAccountMapper.save(userVirtualAccount);
        } else {
            userVirtualAccount.setUpdateTime(new Date());
            userVirtualAccountMapper.update(userVirtualAccount);
        }
        return userVirtualAccount;
    }

    @Resource
    private SysConfigService sysConfigServiceImpl;

    private HashMap<String, String> getHeader2() {
        HashMap<String, String> header = new HashMap<>(1);
        //获取权限token
        String accessToken = getAccessToken();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + accessToken);
        return header;
    }

    private HashMap<String, String> getHeader() {
        HashMap<String, String> header = new HashMap<>(1);
        String authorization = Global.getValue("teamapt_authorization");
        header.put("Authorization", authorization);
        return header;
    }


    private String getAccessToken() {
        //更新到config表中
        SysConfig sysConfig = sysConfigServiceImpl.findByCode("teamapt_access_token");
        //获取失效时间
        String remark = sysConfig.getRemark();
        long time = Long.parseLong(remark);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        if (currentTimeMillis > time) {
            HashMap<String, String> header = new HashMap<>(8);
            String refreshToken = Global.getValue("teamapt_authorization");
            String refreshTokenUrl = Global.getValue("teamapt_access_token_url");
            HashMap<String, String> params = new HashMap<>(8);
            header.put("Authorization", refreshToken);
            String result = HttpClientUtil.sendPost(refreshTokenUrl, header, params, "utf-8");
            if (result != null) {
                JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                JSONObject responseBody = jsonObject.getJSONObject("responseBody");
                String accessToken = responseBody.getString("accessToken");
                Long expiresIn = responseBody.getLong("expiresIn");
                logger.info("用户 {} 获取access_token：{}" + accessToken);
                //更新到config表中
                sysConfig.setValue(accessToken);
                sysConfig.setRemark(Long.toString(currentTimeMillis + expiresIn));
                long updateSysConfig = 0;
                try {
                    updateSysConfig = sysConfigServiceImpl.updateSysConfig(sysConfig);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                logger.info("用户 {} 更新config表的结果" + updateSysConfig);
            }

        }
        return sysConfig.getValue();
    }

    private boolean isAfter6Month(Date lastUseTime) {
        int days = DateUtil.daysBetween(lastUseTime, new Date());
        return days > 180;
    }
}