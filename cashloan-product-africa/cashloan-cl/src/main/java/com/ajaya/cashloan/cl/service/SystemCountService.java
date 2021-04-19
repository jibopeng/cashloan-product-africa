package com.ajaya.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.system.domain.SysUserChannel;

/**
 * 首页数据统计
 * @author caitt
 * @version 1.0
 * @date 2017年3月16日上午10:15:38
 */
public interface SystemCountService {
	
	/**
	 * 今日数据
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> todayInfo()throws Exception;
	
	/**
	 * 累计数据
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> cumulativeInfo()throws Exception;
	
	/**
	 * 实时数据
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> realTimeInfo()throws Exception;

	/**
	 * 地域数据
	 * @param sysUserChannelList 
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> areaInfo(List<SysUserChannel> sysUserChannelList)throws Exception;

	/**
	 * 还款方式数据
	 * @param sysUserChannelList 
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> repayWayInfo(List<SysUserChannel> sysUserChannelList)throws Exception;

	/**
	 * 放款量、还款量数据
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> loanAndRepayInfo()throws Exception;

	/**
	 * 为渠道管理员查询 今日数据
	 * @param sysUserChannelList 
	 * @return
	 */
	public Map<String, Object> todayInfoForChannel(List<SysUserChannel> sysUserChannelList) throws Exception;

	/**
	 * 为渠道管理员查询 累积数据
	 * @param sysUserChannelList
	 * @return
	 */
	public Map<String, Object> cumulativeInfoForChannel(List<SysUserChannel> sysUserChannelList) throws Exception;

	/**
	 * 为渠道管理员工作台-实时数据
	 * @param sysUserChannelList
	 * @return
	 */
	public Map<String, Object> realTimeInfoForChannel(List<SysUserChannel> sysUserChannelList);

	/**
	 * 刘晓亮 为渠道管理员放款量、还款量数据
	 * @param list 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loanAndRepayInfoForChannel(List list);

}
