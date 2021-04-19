package com.ajaya.cashloan.core.enums;


/**
 * 功能说明：英文月份转换枚举
 *
 * @author yanzhiqiang
 * @since 2020-0619 10:42
 */
public enum MonthIndianEnums {

    /**
     * 枚举项
     */
    JAN("01", "Jan"),
    FEB("02", "Feb"),
    MAR("03", "Mar"),
    APR("04", "Apr"),
    MAY("05", "May"),
    JUN("06", "Jun"),
    JUL("07", "Jul"),
    AUG("08", "Aug"),
    SEPT("09", "Sept"),
    OCT("10", "Oct"),
    NOV("11", "Nov"),
    DEC("12", "Dec");

    MonthIndianEnums(String code, String stateName) {
        this.code = code;
        this.stateName = stateName;
    }

    /**
     * 数字月份
     */
    private String code;
    /**
     * 显示的英文名称
     */
    private String stateName;

    public String getCode() {
        return code;
    }

    public String getStateName() {
        return stateName;
    }



    /**
     * 通过数字月份获取英文月份
     *
     * @param code 数字月份
     * @return String 英文月份
     */
    public static String getStateNameByCode(String code) {
        String stateName = "";
        for (MonthIndianEnums e : MonthIndianEnums.values()) {
            if (e.getCode().equals(code)) {
                stateName = e.getStateName();
            }
        }
        return stateName;
    }
}
