package com.ajaya.cashloan.manage.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.entity.SpecialListRequest;
import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.common.util.HttpUtil;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.domain.BorrowRepayLogFq;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrder;
import com.ajaya.cashloan.cl.mapper.BorrowRepayFqMapper;
import com.ajaya.cashloan.cl.mapper.BorrowRepayLogFqMapper;
import com.ajaya.cashloan.cl.model.BorrowRepayModel;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.ajaya.cashloan.manage.domain.QuartzInfo;
import com.ajaya.cashloan.manage.domain.QuartzLog;
import com.ajaya.cashloan.manage.service.QuartzInfoService;
import com.ajaya.cashloan.manage.service.QuartzLogService;

import tool.util.BeanUtil;
import tool.util.BigDecimalUtil;
import tool.util.DateUtil;
import tool.util.StringUtil;


@Component
@Lazy(value = false)
public class QuartzLate implements Job{
	
	private static final Logger logger = Logger.getLogger(QuartzLate.class);
	
	/**
	 * 定时计算逾期
	 * @throws ServiceException 
	 */
	//43 不计算逾期, 
	public String late() throws ServiceException {
		BorrowRepayService borrowRepayService = (BorrowRepayService)BeanUtil.getBean("borrowRepayService");
		BorrowProgressService borrowProgressService = (BorrowProgressService)BeanUtil.getBean("borrowProgressService");
		ClBorrowService clBorrowService = (ClBorrowService)BeanUtil.getBean("clBorrowService");
		UrgeRepayOrderService urgeRepayOrderService = (UrgeRepayOrderService)BeanUtil.getBean("urgeRepayOrderService");
		BorrowRepayFqMapper borrowRepayFqMapper = (BorrowRepayFqMapper)BeanUtil.getBean("borrowRepayFqMapper");
		BorrowRepayLogFqMapper borrowRepayFqLogMapper = (BorrowRepayLogFqMapper)BeanUtil.getBean("borrowRepayLogFqMapper");
		UrgeOrderPushLogService urgeOrderPushLogService = (UrgeOrderPushLogService)BeanUtil.getBean("urgeOrderPushLogService");
		ClBorrowRiskBusinessService clBorrowRiskBusinessService = (ClBorrowRiskBusinessService)BeanUtil.getBean("clBorrowRiskBusinessService");
		logger.info("进入逾期计算...");
		String quartzRemark = null;
		int succeed = 0;
		int fail = 0;
		int total = 0;
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("state",BorrowRepayModel.STATE_REPAY_NO);
		//List<BorrowRepay> list = borrowRepayService.listSelective(paramMap);
		List<BorrowRepay> list = borrowRepayService.listByRepayTimeByDesc(paramMap);
		long badDebtDay = Long.parseLong(Global.getValue("bad_debt_day"));//逾期多少天自动标记为坏账
		String black_white_url = Global.getValue("black_white_url");//黑白名单共库url
		logger.info("进入逾期计算...black_white_url:" + black_white_url);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				try {
					Date date = new Date();
					long day = DateUtil.daysBetween(date, list.get(i).getRepayTime());
					
					//计算逾期的时候,首先判断一下是否已经还款
					long borrowId = list.get(i).getBorrowId();
					Map<String, Object> paramMapBorrowId = new HashMap<>();
					paramMapBorrowId.put("borrowId", borrowId);
					BorrowRepay borrowRepay = borrowRepayService.findSelective(paramMapBorrowId);
					if(BorrowRepayModel.STATE_REPAY_YES.equals(borrowRepay.getState())){
						logger.info("borrowId:" + borrowId + ",状态已经是还款,不再计算逾期");
						continue;
					}
					Borrow borrow = clBorrowService.findByPrimary(list.get(i).getBorrowId());
					if(BorrowModel.STATE_REPAY_PROCESSING.equals(borrow.getState())){
						logger.info("borrowId:" + borrowId + ",状态是还款处理中,暂不计算逾期");
						continue;
					}
					logger.info("borrowId:" + borrowId + ",day:" + day);
					if (Math.abs(day)>Integer.parseInt(list.get(i).getPenaltyDay())) {
						if (day<0) {
							
							//借款天数与逾期利率对应
							double amout = 0;
							double penaltyAmoutMax = Double.parseDouble(Global.getValue("penalty_amout_max"));
							double penaltyAmout = list.get(i).getPenaltyAmout();
							
							Boolean isMax = true;
							//罚息超过上限不再计算
							String penaltyFee = Global.getProduct(borrow.getUserType()).getPenaltyFee();
							String[] penaltyFees = penaltyFee.split(",");
							logger.info("borrowId:" + borrowId + ",penaltyFee :" + penaltyFee);
							if ((penaltyAmout/borrow.getAmount())<penaltyAmoutMax) {
								for (int j = 0; j < penaltyFees.length; j++) {
									String[] penaltyParams=penaltyFees[j].split("-");
									if(penaltyParams.length==2){
										//penaltyParams[0]天数，penaltyParams[1]费率
										if (penaltyParams[0].equals(borrow.getTimeLimit())) {
											amout = BigDecimalUtil.decimal(
													BigDecimalUtil.mul(borrow.getAmount(), Double.parseDouble(penaltyParams[1])),2);
										}
										logger.info("borrowId:" + borrowId + ",amout :" + amout);
									}											
								}
								isMax = false;
							}else {
								amout = borrow.getAmount()*penaltyAmoutMax;
							}
							Map<String,Object> data = new HashMap<>();
							BorrowRepay br = new BorrowRepay();
							br.setId(list.get(i).getId());
							
							Integer countSuccessFq = borrowRepayFqMapper.getCountSuccessFq(list.get(i).getBorrowId());
							logger.info("还款计划id--" + br.getId() + " ==> 已经逾期 ,部分还款成功笔数:" + countSuccessFq);
							if(countSuccessFq > 0){
								logger.info("还款计划id--" + br.getId() + " ==> 已经逾期 ,已经有部分还款成功");
								logger.info("还款计划id--" + br.getId() + " ==> 已经逾期 ,已经有部分还款成功,获取最近一次分期还款成功");
								BorrowRepayLogFq lastBorrowRepayLogFq = borrowRepayFqLogMapper.getLastBorrowRepayLogFq(list.get(i).getBorrowId());
								//查看是否超过时间限制
								Date createTime = lastBorrowRepayLogFq.getCreateTime();
								int daysBetween = DateUtil.daysBetween(new Date(), createTime);// 后面时间减去前面时间 还款时间减去当前时间
								logger.info("还款计划id--" + br.getId() + " ==> 已经逾期 ,已经有部分还款成功,获取最近一次分期还款成功,距离当前时间的天数:" + daysBetween);
								if(daysBetween < 0 && Math.abs(daysBetween) > Global.getInt("fq_late_time")){
									logger.info("还款计划id--" + br.getId() + " ==> 已经逾期 ,已经有部分还款成功,获取最近一次分期还款成功,重新计算逾期");
									//超过几天计算几天逾期金额
									penaltyAmout = penaltyAmout + BigDecimalUtil.decimal(amout*Math.abs(Math.abs(daysBetween) - Global.getInt("fq_late_time")), 2);
									br.setPenaltyAmout(penaltyAmout);
								} else {
									br.setPenaltyAmout(penaltyAmout);
								}
								if (isMax) {
									br.setPenaltyAmout(BigDecimalUtil.decimal(amout, 2));
								}
							} else {
								if (isMax) {
									br.setPenaltyAmout(BigDecimalUtil.decimal(amout, 2));
								}else {
									br.setPenaltyAmout(BigDecimalUtil.decimal(amout*Math.abs(day), 2));
								}
							}
							
							logger.info("borrowId:" + borrowId + ",setPenaltyAmout :" + br.getPenaltyAmout());
							
							long penDay = DateUtil.daysBetween(new Date(), list.get(i).getRepayTime());
							br.setPenaltyDay(Long.toString(Math.abs(penDay)));
							
							logger.info("还款计划id--" + br.getId() + " ==> 已经逾期 " + br.getPenaltyDay() + " 天,逾期费用 " + br.getPenaltyAmout() + "元");
							
							int msg  = borrowRepayService.updateLate(br);
							if (msg>0) {
								logger.debug("--修改逾期信息成功--");
								//保存逾期进度
								int count = borrowProgressService.isNormalBorrowProgress(list.get(i).getBorrowId());
								if(count <= 0){
									logger.debug("---------添加逾期进度---------");
									clBorrowService.savePressState(borrow, BorrowModel.STATE_DELAY,"");
									data = new HashMap<>();
									data.put("id", list.get(i).getBorrowId());
									data.put("state", BorrowModel.STATE_DELAY);
									msg = clBorrowService.updateSelective(data);
									logger.debug("---------添加逾期结束---------");
								}
								
								//催收计划
								logger.debug("---------修改催收计划start-------");
								UrgeRepayOrder uro =  urgeRepayOrderService.findByBorrowId(list.get(i).getBorrowId());
								if (StringUtil.isBlank(uro)) {
									urgeRepayOrderService.saveOrder(list.get(i).getBorrowId());

								}else {
									Map<String,Object> uroMap = new HashMap<>();
									uroMap.put("penaltyAmout", br.getPenaltyAmout());
									uroMap.put("penaltyDay", br.getPenaltyDay());
									uroMap.put("id", uro.getId());
									uroMap.put("updateTime", DateUtil.getNow());
									urgeRepayOrderService.updateLate(uroMap);
								}
								try {
									urgeOrderPushLogService.pushUrgeOrder(borrow.getId());
									clBorrowRiskBusinessService.saveBorrowRiskBusinessForPenalty(borrowId);
								} catch (Exception e) {
									logger.info("推送用户userId=" + borrow.getUserId() + "的用户催收数据异常" + e.getMessage());
								}

								logger.debug("---------修改催收计划end-------");
							}else {
								logger.error("定时计算逾期任务修改数据失败");
							}
						}
					}
					succeed++;
					total++;
				} catch (Exception e) {
					fail ++;
					total++;
					logger.error(e.getMessage(),e);
				}
			}
		}
			
		logger.info("逾期计算结束...");
		quartzRemark = "执行总次数"+total+",成功"+succeed+"次,失败"+fail+"次";
		return quartzRemark;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		QuartzInfoService quartzInfoService = (QuartzInfoService)BeanUtil.getBean("quartzInfoService");
		QuartzLogService quartzLogService = (QuartzLogService)BeanUtil.getBean("quartzLogService");
		QuartzLog ql = new QuartzLog();
		Map<String,Object> qiData = new HashMap<>();
		QuartzInfo qi = quartzInfoService.findByCode("doLate");
		try {
			qiData.put("id", qi.getId());
			ql.setQuartzId(qi.getId());
			ql.setStartTime(DateUtil.getNow());
			
			String remark = late();
			
			ql.setTime(DateUtil.getNow().getTime()-ql.getStartTime().getTime());
			ql.setResult("10");
			ql.setRemark(remark);
			qiData.put("succeed", qi.getSucceed()+1);
			
		}catch (Exception e) {
			ql.setResult("20");
			qiData.put("fail", qi.getFail()+1);
			logger.error(e.getMessage(),e);
		}finally{
			logger.info("保存定时任务日志");
			/*quartzLogService.save(ql);
			quartzInfoService.update(qiData);*/
		}
	}
	
}