package com.ajaya.cashloan.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.manage.model.QuartzInfoModel;
import com.ajaya.cashloan.manage.domain.QuartzInfo;

/**
 * 定时任务详情Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-15 13:30:53

 */
@RDBatisDao
public interface QuartzInfoMapper extends BaseMapper<QuartzInfo, Long> {

	/**
	 * 定时任务查询
	 * 
	 * @param map
	 * @return
	 */
	List<QuartzInfoModel> page(Map<String, Object> map);
}
