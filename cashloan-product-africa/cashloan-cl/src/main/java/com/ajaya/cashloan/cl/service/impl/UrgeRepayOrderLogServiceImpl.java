package com.ajaya.cashloan.cl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BorrowProgress;
import com.ajaya.cashloan.cl.domain.UrgeRepayExtend;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrder;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrderLog;
import com.ajaya.cashloan.cl.model.UrgeRepayOrderModel;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderLogService;
import org.springframework.stereotype.Service;

import tool.util.DateUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.mapper.BorrowProgressMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.mapper.UrgeRepayExtendMapper;
import com.ajaya.cashloan.cl.mapper.UrgeRepayOrderLogMapper;
import com.ajaya.cashloan.cl.mapper.UrgeRepayOrderMapper;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.BorrowMapper;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.model.BorrowModel;

/**
 * 催款记录表ServiceImpl
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-03-07 14:28:22
 */

@Service("urgeRepayOrderLogService")
public class UrgeRepayOrderLogServiceImpl extends
		BaseServiceImpl<UrgeRepayOrderLog, Long> implements
        UrgeRepayOrderLogService {
	 @Resource
     private UrgeRepayOrderMapper urgeRepayOrderMapper;
	 
	@Resource
	private UrgeRepayOrderLogMapper urgeRepayOrderLogMapper;
	
    @Resource
    private ClBorrowMapper clBorrowMapper;
    
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    
    @Resource
    private UrgeRepayExtendMapper urgeRepayExtendMapper;
    
    @Resource
    private BorrowMapper borrowMapper;
    
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    
	@Override
	public BaseMapper<UrgeRepayOrderLog, Long> getMapper() {
		return urgeRepayOrderLogMapper;
	}

	@Override
	public Page<UrgeRepayOrderLog> list(Map<String, Object> params,
			int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<UrgeRepayOrderLog> list = urgeRepayOrderLogMapper
				.listSelective(params);
		return (Page<UrgeRepayOrderLog>) list;
	}

	@Override
	public List<UrgeRepayOrderLog> getLogByParam(Map<String, Object> params) {
		
		return urgeRepayOrderLogMapper.listSelective(params);
	}

	@Override
	public int saveOrderInfo(UrgeRepayOrderLog  orderLog, UrgeRepayOrder order, String answerState) {
		 orderLog.setDueId(order.getId());
		 orderLog.setBorrowId(order.getBorrowId());
		 orderLog.setUserId(order.getUserId());
		 //更新催收记录
		 //刘晓亮 原来的  int i=urgeRepayOrderLogMapper.save(orderLog);
		 //刘晓亮 返回值有主键
		 urgeRepayOrderLogMapper.saveReturnId(orderLog);
		 int i = orderLog.getId().intValue();
		 //更新催收订单进度
		 Map<String,Object> map = new HashMap<>();
		 map.put("id", order.getId());
		 map.put("state", orderLog.getState());
		 map.put("count", order.getCount()+1);
		 map.put("successTime", DateUtil.getNow());
		 urgeRepayOrderMapper.updateSuccess(map);
		 if(order.getState().equals(UrgeRepayOrderModel.STATE_ORDER_BAD)){
			//更新借款状态
			Borrow b=clBorrowMapper.findByPrimary(order.getBorrowId());
			Map<String,Object> stateMap = new HashMap<String,Object>();
			stateMap.put("id", order.getBorrowId()); 
			stateMap.put("state", BorrowModel.STATE_BAD);
			clBorrowMapper.updateSelective(stateMap);
			//添加借款进度
			BorrowProgress bp=new BorrowProgress();
			bp.setBorrowId(b.getId());
			bp.setUserId(b.getUserId());
			bp.setRemark(BorrowModel.convertBorrowRemark(BorrowModel.STATE_BAD));
			bp.setState(BorrowModel.STATE_BAD);
			bp.setCreateTime(new Date());
			borrowProgressMapper.save(bp);
		 }
		 /**刘晓亮 start 2017年12月2日 23:54:53 目的：新增催收扩展记录表*/
		 if(answerState != null){
			 UrgeRepayExtend urgeRepayExtend = new UrgeRepayExtend();
			 urgeRepayExtend.setBorrowId(order.getBorrowId());
			 urgeRepayExtend.setUserId(order.getUserId());
			 urgeRepayExtend.setUrgeOrderId(order.getId());
			 urgeRepayExtend.setUrgeLogId(i);
			 urgeRepayExtend.setCreateTime(new Date());
			 //根据borrowId查找借款人信息
			 Borrow borrow = borrowMapper.findByPrimary(order.getBorrowId());
			 if(borrow != null){
				 urgeRepayExtend.setClUserId(borrow.getUserId());
				 //查找借款人电话
				 UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(borrow.getUserId());
				 if(userBaseInfo != null){
					 urgeRepayExtend.setPhone(userBaseInfo.getPhone());
				 }
			 }
			 urgeRepayExtend.setAnswerState(answerState);
			 urgeRepayExtendMapper.save(urgeRepayExtend);
		 }
		 /**刘晓亮 end*/
		return i;
	}

	@Override
	public List<Map<String, Object>> getCuiShouMsgListByBorrow(List<Borrow> borrowList) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		if(borrowList != null && borrowList.size() > 0 ){
			for (Borrow borrow : borrowList) {
				List<Map<String, Object>> resultListTemp = urgeRepayOrderLogMapper.getCuiShouMsgListByBorrowId(borrow.getId());
				if(resultListTemp != null && resultListTemp.size() > 0){
					for (Map<String, Object> map : resultListTemp) {
						resultList.add(map);
					}
				}
			}
		}
		return resultList;
	}

 
}