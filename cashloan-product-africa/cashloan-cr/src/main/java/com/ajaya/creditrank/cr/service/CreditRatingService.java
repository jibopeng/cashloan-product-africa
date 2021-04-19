package com.ajaya.creditrank.cr.service;

import com.ajaya.cashloan.core.common.exception.CreditException;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.creditrank.cr.domain.CrResult;

/**
 * 信用评级及结果操作
 * @author ctt
 * @version 1.0.0
 * @date 2017年1月6日 上午10:41:22

 */
public interface CreditRatingService extends BaseService<CrResult, Long> {

	/**
	 * 信用自动评分并保存记录
	 * @param userId
	 */
	CrResult saveCreditRating(String consumerNo,Long borrowTypeId) throws CreditException;
}
