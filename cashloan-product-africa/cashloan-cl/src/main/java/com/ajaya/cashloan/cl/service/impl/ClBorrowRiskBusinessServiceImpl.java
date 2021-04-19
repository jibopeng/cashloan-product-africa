package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.cl.domain.BorrowProgress;
import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.domain.BorrowRepayLog;
import com.ajaya.cashloan.cl.domain.ClBorrowRiskBusiness;
import com.ajaya.cashloan.cl.mapper.BorrowProgressMapper;
import com.ajaya.cashloan.cl.mapper.BorrowRepayLogMapper;
import com.ajaya.cashloan.cl.mapper.BorrowRepayMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowMapper;
import com.ajaya.cashloan.cl.mapper.ClBorrowRiskBusinessMapper;
import com.ajaya.cashloan.cl.service.ClBorrowRiskBusinessService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.domain.Borrow;


/**
 * 风控订单业务表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-16 14:32:46
 */
 
@Service("clBorrowRiskBusinessService")
public class ClBorrowRiskBusinessServiceImpl extends BaseServiceImpl<ClBorrowRiskBusiness, Long> implements ClBorrowRiskBusinessService {
	
    private static final Logger logger = LoggerFactory.getLogger(ClBorrowRiskBusinessServiceImpl.class);
   
    @Resource
    private ClBorrowRiskBusinessMapper clBorrowRiskBusinessMapper;
    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    
    private static String APP_ID = "CashBoom";

	@Override
	public BaseMapper<ClBorrowRiskBusiness, Long> getMapper() {
		return clBorrowRiskBusinessMapper;
	}

