package com.ajaya.cashloan.cl.model;

import java.util.Date;

import tool.util.BigDecimalUtil;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.core.model.BorrowModel;

public class ManageBRepayModel extends BorrowRepay {
	
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
	private Double borrowAmount;

	/**
	 * 还款金额
	 */
	private Double repayAmount;
	
	/**
	 * 还款总额
	 */
	private Double repayTotal;
	
	/**
	 * 借款时间
	 */
	private Date borrowTime;
	
	private String stateStr;
	
	private String payChannel;
	
	/**
	 * 已还金额
	 */
	private Double successMoneyFq;
	
	/**
	 * 剩下应还金额
	 */
	private Double shengXiaRepayMoney;
	
	/**
	 * 未还款的分期还款金额
	 * @return
	 */
	private Double currentBorrowRepayFqMoney;


	
	/**
	 * 未还款的分期还款的状态
	 * @return
	 */
	private String currentBorrowRepayFqStr;


	/**
	 * 失效时间
	 */
	private Date expireBy;

	/**
	 * 还款短链接
	 */
	private String shortUrl;

	public Date getExpireBy() {
		return expireBy;
	}

	public void setExpireBy(Date expireBy) {
		this.expireBy = expireBy;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
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

	public Double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Double getRepayTotal() {
		this.repayTotal =  BigDecimalUtil.add(this.getRepayAmount(),this.getPenaltyAmout());
		return repayTotal;
	}

	public void setRepayTotal(Double repayTotal) {
		this.repayTotal = repayTotal;
	}

	public Date getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}
	
	public String getStateStr() {
		this.stateStr = BorrowModel.manageConvertBorrowRepayState(this.getState());
		return stateStr;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public Double getSuccessMoneyFq() {
		return successMoneyFq;
	}

	public void setSuccessMoneyFq(Double successMoneyFq) {
		this.successMoneyFq = successMoneyFq;
	}

	public Double getShengXiaRepayMoney() {
		return shengXiaRepayMoney;
	}

	public void setShengXiaRepayMoney(Double shengXiaRepayMoney) {
		this.shengXiaRepayMoney = shengXiaRepayMoney;
	}

	public Double getCurrentBorrowRepayFqMoney() {
		return currentBorrowRepayFqMoney;
	}

	public void setCurrentBorrowRepayFqMoney(Double currentBorrowRepayFqMoney) {
		this.currentBorrowRepayFqMoney = currentBorrowRepayFqMoney;
	}

	public String getCurrentBorrowRepayFqStr() {
		return currentBorrowRepayFqStr;
	}

	public void setCurrentBorrowRepayFqStr(String currentBorrowRepayFqStr) {
		this.currentBorrowRepayFqStr = currentBorrowRepayFqStr;
	}
	
	
}
