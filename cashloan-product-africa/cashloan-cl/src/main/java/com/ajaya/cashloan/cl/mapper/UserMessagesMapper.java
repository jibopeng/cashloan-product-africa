package com.ajaya.cashloan.cl.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.UserMessages;
import com.ajaya.cashloan.cl.model.UserMessagesModel;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 用户资料--联系人Dao
 *
 * @author yanzhqiiang
 * @since 2020-02-27 11:54:57
 */
@RDBatisDao
public interface UserMessagesMapper extends BaseMapper<UserMessages, Long> {

    /**
     * 根据表名查询表数量
     *
     * @param tableName 表名称
     * @return 返回表名数量
     */
    int countTable(String tableName);

    /**
     * 创建表名称
     *
     * @param tableName 表名称
     */
    void createTable(@Param("tableName") String tableName);

    /**
     * 删除该表中的数据
     *
     * @param tableName 表名称
     * @param userId    用户id
     */
    void deleteShardByUserId(@Param("tableName") String tableName, @Param("userId") Long userId);

    /**
     * (分表)新增
     *
     * @param tableName    表名
     * @param userMessages 短信信息
     * @return 更改数据库条数
     */
    int saveShard(@Param("tableName") String tableName, @Param("item") UserMessages userMessages);

    /**
     * 根据参数(分表)查询
     *
     * @param tableName 表名
     * @param params    参数
     * @return
     */
    List<UserMessages> listShardSelective(
            @Param("tableName") String tableName,
            @Param("params") Map<String, Object> params);

    ArrayList<Long> findUserIdByuPhone(String phone);

    /**
     * 通过用户ID查询未绑定BorrowId的短信数据
     *
     * @param tableName
     * @param userId
     * @return
     */
    List<UserMessages> findListNoBorrowidByUserid(@Param("tableName") String tableName, @Param("userId") Long userId);

    /**
     * 通过ID更新分表更新短信数据
     *
     * @param tableName
     * @param userMessages
     */
    void updateShard(@Param("tableName") String tableName, @Param("item") UserMessages userMessages);

    /**
     * 根据订单号分表查询用户短信原始数据
     *
     * @param tableName
     * @param borrowId
     * @return
     */
    List<UserMessages> listShardByBorrowid(@Param("tableName") String tableName, @Param("borrowId") Long borrowId);

    /**
     * 分表查询，根据用户ID查询数据最近一次创建时间
     *
     * @param tableName
     * @param userId
     * @return
     */
    Date findMaxCreatetimeByUserid(@Param("tableName") String tableName, @Param("userId") Long userId);

    /**
     * 分表查询，根据用户ID和最近一次创建时间查询数据
     *
     * @param tableName
     * @param userId
     * @param maxCreatetime
     * @return
     */
    List<UserMessages> findListByUseridAndCreatetime(@Param("tableName") String tableName, @Param("userId") Long userId, @Param("maxCreatetime") Date maxCreatetime);

    /**
     * top20短信记录
     *
     * @param tableName
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> listShardSelectiveTop20(@Param("tableName") String tableName, @Param("params") Map<String, Object> paramMap);

    /**
     * top20短信记录
     *
     * @param tableName
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> findListByUseridAndCreatetimeTop20(@Param("tableName") String tableName, @Param("userId") long userId, @Param("maxCreatetime") Date maxCreatetime);

    /**
     * top20短信记录
     *
     * @param tableName
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> listShardByBorrowidTop20(@Param("tableName") String tableNameMsg, @Param("borrowId") Long borrowId);

    /**
     * 通过ID批量更新分表更新短信数据
     *
     * @param tableName
     * @param borrowId
     * @param updateTime
     * @param ids
     */
    void updateByIdsShard(@Param("tableName") String tableName, @Param("borrowId") Long borrowId, @Param("updateTime") Date updateTime, @Param("ids") List<Long> ids);

    /**
     * 根据borrowId找找用户的messges原始 数据
     *
     * @param borrowId  订单id
     * @param tableName 分表名称
     * @return List<UserMessagesModel>
     */
    List<UserMessagesModel> selectMessagesListByBorrowIdShard(@Param("borrowId") Long borrowId, @Param("tableName") String tableName);

    
	Long selectDataMaxBorrowIdBefore(@Param("userId")Long userId, @Param("borrowId") Long borrowId, @Param("tableName") String tableName);
	
	/**
	 * 获取短信
	 * @param tableName
	 * @param userId
	 * @return
	 */
	Long findMaxBorrowidByUserid(@Param("tableName")String tableNameMsg, @Param("userId")Long userId);

}
