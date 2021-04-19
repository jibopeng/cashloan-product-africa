package com.ajaya.cashloan.cl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.mapper.UserAuthMapper;
import com.ajaya.cashloan.cl.model.UserAuthModel;
import com.ajaya.cashloan.cl.service.UserAuthService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;

/**
 * 用户认证信息表ServiceImpl
 *
 * @author yanzhiqiang
 * @since 2020-02-25 11:18:17
 */

@Service("userAuthService")
public class UserAuthServiceImpl extends BaseServiceImpl<UserAuth, Long> implements UserAuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Resource
    private UserAuthMapper userAuthMapper;

    @Override
    public BaseMapper<UserAuth, Long> getMapper() {
        return userAuthMapper;
    }

    @Override
    public UserAuth getUserAuth(Map<String, Object> paramMap) {
        return userAuthMapper.findSelective(paramMap);
    }

    @Override
    public Integer updateByUserId(Map<String, Object> paramMap) {
        logger.info("用户 {} 更新认证状态成功",paramMap.get("userId"));
        return userAuthMapper.updateByUserId(paramMap);
    }

    @Override
    public Page<UserAuthModel> listUserAuth(Map<String, Object> params,
                                            int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<UserAuthModel> list = userAuthMapper.listUserAuthModel(params);
        return (Page<UserAuthModel>) list;
    }

    @Override
    public UserAuth findSelective(long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return userAuthMapper.findSelective(map);
    }

    @Override
    public Map<String, Object> getAuthState(Map<String, Object> paramMap) {
        //基础必填项数量
        int qualifiedCount = 4;
        //拼接整个sql
        String sql = "	SELECT ("
                + "IF (personal_info_state = 30, 1, 0) +"
                + "IF (basic_info_state = 30, 1, 0) +"
                + "IF (contact_state = 30, 1, 0) + "
                + "IF (bank_account_state = 30, 1, 0) "
                + ") AS result,"
                + Global.getValue("auth_total") + " AS total,"
                + "IF ("
                + "(IF (personal_info_state = 30, 1, 0) +"
                + "IF (basic_info_state = 30, 1, 0) +"
                + "IF (bank_account_state = 30, 1, 0) +"
                + "IF (contact_state = 30, 1, 0)"
                + ") = "
                + qualifiedCount
                + ",1,0) AS qualified "
                + "FROM cl_user_auth "
                + "WHERE user_id = " + paramMap.get("userId");
        paramMap = new HashMap<>(8);
        paramMap.put("sqlString", sql);
        return userAuthMapper.executeSql(paramMap);
    }
}
