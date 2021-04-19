package com.ajaya.cashloan.cl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 功能说明： 调用还款参数查询
 *
 * @author yanzhiqiang
 * @since 2021-01-29 18:02
 **/
@JsonIgnoreProperties(value = "userId")
public class BorrowRepayParamModel {
    /**
     * 卡号后四位
     */
    @JsonIgnore
    private String last4;

    /**
     * 银行名称
     */
    @JsonIgnore
    private String bank;

    private  Double repayTotal;

    private Long userId;

    private  String email;

    private String authorizationCode;

    public Double getRepayTotal() {
        return repayTotal;
    }

    public void setRepayTotal(Double repayTotal) {
        this.repayTotal = repayTotal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
