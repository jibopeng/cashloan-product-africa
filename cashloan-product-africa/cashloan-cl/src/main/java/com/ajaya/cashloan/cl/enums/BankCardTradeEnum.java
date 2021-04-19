package com.ajaya.cashloan.cl.enums;

/**
 * 功能说明：银行卡绑定保存枚举
 *
 * @author yanzhiqiang
 * @since 2020-03-04 10:42
 **/
public enum BankCardTradeEnum {

    /**
     * 枚举项
     */
    AUDIT_PROGRESS("CREATE", 0),
    ABANDONED("ABANDONED", 1),
    PENDING("PENDING", 2),
    ONGOING("ONGOING", 3),
    AILED("FAILED", 4),
    SUCCESS("SUCCESS",5);
    BankCardTradeEnum(String code, Integer value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private int value;

    public String getCode() {
        return code;
    }

    public Integer getValue() {
        return value;
    }


    /**
     * 借款状态代码获取
     *
     * @param code 借款状态代码
     * @return String 状态名称
     */
    public static Integer getValueByCode(String code) {
        Integer stateName = 0;
        for (BankCardTradeEnum e : BankCardTradeEnum.values()) {
            if (code.equals(e.getCode())) stateName = e.getValue();
        }
        return stateName;
    }
}
