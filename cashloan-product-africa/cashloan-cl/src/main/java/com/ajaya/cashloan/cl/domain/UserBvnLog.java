package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * '用户bvn日志表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-15 17:29:07
 */
 public class UserBvnLog implements Serializable {

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
    * 调用状态
    */
    private String status;

    /**
    * 
    */
    private String message;

    /**
    * 
    */
    private String param;

    /**
    * 
    */
    private String responseParam;

    /**
    * 创建时间
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
    * 获取
    *
    * @return 
    */
    public String getParam(){
        return param;
    }

    /**
    * 设置
    * 
    * @param param 要设置的
    */
    public void setParam(String param){
        this.param = param;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getResponseParam(){
        return responseParam;
    }

    /**
    * 设置
    * 
    * @param responseParam 要设置的
    */
    public void setResponseParam(String responseParam){
        this.responseParam = responseParam;
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

}