package com.ajaya.cashloan.rule.domain;

import java.io.Serializable;

/**
 * 决策引擎执行记录实体
 * 
 * @author caitt
 * @version 1.0.0
 * @date 2017-06-23 15:47:53

 */
 public class BorrowScoreResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 借款Id
    */
    private Long borrowId;

    /**
    * 所属规则引擎id
    */
    private Long ruleEnginId;

    /**
    * 结果描述   评分结果模式下  10 不通过 20 需人工审核  30通过
    */
    private String resultType;

    /**
    * 表达式 
    */
    private String formula;

    /**
    * 分值范围
    */
    private String integral;

    /**
    * 规则对应的总得分
    */
    private Integer score;
    
    /**
     * 比对结果  Y 匹配 N不匹配
     */
    private String result;


    public BorrowScoreResult() {
		super();
	}

	public BorrowScoreResult(Long borrowId, Long ruleEnginId,
			String resultType, String formula, String integral, Integer score,
			String result) {
		super();
		this.borrowId = borrowId;
		this.ruleEnginId = ruleEnginId;
		this.resultType = resultType;
		this.formula = formula;
		this.integral = integral;
		this.score = score;
		this.result = result;
	}

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
    * 获取借款Id
    *
    * @return 借款Id
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置借款Id
    * 
    * @param borrowId 要设置的借款Id
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
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
	 * 获取结果描述评分结果模式下10不通过20需人工审核30通过
	 * @return resultType
	 */
	public String getResultType() {
		return resultType;
	}

	/** 
	 * 设置结果描述评分结果模式下10不通过20需人工审核30通过
	 * @param resultType
	 */
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
    * 获取表达式 
    *
    * @return 表达式 
    */
    public String getFormula(){
        return formula;
    }

    /**
    * 设置表达式 
    * 
    * @param formula 要设置的表达式 
    */
    public void setFormula(String formula){
        this.formula = formula;
    }


    /** 
	 * 获取分值范围
	 * @return integral
	 */
	public String getIntegral() {
		return integral;
	}

	/** 
	 * 设置分值范围
	 * @param integral
	 */
	public void setIntegral(String integral) {
		this.integral = integral;
	}

	/**
    * 获取规则对应的总得分
    *
    * @return 规则对应的总得分
    */
    public Integer getScore(){
        return score;
    }

    /**
    * 设置规则对应的总得分
    * 
    * @param score 要设置的规则对应的总得分
    */
    public void setScore(Integer score){
        this.score = score;
    }

	/** 
	 * 获取比对结果Y匹配N不匹配
	 * @return result
	 */
	public String getResult() {
		return result;
	}

	/** 
	 * 设置比对结果Y匹配N不匹配
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}

}