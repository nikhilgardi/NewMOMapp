package com.mom.app.retail;

/**
 * Created by nikhilg on 1/28/2016.
 */
public class ReturnResponseMessage {
    public String ReturnResponseMessageData(int id) {
        switch (id) {
            case 1:
                return "SUCCESS";
            case 0:
                return "FAILED";
            case 1000:
                return "Invalid Pincode";
            case 1001:
                return "Please Enter Valid Pincode";
            case 1002:
                return "Invalid Date Of Birth";
            case 1003:
                return "Invalid Customer Code";
            case 1004:
                return "Invalid Pancard Number";
            case 1005:
                return "Mobile Number is Already Exists";
            case 1006:
                return "Customer parent not exists";
            case 1007:
                return "CUSTOMER_PARENT_NOT_MAP_FIRST_YOU_CREATE_COMPANY";
            case 1008:
                return "CUSTOMER_PARENT_NOT_MAP_FIRST_YOU_CREATE_CNF";
            case 1009:
                return "CUSTOMER_PARENT_NOT_MAP_FIRST_YOU_CREATE_MD";
            case 1010:
                return "CUSTOMER_PARENT_NOT_MAP_FIRST_YOU_CREATE_AD";
            case 1011:
                return "CUSTOMER_PARENT_NOT_MAP_FIRST_YOU_CREATE_RETAILER";
            case 1012:
                return "CUSTOMER_ROLE_NOT_VALID_CHAIN";
            case 1013:
                return "REGISTRATION_FAILED_MASTER_CUSTOMER_PROFILES";
            case 1014:
                return "REGISTRATION_FAILED_DETAILS_CUSTOMER_WALLETS";
            case 1015:
                return "REGISTRATION_FAILED_DETAILS_CUSTOMER_AUTHENTICATION";
            case 1016:
                return "REGISTRATION_FAILED_DETAILS_CUSTOMERS";
            case 1017:
                return "REGISTRATION_FAILED_MPIN_TPIN_DETAILS_CUSTOMER_PASSWORDS";
            case 1018:
                return "REGISTRATION_FAILED_CUSTOMER_PASSWORD_ROLE_NOT_MAP";
            case 1019:
                return "REQUIRED_FIELD_VALIDATION";
            case 1020:
                return "ERROR_FOREIGN_KEY_UNIQUE_KEY";
            case 1021:
                return "INTERNAL_ERROR";
            case 1022:
                return "SERVICE_ERROR";
            case 1023:
                return "LOGIN_FAILED";
            case 1024:
                return "COMMISSIONTEMPLATE_NOT_FOUND";
            case 1025:
                return "REGISTRATION_FAILED_DETAILS_CUSTOMERTEMPLATEALLOCATION";
            case 1026:
                return "INVALID_COMMISSIONTEMPLATEID";
            case 1027:
                return "REGISTRATION_FAILED_MASTER_CUSTOMER_BANKING_DETAILS";
            case 1028:
                return "INVALID_DATE";
            case 1029:
                return "ERROR_OCCURED_INVALID_PASSWORD_PLEASE_TRY_AGAIN";
            case 1030:
                return "INVALID_PASSWORD";
            case 1031:
                return "INVALID_MOBILE_NUMBER";
            case 1032:
                return "INTERNAL_PAYMENT_TRANSFER_FAILED";
            case 1033:
                return "INTERNAL_PAYMENT_TRANSFER_REVERSE_FAILED";
            case 1034:
                return "INTERNAL_PAYMENT_TRANSFER_REVERSE_ID_NULL";
            case 1035:
                return "INTERNAL_PAYMENT_TRANSFER_ID_NULL";
            case 1036:
                return "INVALID_PARENT";
            case 1037:
                return "INSUFFICIENT_WALLET_BALANCE";
            case 1038:
                return "MOBILE_NUMBER_NOT_VALID_CHAIN";
            default:
                return "Please try again later";
        }
    }
}
