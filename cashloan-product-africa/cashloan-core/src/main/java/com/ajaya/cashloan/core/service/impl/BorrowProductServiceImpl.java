package com.ajaya.cashloan.core.service.impl;

import javax.annotation.Resource;

import com.ajaya.cashloan.core.domain.BorrowProduct;
import com.ajaya.cashloan.core.mapper.BorrowProductMapper;
import com.ajaya.cashloan.core.service.BorrowProductService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * '产品信息配置表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 10:50:37
 */
 
@Service("borrowProductService")
public class BorrowProductServiceImpl extends BaseServiceImpl<BorrowProduct, Long> implements BorrowProductService {
	
    private static final Logger logger = LoggerFactory.getLogger(BorrowProductServiceImpl.class);
   
    @Resource
    private BorrowProductMapper borrowProductMapper;

	@Override
	public BaseMapper<BorrowProduct, Long> getMapper() {
		return borrowProductMapper;
	}

	@Override
	public List<BorrowProduct> findProductList() {

		HashMap<String, Object> param = new HashMap<>();
		param.put("status",1);
		return borrowProductMapper.listSelective(param);
	}
}