package com.ajaya.cashloan.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * '产品信息配置表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 10:50:37
 */
 public class BorrowProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 产品名称
    */
    private String name;

    /**
    * 借款额度
    */
    private Double quota;

    /**
    * 期限
    */
    private Integer term;

    /**
    * 期限单位： 1：天，2：月
    */
    private Integer termUnit;

    /**
    * 商品状态：0：未上线，1：上线
    */
    private Boolean status;

    /**
    * 利率
    */
    private Double rate;

    /**
    * 是否启用砍头息：0：否，1：是
    */
    private Boolean beheadFee;

    /**
    * 费率
    */
    private Double fee;

    /**
    * 砍头金额
    */
    private Double beheadMoney;

    /**
    * 客群
    */
    private String userType;

    /**
    * 格式说明：借款天数-对应逾期利率 ,多个用逗号隔开
    */
    private String penaltyFee;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
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
    * 获取产品名称
    *
    * @return 产品名称
    */
    public String getName(){
        return name;
    }

    /**
    * 设置产品名称
    * 
    * @param name 要设置的产品名称
    */
    public void setName(String name){
        this.name = name;
    }

    /**
    * 获取借款额度
    *
    * @return 借款额度
    */
    public Double getQuota(){
        return quota;
    }

    /**
    * 设置借款额度
    * 
    * @param quota 要设置的借款额度
    */
    public void setQuota(Double quota){
        this.quota = quota;
    }

    /**
    * 获取期限
    *
    * @return 期限
    */
    public Integer getTerm(){
        return term;
    }

    /**
    * 设置期限
    * 
    * @param term 要设置的期限
    */
    public void setTerm(Integer term){
        this.term = term;
    }

    /**
    * 获取期限单位： 1：天，2：月
    *
    * @return 期限单位： 1：天，2：月
    */
    public Integer getTermUnit(){
        return termUnit;
    }

    /**
    * 设置期限单位： 1：天，2：月
    * 
    * @param termUnit 要设置的期限单位： 1：天，2：月
    */
    public void setTermUnit(Integer termUnit){
        this.termUnit = termUnit;
    }

    /**
    * 获取商品状态：0：未上线，1：上线
    *
    * @return 商品状态：0：未上线，1：上线
    */
    public Boolean getStatus(){
        return status;
    }

    /**
    * 设置商品状态：0：未上线，1：上线
    * 
    * @param status 要设置的商品状态：0：未上线，1：上线
    */
    public void setStatus(Boolean status){
        this.status = status;
    }

    /**
    * 获取利率
    *
    * @return 利率
    */
    public Double getRate(){
        return rate;
    }

    /**
    * 设置利率
    * 
    * @param rate 要设置的利率
    */
    public void setRate(Double rate){
        this.rate = rate;
    }

    /**
    * 获取是否启用砍头息：0：否，1：是
    *
    * @return 是否启用砍头息：0：否，1：是
    */
    public Boolean getBeheadFee(){
        return beheadFee;
    }

    /**
    * 设置是否启用砍头息：0：否，1：是
    * 
    * @param beheadFee 要设置的是否启用砍头息：0：否，1：是
    */
    public void setBeheadFee(Boolean beheadFee){
        this.beheadFee = beheadFee;
    }

    /**
    * 获取费率
    *
    * @return 费率
    */
    public Double getFee(){
        return fee;
    }

    /**
    * 设置费率
    * 
    * @param fee 要设置的费率
    */
    public void setFee(Double fee){
        this.fee = fee;
    }

    /**
    * 获取砍头金额
    *
    * @return 砍头金额
    */
    public Double getBeheadMoney(){
        return beheadMoney;
    }

    /**
    * 设置砍头金额
    * 
    * @param beheadMoney 要设置的砍头金额
    */
    public void setBeheadMoney(Double beheadMoney){
        this.beheadMoney = beheadMoney;
    }

    /**
    * 获取客群
    *
    * @return 客群
    */
    public String getUserType(){
        return userType;
    }

    /**
    * 设置客群
    * 
    * @param userType 要设置的客群
    */
    public void setUserType(String userType){
        this.userType = userType;
    }

    /**
    * 获取格式说明：借款天数-对应逾期利率 ,多个用逗号隔开
    *
    * @return 格式说明：借款天数-对应逾期利率 ,多个用逗号隔开
    */
    public String getPenaltyFee(){
        return penaltyFee;
    }

    /**
    * 设置格式说明：借款天数-对应逾期利率 ,多个用逗号隔开
    * 
    * @param penaltyFee 要设置的格式说明：借款天数-对应逾期利率 ,多个用逗号隔开
    */
    public void setPenaltyFee(String penaltyFee){
        this.penaltyFee = penaltyFee;
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
    * 获取修改时间
    *
    * @return 修改时间
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置修改时间
    * 
    * @param updateTime 要设置的修改时间
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}