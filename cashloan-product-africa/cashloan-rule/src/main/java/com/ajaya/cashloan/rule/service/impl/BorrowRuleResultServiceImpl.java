package com.ajaya.cashloan.rule.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.rule.domain.BorrowRuleResult;
import com.ajaya.cashloan.rule.model.ManageReviewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.ShardTableUtil;
import com.ajaya.cashloan.rule.mapper.BorrowRuleResultMapper;
import com.ajaya.cashloan.rule.model.ManageRuleResultModel;
import com.ajaya.cashloan.rule.service.BorrowRuleResultService;


/**
 * 规则匹配结果ServiceImpl
 * 
 * @author ctt
 * @version 1.0.0
 * @date 2016-12-21 15:04:28

 */
 
@Service("borrowRuleResultService")
public class BorrowRuleResultServiceImpl extends BaseServiceImpl<BorrowRuleResult, Long> implements BorrowRuleResultService {
	
    private static final Logger logger = LoggerFactory.getLogger(BorrowRuleResultServiceImpl.class);
   
    @Resource
    private BorrowRuleResultMapper borrowRuleResultMapper;

	@Override
	public BaseMapper<BorrowRuleResult, Long> getMapper() {
		return borrowRuleResultMapper;
	}

	@Override
	public Page<BorrowRuleResult> borrowRuleResult(Map<String, Object> params, int currentPage, int pageSize) {
		/*****  luyu 2017-11-18  *****/
		String tableName = getTableName(Long.valueOf(params.get("borrowId").toString()));
		params.put("tableName", tableName);
		/*****  luyu 2017-11-18  *****/
		PageHelper.startPage(currentPage, pageSize);
		List<BorrowRuleResult> list = borrowRuleResultMapper.listSelective(params);
		return (Page<BorrowRuleResult>)list;
	}

	@Override
	public List<ManageReviewModel> findRuleResult(long borrowId) {
		String tableName = getTableName(borrowId);
		List<ManageReviewModel> ruleList = borrowRuleResultMapper.findRuleResultByShard(tableName, borrowId);
		return ruleList;
	}

	@Override
	public List<ManageRuleResultModel> findResult(long borrowId) {
		String tableName = getTableName(borrowId);
		List<ManageRuleResultModel> result = borrowRuleResultMapper.findResultByShard(tableName, borrowId);
		return result;
	}

	@Override
	public List<BorrowRuleResult> findRule(Map<String, Object> search) {
		String tableName = getTableName(Long.valueOf(search.get("borrowId").toString()));
		search.put("tableName", tableName);
		List<BorrowRuleResult> infoList = borrowRuleResultMapper.findRuleByShard(search);
		return infoList;
	}

	@Override
	public void save(BorrowRuleResult result) {
		String tableName = getTableName(result.getBorrowId());
		borrowRuleResultMapper.saveShard(tableName, result);
	}

	@Override
	public Integer sumScoreByRuleId(Long engineId, Long borrowId) {
		String tableName = getTableName(borrowId);
		Integer score = borrowRuleResultMapper.sumScoreByRuleIdShard(tableName, engineId, borrowId);
		return score;
	}

	@Override
	public List<BorrowRuleResult> findBorrowRuleResult(Long borrowId) {
		String tableName = getTableName(borrowId);
		List<BorrowRuleResult> resultList = borrowRuleResultMapper.findBorrowRuleResultShard(tableName, borrowId);
		return resultList;
	}
	
	
	public String getTableName(Long borrowId){
		// 分表
		String tableName = ShardTableUtil.generateTableNameById("arc_borrow_rule_result", borrowId, 80000);
		if("arc_borrow_rule_result_1".equals(tableName)){
			tableName = "arc_borrow_rule_result";
		}
		int countTable = borrowRuleResultMapper.countTable(tableName);
		if (countTable == 0) {
			borrowRuleResultMapper.createTable(tableName);
		}
		return tableName;
	}
	
}