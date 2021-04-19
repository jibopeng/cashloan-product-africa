package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.BorrowRepayFq;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 分期还款计划表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:01
 */
@RDBatisDao
public interface BorrowRepayFqMapper extends BaseMapper<BorrowRepayFq, Long> {

	/**
	 * 查询该订单之前成功还清过几次分期
	 * @param borrowId 订单id
	 * @return 还清次数
	 */
	Integer getCountSuccessFq(@Param("borrowId")Long borrowId);
	
	/**
	 * 获取已经还清的钱
	 * @param borrowId 订单id
	 * @return 已还清次数
	 */
	Double getSuccessMoneyFq(@Param("borrowId")Long borrowId);

	/**
	 * 获取最近一笔分期还款计划(未还款的)
	 * @param borrowId 订单id
	 * @return 分期还款计划
	 */
	BorrowRepayFq getCurrentBorrowRepayFq(@Param("borrowId")Long borrowId);

}
