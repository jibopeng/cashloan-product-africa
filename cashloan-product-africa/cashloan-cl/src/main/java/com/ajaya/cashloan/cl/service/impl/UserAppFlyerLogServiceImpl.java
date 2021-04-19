package com.ajaya.cashloan.cl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.UserAppFlyerLog;
import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.cl.mapper.UserAppFlyerLogMapper;
import com.ajaya.cashloan.cl.mapper.UserEquipmentInfoMapper;
import com.ajaya.cashloan.cl.service.UserAppFlyerLogService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.AppFlyerUtils;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.User;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.mapper.UserMapper;

/**
 * AF统计记表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-01 10:56:25
 */

@Service("userAppFlyerLogService")
public class UserAppFlyerLogServiceImpl extends BaseServiceImpl<UserAppFlyerLog, Long>
		implements UserAppFlyerLogService {

	private static final Logger logger = LoggerFactory.getLogger(UserAppFlyerLogServiceImpl.class);

	@Resource
	private UserAppFlyerLogMapper userAppFlyerLogMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserEquipmentInfoMapper userEquipmentInfoMapper;

	@Override
	public BaseMapper<UserAppFlyerLog, Long> getMapper() {
		return userAppFlyerLogMapper;
	}

	@Override
	public Integer save(String customerUserId, String eventName,String responseJson,
			String eventTime) {
		UserAppFlyerLog userAppFlyerLog = new UserAppFlyerLog();
		userAppFlyerLog.setCustomerUserId(customerUserId);
		userAppFlyerLog.setEventName(eventName);
		userAppFlyerLog.setResponseJson(responseJson);
		userAppFlyerLog.setEventTime(eventTime);
		userAppFlyerLog.setCreateTime(new Date());
		return userAppFlyerLogMapper.save(userAppFlyerLog);
	}

	@Override
	public void appFlyerEnventMonito(Borrow borrow, int type) {
		try {
			Long userId = borrow.getUserId();
			// 判断是否为当天注册，并且为新贷放款，才上报
			boolean flag = isNewBorrow(borrow, userId);
			HashMap<String, Object> hashMap = new HashMap<>(8);
			hashMap.put("userId", userId);
			UserEquipmentInfo equipmentInfo = userEquipmentInfoMapper.findSelective(hashMap);
			String result = null;
			User user = userMapper.findByPrimary(userId);
			if (!(equipmentInfo.getGpsadId() == null)) {
				String customerUserId = user.getUuid();
				String eventName = null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
				String eventTime = formatter.format(new Date());
				 if (type == 2 && flag) {
					logger.info("订单 {} appFlyer上传申请事件:{}", borrow.getId(), flag);
					eventName = AppFlyerUtils.APPLYCATION_APPFLYER;
					result = AppFlyerUtils.appFlyerEnvent(equipmentInfo.getGpsadId(), user.getUuid(), AppFlyerUtils.APPLYCATION_APPFLYER, eventTime);
					// 申请通过
				} else if (type == 3 && flag) {
					logger.info("订单 {} appFlyer上传通过事件:{}", borrow.getId(), flag);
					eventName = AppFlyerUtils.APPLAYCATION_PASS_APPFLYER;
					result = AppFlyerUtils.appFlyerEnvent(equipmentInfo.getGpsadId(), user.getUuid(), AppFlyerUtils.APPLAYCATION_PASS_APPFLYER, eventTime);
					// 放款
				} else if (type == 4 && flag) {
					logger.info("订单 {} appFlyer上传放款事件:{}", borrow.getId(), flag);
					eventName = AppFlyerUtils.PAYMENT_OUT_APPFLYER;
					result = AppFlyerUtils.appFlyerEnvent(equipmentInfo.getGpsadId(), user.getUuid(), AppFlyerUtils.PAYMENT_OUT_APPFLYER, eventTime);
				} else {
					logger.info("订单 {} appFlyer上传事件:{}", borrow.getId(), flag);
				}
				Integer save = save(customerUserId, eventName, result, eventTime);
				logger.info("订单 {} appFlyer上传事件:{}, 保存结果 {}", borrow.getId(), type, save);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isNewBorrow(Borrow borrow, Long userId) {
		boolean flag = false;
		UserBaseInfo userBaseInfo = userBaseInfoMapper.findByUserId(userId);
		long current = System.currentTimeMillis();
		long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
		long registeTime = userBaseInfo.getCreateTime().getTime();
		if (registeTime >= zero && !"0".equals(borrow.getUserType())) {
			flag = true;
		}
		return flag;
	}

	@Override
	public void appFlyerRegistEnventMonito(String gpsadId, String uuid) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String eventTime = formatter.format(new Date());
		String result = AppFlyerUtils.appFlyerEnvent(gpsadId, uuid, AppFlyerUtils.RESITER_APPFLYER, eventTime);
		Integer save = save(uuid, AppFlyerUtils.RESITER_APPFLYER, result, eventTime);
		logger.info("uuid {} appFlyer上传事件:{}, 保存结果 {}", uuid, AppFlyerUtils.RESITER_APPFLYER, save);
	}
}