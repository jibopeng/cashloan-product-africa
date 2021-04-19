package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BankCardLog;
import com.ajaya.cashloan.cl.mapper.BankCardLogMapper;
import com.ajaya.cashloan.cl.service.BankCardLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 银行卡记录表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:46:04
 */
 
@Service("bankCardLogService")
public class BankCardLogServiceImpl extends BaseServiceImpl<BankCardLog, Long> implements BankCardLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(BankCardLogServiceImpl.class);
   
    @Resource
    private BankCardLogMapper bankCardLogMapper;

	@Override
	public BaseMapper<BankCardLog, Long> getMapper() {
		return bankCardLogMapper;
	}
	
}