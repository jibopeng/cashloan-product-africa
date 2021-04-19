package com.ajaya.cashloan.cl.service;


import com.ajaya.cashloan.cl.domain.SdkCatchDataSyncLog;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * epoch抓取数据同步回调日志表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-14 12:21:01
 */
public interface SdkCatchDataSyncLogService extends BaseService<SdkCatchDataSyncLog, Long> {

	/**
	 * 通过抓取项,判断是否可以走风控
	 * @param userId
	 * @param borrowId
	 * @return
	 */
	public Boolean needRequestSdkModel(Long userId, Long borrowId);
}
