package com.ajaya.cashloan.rule.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.rule.domain.BorrowRuleResult;
import com.ajaya.cashloan.rule.model.ManageReviewModel;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rule.model.ManageRuleResultModel;

/**
 * 规则匹配结果Dao
 * 
 * @author ctt
 * @version 1.0.0
 * @date 2016-12-21 15:04:28

 */
@RDBatisDao
public interface BorrowRuleResultMapper extends BaseMapper<BorrowRuleResult,Long> {

	/**
	 * 查询规则名称
	 * @param borrowId
	 * @return
	 */
	List<ManageReviewModel> findRuleResult(long borrowId);

	/**
	 * 查询审核信息
	 * @param borrowId
	 * @return
	 */
	List<ManageRuleResultModel> findResult(long borrowId);

	/**
	 * 查询审核明细
	 * @param borrowId
	 * @param id 
	 * @return
	 */
	List<BorrowRuleResult> findRule(long borrowId, long id);

	List<BorrowRuleResult> findRule(Map<String, Object> search);
	
	/**
	 * 根据规则ID统计规则得分
	 * @param ruleId
	 * @return
	 */
	Integer sumScoreByRuleId(@Param("ruleId")Long ruleId,@Param("borrowId")Long borrowId);

	/**
	 * 根据表名查询表数量
	 * @param tableName
	 * @return
	 */
	int countTable(String tableName);

	/**
	 * 根据表名创建表
	 * @param tableName
	 */
	void createTable(@Param("tableName") String tableName);

	/**
	 * 分表查询规则名称
	 * @param tableName
	 * @param borrowId
	 * @return
	 */
	List<ManageReviewModel> findRuleResultByShard(@Param("tableName") String tableName, @Param("borrowId") long borrowId);

	/**
	 * 分表查询审核信息
	 * @param tableName
	 * @param borrowId
	 * @return
	 */
	List<ManageRuleResultModel> findResultByShard(@Param("tableName") String tableName, @Param("borrowId") long borrowId);

	/**
	 * 分表查询规则结果
	 * @param search
	 * @return
	 */
	List<BorrowRuleResult> findRuleByShard(Map<String, Object> search);

	/**
	 * 分表插入数据
	 * @param result
	 */
	void saveShard(@Param("tableName")String tableName, @Param("item")BorrowRuleResult result);

	/**
	 * 分表根据规则ID统计规则得分
	 * @param tableName
	 * @param engineId
	 * @param borrowId
	 * @return
	 */
	Integer sumScoreByRuleIdShard(@Param("tableName") String tableName, @Param("ruleId") Long ruleId, @Param("borrowId") Long borrowId);

	/**
	 * 分表查询借款订单规则结果
	 * @param tableName
	 * @param borrowId
	 * @return
	 */
	List<BorrowRuleResult> findBorrowRuleResultShard(@Param("tableName") String tableName, @Param("borrowId") Long borrowId);


}
