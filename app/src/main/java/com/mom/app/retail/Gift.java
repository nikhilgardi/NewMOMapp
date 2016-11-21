package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.mom.app.retail.Request.GiftTransactionRequest;
import com.mom.app.retail.Response.GetDetailsGiftCardDenominationsResponse;
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


public class Gift extends Fragment {

    private ImageView imageView;
    private TextView tvGiftImageText, tv_gift, tvexceptiongift;
    private EditText et_enter_amount, et_tpin;
    private Button btn_get_voucher, btnTpinSubmit, btnTpinCancel;
    private String str_mobileno, str_amount, string_tpin;
    private long str_CustomerId;
    Map<String, Integer> mapGift;
    Map<String, Integer> mapGift1;
    List<String> GiftVocherList;
    Spinner spinnerGiftType,spinnerDenominations;
    private String str_ServiceProviderName, str_ServiceProviderID, ServiceCircleID, ServiceProviderID,title;
    private AutoCompleteTextView et_mobileno = null;
    private ArrayAdapter<String> adapter;
    private SQLiteAdapter sqLiteAdapter;
    private String[] contacts;
    LinearLayout page1, page2;
    public static final String PREFS_NAME = "MyApp_Settings";
    Typeface Light,Normal,SemiBold;
    Map<String, List<String>> map1,map2;
    Gift_ServiceProviderID gift_mode;
    GetDetailsGiftCardDenominationsID getDetailsGiftCardDenominationsID;
    private ProgressBar progressBarTpin;
    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase;
   
    private List<String> DenominationsList;
    FrameLayout frameDenominations;
    double CustomerMainAccountBalance;
    int WalletCustomerBalance;
    int denoSpinnerAmount;

