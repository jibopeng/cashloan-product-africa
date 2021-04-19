package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.SdkCatchDataFindIndexLog;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * sdk抓取数据findindex控制记录日志表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-24 10:52:14
 */
@RDBatisDao
public interface SdkCatchDataFindIndexLogMapper extends BaseMapper<SdkCatchDataFindIndexLog, Long> {

    /**
     * 根据订单id查找最后一条抓取记录
     * @param borrowId  订单id
     * @return SdkCatchDataFindIndexLog
     */
    SdkCatchDataFindIndexLog findLastLog(@Param("borrowId") Long borrowId);

    /**
     * 根据订单id查找首页抓取次数
     * @param borrowId 订单id
     * @return 抓取次数
     */
    int countByBorrowId(@Param("borrowId") Long borrowId);
}
