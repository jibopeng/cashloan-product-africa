package com.ajaya.cashloan.cl.mapper;


import com.ajaya.cashloan.cl.domain.BankCardLog;
import com.ajaya.cashloan.cl.model.BankCardModel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * 银行卡记录表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:46:04
 */
@RDBatisDao
public interface BankCardLogMapper extends BaseMapper<BankCardLog, Long> {
}
