package com.ajaya.cashloan.cl.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.model.*;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.core.domain.Borrow;

/**
 * 借款信息表Dao
 *
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 10:13:31
 */
@RDBatisDao
public interface ClBorrowMapper extends BaseMapper<Borrow, Long> {

    /**
     * 查询用户未完成的借款
     *
     * @param userId
     * @return
     */
    List<Borrow> findUserUnFinishedBorrow(@Param("userId") Long userId);

    /**
     * 首页信息查询
     *
     * @return
     */
    List<IndexModel> listIndex();

    /**
     * 订单查询
     *
     * @param searchMap
     * @return
     */
    List<ClBorrowModel> findBorrow(Map<String, Object> searchMap);

    /**
     * 查看借款
     *
     * @param searchMap
     * @return
     */
    List<RepayModel> findRepay(Map<String, Object> searchMap);

    /**
     * 查看借款new
     *
     * @param searchMap 查询条件
     * @return List<RepayModel>
     */
    List<PendingRepayModel> findPendingRepay(Map<String, Object> searchMap);

    /**
     * 计时任务计算逾期
     *
     * @return
     */
    List<RepayModel> compute();

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<ManageBorrowModel> listModel(Map<String, Object> params);

    /**
     * 查询所有
     *
     * @param searchMap
     * @return List<ClBorrowModel>
     */
    List<ClBorrowModel> listAll(Map<String, Object> searchMap);

    /**
     * 查询借款记录列表表
     *
     * @param userId 用户id
     * @return List<ClBorrowModel>
     */
    List<BorrowListModel> findBorrowList(Long userId);
    /**
     * 查询未还款订单
     *
     * @param borrowMap
     * @return
     */
    Borrow findRepayBorrow(Map<String, Object> borrowMap);

    /**
     * 查询可借款用户
     *
     * @return
     */
    List<ManageBorrowTestModel> seleteUser();

    /**
     * 更新借款状态
     *
     * @param satet
     * @param id
     * @return
     */
    int updateState(@Param("state") String state, @Param("id") Long id);

    /**
     * 借款部分还款信息
     *
     * @param params
     * @return
     */
    List<ManageBorrowModel> listBorrowModel(Map<String, Object> params);

    /**
     * 查询未还款
     *
     * @param paramMap
     * @return
     */
    Borrow findByUserIdAndState(Map<String, Object> paramMap);

    /**
     * 支付时更新Borrow状态
     *
     * @param paramMap
     * @return
     */
    int updatePayState(Map<String, Object> paramMap);

    /**
     * 统计成功借款次数
     *
     * @param l
     * @return
     */
    long countBorrow(long l);

    /**
     * 人工复审修改状态
     *
     * @param id
     * @param state
     * @return
     */
    int reviewState(Map<String, Object> map);

    /**
     * 复审通过查询
     *
     * @return
     */
    List<ManageBorrowModel> listReview(Map<String, Object> params);

    /**
     * 查询最后一条借款
     *
     * @param userId
     * @return
     */
    Borrow findLastBorrow(long userId);

    /**
     * 查询当天借款总额
     *
     * @param map
     * @return
     */
    double borrowAmountSum();

    /**
     * 查询用户还款成功的记录数
     */
    int userBorrowCount(long userId);

    int syncUpdateState(Map<String, Object> params);

    /**
     * 放款审核修改状态
     *
     * @param id
     * @param state
     * @return
     */
    int loanState(Map<String, Object> map);

    /**
     * 为渠道管理员 借款部分还款信息
     *
     * @param params
     * @return
     */
    List<ManageBorrowModel> listBorrowModelForChannel(Map<String, Object> params);

    /**
     * 为渠道管理员 借款部分还款信息 搜索
     *
     * @param params
     * @return
     */
    List<ManageBorrowModel> listBorrowModelForChannelForSearch(Map<String, Object> params);

    /**
     * 查询用户
     *
     * @param paramMap
     * @return
     */
    List<Borrow> listSelectiveByCreateTimeDesc(Map<String, Object> paramMap);

    /**
     * 根据borrowId查询用户电话
     *
     * @param id
     * @return
     */
    String findUserPhone(Long id);

    /**
     * 查询待还款详情页
     *
     * @param searchMap 查询条件
     * @return PendingRepayDetailModel
     */
    PendingRepayDetailModel findPendingRepayDetail(Map<String, Object> searchMap);

    /**
     * 根据borrowId查询借款详情
     *
     * @param borrowId 借款订单id
     * @return BorrowDetailModel
     */
    BorrowDetailModel findBorrowDetailById(Long borrowId);

    /**
     * 根据borrowId查询借款信息（确认放款）
     * @param borrowId 借款订单id
     * @return ClBorrowDetailModel
     */
    ClBorrowDetailModel findPayInfo(Long borrowId);

    /**
     * 查找代发短信的借款列表
     * @param start 开始时间
     * @param end 结束时间
     * @return 待发列表
     */
    List<MessageSendModel> selectSendMessageInfoList(@Param("startTime" ) Date start, @Param("endTime" ) Date end);
    /**
     * 查找代发短信的借款列表
     * @param dateList 开始时间
     * @return 待发列表
     */
    List<MessageSendModel> selectSendMessageInfoListNew(ArrayList<String> dateList);
    
    //老用户没原始数据-定时任务
  	List<Borrow> listOldUserNoData();

  	//之前被拒绝过的用户
	List<Borrow> listRefuseUserBefore();
	
	//查询当天支付账户的放款笔数
  	Integer getPayCountByChannel(@Param("payChannelId")Long payChannelId);
  	
	int saveLanague(Map<String, Object> map);

	/**
  	 * 更新产品名称
  	 * @param borrowId
  	 * @param appFlag
  	 * @return
  	 */
	Integer updateProductName(@Param("borrowId")Long borrowId, @Param("appFlag")String appFlag);
    /**
     * 查询待取消领款订单列表
     * @return 待取消表
     */
    List<MessageSendModel> selectCancelOrderInfoList();
    
    /**
	 * 信审模块-查询
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listModelForCredit(Map<String, Object> params);
    /**
     * 订单详情确认（领款）
     * @param borrowId 订单号
     * @return  BorrowConfirmModel
     */
    BorrowConfirmModel findBorrowConfirm(Long borrowId);

    /**
     * 查找需要代扣的订单id
     * @return  ids
     */
    List<Long> findNeedWithholdingBorrowIds();


}
