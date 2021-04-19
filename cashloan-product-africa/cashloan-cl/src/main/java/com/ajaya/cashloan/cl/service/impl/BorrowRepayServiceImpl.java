package com.ajaya.cashloan.cl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.*;
import com.ajaya.cashloan.cl.mapper.*;
import com.ajaya.cashloan.cl.model.*;
import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.domain.BorrowProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ajaya.cashloan.cl.domain.entity.SpecialListRequest;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.BussinessException;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.common.util.HttpUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.mapper.UserMapper;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tool.util.NumberUtil;
import tool.util.StringUtil;

/**
 * 还款计划ServiceImpl
 *
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 13:42:32
 */

@Service("borrowRepayService")

public class BorrowRepayServiceImpl extends BaseServiceImpl<BorrowRepay, Long> implements BorrowRepayService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayServiceImpl.class);

    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    @Resource
    private UrgeRepayOrderService urgeRepayOrderService;
    @Resource
    private UrgeRepayOrderLogService urgeRepayOrderLogService;
    @Resource
    private BorrowRepayFqService borrowRepayFqService;
    @Resource
    private BorrowRepayFqMapper borrowRepayFqMapper;
    @Resource
    private UrgeOrderPushLogService urgeOrderPushLogService;

    @Override
    public BaseMapper<BorrowRepay, Long> getMapper() {
        return borrowRepayMapper;
    }

    @Override
    public int save(BorrowRepay borrowRepay) {
        return borrowRepayMapper.save(borrowRepay);
    }

    @Override
    public synchronized boolean genRepayPlan(Borrow borrow) {
        // 是否启用砍头息
        BorrowProduct product = Global.getProduct(borrow.getUserType());
        Boolean beheadFee = product.getBeheadFee();
        //放款成功,保存还款计划
        BorrowRepay br = new BorrowRepay();
        //启用
        if (beheadFee) {
            br.setAmount(borrow.getAmount() + borrow.getInterest());
        } else {
            br.setAmount(borrow.getAmount() + borrow.getFee());
        }
        br.setBorrowId(borrow.getId());
        br.setUserId(borrow.getUserId());
        String repay = DateUtil.dateStr2(DateUtil.rollDay(DateUtil.getNow(), (Integer.parseInt(borrow.getTimeLimit())) - 1));
        repay = repay + " 23:59:59";
        br.setRepayTime(DateUtil.valueOf(repay, "yyyy-MM-dd HH:mm:ss"));
        br.setState(BorrowRepayModel.STATE_REPAY_NO);
        br.setPenaltyAmout(0.0);
        br.setPenaltyDay("0");
        Date now = DateUtil.getNow();
        br.setCreateTime(now);
        Integer count = borrowRepayMapper.selectCountByBorrowId(borrow.getId());
        int result = 0;
        if (count < 1) {
            result = borrowRepayMapper.save(br);
        }
        return result > 0;
    }

    @Override
    public void authSignApply(Long userId) {
        /*Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
		paramMap.put("state", BorrowRepayModel.STATE_REPAY_NO);
		List<BorrowRepay> borrowRepayList = borrowRepayMapper.findUnRepay(paramMap);

		// 若未还款列表不为null 并列表数目大于0 则进行授权
		if (null != borrowRepayList && !borrowRepayList.isEmpty()) {
			for (BorrowRepay borrowRepay : borrowRepayList) {
				authApply(borrowRepay);
			}
		}*/
    }


    @Override
    public Page<ManageBRepayModel> listModel(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBRepayModel> list = borrowRepayMapper.listModel(params);
        if(list != null && list.size() > 0){
        	for (ManageBRepayModel manageBRepayModel : list) {
				Borrow borrow = clBorrowMapper.findByPrimary(manageBRepayModel.getBorrowId());
				if(borrow != null){
					if(borrow.getPayChannelId() != null && "2".equals(borrow.getPayChannelId().toString())){
						manageBRepayModel.setPayChannel("Zavron");
					} else {
						manageBRepayModel.setPayChannel("Kudos");
					}
					Long borrowId = borrow.getId();
					//查询已还金额
					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put("borrowId", borrowId);
					BorrowRepayLog borrowRepayLog = borrowRepayLogMapper.findSelective(paramMap);
					Double successMoneyFq = 0.0;
					if(borrowRepayLog == null){
						successMoneyFq = borrowRepayFqMapper.getSuccessMoneyFq(borrowId);
					} else {
						successMoneyFq = borrowRepayLog.getAmount() + borrowRepayLog.getPenaltyAmout();
					}
					manageBRepayModel.setSuccessMoneyFq(successMoneyFq);
					//剩下应还金额
					Double shengXiaRepayMoney = borrowRepayFqService.getShengXiaRepayMoney(borrowId);
					manageBRepayModel.setShengXiaRepayMoney(shengXiaRepayMoney);
					//最近一笔待分期还款金额
					BorrowRepayFq borrowRepayFq = borrowRepayFqMapper.getCurrentBorrowRepayFq(borrowId);
					Double CurrentBorrowRepayFqMoney = 0.0;
					String currentBorrowRepayFqStr = "";
					if(borrowRepayFq != null){
						CurrentBorrowRepayFqMoney = borrowRepayFq.getAmount();
						Date createTimeFq = borrowRepayFq.getCreateTime();
						if(borrowRepayFq.getUpdateTime() != null){
							createTimeFq = borrowRepayFq.getUpdateTime();
						}
						int fqValidTime = Global.getInt("fq_valid_time");
						int hours =  DateUtil.minuteBetween(createTimeFq, new Date()) / 60;
						if(hours <= fqValidTime){
							currentBorrowRepayFqStr = (fqValidTime - hours) + "h";
						} else {
							currentBorrowRepayFqStr = "expired";
						}
					}
					manageBRepayModel.setCurrentBorrowRepayFqMoney(CurrentBorrowRepayFqMoney);
					manageBRepayModel.setCurrentBorrowRepayFqStr(currentBorrowRepayFqStr);
				}
			}
        }
        return (Page<ManageBRepayModel>) list;
    }

    @Override
    public Map<String, Object> confirmRepay(Map<String, Object> param) {
        Map<String, Object> result = new HashMap<>(8);
        Long id = (Long) param.get("id");
        logger.info("进入确认还款...借款borrowRepay表的id=" + id);
        logger.info("进入确认还款...借款state=" + param.get("state"));
        BorrowRepay br = borrowRepayMapper.findByPrimary(id);
        String state = (String) param.get("state");
        if ("10".equals(state)) {
            state = BorrowModel.STATE_FINISH;
            param.put("amount", StringUtil.isNull(br.getAmount()));
            param.put("penaltyAmout", StringUtil.isNull(br.getPenaltyAmout()));
        } else if ("20".equals(state)) {
            state = BorrowModel.STATE_REMISSION_FINISH;
            double repayAmount = NumberUtil.getDouble(param.get("amount") != null ? (String) param.get("amount") : "0");
            if (br.getAmount() < repayAmount) {
                result.put("Code", Constant.FAIL_CODE_VALUE);
                result.put("Msg", "还款金额不能大于应还金额");
                return result;
            }
            double penaltyAmount = NumberUtil.getDouble(param.get("penaltyAmout") != null ? (String) param.get("penaltyAmout") : "0");
            if (br.getPenaltyAmout() < penaltyAmount) {
                result.put("Code", Constant.FAIL_CODE_VALUE);
                result.put("Msg", "逾期罚金不能大于原逾期罚金");
                return result;
            }
        }
        logger.info("进入确认还款...借款,更新还款信息");
        Date repayTime = (Date) param.get("repayTime");
        // 更新还款信息
        int msg = updateBorrowReplay(br, repayTime, param);
        if (msg <= 0) {
            throw new BussinessException("更新还款信息出错" + br.getBorrowId());
        }
        // 更新借款表和借款进度状态
        msg = updateBorrow(br.getBorrowId(), br.getUserId(), state);
        if (msg <= 0) {
            throw new BussinessException("更新借款表和借款进度状态出错" + br.getBorrowId());
        }
        Borrow borrow = clBorrowMapper.findByPrimary(br.getBorrowId());
        // 更新催收订单中的状态
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("borrowId", br.getBorrowId());
        UrgeRepayOrder order = urgeRepayOrderService.findOrderByMap(orderMap);
        if (order != null) {
            logger.debug("更新存在的催收订单中的状态");
            UrgeRepayOrderLog orderLog = new UrgeRepayOrderLog();
            orderLog.setRemark("用户还款成功");
            orderLog.setWay("50");
            orderLog.setCreateTime(DateUtil.getNow());
            orderLog.setState(UrgeRepayOrderModel.STATE_ORDER_SUCCESS);
            urgeRepayOrderLogService.saveOrderInfo(orderLog, order, null);
//
            try {
                logger.info("borrowId:" + order.getBorrowId() + ",向催收系统推送还款信息");
                urgeOrderPushLogService.pushRepaymet(order.getBorrowId());
            } catch (Exception e) {
                logger.info("borrowId:" + order.getBorrowId() + ",向催收系统推送还款信息,报错,",e);
            }

        }
        result.put("Code", Constant.SUCCEED_CODE_VALUE);
        result.put("Msg", "还款成功");
        return result;
    }

    /**
     * 更新借款表和借款进度状态
     *
     * @param borrowId
     * @param userId
     * @return
     */
    public int updateBorrow(long borrowId, long userId, String state) {
        int i = 0;
        // 更新借款状态
        Map<String, Object> stateMap = new HashMap<String, Object>();
        stateMap.put("id", borrowId);
        stateMap.put("state", state);
        i = clBorrowMapper.updateSelective(stateMap);
        if (i > 0) {
            // 添加借款进度
            BorrowProgress bp = new BorrowProgress();
            bp.setBorrowId(borrowId);
            bp.setUserId(userId);
            bp.setRemark(BorrowModel.convertBorrowRemark(state));
            bp.setState(state);
            bp.setCreateTime(DateUtil.getNow());
            return borrowProgressMapper.save(bp);
        }
        return i;
    }

    /**
     * 更新还款计划和还款记录表
     *
     * @param br        还款计划
     * @param repayTime 还款时间
     * @param param     参数
     * @return nit
     */
    public int updateBorrowReplay(BorrowRepay br, Date repayTime,
                                  Map<String, Object> param) {
        // 更新还款计划状态
        int i = 0;
        logger.info("进入确认还款...借款,更新还款计划状态");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", br.getId());
        paramMap.put("state", BorrowRepayModel.STATE_REPAY_YES);

        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        Date repayPlanTime = DateUtil.valueOf(time.format(br.getRepayTime()));
        Date repay_time = DateUtil.valueOf(time.format(repayTime));

        if (StringUtil.isNotBlank(br.getPenaltyDay()) && br.getPenaltyAmout() > 0) {
            //实际还款时间在应还款时间之前或当天（不对比时分秒），重置逾期金额和天数
            if (!repay_time.after(repayPlanTime)) {
                br.setPenaltyDay(String.valueOf(0));
                br.setPenaltyAmout(0d);
                paramMap.put("penaltyDay", "0");
                paramMap.put("penaltyAmout", 0.00);
            }
        }
        i = borrowRepayMapper.updateParam(paramMap);
        if (i > 0) {
            // 生成还款记录
            BorrowRepayLog log = new BorrowRepayLog();
            log.setBorrowId(br.getBorrowId());
            log.setRepayId(br.getId());
            log.setUserId(br.getUserId());
            // 实际还款金额
            log.setAmount(Double.valueOf((String) param.get("amount")));
            // 实际还款时间
            log.setRepayTime(repayTime);
            log.setPenaltyDay(br.getPenaltyDay());
            // 实际还款时间在应还款时间之前或当天（不对比时分秒），重置逾期金额和天数
            if (!repay_time.after(repayPlanTime)) {
                log.setPenaltyAmout(0.00);
                log.setPenaltyDay("0");
            } else {
                // 金额减免时 罚金可页面填写
                String penaltyAmout = StringUtil.isNull(param.get("penaltyAmout"));
                if (StringUtil.isNotBlank(penaltyAmout)) {
                    log.setPenaltyAmout(NumberUtil.getDouble(penaltyAmout));
                } else {
                    log.setPenaltyAmout(br.getPenaltyAmout());
                }
            }

            log.setSerialNumber((String) param.get("serialNumber"));
            log.setRepayAccount((String) param.get("repayAccount"));
            log.setRepayWay((String) param.get("repayWay"));
            log.setCreateTime(DateUtil.getNow());
            return borrowRepayLogMapper.save(log);
        }
        return i;
    }

    @Override
    public List<BorrowRepay> listSelective(Map<String, Object> paramMap) {
        return borrowRepayMapper.listSelective(paramMap);
    }


    @Override
    public int updateLate(BorrowRepay data) {
        return borrowRepayMapper.updateLate(data);
    }

    @Override
    public int updateSelective(Map<String, Object> paramMap) {
        return borrowRepayMapper.updateSelective(paramMap);
    }


    @Override
    public Page<ManageBorrowModel> listRepayModel(Map<String, Object> params,
                                                  int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = borrowRepayMapper.listRepayModel(params);
        return (Page<ManageBorrowModel>) list;
    }

    @Override
    public Page<ManageBorrowModel> listModelNotUrge(Map<String, Object> params,
                                                    int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = borrowRepayMapper
                .listModelNotUrge(params);
        return (Page<ManageBorrowModel>) list;
    }

    @Override
    public BorrowRepay findSelective(Map<String, Object> paramMap) {
        return borrowRepayMapper.findSelective(paramMap);
    }

    @Override
    public List<ManageBRepayModel> listAllModel(Map<String, Object> params) {
        List<ManageBRepayModel> list = borrowRepayMapper.listModel(params);
        return list;
    }

    @Override
    public Map<String, String> repayment(Long borrowId, Long userId, String ip) {
//        // 借款状态判断，如果不是贷款中或者逾期，抛出异常
//        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
//        if (borrow == null) {
//            throw new BussinessException("借款信息有误");
//        }
//
//        String state = borrow.getState();
//        if (BorrowModel.STATE_REPAY_PROCESSING.equals(state)) {
//            throw new BussinessException("还款正在处理中");
//        }
//        if (!BorrowModel.STATE_REPAY.equals(state) &&
//                !BorrowModel.STATE_DELAY.equals(state) &&
//                !BorrowModel.STATE_BAD.equals(state)) {
//            throw new BussinessException("借款状态有误");
//        }
//
//        // 根据借款标识(还款标识) 查询应还款金额
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("borrowId", borrowId);
//        paramMap.put("userId", userId);
//        BorrowRepay borrowRepay = findSelective(paramMap);
//
//        // 还款金额
//        double principal = borrowRepay.getAmount();
//        double penaltyAmout = borrowRepay.getPenaltyAmout();
//        double amount = BigDecimalUtil.add(principal, penaltyAmout);
//
//        // 根据类型 走不同支付渠道，获取SDK所需参数
//        String orderNo = OrderNoUtil.getSerialNumber();
//        // APP应用 + 小额贷款
//        HashMap<String, Object> searchMap = new HashMap<>(8);
//        searchMap.put("userId", borrow.getUserId());
//        searchMap.put("status", "1");
//        searchMap.put("verifiedCode", "1");
//        ClBankAccountAuth bankCard = clBankAccountAuthMapper.findSelective(searchMap);
//        Map<String, String> sdkParam = paySdkParamsBaofu(userId, bankCard, amount, orderNo, borrowId);
//
//        // 若参数封装失败，直接返回失败，业务不再执行
//        if (sdkParam == null || sdkParam.size() < 1) {
//            throw new BussinessException("支付失败");
//        }
//
//        // 若参数封装成功， 标的状态修改为赎回处理中（还款处理中）
//        // updateBorrow(borrowId, userId, BorrowModel.STATE_REPAY_PROCESSING);
//
//        // 插入一条待支付的支付记录
//        String cardNo = bankCard.getCardNo();
//        String bank = bankCard.getBank();
//
//        String scenes = PayLogModel.SCENES_ACTIVE_REPAYMENT;
//
//        //payLogService.savePayLog(orderNo, userId, borrowId, amount, cardNo, bank, scenes);

        return null;
    }

