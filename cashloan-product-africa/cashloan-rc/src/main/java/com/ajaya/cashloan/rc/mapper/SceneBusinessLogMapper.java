package com.ajaya.cashloan.rc.mapper;


import java.util.List;

import com.ajaya.cashloan.rc.domain.SceneBusinessLog;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 场景与第三方征信接口执行记录
 * @author caitt
 * @version 1.0
 * @date 2017年4月11日上午11:53:19

 */
@RDBatisDao
public interface SceneBusinessLogMapper extends BaseMapper<SceneBusinessLog,Long> {

	/**
	 * 查询未完成的（失败的/需要重新执行的）记录数
	 * @param borrowId
	 * @return
	 */
	int countUnFinishLog(@Param("borrowId") Long borrowId);
	
	
	/**
	 * 查询未完成的（失败的/需要重新执行的）记录
	 * @param borrowId
	 * @return
	 */
	List<SceneBusinessLog> findSceneLogByBorrowId(@Param("borrowId")Long borrowId);
	
	/**
	 * 
	 * @param userId
	 * @param busId
	 * @return
	 */
	SceneBusinessLog findLastExcute(@Param("userId")Long userId,@Param("busId")Long busId);
}
