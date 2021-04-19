package com.ajaya.cashloan.cl.service.impl;

import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.cl.mapper.UserEquipmentInfoMapper;
import com.ajaya.cashloan.cl.service.AdJustEnventMonitoService;
import com.ajaya.cashloan.core.common.util.AdJustEnventMonitorUtils;
import com.ajaya.cashloan.core.common.util.DateUtils;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * 功能说明：用于adjust事件统计
 *
 * @author yanzhiqiang
 * @since 2020-07-16 22:21
 **/
@Service
public class AdJustEnventMonitoServiceImpl implements AdJustEnventMonitoService {
    private static final Logger logger = LoggerFactory.getLogger(AdJustEnventMonitoServiceImpl.class);

    @Resource
    private UserEquipmentInfoMapper userEquipmentInfoMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;

    @Override
    public void adjustEnventMonito(Borrow borrow, int type) {

        try {
            Long userId = borrow.getUserId();
            //判断是否为当天注册，并且为新贷放款，才上报
            boolean flag = isNewBorrow(borrow, userId);
            HashMap<String, Object> hashMap = new HashMap<>(8);
            hashMap.put("userId", userId);
            UserEquipmentInfo equipmentInfo = userEquipmentInfoMapper.findSelective(hashMap);

            if (!(equipmentInfo.getAndroidId() == null && equipmentInfo.getGpsadId() == null)) {
                if (type == 2 && flag) {
                    logger.info("订单 {} adjust上传事件:{}",borrow.getId(),flag);
                    AdJustEnventMonitorUtils.adJustEnvent(AdJustEnventMonitorUtils.APPLYCATION_ADJSUT, equipmentInfo.getAndroidId(), equipmentInfo.getGpsadId());
                    //申请通过
                } else if (type == 3 && flag) {
                    logger.info("订单 {} adjust上传事件:{}",borrow.getId(),flag);
                    AdJustEnventMonitorUtils.adJustEnvent(AdJustEnventMonitorUtils.APPLAYCATION_PASS_ADJSUT, equipmentInfo.getAndroidId(), equipmentInfo.getGpsadId());
                    //放款
                } else if (type == 4 && flag) {
                    logger.info("订单 {} adjust上传事件:{}",borrow.getId(),flag);
                    AdJustEnventMonitorUtils.adJustEnvent(AdJustEnventMonitorUtils.PAYMENT_OUT_ADJSUT, equipmentInfo.getAndroidId(), equipmentInfo.getGpsadId());
                }else {
                    logger.info("订单 {} adjust上传事件:{}",borrow.getId(),flag);
                }
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
}
