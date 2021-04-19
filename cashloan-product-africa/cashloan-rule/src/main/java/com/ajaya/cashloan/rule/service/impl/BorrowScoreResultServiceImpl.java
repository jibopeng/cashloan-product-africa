package com.ajaya.cashloan.rule.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.rule.domain.BorrowScoreResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.rule.mapper.BorrowScoreResultMapper;
import com.ajaya.cashloan.rule.service.BorrowScoreResultService;

/**
 * 决策引擎执行记录ServiceImpl
 * 
 * @author caitt
 * @version 1.0.0
 * @date 2017-06-23 15:47:53

 */
 
@Service("borrowScoreResultService")
public class BorrowScoreResultServiceImpl extends BaseServiceImpl<BorrowScoreResult, Long> implements BorrowScoreResultService {
	
    private static final Logger logger = LoggerFactory.getLogger(BorrowScoreResultServiceImpl.class);
   
    @Resource
    private BorrowScoreResultMapper borrowScoreResultMapper;

	@Override
	public BaseMapper<BorrowScoreResult, Long> getMapper() {
		return borrowScoreResultMapper;
	}
	
}