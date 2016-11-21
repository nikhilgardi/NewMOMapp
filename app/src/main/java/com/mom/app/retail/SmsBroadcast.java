package com.mom.app.retail;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsBroadcast extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    String smsBody;
    private static final String mpin  = "Forgot MPIN";
    private static final String tpin  = "Forgot TPIN";
    private static final String mom  = "MOMMOM";


    public void onReceive(Context context, Intent intent) {
        try{
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            assert sms != null;
            for (Object sm : sms) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm);
                String address = smsMessage.getOriginatingAddress();
               // if (address.equals("VM-MOMMOM") || address.equals("VK-MOMMOM")) {
                String substring = address.substring(Math.max(address.length() - 6, 0));
                if(substring.equals(mom))
                {
                    smsBody = smsMessage.getMessageBody();
                }
            }
            if(smsBody!=null) {

                if (smsBody.toLowerCase().contains(mpin.toLowerCase())) {

                    ForgotMPIN inst = ForgotMPIN.instance();
                    inst.receivedSms(getOTP(smsBody));


                }
                else if(smsBody.toLowerCase().contains(tpin.toLowerCase())){

                    ResetTPIN inst = ResetTPIN.instance();
                    inst.receivedSms(getOTP(smsBody));

                }
                else {

                    LoginActivity inst = LoginActivity.instance();
                    inst.receivedSms(getOTP(smsBody));

                }

            }
         }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private String getOTP(String message) {

//        String[] separated = message.split(":");
//
//        String msg=separated[0];
//        Log.e("msg",msg);
//        String OTp=separated[1];
//        Log.e("OTp",OTp);
//
//        String[] separated1 = OTp.split(" ");
//        String msg1=separated1[0];
//        Log.e("msg1",msg1);
//        String OTp1=separated1[1];
//        Log.e("OTp1",OTp1);


        Pattern p = Pattern.compile("([0-9]){6}");
        Matcher m = p.matcher(message);
        if (m.find())
        {
            System.out.println(m.group());

        }

//        m = p.matcher(s2);
//        if (m.find()) {
//            System.out.println(m.group());
//        }

        return m.group();
    }
}