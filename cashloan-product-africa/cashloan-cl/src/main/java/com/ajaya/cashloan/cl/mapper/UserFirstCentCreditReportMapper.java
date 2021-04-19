package com.ajaya.cashloan.cl.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ajaya.cashloan.cl.domain.UserFirstCentCreditReport;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 'FC用户征信报告表Dao
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 15:20:15
 */
@RDBatisDao
public interface UserFirstCentCreditReportMapper extends BaseMapper<UserFirstCentCreditReport, Long> {

	@Select("select ifnull((select id from cl_user_first_cent_credit_report where bvn = #{bvnNo}), 0)")
	Integer checkByBvn(@Param("bvnNo")String bvnNo);

}
