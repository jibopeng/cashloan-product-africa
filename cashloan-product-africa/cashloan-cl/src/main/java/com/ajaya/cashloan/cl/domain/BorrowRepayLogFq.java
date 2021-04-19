package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 分期还款记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:30
 */
 public class BorrowRepayLogFq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 分期还款计划id
    */
    private Long repayFqId;

    /**
    * 借款订单id
    */
    private Long borrowId;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 实际还款金额
    */
    private Double amount;

    /**
    * 逾期天数
    */
    private Integer penaltyDay;

    /**
    * 逾期罚金
    */
    private Double penaltyAmout;

    /**
    * 还款方式   10 DokyPay 20 RazorPay
    */
    private String repayWay;

    /**
    * 支付手续费
    */
    private Double payFee;

    /**
    * 还款账号
    */
    private String repayAccount;

    /**
    * 还款流水号
    */
    private String serialNumber;

    /**
    * 退还或补扣金额
    */
    private Double refundDeduction;

    /**
    * 退还或补扣支付时间
    */
    private Date payTime;

    /**
    * 实际还款时间
    */
    private Date repayTime;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 第几期
    */
    private String periods;


    /**
    * 获取主键Id
    *
    * @return id
    */
    public Long getId(){
        return id;
    }

    /**
    * 设置主键Id
    * 
    * @param 要设置的主键Id
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取分期还款计划id
    *
    * @return 分期还款计划id
    */
    public Long getRepayFqId(){
        return repayFqId;
    }

    /**
    * 设置分期还款计划id
    * 
    * @param repayFqId 要设置的分期还款计划id
    */
    public void setRepayFqId(Long repayFqId){
        this.repayFqId = repayFqId;
    }

    /**
    * 获取借款订单id
    *
    * @return 借款订单id
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置借款订单id
    * 
    * @param borrowId 要设置的借款订单id
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取用户id
    *
    * @return 用户id
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户id
    * 
    * @param userId 要设置的用户id
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取实际还款金额
    *
    * @return 实际还款金额
    */
    public Double getAmount(){
        return amount;
    }

    /**
    * 设置实际还款金额
    * 
    * @param amount 要设置的实际还款金额
    */
    public void setAmount(Double amount){
        this.amount = amount;
    }

    /**
    * 获取逾期天数
    *
    * @return 逾期天数
    */
    public Integer getPenaltyDay(){
        return penaltyDay;
    }

    /**
    * 设置逾期天数
    * 
    * @param penaltyDay 要设置的逾期天数
    */
    public void setPenaltyDay(Integer penaltyDay){
        this.penaltyDay = penaltyDay;
    }

    /**
    * 获取逾期罚金
    *
    * @return 逾期罚金
    */
    public Double getPenaltyAmout(){
        return penaltyAmout;
    }

    /**
    * 设置逾期罚金
    * 
    * @param penaltyAmout 要设置的逾期罚金
    */
    public void setPenaltyAmout(Double penaltyAmout){
        this.penaltyAmout = penaltyAmout;
    }

    /**
    * 获取还款方式   10 DokyPay 20 RazorPay
    *
    * @return 还款方式   10 DokyPay 20 RazorPay
    */
    public String getRepayWay(){
        return repayWay;
    }

    /**
    * 设置还款方式   10 DokyPay 20 RazorPay
    * 
    * @param repayWay 要设置的还款方式   10 DokyPay 20 RazorPay
    */
    public void setRepayWay(String repayWay){
        this.repayWay = repayWay;
    }

    /**
    * 获取支付手续费
    *
    * @return 支付手续费
    */
    public Double getPayFee(){
        return payFee;
    }

    /**
    * 设置支付手续费
    * 
    * @param payFee 要设置的支付手续费
    */
    public void setPayFee(Double payFee){
        this.payFee = payFee;
    }

    /**
    * 获取还款账号
    *
    * @return 还款账号
    */
    public String getRepayAccount(){
        return repayAccount;
    }

    /**
    * 设置还款账号
    * 
    * @param repayAccount 要设置的还款账号
    */
    public void setRepayAccount(String repayAccount){
        this.repayAccount = repayAccount;
    }

    /**
    * 获取还款流水号
    *
    * @return 还款流水号
    */
    public String getSerialNumber(){
        return serialNumber;
    }

    /**
    * 设置还款流水号
    * 
    * @param serialNumber 要设置的还款流水号
    */
    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }

    /**
    * 获取退还或补扣金额
    *
    * @return 退还或补扣金额
    */
    public Double getRefundDeduction(){
        return refundDeduction;
    }

    /**
    * 设置退还或补扣金额
    * 
    * @param refundDeduction 要设置的退还或补扣金额
    */
    public void setRefundDeduction(Double refundDeduction){
        this.refundDeduction = refundDeduction;
    }

    /**
    * 获取退还或补扣支付时间
    *
    * @return 退还或补扣支付时间
    */
    public Date getPayTime(){
        return payTime;
    }

    /**
    * 设置退还或补扣支付时间
    * 
    * @param payTime 要设置的退还或补扣支付时间
    */
    public void setPayTime(Date payTime){
        this.payTime = payTime;
    }

    /**
    * 获取实际还款时间
    *
    * @return 实际还款时间
    */
    public Date getRepayTime(){
        return repayTime;
    }

    /**
    * 设置实际还款时间
    * 
    * @param repayTime 要设置的实际还款时间
    */
    public void setRepayTime(Date repayTime){
        this.repayTime = repayTime;
    }

    /**
    * 获取创建时间
    *
    * @return 创建时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置创建时间
    * 
    * @param createTime 要设置的创建时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取第几期
    *
    * @return 第几期
    */
    public String getPeriods(){
        return periods;
    }

    /**
    * 设置第几期
    * 
    * @param periods 要设置的第几期
    */
    public void setPeriods(String periods){
        this.periods = periods;
    }

}