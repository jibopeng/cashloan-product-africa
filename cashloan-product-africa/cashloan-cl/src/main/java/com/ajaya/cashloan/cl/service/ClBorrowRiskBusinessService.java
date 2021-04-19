package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.ClBorrowRiskBusiness;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 风控订单业务表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-16 14:32:46
 */
public interface ClBorrowRiskBusinessService extends BaseService<ClBorrowRiskBusiness, Long>{

	/**
	 * 保存风控业务表
	 * 调用场景：保存订单的时候
	 * @param borrowId
	 * @return
	 */
	public Integer saveBorrowRiskBusiness(Long borrowId);
	
	/**
	 * 保存风控业务表
	 * 调用场景：有审核的时候
	 * @param borrowId
	 * @return
	 */
	public Integer saveBorrowRiskBusinessForAudit(Long borrowId);
	
	
	/**
	 * 保存风控业务表
	 * 调用场景：放款成功
	 * @param borrowId
	 * @return
	 */
	public Integer saveBorrowRiskBusinessForPay(Long borrowId);
	
	/**
	 * 保存风控业务表
	 * 调用场景：还款成功
	 * @param borrowId
	 * @return
	 */
	public Integer saveBorrowRiskBusinessForRepay(Long borrowId);
	
	
	/**
	 * 保存风控业务表
	 * 调用场景：逾期
	 * @param borrowId
	 * @return
	 */
	public Integer saveBorrowRiskBusinessForPenalty(Long borrowId);
}
