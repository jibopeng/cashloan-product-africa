package com.ajaya.cashloan.rc.model.zx91;

import java.util.Date;

public class LoanInfo {
    private Integer borrowType;//借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
	private Integer borrowState ;//借款状态 0.未知1.拒贷2.批贷已放款3.批贷未放款4.借款人放弃申请5.审核中6.待放款
    private String borrowAmount;//借款金额
    private Date contractDate;//合同日期
    private Integer loanPeriod;//批贷期数
    private Integer repayState;//还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+
    private Long arrearsAmount; //欠款金额
	private String companyCode; //公司代码
	/** 
	 * 获取borrowAmount
	 * @return borrowAmount
	 */
	public String getBorrowAmount() {
		return borrowAmount;
	}
	/** 
	 * 获取borrowType
	 * @return borrowType
	 */
	public Integer getBorrowType() {
		return borrowType;
	}
	/** 
	 * 设置borrowType
	 * @param borrowType
	 */
	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}
	/** 
	 * 设置borrowAmount
	 * @param borrowAmount
	 */
	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	/** 
	 * 获取contractDate
	 * @return contractDate
	 */
	public Date getContractDate() {
		return contractDate;
	}
	/** 
	 * 设置contractDate
	 * @param contractDate
	 */
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	/** 
	 * 获取loanPeriod
	 * @return loanPeriod
	 */
	public Integer getLoanPeriod() {
		return loanPeriod;
	}
	/** 
	 * 设置loanPeriod
	 * @param loanPeriod
	 */
	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	/** 
	 * 获取arrearsAmount
	 * @return arrearsAmount
	 */
	public Long getArrearsAmount() {
		return arrearsAmount;
	}
	/** 
	 * 设置arrearsAmount
	 * @param arrearsAmount
	 */
	public void setArrearsAmount(Long arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}
	/** 
	 * 获取companyCode
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/** 
	 * 设置companyCode
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/** 
	 * 获取borrowState
	 * @return borrowState
	 */
	public Integer getBorrowState() {
		return borrowState;
	}
	/** 
	 * 设置borrowState
	 * @param borrowState
	 */
	public void setBorrowState(Integer borrowState) {
		this.borrowState = borrowState;
	}
	/** 
	 * 获取repayState
	 * @return repayState
	 */
	public Integer getRepayState() {
		return repayState;
	}
	/** 
	 * 设置repayState
	 * @param repayState
	 */
	public void setRepayState(Integer repayState) {
		this.repayState = repayState;
	}
    
    
}

