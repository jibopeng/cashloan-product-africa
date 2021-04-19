package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.PayStackRecipient;
import com.ajaya.cashloan.cl.mapper.PayStackRecipientMapper;
import com.ajaya.cashloan.cl.service.PayStackRecipientService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * PayStack放款联系人ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-28 17:15:31
 */
 
@Service("payStackRecipientService")
public class PayStackRecipientServiceImpl extends BaseServiceImpl<PayStackRecipient, Long> implements PayStackRecipientService {
	
    private static final Logger logger = LoggerFactory.getLogger(PayStackRecipientServiceImpl.class);
   
    @Resource
    private PayStackRecipientMapper payStackRecipientMapper;

	@Override
	public BaseMapper<PayStackRecipient, Long> getMapper() {
		return payStackRecipientMapper;
	}
	
}