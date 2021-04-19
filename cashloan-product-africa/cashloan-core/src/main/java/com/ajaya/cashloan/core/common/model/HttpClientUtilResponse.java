package com.ajaya.cashloan.core.common.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-05-15 13:08
 **/
public class HttpClientUtilResponse {

    /**
     * 响应码
     */
    private int httpStatus;
    /**
     * 响应结果
     */
    private String  result;


    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
