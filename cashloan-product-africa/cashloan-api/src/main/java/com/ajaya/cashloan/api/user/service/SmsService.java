package com.ajaya.cashloan.api.user.service;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import tool.util.DateUtil;

import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.BaseRuntimeException;
import com.ajaya.cashloan.core.common.util.SqlUtil;

/**
 * 短信业务相关接口
 *
 * @author yanzhiqiang
 * @since 2020-02-23 17:21
 */
@Service
public class SmsService {
    @Resource
    private DBService dbService;


    public String validateSmsCode(String phone, String type, String vcode) {
        //测试用dev
        if ("dev".equals(Global.getValue("app_environment")) && "0000".equals(vcode)) {
            return null;
        }
        Map<String, Object> sms = dbService.queryRec("select code, send_time,state,verify_time from cl_sms where phone=? and sms_type=? order by send_time desc limit 1", phone, type);
        if (sms == null) {
            //未找到对应的验证码记录
            return "The corresponding verification code record was not found.";
        }
        String verifyTime = sms.get("verify_time").toString();
        if (Integer.parseInt(verifyTime) > 10) {
            //验证码已失效
            return "The validation code has expired.";
        }
        long timeLimit = Long.parseLong(Global.getValue("sms_time_limit"));
        Date d1 = DateUtil.valueOf(sms.get("send_time").toString(), "yyyy-MM-dd HH:mm:ss");
        Date d2 = DateUtil.getNow();
        long diff = d2.getTime() - d1.getTime();
        if (diff > timeLimit * 60 * 1000) {
            //验证码已过期
            return "The verification code has expired.";
        }
        if (!vcode.equals(sms.get("code").toString())) {
            //验证码错误
            return "Verification code error.";
        }
        return null;
    }

    public String sendSmsByTpl(HttpServletRequest request, String phone, String smsType, Object... params) {
        Map<String, Object> rec = dbService.queryRec("select * from cl_sms_tpl where type=?", smsType);
        if (rec == null) {
            throw new BaseRuntimeException("没有找到短信模板:" + smsType);
        }
        String templ = (String) rec.get("tpl");

        for (Object param : params) {
            Object value = param;
            if (value == null) {
                value = "";
            } else {
                value = value.toString();
            }
            templ = templ.replaceFirst("\\{}", (String) value);
        }
        sendSms(phone, templ, smsType);
        return templ;
    }

    private void sendSms(String phone, String content, String smsType) {
        Map<String, Object> rec = new HashedMap();
        rec.put("send_time", new Date());
        String result = "短信已发送";
        rec.put("phone", phone);
        rec.put("content", content);
        rec.put("resp_time", new Date());
        rec.put("resp", result);
        rec.put("sms_type", smsType);
        dbService.insert(SqlUtil.buildInsertSqlMap("cl_sms", rec));

    }
}
