package com.ajaya.cashloan.core.model;

import com.ajaya.cashloan.core.domain.Borrow;

/**
 * 借款进度状态model
 *
 * @author jdd
 * @version 1.0.0
 * @date 2016年1月12日 下午2:35:42
 */
public class BorrowModel extends Borrow {

    private static final long serialVersionUID = 1L;


    /**
     * 申请成功待审核
     */
    public static final String STATE_PRE = "10";
    /**
     * 自动审核通过
     */
    public static final String STATE_AUTO_PASS = "20";
    /**
     * 自动审核不通过
     */
    public static final String STATE_AUTO_REFUSED = "21";
    /**
     * 自动审核未决待人工复审
     */
    public static final String STATE_NEED_REVIEW = "22";
    /**
     * 人工复审通过
     */
    public static final String STATE_PASS = "26";
    /**
     * 人工复审不通过
     */
    public static final String STATE_REFUSED = "27";
    /**
     * 打款中
     */
    public static final String DAKUAN_ING = "29";

    /**
     * 放款成功
     */
    public static final String STATE_REPAY = "30";
    /**
     * 待放款审核
     */
    public static final String WAIT_AUDIT_LOAN = "301";
    /**
     * 放款审核通过
     */
    public static final String AUDIT_LOAN_PASS = "302";
    /**
     * 放款审核不通过
     */
    public static final String AUDIT_LOAN_FAIL = "303";
    /**
     * 放款失败
     */
    public static final String STATE_REPAY_FAIL = "31";

    /**
     * 还款成功
     */
    public static final String STATE_FINISH = "40";
    /**
     * 还款成功-金额减免
     */
    public static final String STATE_REMISSION_FINISH = "41";
    /**
     * 还款处理中
     */
    public static final String STATE_REPAY_PROCESSING = "43";
    /**
     * 逾期
     */
    public static final String STATE_DELAY = "50";
    /**
     * 坏账
     */
    public static final String STATE_BAD = "90";

    /**
     * 需要主动还款
     */
    public static final String NEED_PAY_TRUE = "yes";
    /**
     * 不需要主动还款
     */
    public static final String NEED_PAY_FALSE = "no";

    /**
     * 取消贷款
     */
    public static final String STATE_CANCEL = "70";

    /**
     * 状态中文描述
     * 10申请成功待审核 20自动审核通过 21自动审核不通过
     * 22自动审核未决待人工复审 26人工复审通过 27人工复审不通过
     * 30放款成功 31放款失败 40还款成功  41还款成功-金额减免  50逾期 90坏账
     */
    private String stateStr;

    /**
     * 进度说明
     */
    private String remark;

