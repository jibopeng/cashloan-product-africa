package com.ajaya.cashloan.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.system.service.SysUserChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.system.domain.SysUserChannel;
import com.ajaya.cashloan.system.mapper.SysUserChannelMapper;


/**
 * 系统渠道用户表ServiceImpl
 * 
 */
 
@Service("sysUserChannelService")
public class SysUserChannelServiceImpl extends BaseServiceImpl<SysUserChannel, Long> implements SysUserChannelService {
	
    private static final Logger logger = LoggerFactory.getLogger(SysUserChannelServiceImpl.class);
   
    @Resource
    private SysUserChannelMapper sysUserChannelMapper;

	@Override
	public BaseMapper<SysUserChannel, Long> getMapper() {
		return sysUserChannelMapper;
	}

	@Override
	public List<SysUserChannel> listSelective(Map<String, Object> paramMap) {
		List<SysUserChannel> listSelective = sysUserChannelMapper.listSelective(paramMap);
		return listSelective;
	}
	
	
}