package com.mom.app.retail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mom.app.retail.Request.CheckCustomerOTPValidationRequest;
import com.mom.app.retail.Request.CustomerAuthenticationMobileRequest;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.NetworkInterface;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends Activity  {

    public Button btn_login ,btnSubmitTPIN, btnSubmitDevicePin,btnCancelTPIN;
    private ImageView imageView;
    private EditText etMobNo,etMpin ,etOTP ,et_TPin ,et_DevicePinInsert,et_ConfirmDevicePinInsert;
    private TextView tvLoginText ,tvForgotPassword,tvException,tvOtpTimer,tvLoginDiffAccount,tvmadeindia;
    public static final String PREFS_NAME = "MyApp_Settings";
    private String string_Login_mobile_number,string_m_pin;
    InputStream instream;
    String a;
    String message;
    Map<String,Integer> mapGift;
    String responseBody,output;
  //  private ProgressBar progressBar;
    private SQLiteAdapter sql;
    private static LoginActivity inst;
    ProgressDialog progressDialog;
    MyCountDownTimer myCountDownTimer;
    private final long startTime = 180 * 1000;
    private final long interval = 1 * 1000;
    long customerOtpLogId;
    private EditText etPin1,etPin2,etPin3,etPin4,etPin5,etPin6;
    private LinearLayout layout6DigitPin;
    String s6DevicePin;
    private static int PROGRESS_DIALOG_TIME_OUT = 3000;
    ComProgressDialog comProgressDialog;
    MediaPlayer mp = new MediaPlayer();

    protected static LoginActivity instance() {
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
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        sql = new SQLiteAdapter(LoginActivity.this);

        comProgressDialog = new ComProgressDialog(this,LoginActivity.this);

        Typeface Light = Typeface.createFromAsset(getApplicationContext().getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(getApplicationContext().getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(getApplicationContext().getAssets(),String_url.OpenSans_Semibold);
        tvLoginText=(TextView)findViewById(R.id.tvLoginText);tvLoginText.setTypeface(Light);

//        progressBar=(ProgressBar)findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.INVISIBLE);


        etMobNo             = (EditText)findViewById(R.id.et_mobileno);
        etMpin              = (EditText)findViewById(R.id.et_m_pin);
        etOTP               = (EditText)findViewById(R.id.et_otp);
        et_TPin             = (EditText)findViewById(R.id.et_LoginTPin);

        tvForgotPassword    = (TextView)findViewById(R.id.tv_forgotPassword);
        tvException         = (TextView)findViewById(R.id.tvexception);
        et_DevicePinInsert  = (EditText)findViewById(R.id.et_DevicePinInsert);
        tvOtpTimer          = (TextView)findViewById(R.id.tvOtpTimer);
        tvLoginDiffAccount  = (TextView)findViewById(R.id.tvLoginDiffAccount);
        layout6DigitPin     = (LinearLayout)findViewById(R.id.layout6DigitPin);
        etPin1              = (EditText)findViewById(R.id.et_Pin1);
        etPin2              = (EditText)findViewById(R.id.et_Pin2);
        etPin3              = (EditText)findViewById(R.id.et_Pin3);
        etPin4              = (EditText)findViewById(R.id.et_Pin4);
        etPin5              = (EditText)findViewById(R.id.et_Pin5);
        etPin6              = (EditText)findViewById(R.id.et_Pin6);
        et_ConfirmDevicePinInsert=(EditText)findViewById(R.id.et_ConfirmDevicePinInsert);
        tvmadeindia=(TextView)findViewById(R.id.tvmadeindia);


        tvException.setTypeface(Light);

        tvLoginDiffAccount.setTypeface(Light);
        etMobNo.setTypeface(Light);
        etMpin.setTypeface(Light);
        tvLoginText.setTypeface(Light);
        etOTP.setTypeface(Light);
        et_TPin.setTypeface(Light);

        et_DevicePinInsert.setTypeface(Light);
        tvForgotPassword.setTypeface(Light);

        tvOtpTimer.setTypeface(Light);
        et_ConfirmDevicePinInsert.setTypeface(Light);
       // etMobNo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        tvmadeindia.setTypeface(Light);




        etPin1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub


            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                if(etPin1.getText().toString().length()==1)
                {

                    etPin2.requestFocus();


                }
                else
                {

                }

            }

        });
        etPin2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    if(etPin2.getText().length()==0)
                    {
                        etPin1.requestFocus();
                    }
                    if(etPin5.getText().length()==1)
                    {
                        etPin5.setText(null);
                    }
                    if(etPin4.getText().length()==1)
                    {
                        etPin4.setText(null);
                    }
                    if(etPin3.getText().length()==1)
                    {
                        etPin3.setText(null);
                    }


                }
                return false;
            }
        });
        etPin3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    if(etPin3.getText().length()==0)
                    {
                        etPin2.requestFocus();
                    }
                    if(etPin5.getText().length()==1)
                    {
                        etPin5.setText(null);
                    }
                    if(etPin4.getText().length()==1)
                    {
                        etPin4.setText(null);
                    }
                }
                return false;
            }
        });
        etPin4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    if(etPin4.getText().length()==0)
                    {
                        etPin3.requestFocus();
                    }
                    if(etPin5.getText().length()==1)
                    {
                        etPin5.setText(null);
                    }


                }
                return false;
            }
        });
        etPin5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace

                    if(etPin5.getText().length()==0)
                    {
                        etPin4.requestFocus();
                    }


                }
                return false;
            }
        });
        etPin6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    if(etPin6.getText().length()==0)
                    {
                        etPin5.requestFocus();
                    }
                }
                return false;
            }
        });
        etPin2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub


            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(etPin2.getText().toString().length()==1)
                {
                    etPin3.requestFocus();

                }
                if(etPin2.getText().toString().length()==0)
                {
                    etPin1.requestFocus();
                }
            }

        });
        etPin3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {

                // TODO Auto-generated method stub

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(etPin3.getText().toString().length()==1)     //size as per your requirement
                {
                    etPin4.requestFocus();


                }
                if(etPin3.getText().toString().length()==0)
                {
                    etPin2.requestFocus();

                }
            }

        });
        etPin4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(etPin4.getText().toString().length()==1)     //size as per your requirement
                {
                    etPin5.requestFocus();

                }

                if(etPin4.getText().toString().length()==0)
                {
                    etPin3.requestFocus();

                }
            }

        });
        etPin5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub


            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(etPin5.getText().toString().length()==1)     //size as per your requirement
                {
                    etPin6.requestFocus();

                }
                if(etPin5.getText().toString().length()==0)
                {
                    etPin4.requestFocus();

                }
            }

        });
        etPin6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub



            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub





                if(etPin6.getText().toString().length()==1)     //size as per your requirement
                {

                    String Pin= etPin1.getText().toString().concat(etPin2.getText().toString()).concat(etPin3.getText().toString())
                            .concat(etPin4.getText().toString()).concat(etPin5.getText().toString()).concat(etPin6.getText().toString());


                    DevicePinAuthentication(Pin);


                }
                if(etPin6.getText().toString().length()==0)
                {
                    etPin5.requestFocus();

                }
            }


        });



        etMpin.setImeActionLabel("Done", EditorInfo.IME_ACTION_DONE);
        etMpin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){

                }
                return false;
            }
        });

        progressDialog= new ProgressDialog(LoginActivity.this);

        myCountDownTimer= new MyCountDownTimer(startTime,interval);


        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgotMPIN.class);
                startActivity(i);
                finish();
            }
        });



        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_login.setText(R.string.Login);
        btn_login.setTypeface(Normal);

        btnSubmitTPIN = (Button) findViewById(R.id.btnSubmitTPIN);
        btnSubmitTPIN.setText(R.string.submit);
        btnSubmitTPIN.setTypeface(Normal);

        btnCancelTPIN=(Button)findViewById(R.id.btnCancelTPIN);
        btnCancelTPIN.setText(R.string.cancel);
        btnCancelTPIN.setTypeface(Normal);
        btnSubmitDevicePin= (Button) findViewById(R.id.btnSubmitDevicePIN);
        btnSubmitDevicePin.setText(R.string.submit);
        btnSubmitDevicePin.setTypeface(Normal);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                NetworkConnection networkConnection = new NetworkConnection(getApplicationContext());
                boolean isNetworkAvailable = networkConnection.isNetworkAvailable();
                if (isNetworkAvailable) {

                    try {


                        validate();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    NetworkConnection.ExitAppDialog(LoginActivity.this);

                }


            }
        });

        btnSubmitTPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnSubmitTPIN.setEnabled(false);

                String sTpin=et_TPin.getText().toString();
                String sOTP=etOTP.getText().toString();

                if(sOTP.length()<6)
                {
                    getResponseDialog(getString(R.string.errorOTPlength));
                }
                else if(sTpin.length()<8)
                {
                    getResponseDialog(getString(R.string.errorTpinlength));

                }
                else
                {
                    btn_login.setEnabled(true);

                    CheckCustomerOTPValidation checkCustomerOTPValidation= new CheckCustomerOTPValidation();
                    checkCustomerOTPValidation.execute();
//                    myCountDownTimer.cancel();
//                    tvOtpTimer.setText("");
                }

            }
        });

        btnCancelTPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                etMobNo.setText(null);
                etMpin.setText(null);
                etOTP.setVisibility(View.GONE);
                et_TPin.setVisibility(View.GONE);
                btnSubmitTPIN.setVisibility(View.GONE);
                btnCancelTPIN.setVisibility(View.GONE);

                etMobNo.setVisibility(View.VISIBLE);
                etMpin.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.VISIBLE);
                tvForgotPassword.setVisibility(View.VISIBLE);
                myCountDownTimer.cancel();
                tvOtpTimer.setText(null);
                btn_login.setEnabled(true);

            }
        });


        btnSubmitDevicePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitDevicePin.setEnabled(false);

                String sDevicePinInsert         =et_DevicePinInsert.getText().toString();
                String sConfirmDevicePinInsert  =et_ConfirmDevicePinInsert.getText().toString();

                if(sDevicePinInsert.length()<6 || sConfirmDevicePinInsert.length()<6)
                {
                   getResponseDialog(getString(R.string.errorDevicePinLength));
                }
                else if(!sDevicePinInsert.equals(sConfirmDevicePinInsert))
                {
                    getResponseDialog(getString(R.string.pinDoesNotMatch));
                }
                else {
                    CustomerMacAuthenticationInsert customerMacAuthenticationInsert = new CustomerMacAuthenticationInsert();
                    customerMacAuthenticationInsert.execute();
                }


            }
        });
        tvLoginDiffAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             final ConfrimRechargeDialog confrimRechargeDialog= new ConfrimRechargeDialog(LoginActivity.this,LoginActivity.this);
                confrimRechargeDialog.showDialog(1,
                getString(R.string.logout),
                getString(R.string.logout_question),
                null, null, null, null, null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confrimRechargeDialog.dialog.dismiss();
                        sql.openToReadLoginDataBase();
                        sql.deleteLoginTable();
                        sql.openToReadNotificationDataBase();
                        sql.deleteNotificationTable();
                        sql.close();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confrimRechargeDialog.dialog.dismiss();
                    }
                }

        );



            }
        });


        sql.openToReadLoginDataBase();
        String sDevicePin = sql.getDevicePin();



