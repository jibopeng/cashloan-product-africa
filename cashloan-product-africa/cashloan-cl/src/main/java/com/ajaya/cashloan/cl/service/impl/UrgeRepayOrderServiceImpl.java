package com.ajaya.cashloan.cl.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BorrowRepayLog;
import com.ajaya.cashloan.cl.mapper.BorrowRepayMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.mapper.UrgeRepayOrderLogMapper;
import com.ajaya.cashloan.cl.mapper.UrgeRepayOrderMapper;
import com.ajaya.cashloan.cl.model.UrgeRepayOrderModel;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.domain.BorrowRepayFq;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrder;
import com.ajaya.cashloan.cl.mapper.BorrowRepayFqMapper;
import com.ajaya.cashloan.cl.mapper.BorrowRepayLogMapper;
import com.ajaya.cashloan.cl.model.UrgeRepayCountModel;
import com.ajaya.cashloan.cl.service.BorrowRepayFqService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.system.mapper.SysUserMapper;

import tool.util.BigDecimalUtil;
import tool.util.StringUtil;

/**
 * 催款计划表ServiceImpl
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-03-07 14:21:58
 */

@Service("urgeRepayOrderService")
public class UrgeRepayOrderServiceImpl extends BaseServiceImpl<UrgeRepayOrder, Long> implements UrgeRepayOrderService {

	private static final Logger logger = LoggerFactory.getLogger(UrgeRepayOrderServiceImpl.class);

	@Resource
	private UrgeRepayOrderMapper urgeRepayOrderMapper;
	@Resource
	private UrgeRepayOrderLogMapper urgeRepayOrderLogMapper;
	@Resource
	private ClBorrowMapper clBorrowMapper;
	@Resource
	private BorrowRepayMapper borrowRepayMapper;
	@Resource
	private UserBaseInfoMapper userBaseinfoMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private BorrowRepayLogMapper borrowRepayLogMapper;
	@Resource
    private BorrowRepayFqService borrowRepayFqService;
    @Resource
    private BorrowRepayFqMapper borrowRepayFqMapper;

	@Override
	public BaseMapper<UrgeRepayOrder, Long> getMapper() {
		return urgeRepayOrderMapper;
	}

	/**
	 * 将查询条件中level转换为penaltyDay区间
	 * 
	 * @param params
	 * @return
	 */
	private Map<String, Object> levelAsPenaltyDay(Map<String, Object> params) {
		String level = StringUtil.isNull(params.get("level"));
		if (StringUtil.isBlank(level)) {
			return params;
		}

		String penaltyDayLevel = Global.getValue("penalty_day_level");
		String[] penaltyDays = penaltyDayLevel.split(",");
		int penaltyDayOne = Integer.parseInt(penaltyDays[0]);
		int penaltyDayTwo = Integer.parseInt(penaltyDays[1]);
		if (StringUtil.isNotBlank(level) && "M1".equals(level)) {
			params.put("penaltyDayOne", penaltyDayOne);
		} else if (StringUtil.isNotBlank(level) && "M2".equals(level)) {
			params.put("penaltyDayOne", penaltyDayOne);
			params.put("penaltyDayTwo", penaltyDayTwo);
		} else if (StringUtil.isNotBlank(level) && "M3".equals(level)) {
			params.put("penaltyDayTwo", penaltyDayTwo);
		}
		return params;
	}

