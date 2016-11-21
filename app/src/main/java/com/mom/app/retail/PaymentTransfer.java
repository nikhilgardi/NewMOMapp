package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mom.app.retail.Response.PaymentRequestResult;
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


public class PaymentTransfer extends Fragment {
    private TextView tvImageText,tvBalTransferHeader,tvexceptionbalancetransfer;
    private EditText etAmount,et_enter_transremark;
    private Button btnTransfer;
    private ImageView imageView;
    private String str_CustomerMobNo,str_Amount,str_transremark,CustomerLoginId;
    public static final String PREFS_NAME = "MyApp_Settings";
   
    long CustomerID;

    private AutoCompleteTextView etCustomerMobNo=null;

    private SQLiteAdapter sqLiteAdapter;
    private ArrayAdapter<String> adapter;

    private String[] contactArray;
    private ProgressBar progressbarTpin;
    Typeface SemiBold,Light,Normal;

    ComProgressDialog comProgressDialog;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_balancetransfer, container, false);
        NetworkConnection networkConnection = new NetworkConnection(getActivity());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();

        if (!isNetworkAvailable) {
            NetworkConnection.ExitAppDialog(getActivity());
        } else {
           
//            Typeface Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
//            Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Normal);
//            Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Semibold);

            comProgressDialog = new ComProgressDialog(getActivity(),getActivity());

            Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            imageView=(ImageView)view.findViewById(R.id.img_image);
            imageView.setBackgroundResource(R.drawable.allscreen);
            tvImageText=(TextView)view.findViewById(R.id.tvBalanceTransferImageText);
            tvImageText.setTypeface(Light);
            tvBalTransferHeader=(TextView) view.findViewById(R.id.tvBalanceTransferHeader);
            tvBalTransferHeader.setTypeface(Light);
            etCustomerMobNo=(AutoCompleteTextView) view.findViewById(R.id.autoCustomerNo);
            etCustomerMobNo.setTypeface(Light);
            etAmount=(EditText) view.findViewById(R.id.etAmount);
            etAmount.setTypeface(Light);
            et_enter_transremark=(EditText) view.findViewById(R.id.et_enter_transremark);
            et_enter_transremark.setTypeface(Light);
            btnTransfer=(Button)view.findViewById(R.id.btnTransfer);
            btnTransfer.setTypeface(Normal);
            tvexceptionbalancetransfer=(TextView)view.findViewById(R.id.tvexceptionbalancetransfer);
            tvexceptionbalancetransfer.setTypeface(Light);

            progressbarTpin=(ProgressBar)view.findViewById(R.id.progressbarTpin);
            progressbarTpin.setVisibility(View.INVISIBLE);

            et_enter_transremark.setImeOptions(EditorInfo.IME_ACTION_DONE);
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());

            etCustomerMobNo.setThreshold(2);
            sqLiteAdapter= new SQLiteAdapter(getActivity());

            sqLiteAdapter.openToRead();

            contactArray=sqLiteAdapter.getContacts();
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line,contactArray);
            etCustomerMobNo.setAdapter(adapter);



            etCustomerMobNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s=adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));

                    //adapterView.getSelectedItem();

                    etCustomerMobNo.setText(upToNCharacters);
                    //         Log.e("NumberTest1", phoneValueArr.get(i));

                }
            });
            sqLiteAdapter.close();



            btnTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnTransfer.setEnabled(false);
                    validate();
                }
            });

            SharedPreferences CustomerMainAccountBalance=getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            CustomerID=CustomerMainAccountBalance.getLong(String_url.CustomerID,0);

            et_enter_transremark.setHorizontallyScrolling(false);
            et_enter_transremark.setMaxLines(Integer.MAX_VALUE);

        }

       return view;
    }
    public boolean validate() {
        int nMinLength          = 10;
        int nMaxLength          = 10;
        int min_balance_amount=10;
        int max_balance_amount=15000;
        int nCustomerMobNoLength   = etCustomerMobNo.getText().toString().trim().length();
        str_CustomerMobNo = etCustomerMobNo.getText().toString();
        str_Amount = etAmount.getText().toString();
        str_transremark = et_enter_transremark.getText().toString();
        int amount=0;
        try
        {
            amount=Integer.parseInt(etAmount.getText().toString());

        }
        catch(NumberFormatException e)
        {
            System.out.println("could not parse"+e);
        }

        if (str_CustomerMobNo.trim().equals("")) {
            getResponseDialog(getString(R.string.error_balancemobno));
            //etCustomerMobNo.setError(getResources().getString(R.string.error_balancemobno));
           // tvexceptionbalancetransfer.setText(getString(R.string.error_balancemobno));
            return false;
        }
        else if(nCustomerMobNoLength < nMinLength || nCustomerMobNoLength > nMaxLength){
            getResponseDialog(getString(R.string.error_phone_length));
            //etCustomerMobNo.setError(String.format(getResources().getString(R.string.error_phone_length), nMinLength));
            //tvexceptionbalancetransfer.setText(getString(R.string.error_phone_length));
            return false;
        }
        else if (str_Amount.trim().equals("")) {
            getResponseDialog(getString(R.string.error_balancemoney_amount));

           // etAmount.setError(getResources().getString(R.string.error_balancemoney_amount));
           // tvexceptionbalancetransfer.setText(getString(R.string.error_balancemoney_amount));
            return false;
        }


        if(amount<min_balance_amount||amount>max_balance_amount)
        {
            getResponseDialog(getString(R.string.amount_length)+min_balance_amount+getString(R.string.and)+max_balance_amount);
           // tvexceptionbalancetransfer.setText(getString(R.string.amount_length)+min_balance_amount+getString(R.string.and)+max_balance_amount);

            // tvExepPaymentRequest.setText(getString(R.string.amount_length)+min_payment_amount+getString(R.string.and)+max_payment_amount);

            return false;
        }
        else if (str_transremark.trim().equals("")) {
            getResponseDialog(getString(R.string.enter_transremark));
            // etAmount.setError(getResources().getString(R.string.error_balancemoney_amount));
            //tvexceptionbalancetransfer.setText(getString(R.string.enter_transremark));
            return false;
        }
        else
        {



            tvexceptionbalancetransfer.setText("");

            final ConfrimRechargeDialog confrimRechargeDialog= new ConfrimRechargeDialog(getActivity(),getActivity());
            confrimRechargeDialog.showDialog(2,
                    getString(R.string.ConfirmTransfer),
                    getString(R.string.mobile_number),
                    str_CustomerMobNo,
                    getString(R.string.amount),
                    str_Amount,
                    null,
                    null,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InternalPaymentTransferRequestBackground internalPaymentTransferRequestBackground = new InternalPaymentTransferRequestBackground();
                            internalPaymentTransferRequestBackground.execute();
                            confrimRechargeDialog.dialog.dismiss();
                            btnTransfer.setEnabled(true);
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confrimRechargeDialog.dialog.dismiss();
                            btnTransfer.setEnabled(true);
                        }
                    }


            );




        }

        return true;
    }

    private class InternalPaymentTransferRequestBackground extends AsyncTask<Void,Void,String> {
        String res, a = null;
        String BalanceTransfer_response;
        int id;
        String message;
        String sTransactionId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressbarTpin.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {


                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.InternalPaymentTransferRequest);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.PayerId, String.valueOf(CustomerID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.TransferAmount, str_Amount));
                nameValuePairs.add(new BasicNameValuePair(String_url.PayeeMobileNumber, str_CustomerMobNo));
                nameValuePairs.add(new BasicNameValuePair(String_url.TransferType, String.valueOf(String_url.str_transactiontype)));
                nameValuePairs.add(new BasicNameValuePair(String_url.TransactionRequestedBy, String.valueOf(CustomerID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceChannelId, String.valueOf(String_url.str_servicechannelid)));
                nameValuePairs.add(new BasicNameValuePair(String_url.TransactionRemarks, str_transremark));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                BalanceTransfer_response = EntityUtils.toString(entity);



            } catch (Exception exception) {
                exception.printStackTrace();
                //response=exception.toString();
            }
            return BalanceTransfer_response;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                if (s != null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<PaymentRequestResult>() {
                    }.getType();
                    PaymentRequestResult paymentRequestResult = gson.fromJson(s, type);
                    message = paymentRequestResult.message;
                    if (paymentRequestResult.responseData != null) {
                        sTransactionId = paymentRequestResult.responseData;

                        final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                        responseDialog1.showResponseDialog(1,
                                getString(R.string.Response),
                                null, null, null, null,
                                message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        responseDialog1.dialog.dismiss();
                                    }
                                }


                        );

                        etCustomerMobNo.setText("");
                        etAmount.setText("");
                        et_enter_transremark.setText("");

                      //  progressbarTpin.setVisibility(View.GONE);
                        comProgressDialog.cancelProgress();

                    } else {
                        final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                        responseDialog1.showResponseDialog(1,
                                getString(R.string.Response),
                                null, null, null, null,
                                message,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        responseDialog1.dialog.dismiss();
                                    }
                                }


                        );
                    }

                }
            } catch (Exception e) {
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
                        btnTransfer.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }
}
