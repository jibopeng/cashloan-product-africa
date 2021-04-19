package com.ajaya.cashloan.core.common.util;

import com.ajaya.cashloan.core.common.context.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 功能说明： adjust事件上传
 *
 * @author yanzhiqiang
 * @since 2020-07-15 18:35
 **/
public class AdJustEnventMonitorUtils {
    private final static Logger logger = LoggerFactory.getLogger(AdJustEnventMonitorUtils.class);
    /**
     * 注册：
     * 申请：
     * 通过：
     * 放款：
     */
    public static String RESITER_ADJSUT = "adjust_register";
    public static String APPLYCATION_ADJSUT = "adjust_application";
    public static String APPLAYCATION_PASS_ADJSUT = "adjust_application_pass";
    public static String PAYMENT_OUT_ADJSUT = "adjust_pay_out";
    public static void adJustEnvent(String eventType, String anddorId, String gpsId) {
        String event = Global.getValue(eventType);
        String appToken = Global.getValue("adjust_app_token");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            HashMap<String,String > hashMap = new HashMap<>(8);
            hashMap.put("app_token", appToken);
            hashMap.put("event_token", event);
            hashMap.put("s2s", 1+"");
            hashMap.put("created_at", format.format(new Date()).replace(" ","%20"));
            if (StringUtil.isEmpty(gpsId)) {
                hashMap.put("android_id", anddorId);
            } else {
                hashMap.put("gps_adid", gpsId);
            }
            String result = HttpClientUtil.sentGet("https://s2s.adjust.com/event", null, hashMap, "utf8");
            logger.info("标识 {} 类型 {} 时间 请求结果：" + result, anddorId + "=" + gpsId, eventType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
