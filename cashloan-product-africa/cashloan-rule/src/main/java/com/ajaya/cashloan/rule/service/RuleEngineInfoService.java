package com.ajaya.cashloan.rule.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rule.domain.RuleEngineInfo;

/**
 * 规则评分设置管理Service
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-01-03 17:28:16

 */
public interface RuleEngineInfoService extends BaseService<RuleEngineInfo, Long>{
	/**
	 * 保存
	 * @param rule
	 * @param list
	 * @return
	 */
	int saveIntegralInfo(Map<String, Object> rule, List list);
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	List<RuleEngineInfo> findByMap(Map<String, Object> search);
}
