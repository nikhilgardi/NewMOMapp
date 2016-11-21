
package com.mom.app.retail.Request;

/**
 * Created by Avinashm on 26-Apr-16.
 */
public class PaymentTransactionRequest {


    public long userId;
    public long customerMobileNumber;
    public int operaterId;
    public float transactionAmount;
    public int circleId ;
    public int rechargeType ;
    public String tpin ;
    public int channelId ;


    public long getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(long customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(int operaterId) {
        this.operaterId = operaterId;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public int getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(int rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getTpin() {
        return tpin;
    }

    public void setTpin(String tpin) {
        this.tpin = tpin;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }






}

