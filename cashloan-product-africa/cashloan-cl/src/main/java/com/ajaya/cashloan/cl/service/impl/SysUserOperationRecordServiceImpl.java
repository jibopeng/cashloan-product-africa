package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.SysUserOperationRecord;
import com.ajaya.cashloan.cl.mapper.SysUserOperationRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.service.SysUserOperationRecordService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 系统人员操作记录表ServiceImpl
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-10-28 16:49:23
 */
 
@Service("sysUserOperationRecordService")
public class SysUserOperationRecordServiceImpl extends BaseServiceImpl<SysUserOperationRecord, Long> implements SysUserOperationRecordService {
	
    private static final Logger logger = LoggerFactory.getLogger(SysUserOperationRecordServiceImpl.class);
   
    @Resource
    private SysUserOperationRecordMapper sysUserOperationRecordMapper;

	@Override
	public BaseMapper<SysUserOperationRecord, Long> getMapper() {
		return sysUserOperationRecordMapper;
	}
	
}