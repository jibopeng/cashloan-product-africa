package com.ajaya.cashloan.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.model.ColStaModel;
import com.ajaya.cashloan.cl.model.ColStaOldOrderModel;
import com.ajaya.cashloan.cl.model.ColStaTodayDataModel;
import com.ajaya.cashloan.cl.model.DayNeedAmountModel;
import com.ajaya.cashloan.cl.model.ExpendDetailModel;
import com.ajaya.cashloan.cl.model.IncomeAndExpendModel;
import com.ajaya.cashloan.cl.model.IncomeDetailModel;
import com.ajaya.cashloan.cl.model.NewLoanRepPerModel;
import com.ajaya.cashloan.cl.model.OldLoanRepPerModel;
import com.ajaya.cashloan.cl.model.StatisticsVerifyModel;
import com.ajaya.cashloan.cl.service.StatisticManageService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;

/**
 * 统计管理
 * @author caitt
 * @version 1.0
 * @date 2017年3月21日下午4:45:20

 */
@Controller
@Scope("prototype")
public class StatisticManageController extends ManageBaseController {
	
	@Resource
	private StatisticManageService statisticManageService;

	/**
	 * 每日未还本金
	 * @param response
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/statistic/dayNeedAmount.htm")
	public void dayNeedAmount(HttpServletResponse response,
			@RequestParam("search")String search,
			@RequestParam("current")Integer current,
			@RequestParam("pageSize")Integer pageSize) throws Exception {
		Map<String, Object> params = JSONObject.parseObject(search);
		Page<DayNeedAmountModel> page = statisticManageService.dayNeedAmount(params,current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 每日收入支出
	 * @param response
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/statistic/incomeAndExpend.htm")
	public void incomeAndExpend(HttpServletResponse response,
			@RequestParam("search")String search,
			@RequestParam("current")Integer current,
			@RequestParam("pageSize")Integer pageSize) throws Exception {
		Map<String, Object> params = JSONObject.parseObject(search);
		Page<IncomeAndExpendModel> page = statisticManageService.repayIncomeAndExpend(params,current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 收入明细
	 * @param response
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/statistic/incomeDetail.htm")
	public void incomeDetail(HttpServletResponse response,
			@RequestParam("search")String search,
			@RequestParam("current")Integer current,
			@RequestParam("pageSize")Integer pageSize) throws Exception {
		Map<String, Object> params = JSONObject.parseObject(search);
		Page<IncomeDetailModel> page = statisticManageService.incomeDetail(params, current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		Double incomeSum = statisticManageService.incomeSum(params);
		result.put("incomeSum", incomeSum);
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 支出明细
	 * @param response
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/statistic/expendDetail.htm")
	public void expendDetail(HttpServletResponse response,
			@RequestParam("search")String search,
			@RequestParam("current")Integer current,
			@RequestParam("pageSize")Integer pageSize) throws Exception {
		Map<String, Object> params = JSONObject.parseObject(search);
		Page<ExpendDetailModel> page = statisticManageService.expendDetail(params, current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		Double expendSum = statisticManageService.expendSum(params);
		result.put("expendSum", expendSum);
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 新贷还款表现
	 * @param searchParams
	 * @param current
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/statistics/newLoan1.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public void newLoan(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int current, @RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<NewLoanRepPerModel> page = statisticManageService.pageNewLoan(params, current, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 复贷还款表现
	 * @param searchParams
	 * @param current
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/statistics/oldLoan1.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public void oldLoan(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int current, @RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<OldLoanRepPerModel> page = statisticManageService.pageOldLoan(params, current, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 个流程转换请款
	 * @param searchParams
	 * @param current
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/statistics/verified1.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public void verified(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int current, @RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<StatisticsVerifyModel> page = statisticManageService.pageVerify(params, current, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	/**
	 * 按照催收订单生成时间查询催收数据
	 * @param searchParams
	 */
	@RequestMapping(value = "/modules/manage/statistics/colStaByCreateTime.htm")
	public void colStaByCreateTime(@RequestParam(value = "searchParams", required = false) String searchParams){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List<ColStaModel> colStaByCreateTime = statisticManageService.colStaByCreateTime(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", colStaByCreateTime);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 按照回款时间查询催收情况
	 * @param searchParams
	 */
	@RequestMapping(value = "/modules/manage/statistics/colStaBySuccessTime.htm")
	public void colStaBySuccessTime(@RequestParam(value = "searchParams", required = false) String searchParams){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List<ColStaModel> colStaBySuccessTime = statisticManageService.colStaBySuccessTime(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", colStaBySuccessTime);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 查询外包催收情况
	 * @param searchParams
	 */
	@RequestMapping(value = "/modules/manage/statistics/colStaByChannel.htm")
	public void colStaByChannel(@RequestParam(value = "searchParams", required = false) String searchParams){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List<ColStaModel> colStaByChannel = statisticManageService.colStaByChannel(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", colStaByChannel);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 查询今日数据 
	 * @param searchParams
	 */
	@RequestMapping(value = "/modules/manage/statistics/colStaTodayData.htm")
	public void colStaTodayData(@RequestParam(value = "searchParams", required = false) String searchParams){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List<ColStaTodayDataModel> colStaTodayData = statisticManageService.colStaTodayData(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", colStaTodayData);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 查询老案件回款情况 
	 * @param searchParams
	 */
	@RequestMapping(value = "/modules/manage/statistics/colStaOldOrder.htm")
	public void colStaOldOrder(@RequestParam(value = "searchParams", required = false) String searchParams){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List<ColStaOldOrderModel> colStaOldOrder = statisticManageService.colStaOldOrder(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", colStaOldOrder);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
}
