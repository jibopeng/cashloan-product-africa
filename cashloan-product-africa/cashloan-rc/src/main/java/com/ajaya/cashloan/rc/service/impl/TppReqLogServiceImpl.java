package com.ajaya.cashloan.rc.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.domain.TppReqLog;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.rc.mapper.TppReqLogMapper;
import com.ajaya.cashloan.rc.service.TppReqLogService;


/**
 * 第三方征信请求记录ServiceImpl
 * 
 * @author zlh
 * @version 1.0.0
 * @date 2017-03-20 13:50:34
 */
 
@Service("tppReqLogService")
public class TppReqLogServiceImpl extends BaseServiceImpl<TppReqLog, Long> implements TppReqLogService {
   
    @Resource
    private TppReqLogMapper tppReqLogMapper;

	@Override
	public BaseMapper<TppReqLog, Long> getMapper() {
		return tppReqLogMapper;
	}

	@Override
	public int modifyTppReqLog(TppReqLog log) {
		return tppReqLogMapper.modifyTppReqLog(log);
	}
	
	@Override
	public TppReqLog findSelective(Map<String, Object> params) {
		return tppReqLogMapper.findSelective(params);
	}
	
}