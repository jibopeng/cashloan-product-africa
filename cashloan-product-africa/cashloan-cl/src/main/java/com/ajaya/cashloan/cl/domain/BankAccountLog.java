package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * '银行账户日志实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-20 18:15:45
 */
 public class BankAccountLog implements Serializable {

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
    * 响应状态
    */
    private String status;

    /**
    * 响应描述
    */
    private String message;

    /**
    * 银行卡是否为绑定状态 1绑定  2未绑定
    */
    private String param;

    /**
    * 响应结果
    */
    private String result;

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
    * 获取响应状态
    *
    * @return 响应状态
    */
    public String getStatus(){
        return status;
    }

    /**
    * 设置响应状态
    * 
    * @param status 要设置的响应状态
    */
    public void setStatus(String status){
        this.status = status;
    }

    /**
    * 获取响应描述
    *
    * @return 响应描述
    */
    public String getMessage(){
        return message;
    }

    /**
    * 设置响应描述
    * 
    * @param message 要设置的响应描述
    */
    public void setMessage(String message){
        this.message = message;
    }

    /**
    * 获取银行卡是否为绑定状态 1绑定  2未绑定
    *
    * @return 银行卡是否为绑定状态 1绑定  2未绑定
    */
    public String getParam(){
        return param;
    }

    /**
    * 设置银行卡是否为绑定状态 1绑定  2未绑定
    * 
    * @param param 要设置的银行卡是否为绑定状态 1绑定  2未绑定
    */
    public void setParam(String param){
        this.param = param;
    }

    /**
    * 获取响应结果
    *
    * @return 响应结果
    */
    public String getResult(){
        return result;
    }

    /**
    * 设置响应结果
    * 
    * @param result 要设置的响应结果
    */
    public void setResult(String result){
        this.result = result;
    }

    /**
    * 获取创建时间
    *
    * @return 创建时间
    */
    public Date getCreateTime() {
        return createTime;
    }

    /**
    * 设置创建时间
    * 
    * @param createTime 要设置的创建时间
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}