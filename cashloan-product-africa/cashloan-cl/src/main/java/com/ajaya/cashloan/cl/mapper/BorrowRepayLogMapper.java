package com.ajaya.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.BorrowRepayLog;
import com.ajaya.cashloan.cl.model.BorrowRepayLogModel;
import com.ajaya.cashloan.cl.model.ManageBRepayLogModel;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 还款计录Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 13:46:12
 */
@RDBatisDao
public interface BorrowRepayLogMapper extends BaseMapper<BorrowRepayLog,Long> {
	
	/**
	 * 根据借款id查询
	 * @param borrowId 订单id
	 * @return BorrowRepayLog
	 */
	BorrowRepayLog findByBorrowId(long borrowId);

	List<ManageBRepayLogModel> listModel(Map<String, Object> params);

	/**
	 * 查询所有
	 * @param searchMap 搜索条件
	 * @return 结果列表
	 */
	List<BorrowRepayLogModel> listSelModel(Map<String, Object> searchMap);

    /**
     * 退还补扣修改还款记录
     * @param paramMap 查找条件
     * @return int
     */
	int refundDeduction(Map<String, Object> paramMap);
	
	/**
	 * 查询用户还款次数
	 * @param userId 用户id
	 * @return 还款次数
	 */
	int findRepayNumbers(@Param("userId")Long userId, @Param("borrowId")Long borrowId);

	/**
	 * 复借天数
	 * @param userId 用户id
	 * @param createTime 日期
	 * @return BorrowRepayLog
	 */
	BorrowRepayLog findLastRepay(@Param("userId")Long userId, @Param("createTime")String createTime);

	
	Integer saveFujie(Map<String, Object> map);

	/**
	 * 分期还款记录查询
	 * @param params 分期记录查询
	 * @return 结果列表
	 */
	List<ManageBRepayLogModel> listModelFq(Map<String, Object> params);
}
