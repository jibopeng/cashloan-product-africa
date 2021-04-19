package com.ajaya.cashloan.rule.domain;

import java.io.Serializable;

/**
 * 规则评分设置管理实体
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-01-03 17:28:16

 */
 public class RuleEngineInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 所属规则引擎id
    */
    private Long ruleEnginId;

    /**
    * 结果描述
    */
    private String result;

    /**
    * 备份字段
    */
    private String reqExt;
    /**
     * 表达式
     */
    private String formula;
    /**
     * 分值区间
     */
     private String integral;
     
    /**
    * 获取主键Id
    *
    * @return id
    */
    public Long getId(){
    return id;
    }

    /**
    * 设置主键Id
    * 
    * @param 要设置的主键Id
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 获取所属规则引擎id
    *
    * @return 所属规则引擎id
    */
    public Long getRuleEnginId(){
    return ruleEnginId;
    }

    /**
    * 设置所属规则引擎id
    * 
    * @param ruleEnginId 要设置的所属规则引擎id
    */
    public void setRuleEnginId(Long ruleEnginId){
    this.ruleEnginId = ruleEnginId;
    }

    /**
    * 获取结果描述
    *
    * @return 结果描述
    */
    public String getResult(){
    return result;
    }

    /**
    * 设置结果描述
    * 
    * @param result 要设置的结果描述
    */
    public void setResult(String result){
    this.result = result;
    }

    /**
    * 获取备份字段
    *
    * @return 备份字段
    */
    public String getReqExt(){
    return reqExt;
    }

    /**
    * 设置备份字段
    * 
    * @param reqExt 要设置的备份字段
    */
    public void setReqExt(String reqExt){
    this.reqExt = reqExt;
    }

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	/** 
	 * 获取分值区间
	 * @return integral
	 */
	public String getIntegral() {
		return integral;
	}

	/** 
	 * 设置分值区间
	 * @param integral
	 */
	public void setIntegral(String integral) {
		this.integral = integral;
	}


}