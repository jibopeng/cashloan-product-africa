package com.ajaya.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.UrgeRepayOrderLog;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 催款记录表Dao
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-03-07 14:28:22
 */
@RDBatisDao
public interface UrgeRepayOrderLogMapper extends BaseMapper<UrgeRepayOrderLog,Long> {
	
	/**
	 * 统计数量
	 * @param params
	 * @return
	 */
	int countLog(Map<String, Object> params);
	
	/**
	 * 刘晓亮：mybatis返回增长后的主键
	 * @param urgeRepayOrderLog
	 */
	public void saveReturnId(UrgeRepayOrderLog urgeRepayOrderLog);

	/**
	 * 该根据borrowId查询催收信息
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getCuiShouMsgListByBorrowId(Long id);


}
