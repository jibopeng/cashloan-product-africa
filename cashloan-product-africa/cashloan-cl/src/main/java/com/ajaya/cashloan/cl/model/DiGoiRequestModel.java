package com.ajaya.cashloan.cl.model;


import java.util.List;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-04-26 17:42
 **/
public class DiGoiRequestModel {
    private List<DiGoiSignerModel> signers;

    private Integer expire_in_days;

    private  String display_on_page;

    private String file_name;

    private String file_data;

    public List<DiGoiSignerModel> getSigners() {
        return signers;
    }

    public void setSigners(List<DiGoiSignerModel> signers) {
        this.signers = signers;
    }

    public Integer getExpire_in_days() {
        return expire_in_days;
    }

    public void setExpire_in_days(Integer expire_in_days) {
        this.expire_in_days = expire_in_days;
    }

    public String getDisplay_on_page() {
        return display_on_page;
    }

    public void setDisplay_on_page(String display_on_page) {
        this.display_on_page = display_on_page;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_data() {
        return file_data;
    }

    public void setFile_data(String file_data) {
        this.file_data = file_data;
    }
}
