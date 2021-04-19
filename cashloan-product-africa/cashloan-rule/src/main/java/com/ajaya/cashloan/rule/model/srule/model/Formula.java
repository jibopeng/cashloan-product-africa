package com.ajaya.cashloan.rule.model.srule.model;

/**
 * 基础运算符及匹配表达式
 * @author ctt
 * @version 1.0.0
 * @date 2017年1月4日 上午11:15:23

 */
public enum Formula {

	//大于，例如：20>3，仅允许使用在数值类型的表达式中
	greater(">", ">"), 
	//小于，例如 3<10，仅允许使用在数值类型的表达式中
	less("<", "<"),
	//等于，判断两个值是否相同，可以匹配文字和数值类型
	equal("=", "="), 
	//不等于，判断两个值不相同，可以匹配文字和数值类型
	not_equal("!=", "!="),
	//大于等于，例如：3>=3，仅允许使用在数值类型的表达式中
	greater_equal(">=",">="),
	//小于等于，例如 3<=3， 仅允许使用在数值类型的表达式中
	less_equal("<=","<="),
	
	
	//大于等于并且小于等于，例如  1<=3<=5， 仅允许使用在数值类型的表达式中
	greater_equal_and_less_equal("<=and<=","<=and<="), 
	//大于等于并且小于，例如  1<=3<5， 仅允许使用在数值类型的表达式中
	greater_equal_and_less("<=and<","<=and<"),
	//大于并且小于等于，例如  1<3<=5， 仅允许使用在数值类型的表达式中
	greater_and_less_equal("<and<=","<and<="),
	//大于并且小于，例如  1<3<5， 仅允许使用在数值类型的表达式中
	greater_and_less("<and<","<and<"),
	
	
	//包含，允许使用在字符类型，如果填写数字，将被当作字符类型匹配
	include("include","包含"),
	//不包含，允许使用在字符类型的表达式，如果填写数字，将被当作字符类型匹配
	exclude("exclude","不包含"),
	
	startWith("startWith","前X位包含"),
	endWith("endWith","后X位包含");
//	and("and",14),
//	or("or",14);
	
    // 成员变量  
    private String formula;  
    private String desc;  
    // 构造方法  
    private Formula(String formula, String desc) {  
        this.formula = formula;  
        this.desc = desc;  
    }  
    
	public static String getDesc(String formula) {
		if (startWith.formula.equals(formula)) {
			return startWith.desc;
			
		}else if (endWith.formula.equals(formula)) {
			return endWith.desc;
			
		}else if(include.formula.equals(formula)){
			return include.desc;
			
		}else if(exclude.formula.equals(formula)){
			return exclude.desc;
			
		}else{
			return formula;
		}
	}

	/** 
	 * 获取formula
	 * @return formula
	 */
	public String getFormula() {
		return formula;
	}

	/** 
	 * 设置formula
	 * @param formula
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

	/** 
	 * 获取desc
	 * @return desc
	 */
	public String getDesc() {
		return desc;
	}

	/** 
	 * 设置desc
	 * @param desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
    
	
}
