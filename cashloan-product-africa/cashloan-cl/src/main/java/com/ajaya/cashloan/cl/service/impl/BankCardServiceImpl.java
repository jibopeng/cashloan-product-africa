package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BankCard;
import com.ajaya.cashloan.cl.domain.BankCardLog;
import com.ajaya.cashloan.cl.domain.BorrowFlag;
import com.ajaya.cashloan.cl.enums.BankCardTradeEnum;
import com.ajaya.cashloan.cl.mapper.BankCardLogMapper;
import com.ajaya.cashloan.cl.mapper.BankCardMapper;
import com.ajaya.cashloan.cl.mapper.BorrowFlagMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.model.BankCardAccessModel;
import com.ajaya.cashloan.cl.model.BankCardModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.BankCardService;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.HttpClientUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.BigDecimalUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 银行卡记录表ServiceImpl
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:43:16
 */

@Service("bankCardService")
public class BankCardServiceImpl extends BaseServiceImpl<BankCard, Long> implements BankCardService {

    private static final Logger logger = LoggerFactory.getLogger(BankCardServiceImpl.class);
    private static final String GET_ACCESS_CODE_URL = "https://api.paystack.co/transaction/initialize";
    private static final String GET_TRADE_CHECK_URL = "https://api.paystack.co/transaction/verify/";
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private BankCardMapper bankCardMapper;
    @Resource
    private BankCardLogMapper bankCardLogMapper;
    @Resource
    private ClSmsService clSmsServiceImpl;

    @Override
    public BaseMapper<BankCard, Long> getMapper() {
        return bankCardMapper;
    }

