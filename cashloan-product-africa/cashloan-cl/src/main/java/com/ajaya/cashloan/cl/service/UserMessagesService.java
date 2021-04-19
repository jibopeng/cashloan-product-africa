package com.ajaya.cashloan.cl.service;

import com.ajaya.cashloan.cl.model.UserMessagesModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.UserMessages;
import com.ajaya.cashloan.core.common.service.BaseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户资料--联系人Service
 * 
 * @author chenxy
 * @version 1.0.0
 * @date 2017-03-04 11:54:57
 */
public interface UserMessagesService extends BaseService<UserMessages, Long>{

	/**
	 * 短信查询
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UserMessages> listMessages(long userId, int current, int pageSize);

	/**
	 * 查找基本信息
	 * @param ides id数组
	 */
	HSSFWorkbook findUserMesByuPhones(String[] ides);

	/**
	 * 删除用户短信(分表)
	 * @param tableName
	 * @param userId
	 */
	void deleteShard(String tableName, Long userId);
	
	/**
	 * 保存用户短信(分表)
	 * @param tableName
	 * @param map
	 * @param userId
	 * @param createTime
	 * @return
	 */
	int saveShard(String tableName, Map<String, Object> map, long userId, Date createTime);
	
	/**
	 * 获取分表表名
	 * @param userId
	 * @return
	 */
	String getTableName(long userId);

	/**
	 * 根据用户电话查询用户id
	 * @param phone 电话
	 * @return 用户id 集合
	 */
	ArrayList<Long> findUserIdByuPhone(String phone);

	/**
	 * 后台返回短信信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getMessageInfo(HashMap<String, Object> paramMap);

	/**
	 * 向用户短信中填入borrow_id
	 * @param userId
	 * @param borrowId
	 */
	void updateMessagesBorrowidByUserid(Long userId, Long borrowId);

	/**
	 * 用户id进行分表 根据borrowId进行短信原数据查询
 	 * @param borrowId 订单id
	 * @param userId 用户id
	 * @return    List<UserMessagesModel>
	 */
    List<UserMessagesModel> selectMessagesListByBorrowIdShard(long borrowId, Long userId);

    /**
     * 复贷查询之前有数据的最大borrowId
     * @param borrowId
     * @param userId
     * @return
     */
	Long selectDataMaxBorrowIdBefore(long borrowId, Long userId);

	/**
	 * 信审展示短信列表
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UserMessages> listMessagesXinShen(long userId, int current, int pageSize);
}
