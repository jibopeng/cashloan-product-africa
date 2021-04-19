package com.ajaya.cashloan.core.model;
/** 
 * @author  itw_yanzq:
 * @date 创建时间：2017年11月27日 上午11:23:42
 * @version 1.0
 * @parameter 
 * @return  
 */
public class UserVoiceModel {

	//用户ID
    private  int userid;
	//借款用户姓名
	private  String realname;
	//通话手机号
	private  String phonenum;
	//通话时间（秒）
	private  String voicedate;
	//通话类型
	private  String voicetype;
	//通话时长
	private  int voiceduration;
	
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

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getVoicedate() {
		return voicedate;
	}

	public void setVoicedate(String voicedate) {
		this.voicedate = voicedate;
	}

	public String getVoicetype() {
		return voicetype;
	}

	public void setVoicetype(String voicetype) {
		this.voicetype = voicetype;
	}

	public int getVoiceduration() {
		return voiceduration;
	}

	public void setVoiceduration(int voiceduration) {
		this.voiceduration = voiceduration;
	}

	@Override
	public String toString() {
		return "UserVoiceModel [userid=" + userid + ", realname=" + realname + ", phonenum=" + phonenum + ", voicedate="
				+ voicedate + ", voicetype=" + voicetype + ", voiceduration=" + voiceduration + "]";
	}
	
}
