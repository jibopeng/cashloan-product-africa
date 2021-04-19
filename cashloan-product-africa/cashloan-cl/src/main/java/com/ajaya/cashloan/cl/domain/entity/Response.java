package com.ajaya.cashloan.cl.domain.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能说明：响应实体报告实体
 *
 * @author yanzhiqiang
 * @since 2020-04-08 16:34
 **/
@XStreamAlias("Response")
public class Response {


    @XStreamAlias("Say")
    private String Say;

    public String getSay() {
        return Say;
    }

    public void setSay(String say) {
        Say = say;
    }
}
