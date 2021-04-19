package com.ajaya.cashloan.cl.service.impl;

import com.ajaya.cashloan.cl.domain.*;
import com.ajaya.cashloan.cl.enums.AfricaStateEnum;
import com.ajaya.cashloan.cl.mapper.*;
import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.HttpClientUtil;
import com.ajaya.cashloan.core.common.util.ListUtil;
import com.ajaya.cashloan.core.common.util.ShardTableUtil;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.util.code.MD5;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * 催收系统订单推送表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-26 10:49:41
 */

@Service("urgeOrderPushLogService")
public class UrgeOrderPushLogServiceImpl extends BaseServiceImpl<UrgeOrderPushLog, Long>
		implements UrgeOrderPushLogService {

	private static final Logger logger = LoggerFactory.getLogger(UrgeOrderPushLogServiceImpl.class);

	@Resource
	private UrgeOrderPushLogMapper urgeOrderPushLogMapper;
	@Resource
	private ClBorrowMapper clBorrowMapper;
	@Resource
	private UserBaseInfoService userBaseInfoService;
	@Resource
	private BorrowRepayService borrowRepayService;
	@Resource
	private BorrowRepayLogService borrowRepayLogService;
	@Resource
	private UserContactsMapper userContactsMapper;
	@Resource
	private UserEmerContactsMapper userEmerContactsMapper;
	@Resource
	private UserEquipmentInfoMapper userEquipmentInfoMapper;
	@Resource
	private UserVirtualAccountMapper virtualAccountMapper;
	@Resource
	private UrgeRepayOrderMapper urgeRepayOrderMapper;
	@Resource
	private UserBvnMapper userBvnMapper;
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private UrgePushStateRecordMapper urgePushStateRecordMapper;
	@Resource
	private UserMessagesMapper clUserMessagesMapper;
	@Resource
	private   BorrowRepayMapper borrowRepayMapper;

	// private String org_id = "rupee_max_test";
	// private String key = "AHAvV6trisDWh4yZUlaiNwj2PNfe58d5";

	@Override
	public BaseMapper<UrgeOrderPushLog, Long> getMapper() {
		return urgeOrderPushLogMapper;
	}

	/**
	 * 订单推送成功以后就不再推送了,天数和逾期都是他那块拉取的
	 */
	@Override
	public Integer pushUrgeOrder(Long borrowId) {
		String org_id ;
		String key = Global.getValue("urge_push_key");
		String url = Global.getValue("urge_push_url");
		Integer result = 0;
		// 首先判断改订单是否已经成功推送过
		logger.info("borrowId:" + borrowId + ",开始推送催收push系统订单基本信息");
		Map<String, Object> borrowIdMap = new HashMap<>();
		borrowIdMap.put("borrowId", borrowId);
		Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
		borrowIdMap.put("borrowState", "1");
		UrgePushStateRecord urgePushStateRecord = urgePushStateRecordMapper.findSelective(borrowIdMap);
		if (urgePushStateRecord != null) {
			logger.info("borrowId:" + borrowId + ",开始推送催收push系统订单基本信息,已经推送过");
		} else {
			Map<String, Object> param = new HashMap<>();
			param.put("call", "push-order");
			Map<String, Object> args = new HashMap<>();
			org_id =borrow.getProductName();
			// 还款计划信息 repaymentPlan
			List<Map<String, Object>> repaymentPlans = new ArrayList<>();
			Map<String, Object> repaymentPlanMap = new HashMap<>();
			BorrowRepay borrowRepay = borrowRepayService.findSelective(borrowIdMap);
			repaymentPlanMap.put("billAmount", borrowRepay.getAmount());// 款项原始⾦金金额，单位：卢⽐比
			repaymentPlanMap.put("shouldRepayTime", borrowRepay.getRepayTime().getTime());// 应还款时间
			BorrowRepayLog borrowRepayLog = borrowRepayLogService.findSelective(borrowIdMap);
			repaymentPlanMap.put("repayTime", "");// 实际还款时间
			if (borrowRepayLog != null) {
				repaymentPlanMap.put("repayTime", borrowRepayLog.getCreateTime().getTime());// 实际还款时间
			}
			repaymentPlanMap.put("applyTime", borrow.getCreateTime().getTime());// 申请时间
			repaymentPlanMap.put("orderNo", borrow.getOrderNo());// 订单号
			repaymentPlanMap.put("repaymentPlanStatus", borrowRepayLog != null ? 10 : 20);
			repaymentPlanMap.put("fundType", "1");// 本金
			repaymentPlanMap.put("returnedAmount", borrowRepayLog != null ? new BigDecimal(borrowRepayLog.getAmount())
					.add(new BigDecimal(borrowRepayLog.getPenaltyAmout())).doubleValue() : 0.0);// 本金
			repaymentPlanMap.put("thirdRepayPlanId", borrowRepay.getId());
			repaymentPlans.add(repaymentPlanMap);
			args.put("repaymentPlan", repaymentPlans);

			// 通讯录
			Long userId = borrow.getUserId();
			args.put("contact", getTopList(userId));
			// emerContact
			List<UserEmerContacts> emerContact = new ArrayList<>();
			Map<String, Object> userIdMap = new HashMap<>();
			userIdMap.put("userId", userId);
			emerContact = userEmerContactsMapper.listSelective(userIdMap);
			args.put("emerContact", emerContact);
			UserBvn bvnByUserId = userBvnMapper.findBvnByUserId(userId);
			// userInfo
			Map<String, Object> userInfo = new HashMap<>();
			UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(userId);
			UserEquipmentInfo equipmentInfo = userEquipmentInfoMapper.findSelective(userIdMap);
			userInfo.put("address", userBaseInfo.getLiveAddr());
			userInfo.put("thirdUserId", userBaseInfo.getUserId());
			userInfo.put("name", userBaseInfo.getRealName());
			userInfo.put("phone", userBaseInfo.getPhone());
			userInfo.put("phoneType", equipmentInfo.getPhoneBrand());
			userInfo.put("bvn", userBaseInfo.getBvnNo());
			userInfo.put("email", userBaseInfo.getEmail());
			userInfo.put("email", userBaseInfo.getEmail());
			userInfo.put("payMethod", userBaseInfo.getSalaryType());
			userInfo.put("bvnName", bvnByUserId.getFirstName()+" "+bvnByUserId.getLastName());

			//虚拟账号信息
			UserVirtualAccount virtualAccount = virtualAccountMapper.findByUserId(userId);

//			BankAccount bankAccount = bankAccountService.getById(borrow.getAccountId());
			userInfo.put("bankAccount", virtualAccount != null ? virtualAccount.getAccountNumber() : "");
			userInfo.put("bankName", virtualAccount != null ? virtualAccount.getBankName() : "");
			userInfo.put("bankAccountName", virtualAccount != null ? virtualAccount.getAccountName() : "");
			
			userInfo.put("sysAddress",
					StringUtil.isNoneBlank(userBaseInfo.getRegisterAddr()) ? userBaseInfo.getRegisterAddr() : "");
			userInfo.put("longitude", userBaseInfo.getRegisterCoordinate());
			userInfo.put("gender", userBaseInfo.getSex().equals("MALE") ? "1" : "2");
			userInfo.put("age", userBaseInfo.getAge());
			userInfo.put("language", "1");
			userInfo.put("appName", borrow.getProductName());
			userInfo.put("latitude", userBaseInfo.getRegisterCoordinate());
			userInfo.put("companyName", "NA");
			userInfo.put("position", userBaseInfo.getOccupation());
			userInfo.put("salary", userBaseInfo.getSalary());
			userInfo.put("registerTime", userBaseInfo.getCreateTime().getTime());
			userInfo.put("province", "");// 邦地址
			args.put("userInfo", userInfo);
			int  count =borrowRepayMapper.getOrderCount(userId);
			// orderInfo
			Map<String, Object> orderInfo = new HashMap<>();
			orderInfo.put("loanTime", borrowRepay.getCreateTime().getTime());// 放款时间
			orderInfo.put("billTime", borrowRepay.getRepayTime().getTime());// 应还款时间
			orderInfo.put("applyTime", borrow.getCreateTime().getTime());
			orderInfo.put("orderNo", borrow.getOrderNo());
			orderInfo.put("loanAmount", borrow.getAmount());
			orderInfo.put("returnedAmount", borrowRepayLog != null ? new BigDecimal(borrowRepayLog.getAmount())
					.add(new BigDecimal(borrowRepayLog.getPenaltyAmout())).doubleValue() : 0.0);// 本金
			orderInfo.put("serviceAmount", BigDecimalUtil.sub(borrow.getAmount(), borrow.getRealAmount()));
			orderInfo.put("penaltyDays", borrowRepay.getPenaltyDay());
			orderInfo.put("penaltyAmount", borrowRepay.getPenaltyAmout());
			orderInfo.put("actualLoanAmount", borrow.getRealAmount());
			orderInfo.put("shouldRepayAmount", new BigDecimal(borrowRepay.getAmount())
					.add(new BigDecimal(borrowRepay.getPenaltyAmout())).doubleValue());
			orderInfo.put("term", borrow.getTimeLimit());
			orderInfo.put("termType", 1);
			orderInfo.put("termUnit", 1);
			orderInfo.put("applyAddress", borrow.getAddress());
			orderInfo.put("interest", borrow.getInterest());// 利息
			orderInfo.put("appName", borrow.getProductName());// 利息
			orderInfo.put("orderCounts", count);// 利息
			boolean refinanceBeforeCreateBorroworder = clBorrowService
					.isRefinanceBeforeCreateBorroworder(userId);
			orderInfo.put("orderType", refinanceBeforeCreateBorroworder ? 2 : 1); // 1新贷,2复贷;
			putVoice(borrowId, orderInfo);
			args.put("orderInfo", orderInfo);
			param.put("orgId", org_id);
			String call = "push-order";

			String sign_key = org_id + key + org_id;
			logger.info("sign_key:" + sign_key);
			String plain = sign_key + call + sign_key
					+ JSON.toJSONString(args, SerializerFeature.DisableCircularReferenceDetect) + sign_key;

			String sign = MD5.SunMD5(plain);
			logger.info("sign:" + sign);
			param.put("sign", sign);
			param.put("args", args);
			String paramJson = JSON.toJSONString(param, SerializerFeature.DisableCircularReferenceDetect);
			Map<String, String> headers = new HashMap<>();
			String sendPost = HttpClientUtil.sendPost(url, headers, paramJson, "UTF-8");
			logger.info("borrowId:" + borrowId + ",推送push-order返回结果:" + sendPost);

			UrgeOrderPushLog urgeOrderPushLog = new UrgeOrderPushLog();
			urgeOrderPushLog.setUserId(userId);
			urgeOrderPushLog.setBorrowId(borrowId);
			urgeOrderPushLog.setParamJson(paramJson);
			urgeOrderPushLog.setReturnJson(sendPost);
			urgeOrderPushLog.setCreateTime(new Date());
			int save = urgeOrderPushLogMapper.save(urgeOrderPushLog);
			logger.info("borrowId:" + borrowId + ",推送push-order保存log记录结果:" + save);

			borrowIdMap.remove("borrowState");
			urgePushStateRecord = urgePushStateRecordMapper.findSelective(borrowIdMap);

			Boolean flag = true;
			if (StringUtil.isNotBlank(sendPost)) {
				JSONObject parseObject = JSON.parseObject(sendPost);
				if (parseObject != null && parseObject.containsKey("code") && 200 == parseObject.getInteger("code")) {
					flag = false;
					logger.info("borrowId:" + borrowId + ",推送push-order成功");
					if (urgePushStateRecord == null) {
						urgePushStateRecord = new UrgePushStateRecord();
						urgePushStateRecord.setUserId(userId);
						urgePushStateRecord.setBorrowId(borrowId);
						urgePushStateRecord.setBorrowState("1");
						urgePushStateRecord.setRepayState("0");
						urgePushStateRecord.setCreateTime(new Date());
						int save2 = urgePushStateRecordMapper.save(urgePushStateRecord);
						logger.info("borrowId:" + borrowId + ",推送urgePushStateRecord保存log记录结果:" + save2);
					} else {
						urgePushStateRecord.setUserId(userId);
						urgePushStateRecord.setBorrowId(borrowId);
						urgePushStateRecord.setBorrowState("1");
						urgePushStateRecord.setRepayState("0");
						urgePushStateRecord.setUpdateTime(new Date());
						int save2 = urgePushStateRecordMapper.update(urgePushStateRecord);
						logger.info("borrowId:" + borrowId + ",推送urgePushStateRecord保存log记录结果:" + save2);
					}
				}
			}
			if (flag) {
				logger.info("borrowId:" + borrowId + ",推送push-order失败");
				if (urgePushStateRecord == null) {
					urgePushStateRecord = new UrgePushStateRecord();
					urgePushStateRecord.setUserId(userId);
					urgePushStateRecord.setBorrowId(borrowId);
					urgePushStateRecord.setBorrowState("0");
					urgePushStateRecord.setRepayState("0");
					urgePushStateRecord.setCreateTime(new Date());
					int save2 = urgePushStateRecordMapper.save(urgePushStateRecord);
					logger.info("borrowId:" + borrowId + ",推送urgePushStateRecord保存log记录结果:" + save2);
				} else {
					urgePushStateRecord.setUserId(userId);
					urgePushStateRecord.setBorrowId(borrowId);
					urgePushStateRecord.setBorrowState("0");
					urgePushStateRecord.setRepayState("0");
					urgePushStateRecord.setUpdateTime(new Date());
					int save2 = urgePushStateRecordMapper.update(urgePushStateRecord);
					logger.info("borrowId:" + borrowId + ",推送urgePushStateRecord保存log记录结果:" + save2);
				}
			}

		}
		return result;
	}

	public String getLanagueType(String data) {
		return "1";
	}

	@Override
	public Integer pushRepaymet(Long borrowId) {
		String org_id;
		String key = Global.getValue("urge_push_key");
		String url = Global.getValue("urge_push_url");
		Map<String, Object> borrowIdMap = new HashMap<>();
		borrowIdMap.put("borrowId", borrowId);
		Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
		Integer result = 0;
		org_id =borrow.getProductName();
		borrowIdMap.put("borrowState", "1");
		UrgePushStateRecord urgePushStateRecord = urgePushStateRecordMapper.findSelective(borrowIdMap);
		if (urgePushStateRecord != null) {
			Map<String, Object> param = new HashMap<>();
			param.put("orgId", org_id);
			String call = "push-repay";
			param.put("call", "push-repay");
			Map<String, Object> args = new HashMap<>();
			Map<String, Object> repaymentPlanMap = new HashMap<>();
			BorrowRepay borrowRepay = borrowRepayService.findSelective(borrowIdMap);
			repaymentPlanMap.put("billAmount", borrowRepay.getAmount());// 款项原始⾦金金额，单位：卢⽐比
			repaymentPlanMap.put("shouldRepayTime", borrowRepay.getRepayTime().getTime());// 应还款时间
			BorrowRepayLog borrowRepayLog = borrowRepayLogService.findSelective(borrowIdMap);
			repaymentPlanMap.put("repayTime", "");// 实际还款时间
			if (borrowRepayLog != null) {
				repaymentPlanMap.put("repayTime", borrowRepayLog.getCreateTime().getTime());// 实际还款时间
			}
			repaymentPlanMap.put("applyTime", borrow.getCreateTime().getTime());// 申请时间
			repaymentPlanMap.put("orderNo", borrow.getOrderNo());// 订单号
			repaymentPlanMap.put("repaymentPlanStatus", borrowRepayLog != null ? 10 : 20);
			repaymentPlanMap.put("fundType", "1");// 本金
			repaymentPlanMap.put("returnedAmount", borrowRepayLog != null ? new BigDecimal(borrowRepayLog.getAmount())
					.add(new BigDecimal(borrowRepayLog.getPenaltyAmout())).doubleValue() : 0.0);// 本金
			repaymentPlanMap.put("thirdRepayPlanId", borrowRepay.getId());
			args.put("repaymentPlan", repaymentPlanMap);

			List<Map<String, Object>> repayRecord = new ArrayList<>();
			Map<String, Object> repayRecordMap = new HashMap<>();
			repayRecordMap.put("fundType", 1);
			repayRecordMap.put("orderNo", borrow.getOrderNo());
			repayRecordMap.put("payStatus", 3);
			repayRecordMap.put("repayAmount", borrowRepayLog != null ? new BigDecimal(borrowRepayLog.getAmount())
					.add(new BigDecimal(borrowRepayLog.getPenaltyAmout())).doubleValue() : 0.0);
			repayRecordMap.put("repayTime", borrowRepayLog.getCreateTime().getTime());
			repayRecordMap.put("repayType", 1);
			repayRecordMap.put("returnedBillAmount", borrowRepayLog.getAmount());
			repayRecordMap.put("returnedPentaltyAmount", borrowRepayLog.getPenaltyAmout());
			repayRecordMap.put("thirdRepayRecordId", borrowRepayLog.getId());
			repayRecord.add(repayRecordMap);
			args.put("repayRecord", repayRecord);

			Map<String, Object> order = new HashMap<>();
			order.put("orderNo", borrow.getOrderNo());
			order.put("settledTime", borrowRepayLog.getCreateTime().getTime());
			order.put("penaltyDays", borrowRepayLog.getPenaltyDay());
			order.put("penaltyAmount", borrowRepayLog.getPenaltyAmout());
			order.put("thirdUserId", borrowRepayLog.getUserId());
			order.put("orderStatus", 1);
			args.put("order", order);

			String sign_key = org_id + key + org_id;
			logger.info("sign_key:" + sign_key);
			String plain = sign_key + call + sign_key + JSON.toJSONString(args) + sign_key;
			logger.info("plain:" + plain);

			String sign = MD5.SunMD5(plain);
			param.put("sign", sign);
			param.put("args", args);

			Map<String, String> headers = new HashMap<>();
			String sendPost = HttpClientUtil.sendPost(url, headers, JSON.toJSONString(param), "UTF-8");
			logger.info("borrowId:" + borrowId + ",推送push-repay返回结果:" + sendPost);

			UrgeOrderPushLog urgeOrderPushLog = new UrgeOrderPushLog();
			urgeOrderPushLog.setUserId(borrow.getUserId());
			urgeOrderPushLog.setBorrowId(borrowId);
			urgeOrderPushLog.setParamJson(JSON.toJSONString(param));
			urgeOrderPushLog.setReturnJson(sendPost);
			urgeOrderPushLog.setCreateTime(new Date());
			result = urgeOrderPushLogMapper.save(urgeOrderPushLog);
			logger.info("borrowId:" + borrowId + ",推送push-repay保存log记录结果:" + result);

			Boolean flag = true;
			if (StringUtil.isNotBlank(sendPost)) {
				JSONObject parseObject = JSON.parseObject(sendPost);
				if (parseObject != null && parseObject.containsKey("code") && 200 == parseObject.getInteger("code")) {
					flag = false;
					urgePushStateRecord.setRepayState("1");
					urgePushStateRecord.setUpdateTime(new Date());
					result = urgePushStateRecordMapper.update(urgePushStateRecord);
				}
			}

			if (flag) {
				urgePushStateRecord.setRepayState("-1s");
				urgePushStateRecord.setUpdateTime(new Date());
				result = urgePushStateRecordMapper.update(urgePushStateRecord);
			}
		}

		return result;
	}

	@Override
	public List getPenaltyAmountByOrderNo(String orderNo) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderNo", orderNo);
		Borrow borrow = clBorrowMapper.findSelective(paramMap);
		if (borrow != null) {
			paramMap.put("borrowId", borrow.getId());
			BorrowRepay borrowRepay = borrowRepayService.findSelective(paramMap);
			if (borrowRepay != null) {
				Map<String, Object> result = new HashMap<>();
				result.put("penaltyAmount", borrowRepay.getPenaltyAmout());
				result.put("fundType", 1);
				list.add(result);
			}
		}
		return list;
	}

	public List<UserContacts> getTopList(Long userId) {
		List<UserContacts> result = new ArrayList<>();
		// 分表
		String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
		int countTable = userContactsMapper.countTable(tableName);
		if (countTable == 0) {
			userContactsMapper.createTable(tableName);
		}

		/** 刘晓亮 start 返回通讯录的备注信息 */
		// 通讯录备注表 分表
		String remarkTableName = ShardTableUtil.generateTableNameById("cl_user_contacts_remark", userId, 30000);
		int remarkCountTable = userContactsMapper.countTable(remarkTableName);
		if (remarkCountTable == 0) {
			userContactsMapper.createContactRemarkTable(remarkTableName);
		}
		/** 刘晓亮 end */

		List<UserContacts> list = null;
		Long borrowId = userContactsMapper.findMaxBorrowidByUserid(tableName, userId);
		if (borrowId == null || borrowId == 0) {
			Map<String, Object> params = new HashMap<>();
			params.put("userId", userId);
			// PageHelper.startPage(current, pageSize);
			list = userContactsMapper.listShardSelective(tableName, params);
		} else {
			// PageHelper.startPage(current, pageSize);
			list = userContactsMapper.listShardByBorrowid(tableName, borrowId);
		}

		list = removeDuplicatePlan(list);

		List<UserContacts> resultContactRemarkList = new ArrayList<>();

		// ----查询有备注功能的
		if (list != null && list.size() > 0) {
			for (UserContacts userContacts : list) {
				String selectParamSql = "select contact_remark from " + remarkTableName + " where user_id = " + userId
						+ " and contact_phone = '" + userContacts.getPhone() + "' limit 1";

				String remark = userContactsMapper.selectContactRemarkBySql(selectParamSql);
				if (StringUtil.isNotBlank(remark)) {
					userContacts.setRemark(remark);
					resultContactRemarkList.add(userContacts);
				}
			}
		}

		// ----查询短信联系人top前20
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		String tableNameMsg = ShardTableUtil.generateTableNameById("cl_user_messages", userId, 30000);
		int countTableMsg = clUserMessagesMapper.countTable(tableNameMsg);
		if (countTableMsg == 0) {
			clUserMessagesMapper.createTable(tableNameMsg);
		}

		// List<Map<String, Object>> msgTopMap = new ArrayList<Map<String,
		// Object>>();
		List<Map<String, Object>> msgTopMap = clUserMessagesMapper.listShardByBorrowidTop20(tableNameMsg, borrowId);
		if (msgTopMap == null || msgTopMap.size() <= 0) {
			Date maxCreatetime = clUserMessagesMapper.findMaxCreatetimeByUserid(tableNameMsg, userId);
			if (maxCreatetime == null) {
				msgTopMap = clUserMessagesMapper.listShardSelectiveTop20(tableNameMsg, paramMap);
			} else {
				msgTopMap = clUserMessagesMapper.findListByUseridAndCreatetimeTop20(tableName, userId, maxCreatetime);
			}
		} else {

		}
		Map<String, Object> msgTopMapRes = new HashMap<>();
		if (msgTopMap != null) {
			for (Map<String, Object> map : msgTopMap) {
				msgTopMapRes.put(map.get("name").toString(), map.get("num"));
			}
		}

		// ---查询命中紧急联系人和工作联系人
		// 查询紧急联系人
		List<UserContacts> resultEmerList = new ArrayList<>();
		List<UserEmerContacts> emerContacts = userEmerContactsMapper.listSelective(paramMap);
		List<String> emerNameList = new ArrayList<>();
		if (emerContacts != null && emerContacts.size() > 0) {
			for (UserEmerContacts userEmerContacts : emerContacts) {
				emerNameList.add(userEmerContacts.getName());
				UserContacts userContacts = new UserContacts();
				userContacts.setUserId(userId);
				userContacts.setName(userEmerContacts.getName());
				userContacts.setPhone(userEmerContacts.getPhone());
				userContacts.setMsgTimes(userEmerContacts.getRelation());
				resultEmerList.add(userContacts);
			}
		}
		List<UserContacts> resultMsgTopList = new ArrayList<>();

		List<UserContacts> resultWorkList = new ArrayList<>();
		List<String> workModelList = ListUtil.parseConfigFileToList("workContactModel");
		List<UserContacts> resultContactSYList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (UserContacts userContacts : list) {
				if (msgTopMapRes.containsKey(userContacts.getName())) {
					userContacts.setMsgTimes(msgTopMapRes.get(userContacts.getName()).toString());
					resultMsgTopList.add(userContacts);
				} else if (workModelList.contains(userContacts.getName())) {
					userContacts.setMsgTimes("reference for work");
					resultWorkList.add(userContacts);
				} else if (StringUtil.isBlank(userContacts.getRemark())) {
					resultContactSYList.add(userContacts);
				}
			}
		}
		// 往后排的最先放
		result.addAll(resultEmerList);
		result.addAll(resultContactRemarkList);
		resultMsgTopList = search(resultMsgTopList);
		if (resultMsgTopList != null && resultMsgTopList.size() > 20) {
			result.addAll(resultMsgTopList.subList(0, 20));
		} else {
			result.addAll(resultMsgTopList);
		}
		result.addAll(resultWorkList);
		result.addAll(resultContactSYList);
		result.removeAll(Collections.singleton(null));
		return result;
	}

	public List<UserContacts> search(List<UserContacts> logsList) {
		Collections.sort(logsList, new Comparator<UserContacts>() {
			@Override
			public int compare(UserContacts o1, UserContacts o2) {
				return Integer.parseInt(o2.getMsgTimes()) - Integer.parseInt(o1.getMsgTimes());
			}
		});
		return logsList;
	}

	private static List<UserContacts> removeDuplicatePlan(List<UserContacts> planList) {
		Set<UserContacts> set = new TreeSet<UserContacts>(new Comparator<UserContacts>() {
			@Override
			public int compare(UserContacts a, UserContacts b) {
				// 字符串则按照asicc码升序排列
				return a.getPhone().replace(" ", "").compareTo(b.getPhone().replace(" ", ""));
			}
		});

		set.addAll(planList);
		return new ArrayList<UserContacts>(set);
	}
	

	public void putVoice(Long borrowId, Map<String, Object> map){
		Borrow borrow = clBorrowService.findByPrimary(borrowId);
		UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(borrow.getUserId());
		String reg_province = userBaseInfo.getRegisterAddr();
		String stateByBorrowAddress = AfricaStateEnum.getStateByBorrowAddress(reg_province);
		map.put("regProvince", ("").equals(stateByBorrowAddress)?"unknown": stateByBorrowAddress);
		
		String apply_province = borrow.getAddress();
		String stateByBorrowAddress1 = AfricaStateEnum.getStateByBorrowAddress(apply_province);

		map.put("applyProvince", ("").equals(stateByBorrowAddress1)?"unknown": stateByBorrowAddress);
	}


	
}