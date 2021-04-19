package com.ajaya.cashloan.cl.service;

import java.util.Map;

import com.ajaya.cashloan.cl.domain.UserAuth;
import com.ajaya.cashloan.cl.model.UserAuthModel;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;


/**
 * 用户认证信息表Service
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @since 2020-02-25 11:18:11
 */
public interface UserAuthService extends BaseService<UserAuth, Long> {
    /**
     * 获取用户认证信息
     *
     * @param paramMap 条件map
     * @return 返回用户认证信息
     */
    UserAuth getUserAuth(Map<String, Object> paramMap);

    /**
     * 根据用户id更新认证状态
     *
     * @param paramMap 条件map
     * @return 返回更新数
     */
    Integer updateByUserId(Map<String, Object> paramMap);

    /**
     * 用户认证列表
     *
     * @param params      条件map
     * @param currentPage 当前页
     * @param pageSize    当前页大小
     * @return list UserAuthModel 分页列表
     */
    Page<UserAuthModel> listUserAuth(Map<String, Object> params, int currentPage,
                                     int pageSize);

    /**
     * 根据用户id获取认证信息
     *
     * @param userId 用户id
     * @return 用户认证信息
     */
    UserAuth findSelective(long userId);

    /**
     * 获取认证状态
     *
     * @param paramMap 参数集合
     * @return 返回集合
     */
    Map<String, Object> getAuthState(Map<String, Object> paramMap);
}
