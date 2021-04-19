package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:43:16
 */
 public class BankCard implements Serializable {

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
    * 绑定状态 1 绑定 2 未绑定
    */
    private String status;

    /**
    * bin码
    */
    private String bin;

    /**
    * 卡号后四位
    */
    private String last4;

    /**
    * 失效月份
    */
    private String expMonth;

    /**
    * 失效年
    */
    private String expYear;

    /**
    * 渠道名称
    */
    private String channel;

    /**
    * 卡类型
    */
    private String cardType;

    /**
    * 银行名称
    */
    private String bank;

    /**
    * 国家代码
    */
    private String countryCode;

    /**
    * 
    */
    private String brand;

    /**
    * 
    */
    private Boolean reusable;

    /**
    * 
    */
    private String signature;

    /**
    * 用户id
    */
    private String customerId;

    /**
    * 游戏
    */
    private String email;

    /**
    * 用户代码
    */
    private String customerCode;

    /**
    * 
    */
    private String riskAction;

    /**
    * 授权码
    */
    private String authorizationCode;

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
    * 获取bin码
    *
    * @return bin码
    */
    public String getBin(){
        return bin;
    }

    /**
    * 设置bin码
    * 
    * @param bin 要设置的bin码
    */
    public void setBin(String bin){
        this.bin = bin;
    }

    /**
    * 获取卡号后四位
    *
    * @return 卡号后四位
    */
    public String getLast4(){
        return last4;
    }

    /**
    * 设置卡号后四位
    * 
    * @param last4 要设置的卡号后四位
    */
    public void setLast4(String last4){
        this.last4 = last4;
    }

    /**
    * 获取失效月份
    *
    * @return 失效月份
    */
    public String getExpMonth(){
        return expMonth;
    }

    /**
    * 设置失效月份
    * 
    * @param expMonth 要设置的失效月份
    */
    public void setExpMonth(String expMonth){
        this.expMonth = expMonth;
    }

    /**
    * 获取失效年
    *
    * @return 失效年
    */
    public String getExpYear(){
        return expYear;
    }

    /**
    * 设置失效年
    * 
    * @param expYear 要设置的失效年
    */
    public void setExpYear(String expYear){
        this.expYear = expYear;
    }

    /**
    * 获取渠道名称
    *
    * @return 渠道名称
    */
    public String getChannel(){
        return channel;
    }

    /**
    * 设置渠道名称
    * 
    * @param channel 要设置的渠道名称
    */
    public void setChannel(String channel){
        this.channel = channel;
    }

    /**
    * 获取卡类型
    *
    * @return 卡类型
    */
    public String getCardType(){
        return cardType;
    }

    /**
    * 设置卡类型
    * 
    * @param cardType 要设置的卡类型
    */
    public void setCardType(String cardType){
        this.cardType = cardType;
    }

    /**
    * 获取银行名称
    *
    * @return 银行名称
    */
    public String getBank(){
        return bank;
    }

    /**
    * 设置银行名称
    * 
    * @param bank 要设置的银行名称
    */
    public void setBank(String bank){
        this.bank = bank;
    }

    /**
    * 获取国家代码
    *
    * @return 国家代码
    */
    public String getCountryCode(){
        return countryCode;
    }

    /**
    * 设置国家代码
    * 
    * @param countryCode 要设置的国家代码
    */
    public void setCountryCode(String countryCode){
        this.countryCode = countryCode;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getBrand(){
        return brand;
    }

    /**
    * 设置
    * 
    * @param brand 要设置的
    */
    public void setBrand(String brand){
        this.brand = brand;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Boolean getReusable(){
        return reusable;
    }

    /**
    * 设置
    * 
    * @param reusable 要设置的
    */
    public void setReusable(Boolean reusable){
        this.reusable = reusable;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getSignature(){
        return signature;
    }

    /**
    * 设置
    * 
    * @param signature 要设置的
    */
    public void setSignature(String signature){
        this.signature = signature;
    }

    /**
    * 获取用户id
    *
    * @return 用户id
    */
    public String getCustomerId(){
        return customerId;
    }

    /**
    * 设置用户id
    * 
    * @param customerId 要设置的用户id
    */
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    /**
    * 获取游戏
    *
    * @return 游戏
    */
    public String getEmail(){
        return email;
    }

    /**
    * 设置游戏
    * 
    * @param email 要设置的游戏
    */
    public void setEmail(String email){
        this.email = email;
    }

    /**
    * 获取用户代码
    *
    * @return 用户代码
    */
    public String getCustomerCode(){
        return customerCode;
    }

    /**
    * 设置用户代码
    * 
    * @param customerCode 要设置的用户代码
    */
    public void setCustomerCode(String customerCode){
        this.customerCode = customerCode;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getRiskAction(){
        return riskAction;
    }

    /**
    * 设置
    * 
    * @param riskAction 要设置的
    */
    public void setRiskAction(String riskAction){
        this.riskAction = riskAction;
    }

    /**
    * 获取授权码
    *
    * @return 授权码
    */
    public String getAuthorizationCode(){
        return authorizationCode;
    }

    /**
    * 设置授权码
    * 
    * @param authorizationCode 要设置的授权码
    */
    public void setAuthorizationCode(String authorizationCode){
        this.authorizationCode = authorizationCode;
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