package com.ajaya.cashloan.cl.model;


/**
 * 功能说明：借款详情
 *
 * @author yanzhiqiang
 * @since 2021-01-15 11:22
 **/
public class BorrowDetailModel {
    /**
     * 借款状态
     */
    private String state;


    /**
     * 借款金额
     */
    private Double loanAmount;
    /**
     * 砍头费用
     */
    private Double originationFee;

    /**
     * 实际放款
     */
    private Double receiptAmount;
    /**
     * 利息
     */
    private Double interest;


    /**
     * 实际应还款
     */
    private Double totalPayment;
    /**
     * 逾期费用
     */
    private Double overDueFee;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 银行账户名称
     */
    private String receiptBank;

    /**
     * 银行账户号
     */
    private String receiptAccountNo;


    /**
     * 申请日期
     */
    private String applicationDate;

    /**
     *剩余应还金额
     */
    private Double repaymentAmount;

    /**
     * 应还款日期
     */
    private String dueDate;

    /**
     * 还款备注
     */
    private String rejectRemark;

    /**
     * 实际还款时间
     */
    private String repaymentDay;


    public String getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(String repaymentDay) {
        this.repaymentDay = repaymentDay;
    }

    public String getRejectRemark() {
        return rejectRemark;
    }

    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    public Double getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(Double repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getOriginationFee() {
        return originationFee;
    }

    public void setOriginationFee(Double originationFee) {
        this.originationFee = originationFee;
    }

    public Double getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Double receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Double getOverDueFee() {
        return overDueFee;
    }

    public void setOverDueFee(Double overDueFee) {
        this.overDueFee = overDueFee;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiptBank() {
        return receiptBank;
    }

    public void setReceiptBank(String receiptBank) {
        this.receiptBank = receiptBank;
    }

    public String getReceiptAccountNo() {
        return receiptAccountNo;
    }

    public void setReceiptAccountNo(String receiptAccountNo) {
        this.receiptAccountNo = receiptAccountNo;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
