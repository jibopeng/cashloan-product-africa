package com.ajaya.cashloan.core.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.User;
import com.ajaya.cashloan.core.model.CloanUserModel;
import com.github.pagehelper.Page;

/**
 * 用户Service
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-21 13:39:06
 */
public interface CloanUserService extends BaseService<User, Long> {
	/**
	 * 查询用户详细信息列表
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<CloanUserModel> listUser(Map<String, Object> params, int currentPage,
                                  int pageSize);
	
	/**
	 * 查询用户详细信息
	 * @param id
	 * @return
	 */
	CloanUserModel getModelById(Long id);
	
	/**
	 * 查询所有相关的数据字典
	 * @return
	 */
	List<Map<String, Object>> findAllDic();


	/**
	 * 据uuid修改用户信息
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean updateByUuid(Map<String, Object> paramMap);
	
	/**
	 * 据用户手机号查询用户
	 * @param phone
	 * @return
	 */
	User findByPhone(String phone);
	
	/**
	 * 今日注册用户数
	 * @return
	 */
	long todayCount();

	/**
	 * 修改登陆时间
	 * @param loginName
	 */
	void modify(String loginName);

	Page<CloanUserModel> listUrUser(Map<String, Object> params, int currentPage, int pageSize);
	
	/**
	 * 通过uuid获取用户
	 * @param uuid
	 * @return
	 */
	public User getUserByUuid(String uuid);
}
