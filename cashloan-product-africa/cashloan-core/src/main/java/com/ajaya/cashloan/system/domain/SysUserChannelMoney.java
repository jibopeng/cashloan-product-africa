package com.ajaya.cashloan.system.domain;
/**
 * 刘晓亮 该类不和数据库类匹配，只是单独记录数据,避免map的去重
 * @author Administrator
 *
 */
public class SysUserChannelMoney {

	private Long id;
	private Long userId;
	private Long channelId;
	private Double money;
	
	
	
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public SysUserChannelMoney() {
		super();

	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public SysUserChannelMoney(Long id, Long userId, Double money) {
		super();
		this.id = id;
		this.userId = userId;
		this.money = money;
	}
	
	
	
}
