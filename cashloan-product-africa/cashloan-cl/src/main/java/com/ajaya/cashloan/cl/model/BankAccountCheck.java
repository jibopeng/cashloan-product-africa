package com.ajaya.cashloan.cl.model;

/**
 * 功能说明： 校验银行卡model
 *
 * @author yanzhiqiang
 * @since 2021-01-15 17:49
 **/

public class BankAccountCheck {
    /**
     * BankAccount 校验成功  true or false
     */
    private boolean  bankAccount;
    /**
     * bvn校验消息
     */
    private String  message;

    public boolean isBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(boolean bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
