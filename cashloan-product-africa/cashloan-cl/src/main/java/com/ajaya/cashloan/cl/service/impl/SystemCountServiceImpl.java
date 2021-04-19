package com.ajaya.cashloan.cl.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import tool.util.BigDecimalUtil;
import tool.util.StringUtil;

import com.ajaya.cashloan.cl.mapper.SystemCountMapper;
import com.ajaya.cashloan.cl.service.SystemCountService;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.system.domain.SysUserChannel;
import com.ajaya.cashloan.system.domain.SysUserChannelMoney;

/**
 * 首页系统数据统计
 * 
 * @author caitt
 * @version 1.0
 * @date 2017年3月16日上午10:16:51
 */
@SuppressWarnings("unchecked")
@Service("systemCountService")
public class SystemCountServiceImpl implements SystemCountService {

	@Resource
	private SystemCountMapper systemCountMapper;

	public Map<String, Object> reBuildMap(List<Map<String, Object>> maps) {
		if (maps != null) {
			Map<String, Object> result = new HashMap<String, Object>();
			for (int i = 0; i < maps.size(); i++) {
				String key = String.valueOf(maps.get(i).get("key"));
				if (StringUtil.isNotBlank(key)) {
					key = key == null ? "" : key;

				} else {
					key = "未知地区";
				}
				Object value = maps.get(i).get("value");
				result.put(key, value);
			}
			result.remove("null");
			return result;
		} else {
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 查询时间过长的内容保存context
	 * 
	 * @param result
	 * @param rtValue
	 * @param rtMap
	 * @throws ParseException
	 */
	private void areaCountDispose(Map<String, Object> result, List<Map<String, Object>> rtValue,
			Map<String, Object> rtMap, List<SysUserChannel> sysUserChannelList) throws ParseException {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext context = webApplicationContext.getServletContext();

		//刘晓亮 start
		if(sysUserChannelList != null && sysUserChannelList.size() > 0){
			rtValue = areaCount(1,sysUserChannelList);
			result = reBuildMap(rtValue);
			rtMap.put("areaBorrowAmt", result);

			rtValue = areaCount(2,sysUserChannelList);
			result = reBuildMap(rtValue);
			rtMap.put("areaBorrowRepay", result);

			rtValue = areaCount(3,sysUserChannelList);
			result = reBuildMap(rtValue);
			rtMap.put("areaRegister", result);
			return;
		}
		//刘晓亮 end
		
		
		if (StringUtil.isNotBlank(context)) {
			Object t = context.getAttribute("areaCountSelectTime");
			String todayStr = DateUtil.dateStr2(DateUtil.getNow());
			if (!todayStr.equals(t)) {

				rtValue = areaCount(1,sysUserChannelList);
				result = reBuildMap(rtValue);
				rtMap.put("areaBorrowAmt", result);
				context.setAttribute("areaBorrowAmt", rtValue);

				rtValue = areaCount(2,sysUserChannelList);
				result = reBuildMap(rtValue);
				rtMap.put("areaBorrowRepay", result);
				context.setAttribute("areaBorrowRepay", rtValue);

				rtValue = areaCount(3,sysUserChannelList);
				result = reBuildMap(rtValue);
				rtMap.put("areaRegister", result);
				context.setAttribute("areaRegister", rtValue);

				context.setAttribute("areaCountSelectTime", todayStr);// 保存时间

			} else {

				rtValue = (List<Map<String, Object>>) context.getAttribute("areaBorrowAmt");
				result = reBuildMap(rtValue);
				rtMap.put("areaBorrowAmt", result);

				rtValue = (List<Map<String, Object>>) context.getAttribute("areaBorrowRepay");
				result = reBuildMap(rtValue);
				rtMap.put("areaBorrowRepay", result);

				rtValue = (List<Map<String, Object>>) context.getAttribute("areaRegister");
				result = reBuildMap(rtValue);
				rtMap.put("areaRegister", result);
			}
		}
	}

	// 所有地区数组
	private static String[] address = {"Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Dadra and Nagar Haveli","Delhi","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Pondicherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttarakhand","Uttar Pradesh","West Bengal"};

	// 地区显示处理
	private String changeAdd(String address) {
		address = address.replace("省", "").replace("市", "").replace("自治区", "").replace("回族", "").replace("维吾尔", "")
				.replace("壮族", "").replace("特别行政区", "");
		return address;
	}

	// 累计数据统计
	private List<Map<String, Object>> areaCount(int type,List<SysUserChannel> sysUserChannelList) {
		Map<String, Object> param = new HashMap<String, Object>();
		// key 是 渠道ID, value 是该渠道对应的值
		Map<Long, Long> channelMap = new HashMap<Long, Long>();
		// 获取渠道用户有哪些渠道ID
		for (SysUserChannel sysUserChannel : sysUserChannelList) {
			channelMap.put(sysUserChannel.getChannelId(), 0L);
		}
		List<Map<Long, Long>> userIdAndChannelIdListMap = systemCountMapper.countRegisterForChannleAll(null);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<Long, Long> userIdAndChannelIdMap = new HashMap<Long, Long>();
		if (userIdAndChannelIdListMap != null && userIdAndChannelIdListMap.size() > 0) {
			for (Map<Long, Long> map : userIdAndChannelIdListMap) {
				userIdAndChannelIdMap.put(map.get("userId"), map.get("channelId"));
			}
		}
		switch (type) {
		case 1:
			for (int i = 0; i < address.length; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("key", changeAdd(address[i]));
				//原来的
				map.put("value", systemCountMapper.sumBorrowAmtByProvince("%" + address[i] + "%"));
				list.add(map);
			}
			break;
		case 2:
			for (int i = 0; i < address.length; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("key", changeAdd(address[i]));
				//原来的
				map.put("value", systemCountMapper.sumBorrowRepayByProvince("%" + address[i] + "%"));
				list.add(map);
			}
			break;
		default:
			for (int i = 0; i < address.length; i++) {
				Map<String, Object> map = new HashMap<>();
				String userCount = "";
				//原来的
				userCount = systemCountMapper.countRegisterByProvince("%" + address[i] + "%");
				if (userCount != null && userCount != "" && Integer.valueOf(userCount) > 0) {
					map.put("key", changeAdd(address[i]));
					map.put("value", userCount);
				}
				list.add(map);
			}
			break;
		}
		return list;
	}

	@Override
	public Map<String, Object> todayInfo() throws Exception {
		Map<String, Object> rtMap = new HashMap<String, Object>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("todayTime", DateUtil.getNow());
		Integer register = systemCountMapper.countRegister(param);
		rtMap.put("register", register);

		Integer login = systemCountMapper.countLogin(param);
		rtMap.put("login", login);

		double borrow = systemCountMapper.countBorrow(param);
		rtMap.put("borrow", borrow);

		double borrowPass = systemCountMapper.countBorrowPass(param);
		rtMap.put("borrowPass", borrowPass);

		if (borrow > 0) {
			rtMap.put("passApr", BigDecimalUtil.decimal(borrowPass / borrow * 100, 2));
		} else {
			rtMap.put("passApr", 0);
		}

		Integer borrowLoan = systemCountMapper.countBorrowLoan(param);
		rtMap.put("borrowLoan", borrowLoan);

		Integer borrowRepay = systemCountMapper.countBorrowRepay(param);
		rtMap.put("borrowRepay", borrowRepay);

		return rtMap;
	}

	@Override
	public Map<String, Object> cumulativeInfo() throws Exception {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		Integer borrowLoanHistory = systemCountMapper.countBorrowLoanHistory();
		rtMap.put("borrowLoanHistory", borrowLoanHistory);

		Integer borrowRepayHistory = systemCountMapper.countBorrowRepayHistory();
		rtMap.put("borrowRepayHistory", borrowRepayHistory);

		return rtMap;
	}

	@Override
	public Map<String, Object> realTimeInfo() throws Exception {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		Double needRepay = systemCountMapper.sumBorrowNeedRepay();
		rtMap.put("needRepay", needRepay == null ? 0.00 : needRepay);

		Double overdueRepay = systemCountMapper.sumBorrowOverdueRepay();
		rtMap.put("overdueRepay", overdueRepay == null ? 0.00 : overdueRepay);

		return rtMap;
	}

	@Override
	public Map<String, Object> areaInfo(List<SysUserChannel> sysUserChannelList) throws Exception {
		Map<String, Object> rtMap = new HashMap<String, Object>();

		Map<String, Object> result = null;
		List<Map<String, Object>> rtValue = null;

		this.areaCountDispose(result, rtValue, rtMap, sysUserChannelList);

		return rtMap;
	}

	@Override
	public Map<String, Object> repayWayInfo(List<SysUserChannel> sysUserChannelList) throws Exception {
		Map<String, Object> rtMap = new HashMap<String, Object>();

		Map<String, Object> result = null;
		List<Map<String, Object>> rtValue = null;
		// 刘晓亮 start
		if(sysUserChannelList != null && sysUserChannelList.size() > 0){
			List<Long> list = new ArrayList<>();
			for (SysUserChannel sysUserChannel : sysUserChannelList) {
				list.add(sysUserChannel.getChannelId());
			}
			rtValue = systemCountMapper.countRepaySourceForChannel(list);
			//刘晓亮 end
		} else {
			//原来的
			rtValue = systemCountMapper.countRepaySource();
		}
		result = reBuildMap(rtValue);
		String[] source = { "自动代扣", "银行卡转账", "支付宝转账", "认证支付" };
		List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
		Map<String, Object> sm;
		for (int i = 0; i < source.length; i++) {
			if (!result.containsKey(source[i])) {
				result.put(source[i], 0);
			}
			sm = new HashMap<String, Object>();
			sm.put(source[i], result.get(source[i]));
			sourceList.add(sm);
		}
		rtMap.put("repaySource", sourceList);

		return rtMap;
	}

	@Override
	public Map<String, Object> loanAndRepayInfo() throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext context = webApplicationContext.getServletContext();
		Map<String, Object> result1;
		Map<String, Object> result2;
		Map<String, Object> result3;
		Map<String, Object> result4;
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(context)) {
			Object t = context.getAttribute("loanAndRepaySelectTime");
			String todayStr = DateUtil.dateStr2(DateUtil.getNow());
			if (!todayStr.equals(t)) {
				List<String> days = new ArrayList<String>();
				Date nowDate = DateUtil.getNow();
				days.add(DateUtil.dateStr2(nowDate));
				Calendar date = Calendar.getInstance();
				for (int i = 0; i < 15; i++) {
					date.setTime(nowDate);
					date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
					nowDate = date.getTime();
					String day = DateUtil.dateStr2(nowDate);
					days.add(day);
				}
				List<Map<String, Object>> rtValue1 = null;
				List<Map<String, Object>> rtValue2 = null;
				List<Map<String, Object>> rtValue4 = null;
				systemCountMapper.countFifteenDaysNeedRepay();
				rtValue1 = systemCountMapper.countFifteenDaysNeedRepay();
				rtValue2 = systemCountMapper.countFifteenDaysRealRepay();
				rtValue4 = systemCountMapper.countFifteenDaysLoan();
			
				result1 = reBuildMap(rtValue1);
				result2 = reBuildMap(rtValue2);
				result4 = reBuildMap(rtValue4);
				result3 = new HashMap<String, Object>();
				for (int i = 0; i < days.size(); i++) {
					String day = days.get(i);
					if (!result1.containsKey(day)) {
						result1.put(day, 0.00);
					}
					if (!result2.containsKey(day)) {
						result2.put(day, 0.00);
					}

					String needStr = String.valueOf(result1.get(day));
					needStr = (StringUtil.isNotBlank(needStr) && !"null".equals(needStr)) ? needStr : "0.00";
					String realStr = String.valueOf(result2.get(day));
					realStr = (StringUtil.isNotBlank(realStr) && !"null".equals(realStr)) ? realStr : "0.00";
					Double need = Double.valueOf(needStr);
					Double real = Double.valueOf(realStr);
					if (real >= need) {
						result3.put(day, 0.00);
					} else if (real < need) {
						Double diff = need - real;
						result3.put(day, diff / need);
					} else {
						result3.put(day, 1.0);
					}

					if (!result4.containsKey(day)) {
						result4.put(day, 0);
					}
				}
				rtMap.put("fifteenDaysNeedRepay", result1);
				context.setAttribute("fifteenDaysNeedRepay", result1);
				rtMap.put("fifteenDaysRealRepay", result2);
				context.setAttribute("fifteenDaysRealRepay", result2);
				rtMap.put("fifteenDaysOverdueApr", result3);
				context.setAttribute("fifteenDaysOverdueApr", result3);
				rtMap.put("fifteenDaysLoan", result4);
				context.setAttribute("fifteenDaysLoan", result4);
				context.setAttribute("loanAndRepaySelectTime", todayStr);
			} else {
				result1 = (Map<String, Object>) context.getAttribute("fifteenDaysNeedRepay");
				rtMap.put("fifteenDaysNeedRepay", result1);

				result2 = (Map<String, Object>) context.getAttribute("fifteenDaysRealRepay");
				rtMap.put("fifteenDaysRealRepay", result2);

				result3 = (Map<String, Object>) context.getAttribute("fifteenDaysOverdueApr");
				rtMap.put("fifteenDaysOverdueApr", result3);

				result4 = (Map<String, Object>) context.getAttribute("fifteenDaysLoan");
				rtMap.put("fifteenDaysLoan", result4);
			}

		}
		return rtMap;
	}

