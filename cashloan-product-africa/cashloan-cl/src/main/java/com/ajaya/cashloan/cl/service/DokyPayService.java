package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.model.ResponseWrapper;

/**
 * DokyPay支付请求Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-05 17:40:07
 */
public interface DokyPayService {
    /**
     * 封装还款参数 拿到DokyPay支付跳转链接链接
     * @param borrowId 借款id
     * @param userId 用户id
     * @param payId 支付方式id
     * @return 响应实体
     */
    ResponseWrapper getPayInfo(Long borrowId, Long userId, String payId);

    /**
     * 更改订单状态
     * @param merTransNo 商户订单号
     * @param transStatus 支付状态 success或者 processing
     * @param tradeNo 第三方交易流水号
     * @return 更新状态是否成功
     */
    boolean updateBorrowStats(String merTransNo, String transStatus, String tradeNo);
}
