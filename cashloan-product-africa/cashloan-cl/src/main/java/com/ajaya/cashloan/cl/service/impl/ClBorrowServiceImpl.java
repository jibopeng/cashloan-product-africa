package com.ajaya.cashloan.cl.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.*;
import com.ajaya.cashloan.cl.mapper.*;
import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajaya.cashloan.cl.config.AsyncProcessQueue;
import com.ajaya.cashloan.cl.enums.BorrowDetailEnum;
import com.ajaya.cashloan.cl.model.BorrowConfirmModel;
import com.ajaya.cashloan.cl.model.BorrowDetailModel;
import com.ajaya.cashloan.cl.model.BorrowListModel;
import com.ajaya.cashloan.cl.model.BorrowProgressModel;
import com.ajaya.cashloan.cl.model.ClBorrowDetailModel;
import com.ajaya.cashloan.cl.model.ClBorrowModel;
import com.ajaya.cashloan.cl.model.IndexModel;
import com.ajaya.cashloan.cl.model.ManageBorrowModel;
import com.ajaya.cashloan.cl.model.ManageBorrowTestModel;
import com.ajaya.cashloan.cl.model.MessageSendModel;
import com.ajaya.cashloan.cl.model.MessageTypeModel;
import com.ajaya.cashloan.cl.model.PayLogModel;
import com.ajaya.cashloan.cl.model.PendingRepayDetailModel;
import com.ajaya.cashloan.cl.model.PendingRepayModel;
import com.ajaya.cashloan.cl.model.RepayModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.BussinessException;
import com.ajaya.cashloan.core.common.exception.SimpleMessageException;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.BorrowProduct;
import com.ajaya.cashloan.core.domain.User;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.mapper.UserMapper;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.core.model.UserBaseInfoModel;
import com.ajaya.cashloan.core.service.CloanUserService;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import com.ajaya.cashloan.rc.domain.SceneBusinessLog;
import com.ajaya.cashloan.rc.domain.TppBusiness;
import com.ajaya.cashloan.rc.mapper.SceneBusinessLogMapper;
import com.ajaya.cashloan.rc.mapper.SceneBusinessMapper;
import com.ajaya.cashloan.rc.model.TppServiceInfoModel;
import com.ajaya.cashloan.rc.service.SceneBusinessLogService;
import com.ajaya.cashloan.rc.service.SimpleBorrowCountService;
import com.ajaya.cashloan.rc.service.SimpleContactCountService;
import com.ajaya.cashloan.rc.service.SimpleVoicesCountService;
import com.ajaya.cashloan.rc.service.TppBusinessService;
import com.ajaya.cashloan.rc.service.Zx91DetailService;
import com.ajaya.cashloan.rule.domain.BorrowRuleResult;
import com.ajaya.cashloan.rule.domain.RuleEngine;
import com.ajaya.cashloan.rule.domain.RuleEngineConfig;
import com.ajaya.cashloan.rule.mapper.BorrowRuleEngineMapper;
import com.ajaya.cashloan.rule.mapper.BorrowRuleResultMapper;
import com.ajaya.cashloan.rule.mapper.BorrowScoreResultMapper;
import com.ajaya.cashloan.rule.mapper.RuleEngineConfigMapper;
import com.ajaya.cashloan.rule.mapper.RuleEngineInfoMapper;
import com.ajaya.cashloan.rule.mapper.RuleEngineMapper;
import com.ajaya.cashloan.rule.model.ManageReviewModel;
import com.ajaya.cashloan.rule.model.ManageRuleResultModel;
import com.ajaya.cashloan.rule.model.srule.client.RulesExecutorUtil;
import com.ajaya.cashloan.rule.model.srule.model.SimpleRule;
import com.ajaya.cashloan.rule.service.BorrowRuleResultService;
import com.ajaya.cashloan.system.service.SysConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tool.util.BigDecimalUtil;

/**
 * ???????????????ServiceImpl
 *
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 10:36:53
 */
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
@Service("clBorrowService")
@Transactional(rollbackFor = Exception.class)
public class ClBorrowServiceImpl extends BaseServiceImpl<Borrow, Long> implements ClBorrowService {

