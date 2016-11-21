package com.mom.app.retail;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.widget.AdapterView.OnItemClickListener;

import com.mom.app.retail.Adapters.PaymentHistoryAdap;
import com.mom.app.retail.Response.GetProviderNameByProviderTypeIDResponse;
import com.mom.app.retail.Response.ResponseBase;
import com.mom.app.retail.Response.TransactionhistoryResponse;
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


import android.widget.AbsListView.OnScrollListener;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Settings_Prepaid_mobileDetailList extends Fragment  implements  OnScrollListener{
    public static final String PREFS_NAME = "MyApp_Settings";
    private ImageView imageView;
    private TextView tvPrepaidMobImageText,tv_prepared_mobile,tv_prepared_mobile_history,tv_transaction_successfull,tv_transaction_id,tv_transaction_id1,
            tv_transaction_type,tv_transaction_type1,tv_mobile_no,tv_mobile_no1,tv_amount,tv_amount1,tv_transaction_time,tv_transaction_time1;;
    ListView list;

    boolean loadingMore = false;
    ProgressDialog  progress;
    long CustomerID;
    PaymentHistoryAdap adapter;
    List<String> ListElementsArrayList;
    ArrayAdapter<String> adapter1;
    boolean flag_loading;
    ResponseBase<GetProviderNameByProviderTypeIDResponse[]> responseBase;
    ResponseBase<TransactionhistoryResponse[]> transaction_responseBase;
    int lastitem;
    int counter=1;
    int serviceProviderTypeId=0;
    LinearLayout page1,page2;

    long MobileNumber,CustomerMobileNumber;
    String OperatorName,TxnAmount,TxnStatus,ISOTransactionID,TxnDate,ServiceProviderTypeName;
    String valuesResponse[];
    ArrayList<Long> MobileNumberlist;
    ArrayList<String> OperatorNamelist;
    ArrayList<String> TxnAmountlist;
    ArrayList<String> TxnStatuslist;
    ArrayList<String> ISOTransactionIDlist;
    ArrayList<String> TxnDatelist;
    ArrayList<String> ServiceProviderTypeNamelist;

    int currentFirstVisibleItem = 0;
    int currentVisibleItemCount = 0;
    int totalItemCount = 0;
    int currentScrollState = 0;

    Long startIndex = 0L;
    Long offset = 10L;
    View footerView;
    private ProgressBar progressBarTransactionDetails;






    ArrayList<String> Operator_mobile_list,status,rs1,ServiceProviderTypeName_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prepaid_mobile_detail_list,container, false);
        NetworkConnection networkConnection = new NetworkConnection(getActivity());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();

        if (!isNetworkAvailable) {
            NetworkConnection.ExitAppDialog(getActivity());
        }
        else {

//            Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
//            Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
//            Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

            imageView=(ImageView)view.findViewById(R.id.img_image);
            imageView.setBackgroundResource(R.drawable.allscreen);
            tvPrepaidMobImageText=(TextView)view.findViewById(R.id.tvPrepaidMobImageText);

            tv_prepared_mobile=(TextView)view.findViewById(R.id.tv_prepared_mobile);

            tv_transaction_successfull=(TextView)view.findViewById(R.id.tv_transaction_successfull);
            tv_transaction_id=(TextView)view.findViewById(R.id.tv_transaction_id);
            tv_transaction_id1=(TextView)view.findViewById(R.id.tv_transaction_id1);
            tv_transaction_type=(TextView)view.findViewById(R.id.tv_transaction_type);
            tv_transaction_type1=(TextView)view.findViewById(R.id.tv_transaction_type1);
            tv_mobile_no=(TextView)view.findViewById(R.id.tv_mobile_no);
            tv_mobile_no1=(TextView)view.findViewById(R.id.tv_mobile_no1);
            tv_amount=(TextView)view.findViewById(R.id.tv_amount);
            tv_amount1=(TextView)view.findViewById(R.id.tv_amount1);
            tv_transaction_time=(TextView)view.findViewById(R.id.tv_transaction_time);
            tv_transaction_time1=(TextView)view.findViewById(R.id.tv_transaction_time1);

            tvPrepaidMobImageText.setTypeface(Light);
            tv_prepared_mobile.setTypeface(Light);

            tv_transaction_successfull.setTypeface(Light);
            tv_transaction_id.setTypeface(Light);
            tv_transaction_id1.setTypeface(Light);
            tv_transaction_type.setTypeface(Light);
            tv_transaction_type1.setTypeface(Light);
            tv_mobile_no.setTypeface(Light);
            tv_mobile_no1.setTypeface(Light);
            tv_amount.setTypeface(Light);
            tv_amount1.setTypeface(Light);
            tv_transaction_time.setTypeface(Light);
            tv_transaction_time1.setTypeface(Light);


            SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String channel = shared.getString(String_url.Position, "");

            String ServiceProviderTypeID=getArguments().getString("ServiceProviderTypeID");

            String ServiceProviderTypeName=getArguments().getString("ServiceProviderTypeName");

            //String ServiceName_Capitalized = ServiceProviderTypeName.substring(0, 1).toUpperCase() + ServiceProviderTypeName.substring(1).toLowerCase();


            //String ServiceName_Capitalized = WordUtils.capitalize("this is first WORD capital test.");





            Log.d("bundle_id",ServiceProviderTypeID);

            Log.d("bundle_Name",ServiceProviderTypeName);

            CustomerID=shared.getLong(String_url.CustomerID,0);
            Log.d("CustomerID",String.valueOf(CustomerID));
            list=(ListView)view.findViewById(R.id.listview1);
            page1 = (LinearLayout) view.findViewById(R.id.page1);
            page2 = (LinearLayout) view.findViewById(R.id.page2);
            MobileNumberlist = new ArrayList<Long>();
            OperatorNamelist = new ArrayList<String>();
            TxnAmountlist = new ArrayList<String>();
            TxnStatuslist = new ArrayList<String>();
            ISOTransactionIDlist = new ArrayList<String>();
            TxnDatelist = new ArrayList<String>();
            ServiceProviderTypeNamelist=new ArrayList<String>();


            Operator_mobile_list = new ArrayList<String>();
            status= new ArrayList<String>();
            rs1= new ArrayList<String>();
            ServiceProviderTypeName_list= new ArrayList<String>();
            flag_loading=true;
            progressBarTransactionDetails=(ProgressBar)view.findViewById(R.id.progressBarTransactionDetails);


            list.setStackFromBottom(true);
            list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);


           tv_prepared_mobile.setText(capLetter(ServiceProviderTypeName));


            serviceProviderTypeId =Integer.parseInt(ServiceProviderTypeID);
            AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
            androidTransactionsBackground.execute();
            list.setOnScrollListener(this);


