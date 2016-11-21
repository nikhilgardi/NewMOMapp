package com.mom.app.retail;

import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;

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
import org.json.JSONObject;


import android.support.v4.app.DialogFragment;

import com.mom.app.retail.Response.GetBankNameResponse;
import com.mom.app.retail.Response.GetPaymentModeRespose;
import com.mom.app.retail.Response.PaymentRequestResult;
import com.mom.app.retail.Response.ResponseBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Payment_Request extends Fragment {

    private EditText et_chequeno, et_branch_name, et_customer_ifsc_code, et_depositedate, et_amount_payment, et_enter_remark;
    private TextView tv_PaymentRequest, tvExepPaymentRequest,tv_totalcount;
    private Button btnpaymentrequest, btnpaymentCancel;
    Spinner spinnerBankMode, spinnerPaymentMode;

    ImageView iv_payment_request;
    Calendar cal;
    String strBankmode,strPaymentmode,BankName,PaymentModeName;
    long CustomerLoginId;
    int ans=0;
   
    public static final String PREFS_NAME = "MyApp_Settings";

//    HashMap<Integer,String> hm;
//    Map<String, Integer> myMap;

    // HashMap<String,String> spinnerMap;

    HashMap<String, Integer> hashMapBranch;
    List<String> branchList;
    int payment_id;
    String str_chequeno,str_branch_name,str_ifsc_code,str_amount,str_remarks,str_date;
    private ProgressBar progressBar;
    Typeface SemiBold,Light,Normal;
    ComProgressDialog comProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_payment__request, container, false);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        NetworkConnection networkConnection=new NetworkConnection(getActivity());
        boolean isNetworkAvailable=networkConnection.isNetworkAvailable();

        if(!isNetworkAvailable)
        {
            NetworkConnection.ExitAppDialog(getActivity());
        }
        else
        {

            comProgressDialog = new ComProgressDialog(getActivity(),getActivity());

            progressBar=(ProgressBar)v.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
          
            iv_payment_request = (ImageView) v.findViewById(R.id.iv_payment_request);
            iv_payment_request.setBackgroundResource(R.drawable.allscreen);
            tv_PaymentRequest = (TextView) v.findViewById(R.id.tv_PaymentRequest);
            tvExepPaymentRequest = (TextView) v.findViewById(R.id.tvExepPaymentRequest);
            // tv_totalcount = (TextView) v.findViewById(R.id.tv_totalcount);
            et_chequeno = (EditText) v.findViewById(R.id.et_chequeno);
            et_branch_name = (EditText) v.findViewById(R.id.et_branch_name);
            et_customer_ifsc_code = (EditText) v.findViewById(R.id.et_customer_ifsc_code);
            et_depositedate = (EditText) v.findViewById(R.id.et_depositedate);
            et_amount_payment = (EditText) v.findViewById(R.id.et_amount_payment);
            et_enter_remark = (EditText) v.findViewById(R.id.et_enter_remark);
            btnpaymentrequest = (Button) v.findViewById(R.id.btnpaymentrequest);
            btnpaymentCancel = (Button) v.findViewById(R.id.btnpaymentCancel);
            spinnerBankMode = (Spinner) v.findViewById(R.id.spinnerBankMode);
            spinnerPaymentMode = (Spinner) v.findViewById(R.id.spinnerPaymentMode);
            et_enter_remark.setImeOptions(EditorInfo.IME_ACTION_DONE);


            et_enter_remark.setScroller(new Scroller(getActivity()));
            et_enter_remark.setVerticalScrollBarEnabled(true);

            et_enter_remark.setHorizontallyScrolling(false);
            et_enter_remark.setMaxLines(Integer.MAX_VALUE);


            SharedPreferences sharedPreferences=getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            CustomerLoginId=sharedPreferences.getLong(String_url.CustomerID,0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
        }


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

//        Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
//        Thin = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Normal);
//        Condensed = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Semibold);

        Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);


        tv_PaymentRequest.setTypeface(Light);
        tvExepPaymentRequest.setTypeface(Light);
        et_chequeno.setTypeface(Light);
        et_branch_name.setTypeface(Light);
        et_customer_ifsc_code.setTypeface(Light);
        et_depositedate.setTypeface(Light);
        et_amount_payment.setTypeface(Light);
        et_enter_remark.setTypeface(Light);




        btnpaymentrequest.setTypeface(Normal);
        btnpaymentCancel.setTypeface(Normal);


        cal=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/yyyy");
        String formateDate=simpleDateFormat.format(cal.getTime());
        et_depositedate.setHint(formateDate);
        et_depositedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new DatePickerFragment();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), String_url.date_picker);
            }
        });


        btnpaymentrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnpaymentrequest.setEnabled(false);
                validate();


            }
        });
        btnpaymentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                Sendmoney_listview sendmoney_listview=new Sendmoney_listview();
                fragmentTransaction.replace(R.id.fragmentcontent,sendmoney_listview).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });





        GetBankName getBankName= new GetBankName();
        getBankName.execute();


        GetPaymentMode getPaymentMode=new GetPaymentMode();
        getPaymentMode.execute();

    }

    private class GetPaymentMode extends AsyncTask<String,Integer,String>{


        Payment_Request_payment_mode payment_request_payment_mode;
        JSONObject json = null;
        String PaymentMode_response;

        @Override
        protected String doInBackground(String... params) {

            try
            {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetPaymentMode);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));


                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                PaymentMode_response= EntityUtils.toString(entity);


                hashMapBranch = new HashMap<String, Integer>();
                branchList  = new ArrayList<String>();
                branchList.add(getResources().getString(R.string.select_payment_mode));








            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return PaymentMode_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {

                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<ResponseBase<GetPaymentModeRespose[]>>() {
                }.getType();
                ResponseBase<GetPaymentModeRespose[]> responseBase = gson.fromJson(PaymentMode_response, type);
                int id = responseBase.id;

                if (responseBase.responseData != null) {
                    for (GetPaymentModeRespose getPaymentModeRespose : responseBase.responseData) {


                        PaymentModeName = getPaymentModeRespose.PaymentModeName;

                        if(PaymentModeName!=null) {


                            int id1 = getPaymentModeRespose.PaymentModeID;


                            hashMapBranch.put(PaymentModeName, id1);
                            branchList.add(PaymentModeName);
                        }
                    }
                }

                payment_request_payment_mode = new Payment_Request_payment_mode();
                payment_request_payment_mode.storeBranchList(String_url.payment_mode, hashMapBranch);
                payment_request_payment_mode.storeBranchListDetail(String_url.payment_id, branchList);

                List<String> branchNameResultList = payment_request_payment_mode.getBranchListDetail();



                // final Spinner mySpinner1 = (Spinner)getActivity().findViewById(R.id.spinner_dth);

                ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNameResultList) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        //  tfavv = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avvaiyar.ttf");
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(Light);
                        v.setTextColor(Color.parseColor(String_url.spinnerHintColor));
                        v.setTextSize(18);
                        v.setGravity(Gravity.CENTER);

                        return v;
                    }

                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(Light);
                        v.setTextColor(Color.parseColor(String_url.spinnerDropDownTextColor));
                        v.setTextSize(18);
                        v.setGravity(Gravity.CENTER);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                dataAdapter_res.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinnerPaymentMode.setAdapter(dataAdapter_res);
                spinnerPaymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        try {
                            payment_id = payment_request_payment_mode.getBranchList(spinnerPaymentMode.getSelectedItem().toString());

                        } catch (NullPointerException e) {
                            System.out.println("Exception" + e);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }


        }
    }

    private class GetBankName extends AsyncTask<String,Integer,String>{

        JSONObject json = null;
        String GetBankName_response;


        List<String> list = new ArrayList<String>();

        @Override
        protected String doInBackground(String... params) {
            try
            {


                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetBankName);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));


                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                GetBankName_response= EntityUtils.toString(entity);


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetBankName_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson=new GsonBuilder().create();
            Type type=new TypeToken<ResponseBase<GetBankNameResponse[]>>(){}.getType();
            ResponseBase<GetBankNameResponse[]> responseBase=gson.fromJson(GetBankName_response,type);
            list.add(getResources().getString(R.string.select_bank_mode));
            if(responseBase.responseData!=null)
            {
                for(GetBankNameResponse getBankNameResponse:responseBase.responseData)
                {
                    String BankName=getBankNameResponse.BankName;

                    list.add(BankName);
                }
            }


            ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list)
            {
                public View getView(int position, View convertView, ViewGroup parent) {
                    //  tfavv = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avvaiyar.ttf");
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(Light);
                    v.setTextColor(Color.parseColor(String_url.spinnerHintColor));
                    v.setTextSize(18);
                    v.setGravity(Gravity.CENTER);

                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(Light);
                    v.setTextColor(Color.parseColor(String_url.spinnerDropDownTextColor));
                    v.setTextSize(18); v.setGravity(Gravity.CENTER);
                    v.setPadding(15,15,15,15);
                    return v;
                }
            };
            dataAdapter_res.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerBankMode.setAdapter(dataAdapter_res);

        }
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        //  Toast.makeText(getActivity(),"hi",Toast.LENGTH_LONG).show();

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String_url String_url1=new String_url();




            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);


            Field mDatePickerField = null;
            try {
                mDatePickerField = dialog.getClass().getDeclaredField(String_url1.mDatePicker);
                mDatePickerField.setAccessible(true);
                DatePicker mDatePickerInstance = (DatePicker) mDatePickerField.get(dialog);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    //Toast.makeText(getActivity(),"in if",Toast.LENGTH_SHORT).show();
                    // (picker is a DatePicker)
                    calendar.add(Calendar.DATE, 0);
                    // mDatePickerInstance.setMinDate(calendar.getTimeInMillis());

                    dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() + 1000);

                    // Subtract 6 days from Calendar updated date
                    calendar.add(Calendar.DATE, -7);

                    // Set the Calendar new date as minimum date of date picker
                    dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                }
                else
                {
                    // Toast.makeText(getActivity(),"in else",Toast.LENGTH_SHORT).show();
                    final int minYear = calendar.get(Calendar.YEAR);
                    final int minMonth = calendar.get(Calendar.MONTH);
                    final int minDay = calendar.get(Calendar.DAY_OF_MONTH);

                    final int sevenday=minDay-7;


                    mDatePickerInstance.init(minYear, minMonth, minDay,
                            new DatePicker.OnDateChangedListener() {

                                public void onDateChanged(DatePicker view, int year,
                                                          int month, int day) {
                                    Calendar newDate = Calendar.getInstance();
                                    newDate.set(year, month, day);


                                    if (calendar.before(newDate)) {
                                        view.init(minYear, minMonth, minDay, this);

                                    }


                                }
                            });

                }


            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return dialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day){
            // Do something with the chosen date
            // TextView tv = (TextView) getActivity().findViewById(R.id.tv);

            // Create a Date variable/object with user chosen date
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(0);
//            cal.set(year, month, day, 0, 0, 0);
//            Date chosenDate = cal.getTime();
//
//            // Format the date using style and locale
//            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
//            String formattedDate = df.format(chosenDate);
//
//            // Display the chosen date to app interface
//            //   tv.setText(formattedDate);
//
//            //Toast.makeText(getActivity(),formattedDate,Toast.LENGTH_SHORT).show();
//            EditText et_depositedate = (EditText) getActivity().findViewById(R.id.et_depositedate);
//            et_depositedate.setHint(day+"/"+(month+1)+"/"+year);
            int mon=month+1;
           // String selected_date = mon + "/" + day + "/" + year;
            String selected_date = mon + "/" + day + "/" + year;
            EditText et_depositedate = (EditText) getActivity().findViewById(R.id.et_depositedate);
            et_depositedate.setHint(selected_date);


        }
    }

    public boolean validate() {
        //Toast.makeText(getActivity(),"payment_id"+payment_id,Toast.LENGTH_SHORT).show();
        int min_payment_amount=10;
        int max_payment_amount=15000;
        int min_ifsc_code=11;
        int min_cheque_no=1;
        int max_cheque_no=15;


        str_chequeno=et_chequeno.getText().toString();
        str_branch_name=et_branch_name.getText().toString();
        str_ifsc_code=et_customer_ifsc_code.getText().toString();
        str_amount=et_amount_payment.getText().toString();
        str_remarks=et_enter_remark.getText().toString();
        str_date=et_depositedate.getHint().toString();
        int payment_amount=0;
        int cheque_no=et_chequeno.getText().toString().length();

        CheckIFSCCodeBackground checkIFSCCodeBackground = new CheckIFSCCodeBackground();
        String ifsc_message = checkIFSCCodeBackground.doInBackground().toLowerCase();



        try {
            // mobile_number = Integer.parseInt(etMob.getText().toString());
            payment_amount = Integer.parseInt(et_amount_payment.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }


        strBankmode = spinnerBankMode.getSelectedItem().toString();
        strPaymentmode=spinnerPaymentMode.getSelectedItem().toString();




        if(strBankmode.equals(getResources().getString(R.string.select_bank_mode)))
        {
            getResponseDialog(getString(R.string.bank_mode));
            //tvExepPaymentRequest.setText(getString(R.string.bank_mode));
            return false;
        }
        if(strPaymentmode.equals(getResources().getString(R.string.select_payment_mode)))
        {
            getResponseDialog(getString(R.string.payment_mode));
            //tvExepPaymentRequest.setText(getString(R.string.payment_mode));
            return false;
        }
        if(str_chequeno.equals(""))
        {
            getResponseDialog(getString(R.string.transaction_cheque_no));
            // et_beneficiary_name.setError("Enter Beneficiary Name");
            //tvExepPaymentRequest.setText(getString(R.string.transaction_cheque_no));
            return false;
        }
        if(cheque_no<min_cheque_no||cheque_no>max_cheque_no)
        {
            getResponseDialog(getString(R.string.valid_cheque_no));
            // tvExepPaymentRequest.setText(getString(R.string.amount_length)+min_payment_amount+getString(R.string.and)+max_payment_amount);

            return false;
        }

        if(str_branch_name.equals(""))
        {
            getResponseDialog(getString(R.string.branch_name_req));
            // et_beneficiary_name.setError("Enter Beneficiary Name");
            //tvExepPaymentRequest.setText(getString(R.string.branch_name_req));
            return false;
        }

        if(str_ifsc_code.length()<min_ifsc_code||str_ifsc_code.length()>min_ifsc_code)
        {
            getResponseDialog(getString(R.string.customer_ifsc)+min_ifsc_code+getString(R.string.digits));
            //tvExepPaymentRequest.setText(getString(R.string.customer_ifsc)+min_ifsc_code+getString(R.string.digits));
            return false;
        }
        else if (!ifsc_message.equals("success")) {
            getResponseDialog(getString(R.string.valideifsc));
           // tvExepPaymentRequest.setText(getString(R.string.valideifsc));
            return false;
        }

        if(payment_amount<min_payment_amount||payment_amount>max_payment_amount)
        {
            getResponseDialog(getString(R.string.amount_length)+min_payment_amount+getString(R.string.and)+max_payment_amount);
           // tvExepPaymentRequest.setText(getString(R.string.amount_length)+min_payment_amount+getString(R.string.and)+max_payment_amount);

            return false;
        }
        if(str_remarks.equals(""))
        {
            getResponseDialog(getString(R.string.remark_req));
            //tvExepPaymentRequest.setText(getString(R.string.remark_req));
            return false;
        }

        else
        {

            tvExepPaymentRequest.setText("");


                final ConfrimRechargeDialog confrimRecharge= new ConfrimRechargeDialog(getActivity(),getActivity());
                confrimRecharge.showDialog(3,
                        getString(R.string.ConfirmPayment),
                        getString(R.string.bankName),
                        strBankmode,
                        getString(R.string.chequeno),
                        str_chequeno,
                        getString(R.string.amount),
                        String.valueOf(payment_amount),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PaymentRequest paymentRequest = new PaymentRequest();
                                paymentRequest.execute();
                                confrimRecharge.dialog.dismiss();
                                btnpaymentrequest.setEnabled(true);
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confrimRecharge.dialog.dismiss();
                                btnpaymentrequest.setEnabled(true);
                            }
                        }


                );



            return true;
        }

    }

    private class PaymentRequest extends AsyncTask<String,Integer,String>{

        int id;
        String message;
        String sTransactionId;
        String paymentrequest_response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.PaymentRequest);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(11);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerID, String.valueOf(CustomerLoginId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.requestedBy, String.valueOf(CustomerLoginId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.branchIFSCCode, str_ifsc_code));
                nameValuePairs.add(new BasicNameValuePair(String_url.bankRefNo, str_chequeno));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceChannelId, String.valueOf(String_url.str_servicechannelid)));
                nameValuePairs.add(new BasicNameValuePair(String_url.paymentModeId, String.valueOf(payment_id)));
                nameValuePairs.add(new BasicNameValuePair(String_url.LoadAmount, str_amount));
                nameValuePairs.add(new BasicNameValuePair(String_url.LoadCategoryID, String.valueOf(String_url.str_LoadCategoryID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.LoadDescription, str_remarks));
                nameValuePairs.add(new BasicNameValuePair(String_url.loadDepositDate, str_date));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                paymentrequest_response= EntityUtils.toString(entity);

                Log.d("payment_request",paymentrequest_response);


            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                //response=exception.toString();
            }
            return paymentrequest_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (s != null) {

                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<PaymentRequestResult>() {
                    }.getType();
                    PaymentRequestResult paymentRequestResult = gson.fromJson(paymentrequest_response, type);

                    sTransactionId = paymentRequestResult.responseData;
                    message = paymentRequestResult.message;



                    String oneLineMessage;

                    if (message.equals(getResources().getString(R.string.SUCCESS))) {


                        oneLineMessage = getResources().getString(R.string.success_request_id).concat(sTransactionId);

                    } else {

                        oneLineMessage = message;


                    }


                    final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                    responseDialog1.showResponseDialog(1,
                            getString(R.string.Response),
                            null, null, null, null,
                            oneLineMessage,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    responseDialog1.dialog.dismiss();
                                }
                            }


                    );

                    spinnerBankMode.setSelection(0);
                    spinnerPaymentMode.setSelection(0);
                    et_chequeno.setText("");
                    et_branch_name.setText("");
                    et_customer_ifsc_code.setText("");
                    et_amount_payment.setText("");
                    et_enter_remark.setText("");

                } else {

                    tvExepPaymentRequest.setText(getString(R.string.service_unavailable));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
          //  progressBar.setVisibility(View.GONE);
            comProgressDialog.cancelProgress();



            btnpaymentrequest.setEnabled(true);

        }
    }


    private class CheckIFSCCodeBackground extends AsyncTask<Void,Void,String>
    {
        String message;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try{
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.CheckIFSCCode);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.iFSCCode, String.valueOf(str_ifsc_code)));



                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                String IFSCCode_response= EntityUtils.toString(entity);
                //Toast.makeText(getActivity(),"this.responseBody"+a,Toast.LENGTH_SHORT).show();


                Gson gson=new GsonBuilder().create();
                Type type=new TypeToken<ResponseBase>(){}.getType();
                ResponseBase responseBase=gson.fromJson(IFSCCode_response,type);
                message=responseBase.message.toLowerCase();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return message;
        }
        @Override
        protected void onPostExecute(String s)
        {

        }


    }

    public void getResponseDialog(String response_message)
    {
        final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
        responseDialog1.showResponseDialog(1,
                getString(R.string.message),
                null,null,null,null,
                response_message,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnpaymentrequest.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }

}
