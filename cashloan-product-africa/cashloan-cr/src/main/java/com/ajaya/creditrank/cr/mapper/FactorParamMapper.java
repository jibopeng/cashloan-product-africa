package com.ajaya.creditrank.cr.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.creditrank.cr.model.FactorParamModel;
import com.ajaya.creditrank.cr.domain.FactorParam;

/**
 * 评分因子参数Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-01-05 11:13:30

 */
@RDBatisDao
public interface FactorParamMapper extends BaseMapper<FactorParam,Long> {

	/**
	 * 查询列表返回FactorParamModel
	 * @param param
	 * @return
	 */
	List<FactorParamModel> listSelect(Map<String, Object> param);

	/**
	 * 根据主键删除
	 * @param parseLong
	 * @return
	 */
	int deleteSelective(long id);

	/**
	 * 计算最高因子参数分值
	 * @param factorId 
	 * @return
	 */
	int findMaxScore(long factorId);

}
