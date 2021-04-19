package com.ajaya.creditrank.cr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.creditrank.cr.domain.CreditType;
import com.ajaya.creditrank.cr.mapper.CreditTypeMapper;
import com.ajaya.creditrank.cr.model.CreditRatingModel;
import com.ajaya.creditrank.cr.model.CreditTypeModel;
import com.ajaya.creditrank.cr.service.CreditTypeService;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 额度类型管理ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-01-18 16:43:13

 */
 
@Service("creditTypeService")
public class CreditTypeServiceImpl extends BaseServiceImpl<CreditType, Long> implements CreditTypeService {
	
   
    @Resource
    private CreditTypeMapper creditTypeMapper;


	@Override
	public BaseMapper<CreditType, Long> getMapper() {
		return creditTypeMapper;
	}

	@Override
	public List<CreditTypeModel> findAllCreditType() {
		
		List<CreditType> list = creditTypeMapper.listSelective(null);
		List<CreditTypeModel> rtList = new ArrayList<CreditTypeModel>();
		if(list != null && !list.isEmpty()){
			for(int i=0;i<list.size();i++){
				CreditTypeModel info = creditTypeMapper.findCreditTypeInfo(list.get(i));
				rtList.add(info);
			}
		}
		return rtList;
	}

	@Override
	public CreditTypeModel findCreditTypeInfo(CreditType creditType) {
		if(creditType!=null && creditType.getId()!=null){
			return creditTypeMapper.findCreditTypeInfo(creditType);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Map<Long, String>> findUnUsedBorrowType() {
		return creditTypeMapper.findUnUsedBorrowType();
	}

	@Override
	public List<CreditRatingModel> findEditBorrowType(Long typeId) {
		return creditTypeMapper.findEditBorrowType(typeId);
	}

	@Override
	public List<CreditType> findCreditType(Map<String, Object> params) {
		return creditTypeMapper.listSelective(params);
	}
	
}