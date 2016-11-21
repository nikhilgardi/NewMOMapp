package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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
import android.widget.TextView;

import com.mom.app.retail.Request.TabCabTransactionRequest;
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
import java.util.List;

public class Tab_Cabfragment extends Fragment {


    private TextView tvexceptiontabcab,tv_tabcab;
    private EditText et_sathi_id,et_sathi_amountno,et_tpin;
    private Button btn_cabsubmit,btnTpinSubmit,btnTpinCancel;
    String  str_sathi_id,str_sathi_mobno,str_sathi_amountno,string_tpin,MomStatusCode,str_serviceChargeAmount,title;
    private RelativeLayout page1;
    private LinearLayout page2;
   
    public static final String PREFS_NAME = "MyApp_Settings";
    private AutoCompleteTextView et_enter_sathi_mobno=null;
    private ArrayAdapter<String> adapter;
    private SQLiteAdapter sqLiteAdapter;
    private String[] contacts;
    ProgressBar progressbar;
    private long str_CustomerId;

    double wallet_balance;
    int WalletCustomerBalance;

    ComProgressDialog comProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab__cabfragment, container, false);

        //    setHasOptionsMenu(true);

        NetworkConnection networkConnection=new NetworkConnection(getActivity());
        boolean isNetworkAvailable=networkConnection.isNetworkAvailable();

        if(!isNetworkAvailable)
        {
            NetworkConnection.ExitAppDialog(getActivity());
        }

        else {


            comProgressDialog   = new ComProgressDialog(getActivity(),getActivity());
          
            final Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            final Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            final Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            str_CustomerId = shared.getLong(String_url.CustomerID, 0);
            sqLiteAdapter= new SQLiteAdapter(getActivity());
            sqLiteAdapter.openToReadBalanceDataBase();

            wallet_balance=sqLiteAdapter.getBalanceFromDB();
            sqLiteAdapter.close();


            WalletCustomerBalance = (int) wallet_balance;

            page1 = (RelativeLayout) v.findViewById(R.id.page1);
            page2 = (LinearLayout) v.findViewById(R.id.page2);


            progressbar=(ProgressBar)v.findViewById(R.id.progressbar);
            progressbar.setVisibility(View.INVISIBLE);

            tv_tabcab=(TextView)v.findViewById(R.id.tv_tabcab);
            tvexceptiontabcab=(TextView)v.findViewById(R.id.tvexceptiontabcab);
            et_sathi_id=(EditText)v.findViewById(R.id.et_sathi_id);
            et_enter_sathi_mobno=(AutoCompleteTextView)v.findViewById(R.id.et_enter_sathi_mobno);
            et_sathi_amountno=(EditText)v.findViewById(R.id.et_sathi_amountno);
            et_tpin=(EditText)v.findViewById(R.id.et_tpin);
            btn_cabsubmit=(Button)v.findViewById(R.id.btn_cabsubmit);
            btnTpinSubmit=(Button)v.findViewById(R.id.btnTpinSubmit);
            btnTpinCancel=(Button)v.findViewById(R.id.btnTpinCancel);
            tvexceptiontabcab.setTypeface(Light);
            tv_tabcab.setTypeface(Light);
            et_sathi_id.setTypeface(Light);
            et_enter_sathi_mobno.setTypeface(Light);
            et_sathi_amountno.setTypeface(Light);
            et_tpin.setTypeface(Light);
            et_tpin.setImeOptions(EditorInfo.IME_ACTION_DONE);
            et_sathi_amountno.setImeOptions(EditorInfo.IME_ACTION_DONE);
            btn_cabsubmit.setTypeface(Normal);
            btnTpinSubmit.setTypeface(Normal);
            btnTpinCancel.setTypeface(Normal);

            title=getString(R.string.tab_cab);
            btn_cabsubmit.setOnClickListener(new View.OnClickListener() {
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
                    et_tpin.setText("");
                }
            });
            btnTpinCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    page1.setVisibility(View.VISIBLE);
                    page2.setVisibility(View.GONE);