    private static final Logger logger = LoggerFactory.getLogger(ClBorrowServiceImpl.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    @Resource
    private BorrowFlagMapper borrowFlagMapper;
    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    @Resource
    private RuleEngineInfoMapper ruleEngineInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    private BorrowRuleEngineMapper borrowRuleEngineMapper;
    @Resource
    private RuleEngineConfigMapper ruleEngineConfigMapper;
    @Resource
    private BorrowRuleResultMapper borrowRuleResultMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private PayLogMapper payLogMapper;
    @Resource
    private UrgeRepayOrderMapper urgeRepayOrderMapper;
    @Resource
    private RuleEngineMapper ruleEngineMapper;
    @Resource
    private ClSmsService clSmsService;
    @Resource
    private SceneBusinessLogMapper sceneBusinessLogMapper;
    @Resource
    private SceneBusinessLogService sceneBusinessLogService;
    @Resource
    private SceneBusinessMapper sceneBusinessMapper;
    @Resource
    private SimpleBorrowCountService simpleBorrowCountService;
    @Resource
    private SimpleVoicesCountService simpleVoicesCountService;
    @Resource
    private SimpleContactCountService simpleContactCountService;
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private TppBusinessService tppBusinessService;
    @Resource
    private BorrowRepayService borrowRepayService;
    @Resource
    private BorrowScoreResultMapper borrowScoreResultMapper;
    @Resource
    private UserBlackInfoMapper userBlackInfoMapper;
    @Resource
    private Zx91DetailService zx91DetailService;
    @Resource
    private UserEquipmentInfoMapper userEquipmentInfoMapper;
    @Resource
    private UserContactsMapper userContactsMapper;
    @Resource
    private BorrowRuleResultService borrowRuleResultService;
    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private SmsTplMapper smsTplMapper;
    @Resource
    private UserEquipmentInfoService userEquipmentInfoServiceImpl;
    @Resource
    private UserEmerContactsMapper userEmerContactsMapper;

    @Resource
    private ClSmsService clSmsServiceImpl;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private PayReqLogService payReqLogServiceImpl;
    @Resource
    private CloanUserService cloanUserServiceImpl;
    //@Resource
    //private AdJustEnventMonitoService adJustEnventMonitoServiceImpl;
    @Resource
    private UserAppFlyerLogService userAppFlyerLogService;
    @Resource
    private UserMessagesService userMessagesService;
    @Resource
    private UserMessagesMapper userMessagesMapper;
    @Resource
    private UserDataCatchLogService userDataCatchLogService;
    @Resource
    private BorrowRepayFqService borrowRepayFbService;
    @Resource
    private PayChannelManageService payChannelManageService;
    @Resource
    private SdkCatchDataCallBackLogService sdkCatchDataCallBackLogServiceImpl;
    @Resource
    private SdkCatchDataFindIndexLogService sdkCatchDataFindIndexLogServiceImpl;
    @Resource
    private ClBorrowRuleService clBorrowRuleServiceImpl;
    @Resource
    private BankCardMapper bankCardMapper;
    @Resource
    private BankCardLogMapper bankCardLogMapper;

    @Resource
    private BankAccountMapper bankAccountMapper;

    @Resource
    private PayStackRecipientMapper payStackRecipientMapper;
    @Override
    public BaseMapper<Borrow, Long> getMapper() {
        return clBorrowMapper;
    }

    private static final String CREATE_RECIPIENT_URL = "https://api.paystack.co/transferrecipient";
    private static final String PAY_OUT_URL = "https://api.paystack.co/transfer";

    @Override
    public boolean isCanBorrow(Borrow borrow) {

        Long userId = borrow.getUserId();
        User user = userMapper.findByPrimary(userId);
        // 1.1 ????????????????????????????????????
        if (user == null || user.getId() < 1) {
            // ??????????????????????????????
            throw new SimpleMessageException("User information not found.");
        }
        // 1.2 ??????????????????????????????
        UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
        if (userBaseInfo != null && UserBaseInfoModel.USER_STATE_BLACK.equals(userBaseInfo.getState())) {
            // ?????????????????????,????????????????????????
            throw new SimpleMessageException(
                    "This account cannot be borrowed. Please contact customer service personnel.");
        }
        // 1.3 ????????????????????????????????????
        Map<String, Object> authMap = new HashMap<String, Object>();
        authMap.put("userId", userId);
        Map<String, Object> auth = userAuthService.getAuthState(authMap);
        if (StringUtil.isBlank(auth) || Integer.parseInt(auth.get("qualified").toString()) == 0) {
            logger.error("?????? userId???" + userId + "??????????????????????????????......");
            // ??????????????????????????????
            throw new SimpleMessageException("The information is not perfect and can not be borrowed.");
        }
        // 1.4 ?????????????????????????????????
        List<Borrow> list = clBorrowMapper.findUserUnFinishedBorrow(userId);
        if (list.size() > 0) {
            logger.error("?????? userId???" + userId + "?????????????????????????????????......");
            throw new SimpleMessageException("If there is an unfinished loan, it cannot be borrowed.");
        }

        // 1.5 ??????????????????
        int day = getAgainBorrowDays(userId);
        if (day > 0) {
            logger.error("?????? userId???" + userId + "??????????????????" + day + "??????????????????????????????......");
            throw new SimpleMessageException("Please try again " + day + " days later.");
        }
        // 1.6??????????????????
        double repayTotal = borrowRepayMapper.findRepayTotal();
        double loanCeiling = Global.getDouble("loan_ceiling");

        // //??????????????????????????????
        if (!isRefinanceBeforeCreateBorroworder(userId)) {
            if (loanCeiling < 0 || (repayTotal > 0 && repayTotal >= loanCeiling)) {
                logger.error("?????? userId???" + userId + "????????????????????????");
                //?????????????????????????????????????????????
                throw new SimpleMessageException("Dear User, our payment servers have attained the daily payment limit. Please apply tomorrow. Regret the inconvenience.");
            }
        }
        return true;
    }

    @Override
    public List<PendingRepayModel> findRepay(Map<String, Object> searchMap) {
        List<PendingRepayModel> modelList = clBorrowMapper.findPendingRepay(searchMap);
        for (PendingRepayModel pendingRepayMode : modelList) {
            if (StringUtil.isNotBlank(pendingRepayMode)) {
                int day = DateUtil.daysBetween(new Date(), pendingRepayMode.getRepayTime());
                if (day > 0) {
                    pendingRepayMode.setDueDay(day);
                } else if (day == 0) {
                    pendingRepayMode.setDueDay(day);
                }
                pendingRepayMode.setRepayTimeStr(DateUtils.getIndiatnFormatDate(pendingRepayMode.getRepayTime()));
                pendingRepayMode
                        .setApplicationTimeStr(DateUtils.getIndiatnFormatDate(pendingRepayMode.getApplicationTime()));
                //????????????????????????
                //????????????????????????????????????????????????,?????????????????????
                Double currentNeedReapyMoney = borrowRepayFbService.getCurrentNeedReapyMoney(pendingRepayMode.getBorrowId());
                pendingRepayMode.setRepayment(currentNeedReapyMoney);
            }
        }
        return modelList;
    }

    @Override
    public BorrowDetailModel findBorrowDetailById(Long borrowId) {
        BorrowDetailModel borrowDetailModel = clBorrowMapper.findBorrowDetailById(borrowId);
        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
        String state = borrow.getState();
        if (BorrowModel.STATE_AUTO_REFUSED.equals(state)||BorrowModel.STATE_REFUSED.equals(state)){
            borrowDetailModel.setTotalPayment(BigDecimalUtil.add(borrow.getAmount(),borrow.getInterest()));
            borrowDetailModel.setRejectRemark("Sorry, currently you do not meet our criteria, please  apply after "+ Global.getValue("again_borrow")+" days.");
        }
        if (BorrowModel.STATE_REPAY.equals(state) || BorrowModel.STATE_DELAY.equals(state) ||  BorrowModel.STATE_REPAY_PROCESSING.equals(state)){
            Double currentNeedRepayMoney = borrowRepayFbService.getCurrentNeedReapyMoney(borrowId);
            borrowDetailModel.setRepaymentAmount(currentNeedRepayMoney);
        }
        //??????????????????
        if (BorrowModel.STATE_FINISH.equals(state)){
            BorrowRepayLog borrowRepayLog = borrowRepayLogMapper.findByBorrowId(borrowId);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            borrowDetailModel.setRepaymentDay(format.format(borrowRepayLog.getCreateTime()));
        }
        return borrowDetailModel;
    }

    @Override
    public ResponseWrapper getPayInfo(Long borrowId) {
        ClBorrowDetailModel clBorrowDetailModel = clBorrowMapper.findPayInfo(borrowId);
        if (clBorrowDetailModel != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date repayTimeDate = DateUtil.dateAddDays(new Date(),
                    Integer.parseInt(clBorrowDetailModel.getTenure()) - 1);
            String repayTime = sdf.format(repayTimeDate);
            String appliCationTime = sdf.format(new Date());
            clBorrowDetailModel.setRepayTime(repayTime);
            clBorrowDetailModel.setApplicationDate(appliCationTime);
        } else {
            return ResponseWrapper.error("The order was not found.");
        }
        logger.info("??????{}????????????????????????", borrowId);
        return ResponseWrapper.success("The query was successful.", clBorrowDetailModel);
    }

    @Override
    public PendingRepayDetailModel findRepayDetail(Map<String, Object> searchMap) {
        PendingRepayDetailModel detail = clBorrowMapper.findPendingRepayDetail(searchMap);
        if (detail != null) {
            detail.setRepayTimeStr(DateUtils.getIndiatnFormatDate(detail.getRepayTime()));
            detail.setApplicationTimeStr(DateUtils.getIndiatnFormatDate(detail.getApplicationTime()));
            int day = DateUtil.daysBetween(new Date(), detail.getRepayTime());
            if (day > 0) {
                detail.setDueDay(day);
            } else if (day == 0) {
                detail.setDueDay(day);
            }
            detail.setRepayment(borrowRepayFbService.getCurrentNeedReapyMoney(detail.getBorrowId()));
        }
        return detail;
    }

    @Override
    public Page<BorrowListModel> pageList(Long userId, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        List<BorrowListModel> list = clBorrowMapper.findBorrowList(userId);
        return (Page<BorrowListModel>) list;
    }

    @Override
    public List<IndexModel> listIndex() {
        List<IndexModel> list = clBorrowMapper.listIndex();
        List indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String cardNo = list.get(i).getCardNo();
            if (StringUtil.isBlank(cardNo)) {
                continue;
            }
            cardNo = StringUtil.substring(cardNo, cardNo.length() - 4);
            double money = list.get(i).getAmount();
            int time = 0;
            if (list.get(i) != null && list.get(i).getCreateTime() != null && list.get(i).getLoanTime() != null) {
                time = DateUtil.minuteBetween(list.get(i).getCreateTime(), list.get(i).getLoanTime());
            }

            String value = "??????" + cardNo + " " + "????????????" + (int) (money) + "Rs ??????" + time + "??????";
            Map<String, Object> map = new HashMap<>(8);
            map.put("id", i);
            map.put("value", value);
            indexList.add(map);
        }
        return indexList;
    }

    @Override
    public Map<String, Object> findIndex(String userId) {
        //???????????????
        Map<String, Object> result = new HashMap<>(8);
        // ??????
        String title = Global.getValue("title");
        //????????????
        int authTotal = Global.getInt("auth_total");
        Map<String, Object> auth = new HashMap<>(8);
        auth.put("total", authTotal);
        auth.put("result", 0);
        auth.put("qualified", 0);
        Borrow borrow;
        //??????????????? ??????
        Boolean haveBorrow = false;
        // ??????????????????
        boolean needRepay = false;
        List<BorrowProgressModel> list = new ArrayList<>();
        Long borrowId = null;
        // ????????????
        Map<String, Object> orderInfo = new HashMap<>(8);
        orderInfo.put("borrowId", "");
        // ????????????????????????????????????????????????????????????
        orderInfo.put("amount", Global.getProduct("1").getQuota());
        orderInfo.put("state", "");
        orderInfo.put("stateStr", "");
        orderInfo.put("dueDate", "");
        result.put("isRefinance", false);
        //??????????????????????????????epoch???????????????,???????????????
        result.put("needCatch", false);
        if (StringUtil.isNotBlank(userId) && !StringUtil.equals(userId, "0")) {
            boolean refinance = isRefinanceBeforeCreateBorroworder(Long.parseLong(userId));
            //???????????????id????????????????????????????????????????????????????????????????????????????????????
            orderInfo.put("amount", refinance ? Global.getProduct("0").getQuota() : Global.getProduct("1").getQuota());
            //??????????????????
            Map<String, Object> params = new HashMap<>(8);
            params.put("userId", userId);
            auth = userAuthService.getAuthState(params);
            borrow = clBorrowMapper.findRepayBorrow(params);
            if (borrow != null) {
                String state = borrow.getState();
                if (state.equals(BorrowModel.STATE_REPAY)
                        || state.equals(BorrowModel.STATE_DELAY)
                        || state.equals(BorrowModel.STATE_BAD)) {
                    needRepay = true;
                }
                // ?????????????????????????????????haveBorrow???false ??????????????????
                if (state.equals(BorrowModel.STATE_FINISH)
                        || state.equals(BorrowModel.STATE_REMISSION_FINISH)
                        || state.equals(BorrowModel.STATE_CANCEL)) {
                    haveBorrow = false;
                } else {
                    // haveBorrow???true????????????????????????,????????????????????????????????????????????????isborrow???false??????????????????
                    if (state.equals(BorrowModel.STATE_AUTO_REFUSED)
                            || state.equals(BorrowModel.STATE_REFUSED)) {
                        haveBorrow = getAgainBorrowDays(borrow.getUserId()) > 0;
                    } else {
                        haveBorrow = true;
                    }
                    //??????????????????
                    if (haveBorrow) dealOrderInfo(borrow, orderInfo, state);
                }
                result.put("needCatch", sdkCatchDataFindIndexLogServiceImpl.needCatchData(borrow));
                result.put("isRefinance", refinance);
            }
        }
        result.put("needRepay", needRepay);
        result.put("auth", auth);
        result.put("haveBorrow", haveBorrow);
        result.put("title", title);
        result.put("orderInfo", orderInfo);
        return result;
    }

