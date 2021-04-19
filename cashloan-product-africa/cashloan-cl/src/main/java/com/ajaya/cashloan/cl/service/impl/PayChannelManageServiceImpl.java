package com.ajaya.cashloan.cl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.PayChannelManage;
import com.ajaya.cashloan.cl.mapper.PayChannelManageMapper;
import com.ajaya.cashloan.cl.service.PayChannelManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 支付渠道管理表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-16 17:52:41
 */
 
@Service("payChannelManageService")
public class PayChannelManageServiceImpl extends BaseServiceImpl<PayChannelManage, Long> implements PayChannelManageService {
	
    private static final Logger logger = LoggerFactory.getLogger(PayChannelManageServiceImpl.class);
   
    @Resource
    private PayChannelManageMapper payChannelManageMapper;

	@Override
	public BaseMapper<PayChannelManage, Long> getMapper() {
		return payChannelManageMapper;
	}

	@Override
	public List<PayChannelManage> getAllPayChannel() {
		return payChannelManageMapper.getAllPayChannel();
	}
	
}