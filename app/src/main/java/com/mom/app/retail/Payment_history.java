package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mom.app.retail.Adapters.GetMasterServiceProviderTypeAdapter;
import com.mom.app.retail.Response.CustomerFavoriteListByCustomerIDResponse;
import com.mom.app.retail.Response.GetMasterServiceProviderTypeResponse;
import com.mom.app.retail.Response.ResponseBase;

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

public class Payment_history extends Fragment {
    public static final String PREFS_NAME = "MyApp_Settings";
    private ImageView imageView;
    private TextView tvHistoryImageText,tv_payment_history;
    ComProgressDialog comProgressDialog;
    List<String> MasterList;
    List<String> ServideProviderResultList;
    ListView list;
    Get_MasterServiceProviderType get_masterServiceProviderType=new Get_MasterServiceProviderType();
   

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment_history,container, false);
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
            tvHistoryImageText=(TextView)view.findViewById(R.id.tvHistoryImageText);
            tv_payment_history=(TextView)view.findViewById(R.id.tv_payment_history);

            comProgressDialog = new ComProgressDialog(getActivity(),getActivity());

            String[] itemlist ={
                    getString(R.string.all),
                    getString(R.string.prepaid_mobile),
                    getString(R.string.postpaid_mobile),
                    getString(R.string.dth_recharge),
                    getString(R.string.electricity_payments),
                    getString(R.string.gas_payments),
                    getString(R.string.mobile_money_transfer),
                    getString(R.string.gift_voucher),
                    getString(R.string.tab_payments)
            };
            tvHistoryImageText.setTypeface(Light);
            tv_payment_history.setTypeface(Light);
            list=(ListView)view.findViewById(R.id.listview1);

            new GetMasterServiceProviderType().execute();
            //CustomListAdapter adapter=new CustomListAdapter(getActivity(),  itemlist);
            //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,setting);
            //list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(getActivity(),"position"+position,Toast.LENGTH_SHORT).show();
