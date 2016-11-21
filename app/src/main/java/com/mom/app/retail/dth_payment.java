package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mom.app.retail.Request.DTHRechargeTransactionRequest;
import com.mom.app.retail.Response.GetProviderNameByProviderTypeIDResponse;
import com.mom.app.retail.Response.PaymentResponseResult;
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

public class dth_payment extends Fragment {


    private TextView tvDthText, tvExepdth;
    private EditText etSubscriberID, etEntAmt;
    private Button btnRecharge;

    Typeface Light;
    Typeface Normal;
    Typeface SemiBold;
    String serviceProviderName, spinnerDth, str_spinner_dth, string_tpin, SubscriberID;
    Spinner spiner_dth;
    AutoCompleteTextView etMobNo = null;
    private ArrayAdapter<String> adapter;
    private SQLiteAdapter sqLiteAdapter;
    private int ServiceProviderID;
    List<String> OprName;
    Map<String, Integer> map;
    String string_mobile_number, str_amount;
    private long str_CustomerId;
    public static final String PREFS_NAME = "MyApp_Settings";
    private String[] contacts;
    Dth_ServicePRoviderID dth_servicePRoviderID;
    private EditText et_tpin;
    private Button btnTpinSubmit, btnCancel;
    int oprId;
    String DTHOperator;

