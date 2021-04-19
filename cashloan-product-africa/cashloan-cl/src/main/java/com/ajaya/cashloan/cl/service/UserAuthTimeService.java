package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserAuthTime;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 用户认证时间记录表Service
 */
public interface UserAuthTimeService extends BaseService<UserAuthTime, Long>{

	/**
	 * 根据user_id,查找记录
	 */
	public UserAuthTime getUserAuthTimeByUserId(Long user_id);

	/**
	 * 更新
	 * @param userAuthTime
	 */
	public void updateSelectiveByObject(UserAuthTime userAuthTime);
}
