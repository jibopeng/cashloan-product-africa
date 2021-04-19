package com.ajaya.cashloan.rule.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ajaya.cashloan.rule.domain.RuleEngine;
import com.ajaya.cashloan.rule.domain.RuleEngineInfo;
import com.ajaya.cashloan.rule.service.RuleEngineConfigService;
import com.ajaya.cashloan.rule.service.RuleEngineInfoService;
import com.ajaya.cashloan.rule.service.RuleEngineService;
import com.ajaya.cashloan.rule.service.RuleInfoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tool.util.StringUtil;

import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.rule.domain.RuleEngineConfig;

/**
 * 规则引擎管理Controller
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2016-12-12 17:25:31

 */
@Controller
@Scope("prototype")
public class RuleEngineConfigController extends BaseController {
	@Resource
	private RuleEngineConfigService ruleEngineConfigService;
	@Resource
	private RuleEngineService ruleEngineService;
	@Resource
	private RuleInfoService ruleInfoService;
	@Resource
	private RuleEngineInfoService ruleEngineInfoService;
	
	/**
	 * 获取配置的表名称 以及字段
	 */
	@RequestMapping("/modules/manage/rule/findTable.htm")
	public void findByTable(){
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("state", "10");
		List<Map<String, Object>> data=ruleEngineConfigService.findAllInfo(map);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA,data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 通过规则id查询相关信息
	 */
	@RequestMapping("/modules/manage/rule/getRuleConfig.htm")
	public void getConfig(@RequestParam(value = "id")  Long id){
		RuleEngine rule=ruleEngineService.findById(id);
		
		Map<String,Object> search = new HashMap<String,Object>();
		search.put("ruleEnginId",id);
		List<RuleEngineConfig> configs=ruleEngineConfigService.findByMap(search);
		List<RuleEngineInfo> reulst=ruleEngineInfoService.findByMap(search);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rule", rule);
		map.put("configList", configs);
		map.put("infoList", reulst);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA,map);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/modules/manage/rule/saveRuleConfig.htm")
	public void saveRuleConfig(HttpServletRequest request,
			@RequestParam(value="name") String name,
			@RequestParam(value="type") String type,
			// @RequestParam(value="typeResultStatus") String typeResultStatus,
			@RequestParam(value ="id",required=false)  Long id,
			@RequestParam(value="configList") String configList,
			@RequestParam(value="infoList",required=false)  String infoList){
		List list = JsonUtil.parse(configList, List.class);
		Map<String,Object> rule = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(id)){
		  rule.put("id",id);
		}
		rule.put("name",name);
		rule.put("type",type);
		// rule.put("typeResultStatus",typeResultStatus);
		int resCode=ruleEngineConfigService.saveOrUpate(rule,list,infoList,request);
		Map<String,Object> result = new HashMap<String,Object>();
		if (resCode>0) {
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "成功");
		}else{
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "失败");
		}
		ServletUtils.writeToResponse(response,result);
	}
	 
}
