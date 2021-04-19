package com.ajaya.cashloan.core.mapper;

import java.util.List;

import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.core.domain.SysLocationPincodeConfig;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;

/**
 * 城市邮编配置表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-09-05 15:25:01
 */
@RDBatisDao
public interface SysLocationPincodeConfigMapper extends BaseMapper<SysLocationPincodeConfig, Long> {

	List<String> findPincodesByStateAndDistrict(@Param("state")String state, @Param("district")String district);

    

}