    private RelativeLayout page1;
    private LinearLayout page2;
    TextView textView1;
    private ProgressBar progressBarTpin;
    ComProgressDialog comProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dth, container, false);

        NetworkConnection networkConnection = new NetworkConnection(getActivity());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();

        if (!isNetworkAvailable) {
            NetworkConnection.ExitAppDialog(getActivity());
        } else {

            comProgressDialog = new ComProgressDialog(getActivity(),getActivity());

            Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
            Normal = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Normal);
            SemiBold = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Semibold);

            page1 = (RelativeLayout) v.findViewById(R.id.page1);
            page2 = (LinearLayout) v.findViewById(R.id.page2);

            progressBarTpin = (ProgressBar) v.findViewById(R.id.progressBarTpin);
            tvDthText = (TextView) v.findViewById(R.id.tvDthText);
            etSubscriberID = (EditText) v.findViewById(R.id.etSubscriberId);
            etEntAmt = (EditText) v.findViewById(R.id.et_enterRechargeAmnt);
            et_tpin = (EditText) v.findViewById(R.id.et_tpin);
            btnTpinSubmit = (Button) v.findViewById(R.id.btnTpinSubmit);
            btnCancel = (Button) v.findViewById(R.id.btnTpinCancel);

            btnRecharge = (Button) v.findViewById(R.id.btnRecharge);
            tvExepdth = (TextView) v.findViewById(R.id.tvExepdth);
            spiner_dth = (Spinner) v.findViewById(R.id.spinner_dth);
            etMobNo = (AutoCompleteTextView) v.findViewById(R.id.etCustomerNo);


            tvDthText.setTypeface(Light);
            tvExepdth.setTypeface(Light);
            etSubscriberID.setTypeface(Light);
            etEntAmt.setTypeface(Light);
            etEntAmt.setImeOptions(EditorInfo.IME_ACTION_DONE);
            et_tpin.setTypeface(Light);
            et_tpin.setImeOptions(EditorInfo.IME_ACTION_DONE);
            etMobNo.setTypeface(Light);
            etMobNo.setThreshold(2);
            btnRecharge.setTypeface(Normal);
            btnTpinSubmit.setTypeface(Normal);
            btnCancel.setTypeface(Normal);
            progressBarTpin.setVisibility(View.INVISIBLE);

            btnRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnTpinSubmit.setEnabled(true);
                    validate();
                }
            });

            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
            sqLiteAdapter = new SQLiteAdapter(getActivity());
            sqLiteAdapter.openToRead();
            contacts = sqLiteAdapter.getContacts();
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, contacts);
            etMobNo.setAdapter(adapter);


            etMobNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));
                    etMobNo.setText(upToNCharacters);

                }
            });
            sqLiteAdapter.close();

            DthOperator dthOperator = new DthOperator();
            dthOperator.execute();

            spiner_dth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    str_spinner_dth = spiner_dth.getSelectedItem().toString();
                    try {
                        switch (str_spinner_dth) {
                            case "DISH":
                            case "SUN DIRECT":

                                etSubscriberID.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                                break;
                            case "BIG TV":

                                etSubscriberID.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
                                break;
                            default:
                                etSubscriberID.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                                break;
                        }

                    } catch (NullPointerException e) {
                        System.out.println("NullPointerException" + e);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnTpinSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnTpinSubmit.setEnabled(false);
                    validate_tpin();
                    et_tpin.setText("");

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    page1.setVisibility(View.VISIBLE);
                    page2.setVisibility(View.GONE);

                    tvExepdth.setText(null);


                }
            });


        }
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        if(etMobNo.getText().toString()!=null || etSubscriberID.getText().toString()!=null)
        {
            spiner_dth.setSelection(0);
            etMobNo.setText(null);
            etSubscriberID.setText(null);
            etEntAmt.setText(null);
        }
    }

    public boolean validate() {

        int WalletCustomerBalance;

        sqLiteAdapter = new SQLiteAdapter(getActivity());
        sqLiteAdapter.openToReadBalanceDataBase();
        double dBalance = sqLiteAdapter.getBalanceFromDB();
        sqLiteAdapter.close();


        WalletCustomerBalance = (int) dBalance;

        int min_mobile_number = 10;
        int max_mobile_number = 10;

        int min_dth_amount = 10;
        int max_dth_amount = 15000;
        int min_subscriber_id = 10;
        int max_subscriber_id = 10;
        String string_mobile_number = etMobNo.getText().toString();
        String str_subscriber_id = etSubscriberID.getText().toString();
        str_amount = etEntAmt.getText().toString();
        int dth_amount = 0;


        try {

            dth_amount = Integer.parseInt(etEntAmt.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }


        if (spiner_dth.getSelectedItemPosition() > 0) {
            spinnerDth = spiner_dth.getSelectedItem().toString();
            String operatorIDDTH = spinnerDth;
            oprId = map.get(operatorIDDTH);
            DTHOperator = (String.valueOf(oprId));


        }


        if (spiner_dth.getSelectedItemPosition() == 0) {
            getResponseDialog(getString(R.string.mobile_recharge_select_type));

            return false;
        }
        if (DTHOperator.equals(String_url.DTH_OPERATOR_AIRTEL_DIGITAL)) {


            min_dth_amount = 100;
            max_dth_amount = 15000;
            min_subscriber_id = 10;
            max_subscriber_id = 10;


        }
        if (DTHOperator.equals(String_url.DTH_OPERATOR_BIG_TV)) {


            min_dth_amount = 25;
            max_dth_amount = 15000;
            min_subscriber_id = 12;
            max_subscriber_id = 12;
        }
        if (DTHOperator.equals(String_url.DTH_OPERATOR_SUN_DIRECT)) {


            min_dth_amount = 10;
            max_dth_amount = 15000;
            min_subscriber_id = 11;
            max_subscriber_id = 11;
        }
        if (DTHOperator.equals(String_url.DTH_OPERATOR_TATA_SKY)) {


            min_dth_amount = 8;
            max_dth_amount = 20000;
            min_subscriber_id = 10;
            max_subscriber_id = 10;
        }

        if (DTHOperator.equals(String_url.DTH_OPERATOR_VIDEOCON_DTH)) {


            min_dth_amount = 50;
            max_dth_amount = 15000;
            min_subscriber_id = 1;
            max_subscriber_id = 10;
        }
        if (DTHOperator.equals(String_url.DTH_OPERATOR_BIG_TV_ACQ)) {


            min_dth_amount = 1;
            max_dth_amount = 15000;
            min_subscriber_id = 1;
            max_subscriber_id = 50;
        }
        if (DTHOperator.equals(String_url.DTH_OPERATOR_DISH)) {

            min_dth_amount = 10;
            max_dth_amount = 15000;
            min_subscriber_id = 11;
            max_subscriber_id = 11;

        }
        if (DTHOperator.equals(String_url.DTH_OPERATOR_TATA_SKY_ACQ)) {


            min_dth_amount = 1;
            max_dth_amount = 15000;
            min_subscriber_id = 1;
            max_subscriber_id = 50;

        }
        if (str_subscriber_id.length() < min_subscriber_id || str_subscriber_id.length() > max_subscriber_id) {

            if (min_subscriber_id == max_subscriber_id) {
                getResponseDialog(getString(R.string.Error_Subscriber_ID) + max_subscriber_id + getString(R.string.digits));

                return false;
            } else {
                getResponseDialog(getString(R.string.Subscriber_ID_should_be) + min_subscriber_id + "and" + max_subscriber_id + " " + getString(R.string.digits));

                return false;
            }

        }
        if (string_mobile_number.length() < min_mobile_number || string_mobile_number.length() > max_mobile_number) {

            getResponseDialog(getString(R.string.mobile_recharge_mobile_no_length));

            return false;
        }
        if (dth_amount < min_dth_amount || dth_amount > max_dth_amount) {
            getResponseDialog(getString(R.string.Amount_should_be_between) + " Rs. " + min_dth_amount + getString(R.string.and) + " Rs. " + max_dth_amount);

            return false;
        } else if (dth_amount > WalletCustomerBalance) {
            getResponseDialog(getString(R.string.WalletBalance));

            return false;


        } else {
            et_tpin.setText(null);
            tvExepdth.setText(null);
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
            return true;
        }

    }

    private boolean validate_tpin() {
        string_tpin = et_tpin.getText().toString();

        if (string_tpin.length() < 8) {
            getResponseDialog(getString(R.string.error_tpin_length));
            //tvExepdth.setText(getString(R.string.error_tpin_length));

            return false;

        } else if (string_tpin.length() == 8) {


            GetValidPasswordPinClass getValidPasswordPinClass = new GetValidPasswordPinClass(getActivity(), getActivity(), string_tpin);
            String message = getValidPasswordPinClass.doInBackground().toLowerCase();


            if (message.equals(String_url.success)) {


                SharedPreferences mbpayment = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                str_CustomerId = mbpayment.getLong(String_url.CustomerID, 0);
                String operator = spiner_dth.getSelectedItem().toString();
                SubscriberID = etSubscriberID.getText().toString();

                final ConfrimRechargeDialog confrimRechargeDialog = new ConfrimRechargeDialog(getActivity(), getActivity());
                confrimRechargeDialog.showDialog(3,
                        getString(R.string.ConfirmRecharge),
                        getString(R.string.subscriberID),
                        SubscriberID,
                        getString(R.string.Operator),
                        operator,
                        getString(R.string.amount),
                        str_amount,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StartRecharge startRecharge = new StartRecharge();
                                startRecharge.execute();
                                confrimRechargeDialog.dialog.dismiss();

                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                confrimRechargeDialog.dialog.dismiss();
                                btnTpinSubmit.setEnabled(true);
                            }
                        }

                );

            } else {

                getResponseDialog(getString(R.string.invalid_tpin));

            }
        }

        return true;
    }

    private class StartRecharge extends AsyncTask<String, Integer, String> {

        String operaterId = spiner_dth.getSelectedItem().toString();
        int oprId = map.get(operaterId);
        String responseDTH, MomStatusCode;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressBarTpin.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
            string_mobile_number = etMobNo.getText().toString();
            SubscriberID = etSubscriberID.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                int rechargeType = 3;


                Gson gson = new GsonBuilder().create();
                DTHRechargeTransactionRequest dthRechargeTransactionRequest = new DTHRechargeTransactionRequest();
                Type type = new TypeToken<DTHRechargeTransactionRequest>() {
                }.getType();

                Encryption encryption = new Encryption(getActivity());

                dthRechargeTransactionRequest.setUserId(Long.valueOf(str_CustomerId));
                dthRechargeTransactionRequest.setCustomerMobileNumber(Long.valueOf(string_mobile_number));
                dthRechargeTransactionRequest.setSubscriberNumber(SubscriberID);
                dthRechargeTransactionRequest.setOperaterId(oprId);
                dthRechargeTransactionRequest.setTransactionAmount(Float.parseFloat(str_amount));
                dthRechargeTransactionRequest.setCircleId(1);
                dthRechargeTransactionRequest.setRechargeType(rechargeType);
                dthRechargeTransactionRequest.setTpin(encryption.SHA1(string_tpin));
                dthRechargeTransactionRequest.setChannelId(String_url.channelId);

                String json = gson.toJson(dthRechargeTransactionRequest, type);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.DoDTHTransaction);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.transaction_Request, json));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                responseDTH = EntityUtils.toString(entity);


            } catch (Exception e) {
                e.printStackTrace();
            }


            return responseDTH;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (s != null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<PaymentResponseResult>>() {
                    }.getType();
                    ResponseBase<PaymentResponseResult> responseBase = gson.fromJson(s, type);
                    PaymentResponseResult paymentResponseResult = responseBase.responseData;


                    int id = responseBase.id;
                    String message = paymentResponseResult.momMessage;
                    MomStatusCode = paymentResponseResult.momStatusCode;
                    String isoTransactionId = paymentResponseResult.isoTransactionId;
                    String operatorId = paymentResponseResult.operatorId;

                    final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                    responseDialog1.showResponseDialog(2,
                            getString(R.string.DTHPayment),
                            getString(R.string.message),
                            message,
                            getString(R.string.TransactionId),
                            isoTransactionId,
                            null,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (MomStatusCode.equals("0") || MomStatusCode.equals("2")) {
                                        GetBalanceClass getBalanceClass = new GetBalanceClass(getActivity(), getActivity());
                                        getBalanceClass.execute();
                                    }
                                    page1.setVisibility(View.VISIBLE);
                                    page2.setVisibility(View.GONE);
                                    spiner_dth.setSelection(0);
                                    etSubscriberID.setText(null);
                                    etMobNo.setText(null);
                                    etEntAmt.setText(null);
                                    et_tpin.setText(null);
                                    tvExepdth.setText(null);

                                    responseDialog1.dialog.dismiss();
                                }
                            }

                    );


                  //  progressBarTpin.setVisibility(View.GONE);
                    comProgressDialog.cancelProgress();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class DthOperator extends AsyncTask<String, Integer, String> {
        List<String> list = new ArrayList<String>();
        String dthOprResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map = new HashMap<String, Integer>();
            dth_servicePRoviderID = new Dth_ServicePRoviderID();
            OprName = new ArrayList<String>();
            OprName.add(getString(R.string.spinner_title));
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.dth__ServiceProviderTypeID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                dthOprResponse = EntityUtils.toString(entity);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return dthOprResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (s != null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>() {
                    }.getType();
                    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase = gson.fromJson(s, type);

                    list.add(getString(R.string.SelectType));
                    if (responseBase.id == 1) {
                        for (GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse : responseBase.responseData) {
                            serviceProviderName = getProviderNameByProviderTypeIDResponse.serviceProviderName;
                            ServiceProviderID = getProviderNameByProviderTypeIDResponse.serviceProviderId;
                            boolean serviceProviderStatus = getProviderNameByProviderTypeIDResponse.serviceProviderStatus;
                            if (serviceProviderStatus) {
                                list.add(serviceProviderName);
                                map.put(serviceProviderName, ServiceProviderID);

                            }


                        }
                    }




                    ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list) {
                        public View getView(int position, View convertView, android.view.ViewGroup parent) {

                            TextView v = (TextView) super.getView(position, convertView, parent);
                            v.setTypeface(Light);
                            v.setTextColor(Color.parseColor(String_url.spinnerHintColor));
                            v.setTextSize(18);
                            v.setGravity(Gravity.CENTER);

                            return v;
                        }

                        public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
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
                    spiner_dth.setAdapter(dataAdapter_res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getResponseDialog(String response_message) {
        final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
        responseDialog1.showResponseDialog(1,
                getString(R.string.message),
                null, null, null, null,
                response_message,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnTpinSubmit.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }


}


