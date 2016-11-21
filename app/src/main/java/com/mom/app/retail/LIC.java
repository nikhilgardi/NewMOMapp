package com.mom.app.retail;

import android.graphics.Typeface;
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
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;


public class LIC extends Fragment {

    private EditText etPolicyNo;
    private Button btnSubmit,btnCashColl,btnCancel1,btnCancelBack;
    private TextView tvPolicyHolder,tvPremiumDate,tvFrom,tvTo,tvTransactionSuccess,tvLicText,tvexceptionlic;
    String str_policynumber,str_mobilenumber;
    private AutoCompleteTextView etMobNo=null;

    private SQLiteAdapter sqLiteAdapter;
    private ArrayAdapter<String> adapter;

    private String[] contacts;


    private RelativeLayout page1,page2,page3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lic, container, false);



        NetworkConnection networkConnection=new NetworkConnection(getActivity());
        boolean isNetworkAvailable=networkConnection.isNetworkAvailable();

        if(!isNetworkAvailable)
        {
            NetworkConnection.ExitAppDialog(getActivity());
        }
        else {

//            Typeface Light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
//            Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
//            Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Condensed.ttf");

            Typeface Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
            Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);



            etPolicyNo = (EditText) v.findViewById(R.id.etPolicyNo);
            etPolicyNo.setTypeface(Condensed);
            etMobNo = (AutoCompleteTextView) v.findViewById(R.id.autoCustomerNo);
            etMobNo.setTypeface(Condensed);
            btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
            btnSubmit.setTypeface(Condensed);
            etMobNo.setImeOptions(EditorInfo.IME_ACTION_DONE);


            btnCancel1 = (Button) v.findViewById(R.id.btnPage2Cancel);
            btnCancel1.setTypeface(Condensed);
            btnCancelBack = (Button) v.findViewById(R.id.btnCancelBack);
            btnCancelBack.setTypeface(Condensed);
            btnCashColl = (Button) v.findViewById(R.id.btnCashCollected);
            btnCashColl.setTypeface(Condensed);
            tvPolicyHolder = (TextView) v.findViewById(R.id.tvPolicyHolder);
            tvPremiumDate = (TextView) v.findViewById(R.id.tvPremiumDate);
            tvPremiumDate.setTypeface(Condensed);
            tvPolicyHolder.setTypeface(Condensed);
            tvFrom = (TextView) v.findViewById(R.id.tvFrom);
            tvFrom.setTypeface(Condensed);
            tvTo = (TextView) v.findViewById(R.id.tvTo);
            tvTo.setTypeface(Condensed);
            tvLicText = (TextView) v.findViewById(R.id.tv_LicText);
            tvLicText.setTypeface(Thin);
            tvTransactionSuccess = (TextView) v.findViewById(R.id.tvTransaction_successful);
            tvTransactionSuccess.setTypeface(Condensed);
            tvexceptionlic = (TextView) v.findViewById(R.id.tvexceptionlic);
            tvexceptionlic.setTypeface(Condensed);


            page1 = (RelativeLayout) v.findViewById(R.id.page1);
            page2 = (RelativeLayout) v.findViewById(R.id.page2);
            page3 = (RelativeLayout) v.findViewById(R.id.page3);

            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());

            etMobNo.setThreshold(2);
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

                    //adapterView.getSelectedItem();

                    etMobNo.setText(upToNCharacters);
                    //         Log.e("NumberTest1", phoneValueArr.get(i));

                }
            });
            sqLiteAdapter.close();

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validate();

                    // page1.setVisibility(View.GONE);
                    //page2.setVisibility(View.VISIBLE);


                }
            });

            btnCashColl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    page1.setVisibility(View.GONE);
                    page2.setVisibility(View.GONE);
                    page3.setVisibility(View.VISIBLE);


                }
            });

            btnCancelBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    page1.setVisibility(View.VISIBLE);
                    page2.setVisibility(View.GONE);
                    page3.setVisibility(View.GONE);


                }
            });

            btnCancel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    page1.setVisibility(View.VISIBLE);
                    page2.setVisibility(View.GONE);
                    page3.setVisibility(View.GONE);


                }
            });


        }
        return v;
    }
    public boolean validate() {
        int policynumberMinLength = 10;
        int policynumberMaxLength = 10;
        int mobilenumberMinLength = 10;
        int mobilenumberMaxLength = 10;

        int policynumberLength = etPolicyNo.getText().toString().trim().length();
        int mobilenumberLength = etMobNo.getText().toString().trim().length();

        str_policynumber = etPolicyNo.getText().toString();
        str_mobilenumber = etMobNo.getText().toString();

        if (str_policynumber.trim().equals("")) {

//            tvException.setText((R.string.error_oldmpin_required));
            tvexceptionlic.setText(getString(R.string.error_policy_number));
            return false;
        } else if (policynumberLength < policynumberMinLength || policynumberLength > policynumberMaxLength) {
            //tvException.setError(String.format(getString(R.string.error_oldmpin_length), nMinLength));
            tvexceptionlic.setText(getString(R.string.error_policy_number));
            return false;
        } else if (str_mobilenumber.trim().equals("")) {

            //et_new_mpin.setError(getString(R.string.error_newmpin_required));
            tvexceptionlic.setText(getString(R.string.error_phone_length));
            return false;
        } else if (mobilenumberLength < mobilenumberMinLength || mobilenumberLength > mobilenumberMaxLength) {
            // et_new_mpin.setError(String.format(getString(R.string.error_newmpin_length), nMinLength));
            tvexceptionlic.setText(getString(R.string.error_phone_length));
            return false;
        }

        else
        {
            tvexceptionlic.setText("");
             page1.setVisibility(View.GONE);
             page2.setVisibility(View.VISIBLE);
            return true;

        }
    }
}
