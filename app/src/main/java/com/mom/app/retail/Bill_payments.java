package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import com.mom.app.retail.Request.PaymentTransactionRequest;
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




public class Bill_payments extends Fragment {


    private TextView tvBillPayText, tvExepBillPay;
    private EditText etSubscriberID, etEntAmt, etDueDate, etTpin;
    private Button btnRecharge, btgetbillamnt, btnTpinSubmit, btnTpinCancel;
    private String str_SubscriberID, str_CustomMobNo, str_EntAmt, serviceProviderName, string_tpin, MomStatusCode;
    public static final String PREFS_NAME = "MyApp_Settings";
    private AutoCompleteTextView etCustomMobNo = null;
    private SQLiteAdapter sqLiteAdapter;
    private ArrayAdapter<String> adapter;
    private RelativeLayout page1;
    private LinearLayout page2;
    String subscriberId;
    private String[] contacts;
    private Map<String, Integer> mapElectricity, mapGas;
    private Spinner spinnerBillType, spinnerBillOpr;
    private Typeface Light, Normal, SemiBold;
    private int rechargeType;
    HttpPost httppost;
    HttpClient httpclient;
    ProgressBar progressBar,progressBarTpin;
    double wallet_balance;
    int WalletCustomerBalance;
    long str_CustomerId;
    ComProgressDialog comProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_bill_payments, container, false);

        NetworkConnection networkConnection = new NetworkConnection(getActivity());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();

        if (!isNetworkAvailable) {
            NetworkConnection.ExitAppDialog(getActivity());
        } else {

            comProgressDialog   = new ComProgressDialog(getActivity(),getActivity());

            Light               = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
            Normal              = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Normal);
            SemiBold            = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            progressBar         = (ProgressBar)v.findViewById(R.id.progressBar);
            progressBarTpin     = (ProgressBar)v.findViewById(R.id.progressBarTpin);
            tvBillPayText       = (TextView) v.findViewById(R.id.tvBillPayText);
            etSubscriberID      = (EditText) v.findViewById(R.id.et_subscriberId);
            etCustomMobNo       = (AutoCompleteTextView) v.findViewById(R.id.autoCustomerNo);
            etEntAmt            = (EditText) v.findViewById(R.id.etEnterAmount);
            etDueDate           = (EditText) v.findViewById(R.id.etDueDate);
            btnRecharge         = (Button) v.findViewById(R.id.btnRecharge);
            btgetbillamnt       = (Button) v.findViewById(R.id.btgetbillamnt);
            tvExepBillPay       = (TextView) v.findViewById(R.id.tvExepBillPay);
            etTpin              = (EditText) v.findViewById(R.id.et_tpin);
            btnTpinSubmit       = (Button) v.findViewById(R.id.btnTpinSubmit);
            btnTpinCancel       = (Button) v.findViewById(R.id.btnTpinCancel);
            spinnerBillType     = (Spinner) v.findViewById(R.id.spinnerBillType);
            spinnerBillOpr      = (Spinner) v.findViewById(R.id.spinnerBillOpr);

            page1               = (RelativeLayout) v.findViewById(R.id.page1);
            page2               = (LinearLayout) v.findViewById(R.id.page2);




            progressBar.setVisibility(View.INVISIBLE);
            progressBarTpin.setVisibility(View.INVISIBLE);

            tvExepBillPay.setTypeface(Light);
            tvBillPayText.setTypeface(Light);
            etSubscriberID.setTypeface(Light);
            etCustomMobNo.setTypeface(Light);
            etEntAmt.setTypeface(Light);
            etDueDate.setTypeface(Light);
            btnRecharge.setTypeface(Normal);
            btgetbillamnt.setTypeface(Normal);
            etTpin.setTypeface(Light);
            btnTpinSubmit.setTypeface(Normal);
            btnTpinCancel.setTypeface(Normal);

            etTpin.setImeOptions(EditorInfo.IME_ACTION_DONE);
            etEntAmt.setImeOptions(EditorInfo.IME_ACTION_DONE);

            sqLiteAdapter= new SQLiteAdapter(getActivity());
            sqLiteAdapter.openToReadBalanceDataBase();
            wallet_balance=sqLiteAdapter.getBalanceFromDB();
            sqLiteAdapter.close();


            WalletCustomerBalance = (int) wallet_balance;



            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
            etCustomMobNo.setThreshold(2);
            sqLiteAdapter = new SQLiteAdapter(getActivity());
            sqLiteAdapter.openToRead();
            contacts = sqLiteAdapter.getContacts();
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, contacts);
            etCustomMobNo.setAdapter(adapter);
            etCustomMobNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));
                    etCustomMobNo.setText(upToNCharacters);
                }
            });
            sqLiteAdapter.close();




            List<String> categories = new ArrayList<>();
            categories.add("Select Type");
            categories.add("Electricity");
            categories.add("Gas");



            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories) {

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

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerBillType.setAdapter(adapter);


            spinnerBillType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //   Toast.makeText(getActivity(), "Item number: " + position, Toast.LENGTH_LONG).show();
                    if (position == 0) {
                        List<String> list = new ArrayList<>();
                        list.add(getString(R.string.spinner_title));

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, list) {

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
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        spinnerBillOpr.setAdapter(dataAdapter);
                    }
                    if (position == 1) {

                        comProgressDialog.showProgress();
                        Electricity electricity= new Electricity();
                        electricity.execute();

                    }
                    if (position == 2) {
                        comProgressDialog.showProgress();
                        Gas gas= new Gas();
                        gas.execute();
                    }
//                    if (position == 3) {
//
//                        Water();
//
//                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

//                Toast.makeText(getActivity(), "Item number: " , Toast.LENGTH_LONG).show();

                }
            });


            btgetbillamnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    str_SubscriberID=etSubscriberID.getText().toString();
                    str_CustomMobNo = etCustomMobNo.getText().toString();


                    validateGetBillAmount();

                }
            });


            btnRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnTpinSubmit.setEnabled(true);
                    validate();

                }
            });


            btnTpinSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnTpinSubmit.setEnabled(false);
                    validate_tpin();
                }
            });


            btnTpinCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    page1.setVisibility(View.VISIBLE);
                    page2.setVisibility(View.GONE);

                }
            });


            spinnerBillOpr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String operator = spinnerBillOpr.getSelectedItem().toString();
                        switch (operator) {
                            case "TATA POWER DELHI":
                            case "MAHANAGAR GAS BILL":
                                etSubscriberID.setHint(getString(R.string.CA_number));
                                break;
                            case "IGL DELHI":
                                etSubscriberID.setHint(getString(R.string.BP_number));
                                break;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    if (position == 3) {
//
//                        Water();
//
//                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(etCustomMobNo.getText().toString()!=null || etSubscriberID.getText().toString()!=null)
        {

            spinnerBillType.setSelection(0);
            spinnerBillOpr.setSelection(0);
            etCustomMobNo.setText(null);
            etSubscriberID.setText(null);
            etEntAmt.setText(null);
        }
    }

    public boolean validateGetBillAmount() {
        int nMinLength          = 10;
        int nMaxLength          = 10;
        int minamount           = 10;
        int maxamount           = 200000;
        int nCustomMobNoLength  = etCustomMobNo.getText().toString().trim().length();
        str_SubscriberID        = etSubscriberID.getText().toString();
        str_CustomMobNo         = etCustomMobNo.getText().toString();
        str_EntAmt              = etEntAmt.getText().toString();
        String SpinnerOpr       = spinnerBillOpr.getSelectedItem().toString();
        String SpinnerType      = spinnerBillType.getSelectedItem().toString();

        if (SpinnerType.equals(getString(R.string.SelectType))) {
           // getResponseDialog(getString(R.string.mobile_recharge_select_type));
            getResponseDialog(getString(R.string.mobile_recharge_select_type));
           // tvExepBillPay.setText(getString(R.string.mobile_recharge_select_type));
        }
        else if (SpinnerOpr.equals(getString(R.string.spinner_title))) {
            //getResponseDialog(getString(R.string.select_operator));
            getResponseDialog(getString(R.string.select_operator));
            //tvExepBillPay.setText(getString(R.string.select_operator));
        }
        else if (str_SubscriberID.trim().equals("")) {
            //getResponseDialog(getString(R.string.error_subscriberid));
            //etSubscriberID.setError(getString(R.string.error_subscriberid));

            String message;
            String operator = spinnerBillOpr.getSelectedItem().toString();
            switch (operator) {
                case "TATA POWER DELHI":
                    message=getString(R.string.CA_number_should_be) + "max_subscriber_id" + getString(R.string.digits);
                    getResponseDialog(message);
                    //tvExepBillPay.setText(message);
                    break;
                case "IGL DELHI":
                    message=getString(R.string.BP_number_should_be) + "max_subscriber_id" + getString(R.string.digits);
                    getResponseDialog(message);
                   // tvExepBillPay.setText(message);
                    break;
                case "MAHANAGAR GAS BILL":
                    message=getString(R.string.CA_number_should_be) + "max_subscriber_id" + getString(R.string.digits);
                    getResponseDialog(message);
                   // tvExepBillPay.setText(message);
                    break;
                default:
                    getResponseDialog(getString(R.string.error_subscriberid));
                    break;

            }


            return false;
        }
        else if (nCustomMobNoLength < nMinLength || nCustomMobNoLength > nMaxLength) {
            //etCustomMobNo.setError(String.format(getString(R.string.error_phone_length), nMinLength));
            getResponseDialog(getString(R.string.mobile_recharge_mobile_no_length));
            //tvExepBillPay.setText(getString(R.string.mobile_recharge_mobile_no_length));
            return false;
        }
        else {
            GetBillAmount getBillAmount= new GetBillAmount();
                       getBillAmount.execute();
            tvExepBillPay.setText(null);
        }
        return true;
    }

    public boolean validate() {
        int nMinLength          = 10;
        int nMaxLength          = 10;
        int minamount           = 10;
        int maxamount           = 200000;
        int nCustomMobNoLength  = etCustomMobNo.getText().toString().trim().length();
        int SubscriberIDMin     =4;
        int SubscriberIDMax     =10;
        str_SubscriberID        = etSubscriberID.getText().toString();
        str_CustomMobNo         = etCustomMobNo.getText().toString();
        str_EntAmt              = etEntAmt.getText().toString();
        String SpinnerOpr       = spinnerBillOpr.getSelectedItem().toString();
        String SpinnerType      = spinnerBillType.getSelectedItem().toString();

        int amount=0;
        try {

            amount = Integer.parseInt(etEntAmt.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        if (SpinnerType.equals(getString(R.string.SelectType))) {
            getResponseDialog(getString(R.string.mobile_recharge_select_type));

        }
        else if (SpinnerOpr.equals(getString(R.string.spinner_title))) {
            getResponseDialog(getString(R.string.select_operator));

        }
        else if (str_SubscriberID.trim().equals("")) {
            //getResponseDialog(getString(R.string.error_subscriberid));
            //etSubscriberID.setError(getString(R.string.error_subscriberid));

            String message;
            String operator = spinnerBillOpr.getSelectedItem().toString();
            switch (operator) {
                case "TATA POWER DELHI":
                    message=getString(R.string.CA_number_should_be) + ""+ SubscriberIDMax+ ""+ getString(R.string.digits);
                    getResponseDialog(message);

                    break;
                case "IGL DELHI":
                    message=getString(R.string.BP_number_should_be) + ""+ SubscriberIDMax + ""+ getString(R.string.digits);
                    getResponseDialog(message);

                    break;
                case "MAHANAGAR GAS BILL":
                    message=getString(R.string.CA_number_should_be) + ""+SubscriberIDMax +""+ getString(R.string.digits);
                    getResponseDialog(message);

                    break;
                default:
                    getResponseDialog(getString(R.string.error_subscriberid));
                    break;

            }


            return false;
        }
        else if(str_SubscriberID.length()<SubscriberIDMin || str_SubscriberID.length()>SubscriberIDMax)
        {
            String error=getString(R.string.Subscriber_ID_should_be).concat(String.valueOf(SubscriberIDMin)).concat("and").concat(String.valueOf(SubscriberIDMax));
            getResponseDialog(error);

        }
        else if (nCustomMobNoLength < nMinLength || nCustomMobNoLength > nMaxLength) {

            getResponseDialog(getString(R.string.mobile_recharge_mobile_no_length));

            return false;
        }
        else if (str_EntAmt.trim().equals("")) {

            String message=getString(R.string.Amount_should_be_between) +  " Rs. "+  minamount +  getString(R.string.and)+ " Rs. "+ maxamount;
            getResponseDialog(message);

            return false;
        }
        else if (amount>WalletCustomerBalance ) {
            getResponseDialog(getString(R.string.WalletBalance));
            return false;
        }
        else {
            etTpin.setText(null);
            tvExepBillPay.setText(null);
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);

        }
        return true;
    }



    private class Electricity extends AsyncTask<String,Integer,String>{
        List<String> list = new ArrayList<>();
        String responseElectricityOpr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapElectricity = new HashMap<>();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                HttpClient httpclient   = new DefaultHttpClient();
                HttpPost httppost       = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.electricityBill_ServiceProviderTypeID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



                HttpResponse response   = httpclient.execute(httppost);
                HttpEntity entity       = response.getEntity();
                responseElectricityOpr          = EntityUtils.toString(entity);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return responseElectricityOpr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if(s!=null)
                {
                    Gson gson   = new GsonBuilder().create();
                    Type type   = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>(){}.getType();
                    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase=gson.fromJson(s,type);

                    list.add(getString(R.string.spinner_title));
                    if(responseBase.id==1)
                    {
                        for(GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse:responseBase.responseData)
                        {
                            serviceProviderName= getProviderNameByProviderTypeIDResponse.serviceProviderName;
                            int ServiceProviderID = getProviderNameByProviderTypeIDResponse.serviceProviderId;
                            boolean serviceProviderStatus = getProviderNameByProviderTypeIDResponse.serviceProviderStatus;
                            if (serviceProviderStatus) {
                                list.add(serviceProviderName);
                                mapElectricity.put(serviceProviderName, ServiceProviderID);

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
                    spinnerBillOpr.setAdapter(dataAdapter_res);
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            comProgressDialog.cancelProgress();
        }

    }

    private class Gas extends AsyncTask<String,Integer,String>{

        List<String> list = new ArrayList<>();
        String responseGasOpr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapGas = new HashMap<>();
        }

        @Override


        protected String doInBackground(String... params) {

            try {
                HttpClient httpclient   = new DefaultHttpClient();
                HttpPost httppost       = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.gasBill_ServiceProviderTypeID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



                HttpResponse response   = httpclient.execute(httppost);
                HttpEntity entity       = response.getEntity();
                responseGasOpr          = EntityUtils.toString(entity);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return responseGasOpr;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);

            try {
                if(strings!=null)
                {
                    Gson gson   = new GsonBuilder().create();
                    Type type   = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>(){}.getType();
                    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase=gson.fromJson(strings,type);

                    list.add(getString(R.string.spinner_title));
                    if(responseBase.id==1)
                    {
                        for(GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse:responseBase.responseData)
                        {
                            serviceProviderName= getProviderNameByProviderTypeIDResponse.serviceProviderName;
                            int ServiceProviderID = getProviderNameByProviderTypeIDResponse.serviceProviderId;
                            boolean serviceProviderStatus = getProviderNameByProviderTypeIDResponse.serviceProviderStatus;
                            if (serviceProviderStatus) {
                                list.add(serviceProviderName);
                                mapGas.put(serviceProviderName, ServiceProviderID);

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
                    spinnerBillOpr.setAdapter(dataAdapter_res);
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            comProgressDialog.cancelProgress();
        }
    }

    private boolean validate_tpin() {
        string_tpin = etTpin.getText().toString();

        if (string_tpin.length() < 8) {
            getResponseDialog(getString(R.string.error_tpin_length));

            return false;

        } else {


            GetValidPasswordPinClass getValidPasswordPinClass = new GetValidPasswordPinClass(getActivity(), getActivity(), string_tpin);
            String message = getValidPasswordPinClass.doInBackground().toLowerCase();


            if (message.equals(String_url.success)) {
                tvExepBillPay.setText("");
                String operator = spinnerBillOpr.getSelectedItem().toString();

                switch (operator) {
                    case "TATA POWER DELHI":
                    case "MAHANAGAR GAS BILL":
                        subscriberId = getString(R.string.CA_number);
                        break;
                    case "IGL DELHI":
                        subscriberId = getString(R.string.BP_number);
                        break;
                    default:
                        subscriberId = getString(R.string.subscriberID);
                        break;
                }

                final ConfrimRechargeDialog confrimRechargeDialog1 = new ConfrimRechargeDialog(getActivity(), getActivity());
                confrimRechargeDialog1.showDialog(3,
                        getString(R.string.ConfirmRecharge),
                        getString(R.string.subscriberID),
                        str_SubscriberID,
                        getString(R.string.Operator),
                        operator,
                        getString(R.string.amount),
                        str_EntAmt,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StartRecharge startRecharge = new StartRecharge();
                                startRecharge.execute();
                                confrimRechargeDialog1.dialog.dismiss();

                            }
                        },

                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnTpinSubmit.setEnabled(true);
                                confrimRechargeDialog1.dialog.dismiss();
                            }
                        }
                );
            }
            else {

                getResponseDialog(getString(R.string.invalid_tpin));

            }


                return true;

        }
    }

    private class StartRecharge extends AsyncTask<String,Integer,String>{

        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
        int str_rechargeType = spinnerBillType.getSelectedItemPosition();
        String title;
        int oprId;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            String operaterIdPrepaid;
            if (str_rechargeType == 1) {
                operaterIdPrepaid = spinnerBillOpr.getSelectedItem().toString();
                oprId = mapElectricity.get(operaterIdPrepaid);
            }
            else {
                operaterIdPrepaid = spinnerBillOpr.getSelectedItem().toString();
                oprId = mapGas.get(operaterIdPrepaid);
            }

           // progressBarTpin.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
        }

        @Override
        protected String doInBackground(String... params) {
                String responseJson=null;

            try {
                Gson gson = new GsonBuilder().create();
                PaymentTransactionRequest paymentTransactionRequest = new PaymentTransactionRequest();
                Type type = new TypeToken<PaymentTransactionRequest>() {}.getType();

                Encryption encryption= new Encryption(getActivity());



                if (str_rechargeType == 1) {

                    rechargeType = 4;

                    paymentTransactionRequest.setUserId(str_CustomerId);
                    paymentTransactionRequest.setCustomerMobileNumber(Long.valueOf(str_CustomMobNo));
                    paymentTransactionRequest.setOperaterId(oprId);
                    paymentTransactionRequest.setTransactionAmount(Float.parseFloat(str_EntAmt));
                    paymentTransactionRequest.setCircleId(1);
                    paymentTransactionRequest.setRechargeType(rechargeType);
                    paymentTransactionRequest.setTpin(encryption.SHA1(string_tpin));
                    paymentTransactionRequest.setChannelId(String_url.channelId);

                    String json = gson.toJson(paymentTransactionRequest, type);
                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(String_url.DoUtilityBillPayTransaction);
                    nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                    nameValuePairs.add(new BasicNameValuePair(String_url.transaction_Request, json));
                    title=getString(R.string.ElectricityPayment);


                }
                if (str_rechargeType == 2) {

                    rechargeType = 5;

                    paymentTransactionRequest.setUserId(str_CustomerId);
                    paymentTransactionRequest.setCustomerMobileNumber(Long.valueOf(str_CustomMobNo));
                    paymentTransactionRequest.setOperaterId(oprId);
                    paymentTransactionRequest.setTransactionAmount(Float.parseFloat(str_EntAmt));
                    paymentTransactionRequest.setCircleId(1);
                    paymentTransactionRequest.setRechargeType(rechargeType);
                    paymentTransactionRequest.setTpin(encryption.SHA1(string_tpin));
                    paymentTransactionRequest.setChannelId(String_url.channelId);

                    String json = gson.toJson(paymentTransactionRequest, type);
                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(String_url.DoUtilityGasTransaction);
                    nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                    nameValuePairs.add(new BasicNameValuePair(String_url.transaction_Request, json));
                    title=getString(R.string.GasPayment);

                }

                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

// Execute HTTP Post Request

                HttpResponse response   = httpclient.execute(httppost);
                HttpEntity entity       = response.getEntity();
                responseJson            = EntityUtils.toString(entity);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return responseJson;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                try {



                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<PaymentResponseResult>>(){}.getType();
                    ResponseBase<PaymentResponseResult> responseBase = gson.fromJson(s,type);
                    PaymentResponseResult paymentResponseResult = responseBase.responseData;

                    int id                  = responseBase.id;
                    String message          = paymentResponseResult.momMessage;
                    MomStatusCode           = paymentResponseResult.momStatusCode;
                    String isoTransactionId = paymentResponseResult.isoTransactionId;
                    String operatorId       = paymentResponseResult.operatorId;

                    final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                    responseDialog1.showResponseDialog(2,
                            title,
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
                                        //  getBalanceClass.UpdateBalance();
                                        getBalanceClass.execute();
                                    }




                                    page1.setVisibility(View.VISIBLE);
                                    page2.setVisibility(View.GONE);
                                    spinnerBillType.setSelection(0);
                                    etSubscriberID.setText("");
                                    etCustomMobNo.setText("");
                                    etEntAmt.setText("");
                                    etTpin.setText("");
                                    responseDialog1.dialog.dismiss();

                                }
                            }

                    );

                   // progressBarTpin.setVisibility(View.GONE);
                    comProgressDialog.cancelProgress();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private class  GetBillAmount extends AsyncTask<String, Integer, String>{

        String amount;
        int id,oprId = 0;
        long mobileLoginNumber;
        String responseGetBillAmount;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            String operaterId;

            int str_rechargeType = spinnerBillType.getSelectedItemPosition();

            if (str_rechargeType == 1) {
                operaterId = spinnerBillOpr.getSelectedItem().toString();
                oprId = mapElectricity.get(operaterId);

            }
            if (str_rechargeType == 2) {
                operaterId = spinnerBillOpr.getSelectedItem().toString();
                oprId = mapGas.get(operaterId);
            }

          //  progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
            SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            mobileLoginNumber=sharedPreferences1.getLong(String_url.MobileNumber,0);
        }

        @Override
        protected String doInBackground(String... params) {




            try {

                List<NameValuePair> nameValuePairs  = new ArrayList<>(5);
                httpclient                          = new DefaultHttpClient();
                httppost                            = new HttpPost(String_url.GetBillAmount);


                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.customerMobileNumber, String.valueOf(mobileLoginNumber)));
                nameValuePairs.add(new BasicNameValuePair(String_url.customerAccountNumber, str_SubscriberID));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderId, String.valueOf(oprId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.channelIdParameter, String.valueOf(String_url.channelId)));

                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

// Execute HTTP Post Request

                HttpResponse response   = httpclient.execute(httppost);
                HttpEntity entity       = response.getEntity();
                responseGetBillAmount                = EntityUtils.toString(entity);




            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return responseGetBillAmount;
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                if(s!=null)
                {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>() {}.getType();
                    ResponseBase responseBase = gson.fromJson(s, type);
                    id=responseBase.id;


                    if (id==1)
                    {
                        String sBal=responseBase.responseData.toString();
                        etEntAmt.setText(sBal);
                        InputMethodManager inputManager = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                    }
                    else
                    {
                        final ResponseDialog responseDialog = new ResponseDialog(getActivity(),getActivity());
                        responseDialog.showResponseDialog(1,
                                getString(R.string.getbillamnt),
                                null,
                                null,
                                null,
                                null,
                                getString(R.string.Please_check_your_subscriber_id_and_try_again),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        spinnerBillType.setSelection(0);
                                        etSubscriberID.setText("");
                                        etCustomMobNo.setText("");
                                        etEntAmt.setText("");
                                        responseDialog.dialog.dismiss();
                                    }
                                }
                        );

                    }

                   // progressBar.setVisibility(View.GONE);
                    comProgressDialog.cancelProgress();
                }


            }
            catch (Exception e)
            {e.printStackTrace();}



            super.onPostExecute(s);
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

                        btnTpinSubmit.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }

}

