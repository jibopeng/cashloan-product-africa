package com.ajaya.cashloan.manage.job;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ajaya.cashloan.cl.service.BorrowRepayService;
import com.ajaya.cashloan.cl.service.ClSmsService;

import tool.util.BeanUtil;
/**
 * 逾期任务---目前只是app推送 
 * create:2017年11月18日 12:02:24
 * @author 刘晓亮	
 *
 */
@Component
@Lazy(value = false)
public class QuartzRepaymentOverDue implements Job{
	
	private static final Logger logger = Logger.getLogger(QuartzRepaymentOverDue.class);

	private BorrowRepayService borrowRepayService = null;
	ClSmsService clSmsService = null;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//初始化
		borrowRepayService = (BorrowRepayService) BeanUtil.getBean("borrowRepayService");
		clSmsService = (ClSmsService) BeanUtil.getBean("clSmsService");
		//发送逾期1-7/14 /30天短信
		overDueSms(1L);
		overDueSms(2L);
		overDueSms(3L);
		overDueSms(4L);
		overDueSms(5L);
		overDueSms(6L);
		overDueSms(7L);
		overDueSms(14L);
		overDueSms(30L);
	}

	/**
	 * 发送逾期短信
	 * @param penalty_day 逾期天数
	 */
	private void overDueSms(long penalty_day) {
		List<Map<String,Object>> overdueList = borrowRepayService.findReaymentOverdueListNew(penalty_day);
		if(overdueList != null && overdueList.size() > 0){
			for (Map<String, Object> map : overdueList) {
				Object userIdObJ = map.get("userId");
				Object productName = map.get("productName");
				Long borrowId =(Long) map.get("borrowId");
				Double penaltyAmount = (Double) map.get("penaltyAmount");
				logger.info("发送逾期短信 user_id" + userIdObJ.toString());
				Object phoneObj = map.get("phone");
				//发送逾期提醒短信
				clSmsService.overdue(phoneObj.toString(), penalty_day+"",productName+"",borrowId,penaltyAmount);
			}
		}
	}
}
