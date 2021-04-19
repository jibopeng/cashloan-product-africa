package com.ajaya.cashloan.cl.service.impl;

import com.ajaya.cashloan.cl.domain.UrgePushStateRecord;
import com.ajaya.cashloan.cl.mapper.UrgePushStateRecordMapper;
import com.ajaya.cashloan.cl.service.UrgePushStateRecordService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 催收推送状态表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-04-28 11:30:15
 */
 
@Service("urgePushStateRecordService")
public class UrgePushStateRecordServiceImpl extends BaseServiceImpl<UrgePushStateRecord, Long> implements UrgePushStateRecordService {
	
    private static final Logger logger = LoggerFactory.getLogger(UrgePushStateRecordServiceImpl.class);
   
    @Resource
    private UrgePushStateRecordMapper urgePushStateRecordMapper;

	@Override
	public BaseMapper<UrgePushStateRecord, Long> getMapper() {
		return urgePushStateRecordMapper;
	}
	
}