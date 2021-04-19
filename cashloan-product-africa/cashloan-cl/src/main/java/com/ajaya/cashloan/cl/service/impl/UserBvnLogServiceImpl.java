package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UserBvnLog;
import com.ajaya.cashloan.cl.mapper.UserBvnLogMapper;
import com.ajaya.cashloan.cl.service.UserBvnLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * '用户bvn日志表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-15 17:29:07
 */
 
@Service("userBvnLogService")
public class UserBvnLogServiceImpl extends BaseServiceImpl<UserBvnLog, Long> implements UserBvnLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserBvnLogServiceImpl.class);
   
    @Resource
    private UserBvnLogMapper userBvnLogMapper;

	@Override
	public BaseMapper<UserBvnLog, Long> getMapper() {
		return userBvnLogMapper;
	}
	
}