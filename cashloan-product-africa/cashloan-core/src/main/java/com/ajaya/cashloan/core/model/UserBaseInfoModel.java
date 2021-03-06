package com.ajaya.cashloan.core.model;

import com.ajaya.cashloan.core.domain.UserBaseInfo;

/**
 * 用户基本信息model
 * @author xx
 * @version 1.0.0
 * @date 2017年3月22日 下午7:20:27
 */
public class UserBaseInfoModel extends UserBaseInfo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户状态-黑名单 10
	 */
	public static final String USER_STATE_BLACK = "10";
	
	/**
	 * 用户状态-不是黑名单 20
	 */
	public static final String USER_STATE_NOBLACK = "20";
	
	/**
	 * 用户状态-白名单 20
	 */
	public static final String USER_STATE_WHITE = "30";

}
