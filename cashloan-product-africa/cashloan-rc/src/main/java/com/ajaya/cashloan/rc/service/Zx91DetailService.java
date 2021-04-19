package com.ajaya.cashloan.rc.service;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.rc.domain.TppBusiness;
import com.ajaya.cashloan.rc.domain.Zx91Detail;

/**
 * 风控数据统计-（简）通话记录统计Service
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:52:59

 */
public interface Zx91DetailService extends BaseService<Zx91Detail, Long>{

	int query91zx1003(String idNo,String realName,Long userId, TppBusiness business);
}
