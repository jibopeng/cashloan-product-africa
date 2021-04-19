package com.ajaya.cashloan.cl.service;


import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.model.ColStaModel;
import com.ajaya.cashloan.cl.model.ColStaOldOrderModel;
import com.ajaya.cashloan.cl.model.OldLoanRepPerModel;
import com.ajaya.cashloan.cl.model.StatisticsVerifyModel;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.model.ColStaTodayDataModel;
import com.ajaya.cashloan.cl.model.DayNeedAmountModel;
import com.ajaya.cashloan.cl.model.ExpendDetailModel;
import com.ajaya.cashloan.cl.model.IncomeAndExpendModel;
import com.ajaya.cashloan.cl.model.IncomeDetailModel;
import com.ajaya.cashloan.cl.model.NewLoanRepPerModel;

/**
 * 统计管理
 * @author caitt
 * @version 1.0
 * @date 2017年3月21日下午4:42:06
 */
public interface StatisticManageService {

	/**
	 * 每日收支记录
	 * @return
	 */
	Page<IncomeAndExpendModel> repayIncomeAndExpend(Map<String, Object> params,Integer current,Integer pageSize);
	
	/**
	 * 每日未还本金
	 * @return
	 */
	Page<DayNeedAmountModel> dayNeedAmount(Map<String, Object> params,Integer current,Integer pageSize);
	
	/**
	 * 收入明细
	 * @return
	 */
	Page<IncomeDetailModel> incomeDetail(Map<String, Object> params,Integer current,Integer pageSize);
	
	/**
	 * 支出明细
	 * @return
	 */
	Page<ExpendDetailModel> expendDetail(Map<String, Object> params,Integer current,Integer pageSize);
	
	/**
	 * 收入总额
	 * @param params
	 * @return
	 */
	Double incomeSum(Map<String,Object> params);
	
	/**
	 * 支出总额
	 * @param params
	 * @return
	 */
	Double expendSum(Map<String,Object> params);
	
	/**
	 * 查询用户新贷表现
	 * @param params
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<NewLoanRepPerModel> pageNewLoan(Map<String, Object>params,int current,int pageSize);
	
	/**
	 * 查询用户复贷表现
	 * @param params
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<OldLoanRepPerModel> pageOldLoan(Map<String, Object>params, int current, int pageSize);
	
	/**
	 * 用户认证表现
	 * @param params
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<StatisticsVerifyModel> pageVerify(Map<String, Object> params, int current, int pageSize);
	
	/**
	 * 根据时间查询新贷还款表现
	 * @param params
	 * @return
	 */
	List<NewLoanRepPerModel> listNewLoan(Map<String,Object> params);
	
	/**
	 * 根据时间查询复贷还款表现
	 * @param params
	 * @return
	 */
	List<OldLoanRepPerModel> ListOldLoan(Map<String,Object> params);
	
	/**
	 * 根据时间查询各流程转化率
	 * @param params
	 * @return
	 */
	List<StatisticsVerifyModel> ListVerify(Map<String,Object> params);
	
	/**
	 * 按照催收订单生成时间查询催收情况
	 * @param params
	 * @return
	 */
	List<ColStaModel> colStaByCreateTime(Map<String,Object> params);
	/**
	 * 按照回款时间查询催收情况
	 * @param params
	 * @return
	 */
	List<ColStaModel> colStaBySuccessTime(Map<String,Object> params);
	/**
	 * 查询外包催收情况 
	 * @param params
	 * @return
	 */
	List<ColStaModel>  colStaByChannel(Map<String, Object> params);
	/**
	 * 查询今日数据 
	 * @param params
	 * @return
	 */
	List<ColStaTodayDataModel> colStaTodayData(Map<String, Object> params);
	/**
	 * 查询老案件回款情况 
	 * @param params
	 * @return
	 */
	List<ColStaOldOrderModel> colStaOldOrder(Map<String, Object> params);
}
