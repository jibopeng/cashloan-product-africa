package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 风控订单业务表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-16 14:32:46
 */
 public class ClBorrowRiskBusiness implements Serializable {

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
    * 产品标识
    */
    private String appid;

    /**
    * 订单主键
    */
    private Long borrowId;

    /**
    * 申请时间
    */
    private Date applyTime;

    /**
    * 
    */
    private Double applicationAmount;

    /**
    * 申请贷款期限，单位：天
    */
    private String applicationTerm;

    /**
    * 新/老用户 1：新用户，0：老用户
    */
    private String isNew;

    /**
    * 是否入审 1,入审，0未入审
    */
    private String isAudit;

    /**
    * 是否审批通过 1:通过，0:未通过，“”未进入审批
    */
    private String approval;

    /**
    * 审批时间 格式为%Y-%m-%d %H:%M:%S “”未进入审批流程
    */
    private Date approvalTime;

    /**
    * 1成功放款，0未放款，“”未进入放款流程
    */
    private String loanSuccess;

    /**
    * 放款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    private Date loanSuccessTime;

    /**
    * 放款金额，“”未放款
    */
    private Double lendingAmmount;

    /**
    * 到期应还时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    private Date expiryTime;

    /**
    * 成功还款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    private Date repayTime;

    /**
    * 逾期天数，“”未放款
    */
    private Integer overdueDays;

    /**
    * #0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款
    */
    private String repayType;

    /**
    * 订单号
    */
    private String orderNo;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
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
    * 获取产品标识
    *
    * @return 产品标识
    */
    public String getAppid(){
        return appid;
    }

    /**
    * 设置产品标识
    * 
    * @param appid 要设置的产品标识
    */
    public void setAppid(String appid){
        this.appid = appid;
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
    * 获取申请时间
    *
    * @return 申请时间
    */
    public Date getApplyTime(){
        return applyTime;
    }

    /**
    * 设置申请时间
    * 
    * @param applyTime 要设置的申请时间
    */
    public void setApplyTime(Date applyTime){
        this.applyTime = applyTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Double getApplicationAmount(){
        return applicationAmount;
    }

    /**
    * 设置
    * 
    * @param applicationAmount 要设置的
    */
    public void setApplicationAmount(Double applicationAmount){
        this.applicationAmount = applicationAmount;
    }

    /**
    * 获取申请贷款期限，单位：天
    *
    * @return 申请贷款期限，单位：天
    */
    public String getApplicationTerm(){
        return applicationTerm;
    }

    /**
    * 设置申请贷款期限，单位：天
    * 
    * @param applicationTerm 要设置的申请贷款期限，单位：天
    */
    public void setApplicationTerm(String applicationTerm){
        this.applicationTerm = applicationTerm;
    }

    /**
    * 获取新/老用户 1：新用户，0：老用户
    *
    * @return 新/老用户 1：新用户，0：老用户
    */
    public String getIsNew(){
        return isNew;
    }

    /**
    * 设置新/老用户 1：新用户，0：老用户
    * 
    * @param isNew 要设置的新/老用户 1：新用户，0：老用户
    */
    public void setIsNew(String isNew){
        this.isNew = isNew;
    }

    /**
    * 获取是否入审 1,入审，0未入审
    *
    * @return 是否入审 1,入审，0未入审
    */
    public String getIsAudit(){
        return isAudit;
    }

    /**
    * 设置是否入审 1,入审，0未入审
    * 
    * @param isAudit 要设置的是否入审 1,入审，0未入审
    */
    public void setIsAudit(String isAudit){
        this.isAudit = isAudit;
    }

    /**
    * 获取是否审批通过 1:通过，0:未通过，“”未进入审批
    *
    * @return 是否审批通过 1:通过，0:未通过，“”未进入审批
    */
    public String getApproval(){
        return approval;
    }

    /**
    * 设置是否审批通过 1:通过，0:未通过，“”未进入审批
    * 
    * @param approval 要设置的是否审批通过 1:通过，0:未通过，“”未进入审批
    */
    public void setApproval(String approval){
        this.approval = approval;
    }

    /**
    * 获取审批时间 格式为%Y-%m-%d %H:%M:%S “”未进入审批流程
    *
    * @return 审批时间 格式为%Y-%m-%d %H:%M:%S “”未进入审批流程
    */
    public Date getApprovalTime(){
        return approvalTime;
    }

    /**
    * 设置审批时间 格式为%Y-%m-%d %H:%M:%S “”未进入审批流程
    * 
    * @param approvalTime 要设置的审批时间 格式为%Y-%m-%d %H:%M:%S “”未进入审批流程
    */
    public void setApprovalTime(Date approvalTime){
        this.approvalTime = approvalTime;
    }

    /**
    * 获取1成功放款，0未放款，“”未进入放款流程
    *
    * @return 1成功放款，0未放款，“”未进入放款流程
    */
    public String getLoanSuccess(){
        return loanSuccess;
    }

    /**
    * 设置1成功放款，0未放款，“”未进入放款流程
    * 
    * @param loanSuccess 要设置的1成功放款，0未放款，“”未进入放款流程
    */
    public void setLoanSuccess(String loanSuccess){
        this.loanSuccess = loanSuccess;
    }

    /**
    * 获取放款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    *
    * @return 放款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    public Date getLoanSuccessTime(){
        return loanSuccessTime;
    }

    /**
    * 设置放款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    * 
    * @param loanSuccessTime 要设置的放款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    public void setLoanSuccessTime(Date loanSuccessTime){
        this.loanSuccessTime = loanSuccessTime;
    }

    /**
    * 获取放款金额，“”未放款
    *
    * @return 放款金额，“”未放款
    */
    public Double getLendingAmmount(){
        return lendingAmmount;
    }

    /**
    * 设置放款金额，“”未放款
    * 
    * @param lendingAmmount 要设置的放款金额，“”未放款
    */
    public void setLendingAmmount(Double lendingAmmount){
        this.lendingAmmount = lendingAmmount;
    }

    /**
    * 获取到期应还时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    *
    * @return 到期应还时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    public Date getExpiryTime(){
        return expiryTime;
    }

    /**
    * 设置到期应还时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    * 
    * @param expiryTime 要设置的到期应还时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    public void setExpiryTime(Date expiryTime){
        this.expiryTime = expiryTime;
    }

    /**
    * 获取成功还款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    *
    * @return 成功还款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    public Date getRepayTime(){
        return repayTime;
    }

    /**
    * 设置成功还款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    * 
    * @param repayTime 要设置的成功还款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款
    */
    public void setRepayTime(Date repayTime){
        this.repayTime = repayTime;
    }

    /**
    * 获取逾期天数，“”未放款
    *
    * @return 逾期天数，“”未放款
    */
    public Integer getOverdueDays(){
        return overdueDays;
    }

    /**
    * 设置逾期天数，“”未放款
    * 
    * @param overdueDays 要设置的逾期天数，“”未放款
    */
    public void setOverdueDays(Integer overdueDays){
        this.overdueDays = overdueDays;
    }

    /**
    * 获取#0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款
    *
    * @return #0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款
    */
    public String getRepayType(){
        return repayType;
    }

    /**
    * 设置#0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款
    * 
    * @param repayType 要设置的#0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款
    */
    public void setRepayType(String repayType){
        this.repayType = repayType;
    }

    /**
    * 获取订单号
    *
    * @return 订单号
    */
    public String getOrderNo(){
        return orderNo;
    }

    /**
    * 设置订单号
    * 
    * @param orderNo 要设置的订单号
    */
    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
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
    * 获取更新时间
    *
    * @return 更新时间
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置更新时间
    * 
    * @param updateTime 要设置的更新时间
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}