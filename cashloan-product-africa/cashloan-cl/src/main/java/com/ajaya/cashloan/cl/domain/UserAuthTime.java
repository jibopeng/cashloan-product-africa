package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户认证时间记录表实体
 * 
 */
 public class UserAuthTime implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户标识
    */
    private Long userId;

    /**
    * 未认证时间
    */
    private Date createTime;

    /**
    * 实名认证状态认证完成时间
    */
    private Date basicInfoAuthOverTime;

    /**
    * 实名认证状态更新完成时间
    */
    private Date basicInfoAuthUpdateTime;

    /**
    * 紧急联系人认证完成时间
    */
    private Date contactAuthOverTime;

    /**
    * 紧急联系人认证更新时间
    */
    private Date contactAuthUpdateTime;

    /**
    * 个人信息认证完成时间
    */
    private Date personalInfoAuthOverTime;

    /**
    * 个人信息认证更新时间
    */
    private Date personalInfoAutUpdateTime;


    /**
     * 银行卡认证完成时间
     */
    private Date bankAccountAuthOverTime;
    
    /**
     * 银行卡认证更新时间
     */
    private Date bankAccountAuthUpdateTime;

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

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

    public Date getBasicInfoAuthOverTime() {
        return basicInfoAuthOverTime;
    }

    public void setBasicInfoAuthOverTime(Date basicInfoAuthOverTime) {
        this.basicInfoAuthOverTime = basicInfoAuthOverTime;
    }

    public Date getBasicInfoAuthUpdateTime() {
        return basicInfoAuthUpdateTime;
    }

    public void setBasicInfoAuthUpdateTime(Date basicInfoAuthUpdateTime) {
        this.basicInfoAuthUpdateTime = basicInfoAuthUpdateTime;
    }

    public Date getContactAuthOverTime() {
      return contactAuthOverTime;
   }

   public void setContactAuthOverTime(Date contactAuthOverTime) {
      this.contactAuthOverTime = contactAuthOverTime;
   }

   public Date getContactAuthUpdateTime() {
      return contactAuthUpdateTime;
   }

   public void setContactAuthUpdateTime(Date contactAuthUpdateTime) {
      this.contactAuthUpdateTime = contactAuthUpdateTime;
   }

   public Date getPersonalInfoAuthOverTime() {
      return personalInfoAuthOverTime;
   }

   public void setPersonalInfoAuthOverTime(Date personalInfoAuthOverTime) {
      this.personalInfoAuthOverTime = personalInfoAuthOverTime;
   }

   public Date getPersonalInfoAutUpdateTime() {
      return personalInfoAutUpdateTime;
   }

   public void setPersonalInfoAutUpdateTime(Date personalInfoAutUpdateTime) {
      this.personalInfoAutUpdateTime = personalInfoAutUpdateTime;
   }

    public Date getBankAccountAuthOverTime() {
        return bankAccountAuthOverTime;
    }

    public void setBankAccountAuthOverTime(Date bankAccountAuthOverTime) {
        this.bankAccountAuthOverTime = bankAccountAuthOverTime;
    }

    public Date getBankAccountAuthUpdateTime() {
        return bankAccountAuthUpdateTime;
    }

    public void setBankAccountAuthUpdateTime(Date bankAccountAuthUpdateTime) {
        this.bankAccountAuthUpdateTime = bankAccountAuthUpdateTime;
    }
}