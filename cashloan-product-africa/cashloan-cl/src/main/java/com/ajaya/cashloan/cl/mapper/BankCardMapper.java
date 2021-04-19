package com.ajaya.cashloan.cl.mapper;


import com.ajaya.cashloan.cl.domain.BankCard;
import com.ajaya.cashloan.cl.model.BankCardModel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.HashMap;
import java.util.List;

/**
 * 银行卡记录表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-27 15:43:16
 */
@RDBatisDao
public interface BankCardMapper extends BaseMapper<BankCard, Long> {

    /**
     * 将该用户的所有银行卡设置为未绑定状态
     * @param userId id
     */
    void setNoBinding(Long userId);

    /**
     * 拉取银行卡列表
     * @param userId 用户id
     * @return list
     */
    List<BankCardModel> getBankCardListByUserId(Long userId);
}
