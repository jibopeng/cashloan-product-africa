package com.ajaya.cashloan.rc.mapper;

import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rc.domain.SimpleVoicesCount;

/**
 * 风控数据统计-（简）通话记录统计Dao
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:11:18

 */
@RDBatisDao
public interface SimpleVoicesCountMapper extends BaseMapper<SimpleVoicesCount, Long> {
	
	/**
	 * 通话记录总次数
	 * @param tableName
	 * @param userId
	 * @return
	 */
	int countOne(@Param("tableName")String tableName, @Param("userId")long userId);

	/**
	 * 统计近6个月均话费
	 * @param tableName
	 * @param userId
	 * @return
	 */
	Double countTwo(@Param("tableName")String tableName, @Param("phone")String phone, @Param("avgTime")String avgTime);
	
	/**
	 * 查询近6个月均话费
	 * @param tableName
	 * @param userId
	 * @return
	 */
	SimpleVoicesCount findByUserId(@Param("userId")long userId);
}
