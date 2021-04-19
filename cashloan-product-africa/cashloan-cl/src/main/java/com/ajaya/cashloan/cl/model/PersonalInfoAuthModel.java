package com.ajaya.cashloan.cl.model;


/**
 * 功能说明：个人认证信息返回model
 *
 * @author yanzhiqiang
 * @since 2020-02-27 12:14
 **/
public class PersonalInfoAuthModel {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 客户表 外键
     */
    private Long userId;

    /**
     * 月薪范围
     */
    private String salary;

    /**
     * 婚姻状况
     */
    private String marital;

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
     * 个人邮箱
     */
    private String email;
    /**
     * 职位
     */
    private String occupation;

    /**
     * 薪资类型
     */
    private String salaryType;

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

    /**
     * 学历
     */

    private String education;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
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

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
