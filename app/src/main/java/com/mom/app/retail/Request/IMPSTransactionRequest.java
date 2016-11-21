package com.mom.app.retail.Request;

/**
 * Created by Akanksha on 21/5/2016.
 */
public class IMPSTransactionRequest {


    public long userId;
    public long customerMobileNumber;
    public int operaterId;
    public float transactionAmount;
    public int circleId ;
    public int rechargeType ;
    public String tpin ;
    public int channelId ;
    public float serviceChargeAmount;
    public String accountNumber ;
    public int iPin;
    public int ConsumerId;
    public int BeneficiaryID;
    public String AccountName;
    public String ifscCode;
    public String transactionNarration;



    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public float getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(float serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }


    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(long customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
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



    public void setiPin(int iPin)
    {
        this.iPin=iPin;
    }
    public int getiPin()
    {
        return iPin;
    }
    public void setConsumerId(int ConsumerId)
    {
        this.ConsumerId=ConsumerId;
    }
    public int getConsumerId()
    {
        return ConsumerId;
    }
    public void setbeneficiaryId(int BeneficiaryID)
    {
        this.BeneficiaryID=BeneficiaryID;
    }
    public int setbeneficiaryId()
    {
        return BeneficiaryID;
    }
    public void setaccountName(String BeneficiaryName)
    {
        this.AccountName=BeneficiaryName;
    }
    public String getaccountName()
    {
        return AccountName;
    }
    public void setifscCode(String ifscCode)
    {
        this.ifscCode=ifscCode;
    }
    public String getifscCode()
    {
        return ifscCode;
    }
    public void settransactionNarration(String transactionNarration)
    {
        this.transactionNarration=transactionNarration;
    }



}
