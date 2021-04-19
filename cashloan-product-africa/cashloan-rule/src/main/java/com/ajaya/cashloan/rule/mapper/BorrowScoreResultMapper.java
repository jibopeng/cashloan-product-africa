package com.ajaya.cashloan.rule.mapper;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rule.domain.BorrowScoreResult;

/**
 * 决策引擎执行记录Dao
 * 
 * @author caitt
 * @version 1.0.0
 * @date 2017-06-23 15:47:53

 */
@RDBatisDao
public interface BorrowScoreResultMapper extends BaseMapper<BorrowScoreResult, Long> {

    

}
