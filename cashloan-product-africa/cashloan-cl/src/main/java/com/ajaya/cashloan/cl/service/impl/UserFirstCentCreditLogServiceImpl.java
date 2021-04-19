package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.UserFirstCentCreditLog;
import com.ajaya.cashloan.cl.mapper.UserFirstCentCreditLogMapper;
import com.ajaya.cashloan.cl.service.UserFirstCentCreditLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 'FC征信报告调用记录表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 15:19:50
 */
 
@Service("userFirstCentCreditLogService")
public class UserFirstCentCreditLogServiceImpl extends BaseServiceImpl<UserFirstCentCreditLog, Long> implements UserFirstCentCreditLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserFirstCentCreditLogServiceImpl.class);
   
    @Resource
    private UserFirstCentCreditLogMapper userFirstCentCreditLogMapper;

	@Override
	public BaseMapper<UserFirstCentCreditLog, Long> getMapper() {
		return userFirstCentCreditLogMapper;
	}

	@Override
	public Integer saveUserFirstCentCreditLog(Long userId, Long borrowId, String bvn, String type, String requestParam,
			String response) {
		UserFirstCentCreditLog userFirstCentCreditLog = new UserFirstCentCreditLog();
		userFirstCentCreditLog.setUserId(userId);
		userFirstCentCreditLog.setBorrowId(borrowId);
		userFirstCentCreditLog.setBvn(bvn);
		userFirstCentCreditLog.setType(type);
		userFirstCentCreditLog.setRequestParam(requestParam);
		userFirstCentCreditLog.setResponse(response);
		userFirstCentCreditLog.setCreateTime(new Date());
		return userFirstCentCreditLogMapper.save(userFirstCentCreditLog);
	}
	
}