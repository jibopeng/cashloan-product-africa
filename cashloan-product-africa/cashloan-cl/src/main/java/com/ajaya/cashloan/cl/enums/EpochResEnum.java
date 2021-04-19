package com.ajaya.cashloan.cl.enums;

/**
 * @author ryan
 * @version 1.0 2020/11/19
 */
public enum EpochResEnum {

    REQUEST_SUCCEED(200, "success"),
    SIG_ERROR(4001, "签名错误"),
    SYSTEM_ERROR(500, "系统错误"),
    CUSTOM_ERROR(400, "接口错误");

    private final int value;

    private final String reasonPhrase;

    EpochResEnum(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public static EpochResEnum parse(int code) {
        for (EpochResEnum responseEnum : EpochResEnum.values()) {
            if (responseEnum.value() == code) {
                return responseEnum;
            }
        }

        throw new IllegalArgumentException("Unable to parse the provided ResponseEnum " + code);
    }
}
