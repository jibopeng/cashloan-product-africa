package com.ajaya.cashloan.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ajaya.cashloan.system.service.SysUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.SysUserOperationRecord;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrder;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrderLog;
import com.ajaya.cashloan.cl.model.ManageBorrowModel;
import com.ajaya.cashloan.cl.model.UrgeRepayOrderModel;
import com.ajaya.cashloan.cl.service.BorrowRepayService;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.SysUserOperationRecordService;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderLogService;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.core.model.CloanUserModel;
import com.ajaya.cashloan.core.service.CloanUserService;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.mapper.SysUserMapper;
import com.ajaya.cashloan.system.permission.annotation.RequiresPermission;
import com.ajaya.cashloan.system.service.SysDictService;

import tool.util.DateUtil;

 /**
 * ?????????????????????Controller
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-03-07 14:21:58

 */
@Scope("prototype")
@Controller
public class ManageUrgeRepayOrderController extends ManageBaseController {

	private static final Logger logger = Logger.getLogger(ManageUrgeRepayOrderController.class);
	
	@Resource
	private UrgeRepayOrderService urgeRepayOrderService;
	
	@Resource
	private UrgeRepayOrderLogService urgeRepayOrderLogService;

	@Resource
	private BorrowRepayService borrowRepayService;

	@Resource 
	private SysUserOperationRecordService sysUserOperationRecordService;
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private CloanUserService cloanUserService;
	@Resource
	private SysUserService sysUserService;

	 /**
	  * ??????????????????
	  * @param request  ????????????
	  * @param searchParams ????????????
	  * @param current ????????????
	  * @param pageSize ????????????
	  * @throws Exception ??????
	  */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/manage/borrow/repay/urge/list.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:list",name = "????????????????????????")
	public void list(HttpServletRequest request,@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception{
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
 		SysUser sysUser=getLoginUser(request);
		if(sysUser!=null){
			if(params==null){
				params=new HashMap<>();
			}
		} 
		Page<UrgeRepayOrder> page =urgeRepayOrderService.list(params,current,pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "????????????");
		ServletUtils.writeToResponse(response,result);
	}

	 /**
	  * ??????????????????
	  * @param request  ????????????
	  * @param searchParams ????????????
	  * @param current ????????????
	  * @param pageSize ????????????
	  */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/manage/borrow/repay/urge/loglist.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:loglist",name = "????????????????????????")
	public void loglist(HttpServletRequest request, @RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
	 	SysUser sysUser=getLoginUser(request);
		if(sysUser!=null){
			if(params==null){
				params=new HashMap<>();
			}
		} 
		Page<UrgeRepayOrderModel> page =urgeRepayOrderService.listModel(params,current,pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "????????????");
		ServletUtils.writeToResponse(response,result);
	}

	 /**
	  * ????????????????????????
	  * @param roleName ????????????
	  * @throws Exception ??????
	  */
	@RequestMapping(value="/modules/manage/borrow/repay/urge/sysUserlist.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:sysUserlist",name = "????????????????????????")
	public void sysUserlist(@RequestParam(value = "roleName",required=false) String roleName)throws Exception {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (null == roleName) {
			params.put("roleName", "CollectionSpecialist");
		} else {
			params.put("roleName", roleName);
		}
		List<Map<String, Object>> users = sysUserService.getUserInfo(params);
		responseMap.put("data", users);
		responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, responseMap);
	}

