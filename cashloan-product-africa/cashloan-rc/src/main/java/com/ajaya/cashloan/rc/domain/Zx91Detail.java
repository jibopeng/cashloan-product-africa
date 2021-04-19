package com.ajaya.cashloan.rc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 风控数据统计-（简）通话记录统计实体
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:52:59

 */
 public class Zx91Detail implements Serializable {

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
    * 交易代码
    */
    private String trxNo;

    /**
    * 借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
    */
    private Integer borrowType;

    /**
    * 借款状态 0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3和6意义一样）
    */
    private Integer borrowState;

    /**
    * 合同金额 区间  单位：万
    */
    private Double borrowAmtMin;

    /**
    * 合同金额 区间  单位：万 -7.[0,0.1) -6.[0.1,0.2) -5.[0.2,0.3) -4.[0.3,0.4) -3.[0.4,0.6) -2.[0.6,0.8) -1.[0.8,1) 1.[1,2) 2.[2,4) 3.[4,6) 4.[6,8)…….(单位:万元)
    */
    private Double borrowAmtMax;

    /**
    * 合同日期
    */
    private Date contractDate;

    /**
    * 批贷期数
    */
    private Integer loanPeriod;

    /**
    * 还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+ 9.已还清
    */
    private Integer repayState;

    /**
    * 逾期金额 实际金额*100000 取整
    */
    private Double arrearsAmount;

    /**
    * 公司代码
    */
    private String companyCode;


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
    * 获取交易代码
    *
    * @return 交易代码
    */
    public String getTrxNo(){
        return trxNo;
    }

    /**
    * 设置交易代码
    * 
    * @param trxNo 要设置的交易代码
    */
    public void setTrxNo(String trxNo){
        this.trxNo = trxNo;
    }

    /**
    * 获取借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
    *
    * @return 借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
    */
    public Integer getBorrowType(){
        return borrowType;
    }

    /**
    * 设置借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
    * 
    * @param borrowType 要设置的借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
    */
    public void setBorrowType(Integer borrowType){
        this.borrowType = borrowType;
    }

    /**
    * 获取借款状态 0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3和6意义一样）
    *
    * @return 借款状态 0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3和6意义一样）
    */
    public Integer getBorrowState(){
        return borrowState;
    }

    /**
    * 设置借款状态 0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3和6意义一样）
    * 
    * @param borrowState 要设置的借款状态 0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3和6意义一样）
    */
    public void setBorrowState(Integer borrowState){
        this.borrowState = borrowState;
    }

    /**
    * 获取合同金额 区间  单位：万
    *
    * @return 合同金额 区间  单位：万
    */
    public Double getBorrowAmtMin(){
        return borrowAmtMin;
    }

    /**
    * 设置合同金额 区间  单位：万
    * 
    * @param borrowAmtMin 要设置的合同金额 区间  单位：万
    */
    public void setBorrowAmtMin(Double borrowAmtMin){
        this.borrowAmtMin = borrowAmtMin;
    }

    /**
    * 获取合同金额 区间  单位：万 -7.[0,0.1) -6.[0.1,0.2) -5.[0.2,0.3) -4.[0.3,0.4) -3.[0.4,0.6) -2.[0.6,0.8) -1.[0.8,1) 1.[1,2) 2.[2,4) 3.[4,6) 4.[6,8)…….(单位:万元)
    *
    * @return 合同金额 区间  单位：万 -7.[0,0.1) -6.[0.1,0.2) -5.[0.2,0.3) -4.[0.3,0.4) -3.[0.4,0.6) -2.[0.6,0.8) -1.[0.8,1) 1.[1,2) 2.[2,4) 3.[4,6) 4.[6,8)…….(单位:万元)
    */
    public Double getBorrowAmtMax(){
        return borrowAmtMax;
    }

    /**
    * 设置合同金额 区间  单位：万 -7.[0,0.1) -6.[0.1,0.2) -5.[0.2,0.3) -4.[0.3,0.4) -3.[0.4,0.6) -2.[0.6,0.8) -1.[0.8,1) 1.[1,2) 2.[2,4) 3.[4,6) 4.[6,8)…….(单位:万元)
    * 
    * @param borrowAmtMax 要设置的合同金额 区间  单位：万 -7.[0,0.1) -6.[0.1,0.2) -5.[0.2,0.3) -4.[0.3,0.4) -3.[0.4,0.6) -2.[0.6,0.8) -1.[0.8,1) 1.[1,2) 2.[2,4) 3.[4,6) 4.[6,8)…….(单位:万元)
    */
    public void setBorrowAmtMax(Double borrowAmtMax){
        this.borrowAmtMax = borrowAmtMax;
    }

    /**
    * 获取合同日期
    *
    * @return 合同日期
    */
    public Date getContractDate(){
        return contractDate;
    }

    /**
    * 设置合同日期
    * 
    * @param contractDate 要设置的合同日期
    */
    public void setContractDate(Date contractDate){
        this.contractDate = contractDate;
    }

    /**
    * 获取批贷期数
    *
    * @return 批贷期数
    */
    public Integer getLoanPeriod(){
        return loanPeriod;
    }

    /**
    * 设置批贷期数
    * 
    * @param loanPeriod 要设置的批贷期数
    */
    public void setLoanPeriod(Integer loanPeriod){
        this.loanPeriod = loanPeriod;
    }

    /**
    * 获取还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+ 9.已还清
    *
    * @return 还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+ 9.已还清
    */
    public Integer getRepayState(){
        return repayState;
    }

    /**
    * 设置还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+ 9.已还清
    * 
    * @param repayState 要设置的还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+ 9.已还清
    */
    public void setRepayState(Integer repayState){
        this.repayState = repayState;
    }

    /**
    * 获取逾期金额 实际金额*100000 取整
    *
    * @return 逾期金额 实际金额*100000 取整
    */
    public Double getArrearsAmount(){
        return arrearsAmount;
    }

    /**
    * 设置逾期金额 实际金额*100000 取整
    * 
    * @param arrearsAmount 要设置的逾期金额 实际金额*100000 取整
    */
    public void setArrearsAmount(Double arrearsAmount){
        this.arrearsAmount = arrearsAmount;
    }

    /**
    * 获取公司代码
    *
    * @return 公司代码
    */
    public String getCompanyCode(){
        return companyCode;
    }

    /**
    * 设置公司代码
    * 
    * @param companyCode 要设置的公司代码
    */
    public void setCompanyCode(String companyCode){
        this.companyCode = companyCode;
    }

}