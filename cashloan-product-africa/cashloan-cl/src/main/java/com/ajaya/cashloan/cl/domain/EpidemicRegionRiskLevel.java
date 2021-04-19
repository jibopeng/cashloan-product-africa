package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;

/**
 * '城市疫情区域危险等级表实体
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-05-08 16:09:44
 */
 public class EpidemicRegionRiskLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 邦名称
    */
    private String state;

    /**
    * 城市名称
    */
    private String city;

    /**
    * 城市危险等级 1 红色等级 2 黄色等级  3 绿色等级
    */
    private short level;


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
    * 获取邦名称
    *
    * @return 邦名称
    */
    public String getState(){
        return state;
    }

    /**
    * 设置邦名称
    * 
    * @param state 要设置的邦名称
    */
    public void setState(String state){
        this.state = state;
    }

    /**
    * 获取城市名称
    *
    * @return 城市名称
    */
    public String getCity(){
        return city;
    }

    /**
    * 设置城市名称
    * 
    * @param city 要设置的城市名称
    */
    public void setCity(String city){
        this.city = city;
    }

    /**
    * 获取城市危险等级 1 红色等级 2 黄色等级  3 绿色等级
    *
    * @return 城市危险等级 1 红色等级 2 黄色等级  3 绿色等级
    */
    public short getLevel(){
        return level;
    }

    /**
    * 设置城市危险等级 1 红色等级 2 黄色等级  3 绿色等级
    * 
    * @param level 要设置的城市危险等级 1 红色等级 2 黄色等级  3 绿色等级
    */
    public void setLevel(short level){
        this.level = level;
    }

}