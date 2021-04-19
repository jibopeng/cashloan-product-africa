package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.UserBvn;
import com.ajaya.cashloan.cl.domain.UserBvnLog;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.HashMap;


/**
 * '用户bvn记录表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-15 17:23:05
 */
@RDBatisDao
public interface UserBvnMapper extends BaseMapper<UserBvn, Long> {
    /**
     * 根据用户id查找bvn账号
     * @param userId 用户id
     * @return UserBvnLog
     */
    UserBvn findBvnByUserId(Long userId);
    

}
