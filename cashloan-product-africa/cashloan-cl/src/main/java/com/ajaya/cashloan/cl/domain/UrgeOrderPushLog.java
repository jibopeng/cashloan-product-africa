package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 催收系统订单推送表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-26 10:49:41
 */
 public class UrgeOrderPushLog implements Serializable {

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
    private String type;

    /**
    * 
    */
    private String state;

    /**
    * 
    */
    private String paramJson;

    /**
    * 
    */
    private String returnJson;

    /**
    * 
    */
    private Date createTime;


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
    public String getType(){
        return type;
    }

    /**
    * 设置
    * 
    * @param type 要设置的
    */
    public void setType(String type){
        this.type = type;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getState(){
        return state;
    }

    /**
    * 设置
    * 
    * @param state 要设置的
    */
    public void setState(String state){
        this.state = state;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getParamJson(){
        return paramJson;
    }

    /**
    * 设置
    * 
    * @param paramJson 要设置的
    */
    public void setParamJson(String paramJson){
        this.paramJson = paramJson;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getReturnJson(){
        return returnJson;
    }

    /**
    * 设置
    * 
    * @param returnJson 要设置的
    */
    public void setReturnJson(String returnJson){
        this.returnJson = returnJson;
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

}