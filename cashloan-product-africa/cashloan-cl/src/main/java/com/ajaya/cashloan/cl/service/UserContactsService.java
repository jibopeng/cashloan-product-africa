package com.ajaya.cashloan.cl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.UserContacts;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 用户资料--联系人Service
 * 
 * @author chenxy
 * @version 1.0.0
 * @date 2017-03-04 11:52:26
 */
public interface UserContactsService extends BaseService<UserContacts, Long>{

	/**
	 * 查询通讯录
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UserContacts> listContacts(long userId, int current, int pageSize);

	/**
	 * 保存前删除原有记录
	 * @param infos 
	 * @param userId
	 * @param type
	 * @return
	 */
	boolean deleteAndSave(List<Map<String, Object>> infos, long userId, String type);
	
	/**
	 * 保存前删除原有记录(分表)
	 * @param tableName 
	 * @param userId
	 * @param type
	 * @return
	 */
	void deleteShard(String tableName, long userId, Integer type);
	
	/**
	 * 获取分表表名
	 * @param userId
	 * @return
	 */
	String getTableName(long userId);
	
	/**
	 * 保存数据（分表）
	 * @param tableName
	 * @param map
	 * @param userId
	 * @param createTime
	 * @param type
	 * @return
	 */
	int saveShard(String tableName, Map<String, Object> map, long userId, Date createTime, Integer type);

	/**
	 * 保存通讯录备注信息
	 * @param userId
	 * @param contactPhone
	 * @param contactName
	 * @param contactRemark
	 * @return
	 */
	public void addOrUpdateContactRemark(long userId, String contactPhone, String contactName, String contactRemark);
	
	/**
	 * 向通讯录中填入borrow_id
	 * @param userId
	 * @param borrowId
	 * @return
	 */
	void updateContactBorrowidByUserid(long userId, long borrowId);
	
	/**
	 * 查询通讯录
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UserContacts> listContactsXinShen(long userId, int current, int pageSize);

	/**
	 * 通讯录模糊查询
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @param name
	 * @param phone
	 * @return
	 */
	Page<UserContacts> listContactsLike(long userId, int current, int pageSize, String name, String phone);

	/**
	 * 复贷查询之前有数据的最大borrowId
	 * @param borrowId
	 * @param userId
	 * @return
	 */
	Long selectDataMaxBorrowIdBefore(Long borrowId, Long userId);

	
	/**
	 * 查询通讯录
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> listContactsTop(long userId, int current, int pageSize);
	
	/**
	 * 查询所有
	 * @param userId
	 * @return
	 */
	List<UserContacts> listContacts(long userId);

}
