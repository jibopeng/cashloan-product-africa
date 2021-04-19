package com.ajaya.creditrank.cr.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.creditrank.cr.domain.BorrowTypeCard;
import com.ajaya.creditrank.cr.mapper.BorrowTypeCardMapper;
import com.ajaya.creditrank.cr.service.BorrowTypeCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 评分卡类型绑定表ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-01-12 10:50:10

 */
 
@Service("borrowTypeCardService")
public class BorrowTypeCardServiceImpl extends BaseServiceImpl<BorrowTypeCard, Long> implements BorrowTypeCardService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BorrowTypeCardServiceImpl.class);
   
    @Resource
    private BorrowTypeCardMapper borrowTypeCardMapper;




	@Override
	public BaseMapper<BorrowTypeCard, Long> getMapper() {
		return borrowTypeCardMapper;
	}




	@Override
	public Page<BorrowTypeCard> page(Map<String, Object> secreditrankhMap, int current,
			int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<BorrowTypeCard> list = borrowTypeCardMapper.listSelective(secreditrankhMap);
		return (Page<BorrowTypeCard>)list;
	}




	@Override
	public int save(long borrowTypeId, String borrowTypeName, long cardId,
			String cardName) {
		BorrowTypeCard btc = new BorrowTypeCard();
		btc.setBorrowTypeId(borrowTypeId);
		btc.setBorrowTypeName(borrowTypeName);
		btc.setCardId(cardId);
		btc.setCardName(cardName);
		btc.setAddTime(new Date());
		return borrowTypeCardMapper.save(btc);
	}




	@Override
	public int update(long id, long borrowTypeId, String borrowTypeName,
			long cardId, String cardName) {
		Map<String,Object> updateMap = new HashMap<String, Object>();
		updateMap.put("id", id);
		updateMap.put("borrowTypeId", borrowTypeId);
		updateMap.put("borrowTypeName", borrowTypeName);
		updateMap.put("cardId", cardId);
		updateMap.put("cardName", cardName);
		return borrowTypeCardMapper.updateSelective(updateMap);
	}
	
}