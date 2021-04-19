package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * '用户登录版本记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-17 12:45:56
 */
 public class UserLoginVersionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 产品名称
    */
    private String productName;

    /**
    * 登录时间
    */
    private String loginTime;

    /**
    * 版本号
    */
    private String version;

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
    * 获取用户id
    *
    * @return 用户id
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户id
    * 
    * @param userId 要设置的用户id
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取产品名称
    *
    * @return 产品名称
    */
    public String getProductName(){
        return productName;
    }

    /**
    * 设置产品名称
    * 
    * @param productName 要设置的产品名称
    */
    public void setProductName(String productName){
        this.productName = productName;
    }

    /**
    * 获取登录时间
    *
    * @return 登录时间
    */
    public String getLoginTime(){
        return loginTime;
    }

    /**
    * 设置登录时间
    * 
    * @param loginTime 要设置的登录时间
    */
    public void setLoginTime(String loginTime){
        this.loginTime = loginTime;
    }

    /**
    * 获取版本号
    *
    * @return 版本号
    */
    public String getVersion(){
        return version;
    }

    /**
    * 设置版本号
    * 
    * @param version 要设置的版本号
    */
    public void setVersion(String version){
        this.version = version;
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