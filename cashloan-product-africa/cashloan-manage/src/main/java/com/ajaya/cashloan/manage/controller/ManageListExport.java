package com.ajaya.cashloan.manage.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.service.BorrowRepayLogService;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.PayLogService;
import com.ajaya.cashloan.cl.service.StatisticManageService;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderService;
import com.ajaya.cashloan.cl.service.UserMessagesService;
import com.ajaya.cashloan.core.common.context.ExportConstant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.excel.JsGridReportBase;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.core.service.CloanUserService;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.domain.SysUserChannel;
import com.ajaya.cashloan.system.service.SysUserChannelService;

import tool.util.StringUtil;

@Scope("prototype")
@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ManageListExport extends ManageBaseController{

	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private CloanUserService userService;
	@Resource
	private BorrowRepayLogService borrowRepayLogService;
	@Resource
	private PayLogService payLogService;
	@Resource
	private UrgeRepayOrderService urgeRepayOrderService;
	
	@Resource
	private SysUserChannelService sysUserChannelService;
	@Resource
	private UserMessagesService userMessagesService;
	@Resource
	private StatisticManageService statisticManageService;
	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrowRepayLog/export.htm")
	public void repayLogExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List list = borrowRepayLogService.listExport(params);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_REPAYLOG_LIST_HEARDERS;
		String[] fields = ExportConstant.EXPORT_REPAYLOG_LIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(list,title,hearders,fields,user.getName());
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrow/export.htm")
	public void borrowExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		//????????? start
		Subject userForChannel = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) userForChannel.getSession().getAttribute("SysUser");
		//??????????????????????????????
		Map<String, Object> paramMapForUserId = new HashMap<>();
		paramMapForUserId.put("userId", sysUser.getId());
		List<SysUserChannel> SysUserChannelList = sysUserChannelService.listSelective(paramMapForUserId);
		if(SysUserChannelList != null && SysUserChannelList.size() > 0){
			//????????????????????????,????????????
			return;
		}
		//????????? end
		
		
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List list = clBorrowService.listBorrow(params);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "????????????Excel???";
		String[] hearders = ExportConstant.EXPORT_BORROW_LIST_HEARDERS;
		String[] fields = ExportConstant.EXPORT_BORROW_LIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(list,title,hearders,fields,user.getName());
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/payLog/export.htm")
	public void payLogExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		List list = payLogService.listPayLog(searchParams);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_PAYLOG_LIST_HEARDERS;
		String[] fields = ExportConstant.EXPORT_PAYLOG_LIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(list,title,hearders,fields,user.getName());
	}
	
	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/overdue/export.htm")
	public void overdueExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		Map<String, Object> params;
		if (StringUtil.isBlank(searchParams)) {
			params = new HashMap<>();
		}else {
			params = JsonUtil.parse(searchParams, Map.class);
		}
		params.put("state", BorrowModel.STATE_DELAY);
		List list = clBorrowService.listBorrow(params);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "???????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_OVERDUE_LIST_HEARDERS;
		String[] fields = ExportConstant.EXPORT_OVERDUE_LIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(list,title,hearders,fields,user.getName());
	}
	
	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/badDebt/export.htm")
	public void badDebtExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		Map<String, Object> params;
		if (StringUtil.isBlank(searchParams)) {
			params = new HashMap<>();
		}else {
			params = JsonUtil.parse(searchParams, Map.class);
		}
		params.put("state", BorrowModel.STATE_BAD);
		List list = clBorrowService.listBorrow(params);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "???????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_BADDEBT_LIST_HEARDERS;
		String[] fields = ExportConstant.EXPORT_BADDEBT_LIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(list,title,hearders,fields,user.getName());
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/urgeRepayOrder/export.htm")
	public void urgeRepayOrderExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List list = urgeRepayOrderService.listUrgeRepayOrder(params);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_REPAYORDER_LIST_HEARDERS;
		String[] fields = ExportConstant.EXPORT_REPAYORDER_LIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(list,title,hearders,fields,user.getName());
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/urgeLog/export.htm")
	public void urgeLogExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List list = urgeRepayOrderService.listUrgeLog(params);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_URGELOG_LIST_HEARDERS;
		String[] fields = ExportConstant.EXPORT_URGELOG_LIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(list,title,hearders,fields,user.getName());
	}
	/**
	 * ???????????????????????????????????????????????????
	 * @itw_yanzq	
	 * @date 2017???11???27???
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/userBaseMes/export.htm")
	public void userBaseMesExport(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
//	     searchParams ="9,30001";
		String[] params = searchParams.split(",");
		HSSFWorkbook excel = userMessagesService.findUserMesByuPhones(params);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyMMdd");
		simpleDateFormat.setLenient(false);
		String time = simpleDateFormat.format(new  Date());
		//??????????????????excel???????????????
		String name ="??????????????????????????????"+time+".xls";
		try {
		//?????????????????????????????????excel??????????????????
		OutputStream output=response.getOutputStream();
		   response.reset();
		   response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(name, "utf-8"));
		   response.setContentType("application/msexcel");        
		   excel.write(output);
		   if (output!=null) {
			   output.close();
		   }
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param searchParams
	 * @throws Exception 
	 */
	@RequestMapping(value = "/modules/manage/newLoanRepPer/export.htm")
	public void newLoanRepPerExport(@RequestParam (value = "searchParams",required = false) String searchParams) throws Exception{
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List listNewLoan = statisticManageService.listNewLoan(params);
		
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "????????????????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_NEWLOANREPPERLIST_HEADERS;
		String[] fields = ExportConstant.EXPORT_NEWLOANREPPERLIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(listNewLoan,title,hearders,fields,user.getName());
	}
	
	/**
	 * ??????????????????????????????
	 * @param searchParams
	 * @throws Exception 
	 */
	@RequestMapping(value = "/modules/manage/oldLoanRepPer/export.htm")
	public void oldLoanRepPerExport(@RequestParam(value = "searchParams" ,required = false) String searchParams) throws Exception{
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List listOldLoan = statisticManageService.ListOldLoan(params);
		
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "????????????????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_OLDLOANREPPERLIST_HEADERS;
		String[] fields = ExportConstant.EXPORT_OLDLOANREPPERLIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(listOldLoan,title,hearders,fields,user.getName());
	}
	
	/**
	 * ???????????????????????????
	 * @param searchParams
	 * @throws Exception 
	 */
	@RequestMapping(value = "/modules/manage/loanVerify/export.htm")
	public void loanVerifyExport(@RequestParam(value = "searchParams",required = false) String searchParams) throws Exception{
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List listVerify = statisticManageService.ListVerify(params);
		
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		response.setContentType("application/msexcel;charset=UTF-8");
		// ????????????
		String title = "?????????????????????Excel???";
		String[] hearders =  ExportConstant.EXPORT_LOANVERIFYLIST_HEADERS;
		String[] fields = ExportConstant.EXPORT_LOANVERIFYLIST_FIELDS;
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(listVerify,title,hearders,fields,user.getName());
	}
}