	/**
	 * CREATE TABLE `cl_borrow_risk_business` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	  `user_id` bigint(20) NOT NULL COMMENT '用户主键', #
	  `appid` varchar(10) NOT NULL COMMENT '产品标识', #
	  `borrow_id` bigint(20) DEFAULT NULL COMMENT '订单主键',#
	  `apply_time` datetime DEFAULT NULL COMMENT '申请时间', #
	  `application_amount` decimal(12,2) DEFAULT NULL COMMENT '申请金额', #
	  `application_term` varchar(10) DEFAULT NULL COMMENT '申请贷款期限，单位：天', #
	  `is_new` varchar(10) DEFAULT NULL COMMENT '新/老用户 1：新用户，0：老用户',
	  `is_audit` varchar(10) DEFAULT NULL COMMENT '是否入审 1,入审，0未入审',#
	  `approval` varchar(10) DEFAULT NULL COMMENT '是否审批通过 1:通过，0:未通过，“”未进入审批',
	  `approval_time` datetime DEFAULT NULL COMMENT '审批时间 格式为%Y-%m-%d %H:%M:%S “”未进入审批流程',
	  `loan_success` varchar(10) DEFAULT NULL COMMENT '1成功放款，0未放款，“”未进入放款流程',
	  `loan_success_time` datetime DEFAULT NULL COMMENT '放款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款',
	  `lending_ammount` decimal(12,2) DEFAULT NULL COMMENT '放款金额，“”未放款',
	  `expiry_time` datetime DEFAULT NULL COMMENT '到期应还时间，格式为%Y-%m-%d %H:%M:%S，“”未放款',
	  `repay_time` datetime DEFAULT NULL COMMENT '成功还款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款',
	  `overdue_days` int(10) DEFAULT NULL COMMENT '逾期天数，“”未放款',
	  `repay_type` varchar(10) DEFAULT NULL COMMENT '#0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款',
	  `order_no` varchar(20) DEFAULT NULL COMMENT '订单号', #
	  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
	  PRIMARY KEY (`id`),
	  UNIQUE KEY `borrow_id` (`borrow_id`),
	  KEY `user_id` (`user_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单风控业务表';
	 */
	@Override
	public Integer saveBorrowRiskBusiness(Long borrowId) {
		Integer result = 0;
		logger.info("borrowId {} 开始保存风控业务表", borrowId);
		try {
			Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
			if (borrow == null) {
				logger.info("borrowId {} 开始保存风控业务表,没有发现订单", borrowId);
				return 0;
			}
			Long userId = borrow.getUserId();
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("borrowId", borrowId);
			ClBorrowRiskBusiness borrowRiskBusiness = clBorrowRiskBusinessMapper.findSelective(paramMap);
			Boolean flag = false;
			if (borrowRiskBusiness == null) {
				borrowRiskBusiness = new ClBorrowRiskBusiness();
				flag = true;
			}
			borrowRiskBusiness.setAppid(APP_ID);
			borrowRiskBusiness.setUserId(userId);
			borrowRiskBusiness.setBorrowId(borrowId);
			borrowRiskBusiness.setApplyTime(borrow.getCreateTime());
			borrowRiskBusiness.setIsNew("0".equals(borrow.getUserType()) ? "0" : "1");//'新/老用户 1：新用户，0：老用户'
			//`is_audit` varchar(10) DEFAULT NULL COMMENT '是否入审 1,入审，0未入审',
			borrowRiskBusiness.setIsAudit("0");
			borrowRiskBusiness.setOrderNo(borrow.getOrderNo());
			if (flag) {
				borrowRiskBusiness.setCreateTime(new Date());
				result = clBorrowRiskBusinessMapper.save(borrowRiskBusiness);
			} else {
				borrowRiskBusiness.setUpdateTime(new Date());
				result = clBorrowRiskBusinessMapper.update(borrowRiskBusiness);
			}
			logger.info("borrowId {} 开始保存风控业务表, 保存结果 {}", borrowId, result);
		} catch (Exception e) {
			logger.info("borrowId:" + borrowId +",开始保存风控业务表,报错", e);
		}
		return result;
	}

	
	/**
	 * 	  `is_audit` varchar(10) DEFAULT NULL COMMENT '是否入审 1,入审，0未入审',#
	  `approval` varchar(10) DEFAULT NULL COMMENT '是否审批通过 1:通过，0:未通过，“”未进入审批',
	  `approval_time` datetime DEFAULT NULL COMMENT '审批时间 格式为%Y-%m-%d %H:%M:%S “”未进入审批流程',
	 */
	@Override
	public Integer saveBorrowRiskBusinessForAudit(Long borrowId) {
		Integer result = 0;
		logger.info("borrowId {} 开始保存风控业务表, 更新审核状态", borrowId);
		try {
			Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
			if (borrow == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新审核状态,没有发现订单", borrowId);
				return 0;
			}
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("borrowId", borrowId);
			ClBorrowRiskBusiness borrowRiskBusiness = clBorrowRiskBusinessMapper.findSelective(paramMap);
			if (borrowRiskBusiness == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新审核状态,没有发现风控业务订单", borrowId);
				return 0;
			}
			
			if("10".equals(borrow.getState())){
				logger.info("borrowId {} 开始保存风控业务表, 更新审核状态,仍然是待机审", borrowId);
				return 0;
			}
			
			String approval = "";
			if ("20".equals(borrow.getState()) || "26".equals(borrow.getState()) || "29".equals(borrow.getState())
					|| "30".equals(borrow.getState()) || "31".equals(borrow.getState())
					|| "40".equals(borrow.getState()) || "41".equals(borrow.getState())
					|| "50".equals(borrow.getState())) {
				approval = "1";
			} else if("21".equals(borrow.getState()) || "27".equals(borrow.getState()) || "70".equals(borrow.getState())) {
				approval = "0";
			}
			String is_audit = "0";
			if (StringUtil.isNotBlank(borrow.getState()) && Integer.parseInt(borrow.getState()) > 10) {
				is_audit = "1";
			}
			Date approval_time = null;
			paramMap.put("state", borrow.getState());
			List<BorrowProgress> list = borrowProgressMapper.listSelective(paramMap);
			if (list != null && list.size() > 0) {
				approval_time = list.get(list.size() - 1).getCreateTime();
			}
			borrowRiskBusiness.setApplicationAmount(borrow.getAmount());
			borrowRiskBusiness.setApplicationTerm(borrow.getTimeLimit());
			borrowRiskBusiness.setIsAudit(is_audit);
			borrowRiskBusiness.setApproval(approval);
			borrowRiskBusiness.setApprovalTime(approval_time);
			borrowRiskBusiness.setUpdateTime(new Date());
			result = clBorrowRiskBusinessMapper.update(borrowRiskBusiness);
			logger.info("borrowId {} 开始保存风控业务表, 更新审核状态, 结果 {}", borrowId, result);
		} catch (Exception e) {
			logger.info("borrowId:" + borrowId +",开始保存风控业务表,报错", e);
		}
		return result;
	}

	/**
	 * `loan_success` varchar(10) DEFAULT NULL COMMENT '1成功放款，0未放款，“”未进入放款流程',
	  `loan_success_time` datetime DEFAULT NULL COMMENT '放款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款',
	  `lending_ammount` decimal(12,2) DEFAULT NULL COMMENT '放款金额，“”未放款',
	  `expiry_time` datetime DEFAULT NULL COMMENT '到期应还时间，格式为%Y-%m-%d %H:%M:%S，“”未放款',
	 */
	@Override
	public Integer saveBorrowRiskBusinessForPay(Long borrowId) {
		Integer result = 0;
		logger.info("borrowId {} 开始保存风控业务表, 更新放款状态", borrowId);
		try {
			Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
			if (borrow == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新放款状态,没有发现订单", borrowId);
				return 0;
			}
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("borrowId", borrowId);
			ClBorrowRiskBusiness borrowRiskBusiness = clBorrowRiskBusinessMapper.findSelective(paramMap);
			if (borrowRiskBusiness == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新放款状态,没有发现风控业务订单", borrowId);
				return 0;
			}
			BorrowRepay borrowRepay = borrowRepayMapper.findSelective(paramMap);
			if (borrowRepay == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新放款状态,没有发现borrowRepay订单", borrowId);
				return 0;
			}
			String loan_success = "1";
			Date loan_success_time = borrowRepay.getCreateTime();
			Double lending_ammount = borrow.getRealAmount();
			Date expiry_time = borrowRepay.getRepayTime();
			borrowRiskBusiness.setLoanSuccess(loan_success);
			borrowRiskBusiness.setLoanSuccessTime(loan_success_time);
			borrowRiskBusiness.setLendingAmmount(lending_ammount);
			borrowRiskBusiness.setExpiryTime(expiry_time);
			borrowRiskBusiness.setUpdateTime(new Date());
			result = clBorrowRiskBusinessMapper.update(borrowRiskBusiness);
			logger.info("borrowId {} 开始保存风控业务表, 更新放款状态, 结果 {}", borrowId, result);
		} catch (Exception e) {
			logger.info("borrowId:" + borrowId +",开始保存风控业务表,报错", e);
		}
		return result;
	}

