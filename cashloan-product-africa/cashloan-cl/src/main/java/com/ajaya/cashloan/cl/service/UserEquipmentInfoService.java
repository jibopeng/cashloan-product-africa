package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.core.common.service.BaseService;


/**
 * 用户设备信息表Service
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-04-17 17:32:05
 */
public interface UserEquipmentInfoService extends BaseService<UserEquipmentInfo, Long>{

	/**
	 * 信息入库
	 * @param uei 用户设备信息
	 * @return 返回更新或者保存个数
	 */
	int saveOrUpdate(UserEquipmentInfo uei);

	/**
	 * 查询
	 * @param userId 用户id
	 * @return UserEquipmentInfo
	 */
	UserEquipmentInfo findSelective(long userId);

	/**
	 * 保存设备指纹
	 * @param loginName 登录名称
	 * @param blackBox 指纹信息
	 */
	void save(String loginName, String blackBox);

	/**
	 * 查询同一个imei地址注册数
	 * @param imei imei地址
	 * @return 返回同一个mac地址注册数
	 */
    int findRegisterSameImei(String imei);
}
