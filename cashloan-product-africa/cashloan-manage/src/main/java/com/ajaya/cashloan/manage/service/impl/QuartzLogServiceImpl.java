package com.ajaya.cashloan.manage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.manage.domain.QuartzLog;
import com.ajaya.cashloan.manage.model.QuartzLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.manage.mapper.QuartzLogMapper;
import com.ajaya.cashloan.manage.service.QuartzLogService;


/**
 * 定时任务记录ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-15 13:38:29

 */
@SuppressWarnings("unused")
@Service("quartzLogService")
public class QuartzLogServiceImpl extends BaseServiceImpl<QuartzLog, Long> implements QuartzLogService {
	
    
	private static final Logger logger = LoggerFactory.getLogger(QuartzLogServiceImpl.class);
   
    @Resource
    private QuartzLogMapper quartzLogMapper;




	@Override
	public BaseMapper<QuartzLog, Long> getMapper() {
		return quartzLogMapper;
	}




	@Override
	public int save(QuartzLog ql) {
		return quartzLogMapper.save(ql);
	}




	@Override
	public Page<QuartzLogModel> page(Map<String, Object> searchMap,
                                     int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<QuartzLogModel> list = quartzLogMapper.page(searchMap);
		return (Page<QuartzLogModel>)list;
	}
	
}