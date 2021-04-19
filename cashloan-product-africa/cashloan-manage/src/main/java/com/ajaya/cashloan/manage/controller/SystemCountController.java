package com.ajaya.cashloan.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ajaya.cashloan.cl.service.SystemCountService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.domain.SysUserChannel;
import com.ajaya.cashloan.system.service.SysUserChannelService;

/**
 * 后台登陆，首页统计数据
 * @author caitt
 * @version 1.0
 * @date 2017年3月15日下午2:28:26

 */
@Scope("prototype")
@Controller
public class SystemCountController extends ManageBaseController {
	
	@Resource
	private SystemCountService systemCountService;
	
	@Resource
	private SysUserChannelService sysUserChannelService;

	/**
	 * 工作台-今日数据
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/workbench/todayInfo.htm")
	public void todayInfo(HttpServletResponse response) throws Exception {
		// 刘晓亮 start
		//获取用户user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//查看该用户有哪些渠道
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", sysUser.getId());
		List<SysUserChannel> SysUserChannelList = sysUserChannelService.listSelective(paramMapForUserId);
		//注意：这里为了区分 本公司用户还是渠道用户，是根据SysUserChannelList是否为null判断
		//所以,如果是本公司员工,就不要向arc_sys_user_channel表插入数据
		Map<String,Object> data = null;
		if(SysUserChannelList != null && SysUserChannelList.size() > 0){
			//说明是渠道用户
			data = systemCountService.todayInfoForChannel(SysUserChannelList);
		} else {
			data = systemCountService.todayInfo();
			
		}
		// 刘晓亮 end
		// 原来的直接查询      Map<String,Object> data = systemCountService.modules/manage/workbench/todayInfo.htm();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 工作台-累计数据
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/workbench/cumulativeInfo.htm")
	public void cumulativeInfo(HttpServletResponse response) throws Exception {
		
		// 刘晓亮 start
		//获取用户user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//查看该用户有哪些渠道
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", sysUser.getId());
		List<SysUserChannel> SysUserChannelList = sysUserChannelService.listSelective(paramMapForUserId);
		//注意：这里为了区分 本公司用户还是渠道用户，是根据SysUserChannelList是否为null判断
		//所以,如果是本公司员工,就不要向arc_sys_user_channel表插入数据
		Map<String,Object> data = null;
		if(SysUserChannelList != null && SysUserChannelList.size() > 0){
			//说明是渠道用户
			data = systemCountService.cumulativeInfoForChannel(SysUserChannelList);
		} else {
			data = systemCountService.cumulativeInfo();
			
		}
		// 刘晓亮 end
		// 刘晓亮 原来的 Map<String,Object> data = systemCountService.cumulativeInfo();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 工作台-实时数据
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/workbench/realTimeInfo.htm")
	public void realTimeInfo(HttpServletResponse response) throws Exception {
		
		// 刘晓亮 start
		//获取用户user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//查看该用户有哪些渠道
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", sysUser.getId());
		List<SysUserChannel> SysUserChannelList = sysUserChannelService.listSelective(paramMapForUserId);
		//注意：这里为了区分 本公司用户还是渠道用户，是根据SysUserChannelList是否为null判断
		//所以,如果是本公司员工,就不要向arc_sys_user_channel表插入数据
		Map<String,Object> data = null;
		if(SysUserChannelList != null && SysUserChannelList.size() > 0){
			//说明是渠道用户
			data = systemCountService.realTimeInfoForChannel(SysUserChannelList);
		} else {
			data = systemCountService.realTimeInfo();
			
		}
		//刘晓亮 end
		//刘晓亮 原来的 Map<String,Object> data = systemCountService.realTimeInfo();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 工作台-地域数据
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/workbench/areaInfo.htm")
	public void areaInfo(HttpServletResponse response) throws Exception {
		
		//刘晓亮 start
		//获取用户user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//查看该用户有哪些渠道
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", sysUser.getId());
		List<SysUserChannel> SysUserChannelList = sysUserChannelService.listSelective(paramMapForUserId);
		//注意：这里为了区分 本公司用户还是渠道用户，是根据SysUserChannelList是否为null判断
		//所以,如果是本公司员工,就不要向arc_sys_user_channel表插入数据
		//刘晓亮 end
		Map<String,Object> data = systemCountService.areaInfo(SysUserChannelList);
		
		// 刘晓亮 原来的 Map<String,Object> data = systemCountService.areaInfo();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 工作台-还款方式数据
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/workbench/repayWayInfo.htm")
	public void repayWayInfo(HttpServletResponse response) throws Exception {
		
		//刘晓亮 start
		//获取用户user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//查看该用户有哪些渠道
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", sysUser.getId());
		List<SysUserChannel> SysUserChannelList = sysUserChannelService.listSelective(paramMapForUserId);
		//注意：这里为了区分 本公司用户还是渠道用户，是根据SysUserChannelList是否为null判断
		//所以,如果是本公司员工,就不要向arc_sys_user_channel表插入数据
		//刘晓亮 end
		
		Map<String,Object> data = systemCountService.repayWayInfo(SysUserChannelList);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 工作台-放款量、还款量数据
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/workbench/loanAndRepayInfo.htm")
	public void loanAndRepayInfo(HttpServletResponse response) throws Exception {
		
		//刘晓亮 start
		//获取用户user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//查看该用户有哪些渠道
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", sysUser.getId());
		List<SysUserChannel> SysUserChannelList = sysUserChannelService.listSelective(paramMapForUserId);
		//注意：这里为了区分 本公司用户还是渠道用户，是根据SysUserChannelList是否为null判断
		//所以,如果是本公司员工,就不要向arc_sys_user_channel表插入数据
		if(SysUserChannelList != null && SysUserChannelList.size() > 0){
			List list = new ArrayList<>();
			for (SysUserChannel sysUserChannel : SysUserChannelList) {
				list.add(sysUserChannel.getChannelId());
			}
			Map<String,Object> dataForChannel = systemCountService.loanAndRepayInfoForChannel(list);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put(Constant.RESPONSE_DATA, dataForChannel);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
			ServletUtils.writeToResponse(response,result);
			return;
		}
		//刘晓亮 end
		Map<String,Object> data = systemCountService.loanAndRepayInfo();
		
		// 原来的 Map<String,Object> data = systemCountService.loanAndRepayInfo();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
}
