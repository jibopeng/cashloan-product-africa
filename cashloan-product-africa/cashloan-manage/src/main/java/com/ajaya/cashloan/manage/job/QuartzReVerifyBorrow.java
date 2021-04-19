package com.ajaya.cashloan.manage.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ajaya.cashloan.cl.domain.ClBorrowRule;
import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowRuleMapper;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.SdkModelScoreService;
import com.ajaya.cashloan.cl.service.UserContactsService;
import com.ajaya.cashloan.cl.service.UserMessagesService;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.mapper.UserMapper;
import com.ajaya.cashloan.manage.domain.QuartzInfo;
import com.ajaya.cashloan.manage.domain.QuartzLog;
import com.ajaya.cashloan.manage.service.QuartzInfoService;
import com.ajaya.cashloan.manage.service.QuartzLogService;
import com.ajaya.cashloan.rc.domain.SceneBusinessLog;
import com.ajaya.cashloan.rc.mapper.SceneBusinessMapper;
import com.ajaya.cashloan.rc.model.TppServiceInfoModel;
import com.ajaya.cashloan.rc.service.SceneBusinessLogService;
import com.alibaba.fastjson.JSON;

import tool.util.BeanUtil;

/**
 * 定时执行卡在待机审的订单
 * 
 * @author Administrator
 *
 */
@Component
@Lazy(value = false)
@org.quartz.DisallowConcurrentExecution
public class QuartzReVerifyBorrow implements Job {

	public static final Logger logger = LoggerFactory.getLogger(QuartzReVerifyBorrow.class);
	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
		QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
		QuartzLog ql = new QuartzLog();
		Map<String, Object> qiData = new HashMap<>();
		QuartzInfo qi = quartzInfoService.findByCode("doReVerifyBorrow");
		try {
			qiData.put("id", qi.getId());
			ql.setQuartzId(qi.getId());
			ql.setStartTime(DateUtil.getNow());

			String remark = reVerifyBorrow();

			ql.setTime(DateUtil.getNow().getTime() - ql.getStartTime().getTime());
			ql.setResult("10");
			ql.setRemark(remark);
			qiData.put("succeed", qi.getSucceed() + 1);

		} catch (Exception e) {
			ql.setResult("20");
			qiData.put("fail", qi.getFail() + 1);
			logger.error(e.getMessage(), e);
		} finally {
			logger.info("保存定时任务日志");
			quartzLogService.save(ql);
			quartzInfoService.update(qiData);
		}
	}

	/**
	 * 执行人工复审订单
	 * @return
	 */
	private String reVerifyBorrow() {
		
		ClBorrowMapper clBorrowMaper = (ClBorrowMapper)BeanUtil.getBean("clBorrowMapper");
		final ClBorrowRuleMapper clBorrowRuleMapper = (ClBorrowRuleMapper)BeanUtil.getBean("clBorrowRuleMapper");
		final ClBorrowService clBorrowService = (ClBorrowService)BeanUtil.getBean("clBorrowService");
		final SceneBusinessLogService sceneBusinessLogService = (SceneBusinessLogService)BeanUtil.getBean("sceneBusinessLogService");
		SceneBusinessMapper sceneBusinessMapper = (SceneBusinessMapper)BeanUtil.getBean("sceneBusinessMapper");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("state", "10");
		List<Borrow> list = clBorrowMaper.listSelective(paramMap);
		if(list != null && list.size() > 0){
			for (Borrow borrow : list) {
				synchronized (this) {
					int minuteBetween = DateUtil.minuteBetween(new Date(), borrow.getCreateTime());
					logger.info("borrowId:" + borrow.getId() + ", minuteBetween:" + minuteBetween);
					if(Math.abs(minuteBetween) >= 30){
						//再次查询当前订单状态
						borrow = clBorrowMaper.findByPrimary(borrow.getId());
						logger.info("borrowId:"+borrow.getId()+",再次查询当前状态,state:"+borrow.getState());
						
						if("10".equals(borrow.getState())){
							
							final Long borrowId = borrow.getId();
							final Long userId = borrow.getUserId();
							Date createTime = borrow.getCreateTime();
							if(DateUtil.daysBetween(createTime, new Date()) > 3){
								logger.info("订单 {} 没有抓取到原始数据, {}", borrowId, "过了3天，可以取消");
								clBorrowService.cancelBorrow(borrow);
								continue;
							}
							
							paramMap.clear();
							paramMap.put("borrowId", borrow.getId());
							ClBorrowRule borrowRule = clBorrowRuleMapper.findSelective(paramMap);
							if(borrowRule == null  || borrowRule.getMsgNum() == null || borrowRule.getContactsNum() == null){
				    			 logger.info("userId {}, borrowId {} borrowRule, 短信或者通讯录数量为空, 不能走风控", userId, borrowId);
				    			 continue;
				    		}
							
							//走风控
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
			                        	 continue;
			                         }
			                         if (needExcute) {
			                         	logger.info("userId {} , borrowId {} 走风控, 开始生成sceneBusinessLog, 生成的三方接口名称 {}", userId , borrowId, info.getSceneId());
			                             sceneLog = new SceneBusinessLog(info.getSceneId(), borrowId, userId, info.getTppId(), info.getBusId(), info.getBusNid(), new Date(), info.getType());
			                             sceneBusinessLogService.insert(sceneLog);
			                         }
			                     }
			                 
			             		}
			             	clBorrowService.verifyBorrowData(borrowId);
							
						}  else {
							logger.info("borrowId:" + borrow.getId() + ",状态不正确");
						}
					}  else {
						logger.info("borrowId:" + borrow.getId() + ",时间不够");
					}
				}
			}
		}
		
		return null;
	}

}
