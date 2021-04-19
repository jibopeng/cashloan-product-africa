package com.ajaya.cashloan.cl.mapper;

import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

import java.util.Map;


/**
 * 用户设备信息表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-17 17:32:05
 */
@RDBatisDao
public interface UserEquipmentInfoMapper extends BaseMapper<UserEquipmentInfo,Long> {

    /**
     * 查询同一个imei地址注册数
     * @param imei imei地址
     * @return 返回同一个mac地址注册数
     */
    int findRegisterSameImei(String imei);

    /**
     * 查找最后一个用户的设备信息
     * @param paramMap 查询条件
     * @return 设备信息
     */
    UserEquipmentInfo findLastUserEquipment(Map<String, Object> paramMap);
}
