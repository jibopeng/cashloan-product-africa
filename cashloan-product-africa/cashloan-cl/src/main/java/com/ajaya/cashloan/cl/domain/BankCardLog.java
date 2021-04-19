package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:46:04
 */
 public class BankCardLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 首页触发认证，则会记录borrow id 
    */
    private Long borrowId;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 绑定状态 1 绑定 2 未绑定
    */
    private String status;

    /**
    * 唯一标识
    */
    private String reference;

    /**
    * 授权码
    */
    private String accessCode;

    /**
    * 授权url
    */
    private String authorizationUrl;

    /**
    * 1 首页触发，2个人中心触发
    */
    private String type;

    /**
    * webhook响应数据
    */
    private String webhookData;

    /**
    * 查询数据
    */
    private String data;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * ‌更新时间
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
    * 获取首页触发认证，则会记录borrow id 
    *
    * @return 首页触发认证，则会记录borrow id 
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置首页触发认证，则会记录borrow id 
    * 
    * @param borrowId 要设置的首页触发认证，则会记录borrow id 
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
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
    * 获取绑定状态 1 绑定 2 未绑定
    *
    * @return 绑定状态 1 绑定 2 未绑定
    */
    public String getStatus(){
        return status;
    }

    /**
    * 设置绑定状态 1 绑定 2 未绑定
    * 
    * @param status 要设置的绑定状态 1 绑定 2 未绑定
    */
    public void setStatus(String status){
        this.status = status;
    }

    /**
    * 获取唯一标识
    *
    * @return 唯一标识
    */
    public String getReference(){
        return reference;
    }

    /**
    * 设置唯一标识
    * 
    * @param reference 要设置的唯一标识
    */
    public void setReference(String reference){
        this.reference = reference;
    }

    /**
    * 获取授权码
    *
    * @return 授权码
    */
    public String getAccessCode(){
        return accessCode;
    }

    /**
    * 设置授权码
    * 
    * @param accessCode 要设置的授权码
    */
    public void setAccessCode(String accessCode){
        this.accessCode = accessCode;
    }

    /**
    * 获取授权url
    *
    * @return 授权url
    */
    public String getAuthorizationUrl(){
        return authorizationUrl;
    }

    /**
    * 设置授权url
    * 
    * @param authorizationUrl 要设置的授权url
    */
    public void setAuthorizationUrl(String authorizationUrl){
        this.authorizationUrl = authorizationUrl;
    }

    /**
    * 获取1 首页触发，2个人中心触发
    *
    * @return 1 首页触发，2个人中心触发
    */
    public String getType(){
        return type;
    }

    /**
    * 设置1 首页触发，2个人中心触发
    * 
    * @param type 要设置的1 首页触发，2个人中心触发
    */
    public void setType(String type){
        this.type = type;
    }

    /**
    * 获取webhook响应数据
    *
    * @return webhook响应数据
    */
    public String getWebhookData(){
        return webhookData;
    }

    /**
    * 设置webhook响应数据
    * 
    * @param webhookData 要设置的webhook响应数据
    */
    public void setWebhookData(String webhookData){
        this.webhookData = webhookData;
    }

    /**
    * 获取查询数据
    *
    * @return 查询数据
    */
    public String getData(){
        return data;
    }

    /**
    * 设置查询数据
    * 
    * @param data 要设置的查询数据
    */
    public void setData(String data){
        this.data = data;
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
    * 获取‌更新时间
    *
    * @return ‌更新时间
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置‌更新时间
    * 
    * @param updateTime 要设置的‌更新时间
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}