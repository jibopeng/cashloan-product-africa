package com.ajaya.cashloan.cl.service;


import com.ajaya.cashloan.cl.domain.EpidemicRegionRiskLevel;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 城市申请禁止Service
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-05-08 16:09:44
 */
public interface EpidemicRegionRiskLevelService extends BaseService<EpidemicRegionRiskLevel, Long> {

    /**
     * 根据放款地址判断该订单能否进行申请
     *
     * @param borrowAddress 订单申请地址
     * @param borrowId      订单id
     * @return true 可以申请 false 不可以申请
     */
    boolean canBorrowByBorrowAddress(Long borrowId, String borrowAddress);

}
