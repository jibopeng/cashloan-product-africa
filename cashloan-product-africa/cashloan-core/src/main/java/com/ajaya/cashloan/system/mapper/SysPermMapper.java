/**
 *
 *
 * @author rd
 * @version 1.0.0.0
 * @date 2016年12月01日 下午16:01:55
 *
 */
package com.ajaya.cashloan.system.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.system.domain.SysPerm;

@RDBatisDao
public interface SysPermMapper extends BaseMapper<SysPerm,Long>  {

    SysPerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPerm record);
    
    List<SysPerm> listByUserName(String userName);
    
    List<SysPerm> selectAll();
    
	List<SysPerm> listByRoleId(Long roleId);

	List<Map<String, Object>> fetchAll();
}