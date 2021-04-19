package com.ajaya.cashloan.cl.service;


import com.ajaya.cashloan.cl.domain.UserVirtualAccount;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.alibaba.fastjson.JSONObject;

/**
 * 用户虚拟账户表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-03 10:42:48
 */
public interface UserVirtualAccountService extends BaseService<UserVirtualAccount, Long> {
    /**
     *  查找用户支付虚拟账户
     * @param userId 用户id
     * @param appFlag app标识
     * @param borrowId 订单id
     * @return ResponseWrapper
     */
    ResponseWrapper getBankTransferInfo(Long userId, String appFlag, Long borrowId);
    /**
     *  生成支付虚拟账户
     * @param userId 用户id
     * @param appFlag app标识
     * @return ResponseWrapper
     */

     void createBankTransferInfo(Long userId, String appFlag);

    /**
     * 拉取ussd银行列表
     * @param userId 用户id
     * @param appFlag app标识
     * @param borrowId 订单id
     * @return ResponseWrapper
     */
    ResponseWrapper getUssdListInfo(Long userId, String appFlag, Long borrowId);

    /**
     * 处理还款
     * @param payOutDetail 回调详情
     */
    void dealTransferCallBack(JSONObject payOutDetail);
}
