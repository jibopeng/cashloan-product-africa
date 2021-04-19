package com.ajaya.cashloan.cl.model;


/**
 * @author ryan
 * @version 1.0 2020/12/16
 */
public class PersonalNameCheck {

    /**
     * 一致性验证结果：0表示拒绝，1表示通过
     */
    private String pass;

    /**
     *
     */
    private String reasonMsg;

    /**
     * 0表示完全匹配，1表示姓名无效，2表示模糊匹配（2的详情见reason_info字段）
     */
    private String reasonCode;
    /**
     * 交易流水号
	 */
	private String tradeNo;

    /**
     * 导致拒绝的字段or不一致情况最严重的字段，完全匹配的情况下返回空字符串
     */
    private String differentNameType;

    /**
     * 相同单词个数
     */
    private String sameCnt;

    /**
     * 相似单词个数
     */
    private String similarCnt;

    /**
     * 不同单词个数
     */
    private String differentCnt;

    /**
     * 较少单词数姓名中单词缺失个数
     */
    private String missCnt;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getReasonMsg() {
		return reasonMsg;
	}

	public void setReasonMsg(String reasonMsg) {
		this.reasonMsg = reasonMsg;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getDifferentNameType() {
		return differentNameType;
	}

	public void setDifferentNameType(String differentNameType) {
		this.differentNameType = differentNameType;
	}

	public String getSameCnt() {
		return sameCnt;
	}

	public void setSameCnt(String sameCnt) {
		this.sameCnt = sameCnt;
	}

	public String getSimilarCnt() {
		return similarCnt;
	}

	public void setSimilarCnt(String similarCnt) {
		this.similarCnt = similarCnt;
	}

	public String getDifferentCnt() {
		return differentCnt;
	}

	public void setDifferentCnt(String differentCnt) {
		this.differentCnt = differentCnt;
	}

	public String getMissCnt() {
		return missCnt;
	}

	public void setMissCnt(String missCnt) {
		this.missCnt = missCnt;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
