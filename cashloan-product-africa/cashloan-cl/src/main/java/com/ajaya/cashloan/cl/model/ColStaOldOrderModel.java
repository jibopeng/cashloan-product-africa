package com.ajaya.cashloan.cl.model;


/**
 * 
 * 老案件催回情况
 *
 */
public class ColStaOldOrderModel {

	/**
	 * 催收员姓名
	 */
	private String userName;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 逾期天数
	 */
	private Integer penaltyDay;
	/**
	 * 实际还款金额
	 */
	private String realAmount;
	/**
	 * 回款时间
	 */
	private String repaymentTime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getPenaltyDay() {
		return penaltyDay;
	}
	public void setPenaltyDay(Integer penaltyDay) {
		this.penaltyDay = penaltyDay;
	}
	public String getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}
	public String getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(String repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	
	
}
