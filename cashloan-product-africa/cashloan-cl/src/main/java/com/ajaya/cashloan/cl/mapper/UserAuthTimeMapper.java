package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.UserAuthTime;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 用户认证时间记录表Dao
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-10-30 15:59:03
 */
@RDBatisDao
public interface UserAuthTimeMapper extends BaseMapper<UserAuthTime, Long> {

	/**
	 * 传入参数是对象,进行更新操作,sql语句中有非空判断
	 * @param userAuthTime
	 */
     void updateSelectiveByObject(UserAuthTime userAuthTime);

}