//                    et_sathi_id.setText("");
//                    et_sathi_amountno.setText("");
//                    et_enter_sathi_mobno.setText("");
//                    et_tpin.setText("");
                    tvexceptiontabcab.setText("");


                }
            });





            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());

            et_enter_sathi_mobno.setThreshold(2);
            sqLiteAdapter = new SQLiteAdapter(getActivity());

            sqLiteAdapter.openToRead();

            contacts = sqLiteAdapter.getContacts();
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, contacts);
            et_enter_sathi_mobno.setAdapter(adapter);


            et_enter_sathi_mobno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));

                    //adapterView.getSelectedItem();

                    et_enter_sathi_mobno.setText(upToNCharacters);
                    //         Log.e("NumberTest1", phoneValueArr.get(i));

                }
            });
            sqLiteAdapter.close();

        }
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        if(et_enter_sathi_mobno.getText().toString()!=null || et_sathi_id.getText().toString()!=null || et_sathi_amountno.getText().toString()!=null)
        {
            et_enter_sathi_mobno.setText(null);
            et_sathi_id.setText(null);
            et_sathi_amountno.setText(null);
        }
    }

    public boolean validate() {
        int mobile_length=10;
        int min_cab_amount=10;
        int max_cab_amount=10000;
        str_sathi_id=et_sathi_id.getText().toString();
        str_sathi_mobno=et_enter_sathi_mobno.getText().toString();
        str_sathi_amountno=et_sathi_amountno.getText().toString();
        int maxSathiID=10;
        int minSathiID=5;


        int amount=0;
        try {
            // mobile_number = Integer.parseInt(etMob.getText().toString());
            amount = Integer.parseInt(et_sathi_amountno.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if (str_sathi_id.trim().equals("")) {

            //  et_enter_occassion.setError(getString(R.string.error_giftoccas_required));
            getResponseDialog(getString(R.string.error_sathi_id_required));
            //tvexceptiontabcab.setText(getString(R.string.error_sathi_id_required));
            return false;
        }
        else if(str_sathi_id.length()<minSathiID || str_sathi_id.length()>maxSathiID)
        {
            String error=getString(R.string.error_sathiID_length).concat(String.valueOf(minSathiID)).concat("and").concat(String.valueOf(maxSathiID));
            getResponseDialog(error);
            //tvexceptiontabcab.setText(error);
            return false;
        }
        else  if(str_sathi_mobno.length()<mobile_length|| str_sathi_mobno.length()>mobile_length)
        {
            getResponseDialog(getString(R.string.error_phone_length));
           // tvexceptiontabcab.setText(getString(R.string.error_phone_length));

            return false;
        }


        else  if(amount<min_cab_amount||amount>max_cab_amount)
        {
            getResponseDialog(getString(R.string.amount_length)+min_cab_amount+getString(R.string.and)+max_cab_amount);
            //tvexceptiontabcab.setText(getString(R.string.amount_length)+min_cab_amount+getString(R.string.and)+max_cab_amount);

            return false;
        }
        else if (amount>WalletCustomerBalance ) {
            getResponseDialog(getString(R.string.WalletBalance));
            return false;
        }
        else
        {
            et_tpin.setText(null);
            tvexceptiontabcab.setText(null);
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
        }

        return true;
    }

    private boolean validate_tpin()
    {
        string_tpin=et_tpin.getText().toString();

        if(string_tpin.length()<8)
        {
            getResponseDialog(getString(R.string.error_tpin_length));

           // tvexceptiontabcab.setText(getString(R.string.error_tpin_length));

            return false;

        }
        else if(string_tpin.length()==8) {


            GetValidPasswordPinClass getValidPasswordPinClass = new GetValidPasswordPinClass(getActivity(), getActivity(), string_tpin);
            String message = getValidPasswordPinClass.doInBackground().toLowerCase();


            if (message.equals(String_url.success)) {


                final ConfrimRechargeDialog confrimRechargeDialog1 = new ConfrimRechargeDialog(getActivity(), getActivity());
                confrimRechargeDialog1.showDialog(3,
                        getString(R.string.ConfirmRecharge),
                        getString(R.string.sathi_id_response),
                        str_sathi_id,
                        getString(R.string.sathi_mobile_number),
                        str_sathi_mobno,
                        getString(R.string.amount),
                        str_sathi_amountno,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                StartRecharge();
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
            else {

                getResponseDialog(getString(R.string.invalid_tpin));
               // tvexceptiontabcab.setText(getString(R.string.invalid_tpin));
            }
        }

        return true;
    }

    private void StartRecharge()
    {


            int rechargeType = 12;


            Get_servicecharge get_servicecharge=new Get_servicecharge();
            String[] result=get_servicecharge.doInBackground();
            str_serviceChargeAmount=result[1];


            DoTabCabTransactionBackground doTabCabTransactionBackground=new DoTabCabTransactionBackground();
            doTabCabTransactionBackground.execute();




    }




    private class Get_servicecharge extends AsyncTask<String, Integer,String[]>
    {
        String id,amount;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


        }
        @Override
        protected String[] doInBackground(String... params)
        {
            String[] result = new String[2];

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.GetCustomerServiceCharge);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.customerId, String.valueOf(str_CustomerId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceProviderId, String.valueOf(String_url.tab_operaterId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceCircleId, "1"));
                nameValuePairs.add(new BasicNameValuePair(String_url.transactionAmount, str_sathi_amountno));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                String tabcabresponse = EntityUtils.toString(entity);


//                JSONObject jsonObject = new JSONObject(tabcabresponse);
//                amount=jsonObject.getString(String_url.message);
//                id=jsonObject.getString(String_url.id);

                Gson gson=new GsonBuilder().create();
                Type type=new TypeToken<ResponseBase>(){}.getType();
                ResponseBase responseBase=gson.fromJson(tabcabresponse,type);
                amount=responseBase.message;
                id=String.valueOf(responseBase.id);
                result[0]=id;
                result[1]=amount;

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);

        }



    }

    private class DoTabCabTransactionBackground extends AsyncTask<String,Integer,String>
    {
        String message,sTransactionId,responseTabcab;
        int     rechargeType = 12;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
           // progressbar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(String... params)
        {

           try
           {
               Gson gson=new GsonBuilder().create();
               TabCabTransactionRequest tabCabTransactionRequest=new TabCabTransactionRequest();
               Type type=new TypeToken<TabCabTransactionRequest>(){}.getType();
               Encryption encryption=new Encryption(getActivity());

               tabCabTransactionRequest.setUserId(str_CustomerId);
               tabCabTransactionRequest.setCustomerMobileNumber(Long.valueOf(str_sathi_mobno));
               tabCabTransactionRequest.setSathiId(str_sathi_id);
               tabCabTransactionRequest.setOperaterId(String_url.tab_operaterId);
               tabCabTransactionRequest.setTransactionAmount(Float.parseFloat(str_sathi_amountno));
               tabCabTransactionRequest.setServiceChargeAmount(Float.parseFloat(str_serviceChargeAmount));
               tabCabTransactionRequest.setCircleId(String_url.circleId_int);
               tabCabTransactionRequest.setRechargeType(rechargeType);
               tabCabTransactionRequest.setTpin(encryption.SHA1(string_tpin));
               tabCabTransactionRequest.setChannelId(String_url.channelId);


                String json=gson.toJson(tabCabTransactionRequest,type);

               HttpClient httpclient = new DefaultHttpClient();
               HttpPost httppost = new HttpPost(String_url.DoTabCabTransaction);
               List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(13);
               nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
               nameValuePairs.add(new BasicNameValuePair(String_url.transaction_Request,json));





               final HttpParams httpParams = httpclient.getParams();
               HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
               HttpConnectionParams.setSoTimeout(httpParams, 45000);
               httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

               // Execute HTTP Post Request

               HttpResponse response = httpclient.execute(httppost);

               HttpEntity entity = response.getEntity();
               responseTabcab = EntityUtils.toString(entity);





           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
            return responseTabcab;
        }
        @Override
        protected void onPostExecute(String s)
        {
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




                                et_sathi_id.setText("");
                                et_sathi_amountno.setText("");
                                et_tpin.setText("");
                                et_enter_sathi_mobno.setText("");

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





           // progressbar.setVisibility(View.GONE);
            comProgressDialog.cancelProgress();
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

