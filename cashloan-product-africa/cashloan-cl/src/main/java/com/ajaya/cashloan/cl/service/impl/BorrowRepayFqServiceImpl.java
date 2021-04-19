package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.domain.BorrowRepayLogFq;
import com.ajaya.cashloan.cl.domain.PayLog;
import com.ajaya.cashloan.cl.mapper.*;
import com.ajaya.cashloan.cl.model.PayLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.BorrowRepayFq;
import com.ajaya.cashloan.cl.service.BorrowRepayFqService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.domain.Borrow;

import tool.util.BigDecimalUtil;


/**
 * 分期还款计划表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-28 15:30:01
 */
 
@Service("borrowRepayFqService")
public class BorrowRepayFqServiceImpl extends BaseServiceImpl<BorrowRepayFq, Long> implements BorrowRepayFqService {
	
    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayFqServiceImpl.class);
   
    @Resource
    private BorrowRepayFqMapper borrowRepayFqMapper;

	@Resource
	private BorrowRepayLogFqMapper borrowRepayLogFqMapper;
    @Resource
	private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
	@Resource
	private PayLogMapper payLogMapper;

	@Override
	public BaseMapper<BorrowRepayFq, Long> getMapper() {
		return borrowRepayFqMapper;
	}

	@Override
	public Integer saveOrUpdateBorrowRepayFq(Long borrowId, Double amount, String state) {
		logger.info("borrowId:" + borrowId + ",开始创建分期计划,金额:" + amount+"分期状态 "+ state);
		//查询之前还清过几次分期
		Integer count = borrowRepayFqMapper.getCountSuccessFq(borrowId);
		logger.info("borrowId:" + borrowId + ",开始创建分期计划,金额:" + amount + ",之前还清的次数:" + count);
		Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
		
		BorrowRepayFq borrowRepayFq = borrowRepayFqMapper.getCurrentBorrowRepayFq(borrowId);
		int save = 0;
		if(borrowRepayFq != null){
			borrowRepayFq.setAmount(amount);
			borrowRepayFq.setUpdateTime(new Date());
			save = borrowRepayFqMapper.update(borrowRepayFq);
		} else {
			borrowRepayFq = new BorrowRepayFq();
			borrowRepayFq.setUserId(borrow.getUserId());
			borrowRepayFq.setBorrowId(borrowId);
			borrowRepayFq.setState(state);//未还款
			borrowRepayFq.setAmount(amount);
			borrowRepayFq.setPeriod((count + 1) + "" );
			borrowRepayFq.setCreateTime(new Date());
			save = borrowRepayFqMapper.save(borrowRepayFq);
		}
		
		logger.info("borrowId:" + borrowId + ",开始创建分期计划,金额:" + amount + ",保存分期还款计划记录结果:" + save);
		return save;
	}



	@Override
	public Integer saveOrUpdateBorrowRepayFqNew(Long borrowId, Double amount, String state,String cardNo ,String bank,String  tradeNo) {
		logger.info("borrowId:" + borrowId + ",线下还款创建分期计划,金额:" + amount+"分期状态 "+ state);
		//查询之前还清过几次分期
		Integer count = borrowRepayFqMapper.getCountSuccessFq(borrowId);
		logger.info("borrowId:" + borrowId + ",线下还款创建分期计划,金额:" + amount + ",之前还清的次数:" + count);

		Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
		BorrowRepayFq borrowRepayFq = borrowRepayFqMapper.getCurrentBorrowRepayFq(borrowId);
		int save = 0;
		if(borrowRepayFq != null){
			borrowRepayFq.setUpdateTime(new Date());
			borrowRepayFq.setAmount(amount);
			save = borrowRepayFqMapper.update(borrowRepayFq);
		} else {
			borrowRepayFq = new BorrowRepayFq();
			borrowRepayFq.setUserId(borrow.getUserId());
			borrowRepayFq.setState(state);
			borrowRepayFq.setBorrowId(borrowId);
			borrowRepayFq.setAmount(amount);
			borrowRepayFq.setPeriod((count + 1) + "" );
			borrowRepayFq.setCreateTime(new Date());
			save = borrowRepayFqMapper.save(borrowRepayFq);
		}
		logger.info("borrowId:" + borrowId + ",开始创建分期计划,金额:" + amount + ",保存分期还款计划记录结果:" + save);

		//插入分期还款计划log
		String orderNo = "RPT" + System.currentTimeMillis() + "B" + borrowId;
		orderNo = orderNo + "PFQ" + borrowRepayFq.getId();
		//保存分期还款成功记录表
		BorrowRepayLogFq borrowRepayLogFq = new BorrowRepayLogFq();
		borrowRepayLogFq.setUserId(borrowRepayFq.getUserId());
		borrowRepayLogFq.setBorrowId(borrowRepayFq.getBorrowId());
		borrowRepayLogFq.setRepayFqId(borrowRepayFq.getId());
		borrowRepayLogFq.setAmount(amount);
		borrowRepayLogFq.setSerialNumber(orderNo);
		borrowRepayLogFq.setRepayWay("20");
		borrowRepayLogFq.setCreateTime(new Date());
		borrowRepayLogFq.setRepayTime(new Date());
		borrowRepayLogFq.setPeriods(borrowRepayFq.getPeriods());
		int borrowRepayLogFqSave = borrowRepayLogFqMapper.save(borrowRepayLogFq);
		logger.info("borrowId:" + borrowId + ",线下还款创repay_log记录结果:" + borrowRepayLogFqSave);

		//插入pay_log
		PayLog payLog = new PayLog();
		payLog.setOrderNo(orderNo);
		payLog.setUserId(borrow.getUserId());
		payLog.setBorrowId(borrowId);
		payLog.setAmount(amount);
		payLog.setCardNo(cardNo);
		payLog.setBank(bank);
		payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
		payLog.setType(PayLogModel.TYPE_OFFLINE_PAYMENT);
		payLog.setScenes("20");
		payLog.setRemark("success");
		payLog.setState(PayLogModel.STATE_PAYMENT_SUCCESS);
		payLog.setPayReqTime(tool.util.DateUtil.getNow());
		payLog.setCreateTime(tool.util.DateUtil.getNow());
		payLog.setPeriods(borrowRepayFq.getPeriods());
		payLog.setTradeNo(tradeNo);
		int result = payLogMapper.save(payLog);
		logger.info("borrowId:" + borrowId + "线下还款创保存pay_log记录结果" + result);
		return save;
	}

	@Override
	public Double getShengXiaRepayMoney(Long borrowId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("borrowId", borrowId);
		BorrowRepay borrowRepay = borrowRepayMapper.findSelective(paramMap);
		if("10".equals(borrowRepay.getState())){
			//表示该订单已经结清
			logger.info("borrowId:" + borrowId + ",已经结清");
			return 0.0;
		}
		//查询已经还清的钱
		double successMoneyFq = borrowRepayFqMapper.getSuccessMoneyFq(borrowId);
		logger.info("borrowId:" + borrowId + ",已经还清的钱:" + successMoneyFq);
		//总共需要还清的金额
		double principal = borrowRepay.getAmount();
        double penaltyAmout = borrowRepay.getPenaltyAmout();
        double repaymentAmount = BigDecimalUtil.add(principal, penaltyAmout);
        logger.info("borrowId:" + borrowId + ",总共需要还清的钱:" + repaymentAmount);
        
        double needReapyAmount = BigDecimalUtil.sub(repaymentAmount, successMoneyFq);
        logger.info("borrowId:" + borrowId + ",待还清的钱:" + needReapyAmount);
		return needReapyAmount;
	}

	@Override
	public Double getCurrentNeedReapyMoney(Long borrowId) {
		Double repaymentAmount = 0.0;
		//查询当前订单最近的一笔部分还款计划数据
		BorrowRepayFq borrowRepayFq = borrowRepayFqMapper.getCurrentBorrowRepayFq(borrowId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("borrowId", borrowId);
        if(isBorrowRepayFq(borrowId)){
        	repaymentAmount = borrowRepayFq.getAmount();
        	logger.info("borrowId:" + borrowId + ",判断当前订单分期还款,金额:" + repaymentAmount);
        } else {
        	//查询已经还了多少钱
        	repaymentAmount = getShengXiaRepayMoney(borrowId);
		}
		return repaymentAmount;
	}

	@Override
	public Boolean isBorrowRepayFq(Long borrowId) {
		Boolean result = false;
		BorrowRepayFq borrowRepayFq = borrowRepayFqMapper.getCurrentBorrowRepayFq(borrowId);
		if(borrowRepayFq != null){
			//查看创建时间,判断是否在有效期内
			Date createTimeFq = borrowRepayFq.getCreateTime();
			if(borrowRepayFq.getUpdateTime() != null){
				createTimeFq = borrowRepayFq.getUpdateTime();
			}
			int fqValidTime = Global.getInt("fq_valid_time");
			int hours =  DateUtil.minuteBetween(createTimeFq, new Date()) / 60;
			if(hours <= fqValidTime){
				result = true;
			}
		}
		logger.info("borrowId:" + borrowId + ",判断当前订单是否要分期还款,结果:" + result);
		return result;
	}

	@Override
	public BorrowRepayFq getCurrentYouXiaoBorrowRepayFq(Long borrowId) {
		if(isBorrowRepayFq(borrowId)){
			return borrowRepayFqMapper.getCurrentBorrowRepayFq(borrowId);
		}
		return null;
	}
	
}