package com.ajaya.cashloan.rule.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rule.domain.BorrowRuleConfig;

/**
 * 借款规则详细配置表Dao
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-04-21 15:23:19
 */
@RDBatisDao
public interface BorrowRuleConfigMapper extends BaseMapper<BorrowRuleConfig,Long> {

	int deleteByBorrowRuleId(Map<String, Object> params);

	void deleteById(Long id);

	void deleteByMap(Map<String, Object> params);

	List<BorrowRuleConfig> findBorrowRuleId(Map<String, Object> paramMap);

    

}
