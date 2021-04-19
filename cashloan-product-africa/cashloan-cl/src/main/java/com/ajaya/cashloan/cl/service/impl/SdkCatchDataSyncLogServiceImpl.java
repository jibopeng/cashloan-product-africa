package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.SdkCatchDataSyncLog;
import com.ajaya.cashloan.cl.mapper.SdkCatchDataSyncLogMapper;
import com.ajaya.cashloan.cl.service.SdkCatchDataSyncLogService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * epoch抓取数据同步回调日志表ServiceImpl
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-14 12:21:01
 */

@Service("sdkCatchDataSyncLogService")
public class SdkCatchDataSyncLogServiceImpl extends BaseServiceImpl<SdkCatchDataSyncLog, Long> implements SdkCatchDataSyncLogService {

    private static final Logger logger = LoggerFactory.getLogger(SdkCatchDataSyncLogServiceImpl.class);

    @Resource
    private SdkCatchDataSyncLogMapper sdkCatchDataSyncLogMapper;

    @Override
    public BaseMapper<SdkCatchDataSyncLog, Long> getMapper() {
        return sdkCatchDataSyncLogMapper;
    }

	@Override
	public Boolean needRequestSdkModel(Long userId, Long borrowId) {
		String value = Global.getValue("need_request_sdk_mode");
		String[] split = value.split(",");
		Boolean result = true;
		for (String type : split) {
			Integer count = sdkCatchDataSyncLogMapper.checkFinishedByType(borrowId, type);
			if(count > 0) {
				logger.info("borrowId {} , type {} 已经抓取完毕." , borrowId , type);
			} else {
				logger.info("borrowId {} , type {} 没有抓取完毕." , borrowId , type);
				result = false;
				break;
			}
		}
		return result;
	}

}