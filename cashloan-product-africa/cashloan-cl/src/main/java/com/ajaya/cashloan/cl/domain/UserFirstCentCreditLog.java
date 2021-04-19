package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 'FC征信报告调用记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 15:19:50
 */
 public class UserFirstCentCreditLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户主键
    */
    private Long userId;

    /**
    * 订单主键
    */
    private Long borrowId;
    
    /**
     * 用户bvn账户 
     */
    private String bvn;

    /**
    * 调用接口名称
    */
    private String type;

    /**
    * 请求参数
    */
    private String requestParam;

    /**
    * 响应参数
    */
    private String response;

    /**
    * 
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
    * 获取用户主键
    *
    * @return 用户主键
    */
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置用户主键
    * 
    * @param userId 要设置的用户主键
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取订单主键
    *
    * @return 订单主键
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置订单主键
    * 
    * @param borrowId 要设置的订单主键
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取调用接口名称
    *
    * @return 调用接口名称
    */
    public String getType(){
        return type;
    }

    /**
    * 设置调用接口名称
    * 
    * @param type 要设置的调用接口名称
    */
    public void setType(String type){
        this.type = type;
    }

    /**
    * 获取请求参数
    *
    * @return 请求参数
    */
    public String getRequestParam(){
        return requestParam;
    }

    /**
    * 设置请求参数
    * 
    * @param requestParam 要设置的请求参数
    */
    public void setRequestParam(String requestParam){
        this.requestParam = requestParam;
    }

    /**
    * 获取响应参数
    *
    * @return 响应参数
    */
    public String getResponse(){
        return response;
    }

    /**
    * 设置响应参数
    * 
    * @param response 要设置的响应参数
    */
    public void setResponse(String response){
        this.response = response;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置
    * 
    * @param createTime 要设置的
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

	public String getBvn() {
		return bvn;
	}

	public void setBvn(String bvn) {
		this.bvn = bvn;
	}
}