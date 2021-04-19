package com.ajaya.creditrank.cr.model;

import java.util.List;

import com.ajaya.creditrank.cr.domain.Info;

/** 
 * @author lyang
 * @version 1.0
 * @date 2017-1-9 下午9:05:58
 */
@SuppressWarnings("serial")
public class InfoModel extends Info{

	@SuppressWarnings("rawtypes")
	private List children;

	/**
	 * 获取children
	 * @return children
	 */
	@SuppressWarnings("rawtypes")
	public List getChildren() {
		return children;
	}

	/**
	 * 设置children
	 * @param children
	 */
	@SuppressWarnings("rawtypes")
	public void setChildren(List children) {
		this.children = children;
	}

}
