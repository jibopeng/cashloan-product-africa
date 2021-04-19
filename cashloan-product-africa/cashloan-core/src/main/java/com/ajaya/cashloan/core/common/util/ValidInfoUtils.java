package com.ajaya.cashloan.core.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能说明：非洲项目验证  姓名验证 bvn验证
 *
 * @author yanzhiqiang
 * @since 2021-01-14 17:06
 **/
public class ValidInfoUtils {
    /**
     * aadHar卡验证正则 12位纯数字
     */
    private static final String BVN_NUM = "[0-9]\\d{10}";


    /**
     * 姓名校验  大于1个字符的字母
     */
    private static final String NAME_CHECK = "[a-zA-Z\\s]{2,}";
    /**
     * ad 卡校验
     *
     * @param adNumber ad卡号
     * @return true
     */
    public static boolean isBvn(String adNumber) {

        return isMatch(BVN_NUM, adNumber);
    }
    /**
     * 姓名校验
     *
     * @param name 姓名校验
     * @return true
     */
    public static boolean nameCheck(String name) {
        name = name.trim();
        return isMatch(NAME_CHECK, name);
    }
    /**
     * bank  account 正则10位纯数字
     *
     */
    private static final String BANK_ACCOUNT_NUM = "[0-9]\\d{9}";

    /**
     * bankAccount 银行账户校验
     *
     * @param bankAccount 银行卡号
     * @return true
     */
    public static boolean isBankAccount(String bankAccount) {

        return isMatch(BANK_ACCOUNT_NUM, bankAccount);
    }

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            System.out.println("空");
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

//    public static void main(String[] args) {
//        System.out.println( ifscCodeCheck("BARB0BLYFAR"));
//    }
}
