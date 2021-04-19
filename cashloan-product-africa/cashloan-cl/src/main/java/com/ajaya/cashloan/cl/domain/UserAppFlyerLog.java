package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * AF统计记表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-01 10:56:25
 */
 public class UserAppFlyerLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private String customerUserId;

    /**
    * 
    */
    private String eventName;

    /**
    * 响应参数
    */
    private String responseJson;

    /**
    * 
    */
    private String eventTime;

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

    public String getCustomerUserId() {
		return customerUserId;
	}

	public void setCustomerUserId(String customerUserId) {
		this.customerUserId = customerUserId;
	}

	/**
    * 获取
    *
    * @return 
    */
    public String getEventName(){
        return eventName;
    }

    /**
    * 设置
    * 
    * @param eventName 要设置的
    */
    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    /**
    * 获取响应参数
    *
    * @return 响应参数
    */
    public String getResponseJson(){
        return responseJson;
    }

    /**
    * 设置响应参数
    * 
    * @param responseJson 要设置的响应参数
    */
    public void setResponseJson(String responseJson){
        this.responseJson = responseJson;
    }

    public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
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