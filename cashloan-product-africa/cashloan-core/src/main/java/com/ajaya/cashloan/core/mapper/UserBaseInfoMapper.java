package com.ajaya.cashloan.core.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.model.ManagerUserModel;
import com.ajaya.cashloan.core.model.UserWorkInfoModel;
import com.ajaya.cashloan.core.model.UserProtocolModel;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;

/**
 * 用户详细信息Dao
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-21 13:44:30
 */
@RDBatisDao
public interface UserBaseInfoMapper extends BaseMapper<UserBaseInfo,Long> {

	List<Map<String, Object>> getDictsCache(String type);

	ManagerUserModel getBaseModelByUserId(Long userId);



	/**
	 * 据用户id查询用户详细信息
	 * 
	 * @param userId
	 * @return
	 */
	UserBaseInfo findByUserId(@Param("userId") Long userId);
	
	/**
	 * 将用户加入黑名单
	 * @param ids
	 * @return
	 */
	int updateBlackIdNos(List<Long> ids);
	
	/**
	 * 用户加入白名单
	 * @param ids
	 * @return
	 */
	int updateWhiteIdNos(List<Long> ids);
	
	List<Long> findByBvnNo(@Param("idNo") String idNo);
	
	/**
	 * 查询是否有其它用户使用此身份证号
	 * @param idNo
	 * @param userId
	 * @return
	 */
	List<UserBaseInfo> findOhterUserBvnNo(@Param("bvnNo")String idNo, @Param("userId")Long userId);

	/**
	 * 获取用户基本信息（协议专用）
	 * @param userId 用户id
	 * @return 用户基本信息
	 */
	UserProtocolModel getUserBaseDate(@Param("userId") Long userId);

	/**
	 * 查询某日期之间的所有用户信息
	 * @param params 
	 * @return
	 */
	List<UserBaseInfo> listUserBaseInfoBetweenDate(Map<String, Object> params);
}
