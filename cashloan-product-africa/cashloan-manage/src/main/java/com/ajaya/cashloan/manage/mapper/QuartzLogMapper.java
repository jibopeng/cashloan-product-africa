package com.ajaya.cashloan.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.manage.domain.QuartzLog;
import com.ajaya.cashloan.manage.model.QuartzLogModel;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 定时任务记录Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-15 13:38:29

 */
@RDBatisDao
public interface QuartzLogMapper extends BaseMapper<QuartzLog,Long> {

	/**
	 * 据quartId查询最后执行时间
	 * @param id
	 * @return
	 */
	String findLastTimeByQzInfoId(@Param("quartzId") Long quartId);

	/**
	 * 日志查询
	 * @param searchMap
	 * @return
	 */
	List<QuartzLogModel> page(Map<String, Object> searchMap);

    

}
