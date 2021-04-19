package com.ajaya.cashloan.core.model;
/** 
 * @author  itw_yanzq: 
 * @date 创建时间：2017年11月25日 下午2:37:58 
 * @version 1.0 
 * @parameter  
 * @return  */
public class UserConnactModel {
    //用户ID
	private int  userid;
	//借款用户姓名
	private String  realname;
	//联系人姓名
	private String  name;
	//手机号
	private String  phone;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "UserConnactModel [userid=" + userid + ", realname=" + realname + ", name=" + name + ", phone=" + phone
				+ "]";
	}
	
}
