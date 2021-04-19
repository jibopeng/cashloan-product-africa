package com.ajaya.cashloan.manage.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出统计表实体
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-11-11 23:44:56

 */
 public class SysExport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private Long sysUserId;

    /**
    * 
    */
    private Date createTime;


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
    * 获取
    *
    * @return 
    */
    public Long getSysUserId(){
        return sysUserId;
    }

    /**
    * 设置
    * 
    * @param sysUserId 要设置的
    */
    public void setSysUserId(Long sysUserId){
        this.sysUserId = sysUserId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置
    * 
    * @param createTime 要设置的
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

}