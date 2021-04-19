package com.ajaya.cashloan.cl.service;


import java.util.Map;

import com.ajaya.cashloan.cl.model.DayPassApr;
import com.ajaya.cashloan.cl.model.SystemDayData;
import com.github.pagehelper.Page;

/**
 * 平台数据日报
 * @author caitt
 * @version 1.0
 * @date 2017年3月20日下午4:56:21
 */
public interface SystemRcService {

	/**
	 * 平台数据日报
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<SystemDayData> findDayData(Map<String,Object> params, Integer current, Integer pageSize);
	
	/**
	 * 每日通过率
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<DayPassApr> findDayApr(Map<String,Object> params, Integer current, Integer pageSize);
}
