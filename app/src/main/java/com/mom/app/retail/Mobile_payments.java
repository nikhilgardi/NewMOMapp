package com.mom.app.retail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mom.app.retail.Adapters.BrowseplaneAdap;
import com.mom.app.retail.Request.PaymentTransactionRequest;
import com.mom.app.retail.Response.CustomerFavoriteListByCustomerIDResponse;
import com.mom.app.retail.Response.CustomerRelationShipResponse;
import com.mom.app.retail.Response.GetPlanByProductIdAndPlanTypeResponse;
import com.mom.app.retail.Response.GetPlanTypeByProductIdResponse;
import com.mom.app.retail.Response.GetProviderNameByProviderTypeIDResponse;
import com.mom.app.retail.Response.GetServiceProviderAndCircleByMobileSeriesResponse;
import com.mom.app.retail.Response.ProductIDResponse;
import com.mom.app.retail.Response.ResponseBase;
import com.mom.app.retail.Response.PaymentResponseResult;
import com.mom.app.retail.Response.ServiceCirclesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

public class Mobile_payments extends Fragment implements TextWatcher{

    String string_FavoriteMobileNumber_update,str_FavoriteName_update,string_FavoriteMobileNumber_insert,str_FavoriteName_insert;
    String string_mobile_number,str_amount,string_tpin;
    public TextView tvMobText,tvExeption,browseplane;
    private EditText et_tpin,etAmount,et_FavoriteName,et_FavoriteNameupdate;
    public String contacts,spinnerMobPay,spinnerSelectOpr,insertspinner,str_CircleID,str_CircleName;
    public static final String PREFS_NAME = "MyApp_Settings";
    private AutoCompleteTextView et_FavoriteMobileNumberupdate=null,et_FavoriteMobileNumber=null;
    private Context context;
    private Button btntpinsubmit,btntpincancel,btn_cancel,btnPay,add_favorite,new_mobile_recharge,btnAdd,btn_Update,btn_Delete,btn_New,btn_add_list,
            btnCalcelnew,btnCancelinsert,btnAddupdate,btnCancelinsertupdate,btnaction,btn_new_mobile_recharge1,btn_recharge_with_favorite_list;
    private ImageView contact,updateFavouriteContact,addFavouriteContact;
    public Typeface Condensed,Light,Normal;
    List<String> payment_list,payment_list_response;
    String string_mobile_numberplan;

    JSONArray contacts1 = null;
    String serviceProviderName,str_spinner,message_insert,str_favourite_list_delete,str_ServiceProviderName,str_ServiceProviderID;
    Spinner spinnerplan,spinnerRechargeType,spinnerSelectOperator,spinnerAddFav,spinnervalue,spinnerUpdateFav,spinneraction,spinnerServiceProviderName,spinnerServiceCircleName;
    int maxLength = 12;
    ListView list,listview1;
    long CustomerID;
    private LinearLayout macPage1,macPage6,amountid;
    private RelativeLayout macPage3,macPage2,macPage7,macPage5,macPage4;
    HashMap<String, Integer> hashMapBranch;
    List<String> branchList3,branchList,branchList1,branchList2,branchList4,branchList5,serviceProviderImagelist;
    List<String> favorite_id;
    int favorite_RelationshipID=0,rechargeType;
    String str_CustomerFavoriteID,str_FavoriteName,str_FavoriteMobileNumber,str_RelationshipID;
    private Spinner spinnerCircle;
    private String circleName;
    private long str_CustomerId;
    ListView browseplanelistview;
    HttpPost httppost;
    HttpClient httpclient;

    private ProgressBar progressBar,progressBar_browseplan;
    double wallet_balance;
    int WalletCustomerBalance,ServiceProviderID,ServiceCircleID;
    int id,RelationshipID,CustomerFavoriteID;
    String FavoriteMobileNumber,FavoriteName;
    public static final int PICK_CONTACT    = 4;
    public static final int PICK_CONTACT_FAV_LIST_UPDATE  = 5;
    public static final int PICK_CONTACT_FAV_LIST_ADD   = 6;
    BrowseplaneAdap adapter1;
    String roundedMrp;

    HashMap<String,Integer> ServiceProviderName_hashMapBranch,CircleName_hashMapBranch;

    int check=0,check1=0;
    String str_serviceProviderId,str_serviceProviderTypeId;
    List<RowItem> rowItems;

    Map<String,Integer> mapPrepaid,mapPostpaid;

    AlertDialog.Builder alert1;
    AlertDialog dialog1;
    int str_rechargeType;
    private String[] contactArray;
    ArrayList<String> planMRP_List,planDescription_List,planValidity_List;

    private AutoCompleteTextView etMob=null;

    private SQLiteAdapter sqLiteAdapter;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    String BankName,selected_ServiceProviderName,StateName,PlanType,select_spinnerplan,planMRP,planDescription,planValidity;

    int serviceProviderId,circleId,productid;

    ArrayList<Product> products = new ArrayList<Product>();
    GetServiceProviderNamebrowseplan_mode getServiceProviderNamebrowseplan_mode;

