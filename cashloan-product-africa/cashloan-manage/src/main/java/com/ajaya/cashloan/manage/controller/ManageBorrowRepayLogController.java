package com.ajaya.cashloan.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.model.ManageBRepayLogModel;
import com.ajaya.cashloan.cl.service.BorrowRepayLogService;
import com.ajaya.cashloan.cl.service.BorrowRepayService;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.PayLogService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.service.CloanUserService;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import com.ajaya.cashloan.system.permission.annotation.RequiresPermission;

/**
 * 还款记录后台管理Controller
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017年3月30日 上午10:16:17
 *
 */

@Controller
@Scope("prototype")
public class ManageBorrowRepayLogController extends ManageBaseController{

	private static final Logger logger  = LoggerFactory.getLogger(ManageBorrowRepayLogController.class);
	
	@Resource
	private CloanUserService cloanUserService;
	@Resource
	private UserBaseInfoService userBaseInfoService;
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private BorrowRepayService borrowRepayService;
	@Resource
	private BorrowRepayLogService borrowRepayLogService;
	@Resource
	private PayLogService payLogService;
	

	/**
	 * 还款记录列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modules/manage/borrow/repay/log/list.htm")
	@RequiresPermission(code = "modules:manage:borrow:repay:log:list", name = "还款记录列表")
	public void page(
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize) {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<ManageBRepayLogModel> page = borrowRepayLogService.listModel(params, currentPage, pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	/**
	 * 退还 还款金额
	 * @param id
	 * @param amount
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrow/repayLog/refund.htm", method = { RequestMethod.POST })
	public void refund(@RequestParam(value = "id") Long id,
			@RequestParam(value = "amount") String amount) throws Exception {

		/*BorrowRepayLog borrowRepayLog = borrowRepayLogService.getById(id);
		BankCard bankCard = bankCardService.getBankCardByUserId(borrowRepayLog.getUserId());
		UserBaseInfo baseInfo = userBaseInfoService.findByUserId(borrowRepayLog.getUserId());
		Borrow borrow = clBorrowService.getById(borrowRepayLog.getBorrowId());

		Date date = DateUtil.getNow();
		String orderNo = OrderNoUtil.getSerialNumber();
		
		PaymentModel payment = new PaymentModel(orderNo);
		payment.setDt_order(DateUtil.dateStr3(date));
		if ("dev".equals(Global.getValue("app_environment"))) {
			payment.setMoney_order("0.01");
		} else {
			payment.setMoney_order(amount);
		}
		payment.setAmount(NumberUtil.getDouble(amount));

		payment.setCard_no(bankCard.getCardNo());
		payment.setAcct_name(baseInfo.getRealName());
		payment.setInfo_order(borrow.getOrderNo() + "付款");
		payment.setMemo(borrow.getOrderNo() + "付款");
		payment.setNotify_url(Global.getValue("server_host") + "/pay/lianlian/refundNotify.htm");
		LianLianHelper helper = new LianLianHelper();
		payment = (PaymentModel) helper.payment(payment);

		PayLog payLog = new PayLog();
		payLog.setOrderNo(payment.getNo_order());
		payLog.setUserId(borrow.getUserId());
		payLog.setBorrowId(borrow.getId());
		payLog.setAmount(payment.getAmount());
		payLog.setCardNo(bankCard.getCardNo());
		payLog.setBank(bankCard.getBank());
		payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
		payLog.setType(PayLogModel.TYPE_PAYMENT);
		payLog.setScenes(PayLogModel.SCENES_REFUND);
		
		if (payment.checkReturn()) { // 已生成连连支付单，付款处理中（交易成功，不是指付款成功，是指流程正常）
			payLog.setState(PayLogModel.STATE_PAYMENT_WAIT);
		} else if ("4002".equals(payment.getRet_code())
				|| "4003".equals(payment.getRet_code())
				|| "4004".equals(payment.getRet_code())) { // 疑似重复订单，待人工审核
			payLog.setConfirmCode(payment.getConfirm_code());
			payLog.setState(PayLogModel.STATE_PENDING_REVIEW);
			payLog.setUpdateTime(DateUtil.getNow());
		} else if ("4006".equals(payment.getRet_code())		//敏感信息加密异常
				|| "4007".equals(payment.getRet_code())     //敏感信息解密异常
				|| "4009".equals(payment.getRet_code())) {  //验证码异常
			payLog.setState(PayLogModel.STATE_PAYMENT_WAIT);
		} else {
			payLog.setState(PayLogModel.STATE_PAYMENT_FAILED);
			payLog.setUpdateTime(DateUtil.getNow());
		}
		payLog.setRemark(payment.getRet_msg());
		payLog.setPayReqTime(date);
		payLog.setCreateTime(DateUtil.getNow());
		payLogService.save(payLog);*/
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, result);
	}
	
	
	/**
	 * 补扣-还款金额
	 * 
	 * @param userId
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrow/repayLog/deduction.htm", method = { RequestMethod.POST })
	public void deduction(@RequestParam(value = "id") Long id,
			@RequestParam(value = "amount") String amount) throws Exception {

		/*BorrowRepayLog borrowRepayLog = borrowRepayLogService.getById(id);
		BorrowRepay borrowRepay = borrowRepayService.getById(borrowRepayLog.getRepayId());
		User user = cloanUserService.getById(borrowRepayLog.getUserId());
		UserBaseInfo baseInfo = userBaseInfoService.findByUserId(borrowRepayLog.getUserId());
		Borrow borrow = clBorrowService.getById(borrowRepayLog.getBorrowId());
		BankCard bankCard = bankCardService.getBankCardByUserId(borrowRepayLog
				.getUserId());

		// 扣款失败无异步通知 故先查询订单是否已经在支付处理中
		Map<String, Object> payLogMap = new HashMap<String, Object>();
		payLogMap.put("userId", borrowRepayLog.getUserId());
		payLogMap.put("borrowId", borrowRepayLog.getBorrowId());
		payLogMap.put("type", PayLogModel.TYPE_COLLECT);
		payLogMap.put("scenes",PayLogModel.SCENES_DEDUCTION);
		PayLog deductionLog = payLogService.findLatestOne(payLogMap);

		// 订单存在并不是支付失败记录
		if (null != deductionLog
				&& !PayLogModel.STATE_PAYMENT_FAILED.equals(deductionLog.getState())) {

			String orderNo = OrderNoUtil.getSerialNumber();
			QueryRepaymentModel queryRepayment = new QueryRepaymentModel(orderNo);
			queryRepayment.setNo_order(deductionLog.getOrderNo());
			queryRepayment.setDt_order(DateUtil.dateStr3(deductionLog.getPayReqTime()));
			LianLianHelper helper = new LianLianHelper();
			queryRepayment = (QueryRepaymentModel) helper.queryRepayment(queryRepayment);

			if (queryRepayment.checkReturn()
					&& LianLianConstant.RESULT_SUCCESS.equals(queryRepayment.getResult_pay())) {
				// 更新订单状态
				deductionLog.setState(PayLogModel.STATE_PAYMENT_SUCCESS);
				deductionLog.setUpdateTime(DateUtil.getNow());
				payLogService.updateById(deductionLog);
			}
		}

		Date payReqTime = DateUtil.getNow();
		String orderNo = OrderNoUtil.getSerialNumber();
		RepaymentModel repayment = new RepaymentModel(orderNo);
		repayment.setUser_id(user.getUuid());
		repayment.setBusi_partner(LianLianConstant.GOODS_VIRTUAL);
		repayment.setDt_order(DateUtil.dateStr3(payReqTime));
		repayment.setName_goods("还款" + borrow.getOrderNo());
		repayment.setInfo_order("repayment_" + borrow.getOrderNo());
		if ("dev".equals(Global.getValue("app_environment"))) {
			repayment.setMoney_order("0.01");
		} else {
			repayment.setMoney_order(StringUtil.isNull(amount));
		}
		repayment.setAmount(NumberUtil.getDouble(amount));
		RiskItems riskItems = new RiskItems();
		riskItems.setFrms_ware_category("2010");
		riskItems.setUser_info_mercht_userno(user.getUuid());
		riskItems.setUser_info_bind_phone(baseInfo.getPhone());
		riskItems.setUser_info_dt_register(DateUtil.dateStr3(user.getRegistTime()));
		riskItems.setUser_info_full_name(baseInfo.getRealName());
		riskItems.setUser_info_id_no(baseInfo.getIdNo());
		riskItems.setUser_info_identify_type("1");
		riskItems.setUser_info_identify_state("1");
		repayment.setRisk_item(JSONObject.toJSONString(riskItems));
		repayment.setSchedule_repayment_date(DateUtil.dateStr2(borrowRepay.getRepayTime()));
		repayment.setRepayment_no(borrow.getOrderNo());
		repayment.setNo_agree(bankCard.getAgreeNo());
		repayment.setNotify_url(Global.getValue("server_host")+ "/pay/lianlian/deductionNotify.htm");

		logger.info("进行补扣代扣" + amount);

		LianLianHelper helper = new LianLianHelper();
		repayment = (RepaymentModel) helper.repayment(repayment);

		PayLog payLog = new PayLog();
		payLog.setOrderNo(repayment.getOrderNo());
		payLog.setUserId(borrowRepay.getUserId());
		payLog.setBorrowId(borrowRepay.getBorrowId());
		payLog.setAmount(repayment.getAmount());
		payLog.setCardNo(bankCard.getCardNo());
		payLog.setBank(bankCard.getBank());
		payLog.setSource(PayLogModel.SOURCE_FUNDS_OWN);
		payLog.setType(PayLogModel.TYPE_COLLECT);
		payLog.setScenes(PayLogModel.SCENES_DEDUCTION);
		payLog.setState(PayLogModel.STATE_PAYMENT_WAIT);
		payLog.setRemark(repayment.getRet_msg());
		payLog.setPayReqTime(payReqTime);
		payLog.setCreateTime(DateUtil.getNow());
		payLogService.save(payLog);*/
		
		
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, result);
	}
	
	
	/**
	 * 还款中检查
	 * @param borrowId
	 */
	// 刘晓亮 start 注释
	/*@RequestMapping(value = "/modules/manage/borrow/repay/check.htm")
	@RequiresPermission(code = "modules:manage:borrow:repay:check", name = "还款记录列表")
	public void check(@RequestParam(value = "borrowId") String borrowId) {
		borrowRepayService.repayCheck(Long.parseLong(borrowId));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, result);
	}*/
}