	@Override
	public Page<UrgeRepayOrder> list(Map<String, Object> params, int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		/** 刘晓亮 2017年12月1日 15:39:53 返回数据增加反馈字段 */
		// 原来的 List<UrgeRepayOrder> list =
		// urgeRepayOrderMapper.listSelective(levelAsPenaltyDay(params));
		List<UrgeRepayOrder> list = null;
		if (params.containsKey("startTime") && params.containsKey("endTime")) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date start = sdf.parse(params.get("endTime").toString());
				Date end = DateUtil.dateAddDays(start, 1);
				params.put("endTime", sdf.format(end));

			} catch (Exception e) {
			}
		}
		logger.info("params----" + JSON.toJSONString(params));
		if ("40".equals(params.get("state"))) {
			list = urgeRepayOrderMapper.listSelectiveForCuiShou40(params);
		} else {
			list = urgeRepayOrderMapper.listSelectiveForCuiShou(params);
		}
		
		// 遍历获取借款人的user_id
		Map<String, Object> paramMap = new HashMap<>();
		if (list != null && list.size() > 0) {
			for (UrgeRepayOrder urgeRepayOrder : list) {
				paramMap.put("phone", urgeRepayOrder.getPhone());
				UserBaseInfo userBase = userBaseinfoMapper.findSelective(paramMap);
				if(userBase != null){
					urgeRepayOrder.setClUserId(userBase.getUserId());
				}
				urgeRepayOrder.setPhone(null);
				Long borrowId = urgeRepayOrder.getBorrowId();
				//查询已还金额
				paramMap.put("borrowId", borrowId);
				BorrowRepayLog borrowRepayLog = borrowRepayLogMapper.findSelective(paramMap);
				Double successMoneyFq = 0.0;
				if(borrowRepayLog == null){
					successMoneyFq = borrowRepayFqMapper.getSuccessMoneyFq(borrowId);
				} else {
					successMoneyFq = borrowRepayLog.getAmount() + borrowRepayLog.getPenaltyAmout();
				}
				
				urgeRepayOrder.setSuccessMoneyFq(successMoneyFq);
				//剩下应还金额
				Double shengXiaRepayMoney = borrowRepayFqService.getShengXiaRepayMoney(borrowId);
				urgeRepayOrder.setShengXiaRepayMoney(shengXiaRepayMoney);
				//最近一笔待分期还款金额
				BorrowRepayFq borrowRepayFq = borrowRepayFqMapper.getCurrentBorrowRepayFq(borrowId);
				Double CurrentBorrowRepayFqMoney = 0.0;
				String currentBorrowRepayFqStr = "";
				if(borrowRepayFq != null){
					CurrentBorrowRepayFqMoney = borrowRepayFq.getAmount();
					Date createTimeFq = borrowRepayFq.getCreateTime();
					if(borrowRepayFq.getUpdateTime() != null){
						createTimeFq = borrowRepayFq.getUpdateTime();
					}
					int fqValidTime = Global.getInt("fq_valid_time");
					int hours =  DateUtil.minuteBetween(createTimeFq, new Date()) / 60;
					if(hours <= fqValidTime){
						currentBorrowRepayFqStr = (fqValidTime - hours) + "h";
					} else {
						currentBorrowRepayFqStr = "expired";
					}
				}
				urgeRepayOrder.setCurrentBorrowRepayFqMoney(CurrentBorrowRepayFqMoney);
				urgeRepayOrder.setCurrentBorrowRepayFqStr(currentBorrowRepayFqStr);
			}
		}
		logger.info("访问新接口");
		return (Page<UrgeRepayOrder>) list;
	}

	@Override
	public List<?> listUrgeRepayOrder(Map<String, Object> params) {
		/** 刘晓亮 2017年12月1日 15:39:53 返回数据增加反馈字段 */
		// 原来的 List<UrgeRepayOrder> list =
		// urgeRepayOrderMapper.listSelective(levelAsPenaltyDay(params));
		List<UrgeRepayOrder> list = null;
		if (params.containsKey("startTime") && params.containsKey("endTime")) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date start = sdf.parse(params.get("endTime").toString());
				Date end = DateUtil.dateAddDays(start, 1);
				params.put("endTime", sdf.format(end));
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("params----" + JSON.toJSONString(params));
		}
		if ("40".equals(params.get("state"))) {
			list = urgeRepayOrderMapper.listSelectiveForCuiShou40(params);
		} else {
			list = urgeRepayOrderMapper.listSelectiveForCuiShou(params);
		}

		// List<UrgeRepayOrder> list =
		// urgeRepayOrderMapper.listSelectiveForCuiShou(params);
		// 遍历获取借款人的user_id
		Map<String, Object> paramMap = new HashMap<>();
		if (list != null && list.size() > 0) {
			for (UrgeRepayOrder urgeRepayOrder : list) {
				paramMap.put("phone", urgeRepayOrder.getPhone());
				UserBaseInfo userBase = userBaseinfoMapper.findSelective(paramMap);
				if(userBase != null){
					urgeRepayOrder.setClUserId(userBase.getUserId());
				}
				urgeRepayOrder.setPhone(null);
			}
		}
		logger.info("访问新接口");
		return list;
	}

	/**
	 * 判断是否为续贷
	 * 
	 * @author luyu
	 * @date 2017-11-16
	 * @param userId
	 * @param borrowId
	 * @return
	 */
	public boolean isRefinance(Long userId, Long borrowId) {
		// 续贷标志位
		Boolean refinanceFlag = false;
		// 判断该比订单是否是续贷，该订单对应的用户之前的所有借款订单 ，只要有一笔还款，就算是续贷
		// 去 cl_borrow_repay 表中查询数据,是否有还款成功的
		Map<String, Object> paramMapForRepaymentSuccess = new HashMap<>();
		paramMapForRepaymentSuccess.put("userId", userId);
		paramMapForRepaymentSuccess.put("state", 10);
		List<BorrowRepay> listSelective = borrowRepayMapper.listSelective(paramMapForRepaymentSuccess);
		if (listSelective != null && listSelective.size() > 0) {
			// 该用户有还款成功的记录，属于续贷
			logger.info("订单 " + borrowId + " 属于 user_id：" + userId + "------是续贷");
			refinanceFlag = true;
		} else {
			// 该用户没有还款成功的记录，不属于续贷
			logger.info("订单 " + borrowId + " 属于 user_id " + userId + "------不是续贷");
		}
		return refinanceFlag;
	}

	public Page<UrgeRepayOrderModel> listModel(Map<String, Object> params, int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<UrgeRepayOrderModel> list = urgeRepayOrderMapper.listModel(levelAsPenaltyDay(params));
		return (Page<UrgeRepayOrderModel>) list;
	}

	@Override
	public int orderAllotUser(Map<String, Object> params) {
		return urgeRepayOrderMapper.updateSelective(params);
	}

	@Override
	public Map<String, Object> saveOrder(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Borrow b = clBorrowMapper.findByPrimary(id);
		UserBaseInfo userBaseInfo = userBaseinfoMapper.findByUserId(b.getUserId());
		// 是否逾期标判断
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("borrowId", b.getId());
		List<UrgeRepayOrder> list = urgeRepayOrderMapper.listSelective(params);
		if (list.size() <= 0) {
			UrgeRepayOrder order = new UrgeRepayOrder();
			order.setBorrowId(b.getId());
			order.setOrderNo(b.getOrderNo());
			order.setBorrowTime(b.getCreateTime());
			order.setTimeLimit(b.getTimeLimit());

			params = new HashMap<String, Object>();
			params.put("userId", b.getUserId());
			UserBaseInfo user = userBaseinfoMapper.findSelective(params);
			if (user != null) {
				order.setPhone(user.getPhone());
				order.setBorrowName(user.getRealName());
			}
			params = new HashMap<String, Object>();
			params.put("borrowId", b.getId());
			BorrowRepay br = borrowRepayMapper.findSelective(params);
			if (br != null) {
				order.setAmount(br.getAmount());
				order.setRepayTime(br.getRepayTime());
				order.setPenaltyDay(Long.valueOf(br.getPenaltyDay()));
				order.setPenaltyAmout(br.getPenaltyAmout());
			}

			order.setState(UrgeRepayOrderModel.STATE_ORDER_PRE);
			order.setCreateTime(new Date());
			order.setCount(Long.valueOf(0));

			boolean refinance = isRefinance(b.getUserId(), b.getId());
			if (refinance) {
				order.setBorrowFlag("old");
			} else {
				order.setBorrowFlag("new");
			}
			// 设置语言

				order.setLanguage("normal");

			urgeRepayOrderMapper.save(order);

			result.put("code", Constant.SUCCEED_CODE_VALUE);
			result.put("msg", "提交成功");
			return result;
		} else {
			result.put("code", Constant.FAIL_CODE_VALUE);
			result.put("msg", "已存在催收订单中，请勿重复添加");
		}
	
		return result;
	}

	@Override
	public List<UrgeRepayOrder> listAll(HashMap<String, Object> hashMap) {
		List<UrgeRepayOrder> list = urgeRepayOrderMapper.listSelective(hashMap);
		return list;
	}

	@Override
	public Page<UrgeRepayCountModel> memberCount(Map<String, Object> params, int current, int pageSize) {
		params = params == null ? new HashMap<String, Object>() : params;
		params.put("roleNid", "collectionSpecialist");

		PageHelper.startPage(current, pageSize);
		List<UrgeRepayCountModel> modelList = urgeRepayOrderMapper.listSysUserByRole(params);
		for (UrgeRepayCountModel model : modelList) {
			long userId = model.getUserId();

			params.clear();
			params.put("userId", userId);
			model.setCount(urgeRepayOrderMapper.countOrder(params));

			params.put("state", UrgeRepayOrderModel.STATE_ORDER_WAIT);
			model.setWaitCount(urgeRepayOrderMapper.countOrder(params));

			params.put("state", UrgeRepayOrderModel.STATE_ORDER_SUCCESS);
			model.setSuccessCount(urgeRepayOrderMapper.countOrder(params));

			Date yester = DateUtil.rollDay(DateUtil.getNow(), -1);
			params.put("startTime", DateUtil.getDayStartTime(yester));
			params.put("endTime", DateUtil.getDayEndTime(yester));
			model.setYesterdayCount(urgeRepayOrderLogMapper.countLog(params));

		}

		return (Page<UrgeRepayCountModel>) modelList;
	}

	@Override
	public List<UrgeRepayCountModel> orderCount(Map<String, Object> params) {
		List<UrgeRepayCountModel> list = null;
		try {
			list = new ArrayList<>();
			Date startTime = new Date();
			Date endTime = new Date();
			List<Date> dateList = null;
			if (params == null) {
				startTime = DateUtil.getDateBefore(-10, endTime);
				dateList = dateList(startTime, endTime);
			} else {
				endTime = DateUtil.valueOf(params.get("afterTime").toString());
				startTime = DateUtil.valueOf(params.get("beforeTime").toString());
				dateList = dateList(startTime, endTime);
			}
			for (Date date : dateList) {
				Map<String, Object> map = new HashMap<>();
				map.put("date", date);
				UrgeRepayCountModel urcModel = new UrgeRepayCountModel();
				urcModel.setCreateTime(date);
				// 累计统计
				String allOrder = urgeRepayOrderMapper.allOrderSum(map);
				urcModel.setAllOrderCount(allOrder != null ? Integer.parseInt(allOrder) : 0);

				String allSuccess = urgeRepayOrderMapper.allSuccessSum(map);
				urcModel.setAllSuccessCount(allSuccess != null ? Integer.parseInt(allSuccess) : 0);

				String allFail = urgeRepayOrderMapper.allFailSum(map);
				urcModel.setAllFailCount(allFail != null ? Integer.parseInt(allFail) : 0);

				if (urcModel.getAllSuccessCount() > urcModel.getAllOrderCount()) {
					urcModel.setAllBackRate(100.00);
				} else if (urcModel.getAllOrderCount() > 0) {
					double allBackRate = urcModel.getAllSuccessCount() / urcModel.getAllOrderCount();
					if (allBackRate * 100 > 100) {
						urcModel.setAllBackRate(100.00);
					} else {
						urcModel.setAllBackRate(BigDecimalUtil.decimal((allBackRate * 100), 2));
					}
				}
				// 今日统计
				String newOrder = urgeRepayOrderMapper.newOrderByUser(map);
				urcModel.setOrderCount(newOrder != null ? Integer.parseInt(newOrder) : 0);
				String repayOrder = urgeRepayOrderMapper.repayOrderByUser(map);
				urcModel.setPromisCount(repayOrder != null ? Integer.parseInt(repayOrder) : 0);
				String successOrder = urgeRepayOrderMapper.successOrderByUser(map);
				urcModel.setSuccessCount(successOrder != null ? Integer.parseInt(successOrder) : 0);
				String failOrder = urgeRepayOrderMapper.failOrderByUser(map);
				urcModel.setFailCount(failOrder != null ? Integer.parseInt(failOrder) : 0);
				String count = urgeRepayOrderMapper.countByUser(map);
				urcModel.setCount(count != null ? Integer.parseInt(count) : 0);
				urcModel.setBackRate(0.00);
				if (urcModel.getSuccessCount() > urcModel.getOrderCount()) {
					urcModel.setBackRate(100.00);
				} else if (urcModel.getOrderCount() > 0) {
					double backRate = urcModel.getSuccessCount() / urcModel.getOrderCount();
					if (backRate > 100) {
						urcModel.setBackRate(100.00);
					} else {
						urcModel.setBackRate(BigDecimalUtil.decimal((backRate * 100), 2));
					}
				}

				list.add(urcModel);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<UrgeRepayCountModel> urgeCount(Map<String, Object> params) {
		List<UrgeRepayCountModel> list = null;
		try {
			list = new ArrayList<>();
			Date startTime = new Date();
			Date endTime = new Date();
			List<Date> dateList = null;
			if (params == null) {
				startTime = DateUtil.getDateBefore(-10, endTime);
				dateList = dateList(startTime, endTime);
			} else {
				endTime = DateUtil.valueOf(params.get("afterTime").toString());
				startTime = DateUtil.valueOf(params.get("beforeTime").toString());
				dateList = dateList(startTime, endTime);
			}
			for (int i = 0; i < dateList.size(); i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("date", dateList.get(i));
				UrgeRepayCountModel urcModel = new UrgeRepayCountModel();
				urcModel.setCreateTime(dateList.get(i));
				String allOrderCount = urgeRepayOrderMapper.allOrderCount(map);
				urcModel.setOrderCount(Integer.valueOf(allOrderCount == null ? "0" : allOrderCount));
				String successCount = urgeRepayOrderMapper.successCount(map);
				urcModel.setSuccessCount(Integer.valueOf(successCount == null ? "0" : successCount));
				String Count = urgeRepayOrderMapper.count(map);
				urcModel.setCount(Integer.valueOf(Count == null ? "0" : Count));
				if (urcModel.getOrderCount() == 0) {
					urcModel.setBackRate(0);
				} else {
					urcModel.setBackRate(BigDecimalUtil.decimal(
							Double.valueOf(urcModel.getSuccessCount()) / Double.valueOf(urcModel.getOrderCount()) * 100,
							2));
				}

				list.add(urcModel);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<UrgeRepayCountModel> memberDayCount(Map<String, Object> params) {
		List<UrgeRepayCountModel> list = null;
		try {
			list = new ArrayList<>();
			Date startTime = new Date();
			Date endTime = new Date();
			List<Date> dateList = null;
			if (params == null) {
				startTime = DateUtil.getDateBefore(-10, endTime);
				dateList = dateList(startTime, endTime);
			} else {
				endTime = DateUtil.valueOf(params.get("afterTime").toString());
				startTime = DateUtil.valueOf(params.get("beforeTime").toString());
				dateList = dateList(startTime, endTime);
			}
			for (Date date : dateList) {
				Map<String, Object> map = new HashMap<>();
				List<UrgeRepayOrder> uroList = urgeRepayOrderMapper.listOrder(map);
				for (UrgeRepayOrder urgeRepayOrder : uroList) {
					if (StringUtil.isNull(urgeRepayOrder.getUserName()) != null) {
						UrgeRepayCountModel urcModel = new UrgeRepayCountModel();
						map.put("date", date);
						map.put("userId", urgeRepayOrder.getUserId());
						urcModel.setName(urgeRepayOrder.getUserName());
						String newOrder = urgeRepayOrderMapper.newOrderByUser(map);
						urcModel.setOrderCount(newOrder != null ? Integer.parseInt(newOrder) : 0);
						String repayOrder = urgeRepayOrderMapper.repayOrderByUser(map);
						urcModel.setPromisCount(repayOrder != null ? Integer.parseInt(repayOrder) : 0);
						String successOrder = urgeRepayOrderMapper.successOrderByUser(map);
						urcModel.setSuccessCount(successOrder != null ? Integer.parseInt(successOrder) : 0);
						String failOrder = urgeRepayOrderMapper.failOrderByUser(map);
						urcModel.setFailCount(failOrder != null ? Integer.parseInt(failOrder) : 0);
						String count = urgeRepayOrderMapper.countByUser(map);
						urcModel.setCount(count != null ? Integer.parseInt(count) : 0);
						urcModel.setBackRate(0.00);
						if (urcModel.getSuccessCount() > urcModel.getOrderCount()) {
							urcModel.setBackRate(100.00);
						} else if (urcModel.getOrderCount() > 0) {
							double backRate = urcModel.getSuccessCount() / urcModel.getOrderCount();
							if (backRate > 100) {
								urcModel.setBackRate(100.00);
							} else {
								urcModel.setBackRate(BigDecimalUtil.decimal((backRate * 100), 2));
							}
						}
						urcModel.setCreateTime(date);
						list.add(urcModel);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public UrgeRepayOrder findByBorrowId(long borrowId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("borrowId", borrowId);
		return urgeRepayOrderMapper.findSelective(map);
	}

	@Override
	public int updateLate(Map<String, Object> uroMap) {
		return urgeRepayOrderMapper.updateSelective(uroMap);
	}

	@Override
	public UrgeRepayOrder findOrderByMap(Map<String, Object> orderMap) {

		return urgeRepayOrderMapper.findSelective(orderMap);
	}

	@Override
	public List<?> listUrgeLog(Map<String, Object> params) {
		List<UrgeRepayOrderModel> list = urgeRepayOrderMapper.listModel(params);
		for (UrgeRepayOrderModel uroModel : list) {
			uroModel.setState(UrgeRepayOrderModel.change(uroModel.getState()));
			switch (uroModel.getWay()) {
			case "10":
				uroModel.setWay("电话");
				break;
			case "20":
				uroModel.setWay("邮件");
				break;
			case "30":
				uroModel.setWay("短信");
				break;
			case "40":
				uroModel.setWay("现场沟通");
				break;
			case "50":
				uroModel.setWay("其他");
				break;
			}
		}
		return list;
	}

	/**
	 * 返回时间集合
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<Date> dateList(Date startTime, Date endTime) throws Exception {
		startTime = DateUtil.getDateBefore(0, startTime);
		List<Date> lists = DateUtil.dateSplit(startTime, endTime);
		if (!lists.isEmpty()) {
			return lists;
		}
		return null;
	}

}