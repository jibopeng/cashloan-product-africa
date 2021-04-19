package com.ajaya.cashloan.manage.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.domain.BorrowProgress;
import com.ajaya.cashloan.cl.domain.PayLog;
import com.ajaya.cashloan.cl.model.ManageBorrowModel;
import com.ajaya.cashloan.cl.model.PayLogModel;
import com.ajaya.cashloan.cl.service.BorrowProgressService;
import com.ajaya.cashloan.cl.service.BorrowRepayLogService;
import com.ajaya.cashloan.cl.service.BorrowRepayService;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.cl.service.PayLogService;
import com.ajaya.cashloan.cl.service.SysUserOperationRecordService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.system.permission.annotation.RequiresPermission;
import com.ajaya.cashloan.system.service.SysUserChannelService;
import com.github.pagehelper.Page;

/**
 * 财务打款Controller
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-23 16:26:19
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ManageBorrowPayController extends ManageBaseController {

	private static final Logger logger = Logger.getLogger(ManageBorrowPayController.class);

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
	@Resource 
	private SysUserOperationRecordService sysUserOperationRecordService;
	@Resource
	private BorrowRepayService borrowRepayService;
	

	/**
	 * 借款还款信息列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/pay/borrowRepayList.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:borrowRepayList", name = "借款还款信息列表 ")
	public void borrowRepayList(@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int current, @RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		if(params == null ){
			params = new HashMap<>();
		}
		List stateList;
		stateList = Arrays.asList(BorrowModel.STATE_AUTO_PASS,BorrowModel.STATE_PASS,BorrowModel.WAIT_AUDIT_LOAN, "29");
		params.put("stateList", stateList);
		
		//刘晓亮 原来的 
		Page<ManageBorrowModel> page = clBorrowService.listBorrowModel(params, current, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 获取银行卡信息,便于财务打账
	 * 
	 * @param borrowId
	 */
	@RequestMapping(value = "/modules/manage/borrow/pay/getBankInfo.htm", method = { RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:payAgain", name = "借款还款信息详细页面    ")
	public void payAgain(@RequestParam(value = "borrowId") long borrowId) {
		
	}

	/**
	 * 财务打款后的数据操作
	 * 
	 * @param borrowId
	 */
	@RequestMapping(value = "/modules/manage/borrow/pay/confirm.htm", method = { RequestMethod.POST })
	public void confirm(@RequestParam(value = "id") long borrowId) {
		
	}

}
