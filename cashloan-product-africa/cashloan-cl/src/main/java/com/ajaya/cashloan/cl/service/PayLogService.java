package com.ajaya.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.PayLog;
import com.ajaya.cashloan.cl.model.ManagePayLogModel;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 支付记录Service
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017-03-07 16:18:56
 */
public interface PayLogService extends BaseService<PayLog, Long>{

	/**
	 * 保存支付记录
	 * 
	 * @param payLog
	 * @return
	 */
	boolean save(PayLog payLog);

	/**
	 * 分页查询支付记录
	 * 
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ManagePayLogModel> page(int current, int pageSize,
                                 Map<String, Object> searchMap);

	/**
	 * 查看详情
	 * 
	 * @param id
	 * @return
	 */
	ManagePayLogModel findDetail(Long id);

	/**
	 * 审核
	 * 
	 * @param id
	 * @return
	 */
	boolean auditPay(Long id,String state);

	/**
	 * 校验付款状态是否指定状态
	 * 
	 * @param id
	 * @param state 审核状态
	 * @return
	 */
	Map<String, Object> checkPayLogState(Long id,String state);
	
	/**
	 * 订单号查询支付记录
	 * 
	 * @param orderNo
	 * @return
	 */
	PayLog findByOrderNo(String orderNo);
	
	/**
	 * 更新
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean updateSelective(Map<String, Object> paramMap);
	
	/**
	 * 条件查询
	 * 
	 * @param paramMap
	 * @return
	 */
	PayLog findSelective(Map<String, Object> paramMap);
	
	/**
	 * 条件查询对账记录
	 * 
	 * @param paramMap
	 * @return
	 */
	List<PayLog> findCheckList(Map<String, Object> paramMap);

	/**
	 * 判断审核状态
	 * @param borrowId
	 * @return
	 */
	boolean judge(long borrowId);

	/**
	 * 导出查询
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List listPayLog(String params);

	/**
	 * 查询最新的代扣日志
	 * 
	 * @param paramMap
	 * @return
	 */
	PayLog findLatestOne(Map<String, Object> paramMap);
	
	/**
	 * 查询用户代扣次数
	 * @param userId
	 * @return
	 */
	int doRepaymentNum(long borrowId);

	int savePayLog(String orderNo, Long userId, Long borrowId, double amount,
			String cardNo, String bank, String scenes, String periods ,String type);

	/**
	 * 查询所有
	 * @param pmap
	 * @return
	 */
	List<PayLog> listSelective(Map<String, Object> pmap);

	/**
	 * 查询用户的还款记录表
	 * @param borrowId
	 * @return
	 */
	PayLog findRepayMentLog(Long borrowId);

	/**
	 * 通过第三方交易流水号查询订单
	 * @param tradeNo
	 * @return
	 */
	PayLog findByTradeNo(String tradeNo);

	/**
	 * 根据订单id查询放款失败次数
	 * @param borrowId 订单id
	 * @return  订单放款失败次数
	 */
	int findPayOutFailedCount(Long borrowId);


}
