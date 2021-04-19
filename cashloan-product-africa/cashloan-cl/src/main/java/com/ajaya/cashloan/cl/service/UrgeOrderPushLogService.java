package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UrgeOrderPushLog;
import com.ajaya.cashloan.core.common.service.BaseService;

import java.util.List;

/**
 * 催收系统订单推送表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-26 10:49:41
 */
public interface UrgeOrderPushLogService extends BaseService<UrgeOrderPushLog, Long> {

	public Integer pushUrgeOrder(Long borrowId);
	
	public Integer pushRepaymet(Long borrowId);
	
	public List getPenaltyAmountByOrderNo(String orderNo);
}
