package com.ajaya.cashloan.cl.model;

/**
 * @author ryan
 * @version 1.0 2020/11/21
 */
public class UserBankAuthInfo {

    /**
     * 交易状态 0交易失败 1交易成功 2验证失败
     */
    private Integer transactionStatus;

    /**
     * 验证状态
     */
    private String status;

    /**
     *
     */
    private String bankRef;

    /**
     * 持卡人姓名
     */
    private String beneName;

    /**
     * 验证时的备注信息
     */
    private String remark;

    /**
     * status code
     */
    private Integer statusCode;

    /**
     * trade no
     */
    private String tradeNo;

    /**
     * res msg
     */
    private String responseMessage;

    /**
     * res code
     */
    private String responseCode;

    /**
     * req time
     */
    private String requestTimestamp;

    /**
     * re time
     */
    private String responseTimestamp;

    /**
     * 错误信息
     */
    private String message;

	public Integer getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankRef() {
		return bankRef;
	}

	public void setBankRef(String bankRef) {
		this.bankRef = bankRef;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getRequestTimestamp() {
		return requestTimestamp;
	}

	public void setRequestTimestamp(String requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}

	public String getResponseTimestamp() {
		return responseTimestamp;
	}

	public void setResponseTimestamp(String responseTimestamp) {
		this.responseTimestamp = responseTimestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