//            switch (channel) {
//                case String_url.zero: {
//
//                    tv_prepared_mobile.setText(R.string.transactionHistory);
//                    serviceProviderTypeId = 0;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.one: {
//                    tv_prepared_mobile.setText(R.string.prepared_mobile_history);
//                    serviceProviderTypeId = 1;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.Two: {
//                    tv_prepared_mobile.setText(R.string.pospaid_mobile_history);
//                    serviceProviderTypeId = 2;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.Three: {
//                    tv_prepared_mobile.setText(R.string.dth_history);
//                    serviceProviderTypeId = 3;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.Four: {
//                    tv_prepared_mobile.setText(R.string.electricity_history);
//                    serviceProviderTypeId = 4;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.Five: {
//                    tv_prepared_mobile.setText(R.string.gas_history);
//                    serviceProviderTypeId = 5;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.Six: {
//                    tv_prepared_mobile.setText(R.string.money_mobile_history);
//                    serviceProviderTypeId = 36;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.Seven: {
//                    tv_prepared_mobile.setText(R.string.gifttransaction);
//                    serviceProviderTypeId = 13;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//                case String_url.Eight: {
//                    tv_prepared_mobile.setText(R.string.tabcabtransaction);
//                    serviceProviderTypeId = 12;
//                    AndroidTransactionsBackground androidTransactionsBackground = new AndroidTransactionsBackground();
//                    androidTransactionsBackground.execute();
//                    list.setOnScrollListener(this);
//                    break;
//                }
//            }
            list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    getData(position);


                }
            });

        }
        return view;
    }

    @Override
    public void onScroll(AbsListView lw, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount) {

        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;
        this.isScrollCompleted();

    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE && this.totalItemCount == (currentFirstVisibleItem + currentVisibleItemCount)) {
            /*** In this way I detect if there's been a scroll which has completed ***/
            /*** do the work for load more date! ***/
            if (!flag_loading) {

                flag_loading = true;

                progress = ProgressDialog.show(getActivity()

                        , "",
                        getString(R.string.loading), true);

                new Handler().postDelayed(new Runnable() {




                    @Override
                    public void run() {

                        counter++;
                        //Toast.makeText(getActivity(),"counter"+counter,Toast.LENGTH_SHORT).show();


                        AndroidTransactionsBackground1 androidTransactionsBackground1=new AndroidTransactionsBackground1();
                        androidTransactionsBackground1.execute();
                    }

                }, 2000);



            }
        }
    }

    private class AndroidTransactionsBackground extends AsyncTask<Void,Void,String>
    {
        String all_response;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBarTransactionDetails.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {

                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.AndroidTransactions);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerID,Long.toString(CustomerID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID,String.valueOf(serviceProviderTypeId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.page,String.valueOf(counter)));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceChannelId,String.valueOf(String_url.channelId)));

                final HttpParams httpParams = httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();
                all_response= EntityUtils.toString(entity);




            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return all_response;
        }
        @Override
        protected void onPostExecute(String s)
        {
            try
            {
                if(s!=null)
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<TransactionhistoryResponse[]>>(){}.getType();
                    transaction_responseBase=gson.fromJson(s,type);

                    if(transaction_responseBase.responseData!=null)
                    {
                        for(TransactionhistoryResponse transactionhistoryResponse:transaction_responseBase.responseData)
                        {
                            int length=transaction_responseBase.responseData.length;
                            valuesResponse=new String[length];

                            MobileNumber=transactionhistoryResponse.MobileNumber;
                            CustomerMobileNumber=transactionhistoryResponse.CustomerMobileNumber;
                            OperatorName=transactionhistoryResponse.OperatorName;
                            TxnAmount=transactionhistoryResponse.TxnAmount;
                            TxnStatus=transactionhistoryResponse.TxnStatus;
                            ISOTransactionID=transactionhistoryResponse.ISOTransactionID;
                            TxnDate=transactionhistoryResponse.TxnDate;
                            ServiceProviderTypeName=transactionhistoryResponse.ServiceProviderTypeName;
                            double txnamt=Double.parseDouble(TxnAmount);

                            //double newTxnAmount = Math.round(txnamt*100.0)/100.0;

                            int decimalsToConsider = 2;
                            BigDecimal bigDecimal = new BigDecimal(txnamt);
                            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);

                            MobileNumberlist.add(CustomerMobileNumber);
                            OperatorNamelist.add(OperatorName);
                            TxnAmountlist.add(String.valueOf(roundedWithScale));
                            TxnStatuslist.add(TxnStatus);
                            ISOTransactionIDlist.add(ISOTransactionID);
                            TxnDatelist.add(TxnDate);
                            ServiceProviderTypeNamelist.add(ServiceProviderTypeName);



                            String Mob_op=OperatorName+"-"+CustomerMobileNumber;

                            Operator_mobile_list.add(Mob_op);
                            status.add(TxnStatus);
                            rs1.add(String.valueOf(roundedWithScale));
                            ServiceProviderTypeName_list.add(ServiceProviderTypeName);
                            flag_loading=false;

                        }
                    }

                    else
                    {
                        flag_loading=true;
                        page1.setVisibility(View.GONE);
                        page2.setVisibility(View.VISIBLE);
                        String trasaction="No Transaction History";
                        tv_transaction_successfull.setText(trasaction);
                        tv_transaction_id.setVisibility(View.GONE);
                        tv_transaction_id1.setVisibility(View.GONE);
                        tv_transaction_type.setVisibility(View.GONE);
                        tv_transaction_type1.setVisibility(View.GONE);
                        tv_mobile_no.setVisibility(View.GONE);
                        tv_mobile_no1.setVisibility(View.GONE);
                        tv_amount.setVisibility(View.GONE);
                        tv_amount1.setVisibility(View.GONE);
                        tv_transaction_time.setVisibility(View.GONE);
                        tv_transaction_time1.setVisibility(View.GONE);
                    }
                }
                progressBarTransactionDetails.setVisibility(View.GONE);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            adapter = new PaymentHistoryAdap(getActivity(), Operator_mobile_list, status,rs1,ServiceProviderTypeName_list);
            list.setAdapter(adapter);
        }


    }

    private class AndroidTransactionsBackground1 extends AsyncTask<Void,Void,String>
    {
        String history_response;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {

                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(String_url.AndroidTransactions);
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));
                nameValuePairs.add(new BasicNameValuePair(String_url.CustomerID,Long.toString(CustomerID)));
                nameValuePairs.add(new BasicNameValuePair(String_url.ServiceProviderTypeID,String.valueOf(serviceProviderTypeId)));
                nameValuePairs.add(new BasicNameValuePair(String_url.page,String.valueOf(counter)));
                nameValuePairs.add(new BasicNameValuePair(String_url.serviceChannelId,String.valueOf(String_url.channelId)));

                final HttpParams httpParams = httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();
                history_response= EntityUtils.toString(entity);



            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return history_response;
        }
        @Override
        protected void onPostExecute(String s)
        {
            if(s!=null)
            {
                try
                {
                    Gson gson=new GsonBuilder().create();
                    Type type=new TypeToken<ResponseBase<TransactionhistoryResponse[]>>(){}.getType();
                    transaction_responseBase=gson.fromJson(s,type);
                    if(transaction_responseBase.responseData!=null)
                    {
                        for(TransactionhistoryResponse transactionhistoryResponse:transaction_responseBase.responseData)
                        {
                            MobileNumber=transactionhistoryResponse.MobileNumber;
                            CustomerMobileNumber=transactionhistoryResponse.CustomerMobileNumber;
                            OperatorName=transactionhistoryResponse.OperatorName;
                            TxnAmount=transactionhistoryResponse.TxnAmount;
                            TxnStatus=transactionhistoryResponse.TxnStatus;
                            ISOTransactionID=transactionhistoryResponse.ISOTransactionID;
                            TxnDate=transactionhistoryResponse.TxnDate;
                            ServiceProviderTypeName=transactionhistoryResponse.ServiceProviderTypeName;

                            double txnamt=Double.parseDouble(TxnAmount);

                            //double newTxnAmount = Math.round(txnamt*100.0)/100.0;

                            int decimalsToConsider = 2;
                            BigDecimal bigDecimal = new BigDecimal(txnamt);
                            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);

                            MobileNumberlist.add(CustomerMobileNumber);
                            OperatorNamelist.add(OperatorName);
                            TxnAmountlist.add(String.valueOf(roundedWithScale));
                            TxnStatuslist.add(TxnStatus);
                            ISOTransactionIDlist.add(ISOTransactionID);
                            TxnDatelist.add(TxnDate);
                            ServiceProviderTypeNamelist.add(ServiceProviderTypeName);

                            String Mob_op=OperatorName+"-"+CustomerMobileNumber;

                            Operator_mobile_list.add(Mob_op);
                            status.add(TxnStatus);
                            rs1.add(String.valueOf(roundedWithScale));
                            ServiceProviderTypeName_list.add(ServiceProviderTypeName);
                            flag_loading=false;
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),R.string.nodata,Toast.LENGTH_SHORT).show();
                        flag_loading=true;
                        progress.dismiss();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                adapter = new PaymentHistoryAdap(getActivity(),Operator_mobile_list, status,rs1,ServiceProviderTypeName_list);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
                progress.dismiss();
                list.setSelection(lastitem - 1);
            }
        }


    }

    public void getData(int position)
    {
        page1.setVisibility(View.GONE);
        page2.setVisibility(View.VISIBLE);
        //String trasaction="TRANSACTION"+" "+TxnStatuslist.get(position);
        String trasaction=TxnStatuslist.get(position);



        if(trasaction.toLowerCase().contains("success"))
        {
            //green
            //   txtTitle1.setTextColor(Color.parseColor("#FF20C220"));
           // tv_transaction_successfull.setTextColor(getResources().getColor(R.color.progress_color));
            tv_transaction_successfull.setTextColor(Color.parseColor("#008513"));
        }
        if(trasaction.toLowerCase().contains("pending"))
        {
            //yellow
            tv_transaction_successfull.setTextColor(Color.RED);
        }
        if(trasaction.toLowerCase().contains("failed"))
        {
            //red
            tv_transaction_successfull.setTextColor(Color.parseColor("#ff5b57"));
        }


        tv_transaction_successfull.setText(trasaction);
        tv_transaction_id1.setText(ISOTransactionIDlist.get(position));
        tv_transaction_type1.setText(OperatorNamelist.get(position));
        tv_mobile_no1.setText(String.valueOf(MobileNumberlist.get(position)));
        tv_amount1.setText(TxnAmountlist.get(position));
        tv_transaction_time1.setText(TxnDatelist.get(position));
    }

    public String capLetter(String string)
    {
        String[] strArray = string.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builder.append(cap + " ");
        }

        return builder.toString();
    }


}