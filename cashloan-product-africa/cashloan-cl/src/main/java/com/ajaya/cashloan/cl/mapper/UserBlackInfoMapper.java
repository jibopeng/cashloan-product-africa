package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.UserBlackInfo;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 黑名单Dao
 * 
 * @author caitt
 * @version 1.0.0
 * @date 2017-07-12 15:23:07
 */
@RDBatisDao
public interface UserBlackInfoMapper extends BaseMapper<UserBlackInfo, Long> {

	UserBlackInfo findByIdNo(@Param("idNo")String idNo);
	
	int deleteByID(Long id);
}
