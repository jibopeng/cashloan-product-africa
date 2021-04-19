package com.ajaya.creditrank.cr.mapper;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.creditrank.cr.domain.BorrowTypeCard;

/**
 * 评分卡类型绑定表Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-01-12 10:50:10
 */
@RDBatisDao
public interface BorrowTypeCardMapper extends BaseMapper<BorrowTypeCard,Long> {

    

}
