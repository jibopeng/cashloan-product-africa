package com.ajaya.cashloan.rc.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rc.domain.TppReqLog;
import com.ajaya.cashloan.rc.model.TppReqLogModel;

/**
 * 第三方征信请求记录Dao
 * 
 * @author zlh
 * @version 1.0.0
 * @date 2017-03-20 13:50:34

 */
@RDBatisDao
public interface TppReqLogMapper extends BaseMapper<TppReqLog,Long> {

	/**
	 * 根据订单号修改记录
	 * @param log
	 * @return
	 */
	int modifyTppReqLog(TppReqLog log);

	/**
	 * 分页查询
	 * @param searchMap
	 * @return
	 */
	List<TppReqLogModel> page(Map<String, Object> searchMap);

}
