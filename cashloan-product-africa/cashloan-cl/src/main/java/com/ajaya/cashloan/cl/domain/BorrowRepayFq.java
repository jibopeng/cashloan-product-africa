package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 分期还款计划表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:01
 */
 public class BorrowRepayFq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 借款订单id
    */
    private Long borrowId;

    /**
    * 还款金额
    */
    private Double amount;

    /**
    * 状态 10已还款 20未还款 30授权中
    */
    private String state;

    /**
    * 第几次分期
    */
    private String periods;

    /**
    * 创建时间
    */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;


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
    * 获取还款金额
    *
    * @return 还款金额
    */
    public Double getAmount(){
        return amount;
    }

    /**
    * 设置还款金额
    * 
    * @param amount 要设置的还款金额
    */
    public void setAmount(Double amount){
        this.amount = amount;
    }

    /**
    * 获取状态 10已还款 20未还款 30授权中
    *
    * @return 状态 10已还款 20未还款 30授权中
    */
    public String getState(){
        return state;
    }

    /**
    * 设置状态 10已还款 20未还款 30授权中
    * 
    * @param state 要设置的状态 10已还款 20未还款 30授权中
    */
    public void setState(String state){
        this.state = state;
    }

    /**
    * 获取第几次分期
    *
    * @return 第几次分期
    */
    public String getPeriods(){
        return periods;
    }

    /**
    * 设置第几次分期
    * 
    * @param period 要设置的第几次分期
    */
    public void setPeriod(String periods){
        this.periods = periods;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}