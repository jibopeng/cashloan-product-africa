package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户虚拟账户表实体
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-03 11:52:43
 */
public class UserVirtualAccount implements Serializable {

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
     * 虚拟账户唯一标识
     */
    private String accountReference;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * NGN
     */
    private String currencyCode;

    /**
     * 用户邮箱
     */
    private String customerEmail;

    /**
     * 用户账号
     */
    private String accountNumber;

    /**
     *
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 关联标识
     */
    private String reservationReference;

    /**
     * 用户bvn
     */
    private String customerBvn;

    /**
     *
     */
    private String createdOn;

    /**
     * 账户状态
     */
    private String status;

    /**
     * 最后使用时间
     */
    private Date lastUseTime;

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
     * 获取虚拟账户唯一标识
     *
     * @return 虚拟账户唯一标识
     */
    public String getAccountReference(){
        return accountReference;
    }

    /**
     * 设置虚拟账户唯一标识
     *
     * @param accountReference 要设置的虚拟账户唯一标识
     */
    public void setAccountReference(String accountReference){
        this.accountReference = accountReference;
    }

    /**
     * 获取账户名称
     *
     * @return 账户名称
     */
    public String getAccountName(){
        return accountName;
    }

    /**
     * 设置账户名称
     *
     * @param accountName 要设置的账户名称
     */
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    /**
     * 获取NGN
     *
     * @return NGN
     */
    public String getCurrencyCode(){
        return currencyCode;
    }

    /**
     * 设置NGN
     *
     * @param currencyCode 要设置的NGN
     */
    public void setCurrencyCode(String currencyCode){
        this.currencyCode = currencyCode;
    }

    /**
     * 获取用户邮箱
     *
     * @return 用户邮箱
     */
    public String getCustomerEmail(){
        return customerEmail;
    }

    /**
     * 设置用户邮箱
     *
     * @param customerEmail 要设置的用户邮箱
     */
    public void setCustomerEmail(String customerEmail){
        this.customerEmail = customerEmail;
    }

    /**
     * 获取用户账号
     *
     * @return 用户账号
     */
    public String getAccountNumber(){
        return accountNumber;
    }

    /**
     * 设置用户账号
     *
     * @param accountNumber 要设置的用户账号
     */
    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getBankCode(){
        return bankCode;
    }

    /**
     * 设置
     *
     * @param bankCode 要设置的
     */
    public void setBankCode(String bankCode){
        this.bankCode = bankCode;
    }

    /**
     * 获取银行名称
     *
     * @return 银行名称
     */
    public String getBankName(){
        return bankName;
    }

    /**
     * 设置银行名称
     *
     * @param bankName 要设置的银行名称
     */
    public void setBankName(String bankName){
        this.bankName = bankName;
    }

    /**
     * 获取关联标识
     *
     * @return 关联标识
     */
    public String getReservationReference(){
        return reservationReference;
    }

    /**
     * 设置关联标识
     *
     * @param reservationReference 要设置的关联标识
     */
    public void setReservationReference(String reservationReference){
        this.reservationReference = reservationReference;
    }

    /**
     * 获取用户bvn
     *
     * @return 用户bvn
     */
    public String getCustomerBvn(){
        return customerBvn;
    }

    /**
     * 设置用户bvn
     *
     * @param customerBvn 要设置的用户bvn
     */
    public void setCustomerBvn(String customerBvn){
        this.customerBvn = customerBvn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * 获取账户状态
     *
     * @return 账户状态
     */
    public String getStatus(){
        return status;
    }

    /**
     * 设置账户状态
     *
     * @param status 要设置的账户状态
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * 获取最后使用时间
     *
     * @return 最后使用时间
     */
    public Date getLastUseTime(){
        return lastUseTime;
    }

    /**
     * 设置最后使用时间
     *
     * @param lastUseTime 要设置的最后使用时间
     */
    public void setLastUseTime(Date lastUseTime){
        this.lastUseTime = lastUseTime;
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