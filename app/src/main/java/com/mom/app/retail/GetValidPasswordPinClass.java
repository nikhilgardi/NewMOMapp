package com.mom.app.retail;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;


import com.mom.app.retail.Response.PaymentRequestResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GetValidPasswordPinClass extends AsyncTask<String,Integer,String> {

    Context context;
    Activity activity;
    String GetValidPassword_response,message;
   
    SharedPreferences sharedPreferences;
    Long string_CustomerAuthenticationID;
    public static final String PREFS_NAME = "MyApp_Settings";
    public String sTpin;
    public GetValidPasswordPinClass(Context context,Activity activity,String sTpin)
    {
        this.context=context;
        this.activity=activity;
        this.sTpin=sTpin;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected String doInBackground(String... params) {
        try {


            sharedPreferences=context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            string_CustomerAuthenticationID=sharedPreferences.getLong(String_url.CustomerAuthenticationID, 0);



            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(String_url.GetValidPasswordPin);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
            nameValuePairs.add(new BasicNameValuePair(String_url.CustomerAuthenticationID, String.valueOf(string_CustomerAuthenticationID)));
            nameValuePairs.add(new BasicNameValuePair(String_url.password, sTpin));
            nameValuePairs.add(new BasicNameValuePair(String_url.passwordType,String.valueOf(String_url.tpin_password_type)));


            HttpParams httpParams = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            GetValidPassword_response = EntityUtils.toString(entity);

            Gson gson=new GsonBuilder().create();
            Type type=new TypeToken<PaymentRequestResult>(){}.getType();
            PaymentRequestResult responseBase=gson.fromJson(GetValidPassword_response,type);
            message=responseBase.message;



        }
         catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


    }
}
