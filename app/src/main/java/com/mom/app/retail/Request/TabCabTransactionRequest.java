package com.mom.app.retail.Request;

/**
 * Created by Avinashm on 21-May-16.
 */
public class TabCabTransactionRequest {

    public long userId;
    public long customerMobileNumber;
    public int operaterId;
    public float transactionAmount;
    public int circleId ;
    public int rechargeType ;
    public String tpin ;
    public int channelId ;
    public String sathiId ;

    public float serviceChargeAmount;


    public float getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(float serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public String getSathiId() {
        return sathiId;
    }

    public void setSathiId(String sathiId) {
        this.sathiId = sathiId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTpin() {
        return tpin;
    }

    public void setTpin(String tpin) {
        this.tpin = tpin;
    }

    public int getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(int rechargeType) {
        this.rechargeType = rechargeType;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(int operaterId) {
        this.operaterId = operaterId;
    }

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






}
