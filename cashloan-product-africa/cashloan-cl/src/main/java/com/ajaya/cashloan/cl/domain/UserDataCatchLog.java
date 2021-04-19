package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 原始数据抓取记录表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-11-01 18:01:26
 */
 public class UserDataCatchLog implements Serializable {

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
    private Long borrowId;

    /**
    * 1：表示抓取完毕，0：表示未抓取完毕
    */
    private String app;

    /**
    * 
    */
    private Date appOverTime;

    /**
    * 
    */
    private String contact;

    /**
    * 
    */
    private Date contactOverTime;

    /**
    * 
    */
    private String msg;

    /**
    * 
    */
    private Date msgOverTime;

    /**
    * 
    */
    private String camera;

    /**
    * 
    */
    private Date cameraOverTime;

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
    public Long getBorrowId(){
        return borrowId;
    }

    /**
    * 设置
    * 
    * @param borrowId 要设置的
    */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取1：表示抓取完毕，0：表示未抓取完毕
    *
    * @return 1：表示抓取完毕，0：表示未抓取完毕
    */
    public String getApp(){
        return app;
    }

    /**
    * 设置1：表示抓取完毕，0：表示未抓取完毕
    * 
    * @param app 要设置的1：表示抓取完毕，0：表示未抓取完毕
    */
    public void setApp(String app){
        this.app = app;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getAppOverTime(){
        return appOverTime;
    }

    /**
    * 设置
    * 
    * @param appOverTime 要设置的
    */
    public void setAppOverTime(Date appOverTime){
        this.appOverTime = appOverTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getContact(){
        return contact;
    }

    /**
    * 设置
    * 
    * @param contact 要设置的
    */
    public void setContact(String contact){
        this.contact = contact;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getContactOverTime(){
        return contactOverTime;
    }

    /**
    * 设置
    * 
    * @param contactOverTime 要设置的
    */
    public void setContactOverTime(Date contactOverTime){
        this.contactOverTime = contactOverTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getMsg(){
        return msg;
    }

    /**
    * 设置
    * 
    * @param msg 要设置的
    */
    public void setMsg(String msg){
        this.msg = msg;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getMsgOverTime(){
        return msgOverTime;
    }

    /**
    * 设置
    * 
    * @param msgOverTime 要设置的
    */
    public void setMsgOverTime(Date msgOverTime){
        this.msgOverTime = msgOverTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getCamera(){
        return camera;
    }

    /**
    * 设置
    * 
    * @param camera 要设置的
    */
    public void setCamera(String camera){
        this.camera = camera;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCameraOverTime(){
        return cameraOverTime;
    }

    /**
    * 设置
    * 
    * @param cameraOverTime 要设置的
    */
    public void setCameraOverTime(Date cameraOverTime){
        this.cameraOverTime = cameraOverTime;
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