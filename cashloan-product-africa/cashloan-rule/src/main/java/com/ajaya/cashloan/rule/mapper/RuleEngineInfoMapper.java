package com.ajaya.cashloan.rule.mapper;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rule.domain.RuleEngineInfo;

/**
 * 规则评分设置管理Dao
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-01-03 17:28:16

 */
@RDBatisDao
public interface RuleEngineInfoMapper extends BaseMapper<RuleEngineInfo,Long> {

	int insert(RuleEngineInfo info);

	int deleteInfoByRuleId(long id);

    

}
