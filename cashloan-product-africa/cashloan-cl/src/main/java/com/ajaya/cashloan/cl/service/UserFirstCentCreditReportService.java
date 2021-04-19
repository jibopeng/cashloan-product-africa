package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserFirstCentCreditReport;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 'FC用户征信报告表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 15:20:15
 */
public interface UserFirstCentCreditReportService extends BaseService<UserFirstCentCreditReport, Long>{

	/**
	 * 保存用户的信用报告
	 * @param borrowId
	 * @return
	 */
	public Integer saveFirstCentCreditReport(Long borrowId);
}
