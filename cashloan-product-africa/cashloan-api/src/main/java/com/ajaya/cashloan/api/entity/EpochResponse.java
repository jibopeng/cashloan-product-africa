package com.ajaya.cashloan.api.entity;

import com.ajaya.cashloan.cl.enums.EpochResEnum;

/**
 * @author ryan
 * @version 1.0 2020/11/19
 */
public class EpochResponse<T> {

    /**
     * 返回状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回 data
     */
    private T data;

    public EpochResponse(EpochResEnum resEnum) {
        this.code = String.valueOf(resEnum.value());
        this.msg = resEnum.getReasonPhrase();
    }

    public EpochResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public EpochResponse() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EpochResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
