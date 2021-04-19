package com.ajaya.cashloan.cl.domain.entity;

import java.util.Date;

/**
 * 
 * 黑白名单共库接口请求参数类
 *
 */
public class SpecialListRequest {

	private String aadNo;
	
	private String panNo;
	
	private String phone;
	
	private Integer isBorrowing;

	private String bankCardNo;
	
	private String projectName;
	
	private Integer penaltyDay;
	
	private Date repayTime;
	
	private Date payTime;
	
	public String getAadNo() {
		return aadNo;
	}

	public void setAadNo(String aadNo) {
		this.aadNo = aadNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getIsBorrowing() {
		return isBorrowing;
	}

	public void setIsBorrowing(Integer isBorrowing) {
		this.isBorrowing = isBorrowing;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getPenaltyDay() {
		return penaltyDay;
	}

	public void setPenaltyDay(Integer penaltyDay) {
		this.penaltyDay = penaltyDay;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
}
