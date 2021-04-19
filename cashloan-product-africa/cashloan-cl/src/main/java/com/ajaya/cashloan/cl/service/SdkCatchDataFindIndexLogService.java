package com.ajaya.cashloan.cl.service;


import com.ajaya.cashloan.cl.domain.SdkCatchDataFindIndexLog;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.Borrow;

/**
 * sdk抓取数据findindex控制记录日志表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-24 10:52:14
 */
public interface SdkCatchDataFindIndexLogService extends BaseService<SdkCatchDataFindIndexLog, Long> {
    /**
     * 判断订单是否需要进行epoch元数据抓取
     * @param borrow 订单实体
     * @return ture or false
     */
    boolean needCatchData(Borrow borrow);
}
