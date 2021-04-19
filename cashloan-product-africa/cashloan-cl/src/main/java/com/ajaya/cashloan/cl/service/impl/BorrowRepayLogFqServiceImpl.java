package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.mapper.BorrowRepayLogFqMapper;
import com.ajaya.cashloan.cl.service.BorrowRepayLogFqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.BorrowRepayFq;
import com.ajaya.cashloan.cl.domain.BorrowRepayLogFq;
import com.ajaya.cashloan.cl.service.BorrowRepayFqService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 分期还款记录表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:30
 */
 
@Service("borrowRepayLogFqService")
public class BorrowRepayLogFqServiceImpl extends BaseServiceImpl<BorrowRepayLogFq, Long> implements BorrowRepayLogFqService {
	
    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayLogFqServiceImpl.class);
   
    @Resource
    private BorrowRepayLogFqMapper borrowRepayLogFqMapper;
    @Resource
    private BorrowRepayFqService borrowRepayFqService;

	@Override
	public BaseMapper<BorrowRepayLogFq, Long> getMapper() {
		return borrowRepayLogFqMapper;
	}

	@Override
	public Integer repaymentNotifyFq(Long borrowRepayFqId, String payOrderNo, Double amount) {
		logger.info("borrowRepayFqId:" + borrowRepayFqId + ",开始处理分期还款成功业务");
		BorrowRepayFq borrowRepayFq = borrowRepayFqService.getById(borrowRepayFqId);
		if(borrowRepayFq != null){
			//首先操作分期还款计划表
			borrowRepayFq.setState("10");//已还款
			int borrowRepayFqUpdateById = borrowRepayFqService.updateById(borrowRepayFq);
			logger.info("borrowRepayFqId:" + borrowRepayFqId + ",开始处理分期还款成功业务,更新分期还款计划表结果:" + borrowRepayFqUpdateById);
			//保存分期还款成功记录表
			BorrowRepayLogFq borrowRepayLogFq = new BorrowRepayLogFq();
			borrowRepayLogFq.setUserId(borrowRepayFq.getUserId());
			borrowRepayLogFq.setBorrowId(borrowRepayFq.getBorrowId());
			borrowRepayLogFq.setRepayFqId(borrowRepayFqId);
			borrowRepayLogFq.setAmount(amount);
			borrowRepayLogFq.setSerialNumber(payOrderNo);
			borrowRepayLogFq.setRepayWay("20");
			borrowRepayLogFq.setCreateTime(new Date());
			borrowRepayLogFq.setRepayTime(new Date());
			borrowRepayLogFq.setPeriods(borrowRepayFq.getPeriods());
			int borrowRepayLogFqSave = borrowRepayLogFqMapper.save(borrowRepayLogFq);
			logger.info("borrowRepayFqId:" + borrowRepayFqId + ",开始处理分期还款成功业务,新增分期还款记录表结果:" + borrowRepayLogFqSave);
		} else {
			logger.info("borrowRepayFqId:" + borrowRepayFqId + ",开始处理分期还款成功业务,分期还款计划不存在");
		}
		
		return null;
	}
	
}