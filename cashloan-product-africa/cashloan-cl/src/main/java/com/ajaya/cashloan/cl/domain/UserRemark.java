package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 本人备注记录表表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-22 20:14:00
 */
 public class UserRemark implements Serializable {

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
    private Long sysUserId;

    /**
    * 
    */
    private String remark;

    /**
    * 搜索的姓名
    */
    private String name;

    /**
    * 搜索的电话
    */
    private String phone;

    /**
    * 
    */
    private String type;

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
    public Long getSysUserId(){
        return sysUserId;
    }

    /**
    * 设置
    * 
    * @param sysUserId 要设置的
    */
    public void setSysUserId(Long sysUserId){
        this.sysUserId = sysUserId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getRemark(){
        return remark;
    }

    /**
    * 设置
    * 
    * @param remark 要设置的
    */
    public void setRemark(String remark){
        this.remark = remark;
    }

    /**
    * 获取搜索的姓名
    *
    * @return 搜索的姓名
    */
    public String getName(){
        return name;
    }

    /**
    * 设置搜索的姓名
    * 
    * @param name 要设置的搜索的姓名
    */
    public void setName(String name){
        this.name = name;
    }

    /**
    * 获取搜索的电话
    *
    * @return 搜索的电话
    */
    public String getPhone(){
        return phone;
    }

    /**
    * 设置搜索的电话
    * 
    * @param phone 要设置的搜索的电话
    */
    public void setPhone(String phone){
        this.phone = phone;
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