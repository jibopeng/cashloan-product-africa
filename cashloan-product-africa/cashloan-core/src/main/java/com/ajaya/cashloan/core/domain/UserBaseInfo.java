package com.ajaya.cashloan.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详细信息实体
 *
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-21 13:44:30
 */
public class UserBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 学历
     */
    private String education;

/**
     * 薪资类型
     */
    private String salaryType;


    /**
     * 月薪范围
     */
    private String salary;

    /**
     * bvn 账户
     */
    private String bvnNo;


    /**
     * 居住地址
     */
    private String liveAddr;

    /**
     * 注册地址
     */
    private String registerAddr;

    /**
     * 注册地坐标，(经度,纬度)
     */
    private String registerCoordinate;

    /**
     * 是否黑名单 ，10是20不是
     */
    private String state;

    /**
     * 拉黑原因
     */
    private String blackReason;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 婚姻状况
     */
    private String marital;
    /**
     * 邦
     */
    private String stateAddr;
    /**
     * 第一个名字
     */
    private String firstName;
    /**
     * 中间名字
     */
    private String middleName;
    /**
     * 最后名字（姓氏）
     */
    private String lastName;
    /**
     * 出生日
     */
    private String dateOfBirth;
    /**
     * 个人邮箱
     */
    private String email;
    /**
     * 职位
     */
    private String occupation;

    /**
     * 居住类型
     */
    private String accommodationType;
    /**
     * 孩子数量
     */
            private String childrenNumber;
    /***
     * 借款目的
     */
    private String loanPurpose;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLiveAddr() {
        return liveAddr;
    }

    public void setLiveAddr(String liveAddr) {
        this.liveAddr = liveAddr;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public String getRegisterCoordinate() {
        return registerCoordinate;
    }

    public void setRegisterCoordinate(String registerCoordinate) {
        this.registerCoordinate = registerCoordinate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getStateAddr() {
        return stateAddr;
    }

    public void setStateAddr(String stateAddr) {
        this.stateAddr = stateAddr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public String getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(String childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getBvnNo() {
        return bvnNo;
    }

    public void setBvnNo(String bvnNo) {
        this.bvnNo = bvnNo;
    }


    @Override
    public String toString() {
        return "UserBaseInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", education='" + education + '\'' +
                ", salaryType='" + salaryType + '\'' +
                ", salary='" + salary + '\'' +
                ", bvnNo='" + bvnNo + '\'' +
                ", liveAddr='" + liveAddr + '\'' +
                ", registerAddr='" + registerAddr + '\'' +
                ", registerCoordinate='" + registerCoordinate + '\'' +
                ", state='" + state + '\'' +
                ", blackReason='" + blackReason + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", marital='" + marital + '\'' +
                ", stateAddr='" + stateAddr + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", occupation='" + occupation + '\'' +
                ", accommodationType='" + accommodationType + '\'' +
                ", childrenNumber='" + childrenNumber + '\'' +
                ", loanPurpose='" + loanPurpose + '\'' +
                '}';
    }
}