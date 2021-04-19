package com.ajaya.cashloan.cl.service;

import java.util.List;

import com.ajaya.cashloan.cl.domain.PayChannelManage;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 支付渠道管理表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-16 17:52:41
 */
public interface PayChannelManageService extends BaseService<PayChannelManage, Long>{

	List<PayChannelManage> getAllPayChannel();

}
