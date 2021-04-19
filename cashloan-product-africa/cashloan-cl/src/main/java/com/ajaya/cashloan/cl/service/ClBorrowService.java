package com.ajaya.cashloan.cl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.model.*;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;

/**
 * 借款信息表Service
 *
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 10:13:31
 */
public interface ClBorrowService extends BaseService<Borrow, Long> {


    /**
     * 判断是否可以借款
     *
     * @param borrow
     * @return
     */
    boolean isCanBorrow(Borrow borrow);

    /**
     * 保存借款申请
     *
     * @param borrow
     * @return
     */
    Borrow saveBorrow(Borrow borrow);

    /**
     * 修改借款状态
     *
     * @param id
     * @param state
     * @return
     */

    int modifyState(long id, String state);
    /**
     * 添加借款进度
     *
     * @param borrow
     * @param state
     */
    void savePressState(Borrow borrow, String state, String remark);
    /**
     * 首页信息查询
     *
     * @param userId 用户id
     * @return Map
     */
    Map<String, Object> findIndex(String userId);


    /**
     * 查询
     *
     * @param searchMap
     * @return
     */
    List<Borrow> findBorrowByMap(Map<String, Object> searchMap);

    /**
     * 查询最新10条借款信息
     *
     * @return
     */
    List<IndexModel> listIndex();

    /**
     * 借款记录查看
     *
     * @param searchMap
     * @return
     */
    List<PendingRepayModel> findRepay(Map<String, Object> searchMap);

    /**
     * 分页查询
     *
     * @param userId 用户id
     * @param current 当前页
     * @param pageSize 页大小
     * @return 借款记录列表
     */
    Page<BorrowListModel> pageList(Long  userId, int current,  int pageSize);

    /**
     * 关联用户的借款分页查询后台列表显示
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<ManageBorrowModel> listModel(Map<String, Object> params,
                                      int currentPage, int pageSize);

    /**
     * 修改数据
     *
     * @param data
     * @return
     */
    int updateSelective(Map<String, Object> data);

    /**
     * 匹配规则查询
     *
     * @param borrowId
     * @return
     */
    Map<String, Object> findResult(long borrowId);

    /**
     * 查询可借款用户
     *
     * @return
     */
    List<ManageBorrowTestModel> seleteUser();


    /**
     * 后台人工复审功能
     *
     * @param borrowId
     * @param state
     * @param remark
     * @return
     */
    int manualVerifyBorrow(Long borrowId, String state, String remark);

    /**
     * 借款部分还款信息
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<ManageBorrowModel> listBorrowModel(Map<String, Object> params,
                                            int currentPage, int pageSize);

    /**
     * 借款详细信息
     *
     * @param borrowId
     * @return
     */
    ManageBorrowModel getModelByBorrowId(long borrowId);

    /**
     * 支付时更新状态
     *
     * @return
     */
    void updatePayState(Map<String, Object> paramMap);

    /**
     * 主键查询借款
     *
     * @param borrowId
     * @return
     */
    Borrow findByPrimary(Long borrowId);

    /**
     * 新增借款申请业务处理
     *
     * @param borrow     借款信息
     * @param mobileType 手机类型
     * @param imie       手机设备唯一标识
     * @param appRunTime @return  ClBorrowModel
     * @throws Exception 抛出异常
     */
    ClBorrowModel rcBorrowApply(Borrow borrow,  String mobileType, String imie, long appRunTime) throws Exception;

    /**
     * 借款规则审核
     *
     * @param borrowId
     */
    void rcBorrowRuleVerify(Long borrowId);

    /**
     * 统计接口接口审核结果
     *
     * @param state
     * @param desc
     * @param borrowId
     * @param nid
     * @return
     */
    void syncSceneBusinessLog(Long borrowId, String nid, int count);

    /**
     * 接口异步通知时更新
     *
     * @param state
     * @param desc
     * @param borrowId
     * @param nid
     */
    void syncSceneBusinessLog(String state, String desc, Long borrowId, String nid);

