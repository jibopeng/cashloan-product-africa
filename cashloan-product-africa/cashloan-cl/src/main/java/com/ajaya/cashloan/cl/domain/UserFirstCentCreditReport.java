package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 'FC用户征信报告表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 15:20:15
 */
 public class UserFirstCentCreditReport implements Serializable {

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
     * 用户的bvn账户
     */
    private String bvn;

    /**
    * 信用报告
    */
    private String creditReport;

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
    * 获取信用报告
    *
    * @return 信用报告
    */
    public String getCreditReport(){
        return creditReport;
    }

    /**
    * 设置信用报告
    * 
    * @param creditReport 要设置的信用报告
    */
    public void setCreditReport(String creditReport){
        this.creditReport = creditReport;
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