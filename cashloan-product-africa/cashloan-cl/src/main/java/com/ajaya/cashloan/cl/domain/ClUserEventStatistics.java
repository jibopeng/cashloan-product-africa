package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户事件统计表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-07-01 11:40:32
 */
 public class ClUserEventStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private Long userId;

    /**
    * 
    */
    private String imei;

    /**
    * 
    */
    private String pageId;

    /**
    * 
    */
    private String pageName;

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
    public Long getUserId(){
        return userId;
    }

    /**
    * 设置
    * 
    * @param userId 要设置的
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getImei(){
        return imei;
    }

    /**
    * 设置
    * 
    * @param imei 要设置的
    */
    public void setImei(String imei){
        this.imei = imei;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getPageId(){
        return pageId;
    }

    /**
    * 设置
    * 
    * @param pageId 要设置的
    */
    public void setPageId(String pageId){
        this.pageId = pageId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getPageName(){
        return pageName;
    }

    /**
    * 设置
    * 
    * @param pageName 要设置的
    */
    public void setPageName(String pageName){
        this.pageName = pageName;
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