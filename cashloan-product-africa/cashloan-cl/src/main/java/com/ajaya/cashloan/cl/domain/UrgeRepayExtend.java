package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 催收扩展表实体
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-12-02 23:24:12
 */
 public class UrgeRepayExtend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 借款人标志
    */
    private Long clUserId;

    /**
    * 催收人标志
    */
    private Long userId;

    /**
    * 借款人电话
    */
    private String phone;

    /**
    * 借款订单id
    */
    private Long borrowId;

    /**
    * 催收订单id（cl_urge_repay_order表）
    */
    private Long urgeOrderId;

    /**
    * 催收订单记录id（cl_urge_repay_log表主键）
    */
    private Integer urgeLogId;

    /**
    * 接听状态（0：预约付款，1：关机，2：停机，3：无法接听，4：无人接听，5：客户设置，6：通话中，7：已还款）
    */
    private String answerState;

    /**
    * 预留字段1
    */
    private String extend1;

    /**
    * 预留字段2
    */
    private String extend2;

    /**
    * 预留字段3
    */
    private String extend3;

    /**
     * 创建时间
     */
     private Date createTime;

     /**
     * 更新时间
     */
     private Date updateTime;
     
     

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

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
    * 获取借款人标志
    *
    * @return 借款人标志
    */
    public Long getClUserId(){
        return clUserId;
    }

    /**
    * 设置借款人标志
    * 
    * @param clUserId 要设置的借款人标志
    */
    public void setClUserId(Long clUserId){
        this.clUserId = clUserId;
    }

    /**
    * 获取催收人标志
    *
    * @return 催收人标志
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置催收人标志
    * 
    * @param userId 要设置的催收人标志
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取借款人电话
    *
    * @return 借款人电话
    */
    public String getPhone(){
        return phone;
    }

    /**
    * 设置借款人电话
    * 
    * @param phone 要设置的借款人电话
    */
    public void setPhone(String phone){
        this.phone = phone;
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
    * 获取催收订单id（cl_urge_repay_order表）
    *
    * @return 催收订单id（cl_urge_repay_order表）
    */
    public Long getUrgeOrderId(){
        return urgeOrderId;
    }

    /**
    * 设置催收订单id（cl_urge_repay_order表）
    * 
    * @param urgeOrderId 要设置的催收订单id（cl_urge_repay_order表）
    */
    public void setUrgeOrderId(Long urgeOrderId){
        this.urgeOrderId = urgeOrderId;
    }

    /**
    * 获取催收订单记录id（cl_urge_repay_log表主键）
    *
    * @return 催收订单记录id（cl_urge_repay_log表主键）
    */
    public Integer getUrgeLogId(){
        return urgeLogId;
    }

    /**
    * 设置催收订单记录id（cl_urge_repay_log表主键）
    * 
    * @param urgeLogId 要设置的催收订单记录id（cl_urge_repay_log表主键）
    */
    public void setUrgeLogId(Integer urgeLogId){
        this.urgeLogId = urgeLogId;
    }

    /**
    * 获取接听状态（0：预约付款，1：关机，2：停机，3：无法接听，4：无人接听，5：客户设置，6：通话中，7：已还款）
    *
    * @return 接听状态（0：预约付款，1：关机，2：停机，3：无法接听，4：无人接听，5：客户设置，6：通话中，7：已还款）
    */
    public String getAnswerState(){
        return answerState;
    }

    /**
    * 设置接听状态（0：预约付款，1：关机，2：停机，3：无法接听，4：无人接听，5：客户设置，6：通话中，7：已还款）
    * 
    * @param answerState 要设置的接听状态（0：预约付款，1：关机，2：停机，3：无法接听，4：无人接听，5：客户设置，6：通话中，7：已还款）
    */
    public void setAnswerState(String answerState){
        this.answerState = answerState;
    }

    /**
    * 获取预留字段1
    *
    * @return 预留字段1
    */
    public String getExtend1(){
        return extend1;
    }

    /**
    * 设置预留字段1
    * 
    * @param extend1 要设置的预留字段1
    */
    public void setExtend1(String extend1){
        this.extend1 = extend1;
    }

    /**
    * 获取预留字段2
    *
    * @return 预留字段2
    */
    public String getExtend2(){
        return extend2;
    }

    /**
    * 设置预留字段2
    * 
    * @param extend2 要设置的预留字段2
    */
    public void setExtend2(String extend2){
        this.extend2 = extend2;
    }

    /**
    * 获取预留字段3
    *
    * @return 预留字段3
    */
    public String getExtend3(){
        return extend3;
    }

    /**
    * 设置预留字段3
    * 
    * @param extend3 要设置的预留字段3
    */
    public void setExtend3(String extend3){
        this.extend3 = extend3;
    }

}