package com.ajaya.cashloan.rule.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.rule.domain.BorrowRuleResult;
import com.ajaya.cashloan.rule.model.ManageReviewModel;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rule.model.ManageRuleResultModel;

/**
 * 规则匹配结果Service
 * 
 * @author ctt
 * @version 1.0.0
 * @date 2016-12-21 15:04:28

 */
public interface BorrowRuleResultService extends BaseService<BorrowRuleResult, Long>{

	/**
	 * 查询匹配结果分页列表
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<BorrowRuleResult> borrowRuleResult(Map<String, Object> params,int currentPage,int pageSize);

	/**
	 * 分表查询规则结果
	 * @author luyu
	 * @date 2017-11-18
	 * @param borrowId
	 * @return
	 */
	List<ManageReviewModel> findRuleResult(long borrowId);

	/**
	 * 分表查询审核信息
	 * @author luyu
	 * @date 2017-11-18
	 * @param borrowId
	 * @return
	 */
	List<ManageRuleResultModel> findResult(long borrowId);
	
	/**
	 * 分表查询规则结果
	 * @param search
	 * @return
	 */
	List<BorrowRuleResult> findRule(Map<String, Object> search);

	/**
	 * 分表插入数据
	 * @param result
	 */
	void save(BorrowRuleResult result);

	/**
	 * 分表根据规则ID统计规则得分
	 * @param engineId
	 * @param id
	 * @return
	 */
	Integer sumScoreByRuleId(Long engineId, Long borrowId);

	/**
	 * 分表查询借款订单规则结果
	 * @param borrowId
	 * @return
	 */
	List<BorrowRuleResult> findBorrowRuleResult(Long borrowId);
}
