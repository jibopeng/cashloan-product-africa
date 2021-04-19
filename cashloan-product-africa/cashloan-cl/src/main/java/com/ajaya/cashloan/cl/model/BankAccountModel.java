package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2021-01-21 12:06
 **/
public class BankAccountModel {

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账户
     */
    private String accountNumber;

    /**
     * 绑定状态 1 可绑定 2 不绑定
     */
    private String status;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
