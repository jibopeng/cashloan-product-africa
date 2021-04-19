package com.ajaya.cashloan.cl.model;

/**
 * 
 * 催收回款列表展示类
 *
 */
public class ColStaModel {

	//用户姓名
	private String userName;
	
	//催收订单生成时间（分案时间）
	private String createTime;
	
	//催收订单催回时间（催收成功时间）
	private String successTime;
	
	//总订单
	private Integer allOrder;
	
	//今分今回订单
	private Integer todayOrder;
	
	//催回率
	private String ratioRecall;
	
	//催收成功订单数量
	private Integer successOrder;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public Integer getAllOrder() {
		return allOrder;
	}

	public void setAllOrder(Integer allOrder) {
		this.allOrder = allOrder;
	}

	public Integer getTodayOrder() {
		return todayOrder;
	}

	public void setTodayOrder(Integer todayOrder) {
		this.todayOrder = todayOrder;
	}

	public String getRatioRecall() {
		return ratioRecall;
	}

	public void setRatioRecall(String ratioRecall) {
		this.ratioRecall = ratioRecall;
	}

	public Integer getSuccessOrder() {
		return successOrder;
	}

	public void setSuccessOrder(Integer successOrder) {
		this.successOrder = successOrder;
	}
	
}
