package com.ajaya.cashloan.rc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 风控数据统计-（简）通话记录统计实体
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:53:59

 */
 public class Simplezx91Count implements Serializable {

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
    * 借款成功次数
    */
    private Integer borrowCount;

    /**
    * 逾期金额
    */
    private Double overdueAmt;

    /**
    * 借款区间（低）单位：万
    */
    private Double amtMin;

    /**
    * 借款区间（低）单位：万
    */
    private Double amtMax;

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
    * 获取借款成功次数
    *
    * @return 借款成功次数
    */
    public Integer getBorrowCount(){
        return borrowCount;
    }

    /**
    * 设置借款成功次数
    * 
    * @param borrowCount 要设置的借款成功次数
    */
    public void setBorrowCount(Integer borrowCount){
        this.borrowCount = borrowCount;
    }

    /**
    * 获取逾期金额
    *
    * @return 逾期金额
    */
    public Double getOverdueAmt(){
        return overdueAmt;
    }

    /**
    * 设置逾期金额
    * 
    * @param overdueAmt 要设置的逾期金额
    */
    public void setOverdueAmt(Double overdueAmt){
        this.overdueAmt = overdueAmt;
    }

    /**
    * 获取借款区间（低）单位：万
    *
    * @return 借款区间（低）单位：万
    */
    public Double getAmtMin(){
        return amtMin;
    }

    /**
    * 设置借款区间（低）单位：万
    * 
    * @param amtMin 要设置的借款区间（低）单位：万
    */
    public void setAmtMin(Double amtMin){
        this.amtMin = amtMin;
    }

    /**
    * 获取借款区间（低）单位：万
    *
    * @return 借款区间（低）单位：万
    */
    public Double getAmtMax(){
        return amtMax;
    }

    /**
    * 设置借款区间（低）单位：万
    * 
    * @param amtMax 要设置的借款区间（低）单位：万
    */
    public void setAmtMax(Double amtMax){
        this.amtMax = amtMax;
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