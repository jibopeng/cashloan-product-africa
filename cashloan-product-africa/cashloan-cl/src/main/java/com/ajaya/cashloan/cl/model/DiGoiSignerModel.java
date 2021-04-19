package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-04-26 17:45
 **/
public class DiGoiSignerModel {


    private String name;
    private String identifier;

    private String aadhaar_id;

    private String reason;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAadhaar_id() {
        return aadhaar_id;
    }

    public void setAadhaar_id(String aadhaar_id) {
        this.aadhaar_id = aadhaar_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
