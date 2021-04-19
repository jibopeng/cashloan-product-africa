package com.ajaya.cashloan.rule.mapper;

import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rule.domain.RuleInfo;

/**
 * 规则信息Dao
 * 
 * @author ctt
 * @version 1.0.0
 * @date 2016-12-20 13:58:48

 */
@RDBatisDao
public interface RuleInfoMapper extends BaseMapper<RuleInfo,Long> {

	int delInfoById(Long id);

	int updateSelective(Map<String, Object> paramMap);


}
