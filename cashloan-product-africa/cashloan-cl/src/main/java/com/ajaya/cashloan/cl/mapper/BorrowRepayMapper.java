package com.ajaya.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.model.BorrowRepayParamModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.model.BorrowRepayModel;
import com.ajaya.cashloan.cl.model.ManageBRepayModel;
import com.ajaya.cashloan.cl.model.ManageBorrowModel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 还款计划Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 13:42:32

 */
@RDBatisDao
public interface BorrowRepayMapper extends BaseMapper<BorrowRepay,Long> {

	int updateByBorrowId(Map<String, Object> repayMap);

	/**
	 * 后台还款记录列表
	 *
	 * @param params
	 * @return
	 */
	List<ManageBRepayModel> listModel(Map<String, Object> params);

	/**
	 * 逾期更新数据
	 *
	 * @param data
	 * @return
	 */
	int updateLate(BorrowRepay data);

	/**
	 * @param paramMap
	 */
	int updateParam(Map<String, Object> paramMap);

	/**
	 * 催款管理
	 *
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listRepayModel(Map<String, Object> params);

	/**
	 * 逾期未入催
	 *
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listModelNotUrge(Map<String, Object> params);

	/**
	 * 查询所有
	 *
	 * @param searchMap
	 * @return
	 */
	List<BorrowRepayModel> listSelModel(Map<String, Object> searchMap);

	/**
	 * 查询借款成功未还款还款已过还款时间记录(放款成功及逾期但未还款的)
	 *
	 * @param paramMap
	 * @return
	 */
	BorrowRepay findUnRepay(Map<String, Object> paramMap);

	/**
	 * 查询借款成功未还款还款已过还款时间记录(放款成功及逾期但未还款的)
	 *
	 * @param paramMap
	 * @return
	 */
	List<BorrowRepay> findUnRepayForSms(Map<String, Object> paramMap);

	/**
	 * 查询还款
	 *
	 * @param borrowId
	 * @return
	 */
	BorrowRepayModel findOverdue(long borrowId);

	/**
	 * 查询当天还款计划总额
	 *
	 * @param map
	 * @return
	 */
	double findRepayTotal();

	/**
	 * 刘晓亮 2017年11月18日 12:22:16
	 * 查询逾期记录，便于定时任务通知
	 *
	 * @return
	 */
	List<Map<String, Object>> findReaymentOverdueList(Long penalty_day);

	/**
	 * 查询最后一条还款记录
	 *
	 * @param paramMapForRepaymentSuccess
	 * @return
	 */
	BorrowRepay findLastRepayLog(Map<String, Object> paramMapForRepaymentSuccess);

	List<BorrowRepay> findUnRepayDaiKou(Map<String, Object> paramMap);

	/**
	 * 根据借款id和借款状态查询实体
	 *
	 * @param paramMap
	 * @return
	 */
	BorrowRepay findByBIdAndState(Map<String, Object> paramMap);

	/**
	 * 根据用户的id查询所有逾期订单
	 *
	 * @param userId 用户id
	 * @return 列表
	 */
	List<BorrowRepay> findOverdueList(long userId);

	//更新还款计划
	Integer updateForCuishou(BorrowRepay borrowRepay);

	List<BorrowRepayModel> findOverDueListByRepayTime(String date);

	/**
	 * 查询订单是否存在
	 *
	 * @param borrowId borrowId
	 * @return 存在数
	 */
	Integer selectCountByBorrowId(long borrowId);

	List<BorrowRepay> listByRepayTimeByDesc(Map<String, Object> paramMap);

	/**
	 * 查询逾期记录，便于定时任务通知
	 *
	 * @return
	 */
	List<Map<String, Object>> findReaymentOverdueListNew(Long penalty_day);

	/**
	 * 查询当天user_type的放款量
	 * @param format 日期
	 * @param userType 用户类型
	 * @return 放款数
	 */
	Integer getPayNumDay(@Param("format")String format, @Param("userType")String userType);

	/**
	 * 获取最大逾期天数
	 * @param userId
	 * @return
	 */
	Integer getMaxPenaltDay(@Param("userId")Long userId);


	/**
	 * 根据订单号、银行卡id 查找应还款金额
	 * @param borrowId 订单id
	 * @param cardId 银行卡id
	 * @return BorrowRepayParamModel
	 */
    BorrowRepayParamModel findRepayParam(@Param("borrowId") Long borrowId,@Param("cardId") Long cardId);

	/**
	 * 根据用户id查找已经放款订单数
	 * @param userId 用户id
	 * @return 数量
	 */
    int getOrderCount(Long userId);

}
