package com.ajaya.cashloan.core.common.util.sms;

import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.model.SmsModel;
import com.ajaya.cashloan.core.common.util.HttpClientUtil;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.MD5Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**9
 * 功能说明：非洲短信平台业务工具类
 *
 * @author yanzhiqiang
 * @since 2021-01-13 17:35
 */
public class TalkingSmsUtils {
    //https://api.sandbox.africastalking.com/version1/messaging
    //private static String URL = "https://api.africastalking.com/version1/messaging";
    private static String URL ="https://api.sandbox.africastalking.com/version1/messaging";
    private static String VOICE_URL = "https://voice.africastalking.com/call";
    /**
     * 发短信工具类
     * @param phone 发送到手机号
     * @param message 发送信息
     * @return 返回机构返回代码
     */
    public static String sendMessage(String phone,String message) {
        phone="234"+phone;
        String username = Global.getValue("talking_sms_username");
        HashMap<String, String> heads = getHeader();
        HashMap<String, String> param = new HashMap<>(3);
        param.put("username",username);
        param.put("to",phone);
        param.put("message",message);
        return HttpClientUtil.sendPost(URL,heads, param,"utf-8");
    }



    /**
     * 发短信工具类
     * @param phone 发送到手机号
     * @return 返回机构返回代码
     */
    public static String sendVoiceMessage(String phone) {
        phone="234"+phone;
        String username = Global.getValue("talking_sms_username");
        String phoneNumber = Global.getValue("talking_phone_number");
        HashMap<String, String> heads = getHeader();
        HashMap<String, String> param = new HashMap<>(3);
        param.put("username",username);
        param.put("to",phone);
        param.put("from",phoneNumber);
        return HttpClientUtil.sendPost(VOICE_URL,heads, param,"utf-8");
    }

    private static HashMap<String, String> getHeader() {
        HashMap<String, String> heads = new HashMap<>(2);
        String apiKey = Global.getValue("talking_sms_apikey");
        heads.put("Accept","application/json");
        heads.put("Content-Type","application/x-www-form-urlencoded");
        heads.put("apiKey",apiKey);
        return heads;
    }

}
