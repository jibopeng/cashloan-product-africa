package com.ajaya.cashloan.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.config.AsyncProcessQueue;
import com.ajaya.cashloan.cl.domain.BorrowFlag;
import com.ajaya.cashloan.cl.domain.ClBorrowRule;
import com.ajaya.cashloan.cl.domain.SdkCatchDataCallBackLog;
import com.ajaya.cashloan.cl.domain.SdkCatchDataSyncLog;
import com.ajaya.cashloan.cl.mapper.BorrowFlagMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowRuleMapper;
import com.ajaya.cashloan.cl.model.OriginalDataModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.ClBorrowOriginalDataService;
import com.ajaya.cashloan.cl.service.ClBorrowRuleService;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.SdkCatchDataCallBackLogService;
import com.ajaya.cashloan.cl.service.SdkCatchDataSyncLogService;
import com.ajaya.cashloan.cl.service.UserContactsService;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.rc.domain.SceneBusinessLog;
import com.ajaya.cashloan.rc.mapper.SceneBusinessMapper;
import com.ajaya.cashloan.rc.model.TppServiceInfoModel;
import com.ajaya.cashloan.rc.service.SceneBusinessLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ryan
 * @version 1.0 2020/12/05
 */
@Scope("prototype")
@Controller
public class SdkController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(SdkController.class);

    @Resource
    private ClBorrowService clBorrowService;
    @Resource
    private SdkCatchDataSyncLogService sdkCatchDataSyncLogServiceImpl;
    @Resource
    private UserContactsService userContactsService;
    @Resource
    private ClBorrowRuleService clBorrowRuleService;
	@Resource
	private ClBorrowOriginalDataService borrowOriginalDataService;
	@Resource
    private SceneBusinessMapper sceneBusinessMapper;
    @Resource
    private SceneBusinessLogService sceneBusinessLogService;
    @Resource
    private SdkCatchDataCallBackLogService sdkCatchDataCallBackLogService;
    @Resource
    private BorrowFlagMapper borrowFlagMapper;
    @Resource
    private ClBorrowRuleMapper clBorrowRuleMapper;
	
    
    /**
     * epoch sdk抓取数据同步响应接口
     *
     * @param borrowId     订单 id
     * @param userId       用户id
     * @param reportStatus 响应状态
     * @param code         响应码
     * @param message      响应信息
     * @param type         取类型：msg：短信 app: app img：相册 contact：通讯录 device：设备信息
     */
    @RequestMapping(value = "/api/act/getSdkReportStatusNew.htm", method = RequestMethod.POST)
    public void getSdkReportStatusNew(
            @RequestParam(value = "borrowId") long borrowId, @RequestParam(value = "userId") long userId,
            @RequestParam(value = "reportStatus") int reportStatus,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "message", required = false) String message) {

        logger.info("订单 {} sdk抓取元数据-" + type + "-同步返回结果 {}", borrowId, reportStatus == 1 ? "success" : "failed");
        if (ObjectUtils.isEmpty(borrowId) || ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(reportStatus)) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("params is empty."));
            return;
        }
        //添加sdk数据抓取同步返回状态记录
        SdkCatchDataSyncLog sdkCatchDataSyncLog = new SdkCatchDataSyncLog();
        sdkCatchDataSyncLog.setBorrowId(borrowId);
        sdkCatchDataSyncLog.setUserId(userId);
        sdkCatchDataSyncLog.setReportStatus(reportStatus);
        sdkCatchDataSyncLog.setCreateTime(new Date());
        sdkCatchDataSyncLog.setMessage(message);
        sdkCatchDataSyncLog.setCode(code);
        sdkCatchDataSyncLog.setType(type);
        sdkCatchDataSyncLogServiceImpl.insert(sdkCatchDataSyncLog);
        ServletUtils.writeToResponse(response, ResponseWrapper.success("Sdk synchronization returned successfully."));
    }
    

    /**
     * 上传原始数据
     * @param borrowId
     * @param userId
     * @param type
     * @param data
     */
    @RequestMapping(value = "/api/act/saveOriginalData.htm", method = RequestMethod.POST)
    public void saveOriginalData(
            @RequestParam(value = "borrowId", required = true) final long borrowId, 
            @RequestParam(value = "userId", required = true) final long userId,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "data") String data) {
    	
    	logger.info("userId {} borrowId {} type {} 开始上报原始数据" , userId, borrowId, type);
    	Integer saveOriginalData = borrowOriginalDataService.saveOriginalData(userId, borrowId, type, data);
    	ServletUtils.writeToResponse(response, saveOriginalData > 0 ? ResponseWrapper.success() : ResponseWrapper.error());
    	
    	if(OriginalDataModel.TYPE_CONTACT.equals(type) && StringUtil.isNotBlank(data)){
    		JSONArray originalContact = JSON.parseArray(data);
    		logger.info("userId {} borrowId {} type {} 开始上报原始数据, 单独保存通讯录规则" , userId, borrowId, type);
			clBorrowRuleService.saveBorrowRule(borrowId, userId, originalContact, null, null);
    		logger.info("userId {} borrowId {} type {} 开始上报原始数据, 单独保存通讯录" , userId, borrowId, type);
    		String tableName = userContactsService.getTableName(userId);
			userContactsService.deleteShard(tableName, userId, null);
			
			Map<String, Object> map = new HashMap<>();
			for(int i=0; i<originalContact.size(); i++){
				JSONObject object = originalContact.getJSONObject(i);
				map.put("name", object.get("name"));
				map.put("phone", object.get("phone"));
				map.put("borrowId", borrowId);
				userContactsService.saveShard(tableName, map, userId, new Date(), null);
			}
			
    	} else if (OriginalDataModel.TYPE_APP.equals(type) && StringUtil.isNotBlank(data)){
    		logger.info("userId {} borrowId {} type {} 开始上报原始数据, 单保存App规则" , userId, borrowId, type);
    		JSONArray originalApp = JSON.parseArray(data);
    		clBorrowRuleService.saveBorrowRule(borrowId, userId, null, null, originalApp);
    		
    	} else if (OriginalDataModel.TYPE_MSG.equals(type) && StringUtil.isNotBlank(data)){
    		logger.info("userId {} borrowId {} type {} 开始上报原始数据, 单保存Msg规则" , userId, borrowId, type);
    		JSONArray originalMsg = JSON.parseArray(data);
    		clBorrowRuleService.saveBorrowRule(borrowId, userId, null, originalMsg, null);
    	}
    	
    	
    	//判断订单状态进行走风控
    	Borrow borrow = clBorrowService.findByPrimary(borrowId);
    	if(borrow.getState().equals(BorrowModel.STATE_PRE)){
    		 Boolean needRequestSdkMode1 = sdkCatchDataSyncLogServiceImpl.needRequestSdkModel(userId, borrowId);
    		 logger.info("userId {} borrowId {} needRequestSdkMode1 {} " , userId, borrowId, type, needRequestSdkMode1);
    		 Boolean needRequestSdkMode2 = sdkCatchDataCallBackLogService.needRequestSdkMode(userId, borrowId);
    		 logger.info("userId {} borrowId {} needRequestSdkMode2 {} " , userId, borrowId, type, needRequestSdkMode2);
    		 
    		 
    		 Map<String, Object> paramMap = new HashMap<>();
    		 paramMap.put("borrowId", borrowId);
    		 ClBorrowRule borrowRule = clBorrowRuleMapper.findSelective(paramMap);
    		 if(borrowRule == null  || borrowRule.getMsgNum() == null || borrowRule.getContactsNum() == null){
    			 logger.info("userId {}, borrowId {} borrowRule, 短信或者通讯录数量为空, 不能走风控", userId, borrowId);
    			 return;
    		 }
    		 
    		 if(needRequestSdkMode1 && !needRequestSdkMode2){
    			SdkCatchDataCallBackLog record = new SdkCatchDataCallBackLog();
    			record.setUserId(userId);
    			record.setBorrowId(borrowId);
    			record.setCatchDataState("1");
    			record.setCreateTime(new Date());
				int insert = sdkCatchDataCallBackLogService.insert(record);
				logger.info("userId {}, borrowId {} 保存数据抓取记录结果 {}", userId, borrowId, insert);
    		    	AsyncProcessQueue.execute(new Runnable() {
    					@Override
    					public void run() {
    						try {
    							BorrowFlag borrowFlag = new BorrowFlag();
    							borrowFlag.setBorrowId(borrowId);
    							borrowFlag.setState("11");
    							borrowFlag.setCreateTime(new Date());
    							borrowFlagMapper.save(borrowFlag);
    							//生成scenelog走
    			    			 List<TppServiceInfoModel> infoList = sceneBusinessMapper.findTppServiceInfo();
    			             	logger.info("userId {} , borrowId {} 走风控, 开始生成sceneBusinessLog, 需要生成的第三方内容: {} ", userId ,  borrowId, JSON.toJSONString(infoList));
    			             	if (infoList != null && infoList.size() > 0) {
    			                     SceneBusinessLog sceneLog = null;
    			                     for (TppServiceInfoModel info : infoList) {
    			                         boolean needExcute = sceneBusinessLogService.needExcute(userId, info.getBusId(),
    			                             info.getGetWay(), info.getPeriod());
    			                         SceneBusinessLog sceneBusinessLog = sceneBusinessLogService.findByBorrowIdAndBusNid(borrowId, info.getBusNid());
    			                         if(sceneBusinessLog != null){
    			                        	 //说明已经生成
    			                        	 return;
    			                         }
    			                         if (needExcute) {
    			                         	logger.info("userId {} , borrowId {} 走风控, 开始生成sceneBusinessLog, 生成的三方接口名称 {}", userId , borrowId, info.getSceneId());
    			                             sceneLog = new SceneBusinessLog(info.getSceneId(), borrowId, userId, info.getTppId(), info.getBusId(), info.getBusNid(), new Date(), info.getType());
    			                             sceneBusinessLogService.insert(sceneLog);
    			                         }
    			                     }
    			                 }
    			             	clBorrowService.verifyBorrowData(borrowId);
    						} catch (Exception e) {
    							logger.error("borrowId :" + borrowId + ",走风控报错,",e);
    						}
    					}
    		        });
    		 } else {
    			 logger.info("userId {} borrowId {} , 信息未抓取, 不走风控" , userId, borrowId);
    		 }
    	} else {
    		logger.info("userId {} borrowId {} , 不是待审核状态, 不走风控" , userId, borrowId);
    	}
    }
}
