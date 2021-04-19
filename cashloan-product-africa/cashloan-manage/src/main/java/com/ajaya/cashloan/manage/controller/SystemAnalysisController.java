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
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.model.OverdueAnalisisModel;
import com.ajaya.cashloan.cl.model.RepayAnalisisModel;
import com.ajaya.cashloan.cl.service.SystemAnalysisService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.domain.SysUserChannel;
import com.ajaya.cashloan.system.service.SysUserChannelService;

/**
 * 运营分析
 * @author caitt
 * @version 1.0
 * @date 2017年3月21日下午2:59:46

 */
@Controller
@Scope("prototype")
public class SystemAnalysisController extends ManageBaseController {

	@Resource
	private SystemAnalysisService systemAnalysisService;
	
	@Resource
	private SysUserChannelService sysUserChannelService;
	
	/**
	 * 每日还款统计
	 * @param response
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/sysanalysis/dayRepay.htm")
	public void dayRepay(HttpServletResponse response,
			@RequestParam("search")String search) throws Exception {
		Map<String, Object> params = JSONObject.parseObject(search);
		List<RepayAnalisisModel> list = null;
		
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
		if(SysUserChannelList != null && SysUserChannelList.size() > 0){
			//说明是渠道管理员
			if(params == null){
				params = new HashMap<>();
			}
			List<Long> channelIds = new ArrayList<>();
			for (SysUserChannel sysUserChannel : SysUserChannelList) {
				channelIds.add(sysUserChannel.getChannelId());
			}
			params.put("channelIds", channelIds);
			list = systemAnalysisService.dayRepayAnalisis(params);
		} else {
			list = systemAnalysisService.dayRepayAnalisis(params);
		}
		
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, list);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	
	/**
	 * 每月还款统计
	 * @param response
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/sysanalysis/monthRepay.htm")
	public void monthRepay(HttpServletResponse response,
			@RequestParam("search")String search) throws Exception {
		Map<String, Object> params = JSONObject.parseObject(search);
		List<RepayAnalisisModel> list = systemAnalysisService.monthRepayAnalisis(params);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, list);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 每月逾期分析
	 * @param response
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/sysanalysis/overdue.htm")
	public void overdue(HttpServletResponse response,
			@RequestParam("search")String search,
			@RequestParam("current")Integer current,
			@RequestParam("pageSize")Integer pageSize) throws Exception {
		Map<String, Object> params = JSONObject.parseObject(search);
		Page<OverdueAnalisisModel> page = systemAnalysisService.overdueAnalisis(params, current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
}
