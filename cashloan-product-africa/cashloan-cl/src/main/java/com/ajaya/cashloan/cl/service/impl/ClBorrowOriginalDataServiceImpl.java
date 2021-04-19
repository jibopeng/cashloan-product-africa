package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.ClBorrowOriginalData;
import com.ajaya.cashloan.cl.mapper.ClBorrowOriginalDataMapper;
import com.ajaya.cashloan.cl.service.ClBorrowOriginalDataService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * '订单原始数据记录表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-21 14:42:23
 */
 
@Service("clBorrowOriginalDataService")
public class ClBorrowOriginalDataServiceImpl extends BaseServiceImpl<ClBorrowOriginalData, Long> implements ClBorrowOriginalDataService {
	
    private static final Logger logger = LoggerFactory.getLogger(ClBorrowOriginalDataServiceImpl.class);
   
    @Resource
    private ClBorrowOriginalDataMapper clBorrowOriginalDataMapper;

	@Override
	public BaseMapper<ClBorrowOriginalData, Long> getMapper() {
		return clBorrowOriginalDataMapper;
	}

	@Override
	public Integer saveOriginalData(Long userId, Long borrowId, String type, String data) {
		ClBorrowOriginalData borrowOriginalData = new ClBorrowOriginalData();
		borrowOriginalData.setUserId(userId);
		borrowOriginalData.setBorrowId(borrowId);
		borrowOriginalData.setType(type);
		borrowOriginalData.setOriginalData(data);
		borrowOriginalData.setCreateTime(new Date());
		Integer result = clBorrowOriginalDataMapper.save(borrowOriginalData );
		logger.info("userId {} , borrowId {}, 保存原始数据  type {} , 结果 {}", userId, borrowId, type, result);
		return result;
	}
	
}