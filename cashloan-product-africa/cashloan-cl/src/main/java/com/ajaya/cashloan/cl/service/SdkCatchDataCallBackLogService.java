package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.SdkCatchDataCallBackLog;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * epoch抓取数据回调日志表Service
 * 
 * @author yzq
 * @version 1.0.0
 * @date 2020-01-08 18:50:33
 */
public interface SdkCatchDataCallBackLogService extends BaseService<SdkCatchDataCallBackLog, Long> {
    /**
     * 通过sdk回调记录表查询是否能进行模型调用
     * @param userId 用户id
     * @param borrowId 订单id
     * @return 返回是否能进行模型调用
     */
    Boolean needRequestSdkMode(Long userId, Long borrowId);
}
