package com.mom.app.retail;

public class ReturnMPOSResponseMessage {

    public enum Errors {

        PNMPAY_001,
        PNMPAY_002,
        PNMPAY_003,
        PNMPAY_004,
        PNMPAY_005,
        PNMPAY_006,
        PNMPAY_007,
        PNMPAY_008,
        PNMPAY_009,
        PNMPAY_010,
        PNMPAY_011,
        PNMPAY_012,
        PNMPAY_013,
        PNMPAY_014,
        PNMPAY_015,
        PNMPAY_016,
        PNMPAY_017,
        PNMPAY_018,
        PNMPAY_019,
        PNMPAY_020,
        PNMPAY_022,
        PNMPAY_023,
        PNMPAY_024,
        PNMPAY_025,
        PNMPAY_026,
        PNMPAY_029,
        PNMPAY_030,
        PNMPAY_031,
        PNMPAY_032,
        PNMPAY_033,
        PNMPAY_034,
        PNMPAY_035,
        PNMPAY_037,
        PNMPAY_038,
        PNMPAY_039,
        PNMPAY_040,
        PNMPAY_041,
        PNMPAY_042,
        PNMPAY_043,
        PNMPAY_044,
        PNMPAY_045,
        PNMPAY_046,
        PNMPAY_047,
        DEFAULT_ERROR;
    }


    public String ReturnMPOSResponseMessageData(Errors errors) {
        switch ( errors) {
            case  PNMPAY_001:
                return "Invalid format";
            case  PNMPAY_002:
                return "Invalid format";
            case  PNMPAY_003:
                return "Authentication Failed.Invalid API keys";
            case  PNMPAY_004:
                return "Card data missing";
            case  PNMPAY_005:
                return "Card data missing";
            case  PNMPAY_006:
                return "Card BIN range is not available";
            case  PNMPAY_007:
                return "No brand found";
            case  PNMPAY_008:
                return "Payment type not enabled";
            case  PNMPAY_009:
                return "No payment type found for this brand ";
            case  PNMPAY_010:
                return "No Gateway found ";
            case  PNMPAY_011:
                return "Terminal not found ";
            case  PNMPAY_012:
                return "Merchant is not assigned with this terminal ";
            case  PNMPAY_013:
                return "Simulator connection not available or Error occur in card decryption.";
            case  PNMPAY_014:
                return "No Track 1, Track 2 details available";
            case  PNMPAY_015:
                return "Error occur in PAN extraction ";
            case  PNMPAY_016:
                return "Error occur in Card holder name extraction";
            case  PNMPAY_017:
                return "Invalid data format";
            case  PNMPAY_018:
                return "Transaction type is incorrect, Error in sending Email. Host not available.";
            case  PNMPAY_019:
                return "Invalid file size";
            case  PNMPAY_020:
                return "Invalid Amount";
            case  PNMPAY_022:
                return "No keys found";
            case  PNMPAY_023:
                return "Invalid masked track ";
            case  PNMPAY_024:
                return "Invalid Masked track length(less than 6)";
            case  PNMPAY_025:
                return "No merchant id found";
            case  PNMPAY_026:
                return "Invalid Merchant terminal assignment";
            case  PNMPAY_029:
                return "No card type available for this bin range";
            case  PNMPAY_030:
                return "Merchant configuration not done";
            case  PNMPAY_031:
                return "No schema available with this track details ";
            case  PNMPAY_032:
                return "No tax data available";
            case  PNMPAY_033:
                return "Error in TDR calculation ";
            case  PNMPAY_034:
                return "Invalid image format";
            case  PNMPAY_035:
                return "Certificates  generation failed";
            case  PNMPAY_037:
                return "DataAccessException";
            case  PNMPAY_038:
                return "IOException";
            case  PNMPAY_039:
                return "Invalid key specification";
            case  PNMPAY_040:
                return "Card Holder Name extraction failed";
            case  PNMPAY_041:
                return "NullPointerException";
            case  PNMPAY_042:
                return "No response from Simulator";
            case  PNMPAY_043:
                return "Minimum amount should be atleast Rs.11/-";
            case  PNMPAY_044:
                return " This card is not eligible for EMI.";
            case  PNMPAY_045:
                return "Error in generating ChargeSlip, Error in generating ChargeSlip.Host not available.";
            case  PNMPAY_046:
                return "Transaction type is not correct, Error in sending SMS, Error in sending SMS. Host not available";
            case  PNMPAY_047:
                return "Brand is not configured for the device Terminal you are using";
            case DEFAULT_ERROR:
                return "Some Error Occured.Please try again Later";
            default:
                return "Some Error Occured.Please try again later";
        }
    }
}
