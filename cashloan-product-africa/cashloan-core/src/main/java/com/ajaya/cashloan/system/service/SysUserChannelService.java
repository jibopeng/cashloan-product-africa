package com.ajaya.cashloan.system.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.system.domain.SysUserChannel;

/**
 * 系统渠道用户表Service
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-11-02 15:41:44
 */
public interface SysUserChannelService extends BaseService<SysUserChannel, Long>{
	
	/**
	 * 根据user_id，返回对象集合
	 */
	public List<SysUserChannel> listSelective(Map<String, Object> paramMap);
	

}
