package com.ajaya.cashloan.manage.job;

import java.util.*;

import com.ajaya.cashloan.cl.model.MessageTypeModel;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.manage.domain.QuartzLog;
import com.ajaya.cashloan.manage.service.QuartzInfoService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tool.util.BeanUtil;
import tool.util.DateUtil;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.model.BorrowRepayModel;
import com.ajaya.cashloan.cl.service.BorrowRepayService;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import com.ajaya.cashloan.manage.domain.QuartzInfo;
import com.ajaya.cashloan.manage.service.QuartzLogService;

@Component
@Lazy(value = false)
@org.quartz.DisallowConcurrentExecution
public class QuartzRepayInform implements Job {

    private static final Logger logger = Logger.getLogger(QuartzRepayInform.class);

    /**
     * 还款提醒 到期日前两天发送通知还款短信。尼日利亚时间上午11点发送。到期日当天发送通知还款短信。尼日利亚时间11、20点各发送一条，共2条。
     *
     * @return
     * @throws ServiceException
     */
    public String repayInform() throws ServiceException {
        BorrowRepayService borrowRepayService = (BorrowRepayService) BeanUtil.getBean("borrowRepayService");
        UserBaseInfoService userBaseInfoService = (UserBaseInfoService) BeanUtil.getBean("userBaseInfoService");
        ClSmsService clSmsService = (ClSmsService) BeanUtil.getBean("clSmsService");
        ClBorrowService clBorrowService = (ClBorrowService) BeanUtil.getBean("clBorrowService");
        //结果为“0”是上午 结果为“1”是下午
        int index = new GregorianCalendar().get(GregorianCalendar.AM_PM);
        logger.info("进入还款日前0~2天计算...");
        String quartzRemark = null;
        int succeed = 0;
        int fail = 0;
        int total = 0;
        String title2 = Global.getValue("title2");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("state", BorrowRepayModel.STATE_REPAY_NO);
        List<BorrowRepay> list = borrowRepayService.listSelective(paramMap);
        if (!list.isEmpty()) {
            for (BorrowRepay borrowRepay : list) {
                try {
                    long day = DateUtil.daysBetween(new Date(), borrowRepay.getRepayTime());
                    //根据app类型发送不同类型的短信
                    Borrow borrow = clBorrowService.findByPrimary(borrowRepay.getBorrowId());
                    String productName = borrow.getProductName();
                    String smsTodayNotice = MessageTypeModel.MESSAGE_TYPE_SMS_TODAY_NOTICE;
                    String smsAdvanceNotice = MessageTypeModel.MESSAGE_TYPE_SMS_ADVANCE_NOTICE;
                    String smsAdvanceNoticeMore = MessageTypeModel.MESSAGE_TYPE_SMS_ADVANCE_NOTICE_MORE;
                    if (title2.equals(productName)) {
                        smsTodayNotice = smsTodayNotice + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
                        smsAdvanceNotice = smsAdvanceNotice + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
                        smsAdvanceNoticeMore = smsAdvanceNoticeMore + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
                    }
                    logger.info("day------------" + day);
                    UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(borrowRepay.getUserId());
                    if (day == 0) {
                        //还款当日提醒
                        clSmsService.sendAdvanceSms(userBaseInfo.getPhone(), smsTodayNotice, borrowRepay);
                    }
                    //还款前发送短信通知n-1
                    if (day == 1 && index == 0) {

                        clSmsService.sendAdvanceSms(userBaseInfo.getPhone(), smsAdvanceNotice, borrowRepay);
                    }
                    // 还款前发送短信通知n-2
                    if (day == 2 && index == 0) {
                        clSmsService.sendAdvanceSms(userBaseInfo.getPhone(), smsAdvanceNoticeMore, borrowRepay);
                    }
                    succeed++;
                    total++;
                } catch (Exception e) {
                    fail++;
                    total++;
                    logger.error(e.getMessage(), e);
                }
            }
        }
        logger.info("还款日前0~2天计算结束");
        quartzRemark = "执行总次数" + total + ",成功" + succeed + "次,失败" + fail + "次";
        return quartzRemark;
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog ql = new QuartzLog();
        Map<String, Object> qiData = new HashMap<>();
        QuartzInfo qi = quartzInfoService.findByCode("advanceNotice");
        try {
            qiData.put("id", qi.getId());
            ql.setQuartzId(qi.getId());
            ql.setStartTime(DateUtil.getNow());

            String remark = repayInform();

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
            //quartzLogService.save(ql);
            //quartzInfoService.update(qiData);
        }
    }
}