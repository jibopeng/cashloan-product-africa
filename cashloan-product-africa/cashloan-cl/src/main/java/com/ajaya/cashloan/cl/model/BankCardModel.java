package com.ajaya.cashloan.cl.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:43:16
 */
 public class BankCardModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 绑定状态 1 绑定 2 未绑定
    */
    private String status;

    /**
    * 卡号后四位
    */
    private String last4;


    /**
    * 银行名称
    */
    private String bank;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}