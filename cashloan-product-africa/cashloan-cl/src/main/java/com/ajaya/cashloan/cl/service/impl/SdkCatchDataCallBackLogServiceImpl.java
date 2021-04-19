package com.ajaya.cashloan.cl.service.impl;

import com.ajaya.cashloan.cl.domain.SdkCatchDataCallBackLog;
import com.ajaya.cashloan.cl.mapper.SdkCatchDataCallBackLogMapper;
import com.ajaya.cashloan.cl.service.SdkCatchDataCallBackLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * epoch抓取数据回调日志表ServiceImpl
 * 
 * @author yzq
 * @version 1.0.0
 * @date 2020-01-08 18:50:33
 */
 
@Service("sdkCatchDataCallBackLogService")
public class SdkCatchDataCallBackLogServiceImpl extends BaseServiceImpl<SdkCatchDataCallBackLog, Long> implements SdkCatchDataCallBackLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(SdkCatchDataCallBackLogServiceImpl.class);
   
    @Resource
    private SdkCatchDataCallBackLogMapper sdkCatchDataCallBackLogMapper;

	@Override
	public BaseMapper<SdkCatchDataCallBackLog, Long> getMapper() {
		return sdkCatchDataCallBackLogMapper;
	}

	@Override
	public Boolean needRequestSdkMode(Long userId, Long borrowId) {
		HashMap<String, Object> params = new HashMap<>(8);
		params.put("borrowId", borrowId);
		params.put("userId", userId);
		params.put("catchDataState", "1");
		List<SdkCatchDataCallBackLog> catchDataSyncLogs = sdkCatchDataCallBackLogMapper.listSelective(params);
		return catchDataSyncLogs.size() > 0;
	}
}