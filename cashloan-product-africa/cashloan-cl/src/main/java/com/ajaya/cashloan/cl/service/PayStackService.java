package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.alibaba.fastjson.JSONObject;

/**
 * 功能说明：PayStack支付相关
 *
 * @author yanzhiqiang
 * @since 2020-06-11 15:21
 **/
public interface PayStackService {

    /**
     * 放款
     *
     * @param userId   用户id
     * @param borrowId 订单id
     * @return ResponseWrapper
     */
    ResponseWrapper payout(Long userId, Long borrowId);

    /**
     * 放款回调
     *
     * @param data  订单处理
     * @param event 时间
     */
    void dealPayOutCallBack(JSONObject data, String event);

    /**
     * 银行卡代扣接口还款
     *
     * @param cardId   银行卡id
     * @param borrowId 订单id
     * @param type 还款类型 10 代扣  20 主动还款
     * @return ResponseWrapper
     */
    ResponseWrapper bankCardRepay(Long cardId, Long borrowId, String type);

    /**
     * 处理银行卡还款webHook回调
     *
     * @param data  回调数据
     * @param event 事件
     */
    void dealRepaymentCallBack(JSONObject data, String event);

    /**
     * 执行代扣
     * @param borrowId 订单id
     */
    void doWithholdingByBorrowId(Long borrowId);

    /**
     * 权限检查接口
     * @param cardId 用户card Id
     * @param borrowId  订单号
     * @return 还款金额
     */
    boolean checkAuthorization(Long cardId, Long borrowId);

}
