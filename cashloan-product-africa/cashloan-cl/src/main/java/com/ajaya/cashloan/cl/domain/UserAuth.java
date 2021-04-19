package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;

/**
 * 用户认证实体
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2017-02-21 13:42:44
 */

public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */

    private Long id;

    /**
     * 客户表 外键
     */

    private Long userId;

    /**
     * 基本信息证状态
     */

    private String basicInfoState;

    /**
     * 紧急联系人状态
     */

    private String contactState;

    /**
     * 银行卡状态
     */

    private String bankAccountState;

    /**
     * 个人信息认证
     */

    private String personalInfoState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBasicInfoState() {
        return basicInfoState;
    }

    public void setBasicInfoState(String basicInfoState) {
        this.basicInfoState = basicInfoState;
    }

    public String getContactState() {
        return contactState;
    }

    public void setContactState(String contactState) {
        this.contactState = contactState;
    }

    public String getBankAccountState() {
        return bankAccountState;
    }

    public void setBankAccountState(String bankAccountState) {
        this.bankAccountState = bankAccountState;
    }

    public String getPersonalInfoState() {
        return personalInfoState;
    }

    public void setPersonalInfoState(String personalInfoState) {
        this.personalInfoState = personalInfoState;
    }
}