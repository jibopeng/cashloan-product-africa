package com.ajaya.cashloan.cl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 功能说明：ussd list
 *
 * @author yanzhiqiang
 * @since 2021-01-03 18:36
 **/
public class UssdModel {

    private String  name ;
    @JsonIgnore
    private String  code ;
    private String  ussdTemplate ;
    @JsonIgnore
    private String  baseUssdCode ;

    @JsonIgnore
    private String  transferUssdTemplate ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //如果ussdTemplate 为null 返回
    public String getUssdTemplate() {

        if (ussdTemplate==null) return transferUssdTemplate;
        return ussdTemplate;
    }

    public void setUssdTemplate(String ussdTemplate) {
        this.ussdTemplate = ussdTemplate;
    }

    public String getBaseUssdCode() {
        return baseUssdCode;
    }

    public void setBaseUssdCode(String baseUssdCode) {
        this.baseUssdCode = baseUssdCode;
    }

    public String getTransferUssdTemplate() {
        return transferUssdTemplate;
    }

    public void setTransferUssdTemplate(String transferUssdTemplate) {
        this.transferUssdTemplate = transferUssdTemplate;
    }
}