    private void dealOrderInfo(Borrow borrow, Map<String, Object> orderInfo, String state) {
        orderInfo.put("state", state);
        orderInfo.put("amount", borrow.getAmount());
        String stateNameByCode = BorrowDetailEnum.getStateNameByCode(state);
        orderInfo.put("stateStr", stateNameByCode);
        orderInfo.put("borrowId", borrow.getId());
        orderInfo.put("dueDate", DateUtils.getDateStr(borrow.getCreateTime()));
        if (BorrowModel.STATE_REPAY.equals(state) || BorrowModel.STATE_DELAY.equals(state)|| BorrowModel.STATE_REPAY_PROCESSING.equals(state)){
            Double currentNeedRepayMoney = borrowRepayFbService.getCurrentNeedReapyMoney(borrow.getId());
            orderInfo.put("repayAmount", currentNeedRepayMoney);
            HashMap<String, Object> bpMap = new HashMap<>(8);
            bpMap.put("borrowId", borrow.getId());
            BorrowRepay repay = borrowRepayMapper.findSelective(bpMap);
            //????????????????????????
            if (BorrowModel.STATE_DELAY.equals(state)) {
                if ("1".equals(repay.getPenaltyDay())) {
                    stateNameByCode = stateNameByCode.replace("@day", " for 1 day");
                } else {
                    stateNameByCode = stateNameByCode.replace("@day", " for "+repay.getPenaltyDay() + " days");
                }
                orderInfo.put("stateStr", stateNameByCode);
            }
            orderInfo.put("dueDate", DateUtils.getDateStr(repay.getRepayTime()));
        }
    }

    @Override
    public int getAgainBorrowDays(Long userId) {
        int day = 0;
        String againBorrow = Global.getValue("again_borrow");
        Borrow lastBorrow = findLastBorrow(userId);
        if (StringUtil.isNotBlank(againBorrow) && lastBorrow != null
                && (lastBorrow.getState().equals(BorrowModel.STATE_AUTO_REFUSED)
                || lastBorrow.getState().equals(BorrowModel.STATE_REFUSED))) {
            day = DateUtil.daysBetween(lastBorrow.getCreateTime(), DateUtil.getNow());
            day = Integer.parseInt(againBorrow) - day;
        }
        return day;
    }

    private void getDataCommon(BorrowProgressModel loan, BorrowProgressModel borrowProgressModel) {
        loan.setType("10");
        loan.setId(borrowProgressModel.getId());
        loan.setUserId(borrowProgressModel.getUserId());
        loan.setRemark(borrowProgressModel.getRemark());
        loan.setState(borrowProgressModel.getState());
        loan.setStr(BorrowDetailEnum.getStateNameByCode(borrowProgressModel.getState()));
        loan.setCreateTime(borrowProgressModel.getCreateTime());
        loan.setCreateTimeStr(DateUtils.getIndiatnFormatDate(borrowProgressModel.getCreateTime()));
    }

    @Override
    public Borrow saveBorrow(Borrow borrow) {
        //?????????????????? 0 ??????????????? 1???????????????
        boolean refinance = isRefinanceBeforeCreateBorroworder(borrow.getUserId());
        String userType = (refinance ? "0" : "1");
        BorrowProduct product = Global.getProduct(userType);
        Double amount = product.getQuota();
        Integer term = product.getTerm();
        Double rate = product.getRate();
        Double interest = BigDecimalUtil.round(BigDecimalUtil.mul(BigDecimalUtil.mul(amount, rate, term)));
        Double beheadMoney = product.getBeheadMoney();
        Boolean beheadFee = product.getBeheadFee();
        // ??????
        if (beheadFee) {
            borrow.setRealAmount(BigDecimalUtil.sub(amount, beheadMoney));
        } else {
            borrow.setRealAmount(amount);
        }
        borrow.setOrderNo(NidGenerator.getOrderNo());
        borrow.setState(BorrowModel.STATE_PRE);
        borrow.setCreateTime(DateUtil.getNow());
        borrow.setProductName(borrow.getProductName());
        borrow.setFee(beheadMoney);
        borrow.setAmount(amount);
        borrow.setTimeLimit(Integer.toString(term));
        borrow.setInterest(interest);
        borrow.setUserType(userType);
        clBorrowMapper.save(borrow);
        return borrow;
    }

    @Override
    public List<Borrow> findBorrowByMap(Map<String, Object> searchMap) {
        return clBorrowMapper.listSelective(searchMap);
    }

    @Override
    public Page<ManageBorrowModel> listModel(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = clBorrowMapper.listModel(params);
        return (Page<ManageBorrowModel>) list;
    }

    @Override
    public int updateSelective(Map<String, Object> data) {
        return clBorrowMapper.updateSelective(data);
    }

    /**
     * ??????????????????
     *
     * @param id    ??????id
     * @param state ??????
     */
    @Override
    public int modifyState(long id, String state) {
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("state", state);
        paramMap.put("id", id);
        return clBorrowMapper.updateSelective(paramMap);
    }

    /**
     * ??????????????????
     *
     * @param borrow ????????????
     * @param state  ?????????
     */
    @Override
    public void savePressState(Borrow borrow, String state, String remark) {
        BorrowProgress borrowProgress = new BorrowProgress();
        borrowProgress.setBorrowId(borrow.getId());
        borrowProgress.setUserId(borrow.getUserId());
        switch (state) {
            case BorrowModel.STATE_PRE:
                borrowProgress.setRemark("Borrowing " + StringUtil.isNull(borrow.getAmount()) + " rupees for "
                        + borrow.getTimeLimit() + " days at a cost of " + borrow.getInterest() + " rupees, "
                        + BorrowModel.convertBorrowRemark(state));
                break;
            case BorrowModel.STATE_AUTO_REFUSED:
                borrowProgress.setRemark(remark);
                break;
            default:
                borrowProgress.setRemark(BorrowModel.apiConvertBorrowState(state));
                break;
        }
        borrowProgress.setState(state);
        borrowProgress.setCreateTime(DateUtil.getNow());
        borrowProgressMapper.save(borrowProgress);
    }

    @Override
    public Map<String, Object> findResult(long borrowId) {
        Map<String, Object> data = new HashMap<>();

        List<ManageReviewModel> ruleList = borrowRuleResultService.findRuleResult(borrowId);
        data.put("ruleList", ruleList);
        List resultList = new ArrayList<>();
        List<ManageRuleResultModel> result = borrowRuleResultService.findResult(borrowId);
        for (ManageRuleResultModel model : result) {
            Map<String, Object> search = new HashMap<>();
            search.put("ruleId", model.getRuleId());
            search.put("borrowId", borrowId);
            List<BorrowRuleResult> infoList = borrowRuleResultService.findRule(search);
            for (BorrowRuleResult borrowRuleResult : infoList) {
                borrowRuleResult.setResultType(borrowRuleResult.alterType(borrowRuleResult.getResultType()));
            }
            model.setInfoList(infoList);
        }
        resultList.add(result);
        data.put("resultList", resultList);
        return data;
    }

    @Override
    public List<ManageBorrowTestModel> seleteUser() {
        List<ManageBorrowTestModel> list = clBorrowMapper.seleteUser();
        List<ManageBorrowTestModel> userList = new ArrayList<>();
        for (ManageBorrowTestModel user : list) {
            boolean type = true;
            Map<String, Object> searchMap = new HashMap<>();
            searchMap.put("userId", user.getUserId());
            List<Borrow> borrowList = clBorrowMapper.listSelective(searchMap);
            for (Borrow borrow : borrowList) {
                if (!borrow.getState().equals(BorrowModel.STATE_AUTO_REFUSED)
                        & !borrow.getState().equals(BorrowModel.STATE_REFUSED)
                        & !borrow.getState().equals(BorrowModel.STATE_FINISH)
                        & !borrow.getState().equals(BorrowModel.STATE_REMISSION_FINISH)) {
                    type = false;
                }
            }
            if (type) {
                userList.add(user);
            }
            if (userList.size() >= 20) {
                break;
            }
        }
        return userList;
    }

