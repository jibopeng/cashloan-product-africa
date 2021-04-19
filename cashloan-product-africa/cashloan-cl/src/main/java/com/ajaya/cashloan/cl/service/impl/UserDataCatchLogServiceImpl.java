package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UserDataCatchLog;
import com.ajaya.cashloan.cl.mapper.UserDataCatchLogMapper;
import com.ajaya.cashloan.cl.service.UserDataCatchLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 原始数据抓取记录表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-01 18:01:26
 */
 
@Service("userDataCatchLogService")
public class UserDataCatchLogServiceImpl extends BaseServiceImpl<UserDataCatchLog, Long> implements UserDataCatchLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserDataCatchLogServiceImpl.class);
   
    @Resource
    private UserDataCatchLogMapper userDataCatchLogMapper;

	@Override
	public BaseMapper<UserDataCatchLog, Long> getMapper() {
		return userDataCatchLogMapper;
	}

	@Override
	public Integer save(Long borrowId,Long userId) {
		UserDataCatchLog userDataCatchLog = new UserDataCatchLog();
		userDataCatchLog.setUserId(userId);
		userDataCatchLog.setBorrowId(borrowId);
		userDataCatchLog.setApp("0");
		userDataCatchLog.setContact("0");
		userDataCatchLog.setMsg("0");
		userDataCatchLog.setCamera("0");
		userDataCatchLog.setCreateTime(new Date());
		return userDataCatchLogMapper.save(userDataCatchLog);
	}

	@Override
	public UserDataCatchLog findByBorrwId(Long borrowId) {
		Map<String, Object> map = new HashMap<>();
		map.put("borrowId", borrowId);
		return userDataCatchLogMapper.findSelective(map);
	}
	
}