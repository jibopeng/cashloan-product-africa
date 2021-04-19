package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.BorrowRepayLogFq;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 分期还款记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:30
 */
public interface BorrowRepayLogFqService extends BaseService<BorrowRepayLogFq, Long>{

	/**
	 * 分期还款成功回调处理业务
	 * @param borrowRepayFqId
	 * @return
	 */
	public Integer repaymentNotifyFq(Long borrowRepayFqId, String payOrderNo, Double amount);
	
}
