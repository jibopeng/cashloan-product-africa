package com.ajaya.cashloan.rc.service;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rc.domain.SimpleContactCount;

/**
 * 风控数据统计-（简）通讯录统计Service
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:12:49
 */
public interface SimpleContactCountService extends BaseService<SimpleContactCount, Long>{
	
	/**
	 * 通讯录总条数
	 * @param userId
	 * @return
	 */
	int countOne(long userId);

}
