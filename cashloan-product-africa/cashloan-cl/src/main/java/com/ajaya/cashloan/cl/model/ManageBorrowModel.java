package com.ajaya.cashloan.cl.model;

import java.io.File;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import tool.util.DateUtil;

import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;


/**
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-3-14 上午9:49:07
 */
public class ManageBorrowModel extends Borrow {

	private static final long serialVersionUID = 1L;

	public static ManageBorrowModel instance(Borrow borrow) {
		ManageBorrowModel borrowModel = new ManageBorrowModel(); 
		BeanUtils.copyProperties(borrow, borrowModel);
		return borrowModel;
	}
	
	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 状态中文含义
	 */
	private String stateStr;

	/**
	 * 待还款金额/已还款金额
	 */
	private Double repayAmount;

	/**
	 * 还款时间
	 */
	private String repayTime;

	/**
	 * 逾期罚金
	 */
	private Double penaltyAmout;

	/**
	 * 逾期罚金
	 */
	private String penaltyDay;

    /**
     * 放款时间
     */
    private Date loanTime;
    
    /**
	 * 逾期等级
	 */
	private String level;
	
	private Date passTime;
	
	private String uuid;
	
	
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	
	/**
	 * 渠道名称
	 */
	private String channelName;
	
	
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * 应还款总额 加逾期金额
	 */
	private Double repayTotal;
	
	/**
	 * 已还款总额 加逾期金额
	 */
	private Double repayYesTotal;

	public Double getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(Double repayTotal) {
		this.repayTotal = repayTotal;
	}
	
	public Double getRepayYesTotal() {
		return repayYesTotal;
	}

	public void setRepayYesTotal(Double repayYesTotal) {
		this.repayYesTotal = repayYesTotal;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public Double getPenaltyAmout() {
		return penaltyAmout;
	}

	public void setPenaltyAmout(Double penaltyAmout) {
		this.penaltyAmout = penaltyAmout;
	}

	public String getPenaltyDay() {
		return penaltyDay;
	}

	public void setPenaltyDay(String penaltyDay) {
		this.penaltyDay = penaltyDay;
	}

	public String getStateStr() {
		this.stateStr = BorrowModel.manageConvertBorrowState(this.getState());
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取借款订单id
	 * @return borrowId
	 */
	public long getBorrowId() {
		//继承自borrow，自带id，不需要再重新设置borrowid
		return getId();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	
	
	private String bankAccount;
	
	private String bankName;
	
	private String ifscCode;
	
	
	
	public String getProtocolPath() {
		String s = File.separator;
		String rootFile = "/" + "readFile.htm?path=";
		String filePath = s + "data" + s + "protocol" + s + "borrow" + s + DateUtil.dateStr(createTime, DateUtil.DATEFORMAT_STR_013) + s + orderNo + "-contract.pdf";
		if (s.equals("\\")) {
			filePath = "D:" + filePath;
			filePath = filePath.replace("\\", "/");
        }
		String protocolPath = rootFile + filePath;
		return protocolPath;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	
	
}
