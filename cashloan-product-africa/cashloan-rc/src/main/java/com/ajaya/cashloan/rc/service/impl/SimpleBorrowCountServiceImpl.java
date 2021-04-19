package com.ajaya.cashloan.rc.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.domain.SimpleBorrowCount;
import com.ajaya.cashloan.rc.mapper.SimpleBorrowCountMapper;
import com.ajaya.cashloan.rc.service.SimpleBorrowCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tool.util.DateUtil;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 风控数据统计-（简）借款统计ServiceImpl
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:12:18

 */
 
@Service("simpleBorrowCountService")
public class SimpleBorrowCountServiceImpl extends BaseServiceImpl<SimpleBorrowCount, Long> implements SimpleBorrowCountService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SimpleBorrowCountServiceImpl.class);
   
    @Resource
    private SimpleBorrowCountMapper simpleBorrowCountMapper;

	@Override
	public BaseMapper<SimpleBorrowCount, Long> getMapper() {
		return simpleBorrowCountMapper;
	}

	@Override
	public int countOne(long userId) {
		int count = simpleBorrowCountMapper.countOne(userId);
		
		SimpleBorrowCount simpleBorrowCount = new SimpleBorrowCount();
		simpleBorrowCount.setUserId(userId);
		simpleBorrowCount.setCountOne(count);
		simpleBorrowCount.setCreateTime(DateUtil.getNow());
		
//		count = simpleBorrowCountMapper.countTwo(userId);
//		simpleBorrowCount.setCountTwo(count);
//		
//		count = simpleBorrowCountMapper.countThree(userId);
//		simpleBorrowCount.setCountThree(count);
//		
//		count = simpleBorrowCountMapper.countFour(userId);
//		simpleBorrowCount.setCountFour(count);
		
		return simpleBorrowCountMapper.save(simpleBorrowCount);
	}
}