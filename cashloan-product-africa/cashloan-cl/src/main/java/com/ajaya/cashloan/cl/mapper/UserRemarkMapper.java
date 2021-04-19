package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.UserRemark;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 本人备注记录表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-11 21:32:04
 */
@RDBatisDao
public interface UserRemarkMapper extends BaseMapper<UserRemark, Long> {

	UserRemark findLast(@Param("userId") Long userId);

}
