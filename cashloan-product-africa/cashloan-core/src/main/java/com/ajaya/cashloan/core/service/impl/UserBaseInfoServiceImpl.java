package com.ajaya.cashloan.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.core.common.util.DateUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;
import com.ajaya.cashloan.core.model.UserProtocolModel;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.model.ManagerUserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
/**
 * 用户详情表ServiceImpl
 *
 * @author yanzhiqiang
 * @since 2020-02-24 11:08:04
 */

@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl extends BaseServiceImpl<UserBaseInfo, Long> implements UserBaseInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoServiceImpl.class);

    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;


    @Override
    public Boolean basicInfoAuth(String homeAddress, String stateAddr, String marital, String accommodationType,
                                 String occupation, String salaryRange, String educationalQualification, String userId, String childrenNumber, String loanPurpose, String salarySource){
        try {
            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("userId", userId);
            UserBaseInfo info = userBaseInfoMapper.findSelective(paramMap);

            info.setMarital(marital);
            info.setSalary(salaryRange);
            info.setOccupation(occupation);
            info.setUpdateTime(new Date());
            info.setSalaryType(salarySource);
            info.setLoanPurpose(loanPurpose);
            info.setEducation(educationalQualification);
            info.setAccommodationType(accommodationType);
            info.setChildrenNumber(childrenNumber);
            info.setStateAddr(stateAddr);
            info.setLiveAddr(homeAddress);
            userBaseInfoMapper.update(info);
            logger.info("用户 {} 基础信息认证成功！", userId);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("用户 {} 基础信息认证失败！", userId);
            return false;
        }
        return true;
    }

    @Override
    public UserProtocolModel getBaseDateByUserId(long userId) {
        return userBaseInfoMapper.getUserBaseDate(userId);
    }

    @Override
    public Boolean personalInfoAuth(String firstName, String middleName, String surName, String birthDate, String bvnNumber, String email, String gender, String userId, String registerCoordinate, String registerAddr) {
        try {
            Map<String, Object> paramMap = new HashMap<>(8);
            paramMap.put("userId", userId);
            UserBaseInfo info = userBaseInfoMapper.findSelective(paramMap);
            info.setFirstName(firstName);
            info.setLastName(surName);
            info.setMiddleName(middleName);
            //用户名称拼接
            String realName=firstName + (StringUtil.isEmpty(middleName)?"":" "+middleName)+" "+surName;
            info.setRealName(realName);
            info.setEmail(email.trim());
            info.setBvnNo(bvnNumber);
            info.setRegisterCoordinate(registerCoordinate);
            info.setRegisterAddr(registerAddr);
            info.setSex(gender);
            info.setDateOfBirth(birthDate);
            info.setAge(DateUtils.getAgeByBirth(birthDate));
            info.setUpdateTime(new Date());
            userBaseInfoMapper.update(info);
            logger.info("用户 {} 个人信息认证成功！", userId);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println();
            logger.info("用户 {} 个人信息认证失败！", userId);
            return false;
        }
        return true;
    }


    @Override
    public BaseMapper<UserBaseInfo, Long> getMapper() {
        return userBaseInfoMapper;
    }

    @Override
    public UserBaseInfo findByUserId(Long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        UserBaseInfo baseInfo = null;
        try {
            baseInfo = userBaseInfoMapper.findSelective(paramMap);
        } catch (Exception e) {
            logger.error("查询用户基本信息异常", e);
        }

        return baseInfo;
    }

    @Override
    public UserBaseInfo findSelective(Map<String, Object> paramMap) {
        return userBaseInfoMapper.findSelective(paramMap);
    }

    @Override
    public List<Map<String, Object>> getDictsCache(String type) {
        return userBaseInfoMapper.getDictsCache(type);
    }

    @Override
    public ManagerUserModel getBaseModelByUserId(Long userId) {
        return userBaseInfoMapper.getBaseModelByUserId(userId);
    }

    @Override
    public int updateState(long id, String state) {
        int i = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", id);
        UserBaseInfo base = userBaseInfoMapper.findSelective(paramMap);
        if (base != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", base.getId());
            map.put("state", state);
            i = userBaseInfoMapper.updateSelective(map);
        }
        return i;
    }

    @Override
    public boolean updateSelective(Map<String, Object> paramMap) {
        int result = userBaseInfoMapper.updateSelective(paramMap);
        if (result > 0L) {
            return true;
        }
        return false;
    }

}