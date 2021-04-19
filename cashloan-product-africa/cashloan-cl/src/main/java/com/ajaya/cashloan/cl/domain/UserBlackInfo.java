package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 黑名单实体
 * 
 * @author caitt
 * @version 1.0.0
 * @date 2017-07-12 15:23:07
 */
 public class UserBlackInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 真实姓名
    */
    private String realName;

    /**
    * 身份证号
    */
    private String idNo;
    
    /**
     * 类型10 黑名单 20 白名单
     */
    private String type;
    
    /**
     * 手机号码
     */
    private String phone;
    
    /**
     * 导入时间
     */
    private Date createTime;
    
    /**
     * 类型
     */
    private String typeStr;
    
    /**
     * 处理结果
     */
    private String result;

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
    * 获取真实姓名
    *
    * @return 真实姓名
    */
    public String getRealName(){
        return realName;
    }

    /**
    * 设置真实姓名
    * 
    * @param realName 要设置的真实姓名
    */
    public void setRealName(String realName){
        this.realName = realName;
    }

    /**
    * 获取身份证号
    *
    * @return 身份证号
    */
    public String getIdNo(){
        return idNo;
    }

    /**
    * 设置身份证号
    * 
    * @param idNo 要设置的身份证号
    */
    public void setIdNo(String idNo){
        this.idNo = idNo;
    }

	/** 
	 * 获取导入时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/** 
	 * 设置导入时间
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/** 
	 * 获取类型10黑名单20白名单
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/** 
	 * 设置类型10黑名单20白名单
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * 获取类型
	 * @return typeStr
	 */
	public String getTypeStr() {
		if("10".equals(this.type)){
			typeStr = "黑名单";
		}else if("20".equals(this.type)){
			typeStr = "白名单";
		}
		return typeStr;
	}

	/** 
	 * 设置类型
	 * @param typeStr
	 */
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	/** 
	 * 获取处理结果
	 * @return result
	 */
	public String getResult() {
		return result;
	}

	/** 
	 * 设置处理结果
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}

}