package com.ajaya.cashloan.rc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 风控数据统计-（简）借款统计实体
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-07-06 18:12:18

 */
 public class SimpleBorrowCount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	private Long id;

	/**
	 * 用户标识
	 */
	private Long userId;

	/**
	 * 借款人逾期30天以上已还借款数
	 */
	private Integer countOne;

	/**
	 * 创建时间
	 */
	private Date createTime;

//	/**
//	 * 借款人有逾期未还借款数
//	 */
//	private Integer countTwo;
//	
//	/**
//	 * 借款人有逾期已还借款数
//	 */
//	private Integer countThree;
//	
//	/**
//	 * 借款人正常还款数
//	 */
//	private Integer countFour;
	
	/**
	 * 获取主键Id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键Id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取用户标识
	 * 
	 * @return userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户标识
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取借款人逾期30天以上已还借款数
	 * 
	 * @return countOne
	 */
	public Integer getCountOne() {
		return countOne;
	}

	/**
	 * 设置借款人逾期30天以上已还借款数
	 * 
	 * @param countOne
	 */
	public void setCountOne(Integer countOne) {
		this.countOne = countOne;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

//	public Integer getCountTwo() {
//		return countTwo;
//	}
//
//	public void setCountTwo(Integer countTwo) {
//		this.countTwo = countTwo;
//	}
//
//	public Integer getCountThree() {
//		return countThree;
//	}
//
//	public void setCountThree(Integer countThree) {
//		this.countThree = countThree;
//	}
//
//	public Integer getCountFour() {
//		return countFour;
//	}
//
//	public void setCountFour(Integer countFour) {
//		this.countFour = countFour;
//	}

	
}