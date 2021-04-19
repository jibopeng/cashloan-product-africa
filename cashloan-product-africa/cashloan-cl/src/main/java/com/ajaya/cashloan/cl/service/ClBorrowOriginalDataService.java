package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.ClBorrowOriginalData;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * '订单原始数据记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-21 14:42:23
 */
public interface ClBorrowOriginalDataService extends BaseService<ClBorrowOriginalData, Long>{
	
	/**
	 * 保存原始数据
	 * @param userId
	 * @param borrowId
	 * @param type
	 * @param data
	 * @return
	 */
	public Integer saveOriginalData(Long userId, Long borrowId, String type , String data);

}
