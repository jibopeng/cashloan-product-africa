package com.ajaya.cashloan.core.common.context;

import java.util.Map;

import com.ajaya.cashloan.core.domain.BorrowProduct;
import com.alibaba.fastjson.JSONObject;
import tool.util.NumberUtil;
import tool.util.StringUtil;

/**
 * 启动加载缓存类
 * 
 * @author gc
 * @version 1.0.0
 * @date 2020年11月11日 下午3:37:13
 */
public class Global {

	public static Map<String, Object> configMap;

	public static Map<String, Object> msg_template_Map;

	public static int getInt(String key){
		return NumberUtil.getInt(StringUtil.isNull(configMap.get(key)));
	}

	public static double getDouble(String key){
		return NumberUtil.getDouble(StringUtil.isNull(configMap.get(key)));
	}

	public static String getValue(String key) {
		return StringUtil.isNull(configMap.get(key));
	}

	public static Object getObject(String key){
		return configMap.get(key);
	}

	public static String getMsgTempLate(String key) {
		return StringUtil.isNull(msg_template_Map.get(key));
	}

	public static BorrowProduct getProduct(String userType){
		String  o = StringUtil.isNull(configMap.get("borrowProduct_" + userType));
		return JSONObject.parseObject(o, BorrowProduct.class);
	}

}
