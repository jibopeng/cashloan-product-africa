package com.ajaya.cashloan.cl.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.domain.PayLog;
import com.ajaya.cashloan.cl.model.BorrowRepayModel;
import com.ajaya.cashloan.cl.model.BorrowRepayParamModel;
import com.ajaya.cashloan.cl.model.ManageBRepayModel;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.model.ManageBorrowModel;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.Borrow;

/**
 * 还款计划Service
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 13:42:32
 */
public interface BorrowRepayService extends BaseService<BorrowRepay, Long>{

	/**
	 * 保存还款计划
	 * @param borrowRepay
	 * @return
	 */
	int save(BorrowRepay borrowRepay);
	
	/**
	 * 生成还款计划
	 * @param borrow
	 * @return
	 */
	boolean genRepayPlan(Borrow borrow);
	
	/**
	 * 还款计划 放款 成功之后 银行卡授权
	 * 
	 * @param userId
	 */
	void authSignApply(Long userId);
	
	
	 /**
	  * 后台列表
	  * @param params
	  * @param currentPage
	  * @param pageSize
	  * @return
	  */
	Page<ManageBRepayModel> listModel(Map<String, Object> params, int currentPage,
                                      int pageSize);

	/**
	 * 确认还款生产还款记录
	 * @param param
	 * @return
	 */
	Map<String,Object> confirmRepay(Map<String, Object> param);

	/**
	 * 查询所有
	 * @param paramMap
	 * @return
	 */
	List<BorrowRepay> listSelective(Map<String, Object> paramMap);

	/**
	 * 逾期更新信息
	 * @param data
	 * @return
	 */
	int updateLate(BorrowRepay data);
	
	/**
	 * 条件更新还款计划数据
	 * @param br
	 * @return
	 */
	int updateSelective(Map<String, Object> paramMap);
	

	/**
	 * 催收借款信息接口
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowModel> listRepayModel(Map<String, Object> params,
			int currentPage, int pageSize);

	/**
	 * 逾期未入催
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowModel> listModelNotUrge(Map<String, Object> params,
			int currentPage, int pageSize);

	/**
	 * 查询未还款列表
	 * 
	 * @param params
	 * @return
	 */
	BorrowRepay findUnRepay(Map<String, Object> paramMap);
	
	
	/**
	 * 查询还款计划
	 * 
	 * @param paramMap
	 * @return
	 */
	BorrowRepay findSelective(Map<String, Object> paramMap);

	/**
	 * 查询所有还款信息
	 * @param params
	 * @return
	 */
	List<ManageBRepayModel> listAllModel(Map<String, Object> params);
	/**
	 * 文件解析批量还款
	 * @param repayFile
	 * @param type
	 * @throws IOException 
	 */
	List<List<String>> fileBatchRepay(MultipartFile repayFile, String type) throws Exception;
	
	Map<String, String> paySdkParams(Long userId, String agreeNo, double amount, String orderNo);
	
	/**
	 * 主动还款
	 * @param payType
	 * @param borrowId
	 * @param userId
	 * @param ip
	 * @return
	 */
	Map<String, String> repayment(Long borrowId, Long userId, String ip);

	void repaymentReturn(String payResult, String payOrderNo);

	void repaymentNotify(PayLog payLog, String statePaymentSuccess,
                         String null1, String repayWayCertified, String cardNo, double payFee);


	/**
	 * 还款短信
	 * @param paramMap
	 * @return
	 */
	List<BorrowRepay> findUnRepayForSms(Map<String, Object> paramMap);

	/**
	 * H5主动还款
	 * @author luyu	2017-11-06
	 * @param payType
	 * @param borrowId
	 * @param userId
	 * @param ip
	 */
	Map<String, String> repaymentH5(Long borrowId, long userId, String ip);
	
	/**
	 * H5主动还款 - 参数封装
	 * @author luyu	2017-11-06
	 * @param userId
	 * @param agreeNo
	 * @param amount
	 * @param orderNo
	 * @return
	 */
	Map<String, String> paySdkParamsH5(Long userId, String agreeNo, double amount, String orderNo);
	
	/**
	 * 查询逾期记录，便于定时任务通知
	 * @author 刘晓亮 2017年11月18日 12:18:22
	 * @param penalty_day 逾期天数
	 */
	List<Map<String, Object>> findReaymentOverdueList(long penalty_day);

	/**
	 * 代扣查询
	 * @param paramMap
	 * @return
	 */
	List<BorrowRepay> findUnRepayDaiKou(Map<String, Object> paramMap);

	/**
	 * 根据借款id和借款状态查询实体
	 * @param borrowId
	 * @param state
	 * @return
	 */
	BorrowRepay findByBIdAndState(Long borrowId,String state);

	//更新还款计划
	Integer repayCheck(Long borrowId, String amount);

	/**
	 * 按应还款时间查找列表
	 * @param date 时间
	 * @return
	 */
    List<BorrowRepayModel> findOverDueListByRepayTime(String date);
    
    /**
	 * 逾期订单降序排序
	 * @param paramMap
	 * @return
	 */
	List<BorrowRepay> listByRepayTimeByDesc(Map<String, Object> paramMap);

    List<Map<String,Object>> findReaymentOverdueListNew(long penalty_day);


	/**
	 * 获取当天的放款笔数
	 * @return 放款天数
	 */
	 Integer getPayNumByUserTypeDay(String type);

	/**
	 * 根据订单号、银行卡id 查找应还款金额
	 * @param borrowId 订单id
	 * @param cardId 银行卡id
	 * @return BorrowRepayParamModel
	 */
    BorrowRepayParamModel findRepayParam(Long borrowId, Long cardId);
}
