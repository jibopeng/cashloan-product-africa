package com.ajaya.cashloan.rc.service;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rc.domain.SimpleVoicesCount;

/**
 * 风控数据统计-（简）通话记录统计Service
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:11:18
 */
public interface SimpleVoicesCountService extends BaseService<SimpleVoicesCount, Long>{
	
	/**
	 * 通话记录总次数
	 * @param userId
	 * @return
	 */
	int countOne(long userId);

	/**
	 * 根据userId获取最新一条数据
	 * @param userId 用户id
	 * @return
	 */
	SimpleVoicesCount findByUserId(long userId);
}
