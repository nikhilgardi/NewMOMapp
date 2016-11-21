package com.mom.app.retail;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.mom.app.retail.Response.CustomerAuthenticationMobileResponse;
import com.mom.app.retail.Response.ResponseBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Type;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class GetBalanceClass extends AsyncTask<String,Integer,String> {

    Context context;
    Activity activity;
    String message;
    
    SharedPreferences mbpayment;
    public static final String PREFS_NAME = "MyApp_Settings";
    public GetBalanceClass(Context context,Activity activity)
    {
        this.context=context;
        this.activity=activity;
    }
    SQLiteAdapter sql;
    String sCustomerCode,sMasterPinValidationResponse;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        sql= new SQLiteAdapter(context);
        sql.openToReadLoginDataBase();
        sCustomerCode=sql.getCustomerCode();
        sql.close();




    }

    @Override
    protected String doInBackground(String... params) {


        try {


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(String_url.CheckCustomerMasterPinValidation);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
            nameValuePairs.add(new BasicNameValuePair(String_url.customerCode, sCustomerCode));
            nameValuePairs.add(new BasicNameValuePair(String_url.macId, getMacAddr()));

            final HttpParams httpParams = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            sMasterPinValidationResponse = EntityUtils.toString(entity);



        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sMasterPinValidationResponse;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Typeface Light = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Light);
        String balance= null;

        if(s!=null) {

            try {

                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<ResponseBase<CustomerAuthenticationMobileResponse>>() {}.getType();
                ResponseBase<CustomerAuthenticationMobileResponse> responseBase = gson.fromJson(s, type);
                CustomerAuthenticationMobileResponse customerAuthenticationMobileResponse = responseBase.responseData;


                if (responseBase.id == 1) {

                    double customerMainAccountBalance = customerAuthenticationMobileResponse.customerMainAccountBalance;



                    sql.openToReadBalanceDataBase();
                    sql.deleteBalance();
                    sql.openToWriteBalanceDataBase();
                    sql.insertBalance(customerMainAccountBalance);
                    sql.close();
                    DecimalFormat precision = new DecimalFormat("0.00");

                    TextView textView1=(TextView)activity.findViewById(R.id.tvActionBalance);
                    balance=context.getString(R.string.Balance).concat(context.getString(R.string.Rs)).concat(String.valueOf(precision.format(customerMainAccountBalance)));
                  try {
                        textView1.setText(balance);
                        textView1.setTypeface(Light);
                    }
                  catch (NullPointerException e)
                  {
                      e.printStackTrace();
                  }







                }

            } catch (JsonSyntaxException jse) {

                jse.printStackTrace();

            }
        }
    }



    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                  //  res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }

                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }




}
