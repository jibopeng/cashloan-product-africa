package com.ajaya.cashloan.core.domain;

import java.io.Serializable;

/**
 * 城市邮编配置表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-09-05 15:25:01
 */
 public class SysLocationPincodeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private String location;

    /**
    * 
    */
    private String pincode;

    /**
    * 
    */
    private String state;

    /**
    * 
    */
    private String district;


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
    public String getLocation(){
        return location;
    }

    /**
    * 设置
    * 
    * @param location 要设置的
    */
    public void setLocation(String location){
        this.location = location;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getPincode(){
        return pincode;
    }

    /**
    * 设置
    * 
    * @param pincode 要设置的
    */
    public void setPincode(String pincode){
        this.pincode = pincode;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getState(){
        return state;
    }

    /**
    * 设置
    * 
    * @param state 要设置的
    */
    public void setState(String state){
        this.state = state;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getDistrict(){
        return district;
    }

    /**
    * 设置
    * 
    * @param district 要设置的
    */
    public void setDistrict(String district){
        this.district = district;
    }

}