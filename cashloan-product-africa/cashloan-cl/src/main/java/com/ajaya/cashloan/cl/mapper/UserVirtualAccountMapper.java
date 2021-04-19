package com.ajaya.cashloan.cl.mapper;


import com.ajaya.cashloan.cl.domain.UserVirtualAccount;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 用户虚拟账户表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-03 10:42:48
 */
@RDBatisDao
public interface UserVirtualAccountMapper extends BaseMapper<UserVirtualAccount, Long> {

    /**
     * 跟根据用户id查找虚拟账户
     * @param userId  用户id
     * @return  UserVirtualAccount
     */
    UserVirtualAccount findByUserId(@Param("userId") Long userId);

    /**
     * 根据用户账户标识查找用户账户信息
     * @param accountReference 账户唯一标识
     * @return UserVirtualAccount
     */
    UserVirtualAccount findByAccountReference(@Param("accountReference")String accountReference);
}
