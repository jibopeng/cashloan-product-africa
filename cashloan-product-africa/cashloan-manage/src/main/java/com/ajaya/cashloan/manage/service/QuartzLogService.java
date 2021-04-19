package com.ajaya.cashloan.manage.service;

import java.util.Map;

import com.ajaya.cashloan.manage.domain.QuartzLog;
import com.ajaya.cashloan.manage.model.QuartzLogModel;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 定时任务记录Service
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-15 13:38:29

 */
public interface QuartzLogService extends BaseService<QuartzLog, Long>{

	/**
	 * 保存日志
	 */
	int save(QuartzLog ql);

	Page<QuartzLogModel> page(Map<String, Object> searchMap, int current,
                              int pageSize);

}
