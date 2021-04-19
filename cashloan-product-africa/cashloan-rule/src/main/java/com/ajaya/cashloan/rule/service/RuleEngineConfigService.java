package com.ajaya.cashloan.rule.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rule.domain.RuleEngineConfig;

/**
 * 规则引擎管理Service
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2016-12-12 17:25:31
 */
public interface RuleEngineConfigService extends BaseService<RuleEngineConfig, Long>{

	List<RuleEngineConfig> findByMap(Map<String, Object> search);

	List<Map<String, Object>> findColumnByName(Map<String, Object> map);

	List<Map<String, Object>> findTable();

	int updateSelective(Map<String, Object> map);

	 
	List<Map<String, Object>> findAllInfo(Map<String, Object> map);

	int saveOrUpate(Map<String, Object> rule, List list,String datalist,
			HttpServletRequest request);
	/**
	 * 根据表名查询数据表信息
	 * @param paramMap
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> findTableByName(Map<String, Object> paramMap);
}
