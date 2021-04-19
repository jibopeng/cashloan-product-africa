package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统人员操作记录表实体
 * 
 */
 public class SysUserOperationRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 系统操作人员ID
    */
    private Long sysUserId;

    /**
    * 
    */
    private String sysUserName;

    /**
    * 借款ID
    */
    private Long borrowId;

    /**
    * 系统操作人员操作描述
    */
    private String sysUserRemark;

    /**
    * 系统操作人员提交结果
    */
    private String sysUserResult;

    /**
    * 系统人员操作来源
    */
    private String sysUserOperationSource;

    /**
    * 预留字段1
    */
    private String sysUserPrepare1;

    /**
    * 预留字段2
    */
    private String sysUserPrepare2;

    
    /**
     * 创建时间
     */
    private Date createTime;

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
    * 获取系统操作人员ID
    *
    * @return 系统操作人员ID
    */
    public Long getSysUserId(){
        return sysUserId;
    }

    /**
    * 设置系统操作人员ID
    * 
    * @param sysUserId 要设置的系统操作人员ID
    */
    public void setSysUserId(Long sysUserId){
        this.sysUserId = sysUserId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getSysUserName(){
        return sysUserName;
    }

    /**
    * 设置
    * 
    * @param sysUserName 要设置的
    */
    public void setSysUserName(String sysUserName){
        this.sysUserName = sysUserName;
    }

    /**
    * 获取借款ID
    *
    * @return 借款ID
    */
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置借款ID
    * 
    * @param borrowId 要设置的借款ID
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取系统操作人员操作描述
    *
    * @return 系统操作人员操作描述
    */
    public String getSysUserRemark(){
        return sysUserRemark;
    }

    /**
    * 设置系统操作人员操作描述
    * 
    * @param sysUserRemark 要设置的系统操作人员操作描述
    */
    public void setSysUserRemark(String sysUserRemark){
        this.sysUserRemark = sysUserRemark;
    }

    /**
    * 获取系统操作人员提交结果
    *
    * @return 系统操作人员提交结果
    */
    public String getSysUserResult(){
        return sysUserResult;
    }

    /**
    * 设置系统操作人员提交结果
    * 
    * @param sysUserResult 要设置的系统操作人员提交结果
    */
    public void setSysUserResult(String sysUserResult){
        this.sysUserResult = sysUserResult;
    }

    /**
    * 获取系统人员操作来源
    *
    * @return 系统人员操作来源
    */
    public String getSysUserOperationSource(){
        return sysUserOperationSource;
    }

    /**
    * 设置系统人员操作来源
    * 
    * @param sysUserOperationSource 要设置的系统人员操作来源
    */
    public void setSysUserOperationSource(String sysUserOperationSource){
        this.sysUserOperationSource = sysUserOperationSource;
    }

    /**
    * 获取预留字段1
    *
    * @return 预留字段1
    */
    public String getSysUserPrepare1(){
        return sysUserPrepare1;
    }

    /**
    * 设置预留字段1
    * 
    * @param sysUserPrepare1 要设置的预留字段1
    */
    public void setSysUserPrepare1(String sysUserPrepare1){
        this.sysUserPrepare1 = sysUserPrepare1;
    }

    /**
    * 获取预留字段2
    *
    * @return 预留字段2
    */
    public String getSysUserPrepare2(){
        return sysUserPrepare2;
    }

    /**
    * 设置预留字段2
    * 
    * @param sysUserPrepare2 要设置的预留字段2
    */
    public void setSysUserPrepare2(String sysUserPrepare2){
        this.sysUserPrepare2 = sysUserPrepare2;
    }

}