    /**
     * 查询借款
     *
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    List listBorrow(Map<String, Object> params);

    /**
     * 获取风控数据并审核
     *
     * @param borrowId
     */
    void verifyBorrowData(long borrowId);

    /**
     * 重新获取风控数据并审核
     *
     * @param borrowId
     */
    void reVerifyBorrowData(Long borrowId) throws Exception;

    /**
     * 复审通过查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<ManageBorrowModel> listReview(Map<String, Object> params, int currentPage, int pageSize);

    /**
     * 查询最后一条借款
     *
     * @param userId
     * @return
     */
    Borrow findLastBorrow(long userId);

    /**
     * 修改借款状态
     *
     * @param borrowId
     * @param userId
     */
    int modifyBorrowState(long borrowId, long userId, String state);
    /**
     * 查询是未完成借款
     *
     * @param userId
     * @return
     */
    List<Borrow> findUserUnFinishedBorrow(long userId);
    /**
     * @param borrowId
     * @param state
     * @param remark
     * @return void
     * @description 审核放款
     * @author mcwang
     * @since 1.0.0
     */
    int auditBorrowLoan(Long borrowId, String state, String remark, Long userId);

    /**
     * 查询待还款详情
     *
     * @param searchMap 查询条件
     * @return PendingRepayDetailModel
     */
    PendingRepayDetailModel findRepayDetail(Map<String, Object> searchMap);

    /**
     * 根据borrowId查询借款详情
     *
     * @param borrowId 借款订单id
     * @return BorrowDetailModel
     */
    BorrowDetailModel findBorrowDetailById(Long borrowId);

    /**
     * 获取用户放款信息
     *
     * @param borrowId 借款id
     * @return 响应信息
     */
    ResponseWrapper getPayInfo(Long borrowId);


    /**
     * 放款
     *
     * @param borrow 放款订单
     * @param date  时间日期
     */
    void borrowLoan(Borrow borrow, Date date);

    /**
     * 查找待发短信列表（未领款）
     * @param start 开始时间
     * @param end 结束时间
     */
    List<MessageSendModel> selectSendMessageInfoList(Date start, Date end);

    /**
     * 判断是否为有结清订单的老用户
     * @param userId
     * @return
     */
    boolean isRefinanceBeforeCreateBorroworder(Long userId);

    /**
     * 根据日期集合查询应该发送短信的集合
     * @param dateList 日期集合
     * @return MessageSendModel
     */
    List<MessageSendModel> selectSendMessageInfoList(ArrayList<String> dateList);

    /**
     * 计算再次借款需间隔的天数
     *
     * @param userId
     *            用户id
     * @return int 天数
     */
    int getAgainBorrowDays(Long userId);

    /**
     * 获取用户最大逾期天数
     * @param userId
     * @return
     */
    int findMaxPenaltyDay(Long userId);


    /**
     * 取消放款
     * @param borrow 订单实体
     */
    void cancelBorrow(Borrow borrow);
    
    /**
     * 查询当前支付账户今天的放款笔数
     */
	Integer getPayCountByChannel(Long payChannelId);
    /**
     * 查询待取消领款订单列表
     * @return MessageSendModel
     */
    List<MessageSendModel> selectCancelOrderInfoList();
    
    /**
     * 信审模块：关联用户的借款分页查询后台列表显示
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<ManageBorrowModel> listModelForCredit(Map<String, Object> params,
                                      int currentPage, int pageSize);

    /**
     * 订单详情确认（领款）
     * @param borrowId 订单号
     * @return  BorrowConfirmModel
     */
    BorrowConfirmModel findBorrowConfirm(Long borrowId);

    /**
     * 额度变更
     * @param borrowId 订单id
     * @return ResponseWrapper
     */
    void changeAmountHalf(Long borrowId);
}
