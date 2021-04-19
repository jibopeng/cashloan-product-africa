package com.ajaya.cashloan.cl.model;

import com.ajaya.cashloan.cl.domain.PayRespLog;

public class PayRespLogModel extends PayRespLog {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 同步返回
	 */
	public static final int RESP_LOG_TYPE_RETURN = 1;
	
	/**
	 * 异步响应
	 */
	public static final int RESP_LOG_TYPE_NOTIFY = 2;
	
	public PayRespLogModel(String orderNo, Integer type, String params) {
		super(orderNo, type, params);
	}
	
	
	
	
}
