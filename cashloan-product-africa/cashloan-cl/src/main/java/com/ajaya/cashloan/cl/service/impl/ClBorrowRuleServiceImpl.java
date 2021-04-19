package com.ajaya.cashloan.cl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BankAccount;
import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.cl.domain.entity.SpecialListRequest;
import com.ajaya.cashloan.cl.mapper.*;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.common.util.HttpUtil;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.ClBorrowRule;
import com.ajaya.cashloan.cl.service.ClBorrowRuleService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 订单规则表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-06-18 19:04:15
 */
 
@Service("clBorrowRuleService")
public class ClBorrowRuleServiceImpl extends BaseServiceImpl<ClBorrowRule, Long> implements ClBorrowRuleService {
	
    private static final Logger logger = LoggerFactory.getLogger(ClBorrowRuleServiceImpl.class);
   
    @Resource
    private ClBorrowRuleMapper clBorrowRuleMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private UserContactsMapper userContactsMapper;
	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	@Resource
	private ClBorrowMapper clBorrowMapper;
	@Resource
	private BankAccountMapper bankAccountMapper;
	@Resource
	private UserEquipmentInfoMapper userEquipmentInfoMapper;
	@Override
	public BaseMapper<ClBorrowRule, Long> getMapper() {
		return clBorrowRuleMapper;
	}

	@Override
	public Integer saveBorrowRule(Long borrowId, Long userId, JSONArray originalContact, JSONArray originalMsg,JSONArray originalApp) {
		logger.info("订单 {} , 开始保存订单规则代码", borrowId);
		Map<String, Object> map = new HashMap<>();
		map.put("borrowId", borrowId);
		ClBorrowRule borrowRule = clBorrowRuleMapper.findSelective(map);
		Boolean flag = false;
		if(borrowRule == null){
			flag = true;
			borrowRule = new ClBorrowRule();
		}
		borrowRule.setUserId(userId);
		borrowRule.setBorrowId(borrowId);
		
		//最大逾期天数
		Integer maxPenaltDay = borrowRepayMapper.getMaxPenaltDay(userId);
		borrowRule.setMaxPenaltyDays(maxPenaltDay);
		
		// 获取的是非重复的
		List<String> list = new ArrayList<>();
		if (originalContact != null && originalContact.size() > 0) {
			for (int i = 0; i < originalContact.size(); i++) {
				JSONObject object = originalContact.getJSONObject(i);
				if (object.containsKey("phone") && object.get("phone") != null
						&& !list.contains(object.get("phone"))) {
					list.add(object.get("phone").toString());
				}
			}
		}
		if(borrowRule.getContactsNum() == null){
			borrowRule.setContactsNum(originalContact != null ? list.size() : null);
		}
		if(borrowRule.getMsgNum() == null){
			borrowRule.setMsgNum(originalMsg != null ? originalMsg.size() : null);
		}
		if(borrowRule.getAppNum() == null){
			borrowRule.setAppNum(originalApp != null ? originalApp.size() : null);
		}
		if((borrowRule.getMsgNum() != null && borrowRule.getAppNum() != null) && (borrowRule.getMsgNum() > 0 || borrowRule.getAppNum() > 0)) {
			borrowRule.setMsgAppNum(1);
		} else {
			borrowRule.setMsgAppNum(0);
		}
		
		Integer result = 0;
		if (flag) {
			borrowRule.setCreateTime(new Date());
			result = clBorrowRuleMapper.save(borrowRule);
		} else {
			borrowRule.setUpdateTime(new Date());
			result = clBorrowRuleMapper.update(borrowRule);
		}
		logger.info("订单 {} , 开始保存订单规则代码， 结果 {}", borrowId , result);
		return result;
	}

	@Override
	public Integer saveBorrowRule2(Borrow borrow, String imei,Boolean forbiddenArea) {
		ClBorrowRule	borrowRule = new ClBorrowRule();

		borrowRule.setCreateTime(new Date());
		borrowRule.setBorrowId(borrow.getId());
		borrowRule.setUserId(borrow.getUserId());
		borrowRule.setImeiIsSame(1);
		borrowRule.setPermBlackList(2);

		UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(borrow.getUserId());
		// 判断新贷注册时imei与申请时imei是否一致，不一致直接拒掉
		HashMap<String, Object> paramMap = new HashMap<>(8);
		paramMap.put("userId", borrow.getUserId());
		UserEquipmentInfo userEquipmentInfo = userEquipmentInfoMapper.findSelective(paramMap);
		if (userEquipmentInfo != null && !"000000000000000".equals(userEquipmentInfo.getImei())) {
			if (!userEquipmentInfo.getImei().equals(imei)) {
				if (Math.abs(DateUtil.daysBetween(userBaseInfo.getCreateTime(), new Date())) < 30) {
					borrow.setSignServiceId("5");
					clBorrowMapper.update(borrow);
					borrowRule.setImeiIsSame(2);
					logger.info("订单 {} 申请时imei与注册时不一致",borrow.getId());
				}
			}
		}
		//禁止区域规则添加
		if (forbiddenArea){
			//不是禁放区区域
			borrowRule.setForbiddenArea(1);
		}else {
			borrowRule.setForbiddenArea(2);
		}
		return clBorrowRuleMapper.save(borrowRule);
	}

	@Override
	public Integer saveXiGuaBorrowRule(Long borrowId, Long userId, JSONObject xiGuaData) {
		logger.info("订单 {} , 开始保存订单风控规则代码", borrowId);
		Map<String, Object> map = new HashMap<>();
		map.put("borrowId", borrowId);
		ClBorrowRule borrowRule = clBorrowRuleMapper.findSelective(map);
		Boolean flag = false;
		if(borrowRule == null){
			flag = true;
			borrowRule = new ClBorrowRule();
		}
		borrowRule.setUserId(userId);
		borrowRule.setBorrowId(borrowId);
		
		Integer onLoanOrderCnt = 0;
		if(xiGuaData != null && xiGuaData.containsKey("feature")){
			JSONObject jsonObject = xiGuaData.getJSONObject("feature");
			if(jsonObject != null && jsonObject.containsKey("order_6063")){
				String str = jsonObject.getString("order_6063");
				onLoanOrderCnt = StringUtil.isNotBlank(str) ? Integer.parseInt(str) : 0;
			}
		}
		borrowRule.setOnLoanOrderCnt(onLoanOrderCnt);
		Integer result = 0;
		if (flag) {
			borrowRule.setCreateTime(new Date());
			result = clBorrowRuleMapper.save(borrowRule);
		} else {
			borrowRule.setUpdateTime(new Date());
			result = clBorrowRuleMapper.update(borrowRule);
		}
		logger.info("订单 {} , 开始保存订单风控规则代码， 结果 {}", borrowId , result);
		return result;
	}

}