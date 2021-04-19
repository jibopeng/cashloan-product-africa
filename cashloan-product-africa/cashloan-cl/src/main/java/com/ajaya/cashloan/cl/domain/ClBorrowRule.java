package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单规则表实体
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-06-18 19:04:15
 */
public class ClBorrowRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单id
     */
    private Long borrowId;

    /**
     * 复贷申请时，之前订单最大逾期天数
     */
    private Integer maxPenaltyDays;

    /**
     * 用户通讯录个数(非重复)
     */
    private Integer contactsNum;

    /**
     * 用户短信个数
     */
    private Integer msgNum;

    /**
     * 用户app个数
     */
    private Integer appNum;

    /**
     * 短信和app是否都为空，0：都为空，1：有有一个不为空
     */
    private Integer msgAppNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 申请时imei 与注册时是否一致 1 一致 2 不一致
     */
    private Integer imeiIsSame;
    /**
     * 是否为永久黑用户 1 永久黑 2 非永久黑
     */
    private Integer permBlackList;
    /**
     * Forbidden area 1不是禁止区域，2是禁止区域
     */
    private Integer forbiddenArea;
    
    /**
     * 多头笔数
     */
    private Integer onLoanOrderCnt;

    public Integer getForbiddenArea() {
        return forbiddenArea;
    }

    public void setForbiddenArea(Integer forbiddenArea) {
        this.forbiddenArea = forbiddenArea;
    }

    /**
     * 获取主键Id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param 要设置的主键Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 要设置的用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取订单id
     *
     * @return 订单id
     */
    public Long getBorrowId() {
        return borrowId;
    }

    /**
     * 设置订单id
     *
     * @param borrowId 要设置的订单id
     */
    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    /**
     * 获取复贷申请时，之前订单最大逾期天数
     *
     * @return 复贷申请时，之前订单最大逾期天数
     */
    public Integer getMaxPenaltyDays() {
        return maxPenaltyDays;
    }

    /**
     * 设置复贷申请时，之前订单最大逾期天数
     *
     * @param maxPenaltyDays 要设置的复贷申请时，之前订单最大逾期天数
     */
    public void setMaxPenaltyDays(Integer maxPenaltyDays) {
        this.maxPenaltyDays = maxPenaltyDays;
    }


    public Integer getImeiIsSame() {
        return imeiIsSame;
    }

    public void setImeiIsSame(Integer imeiIsSame) {
        this.imeiIsSame = imeiIsSame;
    }

    public Integer getPermBlackList() {
        return permBlackList;
    }

    public void setPermBlackList(Integer permBlackList) {
        this.permBlackList = permBlackList;
    }

    /**
     * 获取用户通讯录个数(非重复)
     *
     * @return 用户通讯录个数(非重复)
     */
    public Integer getContactsNum() {
        return contactsNum;
    }

    /**
     * 设置用户通讯录个数(非重复)
     *
     * @param contactsNum 要设置的用户通讯录个数(非重复)
     */
    public void setContactsNum(Integer contactsNum) {
        this.contactsNum = contactsNum;
    }

    /**
     * 获取用户短信个数
     *
     * @return 用户短信个数
     */
    public Integer getMsgNum() {
        return msgNum;
    }

    /**
     * 设置用户短信个数
     *
     * @param msgNum 要设置的用户短信个数
     */
    public void setMsgNum(Integer msgNum) {
        this.msgNum = msgNum;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 要设置的更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMsgAppNum() {
        return msgAppNum;
    }

    public void setMsgAppNum(Integer msgAppNum) {
        this.msgAppNum = msgAppNum;
    }

    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
    }

	public Integer getOnLoanOrderCnt() {
		return onLoanOrderCnt;
	}

	public void setOnLoanOrderCnt(Integer onLoanOrderCnt) {
		this.onLoanOrderCnt = onLoanOrderCnt;
	}
}