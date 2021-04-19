package com.ajaya.cashloan.cl.model;

import com.ajaya.cashloan.cl.domain.PayRespLog;


public class ManagePayRespLogModel extends PayRespLog {

	private static final long serialVersionUID = 1L;

	public ManagePayRespLogModel(String orderNo, Integer type, String params) {
		super(orderNo, type, params);
	}
	
}
