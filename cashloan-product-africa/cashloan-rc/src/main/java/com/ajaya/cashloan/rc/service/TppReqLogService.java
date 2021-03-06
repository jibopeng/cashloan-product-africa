package com.ajaya.cashloan.rc.service;

import java.util.Map;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rc.domain.TppReqLog;

/**
 * 第三方征信请求记录Service
 * 
 * @author zlh
 * @version 1.0.0
 * @date 2017-03-20 13:50:34
 */
public interface TppReqLogService extends BaseService<TppReqLog, Long>{

	/**
	 * 根据订单号修改记录
	 * @param params
	 * @return
	 */
	int modifyTppReqLog(TppReqLog log);
	
	/**
	 * 根据参数查询
	 * @param params
	 * @return
	 */
	TppReqLog findSelective(Map<String, Object> params);
	
}