//
//    /**
//     * 宝付还款跳转H5参数封装
//     *
//     * @param type
//     * @param amount
//     * @param ip
//     * @param bodyL
//     * @param orderNo
//     * @return
//     */
//    public Map<String, String> paySdkParamsBaofu(Long userId, BankCard bankCard, double amount, String orderNo, Long borrowId) {
//
//        // 查询所需参数
//        User user = userMapper.findByPrimary(userId);
//        UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
//        if (StringUtil.isBlank(bankCard.getAgreeNo())) {
//            throw new BussinessException(StringUtil.isNull(Constant.FAIL_CODE_VALUE), "银行卡未绑定,请先绑定银行卡");
//        }
//
//        // 连连认证支付所需参数加入Map集合
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("repay_url", Global.getValue("server_host") + "/h5/baofu/pay.html?borrowId=" + borrowId + "&cardId=" + bankCard.getId());
//        return params;
//
//    }

    @Override
    public void repaymentReturn(String payResult, String payOrderNo) {
    }

    @Override
    public void repaymentNotify(PayLog payLog, String logState, String amount, String repayWay, String repayAccount, double payFee) {

        Map<String, Object> repayMap = new HashMap<String, Object>();
        repayMap.put("userId", payLog.getUserId());
        repayMap.put("borrowId", payLog.getBorrowId());
        BorrowRepay borrowRepay = borrowRepayMapper.findSelective(repayMap);
        // 若已完成还款，那么直接返回success
        if (borrowRepay == null || BorrowRepayModel.STATE_REPAY_YES.equals(borrowRepay.getState())) {
            logger.warn("还款计划有误");
            return;
        }

        Long borrowId = borrowRepay.getBorrowId();

        // 判断交易状态，若为成功 调用确认还款； 若为失败， 修改还款状态
        if (PayLogModel.STATE_PAYMENT_SUCCESS.equals(logState)) {
            logger.info("还款支付成功，订单还款及生成续借记录...");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", borrowRepay.getId());
            param.put("repayTime", DateUtil.getNow());
            param.put("repayWay", repayWay);
            param.put("repayAccount", "");
            param.put("amount", amount);
            param.put("serialNumber", payLog.getOrderNo());
            param.put("penaltyAmout", borrowRepay.getPenaltyAmout());
            param.put("state", BorrowRepayModel.STATE_REPAY_YES);
            param.put("payFee", payFee);

            confirmRepay(param);

            // 发送扣款成功短信
            // clSmsService.repayInform(borrowRepay.getUserId(), borrowId);
        } else if (PayLogModel.STATE_PAYMENT_FAILED.equals(logState)) {
            logger.info("还款支付失败，还原借款状态");
            String state = "";
            // 如果还款时间在当前时间前面，说明已经逾期
            if (borrowRepay.getRepayTime().before(DateUtil.getNow())) {
                state = BorrowModel.STATE_DELAY;
            } else {
                state = BorrowModel.STATE_REPAY;
            }
            // 修改标的状态为 借款中/逾期
            clBorrowMapper.updateState(state, borrowId);

        }
    }

    @Override
    public List<BorrowRepay> findUnRepayForSms(Map<String, Object> paramMap) {
        return borrowRepayMapper.findUnRepayForSms(paramMap);
    }

    @Override
    public Map<String, String> repaymentH5(Long borrowId, long userId, String ip) {
//        // 借款状态判断，如果不是贷款中或者逾期，抛出异常
//        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
//        if (borrow == null) {
//            throw new BussinessException("借款信息有误");
//        }
//
//        String state = borrow.getState();
//        if (BorrowModel.STATE_REPAY_PROCESSING.equals(state)) {
//            throw new BussinessException("还款正在处理中");
//        }
//        if (!BorrowModel.STATE_REPAY.equals(state) &&
//                !BorrowModel.STATE_DELAY.equals(state) &&
//                !BorrowModel.STATE_BAD.equals(state)) {
//            throw new BussinessException("借款状态有误");
//        }
//
//        // 根据借款标识(还款标识) 查询应还款金额
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("borrowId", borrowId);
//        paramMap.put("userId", userId);
//        BorrowRepay borrowRepay = findSelective(paramMap);
//
//        // 还款金额
//        double principal = borrowRepay.getAmount();
//        double penaltyAmout = borrowRepay.getPenaltyAmout();
//        double amount = BigDecimalUtil.add(principal, penaltyAmout);
//
//        // 根据类型 走不同支付渠道，获取SDK所需参数
//        String orderNo = OrderNoUtil.getSerialNumber();
//        // APP应用 + 小额贷款
//
//        BankCard bankCard = bankCardService.getBankCardByUserId(borrow.getUserId());
//        Map<String, String> sdkParam = paySdkParamsH5(userId, bankCard.getAgreeNo(), amount, orderNo);
//
//        // 若参数封装失败，直接返回失败，业务不再执行
//        if (sdkParam == null || sdkParam.size() < 1) {
//            throw new BussinessException("支付失败");
//        }
//
//        // 若参数封装成功， 标的状态修改为赎回处理中（还款处理中）
//        // updateBorrow(borrowId, userId, BorrowModel.STATE_REPAY_PROCESSING);
//
//        // 插入一条待支付的支付记录
//        String cardNo = bankCard.getCardNo();
//        String bank = bankCard.getBank();
//
//        String scenes = PayLogModel.SCENES_ACTIVE_REPAYMENT;
//
//        payLogService.savePayLog(orderNo, userId, borrowId, amount, cardNo, bank, scenes);

//        return sdkParam;
        return null;
    }


    @Override
    public List<Map<String, Object>> findReaymentOverdueList(long penalty_day) {
        return borrowRepayMapper.findReaymentOverdueList(penalty_day);
    }

    @Override
    public BorrowRepay findUnRepay(Map<String, Object> paramMap) {
        return borrowRepayMapper.findUnRepay(paramMap);
    }

    @Override
    public List<BorrowRepay> findUnRepayDaiKou(Map<String, Object> paramMap) {
        return borrowRepayMapper.findUnRepayDaiKou(paramMap);
    }

    @Override
    public BorrowRepay findByBIdAndState(Long borrowId, String state) {
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("borrowId", borrowId);
        paramMap.put("state", state);
        return borrowRepayMapper.findByBIdAndState(paramMap);
    }

    @Override
    public List<List<String>> fileBatchRepay(MultipartFile repayFile, String type) throws Exception {

        return null;
    }

    @Override
    public Map<String, String> paySdkParams(Long userId, String agreeNo, double amount, String orderNo) {

        return null;
    }

    @Override
    public Map<String, String> paySdkParamsH5(Long userId, String agreeNo, double amount, String orderNo) {

        return null;
    }

    @Override
    public Integer repayCheck(Long borrowId, String amount) {


        Integer flag = 0;
        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("borrowId", borrow.getId());
        BorrowRepay borrowRepay = borrowRepayMapper.findSelective(paramMap);
        borrowRepay.setPenaltyAmout(Double.parseDouble(amount));
        flag = borrowRepayMapper.updateForCuishou(borrowRepay);

        BorrowProgress borrowProgress = new BorrowProgress();
        borrowProgress.setUserId(borrow.getUserId());
        borrowProgress.setBorrowId(borrowId);
        borrowProgress.setState("51");
        borrowProgress.setRemark("Change the amount of repayment:" + amount);
        borrowProgress.setCreateTime(new Date());
        borrowProgressMapper.save(borrowProgress);

        return flag;

    }

    @Override
    public List<BorrowRepayModel> findOverDueListByRepayTime(String date) {
        return borrowRepayMapper.findOverDueListByRepayTime(date);
    }
    
    @Override
	public List<BorrowRepay> listByRepayTimeByDesc(Map<String, Object> paramMap) {
		return borrowRepayMapper.listByRepayTimeByDesc(paramMap);
	}
    @Override
    public List<Map<String, Object>> findReaymentOverdueListNew(long penalty_day) {
        return borrowRepayMapper.findReaymentOverdueListNew(penalty_day);
    }

    @Override
    public Integer getPayNumByUserTypeDay(String userType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        Integer num = borrowRepayMapper.getPayNumDay(format, userType);
        logger.info("format:" + format + ",放款数量:" + num);
        return num;
    }

    @Override
    public BorrowRepayParamModel findRepayParam(Long borrowId, Long cardId) {
        return borrowRepayMapper.findRepayParam(borrowId,cardId);
    }

}