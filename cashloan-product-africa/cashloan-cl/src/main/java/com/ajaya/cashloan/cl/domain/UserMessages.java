package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户短信表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-09-03 15:13:13
 */
 public class UserMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户标识
    */
    private Long userId;

    /**
    * 订单标识
    */
    private Long borrowId;

    /**
    * 短信收发人
    */
    private String name;

    /**
    * 手机号码
    */
    private String phone;

    /**
    * 收发时间
    */
    private Date time;

    /**
    * 收发标识，10发20收
    */
    private String type;

    /**
    * 短信内容
    */
    private String msg;

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
    * 获取用户标识
    *
    * @return 用户标识
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户标识
    * 
    * @param userId 要设置的用户标识
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取订单标识
    *
    * @return 订单标识
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置订单标识
    * 
    * @param borrowId 要设置的订单标识
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取短信收发人
    *
    * @return 短信收发人
    */
    public String getName(){
        return name;
    }

    /**
    * 设置短信收发人
    * 
    * @param name 要设置的短信收发人
    */
    public void setName(String name){
        this.name = name;
    }

    /**
    * 获取手机号码
    *
    * @return 手机号码
    */
    public String getPhone(){
        return phone;
    }

    /**
    * 设置手机号码
    * 
    * @param phone 要设置的手机号码
    */
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**
    * 获取收发时间
    *
    * @return 收发时间
    */
    public Date getTime(){
        return time;
    }

    /**
    * 设置收发时间
    * 
    * @param time 要设置的收发时间
    */
    public void setTime(Date time){
        this.time = time;
    }

    /**
    * 获取收发标识，10发20收
    *
    * @return 收发标识，10发20收
    */
    public String getType(){
        return type;
    }

    /**
    * 设置收发标识，10发20收
    * 
    * @param type 要设置的收发标识，10发20收
    */
    public void setType(String type){
        this.type = type;
    }

    /**
    * 获取短信内容
    *
    * @return 短信内容
    */
    public String getMsg(){
        return msg;
    }

    /**
    * 设置短信内容
    * 
    * @param msg 要设置的短信内容
    */
    public void setMsg(String msg){
        this.msg = msg;
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
    
    public String getTypeStr(){
    	if("10".equals(getType())){
    		return "OPTION.SEND";
    	} else {
    		return "OPTION.RECEIVE";
    	}
    }

}