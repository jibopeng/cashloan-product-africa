package com.ajaya.cashloan.cl.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.PayReqLog;
import com.ajaya.cashloan.cl.model.ManagePayReqLogModel;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 支付请求记录Service
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017-03-07 16:18:30
 */
public interface PayReqLogService extends BaseService<PayReqLog, Long>{

	/**
	 * 保存请求记录
	 * 
	 * @param log
	 * @return
	 */
	boolean save(PayReqLog log);

	/**
	 * 根据订单号查询请求记录
	 * 
	 * @param orderNo
	 * @return
	 */
	PayReqLog findByOrderNo(String orderNo);

	/**
	 * 分页查询
	 * 
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ManagePayReqLogModel> page(int current, int pageSize,
			Map<String, Object> searchMap);

	/**
	 * 查询详情
	 * 
	 * @param id
	 * @return
	 */
	ManagePayReqLogModel findDetail(Long id);

}
