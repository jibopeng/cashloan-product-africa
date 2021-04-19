package com.ajaya.cashloan.cl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BorrowRepayLog;
import com.ajaya.cashloan.cl.domain.PayLog;
import com.ajaya.cashloan.cl.mapper.BorrowRepayLogFqMapper;
import com.ajaya.cashloan.cl.model.BorrowRepayLogModel;
import com.ajaya.cashloan.cl.model.ManageBRepayLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.domain.BorrowRepayLogFq;
import com.ajaya.cashloan.cl.mapper.BorrowRepayLogMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.service.BorrowRepayLogService;
import com.ajaya.cashloan.cl.service.PayLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.domain.Borrow;

import tool.util.StringUtil;

/**
 * 还款记录ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 13:46:12
 */
 
@Service("borrowRepayLogService")
public class BorrowRepayLogServiceImpl extends BaseServiceImpl<BorrowRepayLog, Long> implements BorrowRepayLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowRepayLogServiceImpl.class);
   
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    @Resource
    private PayLogService payLogService;
    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowRepayLogFqMapper borrowRepayFqLogMapper;


	@Override
	public BaseMapper<BorrowRepayLog, Long> getMapper() {
		return borrowRepayLogMapper;
	}

	@Override
	public int save(BorrowRepayLog brl) {
		return borrowRepayLogMapper.save(brl);
	}

	@Override
	public Page<BorrowRepayLogModel> page(Map<String, Object> searchMap,
                                          int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<BorrowRepayLogModel> list = borrowRepayLogMapper.listSelModel(searchMap);
		return (Page<BorrowRepayLogModel>)list;
	}
	
	@Override
	public Page<ManageBRepayLogModel> listModel(Map<String, Object> params,
                                                int currentPage, int pageSize) {
		if(params != null && params.containsKey("serialNumber") && StringUtil.isNotBlank(params.get("serialNumber"))){
			String tradeNo = params.get("serialNumber").toString();
			PayLog payLog = payLogService.findByTradeNo(tradeNo);
			params.put("borrowId", payLog.getBorrowId());
		}
		
		PageHelper.startPage(currentPage, pageSize);
		List<ManageBRepayLogModel> list = null;
		if(params != null && params.containsKey("state") && "2".equals(params.get("state"))){
			//说明查询的是部分还款
			list = borrowRepayLogMapper.listModelFq(params);
			if(list != null && list.size() > 0){
				for (ManageBRepayLogModel manageBRepayLogModel : list) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("borrowId", manageBRepayLogModel.getBorrowId());
					//查询payLog数据
					BorrowRepayLogFq borrowRepayLogFq = borrowRepayFqLogMapper.findByPrimary(manageBRepayLogModel.getId());
					paramMap.put("state", 40);
					paramMap.put("periods", borrowRepayLogFq.getRepayFqId());
					PayLog payLog = payLogService.findSelective(paramMap);
					if(payLog != null && StringUtil.isNotBlank(payLog.getTradeNo())){
						manageBRepayLogModel.setSerialNumber(payLog.getTradeNo());
					}
				}
			}
		} else {
			list = borrowRepayLogMapper.listModel(params);
			if(list != null && list.size() > 0){
				for (ManageBRepayLogModel manageBRepayLogModel : list) {
					//查询payLog数据
					PayLog payLog = payLogService.findRepayMentLog(manageBRepayLogModel.getBorrowId());
					if(payLog != null && StringUtil.isNotBlank(payLog.getTradeNo())){
						manageBRepayLogModel.setSerialNumber(payLog.getTradeNo());
					}
				}
			}
		}
		
		return (Page<ManageBRepayLogModel>)list;
	}
	
	@Override
	public BorrowRepayLog findSelective(Map<String, Object> paramMap) {
		BorrowRepayLog borrowRepayLog = null;
		try {
			borrowRepayLog = borrowRepayLogMapper.findSelective(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return borrowRepayLog;
	}

	@Override
	public boolean updateSelective(Map<String, Object> paramMap) {
		int result  =  borrowRepayLogMapper.updateSelective(paramMap);
		if(result > 0L){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean refundDeduction(Map<String, Object> paramMap){
		int result  =  borrowRepayLogMapper.refundDeduction(paramMap);
		if(result > 0L){
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listExport(Map<String, Object> searchParams) {
		List<ManageBRepayLogModel> list = null;
		if(searchParams != null && searchParams.containsKey("state") && "2".equals(searchParams.get("state"))){
			//说明查询的是部分还款
			list = borrowRepayLogMapper.listModelFq(searchParams);
			if(list != null && list.size() > 0){
				for (ManageBRepayLogModel manageBRepayLogModel : list) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("borrowId", manageBRepayLogModel.getBorrowId());
					//查询payLog数据
					BorrowRepayLogFq borrowRepayLogFq = borrowRepayFqLogMapper.findByPrimary(manageBRepayLogModel.getId());
					paramMap.put("state", 40);
					paramMap.put("periods", borrowRepayLogFq.getRepayFqId());
					PayLog payLog = payLogService.findSelective(paramMap);
					if(payLog != null && StringUtil.isNotBlank(payLog.getTradeNo())){
						manageBRepayLogModel.setSerialNumber(payLog.getTradeNo());
					}
				}
			}
		} else {
			list = borrowRepayLogMapper.listModel(searchParams);
			if(list != null && list.size() > 0){
				for (ManageBRepayLogModel manageBRepayLogModel : list) {
					//查询payLog数据
					//PayLog payLog = payLogService.findRepayMentLog(manageBRepayLogModel.getBorrowId());
					PayLog payLog = payLogService.findByOrderNo(manageBRepayLogModel.getSerialNumber());
					if(payLog != null && StringUtil.isNotBlank(payLog.getTradeNo())){
						manageBRepayLogModel.setSerialNumber(payLog.getTradeNo());
					}
				}
			}
		}
		for (ManageBRepayLogModel m : list) {
			//查询payLog数据
			PayLog payLog = payLogService.findRepayMentLog(m.getBorrowId());
			if(payLog != null && StringUtil.isNotBlank(payLog.getTradeNo())){
				Borrow borrow = clBorrowMapper.findByPrimary(m.getBorrowId());
				if("2".equals(borrow.getPayChannelId() + "")){
					m.setPayChannel("Zavron");
				} else {
					m.setPayChannel("KUD");
				}
			}
		}
		return list;
	}

}