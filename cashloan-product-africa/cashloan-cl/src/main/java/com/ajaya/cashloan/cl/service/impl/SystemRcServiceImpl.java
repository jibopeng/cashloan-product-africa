package com.ajaya.cashloan.cl.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.mapper.SystemRcMapper;
import com.ajaya.cashloan.cl.model.DayPassApr;
import com.ajaya.cashloan.cl.model.SystemDayData;
import com.ajaya.cashloan.cl.service.SystemRcService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * 平台数据日报
 * @author caitt
 * @version 1.0
 * @date 2017年3月20日下午4:56:46
 */
@Service("systemRcService")
public class SystemRcServiceImpl implements SystemRcService {
	
	@Resource
	private SystemRcMapper systemRcMapper;

	@Override
	public Page<SystemDayData> findDayData(Map<String,Object> params, Integer current, Integer pageSize) {
		PageHelper.startPage(current, pageSize);
		Page<SystemDayData> page = (Page<SystemDayData>) systemRcMapper.dayData(params);
		return page;
	}

	@Override
	public Page<DayPassApr> findDayApr(Map<String,Object> params, Integer current, Integer pageSize) {
		PageHelper.startPage(current, pageSize);
		List<DayPassApr> list = (Page<DayPassApr>) systemRcMapper.dayApr(params);
		/*NumberFormat nf = NumberFormat.getInstance();
		for (DayPassApr dayPassApr : list) {
			dayPassApr.setBorrowPassApr(nf.format(Double.parseDouble(dayPassApr.getBorrowPassApr())));
			dayPassApr.setBorrowApr(nf.format(Double.parseDouble(dayPassApr.getBorrowApr())));
		}*/
		return (Page<DayPassApr>)list;
	}

}
