package com.mom.app.retail.Request;

/**
 * Created by Avinashm on 15-Jun-16.
 */
public class VerifyCustomerForgotPasswordRequest {

    public long forgotId ;
    public int passwordType ;
    public int serviceChannelId ;
    public String otp ;
    public int authorizedRole ;


    public int getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(int passwordType) {
        this.passwordType = passwordType;
    }

    public int getServiceChannelId() {
        return serviceChannelId;
    }

    public void setServiceChannelId(int serviceChannelId) {
        this.serviceChannelId = serviceChannelId;
    }

    public long getForgotId() {
        return forgotId;
    }

    public void setForgotId(long forgotId) {
        this.forgotId = forgotId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getAuthorizedRole() {
        return authorizedRole;
    }

    public void setAuthorizedRole(int authorizedRole) {
        this.authorizedRole = authorizedRole;
    }




}
