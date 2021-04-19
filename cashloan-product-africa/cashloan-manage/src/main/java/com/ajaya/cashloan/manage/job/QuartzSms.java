package com.ajaya.cashloan.manage.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ajaya.cashloan.cl.model.MessageTypeModel;
import com.ajaya.cashloan.core.common.context.Global;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ajaya.cashloan.cl.model.MessageSendModel;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.core.common.util.DateUtils;

import tool.util.BeanUtil;


@Component
@Lazy(value = false)
public class QuartzSms implements Job{
	
	private static final Logger logger = Logger.getLogger(QuartzSms.class);
	
	/**
	 * 定时跑algo
	 * @throws ServiceException 
	 */
	public String late() throws ServiceException {
		ClBorrowService clBorrowService = (ClBorrowService)BeanUtil.getBean("clBorrowService");
		ClSmsService clSmsService = (ClSmsService)BeanUtil.getBean("clSmsService");
		//发送1-7 10 15 20 天未领款的领款短信
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start1 = DateUtils.getAppointDate(new Date(), 0, -1, 0, 0, 0);
        Date start2 = DateUtils.getAppointDate(new Date(), 0, -2, 0, 0, 0);
        Date start3 = DateUtils.getAppointDate(new Date(), 0, -3, 0, 0, 0);
        Date start4 = DateUtils.getAppointDate(new Date(), 0, -4, 0, 0, 0);
        Date start5 = DateUtils.getAppointDate(new Date(), 0, -5, 0, 0, 0);
        Date start6 = DateUtils.getAppointDate(new Date(), 0, -6, 0, 0, 0);
        Date start7  = DateUtils.getAppointDate(new Date(), 0, -7, 0, 0, 0);
//        Date start10 = DateUtils.getAppointDate(new Date(), 0, -10, 0, 0, 0);
//        Date start15 = DateUtils.getAppointDate(new Date(), 0, -15, 0, 0, 0);

		String format1 = format.format(start1);
		String format2 = format.format(start2 );
		String format3 = format.format(start3 );
		String format4 = format.format(start4 );
		String format5 = format.format(start5 );
		String format6 = format.format(start6 );
		String format7 = format.format(start7);
//		String format10= format.format(start10);
//		String format15= format.format(start15);

		ArrayList<String> dateList = new ArrayList<>();
		dateList.add(format1 );
		dateList.add(format2 );
		dateList.add(format3 );
		dateList.add(format4 );
		dateList.add(format5 );
		dateList.add(format6 );
		dateList.add(format7 );
//		dateList.add(format10);
//		dateList.add(format15);
        List<MessageSendModel> list = clBorrowService.selectSendMessageInfoList(dateList);
		String title2 = Global.getValue("title2");
		if (list.size() > 0) {
			logger.info("发送领款短信条数：{}"+  list.size());
		}
		for (MessageSendModel messageSendModel : list) {
			//根据产品名称判断发送短信类型
			String productName = messageSendModel.getProductName();
			String messageType = MessageTypeModel.MESSAGE_TYPE_SMS_GET_MONEY;
			if (title2.equals(productName)){
				messageType = messageType + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
			}
			try {
				clSmsService.sendGetMoneySms(messageSendModel.getPhone(), messageType);
				logger.info("订单 " + messageSendModel.getBorrowId() + " 用户电话 " + messageSendModel.getPhone() + " 用户id "
						+ messageSendModel.getUserId() + " 审批时间 " + messageSendModel.getCreateTime() + "：发送成功");
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("订单 " + messageSendModel.getBorrowId() + " 用户电话 " + messageSendModel.getPhone() + " 用户id "
						+ messageSendModel.getUserId() + " 审批时间 " + messageSendModel.getCreateTime() + "：发送失败");
			}

		}
		
		return null;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			String remark = late();
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			logger.info("保存定时任务日志");
		}
	}
	
}
