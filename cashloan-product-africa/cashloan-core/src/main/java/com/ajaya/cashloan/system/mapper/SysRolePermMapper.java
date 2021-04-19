/**
 *
 *
 * @author rd
 * @version 1.0.0.0
 * @date 2016年12月02日 下午14:56:41
*
 */
package com.ajaya.cashloan.system.mapper;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.system.domain.SysRolePerm;

@RDBatisDao
public interface SysRolePermMapper extends BaseMapper<SysRolePerm, Long>{

    SysRolePerm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePerm record);
    
    int deleteByRoleId(Integer roleId);
    
}