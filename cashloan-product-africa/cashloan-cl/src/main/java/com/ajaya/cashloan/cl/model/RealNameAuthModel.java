package com.ajaya.cashloan.cl.model;

import java.util.Date;

/**
 * 功能说明：是认证返回信息
 *
 * @author yanzhiqiang
 * @since 2020-02-27 11:58
 **/
public class RealNameAuthModel {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 客户表 外键
     */
    private Long userId;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 证件号码
     */
    private String idNo;

    /**
     * 身份证地址
     */
    private String idAddr;

    /**
     * 自拍(人脸识别照片)
     */
    private String livingImg;

    /**
     * 身份证正面
     */
    private String frontImg;

    /**
     * 身份证反面
     */
    private String backImg;

    /**
     * pan 代码
     */
    private String panCode;

    /**
     * pan 图片
     */
    private String panImg;

    /**
     * 出生日
     */
    private String dateOfBirth;
    /**
     * 邮编
     */
    private String pinCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdAddr() {
        return idAddr;
    }

    public void setIdAddr(String idAddr) {
        this.idAddr = idAddr;
    }

    public String getLivingImg() {
        return livingImg;
    }

    public void setLivingImg(String livingImg) {
        this.livingImg = livingImg;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public String getPanCode() {
        return panCode;
    }

    public void setPanCode(String panCode) {
        this.panCode = panCode;
    }

    public String getPanImg() {
        return panImg;
    }

    public void setPanImg(String panImg) {
        this.panImg = panImg;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "RealNameAuthModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", idNo='" + idNo + '\'' +
                ", idAddr='" + idAddr + '\'' +
                ", livingImg='" + livingImg + '\'' +
                ", frontImg='" + frontImg + '\'' +
                ", backImg='" + backImg + '\'' +
                ", panCode='" + panCode + '\'' +
                ", panImg='" + panImg + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }
}
