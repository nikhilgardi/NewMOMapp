package com.mom.app.retail.Request;

/**
 * Created by Avinashm on 15-Jun-16.
 */
public class GetPasswordChangeRequest {

    public long customerAuthenticationId;
    public String oldPassword;
    public String newPassword;
    public int passwordType;
    public int authorizedRole;
    public int serviceChannelId;
    public long requestCustomerId;


    public long getRequestCustomerId() {
        return requestCustomerId;
    }

    public void setRequestCustomerId(long requestCustomerId) {
        this.requestCustomerId = requestCustomerId;
    }

    public int getServiceChannelId() {
        return serviceChannelId;
    }

    public void setServiceChannelId(int serviceChannelId) {
        this.serviceChannelId = serviceChannelId;
    }

    public int getAuthorizedRole() {
        return authorizedRole;
    }

    public void setAuthorizedRole(int authorizedRole) {
        this.authorizedRole = authorizedRole;
    }

    public int getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(int passwordType) {
        this.passwordType = passwordType;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public long getCustomerAuthenticationId() {
        return customerAuthenticationId;
    }

    public void setCustomerAuthenticationId(long customerAuthenticationId) {
        this.customerAuthenticationId = customerAuthenticationId;
    }


}
