package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * '用户bvn记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-15 17:23:05
 */
 public class UserBvn implements Serializable {

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
    * 姓名
    */
    private String firstName;

    /**
    * 姓氏
    */
    private String lastName;

    /**
    * 
    */
    private String dob;

    /**
    * 生日格式化
    */
    private String formattedDob;

    /**
    * 手机号
    */
    private String mobile;

    /**
    * bvn账号
    */
    private String bvn;

    /**
    * 
    */
    private String message;

    /**
    * 调用状态
    */
    private String status;

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
    * 获取姓名
    *
    * @return 姓名
    */
    public String getFirstName(){
        return firstName;
    }

    /**
    * 设置姓名
    * 
    * @param firstName 要设置的姓名
    */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
    * 获取姓氏
    *
    * @return 姓氏
    */
    public String getLastName(){
        return lastName;
    }

    /**
    * 设置姓氏
    * 
    * @param lastName 要设置的姓氏
    */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getDob(){
        return dob;
    }

    /**
    * 设置
    * 
    * @param dob 要设置的
    */
    public void setDob(String dob){
        this.dob = dob;
    }

    /**
    * 获取生日格式化
    *
    * @return 生日格式化
    */
    public String getFormattedDob(){
        return formattedDob;
    }

    /**
    * 设置生日格式化
    * 
    * @param formattedDob 要设置的生日格式化
    */
    public void setFormattedDob(String formattedDob){
        this.formattedDob = formattedDob;
    }

    /**
    * 获取手机号
    *
    * @return 手机号
    */
    public String getMobile(){
        return mobile;
    }

    /**
    * 设置手机号
    * 
    * @param mobile 要设置的手机号
    */
    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    /**
    * 获取bvn账号
    *
    * @return bvn账号
    */
    public String getBvn(){
        return bvn;
    }

    /**
    * 设置bvn账号
    * 
    * @param bvn 要设置的bvn账号
    */
    public void setBvn(String bvn){
        this.bvn = bvn;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getMessage(){
        return message;
    }

    /**
    * 设置
    * 
    * @param message 要设置的
    */
    public void setMessage(String message){
        this.message = message;
    }

    /**
    * 获取调用状态
    *
    * @return 调用状态
    */
    public String getStatus(){
        return status;
    }

    /**
    * 设置调用状态
    * 
    * @param status 要设置的调用状态
    */
    public void setStatus(String status){
        this.status = status;
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
    * 获取更新时间
    *
    * @return 更新时间
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置更新时间
    * 
    * @param updateTime 要设置的更新时间
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}