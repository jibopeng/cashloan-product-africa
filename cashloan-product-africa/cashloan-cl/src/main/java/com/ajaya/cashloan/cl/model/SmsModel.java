package com.ajaya.cashloan.cl.model;

import java.util.Date;

/**
 * 短信model
 * @author xx
 * @version 1.0.0
 * @date 2017年3月15日 下午6:39:20
 */
public class SmsModel {
	
	/**
	 * 注册验证码-register
	 */
	public static final String SMS_TYPE_REGISTER = "register";
	/**
	 * 找回登录密码-findReg
	 */
	public static final String SMS_TYPE_FINDREG = "findReg";
	/**
	 *  短信发送次数
	 */
	private Integer count;
	/**
	 * 最有一次发送时间
	 */
	private Date lastTime;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

}
