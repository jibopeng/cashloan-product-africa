package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：用于发送信息实体
 *
 * @author yanzhiqiang
 * @since 2020-07-24 15:47
 **/
public class MessageSendModel {

    /**
     * 用户id
     */
    private Long userId;
    /**
     *用户名称
     */
    private String userName;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 订单id
     */
    private Long borrowId;
    /**
     *订单状态
     */

    private String tate;
    /**
     * 审批时间
     */
    private String createTime;
    /**
     * 产品名称
     */
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public String getTate() {
        return tate;
    }

    public void setTate(String tate) {
        this.tate = tate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
