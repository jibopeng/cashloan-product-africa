package com.ajaya.cashloan.cl.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 功能说明：领款确认
 *
 * @author yanzhiqiang
 * @since 2021-01-15 11:22
 **/
public class BorrowConfirmModel {

    @JsonIgnoreProperties
    private Long userId;
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
     * 是否需要进行绑卡
     */
    private Boolean needCardBing;
    private Integer failCount;


    /**
     * 特殊字段
     */
    @JsonIgnoreProperties
    private String signServiceId;
    /**
     * 借款目的
     */
    private String loadPurpose;

    /**
     * 应还款日期
     */
    private String dueDate;

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

    public Boolean getNeedCardBing() {
        return needCardBing;
    }

    public void setNeedCardBing(Boolean needCardBing) {
        this.needCardBing = needCardBing;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLoadPurpose() {
        return loadPurpose;
    }

    public void setLoadPurpose(String loadPurpose) {
        this.loadPurpose = loadPurpose;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSignServiceId() {
        return signServiceId;
    }

    public void setSignServiceId(String signServiceId) {
        this.signServiceId = signServiceId;
    }
}
