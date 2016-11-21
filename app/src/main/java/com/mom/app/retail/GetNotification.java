package com.mom.app.retail;

import android.content.Context;
import android.os.AsyncTask;


import com.mom.app.retail.Response.GetCustomerNotificationResponse;
import com.mom.app.retail.Response.ResponseBase;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class   GetNotification {
    Context context;

    int lastNotificationID;
    public GetNotification(Context context)
    {
     this.context=context;
    }
    public class GetCustomerNotification extends AsyncTask<Integer,String,String>{
        String sCustomerCode, sNotificationResponse;
        SQLiteAdapter sql= new SQLiteAdapter(context);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            sql.openToReadLoginDataBase();
            sCustomerCode=sql.getCustomerCode();
            sql.openToReadNotificationDataBase();

            lastNotificationID=sql.lastNotificationID();
            if(lastNotificationID==-1)
            {
                lastNotificationID=0;
            }
            sql.close();
        }

        @Override
        protected String doInBackground(Integer... params) {

            try {



                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.GetCustomerNotification);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.customerCode, sCustomerCode));
                nameValuePairs.add(new BasicNameValuePair(String_url.notificationId, String.valueOf(lastNotificationID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                sNotificationResponse = EntityUtils.toString(entity);



            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return sNotificationResponse;



        }

        @Override


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<ResponseBase<GetCustomerNotificationResponse[]>>() {
                }.getType();
                ResponseBase<GetCustomerNotificationResponse[]> responseBase = gson.fromJson(s, type);

                if (responseBase.id == 1) {
                    for (GetCustomerNotificationResponse getCustomerNotificationResponse : responseBase.responseData) {
                        int NotificationId = getCustomerNotificationResponse.notificationId;
                        String sNotificationValidDate = getCustomerNotificationResponse.notificationValidDate;
                        String sNotificationDescription = getCustomerNotificationResponse.notificationDescription;

                        sql = new SQLiteAdapter(context);
                        sql.openToWriteNotificationDataBase();
                        sql.insertNotificationInfo(NotificationId, sNotificationValidDate, sNotificationDescription, false);

                        sql.openToReadNotificationDataBase();

                        Boolean test = sql.getStatus();

                        String[] content = sql.getContent();
                        int i = 0;
                        for (i = 0; i < content.length; i++) {

                        }



                    }

                }
                else {



                }
            }
        }

    }
}
