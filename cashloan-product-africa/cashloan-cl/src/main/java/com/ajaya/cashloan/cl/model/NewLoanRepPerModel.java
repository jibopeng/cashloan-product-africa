package com.ajaya.cashloan.cl.model;


/**
 * 
 * 新贷还款表现展示类
 *CREATE TABLE `cl_report_new_loan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loan_time` varchar(255) DEFAULT NULL COMMENT '应还款时间',
  `repay_time` varchar(255) DEFAULT NULL,
  `registered_time` varchar(255) DEFAULT NULL,
  `loan_num` int(11) DEFAULT NULL COMMENT '应还款数',
  `repaid_num` int(11) DEFAULT NULL COMMENT '已还款数',
  `overdue_num` int(11) DEFAULT NULL COMMENT '逾期数',
  `overdue_per` varchar(255) DEFAULT NULL,
  `d1` varchar(255) DEFAULT NULL,
  `d1per` varchar(255) DEFAULT NULL,
  `d3` varchar(255) DEFAULT NULL,
  `d3per` varchar(255) DEFAULT NULL,
  `d5` varchar(255) DEFAULT NULL,
  `d5per` varchar(255) DEFAULT NULL,
  `d7` varchar(255) DEFAULT NULL,
  `d7per` varchar(255) DEFAULT NULL,
  `d14` varchar(255) DEFAULT NULL,
  `d14per` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COMMENT='新贷运营分析表';


 */
public class NewLoanRepPerModel {
	private String loanTime;
	private String repayTime;
	private String registeredTime;
	private String loanNum;
	private String repaidNum;
	private String overdueNum;
	private String overduePer;
	private String d1;
	private String d1per;
	private String d3;
	private String d3per;
	private String d5;
	private String d5per;
	private String d7;
	private String d7per;
	private String d14;
	private String d14per;
	public String getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}
	public String getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}
	public String getRegisteredTime() {
		return registeredTime;
	}
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}
	public String getLoanNum() {
		return loanNum;
	}
	public void setLoanNum(String loanNum) {
		this.loanNum = loanNum;
	}
	public String getRepaidNum() {
		return repaidNum;
	}
	public void setRepaidNum(String repaidNum) {
		this.repaidNum = repaidNum;
	}
	public String getOverdueNum() {
		return overdueNum;
	}
	public void setOverdueNum(String overdueNum) {
		this.overdueNum = overdueNum;
	}
	
	public String getOverduePer() {
		return overduePer;
	}
	public void setOverduePer(String overduePer) {
		this.overduePer = overduePer;
	}
	public String getD1() {
		return d1;
	}
	public void setD1(String d1) {
		this.d1 = d1;
	}
	public String getD1per() {
		return d1per;
	}
	public void setD1per(String d1per) {
		this.d1per = d1per;
	}
	public String getD3() {
		return d3;
	}
	public void setD3(String d3) {
		this.d3 = d3;
	}
	public String getD3per() {
		return d3per;
	}
	public void setD3per(String d3per) {
		this.d3per = d3per;
	}
	public String getD5() {
		return d5;
	}
	public void setD5(String d5) {
		this.d5 = d5;
	}
	public String getD5per() {
		return d5per;
	}
	public void setD5per(String d5per) {
		this.d5per = d5per;
	}
	public String getD7() {
		return d7;
	}
	public void setD7(String d7) {
		this.d7 = d7;
	}
	public String getD7per() {
		return d7per;
	}
	public void setD7per(String d7per) {
		this.d7per = d7per;
	}
	public String getD14() {
		return d14;
	}
	public void setD14(String d14) {
		this.d14 = d14;
	}
	public String getD14per() {
		return d14per;
	}
	public void setD14per(String d14per) {
		this.d14per = d14per;
	}
}
