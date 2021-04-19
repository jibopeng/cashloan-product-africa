package com.ajaya.cashloan.cl.model;

import java.util.Date;

import com.ajaya.cashloan.cl.domain.PayLog;

public class ManagePayLogModel extends PayLog {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 借款订单号
	 */
	private String borrowOrderNo;
	
	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 用户姓名
	 */
	private String realName;

	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 借款时间 
	 */
	private Date loanTime; 

	/**
	 * 状态中文描述
	 */
	private String stateStr;

	/**
	 * 资金来源中文描述
	 */
	private String sourceStr;

	/**
	 * 付款方式中文描述
	 */
	private String typeStr;

	/**
	 * 场景中文描述
	 */
	private String scenesStr;
	
	/**
	 * 借款订单状态
	 */
	private String borrowState;
	
	/**
	 * 支付渠道
	 */
	private String payChannel;

	
	/**
	 * 获取借款订单号
	 * @return borrowOrderNo
	 */
	public String getBorrowOrderNo() {
		return borrowOrderNo;
	}

	/**
	 * 设置借款订单号
	 * @param borrowOrderNo
	 */
	public void setBorrowOrderNo(String borrowOrderNo) {
		this.borrowOrderNo = borrowOrderNo;
	}

	/**
	 * 获取登录名
	 * 
	 * @return loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设置登录名
	 * 
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 获取用户姓名
	 * 
	 * @return realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置用户姓名
	 * 
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取手机号码
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置手机号码
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取借款时间
	 * 
	 * @return loanTime
	 */
	public Date getLoanTime() {
		return loanTime;
	}

	/**
	 * 设置借款时间
	 * 
	 * @param loanTime
	 */
	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	/**
	 * 获取状态中文描述
	 * 
	 * @return stateStr
	 */
	public String getStateStr() {
		this.stateStr = stateConvet(this.getState());
		return stateStr;
	}
	
	public String getStateManageStr() {
		if(this.getState().equals("10")){
			return "待支付";
		} else if(this.getState().equals("40")){
			return "支付成功";
		} else {
			return "支付失败";
		}
	}

	/**
	 * 设置状态中文描述
	 * 
	 * @param stateStr
	 */
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	/**
	 * 获取资金来源中文描述
	 * 
	 * @return sourceStr
	 */
	public String getSourceStr() {
		this.sourceStr = sourceConvert(this.getSource());
		return sourceStr;
	}

	/**
	 * 设置资金来源中文描述
	 * 
	 * @param sourceStr
	 */
	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	/**
	 * 获取付款方式中文描述
	 * 
	 * @return typeStr
	 */
	public String getTypeStr() {
		this.typeStr = typeConvert(this.getType());
		return typeStr;
	}

	/**
	 * 设置付款方式中文描述
	 * 
	 * @param typeStr
	 */
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	
	/**
	 * 获取场景中文描述
	 * 
	 * @return scenesStr
	 */
	public String getScenesStr() {
		this.scenesStr = scenesConvert(this.getScenes());
		return scenesStr;
	}

	public String getScenesManageStr() {
		if(this.getScenes().equals("10")){
			return "放款";
		} else {
			return "还款";
		}
	}
	
	/**
	 * 设置场景中文描述
	 * 
	 * @param scenesStr
	 */
	public void setScenesStr(String scenesStr) {
		this.scenesStr = scenesStr;
	}
	
	/**
	 * 获取借款订单状态
	 * 
	 * @return borrowState
	 */
	public String getBorrowState() {
		return borrowState;
	}

	/**
	 * 设置借款订单状态
	 * 
	 * @param borrowState
	 */
	public void setBorrowState(String borrowState) {
		this.borrowState = borrowState;
	}

	/**
	 * 付款状态中文描述转换
	 * 
	 * @param state
	 * @return
	 */
	public static String stateConvet(String state) {
		String stateStr = null;
		if (PayLogModel.STATE_PAYMENT_WAIT.equals(state)) {
			stateStr = "OPTION.WAITING_FOR_THE_PAYMENT";
		} else if (PayLogModel.STATE_PAYMENT_SUCCESS.equals(state)) {
			stateStr = "OPTION.PAY_SUCESS";
		} else if (PayLogModel.STATE_PAYMENT_FAILED.equals(state)) {
			stateStr = "OPTION.PAY_FAILED";
		} else {
			stateStr = " - ";
		}
		return stateStr;
	}

	/**
	 * 资金来源中文描述转换
	 * 
	 * @param source
	 * @return
	 */
	public static String sourceConvert(String source) {
		String sourceStr;
		if (PayLogModel.SOURCE_FUNDS_OWN.equals(source)) {
			sourceStr = "自有资金";
		} else if (PayLogModel.SOURCE_FUNDS_OTHER.equals(source)) {
			sourceStr = "其他资金";
		} else {
			sourceStr = " - ";
		}
		return sourceStr;
	}

	/**
	 * 付款方式中文描述转换
	 * @param type
	 * @return
	 */
	public static String typeConvert(String type) {
		String typeStr = null;
		if (PayLogModel.TYPE_PAYMENT.equals(type)) {
			typeStr = "代扣";
		} else if (PayLogModel.TYPE_COLLECT.equals(type)) {
			typeStr = "主动还款";
		} else if (PayLogModel.TYPE_OFFLINE_PAYMENT.equals(type)) {
			typeStr = "线下代付";
		}else {
			typeStr = " - ";
		}

		return typeStr;
	}

	public static String scenesConvert(String scenes) {
		String scenesStr = null;
		if (PayLogModel.SCENES_LOANS.equals(scenes)) {
			scenesStr = "OPTION.LOAN";
		} else {
			scenesStr = "OPTION.REPAYMENT";
		}
		return scenesStr;
	}
	
	
	public  String getRemark(){
		String remark = super.getRemark();
		if("success".equals(remark)){
			remark = "OPTION.SUCCESS";
		} else if("failed".equals(remark)){
			remark = "OPTION.FAILED";
		} else {
			remark = "-";
		}
		return remark;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	
	
}
