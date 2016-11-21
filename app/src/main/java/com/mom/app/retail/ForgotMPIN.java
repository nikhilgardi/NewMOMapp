package com.mom.app.retail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class ForgotMPIN extends Activity {

    private EditText etMobNo,etOTP;
    private Button btnSubmitMobno,btnCancelMobno,btnSubmitOtp,btnCancelOtp;
    private TextView tvForgotPwdHeader,tvTransactionSuccess, tvForgotPasswordImage,tvExcepionForgotpassword,tvOtpTimer;
    private LinearLayout lbtn,lbtn1;
    private LinearLayout page1;
    private RelativeLayout page2;
    private ImageView imageView;
    private String str_customerno,str_newmpin,str_conmpin;
    private static ForgotMPIN inst;
    private ProgressDialog progressDialog;
    private String forgotid;
    MyCountDownTimer myCountDownTimer;
    private final long startTime = 180 * 1000;
    private final long interval = 1 * 1000;
    private ProgressBar progressBar;


    protected static ForgotMPIN instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword_mpin);

        Typeface Light = Typeface.createFromAsset(getAssets(),String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(getAssets(),String_url.OpenSans_Semibold);

        imageView = (ImageView) findViewById(R.id.ivForgotPasswordHeader);
        imageView.setBackgroundResource(R.drawable.allscreen);

        tvForgotPasswordImage=(TextView)findViewById(R.id.tvForgotPasswordImage);
        tvForgotPasswordImage.setTypeface(Light);

        tvExcepionForgotpassword=(TextView)findViewById(R.id.tvExcepionForgotpassword);
        tvExcepionForgotpassword.setTypeface(Condensed);

        etMobNo=(EditText)findViewById(R.id.etMobNo);
        etMobNo.setTypeface(Condensed);

        etOTP=(EditText)findViewById(R.id.etOTP);
        etOTP.setTypeface(Condensed);

        btnSubmitMobno=(Button)findViewById(R.id.btnSubmitMobno);
        btnSubmitMobno.setTypeface(Condensed);

        btnCancelMobno=(Button)findViewById(R.id.btnCancelMobno);
        btnCancelMobno.setTypeface(Condensed);

        btnSubmitOtp=(Button)findViewById(R.id.btnSubmitOTP);
        btnSubmitOtp.setTypeface(Condensed);

        btnCancelOtp=(Button)findViewById(R.id.btnCancelOTP);
        btnCancelOtp.setTypeface(Condensed);

        tvForgotPwdHeader=(TextView)findViewById(R.id.tv_forgotPasswordHeader);
        tvForgotPwdHeader.setTypeface(Thin);

        tvOtpTimer=(TextView)findViewById(R.id.tvOtpTimer);
        tvOtpTimer.setTypeface(Condensed);


        progressBar =(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        page1=(LinearLayout)findViewById(R.id.Page1);
        page2=(RelativeLayout)findViewById(R.id.Page2);

        progressDialog= new ProgressDialog(ForgotMPIN.this);
        myCountDownTimer= new MyCountDownTimer(startTime,interval);

        btnSubmitMobno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitMobno.setEnabled(false);

                if(etMobNo.getText().length()==10)
                {
                    CustomerForgotPassword customerForgotPassword= new CustomerForgotPassword();
                    customerForgotPassword.execute();
                }
                else
                {
                    btnSubmitMobno.setEnabled(true);
                    tvExcepionForgotpassword.setText(getString(R.string.mobile_recharge_mobile_no_length));

                }


            }
        });

        btnCancelMobno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();



            }
        });

        btnSubmitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitOtp.setEnabled(false);


                if(etOTP.getText().length()==6) {
                    myCountDownTimer.cancel();
                    VerifyCustomerForgotPassword verifyCustomerForgotPassword= new VerifyCustomerForgotPassword();
                    verifyCustomerForgotPassword.execute();

                }
                else {
                    btnSubmitOtp.setEnabled(true);
                    tvExcepionForgotpassword.setText(R.string.OTP_validation);
                }

            }
        });

        btnCancelOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnSubmitMobno.setEnabled(true);
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.GONE);


            }
        });

       


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_forgot_password1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent i= new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();
    }

    public boolean validate()
    {

        int nMinLength          = 12;
        int nMaxLength          = 12;
        str_customerno=etMobNo.getText().toString();
        int ncustomernoLength   = etMobNo.getText().toString().trim().length();
        if (str_customerno.trim().equals("")) {

            tvExcepionForgotpassword.setText(getString(R.string.error_customer_id_required));
            getResponseDialog(getString(R.string.error_customer_id_required));
            return false;
        }
        else if(ncustomernoLength < nMinLength || ncustomernoLength > nMaxLength){

            tvExcepionForgotpassword.setText(getString(R.string.error_customer_id_length));
            getResponseDialog(getString(R.string.error_customer_id_length));
            return false;
        }
        else
        {
            tvExcepionForgotpassword.setText("");
            etOTP.setText("");

             page1.setVisibility(View.GONE);
             page2.setVisibility(View.VISIBLE);
        }
        return true;
    }

    private class CustomerForgotPassword extends AsyncTask<Integer,String,String>{

        String mobNo,sforgetResponse;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mobNo=etMobNo.getText().toString();

            progressDialog.setMessage("Authenticating....");
            progressDialog.setCancelable(false);

            progressDialog.show();

        }

        @Override
        protected String doInBackground(Integer... params) {

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.CustomerForgotPassword);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.MobileNumber, mobNo));
                nameValuePairs.add(new BasicNameValuePair(String_url.forgetPasswordType,String.valueOf(1)));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceChannelId, String.valueOf(String_url.str_servicechannelid)));

                final HttpParams httpParams = httpclient.getParams();
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
            try
            {
                if(s!=null)
                {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>(){}.getType();
                    ResponseBase responseBase = gson.fromJson(s,type);



                    if(responseBase.id==1)
                    {
                        page1.setVisibility(View.GONE);
                        page2.setVisibility(View.VISIBLE);
                        forgotid=responseBase.responseData.toString();

                        progressDialog.setMessage("Generating OTP please wait...");

                        progressDialog.show();
                        myCountDownTimer.start();
                    }
                    else {

                        final ResponseDialog responseDialog= new ResponseDialog(ForgotMPIN.this,ForgotMPIN.this);
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                responseBase.message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        progressDialog.cancel();
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
            progressDialog.setMessage("Authenticating....");
            progressDialog.setCancelable(false);

            progressDialog.show();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... params) {
            try {

                Gson gson = new GsonBuilder().create();
                VerifyCustomerForgotPasswordRequest verifyCustomerForgotPasswordRequest = new VerifyCustomerForgotPasswordRequest();
                Type type = new TypeToken<VerifyCustomerForgotPasswordRequest>() {}.getType();


                verifyCustomerForgotPasswordRequest.setForgotId(Long.valueOf(forgotid));
                verifyCustomerForgotPasswordRequest.setPasswordType(String_url.MPIN_password);
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
                HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
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
            try{
                if(s!=null)
                {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>(){}.getType();
                    ResponseBase responseBase = gson.fromJson(s,type);

                    if(responseBase.id==1)
                    {
                        final ResponseDialog responseDialog= new ResponseDialog(ForgotMPIN.this,ForgotMPIN.this);
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                getString(R.string.forgot_password_mpin_success),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        responseDialog.dialog.dismiss();
                                        btnSubmitOtp.setEnabled(true);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }


                        );

                    }
                    else {

                        myCountDownTimer.cancel();
                        tvOtpTimer.setText("Resend OTP!");
                        tvOtpTimer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                etOTP.setText(null);
                                CustomerForgotPassword customerForgotPassword= new CustomerForgotPassword();
                                customerForgotPassword.execute();

                            }
                        });

                        final ResponseDialog responseDialog= new ResponseDialog(ForgotMPIN.this,ForgotMPIN.this);
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                responseBase.message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        etOTP.setText(null);
                                        btnSubmitOtp.setEnabled(true);
                                        responseDialog.dialog.dismiss();
                                    }
                                }


                        );
                    }

                }
                //progressBar.setVisibility(View.GONE);
                progressDialog.cancel();
            }
            catch (Exception e)
            {e.printStackTrace();}

        }
    }

    public void receivedSms(String message) {
        try
        {
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


    public void getResponseDialog(String response_message)
    {
        final ResponseDialog responseDialog1= new ResponseDialog(getApplicationContext(),this);
        responseDialog1.showResponseDialog(1,
                getString(R.string.message),
                null,null,null,null,
                response_message,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }

}
