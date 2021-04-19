package com.ajaya.cashloan.cl.service.impl;

import com.ajaya.cashloan.cl.domain.UserRemark;
import com.ajaya.cashloan.cl.mapper.UserRemarkMapper;
import com.ajaya.cashloan.cl.service.UserRemarkService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 本人备注记录表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-11 21:32:04
 */
 
@Service("userRemarkService")
public class UserRemarkServiceImpl extends BaseServiceImpl<UserRemark, Long> implements UserRemarkService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserRemarkServiceImpl.class);
   
    @Resource
    private UserRemarkMapper userRemarkMapper;

	@Override
	public BaseMapper<UserRemark, Long> getMapper() {
		return userRemarkMapper;
	}

	@Override
	public int saveOrUpdateRemark(Long userId, String remark) {
		Integer result = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserRemark userRemark = userRemarkMapper.findSelective(paramMap);
		if (userRemark == null) {
			userRemark = new UserRemark();
			userRemark.setUserId(userId);
			userRemark.setRemark(remark);
			userRemark.setCreateTime(new Date());
			result = userRemarkMapper.save(userRemark);
		} else {
			userRemark.setRemark(remark);
			userRemark.setUpdateTime(new Date());
			result = userRemarkMapper.update(userRemark);
		}
		
		return result;
	}

	@Override
	public UserRemark findByUserId(Long userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserRemark userRemark = userRemarkMapper.findLast(userId);
		return userRemark;
	}
	
}