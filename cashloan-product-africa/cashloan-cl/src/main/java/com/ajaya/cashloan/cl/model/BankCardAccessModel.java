package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2021-01-27 16:13
 **/
public class BankCardAccessModel {

    private String accessCode;
    private String reference;
    private String email;
    private  Double amount;


    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
