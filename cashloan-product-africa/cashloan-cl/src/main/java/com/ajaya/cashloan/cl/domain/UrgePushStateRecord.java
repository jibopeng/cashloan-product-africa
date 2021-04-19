package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 催收推送状态表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-04-28 11:30:15
 */
 public class UrgePushStateRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private Long userId;

    /**
    * 
    */
    private Long borrowId;

    /**
    * 
    */
    private String borrowState;

    /**
    * 
    */
    private String repayState;

    /**
    * 
    */
    private Date createTime;

    /**
    * 
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
    * 获取
    *
    * @return 
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置
    * 
    * @param userId 要设置的
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置
    * 
    * @param borrowId 要设置的
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getBorrowState(){
        return borrowState;
    }

    /**
    * 设置
    * 
    * @param borrowState 要设置的
    */
    public void setBorrowState(String borrowState){
        this.borrowState = borrowState;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getRepayState(){
        return repayState;
    }

    /**
    * 设置
    * 
    * @param repayState 要设置的
    */
    public void setRepayState(String repayState){
        this.repayState = repayState;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置
    * 
    * @param createTime 要设置的
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置
    * 
    * @param updateTime 要设置的
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}