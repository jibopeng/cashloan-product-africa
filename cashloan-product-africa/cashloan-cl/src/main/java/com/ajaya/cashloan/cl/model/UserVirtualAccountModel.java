package com.ajaya.cashloan.cl.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户虚拟账户表实体
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-03 11:52:43
 */
public class UserVirtualAccountModel implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 账户名称
     */
    private String accountName;



    /**
     * 用户账号
     */
    private String accountNumber;

    /**
     * 银行名称
     */
    private String bankName;


    private Double totalAmount;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}