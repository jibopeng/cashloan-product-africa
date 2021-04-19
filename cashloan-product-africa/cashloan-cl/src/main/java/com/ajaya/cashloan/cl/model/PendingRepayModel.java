package com.ajaya.cashloan.cl.model;

import java.sql.Date;

/**
 * 功能说明：待还款或逾期列表返回model
 *
 * @author yanzhiqiang
 * @since 2020-03-12 20:05
 **/
public class PendingRepayModel {

    /**
     * 借款id
     */
    private Long  borrowId;
    /**
     * 总还款金额
     */
    private Double repayment;
    /**
     * 借款放款日期
     */
    private Date applicationTime;
   /**
     * 借款放款日期字符串
     */
    private String applicationTimeStr;

    /**
     * 借款还款日期
     */
    private Date repayTime;
    /**
     * 借款还款日期字符串
     */
    private String repayTimeStr;

    /**
     * 借款状态
     */
    private String state;

    /**
     * 逾期天数
     */
    private Integer penaltyDay;

    /**
     * 剩余还款天数
     */
    private Integer dueDay;


    public Double getRepayment() {
        return repayment;
    }

    public void setRepayment(Double repayment) {
        this.repayment = repayment;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPenaltyDay() {
        return penaltyDay;
    }

    public void setPenaltyDay(Integer penaltyDay) {
        this.penaltyDay = penaltyDay;
    }

    public Integer getDueDay() {
        return dueDay;
    }

    public void setDueDay(Integer dueDay) {
        this.dueDay = dueDay;
    }

    public String getApplicationTimeStr() {
        return applicationTimeStr;
    }

    public void setApplicationTimeStr(String applicationTimeStr) {
        this.applicationTimeStr = applicationTimeStr;
    }

    public String getRepayTimeStr() {
        return repayTimeStr;
    }

    public void setRepayTimeStr(String repayTimeStr) {
        this.repayTimeStr = repayTimeStr;
    }
}
