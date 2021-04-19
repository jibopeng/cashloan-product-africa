package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.UserBvnLog;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.HashMap;

/**
 * '用户bvn日志表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-15 17:29:07
 */
@RDBatisDao
public interface UserBvnLogMapper extends BaseMapper<UserBvnLog, Long> {

    /**
     * 查找已经成功解析出来的日志
     * @param param 参数集合
     * @return 请求日志
     */
    UserBvnLog findSelectiveNew(HashMap<String, Object> param);
}