    /**
     * 获取状态中文描述
     *
     * @return stateStr
     */
    public String getStateStr() {
        this.stateStr = BorrowModel.apiConvertBorrowState(this.getState());
        return stateStr;
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
     * 获取进度说明
     *
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置进度说明
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 响应给管理后台的借款/借款进度状态中文描述转换
     *
     * @return stateStr
     */
    public static String manageConvertBorrowState(String state) {
        String stateStr = state;
        if (BorrowModel.STATE_PRE.equals(state)) {
            stateStr = "OPTION.WAITING_AUDIT";
        } else if (BorrowModel.STATE_AUTO_PASS.equals(state)) {
            stateStr = "OPTION.APPLICATION_APPROVED";
        } else if (BorrowModel.STATE_AUTO_REFUSED.equals(state)) {
            stateStr = "OPTION.AUDIT_FAILED";
        } else if (BorrowModel.STATE_NEED_REVIEW.equals(state)) {//Application under review
            stateStr = "OPTION.APPLICATION_UNDER_REVIEW";//APPLICATION_UNDER_REVIEW
        } else if (BorrowModel.STATE_PASS.equals(state)) {
            stateStr = "OPTION.APPLICATION_APPROVED";
        } else if (BorrowModel.STATE_REFUSED.equals(state)) {
            stateStr = "OPTION.AUDIT_FAILED";
        } else if (BorrowModel.STATE_REPAY.equals(state)) {
            stateStr = "OPTION.LOAN_SUCCESS";//Loan success
        } else if (BorrowModel.STATE_REPAY_FAIL.equals(state)) {
            stateStr = "OPTION.LOAN_FAILURE";//Loan failure
        } else if (BorrowModel.STATE_FINISH.equals(state)) {//Successful repayment
            stateStr = "OPTION.SUCCESSFUL_REPAYMENT";//SUCCESSFUL_REPAYMENT
        } else if (BorrowModel.STATE_REMISSION_FINISH.equals(state)) {
            stateStr = "OPTION.SUCCESSFUL_REPAYMENT";
        } else if (STATE_REPAY_PROCESSING.equals(state)) {//Repayment Processing
            stateStr = ("OPTION.REPAYMENT_PROCESSING");//REPAYMENT_PROCESSING
        } else if (BorrowModel.STATE_DELAY.equals(state)) {//Overdue
            stateStr = "OPTION.OVERDUE";
        } else if (BorrowModel.STATE_BAD.equals(state)) {
            stateStr = "OPTION.OVERDUE";
        } else if (WAIT_AUDIT_LOAN.equals(state)) {//Pending loan review
            stateStr = ("OPTION.PENDING_LOAN_REVIEW");//PENDING_LOAN_REVIEW
        } else if (AUDIT_LOAN_PASS.equals(state)) {//Loan approval
            stateStr = ("OPTION.LOAN_APPROVAL");//LOAN_APPROVAL
        } else if (AUDIT_LOAN_FAIL.equals(state)) {//Loan audit failed
            stateStr = ("OPTION.LOAN_AUDIT_FAILED");
        } else if (DAKUAN_ING.equals(state)) {//Loan on way
            stateStr = ("OPTION.LOAN_ON_WAY");
        } else if (STATE_CANCEL.equals(state)) {//Cancellation of loan
            stateStr = ("OPTION.CANCELLATION_OF_LOAN");//CANCELLATION_OF_LOAN
        }
        return stateStr;
    }
    
    public static String manageConvertBorrowRepayState(String state) {
    	String stateStr = state;
        if ("20".equals(state)) {
            stateStr = "OPTION.UNPAID";
        } else if ("10".equals(state)) {
            stateStr = "OPTION.REPAID";
        }
        return stateStr;
	}

    /**
     * 响应给app的借款状态中文描述转换
     *
     * @param state 状态码
     * @return 状态说明
     */
    public static String apiConvertBorrowState(String state) {
        String stateStr = state;
        if (BorrowModel.STATE_PRE.equals(state)) {
            stateStr = "Application under review";
        } else if (BorrowModel.STATE_AUTO_PASS.equals(state)) {
            stateStr = "Application approved";
        } else if (BorrowModel.STATE_AUTO_REFUSED.equals(state)) {
            stateStr = "Audit failed";
        } else if (BorrowModel.STATE_NEED_REVIEW.equals(state)) {
            stateStr = "Application under review";
        } else if (BorrowModel.STATE_PASS.equals(state)) {
            stateStr = "Application approved";
        } else if (BorrowModel.STATE_REFUSED.equals(state)) {
            stateStr = "Rejected";
        } else if (BorrowModel.STATE_REPAY.equals(state)) {
            stateStr = "Pending repayment";
        } else if (BorrowModel.STATE_REPAY_FAIL.equals(state)) {
            stateStr = "Loan failure";
        } else if (BorrowModel.STATE_FINISH.equals(state)) {
            stateStr = "Successful repayment";
        } else if (BorrowModel.STATE_REMISSION_FINISH.equals(state)) {
            stateStr = "Successful repayment";
        } else if (STATE_REPAY_PROCESSING.equals(state)) {
            stateStr = ("Payment processing");
        } else if (BorrowModel.STATE_DELAY.equals(state)) {
            stateStr = "Overdue";
        } else if (BorrowModel.STATE_BAD.equals(state)) {
            stateStr = "Overdue";
        } else if (WAIT_AUDIT_LOAN.equals(state)) {
            stateStr = ("Pending loan review");
        } else if (AUDIT_LOAN_PASS.equals(state)) {
            stateStr = ("Loan approval");
        } else if (AUDIT_LOAN_FAIL.equals(state)) {
            stateStr = ("Loan audit failed");
        } else if (DAKUAN_ING.equals(state)) {
            stateStr = ("Loan on way");
        } else if (STATE_CANCEL.equals(state)) {
            stateStr = ("Cancellation of loan");
        }
        return stateStr;
    }

    /**
     * 借款状态中文描述转换
     *
     * @param state
     * @return
     */
    public static String convertBorrowRemark(String state) {
        String remarkStr = " - ";
        if (BorrowModel.STATE_PRE.equals(state)) {
            //系统审核中,请耐心等待
            remarkStr = "Please wait patiently during system audit";
        } else if (BorrowModel.STATE_AUTO_PASS.equals(state)) {
            //恭喜通过风控审核
            remarkStr = "Congratulations on your approval";
        } else if (BorrowModel.STATE_AUTO_REFUSED.equals(state)) {
            //很遗憾,您未通过审核
            remarkStr = "Unfortunately, you have not passed the audit";
        } else if (BorrowModel.STATE_NEED_REVIEW.equals(state)) {
            //系统复审中,请耐心等待
            remarkStr = "Please wait patiently during system audit";
        } else if (BorrowModel.STATE_PASS.equals(state)) {
            ////恭喜通过风控审核
            remarkStr = "Congratulations on your approval";
        } else if (BorrowModel.STATE_REFUSED.equals(state)) {
            //很遗憾,您未通过复审
            remarkStr = "Unfortunately, you have not passed the audit";
        } else if (BorrowModel.STATE_REPAY.equals(state)) {
            //已打款,请查看您的提现银行卡
            remarkStr = "Payments have been made. Please check your cash card";
        } else if (BorrowModel.STATE_REPAY_FAIL.equals(state)) {
            //放款失败
            remarkStr = "Loan failure";
        } else if (BorrowModel.STATE_FINISH.equals(state)) {
            //还款成功
            remarkStr = "Repayment is successful";
        } else if (BorrowModel.STATE_REMISSION_FINISH.equals(state)) {
            //借款人无法支付全部借款金额,减免还款成功
            remarkStr = "Borrowers can't pay the full amount of borrowed money, and the repayment is successful";
        } else if (STATE_REPAY_PROCESSING.equals(state)) {
            //还款处理中
            remarkStr = ("Payment processing");
        } else if (BorrowModel.STATE_DELAY.equals(state)) {
            //您的借款已逾期
            remarkStr = "Your loan is overdue";
        } else if (BorrowModel.STATE_BAD.equals(state)) {
            //经长时间催收,没有结果
            remarkStr = "After a long time of collection, no result";
        } else if (BorrowModel.WAIT_AUDIT_LOAN.equals(state)) {
            //放款审核中,请等待
            remarkStr = "In the process of loan auditing, please wait";
        } else if (BorrowModel.AUDIT_LOAN_PASS.equals(state)) {
            //恭喜您,放款审核通过
            remarkStr = "Congratulations. The loan has been approved.";
        } else if (BorrowModel.AUDIT_LOAN_FAIL.equals(state)) {
            //很遗憾,审核放款不通过
            remarkStr = "Regrettably, the loan was not approved";
        }
        return remarkStr;
    }
}