	@Override
	public Map<String, Object> todayInfoForChannel(List<SysUserChannel> sysUserChannelList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("todayTime", DateUtil.getNow());
		Map<String, Object> rtMap = new HashMap<String, Object>();

		// key 是 渠道ID, value 是该渠道对应的值
		Map<Long, Long> channelMap = new HashMap<Long, Long>();
		// 获取渠道用户有哪些渠道ID
		for (SysUserChannel sysUserChannel : sysUserChannelList) {
			channelMap.put(sysUserChannel.getChannelId(), 0L);
		}
		// 为渠道用户统计当日注册量
		Map<Long, Long> channelMapForRegister = new HashMap<>();
		channelMapForRegister.putAll(channelMap);
		Long registerForChannle = 0L;
		List<Long> countRegisterForChannle = systemCountMapper.countRegisterForChannle(param);
		if (countRegisterForChannle != null && countRegisterForChannle.size() > 0 && channelMapForRegister != null
				&& channelMapForRegister.size() > 0) {
			registerForChannle = getChannelCountForRegistAndLogin(channelMapForRegister, countRegisterForChannle);
		}
		rtMap.put("register", registerForChannle);
		
		// 为渠道用户统计登用户陆数据
		Long login = 0L;
		List<Long> countLoginForChannel = systemCountMapper.countLoginForChannel(param);
		Map<Long, Long> channelMapTemp1 = new HashMap<>();
		channelMapTemp1.putAll(channelMap);
		if (channelMap != null && channelMap.size() > 0 && countLoginForChannel != null
				&& countLoginForChannel.size() > 0) {
			login = getChannelCountForRegistAndLogin(channelMapTemp1, countLoginForChannel);
		}
		rtMap.put("login", login);

		// 为渠道用户统计借款数量
		// 首先获取user_id 和 channel_id 的对应关系
		Long borrow = 0L;
		List<Map<Long, Long>> userIdAndChannelIdListMap = systemCountMapper.countRegisterForChannleAll(param);
		// 将sql返回的结果统计到一个map集合中
		Map<Long, Long> userIdAndChannelIdMap = new HashMap<Long, Long>();
		if (userIdAndChannelIdListMap != null && userIdAndChannelIdListMap.size() > 0) {
			for (Map<Long, Long> map : userIdAndChannelIdListMap) {
				userIdAndChannelIdMap.put(map.get("userId"), map.get("channelId"));
			}
		}
		// 其次统计渠道当日借款量
		List<Long> countBorrowForChannel = systemCountMapper.countBorrowForChannel(param);
		Map<Long, Long> channelMapTemp2 = new HashMap<>();
		channelMapTemp2.putAll(channelMap);
		// 通过程序实现,去匹配 borrow -- userid --channelId
		if (userIdAndChannelIdMap != null && userIdAndChannelIdMap.size() > 0 && countBorrowForChannel != null
				&& countBorrowForChannel.size() > 0) {
			// 获取借款user_id 和 channle_id 的一一对应关系
			Map<Object, Long> mapBorrowUserIdAndChannleId = getMaptForBorrowAndChannel(userIdAndChannelIdMap,
					countBorrowForChannel);
			if (channelMap != null && channelMap.size() > 0 && mapBorrowUserIdAndChannleId != null
					&& mapBorrowUserIdAndChannleId.size() > 0) {
				borrow = getChannelCountForBorrow(channelMapTemp2, mapBorrowUserIdAndChannleId);
			}
		}
		rtMap.put("borrow", borrow);

		// 为渠道管理员统计通过次数
		Long borrowPass = 0L;
		List<Long> countBorrowPassForChannel = systemCountMapper.countBorrowPassForChannel(param);
		Map<Long, Long> channelMapTemp3 = new HashMap<>();
		channelMapTemp3.putAll(channelMap);
		// 通过程序实现,去匹配 borrow -- userid --channelId
		if (userIdAndChannelIdMap != null && userIdAndChannelIdMap.size() > 0 && countBorrowPassForChannel != null
				&& countBorrowPassForChannel.size() > 0) {
			// 获取借款user_id 和 channle_id 的一一对应关系
			Map<Object, Long> mapBorrowPassUserIdAndChannleId = getMaptForBorrowAndChannel(userIdAndChannelIdMap,
					countBorrowPassForChannel);
			if (channelMap != null && channelMap.size() > 0 && mapBorrowPassUserIdAndChannleId != null
					&& mapBorrowPassUserIdAndChannleId.size() > 0) {
				borrowPass = getChannelCountForBorrow(channelMapTemp3, mapBorrowPassUserIdAndChannleId);
			}
		}

		rtMap.put("borrowPass", borrowPass);

		if (borrow > 0) {
			rtMap.put("passApr", BigDecimalUtil
					.decimal(Double.parseDouble(borrowPass + "") / Double.parseDouble(borrow + "") * 100, 2));
		} else {
			rtMap.put("passApr", 0);
		}

		// 为渠道管理员统计当日借款申请放款数量
		Long borrowLoan = 0L;
		List<Long> countBorrowLoanForChannel = systemCountMapper.countBorrowLoanForChannel(param);
		Map<Long, Long> channelMapTemp4 = new HashMap<>();
		channelMapTemp4.putAll(channelMap);
		// 通过程序实现,去匹配 borrow -- userid --channelId
		if (userIdAndChannelIdMap != null && userIdAndChannelIdMap.size() > 0 && countBorrowLoanForChannel != null
				&& countBorrowLoanForChannel.size() > 0) {
			// 获取借款user_id 和 channle_id 的一一对应关系
			Map<Object, Long> mapBorrowLoanUserIdAndChannleId = getMaptForBorrowAndChannel(userIdAndChannelIdMap,
					countBorrowLoanForChannel);
			if (channelMap != null && channelMap.size() > 0 && mapBorrowLoanUserIdAndChannleId != null
					&& mapBorrowLoanUserIdAndChannleId.size() > 0) {
				borrowLoan = getChannelCountForBorrow(channelMapTemp4, mapBorrowLoanUserIdAndChannleId);
			}
		}
		rtMap.put("borrowLoan", borrowLoan);

		// 为渠道用户统计当日还款量
		Long borrowRepay = 0L;
		List<Long> countBorrowRepayForChannel = systemCountMapper.countBorrowRepayForChannel(param);
		Map<Long, Long> channelMapTemp5 = new HashMap<>();
		channelMapTemp5.putAll(channelMap);
		// 通过程序实现,去匹配 borrow -- userid --channelId
		if (userIdAndChannelIdMap != null && userIdAndChannelIdMap.size() > 0 && countBorrowRepayForChannel != null
				&& countBorrowRepayForChannel.size() > 0) {
			// 获取借款user_id 和 channle_id 的一一对应关系
			Map<Object, Long> mapBorrowRepayUserIdAndChannleId = getMaptForBorrowAndChannel(userIdAndChannelIdMap,
					countBorrowRepayForChannel);
			if (channelMap != null && channelMap.size() > 0 && mapBorrowRepayUserIdAndChannleId != null
					&& mapBorrowRepayUserIdAndChannleId.size() > 0) {
				borrowRepay = getChannelCountForBorrow(channelMapTemp5, mapBorrowRepayUserIdAndChannleId);
			}
		}
		rtMap.put("borrowRepay", borrowRepay);

		return rtMap;
	}

