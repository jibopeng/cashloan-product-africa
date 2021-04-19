package com.ajaya.cashloan.rc.mapper;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.rc.domain.SimpleBorrowCount;

/**
 * 风控数据统计-（简）借款统计Dao
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:12:18

 */
@RDBatisDao
public interface SimpleBorrowCountMapper extends BaseMapper<SimpleBorrowCount, Long> {

    /**
     * 借款人有逾期30天以上已还借款数
     * @param userId
     * @return
     */
    int countOne(long userId);
    
//    /**
//     * 借款人有逾期未还借款数
//     * @param userId
//     * @return
//     */
//    int countTwo(long userId);
//    
//    /**
//     * 借款人有逾期已还借款数
//     * @param userId
//     * @return
//     */
//    int countThree(long userId);
//    
//    /**
//     * 借款人正常还款数
//     * @param userId
//     * @return
//     */
//    int countFour(long userId);

}
