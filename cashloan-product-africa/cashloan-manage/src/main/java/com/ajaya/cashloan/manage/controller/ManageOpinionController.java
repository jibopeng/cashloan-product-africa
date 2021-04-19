/*package com..cashloan.manage.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import Opinion;
import com..cashloan.cl.domain.UserFkReqLog;
import com..cashloan.cl.domain.UserRuleAppsTemp;
import com..cashloan.cl.domain.UserRuleContactsTemp;
import com..cashloan.cl.domain.UserZiYouDataTemp;
import ClBorrowMapper;
import com..cashloan.cl.mapper.UserFkReqLogMapper;
import com..cashloan.cl.mapper.UserRuleAppsTempMapper;
import com..cashloan.cl.mapper.UserRuleContactsTempMapper;
import com..cashloan.cl.mapper.UserZiYouDataTempMapper;
import com..cashloan.cl.mapper.UserZiYouDataTempNowMapper;
import OpinionModel;
import ClBorrowService;
import OpinionService;
import com..cashloan.cl.service.UserModelScoreAlgoV2Service;
import com..cashloan.cl.service.UserModelScoreZiYouService;
import com..cashloan.cl.service.UserRuleAlGo360DataService;
import com..cashloan.cl.service.UserRuleAppsService;
import com..cashloan.cl.service.UserRuleContactsService;
import Constant;
import Global;
import DateUtil;
import HttpClientUtil;
import JsonUtil;
import ListUtil;
import RdPage;
import ServletUtils;
import StringUtil;
import Borrow;
import SceneBusinessLog;
import SceneBusinessLogMapper;
import SysConfig;
import SysUser;
import SysConfigService;

 *//**
 * 意见反馈表Controller
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 11:30:57

 *//*
@Scope("prototype")
@Controller
public class ManageOpinionController extends ManageBaseController {

	public static final Logger logger = LoggerFactory.getLogger(ManageOpinionController.class);
	@Resource
	private OpinionService opinionService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modules/manage/mine/opinion/page.htm")
	public void page(
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		Map<String,Object> searchMap = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)){
        	searchMap = JsonUtil.parse(searchParams, Map.class);
        }
		Page<OpinionModel> page = opinionService.page(searchMap, current, pageSize);
		if(page != null){
			List<OpinionModel> list = page.getResult();
			if(list != null && list.size() > 0){
				for(OpinionModel om : list){
					om.setPhone(null);
				}
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("list", page);
		resultMap.put(Constant.RESPONSE_DATA, data);
		resultMap.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		resultMap.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,resultMap);
	}
	
	@RequestMapping(value="/modules/manage/mine/opinion/view.htm")
	public void view(@RequestParam(value = "id",required=false) Long id) throws Exception{
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Opinion opinion = opinionService.getById(id);
		if(opinion !=null ){
			resultMap.put(Constant.RESPONSE_DATA, opinion);
			resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		}else{
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "获取意见反馈失败");
		}
		ServletUtils.writeToResponse(response,resultMap);
	}
	
	@RequestMapping(value="/modules/manage/mine/opinion/confirm.htm", method = RequestMethod.POST)
	public void opinionConfirm(
			@RequestParam(value = "id",required=true) Long id,
			@RequestParam(value = "feedback",required=true) String feedback
			) throws Exception{
		Map<String,Object> paramMap = new HashMap<String, Object>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		SysUser user = getLoginUser(request);
		if (user!=null) {
			paramMap.put("id", id);
			paramMap.put("sysUserId", getLoginUser(request).getId());
			paramMap.put("feedback", feedback);
			paramMap.put("confirmTime", DateUtil.dateStr3(DateUtil.getNow()));
			paramMap.put("state", OpinionModel.STATE_CONFIRMED);
			int result = opinionService.updateSelective(paramMap);
			if(result == 1){
				resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, "处理成功");
			}else{
				resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, "处理失败");
			}
		}else {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "处理失败,登陆已失效");
		}
		ServletUtils.writeToResponse(response, resultMap);
	}
	
	
	@Resource
	private SysConfigService sysConfigServiceImpl;
	@RequestMapping(value="/modules/algoTest.htm")
	public void algoTest(Long userId) throws Exception{
		 HashMap<String, String> header = new HashMap<>(8);
	        String getCustomerInformationUrl = Global.getValue("get_customer_information_url");
	        //获取令牌
	        SysConfig config = sysConfigServiceImpl.findByCode("algo_access_token");
	        String accessToken = config.getValue();
	        int count = 0;
	        logger.info("用户 {} 获取access_token：{}", userId, accessToken);
	        //获取用户报告信息
	        header.clear();
	        header.put("Authorization", "Bearer " + accessToken);
	        String getResult = HttpClientUtil.sentGet(getCustomerInformationUrl + "/" + userId, header, null, "utf-8");
	        logger.info(getResult);
	}
	
	@Resource
	private ClBorrowMapper clBorrowMaper;
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private UserRuleAlGo360DataService userRuleAlGo360DataService;
	@Resource
	private SceneBusinessLogMapper sceneBusinessLogMapper;
	@RequestMapping(value="/modules/algoCallback.htm")
	public void algoCallback() throws Exception{
		List<Long> userIds = new ArrayList<>();
		String pathname = ListUtil.class.getResource("/config/file/" + "userIdsAlgo" + ".txt").getFile();
		File filename = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			userIds.add(Long.parseLong(line));
		}
		
		if(userIds != null && userIds.size() > 0){
			for (Long userId : userIds) {
				//调用algo 如果成功
				try {
					userRuleAlGo360DataService.reportHandleNew(userId.toString());
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	
	@Resource
	private UserRuleAppsService userRuleAppsService;
	@Resource
	private UserFkReqLogMapper userFkReqLogMapper;
	@Resource
	private UserRuleAppsTempMapper userRuleAppsTempMapper;
	@RequestMapping(value="/modules/appRule.htm")
	public void appRule() throws Exception{
		List<Long> borrowIds = new ArrayList<>();
		String pathname = ListUtil.class.getResource("/config/file/" + "borrowIds" + ".txt").getFile();
		File filename = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			borrowIds.add(Long.parseLong(line));
		}
		for (Long borrowId : borrowIds) {
			Borrow borrow = clBorrowService.findByPrimary(borrowId);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("borrowId", borrowId);
			paramMap.put("version", "appModel");
			//查询当时线上跑的
			UserFkReqLog fkReqLog = userFkReqLogMapper.findSelective(paramMap);
			String paramJson = fkReqLog.getParamJson();
			UserRuleAppsTemp userRuleAppsTemp = JSON.parseObject(paramJson, UserRuleAppsTemp.class);
			userRuleAppsTemp.setUserId(borrow.getUserId());
			userRuleAppsTemp.setCreateTime(new Date());
			int save = userRuleAppsTempMapper.save(userRuleAppsTemp);
			logger.info("borrowId:" + borrowId + ",保存结果:" + save);
		}
	}
	
	@Resource
	private UserZiYouDataTempMapper userZiYouDataTempMapper;
	@RequestMapping(value="/modules/ziyouRule.htm")
	public void ziyouRule() throws Exception{
		List<Long> borrowIds = new ArrayList<>();
		String pathname = ListUtil.class.getResource("/config/file/" + "borrowIds" + ".txt").getFile();
		File filename = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			borrowIds.add(Long.parseLong(line));
		}
		for (Long borrowId : borrowIds) {
			Borrow borrow = clBorrowService.findByPrimary(borrowId);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("borrowId", borrowId);
			paramMap.put("version", "ziyouModel");
			//查询当时线上跑的
			UserFkReqLog fkReqLog = userFkReqLogMapper.findSelective(paramMap);
			String paramJson = fkReqLog.getParamJson();
			UserZiYouDataTemp userRuleZiYouTemp = JSON.parseObject(paramJson, UserZiYouDataTemp.class);
			userRuleZiYouTemp.setUserId(borrow.getUserId());
			userRuleZiYouTemp.setCreateTime(new Date());
			int save = userZiYouDataTempMapper.save(userRuleZiYouTemp);
			logger.info("borrowId:" + borrowId + ",保存结果:" + save);
		}
	}
	
	
	
	@Resource
	private UserZiYouDataTempNowMapper userZiYouDataTempNowMapper;
	@Resource
	private UserModelScoreZiYouService userModelScoreZiYouService;
	@Resource
	private UserRuleContactsService userRuleContactsService;
	@RequestMapping(value="/modules/ziyouRuleNow.htm")
	public void ziyouRuleNow() throws Exception{
		List<Long> borrowIds = new ArrayList<>();
		String pathname = ListUtil.class.getResource("/config/file/" + "borrowIds" + ".txt").getFile();
		File filename = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			borrowIds.add(Long.parseLong(line));
		}
		for (Long borrowId : borrowIds) {
			Borrow borrow = clBorrowService.findByPrimary(borrowId);
			userRuleContactsService.saveUserContactsRule(borrow.getUserId(), borrowId);
		}
	}
	
	
	@RequestMapping(value="/modules/sescelong.htm")
	public void sescelong() throws Exception{
		List<Long> borrowIds = new ArrayList<>();
		String pathname = ListUtil.class.getResource("/config/file/" + "borrowIds" + ".txt").getFile();
		File filename = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			borrowIds.add(Long.parseLong(line));
		}
		for (Long borrowId : borrowIds) {
			clBorrowService.syncSceneBusinessLog(borrowId, "laGo360Report", 1);
		}
	}
	
	@Resource
    private UserRuleContactsTempMapper userRuleContactsTempMapper;
	@RequestMapping(value="/modules/fkModel.htm")
	public void fkModel() throws Exception{
		List<Long> fkids = new ArrayList<>();
		String pathname = ListUtil.class.getResource("/config/file/" + "fkId" + ".txt").getFile();
		File filename = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			fkids.add(Long.parseLong(line));
		}
		for (Long fkId : fkids) {
			UserRuleContactsTemp ruleContactsTemp = new UserRuleContactsTemp();
			UserFkReqLog fkReqLog = userFkReqLogMapper.findByPrimary(fkId);
			ruleContactsTemp.setUserId(fkReqLog.getUserId());
			String paramJson = fkReqLog.getParamJson();
			Map<String, Object> param = (Map<String, Object>) JSON.parse(paramJson);
			Map<String, Object> modelMap = (Map<String, Object>) JSON.parse(param.get("os_pd_model").toString());
			String intimate_contact_num = modelMap.get("intimate_contact_num").toString();
			if(StringUtil.isNotBlank(intimate_contact_num)){
				ruleContactsTemp.setIntimateContactNum(Integer.parseInt(intimate_contact_num));
			}
			ruleContactsTemp.setCreateTime(new Date());
			int save = userRuleContactsTempMapper.save(ruleContactsTemp);
			logger.info("fkid:" + fkId);
		}
	}
	
	
	
	@Resource
	private UserModelScoreAlgoV2Service userModeScoreAlgoV2Service;
	@RequestMapping(value="/modules/algoV2.htm")
	public void algoV2() throws Exception{
		List<Long> borrowIds = new ArrayList<>();
		String pathname = ListUtil.class.getResource("/config/file/" + "borrowIds" + ".txt").getFile();
		File filename = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			borrowIds.add(Long.parseLong(line));
		}
		for (Long borrowId : borrowIds) {
			userModeScoreAlgoV2Service.saveUserModelScoreAlgoV2(borrowId);
		}
	}
}
*/