	@Override
	public Map<String, Object> cumulativeInfoForChannel(List<SysUserChannel> sysUserChannelList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		// key 是 渠道ID, value 是该渠道对应的值
		Map<Long, Long> channelMap = new HashMap<Long, Long>();
		// 获取渠道用户有哪些渠道ID
		for (SysUserChannel sysUserChannel : sysUserChannelList) {
			channelMap.put(sysUserChannel.getChannelId(), 0L);
		}
		List<Map<Long, Long>> userIdAndChannelIdListMap = systemCountMapper.countRegisterForChannleAll(param);
		// 将sql返回的结果统计到一个map集合中
		Map<Long, Long> userIdAndChannelIdMap = new HashMap<Long, Long>();
		if (userIdAndChannelIdListMap != null && userIdAndChannelIdListMap.size() > 0) {
			for (Map<Long, Long> map : userIdAndChannelIdListMap) {
				userIdAndChannelIdMap.put(map.get("userId"), map.get("channelId"));
			}
		}

		Map<String, Object> rtMap = new HashMap<String, Object>();
		// 为渠道管理员统计历史放款总量
		Long borrowLoanHistory = 0L;
		List<Long> countBorrowLoanHistoryForChannel = systemCountMapper.countBorrowLoanHistoryForChannel();
		Map<Long, Long> channelMapTemp1 = new HashMap<>();
		channelMapTemp1.putAll(channelMap);
		// 通过程序实现,去匹配 borrow -- userid --channelId
		if (userIdAndChannelIdMap != null && userIdAndChannelIdMap.size() > 0
				&& countBorrowLoanHistoryForChannel != null && countBorrowLoanHistoryForChannel.size() > 0) {
			// 获取借款user_id 和 channle_id 的一一对应关系
			Map<Object, Long> mapBorrowLoanHistoryUserIdAndChannleId = getMaptForBorrowAndChannel(userIdAndChannelIdMap,
					countBorrowLoanHistoryForChannel);
			if (channelMapTemp1 != null && channelMapTemp1.size() > 0 && mapBorrowLoanHistoryUserIdAndChannleId != null
					&& mapBorrowLoanHistoryUserIdAndChannleId.size() > 0) {
				borrowLoanHistory = getChannelCountForBorrow(channelMapTemp1, mapBorrowLoanHistoryUserIdAndChannleId);
			}
		}
		rtMap.put("borrowLoanHistory", borrowLoanHistory);

		// 为渠道管理员统计历史还款总量
		Long borrowRepayHistory = 0L;
		List<Long> countBorrowRepayHistoryForChannelId = systemCountMapper.countBorrowRepayHistoryForChannelId();
		Map<Long, Long> channelMapTemp2 = new HashMap<>();
		channelMapTemp2.putAll(channelMap);
		// 通过程序实现,去匹配 borrow -- userid --channelId
		if (userIdAndChannelIdMap != null && userIdAndChannelIdMap.size() > 0
				&& countBorrowRepayHistoryForChannelId != null && countBorrowRepayHistoryForChannelId.size() > 0) {
			// 获取借款user_id 和 channle_id 的一一对应关系
			Map<Object, Long> mapBorrowRepayHistoryUserIdAndChannleId = getMaptForBorrowAndChannel(
					userIdAndChannelIdMap, countBorrowRepayHistoryForChannelId);
			if (channelMap != null && channelMap.size() > 0 && mapBorrowRepayHistoryUserIdAndChannleId != null
					&& mapBorrowRepayHistoryUserIdAndChannleId.size() > 0) {
				borrowRepayHistory = getChannelCountForBorrow(channelMapTemp2, mapBorrowRepayHistoryUserIdAndChannleId);
			}
		}
		rtMap.put("borrowRepayHistory", borrowRepayHistory);
		return rtMap;
	}

