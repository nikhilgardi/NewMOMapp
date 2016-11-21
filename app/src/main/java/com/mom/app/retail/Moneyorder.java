package com.mom.app.retail;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mom.app.retail.Request.IMPSTransactionRequest;
import com.mom.app.retail.Response.CheckDuplicateBeneficiaryResponse;
import com.mom.app.retail.Response.GetBeneficiaryDetailsResponse;
import com.mom.app.retail.Response.GetBeneficiaryListResponse;
import com.mom.app.retail.Response.GetConsumerKYCLimitAndStatusResponse;
import com.mom.app.retail.Response.GetIFSCBanksResponse;
import com.mom.app.retail.Response.GetIFSCBranchResponse;
import com.mom.app.retail.Response.GetIFSCCityResponse;
import com.mom.app.retail.Response.GetIFSCStatesResponse;
import com.mom.app.retail.Response.GetRefundDetailsResponse;
import com.mom.app.retail.Response.IMPSResponse;
import com.mom.app.retail.Response.RegisterBeneficiaryResponse;
import com.mom.app.retail.Response.RegisterConsumerResponse;
import com.mom.app.retail.Response.ResponseBase;
import com.mom.app.retail.Response.SetBeneficiaryAsVerifiedResponse;
import com.mom.app.retail.Response.SubmitPaymentIPINResponse;
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
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Moneyorder extends Fragment {

    private ListView listView;
    private List<String> listBeneficiary, listGetIFSCBanks, listGetIFSCStates, listGetIFSCCity, listGetIFSCBranch, listpayfrom;
    private ArrayAdapter<String> arrayAdapter;
    private ImageView imageView;
    private TextView tvexceptionIFSCCODE, tv_MoneyOrderImageText, tv_mobile_money_order, tv_customerno, tv_available_limit, tv_fullkyc, or;
    private TextView confrim, tv_beneficiary_name, tv_beneficiary_number, tv_bank_ifsc_code, tv_amount, tv_processing_fee, tv_amount_payable,
            tv_ipin, transaction_successful, et_ifsc_select, resend_ipin;
    private EditText et_beneficiary_name, et_beneficiary_accountno, et_ifsc_code, et_beneficiary_mobno, et_amount, et_description, getbankname;
    private Button btn_send, btn_pay, btn_cancel, btn_reset_ipin, btn_pay1, btn_cancel1, new_transaction, payment_history,btnCreateUsercancel;
    private RadioButton rbtn_pay, rbtn_verfiy;
    public static final String PREFS_NAME = "MyApp_Settings";
    private RelativeLayout moneyOrderPage1, moneyOrderPage2, moneyOrderPage3, moneyOrderPage4, moneyOrderPage6;
    private String spinnerPayfrom, str_transition_description, str_amount, spinnerBenificiary, str_customerno, str_beneficiary_name, str_beneficiary_accountno, str_ifsc_code, str_ifsc_select, str_beneficiary_mobno;
    Spinner spinner, spinner_pay, spinnerGetIFSCStates, spinnerGetIFSCCity, spinnerGetIFSCBranch, spinnerGetIFSCBanks;
    FrameLayout frameLayout;
    private AutoCompleteTextView et_customerno = null;

    private SQLiteAdapter sqLiteAdapter;
    private ArrayAdapter<String> adapter;
    public static TextView tvExcepionmoneyorder;

    private String[] contactArray;

    AutoCompleteTextView autoCreateUserMobNo= null;
    EditText et_CreateUserName, et_EmailAddress;
    static EditText et_DateOfBirth;
    Button btnCreateUserSubmit;
    RelativeLayout moneyOrderPage5;
    int CustomerID_imps;

    String BeneficiaryAccountAlias, BeneficiaryID;
    List<String> GetBeneficiaryList_response, GetIFSCBranch_response, spinner_pay_response;
    String BankName, selected_BankName, StateName, selected_StateName, CityName, selected_CityName, selected_Branch, BranchName, IFSCCode, selected_BranchName;
    String str_MobNo, str_UserName, str_DOB, str_EmailAddress, str_IFSCCode, str_BranchName;
    EditText et_tpin, et_ipin;
    Button btntpinsubmit, btntpincancel;
    String str_CustomerID, str_tpin, str_ipin;
    private AdapterView.OnItemSelectedListener listener;
    int BeneficiaryIDDetails;
    String BeneficiaryName, IFSCCodeDetails, AccountNumber, BeneficiaryMobileNumber;
    boolean IsBeneficiaryVerified;
    long CustomerID;
    String title;
    // private ProgressBar progressBar;
    ProgressDialog progressDialog;
    private TextView tv_isverified;
    RadioGroup rdGrp;
    float wallet_balance;
    int WalletCustomerBalance;
    RelativeLayout main_layout;
    private Boolean isAgeValid = true;

    Mobilepayment_ServiceProviderID moneyorderobject = new Mobilepayment_ServiceProviderID();


    Payfrom payfrom = new Payfrom();


    AlertDialog.Builder alert1;
    AlertDialog dialog1;
    String ifsc_message;
    Typeface SemiBold,Light,Normal;
    ComProgressDialog comProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_money_order, container, false);

        NetworkConnection networkConnection = new NetworkConnection(getActivity());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();

        if (!isNetworkAvailable) {
            NetworkConnection.ExitAppDialog(getActivity());
        } else {


//            final Typeface Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
//            final Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Normal);
//            Condensed = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Semibold);
            comProgressDialog=new ComProgressDialog(getActivity(),getActivity());

            Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            tv_MoneyOrderImageText = (TextView) v.findViewById(R.id.tv_MoneyOrderImageText);
            tv_mobile_money_order = (TextView) v.findViewById(R.id.tv_mobile_money_order);
            et_customerno = (AutoCompleteTextView) v.findViewById(R.id.autoCustomerNo);
            btn_send = (Button) v.findViewById(R.id.btn_send);
            tv_customerno = (TextView) v.findViewById(R.id.tv_customerno);
            tv_available_limit = (TextView) v.findViewById(R.id.tv_available_limit);
            tv_fullkyc = (TextView) v.findViewById(R.id.tv_fullkyc);
            et_beneficiary_name = (EditText) v.findViewById(R.id.et_beneficiary_name);
            et_beneficiary_accountno = (EditText) v.findViewById(R.id.et_beneficiary_accountno);

            et_tpin = (EditText) v.findViewById(R.id.et_tpin);
            et_ipin = (EditText) v.findViewById(R.id.et_ipin);


            et_ifsc_code = (EditText) v.findViewById(R.id.et_ifsc_code);
            et_ifsc_select = (TextView) v.findViewById(R.id.et_ifsc_select);
            resend_ipin = (TextView) v.findViewById(R.id.resend_ipin);
            et_beneficiary_mobno = (EditText) v.findViewById(R.id.et_beneficiary_mobno);
            et_amount = (EditText) v.findViewById(R.id.et_amount);
            et_description = (EditText) v.findViewById(R.id.et_description);
            or = (TextView) v.findViewById(R.id.or);
            rbtn_pay = (RadioButton) v.findViewById(R.id.rbtn_pay);
            rbtn_verfiy = (RadioButton) v.findViewById(R.id.rbtn_verfiy);
            btn_pay = (Button) v.findViewById(R.id.btn_pay);
            btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
            btnCreateUsercancel=(Button)v.findViewById(R.id.btnCreateUsercancel);

            confrim = (TextView) v.findViewById(R.id.confrim);
            tv_beneficiary_name = (TextView) v.findViewById(R.id.tv_beneficiary_name);
            tv_beneficiary_number = (TextView) v.findViewById(R.id.tv_beneficiary_number);
            tv_bank_ifsc_code = (TextView) v.findViewById(R.id.tv_bank_ifsc_code);
            tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            tv_processing_fee = (TextView) v.findViewById(R.id.tv_processing_fee);
            tv_amount_payable = (TextView) v.findViewById(R.id.tv_amount_payable);
            tv_ipin = (TextView) v.findViewById(R.id.tv_ipin);
            tvExcepionmoneyorder = (TextView) v.findViewById(R.id.tvExcepionmoneyorder);
            tv_isverified = (TextView) v.findViewById(R.id.tv_isverified);



            frameLayout = (FrameLayout) v.findViewById(R.id.frame1);

            rdGrp = (RadioGroup) v.findViewById(R.id.rdGrp);

            btntpinsubmit = (Button) v.findViewById(R.id.btntpinsubmit);
            btntpincancel = (Button) v.findViewById(R.id.btntpincancel);

            btn_reset_ipin = (Button) v.findViewById(R.id.btn_reset_ipin);
            btn_pay1 = (Button) v.findViewById(R.id.btn_pay1);
            btn_cancel1 = (Button) v.findViewById(R.id.btn_cancel1);
            transaction_successful = (TextView) v.findViewById(R.id.transaction_successful);
            title = getString(R.string.imps);
            new_transaction = (Button) v.findViewById(R.id.new_transaction);
            payment_history = (Button) v.findViewById(R.id.payment_history);

            moneyOrderPage1 = (RelativeLayout) v.findViewById(R.id.moneyOrderPage1);
            moneyOrderPage2 = (RelativeLayout) v.findViewById(R.id.moneyOrderPage2);
            moneyOrderPage3 = (RelativeLayout) v.findViewById(R.id.moneyOrderPage3);
            moneyOrderPage4 = (RelativeLayout) v.findViewById(R.id.moneyOrderPage4);
            moneyOrderPage6 = (RelativeLayout) v.findViewById(R.id.moneyOrderPage6);
            moneyOrderPage5 = (RelativeLayout) v.findViewById(R.id.moneyOrderPage5);

            autoCreateUserMobNo = (AutoCompleteTextView) v.findViewById(R.id.autoCreateUserMobNo);
            et_CreateUserName = (EditText) v.findViewById(R.id.et_CreateUserName);
            et_DateOfBirth = (EditText) v.findViewById(R.id.et_DateOfBirth);
            et_EmailAddress = (EditText) v.findViewById(R.id.et_EmailAddress);
            btnCreateUserSubmit = (Button) v.findViewById(R.id.btnCreateUserSubmit);


//            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
//            progressBar.setVisibility(View.GONE);

            progressDialog= new ProgressDialog(getActivity());

            tv_isverified.setTypeface(Light);
            tvExcepionmoneyorder.setTypeface(Light);
            tv_MoneyOrderImageText.setTypeface(Light);
            tv_mobile_money_order.setTypeface(Light);
            et_customerno.setTypeface(Light);
            btn_send.setTypeface(Light);
            tv_customerno.setTypeface(Light);
            tv_available_limit.setTypeface(Light);
            tv_fullkyc.setTypeface(Light);
            et_beneficiary_name.setTypeface(Light);
            et_beneficiary_accountno.setTypeface(Light);
            et_ifsc_code.setTypeface(Light);
            et_ifsc_select.setTypeface(Light);
            et_beneficiary_mobno.setTypeface(Light);
            et_amount.setTypeface(Light);
            et_description.setTypeface(Light);
            et_tpin.setTypeface(Light);
            et_ipin.setTypeface(Light);
            or.setTypeface(Light);
            rbtn_pay.setTypeface(Light);
            rbtn_verfiy.setTypeface(Light);
            btn_pay.setTypeface(Normal);
            btn_cancel.setTypeface(Normal);
            resend_ipin.setTypeface(Light);

            btntpinsubmit.setTypeface(Normal);
            btntpincancel.setTypeface(Normal);

            confrim.setTypeface(Light);
            tv_beneficiary_name.setTypeface(Light);
            tv_beneficiary_number.setTypeface(Light);
            tv_bank_ifsc_code.setTypeface(Light);
            tv_amount.setTypeface(Light);
            tv_processing_fee.setTypeface(Light);
            tv_amount_payable.setTypeface(Light);
            btn_cancel.setTypeface(Normal);
            btn_reset_ipin.setTypeface(Normal);
            btn_pay1.setTypeface(Normal);
            btn_cancel1.setTypeface(Normal);
            transaction_successful.setTypeface(Light);

            autoCreateUserMobNo.setTypeface(Light);
            et_CreateUserName.setTypeface(Light);
            et_DateOfBirth.setTypeface(Light);
            et_EmailAddress.setTypeface(Light);
            btnCreateUserSubmit.setTypeface(Normal);

            spinner = (Spinner) v.findViewById(R.id.spinner_beneficiary);
            spinner_pay = (Spinner) v.findViewById(R.id.spinner_payfrom);
            new_transaction.setTypeface(Normal);
            payment_history.setTypeface(Normal);


            et_EmailAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);



            // main_layout=(RelativeLayout)v.findViewById(R.id.main_layout);


            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());


            listBeneficiary = new ArrayList<String>();
            listBeneficiary.add(getString(R.string.select_beneficiary));
            listBeneficiary.add(getString(R.string.add_beneficiary));

            listGetIFSCBanks = new ArrayList<String>();
            listGetIFSCBanks.add(getString(R.string.select_bank));

            listGetIFSCStates = new ArrayList<String>();
            listGetIFSCStates.add(getString(R.string.Select_State));

            listGetIFSCCity = new ArrayList<String>();
            listGetIFSCCity.add(getString(R.string.Select_City));

            listGetIFSCBranch = new ArrayList<String>();
            listGetIFSCBranch.add(getString(R.string.Select_Branch));

            listpayfrom = new ArrayList<String>();
            listpayfrom.add(getString(R.string.Pay_From));
            listpayfrom.add(getString(R.string.retailer));


            try {


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if (position == 0 || position == 1) {
                            //  Toast.makeText(getActivity(), "Item number0: " + position, Toast.LENGTH_LONG).show();
                            // spinner_pay.setVisibility(View.GONE);
                            frameLayout.setVisibility(View.GONE);
                            et_amount.setVisibility(View.GONE);
                            et_description.setVisibility(View.GONE);

                            et_beneficiary_name.setText("");
                            et_beneficiary_accountno.setText("");
                            et_ifsc_code.setText("");
                            et_beneficiary_mobno.setText("");

                            et_beneficiary_name.setEnabled(true);
                            et_beneficiary_accountno.setEnabled(true);
                            et_ifsc_code.setEnabled(true);
                            et_beneficiary_mobno.setEnabled(true);
                            tvExcepionmoneyorder.setText("");

                            tv_isverified.setText("");
                            rbtn_verfiy.setVisibility(View.VISIBLE);
                            rdGrp.setVisibility(View.GONE);
                            or.setVisibility(View.VISIBLE);
                            et_ifsc_select.setVisibility(View.VISIBLE);
                        } else {
                            //  Toast.makeText(getActivity(), "Item numberElse: " + spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            frameLayout.setVisibility(View.VISIBLE);
                            et_amount.setVisibility(View.VISIBLE);
                            et_description.setVisibility(View.VISIBLE);

                            or.setVisibility(View.GONE);
                            et_ifsc_select.setVisibility(View.GONE);

                            //et_ifsc_code.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                            // edittext.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            tvExcepionmoneyorder.setText("");

                            GetBeneficiaryList_response = moneyorderobject.getBranchList(spinner.getSelectedItem().toString());
                            BeneficiaryAccountAlias = moneyorderobject.getFavoriteName(spinner.getSelectedItem().toString());
                            BeneficiaryID = moneyorderobject.getFavoriteMobileNumber(spinner.getSelectedItem().toString());


                            GetBeneficiaryDetailsBackground getBeneficiaryDetailsBackground = new GetBeneficiaryDetailsBackground();
                            getBeneficiaryDetailsBackground.execute();

                            SetBeneficiaryAsVerifiedBackground setBeneficiaryAsVerified = new SetBeneficiaryAsVerifiedBackground();
                            setBeneficiaryAsVerified.execute();
                            rdGrp.setVisibility(View.VISIBLE);
                            rbtn_pay.setChecked(true);

                            spinner_pay.setSelection(0);
                            et_amount.setText("");
                            et_description.setText("");


                        }
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getActivity(), "onNothingSelected : " + spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                    }


                });


                int position = spinner.getSelectedItemPosition();
                //Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {




                spinner_pay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(getActivity(), "Item number: " + spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();


                        spinner_pay_response = payfrom.getBranchList(spinner_pay.getSelectedItem().toString());
                        if (spinner_pay_response != null) {

                            String ReceiptID = payfrom.getFavoriteName(spinner_pay.getSelectedItem().toString());
                            String TransactionDescription = payfrom.getFavoriteMobileNumber(spinner_pay.getSelectedItem().toString());
                            String RefundAmount = payfrom.getCustomerFavoriteID(spinner_pay.getSelectedItem().toString());


                            et_amount.setText(RefundAmount);
                            et_amount.setEnabled(false);


                        } else {

                            et_amount.setText("");
                            et_amount.setEnabled(true);
                            //Toast.makeText(getActivity(),"nll",Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

                int position = spinner.getSelectedItemPosition();
                //  Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }


            et_customerno.setThreshold(2);
            autoCreateUserMobNo.setThreshold(2);
            sqLiteAdapter = new SQLiteAdapter(getActivity());

            sqLiteAdapter.openToRead();

            contactArray = sqLiteAdapter.getContacts();
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, contactArray);
            et_customerno.setAdapter(adapter);
            autoCreateUserMobNo.setAdapter(adapter);


            et_customerno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));

                    //adapterView.getSelectedItem();

                    et_customerno.setText(upToNCharacters);
                    //         Log.e("NumberTest1", phoneValueArr.get(i));

                }
            });
            autoCreateUserMobNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);

                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));





                    String upToNCharacters_name = s.substring(16, Math.min(s.length(), 50));
                    String favourite_name=upToNCharacters_name.trim();

                    et_CreateUserName.setText("");
                    autoCreateUserMobNo.setText(upToNCharacters);
                    et_CreateUserName.setText(favourite_name);


                }
            });
            sqLiteAdapter.close();

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_send.setEnabled(false);

                    validate();
                }
            });
            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // validate_beneficiary();
                    btn_send.setEnabled(true);
                    validate_select_add_beneficiary();
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_send.setEnabled(true);
                    // et_customerno.setText("");
                    tvExcepionmoneyorder.setText("");
                    moneyOrderPage1.setVisibility(View.VISIBLE);
                    moneyOrderPage2.setVisibility(View.GONE);
                    moneyOrderPage3.setVisibility(View.GONE);
                    moneyOrderPage4.setVisibility(View.GONE);
                    moneyOrderPage5.setVisibility(View.GONE);

                }
            });
            btnCreateUsercancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_send.setEnabled(true);
                    //et_customerno.setText("");
                    tvExcepionmoneyorder.setText("");
                    moneyOrderPage1.setVisibility(View.VISIBLE);
                    moneyOrderPage2.setVisibility(View.GONE);
                    moneyOrderPage3.setVisibility(View.GONE);
                    moneyOrderPage4.setVisibility(View.GONE);
                    moneyOrderPage5.setVisibility(View.GONE);

                }
            });
            btn_pay1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moneyOrderPage1.setVisibility(View.GONE);
                    moneyOrderPage2.setVisibility(View.GONE);
                    moneyOrderPage3.setVisibility(View.GONE);
                    moneyOrderPage4.setVisibility(View.VISIBLE);
                }
            });

            btn_cancel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moneyOrderPage1.setVisibility(View.GONE);
                    moneyOrderPage2.setVisibility(View.VISIBLE);
                    moneyOrderPage3.setVisibility(View.GONE);
                    moneyOrderPage4.setVisibility(View.GONE);
                }
            });

            new_transaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_customerno.setText("");
                    moneyOrderPage1.setVisibility(View.VISIBLE);
                    moneyOrderPage2.setVisibility(View.GONE);
                    moneyOrderPage3.setVisibility(View.GONE);
                    moneyOrderPage4.setVisibility(View.GONE);
                }
            });

            payment_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(getActivity(), Tabhost_activity.class);
                    ii.putExtra(String_url.position, 26);
                    startActivity(ii);
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().finish();


                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(String_url.home, String_url.dashboard);
                    editor.commit();
                }
            });

            btntpinsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btntpinsubmit.setEnabled(false);

                    validate_tipin();

                }
            });

            btntpincancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    moneyOrderPage1.setVisibility(View.GONE);
                    moneyOrderPage2.setVisibility(View.VISIBLE);
                    moneyOrderPage3.setVisibility(View.GONE);
                    moneyOrderPage4.setVisibility(View.GONE);
                    moneyOrderPage5.setVisibility(View.GONE);
                    moneyOrderPage6.setVisibility(View.GONE);

                }
            });


            et_DateOfBirth.setClickable(true);
