package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UserVoiceSmsLog;
import com.ajaya.cashloan.cl.mapper.UserVoiceSmsLogMapper;
import com.ajaya.cashloan.cl.service.UserVoiceSmsLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * 用户语音验证日志表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-02 16:55:42
 */
 
@Service("userVoiceSmsLogService")
public class UserVoiceSmsLogServiceImpl extends BaseServiceImpl<UserVoiceSmsLog, Long> implements UserVoiceSmsLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserVoiceSmsLogServiceImpl.class);
   
    @Resource
    private UserVoiceSmsLogMapper userVoiceSmsLogMapper;

	@Override
	public BaseMapper<UserVoiceSmsLog, Long> getMapper() {
		return userVoiceSmsLogMapper;
	}
	
}