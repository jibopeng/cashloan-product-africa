package com.ajaya.cashloan.cl.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.Sms;
import com.ajaya.cashloan.cl.model.SmsModel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 短信记录Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 */
@RDBatisDao
public interface SmsMapper extends BaseMapper<Sms,Long> {

	/**
	 * 查询最新一条短信记录
	 * @param data
	 * @return
	 */
	Sms findTimeMsg(Map<String, Object> data);

    /**
     * 查询某号码某种类型当天已发送次数
     * @param data
     * @return
     */
    int countDayTime(Map<String, Object> data);
        /**
     * 查询某号码某种类型30分钟内已发送次数
     * @param data 数据
     * @return
     */
    int countInTime(Map<String, Object> data);


    /**
     * 根据订单号修改短信记录
     * @param data
     * @return
     */
    int updateByOrderNo(Map<String, Object> paramMap);

    /**
     * 查询某号码某种类型一共已发送次数
     * @param data
     * @return
     */
	int countAllTime(Map<String, Object> data);

	/**
	 * 查询电子签章短信
	 * @param params
	 * @return
	 */
	List<Sms> listSelectiveEsign(Map<String, Object> params);

    SmsModel findCountAndLastTime(HashMap<String, Object> params);
}