	/**
	 * `repay_time` datetime DEFAULT NULL COMMENT '成功还款时间，格式为%Y-%m-%d %H:%M:%S，“”未放款',
	 * repay_type` varchar(10) DEFAULT NULL COMMENT '#0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款',
	 * @param borrowId
	 * @return
	 */
	@Override
	public Integer saveBorrowRiskBusinessForRepay(Long borrowId) {
		Integer result = 0;
		try {
			logger.info("borrowId {} 开始保存风控业务表, 更新还款状态", borrowId);
			Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
			if (borrow == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新还款状态,没有发现订单", borrowId);
				return 0;
			}
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("borrowId", borrowId);
			ClBorrowRiskBusiness borrowRiskBusiness = clBorrowRiskBusinessMapper.findSelective(paramMap);
			if (borrowRiskBusiness == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新还款状态,没有发现风控业务订单", borrowId);
				return 0;
			}
			BorrowRepayLog borrowRepayLog = borrowRepayLogMapper.findSelective(paramMap);
			if (borrowRepayLog == null) {
				logger.info("borrowId {} 开始保存风控业务表, 更新还款状态,没有发现borrowRepayLog订单", borrowId);
				return 0;
			}
			Date repay_time = borrowRepayLog.getCreateTime();
			String repay_type = "";
			if (Integer.parseInt(borrowRepayLog.getPenaltyDay()) == 0) {
				repay_type = "0";
			} else if (Integer.parseInt(borrowRepayLog.getPenaltyDay()) > 0) {
				repay_type = "2";
			}
			borrowRiskBusiness.setRepayTime(repay_time);
			borrowRiskBusiness.setRepayType(repay_type);
			borrowRiskBusiness.setUpdateTime(new Date());
			result = clBorrowRiskBusinessMapper.update(borrowRiskBusiness);
			logger.info("borrowId {} 开始保存风控业务表, 更新还款状态, 结果 {}", borrowId, result);
		} catch (Exception e) {
			logger.info("borrowId:" + borrowId +",开始保存风控业务表,报错", e);
		}
		return result;
	}

	/**
	 *  `overdue_days` int(10) DEFAULT NULL COMMENT '逾期天数，“”未放款',
	  `repay_type` varchar(10) DEFAULT NULL COMMENT '#0正常结清，2逾期结清，3逾期中，""未到还款日期,""未放款',
	 * @param borrowId
	 * @return
	 */
	@Override
	public Integer saveBorrowRiskBusinessForPenalty(Long borrowId) {
		Integer result = 0;
		logger.info("borrowId {} 开始保存风控业务表, 更新逾期状态", borrowId);
		try {
			Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
			if(borrow == null){
				logger.info("borrowId {} 开始保存风控业务表, 更新逾期状态,没有发现订单", borrowId);
				return 0;
			}
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("borrowId", borrowId);
			ClBorrowRiskBusiness borrowRiskBusiness = clBorrowRiskBusinessMapper.findSelective(paramMap);
			if(borrowRiskBusiness == null){
				logger.info("borrowId {} 开始保存风控业务表, 更新逾期状态,没有发现风控业务订单", borrowId);
				return 0;
			}
			
			BorrowRepay borrowRepay = borrowRepayMapper.findSelective(paramMap);
			if(borrowRepay == null){
				logger.info("borrowId {} 开始保存风控业务表, 更新逾期状态,没有发现borrowRepay订单", borrowId);
				return 0;
			}
			
			String overdue_days = borrowRepay.getPenaltyDay();
			borrowRiskBusiness.setOverdueDays(Integer.parseInt(overdue_days));
			if(borrowRepay.getState().equals("20")){
				borrowRiskBusiness.setRepayType("3");
			}
			borrowRiskBusiness.setUpdateTime(new Date());
			result = clBorrowRiskBusinessMapper.update(borrowRiskBusiness);
			logger.info("borrowId {} 开始保存风控业务表, 更新逾期款状态, 结果 {}", borrowId, result);
		} catch (Exception e) {
			logger.info("borrowId:" + borrowId +",开始保存风控业务表,报错", e);
		}
		return result;
	}
}