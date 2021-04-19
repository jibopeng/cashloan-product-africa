package com.ajaya.cashloan.cl.model;

import com.ajaya.cashloan.cl.domain.BorrowProgress;

public class BorrowProgressModel extends BorrowProgress {
	
	private static final long serialVersionUID = 1L;
	
	/** 申请成功待审核 */
	public static final String PROGRESS_APPLY = "10";
	/** 自动审核通过 */
	public static final String PROGRESS_AUTO_PASS = "20";
	/** 自动审核不通过 */
	public static final String PROGRESS_AUTO_REFUSED = "21";
	/** 自动审核未决待人工复审 */
	public static final String PROGRESS_NEED_REVIEW = "22";
	/** 人工复审通过 */
	public static final String PROGRESS_PERSON_PASS = "26";
	/** 人工复审不通过 */
	public static final String PROGRESS_PERSON_RESUSED = "27";
	/** 放款成功 */
	public static final String PROGRESS_LOAN_SUCCESS = "30";
	/** 放款失败 */
	public static final String PROGRESS_LOAN_FAIL = "31";
	/** 还款成功 */
	public static final String PROGRESS_REPAY_SUCCESS = "40";
	/** 逾期减免 */
	public static final String PROGRESS_REPAY_REMISSION_SUCCESS = "41";
	/** 还款中*/
	public static final String STATE_REPAY_PROCESSING = "43";
	/** 逾期 */
	public static final String PROGRESS_REPAY_OVERDUE = "50";
	/** 坏账 */
	public static final String PROGRESS_BILL_BAD = "90";

	private String msg;
	
	private String type;
	
	private String createTimeStr;
	
	/**
	 * 状态描述
	 */
	private String str;
	
	private String alter(String state) {
		String stateStr = "";
		if (PROGRESS_APPLY.equals(state)) {
			stateStr = "申请提交成功";
		}else if (PROGRESS_NEED_REVIEW.equals(state)) {
			stateStr = "审核中";
		}else if (PROGRESS_AUTO_PASS.equals(state)
				||PROGRESS_PERSON_PASS.equals(state)) {
			stateStr = "审核通过";
		}else if (PROGRESS_AUTO_REFUSED.equals(state)
				||PROGRESS_PERSON_RESUSED.equals(state)) {
			stateStr = "审核未通过";
		}else if (PROGRESS_LOAN_SUCCESS.equals(state)
				||PROGRESS_LOAN_FAIL.equals(state)) {
			stateStr = "已打款";
		}else if (STATE_REPAY_PROCESSING.equals(state)) {
			stateStr = "还款中";
		}else if (PROGRESS_REPAY_SUCCESS.equals(state)
				||PROGRESS_REPAY_REMISSION_SUCCESS.equals(state)){
			stateStr="已还款";
		}else if (PROGRESS_REPAY_OVERDUE.equals(state)){
			stateStr="已逾期";
		}else if (PROGRESS_BILL_BAD.equals(state)){
			stateStr="坏账";
		}else{
			stateStr=state;
		}
		return stateStr;
	}


	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the createTimeStr
	 */
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	/**
	 * @param createTimeStr the createTimeStr to set
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}



	/**
	 * @return the str
	 */
	public String getStr() {
		return str;
	}


	/**
	 * @param str the str to set
	 */
	public void setStr(String str) {
		this.str = alter(str);
	}

	
}
