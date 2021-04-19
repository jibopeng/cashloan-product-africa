package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * '银行账户记录表实体
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-20 17:54:03
 */
public class BankAccount implements Serializable {

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
     * 银行名称
     */
    private String bankName;

    /**
     * 账户持有人姓名
     */
    private String holderName;

    /**
     * 银行账户
     */
    private String accountNumber;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * bvn账号
     */
    private String bvn;

    /**
     * 绑定状态 1 绑定 2 未绑定
     */
    private String status;

    /**
     * 账号匹配结果
     */
    private String account;

    /**
     * 名字匹配结果
     */
    private String firstName;

    /**
     * 中间名字匹配结果
     */
    private String middleName;

    /**
     * 姓氏匹配结果
     */
    private String lastName;

    /**
     * 是否在黑名单中
     */
    private String isBlacklisted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
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
     * 获取账户持有人姓名
     *
     * @return 账户持有人姓名
     */
    public String getHolderName(){
        return holderName;
    }

    /**
     * 设置账户持有人姓名
     *
     * @param holderName 要设置的账户持有人姓名
     */
    public void setHolderName(String holderName){
        this.holderName = holderName;
    }

    /**
     * 获取银行账户
     *
     * @return 银行账户
     */
    public String getAccountNumber(){
        return accountNumber;
    }

    /**
     * 设置银行账户
     *
     * @param accountNumber 要设置的银行账户
     */
    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    /**
     * 获取银行编码
     *
     * @return 银行编码
     */
    public String getBankCode(){
        return bankCode;
    }

    /**
     * 设置银行编码
     *
     * @param bankCode 要设置的银行编码
     */
    public void setBankCode(String bankCode){
        this.bankCode = bankCode;
    }

    /**
     * 获取bvn账号
     *
     * @return bvn账号
     */
    public String getBvn(){
        return bvn;
    }

    /**
     * 设置bvn账号
     *
     * @param bvn 要设置的bvn账号
     */
    public void setBvn(String bvn){
        this.bvn = bvn;
    }

    /**
     * 获取绑定状态 1 绑定 2 未绑定
     *
     * @return 绑定状态 1 绑定 2 未绑定
     */
    public String getStatus(){
        return status;
    }

    /**
     * 设置绑定状态 1 绑定 2 未绑定
     *
     * @param status 要设置的绑定状态 1 绑定 2 未绑定
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * 获取账号匹配结果
     *
     * @return 账号匹配结果
     */
    public String getAccount(){
        return account;
    }

    /**
     * 设置账号匹配结果
     *
     * @param account 要设置的账号匹配结果
     */
    public void setAccount(String account){
        this.account = account;
    }

    /**
     * 获取名字匹配结果
     *
     * @return 名字匹配结果
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * 设置名字匹配结果
     *
     * @param firstName 要设置的名字匹配结果
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * 获取中间名字匹配结果
     *
     * @return 中间名字匹配结果
     */
    public String getMiddleName(){
        return middleName;
    }

    /**
     * 设置中间名字匹配结果
     *
     * @param middleName 要设置的中间名字匹配结果
     */
    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    /**
     * 获取姓氏匹配结果
     *
     * @return 姓氏匹配结果
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * 设置姓氏匹配结果
     *
     * @param lastName 要设置的姓氏匹配结果
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * 获取是否在黑名单中
     *
     * @return 是否在黑名单中
     */
    public String getIsBlacklisted(){
        return isBlacklisted;
    }

    /**
     * 设置是否在黑名单中
     *
     * @param isBlacklisted 要设置的是否在黑名单中
     */
    public void setIsBlacklisted(String isBlacklisted){
        this.isBlacklisted = isBlacklisted;
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
     * 获取最后修改时间
     *
     * @return 最后修改时间
     */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
     * 设置最后修改时间
     *
     * @param updateTime 要设置的最后修改时间
     */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}