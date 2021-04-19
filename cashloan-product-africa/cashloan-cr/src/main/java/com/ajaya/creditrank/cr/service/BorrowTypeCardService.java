package com.ajaya.creditrank.cr.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.creditrank.cr.domain.BorrowTypeCard;

/**
 * 评分卡类型绑定表Service
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-01-12 10:50:10

 */
public interface BorrowTypeCardService extends BaseService<BorrowTypeCard, Long>{

	/**
	 * 分页查询
	 * @param secreditrankhMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<BorrowTypeCard> page(Map<String, Object> secreditrankhMap, int current, int pageSize);

	/**
	 * 新增
	 * @param borrowTypeId
	 * @param borrowTypeName
	 * @param cardId
	 * @param cardName
	 * @return
	 */
	int save(long borrowTypeId, String borrowTypeName, long cardId, String cardName);

	/**
	 * 修改
	 * @param id
	 * @param borrowTypeId
	 * @param borrowTypeName
	 * @param cardId
	 * @param cardName
	 * @return
	 */
	int update(long id, long borrowTypeId, String borrowTypeName, long cardId,
			String cardName);

}
