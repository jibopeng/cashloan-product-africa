package com.ajaya.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 统计管理
 * @author caitt
 * @version 1.0
 * @date 2017年3月21日下午4:39:37
 */
@RDBatisDao
public interface StatisticManageMapper {

	/**
	 * 每日收支记录
	 * @return
	 */
	List<IncomeAndExpendModel> repayIncomeAndExpend(Map<String,Object> params);
	
	/**
	 * 每日未还本金
	 * @return
	 */
	List<DayNeedAmountModel> dayNeedAmount(Map<String,Object> params);
	
	/**
	 * 收入明细
	 * @return
	 */
	List<IncomeDetailModel> incomeDetail(Map<String,Object> params);
	
	/**
	 * 收入总额
	 * @param params
	 * @return
	 */
	Double incomeSum(Map<String,Object> params);
	
	/**
	 * 支出明细
	 * @return
	 */
	List<ExpendDetailModel> expendDetail(Map<String,Object> params);
	
	/**
	 * 支出总额
	 * @param params
	 * @return
	 */
	Double expendSum(Map<String,Object> params);
	
	/**
	 * 查询用户新贷表现
	 * @param params
	 * @return
	 */
	List<NewLoanRepPerModel> pageNewLoan(Map<String, Object>params);
	
	/**
	 * 查询用户复贷表现
	 * @param params
	 * @return
	 */
	List<OldLoanRepPerModel> pageOldLoan(Map<String, Object>params);
	
	/**
	 * 用户认证表现
	 * @param params
	 * @return
	 */
	List<StatisticsVerifyModel> pageVerify(Map<String, Object> params);
	
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

	@Select("select count(1) from cl_user where date_format(regist_time, '%Y-%m-%d') = #{time}")
	Integer getRegistNum(@Param("time")String time);
}
