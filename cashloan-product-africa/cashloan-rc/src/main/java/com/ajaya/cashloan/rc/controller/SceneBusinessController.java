package com.ajaya.cashloan.rc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.model.ManageSceneBusinessModel;
import com.ajaya.cashloan.rc.service.SceneBusinessService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.rc.domain.SceneBusiness;

/**
 * 场景与第三方征信接口关联关系Controller
 * 
 * @author zlh
 * @version 1.0.0
 * @date 2017-03-14 13:42:36
 */
@Controller
@Scope("prototype")
public class SceneBusinessController extends BaseController {

	@Resource
	private SceneBusinessService sceneBusinessService;

	/**
	 * 第三方征信接口分页查询
	 *
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping("/modules/manage/credit/sceneBusiness/page.htm")
	public void page(
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JSONObject.parseObject(search);
		Page<ManageSceneBusinessModel> page = sceneBusinessService.page(params, currentPage,pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	

	/**
	 * 添加第三方征信接口信息
	 * 
	 * @param tpp
	 */
	@RequestMapping("/modules/manage/credit/sceneBusiness/save.htm")
	public void save(SceneBusiness sceneBusiness) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//数据校验
		boolean flag = sceneBusinessService.validExist(sceneBusiness.getScene(),sceneBusiness.getBusinessId(),sceneBusiness.getType());
		if(flag){
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "场景接口关联关系已经存在");
			ServletUtils.writeToResponse(response, result);
			return;
		}else{
			flag = sceneBusinessService.save(sceneBusiness );
		}
		
		if (flag) {
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 修改第三方征信接口信息
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping("/modules/manage/credit/sceneBusiness/update.htm")
	public void update(SceneBusiness sceneBusiness) {
		boolean flag = sceneBusinessService.update(sceneBusiness);

		Map<String, Object> result = new HashMap<String, Object>();
		if (flag) {

			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {

			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, result);
	}

	


	/**
	 * 启用第三方征信接口
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping("/modules/manage/credit/sceneBusiness/enable.htm")
	public void enable(@RequestParam(value = "id") Long id) {
		boolean flag = sceneBusinessService.enable(id);
		Map<String, Object> result = new HashMap<String, Object>();
		if (flag) {

			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {

			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 禁用第三方征信接口
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping("/modules/manage/credit/sceneBusiness/disable.htm")
	public void disable(@RequestParam(value = "id") Long id) {
		boolean flag = sceneBusinessService.disable(id);

		Map<String, Object> result = new HashMap<String, Object>();
		if (flag) {

			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {

			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, result);
	}
	
}
