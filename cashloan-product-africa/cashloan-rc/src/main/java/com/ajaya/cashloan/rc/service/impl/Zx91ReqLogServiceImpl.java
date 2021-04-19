package com.ajaya.cashloan.rc.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.domain.Zx91ReqLog;
import com.ajaya.cashloan.rc.mapper.Zx91ReqLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.rc.service.Zx91ReqLogService;


/**
 * 风控数据统计-（简）通话记录统计ServiceImpl
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:53:19

 */
 
@Service("zx91ReqLogService")
public class Zx91ReqLogServiceImpl extends BaseServiceImpl<Zx91ReqLog, Long> implements Zx91ReqLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(Zx91ReqLogServiceImpl.class);
   
    @Resource
    private Zx91ReqLogMapper zx91ReqLogMapper;

	@Override
	public BaseMapper<Zx91ReqLog, Long> getMapper() {
		return zx91ReqLogMapper;
	}
	
}