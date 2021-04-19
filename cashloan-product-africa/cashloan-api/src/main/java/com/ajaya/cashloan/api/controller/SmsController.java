package com.ajaya.cashloan.api.controller;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ajaya.cashloan.cl.domain.UserVoiceSmsLog;
import com.ajaya.cashloan.cl.domain.entity.Response;
import com.ajaya.cashloan.cl.model.MessageTypeModel;
import com.ajaya.cashloan.cl.model.PayLogModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.UserVoiceSmsLogService;
import com.ajaya.cashloan.core.common.util.ReqToJsonUtil;
import com.ajaya.cashloan.core.common.util.XStreamUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.common.XmlStreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.model.BorrowRepayModel;
import com.ajaya.cashloan.cl.service.BorrowRepayService;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.core.service.CloanUserService;


/**
 * 短信记录Controller
 *
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 */
@Scope("prototype")
@Controller
public class SmsController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Resource
    private ClSmsService clSmsService;
    @Resource
    private CloanUserService cloanUserService;
    @Resource
    private UserVoiceSmsLogService userVoiceSmsLogServiceImpl;

    /**
     * 探测短信验证码是否可获取
     *
     * @param phone 手机号
     * @param type  短信类型
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/user/probeSms.htm")
    public void probeSms(@RequestParam(value = "phone") String phone, @RequestParam(value = "type") String type) throws Exception {
        Map<String, Object> result = new HashMap<>(8);
        Map<String, Object> data = new HashMap<>(8);
        if (StringUtil.isBlank(phone) || StringUtil.isBlank(type)) {
            //参数不能为空
            data.put("message", "Parameters cannot be null.");
            result.put(Constant.RESPONSE_DATA, data);
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "Detection failed.");
        } else if (!StringUtil.isPhone(phone)) {
            //手机号码格式有误
            data.put("message", "The format of mobile number is incorrect.");
            result.put(Constant.RESPONSE_DATA, data);
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "Detection failed.");
        } else {
            long countDown = clSmsService.findTimeDifference(phone, type);
            data.put("countDown", countDown);
            if (countDown == 0) {
                data.put("state", 10);
            } else {
                data.put("state", 20);
            }
            result.put(Constant.RESPONSE_DATA, data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "The detection was successful.");
        }
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 获取短信验证码
     *
     * @param phone 手机号
     * @param type  短信类型
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/user/sendSms.htm")
    public void sendSms(@RequestParam(value = "phone") String phone,
                        @RequestParam(value = "type") String type,
                        @RequestParam(value = "appFlag", required = false) String appFlag) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(8);
        Map<String, Object> data = new HashMap<>(8);

        //如果发送注册和找回密码则进行类型修改（新app前提下）
        String secondProductName = Global.getValue("title2");
        boolean flag = secondProductName.equals(appFlag) && (MessageTypeModel.MESSAGE_TYPE_FINDREG.equals(type) || MessageTypeModel.MESSAGE_TYPE_REGISTER.equals(type));
        if (flag) {
            type = type + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
        }

        logger.info("phone：" + phone + "，type：" + type + "，开始发送验证码");
        String message = this.check(phone, type);
        logger.info("phone：" + phone + "，type：" + type + "，开始发送验证码，检测结果：" + message);

        if (StringUtil.isBlank(message)) {
            long countDown = clSmsService.findTimeDifference(phone, type);
            if (countDown != 0) {
                data.put("countDown", countDown);
                data.put("state", "20");
                //获取短信验证码过于频繁，请稍后再试
                message = "Getting SMS authentication code is too frequent. Please try again later";
                logger.info("发送短信，phone：" + phone + "， type：" + type + "，发送前的校验结果message：" + message);
            } else {
                logger.info("发送短信，phone：" + phone + "， type：" + type + "，准备发送");
                int vcode = (int) (Math.random() * 9000) + 1000;
                logger.info("----------------------验证码--"+vcode);
                String orderNo = clSmsService.sendSms(phone, type);
                if (StringUtil.isNotBlank(orderNo)) {
                    data.put("state", "10");
                    resultMap.put(Constant.RESPONSE_DATA, data);
                    resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                    //已发送，请注意查收
                    resultMap.put(Constant.RESPONSE_CODE_MSG, "Has been sent, please pay attention to check");
                } else {
                    resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                    resultMap.put(Constant.RESPONSE_CODE_MSG, "Failed to send SMS");
                }
            }
        }
        if (StringUtil.isNotBlank(message)) {
            logger.info("发送短信，phone：" + phone + "， type：" + type + "，发送前的校验结果message：" + message);
            data.put("state", "20");
            data.put("message", message);
            resultMap.put(Constant.RESPONSE_DATA, data);
            resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            //发送失败
            resultMap.put(Constant.RESPONSE_CODE_MSG, message);
        }
        ServletUtils.writeToResponse(response, resultMap);
    }

    /**
     * 短信验证
     *
     * @param phone 手机号
     * @param type  短信类型
     * @param code  验证码
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/user/verifySms.htm")
    public void verifyMsg(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "vcode") String code,
             @RequestParam(value = "appFlag", required = false) String appFlag
    ) throws Exception {
        //如果验证注册和找回密码则进行类型修改（新app前提下）
        String secondProductName = Global.getValue("title2");
        boolean flag = secondProductName.equals(appFlag) && (MessageTypeModel.MESSAGE_TYPE_FINDREG.equals(type) || MessageTypeModel.MESSAGE_TYPE_REGISTER.equals(type));
        if (flag) {
            type = type + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
        }

        int msg = clSmsService.verifySms(phone, type, code);
        Map<String, Object> result = new HashMap<>(8);
        Map<String, Object> data = new HashMap<>(8);
        if (msg == 1) {
            data.put("state", "10");
            data.put("message", "Verify success.");
            result.put(Constant.RESPONSE_DATA, data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            //验证成功
            result.put(Constant.RESPONSE_CODE_MSG, "Verify success.");
        } else if (msg == -1) {
            //验证码已过期
            data.put("message", "Verification code expired.");
            data.put("state", "20");
            result.put(Constant.RESPONSE_DATA, data);
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            //验证失败
            result.put(Constant.RESPONSE_CODE_MSG, "Verification code expired.");
        } else {
            //手机号码或验证码错误
            data.put("message", "Your OTP is incorrect. Please enter the correct number.");
            data.put("state", "20");
            result.put(Constant.RESPONSE_DATA, data);
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            //验证失败
            result.put(Constant.RESPONSE_CODE_MSG, "Your OTP is incorrect. Please enter the correct number.");
        }
        System.out.println(JSON.toJSON(result));
        ServletUtils.writeToResponse(response, result);
    }


    private String check(String phone, String type) {
        String message = null;
        if (StringUtil.isBlank(phone) || StringUtil.isBlank(type)) {
            //参数不能为空
            message = "Parameters cannot be null.";
        } else if (!StringUtil.isPhone(phone)) {
            //手机格式有误
            message = "The format of mobile number is incorrect.";
        } else {
            if (StringUtil.equals(MessageTypeModel.MESSAGE_TYPE_REGISTER, type)||StringUtil.equals(MessageTypeModel.MESSAGE_TYPE_REGISTER+MessageTypeModel.MESSAGE_TYPE_SUFFIX, type)) {
                // 当日最大注册用户数
                long todayCount = cloanUserService.todayCount();
                String dayRegisterMaxCount = Global.getValue("day_register_max");
                if (StringUtil.isNotBlank(dayRegisterMaxCount)) {
                    int dayRegisterMax = Integer.parseInt(dayRegisterMaxCount);
                    if (dayRegisterMax > 0 && todayCount >= dayRegisterMax) {
                        //今天注册人数已达到上限
                        message = "Today, the number of registered people has reached the upper limit.";
                    }
                }
                if (clSmsService.findUser(phone) > 0) {
                    //该手机号已经注册
                    message = "The mobile phone number has been registered.";
                }
            }
            if (StringUtil.equals(MessageTypeModel.MESSAGE_TYPE_FINDREG, type)||StringUtil.equals(MessageTypeModel.MESSAGE_TYPE_FINDREG+MessageTypeModel.MESSAGE_TYPE_SUFFIX, type)) {
                if (clSmsService.findUser(phone) < 1) {
                    //
                    message = "The phone number does not exist";
                }
            }
            if (message == null && clSmsService.countDayTime(phone, type) <= 0) {
                //获取短信验证码过于频繁，请稍后再试
                message = "Access to SMS Verification Code is too frequent. Please try again later.";
            }
            if (message == null && clSmsService.checkIsMotherFucker(phone, type)) {
                //获取短信验证码过于频繁，请稍后再试
                message = "Access to SMS Verification Code is too frequent. Please try again later.";
            }
        }
        return message;
    }


    /**
     * 获取验证码（非短信验证码）
     */
    @RequestMapping(value = "/api/user/getCode.htm")
    public void getCode() {
        Map<String, Object> resultMap = new HashMap<>(8);
        // 生成随机数
        Random random = new Random();
        int codeCount = 4;
        int num;
        // randomCode记录随机产生的验证码
        StringBuilder randomCode = new StringBuilder();
        // 随机产生codeCount个字符的验证码。  
        for (int i = 0; i < codeCount; i++) {
            num = random.nextInt(255);
            randomCode.append(num);
        }
        // 将四位数字的验证码保存到Session中。  
        String code = randomCode.toString();

        HttpSession session = request.getSession();
        session.setAttribute("randomCode", code);
        session.setMaxInactiveInterval(3 * 60);

        resultMap.put(Constant.RESPONSE_CODE, 200);
        resultMap.put("randomCode", code);

        ServletUtils.writeToResponse(response, resultMap);
    }

    /**
     * 获取app的版本号
     * 2018-04-20
     */
    @RequestMapping(value = "/api/user/appVersion.htm")
    public void appVersion() {
        Map<String, Object> data = new HashMap<>(8);
        String version = Global.getValue("check_version");
        logger.info("当前app版本号：" + version);
        data.put("version", version);
        ServletUtils.writeToResponse(response, data);
    }


    /**
     * 语音验证码动作处理
     */
    @RequestMapping(value = "/sms/voice/callback.htm", method = RequestMethod.POST)
    public void callback() {
        try {
            JSONObject params = getParams(request);
            System.out.println("callback");
            System.out.println(params);
            UserVoiceSmsLog userVoiceSmsLog = JSONObject.parseObject(params.toString(), UserVoiceSmsLog.class);
            userVoiceSmsLog.setType(Constant.USER_VOICE_CALL_BACK);
            userVoiceSmsLog.setCreateTime(new Date());
            userVoiceSmsLogServiceImpl.insert(userVoiceSmsLog);

            String callSessionState = userVoiceSmsLog.getCallSessionState();
            System.out.println(callSessionState);
            if (Constant.USER_VOICE_SESSON_STATUS.equals(callSessionState)){
                System.out.println(callSessionState+"");

                String content = clSmsService.dealVoiceCall(params);
                Response response = new Response();
                response.setSay(content);
                String xml = XStreamUtils.toXml(response);
                this.response.getWriter().write(xml);
            }else {
                this.response.getWriter().write(200);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *语音验证码记录拨打事件
     *
     */
    @RequestMapping(value = "/sms/voice/event.htm", method = RequestMethod.POST)
    public void callbackEvent () {
        try {
            JSONObject params = getParams(request);
            UserVoiceSmsLog userVoiceSmsLog = JSONObject.parseObject(params.toString(), UserVoiceSmsLog.class);
            userVoiceSmsLog.setType(Constant.USER_VOICE_EVENT);
            userVoiceSmsLog.setCreateTime(new Date());
            userVoiceSmsLogServiceImpl.insert(userVoiceSmsLog);
            System.out.println("event");
            System.out.println(params);
            this.response.getWriter().write(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 获取语音验证码接口
     *
     * @param phone 手机号/api
     */
    @RequestMapping(value = "/api/sms/voice/makeCall.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void makeCall (@RequestParam(value = "phone") String phone, @RequestParam(value = "type") String type) {

        String check = check(phone, type);
        //语音验证码
        if (check!=null){
            ServletUtils.writeToResponse(response, ResponseWrapper.error(check));
            return;
        }
        ResponseWrapper responseWrapper= clSmsService.makeVoiceCall(phone,type);
        ServletUtils.writeToResponse(response, responseWrapper);
    }

    private  JSONObject getParams( HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();//获取当前登陆用户资料流
        String requestJson = ReqToJsonUtil.parseRequestToJson(inputStream);
        JSONObject result = new JSONObject();
        String[] split = requestJson.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            result.put(split1[0],split1[1]);
        }
        return result;
    }
}
