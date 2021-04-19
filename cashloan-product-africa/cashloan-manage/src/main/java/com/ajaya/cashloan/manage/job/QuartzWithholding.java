package com.ajaya.cashloan.manage.job;

import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.PayLogService;
import com.ajaya.cashloan.cl.service.PayStackService;
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
public class QuartzWithholding implements Job {

    private static final Logger logger = Logger.getLogger(QuartzWithholding.class);

    /**
     * 执行代扣
     *
     * @throws ServiceException
     */
    public String late() throws ServiceException {
        ClBorrowMapper clBorrowMapper = (ClBorrowMapper) BeanUtil.getBean("clBorrowMapper");
        PayStackService payStackServiceImpl = (PayStackService) BeanUtil.getBean("payStackService");
        List<Long> list = clBorrowMapper.findNeedWithholdingBorrowIds();
        //遍历执行订单代扣
        for (Long borrowId : list) {
            payStackServiceImpl.doWithholdingByBorrowId(borrowId);
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
