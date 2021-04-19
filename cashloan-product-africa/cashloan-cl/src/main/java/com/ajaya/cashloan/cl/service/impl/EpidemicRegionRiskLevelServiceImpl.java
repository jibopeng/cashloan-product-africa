package com.ajaya.cashloan.cl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.enums.AfricaStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.EpidemicRegionRiskLevel;
import com.ajaya.cashloan.cl.mapper.EpidemicRegionRiskLevelMapper;
import com.ajaya.cashloan.cl.service.EpidemicRegionRiskLevelService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.StringUtil;
/**
 * '城市疫情区域危险等级表ServiceImpl
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-05-08 16:09:44
 */

@Service("epidemicRegionRiskLevelService")
public class EpidemicRegionRiskLevelServiceImpl extends BaseServiceImpl<EpidemicRegionRiskLevel, Long> implements EpidemicRegionRiskLevelService {

    private static final Logger logger = LoggerFactory.getLogger(EpidemicRegionRiskLevelServiceImpl.class);

    @Resource
    private EpidemicRegionRiskLevelMapper epidemicRegionRiskLevelMapper;

    @Override
    public BaseMapper<EpidemicRegionRiskLevel, Long> getMapper() {
        return epidemicRegionRiskLevelMapper;
    }

    
    public static final ArrayList<String> highRisk = new ArrayList<>();

    
    @Override
    public boolean canBorrowByBorrowAddress(Long borrowId, String borrowAddress) {

        logger.info("订单 {} 判断城市是否为禁止放款邦，借款地址为：{}", borrowId, borrowAddress);

        String state = AfricaStateEnum.getStateByBorrowAddress(borrowAddress);
        //地址关联邦失败
        if (StringUtil.isEmpty(state)) {
            logger.info("订单 {} 判断城市是否为禁止放款区域，发现地址关联不到邦信息", borrowId);
            return false;
        }
        List<EpidemicRegionRiskLevel> riskCityList = epidemicRegionRiskLevelMapper.selectRiskCitysByState(state);
        if (riskCityList.size() == 0) {
            logger.info("订单 {} 判断城市是否为禁止放款区域，{} 邦不进行放款申请拦截，可以继续申请", borrowId, state);
            return true;
        }
        for (EpidemicRegionRiskLevel epidemicRegionRiskLevel : riskCityList) {
            //拦截级别判定
            if (epidemicRegionRiskLevel.getLevel()==1){
                logger.info("订单 {} 判断城市是否为禁止放款区域，{} 邦不允许申请，不能继续申请", borrowId, epidemicRegionRiskLevel.getState());
                return false;
            }else {
                if (borrowAddress.contains(", " + epidemicRegionRiskLevel.getCity() + ",")) {
                    logger.info("订单 {}  判断城市是否为禁止放款区域，{} 为禁止申请城市，不能继续申请", borrowId, epidemicRegionRiskLevel.getCity());
                    return false;
                }
            }
        }

        logger.info("订单 {}判断城市是否为禁止放款区域，{}  不属于禁止申请区域，可以进行申请", borrowId, borrowAddress);
        return true;
    }

    /**
     * 判断str1中包含str2的个数
     * @param str1 长字符串
     * @param str2 短字符串
     * @return counter
     */
    private static int countStr(String str1, String str2) {
        int counter = 0;
        while (str1.contains(str2)) {
            counter++;
            str1 = str1.substring(str1.indexOf(str2) + str2.length());
        }
        return counter;
    }

}