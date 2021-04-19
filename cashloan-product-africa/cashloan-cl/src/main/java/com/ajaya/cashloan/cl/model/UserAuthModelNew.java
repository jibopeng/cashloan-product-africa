package com.ajaya.cashloan.cl.model;

import com.ajaya.cashloan.cl.domain.UserAuth;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-04-23 14:59
 **/
public class UserAuthModelNew extends UserAuth {

    private String synced;

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }
}
