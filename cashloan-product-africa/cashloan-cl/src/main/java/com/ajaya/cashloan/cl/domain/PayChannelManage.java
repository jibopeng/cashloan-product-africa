package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付渠道管理表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-16 18:05:43
 */
 public class PayChannelManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 放款账户
    */
    private String razpayAccount;

    /**
    * 
    */
    private String razpayId;

    /**
    * 
    */
    private String razpayKey;

    /**
    * 支付渠道名称
    */
    private String channelName;

    /**
    * 渠道code
    */
    private String chennelCode;

    /**
    * 优先级
    */
    private String level;

    /**
    * 放款数量
    */
    private Integer limit;

    /**
    * 公司名称(协议)
    */
    private String companyName;

    /**
    * 公司地址(协议)
    */
    private String companyAddr;

    /**
    * 状态(1：表示可用，0：表示弃用)
    */
    private String state;


    /**
     * nbfc相关信息(协议)
     */
    private String protocolInformation;

    public String getProtocolInformation() {
        return protocolInformation;
    }

    public void setProtocolInformation(String protocolInformation) {
        this.protocolInformation = protocolInformation;
    }

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
    * 获取放款账户
    *
    * @return 放款账户
    */
    public String getRazpayAccount(){
        return razpayAccount;
    }

    /**
    * 设置放款账户
    * 
    * @param razpayAccount 要设置的放款账户
    */
    public void setRazpayAccount(String razpayAccount){
        this.razpayAccount = razpayAccount;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getRazpayId(){
        return razpayId;
    }

    /**
    * 设置
    * 
    * @param razpayId 要设置的
    */
    public void setRazpayId(String razpayId){
        this.razpayId = razpayId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getRazpayKey(){
        return razpayKey;
    }

    /**
    * 设置
    * 
    * @param razpayKey 要设置的
    */
    public void setRazpayKey(String razpayKey){
        this.razpayKey = razpayKey;
    }

    /**
    * 获取支付渠道名称
    *
    * @return 支付渠道名称
    */
    public String getChannelName(){
        return channelName;
    }

    /**
    * 设置支付渠道名称
    * 
    * @param channelName 要设置的支付渠道名称
    */
    public void setChannelName(String channelName){
        this.channelName = channelName;
    }

    /**
    * 获取渠道code
    *
    * @return 渠道code
    */
    public String getChennelCode(){
        return chennelCode;
    }

    /**
    * 设置渠道code
    * 
    * @param chennelCode 要设置的渠道code
    */
    public void setChennelCode(String chennelCode){
        this.chennelCode = chennelCode;
    }

    /**
    * 获取优先级
    *
    * @return 优先级
    */
    public String getLevel(){
        return level;
    }

    /**
    * 设置优先级
    * 
    * @param level 要设置的优先级
    */
    public void setLevel(String level){
        this.level = level;
    }

    /**
    * 获取放款数量
    *
    * @return 放款数量
    */
    public Integer getLimit(){
        return limit;
    }

    /**
    * 设置放款数量
    * 
    * @param limit 要设置的放款数量
    */
    public void setLimit(Integer limit){
        this.limit = limit;
    }

    /**
    * 获取公司名称(协议)
    *
    * @return 公司名称(协议)
    */
    public String getCompanyName(){
        return companyName;
    }

    /**
    * 设置公司名称(协议)
    * 
    * @param companyName 要设置的公司名称(协议)
    */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
    * 获取公司地址(协议)
    *
    * @return 公司地址(协议)
    */
    public String getCompanyAddr(){
        return companyAddr;
    }

    /**
    * 设置公司地址(协议)
    * 
    * @param companyAddr 要设置的公司地址(协议)
    */
    public void setCompanyAddr(String companyAddr){
        this.companyAddr = companyAddr;
    }

    /**
    * 获取状态(1：表示可用，0：表示弃用)
    *
    * @return 状态(1：表示可用，0：表示弃用)
    */
    public String getState(){
        return state;
    }

    /**
    * 设置状态(1：表示可用，0：表示弃用)
    * 
    * @param state 要设置的状态(1：表示可用，0：表示弃用)
    */
    public void setState(String state){
        this.state = state;
    }

}