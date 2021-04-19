package com.ajaya.cashloan.cl.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.cl.domain.UserContacts;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 用户资料--联系人Dao
 * 
 * @author chenxy
 * @version 1.0.0
 * @date 2017-03-04 11:52:26
 */
@RDBatisDao
public interface UserContactsMapper extends BaseMapper<UserContacts,Long> {
	
	/**
	 * 根据表名查询表数量
	 * @param tableName
	 * @return
	 */
	int countTable(String tableName);
	
	/**
	 * 根据表名创建表
	 * @param tableName
	 */
	void createTable(@Param("tableName") String tableName);
	
	/**
	 * (分表)新增
	 * @param tableName
	 * @param userContacts
	 * @return
	 */
	int saveShard(@Param("tableName")String tableName, @Param("item")UserContacts userContacts);
	
	/**
	 * 删除原有记录
	 * @param tableName
	 * @param userId
	 * @param type
	 * @return
	 */
	int deleteShardByUserId(@Param("tableName")String tableName, @Param("userId")long userId, @Param("type")Integer type);
	
	/**
	 * 根据参数(分表)查询
	 * @param tableName
	 * @param beginRow
	 * @param pageSize
	 * @param params
	 * @return
	 */
	List<UserContacts> listShardSelective(
			@Param("tableName") String tableName,
			@Param("params") Map<String, Object> params);
	
	/**
	 * 根据参数(分表)查询
	 * @param tableName
	 * @param params
	 * @return
	 */
	int listShardSelectiveCount(
			@Param("tableName") String tableName,
			@Param("params") Map<String, Object> params);

	/**
	 * (分表)新增通讯录备注表
	 * @param tableName
	 * @param userContacts
	 * @return
	 */
	void createContactRemarkTable(@Param("tableName") String tableName);

	/**
	 * 查询通讯录备注表,根据user_id和phone
	 * @param selectParamSql
	 * @return
	 */
	Integer selectContactRemarkCountBySql(@Param("contactRemarkCountBySelectSql") String selectParamSql);

	/**
	 * 更新通讯录日志表
	 * @param updateParamSql
	 */
	void updateContactRemarkBySql(@Param("contactRemarkByUpdateSql") String updateParamSql);

	/**
	 * 新增通讯录日志表
	 * @param insertParamSql
	 */
	void saveContactRemarkBySql(@Param("contactRemarkByInsertSql") String insertParamSql);

	/**
	 * 查询通讯录备注表,根据user_id和phone，返回备注
	 * @param selectParamSql
	 * @return
	 */
	String selectContactRemarkBySql(@Param("contactRemarkBySelectSql") String selectParamSql);
	
	/**
	 * 查询通讯录电话,姓名
	 * @param sqlCon
	 * @return
	 */
	List<Map<String, Object>> getContact(@Param("getContact") String getContact);

	/**
	 * 通过用户ID查询未绑定BorrowId的通讯录数据
	 * @param userId
	 */
	List<UserContacts> findListNoBorrowidByUserid(@Param("tableName") String tableName, @Param("userId")long userId);

	/**
	 * 通过ID更新分表更新通讯录数据
	 * @param tableName
	 * @param userContactsNoBorrowid
	 */
	void updateShard(@Param("tableName") String tableName, @Param("item") UserContacts userContacts);

	/**
	 * 通过订单号分表查询通讯录原始数据
	 * @param tableName
	 * @param borrowId
	 */
	List<UserContacts> listShardByBorrowid(@Param("tableName") String tableName, @Param("borrowId") Long borrowId);

	/**
	 * 分表查询，根据用户ID查询最大的订单号
	 * @param tableName
	 * @param userId
	 * @return
	 */
	Long findMaxBorrowidByUserid(@Param("tableName") String tableName, @Param("userId") Long userId);

	/**
	 * 分表查询，根据用户ID查询数据最近一次创建时间
	 * @param tableName
	 * @param userId
	 */
	Date findMaxCreatetimeByUserid(@Param("tableName") String tableName, @Param("userId") Long userId);

	/**
	 * 分表查询，根据用户ID和最近一次创建时间查询数据
	 * @param tableName
	 * @param userId
	 * @param maxCreatetime
	 * @return
	 */
	List<UserContacts> findListByUseridAndCreatetime(@Param("tableName") String tableName, @Param("userId") Long userId, @Param("maxCreatetime") Date maxCreatetime);

	/**
	 * 通过姓名电话模糊查询
	 * @param tableName
	 * @param params
	 * @return
	 */
	List<UserContacts> listShardSelectiveLike(@Param("tableName") String tableName,
			@Param("params") Map<String, Object> params);

	/**
	 * 通过ID批量更新分表更新通讯录数据
	 * @param tableName
	 * @param borrowId
	 * @param updateTime
	 * @param ids
	 */
	void updateByIdsShard(@Param("tableName") String tableName, @Param("borrowId") Long borrowId, @Param("updateTime") Date updateTime, @Param("ids") List<Long> ids);

	/**
	 * 复贷查询之前有数据的最大borrowId
	 * @param userId
	 * @param borrowId
	 * @param tableName
	 * @return
	 */
	Long selectDataMaxBorrowIdBefore(@Param("userId")Long userId, @Param("borrowId")Long borrowId, @Param("tableName")String tableName);

}
