package com.ajaya.cashloan.cl.model;


/**
 * 功能描述:请求的响应体
 *
 * @author yanzhiqiang
 * @since 2020/01/23
 */
public class ResponseWrapper {


    /**
     * infoCode 成功
     */
    private static final Integer INFO_CODE_SUCCESS = 200;
    /**
     * infoCode 异常
     */
    private static final Integer INFO_CODE_ERROR = 400;

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 传输数据
     */
    private Object data;

    /**
     * 无参构造器
     */
    public ResponseWrapper() {
    }

    /**
     * @param code 响应码
     * @param msg  响应信息
     * @param data 传输数据
     */
    public ResponseWrapper(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseWrapper(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * 状态为成功的响应.
     */
    public static ResponseWrapper success() {
        return new ResponseWrapper(INFO_CODE_SUCCESS, "SUCCESS");
    }

    public static ResponseWrapper success(String msg) {
        return new ResponseWrapper(INFO_CODE_SUCCESS, msg);
    }

    public static ResponseWrapper success(Object data) {
        return new ResponseWrapper(INFO_CODE_SUCCESS, "SUCCESS", data);
    }

    public static ResponseWrapper success(String msg, Object data) {
        return new ResponseWrapper(INFO_CODE_SUCCESS, msg, data);
    }

    /**
     * 状态为异常的响应.
     */
    public static ResponseWrapper error() {
        return new ResponseWrapper(INFO_CODE_ERROR, "FAILED");
    }

    public static ResponseWrapper error(String msg) {
        return new ResponseWrapper(INFO_CODE_ERROR, msg);
    }

    public static ResponseWrapper error(Integer code, String msg) {
        return new ResponseWrapper(code, msg);
    }

    public static ResponseWrapper error(Object data) {
        return new ResponseWrapper(INFO_CODE_ERROR, "FAILED", data);
    }

    public static ResponseWrapper error(String msg, Object data) {
        return new ResponseWrapper(INFO_CODE_ERROR, msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}

