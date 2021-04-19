package com.ajaya.cashloan.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.Sms;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;

 /**
 * 短信消息
 * 
 * @author 
 * @version 
 * @date 

 */
@Controller
@Scope("prototype")
public class ManageSmsController extends BaseController {

	@Resource
	private ClSmsService clSmsService;
	
	/**
	 * 短信列表
	 * @param searchParams
	 * @param current
	 * @param pageSize
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/manage/sms/list.htm",method={RequestMethod.GET})
	public void listSms(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Map<String,Object> result = new HashMap<String,Object>();
		Page<Sms> page = clSmsService.list(params, current, pageSize);
		Map<String,Object> list = new HashMap<String,Object>();
		list.put("list", page);
		result.put(Constant.RESPONSE_DATA, list);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page)); 
		result.put(Constant.RESPONSE_CODE,Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	
}
