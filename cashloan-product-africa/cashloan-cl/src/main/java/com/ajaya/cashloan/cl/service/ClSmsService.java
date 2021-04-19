package com.ajaya.cashloan.cl.service;

import java.util.Map;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.domain.Sms;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 短信记录Service
 *
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 */
public interface ClSmsService extends BaseService<Sms, Long> {
    /**
     * 群发催收短信
     *
     * @param id
     * @return
     */
    int smsBatch(String id, String type);

    /**
     * 查询与最近一条短信的时间差（秒）
     *
     * @param phone
     * @param type
     * @return
     */
    long findTimeDifference(String phone, String type);

    /**
     * 根据手机号码、短信验证码类型查询今日可获取次数，防短信轰炸
     *
     * @param phone
     * @param type
     * @return
     */
    int countDayTime(String phone, String type);

    /**
     * 发送短信
     *
     * @param phone
     * @param type
     * @return
     */
    String sendSms(String phone, String type);

    /**
     * 验证短信
     *
     * @param phone
     * @param type
     * @param code
     * @param signMsg
     * @return
     */
    int verifySms(String phone, String type, String code);

    /**
     * 查询用户
     *
     * @param phone
     * @return
     */
    int findUser(String phone);

    /**
     * 领款通知短信
     *
     * @param phone 用户手机号
     * @param type  消息类型
     * @return String
     */

    String sendGetMoneySms(String phone, String type);
    
    /**
     * 提前通知短信&还款到期提醒
     * @param phone
     * @param type =
     * 			sms_advance_notice【还款提醒短信】
     * 			sms_today_notice【还款到期提醒】
     * @param borrowId
     * @return
     */
    String sendAdvanceSms(String phone, String type, BorrowRepay borrowId);
    /**
     * 逾期通知短信
     *
     * @param phone 电话
     * @param dueDay 逾期天数
     * @param productName 名称产品
     * @param borrowId 订单号
     *@param penaltyAmount 逾期金额
     */
    String overdue(String phone, String dueDay, String productName, Long borrowId, Double penaltyAmount);

    /**
     * 短信列表查询
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Sms> list(Map<String, Object> params, int currentPage, int pageSize);


    /**
     * 判断是否为刷短信的
     *
     * @param phone
     * @param type
     * @return
     */
    boolean checkIsMotherFucker(String phone, String type);

    /**
     * 发送逾期1-3天还款客户提醒短信(防催收员私自收钱)
     *
     * @param phone       电话号码
     * @param productName 产品名称
     * @return 发送结果
     */
    String repaymentAdvanceCustomer(String phone, String productName);

    /**
     * 发送老app禁用通知
     * @param userId 用户id
     */

    void sendAppDisableNotice(String  userId);

    /**
     * 拨打一个语音电话
     * @param phone 手机号
     *              @param type
     * @return ResponseWrapper
     */
    ResponseWrapper makeVoiceCall(String phone,String type);

    /**
     * 处理语音电话回调
     * @param params 回调参数
     * @return 返回要发送的内容
     */
    String dealVoiceCall(JSONObject params);
}
