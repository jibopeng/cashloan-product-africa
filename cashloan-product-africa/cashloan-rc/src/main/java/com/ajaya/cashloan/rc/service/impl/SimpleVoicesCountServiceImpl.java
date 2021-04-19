package com.ajaya.cashloan.rc.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.mapper.SimpleVoicesCountMapper;
import com.ajaya.cashloan.rc.service.SimpleVoicesCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tool.util.DateUtil;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.ShardTableUtil;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import com.ajaya.cashloan.rc.domain.SimpleVoicesCount;


/**
 * 风控数据统计-（简）通话记录统计ServiceImpl
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:11:18
 */
 
@Service("simpleVoicesCountService")
public class SimpleVoicesCountServiceImpl extends BaseServiceImpl<SimpleVoicesCount, Long> implements SimpleVoicesCountService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SimpleVoicesCountServiceImpl.class);
   
    @Resource
    private SimpleVoicesCountMapper simpleVoicesCountMapper;
    @Resource
    private UserBaseInfoService userBaseInfoService;

	@Override
	public BaseMapper<SimpleVoicesCount, Long> getMapper() {
		return simpleVoicesCountMapper;
	}
	
	@Override
	public int countOne(long userId) {
		String tableName = ShardTableUtil.generateTableNameById("cl_operator_voices", userId, 30000);
		int count = simpleVoicesCountMapper.countOne(tableName, userId);
		
//		UserBaseInfo user = userBaseInfoService.findByUserId(userId);
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MONTH, -6);
//        //格式化输出年月日
//		Double antCount = simpleVoicesCountMapper.countTwo("cl_operator_bills", user.getPhone(),DateUtil.dateStr2(cal.getTime()));
		
		SimpleVoicesCount simpleVoicesCount = new SimpleVoicesCount();
		simpleVoicesCount.setUserId(userId);
		simpleVoicesCount.setCountOne(count);
		simpleVoicesCount.setCreateTime(DateUtil.getNow());
//		simpleVoicesCount.setCountTwo(antCount);
		return simpleVoicesCountMapper.save(simpleVoicesCount);
	}

	@Override
	public SimpleVoicesCount findByUserId(long userId) {
		return simpleVoicesCountMapper.findByUserId(userId);
	}
	
}