package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.ClUserEventStatistics;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 用户事件统计表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-07-01 11:40:32
 */
public interface ClUserEventStatisticsService extends BaseService<ClUserEventStatistics, Long>{

	public Integer save(Long userId, String imei, String pageId, String pageName);
}
