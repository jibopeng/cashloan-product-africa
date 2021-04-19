package com.ajaya.cashloan.rc.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.domain.SimpleContactCount;
import com.ajaya.cashloan.rc.service.SimpleContactCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tool.util.DateUtil;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.ShardTableUtil;
import com.ajaya.cashloan.rc.mapper.SimpleContactCountMapper;


/**
 * 风控数据统计-（简）通讯录统计ServiceImpl
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:12:49

 */
 
@Service("simpleContactCountService")
public class SimpleContactCountServiceImpl extends BaseServiceImpl<SimpleContactCount, Long> implements SimpleContactCountService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SimpleContactCountServiceImpl.class);
   
    @Resource
    private SimpleContactCountMapper simpleContactCountMapper;

	@Override
	public BaseMapper<SimpleContactCount, Long> getMapper() {
		return simpleContactCountMapper;
	}

	@Override
	public int countOne(long userId) {
		String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
		int count = simpleContactCountMapper.countOne(tableName, userId);
		
		SimpleContactCount simpleContactCount = new SimpleContactCount();
		simpleContactCount.setUserId(userId);
		simpleContactCount.setCountOne(count);
		simpleContactCount.setCreateTime(DateUtil.getNow());
		
		return simpleContactCountMapper.save(simpleContactCount);
	}
	
}