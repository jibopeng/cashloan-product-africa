package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.ClUserEventStatistics;
import com.ajaya.cashloan.cl.mapper.ClUserEventStatisticsMapper;
import com.ajaya.cashloan.cl.service.ClUserEventStatisticsService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 用户事件统计表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-07-01 11:40:32
 */
 
@Service("clUserEventStatisticsService")
public class ClUserEventStatisticsServiceImpl extends BaseServiceImpl<ClUserEventStatistics, Long> implements ClUserEventStatisticsService {
	
    private static final Logger logger = LoggerFactory.getLogger(ClUserEventStatisticsServiceImpl.class);
   
    @Resource
    private ClUserEventStatisticsMapper clUserEventStatisticsMapper;

	@Override
	public BaseMapper<ClUserEventStatistics, Long> getMapper() {
		return clUserEventStatisticsMapper;
	}

	@Override
	public Integer save(Long userId, String imei, String pageId, String pageName) {
//		logger.info("userId {} , imei {} , 保存事件统计", userId , imei);
		ClUserEventStatistics clUserEventStatistics = new ClUserEventStatistics();
		clUserEventStatistics.setUserId(userId);
		clUserEventStatistics.setImei(imei);
		clUserEventStatistics.setPageId(pageId);
		clUserEventStatistics.setPageName(pageName);
		clUserEventStatistics.setCreateTime(new Date());
		return clUserEventStatisticsMapper.save(clUserEventStatistics);
	}
	
}