    ComProgressDialog comProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gift, container, false);
        NetworkConnection networkConnection = new NetworkConnection(getActivity());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();

        if (!isNetworkAvailable) {
            NetworkConnection.ExitAppDialog(getActivity());
        }
        else {

            comProgressDialog               = new ComProgressDialog(getActivity(),getActivity());
            spinnerGiftType                 = (Spinner) v.findViewById(R.id.spinner);
            spinnerDenominations            =(Spinner)v.findViewById(R.id.spinnerDenominations);

            SharedPreferences mbpayment     = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            str_CustomerId                  = mbpayment.getLong(String_url.CustomerID, 0);
            sqLiteAdapter= new SQLiteAdapter(getActivity());
            sqLiteAdapter.openToReadBalanceDataBase();
            CustomerMainAccountBalance      =sqLiteAdapter.getBalanceFromDB();
            sqLiteAdapter.close();
             WalletCustomerBalance          = (int) CustomerMainAccountBalance;


            Light                           = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
            Normal                          = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Normal);
            SemiBold                        = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Semibold);

            imageView                       = (ImageView) v.findViewById(R.id.img_image);
            tvGiftImageText                 = (TextView) v.findViewById(R.id.tvGiftImageText);
            tvexceptiongift                 = (TextView) v.findViewById(R.id.tvexceptiongift);
            tvexceptiongift.setTypeface(Light);
            tv_gift                         = (TextView) v.findViewById(R.id.tv_gift);

            progressBarTpin                 =(ProgressBar)v.findViewById(R.id.progressbarTpin);
         //   progressBarTpin.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();

            et_mobileno                     = (AutoCompleteTextView) v.findViewById(R.id.et_mobileno);
            et_enter_amount                 = (EditText) v.findViewById(R.id.et_enter_amount);
            et_tpin                         = (EditText) v.findViewById(R.id.et_tpin);

            btn_get_voucher                 = (Button) v.findViewById(R.id.btn_get_voucher);
            btnTpinSubmit                   = (Button) v.findViewById(R.id.btnTpinSubmit);
            btnTpinCancel                   = (Button) v.findViewById(R.id.btnTpinCancel);

            page1                           = (LinearLayout) v.findViewById(R.id.page1);
            page2                           = (LinearLayout) v.findViewById(R.id.page2);

            imageView.setBackgroundResource(R.drawable.allscreen);
            tvGiftImageText.setTypeface(Light);
            tv_gift.setTypeface(Light);
            et_mobileno.setTypeface(Light);
            et_enter_amount.setTypeface(Light);
            et_tpin.setTypeface(Light);
            et_enter_amount.setImeOptions(EditorInfo.IME_ACTION_DONE);
            et_tpin.setImeOptions(EditorInfo.IME_ACTION_DONE);
            frameDenominations              = (FrameLayout) v.findViewById(R.id.frameDenominations);

            btn_get_voucher.setTypeface(Normal);
            btnTpinSubmit.setTypeface(Normal);
            btnTpinCancel.setTypeface(Normal);
           
            title=getString(R.string.gift_voucher);
            btnTpinSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnTpinSubmit.setEnabled(false);
                    validate_tpin();
                    et_tpin.setText("");

                }
            });
            btnTpinCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvexceptiongift.setText("");
                    page1.setVisibility(View.VISIBLE);
                    page2.setVisibility(View.GONE);

                }
            });

            gift_mode = new Gift_ServiceProviderID();
            getDetailsGiftCardDenominationsID=new GetDetailsGiftCardDenominationsID();


            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());

            et_mobileno.setThreshold(2);
            sqLiteAdapter = new SQLiteAdapter(getActivity());

            sqLiteAdapter.openToRead();

            contacts = sqLiteAdapter.getContacts();
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, contacts);
            et_mobileno.setAdapter(adapter);


            et_mobileno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));

                    et_mobileno.setText(upToNCharacters);


                }
            });
            sqLiteAdapter.close();


            DenominationsList=new ArrayList<String>();
            DenominationsList.add(getString(R.string.select_denominations));



            try
            {
                ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, DenominationsList) {
                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                        //  tfavv = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avvaiyar.ttf");
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
                spinnerDenominations.setAdapter(dataAdapter_res);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


            GetProviderNameByproviderTypeID getProviderNameByproviderTypeID = new GetProviderNameByproviderTypeID();
            getProviderNameByproviderTypeID.execute();


            spinnerGiftType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        tvexceptiongift.setText(null);
                        str_ServiceProviderName = gift_mode.getgetserviceProviderName(spinnerGiftType.getSelectedItem().toString());
                        str_ServiceProviderID = gift_mode.getServiceProviderID(spinnerGiftType.getSelectedItem().toString());
                        String spinnervalue=spinnerGiftType.getSelectedItem().toString();

                        Log.d("serviceProviderId",str_ServiceProviderID);

                        DenominationsList.clear();
                        DenominationsList=new ArrayList<String>();
                        DenominationsList.add(getString(R.string.select_denominations));

                        if(spinnervalue.equals("FLIPKART EGV"))
                        {

                            frameDenominations.setVisibility(View.GONE);
                            et_enter_amount.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            frameDenominations.setVisibility(View.VISIBLE);
                            et_enter_amount.setVisibility(View.GONE);
                            GetDetailsGiftCardDenominationsBackground getDetailsGiftCardDenominationsBackground=new GetDetailsGiftCardDenominationsBackground();
                            getDetailsGiftCardDenominationsBackground.execute();

                        }
                    } catch (NullPointerException e) {
                        System.out.println("Exception" + e);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinnerDenominations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        tvexceptiongift.setText(null);
                        String DenominationsValues=spinnerDenominations.getSelectedItem().toString();



                        if(spinnerDenominations.getSelectedItemPosition()!=0){
                            str_amount=DenominationsValues;
                            denoSpinnerAmount = Integer.parseInt(str_amount);


                        }
                        else
                        {

                        }


                    } catch (NullPointerException e) {
                        System.out.println("Exception" + e);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            btn_get_voucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //validate();
                    btnTpinSubmit.setEnabled(true);
                    validate_spinner();

                }
            });

        }


        return v;
    }

    private class GetProviderNameByproviderTypeID extends AsyncTask<String, Integer, String> {
            List<String> branchNameResultList;
            String serviceprovider_response;

            @Override
            protected void onPreExecute() {
                map1 = new HashMap<String, List<String>>();
                mapGift = new HashMap<String, Integer>();
                GiftVocherList = new ArrayList<String>();
                GiftVocherList.add(getString(R.string.e_tailer));
            }

            @Override
            protected String doInBackground(String... params) {

                try {

                    HttpClient httpclient = new DefaultHttpClient();

                    //HttpPost httppost = new HttpPost("http://192.168.11.10/android_middleware/Customers.asmx/GetProviderNameByProviderTypeID");
                    HttpPost httppost = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                    nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.gift_ServiceProviderTypeID)));

                    final HttpParams httpParams = httpclient.getParams();
                    HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                    HttpConnectionParams.setSoTimeout(httpParams, 45000);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    serviceprovider_response = EntityUtils.toString(entity);




                } catch (Exception e) {
                    e.printStackTrace();
                }
                return serviceprovider_response;
            }

            @Override
            protected void onPostExecute(String s) {

                try {
                    if(s!=null)
                    {
                        Gson gson = new GsonBuilder().create();
                        Type type = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>() {}.getType();
                        responseBase = gson.fromJson(serviceprovider_response, type);

                        if (responseBase.id == 1) {
                            for (GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse : responseBase.responseData) {
                                int ServiceProviderID = getProviderNameByProviderTypeIDResponse.serviceProviderId;
                                String ServiceProviderName = getProviderNameByProviderTypeIDResponse.serviceProviderName;
                                List<String> gifset = new ArrayList<String>();
                                gifset.add(ServiceProviderName);
                                gifset.add(String.valueOf(ServiceProviderID));
                                map1.put(ServiceProviderName, gifset);
                                GiftVocherList.add(ServiceProviderName);
                                gift_mode.storeGiftList(String_url.payment_mode, map1);
                                gift_mode.storeGiftListDetail(String_url.payment_id, GiftVocherList);
                                mapGift.put(ServiceProviderName, ServiceProviderID);
                                branchNameResultList = gift_mode.getGiftListDetail();
                            }
                            ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNameResultList) {
                                public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                    //  tfavv = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avvaiyar.ttf");
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


                            spinnerGiftType.setAdapter(dataAdapter_res);
                        }
                    }
                //    progressBarTpin.setVisibility(View.GONE);
                    comProgressDialog.cancelProgress();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    public boolean validate_spinner() {
            String spinnervalue=spinnerGiftType.getSelectedItem().toString();
            if (spinnerGiftType.getSelectedItemPosition() == 0) {
                getResponseDialog(getString(R.string.select_gift_type));

            return false;
        }
            else if(spinnervalue.equals("FLIPKART EGV")){
                validate_amount();
            }
            else
            {
                validate();
            }
        return true;

    }

    public boolean validate_amount() {
        int mobile_length = 10;
        int min_gift_amount = 10;
        int max_gift_amount = 10000;
        str_mobileno = et_mobileno.getText().toString();
        str_amount = et_enter_amount.getText().toString();

        int amount = 0;
        try {

            amount = Integer.parseInt(et_enter_amount.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }



        if (str_mobileno.length() < mobile_length || str_mobileno.length() > mobile_length) {
            getResponseDialog(getString(R.string.error_phone_length));

            return false;
        }
        else if (amount < min_gift_amount || amount > max_gift_amount) {
            getResponseDialog(getString(R.string.amount_length) + min_gift_amount + getString(R.string.and) + max_gift_amount);


                return false;
            }
        else if (amount>WalletCustomerBalance ) {
            getResponseDialog(getString(R.string.WalletBalance));


            return false;
        }

        else {

            tvexceptiongift.setText(null);
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
        }

        return true;
    }

    public boolean validate() {
        int mobile_length = 10;
        str_mobileno = et_mobileno.getText().toString();
        String DenominationsValues=spinnerDenominations.getSelectedItem().toString();
        if (str_mobileno.length() < mobile_length || str_mobileno.length() > mobile_length) {
            getResponseDialog(getString(R.string.error_phone_length));

            return false;
        }
        else if(spinnerDenominations.getSelectedItemPosition()==0){
            getResponseDialog(getString(R.string.select_denominations));

            return false;
        }
       else if (denoSpinnerAmount>WalletCustomerBalance ) {
            getResponseDialog(getString(R.string.WalletBalance));


            return false;
        }


        else {

            tvexceptiongift.setText(null);
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
        }

        return true;
    }

    private boolean validate_tpin() {
        string_tpin = et_tpin.getText().toString();

        if (string_tpin.length() < 8) {
            getResponseDialog(getString(R.string.error_tpin_length));


            return false;

        } else if (string_tpin.length() == 8) {



            GetValidPasswordPinClass getValidPasswordPinClass=new GetValidPasswordPinClass(getActivity(), getActivity(),string_tpin);
            String message=getValidPasswordPinClass.doInBackground().toLowerCase();


            if(message.equals(String_url.success))
            {

                String operator = spinnerGiftType.getSelectedItem().toString();


                final ConfrimRechargeDialog confrimRechargeDialog1= new ConfrimRechargeDialog(getActivity(),getActivity());
                confrimRechargeDialog1.showDialog(2,
                        getString(R.string.ConfirmTransfer),
                        getString(R.string.Operator),
                        operator,
                        getString(R.string.amount),
                        str_amount,
                        null,
                        null,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DoGiftVoucherTransactionbackground startRecharge = new DoGiftVoucherTransactionbackground();
                                startRecharge.execute();
                                confrimRechargeDialog1.dialog.dismiss();

                            }
                        },

                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                confrimRechargeDialog1.dialog.dismiss();
                                btnTpinSubmit.setEnabled(true);


                            }
                        }
                );
            }
            else
            {
                getResponseDialog(getString(R.string.invalid_tpin));


            }





        }

        return true;
    }

    private class DoGiftVoucherTransactionbackground extends AsyncTask<String, Integer, String> {
        String message, sTransactionId,MomStatusCode;
        int rechargeType = 13;
        String responseDoGiftVoucher;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   progressBarTpin.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                Gson gson=new GsonBuilder().create();
                GiftTransactionRequest giftTransactionRequest=new GiftTransactionRequest();
                Type type=new TypeToken<GiftTransactionRequest>(){}.getType();
                Encryption encryption=new Encryption(getActivity());


                giftTransactionRequest.setUserId(str_CustomerId);
                giftTransactionRequest.setCustomerMobileNumber(Long.valueOf(str_mobileno));
                giftTransactionRequest.setOperaterId(Integer.parseInt(str_ServiceProviderID));
                giftTransactionRequest.setTransactionAmount(Float.parseFloat(str_amount));
                giftTransactionRequest.setCircleId(String_url.circleId_int);
                giftTransactionRequest.setRechargeType(rechargeType);
                giftTransactionRequest.setTpin(encryption.SHA1(string_tpin));
                giftTransactionRequest.setChannelId(String_url.channelId);

                String json=gson.toJson(giftTransactionRequest,type);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.DoGiftVoucherTransaction);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.transaction_Request,json));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                responseDoGiftVoucher = EntityUtils.toString(entity);




            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseDoGiftVoucher;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<PaymentResponseResult>>(){}.getType();
                    ResponseBase<PaymentResponseResult> responseBase=gson.fromJson(s,type);
                    PaymentResponseResult paymentResponseResult=responseBase.responseData;

                    String message          = paymentResponseResult.momMessage;
                    MomStatusCode           = paymentResponseResult.momStatusCode;
                    String isoTransactionId = paymentResponseResult.isoTransactionId;

                    final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                    responseDialog1.showResponseDialog(
                            2,
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
                                        getBalanceClass.execute();
                                    }

                                    page1.setVisibility(View.VISIBLE);
                                    page2.setVisibility(View.GONE);



                                    spinnerGiftType.setSelection(0);
                                    et_mobileno.setText("");
                                    et_enter_amount.setText("");
                                    et_tpin.setText("");

                                    responseDialog1.dialog.dismiss();

                                }
                            }

                    );
                }


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }



          //  progressBarTpin.setVisibility(View.GONE);
            comProgressDialog.cancelProgress();
        }
    }

    private class GetDetailsGiftCardDenominationsBackground extends AsyncTask<String, Integer, String> {
        String denomination_response;
        float denominationAmount;
        List<String> branchNameResultList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.GetDetailsGiftCardDenominations);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.vendorId,String.valueOf(String_url.vendorId_int)));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceProviderId,str_ServiceProviderID));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                denomination_response = EntityUtils.toString(entity);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return denomination_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetDetailsGiftCardDenominationsResponse[]>>(){}.getType();
                    ResponseBase<GetDetailsGiftCardDenominationsResponse[]> responseBase=gson.fromJson(s,type);
                    map2 = new HashMap<String, List<String>>();
                    mapGift1 = new HashMap<String, Integer>();
                    if(responseBase.responseData!=null)
                    {
                        for(GetDetailsGiftCardDenominationsResponse getDetailsGiftCardDenominationsResponse:responseBase.responseData)
                        {
                            int denominationId=getDetailsGiftCardDenominationsResponse.denominationId;
                            int vendorId=getDetailsGiftCardDenominationsResponse.vendorId;
                            int serviceProviderId=getDetailsGiftCardDenominationsResponse.serviceProviderId;
                            denominationAmount=getDetailsGiftCardDenominationsResponse.denominationAmount;
                            String skuId=getDetailsGiftCardDenominationsResponse.skuId;
                            String denominationDescription=getDetailsGiftCardDenominationsResponse.denominationDescription;
                            int denominationStatus=getDetailsGiftCardDenominationsResponse.denominationStatus;
                            String vendorName=getDetailsGiftCardDenominationsResponse.vendorName;
                            String serviceProviderName=getDetailsGiftCardDenominationsResponse.serviceProviderName;

                            int denominationAmount_int=(int) denominationAmount;

                            List<String> gifset=new ArrayList<String>();
                            gifset.add(String.valueOf(denominationAmount_int));
                            gifset.add(String.valueOf(skuId));
                            map2.put(String.valueOf(denominationAmount_int),gifset);
                            DenominationsList.add(String.valueOf(denominationAmount_int));
                            getDetailsGiftCardDenominationsID.storeDenominationsList(String_url.payment_mode, map2);
                            getDetailsGiftCardDenominationsID.storeDenominationsDetail(String_url.payment_id, DenominationsList);

                            branchNameResultList = getDetailsGiftCardDenominationsID.getDenominationsDetail();
                        }
                        ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNameResultList) {
                            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                //  tfavv = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avvaiyar.ttf");
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(Light);
                                v.setTextColor(Color.parseColor(String_url.spinnerDropDownTextColor));
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
                        spinnerDenominations.setAdapter(dataAdapter_res);
                    }
                    else{
                        DenominationsList.clear();
                        DenominationsList.add(getString(R.string.noDenominations));
                        ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, DenominationsList) {
                            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                //  tfavv = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avvaiyar.ttf");
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(Light);
                                v.setTextColor(Color.parseColor(String_url.spinnerDropDownTextColor));
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
                        spinnerDenominations.setAdapter(dataAdapter_res);


                    }
                }

            }
            catch(Exception e){
                 e.printStackTrace();
            }

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
