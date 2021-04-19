package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户语音验证日志表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-02 16:55:42
 */
 public class UserVoiceSmsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 接收方法 callback  event 两种
    */
    private String type;

    /**
    * 接电话的电话号码
    */
    private String callerNumber;

    /**
    * 电话会话状态
    */
    private String callSessionState;

    /**
    * 会话开始时间
    */
    private String callStartTime;

    /**
    * 会话id
    */
    private String sessionId;

    /**
    * 会话所在国家代码
    */
    private String callerCountryCode;

    /**
    * 是否激活
    */
    private String isActive;

    /**
    * 
    */
    private String callerCarrierName;

    /**
    * 
    */
    private String direction;

    /**
    * 
    */
    private String destinationNumber;

    /**
    * 通话费用
    */
    private String amount;

    /**
    * 通话时长
    */
    private String durationInSeconds;

    /**
    * 国家代码
    */
    private String currencyCode;

    /**
    * 状态
    */
    private String status;

    /**
    * 挂断原因
    */
    private String hangupCause;

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
    * 获取接收方法 callback  event 两种
    *
    * @return 接收方法 callback  event 两种
    */
    public String getType(){
        return type;
    }

    /**
    * 设置接收方法 callback  event 两种
    * 
    * @param type 要设置的接收方法 callback  event 两种
    */
    public void setType(String type){
        this.type = type;
    }

    /**
    * 获取接电话的电话号码
    *
    * @return 接电话的电话号码
    */
    public String getCallerNumber(){
        return callerNumber;
    }

    /**
    * 设置接电话的电话号码
    * 
    * @param callerNumber 要设置的接电话的电话号码
    */
    public void setCallerNumber(String callerNumber){
        this.callerNumber = callerNumber;
    }

    /**
    * 获取电话会话状态
    *
    * @return 电话会话状态
    */
    public String getCallSessionState(){
        return callSessionState;
    }

    /**
    * 设置电话会话状态
    * 
    * @param callSessionState 要设置的电话会话状态
    */
    public void setCallSessionState(String callSessionState){
        this.callSessionState = callSessionState;
    }

    /**
    * 获取会话开始时间
    *
    * @return 会话开始时间
    */
    public String getCallStartTime(){
        return callStartTime;
    }

    /**
    * 设置会话开始时间
    * 
    * @param callStartTime 要设置的会话开始时间
    */
    public void setCallStartTime(String callStartTime){
        this.callStartTime = callStartTime;
    }

    /**
    * 获取会话id
    *
    * @return 会话id
    */
    public String getSessionId(){
        return sessionId;
    }

    /**
    * 设置会话id
    * 
    * @param sessionId 要设置的会话id
    */
    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }

    /**
    * 获取会话所在国家代码
    *
    * @return 会话所在国家代码
    */
    public String getCallerCountryCode(){
        return callerCountryCode;
    }

    /**
    * 设置会话所在国家代码
    * 
    * @param callerCountryCode 要设置的会话所在国家代码
    */
    public void setCallerCountryCode(String callerCountryCode){
        this.callerCountryCode = callerCountryCode;
    }

    /**
    * 获取是否激活
    *
    * @return 是否激活
    */
    public String getIsActive(){
        return isActive;
    }

    /**
    * 设置是否激活
    * 
    * @param isActive 要设置的是否激活
    */
    public void setIsActive(String isActive){
        this.isActive = isActive;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getCallerCarrierName(){
        return callerCarrierName;
    }

    /**
    * 设置
    * 
    * @param callerCarrierName 要设置的
    */
    public void setCallerCarrierName(String callerCarrierName){
        this.callerCarrierName = callerCarrierName;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getDirection(){
        return direction;
    }

    /**
    * 设置
    * 
    * @param direction 要设置的
    */
    public void setDirection(String direction){
        this.direction = direction;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getDestinationNumber(){
        return destinationNumber;
    }

    /**
    * 设置
    * 
    * @param destinationNumber 要设置的
    */
    public void setDestinationNumber(String destinationNumber){
        this.destinationNumber = destinationNumber;
    }

    /**
    * 获取通话费用
    *
    * @return 通话费用
    */
    public String getAmount(){
        return amount;
    }

    /**
    * 设置通话费用
    * 
    * @param amount 要设置的通话费用
    */
    public void setAmount(String amount){
        this.amount = amount;
    }

    /**
    * 获取通话时长
    *
    * @return 通话时长
    */
    public String getDurationInSeconds(){
        return durationInSeconds;
    }

    /**
    * 设置通话时长
    * 
    * @param durationInSeconds 要设置的通话时长
    */
    public void setDurationInSeconds(String durationInSeconds){
        this.durationInSeconds = durationInSeconds;
    }

    /**
    * 获取国家代码
    *
    * @return 国家代码
    */
    public String getCurrencyCode(){
        return currencyCode;
    }

    /**
    * 设置国家代码
    * 
    * @param currencyCode 要设置的国家代码
    */
    public void setCurrencyCode(String currencyCode){
        this.currencyCode = currencyCode;
    }

    /**
    * 获取状态
    *
    * @return 状态
    */
    public String getStatus(){
        return status;
    }

    /**
    * 设置状态
    * 
    * @param status 要设置的状态
    */
    public void setStatus(String status){
        this.status = status;
    }

    /**
    * 获取挂断原因
    *
    * @return 挂断原因
    */
    public String getHangupCause(){
        return hangupCause;
    }

    /**
    * 设置挂断原因
    * 
    * @param hangupCause 要设置的挂断原因
    */
    public void setHangupCause(String hangupCause){
        this.hangupCause = hangupCause;
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