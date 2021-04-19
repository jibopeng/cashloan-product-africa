package com.ajaya.cashloan.cl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UserEmerContacts;
import com.ajaya.cashloan.cl.mapper.UserContactsMapper;
import com.ajaya.cashloan.cl.mapper.UserEmerContactsMapper;
import com.ajaya.cashloan.cl.service.UserEmerContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.UserContacts;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.ShardTableUtil;

/**
 * 紧急联系人表ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 11:24:05

 */
 
@Service("userEmerContactsService")
public class UserEmerContactsServiceImpl extends BaseServiceImpl<UserEmerContacts, Long> implements UserEmerContactsService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserEmerContactsServiceImpl.class);
   
    @Resource
    private UserEmerContactsMapper userEmerContactsMapper;
    @Resource
    private UserContactsMapper userContactsMapper;

	@Override
	public BaseMapper<UserEmerContacts, Long> getMapper() {
		return userEmerContactsMapper;
	}
	
	@Override
	public List<UserEmerContacts> getUserEmerContactsByUserId(Map<String,Object> paramMap) {
		Long userId = Long.parseLong(paramMap.get("userId").toString());
		String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }
		
		List<UserEmerContacts> listSelective = userEmerContactsMapper.listSelective(paramMap);
		if(listSelective != null && listSelective.size() > 0){
			for (UserEmerContacts userEmerContacts : listSelective) {
				String phone = userEmerContacts.getPhone();
				String nameInContact = "";
				
				//查询用户的通讯录
				List<UserContacts> list = null;
				Long borrowId = userContactsMapper.findMaxBorrowidByUserid(tableName, userId);
				if(borrowId == null || borrowId == 0){
		        	list = userContactsMapper.listShardSelective(tableName, paramMap);
		        } else {
		        	list = userContactsMapper.listShardByBorrowid(tableName, borrowId);
		        }
				if(list != null && list.size() > 0){
					for (UserContacts userContacts : list) {
						if(phone.equals(userContacts.getPhone())){
							if(!nameInContact.contains(userContacts.getName())){
								nameInContact = nameInContact + userContacts.getName() + ",";
							}
						}
					}
				}
				if(nameInContact.contains(",")){
					int lastIndexOf = nameInContact.lastIndexOf(",");
					nameInContact = nameInContact.substring(0, lastIndexOf);
				}
				userEmerContacts.setNameInContact(nameInContact);
			}
		}
		return listSelective;
	}

	@Override
	public UserEmerContacts getUserEmerContactsByUserIdAndType(Long userId, String type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("type", type);
		return userEmerContactsMapper.findSelective(paramMap);
	}
}