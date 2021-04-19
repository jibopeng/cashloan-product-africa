package com.ajaya.cashloan.core.model;

import java.util.Date;

import com.ajaya.cashloan.core.domain.UserBaseInfo;

public class ManagerUserModel extends UserBaseInfo {

	// 注册信息
	private String phone;
	private String sex;
	private Date registTime;
	private String channleName;
	private String gpsCoordinate;
	private String registCoordinate;
	
	
	// 个人信息
	private String handFillName;
	private String birthday;
	private String  bvn;
	private String email;
	private String bvnName;
	private String bvnPhone;
	private String bvnBirthday;
	
	
	// 基本信息
	private String marriage;
	private String childenNum;
	private String education;
	private String occupation;
	private String payWay;
	private String monthlySalary;
	private String borrowPurpose;
	private String residentialType;
	private String state;
	private String address;
	
	// 银行信息
	private String bankName;
	private String accountNo;
	private String accountName;
	private String accountNo1;
	private String accountNo2;
	private String accountNo3;
	private String virtualAccountBank;
	private String virtualAccountNo;
	private String virtualAccountName;
	
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	public String getChannleName() {
		return channleName;
	}
	public void setChannleName(String channleName) {
		this.channleName = channleName;
	}
	public String getGpsCoordinate() {
		return gpsCoordinate;
	}
	public void setGpsCoordinate(String gpsCoordinate) {
		this.gpsCoordinate = gpsCoordinate;
	}
	public String getRegistCoordinate() {
		return registCoordinate;
	}
	public void setRegistCoordinate(String registCoordinate) {
		this.registCoordinate = registCoordinate;
	}
	public String getHandFillName() {
		return handFillName;
	}
	public void setHandFillName(String handFillName) {
		this.handFillName = handFillName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBvn() {
		return bvn;
	}
	public void setBvn(String bvn) {
		this.bvn = bvn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBvnName() {
		return bvnName;
	}
	public void setBvnName(String bvnName) {
		this.bvnName = bvnName;
	}
	public String getBvnPhone() {
		return bvnPhone;
	}
	public void setBvnPhone(String bvnPhone) {
		this.bvnPhone = bvnPhone;
	}
	public String getBvnBirthday() {
		return bvnBirthday;
	}
	public void setBvnBirthday(String bvnBirthday) {
		this.bvnBirthday = bvnBirthday;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getChildenNum() {
		return childenNum;
	}
	public void setChildenNum(String childenNum) {
		this.childenNum = childenNum;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getMonthlySalary() {
		return monthlySalary;
	}
	public void setMonthlySalary(String monthlySalary) {
		this.monthlySalary = monthlySalary;
	}
	public String getBorrowPurpose() {
		return borrowPurpose;
	}
	public void setBorrowPurpose(String borrowPurpose) {
		this.borrowPurpose = borrowPurpose;
	}
	public String getResidentialType() {
		return residentialType;
	}
	public void setResidentialType(String residentialType) {
		this.residentialType = residentialType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo1() {
		return accountNo1;
	}
	public void setAccountNo1(String accountNo1) {
		this.accountNo1 = accountNo1;
	}
	public String getAccountNo2() {
		return accountNo2;
	}
	public void setAccountNo2(String accountNo2) {
		this.accountNo2 = accountNo2;
	}
	public String getAccountNo3() {
		return accountNo3;
	}
	public void setAccountNo3(String accountNo3) {
		this.accountNo3 = accountNo3;
	}
	public String getVirtualAccountBank() {
		return virtualAccountBank;
	}
	public void setVirtualAccountBank(String virtualAccountBank) {
		this.virtualAccountBank = virtualAccountBank;
	}
	public String getVirtualAccountNo() {
		return virtualAccountNo;
	}
	public void setVirtualAccountNo(String virtualAccountNo) {
		this.virtualAccountNo = virtualAccountNo;
	}
	public String getVirtualAccountName() {
		return virtualAccountName;
	}
	public void setVirtualAccountName(String virtualAccountName) {
		this.virtualAccountName = virtualAccountName;
	}
}
