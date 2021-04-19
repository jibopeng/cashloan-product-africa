package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserRemark;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 本人备注记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-11 21:32:04
 */
public interface UserRemarkService extends BaseService<UserRemark, Long> {

	int saveOrUpdateRemark(Long userId, String remark);
	
	UserRemark findByUserId(Long userId);

}
