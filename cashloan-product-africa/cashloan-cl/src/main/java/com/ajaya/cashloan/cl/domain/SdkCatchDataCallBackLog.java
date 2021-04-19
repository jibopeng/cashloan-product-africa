package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * epoch抓取数据回调日志表实体
 * 
 * @author yzq
 * @version 1.0.0
 * @date 2020-01-08 18:50:33
 */
 public class SdkCatchDataCallBackLog implements Serializable {

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
    * 订单id
    */
    private Long borrowId;

    /**
    * 响应状态 1 成功
    */
    private String catchDataState;

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
    * 获取订单id
    *
    * @return 订单id
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置订单id
    * 
    * @param borrowId 要设置的订单id
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取响应状态 1 成功
    *
    * @return 响应状态 1 成功
    */
    public String getCatchDataState(){
        return catchDataState;
    }

    /**
    * 设置响应状态 1 成功
    * 
    * @param catchDataState 要设置的响应状态 1 成功
    */
    public void setCatchDataState(String catchDataState){
        this.catchDataState = catchDataState;
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