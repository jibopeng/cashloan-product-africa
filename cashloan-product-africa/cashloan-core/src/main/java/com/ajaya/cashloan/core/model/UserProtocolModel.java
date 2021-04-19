package com.ajaya.cashloan.core.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-05-27 21:44
 **/
public class UserProtocolModel {

            private String customerId;
            private String userId;
            private String realName;
            private String liveDetailAddr;
            private String liveAddr;
            private String imei;
            private String mac;
            private String phoneTyep;
            private String operatingSystem;
            private String phoneBrand;
            private String ip;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLiveDetailAddr() {
        return liveDetailAddr;
    }

    public void setLiveDetailAddr(String liveDetailAddr) {
        this.liveDetailAddr = liveDetailAddr;
    }

    public String getLiveAddr() {
        return liveAddr;
    }

    public void setLiveAddr(String liveAddr) {
        this.liveAddr = liveAddr;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPhoneTyep() {
        return phoneTyep;
    }

    public void setPhoneTyep(String phoneTyep) {
        this.phoneTyep = phoneTyep;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
