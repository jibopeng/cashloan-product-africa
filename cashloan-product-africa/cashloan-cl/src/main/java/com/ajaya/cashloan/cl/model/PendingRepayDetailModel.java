package com.ajaya.cashloan.cl.model;


/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-03-12 21:00
 **/
public class PendingRepayDetailModel extends PendingRepayModel {

    /**
     * 借款金额
     *
     */
    private Double amount;
    /**
     * 借款期限
     */
    private String tenure;

    /**
     *实际接收金额
     */
    private Double received;
    /**
     *逾期金额
     */
    private Double penaltyAmount;


    /**
     *利息
     */

    private Double interestFee;

    public Double getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public Double getInterestFee() {
        return interestFee;
    }

    public void setInterestFee(Double interestFee) {
        this.interestFee = interestFee;
    }

    public Double getPenaltyAmout() {
        return penaltyAmount;
    }

    public void setPenaltyAmout(Double penaltyAmout) {
        this.penaltyAmount = penaltyAmout;
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

    public Double getReceived() {
        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }
}
