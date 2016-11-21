package com.mom.app.retail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mom.app.retail.Request.VerifyCustomerForgotPasswordRequest;
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
import java.util.concurrent.TimeUnit;




public class ResetTPIN extends Fragment{

    private EditText etMobNo,etOTP;
    private Button btnSubmitMobno,btnCancelMobno,btnSubmitOtp,btnCancelOtp;
    private TextView tvResetTpinHeader,tvTransactionSuccess, tvResetTpinImage,tvExcepionResetTpin,tvOtpTimer;
    private LinearLayout lbtn,lbtn1;
    private LinearLayout page1;
    private RelativeLayout page2;
    private ImageView imageView;
    private String str_customerno,str_newmpin,str_conmpin,forgotId;
    private static ResetTPIN inst;
    private ProgressDialog progressDialog;
    public static final String PREFS_NAME = "MyApp_Settings";
    MyCountDownTimer myCountDownTimer;
    private final long startTime = 180 * 1000;
    private final long interval = 1 * 1000;
    private ProgressBar progressBar;
    ComProgressDialog comProgressDialog;


    protected static ResetTPIN instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view= inflater.inflate(R.layout.fragment_reset_tpin,container,false);

        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

        imageView = (ImageView) view.findViewById(R.id.ivResetTpinHeader);
        imageView.setBackgroundResource(R.drawable.allscreen);

        tvResetTpinImage=(TextView)view.findViewById(R.id.tvResetTpinImage);
        tvResetTpinImage.setTypeface(Light);

        tvExcepionResetTpin=(TextView)view.findViewById(R.id.tvExcepionResetTpin);
        tvExcepionResetTpin.setTypeface(Light);

        etMobNo=(EditText)view.findViewById(R.id.etMobNo);
        etMobNo.setTypeface(Light);

        etOTP=(EditText)view.findViewById(R.id.etOTP);
        etOTP.setTypeface(Light);

        btnSubmitMobno=(Button)view.findViewById(R.id.btnSubmitMobno);
        btnSubmitMobno.setTypeface(Normal);

        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnCancelMobno=(Button)view.findViewById(R.id.btnCancelMobno);
        btnCancelMobno.setTypeface(Normal);

        btnSubmitOtp=(Button)view.findViewById(R.id.btnSubmitOTP);
        btnSubmitOtp.setTypeface(Normal);

        btnCancelOtp=(Button)view.findViewById(R.id.btnCancelOTP);
        btnCancelOtp.setTypeface(Normal);

        tvResetTpinHeader=(TextView)view.findViewById(R.id.tvResetTpinHeader);
        tvResetTpinHeader.setTypeface(Light);
        
        page1=(LinearLayout)view.findViewById(R.id.Page1);
        page2=(RelativeLayout)view.findViewById(R.id.Page2);

        tvOtpTimer =(TextView)view.findViewById(R.id.tvOtpTimer);
        tvOtpTimer.setTypeface(Light);

        progressDialog= new ProgressDialog(getActivity());

        myCountDownTimer= new MyCountDownTimer(startTime,interval);

