package com.ajaya.cashloan.cl.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UserAuthTime;
import com.ajaya.cashloan.cl.mapper.UserAuthTimeMapper;
import com.ajaya.cashloan.cl.service.UserAuthTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 用户认证时间记录表ServiceImpl
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-10-30 15:59:03
 */
 
@Service("userAuthTimeService")
public class UserAuthTimeServiceImpl extends BaseServiceImpl<UserAuthTime, Long> implements UserAuthTimeService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserAuthTimeServiceImpl.class);
   
    @Resource
    private UserAuthTimeMapper userAuthTimeMapper;

	@Override
	public BaseMapper<UserAuthTime, Long> getMapper() {
		return userAuthTimeMapper;
	}

	@Override
	public UserAuthTime getUserAuthTimeByUserId(Long user_id) {
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", user_id);
		return userAuthTimeMapper.findSelective(paramMapForUserId);
	}

	@Override
	public void updateSelectiveByObject(UserAuthTime userAuthTime) {
		logger.info("用户 {} 更新认证时间成功",userAuthTime.getUserId());
		userAuthTimeMapper.updateSelectiveByObject(userAuthTime);
	}
	
	
	
}