//                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor=sharedPreferences.edit();
//                    switch(position)
//                    {
//                        case 0:
//
//                            editor.putString(String_url.Position,String_url.zero);
//                            editor.commit();
//                            FragmentManager fragmentManager=getFragmentManager();
//                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList spm=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction.replace(R.id.fragmentcontent,spm).addToBackStack(null);
//                            fragmentTransaction.commit();
//                            break;
//
//                        case 1:
//
//                            editor.putString(String_url.Position, String_url.one);
//                            editor.commit();
//                            FragmentManager fragmentManager1=getFragmentManager();
//                            FragmentTransaction fragmentTransaction1=fragmentManager1.beginTransaction();
//                            fragmentTransaction1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm1=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction1.replace(R.id.fragmentcontent,tm1).addToBackStack(null);
//                            fragmentTransaction1.commit();
//
//                            break;
//                        case 2:
//
//                            editor.putString(String_url.Position, String_url.Two);
//                            editor.commit();
//                            FragmentManager fragmentManager2=getFragmentManager();
//                            FragmentTransaction fragmentTransaction2=fragmentManager2.beginTransaction();
//                            fragmentTransaction2.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm2=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction2.replace(R.id.fragmentcontent,tm2).addToBackStack(null);
//                            fragmentTransaction2.commit();
//
//                            break;
//                        case 3:
//
//                            editor.putString(String_url.Position, String_url.Three);
//                            editor.commit();
//                            FragmentManager fragmentManager3=getFragmentManager();
//                            FragmentTransaction fragmentTransaction3=fragmentManager3.beginTransaction();
//                            fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm3=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction3.replace(R.id.fragmentcontent,tm3).addToBackStack(null);
//                            fragmentTransaction3.commit();
//
//                            break;
//
//                        case 4:
//
//                            editor.putString(String_url.Position, String_url.Four);
//                            editor.commit();
//                            FragmentManager fragmentManager4=getFragmentManager();
//                            FragmentTransaction fragmentTransaction4=fragmentManager4.beginTransaction();
//                            fragmentTransaction4.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm4=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction4.replace(R.id.fragmentcontent,tm4).addToBackStack(null);
//                            fragmentTransaction4.commit();
//
//                            break;
//
//                        case 5:
//
//                            editor.putString(String_url.Position, String_url.Five);
//                            editor.commit();
//                            FragmentManager fragmentManager5=getFragmentManager();
//                            FragmentTransaction fragmentTransaction5=fragmentManager5.beginTransaction();
//                            fragmentTransaction5.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm5=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction5.replace(R.id.fragmentcontent,tm5).addToBackStack(null);
//                            fragmentTransaction5.commit();
//
//                            break;
//
//                        case 6:
//
//                            editor.putString(String_url.Position, String_url.Six);
//                            editor.commit();
//                            FragmentManager fragmentManager6=getFragmentManager();
//                            FragmentTransaction fragmentTransaction6=fragmentManager6.beginTransaction();
//                            fragmentTransaction6.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm6=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction6.replace(R.id.fragmentcontent,tm6).addToBackStack(null);
//                            fragmentTransaction6.commit();
//                            break;
//
//                        case 7:
//
//                            editor.putString(String_url.Position, String_url.Seven);
//                            editor.commit();
//                            FragmentManager fragmentManager7=getFragmentManager();
//                            FragmentTransaction fragmentTransaction7=fragmentManager7.beginTransaction();
//                            fragmentTransaction7.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm7=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction7.replace(R.id.fragmentcontent,tm7).addToBackStack(null);
//                            fragmentTransaction7.commit();
//                            break;
//
//                        case 8:
//
//                            editor.putString(String_url.Position, String_url.Eight);
//                            editor.commit();
//                            FragmentManager fragmentManager8=getFragmentManager();
//                            FragmentTransaction fragmentTransaction8=fragmentManager8.beginTransaction();
//                            fragmentTransaction8.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Settings_Prepaid_mobileDetailList tm8=new Settings_Prepaid_mobileDetailList();
//                            fragmentTransaction8.replace(R.id.fragmentcontent,tm8).addToBackStack(null);
//                            fragmentTransaction8.commit();
//                            break;
//
//                    }


                    String str_listview=(String) (list.getItemAtPosition(position));


                    String ServiceProviderTypeID=get_masterServiceProviderType.getServiceProviderTypeID(str_listview);
                    String ServiceProviderTypeName=get_masterServiceProviderType.getServiceProviderTypeName(str_listview);

                    Log.d("ServiceProviderTypeID",ServiceProviderTypeID);
                    Log.d("ServiceProviderTypeName",ServiceProviderTypeName);

                    FragmentManager fragmentManager3=getFragmentManager();
                    FragmentTransaction fragmentTransaction3=fragmentManager3.beginTransaction();
                    fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    Settings_Prepaid_mobileDetailList tm3=new Settings_Prepaid_mobileDetailList();
                    fragmentTransaction3.replace(R.id.fragmentcontent,tm3).addToBackStack(null);
                    Bundle mBundle=new Bundle();
                    mBundle.putString("ServiceProviderTypeID",ServiceProviderTypeID);
                    mBundle.putString("ServiceProviderTypeName",ServiceProviderTypeName);
                    tm3.setArguments(mBundle);
                    fragmentTransaction3.commit();
                }
            });
        }

        return view;

    }


    private class GetMasterServiceProviderType extends AsyncTask<String,Integer,String>
    {
        String GetMasterServiceProviderType_response;
        ResponseBase<GetMasterServiceProviderTypeResponse[]> responseBase;
        Map<String, List<String>> map1;

        ArrayList<String> mylist = new ArrayList<String>();

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
                HttpPost httppost = new HttpPost(String_url.GetMasterServiceProviderTypeAll);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(String_url.AuthKey, String_url.auth_key1));


                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
                HttpConnectionParams.setSoTimeout(httpParams, 45000);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                GetMasterServiceProviderType_response = EntityUtils.toString(entity);
                Log.d("GetMasterType_response",String.valueOf(GetMasterServiceProviderType_response));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return GetMasterServiceProviderType_response;
        }



        @Override
        protected void onPostExecute(String s)
        {
            try {

                if(s!=null)
                {

                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ResponseBase<GetMasterServiceProviderTypeResponse[]>>(){}.getType();
                    responseBase = gson.fromJson(s,type);
                    map1 = new HashMap<String, List<String>>();
                    MasterList = new ArrayList<String>();

                    if(responseBase.id==1)
                    {
                        for(GetMasterServiceProviderTypeResponse getMasterServiceProviderTypeResponse:responseBase.responseData)
                        {
                            int ServiceProviderTypeID=getMasterServiceProviderTypeResponse.ServiceProviderTypeID;
                            String ServiceProviderTypeName=getMasterServiceProviderTypeResponse.ServiceProviderTypeName;

                            List<String> set=new ArrayList<String>();
                            set.add(ServiceProviderTypeName);
                            set.add(String.valueOf(ServiceProviderTypeID));
                            map1.put(ServiceProviderTypeName,set);
                            MasterList.add(ServiceProviderTypeName);

                            get_masterServiceProviderType.storeGetMasterServiceProviderTypeAll(String_url.MasterServiceProviderType_mode,map1);
                            get_masterServiceProviderType.storeGetMasterServiceProviderTypeAll(String_url.ServiceProviderTypeName,MasterList);
                            ServideProviderResultList = get_masterServiceProviderType.getGetMasterServiceProviderTypeAll();

                            Log.d("ServideProvider",String.valueOf(ServideProviderResultList));

                        }

                        GetMasterServiceProviderTypeAdapter adapter=new GetMasterServiceProviderTypeAdapter(getActivity(),  ServideProviderResultList);
                        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,setting);
                        list.setAdapter(adapter);
                    }



                }
                comProgressDialog.cancelProgress();


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}