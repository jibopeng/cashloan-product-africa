package com.ajaya.cashloan.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统渠道用户表实体
 */
 public class SysUserChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户标志
    */
    private Long userId;

    /**
    * 渠道标志
    */
    private Long channelId;

    /**
    * 10 ： 启用，20：禁止
    */
    private String state;


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
    * 获取用户标志
    *
    * @return 用户标志
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户标志
    * 
    * @param userId 要设置的用户标志
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取渠道标志
    *
    * @return 渠道标志
    */
    public Long getChannelId(){
        return channelId;
    }

    /**
    * 设置渠道标志
    * 
    * @param channelId 要设置的渠道标志
    */
    public void setChannelId(Long channelId){
        this.channelId = channelId;
    }

    /**
    * 获取10 ： 启用，20：禁止
    *
    * @return 10 ： 启用，20：禁止
    */
    public String getState(){
        return state;
    }

    /**
    * 设置10 ： 启用，20：禁止
    * 
    * @param state 要设置的10 ： 启用，20：禁止
    */
    public void setState(String state){
        this.state = state;
    }

}