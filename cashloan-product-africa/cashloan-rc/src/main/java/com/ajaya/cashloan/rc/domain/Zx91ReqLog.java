package com.ajaya.cashloan.rc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 风控数据统计-（简）通话记录统计实体
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:53:19

 */
 public class Zx91ReqLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户标识
    */
    private Long userId;

    /**
    * 请求订单号
    */
    private String reqOrder;

    /**
    * 请求参数
    */
    private String reqParams;

    /**
    * 添加时间
    */
    private Date createTime;

    /**
    * 回调返回码
    */
    private String respCode;

    /**
    * 同步响应结果
    */
    private String respParams;

    /**
    * 同步响应时间
    */
    private Date respTime;

    /**
    * 同步响应订单号
    */
    private String respOrderNo;


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
    * 获取用户标识
    *
    * @return 用户标识
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户标识
    * 
    * @param userId 要设置的用户标识
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取请求订单号
    *
    * @return 请求订单号
    */
    public String getReqOrder(){
        return reqOrder;
    }

    /**
    * 设置请求订单号
    * 
    * @param reqOrder 要设置的请求订单号
    */
    public void setReqOrder(String reqOrder){
        this.reqOrder = reqOrder;
    }

    /**
    * 获取请求参数
    *
    * @return 请求参数
    */
    public String getReqParams(){
        return reqParams;
    }

    /**
    * 设置请求参数
    * 
    * @param reqParams 要设置的请求参数
    */
    public void setReqParams(String reqParams){
        this.reqParams = reqParams;
    }

    /**
    * 获取添加时间
    *
    * @return 添加时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置添加时间
    * 
    * @param createTime 要设置的添加时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取回调返回码
    *
    * @return 回调返回码
    */
    public String getRespCode(){
        return respCode;
    }

    /**
    * 设置回调返回码
    * 
    * @param respCode 要设置的回调返回码
    */
    public void setRespCode(String respCode){
        this.respCode = respCode;
    }

    /**
    * 获取同步响应结果
    *
    * @return 同步响应结果
    */
    public String getRespParams(){
        return respParams;
    }

    /**
    * 设置同步响应结果
    * 
    * @param respParams 要设置的同步响应结果
    */
    public void setRespParams(String respParams){
        this.respParams = respParams;
    }

    /**
    * 获取同步响应时间
    *
    * @return 同步响应时间
    */
    public Date getRespTime(){
        return respTime;
    }

    /**
    * 设置同步响应时间
    * 
    * @param respTime 要设置的同步响应时间
    */
    public void setRespTime(Date respTime){
        this.respTime = respTime;
    }

    /**
    * 获取同步响应订单号
    *
    * @return 同步响应订单号
    */
    public String getRespOrderNo(){
        return respOrderNo;
    }

    /**
    * 设置同步响应订单号
    * 
    * @param respOrderNo 要设置的同步响应订单号
    */
    public void setRespOrderNo(String respOrderNo){
        this.respOrderNo = respOrderNo;
    }

}