    /**
     * ????????????
     */
    @Override
    public int manualVerifyBorrow(final Long borrowId, String state, String remark) {
        int code;
        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
        if (borrow != null) {
            if (!borrow.getState().equals(BorrowModel.STATE_NEED_REVIEW)) {
                logger.error("??????????????????,?????????????????????????????????");
                throw new BussinessException("????????????,?????????????????????????????????");
            }
            final Long userId = borrow.getUserId();
            Map<String, Object> map = new HashMap<>();
            map.put("id", borrowId);
            map.put("state", state);
            map.put("remark", remark);
            code = clBorrowMapper.reviewState(map);
            if (code != 1) {
                throw new BussinessException("????????????,?????????????????????????????????");
            }
            savePressState(borrow, state, "");
            if (BorrowModel.STATE_REFUSED.equals(state) || BorrowModel.STATE_AUTO_REFUSED.equals(state)) {
                // ?????????????????????????????????
                //modifyCredit(userId, borrow.getAmount(), "unuse");
            }
            if (BorrowModel.STATE_PASS.equals(state)) {
                final UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(userId);
                //??????app?????????????????????????????????????????????
                String productName = borrow.getProductName();
                String messageType = MessageTypeModel.MESSAGE_TYPE_SMS_GET_MONEY;
                String title2 = Global.getValue("title2");
                if (title2.equals(productName)) {
                    messageType = messageType + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
                }
                final String finalMessageType = messageType;
                final Borrow borrowS = borrow;
                //????????????????????????
                AsyncProcessQueue.execute(new Runnable() {
                    @Override
                    public void run() {
                        // ????????????????????????????????????
                        clSmsServiceImpl.sendGetMoneySms(userBaseInfo.getPhone(), finalMessageType);
                        // adjust????????????????????????
                        //adJustEnventMonitoServiceImpl.adjustEnventMonito(borrowS, 3);
                        //userApp??????????????????
                        userAppFlyerLogService.appFlyerEnventMonito(borrowS, 3);
                    }
                });
            }
        } else {
            logger.error("?????????????????????????????????");
            throw new BussinessException("?????????????????????????????????");
        }
        return code;
    }

    private String findBorrowDay(long userId) {
        String remark = null;
        Map<String, Object> searchMap = new HashMap<>(8);
        searchMap.put("userId", userId);
        List<RepayModel> modelList = clBorrowMapper.findRepay(searchMap);
        for (RepayModel repayModel : modelList) {
            if (StringUtil.isNotBlank(repayModel)) {
                int day = DateUtil.daysBetween(new Date(), repayModel.getRepayTime());
                if (day > 0) {
                    remark = "?????????" + day + "????????????" + repayModel.getAmount() + "Rs";
                } else if (day == 0) {
                    remark = "????????????????????????" + repayModel.getAmount() + "Rs";
                }
            }
        }
        return remark;
    }

    /**
     * ??????????????????
     */
    @SuppressWarnings("static-access")
    @Override
    public ManageBorrowModel getModelByBorrowId(long borrowId) {
        ManageBorrowModel model = new ManageBorrowModel();
        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
        if (borrow == null) {
            logger.error("???????????????????????????");
        } else {
            model = model.instance(borrow);
            UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(borrow.getUserId());
            if (userBaseInfo != null) {
                model.setPhone(userBaseInfo.getPhone());
                model.setRealName(userBaseInfo.getRealName());
            }

            Map<String, Object> paramMap = new HashMap<>(8);
            paramMap.put("borrowId", borrowId);
            paramMap.put("state", BorrowProgressModel.PROGRESS_LOAN_SUCCESS);
            BorrowProgress bp = borrowProgressMapper.findSelective(paramMap);
            if (bp != null) {
                model.setLoanTime(bp.getCreateTime());
            }
            paramMap = new HashMap<>(8);
            paramMap.put("borrowId", borrowId);
            BorrowRepay borrowRepay = borrowRepayMapper.findSelective(paramMap);
            if (borrowRepay != null) {
                model.setPenaltyAmout(borrowRepay.getPenaltyAmout());
                model.setPenaltyDay(borrowRepay.getPenaltyDay());
                if (StringUtil.isNotBlank(borrowRepay.getAmount())) {
                    model.setRepayTotal(BigDecimalUtil.add(borrowRepay.getAmount(), borrowRepay.getPenaltyAmout()));
                } else {
                    model.setRepayTotal(0.0);
                }
            }
            paramMap = new HashMap<>(8);
            paramMap.put("borrowId", borrowId);
            BorrowRepayLog borrowRepaylog = borrowRepayLogMapper.findSelective(paramMap);
            if (borrowRepaylog != null) {
                model.setRepayTime(DateUtil.dateStr(borrowRepaylog.getRepayTime(), DateUtil.DATEFORMAT_STR_001));
                model.setRepayAmount(borrowRepaylog.getAmount());
                if (StringUtil.isNotBlank(borrowRepay.getAmount())) {
                    model.setRepayYesTotal(
                            BigDecimalUtil.add(borrowRepaylog.getAmount(), borrowRepaylog.getPenaltyAmout()));
                } else {
                    model.setRepayYesTotal(0.0);
                }

            }
            paramMap = new HashMap<>(8);
            paramMap.put("borrowId", borrowId);
            UrgeRepayOrder order = urgeRepayOrderMapper.findSelective(paramMap);
            if (order != null) {
                model.setLevel(order.getLevel());
            }
        }
        return model;
    }

    @Override
    public Page<ManageBorrowModel> listBorrowModel(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = null;
        list = clBorrowMapper.listBorrowModel(params);
        if (list != null && list.size() > 0) {
            for (ManageBorrowModel manageBorrowModel : list) {
                Map<String, Object> paramMap = new HashMap<>(8);
                paramMap.put("borrowId", manageBorrowModel.getBorrowId());
                List<BorrowProgress> listBorrowProgress = borrowProgressMapper.listSelective(paramMap);
                if (listBorrowProgress != null && listBorrowProgress.size() > 0) {
                    for (BorrowProgress borrowProgress : listBorrowProgress) {
                        if ("26".equals(borrowProgress.getState()) || "20".equals(borrowProgress.getState())) {
                            manageBorrowModel.setPassTime(borrowProgress.getCreateTime());
                        }
                        if ("30".equals(borrowProgress.getState())) {
                            manageBorrowModel.setLoanTime(borrowProgress.getCreateTime());
                        }
                    }
                }
                HashMap<String, Object> map = new HashMap<>(8);
                map.put("userId", manageBorrowModel.getUserId());
                map.put("bindingStatus", "1");
                BankAccount selective = bankAccountMapper.findSelective(map);
                if (selective != null) {
                    manageBorrowModel.setBankName(selective.getBankName());
                    manageBorrowModel.setBankAccount(selective.getAccountNumber());
                    manageBorrowModel.setIfscCode(selective.getBankCode());
                }
            }
        }
        return (Page<ManageBorrowModel>) list;
    }

    /**
     * ???????????????*???
     */
    private String createAsterisk(int length) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

    @Override
    public void updatePayState(Map<String, Object> paramMap) {
        int result = clBorrowMapper.updatePayState(paramMap);
        logger.info("???????????????????????????borrow??????, borrowId  " + paramMap.get("id") + "??????, ?????????  " + result);
        if (result < 1) {
            throw new BussinessException("?????????????????????????????????");
        }
    }

    @Override
    public Borrow findByPrimary(Long borrowId) {
        return clBorrowMapper.findByPrimary(borrowId);
    }

    @Override
    public ClBorrowModel rcBorrowApply(Borrow borrow, String mobileType, String imei, long appRunTime)
            throws Exception {
        ClBorrowModel clBorrow = new ClBorrowModel();
        Borrow realBorrow = null;
        Long userId = borrow.getUserId();
        // ????????????????????????????????????
        logger.info("?????? user_id " + userId + " ????????????????????????????????????");
        boolean isCanBorrow = isCanBorrow(borrow);
        if (isCanBorrow) {
            realBorrow = saveBorrow(borrow);
            BeanUtils.copyProperties(realBorrow, clBorrow);
            clBorrow.setNeedApprove(true);
        }
        if (realBorrow.getId() > 0) {

            UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(borrow.getUserId());
            //??????????????????
            boolean b = epidemicRiskControl(borrow, clBorrow, userBaseInfo);
            long borrowId = realBorrow.getId();
            savePressState(realBorrow, BorrowModel.STATE_PRE, "");
            //????????????imei????????????????????????
            clBorrowRuleServiceImpl.saveBorrowRule2(borrow, imei, b);
        }
        return clBorrow;
    }

    public void getThirdServiceData(final Borrow borrow, final String nid, final Long tppId) {
        if ("qtPanAuth".equals(nid)) {
            logger.info("?????? {} ??????QTPAN????????????", borrow.getUserId());
            TppBusiness business = tppBusinessService.findByNid(nid, tppId);
//			int count = userPanServiceImpl.epochPanAuthLite(borrow.getUserId());
//			syncSceneBusinessLog(borrow.getId(), nid, count);
        } else if ("epochNameCheck".equals(nid)) {
            logger.info("?????? {} ??????epoch????????????", borrow.getUserId());
            TppBusiness business = tppBusinessService.findByNid(nid, tppId);
            int count = 0;
            if (canRequestNameCheck(borrow.getUserId())) {
//				count = nameCheckServiceImpl.nameCheck(borrow.getId());
            }
            syncSceneBusinessLog(borrow.getId(), nid, count);
        } else {
            logger.error("????????????" + nid + "??????????????????????????????");
        }
    }

