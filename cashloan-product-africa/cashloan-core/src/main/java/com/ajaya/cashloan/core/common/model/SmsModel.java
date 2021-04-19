package com.ajaya.cashloan.core.common.model;

/**
 * 功能说明： 天一泓封装短信信息
 *
 * @author yanzhiqiang
 * @since 2020-02-13 18:35
 **/
public class SmsModel {

    /**
     * 发送id
     */
    private String  username;
    /**
     * 电话号码
     */
    private String  to;
    /**
     * 发送内容
     */
    private String message ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
