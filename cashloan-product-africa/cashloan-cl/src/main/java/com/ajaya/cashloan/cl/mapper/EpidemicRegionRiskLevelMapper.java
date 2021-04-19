package com.ajaya.cashloan.cl.mapper;


import com.ajaya.cashloan.cl.domain.EpidemicRegionRiskLevel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * '城市疫情区域危险等级表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-05-08 16:09:44
 */
@RDBatisDao
public interface EpidemicRegionRiskLevelMapper extends BaseMapper<EpidemicRegionRiskLevel, Long> {

    /**
     *
     * 根据邦名称查询所有高危城市名称（城市危险等级为红色和黄色为高危城市等级）
     * @param state 邦名称
     * @return 该邦下的高危城市名称
     */
    List<EpidemicRegionRiskLevel> selectRiskCitysByState(String state);
}