    /**
     * ????????????id ?????????????????????nameCheck??????(nameCheck??????????????????pan?????????????????????)
     *
     * @param userId ??????id
     * @return true or false
     */
    private boolean canRequestNameCheck(long userId) {
        boolean flag = false;
        Map<String, Object> params = new HashMap<>(8);
        params.put("userId", userId);
        params.put("nid", "qtPanAuth");
        List<SceneBusinessLog> list = sceneBusinessLogMapper.listSelective(params);
        if (list != null) {
            for (SceneBusinessLog sceneBusinessLog : list) {
                if (sceneBusinessLog.getRsState().equals("1")) {
                    flag = true;
                    break;
                }
            }
        }
        logger.info("?????? {} ????????????????????????????????????pan???????????????{} ", userId, flag);
        return flag;
    }

    @Override
    public void rcBorrowRuleVerify(Long borrowId) {
        logger.info("?????? {} ?????????????????????", borrowId);
        Borrow borrow = getById(borrowId);
        Long userId = borrow.getUserId();

        // ?????????????????????
        Boolean refinanceFlag = isRefinance(userId, borrowId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("state", 10);
        List<RuleEngine> ruleEngieList = ruleEngineMapper.listSelective(paramMap);
        // ?????????????????????????????????????????????????????????
        if (ruleEngieList == null || ruleEngieList.isEmpty()) {
            return;
        }
        boolean review = false;
        boolean refused = false;
        for (int n = 0; n < ruleEngieList.size(); n++) {
            Long engineId = ruleEngieList.get(n).getId();
            paramMap.clear();
            String userType = borrow.getUserType();
            if ("0".equals(userType)) {
                //0??????
                paramMap.put("adaptedId", "100");
            } else if ("1".equals(userType)) {
                //1??????
                paramMap.put("adaptedId", "100");
            } else if ("2".equals(userType)) {
                //?????? 2
                paramMap.put("adaptedId", "100");
            }
            paramMap.put("engineId", engineId);
            List<RuleEngineConfig> configCollection = ruleEngineConfigMapper.findRuleEnginConfigForBorrow(paramMap);
            BorrowRuleResult result;
            for (int i = 0; i < configCollection.size(); i++) {
                RuleEngineConfig config = configCollection.get(i);
                result = new BorrowRuleResult(config.getId(), borrowId, config.getRuleEnginId(), config.getCtable(),
                        config.getTableComment(), config.getCcolumn(), config.getColumnComment(), config.getFormula(),
                        new Date());
                String tableName = config.getCtable();
                logger.info("?????? {} ????????????????????? , ?????????????????? {} ", borrowId, tableName);
                // ???????????????????????????????????????????????????????????????
                tableName = ShardTableUtil.generateTableNameById(config.getCtable(), borrow.getUserId(), 30000);
                
             // ????????????????????????
                String statement = "select " + config.getCcolumn() + " from " + tableName + " where user_id = "
                        + borrow.getUserId() + " order by id desc limit 1";
                
                if("cl_user".equals(tableName)){
                	statement = "select " + config.getCcolumn() + " from " + tableName + " where id = "
                            + borrow.getUserId() + " order by id desc limit 1";
                }
                
                logger.info("?????? {} ????????????????????? , ???????????????sql {} ", borrowId, statement);
                String value = ruleEngineMapper.findValidValue(statement);
                logger.info("?????? {} ????????????????????? , ????????????sql????????? {} ", borrowId, value);
                result.setValue(config.getCvalue());

                // ???????????????????????????????????????,???????????????????????????????????????
                boolean hasValue = StringUtil.isNotBlank(value);
                String coparResult = hasValue ? comparRule(config, value) : SimpleRule.COMPAR_FAIL;
                logger.info("?????? {} ????????????????????? , ???????????? {} ", borrowId, coparResult);
                result.setResult(coparResult);
                result.setMatching(hasValue ? value : "??????");
                if (!hasValue) {
                    review = true;
                }
                String type = ruleEngieList.get(n).getType();

                // ?????????????????????????????????????????????
                if (RuleEngineConfig.ENGINE_RESULT.equals(type)) {
                    String resultType = hasValue ? config.getResult() : BorrowRuleResult.RESULT_TYPE_REVIEW;
                    result.setResultType(resultType);
                    // ??????????????????
                    borrowRuleResultService.save(result);

                    // ?????????????????????,????????????????????????????????????????????????
                    if (SimpleRule.COMPAR_PASS.equals(coparResult)
                            && BorrowRuleResult.RESULT_TYPE_REFUSED.equals(result.getResultType())) {
                        refused = true;
                        // ??????????????????
                    } else if (SimpleRule.COMPAR_PASS.equals(coparResult)
                            && BorrowRuleResult.RESULT_TYPE_REVIEW.equals(result.getResultType())) {
                        // ????????????
                        review = true;
                    }
                    // ????????????????????????????????????
                } else {
                    throw new BussinessException("????????????????????????");
                }
            }
            // ??????????????????????????????????????????????????????????????????????????????????????? ?????????????????????????????????
            if (n == (ruleEngieList.size() - 1)) {
                if (refused) {
                    handleBorrow(BorrowRuleResult.RESULT_TYPE_REFUSED, borrow, "");
                } else {
                    if (review) {
                        handleBorrow(BorrowRuleResult.RESULT_TYPE_REVIEW, borrow, "");
                    } else {
                        handleBorrow(BorrowRuleResult.RESULT_TYPE_PASS, borrow, "");
                    }
                }
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param config ??????
     * @param value  ???
     * @return String
     */
    private String comparRule(RuleEngineConfig config, String value) {
        SimpleRule simpleRule = RulesExecutorUtil.singleRuleResult(config.getId(), config.getCcolumn(),
                config.getFormula(), config.getCvalue(), value, config.getType(), "");
        return simpleRule.getComparResult();
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     *
     * @param resultType ????????????
     * @param borrow     ????????????
     * @param remark     ????????????????????????
     */
    private void handleBorrow(String resultType, Borrow borrow, String remark) {
        UserBaseInfo userInfo = userBaseInfoService.findByUserId(borrow.getUserId());
        if (BorrowRuleResult.RESULT_TYPE_REFUSED.equals(resultType)) {
            modifyState(borrow.getId(), BorrowModel.STATE_AUTO_REFUSED);
            savePressState(borrow, BorrowModel.STATE_AUTO_REFUSED, "");
        } else if (BorrowRuleResult.RESULT_TYPE_REVIEW.equals(resultType)) {
            modifyState(borrow.getId(), BorrowModel.STATE_NEED_REVIEW);
            savePressState(borrow, BorrowModel.STATE_NEED_REVIEW, "");
        } else if (BorrowRuleResult.RESULT_TYPE_PASS.equals(resultType)) {
            modifyState(borrow.getId(), BorrowModel.STATE_AUTO_PASS);
            savePressState(borrow, BorrowModel.STATE_AUTO_PASS, "");
            //adJustEnventMonitoServiceImpl.adjustEnventMonito(borrow, 3);
            userAppFlyerLogService.appFlyerEnventMonito(borrow, 3);
            //??????app?????????????????????????????????????????????
            String productName = borrow.getProductName();
            String messageType = MessageTypeModel.MESSAGE_TYPE_SMS_GET_MONEY;
            String title2 = Global.getValue("title2");
            if (title2.equals(productName)) {
                messageType = messageType + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
            }
            // ??????????????????????????????????????????
            clSmsServiceImpl.sendGetMoneySms(userInfo.getPhone(), messageType);
        }
    }

    private void getStatisticsServiceData(final Borrow borrow, final String nid) {
        if ("simple_borrow_count".equals(nid)) {
            // ?????????????????????
            AsyncProcessQueue.execute(new Runnable() {
                @Override
                public void run() {
                    int count = simpleBorrowCountService.countOne(borrow.getUserId());
                    syncSceneBusinessLog(borrow.getId(), nid, count);
                }
            });
        } else if ("simple_contact_count".equals(nid)) {
            // ????????????????????????
            AsyncProcessQueue.execute(new Runnable() {
                @Override
                public void run() {
                    int count = simpleContactCountService.countOne(borrow.getUserId());
                    syncSceneBusinessLog(borrow.getId(), nid, count);
                }
            });
        } else if ("simple_voices_count".equals(nid)) {
            // ???????????????????????????
            AsyncProcessQueue.execute(new Runnable() {
                @Override
                public void run() {
                    int count = simpleVoicesCountService.countOne(borrow.getUserId());
                    syncSceneBusinessLog(borrow.getId(), nid, count);
                }
            });
        } else {
            logger.error("????????????" + nid + "?????????????????????");
        }
    }

    @Override
    public void syncSceneBusinessLog(final Long borrowId, String nid, int count) {
        Map<String, Object> params = new HashMap<>(8);
        params.put("borrowId", borrowId);
        params.put("nid", nid);
        logger.info("parm:" + JSON.toJSONString(params));
        SceneBusinessLog log = sceneBusinessLogMapper.findSelective(params);

        if (log != null) {
            String state = "0";
            String desc = "??????";
            if (count > 0) {
                state = "1";
                desc = "??????";
            }
            log.setUpdateTime(new Date());
            log.setRsState(state);
            log.setRsDesc(desc);
            int result = sceneBusinessLogMapper.update(log);
            logger.info("syncSceneBusinessLog???borrowId:" + borrowId + "???nid???" + nid + "???syncSceneBusinessLog???????????????" + result);
            boolean haveNeed = sceneBusinessLogService.haveNeedExcuteService(borrowId);
            logger.info("??????:{}haveNeedExcuteService????????????:{}", nid, haveNeed);
            if (!haveNeed) {
                rcBorrowRuleVerify(borrowId);
            }
        } else {
            logger.error("syncSceneBusinessLog???borrowId???" + borrowId + "???nid???" + nid + "?????????????????????sceneBusinessLog");
        }
    }

    @Override
    public void syncSceneBusinessLog(String state, String desc, final Long borrowId, String nid) {
        Map<String, Object> params = new HashMap<>(8);
        params.put("borrowId", borrowId);
        params.put("nid", nid);
        SceneBusinessLog log = sceneBusinessLogMapper.findSelective(params);

        if (log != null) {
            log.setUpdateTime(new Date());
            log.setRsState(state);
            log.setRsDesc(desc);
            int result = sceneBusinessLogMapper.update(log);
            logger.info("syncSceneBusinessLog???borrowId???" + borrowId + "???nid???" + nid + "???syncSceneBusinessLog????????????result???"
                    + result);
            boolean haveNeed = sceneBusinessLogService.haveNeedExcuteService(borrowId);
            logger.info("??????haveNeedExcuteService????????????:{}", haveNeed);
            if (!haveNeed) {
                rcBorrowRuleVerify(borrowId);
            }
        } else {
            logger.error("syncSceneBusinessLog???borrowId???" + borrowId + "???nid???" + nid + "?????????????????????sceneBusinessLog");
        }
    }

    @Override
    public void verifyBorrowData(long borrowId) {
        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
        List<SceneBusinessLog> sceneLogList = sceneBusinessLogMapper.findSceneLogByBorrowId(borrowId);

        if (borrow != null) {
            boolean getThirdData = isGetThirdData(borrow.getUserId());
            if (getThirdData) {
                if (sceneLogList != null && !sceneLogList.isEmpty()) {
                    for (SceneBusinessLog log : sceneLogList) {
                        if (TppServiceInfoModel.SERVICE_TYPE_STATISTICS.equals(log.getType())) {
                            getStatisticsServiceData(borrow, log.getNid());
                        } else if (TppServiceInfoModel.SERVICE_TYPE_THIRD.equals(log.getType())) {
                            getThirdServiceData(borrow, log.getNid(), log.getTppId());
                        }
                    }
                } else {
                    rcBorrowRuleVerify(borrowId);
                }
            } else {
                rcBorrowRuleVerify(borrowId);
            }
        }
    }

    @Override
    public void reVerifyBorrowData(Long borrowId) throws Exception {
        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
        List<SceneBusinessLog> sceneLogList = sceneBusinessLogMapper.findSceneLogByBorrowId(borrowId);
        if (borrow.getState().equals(BorrowModel.STATE_PRE)) {
            boolean getThirdData = isGetThirdData(borrow.getUserId());
            if (getThirdData) {
                if (sceneLogList != null && !sceneLogList.isEmpty()) {
                    logger.info("????????????????????????borrowId???" + borrow.getId() + "?????????????????????????????????");
                    for (SceneBusinessLog log : sceneLogList) {
                        if (TppServiceInfoModel.SERVICE_TYPE_STATISTICS.equals(log.getType())) {
                            getStatisticsServiceData(borrow, log.getNid());
                        } else if (TppServiceInfoModel.SERVICE_TYPE_THIRD.equals(log.getType())) {
                            getThirdServiceData(borrow, log.getNid(), log.getTppId());
                        }
                    }
                } else {
                    List<BorrowRuleResult> resultList = borrowRuleResultService.findBorrowRuleResult(borrowId);
                    if (resultList == null || resultList.isEmpty()) {
                        logger.info("????????????????????????borrowId???" + borrow.getId() + "????????????????????????");
                        rcBorrowRuleVerify(borrowId);
                    } else if (BorrowModel.STATE_AUTO_PASS.equals(borrow.getState())
                            || BorrowModel.STATE_PASS.equals(borrow.getState())) {

                    }
                }
            } else {
                logger.info("????????????????????????borrowId???" + borrow.getId() + "????????????????????????");
                rcBorrowRuleVerify(borrowId);
            }
        }
    }


    @Override
    public List listBorrow(Map<String, Object> params) {
        List<ManageBorrowModel> list = clBorrowMapper.listBorrowModel(params);
        for (ManageBorrowModel model : list) {
            model.setState(BorrowModel.apiConvertBorrowState(model.getState()));
            UserBaseInfo ubi = userBaseInfoMapper.findByUserId(model.getUserId());
            if (ubi != null) {
                model.setRealName(ubi.getRealName());
                // model.setPhone(ubi.getPhone());
            }
            Map<String, Object> params2 = new HashMap<>();
            params2.put("borrowId", model.getId());
            params2.put("state", BorrowModel.STATE_REPAY);
            BorrowProgress bp = borrowProgressMapper.findSelective(params2);
            if (bp != null) {
                model.setLoanTime(bp.getCreateTime());
            }
            Map<String, Object> params3 = new HashMap<>(8);
            params3.put("borrowId", model.getId());
            BorrowRepay br = borrowRepayMapper.findSelective(params3);
            if (br != null) {
                model.setPenaltyDay(br.getPenaltyDay());
                model.setPenaltyAmout(br.getPenaltyAmout());
            }
            BorrowRepayLog brl = borrowRepayLogMapper.findSelective(params3);
            if (brl != null) {
                model.setRepayAmount(brl.getAmount());
                model.setRepayTime(DateUtil.dateStr2(brl.getRepayTime()));
            }
            UrgeRepayOrder uro = urgeRepayOrderMapper.findSelective(params3);
            if (uro != null) {
                model.setLevel(uro.getLevel());
            }

        }
        return list;
    }

    @Override
    public Page<ManageBorrowModel> listReview(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = clBorrowMapper.listReview(params);
        return (Page<ManageBorrowModel>) list;
    }

    @Override
    public List<Borrow> findUserUnFinishedBorrow(long userId) {
        return clBorrowMapper.findUserUnFinishedBorrow(userId);
    }

    @Override
    public Borrow findLastBorrow(long userId) {
        return clBorrowMapper.findLastBorrow(userId);
    }

    @Override
    public int modifyBorrowState(long borrowId, long userId, String state) {
        logger.info("?????? " + userId + " ?????? " + borrowId + " ????????? borrow???state ??? " + state);
        // 2017???12???1??? 19:58:39 ?????????borrow???state??????????????????????????????????????????
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("borrowId", borrowId);
        paramMap.put("state", "40");
        List<BorrowProgress> listSelective = borrowProgressMapper.listSelective(paramMap);
        if (listSelective != null && listSelective.size() > 0) {
            logger.info("?????? " + userId + " ?????? " + borrowId
                    + " ?????????borrowProsess???state ????????? 40, ????????????borrow???????????????borrowProsess???");
            BorrowProgress bp = new BorrowProgress();
            bp.setUserId(userId);
            bp.setBorrowId(borrowId);
            bp.setState(state);
            bp.setBorrowId(borrowId);
            bp.setRemark("???????????????,?????????");
            bp.setCreateTime(DateUtil.getNow());
            return borrowProgressMapper.save(bp);
        } else {
            logger.info("?????? " + userId + " ?????? " + borrowId + " ??????borrow??????????????????borrowProsess???");
            // ??????????????????
            BorrowProgress bp = new BorrowProgress();
            bp.setUserId(userId);
            bp.setBorrowId(borrowId);
            bp.setState(state);
            bp.setBorrowId(borrowId);
            bp.setRemark("???????????????,?????????");
            bp.setCreateTime(DateUtil.getNow());
            return borrowProgressMapper.save(bp);
        }
    }


    /**
     * ????????????
     */
    @Override
    public int auditBorrowLoan(Long borrowId, String state, String remark, Long userId) {
        int code = 0;
//		Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
//		if (borrow != null) {
//			if (!borrow.getState().equals(BorrowModel.WAIT_AUDIT_LOAN)) {
//				logger.error("????????????,?????????????????????????????????");
//				throw new BussinessException("????????????,?????????????????????????????????");
//			}
//			Map<String, Object> map = new HashMap<>();
//			map.put("id", borrowId);
//			map.put("state", state);
//			map.put("remark", remark);
//			code = clBorrowMapper.loanState(map);
//			logger.info("borrowId:" + borrowId + ",????????????,????????????????????????:" + code);
//			if (code != 1) {
//				throw new BussinessException("??????????????????,?????????????????????????????????");
//			}
//			// ??????????????????
//			savePressState(borrow, state, "");
//
//			if (BorrowModel.AUDIT_LOAN_FAIL.equals(state)) {
//				// ?????????????????????????????????
//				modifyCredit(borrow.getUserId(), borrow.getAmount(), "unuse");
//			}
//			// ?????????????????? ??????
//			if (BorrowModel.AUDIT_LOAN_PASS.equals(state)) {
//				//??????????????????
//				logger.info("borrowId:" + borrowId + ",??????????????????,????????????");
//				newBorrowEsignRecordService.signatureConfirmation(borrow.getUserId(),borrowId, bankAccountId);
//			}
//		} else {
//			logger.error("???????????????????????????????????????");
//			throw new BussinessException("???????????????????????????????????????");
//		}
        return code;
    }

    /**
     * ?????????????????????
     *
     * @param userId   ??????id
     * @param borrowId ??????id
     * @return boolean
     * @author luyu
     * @date 2017-11-16
     */
    private boolean isRefinance(Long userId, Long borrowId) {
        // ???????????????
        Boolean refinanceFlag = false;
        // ??????????????????????????????????????????????????????????????????????????????????????? ??????????????????????????????????????????/??? cl_borrow_repay
        // ??????????????????,????????????????????????
        Map<String, Object> paramMapForRepaymentSuccess = new HashMap<>(8);
        paramMapForRepaymentSuccess.put("userId", userId);
        paramMapForRepaymentSuccess.put("state", 10);
        BorrowRepay borrowRepay = borrowRepayMapper.findLastRepayLog(paramMapForRepaymentSuccess);
        if (borrowRepay != null) {
            // ????????????????????????????????????????????????
            logger.info("???????????? borrowId???" + borrowId + "????????? user_id???" + userId + "------?????????");
            refinanceFlag = true;
        } else {
            // ??????????????????????????????????????????????????????
            logger.info("???????????? borrowId???" + borrowId + "????????? user_id " + userId + "------????????????");
        }
        return refinanceFlag;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param userId ??????id
     * @return boolean
     * @author luyu
     * @date 2017-11-30
     */
    @Override
    public boolean isRefinanceBeforeCreateBorroworder(Long userId) {
        // ???????????????
        Boolean refinanceFlag = false;
        // ??? cl_borrow_repay ??????????????????,????????????????????????
        Map<String, Object> paramMapForRepaymentSuccess = new HashMap<>(8);
        paramMapForRepaymentSuccess.put("userId", userId);
        paramMapForRepaymentSuccess.put("state", 10);
        BorrowRepay borrowRepay = borrowRepayMapper.findLastRepayLog(paramMapForRepaymentSuccess);
        if (borrowRepay != null) {
            // ????????????????????????????????????????????????
            refinanceFlag = true;
        } else {
            // ??????????????????????????????????????????????????????
        }
        return refinanceFlag;
    }

    @Override
    public List<MessageSendModel> selectSendMessageInfoList(ArrayList<String> dateList) {

        return clBorrowMapper.selectSendMessageInfoListNew(dateList);
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????
     *
     * @return boolean
     * @author luyu
     * @date 2017-12-30
     */
    private boolean isGetThirdData(Long userId) {
        boolean flag = false;
        String thirdDataGetSwitch = Global.getValue("third_data_get_switch");
        // 1?????????0?????????
        if ("1".equals(thirdDataGetSwitch)) {
            logger.info("???????????????????????????????????????????????????????????????...");
            // ???????????????????????????????????????????????????
            Map<String, Object> map = new HashMap<>(8);
            map.put("userId", userId);
        } else {
            logger.info("??????????????????????????????????????????????????????????????????...");
            flag = true;
        }
        return flag;
    }



    private HashMap<String, String> getHeader2() {
        HashMap<String, String> header = new HashMap<>(1);
        header.put("Authorization", Global.getValue("pay_stack_secret_key"));
        header.put("Content-Type", "application/json");
        return header;
    }

    /**
     * ???????????????
     *
     * @param borrow ????????????
     * @param date   ??????
     */
    @Override
    public void borrowLoan(final Borrow borrow, final Date date) {
        //??????PayStack??????????????????????????????
        AsyncProcessQueue.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("??????PayStack????????????  " + borrow.getId());
                HashMap<String, String> header2 = getHeader2();
                //???????????????
                HashMap<String, Object> params = new HashMap<>(8);
                UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(borrow.getUserId());
                Long userId = userBaseInfo.getUserId();
                BankAccount bankAccount = bankAccountMapper.findByPrimary(borrow.getAccountId());
                params.put("userId", userId);
                params.put("accountNumber", bankAccount.getAccountNumber());
                params.put("bankCode", bankAccount.getBankCode());

                PayStackRecipient stackRecipient = payStackRecipientMapper.findSelective(params);
                if (stackRecipient == null) {
                    params.clear();
                    params.put("name", bankAccount.getHolderName());
                    params.put("account_number", bankAccount.getAccountNumber());
                    params.put("type", "nuban");
                    params.put("bank_code", bankAccount.getBankCode());

                    String result = HttpClientUtil.sendPost(CREATE_RECIPIENT_URL, header2, JSONObject.toJSONString(params), "utf-8");
                    if (result == null) {
                        logger.info("?????? {} ??????PayStack???????????????-????????????", borrow.getId());
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    logger.info("?????? {} ??????PayStack?????????: " + result, borrow.getId());
                    boolean status = jsonObject.getBoolean("status");
                    String message = jsonObject.getString("message");
                    if (status) {
                        stackRecipient = savePayStackRecipient(userId, jsonObject);
                    } else {
                        logger.info("?????? {} ??????PayStack??????????????? " + message, borrow.getId());
                        return;
                    }
                }
                //?????????????????????????????????
                if (stackRecipient == null || stackRecipient.getRecipientCode() == null) {
                    logger.info("?????? {} ??????PayStack??????????????? ", borrow.getId());
                    return;
                }
                String orderNo = "pot" + System.currentTimeMillis() + "b" + borrow.getId();
                params.clear();
                params.put("source", "balance");
                params.put("currency", "NGN");
                params.put("reference", orderNo);
                params.put("reason", userBaseInfo.getLoanPurpose());
                params.put("amount", new BigDecimal(BigDecimalUtil.mul(borrow.getRealAmount(), 100)).intValue() + "");
//                params.put("amount", 1000);
                params.put("recipient", stackRecipient.getRecipientCode());

                String result = HttpClientUtil.sendPost(PAY_OUT_URL, header2, JSONObject.toJSONString(params), "utf-8");
                // ??????????????????????????????
                PayReqLog payReqLog = new PayReqLog();
                payReqLog.setCreateTime(new Date());
                payReqLog.setReqDetailParams(JsonUtil.toString(params));
                payReqLog.setOrderNo(orderNo);
                payReqLog.setReturnParams(result);
                payReqLog.setReturnTime(new Date());
                payReqLogServiceImpl.save(payReqLog);
                //????????????null???????????????????????????
                if (result == null) {
                    logger.info("?????? {} ??????PayStack??????-????????????", borrow.getId());
                    return;
                }
                JSONObject jsonObject = JSONObject.parseObject(result);
                logger.info("?????? {} ??????PayStack?????????????????? " + result, borrow.getId());
                boolean status = jsonObject.getBoolean("status");
                String message = jsonObject.getString("message");
                if (status) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    PayLog payLog = new PayLog();
                    payLog.setOrderNo(orderNo);
                    payLog.setUserId(borrow.getUserId());
                    payLog.setBorrowId(borrow.getId());
                    payLog.setAmount(borrow.getRealAmount());
                    payLog.setCardNo(bankAccount.getAccountNumber());
                    payLog.setBank(bankAccount.getBankName());
                    payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
                    payLog.setType(PayLogModel.TYPE_PAYMENT);
                    payLog.setScenes(PayLogModel.SCENES_LOANS);
                    String transferCode = data.getString("transfer_code");
                    // ????????????????????????pout_id
                    if (transferCode == null) {
                        payLog.setState(PayLogModel.STATE_PAYMENT_FAILED);
                    } else {
                        payLog.setState(PayLogModel.STATE_PAYMENT_WAIT);
                    }
                    payLog.setPayReqTime(date);
                    payLog.setCreateTime(DateUtil.getNow());
                    payLog.setTradeNo(transferCode);
                    payLogMapper.save(payLog);
                    logger.info("??????PayStack????????????  " + borrow.getId());
                } else {
                    logger.info("?????? {} ??????PayStack????????????-message {}", borrow.getId(), message);
                    //????????????????????????????????????????????????????????????????????????
                    if (Constant.PAYSTACK_SERVER_EORRO_DESCRIPTION.equals(message)) {
                        poutFailedStateChange(borrow);
                        return;
                    }
                }

            }
        });
    }

    private PayStackRecipient savePayStackRecipient(Long userId, JSONObject jsonObject) {
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject details = data.getJSONObject("details");
        PayStackRecipient stackRecipient = JSON.toJavaObject(data, PayStackRecipient.class);
        stackRecipient.setAuthorizationCode(details.getString("authorization_code"));
        stackRecipient.setBankName(details.getString("bank_name"));
        stackRecipient.setAccountNumber(details.getString("account_number"));
        stackRecipient.setAccountName(details.getString("account_name"));
        stackRecipient.setBankCode(details.getString("bank_code"));
        stackRecipient.setBankName(details.getString("bank_name"));
        stackRecipient.setUserId(userId);
        stackRecipient.setCreateTime(new Date());
        stackRecipient.setRecipientId(data.getString("id"));
        stackRecipient.setId(null);
        payStackRecipientMapper.save(stackRecipient);
        return stackRecipient;
    }

    @Override
    public List<MessageSendModel> selectSendMessageInfoList(Date start, Date end) {
        return clBorrowMapper.selectSendMessageInfoList(start, end);
    }

    @Resource
    private EpidemicRegionRiskLevelService epidemicRegionRiskLevelServiceImpl;


    private boolean epidemicRiskControl(Borrow borrow, ClBorrowModel clBorrow, UserBaseInfo userBaseInfo) {
        boolean canBorrow;
        //?????????????????????????????????????????????????????????????????? ??????????????????unkown?????????
        if("unknown".equals(clBorrow.getAddress())&&"unknown".equals(userBaseInfo.getRegisterAddr())){
           return false;
        }
        canBorrow = epidemicRegionRiskLevelServiceImpl.canBorrowByBorrowAddress(borrow.getId(), userBaseInfo.getStateAddr());
        return canBorrow;
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param inputString ???????????????
     * @param length      ????????????
     * @return String
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param inputString ???????????????
     * @param length      ????????????
     * @param size        ??????????????????
     * @return String
     */
    public static List<String> getStrList(String inputString, int length, int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length, (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }


    /**
     * ?????????????????????????????????????????????????????????????????????
     *
     * @param str ???????????????
     * @param f   ????????????
     * @param t   ????????????
     * @return String
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length()) {
            return null;
        }
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }


    private int secondToMinute(int second) {
        int min = second / 60;
        int surplus = second % 60;
        return surplus == 0 ? min : min + 1;
    }

    private boolean nameCompare(String realName, String benName) {
        // ????????????????????????
        realName = realName.replace(" ", "");
        benName = benName.replace(" ", "");
        if (benName.length() > realName.length()) {
            return false;
        }
        realName = realName.substring(0, benName.length());
        return realName.equalsIgnoreCase(benName);
    }


    @Override
    public int findMaxPenaltyDay(Long userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        // ????????????????????????????????????
        List<BorrowRepay> listSelective = borrowRepayMapper.listSelective(paramMap);
        // ?????????????????????
        Integer maxPenaltyDay = 0;
        if (listSelective != null && listSelective.size() > 0) {
            for (BorrowRepay borrowRepay : listSelective) {
                if (borrowRepay.getPenaltyDay() != null && !"".equals(borrowRepay.getPenaltyDay())) {
                    if (maxPenaltyDay < Integer.parseInt(borrowRepay.getPenaltyDay())) {
                        maxPenaltyDay = Integer.parseInt(borrowRepay.getPenaltyDay());
                    }
                }
            }
        }
        return maxPenaltyDay;
    }


    @Override
    public void cancelBorrow(Borrow borrow) {
        logger.info("?????? " + borrow.getId() + " ???????????????????????????");
        //????????????????????????
        UserBaseInfo baseInfo = userBaseInfoService.findByUserId(borrow.getUserId());
        String type = MessageTypeModel.MESSAGE_TYPE_SMS_CANCELLED_DISBURSEMENT;
        String title2 = Global.getValue("title2");
        if (title2.equals(borrow.getProductName())) {
            type = type + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
        }
        clSmsService.sendSms(baseInfo.getPhone(), type);
        borrow.setState("70");
        updateById(borrow);
        savePressState(borrow, "70", "");
    }

    private void poutFailedStateChange(Borrow borrow) {
        logger.info("?????? " + borrow.getId() + " ??????????????????-poutFailedStateChange");
        Map<String, Object> borrowMap = new HashMap<>(8);
        borrowMap.put("id", borrow.getId());
        borrowMap.put("state", BorrowModel.STATE_REPAY_FAIL);
        updatePayState(borrowMap);
    }

    @Override
    public Integer getPayCountByChannel(Long payChannelId) {
        return clBorrowMapper.getPayCountByChannel(payChannelId);
    }

    @Override
    public List<MessageSendModel> selectCancelOrderInfoList() {

        return clBorrowMapper.selectCancelOrderInfoList();
    }


    @Override
    public Page<ManageBorrowModel> listModelForCredit(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ManageBorrowModel> list = clBorrowMapper.listModelForCredit(params);
        return (Page<ManageBorrowModel>) list;
    }


    @Override
    public BorrowConfirmModel findBorrowConfirm(Long borrowId) {
        BorrowConfirmModel borrowConfirm = clBorrowMapper.findBorrowConfirm(borrowId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", borrowConfirm.getUserId());
        List<BankCard> bankCards = bankCardMapper.listSelective(hashMap);
        //????????????????????????????????????????????????
        if (bankCards.size() > 0 || ("10".equals(borrowConfirm.getSignServiceId()))) {
            borrowConfirm.setNeedCardBing(false);
            return borrowConfirm;
        }
        hashMap.put("borrowId", borrowId);
        List<BankCardLog> bankCardLogs = bankCardLogMapper.listSelective(hashMap);
        //?????????
        borrowConfirm.setFailCount(0);
        borrowConfirm.setNeedCardBing(true);
        int processingCount = 0;
        int failedCount = 0;
        for (BankCardLog bankCardLog : bankCardLogs) {
            if ( bankCardLog.getStatus().equals("PENDING")) {
                processingCount++;
            }
            if (bankCardLog.getStatus().equals("ONGOING")||bankCardLog.getStatus().equals("FAILED")) {
                failedCount++;
            }
        }
        //????????????????????????-1
        if (processingCount > 0) {
            borrowConfirm.setFailCount(-1);
            return borrowConfirm;
        }
        //????????????
        if (failedCount == 1) {
            borrowConfirm.setFailCount(1);
        } else if (failedCount > 1) {       //??????2?????????
            borrowConfirm.setFailCount(2);
        }
        return borrowConfirm;
    }

    @Override
    public void changeAmountHalf(Long borrowId) {
        Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
        if (BorrowModel.STATE_PASS.equals(borrow.getState()) || BorrowModel.STATE_AUTO_PASS.equals(borrow.getState())) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("userId", borrow.getUserId());
            List<BankCard> bankCards = bankCardMapper.listSelective(hashMap);
            //????????????????????????????????????????????????
            if (bankCards.size() > 0 || ("10".equals(borrow.getSignServiceId()))) {
                logger.info("?????? {} ???????????????????????????.", borrowId);
                return;
            }
            hashMap.put("borrowId", borrowId);
            List<BankCardLog> bankCardLogs = bankCardLogMapper.listSelective(hashMap);
            int processingCount = 0;
            int failedCount = 0;
            for (BankCardLog bankCardLog : bankCardLogs) {
                if (bankCardLog.getStatus().equals("ONGOING") ||bankCardLog.getStatus().equals("FAILED")) {
                    failedCount++;
                }
            }
            //????????????????????????????????????
            if (failedCount >= 2) {
                //????????????????????????
                try {
                    BorrowFlag borrowFlag = new BorrowFlag();
                    borrowFlag.setBorrowId(borrow.getId());
                    borrowFlag.setCreateTime(new Date());
                    borrowFlag.setState("10");
                    int save = borrowFlagMapper.save(borrowFlag);
                    logger.info("borrowId:" + borrow.getId() + ",???????????????????????????,??????:" + save);
                } catch (Exception E) {
                    logger.info("?????? {} ??????????????????????????? -?????????.", borrowId);
                    return;
                }
                borrow.setAmount(BigDecimalUtil.mul(borrow.getAmount(), 0.8));
                borrow.setInterest(BigDecimalUtil.mul(borrow.getInterest(), 0.8));
                borrow.setFee(BigDecimalUtil.mul(borrow.getFee(), 0.8));
                borrow.setRealAmount(BigDecimalUtil.mul(borrow.getRealAmount(), 0.8));
                borrow.setSignServiceId("10");
                clBorrowMapper.update(borrow);
                logger.info("borrowId:" + borrow.getId() + "??????????????????");
            }
        } else {
            logger.info("?????? {} ????????????????????????????????????-????????????.", borrowId);
        }
    }
}
