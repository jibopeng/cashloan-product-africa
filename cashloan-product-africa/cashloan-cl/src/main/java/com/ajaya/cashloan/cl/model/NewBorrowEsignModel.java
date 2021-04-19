package com.ajaya.cashloan.cl.model;
//新签章
/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-11-18 20:37
 **/
public class NewBorrowEsignModel {

    /**
     * 手机号
     */

    private String phone;

    /**
     * 访问的url
     */
    private String htmlRul;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHtmlRul() {
        return htmlRul;
    }

    public void setHtmlRul(String htmlRul) {
        this.htmlRul = htmlRul;
    }

	@Override
	public String toString() {
		return "NewBorrowEsignModel [phone=" + phone + ", htmlRul=" + htmlRul + "]";
	}


}
