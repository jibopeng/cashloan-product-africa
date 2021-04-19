package com.ajaya.creditrank.cr.mapper;

import com.ajaya.creditrank.cr.domain.RankDetail;
import org.apache.ibatis.annotations.Param;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 评分卡等级详情表Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-06 11:27:25
 */
@RDBatisDao
public interface RankDetailMapper extends BaseMapper<RankDetail,Long> {

	/**
	 * 根据等级父ID和评分找到对应的等级详情信息
	 * @param id
	 * @param score
	 * @return
	 */
    RankDetail findByParentIdAndScore(@Param("id") Long id, @Param("score") Integer score);

}
