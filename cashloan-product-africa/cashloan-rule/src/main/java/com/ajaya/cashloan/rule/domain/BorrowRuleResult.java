package com.ajaya.cashloan.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 规则匹配结果实体
 * 
 * @author ctt
 * @version 1.0.0
 * @date 2016-12-21 15:04:28

 */
 public class BorrowRuleResult implements Serializable {

	    private static final long serialVersionUID = 1L;
	    
	    /**
	     * 结果类型
	     * 10 不通过
	     * 20 需人工复审
	     * 30 通过
	     */
	    public static final String RESULT_TYPE_PASS = "30";
	    public static final String RESULT_TYPE_REVIEW= "20";
	    public static final String RESULT_TYPE_REFUSED = "10";
		
		public String alterType (String str){
			switch (str) {
			case "10":
				str = "FAIL";
				break;
			case "20":
				str = "MANUAL REVIEW";
				break;
			case "30":
				str = "PASS";
				break;
			default:
				break;
			}
			return str;
		}
	    
	    
	    /**
	    * 主键Id
	    */
	    private Long id;

	    /**
	    * 借款申请表ID
	    */
	    private Long borrowId;
	    
	    /**
	     * 借款规则详细配置表Id
	     */
	    private Long ruleEngineConfigId;

	    /**
	    * 规则表ID
	    */
	    private Long ruleId;

	    /**
	    * 表英文名称
	    */
	    private String tbNid;

	    /**
	    * 表中文名称
	    */
	    private String tbName;

	    /**
	    * 列名英文名称
	    */
	    private String colNid;

	    /**
	    * 列名中文名称
	    */
	    private String colName;

	    /**
	    * 匹配值
	    */
	    private String value;
	    
	    /**
	     * 匹配当前值
	     */
	    private String matching;

	    /**
	    * 匹配规则表达式
	    */
	    private String rule;

	    /**
	    * 规则匹配结果
	    */
	    private String result;
	    
	    /**
	     * 结果类型  10 不通过 20 需要人工审核  30 通过
	     */
	    private String resultType;

	    /**
	    * 扩展字段
	    */
	    private String reqExt;

	    /**
	    * 添加时间
	    */
	    private Date addTime;
	    
	    /**
	     * 结果得分
	     */
	    private Integer score;


	    public BorrowRuleResult() {
			super();
		}
	    
	    

		public BorrowRuleResult(Long ruleEngineConfigId, Long borrowId, Long ruleId, String tbNid,
				String tbName, String colNid, String colName, String rule, Date addTime) {
			super();
			this.ruleEngineConfigId = ruleEngineConfigId;
			this.borrowId = borrowId;
			this.ruleId = ruleId;
			this.tbNid = tbNid;
			this.tbName = tbName;
			this.colNid = colNid;
			this.colName = colName;
			this.rule = rule;
			this.addTime = addTime;
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
	    * 获取借款申请表ID
	    *
	    * @return 借款申请表ID
	    */
	    public Long getBorrowId(){
	    return borrowId;
	    }

	    /**
	    * 设置借款申请表ID
	    * 
	    * @param borrowId 要设置的借款申请表ID
	    */
	    public void setBorrowId(Long borrowId){
	    this.borrowId = borrowId;
	    }


	    /**
	    * 获取规则表ID
	    *
	    * @return 规则表ID
	    */
	    public Long getRuleId(){
	    return ruleId;
	    }

	    /**
	    * 设置规则表ID
	    * 
	    * @param ruleId 要设置的规则表ID
	    */
	    public void setRuleId(Long ruleId){
	    this.ruleId = ruleId;
	    }


	    /**
	    * 获取表英文名称
	    *
	    * @return 表英文名称
	    */
	    public String getTbNid(){
	    return tbNid;
	    }

	    /**
	    * 设置表英文名称
	    * 
	    * @param tbNid 要设置的表英文名称
	    */
	    public void setTbNid(String tbNid){
	    this.tbNid = tbNid;
	    }

	    /**
	    * 获取表中文名称
	    *
	    * @return 表中文名称
	    */
	    public String getTbName(){
	    return tbName;
	    }

	    /**
	    * 设置表中文名称
	    * 
	    * @param tbName 要设置的表中文名称
	    */
	    public void setTbName(String tbName){
	    this.tbName = tbName;
	    }

	    /**
	    * 获取列名英文名称
	    *
	    * @return 列名英文名称
	    */
	    public String getColNid(){
	    return colNid;
	    }

	    /**
	    * 设置列名英文名称
	    * 
	    * @param colNid 要设置的列名英文名称
	    */
	    public void setColNid(String colNid){
	    this.colNid = colNid;
	    }

	    /**
	    * 获取列名中文名称
	    *
	    * @return 列名中文名称
	    */
	    public String getColName(){
	    return colName;
	    }

	    /**
	    * 设置列名中文名称
	    * 
	    * @param colName 要设置的列名中文名称
	    */
	    public void setColName(String colName){
	    this.colName = colName;
	    }

	    /**
	    * 获取匹配值
	    *
	    * @return 匹配值
	    */
	    public String getValue(){
	    return value;
	    }

	    /**
	    * 设置匹配值
	    * 
	    * @param value 要设置的匹配值
	    */
	    public void setValue(String value){
	    this.value = value;
	    }

	    /**
	    * 获取匹配规则表达式
	    *
	    * @return 匹配规则表达式
	    */
	    public String getRule(){
	    return rule;
	    }

	    /**
	    * 设置匹配规则表达式
	    * 
	    * @param rule 要设置的匹配规则表达式
	    */
	    public void setRule(String rule){
	    this.rule = rule;
	    }

	    /**
	    * 获取规则匹配结果
	    *
	    * @return 规则匹配结果
	    */
	    public String getResult(){
	    return result;
	    }

	    /**
	    * 设置规则匹配结果
	    * 
	    * @param result 要设置的规则匹配结果
	    */
	    public void setResult(String result){
	    this.result = result;
	    }

	    /**
	    * 获取扩展字段
	    *
	    * @return 扩展字段
	    */
	    public String getReqExt(){
	    return reqExt;
	    }

	    /**
	    * 设置扩展字段
	    * 
	    * @param reqExt 要设置的扩展字段
	    */
	    public void setReqExt(String reqExt){
	    this.reqExt = reqExt;
	    }

	    /**
	    * 获取添加时间
	    *
	    * @return 添加时间
	    */
	    public Date getAddTime(){
	    return addTime;
	    }

	    /**
	    * 设置添加时间
	    * 
	    * @param addTime 要设置的添加时间
	    */
	    public void setAddTime(Date addTime){
	    this.addTime = addTime;
	    }

		/** 
		 * 获取结果类型
		 * @return resultType
		 */
		public String getResultType() {
			return resultType;
		}
		
		/** 
		 * 设置结果类型
		 * @param resultType
		 */
		public void setResultType(String resultType) {
			this.resultType = resultType;
		}

		/**
		 * @return the matching
		 */
		public String getMatching() {
			if("未知".equals(matching)){
				return "Unknown";
			}
			return matching;
		}

		/**
		 * @param matching the matching to set
		 */
		public void setMatching(String matching) {
			this.matching = matching;
		}

		/** 
		 * 获取结果得分
		 * @return score
		 */
		public Integer getScore() {
			return score;
		}

		/** 
		 * 设置结果得分
		 * @param score
		 */
		public void setScore(Integer score) {
			this.score = score;
		}



		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}



		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BorrowRuleResult other = (BorrowRuleResult) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}



		public Long getRuleEngineConfigId() {
			return ruleEngineConfigId;
		}



		public void setRuleEngineConfigId(Long ruleEngineConfigId) {
			this.ruleEngineConfigId = ruleEngineConfigId;
		}

	    

}
