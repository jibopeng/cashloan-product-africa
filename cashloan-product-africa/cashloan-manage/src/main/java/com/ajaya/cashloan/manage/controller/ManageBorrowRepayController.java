package com.ajaya.cashloan.manage.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ajaya.cashloan.cl.domain.*;
import com.ajaya.cashloan.cl.model.*;
import com.ajaya.cashloan.cl.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.mapper.BorrowRepayFqMapper;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.BussinessException;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.permission.annotation.RequiresPermission;

import tool.util.DateUtil;

/**
 * 还款计划表和还款记录Controller
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-24 14:02:41
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked" })
public class ManageBorrowRepayController extends ManageBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManageBorrowRepayController.class);
	@Resource
	private BorrowRepayService borrowRepayService;
	@Resource
	private SysUserOperationRecordService sysUserOperationRecordService;
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private PayLogService payLogService;
	@Resource
	private BorrowRepayFqService borrowRepayFqService;
	@Resource
	private BorrowRepayLogFqService borrowRepayLogFqService;
	@Resource
	private ClBorrowRiskBusinessService clBorrowRiskBusinessService;
	/**
	 * 还款计划列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/list.htm")
	@RequiresPermission(code = "modules:manage:borrow:repay:list", name = "还款信息列表")
	public void list(
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<ManageBRepayModel> page = borrowRepayService.listModel(params,
				currentPage, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 确认还款
	 * @param id  还款计划id
	 * @param amount  还款金额
	 * @param penaltyAmout   逾期罚息
	 * @param repayTime  还款时间
	 * @param repayWay   还款方式
	 * @param serialNumber 流水号
	 * @param repayAccount 还款账号
	 * @param state 正常还款  10  ，金额减免 20
	 * @throws  
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/confirmRepay.htm", method = {RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:repay:confirmRepay", name = "确认还款")
	public void confirmRepay(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "amount", required = false) String amount,
			@RequestParam(value = "penaltyAmout", required = false) String penaltyAmout,
			@RequestParam(value = "repayTime") String repayTime,
			@RequestParam(value = "repayWay") String repayWay,
			@RequestParam(value = "serialNumber") String serialNumber,
			@RequestParam(value = "repayAccount") String repayAccount,
			@RequestParam(value = "state") String state) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			param.put("repayTime",DateUtil.valueOf(repayTime, DateUtil.DATEFORMAT_STR_001));
			param.put("repayWay", repayWay);
			param.put("repayAccount", repayAccount);
			param.put("amount", amount);
			param.put("serialNumber", serialNumber);
			param.put("penaltyAmout", penaltyAmout);
			param.put("state", state);
			BorrowRepay br = borrowRepayService.getById(id);
			if (br != null) {
				if (!br.getState().equals(BorrowRepayModel.STATE_REPAY_YES)) {
						//操作paylog表
					   String orderNo = "RPT" + System.currentTimeMillis() + "B" + br.getBorrowId();
					   Double pay_amount = Double.parseDouble(amount);
					   logger.info("penaltyAmout---" + penaltyAmout);
					   if(StringUtil.isNotBlank(penaltyAmout)){
						   pay_amount = new BigDecimal(pay_amount).add(new BigDecimal(penaltyAmout)).doubleValue();
					   }
					   logger.info("pay_amount---" + pay_amount);
					   int i = payLogService.savePayLog(orderNo, br.getUserId(), br.getBorrowId(), pay_amount , "", "", "20", "",PayLogModel.TYPE_OFFLINE_PAYMENT);
					   logger.info("borrowId:" + br.getBorrowId() + ",生成payLog表返回结果:" + i);
					   PayLog payLog = payLogService.findByOrderNo(orderNo);
					   Boolean modifyPayLog = modifyPayLog(payLog.getId(), PayLogModel.STATE_PAYMENT_SUCCESS, serialNumber);
					   logger.info("borrowId:" + br.getBorrowId() + ",更新payLog表返回结果:" + modifyPayLog);
					   param.put("serialNumber", orderNo);
					   resultMap = borrowRepayService.confirmRepay(param);
					   clBorrowRiskBusinessService.saveBorrowRiskBusinessForRepay(br.getBorrowId());

					//添加还款同步信息表
					 //获取当前操作人员的user_id
					Subject user = SecurityUtils.getSubject();
					SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
					SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
					sysUserOperationRecord.setSysUserId(sysUser.getId());
					sysUserOperationRecord.setSysUserName(sysUser.getName());
					sysUserOperationRecord.setBorrowId(br.getBorrowId());
					sysUserOperationRecord.setSysUserResult("后台确认还款");
					sysUserOperationRecord.setSysUserOperationSource("后台确认还款催收功能");
					sysUserOperationRecord.setCreateTime(new Date());
					int insert = sysUserOperationRecordService.insert(sysUserOperationRecord);
					   
				} else {
					resultMap.put("Code", Constant.FAIL_CODE_VALUE);
					resultMap.put("Msg", "该还款计划已还款");
				}
			} else {
				resultMap.put("Code", Constant.FAIL_CODE_VALUE);
				resultMap.put("Msg", "还款计划不存在");
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
			resultMap.put("Code", Constant.FAIL_CODE_VALUE);
			resultMap.put("Msg", "还款失败");
		}
		result.put(Constant.RESPONSE_CODE, resultMap.get("Code"));
		result.put(Constant.RESPONSE_CODE_MSG, resultMap.get("Msg"));
		ServletUtils.writeToResponse(response, result);
	}


	/**
	 * 确认分期还款
	 * @param id  还款计划id
	 * @param amount  还款金额
	 * @param penaltyAmout   逾期罚息
	 * @param repayTime  还款时间
	 * @param repayWay   还款方式
	 * @param serialNumber 流水号
	 * @param repayAccount 还款账号
	 * @param state 正常还款  10  ，金额减免 20
	 * @throws  
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/confirmRepayFq.htm", method = {RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:repay:confirmRepayFq", name = "确认分期还款")
	public void confirmRepayFq(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "repayTime") String repayTime,
			@RequestParam(value = "repayWay") String repayWay,
			@RequestParam(value = "serialNumber") String serialNumber,
			@RequestParam(value = "repayAccount") String repayAccount,
			@RequestParam(value = "state") String state) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			BorrowRepay br = borrowRepayService.getById(id);
			if (br != null) {
				//查询当前最近一笔有效的分期还款
				BorrowRepayFq borrowRepayFq = borrowRepayFqMapper.getCurrentBorrowRepayFq(br.getBorrowId());
				if(borrowRepayFq != null){
					//要录入的就是这笔金额
					String payOrderNo = "RPT" + System.currentTimeMillis() + "B" + br.getBorrowId() + "PFQ" + borrowRepayFq.getId();
					borrowRepayLogFqService.repaymentNotifyFq(borrowRepayFq.getId(), payOrderNo, borrowRepayFq.getAmount());
					int i = payLogService.savePayLog(payOrderNo, br.getUserId(), br.getBorrowId(), borrowRepayFq.getAmount() , "", "", "20", borrowRepayFq.getId() + "",PayLogModel.TYPE_OFFLINE_PAYMENT);
					logger.info("borrowId:" + br.getBorrowId() + ",生成payLog表返回结果:" + i);
					PayLog payLog = payLogService.findByOrderNo(payOrderNo);
					Boolean modifyPayLog = modifyPayLog(payLog.getId(), PayLogModel.STATE_PAYMENT_SUCCESS, serialNumber);
					logger.info("borrowId:" + br.getBorrowId() + ",更新payLog表返回结果:" + modifyPayLog);
					//判断是否已经结清
					Double shengXiaRepayMoney = borrowRepayFqService.getShengXiaRepayMoney(br.getBorrowId());
                	if(Double.compare(shengXiaRepayMoney, 0.0) <= 0){
                		logger.info("payOrderNo:" + payOrderNo + ",borrowId:" + br.getBorrowId() + ",属于分期还款回调,已经全部结清");
                		Map<String, Object> param = new HashMap<String, Object>();
            			param.put("id", id);
            			param.put("repayTime",DateUtil.valueOf(repayTime, DateUtil.DATEFORMAT_STR_001));
            			param.put("repayWay", repayWay);
            			param.put("repayAccount", repayAccount);
            			param.put("amount", br.getAmount());
            			param.put("serialNumber", payOrderNo);
            			param.put("penaltyAmout", br.getPenaltyAmout());
            			param.put("state", state);
            			resultMap = borrowRepayService.confirmRepay(param);
                	} else {
                		resultMap.put("Code", Constant.SUCCEED_CODE_VALUE);
    					resultMap.put("Msg", Constant.OPERATION_SUCCESS);
                	}
					
					//获取当前操作人员的user_id
					Subject user = SecurityUtils.getSubject();
					SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
					SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
					sysUserOperationRecord.setSysUserId(sysUser.getId());
					sysUserOperationRecord.setSysUserName(sysUser.getName());
					sysUserOperationRecord.setBorrowId(br.getBorrowId());
					sysUserOperationRecord.setSysUserResult("后台确认分期还款");
					sysUserOperationRecord.setSysUserOperationSource("后台确认分期还款催收功能");
					sysUserOperationRecord.setCreateTime(new Date());
					int insert = sysUserOperationRecordService.insert(sysUserOperationRecord);
				} else {
					resultMap.put("Code", Constant.FAIL_CODE_VALUE);
					resultMap.put("Msg", "不存在分期还款计划");
				}
			} else {
				resultMap.put("Code", Constant.FAIL_CODE_VALUE);
				resultMap.put("Msg", "还款计划不存在");
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
			resultMap.put("Code", Constant.FAIL_CODE_VALUE);
			resultMap.put("Msg", "还款失败");
		}
		result.put(Constant.RESPONSE_CODE, resultMap.get("Code"));
		result.put(Constant.RESPONSE_CODE_MSG, resultMap.get("Msg"));
		ServletUtils.writeToResponse(response, result);
	}
	
	public  Boolean modifyPayLog(Long payLogId, String payState, String razorpayPaymentID) {
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("state", payState);
        paramMap.put("tradeNo",razorpayPaymentID);
        paramMap.put("updateTime", DateUtil.getNow());
        if (PayLogModel.STATE_PAYMENT_FAILED.equals(payState)) {
            paramMap.put("remark", "failed");
        } else if(PayLogModel.STATE_PAYMENT_SUCCESS.equals(payState)){
            paramMap.put("remark", "success");
        }
        paramMap.put("id", payLogId);
        boolean updateSelective = payLogService.updateSelective(paramMap);
        return updateSelective;
    }

	/**
	 * 后台催收管理列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/repayList.htm", method = {RequestMethod.POST, RequestMethod.GET })
	@RequiresPermission(code = "modules:manage:borrow:repayList.htm", name = "催收管理列表")
	public void repayList(
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		if (params == null) {
			params = new HashMap<String, Object>();
			List<String> stateList = Arrays.asList(BorrowModel.STATE_DELAY,
					BorrowModel.STATE_BAD, BorrowModel.STATE_REPAY); // 未收款的借款
			params.put("stateList", stateList);
			params.put("state", BorrowModel.STATE_REPAY);
		} else {
			String state = StringUtil.isNull(params.get("state"));
			if (null == state || StringUtil.isBlank(state)) {
				List<String> stateList = Arrays.asList(BorrowModel.STATE_DELAY,
						BorrowModel.STATE_BAD, BorrowModel.STATE_REPAY); // 未收款的借款
				params.put("stateList", stateList);
				params.put("state", BorrowModel.STATE_REPAY);
			} else {
				List<String> stateList = Arrays.asList(state); // 未收款的借款
				params.put("stateList", stateList);
				params.put("state", state);
			}
		}
		Page<ManageBorrowModel> page = borrowRepayService.listRepayModel(
				params, currentPage, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 下载模板
	 * @param repayFile
	 * @param type
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/downRepayByFile.htm", method = {RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:repay:downRepayByFile", name = "下载批量确认还款模板")
	public void fileBatchRepay(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setCharacterEncoding("UTF-8");
        String fileBatchRepay = "批量确认还款模板.rar";
        InputStream input = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			String url = session.getServletContext().getRealPath("/")+"/excel/批量确认还款模板.rar";
			File file = new File(url);
			input = FileUtils.openInputStream(file);
            byte[] data = IOUtils.toByteArray(input);
            response.reset();
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileBatchRepay, "UTF-8"));
            response.addHeader("Content-Length", "" + data.length);
            
            response.setContentType("text/html;charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
		} catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 上传文件批量确认还款
	 * @param repayFile
	 * @param type
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/fileBatchRepay.htm", method = {RequestMethod.POST })
	@RequiresPermission(code = "modules:manage:borrow:repay:fileBatchRepay", name = "文件上传批量还款")
	public void fileBatchRepay(
			@RequestParam(value = "repayFile") MultipartFile repayFile,
			@RequestParam(value = "type") String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<List<String>> list=new ArrayList<List<String>>();
	    try {
			list = borrowRepayService.fileBatchRepay(repayFile,type);
	    	String title = "批量还款匹配结果";
	    	RepayExcelModel report = new RepayExcelModel();
			String fileName = report.saveExcelByList(list, title, repayFile.getOriginalFilename(),request);
	    	result.put(Constant.RESPONSE_DATA, "/modules/manage/borrow/repay/downBatchResult.htm?path="+fileName);
	    	result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		} catch (BussinessException e) {
			logger.error(e.getMessage(),e);
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "批量还款失败");
		}
	    ServletUtils.writeToResponse(response, result);
	}
	
	
	/**
	 * 保险扣款失败重新推送还款计划
	 * @param borrowId
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/checkByBorrowRepayId.htm")
	@RequiresPermission(code = "modules:manage:borrow:repay:check", name = "还款记录列表")
	public void checkByBorrowRepayId(@RequestParam(value = "id") Long id, String amount) {
		BorrowRepay borrowRepay = borrowRepayService.getById(id);
		Long borrowId = borrowRepay.getBorrowId();
		Borrow borrow = clBorrowService.findByPrimary(borrowId);
		Integer repayCheck = borrowRepayService.repayCheck(borrowId, amount);
		Map<String, Object> result = new HashMap<String, Object>();
		SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
		//获取当前操作人员的user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		sysUserOperationRecord.setSysUserId(sysUser.getId());
		sysUserOperationRecord.setSysUserName(sysUser.getName());
		sysUserOperationRecord.setBorrowId(borrowId);
		logger.info("borrowId：" + borrowId + "，更新还款计划结果：" + repayCheck);
		String remark;
		if(repayCheck > 0){
			//说明更新成功
			//推送还款计划
			remark = "更新成功";
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			//说明更新失败
			remark = "更新失败";
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		sysUserOperationRecord.setSysUserRemark(remark);
		sysUserOperationRecord.setSysUserOperationSource("更改还款计划");
		sysUserOperationRecord.setCreateTime(new Date());
		sysUserOperationRecordService.insert(sysUserOperationRecord);
		ServletUtils.writeToResponse(response, result);
	}
	
	
	/**
	 * 分期更新还款计划
	 * @param borrowId
	 */
	@Resource
	private BorrowRepayFqMapper borrowRepayFqMapper;
	
	@RequestMapping(value = "/modules/manage/borrow/repay/checkByBorrowRepayIdFq.htm")
	@RequiresPermission(code = "modules:manage:borrow:repay:check", name = "还款记录列表")
	public void checkByBorrowRepayIdFq(@RequestParam(value = "id") Long id, String amount) {
		BorrowRepay borrowRepay = borrowRepayService.getById(id);
		Long borrowId = borrowRepay.getBorrowId();
		Borrow borrow = clBorrowService.findByPrimary(borrowId);
		Double amountFq = Double.parseDouble(amount);
		Map<String, Object> result = new HashMap<String, Object>();
		Double needRepayMoney = borrowRepayFqService.getShengXiaRepayMoney(borrowId);
		double double1 = Global.getDouble("jm_money");
        if((Double.compare(amountFq, double1) >= 0 || Double.compare(needRepayMoney, double1) < 0)){
        	Integer saveBorrowRepayFq = borrowRepayFqService.saveOrUpdateBorrowRepayFq(borrowId, amountFq,BorrowRepayModel.STATE_REPAY_NO);
        	if(saveBorrowRepayFq > 0){
    			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        	} else {
        		result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
    			result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        	}
        } else {
        	result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "输入错误");
        }
		SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
		//获取当前操作人员的user_id
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		sysUserOperationRecord.setSysUserId(sysUser.getId());
		sysUserOperationRecord.setSysUserName(sysUser.getName());
		sysUserOperationRecord.setBorrowId(borrowId);
		sysUserOperationRecord.setSysUserPrepare1(amount);
		sysUserOperationRecord.setSysUserOperationSource("更新分期还款功能");
		sysUserOperationRecord.setCreateTime(new Date());
		sysUserOperationRecordService.insert(sysUserOperationRecord);
		ServletUtils.writeToResponse(response, result);
	}
	/**
	 * 批量还款成功后下载结果文件
	 * @param path
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/downBatchResult.htm")
	public void downRepayByBatch(@RequestParam(value = "path") String path) {
		RepayExcelModel report = new RepayExcelModel();
		try {
			report.exportFile(path, request, response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 还款计划列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value = "/modules/manage/borrow/repay/listChangePlan.htm")
	public void listChangePlan(
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		if(params == null){
			params = new HashMap<>();
		}
		params.put("state", "20");
		Page<ManageBRepayModel> page = borrowRepayService.listModel(params,
				currentPage, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
}
