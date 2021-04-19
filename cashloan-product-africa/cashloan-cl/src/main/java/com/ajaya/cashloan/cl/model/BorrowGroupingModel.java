package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-12-02 11:44
 **/
public class BorrowGroupingModel {

    /**
     * 分流分组A
     */
    public static final String GROUPING_A = "A";
    /**
     * 分流分组B
     */
    public static final String GROUPING_B = "B";
    /**
     * 分流分组A,但是原数据（msg、app）全部缺失走B分组，重新标识为AB分组
     */
    public static final String GROUPING_AB = "AB";

}
