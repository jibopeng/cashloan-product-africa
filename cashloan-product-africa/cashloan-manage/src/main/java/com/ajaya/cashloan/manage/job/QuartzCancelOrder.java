package com.ajaya.cashloan.manage.job;

import com.ajaya.cashloan.cl.model.MessageSendModel;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tool.util.BeanUtil;

import java.util.List;


@Component
@Lazy(value = false)
public class QuartzCancelOrder implements Job {

    private static final Logger logger = Logger.getLogger(QuartzCancelOrder.class);

    /**
     * 定时跑 取消超过15天订单
     *
     * @throws ServiceException
     */
    public String late() throws ServiceException {
        ClBorrowService clBorrowService = (ClBorrowService) BeanUtil.getBean("clBorrowService");


        List<MessageSendModel> list = clBorrowService.selectCancelOrderInfoList();
        for (MessageSendModel messageSendModel : list) {
            Borrow borrow = clBorrowService.findByPrimary(messageSendModel.getBorrowId());
            if (BorrowModel.STATE_AUTO_PASS.equals(borrow.getState()) || BorrowModel.STATE_PASS.equals(borrow.getState())) {
                try {
                    borrow.setState("70");
                    clBorrowService.updateById(borrow);
                    clBorrowService.savePressState(borrow, "70", "Order has been cancelled for more than 15 days");
                    logger.info("订单 " + messageSendModel.getBorrowId() + " 订单状态取消成功 审核日期:" + messageSendModel.getCreateTime());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("订单 " + messageSendModel.getBorrowId() + " 订单状态取消异常 state:" + borrow.getState());
                }
            } else {
                logger.info("订单 " + messageSendModel.getBorrowId() + " 订单状态不能取消 state:" + borrow.getState());
            }
        }
        return null;
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            String remark = late();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            logger.info("保存定时任务日志");
        }
    }

}