///RELOGIN

        if(sDevicePin!=null){

            // show Device Pin Layout //
            layout6DigitPin.setVisibility(View.VISIBLE);
            etPin1.requestFocus();

            tvException.setText(getString(R.string.pleaseEnterDevicePin));
            tvLoginDiffAccount.setVisibility(View.VISIBLE);
            tvmadeindia.setVisibility(View.VISIBLE);
            tvForgotPassword.setVisibility(View.GONE);
            etMobNo.setVisibility(View.GONE);
            etMpin.setVisibility(View.GONE);
            btn_login.setVisibility(View.GONE);
            sql= new SQLiteAdapter(LoginActivity.this);
            sql.openToReadLoginDataBase();

            sql.deleteBalance();
            sql.close();
            etPin1.requestFocus();

        }

    }

    public boolean validate() throws UnsupportedEncodingException {
        string_Login_mobile_number=etMobNo.getText().toString();
        string_m_pin=etMpin.getText().toString();



        if(string_Login_mobile_number.length()==0)
        {
            getResponseDialog(getString(R.string.error_customer_id_required));
            return false;
        }

        else if(string_Login_mobile_number.length()<12)
        {
            getResponseDialog(getString(R.string.error_customer_id_length));
            return false;
        }
        else if(string_m_pin.length()==0)
        {
            getResponseDialog(getString(R.string.error_password_required));
            return false;
        }
        else if(string_m_pin.length()<8)
        {
            getResponseDialog(getString(R.string.error_password_length));
            return false;
        }
        else {

            btn_login.setEnabled(false);
            CustomerAuthenticationMobileValidation customerAuthenticationMobileValidation= new CustomerAuthenticationMobileValidation();
            customerAuthenticationMobileValidation.execute();
           // progressBar.setVisibility(View.VISIBLE);

            progressDialog.setMessage("Authenticating....");
            progressDialog.setCancelable(false);

            progressDialog.show();



            return true;
        }
    }

    private void DevicePinAuthentication(String pin) {


        NetworkConnection networkConnection = new NetworkConnection(getApplicationContext());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();
        if (isNetworkAvailable) {

            try {

                Encryption encryption = new Encryption(LoginActivity.this);
                sql = new SQLiteAdapter(LoginActivity.this);
                sql.openToReadLoginDataBase();
                String sDevicePin       = null;
                try {
                    sDevicePin = encryption.SHA1(pin);
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf    = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
                String sExpiryDate      = sql.getExpiryDate();
                int deviceLength        = pin.length();

                if(deviceLength==6) {


                    Date strDate = null;
                    try {
                        strDate = sdf.parse(sExpiryDate);
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    assert strDate != null;
                    if (new Date().before(strDate)) {

                        if (sql.getDevicePin().equals(sDevicePin)) {
                            //progressBar.setVisibility(View.VISIBLE);

                            progressDialog.setMessage("Authenticating....");
                            progressDialog.setCancelable(false);

                            progressDialog.show();

                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);


                            //show dashboard
                            CheckCustomerMasterPinValidation checkCustomerMasterPinValidation= new CheckCustomerMasterPinValidation();
                            checkCustomerMasterPinValidation.execute();

                        } else {

                            //Error Invalid Device Pin
                            final ResponseDialog responseDialog= new ResponseDialog(LoginActivity.this,LoginActivity.this);
                            responseDialog.showResponseDialog(1,
                                    getString(R.string.response),
                                    null, null, null, null,
                                    "Invalid Device Pin",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            responseDialog.dialog.dismiss();
                                            etPin1.setText(null);
                                            etPin2.setText(null);
                                            etPin3.setText(null);
                                            etPin4.setText(null);
                                            etPin5.setText(null);
                                            etPin6.setText(null);
                                            etPin1.requestFocus();

                                        }
                                    }


                            );

                        }

                    } else {

                        // Error Expiry Date  expires and reset Device Pin
                        final ResponseDialog responseDialog= new ResponseDialog(LoginActivity.this,LoginActivity.this);
                        responseDialog.showResponseDialog(1,
                                getString(R.string.message),
                                null, null, null, null,
                                getString(R.string.expiredDevicePin),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sql.deleteLoginTable();

                                        Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        responseDialog.dialog.dismiss();
                                    }
                                }


                        );

                    }


                    sql.close();

                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            NetworkConnection.ExitAppDialog(LoginActivity.this);

        }


    }

    private class CustomerAuthenticationMobileValidation extends AsyncTask<Integer,String,String>{

        String sResponse,userName,password;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userName=etMobNo.getText().toString();
            password=etMpin.getText().toString();


        }

        @Override
        protected String doInBackground(Integer... params) {

            Gson gson = new GsonBuilder().create();
            CustomerAuthenticationMobileRequest customerAuthenticationMobileRequest =new CustomerAuthenticationMobileRequest();
            Type type = new TypeToken<CustomerAuthenticationMobileRequest>() {}.getType();
            Encryption encryption= new Encryption(getApplicationContext());
            try {

                customerAuthenticationMobileRequest.setUserName(userName);
                customerAuthenticationMobileRequest.setPassword(encryption.SHA1(password));
                customerAuthenticationMobileRequest.setServiceChannelId(String_url.channelId);
                String json = gson.toJson(customerAuthenticationMobileRequest, type);

                Log.d("json",json);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.CustomerAuthenticationMobileValidation);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.requestCustomerAuthentication, json));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httppost.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                httppost.setHeader("Pragma", "no-cache");
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                sResponse = EntityUtils.toString(entity);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return sResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                try {


                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<CustomerAuthenticationMobileResponse>>() {}.getType();
                    ResponseBase<CustomerAuthenticationMobileResponse> responseBase = gson.fromJson(s, type);
                    CustomerAuthenticationMobileResponse customerAuthenticationMobileResponse = responseBase.responseData;


                    if (responseBase.id == 1) {


                       // progressBar.setVisibility(View.GONE);

                        progressDialog.setMessage("Generating OTP please wait...");


                        progressDialog.show();



                        long customerLoginId                = customerAuthenticationMobileResponse.customerLoginId;
                        long mobileNumber                   = customerAuthenticationMobileResponse.mobileNumber;
                        int customerAccountStatus           = customerAuthenticationMobileResponse.customerAccountStatus;
                        double customerMainAccountBalance    = customerAuthenticationMobileResponse.customerMainAccountBalance;
                        long customerWalletID               = customerAuthenticationMobileResponse.customerWalletId;
                        String customerRole                 = customerAuthenticationMobileResponse.customerRole;
                        String customerRoleName             = customerAuthenticationMobileResponse.customerRoleName;
                        int customerRoleID                  = customerAuthenticationMobileResponse.customerRoleId;
                        long customerID                     = customerAuthenticationMobileResponse.customerId;
                        long customerAuthenticationID       = customerAuthenticationMobileResponse.customerAuthenticationId;
                        String firstName                    = customerAuthenticationMobileResponse.firstName;
                        String lastName                     = customerAuthenticationMobileResponse.lastName;
                        customerOtpLogId                    = customerAuthenticationMobileResponse.customerOtpLogId;


                        SharedPreferences sharedPreferences1 = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        editor.putString(String_url.CustomerCode, userName);
                        editor.putString(String_url.Image, string_Login_mobile_number);
                        editor.putString(String_url.Authkey, String_url.auth_key1);
                        editor.putLong(String_url.CustomerLoginId, customerLoginId);
                        editor.putLong(String_url.MobileNumber, mobileNumber);
                        editor.putInt(String_url.CustomerAccountStatus, customerAccountStatus);



                        sql.openToWriteBalanceDataBase();
                        sql.insertBalance(customerMainAccountBalance);
                        sql.close();
                        DecimalFormat precision = new DecimalFormat("0.00");

                        editor.putLong(String_url.CustomerWalletID, customerWalletID);
                        editor.putString(String_url.CustomerRole, customerRole);
                        editor.putString(String_url.CustomerRoleName, customerRoleName);
                        editor.putInt(String_url.CustomerRoleID, customerRoleID);
                        editor.putLong(String_url.CustomerID, customerID);
                        editor.putLong(String_url.CustomerAuthenticationID, customerAuthenticationID);
                        editor.putString(String_url.String_Mpin, string_m_pin);
                        editor.putString(String_url.firstName, firstName);
                        editor.putString(String_url.lastName, lastName);
                        editor.apply();



                        myCountDownTimer.start();


                        etOTP.setVisibility(View.VISIBLE);
                        et_TPin.setVisibility(View.VISIBLE);
                        btnSubmitTPIN.setVisibility(View.VISIBLE);
                        btnCancelTPIN.setVisibility(View.VISIBLE);

                        etMobNo.setVisibility(View.GONE);
                        etMpin.setVisibility(View.GONE);
                        btn_login.setVisibility(View.GONE);
                        tvForgotPassword.setVisibility(View.GONE);



                    } else {

                        //progressBar.setVisibility(View.GONE);
                   //     getResponseDialog(responseBase.message);

                        final ResponseDialog responseDialog1= new ResponseDialog(LoginActivity.this,LoginActivity.this);
                        responseDialog1.showResponseDialog(1,
                                getString(R.string.message),
                                null,null,null,null,
                                responseBase.message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btn_login.setEnabled(true);
                                        btnSubmitTPIN.setEnabled(true);
                                        btnCancelTPIN.setEnabled(true);
                                        btnSubmitDevicePin.setEnabled(true);
                                        responseDialog1.dialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                }


                        );

                    }
                } catch (JsonSyntaxException jse) {

                    jse.printStackTrace();
                    getResponseDialog(getString(R.string.service_unavailable));
                  //  progressBar.setVisibility(View.GONE);
                    progressDialog.cancel();
                }


            }
            else {
               // progressBar.setVisibility(View.GONE);
                btn_login.setEnabled(true);
                getResponseDialog(getString(R.string.errorsomeIssueOccured));
                progressDialog.cancel();
            }


        }
    }

    private class CheckCustomerOTPValidation extends AsyncTask<Integer,String,String>{

        String sResponse,sTpin,sOTP;
        long sCustomerID;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sTpin=et_TPin.getText().toString();
            sOTP=etOTP.getText().toString();
            SharedPreferences sharedPreferences1 = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            sCustomerID=sharedPreferences1.getLong(String_url.CustomerID, 0);


           //progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
        }

        @Override
        protected String doInBackground(Integer... params) {

            Gson gson = new GsonBuilder().create();
            CheckCustomerOTPValidationRequest checkCustomerOTPValidationRequest =new CheckCustomerOTPValidationRequest();
            Type type = new TypeToken<CheckCustomerOTPValidationRequest>() {}.getType();
            Encryption encryption= new Encryption(getApplicationContext());

            try{

                checkCustomerOTPValidationRequest.setCustomerId(sCustomerID);
                checkCustomerOTPValidationRequest.setOtp(sOTP);
                checkCustomerOTPValidationRequest.setServiceChannelId(String_url.channelId);
                checkCustomerOTPValidationRequest.setTpin(encryption.SHA1(sTpin));
                checkCustomerOTPValidationRequest.setCustomerOtpLogId(customerOtpLogId);


                String json = gson.toJson(checkCustomerOTPValidationRequest, type);

                HttpClient httpclient   = new DefaultHttpClient();
                HttpPost httppost       = new HttpPost(String_url.CheckCustomerOTPValidation);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.checkCustomerOTPValidationRequest, json));
                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                sResponse = EntityUtils.toString(entity);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            return sResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if(s!=null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>() {}.getType();
                    ResponseBase responseBase = gson.fromJson(s, type);

                    if (responseBase.id == 1) {

                        String responseData = responseBase.responseData.toString();

                        et_DevicePinInsert.setVisibility(View.VISIBLE);
                        et_ConfirmDevicePinInsert.setVisibility(View.VISIBLE);
                        btnSubmitDevicePin.setVisibility(View.VISIBLE);
                        etOTP.setVisibility(View.GONE);
                        et_TPin.setVisibility(View.GONE);
                        btnSubmitTPIN.setVisibility(View.GONE);
                        btnSubmitTPIN.setEnabled(true);
                        btnCancelTPIN.setVisibility(View.GONE);
                        tvOtpTimer.setText(null);
                        btnCancelTPIN.setEnabled(true);
                        getResponseDialog(getString(R.string.DevicePin_Info));
                    } else {

                        final ResponseDialog responseDialog = new ResponseDialog(LoginActivity.this, LoginActivity.this);
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                responseBase.message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnSubmitTPIN.setEnabled(true);
                                        btnCancelTPIN.setEnabled(true);
                                        responseDialog.dialog.dismiss();
                                    }
                                }
                        );
                        myCountDownTimer.cancel();
                        tvOtpTimer.setText("Resend OTP!");
                        tvOtpTimer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                etOTP.setText(null);
                                et_TPin.setText(null);

                                CustomerAuthenticationMobileValidation customerAuthenticationMobileValidation= new CustomerAuthenticationMobileValidation();
                                customerAuthenticationMobileValidation.execute();

                            }
                        });

                    }
                    myCountDownTimer.cancel();
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

    private class CustomerMacAuthenticationInsert extends AsyncTask<Integer,String,String>{

        String sCustomerCode,sCustomerMacAuthResponse;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SharedPreferences sharedPreferences1 = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            sCustomerCode=sharedPreferences1.getString(String_url.CustomerCode,"");
            //progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();

        }

        @Override
        protected String doInBackground(Integer... params) {

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.CustomerMacAuthenticationInsert);
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
                sCustomerMacAuthResponse = EntityUtils.toString(entity);

                String macaddress=getMacAddr();
                Log.d("macaddress",macaddress);
                Log.d("macaddress",sCustomerMacAuthResponse);


            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return sCustomerMacAuthResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                if (s != null) {

                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>() {}.getType();
                    ResponseBase responseBase = gson.fromJson(s, type);

                    if (responseBase.id == 1) {

                        String responseData = responseBase.responseData.toString();

                        String sDevicePin = null;
                        String sCustomerCode = etMobNo.getText().toString();
                        String sMPIN = etMpin.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String sExpiryDate;
                        Encryption encryption = new Encryption(LoginActivity.this);
                        try {
                            sDevicePin = encryption.SHA1(et_DevicePinInsert.getText().toString());
                        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Calendar c = Calendar.getInstance();
                        String sCreationDate = sdf.format(c.getTime());

                        try {
                            c.setTime(sdf.parse(sCreationDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 90);
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        sExpiryDate = sdf1.format(c.getTime());



                        sql = new SQLiteAdapter(LoginActivity.this);
                        sql.openToWriteLoginDataBase();
                        sql.insertLoginInfo(sCustomerCode, sMPIN, sDevicePin, sCreationDate, sExpiryDate);

                        sql.openToReadLoginDataBase();
                        if (sql.getDevicePin() != null) {

                            soundEffect();
                            Intent intent = new Intent(LoginActivity.this, Tabhost_activity.class);
                            startActivity(intent);
                            finish();
                        }
                        sql.close();

                        GetNotification getNotification = new GetNotification(getApplicationContext());
                        GetNotification.GetCustomerNotification getCustomerNotification = getNotification.new GetCustomerNotification();
                        getCustomerNotification.execute();


                    } else {

                        final ResponseDialog responseDialog = new ResponseDialog(LoginActivity.this, LoginActivity.this);
                        responseDialog.showResponseDialog(1,
                                getString(R.string.response),
                                null, null, null, null,
                                responseBase.message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnSubmitDevicePin.setEnabled(true);
                                        responseDialog.dialog.dismiss();
                                    }
                                }


                        );

                    }
                }
                //progressBar.setVisibility(View.GONE);
                comProgressDialog.cancelProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class CheckCustomerMasterPinValidation extends AsyncTask<Integer,String,String>{

        String sMasterPinValidationResponse,sCustomerCode;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sql= new SQLiteAdapter(LoginActivity.this);
            sql.openToReadLoginDataBase();
            sCustomerCode=sql.getCustomerCode();
            sql.close();

        }

        @Override
        protected String doInBackground(Integer... params) {

            try {



                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.CheckCustomerMasterPinValidation);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.customerCode, sCustomerCode));
                nameValuePairs.add(new BasicNameValuePair(String_url.macId, getMacAddr()));


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



            if(s!=null) {

                try {

                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<CustomerAuthenticationMobileResponse>>() {}.getType();
                    ResponseBase<CustomerAuthenticationMobileResponse> responseBase = gson.fromJson(s, type);
                    CustomerAuthenticationMobileResponse customerAuthenticationMobileResponse = responseBase.responseData;


                    if (responseBase.id == 1) {

                        long customerLoginId = customerAuthenticationMobileResponse.customerLoginId;
                        long mobileNumber = customerAuthenticationMobileResponse.mobileNumber;
                        int customerAccountStatus = customerAuthenticationMobileResponse.customerAccountStatus;
                        double customerMainAccountBalance = customerAuthenticationMobileResponse.customerMainAccountBalance;
                        long customerWalletID = customerAuthenticationMobileResponse.customerWalletId;
                        String customerRole = customerAuthenticationMobileResponse.customerRole;
                        String customerRoleName = customerAuthenticationMobileResponse.customerRoleName;
                        int customerRoleID = customerAuthenticationMobileResponse.customerRoleId;
                        long customerID = customerAuthenticationMobileResponse.customerId;
                        long customerAuthenticationID = customerAuthenticationMobileResponse.customerAuthenticationId;
                        String firstName = customerAuthenticationMobileResponse.firstName;
                        String lastName = customerAuthenticationMobileResponse.lastName;
                        float customerUnclearBalance=customerAuthenticationMobileResponse.customerUnclearBalance;
                        SharedPreferences sharedPreferences1 = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        editor.putString(String_url.Image, sCustomerCode);
                        editor.putString(String_url.Authkey, String_url.auth_key1);
                        editor.putLong(String_url.CustomerLoginId, customerLoginId);
                        editor.putLong(String_url.MobileNumber, mobileNumber);
                        editor.putInt(String_url.CustomerAccountStatus, customerAccountStatus);

                        sql.openToWriteBalanceDataBase();
                        sql.insertBalance(customerMainAccountBalance);
                        sql.close();
                        DecimalFormat precision = new DecimalFormat("0.00");
                        editor.putLong(String_url.CustomerWalletID, customerWalletID);
                        editor.putString(String_url.CustomerRole, customerRole);
                        editor.putString(String_url.CustomerRoleName, customerRoleName);
                        editor.putInt(String_url.CustomerRoleID, customerRoleID);
                        editor.putLong(String_url.CustomerID, customerID);
                        editor.putLong(String_url.CustomerAuthenticationID, customerAuthenticationID);
                        editor.putString(String_url.String_Mpin, string_m_pin);
                        editor.putString(String_url.firstName, firstName);
                        editor.putString(String_url.lastName, lastName);
                        editor.apply();




                        soundEffect();
                        Intent intent = new Intent(LoginActivity.this, Tabhost_activity.class);
                        startActivity(intent);
                        finish();

                        GetNotification getNotification = new GetNotification(getApplicationContext());
                        GetNotification.GetCustomerNotification getCustomerNotification = getNotification.new GetCustomerNotification();
                        getCustomerNotification.execute();


                    } else {
                        getResponseDialog(responseBase.message);
                        final ResponseDialog responseDialog = new ResponseDialog(LoginActivity.this, LoginActivity.this);
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
                } catch (JsonSyntaxException jse) {

                    jse.printStackTrace();
                    getResponseDialog(getString(R.string.service_unavailable));

                }
            }
            else {
                getResponseDialog(getString(R.string.service_unavailable));
            }
            //progressBar.setVisibility(View.GONE);

            progressDialog.cancel();

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

    private class MyCountDownTimer extends CountDownTimer{
        final String FORMAT = "%02d:%02d";
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {





            String time=""+String.format(Locale.getDefault(),FORMAT,

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
                    CustomerAuthenticationMobileValidation customerAuthenticationMobileValidation= new CustomerAuthenticationMobileValidation();
                    customerAuthenticationMobileValidation.execute();

                }
            });
        }
    }

    public void getResponseDialog(String response_message) {
        final ResponseDialog responseDialog1= new ResponseDialog(LoginActivity.this,LoginActivity.this);
        responseDialog1.showResponseDialog(1,
                getString(R.string.message),
                null,null,null,null,
                response_message,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_login.setEnabled(true);
                        btnSubmitTPIN.setEnabled(true);
                        btnCancelTPIN.setEnabled(true);
                        btnSubmitDevicePin.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }

    @Override
    public void onBackPressed() {


        final ConfrimRechargeDialog confrimRechargeDialog= new ConfrimRechargeDialog(LoginActivity.this,LoginActivity.this);
        confrimRechargeDialog.showDialog(1,
                getString(R.string.exit),
                getString(R.string.exit_question),
                null, null, null, null, null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confrimRechargeDialog.dialog.dismiss();

                        finish();

                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confrimRechargeDialog.dialog.dismiss();
                    }
                }

        );



    }



    public void soundEffect()
    {
        if(mp.isPlaying())
        {
            mp.stop();
        }
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getAssets().openFd("sound1.mp3");
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}













