package com.ajaya.cashloan.cl.model;

/**
 * 
 * 还款表现展示类
 *
 */
public class ColStaTodayDataModel {

	/**
	 * 首逾笔数
	 */
	private Integer totalData;
	/**
	 * 自然回款
	 */
	private Integer repayment;
	/**
	 * 实际入催笔数
	 */
	private Integer colOrder;
	public Integer getTotalData() {
		return totalData;
	}
	public void setTotalData(Integer totalData) {
		this.totalData = totalData;
	}
	public Integer getRepayment() {
		return repayment;
	}
	public void setRepayment(Integer repayment) {
		this.repayment = repayment;
	}
	public Integer getColOrder() {
		return colOrder;
	}
	public void setColOrder(Integer colOrder) {
		this.colOrder = colOrder;
	}
	
}
