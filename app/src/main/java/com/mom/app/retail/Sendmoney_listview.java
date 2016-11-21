package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sendmoney_listview extends Fragment {
    private ImageView imageView;
    private TextView tvsettingImageText,tv_setting;
    private ListView listview1;
    String str_CustomerRoleName;

   public static final String PREFS_NAME = "MyApp_Settings";
  

    final String[] setting=new String[]{"Money Mobile Order","Load Transfer","Balance Transfer"};
    String[] itemlist ={
            String_url.money_mobile_order,
            String_url.load_money,
            String_url.payment_transfer,
            String_url.payment_request,

    };

    String[] itemlist_retailer={
       String_url.money_mobile_order,
       String_url.load_money,
       String_url.payment_request,
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting,container, false);
        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);




        imageView=(ImageView)view.findViewById(R.id.img_image);
        tvsettingImageText=(TextView)view.findViewById(R.id.tvsettingImageText);
        tv_setting=(TextView)view.findViewById(R.id.tv_setting);
        listview1=(ListView)view.findViewById(R.id.listview1);

        tvsettingImageText.setTypeface(Light);
        tv_setting.setTypeface(Light);
        tv_setting.setText(getString(R.string.send_money));
        tvsettingImageText.setText(getString(R.string.send_money));


        SharedPreferences getCustomerRoleName=getActivity().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        str_CustomerRoleName=getCustomerRoleName.getString(String_url.CustomerRoleName,"").toLowerCase();





        imageView.setBackgroundResource(R.drawable.allscreen);
        if(str_CustomerRoleName.equals(String_url.retailer))
        {
            CustomListAdapter adapter=new CustomListAdapter(getActivity(),  itemlist_retailer);
            ListView list=(ListView)view.findViewById(R.id.listview1);



            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch(position)
                    {
                        case 0:
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Moneyorder moneyorder=new Moneyorder();
                            fragmentTransaction.replace(R.id.fragmentcontent,moneyorder).addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 1:

                            FragmentManager fragmentManager1=getFragmentManager();
                            FragmentTransaction fragmentTransaction1=fragmentManager1.beginTransaction();
                            fragmentTransaction1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            LoadMoney loadMoney=new LoadMoney();
                            fragmentTransaction1.replace(R.id.fragmentcontent,loadMoney).addToBackStack(null);
                            fragmentTransaction1.commit();
                            break;

                        case 2:
                            FragmentManager fragmentManager3=getFragmentManager();
                            FragmentTransaction fragmentTransaction3=fragmentManager3.beginTransaction();
                            fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Payment_Request payment_req=new Payment_Request();
                            fragmentTransaction3.replace(R.id.fragmentcontent,payment_req).addToBackStack(null);
                            fragmentTransaction3.commit();
                    }
                }
            });
        }
        else
        {
            CustomListAdapter adapter=new CustomListAdapter(getActivity(),  itemlist);
            ListView list=(ListView)view.findViewById(R.id.listview1);



            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch(position)
                    {
                        case 0:
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Moneyorder moneyorder=new Moneyorder();
                            fragmentTransaction.replace(R.id.fragmentcontent,moneyorder).addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 1:

                            FragmentManager fragmentManager1=getFragmentManager();
                            FragmentTransaction fragmentTransaction1=fragmentManager1.beginTransaction();
                            fragmentTransaction1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            LoadMoney loadMoney=new LoadMoney();
                            fragmentTransaction1.replace(R.id.fragmentcontent,loadMoney).addToBackStack(null);
                            fragmentTransaction1.commit();
                            break;
                        case 2:
                            FragmentManager fragmentManager2=getFragmentManager();
                            FragmentTransaction fragmentTransaction2=fragmentManager2.beginTransaction();
                            fragmentTransaction2.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            PaymentTransfer balanceTransfer=new PaymentTransfer();
                            fragmentTransaction2.replace(R.id.fragmentcontent,balanceTransfer).addToBackStack(null);
                            fragmentTransaction2.commit();
                            break;
                        case 3:
                            FragmentManager fragmentManager3=getFragmentManager();
                            FragmentTransaction fragmentTransaction3=fragmentManager3.beginTransaction();
                            fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Payment_Request payment_req=new Payment_Request();
                            fragmentTransaction3.replace(R.id.fragmentcontent,payment_req).addToBackStack(null);
                            fragmentTransaction3.commit();
                    }
                }
            });
        }
        SharedPreferences sendMoney = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sendMoney.edit();
        String sSendMoney =sendMoney.getString(String_url.SendMoney,null);


        if(sSendMoney!=null) {
            if (sSendMoney.equals(String_url.SendMoneyDashBoard)) {
                FragmentManager fragmentManager3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                LoadMoney loadMoney = new LoadMoney();
                fragmentTransaction3.replace(R.id.fragmentcontent, loadMoney).addToBackStack(null);
                fragmentTransaction3.commit();
                editor.remove(String_url.SendMoney).commit();

            } else if (sSendMoney.equals(String_url.BalanceTransferDashBoard)) {
                FragmentManager fragmentManager3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                PaymentTransfer balanceTransfer = new PaymentTransfer();
                fragmentTransaction3.replace(R.id.fragmentcontent, balanceTransfer).addToBackStack(null);
                fragmentTransaction3.commit();
                editor.remove(String_url.SendMoney).commit();
            }
        }

        return view;

    }

}