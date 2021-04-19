package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * BorrowFlag实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-02-08 11:44:04
 */
 public class BorrowFlag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private Long borrowId;

    /**
    * 
    */
    private String state;

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