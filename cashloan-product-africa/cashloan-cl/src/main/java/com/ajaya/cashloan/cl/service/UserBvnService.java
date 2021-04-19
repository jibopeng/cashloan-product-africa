package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.domain.UserBvn;
import com.ajaya.cashloan.cl.model.BvnCheck;
import com.ajaya.cashloan.core.common.service.BaseService;
/**
 * '用户bvn记录表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-15 17:23:05
 */
public interface UserBvnService extends BaseService<UserBvn, Long>{
    /**
     * 校验是否weibvn号
     * @param userId 用户id
     * @param bvnNumber bvn号码
     * @param phone  手机号码
     * @return 检查对象
     */
    BvnCheck checkBvn(String userId, String bvnNumber,String phone);

}
