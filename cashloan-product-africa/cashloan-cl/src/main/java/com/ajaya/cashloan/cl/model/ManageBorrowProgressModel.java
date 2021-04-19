package com.ajaya.cashloan.cl.model;

import com.ajaya.cashloan.cl.domain.BorrowProgress;
import com.ajaya.cashloan.core.model.BorrowModel;

public class ManageBorrowProgressModel extends BorrowProgress {

	private static final long serialVersionUID = 1L;

	/**
	 * 真实姓名
	 */
	private String realName;
 
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 借款金额
	 */
	private Double amount;

	/**
	 * 状态中文含义
	 */
	private String stateStr;
	
	
	public String getStateStr() {
		this.stateStr = BorrowModel.apiConvertBorrowState(this.getState());
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		
		this.stateStr = stateStr;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
	
}
