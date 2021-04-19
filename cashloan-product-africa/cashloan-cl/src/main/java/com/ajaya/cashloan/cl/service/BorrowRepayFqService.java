package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.BorrowRepayFq;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 分期还款计划表Service
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:01
 */
public interface BorrowRepayFqService extends BaseService<BorrowRepayFq, Long> {

    /**
     * 保存分期还款计划
     *
     * @param borrowId
     * @param amount
     * @param stateRepayNo
     * @return
     */
    public Integer saveOrUpdateBorrowRepayFq(Long borrowId, Double amount, String stateRepayNo);

    /**
     * 保存分期还款计划
     *
     * @param borrowId
     * @param amount
     * @param stateRepayNo
     * @param bank
     * @param cardNo
     * @return
     */
    public Integer saveOrUpdateBorrowRepayFqNew(Long borrowId, Double amount, String stateRepayNo, String cardNo, String bank,String tradeNo);

    /**
     * 获取剩余应还所有的钱
     *
     * @param borrowId
     * @return
     */
    public Double getShengXiaRepayMoney(Long borrowId);

    /**
     * 获取当前订单应该还的钱(部分还款功能)
     *
     * @param borrowId
     * @return
     */
    public Double getCurrentNeedReapyMoney(Long borrowId);

    /**
     * 判断当前订单是否要分期还款
     *
     * @param borrowId
     * @return
     */
    public Boolean isBorrowRepayFq(Long borrowId);

    /**
     * 获取一笔有效的部分还款
     *
     * @return
     */
    public BorrowRepayFq getCurrentYouXiaoBorrowRepayFq(Long borrowId);
}