	 /**
	  * ????????????????????????
	  * @param id  id
	  * @param userName ????????????
	  * @throws Exception ??????
	  */
	@RequestMapping(value="/modules/manage/borrow/repay/urge/allotUser.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:allotUser",name = "????????????????????????")
	public void allotUser(
			String id,
			String userName)throws Exception {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		//????????????????????????????????????
		Map<String, Object> paramMapUserName = new HashMap<>();
		paramMapUserName.put("name", userName);
		SysUser sysUserName = sysUserMapper.findSelective(paramMapUserName);
		if(sysUserName == null){
			responseMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, "?????????????????????");
			ServletUtils.writeToResponse(response, responseMap);
			return;
		}
		Long userId = sysUserName.getId();
		long[] ids = StringUtil.toLongs(id.split(","));
		int msg = 0;
		for (long id1 : ids) {
			Map<String, Object> params = new HashMap<>();

			//???????????????????????????user_id
			UrgeRepayOrder urgeRepayOrder = urgeRepayOrderService.getById(id1);
			Subject user = SecurityUtils.getSubject();
			SysUser sysUser = (SysUser) user.getSession().getAttribute("SysUser");
			SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
			sysUserOperationRecord.setSysUserId(sysUser.getId());
			sysUserOperationRecord.setSysUserName(sysUser.getName());
			sysUserOperationRecord.setBorrowId(urgeRepayOrder.getBorrowId());
			sysUserOperationRecord.setSysUserResult("??????????????????");
			sysUserOperationRecord.setSysUserOperationSource("????????????????????????");
			sysUserOperationRecord.setSysUserPrepare1(urgeRepayOrder.getBorrowName());
			sysUserOperationRecord.setSysUserPrepare2(userId.toString());
			sysUserOperationRecord.setSysUserRemark(userName);
			sysUserOperationRecord.setCreateTime(new Date());
			int insert = sysUserOperationRecordService.insert(sysUserOperationRecord);
			logger.info("????????????id:" + id1 + "????????????????????????:" + insert);

			//??????????????????????????????,??????????????????????????????
			Long borrowId = urgeRepayOrder.getBorrowId();
			Borrow borrow = clBorrowService.findByPrimary(borrowId);
			if ("40".equals(borrow.getState())) {
				//????????????????????????,???????????????????????????
				params.put("id", id1);
				params.put("userId", 14L);
				params.put("userName", "?????????");
				params.put("state", UrgeRepayOrderModel.STATE_ORDER_SUCCESS);

			} else {
				params.put("id", id1);
				params.put("userId", userId);
				params.put("userName", userName);
				params.put("state", UrgeRepayOrderModel.STATE_ORDER_WAIT);
			}
			msg = urgeRepayOrderService.orderAllotUser(params);
		}
		if (msg > 0) {
			responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			responseMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, responseMap);
	}

	 /**
	  * ????????????????????????
	  * @param id id
	  */
	@RequestMapping(value="/modules/manage/borrow/repay/urge/listDetail.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:listDetail",name = "????????????????????????")
	public void listDetail(@RequestParam(value = "id") Long id) {
		Map<String, Object> data = new HashMap<String, Object>();
		UrgeRepayOrder order = urgeRepayOrderService.getById(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dueId", id);
		List<UrgeRepayOrderLog> logs = urgeRepayOrderLogService.getLogByParam(params);
		data.put("order", order);
		data.put("logs", logs);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);

		result.put(Constant.RESPONSE_CODE_MSG, "????????????");
		ServletUtils.writeToResponse(response,result);
	}

	 /**
	  * ?????????????????????
	  * @param searchParams
	  * @param currentPage
	  * @param pageSize
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/manage/borrow/repay/urge/borrowlist.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:borrowlist",name = "????????????????????????")
	public void borrowlist(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		if (params == null) {
			params = new HashMap<>();
		}
		params.put("state", BorrowModel.STATE_DELAY);
		List<UrgeRepayOrder> list = urgeRepayOrderService.listAll(new HashMap<String, Object>());
		List<Long> idList = new ArrayList<Long>();
		if (list != null && list.size() > 0) {
			params.put("type", "urge");
			for (UrgeRepayOrder or : list) {
				idList.add(or.getBorrowId());
			}
			params.put("idList", idList);
		} else {
			params.put("type", "");
		}

		Page<ManageBorrowModel> page = borrowRepayService.listModelNotUrge(params, currentPage, pageSize);
		responseMap.put(Constant.RESPONSE_DATA, page);
		responseMap.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, responseMap);
	}

	 /**
	  *  ????????????????????????
	  * @param id id
	  */
	@RequestMapping(value="/modules/manage/borrow/repay/urge/addOrder.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:addOrder",name = "????????????????????????")
	public void addOrder(@RequestParam(value = "id") Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultmap = urgeRepayOrderService.saveOrder(id);
		result.put(Constant.RESPONSE_CODE, resultmap.get("code"));
		result.put(Constant.RESPONSE_CODE_MSG, resultmap.get("msg"));
		ServletUtils.writeToResponse(response,result);
	}

	 /**
	  * ??????????????????????????????
	  * @param id id
	  * @param state ?????????
	  */
	@RequestMapping(value="/modules/manage/borrow/repay/urge/updateOrderState.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:updateOrderState",name = "??????????????????????????????")
	public void addOrder(@RequestParam(value = "id") Long id,@RequestParam(value = "state") String state) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("state", state);
		urgeRepayOrderService.updateLate(param);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "????????????");
		ServletUtils.writeToResponse(response,result);
	}

	 /**
	  * ????????????????????????
	  * @param dueId ????????????id
	  * @param createTime ????????????
	  * @param state   ??????
	  * @param remark ??????
	  * @param promiseTime
	  * @param way
	  * @param answerState
	  */
	@RequestMapping(value="/modules/manage/borrow/repay/urge/saveOrderInfo.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:urge:saveOrderInfo",name = "????????????????????????")
	public void saveOrderInfo(@RequestParam(value = "dueId") Long dueId,
			@RequestParam(value = "createTime") String createTime,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "remark") String remark,
			@RequestParam(value = "promiseTime",required=false) String promiseTime,
			@RequestParam(value = "way") String way,
			@RequestParam(value = "answerState") String answerState) {
		Map<String, Object> result = new HashMap<String, Object>();
		UrgeRepayOrder order = urgeRepayOrderService.getById(dueId);
		UrgeRepayOrderLog orderLog = new UrgeRepayOrderLog();
		if (order != null) {
			 try {
				orderLog.setCreateTime(DateUtil.valueOf(createTime, DateUtil.DATEFORMAT_STR_001));
				if (promiseTime != null && promiseTime != "") {
					orderLog.setPromiseTime(DateUtil.valueOf(promiseTime, DateUtil.DATEFORMAT_STR_001));
				}
				orderLog.setRemark(remark);
				orderLog.setWay(way);
				if (state == null || state == "") {
					orderLog.setState(UrgeRepayOrderModel.STATE_ORDER_URGE);
				} else {
					orderLog.setState(state);
				}
				urgeRepayOrderLogService.saveOrderInfo(orderLog, order,answerState);

				result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "????????????");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "????????????");
			}
		} else {
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "??????????????????");
		}
		ServletUtils.writeToResponse(response,result);
	}
	
	
	/**
	 *??????????????????
	 * @param searchParams ????????????
	 * @param currentPage ????????????
	 * @param pageSize ????????????
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/manage/cl/uruser/list.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:cl:cluser:list",name = "????????????????????????")
	public void list(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		if(params == null){
			params = new HashMap<>();
		}
		//????????????????????????????????????
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		params.put("urUserId", sysUser.getId());
		Page<CloanUserModel> page = cloanUserService.listUrUser(params,currentPage,pageSize);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "????????????");
		ServletUtils.writeToResponse(response,result);
	}
}