package com.ajaya.cashloan.manage.job;

import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.PayLogService;
import com.ajaya.cashloan.core.common.context.Global;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy(value = false)
@org.quartz.DisallowConcurrentExecution
public class QuartzPayFail implements Job {

    private static final Logger logger = Logger.getLogger(QuartzPayFail.class);

    /**
     * 定时处理放款失败
     *
     * @throws ServiceException
     */
    public String late() throws ServiceException {
        ClBorrowMapper clBorrowMaper = (ClBorrowMapper) BeanUtil.getBean("clBorrowMapper");
        ClBorrowService clBorrowService = (ClBorrowService) BeanUtil.getBean("clBorrowService");
        PayLogService payLogService = (PayLogService) BeanUtil.getBean("payLogService");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int failedCountflage = Global.getInt("pay_out_failed_count");
        // 查询放款失败的订单
        paramMap.put("state", "31");
        List<Borrow> list = clBorrowMaper.listSelective(paramMap);
        if (list != null && list.size() > 0) {
            for (Borrow borrow : list) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //查询当前订单状态
                borrow = clBorrowMaper.findByPrimary(borrow.getId());
                logger.info("borrowId:" + borrow.getId() + ",当前订单状态:" + borrow.getState());
                if (BorrowModel.STATE_REPAY_FAIL.equals(borrow.getState())) {
                    //查询该笔订单放款次数
                    int failedCount = payLogService.findPayOutFailedCount(borrow.getId());
                    //如果用户重复放款次数达到配置上线则取消放款
                    logger.info("订单 "+borrow.getId()+" 已放款次数："+failedCount+" 重试放款上限："+failedCountflage);
                    if (failedCount >= failedCountflage) {
                        clBorrowService.cancelBorrow(borrow);
                    } else {
                        logger.info("borrow id " + borrow.getId() + "   state  " + borrow.getState());
                        // 设置borrow表为29,放款中
                        borrow.setState(BorrowModel.DAKUAN_ING);
                        clBorrowService.updateById(borrow);
                        logger.info("订单 " + borrow.getId() + "设置成放款中状态");
                        clBorrowService.borrowLoan(borrow, new Date());
                    }
                } else {
                    logger.info("订单 " + borrow.getId() + "不是放款失败状态");
                }
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
