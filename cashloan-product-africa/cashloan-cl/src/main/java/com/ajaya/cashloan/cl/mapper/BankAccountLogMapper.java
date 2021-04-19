package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.BankAccountLog;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.HashMap;

/**
 * '银行账户日志Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-20 18:15:45
 */
@RDBatisDao
public interface BankAccountLogMapper extends BaseMapper<BankAccountLog, Long> {

    /**
     * 查找已经成功解析出来的日志
     * @param param 参数集合
     * @return 请求日志
     */
    BankAccountLog findSelectiveNew(HashMap<String, String> param);
}
