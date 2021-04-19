package com.ajaya.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.UserAuth;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.cl.model.UserAuthModel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 用户Dao
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-21 13:42:44
 */
@RDBatisDao
public interface UserAuthMapper extends BaseMapper<UserAuth,Long> {

	List<UserAuthModel> listUserAuthModel(Map<String, Object> params);

	int updateByUserId(Map<String, Object> paramMap);
	
	UserAuth findByUserId(@Param("userId")Long userId);

	Map<String,Object> executeSql(Map<String,Object> paramMap);
}
