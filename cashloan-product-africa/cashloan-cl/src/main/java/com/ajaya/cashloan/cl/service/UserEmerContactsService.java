package com.ajaya.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.UserEmerContacts;
import com.ajaya.cashloan.core.common.service.BaseService;


/**
 * 紧急联系人表Service
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 11:24:05
 */
public interface UserEmerContactsService extends BaseService<UserEmerContacts, Long>{

	public List<UserEmerContacts> getUserEmerContactsByUserId(Map<String,Object> paramMap);

	public UserEmerContacts getUserEmerContactsByUserIdAndType(Long userId, String string);
}
