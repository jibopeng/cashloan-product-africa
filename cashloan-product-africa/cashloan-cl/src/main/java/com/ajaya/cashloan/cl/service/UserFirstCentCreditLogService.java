package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserFirstCentCreditLog;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 'FC征信报告调用记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 15:19:50
 */
public interface UserFirstCentCreditLogService extends BaseService<UserFirstCentCreditLog, Long>{

	public Integer saveUserFirstCentCreditLog(Long userId , Long borrowId, String bvn, String type, String requestParam, String response);
}
