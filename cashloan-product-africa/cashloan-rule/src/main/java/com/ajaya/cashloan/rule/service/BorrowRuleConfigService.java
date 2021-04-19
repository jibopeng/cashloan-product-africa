package com.ajaya.cashloan.rule.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rule.domain.BorrowRuleConfig;
import com.ajaya.cashloan.rule.model.BorrowRuleConfigModel;

/**
 * 借款规则详细配置表Service
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-04-21 15:23:19
 */
public interface BorrowRuleConfigService extends BaseService<BorrowRuleConfig, Long>{

	List<BorrowRuleConfigModel> findConfig(Map<String, Object> params);

	void deleteByMap(Map<String, Object> map);

	List<BorrowRuleConfig> findBorrowRuleId(Map<String, Object> paramMap);

}
