package com.ajaya.cashloan.system.service;

import java.util.List;

import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.system.domain.SysUserRole;


/**
 * 
 * 用户角色关联信息service接口
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月23日 上午9:55:01
 */
public interface SysUserRoleService {
	/**
	 * 用户角色关联信息查询
	 * @param userId 角色ID
	 * @return 关联信息List
	 * @throws ServiceException 
	 */
	List<SysUserRole> getSysUserRoleList(Long userId) throws ServiceException;
	
}
