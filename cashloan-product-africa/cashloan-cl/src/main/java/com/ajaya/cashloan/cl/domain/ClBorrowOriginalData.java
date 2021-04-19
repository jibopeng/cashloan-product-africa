package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * '订单原始数据记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-21 14:42:23
 */
 public class ClBorrowOriginalData implements Serializable {

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
     * 类型
     */
    private String type;

    /**
     * 原始数据
     */
    private String originalData;
    
    /**
    * 
    */
    private Date createTime;

    /**
    * 
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOriginalData() {
		return originalData;
	}

	public void setOriginalData(String originalData) {
		this.originalData = originalData;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}