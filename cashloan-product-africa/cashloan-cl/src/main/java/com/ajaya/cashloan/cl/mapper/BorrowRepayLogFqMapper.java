package com.ajaya.cashloan.cl.mapper;

import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.cl.domain.BorrowRepayLogFq;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 分期还款记录表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:30
 */
@RDBatisDao
public interface BorrowRepayLogFqMapper extends BaseMapper<BorrowRepayLogFq, Long> {

	/**
	 * 获取最近一笔分期还款
	 * @param borrowId 订单id
	 * @return 还款信息
	 */
     BorrowRepayLogFq getLastBorrowRepayLogFq(@Param("borrowId") Long borrowId);

}
