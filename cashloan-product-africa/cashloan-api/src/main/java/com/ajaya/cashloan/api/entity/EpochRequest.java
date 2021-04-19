package com.ajaya.cashloan.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ryan
 * @version 1.0 2020/11/19
 */
public class EpochRequest {

    @NotEmpty
    private String sign;

    @JsonProperty(value = "biz_data")
    @NotEmpty
    private String biz_data;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBiz_data() {
		return biz_data;
	}

	public void setBiz_data(String biz_data) {
		this.biz_data = biz_data;
	}
    
    
}