//            et_DateOfBirth.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    DialogFragment newFragment = new SelectDateFragment();
//                    newFragment.show(getFragmentManager(), getString(R.string.DatePicker));
//
//                    //  Toast.makeText(getActivity(),"Hello",Toast.LENGTH_SHORT).show();
//
//                }
//            });
            et_DateOfBirth.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if(event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        DialogFragment newFragment = new SelectDateFragment();
                        newFragment.show(getFragmentManager(), getString(R.string.DatePicker));
                    }
                    return true;
                }
            });

            et_ifsc_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getIFSCCODE();
                }
            });

            btnCreateUserSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateCreateUser();
                }
            });

            resend_ipin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resent_Ipin resent_ipin = new resent_Ipin();
                    resent_ipin.execute();
                }
            });


            SharedPreferences CustomerMainAccountBalance = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            //str_CustomerID=CustomerMainAccountBalance.getString(String_url.CustomerID,"");

            CustomerID = CustomerMainAccountBalance.getLong(String_url.CustomerID, 0);

            wallet_balance=CustomerMainAccountBalance.getFloat(String_url.CustomerMainAccountBalance,0);


            WalletCustomerBalance = (int) wallet_balance;



            rbtn_verfiy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frameLayout.setVisibility(View.GONE);
                    et_amount.setVisibility(View.GONE);
                    et_description.setVisibility(View.GONE);

                }
            });




            rbtn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frameLayout.setVisibility(View.VISIBLE);
                    et_amount.setVisibility(View.VISIBLE);
                    et_description.setVisibility(View.VISIBLE);

                }
            });




        }//end of else


        return v;
    }





    public boolean validate() {
        int nMinLength = 10;
        int nMaxLength = 10;
        str_customerno = et_customerno.getText().toString();
        int ncustomernoLength = et_customerno.getText().toString().trim().length();
        if (str_customerno.trim().equals("")) {
            //et_customerno.setError(getString(R.string.error_customermobno));
            getResponseDialog(getString(R.string.error_customermobno));
            // tvExcepionmoneyorder.setText(getString(R.string.error_customermobno));
            return false;
        } else if (ncustomernoLength < nMinLength || ncustomernoLength > nMaxLength) {
            // et_customerno.setError(String.format(getString(R.string.error_phone_length), nMinLength));
            getResponseDialog(getString(R.string.error_phone_length));
            // tvExcepionmoneyorder.setText(getString(R.string.error_phone_length));
            return false;
        } else {


            GetConsumerStatusBackground getConsumerStatusBackground = new GetConsumerStatusBackground();
            getConsumerStatusBackground.execute();


        }
        return true;
    }

    public void validate_select_add_beneficiary() {
        String str_beneficiary = spinner.getSelectedItem().toString();

        if (str_beneficiary.equals("Select Beneficiary")) {
            getResponseDialog(getString(R.string.select_beneficiary));

        } else if (str_beneficiary.equals("Add Beneficiary")) {
            validate_add_beneficiary();
        } else {

            validate_radio();
        }
    }
    public void validate_radio()
    {
        int id = rdGrp.getCheckedRadioButtonId();
        if (id == R.id.rbtn_pay) {
            validate_beneficiary();
        }
        else if(id==R.id.rbtn_verfiy)
        {
            validate_beneficiary_verify();
        }
    }

    public boolean validate_beneficiary() {

        int processing_fee = 10;
        int nMinLength = 10;
        int nMaxLength = 10;
        int nMinAccountLength = 10;
        int nMaxAccountLength = 10;
        int nMaxIfscLength = 5;
        int min_amount_refund = 10;
        int min_amount = 10;
        int minaccountno=11;
        int maxaccountno=16;

        int max_amount = 25000;
        int nbeneficiary_mobnoLength = et_beneficiary_mobno.getText().toString().trim().length();
        int nbeneficiary_AccountLength = et_beneficiary_accountno.getText().toString().trim().length();
        str_beneficiary_name = et_beneficiary_name.getText().toString();
        str_beneficiary_accountno = et_beneficiary_accountno.getText().toString();
        str_ifsc_code = et_ifsc_code.getText().toString();
        str_ifsc_select = et_ifsc_select.getText().toString();
        int ifsc_codeLength = et_ifsc_code.getText().toString().trim().length();
        int ifsc_selectLength = et_ifsc_select.getText().toString().trim().length();
        str_beneficiary_mobno = et_beneficiary_mobno.getText().toString();

        CheckIFSCCodeBackground checkIFSCCodeBackground = new CheckIFSCCodeBackground();
        ifsc_message = checkIFSCCodeBackground.doInBackground().toLowerCase();


        str_transition_description = et_description.getText().toString();
        spinnerBenificiary = spinner.getSelectedItem().toString();
        spinnerPayfrom = spinner_pay.getSelectedItem().toString();
        int beneficiary_amount = 0;
        int amount_payable = 0;
        int position = spinner_pay.getSelectedItemPosition();


        try {

            float amount=Float.parseFloat(et_amount.getText().toString());

            beneficiary_amount =(int)amount;

            amount_payable = beneficiary_amount + processing_fee;
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if (spinnerBenificiary == getString(R.string.SelectType)) {
            getResponseDialog(getString(R.string.mobile_recharge_select_type));

            return false;
        } else if (str_beneficiary_name.equals("")) {
            getResponseDialog(getString(R.string.beneficiary_name));

            return false;
        }
        else if (str_ifsc_code.equals("")) {
            getResponseDialog(getString(R.string.beneficiary_ifsc_code_select));

            return false;
        } else if (!ifsc_message.equals("success")) {
            getResponseDialog(getString(R.string.valideifsc));

            return false;
        } else if (nbeneficiary_mobnoLength < nMinLength || nbeneficiary_mobnoLength > nMaxLength) {
            getResponseDialog(getString(R.string.error_phone_length));

            return false;
        }
        else if (beneficiary_amount < min_amount_refund || beneficiary_amount > max_amount) {
            getResponseDialog(getString(R.string.amount_length) + min_amount_refund + getString(R.string.and) + max_amount);


            return false;
        }

        else if (str_transition_description.equals("")) {
            getResponseDialog(getString(R.string.transaction_description));

            return false;
        }

        else{
            et_tpin.setText(null);
            et_ipin.setText(null);

            btntpinsubmit.setEnabled(true);

            tvExcepionmoneyorder.setText("");
            moneyOrderPage1.setVisibility(View.GONE);
            moneyOrderPage2.setVisibility(View.GONE);
            moneyOrderPage3.setVisibility(View.GONE);
            moneyOrderPage4.setVisibility(View.GONE);
            moneyOrderPage5.setVisibility(View.GONE);
            moneyOrderPage6.setVisibility(View.VISIBLE);
        }
        return true;

    }


    public boolean validate_beneficiary_verify() {

        int processing_fee = 10;
        int nMinLength = 10;
        int nMaxLength = 10;
        int minaccountno=11;
        int maxaccountno=16;

        int max_amount = 25000;
        int nbeneficiary_mobnoLength = et_beneficiary_mobno.getText().toString().trim().length();
        int nbeneficiary_AccountLength = et_beneficiary_accountno.getText().toString().trim().length();
        str_beneficiary_name = et_beneficiary_name.getText().toString();
        str_beneficiary_accountno = et_beneficiary_accountno.getText().toString();
        str_ifsc_code = et_ifsc_code.getText().toString();
        str_ifsc_select = et_ifsc_select.getText().toString();
        int ifsc_codeLength = et_ifsc_code.getText().toString().trim().length();
        int ifsc_selectLength = et_ifsc_select.getText().toString().trim().length();
        str_beneficiary_mobno = et_beneficiary_mobno.getText().toString();
        spinnerBenificiary = spinner.getSelectedItem().toString();
        spinnerPayfrom = spinner_pay.getSelectedItem().toString();
        int beneficiary_amount = 0;
        int amount_payable = 0;
        int position = spinner_pay.getSelectedItemPosition();

        CheckIFSCCodeBackground checkIFSCCodeBackground = new CheckIFSCCodeBackground();
        ifsc_message = checkIFSCCodeBackground.doInBackground().toLowerCase();



        try {

            beneficiary_amount = Integer.parseInt(et_amount.getText().toString());
            amount_payable = beneficiary_amount + processing_fee;
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if (spinnerBenificiary == getString(R.string.SelectType)) {
            getResponseDialog(getString(R.string.mobile_recharge_select_type));
            //tvExcepionmoneyorder.setText(getString(R.string.mobile_recharge_select_type));
            return false;
        } else if (str_beneficiary_name.equals("")) {
            getResponseDialog(getString(R.string.beneficiary_name));
            // tvExcepionmoneyorder.setText(getString(R.string.beneficiary_name));
            return false;
        }
//        } else if (str_beneficiary_accountno.equals("")) {
//            getResponseDialog(getString(R.string.beneficiary_acnt));
//            //tvExcepionmoneyorder.setText(getString(R.string.beneficiary_acnt));
//            return false;
//        }
//        else if (nbeneficiary_AccountLength < minaccountno || nbeneficiary_AccountLength > maxaccountno) {
//            getResponseDialog(getString(R.string.valid_account_no));
//            //tvExcepionmoneyorder.setText(getString(R.string.beneficiary_acnt));
//            return false;
//        }
        else if (str_ifsc_code.equals("")) {
            getResponseDialog(getString(R.string.beneficiary_ifsc_code_select));
            // tvExcepionmoneyorder.setText(getString(R.string.beneficiary_ifsc_code_select));
            return false;
        } else if (!ifsc_message.equals("success")) {
            getResponseDialog(getString(R.string.valideifsc));

            return false;
        } else if (nbeneficiary_mobnoLength < nMinLength || nbeneficiary_mobnoLength > nMaxLength) {
            getResponseDialog(getString(R.string.error_phone_length));

            return false;
        }


        else{
            et_tpin.setText(null);
            et_ipin.setText(null);
            btntpinsubmit.setEnabled(true);
            tvExcepionmoneyorder.setText("");
            moneyOrderPage1.setVisibility(View.GONE);
            moneyOrderPage2.setVisibility(View.GONE);
            moneyOrderPage3.setVisibility(View.GONE);
            moneyOrderPage4.setVisibility(View.GONE);
            moneyOrderPage5.setVisibility(View.GONE);
            moneyOrderPage6.setVisibility(View.VISIBLE);
        }
        return true;

    }


    public boolean validate_add_beneficiary()
    {
        int minaccountno=11;
        int maxaccountno=16;
        int processing_fee=10;
        int nMinLength          = 10;
        int nMaxLength          = 10;
        int nbeneficiary_mobnoLength   = et_beneficiary_mobno.getText().toString().trim().length();
        int nbeneficiary_AccountLength   = et_beneficiary_accountno.getText().toString().trim().length();
        str_beneficiary_name=et_beneficiary_name.getText().toString();
        str_beneficiary_accountno=et_beneficiary_accountno.getText().toString();
        str_ifsc_code=et_ifsc_code.getText().toString();
        int ifsc_codeLength   = et_ifsc_code.getText().toString().trim().length();
        int ifsc_selectLength   = et_ifsc_select.getText().toString().trim().length();
        str_beneficiary_mobno=et_beneficiary_mobno.getText().toString();


        CheckIFSCCodeBackground checkIFSCCodeBackground = new CheckIFSCCodeBackground();
        ifsc_message = checkIFSCCodeBackground.doInBackground().toLowerCase();


        if(str_beneficiary_name.equals(""))
        {

            getResponseDialog(getString(R.string.beneficiary_name));
            //tvExcepionmoneyorder.setText(getString(R.string.beneficiary_name));
            return false;
        }
        else if(str_beneficiary_accountno.equals(""))
        {
            getResponseDialog(getString(R.string.beneficiary_acnt));
            //tvExcepionmoneyorder.setText(getString(R.string.beneficiary_acnt));
            return false;
        }
        else if (nbeneficiary_AccountLength < minaccountno || nbeneficiary_AccountLength > maxaccountno) {
            getResponseDialog(getString(R.string.valid_account_no));
            //tvExcepionmoneyorder.setText(getString(R.string.beneficiary_acnt));
            return false;
        }
        else if(str_ifsc_code.equals(""))
        {
            getResponseDialog(getString(R.string.beneficiary_ifsc_code_select));
            //tvExcepionmoneyorder.setText(getString(R.string.beneficiary_ifsc_code_select));
            return false;
        }
        else if (!ifsc_message.equals("success")) {
            getResponseDialog(getString(R.string.valideifsc));
            //tvExcepionmoneyorder.setText(getString(R.string.valideifsc));
            return false;
        }
        else if(nbeneficiary_mobnoLength<nMinLength||nbeneficiary_mobnoLength>nMaxLength)
        {
            getResponseDialog(getString(R.string.error_phone_length));
            //tvExcepionmoneyorder.setText(getString(R.string.error_phone_length));
            return false;
        }

        else
        {

            tvExcepionmoneyorder.setText("");


            CheckDuplicateBeneficiary checkDuplicateBeneficiary=new CheckDuplicateBeneficiary();
            int value=checkDuplicateBeneficiary.doInBackground();

            if(value==1)
            {
                //comProgressDialog.cancelProgress();
                // comProgressDialog.cancelProgress();
                getResponseDialog(getString(R.string.beneficiary_exist));
                return false;
            }
            else{


               // comProgressDialog.cancelProgress();
                RegisterBeneficiaryBackground registerBeneficiaryBackground=new RegisterBeneficiaryBackground();
                registerBeneficiaryBackground.execute();

            }



        }
        return true;

    }

    public boolean validate_tipin()
    {

        int min_tpin_length=8;
        str_tpin=et_tpin.getText().toString();
        str_ipin=et_ipin.getText().toString();
        if(str_tpin.length()<min_tpin_length||str_tpin.length()>min_tpin_length)
        {
            getResponseDialog(getString(R.string.error_tpin_length));
            //tvExcepionmoneyorder.setText(getString(R.string.error_tpin_length));
            return false;
        }
        else  if(str_ipin.length()<min_tpin_length||str_ipin.length()>min_tpin_length)
        {
            getResponseDialog(getString(R.string.error_ipin_length));
            //tvExcepionmoneyorder.setText(getString(R.string.error_ipin_length));
            return false;
        }
        else
        {


            GetValidPasswordPinClass getValidPasswordPinClass=new GetValidPasswordPinClass(getActivity(), getActivity(),str_tpin);
            String message=getValidPasswordPinClass.doInBackground().toLowerCase();


            if(message.equals(String_url.success))
            {

                final ConfrimRechargeDialog confrimRechargeDialog1 = new ConfrimRechargeDialog(getActivity(), getActivity());
                confrimRechargeDialog1.showDialog(3,
                        getString(R.string.imps),
                        getString(R.string.beneficiary_name_dialog),
                        str_beneficiary_name,
                        getString(R.string.mobile_number),
                        str_beneficiary_mobno,
                        getString(R.string.ifsc_code_dialog),
                        str_ifsc_code,
                        new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v) {
                                SubmitPaymentIPINBackground submitPaymentIPINBackground=new SubmitPaymentIPINBackground();
                                submitPaymentIPINBackground.execute();
                                confrimRechargeDialog1.dialog.dismiss();
                                btntpinsubmit.setEnabled(true);
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confrimRechargeDialog1.dialog.dismiss();
                                btntpinsubmit.setEnabled(true);
                            }
                        }
                );
//                SubmitPaymentIPINBackground submitPaymentIPINBackground=new SubmitPaymentIPINBackground();
//                submitPaymentIPINBackground.execute();
            }
            else
            {

                getResponseDialog(getString(R.string.invalid_tpin));
                //tvExcepionmoneyorder.setText(getString(R.string.invalid_tpin));
            }

            return true;
        }
    }





    private class GetConsumerStatusBackground extends AsyncTask<Void, Void, String>
    {
        JSONObject jsonObject=null;
        String IsIMPSServiceAllowedresponse;
        boolean IsIMPSServiceAllowed;
        boolean IsRegistered;
        boolean[] value=new boolean[2];
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressBar.setVisibility(View.VISIBLE);

           comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetConsumerStatus);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.MobileNumberIMPs,str_customerno));

                final HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams,45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                IsIMPSServiceAllowedresponse = EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return IsIMPSServiceAllowedresponse;

        }
        @Override
        protected void onPostExecute(String dataResponse) {
            try
            {
                if(dataResponse!=null)
                {

                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<IMPSResponse>>(){}.getType();
                    ResponseBase<IMPSResponse> responseBase=gson.fromJson(IsIMPSServiceAllowedresponse,type);
                    IMPSResponse imps_response=responseBase.responseData;
                    if(responseBase.responseData!=null)
                    {
                        IsRegistered=imps_response.IsRegistered;
                        IsIMPSServiceAllowed=imps_response.IsIMPSServiceAllowed;
                        CustomerID_imps=imps_response.CustomerID;

                        value[0]=IsRegistered;
                        value[1]=IsIMPSServiceAllowed;

                    }
                    if(!value[0]) {
                        comProgressDialog.cancelProgress();
                        tvExcepionmoneyorder.setText("");
                        autoCreateUserMobNo.setText("");
                        et_CreateUserName.setText("");
                        et_DateOfBirth.setText("");
                        et_EmailAddress.setText("");

                        moneyOrderPage1.setVisibility(View.GONE);
                        moneyOrderPage2.setVisibility(View.GONE);
                        moneyOrderPage5.setVisibility(View.VISIBLE);
                    }


                    else  if((value[0])&&(value[1])) {
                        comProgressDialog.cancelProgress();
                        tvExcepionmoneyorder.setText("");
                        et_beneficiary_name.setText("");
                        et_beneficiary_accountno.setText("");
                        et_ifsc_code.setText("");
                        //et_ifsc_select.setText("");
                        et_beneficiary_mobno.setText("");

                        moneyOrderPage1.setVisibility(View.GONE);
                        moneyOrderPage2.setVisibility(View.VISIBLE);


                        GetConsumerKYCLimitAndStatusBackground getConsumerKYCLimitAndStatusBackground=new GetConsumerKYCLimitAndStatusBackground();
                        getConsumerKYCLimitAndStatusBackground.execute();

                        GetBeneficiaryListBackground getBeneficiaryListBackground=new GetBeneficiaryListBackground();
                        getBeneficiaryListBackground.execute();

                        GetPayfromBackground getPayfromBackground=new GetPayfromBackground();
                        getPayfromBackground.execute();
                    }

                    else if(value[0] && !value[1])
                    {
                        comProgressDialog.cancelProgress();
                        getResponseDialog(getString(R.string.ImpsAuthorizationError));

                    }

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //progressBar.setVisibility(View.GONE);
            //comProgressDialog.cancelProgress();
            //comProgressDialog.cancelProgress();
        }
    }


    private class GetConsumerKYCLimitAndStatusBackground extends AsyncTask<Void, Void, String>
    {
        String GetConsumerKYCLimitAndStatusresponse=null;
        JSONObject jsonObject=null;
        int ConsumerID;
        boolean isFullKYC;
        float AvailableLimit;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
           // comProgressDialog.showProgress();
        }

        @Override
        protected String doInBackground(Void... params)
        {
            try
            {


                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetConsumerKYCLimitAndStatus);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ConsumerId,String.valueOf(CustomerID_imps)));

                final HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams,45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();
                GetConsumerKYCLimitAndStatusresponse  = EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetConsumerKYCLimitAndStatusresponse;

        }
        @Override
        protected void onPostExecute(String dataResponse) {
            try
            {
                if(dataResponse!=null)
                {

                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetConsumerKYCLimitAndStatusResponse>>(){}.getType();
                    ResponseBase<GetConsumerKYCLimitAndStatusResponse> responseBase=gson.fromJson(GetConsumerKYCLimitAndStatusresponse,type);
                    GetConsumerKYCLimitAndStatusResponse getConsumerKYCLimitAndStatusresponse=responseBase.responseData;
                    if(responseBase.responseData!=null)
                    {
                        ConsumerID=getConsumerKYCLimitAndStatusresponse.ConsumerID;
                        isFullKYC=getConsumerKYCLimitAndStatusresponse.isFullKYC;
                        AvailableLimit=getConsumerKYCLimitAndStatusresponse.AvailableLimit;
                    }

                    tv_customerno.setText("Customer no.:"+str_customerno);
                    tv_available_limit.setText("Available Limit:"+AvailableLimit);
                    if(isFullKYC)
                    {
                        tv_fullkyc.setText("Full KYC");
                    }
                    else
                    {
                        tv_fullkyc.setText("Nil KYC");
                    }

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            // progressBar.setVisibility(View.VISIBLE);
           // comProgressDialog.cancelProgress();



        }
    }



    public class GetBeneficiaryListBackground extends AsyncTask<Void,Void,String>
    {

        Map<String, List<String>> map1;
        Map<String,Integer> mapGift;
        List<String> branchNameResultList;
        String GetBeneficiaryList_res;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
            listBeneficiary.clear();
            listBeneficiary = new ArrayList<String>();
            listBeneficiary.add(getString(R.string.select_beneficiary));
            listBeneficiary.add(getString(R.string.add_beneficiary));

        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {

                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetBeneficiaryList);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.consumerId,String.valueOf(CustomerID_imps)));
                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetBeneficiaryList_res= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetBeneficiaryList_res;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetBeneficiaryListResponse[]>>(){}.getType();
                    ResponseBase<GetBeneficiaryListResponse[]> responseBase=gson.fromJson(GetBeneficiaryList_res,type);
                    map1 = new HashMap<String, List<String>>();
                    mapGift = new HashMap<String, Integer>();
                    if(responseBase.responseData!=null)
                    {
                        for(GetBeneficiaryListResponse getBeneficiaryListResponse:responseBase.responseData)
                        {
                            String BeneficiaryAccountAlias=getBeneficiaryListResponse.BeneficiaryAccountAlias;
                            int BeneficiaryID=getBeneficiaryListResponse.BeneficiaryID;



                            List<String> moset = new ArrayList<String>();
                            moset.add(BeneficiaryAccountAlias);
                            moset.add(String.valueOf(BeneficiaryID));
                            map1.put(BeneficiaryAccountAlias, moset);




                            listBeneficiary.add(BeneficiaryAccountAlias);

                            moneyorderobject.storeBranchList(String_url.payment_mode, map1);
                            moneyorderobject.storeBranchListDetail(String_url.payment_id, listBeneficiary);

                            mapGift.put(BeneficiaryAccountAlias, BeneficiaryID);

                            branchNameResultList = moneyorderobject.getBranchListDetail();

                        }
                    }

                    if(branchNameResultList!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBeneficiary) {

                            public View getView(int position, View convertView, ViewGroup parent) {
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
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter.notifyDataSetChanged();
                        spinner.setAdapter(adapter);

                    }

                    else{


                        listBeneficiary = new ArrayList<String>();
                        listBeneficiary.add(getString(R.string.select_beneficiary));
                        listBeneficiary.add(getString(R.string.add_beneficiary));

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBeneficiary) {

                            public View getView(int position, View convertView, ViewGroup parent) {
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
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter.notifyDataSetChanged();
                        spinner.setAdapter(adapter);

                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //progressBar.setVisibility(View.GONE);
            comProgressDialog.cancelProgress();

        }
    }

    public class GetPayfromBackground extends AsyncTask<Void,Void,String>
    {
        Map<String, List<String>> map1;
        Map<String,Float> mapGift;
        List<String> branchNameResultList;
        String GetPayfromresponse;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
            //comProgressDialog.showProgress();
            listpayfrom.clear();
            listpayfrom = new ArrayList<String>();
            listpayfrom.add(getString(R.string.Pay_From));
            listpayfrom.add(getString(R.string.retailer));
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetRefundDetails);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerID,String.valueOf(CustomerID_imps)));
                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetPayfromresponse= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetPayfromresponse;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null) {

                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetRefundDetailsResponse[]>>(){}.getType();
                    ResponseBase<GetRefundDetailsResponse[]> responseBase=gson.fromJson(s,type);
                    if(responseBase.responseData!=null)
                    {
                        map1 = new HashMap<String, List<String>>();
                        mapGift = new HashMap<String, Float>();
                        for(GetRefundDetailsResponse getRefundDetailsResponse:responseBase.responseData)
                        {
                            String ReceiptID=getRefundDetailsResponse.ReceiptID;
                            String TransactionDescription=getRefundDetailsResponse.TransactionDescription;
                            float RefundAmount=getRefundDetailsResponse.RefundAmount;


                            List<String> moset = new ArrayList<String>();
                            moset.add(TransactionDescription);
                            moset.add(String.valueOf(ReceiptID));
                            moset.add(String.valueOf(RefundAmount));
                            map1.put(TransactionDescription, moset);



                            listpayfrom.add(TransactionDescription);

                            payfrom.storeBranchList(String_url.payment_mode, map1);
                            payfrom.storeBranchListDetail(String_url.payment_id, listpayfrom);

                            mapGift.put(TransactionDescription, RefundAmount);

                            branchNameResultList = payfrom.getBranchListDetail();
                        }
                    }
                    else
                    {

                        //refund value is null
                        listpayfrom.clear();
                        listpayfrom.add("No Refund Amount found");
//                        spinner_pay.setClickable(false);
//                        spinner_pay.setEnabled(false);

                    }

                    if (branchNameResultList != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listpayfrom) {

                            public View getView(int position, View convertView, ViewGroup parent) {
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
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // adapter.notifyDataSetChanged();
                        spinner_pay.setAdapter(adapter);

                    }
                    else {
                        listpayfrom = new ArrayList<String>();
                        listpayfrom.add(getString(R.string.Pay_From));
                        listpayfrom.add("No Refund Amount found");

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listpayfrom) {

                            public View getView(int position, View convertView, ViewGroup parent) {
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
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner_pay.setAdapter(adapter);
                    }
                    //progressBar.setVisibility(View.GONE);
                    //comProgressDialog.cancelProgress();

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView=(ImageView)getActivity().findViewById(R.id.iv_moneyorder);
        imageView.setBackgroundResource(R.drawable.allscreen);



    }

    public static   class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        // public TextView tvExcepionmoneyorder;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            //tvExcepionmoneyorder=(TextView)
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);

        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {


            Calendar userAge = new GregorianCalendar(yy,mm,dd);
            Calendar minAdultAge = new GregorianCalendar();
            minAdultAge.add(Calendar.YEAR, -18);
            if (minAdultAge.before(userAge))
            {

                //Toast.makeText(getActivity(),"Minimum age should be 18 years",Toast.LENGTH_SHORT).show();
                //  tvExcepionmoneyorder.setText(R.string.Error_age_limit);

                final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
                responseDialog1.showResponseDialog(1,
                        getString(R.string.message),
                        null,null,null,null,
                        getString(R.string.Error_age_limit),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                responseDialog1.dialog.dismiss();
                            }
                        }


                );




            }
            else {
                populateSetDate(yy, mm + 1, dd);
            }


        }
        public void populateSetDate(int year, int month, int day) {
            et_DateOfBirth.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }

    }

    public class GetBeneficiaryDetailsBackground extends AsyncTask<Void,Void,String>
    {
        String GetBeneficiaryDetails_response;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {

                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetBeneficiaryDetails);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.beneficiaryAccountName,BeneficiaryAccountAlias));
                nameValuePairs.add(new BasicNameValuePair("customerId",String.valueOf(CustomerID_imps)));

                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams,45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetBeneficiaryDetails_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetBeneficiaryDetails_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetBeneficiaryDetailsResponse>>(){}.getType();
                    ResponseBase<GetBeneficiaryDetailsResponse> responseBase=gson.fromJson(GetBeneficiaryDetails_response,type);
                    GetBeneficiaryDetailsResponse getBeneficiaryDetailsResponse=responseBase.responseData;
                    if(responseBase.responseData!=null)
                    {

                        BeneficiaryIDDetails=getBeneficiaryDetailsResponse.BeneficiaryID;
                        BeneficiaryName=getBeneficiaryDetailsResponse.BeneficiaryName;
                        AccountNumber=getBeneficiaryDetailsResponse.AccountNumber;
                        IFSCCode=getBeneficiaryDetailsResponse.IFSCCode;
                        BeneficiaryMobileNumber=getBeneficiaryDetailsResponse.BeneficiaryMobileNumber;
                        IsBeneficiaryVerified=getBeneficiaryDetailsResponse.IsBeneficiaryVerified;



                        if(BeneficiaryIDDetails==0)
                        {

//
//                            et_beneficiary_name.setText(R.string.beneficiary_name);
//                            et_beneficiary_accountno.setText(R.string.beneficiary_acnt);
//                            et_ifsc_code.setText(R.string.ifsc_code);
//                            et_beneficiary_mobno.setText(R.string.beneficiary_mobile);
//
//                            et_beneficiary_name.setEnabled(true);
//                            et_beneficiary_accountno.setEnabled(true);
//                            et_ifsc_code.setEnabled(true);
//                            et_beneficiary_mobno.setEnabled(true);


                            frameLayout.setVisibility(View.GONE);
                            et_amount.setVisibility(View.GONE);
                            et_description.setVisibility(View.GONE);

                            et_beneficiary_name.setText("");
                            et_beneficiary_accountno.setText("");
                            et_ifsc_code.setText("");
                            et_beneficiary_mobno.setText("");

                            et_beneficiary_name.setEnabled(true);
                            et_beneficiary_accountno.setEnabled(true);
                            et_ifsc_code.setEnabled(true);
                            et_beneficiary_mobno.setEnabled(true);
                            tvExcepionmoneyorder.setText("");

                            tv_isverified.setText("");
                            rbtn_verfiy.setVisibility(View.VISIBLE);
                            rdGrp.setVisibility(View.GONE);
                            or.setVisibility(View.VISIBLE);
                            et_ifsc_select.setVisibility(View.VISIBLE);
                        }
                        else {

                            et_beneficiary_name.setText(BeneficiaryName);
                            et_beneficiary_accountno.setText(AccountNumber);
                            et_ifsc_code.setText(IFSCCode);
                            et_beneficiary_mobno.setText(BeneficiaryMobileNumber);

                            et_beneficiary_name.setEnabled(false);
                            et_beneficiary_accountno.setEnabled(false);
                            et_ifsc_code.setEnabled(false);
                            et_beneficiary_mobno.setEnabled(false);

                        }
                    }

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            //progressBar.setVisibility(View.GONE);

            comProgressDialog.cancelProgress();
        }
    }


    public class SetBeneficiaryAsVerifiedBackground extends AsyncTask<Void,Void,String>
    {
        String SetBeneficiaryAsVerified_response;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
            //comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(Void... params)
        {

            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.SetBeneficiaryAsVerified);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.beneficiaryID,BeneficiaryID));


                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                SetBeneficiaryAsVerified_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return SetBeneficiaryAsVerified_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<SetBeneficiaryAsVerifiedResponse>>(){}.getType();
                    ResponseBase<SetBeneficiaryAsVerifiedResponse> responseResponseBase=gson.fromJson(SetBeneficiaryAsVerified_response,type);
                    SetBeneficiaryAsVerifiedResponse setBeneficiaryAsVerifiedResponse=responseResponseBase.responseData;
                    boolean UpdateStatus=setBeneficiaryAsVerifiedResponse.UpdateStatus;
                    if(UpdateStatus)
                    {
                        tv_isverified.setVisibility(View.VISIBLE);
                        tv_isverified.setText(R.string.is_verified);
                        rbtn_verfiy.setVisibility(View.GONE);

                    }
                    else
                    {
                        tv_isverified.setVisibility(View.VISIBLE);
                        tv_isverified.setText(R.string.isnot_verified);
                        rbtn_verfiy.setVisibility(View.VISIBLE);
                    }
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            //progressBar.setVisibility(View.GONE);
            //comProgressDialog.cancelProgress();
        }
    }

    public void getIFSCCODE()
    {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.getifsccodelayout, null);
        tvexceptionIFSCCODE=(TextView)alertLayout.findViewById(R.id.tvexceptionIFSCCODE);
        spinnerGetIFSCBanks = (Spinner)alertLayout.findViewById(R.id.spinnerGetIFSCBanks);
        spinnerGetIFSCStates = (Spinner)alertLayout.findViewById(R.id.spinnerGetIFSCStates);
        spinnerGetIFSCCity = (Spinner)alertLayout.findViewById(R.id.spinnerGetIFSCCity);
        spinnerGetIFSCBranch = (Spinner)alertLayout.findViewById(R.id.spinnerGetIFSCBranch);


        listGetIFSCBanks.clear();
        listGetIFSCStates.clear();
        listGetIFSCCity.clear();
        listGetIFSCBranch.clear();

        listGetIFSCBanks=new ArrayList<String>();
        listGetIFSCBanks.add(getString(R.string.select_bank));

        listGetIFSCStates=new ArrayList<String>();
        listGetIFSCStates.add(getString(R.string.Select_State));

        listGetIFSCCity=new ArrayList<String>();
        listGetIFSCCity.add(getString(R.string.Select_City));

        listGetIFSCBranch=new ArrayList<String>();
        listGetIFSCBranch.add(getString(R.string.Select_Branch));



        GetIFSCBanks getIFSCBanks=new GetIFSCBanks();
        getIFSCBanks.execute();
