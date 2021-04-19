package com.ajaya.cashloan.cl.model;


/**
 * 功能说明： 借款记录列表
 *
 * @author yanzhiqiang
 * @since 2021-01-21 11:23
 **/
public class BorrowListModel  {

	/**
	 * 借款金额
	 */
	private Double amount;

	/**
	 * 借款状态
	 */
	private String state;



	/**
	 * 订单id
	 */
	private Long borrowId;

	/**
	 * 申请日期
	 */
	private String applicationDate;


	/**
	 * 放款日期
	 */
	private String loanDate;


	/**
	 * 应还款日期
	 */
	private String dueDate;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
}
