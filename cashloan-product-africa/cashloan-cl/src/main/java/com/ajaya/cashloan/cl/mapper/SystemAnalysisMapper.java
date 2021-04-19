package com.ajaya.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.model.OverdueAnalisisModel;
import com.ajaya.cashloan.cl.model.RepayAnalisisModel;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 运营分析
 * @author caitt
 * @version 1.0
 * @date 2017年3月21日下午3:01:07
 */
@RDBatisDao
public interface SystemAnalysisMapper {
	
	/**
	 * 还款统计
	 * @param params
	 * @return
	 */
	List<RepayAnalisisModel> repayAnalisis(Map<String,Object> params);

	/**
	 * 每月逾期统计
	 * @param params
	 * @return
	 */
	List<OverdueAnalisisModel> overdueAnalisis(Map<String,Object> params);

	/**
	 * 还款笔数
	 * @param map 
	 */
	String repayCount(Map<String, Object> map);

	/**
	 * 逾期还款笔数
	 * @param map
	 * @return
	 */
	String overdueCount(Map<String, Object> map);

	/**
	 * 还款金额
	 * @param map
	 * @return
	 */
	String repayAmt(Map<String, Object> map);

	/**
	 * 逾期还款金额
	 * @param map
	 * @return
	 */
	String penaltyRepayAmt(Map<String, Object> map);

	/**
	 * 得到有数据的月份
	 * @return
	 */
	List<String> mouthList();

	/**
	 * 刘晓亮 渠道管理员还款笔数
	 * @param map 
	 */
	String repayCountForChannel(Map<String, Object> map);

	/**
	 * 刘晓亮 渠道管理员逾期还款笔数
	 * @param map
	 * @return
	 */
	String overdueCountForChannel(Map<String, Object> map);

	/**
	 * 刘晓亮 渠道管理员还款金额
	 * @param map
	 * @return
	 */
	String repayAmtForChannel(Map<String, Object> map);

	/**
	 * 刘晓亮 渠道管理员逾期还款金额
	 * @param map
	 * @return
	 */
	String penaltyRepayAmtForChannel(Map<String, Object> map);
}
