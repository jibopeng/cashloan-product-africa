package com.ajaya.cashloan.cl.model;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-11-04 10:57
 **/
public class DiversionModel {
    /**
     * 开关
     */
   private  String  openSwitch;
    /**
     * 名称
     */
   private String  title;
    /**
     * 包名或者链接名称
     */
   private  String  value;

    public String getOpenSwitch() {
        return openSwitch;
    }

    public void setOpenSwitch(String openSwitch) {
        this.openSwitch = openSwitch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
