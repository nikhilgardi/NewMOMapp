package com.mom.app.retail.Request;

/**
 * Created by Avinashm on 07-May-16.
 */
public class MacAuthCustomerMasterPinRequest {

    public String AuthKey;
    public String customerCode;
    public String macId;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAuthKey() {
        return AuthKey;
    }

    public void setAuthKey(String authKey) {
        AuthKey = authKey;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }


}
