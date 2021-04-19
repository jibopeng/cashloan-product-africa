package com.ajaya.cashloan.cl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.mapper.StatisticManageMapper;
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
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * 统计管理
 * @author caitt
 * @version 1.0
 * @date 2017年3月21日下午4:42:31
 */
@Service("statisticManageService")
public class StatisticManageServiceImpl implements StatisticManageService {

	@Resource
	private StatisticManageMapper statisticManageMapper;

	@Override
	public Page<IncomeAndExpendModel> repayIncomeAndExpend(Map<String, Object> params,Integer current,Integer pageSize) {
		PageHelper.startPage(current, pageSize);
		Page<IncomeAndExpendModel> page = (Page<IncomeAndExpendModel>) statisticManageMapper.repayIncomeAndExpend(params);
		return page;
	}

	@Override
	public Page<DayNeedAmountModel> dayNeedAmount(Map<String, Object> params,Integer current,Integer pageSize) {
		PageHelper.startPage(current, pageSize);
		Page<DayNeedAmountModel> page = (Page<DayNeedAmountModel>) statisticManageMapper.dayNeedAmount(params);
		return page;
	}

	@Override
	public Page<IncomeDetailModel> incomeDetail(Map<String, Object> params,Integer current, Integer pageSize) {
		PageHelper.startPage(current, pageSize);
		Page<IncomeDetailModel> page = (Page<IncomeDetailModel>) statisticManageMapper.incomeDetail(params);
		return page;
	}

	@Override
	public Page<ExpendDetailModel> expendDetail(Map<String, Object> params,Integer current, Integer pageSize) {
		PageHelper.startPage(current, pageSize);
		Page<ExpendDetailModel> page = (Page<ExpendDetailModel>) statisticManageMapper.expendDetail(params);
		return page;
	}

	@Override
	public Double incomeSum(Map<String, Object> params) {
		return statisticManageMapper.incomeSum(params);
	}

	@Override
	public Double expendSum(Map<String, Object> params) {
		return statisticManageMapper.expendSum(params);
	}
	
	@Override
	public Page<NewLoanRepPerModel> pageNewLoan(Map<String, Object> params, int current, int pageSize) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		date = DateUtil.dateAddDays(date, -1);
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("endTime", sdf.format(date));
		PageHelper.startPage(current, pageSize);
        List<NewLoanRepPerModel> list = statisticManageMapper.pageNewLoan(params);
        for(int i=0; i<list.size(); i++){
        	NewLoanRepPerModel newLoanRepPerModel = list.get(i);
        	if("0".equals(newLoanRepPerModel.getD3())){
        		newLoanRepPerModel.setD3(null);
        		newLoanRepPerModel.setD3per(null);
        	}
        	if("0".equals(newLoanRepPerModel.getD5())){
        		newLoanRepPerModel.setD5(null);
        		newLoanRepPerModel.setD5per(null);
        	}
        	if("0".equals(newLoanRepPerModel.getD7())){
        		newLoanRepPerModel.setD7(null);
        		newLoanRepPerModel.setD7per(null);
        	}
        	if("0".equals(newLoanRepPerModel.getD14())){
        		newLoanRepPerModel.setD14(null);
        		newLoanRepPerModel.setD14per(null);
        	}
        	String loanTime = newLoanRepPerModel.getLoanTime();
        	Integer registNum = statisticManageMapper.getRegistNum(loanTime);
        	newLoanRepPerModel.setRegisteredTime(registNum.toString());
        }
        return (Page<NewLoanRepPerModel>) list;
	}

	@Override
	public Page<OldLoanRepPerModel> pageOldLoan(Map<String, Object> params, int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
        List<OldLoanRepPerModel> list = statisticManageMapper.pageOldLoan(params);
        for(int i=0; i<list.size(); i++){
        	OldLoanRepPerModel oldLoanRepPerModel = list.get(i);
        	if("0".equals(oldLoanRepPerModel.getD3())){
        		oldLoanRepPerModel.setD3(null);
        		oldLoanRepPerModel.setD3per(null);
        	}
        	if("0".equals(oldLoanRepPerModel.getD5())){
        		oldLoanRepPerModel.setD5(null);
        		oldLoanRepPerModel.setD5per(null);
        	}
        	if("0".equals(oldLoanRepPerModel.getD7())){
        		oldLoanRepPerModel.setD7(null);
        		oldLoanRepPerModel.setD7per(null);
        	}
        	if("0".equals(oldLoanRepPerModel.getD14())){
        		oldLoanRepPerModel.setD14(null);
        		oldLoanRepPerModel.setD14per(null);
        	}
        }
        return (Page<OldLoanRepPerModel>) list;
	}

	@Override
	public Page<StatisticsVerifyModel> pageVerify(Map<String, Object> params, int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
        List<StatisticsVerifyModel> list = statisticManageMapper.pageVerify(params);
        return (Page<StatisticsVerifyModel>) list;
	}

	@Override
	public List<NewLoanRepPerModel> listNewLoan(Map<String, Object> params) {
		return statisticManageMapper.pageNewLoan(params);
	}

	@Override
	public List<OldLoanRepPerModel> ListOldLoan(Map<String, Object> params) {
		return statisticManageMapper.pageOldLoan(params);
	}

	@Override
	public List<StatisticsVerifyModel> ListVerify(Map<String, Object> params) {
		return statisticManageMapper.pageVerify(params);
	}
	
	public List<ColStaModel> colStaByCreateTime(Map<String, Object> params) {
		return statisticManageMapper.colStaByCreateTime(params);
	}

	public List<ColStaModel> colStaBySuccessTime(Map<String, Object> params) {
		return statisticManageMapper.colStaBySuccessTime(params);
	}

	public List<ColStaModel> colStaByChannel(Map<String, Object> params) {
		return statisticManageMapper.colStaByChannel(params);
	}

	public List<ColStaTodayDataModel> colStaTodayData(Map<String, Object> params) {
		return statisticManageMapper.colStaTodayData(params);
	}

	public List<ColStaOldOrderModel> colStaOldOrder(Map<String, Object> params) {
		return statisticManageMapper.colStaOldOrder(params);
	}
}
