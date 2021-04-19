package com.ajaya.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrder;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrderLog;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.Borrow;

/**
 * 催款记录表Service
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-03-07 14:28:22
 */
public interface UrgeRepayOrderLogService extends BaseService<UrgeRepayOrderLog, Long>{
	/**
	 * 催款记录信息
	 * @param params
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UrgeRepayOrderLog> list(Map<String, Object> params, int current,
			int pageSize);
	/**
	 * 根据条件查询催款记录信息
	 * @param id
	 * @return
	 */
	List<UrgeRepayOrderLog> getLogByParam(Map<String, Object> params);
	
	/**
	 * 保存催款记录信息
	 * @param params
	 * @return
	 */
	int saveOrderInfo(UrgeRepayOrderLog  orderLog,UrgeRepayOrder order, String answerState);
	
	/**
	 * 查询用户的催收状态
	 * @param borrowList
	 * @return
	 */
	List<Map<String, Object>> getCuiShouMsgListByBorrow(List<Borrow> borrowList);
	

}