        btnSubmitMobno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitMobno.setEnabled(false);
                // validate();
//                page1.setVisibility(View.GONE);
//                page2.setVisibility(View.VISIBLE);
                if(etMobNo.getText().length()==10)
                {

                    CustomerForgotPassword customerForgotPassword= new CustomerForgotPassword();
                    customerForgotPassword.execute();
                }
                else
                {
                    btnSubmitMobno.setEnabled(true);
                    tvExcepionResetTpin.setText(getString(R.string.mobile_recharge_mobile_no_length));

                }


            }
        });

        btnCancelMobno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                Setting setting=new Setting();
                fragmentTransaction.replace(R.id.fragmentcontent,setting).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        btnSubmitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitOtp.setEnabled(false);

                //   page1.setVisibility(View.GONE);
                //   page2.setVisibility(View.GONE);
                //   page3.setVisibility(View.VISIBLE);

                if(etOTP.getText().length()==6) {
                    myCountDownTimer.cancel();
                    VerifyCustomerForgotPassword verifyCustomerForgotPassword= new VerifyCustomerForgotPassword();
                    verifyCustomerForgotPassword.execute();

                }
                else {
                    btnSubmitOtp.setEnabled(true);

                    tvExcepionResetTpin.setText(R.string.OTP_validation);
                }

            }
        });

        btnCancelOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitMobno.setEnabled(true);
                tvExcepionResetTpin.setText("");
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.GONE);


            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long MobNo= sharedPreferences.getLong(String_url.MobileNumber, 0);
        etMobNo.setText(String.valueOf(MobNo));
        etMobNo.setEnabled(false);

        return view;
        
    }

    private class CustomerForgotPassword extends AsyncTask<Integer,String,String> {

        String mobNo,sforgetResponse;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mobNo=etMobNo.getText().toString();


        }

        @Override
        protected String doInBackground(Integer... params) {

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.CustomerForgotPassword);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.MobileNumber, mobNo));
                nameValuePairs.add(new BasicNameValuePair(String_url.forgetPasswordType,String.valueOf(2)));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceChannelId, String.valueOf(String_url.str_servicechannelid)));

                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                sforgetResponse = EntityUtils.toString(entity);


            }

            catch (Exception e) {
                e.printStackTrace();
            }

            return sforgetResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if(s!=null)
                {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>(){}.getType();
                    ResponseBase responseBase = gson.fromJson(s,type);

                    if(responseBase.id==1)
                    {
                        page1.setVisibility(View.GONE);
                        page2.setVisibility(View.VISIBLE);
                        forgotId=responseBase.responseData.toString();

                        progressDialog.setMessage("Generating OTP please wait...");
                        // progressDialog.setCancelable(false);
                        progressDialog.show();
                        myCountDownTimer.start();
                    }
                    else {

                        final ResponseDialog responseDialog= new ResponseDialog(getActivity(),getActivity());
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                responseBase.message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnSubmitMobno.setEnabled(true);
                                        responseDialog.dialog.dismiss();
                                    }
                                }


                        );
                    }


                }
            }
            catch (Exception e)
            {e.printStackTrace();}



        }
    }

    private class VerifyCustomerForgotPassword extends AsyncTask<Integer,String,String>{
        String sverifyOTPResponse,mobNo,sOTP;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mobNo=etMobNo.getText().toString();
            sOTP=etOTP.getText().toString();
           // progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
        }

        @Override
        protected String doInBackground(Integer... params) {
            try {

                Gson gson = new GsonBuilder().create();
                VerifyCustomerForgotPasswordRequest verifyCustomerForgotPasswordRequest = new VerifyCustomerForgotPasswordRequest();
                Type type = new TypeToken<VerifyCustomerForgotPasswordRequest>() {}.getType();

                verifyCustomerForgotPasswordRequest.setForgotId(Long.valueOf(forgotId));
                verifyCustomerForgotPasswordRequest.setPasswordType(String_url.TPIN_password);
                verifyCustomerForgotPasswordRequest.setOtp(sOTP);
                verifyCustomerForgotPasswordRequest.setServiceChannelId(String_url.str_servicechannelid);
                verifyCustomerForgotPasswordRequest.setAuthorizedRole(String_url.AuthorizedRole);

                String json = gson.toJson(verifyCustomerForgotPasswordRequest, type);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.VerifyCustomerForgotPassword);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.forgotPasswordVerifyRequest, json));




                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                sverifyOTPResponse = EntityUtils.toString(entity);


            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return sverifyOTPResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if(s!=null)
                {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>(){}.getType();
                    ResponseBase responseBase = gson.fromJson(s,type);

                    if(responseBase.id==1)
                    {
                        final ResponseDialog responseDialog= new ResponseDialog(getActivity(),getActivity());
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                getString(R.string.forgot_password_tpin_success),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        responseDialog.dialog.dismiss();
                                        btnSubmitOtp.setEnabled(true);

                                        FragmentManager fragmentManager2=getFragmentManager();
                                        FragmentTransaction fragmentTransaction2=fragmentManager2.beginTransaction();
                                        fragmentTransaction2.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                                        Setting setting=new Setting();
                                        fragmentTransaction2.replace(R.id.fragmentcontent,setting).addToBackStack(null);
                                        fragmentTransaction2.commit();


                                    }
                                }


                        );

                    }
                    else {

                        final ResponseDialog responseDialog= new ResponseDialog(getActivity(),getActivity());
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                responseBase.message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnSubmitOtp.setEnabled(true);

                                        responseDialog.dialog.dismiss();
                                    }
                                }


                        );
                    }

                }
               // progressBar.setVisibility(View.GONE);
                comProgressDialog.cancelProgress();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public void receivedSms(String message) {
        try
        {
//            tvOtpTimer.setText("");
//            myCountDownTimer.cancel();
 //           progressDialog.dismiss();
            etOTP.setText(message);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private class MyCountDownTimer extends CountDownTimer {
        final String FORMAT = "%02d:%02d";
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String time=""+String.format(FORMAT,

                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

            if(time.equals("02:55"))
            {
                progressDialog.dismiss();
            }


            tvOtpTimer.setText(time);
        }

        @Override
        public void onFinish() {
            tvOtpTimer.setText("Resend OTP!");
            tvOtpTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomerForgotPassword customerForgotPassword= new CustomerForgotPassword();
                    customerForgotPassword.execute();

                }
            });
        }
    }
}
