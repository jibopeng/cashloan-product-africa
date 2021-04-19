package com.ajaya.cashloan.rule.model;

import com.ajaya.cashloan.rule.domain.BorrowRuleResult;

public class ManageReviewModel extends BorrowRuleResult{

	private static final long serialVersionUID = 1L;
	
	private String ruleName;

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

}
