package com.ajaya.cashloan.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 登陆记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-04-11 16:12:19
 */
 public class SysUserLoginLog implements Serializable {

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
    * 登陆姓名
    */
    private String userName;

    /**
    * 登陆时间
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
    * 获取登陆姓名
    *
    * @return 登陆姓名
    */
    public String getUserName(){
        return userName;
    }

    /**
    * 设置登陆姓名
    * 
    * @param userName 要设置的登陆姓名
    */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
    * 获取登陆时间
    *
    * @return 登陆时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置登陆时间
    * 
    * @param createTime 要设置的登陆时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

}