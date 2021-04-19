package com.ajaya.cashloan.cl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.domain.Sms;
import com.ajaya.cashloan.cl.model.MessageTypeModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.model.SmsModel;
import com.ajaya.cashloan.cl.service.BorrowRepayFqService;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.domain.SmsTpl;
import com.ajaya.cashloan.cl.mapper.SmsMapper;
import com.ajaya.cashloan.cl.mapper.SmsTplMapper;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.util.sms.TalkingSmsUtils;
import com.ajaya.cashloan.core.domain.User;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.mapper.UserMapper;
import sun.util.resources.ga.LocaleNames_ga;


@Service("clSmsService")
public class ClSmsServiceImpl extends BaseServiceImpl<Sms, Long> implements ClSmsService {

    public static final Logger logger = LoggerFactory.getLogger(ClSmsServiceImpl.class);
    @Resource
    private BorrowRepayFqService borrowRepayFbService;
    @Resource
    private SmsMapper smsMapper;
    @Resource
    private SmsTplMapper smsTplMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Override
    public BaseMapper<Sms, Long> getMapper() {
        return smsMapper;
    }

    @Override
    public long findTimeDifference(String phone, String type) {
        int countdown = Global.getInt("sms_countdown");
        Map<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("smsType", type);
        Sms sms = smsMapper.findTimeMsg(data);
        long times = 0;
        if (sms != null) {
            Date d1 = sms.getSendTime();
            Date d2 = DateUtil.getNow();
            long diff = d2.getTime() - d1.getTime();
            if (diff < countdown * 1000) {
                times = countdown - (diff / 1000);
            } else {
                times = 0;
            }
        }
        return times;
    }

    @Override
    public int countDayTime(String phone, String type) {
        String mostTimes = Global.getValue("sms_day_most_times");
        int mostTime = JSONObject.parseObject(mostTimes).getIntValue(type);

        Map<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("smsType", type);
        int times = smsMapper.countInTime(data);
        return mostTime - times;
    }


    @Override
    public String sendSms(String phone, String type) {
        logger.info("准备发送短信，phone：" + phone + "， type：" + type);
        Map<String, Object> search = new HashMap<>();
        search.put("type", type);
        search.put("state", "10");
        SmsTpl tpl = smsTplMapper.findSelective(search);
        if (tpl != null) {
            Map<String, Object> payload = new HashMap<>();
            int vcode = (int) (Math.random() * 9000) + 1000;
            payload.put("mobile", phone);
            payload.put("message", change(type).replace("xxxxxx", String.valueOf(vcode)));
            payload.put("code", vcode + "");
            String result = sendCode(payload, tpl.getNumber(), type);
            logger.debug("发送短信，phone：" + phone + "， type：" + type + "，同步响应结果：" + result);
            return result(result, phone, type, vcode,(String) payload.get("message"));
        }
        logger.error("发送短信，phone：" + phone + "， type：" + type + "，没有获取到smsTpl");
        return null;
    }

