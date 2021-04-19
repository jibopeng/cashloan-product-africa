package com.ajaya.cashloan.manage.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;

import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.domain.SysUserOperationRecord;
import com.ajaya.cashloan.cl.model.ManageBorrowModel;
import com.ajaya.cashloan.cl.model.ManageBorrowProgressModel;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.domain.SysUserChannel;
import com.ajaya.cashloan.system.permission.annotation.RequiresPermission;
import com.ajaya.cashloan.system.service.SysUserChannelService;
import com.github.pagehelper.Page;

import tool.util.StringUtil;

/**
 * 借款信息表Controller
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-23 16:26:19
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ManageBorrowController extends ManageBaseController {

	private static final Logger logger = Logger.getLogger(ManageBorrowController.class);

	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private BorrowProgressService borrowProgressService;
	@Resource
	private BorrowRepayLogService borrowRepayLogService;
	@Resource
	private PayLogService payLogService;
	@Resource
	private ClSmsService clSmsService;
	
	@Resource
	private SysUserChannelService sysUserChannelService;

	@Resource SysUserOperationRecordService sysUserOperationRecordService;
	@Resource
	private UserBaseInfoService userBaseInfoServiceImpl;
	@Resource
	private ClBorrowRiskBusinessService clBorrowRiskBusinessService;

	/**
	 * 借款信息列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/list.htm", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:list", name = "借款信息列表")
	public void list(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int current, @RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<ManageBorrowModel> page = clBorrowService.listModel(params, current, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 借款进度列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/progress/list.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:progress:list", name = "借款进度列表")
	public void progresslist(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int currentPage, @RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<ManageBorrowProgressModel> page = borrowProgressService.listModel(params, currentPage, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 人工复审查询
	 * 
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/reviewList.htm", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:reviewList", name = "人工复审通过列表")
	public void reviewList(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int currentPage, @RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List stateList;
		Page<ManageBorrowModel> page = null;
		if (params != null) {
			String state = StringUtil.isNull(params.get("state"));
			if (null != state && !StringUtil.isBlank(state)) {
				if (StringUtil.equals(state, BorrowModel.STATE_PASS)) {
					page = clBorrowService.listReview(params, currentPage, pageSize);
				} else {
					stateList = Arrays.asList(state);
					params.put("stateList", stateList);
					params.put("state", "");
					page = clBorrowService.listModel(params, currentPage, pageSize);
				}
			} else {
				stateList = Arrays.asList(BorrowModel.STATE_NEED_REVIEW, BorrowModel.STATE_REFUSED,
						BorrowModel.STATE_PASS);
				params.put("stateList", stateList);
				params.put("state", "");
				page = clBorrowService.listModel(params, currentPage, pageSize);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 借款审核列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/borrowList.htm", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:borrowList", name = "借款审核状态列表")
	public void borrowList(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int currentPage, @RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List stateList;
		if (params != null) {
			String state = StringUtil.isNull(params.get("state"));
			if (null != state && !StringUtil.isBlank(state)) {
				// 待自动审核
				if (state.equals(BorrowModel.STATE_PRE)) {// 10
					stateList = Arrays.asList(BorrowModel.STATE_PRE);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				// 自动审核失败
				if (state.equals(BorrowModel.STATE_AUTO_REFUSED)) {// 21
					stateList = Arrays.asList(BorrowModel.STATE_AUTO_REFUSED);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				// 人工复审
				if (state.equals(BorrowModel.STATE_NEED_REVIEW)) {// 22
					stateList = Arrays.asList(BorrowModel.STATE_NEED_REVIEW, BorrowModel.STATE_REFUSED,
							BorrowModel.STATE_PASS);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				// 自动审核通过
				if (state.equals(BorrowModel.STATE_AUTO_PASS)) {// 20
					stateList = Arrays.asList(BorrowModel.STATE_AUTO_PASS, BorrowModel.STATE_NEED_REVIEW,
							BorrowModel.STATE_PASS, BorrowModel.STATE_REFUSED, BorrowModel.STATE_REPAY,
							BorrowModel.WAIT_AUDIT_LOAN, BorrowModel.AUDIT_LOAN_PASS, BorrowModel.AUDIT_LOAN_FAIL,
							BorrowModel.STATE_REPAY_FAIL, BorrowModel.STATE_FINISH, BorrowModel.STATE_REMISSION_FINISH,
							BorrowModel.STATE_REPAY_PROCESSING, BorrowModel.STATE_DELAY, BorrowModel.STATE_BAD,  BorrowModel.DAKUAN_ING);
					params.put("stateList", stateList);
					params.put("state", "");
				}
			}
		}
		Page<ManageBorrowModel> page = clBorrowService.listModel(params, currentPage, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 借款还款信息列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/borrowRepayList.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:borrowRepayList", name = "借款还款信息列表 ")
	public void borrowRepayList(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int current, @RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List stateList;
		if (params != null) {
			// 放款列表
			String type = StringUtil.isNull(params.get("type"));
			if ("repay".equals(type)) {
				stateList = Arrays.asList(BorrowModel.STATE_AUTO_PASS, BorrowModel.STATE_PASS,
						BorrowModel.WAIT_AUDIT_LOAN, BorrowModel.AUDIT_LOAN_PASS, BorrowModel.AUDIT_LOAN_FAIL,
						BorrowModel.STATE_REPAY_FAIL, BorrowModel.STATE_REPAY, BorrowModel.STATE_FINISH,
						BorrowModel.STATE_REMISSION_FINISH, BorrowModel.STATE_DELAY, BorrowModel.STATE_BAD,BorrowModel.STATE_CANCEL);
				params.put("stateList", stateList);
				String state = StringUtil.isNull(params.get("state"));
				if (null != state && !StringUtil.isBlank(state)) {
					params.put("state", state);
				}
			}
			String state = StringUtil.isNull(params.get("state"));
			if (null != state && !StringUtil.isBlank(state)) {
				// 还款列表
				if (state.equals(BorrowModel.STATE_FINISH)) {// 40
					stateList = Arrays.asList(BorrowModel.STATE_FINISH, BorrowModel.STATE_REMISSION_FINISH);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				// 逾期中列表
				if (state.equals(BorrowModel.STATE_DELAY)) {// 50
					stateList = Arrays.asList(BorrowModel.STATE_DELAY);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				// 坏账列表
				if (state.equals(BorrowModel.STATE_BAD)) {// 90
					stateList = Arrays.asList(BorrowModel.STATE_BAD);
					params.put("stateList", stateList);
					params.put("state", "");
				}

			}
		}
		
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
		Page<ManageBorrowModel> page = null;
		if(SysUserChannelList != null && SysUserChannelList.size() > 0){
			List<Long> channelIds = new ArrayList<>();
			if(params == null ){
				params = new HashMap<>();
			}
			for (SysUserChannel sysUserChannel : SysUserChannelList) {
				channelIds.add(sysUserChannel.getChannelId());
			}
			params.put("channelIds", channelIds);
			//说明是渠道用户
			page = clBorrowService.listBorrowModel(params, current, pageSize);
		} else {
			page = clBorrowService.listBorrowModel(params, current, pageSize);
			
		}
		// 刘晓亮 end
		
		//刘晓亮 原来的 Page<ManageBorrowModel> page = clBorrowService.listBorrowModel(params, current, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 重新支付
	 * 
	 * @param borrowId
	 */
	@RequestMapping(value = "/modules/manage/borrow/payAgain.htm", method = { RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:payAgain", name = "借款还款信息详细页面    ")
	public void payAgain(@RequestParam(value = "borrowId") long borrowId) {
		Borrow borrow = clBorrowService.getById(borrowId);
		boolean flag = payLogService.judge(borrowId);
		Map<String, Object> result = new HashMap<String, Object>();
		if (null != borrow && flag
				&& (BorrowModel.STATE_AUTO_PASS.equals(borrow.getState())
						|| BorrowModel.STATE_PASS.equals(borrow.getState())
						|| BorrowModel.STATE_REPAY_FAIL.equals(borrow.getState()))) {
			//clBorrowService.borrowLoan(borrow, DateUtil.getNow());
			//系统人员点击 放款失败
			SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
			//获取当前操作人员的user_id
			Subject user = SecurityUtils.getSubject();
			SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
			sysUserOperationRecord.setSysUserId(sysUser.getId());
			sysUserOperationRecord.setSysUserName(sysUser.getName());
			sysUserOperationRecord.setBorrowId(borrowId);
			sysUserOperationRecord.setSysUserResult("人工取消放款");
			sysUserOperationRecord.setCreateTime(new Date());
			sysUserOperationRecord.setSysUserOperationSource("人工取消放款");
			sysUserOperationRecordService.insert(sysUserOperationRecord);
			Long userId = borrow.getUserId();
			if (BorrowModel.STATE_REPAY_FAIL.equals(borrow.getState())){
				//发送取消放款短信（放款多次或者ifSc code被校验住的订单）
				clBorrowService.cancelBorrow(borrow);
			}else{
				borrow.setState("70");
				clBorrowService.updateById(borrow);
				clBorrowService.savePressState(borrow, "70", "");
			}
			clBorrowRiskBusinessService.saveBorrowRiskBusinessForAudit(borrowId);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "此借款状态不允许再次支付");
		}

		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 借款还款信息详细页面
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/borrowRepayContent.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:borrowRepayContent", name = "借款还款信息详细页面    ")
	public void borrowRepayContent(@RequestParam(value = "borrowId") long borrowId) {
		
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
			//说明是渠道用户
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(Constant.RESPONSE_DATA, "");
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
			ServletUtils.writeToResponse(response, result);
			return;
		}
		// 刘晓亮 end
		
		ManageBorrowModel model = clBorrowService.getModelByBorrowId(borrowId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, model);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 查询借款记录
	 * 
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrow/listBorrowLog.htm")
	public void listBorrowLog(@RequestParam(value = "userId") long userId, @RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		Page<ManageBorrowModel> page = clBorrowService.listBorrowModel(params, current, pageSize);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", page.getResult());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response, result);
	}


	/**
	 * 重新发起审核
	 * 
	 * @param request
	 * @param response
	 * @param borrowId
	 */
	@RequestMapping(value = "/modules/manage/borrow/reVerifyBorrowData.htm", method = RequestMethod.POST)
	public void reVerifyBorrowData(@RequestParam(value = "borrowId") String borrowId) throws Exception{
		
		//刘晓亮 start
		//获取当前操作人员的user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//刘晓亮 end
		long[] ids = StringUtil.toLongs(borrowId.split(","));
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < ids.length; i++) {
			clBorrowService.reVerifyBorrowData(ids[i]);
			//刘晓亮 start
			SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
			sysUserOperationRecord.setSysUserId(sysUser.getId());
			sysUserOperationRecord.setSysUserName(sysUser.getName());
			sysUserOperationRecord.setBorrowId(Long.parseLong(ids[i]+""));
			sysUserOperationRecord.setSysUserResult("重新发起审核");
			sysUserOperationRecord.setSysUserOperationSource("后台重新发起审核功能");
			sysUserOperationRecord.setSysUserPrepare1("系统返回log : 提交成功，请等待处理结果");
			sysUserOperationRecord.setCreateTime(new Date());
			sysUserOperationRecordService.insert(sysUserOperationRecord);
			//刘晓亮 end
		}
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "提交成功，请等待处理结果");
		
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 群发短信
	 */
	@RequestMapping(value = "/api/smsBatch.htm")
	public void smsBatch() {
		String id = request.getParameter("ids");
		Map<String, Object> result = new HashMap<String, Object>();
		int r = clSmsService.smsBatch(id, "overdue");
		if (r == 1) {
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "处理结束");
		}
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 群发线下催收短信 - 支付宝
	 */
	@RequestMapping(value = "/api/offlineAlipayRepaySms.htm")
	public void offlineAlipayRepaySms() {
		String id = request.getParameter("ids");
		Map<String, Object> result = new HashMap<String, Object>();
		int r = clSmsService.smsBatch(id, "offlineAlipay");
		if (r == 1) {
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "处理结束");
		}
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 群发线下催收短信 - 银行
	 */
	@RequestMapping(value = "/api/offlineBankRepaySms.htm")
	public void offlineBankRepaySms() {
		String id = request.getParameter("ids");
		Map<String, Object> result = new HashMap<String, Object>();
		int r = clSmsService.smsBatch(id, "offlineBank");
		if (r == 1) {
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "处理结束");
		}
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 审核放款
	 * 
	 * @param borrowId
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrow/auditBorrowLoan.htm")
	public void auditBorrowLoan(@RequestParam(value = "borrowId") Long borrowId,
			@RequestParam(value = "state") String state, @RequestParam(value = "remark") String remark)
					throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SysUser loginUser = getLoginUser(request);
			Long userId = loginUser.getId();

			int msg = clBorrowService.auditBorrowLoan(borrowId, state, remark, userId);
			if (msg == 1) {
				result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "操作完成");
			} else {
				result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e);
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
		}
		ServletUtils.writeToResponse(response, result);
	}


	@Resource
	com.ajaya.cashloan.cl.service.UrgeOrderPushLogService UrgeOrderPushLogService;

	//查询地址  http://3.6.167.50:8082/urge/getPenaltyByOrderNo.htm?orderNo=02006911383
	@RequestMapping("/urge/getPenaltyByOrderNo.htm")
	public void getPenaltyByOrderNo() throws Exception {
		ServletInputStream inputStream = request.getInputStream();
		String parseRequestToJson = ReqToJsonUtil.parseRequestToJson(inputStream);
		JSONObject parseObject = JSON.parseObject(parseRequestToJson);
		Map<String, Object> result = new HashMap<>(8);
		result.put(Constant.RESPONSE_DATA, UrgeOrderPushLogService.getPenaltyAmountByOrderNo(parseObject.get("orderNo").toString()));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 后台人工复审功能
	 * 
	 * @param borrowId
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrow/verifyBorrow.htm")
	public void verifyBorrow(@RequestParam(value = "borrowId") Long borrowId,
			@RequestParam(value = "state") String state, @RequestParam(value = "remark") String remark)
					throws Exception {
		
		//刘晓亮 start
		//系统人员点击 后台人工复审 
		SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
		//获取当前操作人员的user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		Map<String, Object> result = new HashMap<String, Object>();
		
		sysUserOperationRecord.setSysUserId(sysUser.getId());
		sysUserOperationRecord.setSysUserName(sysUser.getName());
		sysUserOperationRecord.setBorrowId(borrowId);
		if("26".equals(state)){
			//系统人员点击的是同意放款
			sysUserOperationRecord.setSysUserResult("人工复审同意");
		}
		if("27".equals(state)){
			//系统人员点击的是拒绝放款
			sysUserOperationRecord.setSysUserResult("人工复审拒绝");
		}
		sysUserOperationRecord.setSysUserRemark(remark);
		sysUserOperationRecord.setSysUserOperationSource("后台人工复审功能");
		//刘晓亮 end
		
		
		try {
			int msg = clBorrowService.manualVerifyBorrow(borrowId, state, remark);
			clBorrowRiskBusinessService.saveBorrowRiskBusinessForAudit(borrowId);
			if (msg == 1) {
				result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "复审成功");
				//刘晓亮 start
				sysUserOperationRecord.setSysUserPrepare1("系统返回log : 复审成功");
				//刘晓亮 end
			} else {
				result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "复审失败");
				//刘晓亮 start
				sysUserOperationRecord.setSysUserPrepare1("系统返回log : 复审失败");
				//刘晓亮 end
			}
		} catch (Exception e) {
			logger.error(e);
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
			//刘晓亮 start
			sysUserOperationRecord.setSysUserPrepare1("系统返回log : " + e.getMessage());
			//刘晓亮 end
		}
		//刘晓亮 end
		sysUserOperationRecord.setCreateTime(new Date());
		sysUserOperationRecordService.insert(sysUserOperationRecord);
		//刘晓亮 end
		ServletUtils.writeToResponse(response, result);
	}

}