    @Override
    public ResponseWrapper getAccessCode(Long userId, Long borrowId, String type) {
        UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
        if (userBaseInfo == null) {
            return ResponseWrapper.error("User information was not found");
        }
        String email = userBaseInfo.getEmail();
        String reference = "car" + System.currentTimeMillis() + "u" + userId;
        HashMap<String, String> header = getHeader();
        HashMap<String, String> param = new HashMap<>(2);
        param.put("amount", Long.toString(2500));
        param.put("email", email);
        param.put("reference", reference);
        String result = HttpClientUtil.sendPost(GET_ACCESS_CODE_URL, header, JSONObject.toJSONString(param), "utf-8");
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            String message = resultObj.getString("message");
            Boolean status = resultObj.getBoolean("status");

            if (status) {
                JSONObject data = resultObj.getJSONObject("data");
                BankCardAccessModel accountModel = JSONObject.toJavaObject(data, BankCardAccessModel.class);
                accountModel.setAmount(2500d);
                accountModel.setEmail(email);
                //保存日志
                saveLog(userId, borrowId, data, type);
                return ResponseWrapper.success(accountModel);
            } else {
                logger.info("用户 {} 调用绑卡获取授权码接口，返回 {}", userId, message);
                return ResponseWrapper.error(message);
            }
        } else {
            logger.info("用户 {} 调用绑卡获取授权码接口，返回空", userId);
            return ResponseWrapper.error("System abnormal, please try again later.");
        }
    }

    @Override
    public ResponseWrapper tradingCheck(Long userId, String reference) {
        HashMap<String, Object> param = new HashMap<>(2);
        param.put("userId", userId);
        param.put("reference", reference);
        BankCardLog cardLog = bankCardLogMapper.findSelective(param);
        if (cardLog == null) {
            return ResponseWrapper.error("Transaction records were not found.");
        }
        HashMap<String, String> header = getHeader2();
        String url = GET_TRADE_CHECK_URL + reference;
        String result = HttpClientUtil.sentGet(url, header, null, "utf-8");
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            String message = resultObj.getString("message");
            Boolean status = resultObj.getBoolean("status");
            if (status) {
                JSONObject data = resultObj.getJSONObject("data");
                String tradeStatus = data.getString("status");
                JSONObject authorization = data.getJSONObject("authorization");
                JSONObject customer = data.getJSONObject("customer");
                if ("success".equals(tradeStatus)) {
                    //保存银行卡
                    bankCardBinding(authorization, customer, userId);
                }
                //保存日志
                updateLog(cardLog, tradeStatus, data,1);
                HashMap<String, Object> hashMap = new HashMap<>();
                Integer valueByCode = BankCardTradeEnum.getValueByCode(tradeStatus.toUpperCase());
                if (valueByCode==2){
                    hashMap.put("tradeStatus",1);
                }else if (valueByCode==4||valueByCode==1||valueByCode==3){
                    hashMap.put("tradeStatus",2);

                }else if(valueByCode==5) {
                    hashMap.put("tradeStatus",3);
                }
                return ResponseWrapper.success(hashMap);
            } else {
                logger.info("用户 {} 调用绑卡查询交易状态接口，返回 {}", userId, message);
                return ResponseWrapper.error(message);
            }
        } else {
            logger.info("用户 {} 调用绑卡查询交易状态接口，返回空", userId);
            return ResponseWrapper.error("System abnormal, please try again later.");
        }
    }


    @Override
    public void dealCallBack(JSONObject data) {
        String reference = data.getString("reference");
        HashMap<String, Object> param = new HashMap<>(2);
        param.put("reference", reference);
        BankCardLog cardLog = bankCardLogMapper.findSelective(param);
        if (cardLog == null) {
            logger.info("未查到响应绑卡记录，-{}",reference);
            return;
        }
        String tradeStatus = data.getString("status");
        JSONObject authorization = data.getJSONObject("authorization");
        JSONObject customer = data.getJSONObject("customer");
        //保存认证记录
        if ("success".equals(tradeStatus)) {
            //保存银行卡
            bankCardBinding(authorization, customer, cardLog.getUserId());
            //绑卡成功发送短信
            try {
                UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(cardLog.getUserId());
                clSmsServiceImpl.sendSms(userBaseInfo.getPhone(),"sms_bank_card_bing");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        //保存日志
        updateLog(cardLog, tradeStatus, data,2);
     }

    @Override
    public List<BankCardModel> getBankCardList(Long userId) {
       return bankCardMapper.getBankCardListByUserId(userId);

    }

    @Override
    public void setDefault(Long userId, Long cardId) {
        bankCardMapper.setNoBinding(userId);
        BankCard bankCard = bankCardMapper.findByPrimary(cardId);
        bankCard.setStatus("1");
        bankCard.setUpdateTime(new Date());
        bankCardMapper.update(bankCard);
    }

    private void updateLog(BankCardLog cardLog, String tradeStatus, JSONObject data,int type) {
        tradeStatus= tradeStatus.toUpperCase();
        Boolean  flag =BankCardTradeEnum.getValueByCode(cardLog.getStatus())< (BankCardTradeEnum.getValueByCode(tradeStatus));
        if (flag) {
            cardLog.setStatus(tradeStatus);
        }
        if (type==1){
            cardLog.setData(JSONObject.toJSONString(data));
        }else {
            cardLog.setWebhookData(JSONObject.toJSONString(data));
        }
        cardLog.setUpdateTime(new Date());
        bankCardLogMapper.update(cardLog);
    }


    private void bankCardBinding(JSONObject authorization, JSONObject customer, Long userId) {
        BankCard bankCard = JSONObject.toJavaObject(authorization, BankCard.class);
        //查询是否已经保存过了
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("bin", bankCard.getBin());
        param.put("last4", bankCard.getLast4());
        BankCard bankCard2 = bankCardMapper.findSelective(param);
        if (bankCard2 == null) {
            //将该用户所有卡重置为未绑定状态
            HashMap<String, Object> paramMap = new HashMap<>(8);
            paramMap.put("userId", userId);
            bankCardMapper.setNoBinding(userId);
            //将其他的银行卡状态设为不绑定状态
            bankCard.setCustomerId(customer.getString("id"));
            bankCard.setEmail(customer.getString("email"));
            bankCard.setCustomerCode(customer.getString("customer_code"));
            bankCard.setRiskAction(customer.getString("risk_action"));
            bankCard.setCreateTime(new Date());
            bankCard.setStatus("1");
            bankCard.setUserId(userId);
            bankCardMapper.save(bankCard);
            logger.info("用户 {} 邦卡成功", userId);
        }else {
            logger.info("用户 {} 已经绑过卡了", userId);
        }
    }

    private void saveLog(Long userId, Long borrowId, JSONObject data, String type) {
        BankCardLog bankCardLog = JSONObject.toJavaObject(data, BankCardLog.class);
        bankCardLog.setCreateTime(new Date());
        bankCardLog.setUserId(userId);
        bankCardLog.setType(type);
        bankCardLog.setStatus("CREATE");
        bankCardLog.setBorrowId(borrowId);
        bankCardLogMapper.save(bankCardLog);
    }

    private HashMap<String, String> getHeader() {
        HashMap<String, String> header = new HashMap<>(1);
        header.put("Authorization", Global.getValue("pay_stack_secret_key"));
        header.put("Content-Type", "application/json");
        return header;
    }

    private HashMap<String, String> getHeader2() {
        HashMap<String, String> header = new HashMap<>(1);
        header.put("Authorization", Global.getValue("pay_stack_secret_key"));
        return header;
    }
}