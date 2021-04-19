package com.ajaya.cashloan.cl.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.mapper.PayLogMapper;
import com.ajaya.cashloan.cl.model.PayLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.domain.PayLog;
import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.model.ManagePayLogModel;
import com.ajaya.cashloan.cl.service.PayLogService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;

import tool.util.DateUtil;
import tool.util.StringUtil;


/**
 * 支付记录ServiceImpl
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017-03-07 16:18:56
 */
 
@Service("payLogService")
public class PayLogServiceImpl extends BaseServiceImpl<PayLog, Long> implements PayLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayLogServiceImpl.class);
	
	@Resource
	private PayLogMapper payLogMapper;
	@Resource
	private ClBorrowMapper clBorrowMapper;

	@Override
	public BaseMapper<PayLog, Long> getMapper() {
		return payLogMapper;
	}
	
	@Override
	public boolean save(PayLog payLog) {
		payLog.setCreateTime(DateUtil.getNow());
		int result = payLogMapper.save(payLog);
		if (result > 0L) {
			return true;
		}
		return false;
	}

	@Override
	public Page<ManagePayLogModel> page(int current, int pageSize,
			Map<String, Object> searchMap) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/** 刘晓亮  start 2017年11月16日 13:49:12  导出excel增加时间功能 */
		if(searchMap.containsKey("startTime") && searchMap.containsKey("endTime")){
			try {
				String end = searchMap.get("endTime").toString();
				Date endDate = sdf.parse(end);
				Date dateAddDays = com.ajaya.cashloan.core.common.util.DateUtil.dateAddDays(endDate, 1);
				searchMap.put("endTime", sdf.format(dateAddDays));
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}
		/** 刘晓亮  end 2017年11月16日 13:49:12  */
		PageHelper.startPage(current, pageSize);
		Page<ManagePayLogModel> page = (Page<ManagePayLogModel>) payLogMapper
				.page(searchMap);
		if(page.getResult() != null && page.getResult().size() > 0){
			for (ManagePayLogModel managePayLogModel : page.getResult()) {
				managePayLogModel.setOrderNo(managePayLogModel.getTradeNo());
			}
		}
		return page;
	}

	@Override
	public ManagePayLogModel findDetail(Long id) {
		return payLogMapper.findDetail(id);
	}

	@Override
	public boolean auditPay(Long id, String state) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("state", state);
		int result = payLogMapper.updateSelective(paramMap);

		if (PayLogModel.STATE_AUDIT_PASSED.equals(state)) {
			PayLog payLog = payLogMapper.findByPrimary(id);
			confirmPayment(payLog);
		}

		if (result > 0L) {
			return true;
		}
		return false;
	}
	
	/**
	 * 调用连连支付接口进行支付
	 * @param payLog
	 */
	private void confirmPayment(PayLog payLog){}

	@Override
	public Map<String, Object> checkPayLogState(Long id, String state) {
		PayLog log = payLogMapper.findByPrimary(id);

		Map<String, Object> check = new HashMap<String, Object>();
		if (!PayLogModel.STATE_PENDING_REVIEW.equals(log.getState())) {
			check.put(Constant.RESPONSE_CODE_MSG, "当前交易记录状态不允许审核！");
		}

		// 若借款不是审核通过或放款失败 则不允许审核通过
		if (PayLogModel.SCENES_LOANS.equals(log.getScenes()) 
				&& PayLogModel.STATE_AUDIT_PASSED.equals(state)) {

			Borrow borrow = clBorrowMapper.findByPrimary(log.getBorrowId());
			if (BorrowModel.STATE_AUTO_PASS.equals(borrow.getState())
					|| BorrowModel.STATE_PASS.equals(borrow.getState())
					|| BorrowModel.STATE_REPAY_FAIL.equals(borrow.getState())
					|| BorrowModel.AUDIT_LOAN_PASS.equals(borrow.getState())) {
			} else {
				check.put(Constant.RESPONSE_CODE_MSG, "当前借款状态不允许审核通过！");
			}
		}
		return check;
	}

	@Override
	public PayLog findByOrderNo(String orderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", orderNo);
		return payLogMapper.findSelective(paramMap);
	}
	
	@Override
	public boolean updateSelective(Map<String, Object> paramMap){
		int result  = payLogMapper.updateSelective(paramMap);
		if(result > 0L){
			return true;
		}
		return false;
	}

	@Override
	public PayLog findSelective(Map<String, Object> paramMap) {
		return payLogMapper.findSelective(paramMap);
	}
	
	@Override
	public PayLog findLatestOne(Map<String, Object> paramMap) {
		return payLogMapper.findLatestOne(paramMap);
	}
	
	@Override
	public List<PayLog> findCheckList(Map<String, Object> paramMap){
		return payLogMapper.findCheckList(paramMap);
	}

	@Override
	public boolean judge(long borrowId) {
		Map<String,Object> map = new HashMap<>();
		map.put("borrowId", borrowId);
		map.put("scenes", PayLogModel.SCENES_LOANS);
        List<PayLog> plist = payLogMapper.listSelective(map);
        boolean flag = true;
        for (PayLog payLog : plist) {
            if (PayLogModel.STATE_PAYMENT_WAIT.equals(payLog.getState())
                    || PayLogModel.STATE_PENDING_REVIEW.equals(payLog.getState())) {
                flag = false;
                break;
            }
        }
		return flag;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List listPayLog(String params) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(params)) {
			searchMap = JsonUtil.parse(params, Map.class);
		}
		
		/** 刘晓亮  start 2017年11月16日 13:49:12  导出excel增加时间功能 */
		List<String> timeArr = (List<String>) searchMap.get("time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar();
		if(timeArr != null && timeArr.size() == 2){
			String startTime = timeArr.get(0).split("T")[0] + " 00:00:00";
			Date startTimeDate;
			try {
				startTimeDate = sdf.parse(startTime);
				String endTime = timeArr.get(1).split("T")[0] + " 00:00:00";
				Date endTimeDate = sdf.parse(endTime);
				calendar.setTime(endTimeDate);
				calendar.add(calendar.DATE,1);
				//获取endTime的明日日期
				endTimeDate = calendar.getTime();
				searchMap.put("startTime", startTimeDate);
				searchMap.put("endTime", endTimeDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		/** 刘晓亮  end 2017年11月16日 13:49:12  */
		
		/**   itw_yanzq 2017-12-01    start   */
		String scenes  = StringUtil.isNull(searchMap.get("scenes"));
		String[] scenesArray = scenes.split(",");
		
		List<String> scenesList =  new ArrayList<String>();
		for (String scenesStr : scenesArray) {
			if (StringUtil.isNotBlank(scenesStr)) {
				scenesList.add(scenesStr);
			}
		} 
		searchMap.put("scenes", scenesList);
		/**   itw_yanzq 2017-12-01    end  */
		String type  = StringUtil.isNull(searchMap.get("type"));
		String[] typeArray = type.split(",");
		
		List<String> typeList =  new ArrayList<String>();
		for (String typeStr : typeArray) {
			if (StringUtil.isNotBlank(typeStr)) {
				typeList.add(typeStr);
			}
		}
		searchMap.put("type", typeList);
		//添加通过支付订单号查询
		if(StringUtil.isNotBlank(searchMap.get("orderNo"))){
			searchMap.put("orderNo", searchMap.get("orderNo").toString());
		}
		List<ManagePayLogModel> list = payLogMapper.page(searchMap);
		if(list != null && list.size() > 0){
			for (ManagePayLogModel managePayLogModel : list) {
				Borrow borrow = clBorrowMapper.findByPrimary(managePayLogModel.getBorrowId());
				if("2".equals(borrow.getPayChannelId() + "")){
					managePayLogModel.setPayChannel("Zavron");
				} else {
					managePayLogModel.setPayChannel("KUD");
				}
			}
		}
		return list;
	}

	@Override
	public int doRepaymentNum(long borrowId) {
		return payLogMapper.doRepaymentCount(borrowId);
	}
	
	/**
	 * 保存支付记录
	 * 
	 * @param orderNo
	 * @param userId
	 * @param borrowId
	 * @param amount
	 * @param cardNo
	 * @param bank
	 * @param type
	 * @return
	 */
	@Override
	public int savePayLog(String orderNo, Long userId, Long borrowId, double amount, String cardNo, String bank, String scenes, String periods,String Type ) {
		PayLog payLog = new PayLog();
		payLog.setOrderNo(orderNo);
		payLog.setUserId(userId);
		payLog.setBorrowId(borrowId);
		payLog.setAmount(amount);
		payLog.setCardNo(cardNo);
		payLog.setBank(bank);
		payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
		payLog.setType(Type);
		payLog.setScenes(scenes);
		payLog.setState(PayLogModel.STATE_PAYMENT_WAIT);
		payLog.setPayReqTime(DateUtil.getNow());
		payLog.setCreateTime(DateUtil.getNow());
		payLog.setPeriods(periods);
		int result = payLogMapper.save(payLog);
		return result;
	}

	@Override
	public List<PayLog> listSelective(Map<String, Object> pmap) {
		return payLogMapper.listSelective(pmap);
	}

	@Override
	public PayLog findRepayMentLog(Long borrowId) {
		return payLogMapper.findRepayMentLog(borrowId);
	}

	@Override
	public PayLog findByTradeNo(String tradeNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("tradeNo", tradeNo);
		return payLogMapper.findSelective(map);
	}

	@Override
	public int findPayOutFailedCount(Long borrowId) {
		return payLogMapper.findPayOutFailedCount( borrowId);
	}

}