    ComProgressDialog comProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragments_mobile_payments, container, false);

        NetworkConnection networkConnection=new NetworkConnection(getActivity());
        boolean isNetworkAvailable=networkConnection.isNetworkAvailable();

        if(!isNetworkAvailable)
        {
            NetworkConnection.ExitAppDialog(getActivity());
        }
        else {

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            comProgressDialog = new ComProgressDialog(getActivity(),getActivity());

            Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            contact=(ImageView)v.findViewById(R.id.contact);
            contact.setImageResource(R.drawable.contact);
            updateFavouriteContact=(ImageView)v.findViewById(R.id.UpdateFavouriteContact);
            updateFavouriteContact.setImageResource(R.drawable.contact);
            addFavouriteContact=(ImageView)v.findViewById(R.id.addFavouriteContact);
            addFavouriteContact.setImageResource(R.drawable.contact);

            progressBar=(ProgressBar)v.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            tvExeption=(TextView)v.findViewById(R.id.tvExep);
            tvExeption.setTypeface(Light);
            btnPay=(Button)v.findViewById(R.id.btnPay);
            btnPay.setTypeface(Normal);
            add_favorite=(Button)v.findViewById(R.id.add_favorite);
            add_favorite.setTypeface(Normal);
            new_mobile_recharge=(Button)v.findViewById(R.id.new_mobile_recharge);
            new_mobile_recharge.setTypeface(Normal);
            btnAdd=(Button)v.findViewById(R.id.btnAdd);
            btnAdd.setTypeface(Normal);
            btn_new_mobile_recharge1=(Button)v.findViewById(R.id.btn_new_mobile_recharge1);
            btn_new_mobile_recharge1.setTypeface(Normal);
            btn_recharge_with_favorite_list=(Button)v.findViewById(R.id.btn_recharge_with_favorite_list);
            btn_recharge_with_favorite_list.setTypeface(Normal);
            btn_cancel=(Button)v.findViewById(R.id.btn_cancel);
            btn_cancel.setTypeface(Normal);
            browseplane=(TextView)v.findViewById(R.id.browseplane);
            browseplane.setTypeface(Light);
            btntpincancel=(Button)v.findViewById(R.id.btntpincancel);
            btntpincancel.setTypeface(Normal);
            btntpinsubmit=(Button)v.findViewById(R.id.btntpinsubmit);
            btntpinsubmit.setTypeface(Normal);
            btnCalcelnew=(Button)v.findViewById(R.id.btnCalcelnew);
            btnCalcelnew.setTypeface(Normal);
            btnCancelinsert=(Button)v.findViewById(R.id.btnCancelinsert);
            btnCancelinsert.setTypeface(Normal);
            btnAddupdate=(Button)v.findViewById(R.id.btnAddupdate);
            btnAddupdate.setTypeface(Normal);
            btnCancelinsertupdate=(Button)v.findViewById(R.id.btnCancelinsertupdate);
            btnCancelinsertupdate.setTypeface(Normal);
            btnaction=(Button)v.findViewById(R.id.btnaction);
            btnaction.setTypeface(Normal);
            tvMobText=(TextView)v.findViewById(R.id.tv_Mobile_paymentsText);
            tvMobText.setTypeface(Light);
//
            SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            CustomerID = shared.getLong(String_url.customerId, 0);
            sqLiteAdapter= new SQLiteAdapter(getActivity());
            sqLiteAdapter.openToReadBalanceDataBase();
            wallet_balance=sqLiteAdapter.getBalanceFromDB();
            sqLiteAdapter.close();


            planMRP_List=new ArrayList<String>();
            planDescription_List=new ArrayList<String>();
            planValidity_List=new ArrayList<String>();

            WalletCustomerBalance = (int) wallet_balance;


            progressDialog= new ProgressDialog(getActivity());

            macPage1 = (LinearLayout) v.findViewById(R.id.macPage1);
            macPage2 = (RelativeLayout) v.findViewById(R.id.macPage2);
            macPage3 = (RelativeLayout) v.findViewById(R.id.macPage3);
            macPage4 = (RelativeLayout) v.findViewById(R.id.macPage4);
            macPage5 = (RelativeLayout) v.findViewById(R.id.macPage5);
            macPage6 = (LinearLayout) v.findViewById(R.id.macPage6);
            macPage7 = (RelativeLayout) v.findViewById(R.id.macPage7);
            listview1 = (ListView) v.findViewById(R.id.listview1);

            spinnerRechargeType = (Spinner) v.findViewById(R.id.spinnerMobPay);
            spinnerSelectOperator = (Spinner) v.findViewById(R.id.spinnerSelectOpr);
            spinnerAddFav = (Spinner) v.findViewById(R.id.spinnerAddFav);
            spinnervalue = (Spinner) v.findViewById(R.id.spinnervalue);
            spinnerUpdateFav = (Spinner) v.findViewById(R.id.spinnerUpdateFav);
            spinneraction = (Spinner) v.findViewById(R.id.spinneraction);
            SharedPreferences mbpayment = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            str_CustomerId = mbpayment.getLong(String_url.CustomerID, 0);


            CustomerFavoriteListByCustomerIDBackground customerFavoriteListByCustomerIDBackground=new CustomerFavoriteListByCustomerIDBackground();
            customerFavoriteListByCustomerIDBackground.execute();

            etAmount = (EditText) v.findViewById(R.id.et_enter_amount_recharge);
            etAmount.setTypeface(Light);

            etAmount.setImeOptions(EditorInfo.IME_ACTION_DONE);


            getServiceProviderNamebrowseplan_mode = new GetServiceProviderNamebrowseplan_mode();


            // et_FavoriteMobileNumber = (EditText) v.findViewById(R.id.et_FavoriteMobileNumber);
            et_FavoriteMobileNumber = (AutoCompleteTextView) v.findViewById(R.id.et_FavoriteMobileNumber);
            et_FavoriteMobileNumber.setTypeface(Light);

            et_FavoriteName = (EditText) v.findViewById(R.id.et_FavoriteName);
            et_FavoriteName.setTypeface(Light);

            et_FavoriteName.setImeOptions(EditorInfo.IME_ACTION_DONE);

            et_FavoriteMobileNumberupdate=(AutoCompleteTextView)v.findViewById(R.id.et_FavoriteMobileNumberupdate);

            // et_FavoriteMobileNumberupdate = (EditText) v.findViewById(R.id.et_FavoriteMobileNumberupdate);
            et_FavoriteMobileNumberupdate.setTypeface(Light);

            et_tpin = (EditText) v.findViewById(R.id.et_tpin);

            et_tpin.setTypeface(Light) ;
            et_tpin.setImeOptions(EditorInfo.IME_ACTION_DONE);

            et_FavoriteNameupdate = (EditText) v.findViewById(R.id.et_FavoriteNameupdate);
            et_FavoriteNameupdate.setTypeface(Light);
            et_FavoriteNameupdate.setImeOptions(EditorInfo.IME_ACTION_DONE);



            btntpincancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    macPage1.setVisibility(View.GONE);
                    macPage2.setVisibility(View.GONE);
                    macPage3.setVisibility(View.VISIBLE);
                    macPage4.setVisibility(View.GONE);
                    macPage5.setVisibility(View.GONE);
                    macPage6.setVisibility(View.GONE);
                    macPage7.setVisibility(View.GONE);
                    tvExeption.setText(null);


                }
            });


            branchList3=new ArrayList<String>();
            branchList3.add(getString(R.string.spinner_title));

            branchList4=new ArrayList<String>();
            branchList4.add(getString(R.string.spinner_title));


            branchList5=new ArrayList<String>();
            branchList5.add(getString(R.string.spinner_plan));


            browseplane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    browseplanefunction();

                }
            });
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btntpinsubmit.setEnabled(true);

                    validate();

                }
            });
            btntpinsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btntpinsubmit.setEnabled(false);
                    validate_tpin();
                    et_tpin.setText(null);

                }
            });
            btn_new_mobile_recharge1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tvExeption.setText(null);
                    macPage1.setVisibility(View.GONE);
                    macPage2.setVisibility(View.GONE);
                    macPage3.setVisibility(View.VISIBLE);
                    macPage4.setVisibility(View.GONE);
                    macPage5.setVisibility(View.GONE);
                    macPage6.setVisibility(View.GONE);

                }
            });

            btn_recharge_with_favorite_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FavouriteRecharge favouriteRecharge=new FavouriteRecharge();
                    favouriteRecharge.execute();

                }
            });



            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomerFavoriteListByCustomerIDBackground customerFavoriteListByCustomerIDBackground=new CustomerFavoriteListByCustomerIDBackground();
                    customerFavoriteListByCustomerIDBackground.execute();
                    tvExeption.setText(null);
                }
            });



            btnaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    check_action();

                }
            });

            add_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    macPage1.setVisibility(View.GONE);
                    macPage2.setVisibility(View.GONE);
                    macPage3.setVisibility(View.GONE);
                    macPage4.setVisibility(View.VISIBLE);


                }
            });

            new_mobile_recharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    macPage1.setVisibility(View.GONE);
                    macPage2.setVisibility(View.GONE);
                    macPage3.setVisibility(View.VISIBLE);
                    macPage4.setVisibility(View.GONE);

                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validate_insert();

                }
            });
            btnAddupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_insert();

                }
            });
            btnCancelinsertupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    CustomerFavoriteListByCustomerIDBackground customerFavoriteListByCustomerIDBackground=new CustomerFavoriteListByCustomerIDBackground();
                    customerFavoriteListByCustomerIDBackground.execute();

                    spinnerUpdateFav.setSelection(0);
                    et_FavoriteNameupdate.setText(null);
                    et_FavoriteMobileNumberupdate.setText(null);
                    tvExeption.setText(null);


                }
            });



            btnCalcelnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomerFavoriteListByCustomerIDBackground customerFavoriteListByCustomerIDBackground=new CustomerFavoriteListByCustomerIDBackground();
                    customerFavoriteListByCustomerIDBackground.execute();
                    spinnerRechargeType.setSelection(0);
                    etMob.setText(null);
                    etAmount.setText(null);
                    tvExeption.setText(null);


                }
            });



            btnCancelinsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomerFavoriteListByCustomerIDBackground customerFavoriteListByCustomerIDBackground=new CustomerFavoriteListByCustomerIDBackground();
                    customerFavoriteListByCustomerIDBackground.execute();
                    spinnerAddFav.setSelection(0);
                    et_FavoriteName.setText(null);
                    et_FavoriteMobileNumber.setText(null);
                    tvExeption.setText(null);

                }
            });
            spinnerAddFav();

            spinnerRechargeType();

            etMob = (AutoCompleteTextView)v.findViewById(R.id.autoCustomerNo);
            etMob.setTypeface(Light);
            //Create adapter
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());


            etMob.setThreshold(2);
            et_FavoriteMobileNumberupdate.setThreshold(2);
            et_FavoriteMobileNumber.setThreshold(2);

            sqLiteAdapter= new SQLiteAdapter(getActivity());

            sqLiteAdapter.openToRead();

            contactArray=sqLiteAdapter.getContacts();
            adapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line,contactArray);
            etMob.setAdapter(adapter);
            et_FavoriteMobileNumberupdate.setAdapter(adapter);
            et_FavoriteMobileNumber.setAdapter(adapter);



            etMob.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));



                    etMob.setText(upToNCharacters);
                    etAmount.requestFocus();


                }
            });
            et_FavoriteMobileNumberupdate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);

                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));



                    String upToNCharacters_name = s.substring(16, Math.min(s.length(), 50));
                    String favourite_name=upToNCharacters_name.trim();
                    et_FavoriteNameupdate.setText(null);
                    et_FavoriteMobileNumberupdate.setText(upToNCharacters);
                    et_FavoriteNameupdate.setText(favourite_name);


                }
            });
            et_FavoriteMobileNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = adapter.getItem(i);
                    String upToNCharacters = s.substring(0, Math.min(s.length(), 10));



                    String upToNCharacters_name = s.substring(16, Math.min(s.length(), 50));
                    String favourite_name=upToNCharacters_name.trim();

                    et_FavoriteName.setText(null);
                    et_FavoriteMobileNumber.setText(upToNCharacters);
                    et_FavoriteName.setText(favourite_name);


                }
            });
            sqLiteAdapter.close();
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                    getActivity().startActivityForResult(intent, 4);

                }
            });

            addFavouriteContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                    getActivity().startActivityForResult(intent, 6);
                }
            });

            updateFavouriteContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                    getActivity().startActivityForResult(intent, 5);



                }
            });



            etMob.addTextChangedListener(this);


        }
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(etMob.getText().toString()!=null || etAmount.getText().toString()!=null)
        {
            spinnerRechargeType.setSelection(0);
            spinnerSelectOperator.setSelection(0);
            etMob.setText(null);
            etAmount.setText(null);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode     = event.getRequestCode();
            int resultCode      = event.getResultCode();
            Intent data         = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        super.onActivityResult(requestCode, resultCode, data);

        //  Toast.makeText(getActivity(), "MobilePayments", Toast.LENGTH_LONG).show();

        if (requestCode == PICK_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
            //    Toast.makeText(getActivity(),"PaymentHome",Toast.LENGTH_SHORT).show();

            Uri contactData = data.getData();
            Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {


                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));



                if(hasPhone.equals("0"))
                {
                    Toast.makeText(getActivity(),"The Contact does not Contains Mobile Number",Toast.LENGTH_LONG).show();
                }

                else if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);

                    phones.moveToFirst();

                    String contacts = (phones.getString(phones.getColumnIndex("data1")));
                    String testReplace = null;
                    String pickupReplacedContact = null;



                     if (contacts.length() >= 10) {
                        if ((contacts.startsWith("+91") || (contacts.startsWith("0")))) {


                            if (contacts.startsWith("0")) {
                                String string;
                                string = contacts.substring(1);
                                testReplace = string.replaceAll("\\s", "").replaceAll("\\p{P}", "");
                            } else if (contacts.startsWith("+91")) {
                                testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
                            }


                        } else {
                            testReplace = contacts.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\p{P}", "");
                        }
                    }

                    if (testReplace != null) {


                        if (testReplace.length() > 10) {

                            testReplace = testReplace.substring(testReplace.length() - 10);


                        }

                        etMob.setText(testReplace);


                    }


                }
            }
        } else if(requestCode == PICK_CONTACT_FAV_LIST_UPDATE && resultCode == Activity.RESULT_OK && data != null){

            Uri contactData = data.getData();
            Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {


                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);

                    phones.moveToFirst();

                    String contacts = (phones.getString(phones.getColumnIndex("data1")));
                    String name=(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    String testReplace = null;
                    String pickupReplacedContact = null;

                    if (contacts.length() >= 10) {
                        if ((contacts.startsWith("+91") || (contacts.startsWith("0")))) {


                            if (contacts.startsWith("0")) {
                                String string;

                                string = contacts.substring(1);
                                testReplace = string.replaceAll("\\s", "").replaceAll("\\p{P}", "");
                            } else if (contacts.startsWith("+91")) {
                                testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
                            }


                        } else {
                            testReplace = contacts.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\p{P}", "");
                        }
                    }

                    if (testReplace != null) {


                        if (testReplace.length() > 10) {

                            testReplace = testReplace.substring(testReplace.length() - 10);

                        }





                        et_FavoriteMobileNumberupdate.setText(testReplace);
                        et_FavoriteName.setText(name);


                    }


                }
            }
        }

        else if(requestCode == PICK_CONTACT_FAV_LIST_ADD && resultCode == Activity.RESULT_OK && data != null){

            Uri contactData = data.getData();
            Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {


                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);

                    phones.moveToFirst();

                    String contacts = (phones.getString(phones.getColumnIndex("data1")));
                    String name=(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    String testReplace = null;
                    String pickupReplacedContact = null;

                    if (contacts.length() >= 10) {
                        if ((contacts.startsWith("+91") || (contacts.startsWith("0")))) {


                            if (contacts.startsWith("0")) {
                                String string;

                                string = contacts.substring(1);
                                testReplace = string.replaceAll("\\s", "").replaceAll("\\p{P}", "");
                            } else if (contacts.startsWith("+91")) {
                                testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
                            }


                        } else {
                            testReplace = contacts.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\p{P}", "");
                        }
                    }

                    if (testReplace != null) {


                        if (testReplace.length() > 10) {

                            testReplace = testReplace.substring(testReplace.length() - 10);


                        }




                        et_FavoriteMobileNumber.setText(testReplace);
                        et_FavoriteName.setText(name);

                    }


                }
            }
        }

        else if (resultCode == Activity.RESULT_CANCELED) {

        } else {

        }

    }

    private class CustomerFavoriteListByCustomerIDBackground extends AsyncTask<String,Integer,String>
    {
        int id;
        String  CustomerFavoriteListByCustomerID_response;
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        ResponseBase<CustomerFavoriteListByCustomerIDResponse[]> responseBase;

        Map<String, List<String>> map1;
        List<String> GiftVocherList;
        Map<String,Integer> mapGift;
        List<String> branchNameResultList;
        Mobilepayment_ServiceProviderID_testing gift_modelist=new Mobilepayment_ServiceProviderID_testing();


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


           // progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();


        }
        @Override
        protected String doInBackground(String... params)
        {
            try {


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(String_url.CustomerFavoriteListByCustomerID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.Customer_FavouriteList, String.valueOf(CustomerID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                CustomerFavoriteListByCustomerID_response = EntityUtils.toString(entity);
                } catch (Exception e) {
                e.printStackTrace();
            }
            return CustomerFavoriteListByCustomerID_response;
        }



        @Override
        protected void onPostExecute(String s)
        {
            try {

                if(s!=null)
                {

                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<CustomerFavoriteListByCustomerIDResponse[]>>(){}.getType();
                    responseBase = gson.fromJson(s,type);
                    map1 = new HashMap<String, List<String>>();
                    mapGift = new HashMap<String, Integer>();
                    GiftVocherList = new ArrayList<String>();
                    GiftVocherList.add(getString(R.string.select_favorite));

                    if(responseBase.id == 1){
                        // ArrayList<CustomerFavoriteListByCustomerIDResponse> list = new ArrayList<CustomerFavoriteListByCustomerIDResponse>();
                        for(CustomerFavoriteListByCustomerIDResponse customerFavoriteListByCustomerIDResponse : responseBase.responseData) {
                            String FavoriteNameList = customerFavoriteListByCustomerIDResponse.favoriteName;
                            String FavoriteMobileNumberList=customerFavoriteListByCustomerIDResponse.favoriteMobileNumber;
                            int customerFavoriteIdList=customerFavoriteListByCustomerIDResponse.customerFavoriteId;
                            int RelationshipIDList=customerFavoriteListByCustomerIDResponse.relationshipId;

                            List<String> giftset = new ArrayList<String>();
                            giftset.add(FavoriteNameList);
                            giftset.add(FavoriteMobileNumberList);
                            giftset.add(String.valueOf(customerFavoriteIdList));
                            giftset.add(String.valueOf(RelationshipIDList));

                            map1.put(FavoriteNameList, giftset);
                            GiftVocherList.add(FavoriteNameList);

                            gift_modelist.storeBranchList(String_url.payment_mode, map1);
                            gift_modelist.storeBranchListDetail(String_url.payment_id, GiftVocherList);
                            mapGift.put(FavoriteNameList, customerFavoriteIdList);
                            branchNameResultList = gift_modelist.getBranchListDetail();



                            macPage1.setVisibility(View.GONE);
                            macPage2.setVisibility(View.GONE);
                            macPage3.setVisibility(View.GONE);
                            macPage4.setVisibility(View.GONE);
                            macPage5.setVisibility(View.GONE);
                            macPage6.setVisibility(View.VISIBLE);
                            macPage7.setVisibility(View.GONE);
                          //  progressBar.setVisibility(View.GONE);
                            comProgressDialog.cancelProgress();
                        }

                    }
                    else {
                        macPage2.setVisibility(View.GONE);
                        macPage1.setVisibility(View.VISIBLE);
                        macPage3.setVisibility(View.GONE);
                        macPage4.setVisibility(View.GONE);
                        macPage5.setVisibility(View.GONE);
                       // progressBar.setVisibility(View.GONE);
                         comProgressDialog.cancelProgress();

                    }



                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }


    private class FavouriteRecharge extends AsyncTask<String,Integer,String>
    {
        Map<String, List<String>> map1;
        List<String> GiftVocherList;
        Map<String,Integer> mapGift;
        List<String> branchNameResultList;
        Mobilepayment_ServiceProviderID gift_mode=new Mobilepayment_ServiceProviderID();
        ResponseBase<CustomerFavoriteListByCustomerIDResponse[]> responseBase;
        String CustomerFavoriteListByCustomerID_response;
        @Override
        protected void onPreExecute()
        {
            map1 = new HashMap<String, List<String>>();
            mapGift = new HashMap<String, Integer>();
            GiftVocherList = new ArrayList<String>();
            GiftVocherList.add(getString(R.string.Select_Favourite1));
        }
        @Override
        protected String doInBackground(String... params)
        {

            try {

                HttpClient httpclient = new DefaultHttpClient();


                HttpPost httppost = new HttpPost(String_url.CustomerFavoriteListByCustomerID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerID, String.valueOf(CustomerID)));
                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);


                HttpEntity entity = response.getEntity();
                CustomerFavoriteListByCustomerID_response = EntityUtils.toString(entity);





            } catch (Exception e) {
                e.printStackTrace();
            }
            return CustomerFavoriteListByCustomerID_response;
        }
        @Override
        protected void onPostExecute(String s) {
            try {

                if(s!=null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<CustomerFavoriteListByCustomerIDResponse[]>>() {
                    }.getType();
                    responseBase = gson.fromJson(CustomerFavoriteListByCustomerID_response, type);


                    if (responseBase.id == 1) {

                        for (CustomerFavoriteListByCustomerIDResponse customerFavoriteListByCustomerIDResponse : responseBase.responseData) {
                            FavoriteName = customerFavoriteListByCustomerIDResponse.favoriteName;
                            FavoriteMobileNumber = customerFavoriteListByCustomerIDResponse.favoriteMobileNumber;
                            CustomerFavoriteID = customerFavoriteListByCustomerIDResponse.customerFavoriteId;
                            RelationshipID = customerFavoriteListByCustomerIDResponse.relationshipId;

                            List<String> giftset = new ArrayList<String>();
                            giftset.add(FavoriteName);
                            giftset.add(FavoriteMobileNumber);
                            giftset.add(String.valueOf(CustomerFavoriteID));
                            giftset.add(String.valueOf(RelationshipID));

                            map1.put(FavoriteName, giftset);
                            GiftVocherList.add(FavoriteName);

                            gift_mode.storeBranchList(String_url.payment_mode, map1);
                            gift_mode.storeBranchListDetail(String_url.payment_id, GiftVocherList);
                            mapGift.put(FavoriteName, CustomerFavoriteID);
                            branchNameResultList = gift_mode.getBranchListDetail();
                        }

                    }

                    macPage1.setVisibility(View.GONE);
                    macPage2.setVisibility(View.VISIBLE);
                    macPage3.setVisibility(View.GONE);
                    macPage4.setVisibility(View.GONE);
                    macPage5.setVisibility(View.GONE);
                    macPage6.setVisibility(View.GONE);
                    ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNameResultList) {
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
                    spinnervalue.setAdapter(dataAdapter_res);
                    spinnervalue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            try {
                                str_FavoriteName = gift_mode.getFavoriteName(spinnervalue.getSelectedItem().toString());
                                str_FavoriteMobileNumber = gift_mode.getFavoriteMobileNumber(spinnervalue.getSelectedItem().toString());
                                str_CustomerFavoriteID = gift_mode.getCustomerFavoriteID(spinnervalue.getSelectedItem().toString());
                                str_RelationshipID = gift_mode.getRelationshipID(spinnervalue.getSelectedItem().toString());

                            } catch (NullPointerException e) {
                                System.out.println("Exception" + e);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    spinneraction_function();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public <ViewGroup> void spinnerAddFav()
    {

        spinnerAddFavBackground spinnerAddFavBackground=new spinnerAddFavBackground();
        spinnerAddFavBackground.execute();

    }

    private class spinnerAddFavBackground extends AsyncTask<String,Integer,String>
    {
        Payment_Request_payment_mode  payment_request_payment_mode=new Payment_Request_payment_mode();
        String CustomerRelationShip_response;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.CustomerRelationShip);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey,String_url.auth_key1));
                final HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();
                CustomerRelationShip_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return CustomerRelationShip_response;
        }
        @Override
        protected void onPostExecute(String s)
        {
            try {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<CustomerRelationShipResponse[]>>(){}.getType();
                    ResponseBase<CustomerRelationShipResponse[]> responseBase=gson.fromJson(CustomerRelationShip_response,type);
                    if(responseBase.responseData!=null)
                    {
                        hashMapBranch = new HashMap<String, Integer>();
                        branchList  = new ArrayList<String>();
                        branchList.add(getString(R.string.select_relationshipid));
                        for(CustomerRelationShipResponse customerRelationShipResponse:responseBase.responseData)
                        {
                            int RelationshipID=customerRelationShipResponse.RelationshipID;
                            String RelationName=customerRelationShipResponse.RelationName;

                            hashMapBranch.put(RelationName, RelationshipID) ;
                            branchList.add(RelationName);
                        }
                        payment_request_payment_mode.storeBranchList(String_url.payment_mode, hashMapBranch);
                        payment_request_payment_mode.storeBranchListDetail(String_url.payment_id, branchList);
                        List<String> branchNameResultList =  payment_request_payment_mode.getBranchListDetail();

                        ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNameResultList)
                        {
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
                                v.setTextSize(18); v.setGravity(Gravity.CENTER);
                                v.setPadding(15,15,15,15);
                                return v;
                            }
                        };
                        dataAdapter_res.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spinnerAddFav.setAdapter(dataAdapter_res);
                        spinnerAddFav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                try {
                                    favorite_RelationshipID = payment_request_payment_mode.getBranchList(spinnerAddFav.getSelectedItem().toString());

                                } catch (NullPointerException e) {
                                    System.out.println("Exception" + e);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                        spinnerUpdateFav.setAdapter(dataAdapter_res);
                        spinnerUpdateFav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                try {
                                    favorite_RelationshipID = payment_request_payment_mode.getBranchList(spinnerUpdateFav.getSelectedItem().toString());

                                } catch (NullPointerException e) {
                                    System.out.println("Exception" + e);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        int positionupdateFavContact=settings.getInt(String_url.updateFavContact, 0);
                        int positionaddFavContact = settings.getInt(String_url.addFavContact, 0);


                        if(positionupdateFavContact!=0)
                        {
                            spinnerUpdateFav.setSelection(positionupdateFavContact);

                            editor.remove(String_url.updateFavContact);
                            editor.commit();
                        }


                        if(positionaddFavContact!=0)
                        {

                            spinnerAddFav.setSelection(positionaddFavContact);
                            editor.remove(String_url.addFavContact);
                            editor.commit();

                        }

                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }





        }
    }

    public <ViewGroup> void spinnerupdate()
    {
        spinnerAddFavBackground spinnerAddFavBackground=new spinnerAddFavBackground();
        spinnerAddFavBackground.execute();

    }
    public <ViewGroup> void spinnerRechargeType(){




        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Type");
        categories.add("Prepaid");
        categories.add("Postpaid");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories)
        {
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
                v.setTextSize(18); v.setGravity(Gravity.CENTER);
                v.setPadding(15,15,15,15);
                return v;
            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerRechargeType.setAdapter(dataAdapter);
        spinnerRechargeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                if(position==2)
                {

                    browseplane.setVisibility(View.GONE);

                    btnPay.setText(getString(R.string.Pay));



                    PostpaidOperator postpaidOperator= new PostpaidOperator();
                    postpaidOperator.execute();
                }
                else if(position==1){

                    browseplane.setVisibility(View.VISIBLE);

                    btnPay.setText(getString(R.string.recharge));


                    PrepaidOperator prepaidOperator= new PrepaidOperator();
                    prepaidOperator.execute();
                    afterTextChanged(etMob.getText());


                }
                else
                {

                    browseplane.setVisibility(View.GONE);

                    btnPay.setText(getString(R.string.recharge));
                    List<String> list = new ArrayList<String>();
                    list.add("Select Operator");


                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, list)
                    {

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
                            v.setTextSize(18); v.setGravity(Gravity.CENTER);
                            v.setPadding(15,15,15,15);
                            return v;
                        }
                    };
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    spinnerSelectOperator.setAdapter(dataAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });
        spinnerSelectOperator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_spinner=spinnerSelectOperator.getSelectedItem().toString();

                try
                {
                    if(str_spinner.equals("TATA WALKY"))
                    {

                        etMob.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
                    }
                    else {

                        etMob.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                    }
                }
                catch(NullPointerException e)
                {
                    System.out.println("NullPointerException"+e);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });

    }

    public <ViewGroup> void spinneraction_function()
    {
        List<String> categories = new ArrayList<String>();
        categories.add(getString(R.string.Select_Option));
        categories.add(getString(R.string.Recharge_with_Favourite_List));
        categories.add(getString(R.string.Add_New_Favourite_List));
        categories.add(getString(R.string.Update_Favourite_List));
        categories.add(getString(R.string.Delete_Favourite_List));

        // categories.add("New Recharge");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories)
        {
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
                v.setTextSize(18); v.setGravity(Gravity.CENTER);
                v.setPadding(15,15,15,15);
                return v;
            }
        };


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinneraction.setAdapter(dataAdapter);

    }

    public boolean validate_insert()
    {
        String oneLineMessage;
        int min_mobile_number=10;
        int max_mobile_number=10;
        string_FavoriteMobileNumber_insert=et_FavoriteMobileNumber.getText().toString();
        str_FavoriteName_insert=et_FavoriteName.getText().toString();
        insertspinner = spinnerAddFav.getSelectedItem().toString();
        int RelationShipID=0;
        if(insertspinner.equals(getString(R.string.select_relationshipid)))
        {
            getResponseDialog(getString(R.string.select_relationship));

            return false;
        }

        else  if(string_FavoriteMobileNumber_insert.length()<min_mobile_number||string_FavoriteMobileNumber_insert.length()>max_mobile_number)
        {
            getResponseDialog(getString(R.string.FavoriteMobileNumber));

            return false;
        }
        else  if(str_FavoriteName_insert.equals(""))
        {
            getResponseDialog(getString(R.string.FavoriteName));

            return false;
        }
        else
        {
            tvExeption.setText(null);

            try
            {

                InsertCustomerFavoriteListBackground insertCustomerFavoriteListBackground=new InsertCustomerFavoriteListBackground();
                String message=insertCustomerFavoriteListBackground.doInBackground().toLowerCase();
                if(message.equals(getString(R.string.favorite_success)))
                {


                    oneLineMessage=getString(R.string.favorite_insert);
                    final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
                    responseDialog1.showResponseDialog(1,
                            getString(R.string.Response),
                            null, null, null, null,
                            oneLineMessage,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    responseDialog1.dialog.dismiss();

                                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                                    // Writing data to SharedPreferences
                                    SharedPreferences.Editor editor = settings.edit();

                                    editor.putString("key", "one");
                                    editor.apply();
                                    Intent i = new Intent(getActivity(), Tabhost_activity.class);
                                    i.putExtra("position", 1);

                                    startActivity(i);
                                    getActivity().overridePendingTransition(0, 0);
                                    getActivity().finish();
                                }
                            }


                    );

                }
                else
                {

                    oneLineMessage=message;

                    final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
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


                }






            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


        }
        return true;
    }

    private class InsertCustomerFavoriteListBackground extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.InsertCustomerFavoriteList);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerID, String.valueOf(CustomerID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.FavoriteMobileNumber, string_FavoriteMobileNumber_insert));
                nameValuePairs.add(new BasicNameValuePair(String_url.FavoriteName, str_FavoriteName_insert));
                nameValuePairs.add(new BasicNameValuePair(String_url.RelationShipID, String.valueOf(favorite_RelationshipID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerFavoriteStatusParameter,String.valueOf(String_url.CustomerFavoriteStatus) ));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                String InsertCustomerFavoriteList_response= EntityUtils.toString(entity);

                Gson gson = new GsonBuilder().create();


                Type type = new TypeToken<ResponseBase>(){}.getType();
                ResponseBase responseBase = gson.fromJson(InsertCustomerFavoriteList_response,type);
                message_insert=responseBase.message;
                return message_insert;



            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return message_insert;
        }

    }

    public boolean update_insert()
    {
        String oneLineMessage;
        int min_mobile_number=10;
        int max_mobile_number=10;
        string_FavoriteMobileNumber_update=et_FavoriteMobileNumberupdate.getText().toString();
        str_FavoriteName_update=et_FavoriteNameupdate.getText().toString();
        insertspinner = spinnerUpdateFav.getSelectedItem().toString();
        int RelationShipID=0;
        if(insertspinner.equals(getString(R.string.select_relationshipid)))
        {
            getResponseDialog(getString(R.string.select_relationship));

            return false;
        }

        else  if(string_FavoriteMobileNumber_update.length()<min_mobile_number||string_FavoriteMobileNumber_update.length()>max_mobile_number)
        {
            getResponseDialog(getString(R.string.FavoriteMobileNumber));

            return false;
        }
        else  if(str_FavoriteName_update.equals(""))
        {
            getResponseDialog(getString(R.string.FavoriteName));

            return false;
        }
        else
        {
            tvExeption.setText(null);

            try
            {



                update_insertBackground update_insertBackground=new update_insertBackground();
                String message=update_insertBackground.doInBackground().toLowerCase();


                if(message.equals(getString(R.string.favorite_success)))
                {


                    oneLineMessage=getString(R.string.update_favorite);
                    final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
                    responseDialog1.showResponseDialog(1,
                            getString(R.string.Response),
                            null, null, null, null,
                            oneLineMessage,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    responseDialog1.dialog.dismiss();

                                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                                    // Writing data to SharedPreferences
                                    SharedPreferences.Editor editor = settings.edit();

                                    editor.putString("key", "one");
                                    editor.commit();
                                    Intent i = new Intent(getActivity(), Tabhost_activity.class);
                                    i.putExtra("position", 1);

                                    startActivity(i);
                                    getActivity().overridePendingTransition(0, 0);
                                    getActivity().finish();
                                }
                            }


                    );

                }
                else
                {

                    oneLineMessage=message;

                    final ResponseDialog responseDialog1= new ResponseDialog(getActivity(),getActivity());
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
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


        }
        return true;
    }

    private class update_insertBackground extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            try
            {


                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.UpdateCustomerFavoriteList);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerID, String.valueOf(CustomerID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerFavoriteID,String.valueOf(str_CustomerFavoriteID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.FavoriteMobileNumber, string_FavoriteMobileNumber_update));
                nameValuePairs.add(new BasicNameValuePair(String_url.FavoriteName, str_FavoriteName_update));
                nameValuePairs.add(new BasicNameValuePair(String_url.RelationShipID, String.valueOf(favorite_RelationshipID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerFavoriteStatusParameter,String.valueOf(String_url.CustomerFavoriteStatus)));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                String update_response= EntityUtils.toString(entity);



                Gson gson = new GsonBuilder().create();


                Type type = new TypeToken<ResponseBase>(){}.getType();
                ResponseBase responseBase = gson.fromJson(update_response,type);
                message_insert=responseBase.message;
                return message_insert;



            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return message_insert;
        }

    }

    private class PostpaidOperator extends AsyncTask<String,Integer,String>{
        Mobilepayment_ServiceProviderID mobile_payment_mode=new Mobilepayment_ServiceProviderID();
        String responsePostpaidOpr;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapPostpaid= new HashMap<String, Integer>();
        }

        @Override
        protected String doInBackground(String... params) {
            try
            {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.postpaid_ServiceProviderTypeID)));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                responsePostpaidOpr= EntityUtils.toString(entity);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return responsePostpaidOpr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                if(s!=null)
                {
                    Gson gson   = new GsonBuilder().create();
                    Type type   = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>(){}.getType();
                    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase=gson.fromJson(s, type);
                    Map<String, List<String>> map1 = new HashMap<String, List<String>>();
                    branchList1  = new ArrayList<String>();
                    serviceProviderImagelist= new ArrayList<String>();

                    branchList1.add(getString(R.string.spinner_title));
                    if(responseBase.id==1)
                    {
                        for(GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse:responseBase.responseData)
                        {
                            serviceProviderName= getProviderNameByProviderTypeIDResponse.serviceProviderName;
                            int ServiceProviderID = getProviderNameByProviderTypeIDResponse.serviceProviderId;
                            boolean serviceProviderStatus = getProviderNameByProviderTypeIDResponse.serviceProviderStatus;
                            String serviceProviderImage=getProviderNameByProviderTypeIDResponse.serviceProviderImage;
                            if (serviceProviderStatus) {

                                List<String> valSetOne = new ArrayList<String>();
                                valSetOne.add(serviceProviderName);
                                valSetOne.add(String.valueOf(ServiceProviderID));
                                map1.put(serviceProviderName, valSetOne);
                                branchList1.add(serviceProviderName);
                                serviceProviderImagelist.add(serviceProviderImage);

                                mobile_payment_mode.storeBranchList("payment_mode", map1);
                                mobile_payment_mode.storeBranchListDetail("payment_id", branchList1);

                                mapPostpaid.put(serviceProviderName, ServiceProviderID);

                            }


                        }
                    }



                    List<String> branchNameResultList =  mobile_payment_mode.getBranchListDetail();
                    ArrayAdapter<String> dataAdapter_res = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNameResultList)
                    {
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
                            v.setTextSize(18); v.setGravity(Gravity.CENTER);
                            v.setPadding(15,15,15,15);
                            return v;
                        }
                    };
                    dataAdapter_res.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    spinnerSelectOperator.setAdapter(dataAdapter_res);
                    spinnerSelectOperator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            try {
                                str_ServiceProviderName = mobile_payment_mode.getFavoriteName(spinnerSelectOperator.getSelectedItem().toString());
                                str_ServiceProviderID = mobile_payment_mode.getFavoriteMobileNumber(spinnerSelectOperator.getSelectedItem().toString());


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
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }
    }

    private class PrepaidOperator extends AsyncTask<String,Integer,String>{

        Mobilepayment_ServiceProviderID mobile_payment_mode=new Mobilepayment_ServiceProviderID();
        String responsePrepaidOpr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapPrepaid= new HashMap<String, Integer>();
        }


        @Override
        protected String doInBackground(String... params) {

            try
            {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.prepaid_ServiceProviderTypeID)));



                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                responsePrepaidOpr= EntityUtils.toString(entity);





            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return responsePrepaidOpr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{

                if(s!=null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>() {
                    }.getType();
                    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase = gson.fromJson(s, type);
                    Map<String, List<String>> map2 = new HashMap<String, List<String>>();
                    branchList2 = new ArrayList<String>();
                    branchList2.add(getString(R.string.spinner_title));
                    if (responseBase.id == 1) {
                        for (GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse : responseBase.responseData) {
                            serviceProviderName = getProviderNameByProviderTypeIDResponse.serviceProviderName;
                            int ServiceProviderID = getProviderNameByProviderTypeIDResponse.serviceProviderId;
                            boolean serviceProviderStatus = getProviderNameByProviderTypeIDResponse.serviceProviderStatus;
                            if (serviceProviderStatus) {

                                List<String> valSetOne = new ArrayList<String>();
                                valSetOne.add(serviceProviderName);
                                valSetOne.add(String.valueOf(ServiceProviderID));
                                map2.put(serviceProviderName, valSetOne);
                                branchList2.add(serviceProviderName);

                                mobile_payment_mode.storeBranchList("payment_mode", map2);
                                mobile_payment_mode.storeBranchListDetail("payment_id", branchList2);

                                mapPrepaid.put(serviceProviderName, ServiceProviderID);

                            }

                        }
                    }

                    List<String> branchNameResultList = mobile_payment_mode.getBranchListDetail();
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
                    spinnerSelectOperator.setAdapter(dataAdapter_res);
                    spinnerSelectOperator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            try {
                                str_ServiceProviderName = mobile_payment_mode.getFavoriteName(spinnerSelectOperator.getSelectedItem().toString());
                                str_ServiceProviderID = mobile_payment_mode.getFavoriteMobileNumber(spinnerSelectOperator.getSelectedItem().toString());




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
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    public boolean validate()
    {
        int WalletCustomerBalance;

        sqLiteAdapter = new SQLiteAdapter(getActivity());
        sqLiteAdapter.openToReadBalanceDataBase();
        double dBalance =sqLiteAdapter.getBalanceFromDB();
        sqLiteAdapter.close();


        WalletCustomerBalance = (int) dBalance;

        int min_mobile_number=10;
        int max_mobile_number=10;
        int min_recharge_amount=10;
        int max_recharge_amount=10000;

        string_mobile_number=etMob.getText().toString();
        str_amount=etAmount.getText().toString();

        int recharge_amount=0;
        try {

            recharge_amount = Integer.parseInt(etAmount.getText().toString());
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        spinnerMobPay = spinnerRechargeType.getSelectedItem().toString();
        spinnerSelectOpr=spinnerSelectOperator.getSelectedItem().toString();


        if(spinnerMobPay.equals(String_url.Select_Type))
        {
            getResponseDialog(getString(R.string.mobile_recharge_select_type));

            return false;
        }
        if(spinnerSelectOpr.equals(String_url.TATA_WALKY)) {



            min_mobile_number = 1;
            max_mobile_number = 12;
        }

        if(string_mobile_number.length()<min_mobile_number||string_mobile_number.length()>max_mobile_number)
        {

            if(spinnerSelectOpr.equals(String_url.TATA_WALKY)) {
                getResponseDialog(getString(R.string.Mobile_number_should_be_between)+min_mobile_number+getString(R.string.and)+max_mobile_number);

                return false;
            }
            else
            {
                getResponseDialog(getString(R.string.MobNo_Should_be)+min_mobile_number+getString(R.string.digits));

                return false;
            }
        }

        if(spinnerSelectOperator.getSelectedItemPosition()==0)
        {
            getResponseDialog(getString(R.string.select_operator));

            return false;
        }
        if(recharge_amount<min_recharge_amount||recharge_amount>max_recharge_amount)
        {
            getResponseDialog(getString(R.string.mobile_recharge_amount));


            return false;
        }
        if (recharge_amount>WalletCustomerBalance ) {
            getResponseDialog(getString(R.string.WalletBalance));


            return false;
        }


        else
        {
            tvExeption.setText(null);

            et_tpin.setText(null);
            macPage1.setVisibility(View.GONE);
            macPage2.setVisibility(View.GONE);
            macPage3.setVisibility(View.GONE);
            macPage4.setVisibility(View.GONE);
            macPage5.setVisibility(View.GONE);
            macPage6.setVisibility(View.GONE);
            macPage7.setVisibility(View.VISIBLE);

            return true;
        }

    }
    public boolean validate_tpin()
    {


        int min_tpin_length=8;


        string_tpin=et_tpin.getText().toString();

        if(string_tpin.length()<min_tpin_length||string_tpin.length()>min_tpin_length)
        {
            getResponseDialog(getString(R.string.error_tpin_length));


            return false;
        }
        else
        {


            GetValidPasswordPinClass getValidPasswordPinClass=new GetValidPasswordPinClass(getActivity(), getActivity(),string_tpin);
            String message=getValidPasswordPinClass.doInBackground().toLowerCase();


            if(message.equals(String_url.success)) {


                tvExeption.setText(null);
                String operator = spinnerSelectOperator.getSelectedItem().toString();
                final ConfrimRechargeDialog confrimRechargeDialog1 = new ConfrimRechargeDialog(getActivity(), getActivity());
                confrimRechargeDialog1.showDialog(3,
                        getString(R.string.ConfirmRecharge),
                        getString(R.string.mobile_number),
                        string_mobile_number,
                        getString(R.string.Operator),
                        operator,
                        getString(R.string.amount),
                        str_amount,
                        new View.OnClickListener()

                        {
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
                                confrimRechargeDialog1.dialog.dismiss();
                                btntpinsubmit.setEnabled(true);

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
    public boolean validate_mobilenumber()
    {
        int min_mobile_number=10;
        int max_mobile_number=10;


        String string_mobile_number=etMob.getText().toString();

        spinnerMobPay = spinnerRechargeType.getSelectedItem().toString();
        spinnerSelectOpr= spinnerSelectOperator.getSelectedItem().toString();



        if(spinnerMobPay.equals("Select Type"))
        {
            getResponseDialog(getString(R.string.mobile_recharge_select_type));

            return false;
        }
        if(spinnerSelectOpr.equals("TATA WALKY")) {
            min_mobile_number = 1;
            max_mobile_number = 12;
        }

        if(string_mobile_number.length()<min_mobile_number||string_mobile_number.length()>max_mobile_number)
        {

            if(spinnerSelectOpr.equals("TATA WALKY")) {
                getResponseDialog("Mobile number should be between"+min_mobile_number+"and"+max_mobile_number);

                return false;
            }
            else
            {
                getResponseDialog("Mobile number should be"+min_mobile_number+"digits");

                return false;
            }
        }
        else
        {
            return true;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(s.length()<10)
        {
            spinnerSelectOperator.setSelection(0);
        }

        if(s.length()==10) {
            if (spinnerRechargeType.getSelectedItemPosition() == 0) {


            } else {


                ServiceCircleID serviceCircleID= new ServiceCircleID();
                serviceCircleID.execute();
            }
        }

    }

    private class ServiceCircleID extends AsyncTask<String,Integer,String>{

        String message,Operatorname,service_circle_response;
        String mobileNumber=etMob.getText().toString();



        @Override
        protected String doInBackground(String... params) {
            try {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetServiceProviderAndCircleByMobileSeries);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.mobileNumber,mobileNumber ));



                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                service_circle_response = EntityUtils.toString(entity);





            } catch (Exception e) {
                e.printStackTrace();
            }

            return service_circle_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetServiceProviderAndCircleByMobileSeriesResponse>>(){}.getType();
                    ResponseBase<GetServiceProviderAndCircleByMobileSeriesResponse> responseResponseBase=gson.fromJson(s,type);
                    GetServiceProviderAndCircleByMobileSeriesResponse getServiceProviderAndCircleByMobileSeriesResponse=responseResponseBase.responseData;


                    if (responseResponseBase.id == 1) {

                        ServiceProviderID = getServiceProviderAndCircleByMobileSeriesResponse.serviceProviderId;
                        int ServiceProviderTypeID = getServiceProviderAndCircleByMobileSeriesResponse.serviceProviderTypeId;
                        Operatorname = getServiceProviderAndCircleByMobileSeriesResponse.serviceProviderName;
                        ServiceCircleID = getServiceProviderAndCircleByMobileSeriesResponse.serviceCircleId;
                        String ServiceCircleName = getServiceProviderAndCircleByMobileSeriesResponse.serviceCircleName;

                        if (spinnerRechargeType.getSelectedItemPosition() == 1)
                        {
                            int countOperator =  spinnerSelectOperator.getCount();

                            for (int i = 0; i < countOperator; i++) {
                                String operator =  spinnerSelectOperator.getItemAtPosition(i).toString();

                                if (operator.equals(Operatorname)) {
                                    spinnerSelectOperator.setSelection(i);
                                }
                            }
                        }
                    }
                    else
                    {

                        ServiceCircleID=1;
                    }

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean delete_favourite_list() {
        str_favourite_list_delete = spinnervalue.getSelectedItem().toString();
        if(str_favourite_list_delete.equals(getString(R.string.select_relationshipid)))
        {
            getResponseDialog(getString(R.string.Select_Favourite));
            return false;
        }
        else
        {
            new android.app.AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getString(R.string.delete))
                    .setMessage(getString(R.string.SureDelete))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Favourite_list favourite_list=new Favourite_list();

                            try
                            {
                                DeleteCustomerFavoriteListbackground deleteCustomerFavoriteListbackground=new DeleteCustomerFavoriteListbackground();
                                String message=deleteCustomerFavoriteListbackground.doInBackground().toLowerCase();

                                if(message.equals(String_url.success))
                                {
                                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
                                    alert.setTitle( getString(R.string.Response));
                                    alert.setMessage(getString(R.string.DeletedSuccessfully));
                                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                                    alert.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                                            // Writing data to SharedPreferences
                                            SharedPreferences.Editor editor = settings.edit();

                                            editor.putString("key", "one");
                                            editor.commit();
                                            Intent i = new Intent(getActivity(), Tabhost_activity.class);
                                            i.putExtra("position", 1);

                                            startActivity(i);
                                            getActivity().overridePendingTransition(0, 0);
                                            getActivity().finish();
                                        }
                                    });
                                    android.app.AlertDialog dialog1 = alert.create();
                                    dialog1.show();
                                }
                                else
                                {
                                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
                                    alert.setTitle( getString(R.string.Response));
                                    alert.setMessage(getString(R.string.DeletedFailed));
                                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                                    alert.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    android.app.AlertDialog dialog1 = alert.create();
                                    dialog1.show();
                                }
                            }

                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }


                        }
                    })
                    .setNegativeButton(getString(R.string.no), null)
                    .show();

            return true;
        }

    }

    private class DeleteCustomerFavoriteListbackground extends AsyncTask<String,Integer,String> {
        String message;

        @Override
        protected String doInBackground(String... params)
        {

            try
            {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.DeleteCustomerFavoriteList);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerFavoriteID, str_CustomerFavoriteID));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                String delete_response= EntityUtils.toString(entity);


                Gson gson = new GsonBuilder().create();


                Type type = new TypeToken<ResponseBase>(){}.getType();
                ResponseBase responseBase = gson.fromJson(delete_response,type);
                message=responseBase.message;

                return message;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return message;
        }

    }

    public boolean update(){
        str_favourite_list_delete = spinnervalue.getSelectedItem().toString();

        if(str_favourite_list_delete.equals("Select Favourite"))
        {
            getResponseDialog(getString(R.string.Select_Favourite));

            return false;
        }
        else
        {
            tvExeption.setText(null);

            macPage1.setVisibility(View.GONE);
            macPage2.setVisibility(View.GONE);
            macPage3.setVisibility(View.GONE);
            macPage4.setVisibility(View.GONE);
            macPage5.setVisibility(View.VISIBLE);
            spinnerupdate();

            et_FavoriteMobileNumberupdate.setText(str_FavoriteMobileNumber);
            et_FavoriteNameupdate.setText(str_FavoriteName);



            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("str_FavoriteName",str_FavoriteName);
            editor.commit();
            return true;
        }

    }

    public boolean check_action()
    {

        String str_spinnervalue = spinnervalue.getSelectedItem().toString();
        String str_spinneraction=spinneraction.getSelectedItem().toString();
        if(str_spinneraction.equals("Add New Favourite List"))
        {
            tvExeption.setText(null);
            macPage1.setVisibility(View.GONE);
            macPage2.setVisibility(View.GONE);
            macPage3.setVisibility(View.GONE);
            macPage4.setVisibility(View.VISIBLE);

        }
        else
        {
            if(str_spinnervalue.equals("Select Favourite"))
            {

                getResponseDialog(getString(R.string.Select_Favourite));

                return false;
            }

            if(str_spinneraction.equals("Select Option"))
            {

                getResponseDialog(getString(R.string.Select_Option));

                return false;
            }
            else if(str_spinneraction.equals("Recharge with Favourite List"))
            {
                tvExeption.setText(null);
                macPage1.setVisibility(View.GONE);
                macPage2.setVisibility(View.GONE);
                macPage3.setVisibility(View.VISIBLE);
                macPage4.setVisibility(View.GONE);
                etMob.setText(str_FavoriteMobileNumber);

            }
            else if(str_spinneraction.equals("Update Favourite List"))
            {

                tvExeption.setText(null);
                update();
            }
            else if(str_spinneraction.equals("Delete Favourite List"))
            {
                tvExeption.setText(null);
                delete_favourite_list();
            }
        }


        return true;
    }

    public void browseplanefunction()
    {
        string_mobile_numberplan=etMob.getText().toString();
        String mobile_number=spinnerSelectOperator.getSelectedItem().toString();
        if(string_mobile_numberplan.equals(""))
        {
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.browse_plan_layout, null);
            spinnerServiceProviderName = (Spinner)alertLayout.findViewById(R.id.spinnerServiceProviderName);
            spinnerServiceCircleName = (Spinner)alertLayout.findViewById(R.id.spinnerServiceCircleName);
            spinnerplan = (Spinner)alertLayout.findViewById(R.id.spinnerplan);
            browseplanelistview=(ListView)alertLayout.findViewById(R.id.browseplanelistview);
            progressBar_browseplan=(ProgressBar)alertLayout.findViewById(R.id.progressBar_browseplan);
            amountid=(LinearLayout)alertLayout.findViewById(R.id.amountid);



            branchList3.clear();
            branchList4.clear();
            branchList5.clear();


            branchList3=new ArrayList<String>();
            branchList3.add(getString(R.string.spinner_title));

            branchList4=new ArrayList<String>();
            branchList4.add(getString(R.string.spinner_title));


            branchList5=new ArrayList<String>();
            branchList5.add(getString(R.string.spinner_plan));




            GetServiceProviderNameBackground getIFSCBanks=new GetServiceProviderNameBackground();
            getIFSCBanks.execute();

            alert1 = new AlertDialog.Builder(getActivity());
            alert1.setTitle("BROWSE PLANS");
            alert1.setView(alertLayout);
            alert1.setCancelable(true);
            alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    etAmount.setText(null);
                }
            });


            dialog1 = alert1.create();
            dialog1.show();
        }
        else
        {
            boolean a=validate_mobilenumber();

            if(a)
            {
                LayoutInflater inflater=getActivity().getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.browse_plan_layout, null);
                spinnerServiceProviderName = (Spinner)alertLayout.findViewById(R.id.spinnerServiceProviderName);
                spinnerServiceCircleName = (Spinner)alertLayout.findViewById(R.id.spinnerServiceCircleName);
                spinnerplan = (Spinner)alertLayout.findViewById(R.id.spinnerplan);
                browseplanelistview=(ListView)alertLayout.findViewById(R.id.browseplanelistview);
                progressBar_browseplan=(ProgressBar)alertLayout.findViewById(R.id.progressBar_browseplan);
                amountid=(LinearLayout)alertLayout.findViewById(R.id.amountid);


                branchList3.clear();
                branchList4.clear();
                branchList5.clear();


                branchList3=new ArrayList<String>();
                branchList3.add(getString(R.string.spinner_title));

                branchList4=new ArrayList<String>();
                branchList4.add(getString(R.string.spinner_title));


                branchList5=new ArrayList<String>();
                branchList5.add(getString(R.string.spinner_plan));

                final GetServiceProviderNameBackground_browseplan getServiceProviderNameBackground_browseplan=new GetServiceProviderNameBackground_browseplan();
                getServiceProviderNameBackground_browseplan.execute();



                GetServiceProviderAndCircleByMobileSeries(string_mobile_numberplan);

                alert1 = new AlertDialog.Builder(getActivity());
                alert1.setTitle("BROWSE PLANS");
                alert1.setView(alertLayout);
                alert1.setCancelable(true);
                alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etAmount.setText(null);

                    }
                });


                dialog1 = alert1.create();
                dialog1.show();
            }
        }

    }




    public void GetServiceProviderAndCircleByMobileSeries(String mobilenumber)
    {
        GetServiceProviderAndCircleByMobileSeriesBackground getServiceProviderAndCircleByMobileSeriesBackground=new GetServiceProviderAndCircleByMobileSeriesBackground();
        getServiceProviderAndCircleByMobileSeriesBackground.execute();

    }


    private class StartRecharge extends AsyncTask<String,Integer,String>  {

        AlertDialog alertDialogResponse = new AlertDialog.Builder(getActivity()).create();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        int oprId;
        String MomStatusCode,title,responseJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // progressBar.setVisibility(View.VISIBLE);
            comProgressDialog.showProgress();
            str_rechargeType=spinnerRechargeType.getSelectedItemPosition();


            if(str_rechargeType==1) {

                String operaterIdPrepaid = spinnerSelectOperator.getSelectedItem().toString();
                oprId = mapPrepaid.get(operaterIdPrepaid);
            }

            if(str_rechargeType==2) {

                String operaterIdPrepaid = spinnerSelectOperator.getSelectedItem().toString();
                oprId = mapPostpaid.get(operaterIdPrepaid);
            }

        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                Gson gson = new GsonBuilder().create();
                PaymentTransactionRequest paymentTransactionRequest = new PaymentTransactionRequest();
                Type type = new TypeToken<PaymentTransactionRequest>() {}.getType();

                Encryption encryption= new Encryption(getActivity());

                if(str_rechargeType==1)
                {
                    rechargeType=1;


                    paymentTransactionRequest.setUserId(str_CustomerId);
                    paymentTransactionRequest.setCustomerMobileNumber(Long.valueOf(string_mobile_number));
                    paymentTransactionRequest.setOperaterId(oprId);
                    paymentTransactionRequest.setTransactionAmount(Float.parseFloat(str_amount));
                    paymentTransactionRequest.setCircleId(ServiceCircleID);

                    paymentTransactionRequest.setRechargeType(rechargeType);
                    paymentTransactionRequest.setTpin(encryption.SHA1(string_tpin));
                    paymentTransactionRequest.setChannelId(String_url.channelId);

                    String json = gson.toJson(paymentTransactionRequest, type);
                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(String_url.DoTopUpTransaction);
                    nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                    nameValuePairs.add(new BasicNameValuePair(String_url.transaction_Request, json));

                    title=getString(R.string.MobilePayment);


                }
                if(str_rechargeType==2)
                {

                    rechargeType=2;



                    paymentTransactionRequest.setUserId(Long.valueOf(str_CustomerId));
                    paymentTransactionRequest.setCustomerMobileNumber(Long.valueOf(string_mobile_number));
                    paymentTransactionRequest.setOperaterId(oprId);
                    paymentTransactionRequest.setTransactionAmount(Float.parseFloat(str_amount));
                    paymentTransactionRequest.setCircleId(1);
                    paymentTransactionRequest.setRechargeType(rechargeType);
                    paymentTransactionRequest.setTpin(encryption.SHA1(string_tpin));
                    paymentTransactionRequest.setChannelId(String_url.channelId);

                    String json = gson.toJson(paymentTransactionRequest, type);

                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(String_url.DoMobileBillPayTransaction);

                    nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                    nameValuePairs.add(new BasicNameValuePair(String_url.transaction_Request, json));

                    title=getString(R.string.MobileBillPayment);


                }

                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


// Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                responseJson= EntityUtils.toString(entity);

            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            return responseJson;
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


                    String message = paymentResponseResult.momMessage;
                    MomStatusCode = paymentResponseResult.momStatusCode;
                    String isoTransactionId = paymentResponseResult.isoTransactionId;


                    final ResponseDialog responseDialog1 = new ResponseDialog(getActivity(), getActivity());
                    responseDialog1.showResponseDialog(2, title,
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

                                    macPage1.setVisibility(View.GONE);
                                    macPage2.setVisibility(View.GONE);
                                    macPage3.setVisibility(View.VISIBLE);
                                    macPage4.setVisibility(View.GONE);
                                    macPage5.setVisibility(View.GONE);
                                    macPage6.setVisibility(View.GONE);
                                    macPage7.setVisibility(View.GONE);

                                    spinnerRechargeType.setSelection(0);
                                    etMob.setText(null);
                                    etAmount.setText(null);

                                    responseDialog1.dialog.dismiss();

                                }
                            }

                    );

                   // progressBar.setVisibility(View.GONE);
                    comProgressDialog.cancelProgress();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }



    private class GetServiceProviderAndCircleByMobileSeriesBackground extends AsyncTask<String,Integer,String>  {
        String series_response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar_browseplan.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {

            try {


                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.GetServiceProviderAndCircleByMobileSeries);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.mobileNumber, string_mobile_numberplan));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                series_response = EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }



            return series_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (s != null) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<GetServiceProviderAndCircleByMobileSeriesResponse>>() {
                    }.getType();
                    ResponseBase<GetServiceProviderAndCircleByMobileSeriesResponse> responseResponseBase = gson.fromJson(s, type);
                    GetServiceProviderAndCircleByMobileSeriesResponse getServiceProviderAndCircleByMobileSeriesResponse = responseResponseBase.responseData;
                    if (responseResponseBase.id == 1) {
                        serviceProviderId = getServiceProviderAndCircleByMobileSeriesResponse.serviceProviderId;
                        String ServiceCircleName = getServiceProviderAndCircleByMobileSeriesResponse.serviceCircleName;
                        circleId = getServiceProviderAndCircleByMobileSeriesResponse.serviceCircleId;
                        String ServiceProviderName = getServiceProviderAndCircleByMobileSeriesResponse.serviceProviderName;
                        int ServiceProviderTypeID = getServiceProviderAndCircleByMobileSeriesResponse.serviceProviderTypeId;


                        int ServiceProvider = spinnerServiceProviderName.getCount();
                        String str_ServiceProvider1 = spinnerSelectOperator.getSelectedItem().toString();

                        for (int i = 0; i < ServiceProvider; i++) {
                            String str_ServiceProvider = spinnerServiceProviderName.getItemAtPosition(i).toString();


                            if (str_ServiceProvider1.equals(str_ServiceProvider)) {

                                spinnerServiceProviderName.setSelection(i);
                            }
                        }


                        spinnerServiceProviderName .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                check=check+1;
                                selected_ServiceProviderName=spinnerServiceProviderName.getSelectedItem().toString();

//                        if(check>1)
//                        {
                                branchList4.clear();
                                branchList4=new ArrayList<String>();
                                branchList4.add(getString(R.string.spinner_circle));

                                GetcirclenameBackground getcirclenameBackground1=new GetcirclenameBackground();
                                getcirclenameBackground1.execute();

                                if(!selected_ServiceProviderName.equals("Select Operator"))
                                {
                                    serviceProviderId = getServiceProviderNamebrowseplan_mode.getServiceProviderList(spinnerServiceProviderName.getSelectedItem().toString());


                                    branchList4.clear();
                                    branchList4=new ArrayList<String>();
                                    branchList4.add(getString(R.string.spinner_circle));

                                    branchList5.clear();
                                    branchList5=new ArrayList<String>();
                                    branchList5.add(getString(R.string.spinner_plan));

                                    GetcirclenameBackground getcirclenameBackground=new GetcirclenameBackground();
                                    getcirclenameBackground.execute();
//                            }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });




                    }
                    else
                    {
                        circleId=1;
                    }


                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            progressBar_browseplan.setVisibility(View.GONE);
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


                        btntpinsubmit.setEnabled(true);
                        responseDialog1.dialog.dismiss();

                    }
                }


        );
    }
    public class GetServiceProviderNameBackground extends AsyncTask<Void,Void,String>
    {
        String getServiceProviderName_response;
        GetServiceProviderName_mode getServiceProviderName_mode;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar_browseplan.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();


                HttpPost httppost = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.prepaid_ServiceProviderTypeID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                getServiceProviderName_response = EntityUtils.toString(entity);
                ServiceProviderName_hashMapBranch=new HashMap<String,Integer>();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return getServiceProviderName_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson   = new GsonBuilder().create();
                    Type type   = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>(){}.getType();
                    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase=gson.fromJson(s, type);
                    if(responseBase.id==1)
                    {
                        for(GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse : responseBase.responseData)
                        {
                            int ServiceProviderID=getProviderNameByProviderTypeIDResponse.serviceProviderId;
                            String ServiceProviderName=getProviderNameByProviderTypeIDResponse.serviceProviderName;
                            boolean ServiceProviderStatus=getProviderNameByProviderTypeIDResponse.serviceProviderStatus;

                            ServiceProviderName_hashMapBranch.put(ServiceProviderName, ServiceProviderID);
                            branchList3.add(ServiceProviderName);
                        }
                    }
                }
                getServiceProviderName_mode = new GetServiceProviderName_mode();
                getServiceProviderName_mode.storeServiceProviderList(String_url.ServiceProviderName, ServiceProviderName_hashMapBranch);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchList3) {

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
                spinnerServiceProviderName.setAdapter(adapter);

                spinnerServiceProviderName .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        selected_ServiceProviderName=spinnerServiceProviderName.getSelectedItem().toString();


                        branchList4.clear();
                        branchList4=new ArrayList<String>();
                        branchList4.add(getString(R.string.spinner_circle));

                        GetcirclenameBackground getcirclenameBackground1=new GetcirclenameBackground();
                        getcirclenameBackground1.execute();

                        if(!selected_ServiceProviderName.equals("Select Operator"))
                        {
                            serviceProviderId = getServiceProviderName_mode.getServiceProviderList(spinnerServiceProviderName.getSelectedItem().toString());


                            branchList4.clear();
                            branchList4=new ArrayList<String>();
                            branchList4.add(getString(R.string.spinner_circle));

                            branchList5.clear();
                            branchList5=new ArrayList<String>();
                            branchList5.add(getString(R.string.spinner_plan));

                            GetcirclenameBackground getcirclenameBackground=new GetcirclenameBackground();
                            getcirclenameBackground.execute();
                        }


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
            progressBar_browseplan.setVisibility(View.GONE);
        }
    }
    public class GetcirclenameBackground extends AsyncTask<Void,Void,String>
    {
        String Getcirclename_response;
        GetcirclenameName_mode getcirclenameName_mode;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar_browseplan.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.ServiceCircles);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                Getcirclename_response = EntityUtils.toString(entity);
                CircleName_hashMapBranch=new HashMap<String,Integer>();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return Getcirclename_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson   = new GsonBuilder().create();
                    Type type   = new TypeToken<ResponseBase<ServiceCirclesResponse[]>>(){}.getType();
                    ResponseBase<ServiceCirclesResponse[]> responseBase=gson.fromJson(s, type);
                    if(responseBase.id==1)
                    {
                        for(ServiceCirclesResponse serviceCirclesResponse1:responseBase.responseData)
                        {
                            int ServiceCircleID=serviceCirclesResponse1.ServiceCircleID;
                            String ServiceCircleName=serviceCirclesResponse1.ServiceCircleName;
                            boolean ServiceCircleStatus=serviceCirclesResponse1.ServiceCircleStatus;

                            CircleName_hashMapBranch.put(ServiceCircleName, ServiceCircleID);
                            branchList4.add(ServiceCircleName);
                        }
                    }

                    getcirclenameName_mode = new GetcirclenameName_mode();
                    getcirclenameName_mode.storeCircleList(String_url.CircleName,CircleName_hashMapBranch);
                    if(branchList4!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchList4) {

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
                        spinnerServiceCircleName.setAdapter(adapter);

                        spinnerServiceCircleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                circleName=spinnerServiceCircleName.getSelectedItem().toString();


                                branchList5.clear();
                                branchList5=new ArrayList<String>();
                                branchList5.add(getString(R.string.select_plan));


                                if (!circleName.equals("Select Circle")) {
                                    circleId = getcirclenameName_mode.getCircleList(spinnerServiceCircleName.getSelectedItem().toString());


                                    branchList5.clear();
                                    branchList5 = new ArrayList<String>();
                                    branchList5.add(getString(R.string.select_plan));
                                    GetProductIdByServiceProviderId getProductIdByServiceProviderId = new GetProductIdByServiceProviderId();
                                    productid = getProductIdByServiceProviderId.doInBackground();

                                    GetPlanTypeByProductId getPlanTypeByProductId = new GetPlanTypeByProductId();
                                    getPlanTypeByProductId.execute();
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
            progressBar_browseplan.setVisibility(View.GONE);

        }
    }
    public class GetPlanTypeByProductId extends AsyncTask<Void,Void,String>
    {
        String GetPlanTypeByProductId_response;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar_browseplan.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetPlanTypeByProductId);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.productId,String.valueOf(productid)));
                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetPlanTypeByProductId_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetPlanTypeByProductId_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetPlanTypeByProductIdResponse[]>>(){}.getType();
                    ResponseBase<GetPlanTypeByProductIdResponse[]> responseBase=gson.fromJson(s,type);
                    if(responseBase.responseData!=null)
                    {
                        for(GetPlanTypeByProductIdResponse getPlanTypeByProductIdResponse:responseBase.responseData)
                        {
                            PlanType=getPlanTypeByProductIdResponse.PlanType;
                            branchList5.add(PlanType);
                        }
                    }
                    else
                    {
                        branchList5.clear();
                        branchList5.add(getResources().getString(R.string.noplan));
                        planMRP_List.clear();
                        planDescription_List.clear();
                        planValidity_List.clear();
                        browseplanelistview.setVisibility(View.GONE);
                        amountid.setVisibility(View.GONE);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchList5) {

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
                    spinnerplan.setAdapter(adapter);

                    spinnerplan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            select_spinnerplan=spinnerplan.getSelectedItem().toString();

                            if((!select_spinnerplan.equals(getResources().getString(R.string.spinner_plan)))||(!select_spinnerplan.equals(getResources().getString(R.string.noplan))))
                            {
                                amountid.setVisibility(View.VISIBLE);
                                browseplanelistview.setVisibility(View.VISIBLE);
                                products.clear();
                                GetPlanByProductIdAndPlanType getPlanByProductIdAndPlanType=new GetPlanByProductIdAndPlanType();
                                getPlanByProductIdAndPlanType.execute();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                    });


                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            progressBar_browseplan.setVisibility(View.GONE);

        }
    }
    public class GetPlanByProductIdAndPlanType extends AsyncTask<Void,Void,String>
    {
        String GetPlanByProductIdAndPlanType_response;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar_browseplan.setVisibility(View.VISIBLE);
            planMRP_List.clear();
            planDescription_List.clear();
            planValidity_List.clear();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.GetPlanByProductIdAndPlanType);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.productId,String.valueOf(productid)));
                nameValuePairs.add(new BasicNameValuePair(String_url.planType,select_spinnerplan));
                HttpParams httpParams=httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                GetPlanByProductIdAndPlanType_response= EntityUtils.toString(entity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return GetPlanByProductIdAndPlanType_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<GetPlanByProductIdAndPlanTypeResponse[]>>(){}.getType();
                    ResponseBase<GetPlanByProductIdAndPlanTypeResponse[]> responseBase=gson.fromJson(s,type);
                    if(responseBase.responseData!=null)
                    {
                        for(GetPlanByProductIdAndPlanTypeResponse getPlanByProductIdAndPlanType:responseBase.responseData)
                        {
                            planMRP=getPlanByProductIdAndPlanType.planMRP;
                            planDescription=getPlanByProductIdAndPlanType.planDescription;
                            planValidity=getPlanByProductIdAndPlanType.planValidity;

                            planMRP_List.add(planMRP);
                            planDescription_List.add(planDescription);
                            planValidity_List.add(planValidity);



                        }
                    }
                    adapter1 = new BrowseplaneAdap(getActivity(), planMRP_List, planDescription_List,planValidity_List);
                    browseplanelistview.setAdapter(adapter1);

                    browseplanelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView a, View v, int position, long id) {
                String[] split = planMRP_List.get(position).split("\\.");
                roundedMrp = split[0];
                String secondSubString = split[1];
                etAmount.setText("");
                etAmount.setText(roundedMrp);
                dialog1.dismiss();

            }
        });


                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            progressBar_browseplan.setVisibility(View.GONE);

        }
    }


    private Model get(String s,String place) {
        return new Model(s,place);
    }


    private class GetProductIdByServiceProviderId extends AsyncTask<Void,Void,Integer>
    {
        String message;
        int productId;

        @Override
        protected Integer doInBackground(Void... params)
        {
            try{
                HttpClient httpclient = new DefaultHttpClient();


                HttpPost httppost = new HttpPost(String_url.GetProductIdByServiceProviderId);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceProviderId, String.valueOf(serviceProviderId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.circleId, String.valueOf(circleId)));



                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                String GetProductIdByServiceProviderId_response= EntityUtils.toString(entity);


                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<ResponseBase<ProductIDResponse>>() {}.getType();
                ResponseBase<ProductIDResponse> responseBase = gson.fromJson(GetProductIdByServiceProviderId_response, type);
                ProductIDResponse productIDResponse = responseBase.responseData;
                if(responseBase.id==1)
                {
                    productId=productIDResponse.productId;
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return productId;
        }


    }


    public class MobilePaymentAdap extends ArrayAdapter<String> {


        private final Activity context;
        private final List<String> planMRP_List;
        private final List<String> planDescription_List;
        private final List<String> planValidity_List;
        private final List<String> planValidity_List1;
        String pos,roundedMrp;

        public MobilePaymentAdap(Activity context, List<String> planMRP_List, List<String> planDescription_List, List<String> planValidity_List,List<String> planValidity_List1) {
            super(context, R.layout.mobilepayment_adapter, planMRP_List);
            // TODO Auto-generated constructor stub
            this.context=context;
            this.planMRP_List=planMRP_List;
            this.planDescription_List=planDescription_List;
            this.planValidity_List=planValidity_List;
            this.planValidity_List1=planValidity_List1;
        }
        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            final View rowView=inflater.inflate(R.layout.mobilepayment_adapter, null,true);



            Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            TextView amount = (TextView) rowView.findViewById(R.id.amount);
            // TextView amount = (TextView) ((View) view.getParent()).findViewById(R.id.amount);
            TextView validity1 = (TextView) rowView.findViewById(R.id.validity);
            TextView talktime1 = (TextView) rowView.findViewById(R.id.talktime);
            TextView description = (TextView) rowView.findViewById(R.id.description);
            // TextView btnselectplan = (TextView) rowView.findViewById(R.id.btnselectplan);
            Button btnselectplan=(Button)rowView.findViewById(R.id.btnselectplan);



            pos=planMRP_List.get(position);




            String[] split = pos.split("\\.");
            roundedMrp = split[0];
            String secondSubString = split[1];





            amount.setText("");
            talktime1.setText("");
            description.setText("");
            validity1.setText("");
            amount.setText(roundedMrp);
            description.setText(planDescription_List.get(position));
            if(planValidity_List.get(position)==""||planValidity_List.get(position)==null)
            {
                validity1.setText(getResources().getString(R.string.na));
                talktime1.setText(getResources().getString(R.string.na));
            }
            else
            {
                validity1.setText(planValidity_List.get(position));
                talktime1.setText(planValidity_List.get(position));
            }
            //txtTitle2.setText(rs[position]);
            amount.setTypeface(Light);
            validity1.setTypeface(Light);
            talktime1.setTypeface(Light);
            description.setTypeface(Light);

            return rowView;
        };


    }
    public class MobilePaymentadapter1 extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] amount;
        private final String[] validity;
        private final String[] itemlist;
        private final String[] talktime;
        public String amnt;
        String pos;
        EditText et_enter_amount_recharge;



        public MobilePaymentadapter1(Activity context, String[] amount, String[] validity, String[] itemlist,String[] talktime) {
            super(context, R.layout.mobilepayment_adapter, amount);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.amount=amount;
            this.validity=validity;
            this.itemlist=itemlist;
            this.talktime=talktime;
        }

        public View getView(final int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            final View rowView=inflater.inflate(R.layout.mobilepayment_adapter, null,true);

            Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            TextView amount1 = (TextView) rowView.findViewById(R.id.amount);
            TextView validity1 = (TextView) rowView.findViewById(R.id.validity);
            TextView talktime1 = (TextView) rowView.findViewById(R.id.talktime);
            TextView txtTitle1 = (TextView) rowView.findViewById(R.id.textView2);

            Button btnselectplan=(Button)rowView.findViewById(R.id.btnselectplan);


            pos=amount[position];

            amount1.setText(amount[position]);
            validity1.setText(validity[position]);
            talktime1.setText(talktime[position]);
            txtTitle1.setText(itemlist[position]);


            amount1.setTypeface(Light);
            validity1.setTypeface(Light);
            talktime1.setTypeface(Light);
            txtTitle1.setTypeface(Light);

            btnselectplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etAmount.setText(pos);
                    dialog1.dismiss();

                }


            });

            return rowView;

        };
        public void  setamount(String amnt)
        {
            this.amnt=amnt;

        }
        public String getamount()
        {
            return amnt;
        }
    }

    public class GetServiceProviderNameBackground_browseplan extends AsyncTask<Void,Void,String>
    {
        String getServiceProviderName_response;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar_browseplan.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();


                HttpPost httppost = new HttpPost(String_url.GetProviderNameByProviderTypeID);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID, String.valueOf(String_url.prepaid_ServiceProviderTypeID)));

                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                getServiceProviderName_response = EntityUtils.toString(entity);
                ServiceProviderName_hashMapBranch=new HashMap<String,Integer>();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return getServiceProviderName_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson   = new GsonBuilder().create();
                    Type type   = new TypeToken<ResponseBase<GetProviderNameByProviderTypeIDResponse[]>>(){}.getType();
                    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase=gson.fromJson(s, type);
                    if(responseBase.id==1)
                    {
                        for(GetProviderNameByProviderTypeIDResponse getProviderNameByProviderTypeIDResponse : responseBase.responseData)
                        {
                            int ServiceProviderID=getProviderNameByProviderTypeIDResponse.serviceProviderId;
                            String ServiceProviderName=getProviderNameByProviderTypeIDResponse.serviceProviderName;
                            boolean ServiceProviderStatus=getProviderNameByProviderTypeIDResponse.serviceProviderStatus;



                            ServiceProviderName_hashMapBranch.put(ServiceProviderName, ServiceProviderID);
                            branchList3.add(ServiceProviderName);
                        }
                    }
                }

                getServiceProviderNamebrowseplan_mode.storeServiceProviderList(String_url.ServiceProviderName, ServiceProviderName_hashMapBranch);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchList3) {

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
                spinnerServiceProviderName.setAdapter(adapter);




            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            progressBar_browseplan.setVisibility(View.GONE);
        }
    }
    public class GetcirclenameBackground_browseplan extends AsyncTask<Void,Void,String>
    {
        String Getcirclename_response;
        GetcirclenameNameBrowseplan_mode getcirclenameName_mode;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar_browseplan.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(String_url.ServiceCircles);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                Getcirclename_response = EntityUtils.toString(entity);
                CircleName_hashMapBranch=new HashMap<String,Integer>();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return Getcirclename_response;
        }
        @Override
        protected  void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson   = new GsonBuilder().create();
                    Type type   = new TypeToken<ResponseBase<ServiceCirclesResponse[]>>(){}.getType();
                    ResponseBase<ServiceCirclesResponse[]> responseBase=gson.fromJson(s, type);
                    if(responseBase.id==1)
                    {
                        for(ServiceCirclesResponse serviceCirclesResponse1:responseBase.responseData)
                        {
                            int ServiceCircleID=serviceCirclesResponse1.ServiceCircleID;
                            String ServiceCircleName=serviceCirclesResponse1.ServiceCircleName;
                            boolean ServiceCircleStatus=serviceCirclesResponse1.ServiceCircleStatus;

                            CircleName_hashMapBranch.put(ServiceCircleName, ServiceCircleID);
                            branchList4.add(ServiceCircleName);
                        }
                    }

                    getcirclenameName_mode = new GetcirclenameNameBrowseplan_mode();
                    getcirclenameName_mode.storeCircleList(String_url.CircleName,CircleName_hashMapBranch);
                    if(branchList4!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchList4) {

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
                        spinnerServiceCircleName.setAdapter(adapter);




                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            progressBar_browseplan.setVisibility(View.GONE);

        }
    }
}





