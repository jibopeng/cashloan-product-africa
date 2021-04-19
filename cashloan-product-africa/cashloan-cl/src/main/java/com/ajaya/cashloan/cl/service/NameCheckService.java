package com.ajaya.cashloan.cl.service;

import java.util.Map;

/**
 * @author ryan
 * @version 1.0 2020/12/19
 */
public interface NameCheckService {
    /**
     * 根据borrowId进行epoch姓名校验
     * @param borrowId 订单id
     * @return 是否成功
     */
    int nameCheck(Long borrowId);

    /**
     * 查询用户的所有名字
     * @param userId 用户id
     * @return 姓名列表map
     */

    Map<String,String> getAllNameAndStatusByUserId(Long userId);
}