	@Override
	public Map<String, Object> realTimeInfoForChannel(List<SysUserChannel> sysUserChannelList) {
		Map<String, Object> param = new HashMap<String, Object>();
		// key 是 渠道ID, value 是该渠道对应的值
		Map<Long, Long> channelMap = new HashMap<Long, Long>();
		// 获取渠道用户有哪些渠道ID
		for (SysUserChannel sysUserChannel : sysUserChannelList) {
			channelMap.put(sysUserChannel.getChannelId(), 0L);
		}
		List<Map<Long, Long>> userIdAndChannelIdListMap = systemCountMapper.countRegisterForChannleAll(param);
		// 将sql返回的结果统计到一个map集合中
		Map<Long, Long> userIdAndChannelIdMap = new HashMap<Long, Long>();
		if (userIdAndChannelIdListMap != null && userIdAndChannelIdListMap.size() > 0) {
			for (Map<Long, Long> map : userIdAndChannelIdListMap) {
				userIdAndChannelIdMap.put(map.get("userId"), map.get("channelId"));
			}
		}
		
		
		Map<String, Object> rtMap = new HashMap<String, Object>();
		List<Map<Long, Object>> sumBorrowNeedRepayForChannel = systemCountMapper.sumBorrowNeedRepayForChannel();
		Map<Long, Long> userIdAndChannelIdMapTemp1 = new HashMap<>();
		userIdAndChannelIdMapTemp1.putAll(userIdAndChannelIdMap);
		Map<Long, Long> channelMapTemp1 = new HashMap<>();
		channelMapTemp1.putAll(channelMap);
		// 重新组转map
		List<SysUserChannelMoney> listUserAndMoney = new ArrayList<SysUserChannelMoney>();
		if(sumBorrowNeedRepayForChannel != null && sumBorrowNeedRepayForChannel.size() > 0){
			for (Map<Long, Object> map : sumBorrowNeedRepayForChannel) {
				//这里key拼接字符串,是为了防止map集合去重
				if(map.get("id") != null && map.get("user_id") != null && map.get("money") != null){
					Long id = Long.parseLong(map.get("id").toString());
					Long userId = Long.parseLong(map.get("user_id").toString());
					Double money = Double.parseDouble(map.get("money").toString());
					SysUserChannelMoney sysUserChannelMoney = new SysUserChannelMoney(id, userId, money);
					listUserAndMoney.add(sysUserChannelMoney);
				}
			}
		}
		//去查询这些用户,哪些属于渠道管理员的
		getMaptForMoneyAndChannel(userIdAndChannelIdMapTemp1, listUserAndMoney);
		//此时listUserAndMoney集合中的对象已经有chanelId
		Double needRepay = getMoneyForChannel(channelMapTemp1, listUserAndMoney);
		rtMap.put("needRepay", needRepay == null ? 0.0 : needRepay);
		
		//为渠道管理员查询逾期未还款总额
		List listForChannelIds = new ArrayList<>();
		for (SysUserChannel sysUserChannel : sysUserChannelList) {
			listForChannelIds.add(sysUserChannel.getChannelId());
		}
		Double overdueRepay = systemCountMapper.sumBorrowOverdueRepayForChannel(listForChannelIds);
		/*Map<Long, Long> userIdAndChannelIdMapTemp2 = new HashMap<>();
		userIdAndChannelIdMapTemp1.putAll(userIdAndChannelIdMap);
		Map<Long, Long> channelMapTemp2 = new HashMap<>();
		channelMapTemp2.putAll(channelMap);
		// 重新组转map
		List<SysUserChannelMoney> listUserAnddueMoney = new ArrayList<SysUserChannelMoney>();
		if(sumBorrowOverdueRepayForChannel != null && sumBorrowOverdueRepayForChannel.size() > 0){
			for (Map<Long, Object> map : sumBorrowOverdueRepayForChannel) {
				if(map.get("id") != null && map.get("user_id") != null && map.get("money") != null){
					//这里key拼接字符串,是为了防止map集合去重
					Long id = Long.parseLong(map.get("id").toString());
					Long userId = Long.parseLong(map.get("user_id").toString());
					Double money = Double.parseDouble(map.get("money").toString());
					SysUserChannelMoney sysUserChannelMoney = new SysUserChannelMoney(id, userId, money);
					listUserAnddueMoney.add(sysUserChannelMoney);
				}
			}
		}
		
		//去查询这些用户,哪些属于渠道管理员的
		getMaptForMoneyAndChannel(userIdAndChannelIdMapTemp2, listUserAnddueMoney);
		//此时listUserAndMoney集合中的对象已经有chanelId
		Double overdueRepay = getMoneyForChannel(channelMapTemp2, listUserAnddueMoney);*/
		
		rtMap.put("overdueRepay", overdueRepay == null ? 0.0 : overdueRepay);

		return rtMap;
	}

