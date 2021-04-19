package com.ajaya.cashloan.cl.service;


import com.ajaya.cashloan.cl.domain.UserLoginVersionLog;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * '用户登录版本记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-17 12:45:56
 */
public interface UserLoginVersionLogService extends BaseService<UserLoginVersionLog, Long> {
    /**
     * 插入用户登录版本记录表
     * @param userId 用户id
     * @param versionNumber 用户id
     * @param appFlag app标识
     */
    void saveUserLoginInfo(String userId, String versionNumber, String appFlag);

}
