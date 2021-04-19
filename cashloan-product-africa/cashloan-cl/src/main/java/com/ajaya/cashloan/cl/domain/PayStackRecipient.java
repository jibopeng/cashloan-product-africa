package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * PayStack放款联系人实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-28 17:15:31
 */
 public class PayStackRecipient implements Serializable {

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
    * 银行账户
    */
    private String accountNumber;
    /**
    * 银行账户
    */
    private String authorizationCode;

    /**
    * 账户名称
    */
    private String accountName;

    /**
    * 银行code
    */
    private String bankCode;

    /**
    * 银行名称
    */
    private String bankName;

    /**
    * 是否活跃
    */
    private Boolean active;

    /**
    * 创建时间
    */
    private String createdAt;

    /**
    * 国家标识
    */
    private String currency;

    /**
    * 
    */
    private String domain;

    /**
    * 
    */
    private String integration;

    /**
    * 账号名
    */
    private String name;

    /**
    * recipient  code
    */
    private String recipientCode;

    /**
    * recipient  id
    */
    private String recipientId;

    /**
    * 
    */
    private String type;

    /**
    * 
    */
    private String updatedAt;

    /**
    * 是否删除
    */
    private Boolean isDeleted;

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
    * 获取银行账户
    *
    * @return 银行账户
    */
    public String getAccountNumber(){
        return accountNumber;
    }

    /**
    * 设置银行账户
    * 
    * @param accountNumber 要设置的银行账户
    */
    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    /**
    * 获取账户名称
    *
    * @return 账户名称
    */
    public String getAccountName(){
        return accountName;
    }

    /**
    * 设置账户名称
    * 
    * @param accountName 要设置的账户名称
    */
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    /**
    * 获取银行code
    *
    * @return 银行code
    */
    public String getBankCode(){
        return bankCode;
    }

    /**
    * 设置银行code
    * 
    * @param bankCode 要设置的银行code
    */
    public void setBankCode(String bankCode){
        this.bankCode = bankCode;
    }

    /**
    * 获取银行名称
    *
    * @return 银行名称
    */
    public String getBankName(){
        return bankName;
    }

    /**
    * 设置银行名称
    * 
    * @param bankName 要设置的银行名称
    */
    public void setBankName(String bankName){
        this.bankName = bankName;
    }

    /**
    * 获取是否活跃
    *
    * @return 是否活跃
    */
    public Boolean getActive(){
        return active;
    }

    /**
    * 设置是否活跃
    * 
    * @param active 要设置的是否活跃
    */
    public void setActive(Boolean active){
        this.active = active;
    }

    /**
    * 获取创建时间
    *
    * @return 创建时间
    */
    public String getCreatedAt(){
        return createdAt;
    }

    /**
    * 设置创建时间
    * 
    * @param createdAt 要设置的创建时间
    */
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    /**
    * 获取国家标识
    *
    * @return 国家标识
    */
    public String getCurrency(){
        return currency;
    }

    /**
    * 设置国家标识
    * 
    * @param currency 要设置的国家标识
    */
    public void setCurrency(String currency){
        this.currency = currency;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getDomain(){
        return domain;
    }

    /**
    * 设置
    * 
    * @param domain 要设置的
    */
    public void setDomain(String domain){
        this.domain = domain;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getIntegration(){
        return integration;
    }

    /**
    * 设置
    * 
    * @param integration 要设置的
    */
    public void setIntegration(String integration){
        this.integration = integration;
    }

    /**
    * 获取账号名
    *
    * @return 账号名
    */
    public String getName(){
        return name;
    }

    /**
    * 设置账号名
    * 
    * @param name 要设置的账号名
    */
    public void setName(String name){
        this.name = name;
    }

    /**
    * 获取recipient  code
    *
    * @return recipient  code
    */
    public String getRecipientCode(){
        return recipientCode;
    }

    /**
    * 设置recipient  code
    * 
    * @param recipientCode 要设置的recipient  code
    */
    public void setRecipientCode(String recipientCode){
        this.recipientCode = recipientCode;
    }

    /**
    * 获取recipient  id
    *
    * @return recipient  id
    */
    public String getRecipientId(){
        return recipientId;
    }

    /**
    * 设置recipient  id
    * 
    * @param recipientId 要设置的recipient  id
    */
    public void setRecipientId(String recipientId){
        this.recipientId = recipientId;
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
    public String getUpdatedAt(){
        return updatedAt;
    }

    /**
    * 设置
    * 
    * @param updatedAt 要设置的
    */
    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    /**
    * 获取是否删除
    *
    * @return 是否删除
    */
    public Boolean getIsDeleted(){
        return isDeleted;
    }

    /**
    * 设置是否删除
    * 
    * @param isDeleted 要设置的是否删除
    */
    public void setIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
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

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}