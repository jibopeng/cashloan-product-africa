package com.ajaya.cashloan.cl.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ajaya.cashloan.cl.domain.SdkCatchDataSyncLog;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * epoch抓取数据同步回调日志表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-14 12:21:01
 */
@RDBatisDao
public interface SdkCatchDataSyncLogMapper extends BaseMapper<SdkCatchDataSyncLog, Long> {
	
	@Select("select ifnull((select count(1) from cl_sdk_catch_data_sync_log where borrow_id = #{borrowId} and type = #{type}) , 0)")
	Integer checkFinishedByType(@Param("borrowId")Long borrowId, @Param("type")String type);
	
}