    @Override
    public String sendGetMoneySms(String phone, String type) {
        logger.info("准备发送短信，phone：" + phone + "， type：" + type);
        Map<String, Object> search = new HashMap<>();
        search.put("type", type);
        search.put("state", "10");
        SmsTpl tpl = smsTplMapper.findSelective(search);
        if (tpl != null) {
            Map<String, Object> payload = new HashMap<>();
            int vcode = (int) (Math.random() * 9000) + 1000;
            payload.put("mobile", phone);
            payload.put("message", tpl.getTpl());
            payload.put("code", vcode + "");
            String result = sendCode(payload, tpl.getNumber(), type);
            logger.debug("发送短信，phone：" + phone + "， type：" + type + "，同步响应结果：" + result);
            return result(result, phone, type, vcode, (String) payload.get("message"));
        }
        logger.error("发送短信，phone：" + phone + "， type：" + type + "，没有获取到smsTpl");
        return null;
    }
    
    
    @Override
	public String sendAdvanceSms(String phone, String type, BorrowRepay borrowRepay) {
        Date repayTime = borrowRepay.getRepayTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String repayDate = format.format(repayTime);

        logger.info("准备发送短信，phone：" + phone + "， type：" + type);
        Map<String, Object> search = new HashMap<>();
        search.put("type", type);
        search.put("state", "10");
        SmsTpl tpl = smsTplMapper.findSelective(search);
        Double currentNeedRepayMoney = borrowRepayFbService.getCurrentNeedReapyMoney(borrowRepay.getBorrowId());
        if (tpl != null) {
            String tplStr = tpl.getTpl();
            tplStr = tplStr.replace("{#day#}",repayDate).replace("{#amount#}",currentNeedRepayMoney+"");
            Map<String, Object> payload = new HashMap<>();
            payload.put("mobile", phone);
            payload.put("message", tplStr);
            String result = sendCode(payload, tpl.getNumber(), type);
            logger.debug("发送短信，phone：" + phone + "， type：" + type + "，同步响应结果：" + result);
            return result(result, phone, type, 0, (String) payload.get("message"));
        }
        logger.error("发送短信，phone：" + phone + "， type：" + type + "，没有获取到smsTpl");
        return null;
	}


