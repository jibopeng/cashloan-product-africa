package com.ajaya.cashloan.cl.model;

public class StatisticsVerifyModel {

    /**
    * 注册时间
    */
    private String registerTime;

    /**
     * 个人信息r认证状态
     */
    private String personalInformationNum;
    
    /**
     * 个人信息认证完成比例
     */
    private String personalInformationNumPer;
    
    /**
     * 联系人认证
     */
    private String contatcInformationNum;
    
    /**
     * 联系人认证完成比例
     */
    private String contatcInformationNumPer;
    
    /**
     * 基本信息认证
     */
    private String basicInfomationNum;

    /**
     * 基本信息认证比例
     */
    private String basicInfomationNumPer;

    /**
     * 银行卡认证
     */
    private String bankInformationNum;
    
    /**
     * 银行卡认证比例
     */
    private String bankInformationNumPer;
    
    
    /**
    * 获取注册时间
    *
    * @return 注册时间
    */
    public String getRegisterTime(){
        return registerTime;
    }

    /**
    * 设置注册时间
    * 
    * @param registerTime 要设置的注册时间
    */
    public void setRegisterTime(String registerTime){
        this.registerTime = registerTime;
    }

	public String getPersonalInformationNum() {
		return personalInformationNum;
	}

	public void setPersonalInformationNum(String personalInformationNum) {
		this.personalInformationNum = personalInformationNum;
	}

	public String getPersonalInformationNumPer() {
		return personalInformationNumPer;
	}

	public void setPersonalInformationNumPer(String personalInformationNumPer) {
		this.personalInformationNumPer = personalInformationNumPer;
	}

	public String getContatcInformationNum() {
		return contatcInformationNum;
	}

	public void setContatcInformationNum(String contatcInformationNum) {
		this.contatcInformationNum = contatcInformationNum;
	}

	public String getContatcInformationNumPer() {
		return contatcInformationNumPer;
	}

	public void setContatcInformationNumPer(String contatcInformationNumPer) {
		this.contatcInformationNumPer = contatcInformationNumPer;
	}

	public String getBasicInfomationNum() {
		return basicInfomationNum;
	}

	public void setBasicInfomationNum(String basicInfomationNum) {
		this.basicInfomationNum = basicInfomationNum;
	}

	public String getBasicInfomationNumPer() {
		return basicInfomationNumPer;
	}

	public void setBasicInfomationNumPer(String basicInfomationNumPer) {
		this.basicInfomationNumPer = basicInfomationNumPer;
	}

	public String getBankInformationNum() {
		return bankInformationNum;
	}

	public void setBankInformationNum(String bankInformationNum) {
		this.bankInformationNum = bankInformationNum;
	}

	public String getBankInformationNumPer() {
		return bankInformationNumPer;
	}

	public void setBankInformationNumPer(String bankInformationNumPer) {
		this.bankInformationNumPer = bankInformationNumPer;
	}
}