	/**
	 * 统计工具方法
	 * 
	 * @param channelMap
	 *            该map集合存储的渠道管理员有哪些渠道 key是渠道id， value是对应的人数
	 * @param paramListSql
	 *            该list集合存储的是，sql查询出来的渠道用户的渠道id
	 * @return
	 */
	public Long getChannelCountForRegistAndLogin(Map<Long, Long> channelMap, List<Long> paramListSql) {
		Long count = 0L;
		if (paramListSql != null && paramListSql.size() > 0) {
			for (Long chanenlId : paramListSql) {
				// 判断该chanenlId是否属于渠道用户
				if (channelMap.containsKey(chanenlId)) {
					// 说明该用户属于渠道用户
					Long registerCount = channelMap.get(chanenlId);
					// 将数量累加
					registerCount++;
					channelMap.put(chanenlId, registerCount);
				}
			}
		}
		// 最后统一统计
		if (channelMap != null && channelMap.size() > 0) {
			for (Map.Entry<Long, Long> entry : channelMap.entrySet()) {
				count = count + entry.getValue();
			}
		}

		return count;
	}

	/**
	 * 获取借款user_id 和 channle_id 的一一对应关系
	 * 
	 * @param userIdAndChannelId
	 *            该map集合存储的所有的是 user_id 和 channel_id 的对应关系
	 * @param paramListSql
	 *            该list集合存储的sql返回的 user_id，也就是借款人的user_id
	 * @return 返回的是map,key借款人的 user_id,value是渠道id:channel_id
	 */
	public Map<Object, Long> getMaptForBorrowAndChannel(Map<Long, Long> userIdAndChannelId, List<Long> paramListSql) {
		Map<Object, Long> resultMap = new HashMap<Object, Long>();
		for (int i = 0; i < paramListSql.size(); i++) {
			// 去userIdAndChannelId找对应的channel_id
			if (userIdAndChannelId.containsKey(paramListSql.get(i))) {
				Long channelId = userIdAndChannelId.get(paramListSql.get(i));
				// 这里存在问题,如果user_id一样会覆盖,续贷的就查不出来
				// 主要是查询数量,可以拼接
				resultMap.put(i + "_" + paramListSql.get(i), channelId);
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取借款user_id 和 channle_id 的一一对应关系
	 * 
	 * @param userIdAndChannelId
	 *            该map集合存储的所有的是 user_id 和 channel_id 的对应关系
	 * @param sysUserChannelMoneyList
	 *            该list集合存储的对象
	 * @return 返回的是map,key借款人的 user_id,value是渠道id:channel_id
	 */
	public void getMaptForMoneyAndChannel(Map<Long, Long> userIdAndChannelId, List<SysUserChannelMoney> sysUserChannelMoneyList) {
		for (int i = 0; i < sysUserChannelMoneyList.size(); i++) {
			// 去userIdAndChannelId找对应的channel_id
			if (userIdAndChannelId.containsKey(sysUserChannelMoneyList.get(i).getUserId())) {
				Long channelId = userIdAndChannelId.get(sysUserChannelMoneyList.get(i).getUserId());
				// 这里存在问题,如果user_id一样会覆盖,续贷的就查不出来
				// 将channelId,存储到对象中
				sysUserChannelMoneyList.get(i).setChannelId(channelId);
			}
		}
	}

	/**
	 * 为渠道管理员获取借款数量
	 * 
	 * @param channelMap
	 *            渠道管理员有哪些渠道 key 渠道id，value 对应的借款人数
	 * @param borrowChannelMap
	 *            借款人数map集合 key:借款人的 user_id value:是渠道id
	 * @return 直接返回借款数量
	 */
	public Long getChannelCountForBorrow(Map<Long, Long> channelMap, Map<Object, Long> borrowChannelMap) {
		Long result = 0L;
		for (Map.Entry<Object, Long> entry : borrowChannelMap.entrySet()) {
			if (channelMap.containsKey(entry.getValue())) {
				result++;
			}
		}
		return result;
	}
	
	/**
	 * 为渠道管理员获取借款数量
	 * 
	 * @param channelMap
	 *            渠道管理员有哪些渠道 key 渠道id，value 对应的借款人数
	 * @param borrowChannelMap
	 *            借款人数map集合 key:借款人的 user_id value:是渠道id
	 * @return 直接返回借款数量
	 */
	public Double getMoneyForChannel(Map<Long, Long> channelMap, List<SysUserChannelMoney> sysUserChannelMoneyList) {
		Double result = 0.0;
		
		for (SysUserChannelMoney sysUserChannelMoney : sysUserChannelMoneyList) {
			if (channelMap.containsKey(sysUserChannelMoney.getChannelId())) {
				result += sysUserChannelMoney.getMoney();
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> loanAndRepayInfoForChannel(List list) {
		Map<String, Object> result1;
		Map<String, Object> result2;
		Map<String, Object> result3;
		Map<String, Object> result4;
		Map<String, Object> rtMap = new HashMap<String, Object>();

		List<String> days = new ArrayList<String>();
		Date nowDate = DateUtil.getNow();
		days.add(DateUtil.dateStr2(nowDate));
		Calendar date = Calendar.getInstance();
		for (int i = 0; i < 15; i++) {
			date.setTime(nowDate);
			date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
			nowDate = date.getTime();
			String day = DateUtil.dateStr2(nowDate);
			days.add(day);
		}
		List<Map<String, Object>> rtValue1 = null;
		List<Map<String, Object>> rtValue2 = null;
		List<Map<String, Object>> rtValue4 = null;
		systemCountMapper.countFifteenDaysNeedRepay();
		rtValue1 = systemCountMapper.countFifteenDaysNeedRepayForChannel(list);
		rtValue2 = systemCountMapper.countFifteenDaysRealRepayForChannel(list);
		rtValue4 = systemCountMapper.countFifteenDaysLoanForChannel(list);
	
		result1 = reBuildMap(rtValue1);
		result2 = reBuildMap(rtValue2);
		result4 = reBuildMap(rtValue4);
		result3 = new HashMap<String, Object>();
		for (int i = 0; i < days.size(); i++) {
			String day = days.get(i);
			if (!result1.containsKey(day)) {
				result1.put(day, 0.00);
			}
			if (!result2.containsKey(day)) {
				result2.put(day, 0.00);
			}

			String needStr = String.valueOf(result1.get(day));
			needStr = (StringUtil.isNotBlank(needStr) && !"null".equals(needStr)) ? needStr : "0.00";
			String realStr = String.valueOf(result2.get(day));
			realStr = (StringUtil.isNotBlank(realStr) && !"null".equals(realStr)) ? realStr : "0.00";
			Double need = Double.valueOf(needStr);
			Double real = Double.valueOf(realStr);
			if (real >= need) {
				result3.put(day, 0.00);
			} else if (real < need) {
				Double diff = need - real;
				result3.put(day, diff / need);
			} else {
				result3.put(day, 1.0);
			}

			if (!result4.containsKey(day)) {
				result4.put(day, 0);
			}
		}
		rtMap.put("fifteenDaysNeedRepay", result1);
		rtMap.put("fifteenDaysRealRepay", result2);
		rtMap.put("fifteenDaysOverdueApr", result3);
		rtMap.put("fifteenDaysLoan", result4);
		return rtMap;
		
	}

}
