package com.mom.app.retail.Request;

/**
 * Created by Avinashm on 06-May-16.
 */
public class CheckCustomerOTPValidationRequest {

    public long customerId;
    public int serviceChannelId;
    public String tpin;
    public String otp ;
    public long customerOtpLogId;

    public long getCustomerOtpLogId() {
        return customerOtpLogId;
    }

    public void setCustomerOtpLogId(long customerOtpLogId) {
        this.customerOtpLogId = customerOtpLogId;
    }



    public String getTpin() {
        return tpin;
    }

    public void setTpin(String tpin) {
        this.tpin = tpin;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getServiceChannelId() {
        return serviceChannelId;
    }

    public void setServiceChannelId(int serviceChannelId) {
        this.serviceChannelId = serviceChannelId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


}