    private String  getVcode() {
        int vcode = (int) (Math.random() * 9000) + 1000;
        String string =Integer.toString(vcode);
        StringBuffer stringBuffer = new StringBuffer();
        int length = string.length();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(string.substring(0,1)).append("  ");
            string=string.substring(1);
        }
        return stringBuffer.toString().trim();
    }

    @Override
    public int smsBatch(String id, String type) {
        return 1;
    }

    @Override
    public int verifySms(String phone, String type, String code) {
        logger.info("-------------------------"+Global.getValue("app_environment"));
        String ss=Global.getValue("app_environment");
        if ("dev".equals(Global.getValue("app_environment")) && "0000".equals(code)) {
            return 1;
        }

        if (StringUtil.isBlank(phone) || StringUtil.isBlank(type) || StringUtil.isBlank(code)) {
            return 0;
        }

        if (!StringUtil.isPhone(phone)) {
            return 0;
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("phone", phone);
        data.put("smsType", type);
        Sms sms = smsMapper.findTimeMsg(data);
        if (sms != null) {
            String mostTimes = Global.getValue("sms_day_most_times");
            int mostTime = JSONObject.parseObject(mostTimes).getIntValue("verifyTime");

            data = new HashMap<>();
            data.put("verifyTime", sms.getVerifyTime() + 1);
            data.put("id", sms.getId());
            smsMapper.updateSelective(data);

            if (StringUtil.equals("20", sms.getState()) || sms.getVerifyTime() > mostTime) {
                return 0;
            }

            long timeLimit = Long.parseLong(Global.getValue("sms_time_limit"));

            Date d1 = sms.getSendTime();
            Date d2 = DateUtil.getNow();
            long diff = d2.getTime() - d1.getTime();
            if (diff > timeLimit * 60 * 1000) {
                return -1;
            }
            if (sms.getCode().equals(code)) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", sms.getId());
                map.put("state", "20");
                smsMapper.updateSelective(map);
                return 1;
            }
        }
        return 0;
    }
    public String change(String code) {
        String message = null;
        if (MessageTypeModel.MESSAGE_TYPE_REGISTER.equals(code)) {
            message = ret(MessageTypeModel.MESSAGE_TYPE_REGISTER);
        } else if (MessageTypeModel.MESSAGE_TYPE_FINDREG.equals(code)) {
            message = ret(MessageTypeModel.MESSAGE_TYPE_FINDREG);
            //新签章
        } else if (MessageTypeModel.MESSAGE_TYPE_ESIGN.equals(code)) {
            message = ret(MessageTypeModel.MESSAGE_TYPE_ESIGN);
        } else if (MessageTypeModel.MESSAGE_TYPE_SMS_CANCELLED_DISBURSEMENT.equals(code)) {
            message = ret(MessageTypeModel.MESSAGE_TYPE_SMS_CANCELLED_DISBURSEMENT);
        } else if ((MessageTypeModel.MESSAGE_TYPE_REGISTER + MessageTypeModel.MESSAGE_TYPE_SUFFIX).equals(code)) {
            message = ret((MessageTypeModel.MESSAGE_TYPE_REGISTER + MessageTypeModel.MESSAGE_TYPE_SUFFIX));
        } else if ((MessageTypeModel.MESSAGE_TYPE_FINDREG + MessageTypeModel.MESSAGE_TYPE_SUFFIX).equals(code)) {
            message = ret((MessageTypeModel.MESSAGE_TYPE_FINDREG + MessageTypeModel.MESSAGE_TYPE_SUFFIX));
        } else if ((MessageTypeModel.MESSAGE_TYPE_SMS_CANCELLED_DISBURSEMENT + MessageTypeModel.MESSAGE_TYPE_SUFFIX).equals(code)) {
            message = ret((MessageTypeModel.MESSAGE_TYPE_SMS_CANCELLED_DISBURSEMENT + MessageTypeModel.MESSAGE_TYPE_SUFFIX));
        } else {
            message = ret(code);
        }
        return message;
    }

    public String ret(String type) {
        Map<String, Object> search = new HashMap<>();
        search.put("type", type);
        SmsTpl tpl = smsTplMapper.findSelective(search);
        return tpl.getTpl();
    }

    private String result(String result, String phone, String type, int vcode, String message) {
        String msg = "";
        JSONObject resultJson = JSONObject.parseObject(result);
        String sms_passageway = Global.getValue("sms_passageway");
        Integer code = null;
        if (StringUtil.isNotBlank(resultJson)) {
            Date now = DateUtil.getNow();
            Sms sms = new Sms();
            sms.setPhone(phone);
            sms.setSendTime(now);
            sms.setRespTime(now);
            sms.setSmsType(type);
            sms.setVerifyTime(0);
            //天一泓国际短信 10
            if (Constant.SMS_TALKING_FLAG.equals(sms_passageway)) {
          //100: Processed  101: Sent  102: Queued   401: RiskHold  402: InvalidSenderId  403: InvalidPhoneNumbe
                // 404: UnsupportedNumberType  405: InsufficientBalance  406: UserInBlacklist  407: CouldNotRoute   500: InternalServerError  501: GatewayError  502: RejectedByGateway
                JSONObject smsMessageData = resultJson.getJSONObject("SMSMessageData");
                JSONArray recipients = smsMessageData.getJSONArray("Recipients");
                JSONObject recipient = recipients.getJSONObject(0);
                Integer statusCode = recipient.getInteger("statusCode");
                //短信成功条数
                if (statusCode == 101) {
                    //h获取二维数组的第一个元素中的第二个元素
                    String orderNo = recipient.getString("messageId");
                    sms.setResp(result);
                    sms.setCode(String.valueOf(vcode));
                    sms.setOrderNo(orderNo);
                    sms.setContent(message);
                    sms.setState("30");
                    int ms = smsMapper.save(sms);
                    if (ms > 0) {
                        msg = orderNo;
                    }
                } else {
                    sms.setContent(result);
                    sms.setResp("短信发送失败");
                    sms.setCode("");
                    sms.setOrderNo("");
                    sms.setState("20");
                    smsMapper.save(sms);
                }
            }
            logger.info("发送短信，phone：" + phone + "， type：" + type + "，保存sms时code：" + code);
        }
        return msg;
    }

    /**
     * 判断短信发送渠道
     * <p>
     * register, 		注册验证码
     * overdue, 		逾期催收
     * loanInform, 		放款通知
     * repayInform, 	还款通知
     * repayBefore,
     * offlineAlipay,
     * offlineBank
     * findReg			找回登陆密码
     */
    private String sendCode(Map<String, Object> payload, String smsNo, String type) {
        String sms_passageway = Global.getValue("sms_passageway");
        if (StringUtil.isBlank(sms_passageway)) {
            sms_passageway = "10";
        }
        if (Constant.SMS_TALKING_FLAG.equals(sms_passageway)) {
            String mobile = (String) payload.get("mobile");
            String content = (String) payload.get("message");
            return TalkingSmsUtils.sendMessage(mobile, content);
        }
        return "";
    }


    @Override
    public int findUser(String phone) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loginName", phone);
        User user = userMapper.findSelective(paramMap);
        if (StringUtil.isNotBlank(user)) {
            return 1;
        }
        return 0;
    }


    @Override
    public String overdue(String phone, String dueDay, String productName, Long borrowId, Double penaltyAmount) {
        String title2 = Global.getValue("title2");
        String type = MessageTypeModel.MESSAGE_TYPE_SMS_DUE_NOTICE;
        String typeMore = MessageTypeModel.MESSAGE_TYPE_SMS_DUE_NOTICE_MORE;
        if (title2.equals(productName)){
            type=type+ MessageTypeModel.MESSAGE_TYPE_SUFFIX;
            typeMore=typeMore+MessageTypeModel.MESSAGE_TYPE_SUFFIX;
        }
        Map<String, Object> search = new HashMap<>();
        search.put("type", type);
        if(!"1".equals(dueDay)){
            search.put("type", typeMore);
            type = typeMore;
        }
        logger.info("准备发送短信，phone：" + phone + "， type：" + type );
        search.put("state", "10");
        SmsTpl tpl = smsTplMapper.findSelective(search);

        Double currentNeedRepayMoney = borrowRepayFbService.getCurrentNeedReapyMoney(borrowId);
        if (currentNeedRepayMoney<penaltyAmount){
            penaltyAmount=currentNeedRepayMoney;
        }



        if (tpl != null) {
            Map<String, Object> payload = new HashMap<>();
            String tplStr = tpl.getTpl().replace("{#day#}", dueDay).
                    replace("{#amount#}",currentNeedRepayMoney+"").replace("{#penaltyAmount#}",penaltyAmount+"");
            payload.put("mobile", phone);
            payload.put("message", tplStr);
            String result = sendCode(payload, tpl.getNumber(), type);
            logger.debug("发送短信，phone：" + phone + "， type：" + type + "，同步响应结果：" + result);
            return result(result, phone, type, 0, (String) payload.get("message"));
        }
        logger.error("发送短信，phone：" + phone + "， type：" + type + "，没有获取到smsTpl");
        return null;
    }


    @Override
    public Page<Sms> list(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);	
        List<Sms> listSign ;
        if(params == null || params.size() == 0 || !params.containsKey("phone")){
        	params = new HashMap<>();
        	params.put("phone", "00000");
        	listSign = smsMapper.listSelectiveEsign(params);
        } else {
        	listSign = smsMapper.listSelectiveEsign(params);
        	if(listSign != null && listSign.size() > 0){
        		for (Sms sms : listSign) {
        			sms.setState("10");
				}
        	}
        }
        return (Page<Sms>) listSign;
    }

    @Override
    public boolean checkIsMotherFucker(String phone, String type) {
        String mostTimes = Global.getValue("most_regmessage_sendtimes");
        int mosttimes = Integer.parseInt(mostTimes);
        Map<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("smsType", type);
        int times = smsMapper.countAllTime(data);
        return times >= mosttimes;
    }

    @Override
    public String repaymentAdvanceCustomer(String phone, String productName) {
        String title2 = Global.getValue("title2");
        String type = MessageTypeModel.MESSAGE_TYPE_SMS_ADVANCE_CUSTOMER_REPAYMENT;
        if (title2.equals(productName)){
            type=type+ MessageTypeModel.MESSAGE_TYPE_SUFFIX;
        }
        Map<String, Object> search = new HashMap<>();
        search.put("type", type);
        logger.info("准备发送短信，phone：" + phone + "， type：" + type );
        search.put("state", "10");
        SmsTpl tpl = smsTplMapper.findSelective(search);
        if (tpl != null) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("message", tpl.getTpl());
            payload.put("mobile", phone);
            String result = sendCode(payload, tpl.getNumber(), type);
            logger.debug("发送短信，phone：" + phone + "， type：" + type + "，同步响应结果：" + result);
            return result(result, phone, type, 0, (String) payload.get("message"));
        }
        logger.error("发送短信，phone：" + phone + "， type：" + type + "，没有获取到smsTpl");
        return null;
    }

    @Override
    public void sendAppDisableNotice(String userId) {
        //如果没有登录则不进行短信通知
        if ("".equals(userId)) {
            return;
        }
        UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(Long.parseLong(userId));
        String phone = userBaseInfo.getPhone();
        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("smsType", "sms_app_disable_notice");
        SmsModel smsModel = smsMapper.findCountAndLastTime(params);
        logger.info("userId:" + userId + ",查询拦截短信记录:" + smsModel);
        if (smsModel == null||smsModel.getLastTime()==null) {
            sendSms(phone, "sms_app_disable_notice");
        } else {
            Integer count = smsModel.getCount();
            logger.info("userId:" + userId + ",查询拦截短信发送次数:" + count);
            //发送超过三次则不发送
            if (count >= 3) {
                return;
            }
            //当天发送过则不发送
            Date lastTime = smsModel.getLastTime();
            Date integralTime = DateUtil.getIntegralTime();
                logger.info("userId:" + userId + ",查询拦截短信lastTime:" + lastTime + ", integralTime:" + integralTime);
            if (lastTime == null || lastTime.before(integralTime)) {
                sendSms(phone, "sms_app_disable_notice");
            }
        }
    }

    @Override
    public ResponseWrapper makeVoiceCall(String phone,String type) {
        String result = TalkingSmsUtils.sendVoiceMessage(phone);
        if (result==null ){
            return ResponseWrapper.error("System abnormal, please try again later.");
        }
        String msg;
        JSONObject resJson = JSONObject.parseObject(result);
        JSONArray entries = resJson.getJSONArray("entries");
        JSONObject entry = entries.getJSONObject(0);
        String status = entry.getString("status");
        String sessionId = entry.getString("sessionId");
        if ("Queued".equals(status)){
            String tpl = ret(type);
            String vcode = getVcode();
            String  content= tpl.replace("xxxxxx", vcode);
            logger.info("电话:" + phone + "发送验证短信信息:" +content);
            Sms sms = new Sms();
            Date now = DateUtil.getNow();
            sms.setPhone(phone);
            sms.setSendTime(now);
            sms.setRespTime(now);
            sms.setContent(content);
            sms.setSmsType(type);
            sms.setCode(vcode.replace(" ",""));
            sms.setVerifyTime(0);
            sms.setResp(result);
            sms.setOrderNo(sessionId);
            sms.setState("30");
            smsMapper.save(sms);
            return ResponseWrapper.success("Our customer service will call you.");
        }else if ("InvalidPhoneNumber".equals(status)){
            msg ="Recipient number is in an incorrect format.";
        }else if ("DestinationNotSupported".equals(status)){
            msg ="Recipient number is outside the supported zone.";
        }else if ("InsufficientCredit".equals(status)){
            msg ="Your AfricasTalking account has insufficient balance.";
            logger.info("手机号 {} 进行语音拨号时返回错误 {} ",phone , msg);
            msg ="System abnormal, please try again later.";
        }else {
            msg ="System abnormal, please try again later.";
        }
        logger.info("手机号 {} 进行语音拨号时返回错误 {} ",phone , msg);
        return ResponseWrapper.error(msg);
    }

    @Override
    public String dealVoiceCall(JSONObject params) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String sessionId = params.getString("sessionId");
        hashMap.put("orderNo",sessionId);
        Sms sms = smsMapper.findSelective(hashMap);
        return sms.getContent();
    }
}
