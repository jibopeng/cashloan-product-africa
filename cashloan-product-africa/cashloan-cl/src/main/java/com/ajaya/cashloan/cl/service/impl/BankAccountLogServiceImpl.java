package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BankAccountLog;
import com.ajaya.cashloan.cl.mapper.BankAccountLogMapper;
import com.ajaya.cashloan.cl.service.BankAccountLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * '银行账户日志ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-20 18:15:45
 */
 
@Service("bankAccountLogService")
public class BankAccountLogServiceImpl extends BaseServiceImpl<BankAccountLog, Long> implements BankAccountLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(BankAccountLogServiceImpl.class);
   
    @Resource
    private BankAccountLogMapper bankAccountLogMapper;

	@Override
	public BaseMapper<BankAccountLog, Long> getMapper() {
		return bankAccountLogMapper;
	}
	
}