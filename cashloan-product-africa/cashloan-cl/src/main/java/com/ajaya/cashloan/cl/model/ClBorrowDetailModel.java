package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：确认放款VO展示
 *
 * @author yanzhiqiang
 * @since 2020-05-09 16:30
 **/
public class ClBorrowDetailModel {
    /**
     * 借款金额
     */
    private Double amount;
    /**
     * 借款期限
     */
    private String tenure;
    /**
     * 实际到帐金额
     */
    private Double received;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 应用日期
     */
    private String applicationDate;
    /**
     * 总还款金额
     */
    private Double repayment;

    /**
     * 借款还款日期
     */

    private String repayTime;
    public Double getReceived() {

        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Double getRepayment() {
        return repayment;
    }

    public void setRepayment(Double repayment) {
        this.repayment = repayment;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }
}
