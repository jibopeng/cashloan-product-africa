package com.ajaya.cashloan.core.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.model.UserProtocolModel;
import com.ajaya.cashloan.core.model.ManagerUserModel;


/**
 * 用户详情表Service
 *
 * @author yanzhiqiang
 * @since 2020-02-25 11:08:04
 */
public interface UserBaseInfoService extends BaseService<UserBaseInfo, Long> {

    /**
     * 据userId查询用户基本信息
     *
     * @param userId 用户id
     * @return UserBaseInfo
     */
    UserBaseInfo findByUserId(Long userId);

    /**
     * 据条件查询用户基本信息
     *
     * @param paramMap 条件查询
     * @return UserBaseInfo
     */
    UserBaseInfo findSelective(Map<String, Object> paramMap);

    List<Map<String, Object>> getDictsCache(String type);

    /**
     * 查询信息
     *
     * @param userId 用户id
     * @return ManagerUserModel
     */
    ManagerUserModel getBaseModelByUserId(Long userId);

    /**
     * 添加取现黑名单
     *
     * @param id    userId
     * @param state 更新状态
     * @return 更新数
     */
    int updateState(long id, String state);

    /**
     * 修改用户基本信息
     *
     * @param paramMap 条件更新
     * @return boolean
     */
    boolean updateSelective(Map<String, Object> paramMap);


    /**
     * 获取用户协议相关信息
     *
     * @param userId 用户【表情】
     * @return 基本信息
     */
    UserProtocolModel getBaseDateByUserId(long userId);

    /**
     * 保存个人信息
     *
     * @param firstName  第一个名字
     * @param middleName 中间名字
     * @param surName    最后一个名字
     * @param birthDate  生日
     * @param bvnNumber  bvn 号码
     * @param email      emial
     * @param gender     性别
     * @param userId     用户id
     * @param registerCoordinate 经纬度
     *@param registerAddr 注册地址
     * @return 是否成功
     */

    Boolean personalInfoAuth(String firstName, String middleName, String surName, String birthDate, String bvnNumber, String email, String gender, String userId, String registerCoordinate, String registerAddr);

    /**
     * 详细信息保存
     *
     * @param marital                  婚姻状态(必传)
     * @param childrenNumber           孩子数量(必传)
     * @param educationalQualification 学历(必传)
     * @param occupation               职位(必传)
     * @param salarySource             薪资发放方式(必传)
     * @param salaryRange              工资范围(必传)
     * @param loanPurpose              借款目的(必传)
     * @param accommodationType        居住类型(必传)
     * @param stateAddr                邦信息
     * @param homeAddress              家庭住址
     * @param userId                   用户id(必传)
     * @return 是否验证成功
     */
    Boolean basicInfoAuth(String homeAddress, String stateAddr, String marital, String accommodationType, String occupation, String salaryRange, String educationalQualification, String userId, String childrenNumber, String loanPurpose, String salarySource);
}
