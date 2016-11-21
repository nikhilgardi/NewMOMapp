package com.mom.app.retail;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mom.app.retail.Response.CRMCallAndSubCategoriesResponseResult;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book_complaint extends Fragment {
    private ImageView imageView;
    private TextView tvBookImageText,tv_book,tvexceptionbookcomplaint;
    private EditText et_enter_trans,et_enter_comment,et_AlternateNo;
    private Button btn_submit,btn_cancel;

    Spinner spCall,spCallSub,spLanguage,spSupportChannel;
    List<String> listCallCategory,listSubCallCategory,listLanguage,listSupportChannel;
    Map<String,Integer> mapCallCategory,mapSubCallCategory,mapLanguage,mapSupportChannel;
    Typeface Light,Thin,Condensed;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v             = inflater.inflate(R.layout.fragment_book_complaint, container, false);
         Light              = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
         Thin               = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
         Condensed          = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Condensed.ttf");

        imageView           =(ImageView)v.findViewById(R.id.iv_book_complain);
        tvBookImageText     =(TextView)v.findViewById(R.id.tvBookImageText);
        tv_book             =(TextView)v.findViewById(R.id.tv_book);
        et_enter_trans      =(EditText)v.findViewById(R.id.et_enter_trans);
        et_enter_comment    =(EditText)v.findViewById(R.id.et_enter_comment);
        et_AlternateNo      =(EditText)v.findViewById(R.id.et_AlternateNo);
        btn_submit          =(Button)v.findViewById(R.id.btn_submit);
        btn_cancel          =(Button)v.findViewById(R.id.btn_cancel);

         spCall             = (Spinner)v.findViewById(R.id.spCall);
         spCallSub          = (Spinner)v.findViewById(R.id.spCallSub);
         spLanguage         = (Spinner)v.findViewById(R.id.spLanguage);
         spSupportChannel   = (Spinner)v.findViewById(R.id.spSupportChannel);
        tvexceptionbookcomplaint=(TextView)v.findViewById(R.id.tvexceptionbookcomplaint);

        tvexceptionbookcomplaint.setTypeface(Condensed);
        tvBookImageText.setTypeface(Light);
        tv_book.setTypeface(Thin);
        et_enter_trans.setTypeface(Condensed);
        et_enter_comment.setTypeface(Condensed);
        btn_cancel.setTypeface(Condensed);
        btn_submit.setTypeface(Condensed);

        imageView.setBackgroundResource(R.drawable.allscreen);

        listCallCategory    = new ArrayList<String>();
        listSubCallCategory = new ArrayList<String>();
        listLanguage        = new ArrayList<String>();
        listSupportChannel  = new ArrayList<String>();

        listCallCategory.add("Select Call Category");
        listSubCallCategory.add("Select Call Sub Category");
        listLanguage.add("Select Language");
        listSupportChannel.add("Select Support Channel");

        GetCRMCallCategories getCRMCallCategories= new GetCRMCallCategories();
        getCRMCallCategories.execute();



        spCall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spCall.getSelectedItemPosition() > 0) {
                    GetCallSubCategoriesByCallCategoryID getCallSubCategoriesByCallCategoryID = new GetCallSubCategoriesByCallCategoryID();
                    getCallSubCategoriesByCallCategoryID.execute();
                }
                else{
                    spCallSub.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                Setting setting=new Setting();
                fragmentTransaction.replace(R.id.fragmentcontent,setting).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return v;
    }
    public boolean validate() {


        String sAlternateNo =et_AlternateNo.getText().toString();
        int spCallID        =spCall.getSelectedItemPosition();
        int spCallSubID     =spCallSub.getSelectedItemPosition();
        int spLanguageID    =spLanguage.getSelectedItemPosition();
        int spSupportID     =spSupportChannel.getSelectedItemPosition();

        if(sAlternateNo.length()<10)
        {
            tvexceptionbookcomplaint.setText(getString(R.string.error_AlternateNo));
            return false;
        }
        else if(spCallID==0)
        {
            tvexceptionbookcomplaint.setText(getString(R.string.error_CallCategory));
            return false;
        }
        else if(spCallSubID==0)
        {
            tvexceptionbookcomplaint.setText(getString(R.string.error_CallSubCategory));
            return false;
        }
        else if(spLanguageID==0)
        {
            tvexceptionbookcomplaint.setText(getString(R.string.error_Language));
            return false;
        }

        else if(spSupportID==0)
        {
            tvexceptionbookcomplaint.setText(getString(R.string.error_SupportChannel));
            return false;
        }
        else {


        }



        return true;

    }

    private class GetCRMCallCategories extends AsyncTask<Integer,String,String>{

        String CRMCallCategoriesResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapCallCategory =new HashMap<>();

        }

        @Override
        protected String doInBackground(Integer... params) {
            try {



                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.GetCRMCallCategories);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                CRMCallCategoriesResponse = EntityUtils.toString(entity);



            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return CRMCallCategoriesResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null)
            {
                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<ResponseBase<CRMCallAndSubCategoriesResponseResult[]>>() {}.getType();
                ResponseBase<CRMCallAndSubCategoriesResponseResult[]> responseBase = gson.fromJson(s, type);


                if(responseBase.id==1)
                {
                    for(CRMCallAndSubCategoriesResponseResult crmCallAndSubCategoriesResponseResult:responseBase.responseData)
                    {
                        String CallCategoryDescription=crmCallAndSubCategoriesResponseResult.CallCategoryDescription;

                        int CallCategoryID=crmCallAndSubCategoriesResponseResult.CallCategoryID;


                        mapCallCategory.put(CallCategoryDescription, CallCategoryID);
                        listCallCategory.add(CallCategoryDescription);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listCallCategory)
                        {

                            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(Condensed);
                                v.setTextColor(Color.parseColor(String_url.spinnerHintColor));
                                v.setTextSize(18);
                                v.setGravity(Gravity.CENTER);

                                return v;
                            }

                            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(Condensed);
                                v.setTextColor(Color.parseColor(String_url.spinnerDropDownTextColor));
                                v.setTextSize(18); v.setGravity(Gravity.CENTER);
                                v.setPadding(15,15,15,15);
                                return v;
                            }
                        };
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spCall.setAdapter(adapter);
                    }

                }
                else {

                    tvexceptionbookcomplaint.setText(responseBase.message);
                    final ResponseDialog responseDialog = new ResponseDialog(getActivity(), getActivity());
                    responseDialog.showResponseDialog(1,
                            getString(R.string.response),
                            null, null, null, null,
                            responseBase.message,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    responseDialog.dialog.dismiss();
                                }
                            }
                    );
                }

            }
            else {
                tvexceptionbookcomplaint.setText(getString(R.string.service_unavailable));
            }

        }
    }

    private class GetCallSubCategoriesByCallCategoryID extends AsyncTask<Integer,String,String>{

        String CallSubCategoriesResponse;
        int callCategoryID;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapSubCallCategory =new HashMap<>();


            String callItem = spCall.getSelectedItem().toString();

            callCategoryID = mapCallCategory.get(callItem);


        }

        @Override
        protected String doInBackground(Integer... params) {
            try {



                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.GetCallSubCategoriesByCallCategoryID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.callCategoryId,String.valueOf(callCategoryID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                CallSubCategoriesResponse = EntityUtils.toString(entity);



            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return CallSubCategoriesResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null)
            {
                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<ResponseBase<CRMCallAndSubCategoriesResponseResult[]>>() {}.getType();
                ResponseBase<CRMCallAndSubCategoriesResponseResult[]> responseBase = gson.fromJson(s, type);


                if(responseBase.id==1)
                {
                    for(CRMCallAndSubCategoriesResponseResult crmCallAndSubCategoriesResponseResult:responseBase.responseData)
                    {
                        String CallSubCategoryDescription=crmCallAndSubCategoriesResponseResult.CallSubCategoryDescription;

                        int CallSubCategoryID=crmCallAndSubCategoriesResponseResult.CallSubCategoryID;


                        mapSubCallCategory.put(CallSubCategoryDescription, CallSubCategoryID);
                        listSubCallCategory.add(CallSubCategoryDescription);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSubCallCategory)
                        {

                            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(Condensed);
                                v.setTextColor(Color.parseColor(String_url.spinnerHintColor));
                                v.setTextSize(18);
                                v.setGravity(Gravity.CENTER);

                                return v;
                            }

                            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(Condensed);
                                v.setTextColor(Color.parseColor(String_url.spinnerDropDownTextColor));
                                v.setTextSize(18); v.setGravity(Gravity.CENTER);
                                v.setPadding(15,15,15,15);
                                return v;
                            }
                        };
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spCallSub.setAdapter(adapter);
                    }

                }
                else {

                    tvexceptionbookcomplaint.setText(responseBase.message);
                    final ResponseDialog responseDialog = new ResponseDialog(getActivity(), getActivity());
                    responseDialog.showResponseDialog(1,
                            getString(R.string.response),
                            null, null, null, null,
                            responseBase.message,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    responseDialog.dialog.dismiss();
                                }
                            }
                    );
                }

            }
            else {
                tvexceptionbookcomplaint.setText(getString(R.string.service_unavailable));
            }

        }
    }


}
