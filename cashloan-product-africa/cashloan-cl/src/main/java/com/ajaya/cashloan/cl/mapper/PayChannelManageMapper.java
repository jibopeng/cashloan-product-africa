package com.ajaya.cashloan.cl.mapper;

import java.util.List;

import com.ajaya.cashloan.cl.domain.PayChannelManage;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 支付渠道管理表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-16 17:52:41
 */
@RDBatisDao
public interface PayChannelManageMapper extends BaseMapper<PayChannelManage, Long> {

	//按照优先级获取所有有效账户
	List<PayChannelManage> getAllPayChannel();

}
