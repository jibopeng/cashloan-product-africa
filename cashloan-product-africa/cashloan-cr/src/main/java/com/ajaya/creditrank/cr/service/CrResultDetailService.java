package com.ajaya.creditrank.cr.service;

import java.util.List;
import java.util.Map;

import com.ajaya.creditrank.cr.domain.CrResultDetail;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.creditrank.cr.model.CrResultDetailModel;

/**
 * 评分结果Service
 * 
 * @author ctt
 * @version 1.0.0
 * @date 2017-01-05 16:46:34

 */
public interface CrResultDetailService extends BaseService<CrResultDetail, Long>{

	Page<CrResultDetail> page(Map<String, Object> secreditrankhMap, int current,int pageSize);

	List<CrResultDetailModel> listDetailTree(Long resultId);

	List<CrResultDetail> listInfo(Long cardId);
}
