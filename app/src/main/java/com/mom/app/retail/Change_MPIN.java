package com.mom.app.retail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mom.app.retail.Request.GetPasswordChangeRequest;
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

 public class Change_MPIN extends Fragment {

    private ImageView imageView;
    private TextView tvSettingmageText,tv_change_mpin,tvException;
    private EditText et_old_mpin,et_new_mpin,et_con_mpin;
    private Button btn_submit;
    private String str_oldmpin,str_newmpin,str_conmpin;
    
    public static final String PREFS_NAME = "MyApp_Settings";
    long customerAuthenticationID;
    private ProgressBar progressBar;
     ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_change__mpin,container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
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
           

            Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            imageView=(ImageView)v.findViewById(R.id.ivChangeMpin);
            imageView.setBackgroundResource(R.drawable.allscreen);
            tvSettingmageText=(TextView)v.findViewById(R.id.tvSettingmageText);
            tv_change_mpin=(TextView)v.findViewById(R.id.tv_change_mpin);
            et_old_mpin=(EditText)v.findViewById(R.id.et_old_mpin);
            et_new_mpin=(EditText)v.findViewById(R.id.et_new_mpin);
            et_con_mpin=(EditText)v.findViewById(R.id.et_con_mpin);
            btn_submit=(Button)v.findViewById(R.id.btn_submit);
            progressBar=(ProgressBar)v.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            tvSettingmageText.setTypeface(Light);
            tv_change_mpin.setTypeface(Light);
            et_old_mpin.setTypeface(Light);
            et_new_mpin.setTypeface(Light);
            et_con_mpin.setTypeface(Light);
            btn_submit.setTypeface(Normal);

            tvException =(TextView)v.findViewById(R.id.tvexceptionmpin);
            tvException.setTypeface(Light);

            et_con_mpin.setImeOptions(EditorInfo.IME_ACTION_DONE);

            progressDialog = new ProgressDialog(getActivity());

            SharedPreferences CustomerMainAccountBalance=getActivity().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

            customerAuthenticationID=CustomerMainAccountBalance.getLong(String_url.CustomerAuthenticationID,0);
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_submit.setEnabled(false);
                    validate();
                }
            });
        }


        return v;
    }

    public boolean validate() {
        int nMinLength = 8;
        int nMaxLength = 8;
        int nold_mpinLength = et_old_mpin.getText().toString().trim().length();
        int nnew_mpinLength = et_new_mpin.getText().toString().trim().length();
        int ncon_mpinLength = et_con_mpin.getText().toString().trim().length();
        str_oldmpin = et_old_mpin.getText().toString();
        str_newmpin = et_new_mpin.getText().toString();
        str_conmpin = et_con_mpin.getText().toString();
        if (str_oldmpin.trim().equals("")) {


            getResponseDialog(getString(R.string.error_oldmpin_required));
            return false;
        } else if (nold_mpinLength < nMinLength || nold_mpinLength > nMaxLength) {

            getResponseDialog(getString(R.string.error_oldmpin_length));
            return false;
        } else if (str_newmpin.trim().equals("")) {


            getResponseDialog(getString(R.string.error_newmpin_required));
            return false;
        } else if (nnew_mpinLength < nMinLength || nnew_mpinLength > nMaxLength) {

            getResponseDialog(getString(R.string.error_newmpin_length));
            return false;
        }
        else if(str_oldmpin.equals(str_newmpin))
        {
            getResponseDialog(getString(R.string.error_old_newmpin));
            return false;
        }
        else if (str_conmpin.trim().equals("")) {


            getResponseDialog(getString(R.string.error_conmpin_required));
            return false;
        } else if (ncon_mpinLength < nMinLength || ncon_mpinLength > nMaxLength) {

            getResponseDialog(getString(R.string.error_conmpin_length));
            return false;
        } else if (!str_newmpin.equals(str_conmpin)) {

            getResponseDialog(getString(R.string.error_new_confirmmpin));
            return false;
        } else if (!str_conmpin.equals(str_newmpin)) {

            getResponseDialog(getString(R.string.error_new_confirmmpin));
            return false;
        }
        else
        {

            final ConfrimRechargeDialog confrimRechargeDialog1 = new ConfrimRechargeDialog(getActivity(), getActivity());
            confrimRechargeDialog1.showDialog(1,
                    getString(R.string.con_mpin_dialog),
                    getString(R.string.mpin_exit),
                    null,
                    null,
                    null,
                    null,
                    null,

                    new View.OnClickListener()

                    {
                        @Override
                        public void onClick(View v) {

                            confrimRechargeDialog1.dialog.dismiss();
                            GetPasswordChange getPasswordChange = new GetPasswordChange();
                            getPasswordChange.execute();
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confrimRechargeDialog1.dialog.dismiss();
                            btn_submit.setEnabled(true);

                        }
                    }
            );

            return true;

        }
    }

    private class GetPasswordChange extends AsyncTask<String,Integer,String>
    {
        String message_password,id,change_password_response,sOldMpin,sNewMpin;
        long CustomerAuthenticationId,customerID;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            CustomerAuthenticationId=sharedPreferences.getLong(String_url.CustomerAuthenticationID, 0);
            customerID=sharedPreferences.getLong(String_url.CustomerID,0);
            sOldMpin=et_old_mpin.getText().toString();
            sNewMpin=et_new_mpin.getText().toString();


            progressDialog.setMessage("Please wait...");
            progressDialog.show();


        }
        @Override
        protected String doInBackground(String... params)
        {

            try
            {

                Gson gson = new GsonBuilder().create();
                GetPasswordChangeRequest getPasswordChangeRequest = new GetPasswordChangeRequest();
                Type type = new TypeToken<GetPasswordChangeRequest>() {}.getType();

                Encryption encryption= new Encryption(getActivity());

                getPasswordChangeRequest.setCustomerAuthenticationId(CustomerAuthenticationId);
                getPasswordChangeRequest.setOldPassword(encryption.SHA1(sOldMpin));
                getPasswordChangeRequest.setNewPassword(encryption.SHA1(sNewMpin));
                getPasswordChangeRequest.setPasswordType(String_url.MPIN_password);
                getPasswordChangeRequest.setAuthorizedRole(String_url.AuthorizedRole);
                getPasswordChangeRequest.setServiceChannelId(String_url.str_servicechannelid);
                getPasswordChangeRequest.setRequestCustomerId(customerID);


                String json = gson.toJson(getPasswordChangeRequest, type);

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetPasswordChange);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.changePasswordRequest, json));




                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                change_password_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return change_password_response;
        }
        @Override
        protected void onPostExecute(String s) {

            try {
                if (s != null) {

                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>() {}.getType();
                    ResponseBase responseBase = gson.fromJson(s, type);

                    message_password = responseBase.message.toLowerCase();
                    id = String.valueOf(responseBase.id);

                    String message;
                    if (message_password.equals(String_url.success)) {
                        message = getString(R.string.successMPin);
                    } else {
                        message = getString(R.string.message).concat(":").concat(message_password);

                    }

                    final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                    responseDialog1.showResponseDialog(1,
                            getString(R.string.Response),
                            null, null, null, null,
                            message,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    et_old_mpin.setText("");
                                    et_new_mpin.setText("");
                                    et_con_mpin.setText("");
                                    btn_submit.setEnabled(true);
                                    responseDialog1.dialog.dismiss();
                                }
                            }


                    );

                }

                progressDialog.dismiss();
            }
            catch (Exception e)
            {e.printStackTrace();}


        }

    }

    public void getResponseDialog(String response_message)
    {
        final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
        responseDialog1.showResponseDialog(1,
                getString(R.string.message),
                null, null, null, null,
                response_message,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_submit.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }


}
