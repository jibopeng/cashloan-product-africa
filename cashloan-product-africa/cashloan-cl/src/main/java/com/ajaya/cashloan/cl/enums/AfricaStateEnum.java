package com.ajaya.cashloan.cl.enums;

import com.ajaya.cashloan.core.common.util.StringUtil;

/**
 * 功能说明：非洲邦枚举
 *
 * @author yanzhiqiang
 * @since 2020-03-04 10:42
 **/
public enum AfricaStateEnum {

    /**
     * 枚举项(邦，以及需要进行字符串匹配的代码)
     */

    ABIA           ("Abia" ,"Abia"),
    EDO            ("Edo" ,"Edo"),
    NASARAWA       ("Nasarawa" ,"Nasarawa"),
    ADAMAWA        ("Adamawa" ,"Adamawa"),
    EKITI          ("Ekiti" ,"Ekiti"),
    NIGER          ("Niger","Niger"),
    AKWA_IBOM      ("Akwa Ibom" ,"Akwa Ibom"),
    GOMBE          ("Gombe","Gombe"),
    OGUN           ("Ogun" ,"Ogun"),
    ANAMBRA        ("Anambra" ,"Anambra"),
    IMO            ("Imo" ,"Imo"),
    ONDO           ("Ondo" ,"Ondo"),
    BAUCHI         ("Bauchi" ,"Bauchi"),
    JIGAWA         ("Jigawa" ,"Jigawa"),
    OSUN           ("Osun" ,"Osun"),
    BAYELSA        ("Bayelsa" ,"Bayelsa"),
    KADUNA         ("Kaduna" ,"Kaduna"),
    OYO            ("Oyo" ,"Oyo"),
    BENUE          ("Benue","Benue"),
    KANO           ("Kano" ,"Kano"),
    PLATEAU        ("Plateau" ,"Plateau"),
    BORNO          ("Borno","Borno"),
    KATSINA        ("Katsina" ,"Katsina"),
    RIVERS         ("Rivers" ,"Rivers"),
    CROSS_RIVER    ("Cross River" ,"Cross River"),
    KEBBI          ("Kebbi","Kebbi"),
    SOKOTO         ("Sokoto" ,"Sokoto"),
    DELTA          ("Delta","Delta"),
    KOGI           ("Kogi" ,"Kogi"),
    TARABA         ("Taraba" ,"Taraba"),
    EBONYI         ("Ebonyi" ,"Ebonyi"),
    KWARA          ("Kwara","Kwara"),
    YOBE           ("Yobe" ,"Yobe"),
    ENUGU          ("Enugu","Enugu"),
    LAGOS          ("Lagos","Lagos"),
    ZAMFARA        ("Zamfara" ,"Zamfara"),
    ABUJA          ("Abuja","Abuja");

    AfricaStateEnum(String state, String code) {
        this.state = state;
        this.code = code;
    }
    /**
     * 邦名称
     */
    private String state;

    /**
     * 邦与地址匹配字符
     */
    private String code;


    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    /**
     * 根据借款地址判断是返回响应的邦名称
     *
     * @param borrowAddress 借款申请地址
     * @return String 邦名称
     */
    public static String getStateByBorrowAddress(String borrowAddress) {
        borrowAddress= StringUtil.isNull(borrowAddress);
        String state = "";
        for (AfricaStateEnum e : AfricaStateEnum.values()) {
            String code = e.getCode();
            if (borrowAddress.equals(code)) {
                state = e.getState();
                break;
            }
        }
        return state;
    }
}
