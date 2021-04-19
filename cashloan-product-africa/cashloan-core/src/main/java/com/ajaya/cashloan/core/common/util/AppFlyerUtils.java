package com.ajaya.cashloan.core.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.model.HttpClientUtilResponse;
import com.alibaba.fastjson.JSON;

public class AppFlyerUtils {

	private final static Logger logger = LoggerFactory.getLogger(AppFlyerUtils.class);
	 /**
     * 注册：
     * 申请：
     * 通过：
     * 放款：
     */
	public static String RESITER_APPFLYER = "register";
	public static String APPLYCATION_APPFLYER = "application";
	public static String APPLAYCATION_PASS_APPFLYER = "application_pass";
	public static String PAYMENT_OUT_APPFLYER = "pay_out";
	
	
	public static String appFlyerEnvent(String appsflyerId , String customerUserId, String eventName, String eventTime) {
		String authentication = Global.getValue("appFlyer_authentication");
		String appName = Global.getValue("appFlyer_app_name");
		
    	Map<String, String> header = new HashMap<String, String>();
    	header.put("authentication", authentication);
		Map<String, Object> param = new HashMap<>();
		
		param.put("appsflyer_id", appsflyerId);
		param.put("customer_user_id", customerUserId);
		param.put("eventName", eventName);
		Map<String, Object> eventValue = new HashMap<>();
		eventValue.put("uuid", customerUserId);
		param.put("eventValue", JSON.toJSONString(eventValue));
		param.put("eventTime", eventTime);
		HttpClientUtilResponse httpClientUtilResponse = HttpClientKudosUtil.sendPost("https://api2.appsflyer.com/inappevent/" + appName, header, JSON.toJSONString(param), "utf-8");
		logger.info("customerUserId {} , eventName {} ,上报结果  " + httpClientUtilResponse.getResult(), customerUserId , eventName );
		return httpClientUtilResponse.getResult();
	}

}
