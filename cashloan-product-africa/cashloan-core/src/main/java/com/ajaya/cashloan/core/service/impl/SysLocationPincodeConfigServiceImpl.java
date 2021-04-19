package com.ajaya.cashloan.core.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.core.domain.SysLocationPincodeConfig;
import com.ajaya.cashloan.core.service.SysLocationPincodeConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.mapper.SysLocationPincodeConfigMapper;


/**
 * 城市邮编配置表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-09-05 15:25:01
 */
 
@Service("sysLocationPincodeConfigService")
public class SysLocationPincodeConfigServiceImpl extends BaseServiceImpl<SysLocationPincodeConfig, Long> implements SysLocationPincodeConfigService {
	
    private static final Logger logger = LoggerFactory.getLogger(SysLocationPincodeConfigServiceImpl.class);
   
    @Resource
    private SysLocationPincodeConfigMapper sysLocationPincodeConfigMapper;

	@Override
	public BaseMapper<SysLocationPincodeConfig, Long> getMapper() {
		return sysLocationPincodeConfigMapper;
	}
	
}