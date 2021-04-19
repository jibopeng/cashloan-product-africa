package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UrgeRepayExtend;
import com.ajaya.cashloan.cl.mapper.UrgeRepayExtendMapper;
import com.ajaya.cashloan.cl.service.UrgeRepayExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 催收扩展表ServiceImpl
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-12-02 23:24:12
 */
 
@Service("urgeRepayExtendService")
public class UrgeRepayExtendServiceImpl extends BaseServiceImpl<UrgeRepayExtend, Long> implements UrgeRepayExtendService {
	
    private static final Logger logger = LoggerFactory.getLogger(UrgeRepayExtendServiceImpl.class);
   
    @Resource
    private UrgeRepayExtendMapper urgeRepayExtendMapper;

	@Override
	public BaseMapper<UrgeRepayExtend, Long> getMapper() {
		return urgeRepayExtendMapper;
	}
	
}