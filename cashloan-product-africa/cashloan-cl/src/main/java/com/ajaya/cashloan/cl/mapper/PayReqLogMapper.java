package com.ajaya.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.model.ManagePayReqLogModel;
import com.ajaya.cashloan.cl.domain.PayReqLog;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 支付请求记录Mapper
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017-03-07 16:18:30
 */
@RDBatisDao
public interface PayReqLogMapper extends BaseMapper<PayReqLog,Long> {

	/**
	 * 分页查询
	 * 
	 * @param searchMap
	 * @return
	 */
	List<ManagePayReqLogModel> page(Map<String, Object> searchMap);

	/**
	 * 查询详情
	 * 
	 * @param id
	 * @return
	 */
	ManagePayReqLogModel findDetail(Long id);
}
