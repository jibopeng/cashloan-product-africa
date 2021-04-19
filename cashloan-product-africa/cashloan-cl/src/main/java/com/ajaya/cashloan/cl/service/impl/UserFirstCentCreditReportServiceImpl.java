package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.UserFirstCentCreditReport;
import com.ajaya.cashloan.cl.mapper.UserFirstCentCreditReportMapper;
import com.ajaya.cashloan.cl.model.credit.FirstCentralNigeriaWebService;
import com.ajaya.cashloan.cl.model.credit.FirstCentralNigeriaWebServiceSoap;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.UserFirstCentCreditLogService;
import com.ajaya.cashloan.cl.service.UserFirstCentCreditReportService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.util.XmlToMapUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import com.alibaba.fastjson.JSON;

/**
 * 'FC用户征信报告表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 15:20:15
 */

@Service("userFirstCentCreditReportService")
public class UserFirstCentCreditReportServiceImpl extends BaseServiceImpl<UserFirstCentCreditReport, Long>
		implements UserFirstCentCreditReportService {

	private static final Logger logger = LoggerFactory.getLogger(UserFirstCentCreditReportServiceImpl.class);

	@Resource
	private UserFirstCentCreditReportMapper userFirstCentCreditReportMapper;
	@Resource
	private UserBaseInfoService userBaseInfoService;
	@Resource(name = "clBorrowService")
	private ClBorrowService borrowService;
	@Resource
	private UserFirstCentCreditLogService userFirstCentCreditLogService;
	
	private static String tickt = null;
	


	@Override
	public BaseMapper<UserFirstCentCreditReport, Long> getMapper() {
		return userFirstCentCreditReportMapper;
	}

	@Override
	public Integer saveFirstCentCreditReport(Long borrowId) {
		Borrow borrow = borrowService.findByPrimary(borrowId);
		if (borrow == null) {
			logger.info("borrowId 查询信用报告 {} 订单未找到", borrowId);
			return 0;
		}
		Long userId = borrow.getUserId();
		UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(userId);
		if (userBaseInfo == null || StringUtil.isBlank(userBaseInfo.getBvnNo())) {
			logger.info("userId {} borrowId {} 查询信用报告 , 用户未找到或者bvn号为空", userId, borrowId);
			return 0;
		}

		// 查询信用报告是否已经生成
		String bvnNo = userBaseInfo.getBvnNo();
		Integer id = userFirstCentCreditReportMapper.checkByBvn(bvnNo);
		if (id > 0) {
			logger.info("userId {} borrowId {} 查询信用报告,已经存在", userId, borrowId);
			return 1;
		}

		// 开始调用信用报告
		logger.info("userId {} borrowId {} 开始查询信用报告", userId, borrowId);
		
		Long start = System.currentTimeMillis();
		FirstCentralNigeriaWebService firstCentralNigeriaWebService = new FirstCentralNigeriaWebService();
		FirstCentralNigeriaWebServiceSoap firstCentralNigeriaWebServiceSoap = firstCentralNigeriaWebService
				.getFirstCentralNigeriaWebServiceSoap();
		long timeout = 120 * 1000;
		((BindingProvider) firstCentralNigeriaWebServiceSoap).getRequestContext().put("javax.xml.ws.client.connectionTimeout", timeout);  
	    ((BindingProvider) firstCentralNigeriaWebServiceSoap).getRequestContext().put("javax.xml.ws.client.receiveTimeout", timeout);  
		
		if(tickt == null){
			tickt = firstCentralNigeriaWebServiceSoap.login(Global.getValue("fc_user_name"), Global.getValue("fc_pass_word"));
		} else {
			boolean ticketValid = firstCentralNigeriaWebServiceSoap.isTicketValid(tickt);
			if(!ticketValid){
				logger.info("firstCentral刷新tickt {}" , tickt);
				tickt = firstCentralNigeriaWebServiceSoap.login(Global.getValue("fc_user_name"), Global.getValue("fc_pass_word"));
			} else {
				logger.info("firstCentral当前有效tickt {}" , tickt);
			}
		}
		Long ticketEnd = System.currentTimeMillis();
		logger.info("userId {} borrowId {} 开始查询信用报告, 获取ticket耗时 {}", userId, borrowId, (ticketEnd - start) + "ms");
		
		//获取用户在FC的信息
		String enquiryReason = "Application for Credit by a borrower";//固定值
		String consumerName = "";
		String dateOfBirth = "";
		String identification = bvnNo;
		String accountNumber = "";
		String productID = "50";//固定值,和获取的报告类型对应
		
		//组装参数
		Map<String, Object> param = new HashMap<>();
		param.put("enquiryReason", enquiryReason);
		param.put("identification", identification);
		param.put("productID", productID);
		String connectConsumerMatch = firstCentralNigeriaWebServiceSoap.connectConsumerMatch(tickt, enquiryReason , consumerName , dateOfBirth , identification, accountNumber, productID );
		
		Long connectConsumerMatchEnd = System.currentTimeMillis();
		logger.info("userId {} borrowId {} 开始查询信用报告, 获取FC用户信息耗时 {}", userId, borrowId, (connectConsumerMatchEnd - ticketEnd) + "ms");
		//存储日志记录
		userFirstCentCreditLogService.saveUserFirstCentCreditLog(userId, borrowId, bvnNo, "connectConsumerMatch", JSON.toJSONString(param), connectConsumerMatch);
		
		//解析获取关键参数
		List<Map<String,String>> list = XmlToMapUtil.getListByXml(connectConsumerMatch, "MatchedConsumer");
		int consumerID = 0;
		String consumerMergeList = "";
		String subscriberEnquiryEngineID = "";
		int enquiryID = 0;
		for(int i=0; i<list.size(); i++){
			if(i == 0){
				consumerID = Integer.parseInt(list.get(i).get("ConsumerID"));
				enquiryID =  Integer.parseInt(list.get(i).get("EnquiryID"));
				subscriberEnquiryEngineID = list.get(i).get("MatchingEngineID");
			}
			if(i == list.size() - 1){
				consumerMergeList = consumerMergeList + list.get(i).get("ConsumerID");
			} else {
				consumerMergeList = consumerMergeList + list.get(i).get("ConsumerID") + ",";
			}
		}
		
		if(consumerID == 0){
			logger.info("userId {} borrowId {} 查询信用报告,未命中", userId, borrowId);
			return 1;
		}
		
		//调用接口获取信用报告
		param.clear();
		param.put("consumerID", consumerID);
		param.put("consumerMergeList", consumerMergeList);
		param.put("subscriberEnquiryEngineID", subscriberEnquiryEngineID);
		param.put("enquiryID", enquiryID);
		String xsCoreConsumerFullCreditReport = firstCentralNigeriaWebServiceSoap.getXSCoreConsumerFullCreditReport(tickt, consumerID , consumerMergeList , subscriberEnquiryEngineID , enquiryID);
		
		Long xsCoreConsumerFullCreditReportEnd = System.currentTimeMillis();
		logger.info("userId {} borrowId {} 开始查询信用报告, 获取FC信用报告信息耗时 {}", userId, borrowId, (xsCoreConsumerFullCreditReportEnd - connectConsumerMatchEnd) + "ms");
		//存储日志记录
		userFirstCentCreditLogService.saveUserFirstCentCreditLog(userId, borrowId, bvnNo, "getXSCoreConsumerFullCreditReport", JSON.toJSONString(param), xsCoreConsumerFullCreditReport);
		
		//存储信用报告
		UserFirstCentCreditReport userFirstCentCreditReport = new UserFirstCentCreditReport();
		userFirstCentCreditReport.setUserId(userId);
		userFirstCentCreditReport.setBorrowId(borrowId);
		userFirstCentCreditReport.setBvn(bvnNo);
		userFirstCentCreditReport.setCreditReport(xsCoreConsumerFullCreditReport);
		userFirstCentCreditReport.setCreateTime(new Date());
		int save = userFirstCentCreditReportMapper.save(userFirstCentCreditReport);
		logger.info("userId {} borrowId {} 开始查询信用报告, 保存结果 {}", userId, borrowId, save);
		return save;
	}
}
