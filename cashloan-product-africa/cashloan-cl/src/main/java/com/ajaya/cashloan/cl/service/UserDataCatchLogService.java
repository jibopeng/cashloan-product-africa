package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserDataCatchLog;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 原始数据抓取记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-01 18:01:26
 */
public interface UserDataCatchLogService extends BaseService<UserDataCatchLog, Long>{

	Integer save(Long borrowId, Long userId);
	
	UserDataCatchLog findByBorrwId(Long borrowId);

}
