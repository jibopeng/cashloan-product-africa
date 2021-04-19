package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.ClBorrowRule;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.Borrow;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 订单规则表Service
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-06-18 19:04:15
 */
public interface ClBorrowRuleService extends BaseService<ClBorrowRule, Long> {

    Integer saveBorrowRule(Long borrowId, Long userId, JSONArray originalContact, JSONArray originalMsg, JSONArray originalApp);

    /**
     * 保存用户imei 和 永久黑规则
     *
     * @param borrow 订单
     * @param imei   imei
     * @param b      是否可以继续申请
     * @return 成功
     */
    Integer saveBorrowRule2(Borrow borrow, String imei, Boolean b);
    
    /**
     * 保存西瓜规则
     * @param borrowId
     * @param userId
     * @param xiGuaData
     * @return
     */
    Integer saveXiGuaBorrowRule(Long borrowId, Long userId, JSONObject xiGuaData);
}
