package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.BankCard;
import com.ajaya.cashloan.cl.model.BankCardModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 银行卡记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:43:16
 */
public interface BankCardService extends BaseService<BankCard, Long> {
    /**
     * 生成用户授权码
     * @param userId 用户id
     * @param borrowId 订单id
     * @param type  1 首页触发 2 用户中心触发
     * @return ResponseWrapper
     */
    ResponseWrapper getAccessCode(Long userId, Long borrowId, String type);

    /***
     *  查询绑卡交易订单状态
     * @param userId 用户id
     * @param reference 交易唯一标识
     * @return ResponseWrapper
     */
    ResponseWrapper tradingCheck(Long userId, String reference);

    /**
     * 处理callback事件
     * @param payOutDetail 响应类
     */
    void dealCallBack(JSONObject payOutDetail);

    /**
     * 获取银行卡列表
     * @param userId 用户id
     * @return list
     */

    List<BankCardModel> getBankCardList(Long userId);

    /**
     * 设置 银行卡为主卡
     * @param userId 用户id
     * @param cardId card id
     */
    void setDefault(Long userId, Long cardId);
}
