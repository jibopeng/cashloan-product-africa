package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * epoch抓取数据同步回调日志表实体
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-14 12:21:01
 */
public class SdkCatchDataSyncLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 借款id
     */
    private Long borrowId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 报告抓取状态1：成功 2 成功失败
     */
    private Integer reportStatus;
    /**
     * sdk抓取epoch相应吗
     */
    private  String  code;
    /**
     * epoch抓取响应信息
     */
    private String  message;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *抓取类型：msg：短信 app: app img：相册 contact：通讯录 device：设备信息
     */
    private String  type;


    /**
     * 获取主键Id
     *
     * @return id
     */
    public Long getId(){
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param 要设置的主键Id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * 获取借款id
     *
     * @return 借款id
     */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
     * 设置借款id
     *
     * @param borrowId 要设置的借款id
     */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public Long getUserId(){
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 要设置的用户id
     */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
     * 获取报告抓取状态1：成功 2 成功失败
     *
     * @return 报告抓取状态1：成功 2 成功失败
     */
    public Integer getReportStatus(){
        return reportStatus;
    }

    /**
     * 设置报告抓取状态1：成功 2 成功失败
     *
     * @param reportStatus 要设置的报告抓取状态1：成功 2 成功失败
     */
    public void setReportStatus(Integer reportStatus){
        this.reportStatus = reportStatus;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime(){
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}