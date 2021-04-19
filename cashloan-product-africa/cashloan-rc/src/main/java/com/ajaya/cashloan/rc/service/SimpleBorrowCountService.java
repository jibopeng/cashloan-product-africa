package com.ajaya.cashloan.rc.service;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rc.domain.SimpleBorrowCount;

/**
 * 风控数据统计-（简）借款统计Service
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:12:18
 */
public interface SimpleBorrowCountService extends BaseService<SimpleBorrowCount, Long>{
	
	/**
     * 借款人有逾期30天以上已还借款数
     * @param userId
     * @return
     */
    int countOne(long userId);
    
}
