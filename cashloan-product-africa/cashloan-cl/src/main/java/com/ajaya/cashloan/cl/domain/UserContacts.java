package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户联系人
 * @author chenxy
 * @version 1.0.0
 * @date 2017-03-04 11:52:26
 */
 public class UserContacts implements Serializable {

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
	 * 订单标识
	 */
	private Long borrowId;

    /**
    * 姓名
    */
    private String name;

    /**
    * 手机号码
    */
    private String phone;
    
    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
    
    /**
    * 区分认证紧急联系人前后抓取数据，1：完善紧急联系人前；2：完善紧急联系人后
    */
    private Integer type;

    /**
     * 通讯录备注
     */
    private String remark;
    
    /**
     * 短信联系次数
     */
    private String msgTimes;
    
    
    
	public String getMsgTimes() {
		return msgTimes;
	}

	public void setMsgTimes(String msgTimes) {
		this.msgTimes = msgTimes;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取主键Id
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键Id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取用户标识
	 * @return userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户标识
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
    /**
    * 获取订单标识
    *
    * @return 订单标识
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置订单标识
    * 
    * @param borrowId 要设置的订单标识
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

	/**
	 * 获取姓名
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取手机号码
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置手机号码
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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

    /**
    * 获取更新时间
    *
    * @return 更新时间
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置更新时间
    * 
    * @param updateTime 要设置的更新时间
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    
    /**
     * 获取区分认证紧急联系人前后抓取数据，1：完善紧急联系人前；2：完善紧急联系人后
     *
     * @return 区分认证紧急联系人前后抓取数据，1：完善紧急联系人前；2：完善紧急联系人后
     */
     public Integer getType(){
         return type;
     }

    /**
     * 设置区分认证紧急联系人前后抓取数据，1：完善紧急联系人前；2：完善紧急联系人后
     * 
     * @param type 要设置的区分认证紧急联系人前后抓取数据，1：完善紧急联系人前；2：完善紧急联系人后
     */
     public void setType(Integer type){
         this.type = type;
     }

}