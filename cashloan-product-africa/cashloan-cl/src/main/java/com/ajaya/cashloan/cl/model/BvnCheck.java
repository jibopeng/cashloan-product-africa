package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2021-01-15 17:49
 **/

public class BvnCheck {
    /**
     * 是bvn  true or false
     */
    private boolean  bvn;
    /**
     * bvn校验消息
     */
    private String  message;

    public boolean isBvn() {
        return bvn;
    }

    public void setBvn(boolean bvn) {
        this.bvn = bvn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
