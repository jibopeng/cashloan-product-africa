package com.ajaya.cashloan.cl.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 功能说明：短信特征model
 *
 * @author yanzhiqiang
 * @since 2020-11-22 16:31
 **/
public class UserMessagesModel {
    /**
     * 短信内容
     */
    private String msg;

    /**
     * 短信发送时间
     */
    private String msg_time;
    /**
     * 采集短信时间
     */

    @JSONField(serialize=false)
    private String create_time;

    /**
     * 短信发送名称
     */

    private String send_name;

    public String getMsg() {
        return msg;
    }

    /**
     *对短信内容进行处理
     * @param msg 短信内容
     */
    public void setMsg(String msg) {
        this.msg = msg.replace("\n","").replace("\r","").replace("\t","");


    }

    public String getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    @Override
    public String toString() {
        return "UserMessagesModel{" +
                "msg='" + msg + '\'' +
                ", msg_time='" + msg_time + '\'' +
                ", create_time='" + create_time + '\'' +
                ", send_name='" + send_name + '\'' +
                '}';
    }
}
