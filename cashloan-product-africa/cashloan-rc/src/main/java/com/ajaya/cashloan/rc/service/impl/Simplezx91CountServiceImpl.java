package com.ajaya.cashloan.rc.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.rc.mapper.Simplezx91CountMapper;
import com.ajaya.cashloan.rc.domain.Simplezx91Count;
import com.ajaya.cashloan.rc.service.Simplezx91CountService;


/**
 * 风控数据统计-（简）通话记录统计ServiceImpl
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:53:59
 */
 
@Service("simplezx91CountService")
public class Simplezx91CountServiceImpl extends BaseServiceImpl<Simplezx91Count, Long> implements Simplezx91CountService {
	
    private static final Logger logger = LoggerFactory.getLogger(Simplezx91CountServiceImpl.class);
   
    @Resource
    private Simplezx91CountMapper simplezx91CountMapper;

	@Override
	public BaseMapper<Simplezx91Count, Long> getMapper() {
		return simplezx91CountMapper;
	}
	
}