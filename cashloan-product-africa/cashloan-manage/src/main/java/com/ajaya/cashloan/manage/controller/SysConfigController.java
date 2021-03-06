package com.ajaya.cashloan.manage.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.core.common.util.CacheUtil;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.common.util.HttpUtil;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.PropertiesUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.rc.domain.TppBusiness;
import com.ajaya.cashloan.rc.service.TppBusinessService;
import com.ajaya.cashloan.system.constant.SystemConstant;
import com.ajaya.cashloan.system.domain.SysConfig;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.model.SysConfigModel;
import com.ajaya.cashloan.system.permission.annotation.RequiresPermission;
import com.ajaya.cashloan.system.service.SysConfigService;
import com.ajaya.cashloan.system.service.SysDictService;

/**
 * User:    mcwang
 * DateTime:2016-08-04 03:26:22
 * details: ???????????????,Action?????????
 * source:  ???????????????
 */
@Scope("prototype")
@Controller
public class SysConfigController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private SysDictService sysDictService;
	@Resource
	private TppBusinessService tppBusinessService;
	@Resource
	private ClBorrowService clBorrowService;
    /**
     * ??????????????????,????????????
     * @param response      ?????????response
     * @param json          ????????????
     * @throws ServiceException
     */
	@RequestMapping("/modules/manage/system/config/save.htm")
    @RequiresPermission(code = "modules:manage:system:config:save", name = "????????????-??????????????????-??????")
    public void saveOrUpdate(HttpServletRequest request,HttpServletResponse response,
    	@RequestParam(value = "json" ,required = false)String json,
    	@RequestParam(value = "status" ,required = false)String status  //???????????????
    	)throws Exception {
	     Map<String,Object> returnMap=new HashMap<String,Object>();
	     long flag;
     
        SysConfig sysConfig = new SysConfig();
        //???json??????????????????
        if (!StringUtils.isEmpty(json))
            sysConfig = JsonUtil.parse(json, SysConfig.class);
		if("create".equals(status)){
			  SysUser sysUser = this.getLoginUser(request);
			  sysConfig.setStatus(1);//???????????????
			  sysConfig.setCreator(sysUser.getId());
		//??????????????????
			flag=sysConfigService.insertSysConfig(sysConfig);
		}else{
		 //????????????
			flag=sysConfigService.updateSysConfig(sysConfig);
		}
		
		if(flag>0){//????????????????????????
        	returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS+",?????????????????????");
        }else{
        	returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
        	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        //???????????????
        ServletUtils.writeToResponse(response, returnMap);
    }

    

    /**
     * ???????????????,????????????
     * @param response      ?????????response
     * @param currentPage   ????????????
     * @param pageSize      ????????????
     * @param searchParam   ????????????
     * @param whereSql      ?????????sql
     * @param fields        ????????????
     * @param rule          ????????????
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/modules/manage/system/config/list.htm")
    @RequiresPermission(code = "modules:manage:system:config:list", name = "????????????-??????????????????-??????")
    public void listConfigs ( HttpServletRequest request,HttpServletResponse response,
                        @RequestParam(value = "current") Integer current,
                        @RequestParam(value = "pageSize") Integer pageSize,
                        @RequestParam(value = "searchParams",required = false)String searchParams)
    throws Exception{
		Map<String, Object> paramap = new HashMap<String, Object>();
    	if (!StringUtils.isEmpty(searchParams)){
    		paramap = JsonUtil.parse(searchParams, Map.class);
    	}
    	List<Map<String,Object>> typeList = new ArrayList<Map<String,Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
    	//????????????????????????????????????
    	List<Map<String, Object>> dicList = sysDictService.getDictsCache("SYSTEM_TYPE");
		for (Map<String, Object> dic : dicList) {
    		Map<String, Object> types = new HashMap<String, Object>();
    		types.put("systemType", dic.get("value"));
			types.put("systemTypeName", dic.get("text"));
    		dataMap.put((String) dic.get("value"), dic.get("text"));
    		typeList.add(types);
    	}
    	//???????????????json??????
		Page<SysConfig> page = sysConfigService.getSysConfigPageList(current,pageSize,paramap);
    	
		List<SysConfigModel> sysModel = new ArrayList<SysConfigModel>();
		if (page != null && !page.isEmpty()) {
			for (SysConfig sys : page) {
				SysConfigModel model = new SysConfigModel();
				model = model.getSysModel(sys, dataMap);
				sysModel.add(model);
			}
		}
		Map<String, Object> returnMap = new HashMap<String,Object>();
    	
    	//???????????????
    	returnMap.put("dicData", typeList);
    	returnMap.put(Constant.RESPONSE_DATA, sysModel);
    	returnMap.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
    	returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

    	ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * ??????????????????,???????????? ????????????
     * @param response      ?????????response
     * @param json          ????????????
     * @throws ServiceException
     */
    @RequestMapping("/modules/manage/system/config/delete.htm")
    @RequiresPermission(code = "modules:manage:system:config:delete", name = "????????????-??????????????????- ????????????")
    public void deleteSysConfig(HttpServletRequest request,HttpServletResponse response,
    	@RequestParam(value = "id" )String id,
    	@RequestParam(value = "status" )int status
    	)throws Exception {
	     Map<String,Object> returnMap=new HashMap<String,Object>();
	     long flag;
        SysConfig sysConfig = new SysConfig();
		 //????????????
        	sysConfig.setId(Long.valueOf(id));
        	sysConfig.setStatus(status);
			flag=sysConfigService.updateSysConfig(sysConfig);
		
		if(flag>0){//????????????????????????
        	returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS+",?????????????????????");
        }else{
        	returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
        	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        //???????????????
        ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * ???????????????????????????
     * 
     * @throws Exception
     */
    @RequestMapping("/modules/manage/system/config/reload.htm")
    @RequiresPermission(code = "modules:manage:system:config:reload", name = "????????????-??????????????????-?????????????????????")
    public void reload() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // ????????????????????? ???????????????????????????
        CacheUtil.initSysConfig();

        String serverWeighs = Global.getValue("server_weigh");
		String[] splits = serverWeighs.split(",");
		String webResult = null;
		for (int i = 0; i < splits.length; i++) {
			String webCleanUrl = splits[i] + "/system/config/reload.htm";
			//??????????????????
			try {
				webResult = HttpUtil.getHttpResponse(webCleanUrl);
				logger.info("??????api????????????:" + webResult);
			} catch (Exception e) {
				logger.info("??????api????????????");
				logger.error(e.getMessage(),e);
			}
		}
        
        if(StringUtil.isNotBlank(webResult)){
        	@SuppressWarnings("unchecked")
			Map<String, Object> result = JsonUtil.parse(webResult, Map.class);
			String resultCode = StringUtil.isNull(result.get(Constant.RESPONSE_CODE));
        	if (StringUtil.isNotBlank(resultCode)
        			&& StringUtil.isNull(Constant.SUCCEED_CODE_VALUE).equals(resultCode)) {
        		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        	}else{
        		returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
        		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        	}
        }else{
        	returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        	returnMap.put(Constant.RESPONSE_CODE_MSG, "????????????????????????,????????????????????????");
        }

        // ???????????????
        ServletUtils.writeToResponse(response, returnMap);
    }
}

