package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.BankAccount;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.HashMap;
import java.util.List;

/**
 * 银行账号信息表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-04-20 14:49:23
 */
@RDBatisDao
public interface BankAccountMapper extends BaseMapper<BankAccount, Long> {

    /**
     * 将该用户的所有银行卡设置为未绑定状态
     * @param paramMap 参数集合
     */
    void setNoBinding(HashMap<String, Object> paramMap);

}