//        GetIFSCStates getIFSCStates=new GetIFSCStates();
//        getIFSCStates.execute();
//
//        GetIFSCCity getIFSCCity=new GetIFSCCity();
//        getIFSCCity.execute();
//
//        GetIFSCBranch getIFSCBranch=new GetIFSCBranch();
//        getIFSCBranch.execute();
        alert1 = new AlertDialog.Builder(getActivity());
        alert1.setTitle("IFSC Code");
        alert1.setView(alertLayout);
        alert1.setCancelable(false);
        alert1.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert1.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {

            }
        });


        dialog1 = alert1.create();
        dialog1.show();
        Button theButton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new ValidateCustomDialogue(dialog1,spinnerGetIFSCBanks,spinnerGetIFSCStates,spinnerGetIFSCCity,spinnerGetIFSCBranch,str_IFSCCode));
    }


    public class GetIFSCBanks extends AsyncTask<Void,Void,String>
    {
        String GetIFSCBanks_response;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
           // comProgressDialog.showProgress();

        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetIFSCBanks);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetIFSCBanks_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetIFSCBanks_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetIFSCBanksResponse[]>>(){}.getType();
                    ResponseBase<GetIFSCBanksResponse[]> responseBase=gson.fromJson(GetIFSCBanks_response,type);
                    if(responseBase.responseData!=null)
                    {
                        for(GetIFSCBanksResponse getIFSCBanksResponse:responseBase.responseData)
                        {
                            BankName=getIFSCBanksResponse.BankName;

                            listGetIFSCBanks.add(BankName);
                        }
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listGetIFSCBanks) {

                    public View getView(int position, View convertView, ViewGroup parent) {
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
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                spinnerGetIFSCBanks.setAdapter(adapter);

                spinnerGetIFSCBanks .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        selected_BankName=spinnerGetIFSCBanks.getSelectedItem().toString();


                        listGetIFSCStates.clear();
                        listGetIFSCStates=new ArrayList<String>();
                        listGetIFSCStates.add(getString(R.string.Select_State));

                        GetIFSCStates getIFSCStates=new GetIFSCStates();
                        getIFSCStates.execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //progressBar.setVisibility(View.GONE);
            //comProgressDialog.cancelProgress();
        }
    }


    public class GetIFSCStates extends AsyncTask<Void,Void,String>
    {
        String GetIFSCStates_response;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
           //comProgressDialog.showProgress();

        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetIFSCStates);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.bankName,selected_BankName));
                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetIFSCStates_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetIFSCStates_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetIFSCStatesResponse[]>>(){}.getType();
                    ResponseBase<GetIFSCStatesResponse[]> responseBase=gson.fromJson(GetIFSCStates_response,type);
                    if(responseBase.responseData!=null)
                    {
                        for(GetIFSCStatesResponse getIFSCStatesResponse:responseBase.responseData)
                        {
                            StateName=getIFSCStatesResponse.StateName;

                            listGetIFSCStates.add(StateName);
                        }
                    }
                    if(listGetIFSCStates!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listGetIFSCStates) {

                            public View getView(int position, View convertView, ViewGroup parent) {
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
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter.notifyDataSetChanged();
                        spinnerGetIFSCStates.setAdapter(adapter);

                        spinnerGetIFSCStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                selected_StateName = spinnerGetIFSCStates.getSelectedItem().toString();


                                listGetIFSCCity.clear();
                                listGetIFSCCity=new ArrayList<String>();
                                listGetIFSCCity.add(getString(R.string.Select_City));


                                GetIFSCCity getIFSCCity = new GetIFSCCity();
                                getIFSCCity.execute();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //progressBar.setVisibility(View.GONE);
            //comProgressDialog.cancelProgress();

        }
    }


    public class GetIFSCCity extends AsyncTask<Void,Void,String>
    {
        String GetIFSCCity_resp;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);
            //comProgressDialog.showProgress();

        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetIFSCCity);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair("bankName",selected_BankName));
                nameValuePairs.add(new BasicNameValuePair("stateName",selected_StateName));



                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetIFSCCity_resp= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetIFSCCity_resp;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetIFSCCityResponse[]>>(){}.getType();
                    ResponseBase<GetIFSCCityResponse[]> responseBase=gson.fromJson(GetIFSCCity_resp,type);
                    if(responseBase.responseData!=null)
                    {
                        for(GetIFSCCityResponse getIFSCCityResponse:responseBase.responseData)
                        {
                            CityName=getIFSCCityResponse.CityName;

                            listGetIFSCCity.add(CityName);
                        }
                    }
                    if(listGetIFSCCity!=null) {

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listGetIFSCCity) {

                            public View getView(int position, View convertView, ViewGroup parent) {
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
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter.notifyDataSetChanged();
                        spinnerGetIFSCCity.setAdapter(adapter);

                        spinnerGetIFSCCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                selected_CityName = spinnerGetIFSCCity.getSelectedItem().toString();


                                listGetIFSCBranch.clear();
                                listGetIFSCBranch=new ArrayList<String>();
                                listGetIFSCBranch.add(getString(R.string.Select_Branch));

                                GetIFSCBranch getIFSCBranch = new GetIFSCBranch();
                                getIFSCBranch.execute();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //progressBar.setVisibility(View.GONE);
            //comProgressDialog.cancelProgress();
        }
    }


    public class GetIFSCBranch extends AsyncTask<Void,Void,String>
    {

        Imps_hashmap imps_hashmap=new Imps_hashmap();
        Map<String, List<String>> map1;
        String GetIFSCBranch_res;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
            //comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetIFSCBranch);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair("bankName",selected_BankName));
                nameValuePairs.add(new BasicNameValuePair("stateName",selected_StateName));
                nameValuePairs.add(new BasicNameValuePair("cityName",selected_CityName));



                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetIFSCBranch_res= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetIFSCBranch_res;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetIFSCBranchResponse[]>>(){}.getType();
                    ResponseBase<GetIFSCBranchResponse[]> responseBase=gson.fromJson(GetIFSCBranch_res,type);
                    map1 = new HashMap<String, List<String>>();
                    if(responseBase.responseData!=null)
                    {
                        for(GetIFSCBranchResponse getIFSCBranchResponse:responseBase.responseData)
                        {
                            BranchName=getIFSCBranchResponse.BranchName;
                            IFSCCode=getIFSCBranchResponse.IFSCCode;

                            List<String> moset = new ArrayList<String>();
                            moset.add(BranchName);
                            moset.add(IFSCCode);

                            map1.put(BranchName, moset);

                            listGetIFSCBranch.add(BranchName);
                            imps_hashmap.storeBranchList(String_url.payment_mode, map1);
                        }
                    }
                    if(listGetIFSCBranch!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listGetIFSCBranch) {

                            public View getView(int position, View convertView, ViewGroup parent) {
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
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter.notifyDataSetChanged();

                        spinnerGetIFSCBranch.setAdapter(adapter);

                        spinnerGetIFSCBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                selected_BranchName = spinnerGetIFSCBranch.getSelectedItem().toString();


                                if (!spinnerGetIFSCBranch.getSelectedItem().toString().equals("Select Branch")) {
                                    str_BranchName = imps_hashmap.getFavoriteName(spinnerGetIFSCBranch.getSelectedItem().toString());
                                    str_IFSCCode = imps_hashmap.getFavoriteMobileNumber(spinnerGetIFSCBranch.getSelectedItem().toString());

                                    et_ifsc_code.setText("" + str_IFSCCode);
                                    //et_ifsc_code.setEnabled(false);
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //progressBar.setVisibility(View.GONE);
           // comProgressDialog.cancelProgress();
        }
    }
    public void validateCreateUser(){


        str_MobNo= autoCreateUserMobNo.getText().toString();
        str_UserName= et_CreateUserName.getText().toString();
        str_DOB= et_DateOfBirth.getText().toString();
        str_EmailAddress= et_EmailAddress.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(str_MobNo.length()<10)
        {
            getResponseDialog(getString(R.string.error_phone_length));
            //tvExcepionmoneyorder.setText(R.string.error_phone_length);
        }
        else if(str_UserName.equals(""))
        {
            getResponseDialog(getString(R.string.error_UserName));
            //tvExcepionmoneyorder.setText(R.string.error_UserName);
        }
        else if(str_DOB.equals("")){
            getResponseDialog(getString(R.string.error_DOB));
            //tvExcepionmoneyorder.setText(R.string.error_DOB);
        }
        else if(str_EmailAddress.equals("") || !str_EmailAddress.matches(emailPattern))
        {
            getResponseDialog(getString(R.string.error_EmailAddress));
            //tvExcepionmoneyorder.setText(R.string.error_EmailAddress);
        }

        else {
            //  tvExcepionmoneyorder.setText("Success");
            RegisterConsumer registerConsumer= new RegisterConsumer();
            registerConsumer.execute();
        }


    }

    private class RegisterConsumer extends AsyncTask<Void,Void,String>{

        String ErrorMessage,message,responseRegisterConsumer;
        Boolean RegistrationStatus;
        int RegisteredCustomerID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressBar.setVisibility(View.VISIBLE);
           comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(Void... params) {

            try
            {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.RegisterConsumer);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.MobileNumberIMPs,str_MobNo));
                nameValuePairs.add(new BasicNameValuePair(String_url.Name,str_UserName));
                nameValuePairs.add(new BasicNameValuePair(String_url.DateOfBirth,str_DOB));
                nameValuePairs.add(new BasicNameValuePair(String_url.EmailAddress, str_EmailAddress));
                HttpParams httpParams=httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                responseRegisterConsumer  = EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return responseRegisterConsumer;
        }

        @Override
        protected void onPostExecute(String s) {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<RegisterConsumerResponse>>(){}.getType();
                    ResponseBase<RegisterConsumerResponse> responseBase=gson.fromJson(responseRegisterConsumer,type);
                    RegisterConsumerResponse registerConsumerResponse=responseBase.responseData;
                    if(responseBase.responseData!=null)
                    {
                        RegisteredCustomerID=registerConsumerResponse.RegisteredCustomerID;
                        RegistrationStatus=registerConsumerResponse.RegistrationStatus;
                        ErrorMessage=registerConsumerResponse.ErrorMessage;

                    }
                    if(RegistrationStatus)
                    {
                        comProgressDialog.cancelProgress();
                        // tvExcepionmoneyorder.setText(ErrorMessage);
                        getResponseDialog(ErrorMessage);
                        moneyOrderPage1.setVisibility(View.VISIBLE);
                        moneyOrderPage2.setVisibility(View.GONE);
                        moneyOrderPage3.setVisibility(View.GONE);
                        moneyOrderPage4.setVisibility(View.GONE);
                        moneyOrderPage5.setVisibility(View.GONE);
                        et_customerno.setText(str_MobNo);
                    }
                    else {
                        comProgressDialog.cancelProgress();
                        //  tvExcepionmoneyorder.setText(ErrorMessage);
                        getResponseDialog(ErrorMessage);
                        moneyOrderPage1.setVisibility(View.VISIBLE);
                        moneyOrderPage2.setVisibility(View.GONE);
                        moneyOrderPage3.setVisibility(View.GONE);
                        moneyOrderPage4.setVisibility(View.GONE);
                        moneyOrderPage5.setVisibility(View.GONE);


                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            // progressBar.setVisibility(View.GONE);
            //comProgressDialog.cancelProgress();
        }


    }

    private class SubmitPaymentIPINBackground extends AsyncTask<Void,Void,String>{

        String RegisteredCustomerID,ErrorMessage,message,isoTransactionId,responseImps,isoTransactionId_responseData,momStatusCode_responseData;
        Boolean RegistrationStatus;
        String message_responseData,ipin_Response;

        // int status_responseData;
        String et_ifsc_code_response ;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            comProgressDialog.showProgress();
            et_ifsc_code_response=et_ifsc_code.getText().toString();

            //progressBar.setVisibility(View.VISIBLE);
            //comProgressDialog.showProgress();
            int id = rdGrp.getCheckedRadioButtonId();
            if (id == -1){
                //no item selected
            }

            else{
                if (id == R.id.rbtn_pay){
                    //Do something with the button
                    str_amount=et_amount.getText().toString();
                }
                else if (id == R.id.rbtn_verfiy){
                    //Do something with the button
                    str_amount="1";
                }

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try
            {

                Gson gson=new GsonBuilder().create();
                IMPSTransactionRequest impsTransactionRequest=new IMPSTransactionRequest();
                Type type=new TypeToken<IMPSTransactionRequest>(){}.getType();
                Encryption encryption=new Encryption(getActivity());

                impsTransactionRequest.setUserId(CustomerID);
                impsTransactionRequest.setTransactionAmount(Float.parseFloat(str_amount));
                impsTransactionRequest.setServiceChargeAmount(String_url.serviceCharge_Amount);
                impsTransactionRequest.setiPin(Integer.parseInt(str_ipin));
                impsTransactionRequest.setTpin(encryption.SHA1(str_tpin));
                impsTransactionRequest.setConsumerId(CustomerID_imps);
                impsTransactionRequest.setbeneficiaryId(Integer.parseInt(BeneficiaryID));
                impsTransactionRequest.setaccountName(BeneficiaryName);
                impsTransactionRequest.setAccountNumber(AccountNumber);
                impsTransactionRequest.setifscCode(et_ifsc_code_response);
                impsTransactionRequest.setCustomerMobileNumber(Long.parseLong(str_beneficiary_mobno));
                impsTransactionRequest.setCircleId(String_url.circleId_int);
                impsTransactionRequest.setOperaterId(String_url.operaterId_int);
                impsTransactionRequest.setRechargeType(String_url.rechargeType_int);
                impsTransactionRequest.setChannelId(String_url.channelId);
                impsTransactionRequest.settransactionNarration(str_transition_description);

                String json=gson.toJson(impsTransactionRequest,type);




                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.SubmitPaymentIPIN);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.impsPaymentRequest,json));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams,45000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                ipin_Response = EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return ipin_Response;
        }

        @Override
        protected void onPostExecute(String s) {
            try
            {
                if(s!=null) {

                    Gson gson1=new GsonBuilder().create();
                    Type type1=new TypeToken<ResponseBase<SubmitPaymentIPINResponse[]>>(){}.getType();
                    ResponseBase<SubmitPaymentIPINResponse[]> responseBase=gson1.fromJson(ipin_Response,type1);
                    if(responseBase.responseData!=null) {

                        for (SubmitPaymentIPINResponse submitPaymentIPINResponse : responseBase.responseData) {
                            momStatusCode_responseData = submitPaymentIPINResponse.momStatusCode;
                            isoTransactionId_responseData = submitPaymentIPINResponse.isoTransactionId;
                            message_responseData = submitPaymentIPINResponse.momMessage;


                        }
                    }
                    if (momStatusCode_responseData.equals("0") || momStatusCode_responseData.equals("2")) {
                        final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                        responseDialog1.showResponseDialog(2,
                                getString(R.string.Response),
                                getString(R.string.message),
//                            message_responseData,
                                getString(R.string.trans_success),
                                getString(R.string.TransactionId),
                                isoTransactionId_responseData,
                                null,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //  if (momStatusCode_responseData.equals("0") || momStatusCode_responseData.equals("2")) {
                                        GetBalanceClass getBalanceClass = new GetBalanceClass(getActivity(), getActivity());
                                        // getBalanceClass.UpdateBalance();
                                        getBalanceClass.execute();
                                        // }
                                        moneyOrderPage1.setVisibility(View.VISIBLE);
                                        moneyOrderPage2.setVisibility(View.GONE);
                                        moneyOrderPage3.setVisibility(View.GONE);
                                        moneyOrderPage4.setVisibility(View.GONE);
                                        moneyOrderPage5.setVisibility(View.GONE);
                                        moneyOrderPage6.setVisibility(View.GONE);

                                        spinner.setSelection(0);
                                        spinner_pay.setSelection(0);


                                        tvExcepionmoneyorder.setText("");
                                        et_customerno.setText("");
                                        et_beneficiary_name.setText("");
                                        et_beneficiary_accountno.setText("");
                                        et_ifsc_code.setText("");
                                        et_beneficiary_mobno.setText("");
                                        et_amount.setText("");
                                        et_description.setText("");
                                        autoCreateUserMobNo.setText("");
                                        et_CreateUserName.setText("");
                                        et_DateOfBirth.setText("");
                                        et_EmailAddress.setText("");
                                        et_tpin.setText("");
                                        et_ipin.setText("");
                                        btntpinsubmit.setEnabled(true);
                                        responseDialog1.dialog.dismiss();

                                    }
                                }

                        );
                    } else {
                        final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                        responseDialog1.showResponseDialog(1,
                                getString(R.string.Response),
                                null, null, null, null,
                                message_responseData,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btntpinsubmit.setEnabled(true);
                                        responseDialog1.dialog.dismiss();
                                    }
                                }


                        );
                    }
                    //progressBar.setVisibility(View.GONE);
                  //  comProgressDialog.cancelProgress();
                    comProgressDialog.cancelProgress();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


        }


    }


    private class resent_Ipin extends AsyncTask<Void,Void,String>
    {
        String resend_ipin_response;
        @Override
        protected void onPreExecute()
        {

            comProgressDialog.showProgress();
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
            //comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.ResendIPIN);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.Customer_FavouriteList, String.valueOf(CustomerID_imps)));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                resend_ipin_response = EntityUtils.toString(entity);


            }

            catch (Exception e) {
                e.printStackTrace();
            }

            return resend_ipin_response;
        }
        @Override
        protected void onPostExecute(String s)
        {
            try
            {
                if(s!=null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase>() {
                    }.getType();
                    ResponseBase responseBase = gson.fromJson(resend_ipin_response, type);
                    String message = responseBase.message.toLowerCase();

                    if (message.equals("success")) {

                        Toast.makeText(getActivity(), R.string.resent_Ipin, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), R.string.ipin_failed, Toast.LENGTH_LONG).show();
                    }
                    //progressBar.setVisibility(View.GONE);
                   // comProgressDialog.cancelProgress();
                    comProgressDialog.cancelProgress();
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private class CheckIFSCCodeBackground extends AsyncTask<Void,Void,String>
    {
        String message;

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


    }


    private class RegisterBeneficiaryBackground extends AsyncTask<Void,Void,String>
    {
        String newBeneficiary_response;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);
           // comProgressDialog.showProgress();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try{
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.RegisterBeneficiary);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.consumerId, String.valueOf(CustomerID_imps)));
                nameValuePairs.add(new BasicNameValuePair(String_url.beneficiaryName, str_beneficiary_name));
                nameValuePairs.add(new BasicNameValuePair(String_url.accountNumber,str_beneficiary_accountno ));
                nameValuePairs.add(new BasicNameValuePair(String_url.iFSCCode, str_ifsc_code));
                nameValuePairs.add(new BasicNameValuePair(String_url.beneficiaryMobileNumber, str_beneficiary_mobno));





                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                newBeneficiary_response= EntityUtils.toString(entity);
                //Toast.makeText(getActivity(),"this.responseBody"+a,Toast.LENGTH_SHORT).show();




            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return newBeneficiary_response;
        }
        @Override
        protected void onPostExecute(String s)
        {
            try
            {
                if(s!=null) {

                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<RegisterBeneficiaryResponse>>() {
                    }.getType();
                    ResponseBase<RegisterBeneficiaryResponse> responseBase = gson.fromJson(newBeneficiary_response, type);
                    RegisterBeneficiaryResponse registerBeneficiaryResponse = responseBase.responseData;
                    if (responseBase.responseData != null) {
                        String error_message = registerBeneficiaryResponse.ErrorMessage;
                        int registeredBeneficiaryID = registerBeneficiaryResponse.RegisteredBeneficiaryID;
                        boolean registrationStatus = registerBeneficiaryResponse.RegistrationStatus;

                        if (registrationStatus) {

                            final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());

                            responseDialog1.showResponseDialog(2, title,
                                    getString(R.string.message),
                                    error_message,
                                    getString(R.string.BeneficiaryID),
                                    String.valueOf(registeredBeneficiaryID),
                                    null,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            tvExcepionmoneyorder.setText("");
                                            et_customerno.setText("");
                                            et_beneficiary_mobno.setText("");
                                            et_beneficiary_accountno.setText("");
                                            et_beneficiary_name.setText("");
                                            et_ifsc_code.setText("");
                                            responseDialog1.dialog.dismiss();
                                            moneyOrderPage1.setVisibility(View.VISIBLE);
                                            moneyOrderPage2.setVisibility(View.GONE);
                                            moneyOrderPage3.setVisibility(View.GONE);
                                            moneyOrderPage4.setVisibility(View.GONE);
                                            moneyOrderPage6.setVisibility(View.GONE);
                                            moneyOrderPage5.setVisibility(View.GONE);

                                        }
                                    }

                            );

                        } else {

                            final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                            responseDialog1.showResponseDialog(1,
                                    getString(R.string.Response),
                                    null, null, null, null,
                                    error_message,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            responseDialog1.dialog.dismiss();
                                        }
                                    }


                            );

                        }
                        // progressBar.setVisibility(View.GONE);
                       // comProgressDialog.cancelProgress();
                    }
                }
            }
            catch(Exception e)
            {

            }
        }


    }

    private class CheckDuplicateBeneficiary extends AsyncTask<Void,Void,Integer>
    {

        String CheckDuplicateBeneficiary_response;
        String [] c;
        int BeneficiaryStatus;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
           // comProgressDialog.showProgress();
            c=new String[2];
        }
        @Override
        protected Integer doInBackground(Void... params)
        {
            try{
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.CheckDuplicateBeneficiary);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ConsumerId, String.valueOf(CustomerID_imps)));
                nameValuePairs.add(new BasicNameValuePair(String_url.AccountNumber,str_beneficiary_accountno ));
                nameValuePairs.add(new BasicNameValuePair(String_url.IFSCCode, str_ifsc_code));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                CheckDuplicateBeneficiary_response= EntityUtils.toString(entity);
                //Toast.makeText(getActivity(),"this.responseBody"+a,Toast.LENGTH_SHORT).show();

                if(CheckDuplicateBeneficiary_response!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<CheckDuplicateBeneficiaryResponse>>() {}.getType();
                    ResponseBase<CheckDuplicateBeneficiaryResponse> responseBase=gson.fromJson(CheckDuplicateBeneficiary_response,type);
                    CheckDuplicateBeneficiaryResponse checkDuplicateBeneficiaryResponse=responseBase.responseData;
                    if(checkDuplicateBeneficiaryResponse!=null)
                    {
                        BeneficiaryStatus=checkDuplicateBeneficiaryResponse.BeneficiaryStatus;
                        String AccountNumber=checkDuplicateBeneficiaryResponse.AccountNumber;
                        String IFSCCode=checkDuplicateBeneficiaryResponse.IFSCCode;



                    }
                }




            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return BeneficiaryStatus;
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
                        btntpinsubmit.setEnabled(true);
                        btn_send.setEnabled(true);
                        responseDialog1.dialog.dismiss();
                    }
                }


        );
    }

}


