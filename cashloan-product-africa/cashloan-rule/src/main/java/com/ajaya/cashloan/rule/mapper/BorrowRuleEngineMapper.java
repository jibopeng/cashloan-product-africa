package com.ajaya.cashloan.rule.mapper;

import java.util.List;

import com.ajaya.cashloan.rule.domain.BorrowRuleEngine;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 借款规则管理Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2016-12-20 10:22:30

 */
@RDBatisDao
public interface BorrowRuleEngineMapper extends BaseMapper<BorrowRuleEngine,Long> {

	int deleteById(long id);
	
	List<BorrowRuleEngine> listByBorrowType(@Param("borrowType")String borrowType);

}
