package com.ajaya.cashloan.cl.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 功能说明：用于展示借款进度详情所用枚举
 *
 * @author yanzhiqiang
 * @since 2020-03-04 10:42
 **/
public enum BorrowDetailEnum {

    /**
     * 枚举项
     */
    //审核中
    AUDIT_PROGRESS("10,22", "Under Review"),
    //审核通过
    AUDIT_PASS("20,26", "Approved"),
    //审核未通过
    AUDIT_FAILED("21,27", "Rejected"),
    //放款中
    IN_LENDING("31,29,301,302", "Disbursing"),
    //放款成功
    LOAN_SUCCESS("30", "Paid"),
    //已逾期
    OVERDUE("50", "Overdue @day"),
    //已还款
    SUCCESSFUL_REPAYMENT("40,41", "Successful repayment"),
    //还款中
    REPAYMENT("43", "Processing payment");


    BorrowDetailEnum(String code, String stateName) {
        this.code = code;
        this.stateName = stateName;
    }

    /**
     * 借款进度代码
     */
    private String code;
    /**
     * 借款进度显示名称
     */
    private String stateName;

    public String getCode() {
        return code;
    }

    public String getStateName() {
        return stateName;
    }


    /**
     * 借款状态代码是否属于枚举
     *
     * @param code 菜单代码
     * @return boolean
     */
    public static boolean isIncludeCode(String code) {
        boolean include = false;
        for (BorrowDetailEnum e : BorrowDetailEnum.values()) {
            String[] splits = e.getCode().split(",");
            List<String> strings = Arrays.asList(splits);
            if (strings.contains(code)) {
                include = true;
                break;
            }
        }
        return include;
    }

    /**
     * 借款状态代码获取
     *
     * @param code 借款状态代码
     * @return String 状态名称
     */
    public static String getStateNameByCode(String code) {
        String stateName = "";
        for (BorrowDetailEnum e : BorrowDetailEnum.values()) {
            String[] splits = e.getCode().split(",");
            List<String> strings = Arrays.asList(splits);
            if (strings.contains(code)) {
                stateName = e.getStateName();
            }
        }
        return stateName;
    }
}
