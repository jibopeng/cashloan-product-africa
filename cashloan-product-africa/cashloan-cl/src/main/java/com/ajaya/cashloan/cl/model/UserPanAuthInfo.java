package com.ajaya.cashloan.cl.model;


/**
 * @author ryan
 * @version 1.0 2020/11/21
 */
public class UserPanAuthInfo {

    /**
     * 交易状态
     */
    private Integer transactionStatus;

    /**
     * 1- 成功
     */
    private Integer responseCode;

    /**
     * pan号码
     */
    private String panNumber;

    /**
     * pan校验结果状态
     */
    private String panStatus;

    /**
     * 第一个名字
     */
    private String firstName;

    /**
     * 中间名称
     */
    private String middleName;

    /**
     * 最后一个名字
     */
    private String lastName;

    /**
     * 持有者名称
     */
    private String panHolderTitle;

    /**
     * pan最后更新时间
     */
    private String panLastUpdated;

    /**
     * pan count
     */
    private Integer panCount;

    /**
     * pan succs count
     */
    private Integer panSuccessCount;

    /**
     * req time
     */
    private String requestTimestamp;

    /**
     * res time
     */
    private String responseTimestamp;

    /**
     * trade no
     */
    private String tradeNo;

    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * pan-lite认证接口返回姓名字段
     */
    private String name;

	public Integer getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getPanStatus() {
		return panStatus;
	}

	public void setPanStatus(String panStatus) {
		this.panStatus = panStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPanHolderTitle() {
		return panHolderTitle;
	}

	public void setPanHolderTitle(String panHolderTitle) {
		this.panHolderTitle = panHolderTitle;
	}

	public String getPanLastUpdated() {
		return panLastUpdated;
	}

	public void setPanLastUpdated(String panLastUpdated) {
		this.panLastUpdated = panLastUpdated;
	}

	public Integer getPanCount() {
		return panCount;
	}

	public void setPanCount(Integer panCount) {
		this.panCount = panCount;
	}

	public Integer getPanSuccessCount() {
		return panSuccessCount;
	}

	public void setPanSuccessCount(Integer panSuccessCount) {
		this.panSuccessCount = panSuccessCount;
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

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserPanAuthInfo [transactionStatus=" + transactionStatus + ", responseCode=" + responseCode
				+ ", panNumber=" + panNumber + ", panStatus=" + panStatus + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", panHolderTitle=" + panHolderTitle + ", panLastUpdated="
				+ panLastUpdated + ", panCount=" + panCount + ", panSuccessCount=" + panSuccessCount
				+ ", requestTimestamp=" + requestTimestamp + ", responseTimestamp=" + responseTimestamp + ", tradeNo="
				+ tradeNo + ", errorMessage=" + errorMessage + ", name=" + name + "]";
	}
}
