package com.mom.app.retail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class LoadMoney extends Fragment {

    private TextView tvImageText,tvLoadMoneyHeader,tvexceptionloadmoney , tvClear;
    private EditText etCustomerName,etCustomerEmailID,etAmount;
    private Button btnSubmit;
    private ImageView imageView;
    private String str_CustomerName,str_CustomerEmailID,str_Amount;
    private RadioGroup rGroup;
    private RadioButton _rBtn50,_rBtn100,_rBtn500;
    public static final String PREFS_NAME = "MyApp_Settings";
    Typeface SemiBold,Light,Normal;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_loadmoney,container,false);


//        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
//        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
//        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

        Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

        imageView=(ImageView)view.findViewById(R.id.img_image);
        imageView.setBackgroundResource(R.drawable.allscreen);

         rGroup  =(RadioGroup)view.findViewById(R.id.rdGrp);
         rGroup.setVisibility(View.VISIBLE);
        _rBtn50     =(RadioButton)view.findViewById(R.id.rbtn_Amount50);
        _rBtn100    =(RadioButton)view.findViewById(R.id.rbtn_Amount100);
        _rBtn500    =(RadioButton)view.findViewById(R.id.rbtn_Amount500);
        tvImageText=(TextView)view.findViewById(R.id.tvLoadMoneyImageText);
        tvImageText.setTypeface(Light);

        tvexceptionloadmoney=(TextView)view.findViewById(R.id.tvexceptionloadmoney);
        tvexceptionloadmoney.setTypeface(Light);

        tvLoadMoneyHeader=(TextView) view.findViewById(R.id.tvLoadMoneyHeader);
        tvLoadMoneyHeader.setTypeface(Light);

        _rBtn50.setTypeface(Light);
        _rBtn100.setTypeface(Light);
        _rBtn500.setTypeface(Light);



        etAmount=(EditText) view.findViewById(R.id.etAmount);
        etAmount.setTypeface(Light);
        etAmount.setImeOptions(EditorInfo.IME_ACTION_DONE);
        _rBtn50.setClickable(true);
        _rBtn100.setClickable(true);
        _rBtn500.setClickable(true);


        //by default set the value of edit and check the radio button starts

        etAmount.setText(null);
        _rBtn50.setChecked(true);


        int id= rGroup.getCheckedRadioButtonId();
        View radioButton = rGroup.findViewById(id);
        int radioId = rGroup.indexOfChild(radioButton);
        RadioButton btn = (RadioButton) rGroup.getChildAt(radioId);
        String selection = (String) btn.getText();
        int selectedAmount = Integer.parseInt(selection);

        etAmount.setText(selection);

        //by default set the value of edit and check the radio button ends

        tvClear = (TextView)view.findViewById(R.id.tvClear);
        tvClear.setTypeface(Light);
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAmount.setText(null);
                _rBtn50.setChecked(true);

                int id= rGroup.getCheckedRadioButtonId();
                View radioButton = rGroup.findViewById(id);
                int radioId = rGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) rGroup.getChildAt(radioId);
                String selection = (String) btn.getText();
                int selectedAmount = Integer.parseInt(selection);

                if(etAmount.getText().toString().equals("")){
                    etAmount.setText(selection);

                }


            }
        });


        btnSubmit=(Button)view.findViewById(R.id.btnLoadMoneySubmit);
        btnSubmit.setTypeface(Normal);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.setEnabled(false);
                validate();
            }
        });

      _rBtn50.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(rGroup.getCheckedRadioButtonId()!=-1){
                  int id= rGroup.getCheckedRadioButtonId();
                  View radioButton = rGroup.findViewById(id);
                  int radioId = rGroup.indexOfChild(radioButton);
                  RadioButton btn = (RadioButton) rGroup.getChildAt(radioId);
                  String selection = (String) btn.getText();
                  int selectedAmount = Integer.parseInt(selection);


                  if(etAmount.getText().toString().equals("")){
                      etAmount.setText(selection);

                  }
                  else{
                      String sAmount = etAmount.getText().toString();
                      int previousAmount = Integer.parseInt(sAmount);
                      int totalAmount = previousAmount+selectedAmount;
                      etAmount.setText(String.valueOf(totalAmount));

                  }


              }
          }
      });

        _rBtn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rGroup.getCheckedRadioButtonId()!=-1){
                    int id= rGroup.getCheckedRadioButtonId();
                    View radioButton = rGroup.findViewById(id);
                    int radioId = rGroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) rGroup.getChildAt(radioId);
                    String selection = (String) btn.getText();
                    int selectedAmount = Integer.parseInt(selection);


                    if(etAmount.getText().toString().equals("")){
                        etAmount.setText(selection);

                    }
                    else{
                        String sAmount = etAmount.getText().toString();
                        int previousAmount = Integer.parseInt(sAmount);
                        int totalAmount = previousAmount+selectedAmount;
                        etAmount.setText(String.valueOf(totalAmount));

                    }


                }
            }
        });

        _rBtn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rGroup.getCheckedRadioButtonId()!=-1){
                    int id= rGroup.getCheckedRadioButtonId();
                    View radioButton = rGroup.findViewById(id);
                    int radioId = rGroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) rGroup.getChildAt(radioId);
                    String selection = (String) btn.getText();
                    int selectedAmount = Integer.parseInt(selection);


                    if(etAmount.getText().toString().equals("")){
                        etAmount.setText(selection);

                    }
                    else{
                        String sAmount = etAmount.getText().toString();
                        int previousAmount = Integer.parseInt(sAmount);
                        int totalAmount = previousAmount+selectedAmount;
                        etAmount.setText(String.valueOf(totalAmount));

                    }

                }
            }
        });


        return view;


    }

    public boolean validate() {
        int min_load_amount=10;
        int max_load_amount=10000;

        str_Amount = etAmount.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int amount=0;
        try {

            amount = Integer.parseInt(etAmount.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }



        if (str_Amount.trim().equals("")) {


            getResponseDialog(getString(R.string.error_loadmoney_amount));
            return false;
        }
       else  if(amount<min_load_amount||amount>max_load_amount)
        {
            getResponseDialog(getString(R.string.Amount_should_be_between) +" Rs. "+ min_load_amount+getString(R.string.and)+" Rs. "+max_load_amount);
            _rBtn50.setClickable(false);
            _rBtn100.setClickable(false);
            _rBtn500.setClickable(false);
            return false;
        }

        else
        {
            startPayment();


        }
        return true;
    }

    private void startPayment() {

        SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
       
        Intent myintent = new Intent(getActivity(), WebViewActivity.class);
        myintent.putExtra(String_url.PARAM_NEW_STR_NAME, shared.getString(String_url.firstName, null));
        myintent.putExtra(String_url.PARAM_NEW_STR_EMAIL,String_url.EMAIL_ID);
        myintent.putExtra(String_url.PARAM_NEW_STR_AMOUNT, etAmount.getText().toString());
        myintent.putExtra(String_url.PARAM_NEW_STR_MOBILE_NUMBER_PAY_U, shared.getLong(String_url.MobileNumber, 0));
        startActivity(myintent);
        etAmount.setText(null);
        btnSubmit.setEnabled(true);
        GetBalanceClass getBalanceClass = new GetBalanceClass(getActivity(), getActivity());
        getBalanceClass.execute();




    }

    public void getResponseDialog(String response_message) {
        final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
        responseDialog1.showResponseDialog(1,
                getString(R.string.message),
                null, null, null, null,
                response_message,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnSubmit.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }

}

