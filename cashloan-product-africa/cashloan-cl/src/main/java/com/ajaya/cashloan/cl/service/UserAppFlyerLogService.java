package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserAppFlyerLog;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.Borrow;

/**
 * AF统计记表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-01 10:56:25
 */
public interface UserAppFlyerLogService extends BaseService<UserAppFlyerLog, Long>{

	public Integer save(String customerUserId, String eventName, String responseJson, String eventTime);
	
	/**
     * 统计事件
     * @param borrow 订单详情
     * @param type 事件类型  2申请3 申请通过 4放款
     */
    void appFlyerEnventMonito(Borrow borrow, int type);
    
    /**
     * 注册事件
     * @param gpsadId
     * @param uuid
     */
    void appFlyerRegistEnventMonito(String gpsadId, String uuid);
}
