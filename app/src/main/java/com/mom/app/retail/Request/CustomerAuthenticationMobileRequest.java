package com.mom.app.retail.Request;

/**
 * Created by Avinashm on 06-May-16.
 */
public class CustomerAuthenticationMobileRequest {

    public String userName;
    public String password;
    public int serviceChannelId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getServiceChannelId() {
        return serviceChannelId;
    }

    public void setServiceChannelId(int serviceChannelId) {
        this.serviceChannelId = serviceChannelId;
    }



}
