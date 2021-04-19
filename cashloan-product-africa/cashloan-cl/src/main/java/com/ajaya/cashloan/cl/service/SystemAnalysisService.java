package com.ajaya.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.model.OverdueAnalisisModel;
import com.ajaya.cashloan.cl.model.RepayAnalisisModel;
import com.github.pagehelper.Page;

/**
 * 运营分析
 * @author caitt
 * @version 1.0
 * @date 2017年3月21日下午3:00:15
 */
public interface SystemAnalysisService {

	/**
	 * 每月还款统计
	 * @param params
	 * @return
	 */
	List<RepayAnalisisModel> monthRepayAnalisis(Map<String,Object> params);
	
	/**
	 * 每日还款统计
	 * @param params
	 * @return
	 */
	List<RepayAnalisisModel> dayRepayAnalisis(Map<String,Object> params);

	/**
	 * 每月逾期统计
	 * @param params
	 * @return
	 */
	Page<OverdueAnalisisModel> overdueAnalisis(Map<String,Object> params, Integer current, Integer pageSize);
}
