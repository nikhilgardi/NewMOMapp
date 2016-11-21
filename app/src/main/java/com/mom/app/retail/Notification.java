package com.mom.app.retail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mom.app.retail.Adapters.CustomListNotificationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification extends Fragment {
    private ImageView imageView;
    private TextView tv_NOtificationImageText,tv_notifications,tvDetailNotification;
    ListView list;
    private CustomListNotificationAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Map<String,Integer> mapPositonClick;
    ArrayList<Integer> listItem;
    List<String> itemname;
    Map<String,Integer> getNotificationlist;
    private static final String PREFS_NAME = "MyApp_Settings";
    SQLiteAdapter sql;
    private LinearLayout page1,page2;
    private Button btnNotificationDetailBack;





    List<String> itemlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification,container, false);
        //mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);

//        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
//        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
//        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);


        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);


        imageView=(ImageView)view.findViewById(R.id.img_image);
        imageView.setBackgroundResource(R.drawable.allscreen);
        tv_NOtificationImageText=(TextView)view.findViewById(R.id.tv_NOtificationImageText);
        tv_notifications=(TextView)view.findViewById(R.id.tv_notifications);
        tv_NOtificationImageText.setTypeface(Light);
        tv_notifications.setTypeface(Light);
        list=(ListView)view.findViewById(R.id.listview1);
        list.setEmptyView(view.findViewById(R.id.empty));

        page1=(LinearLayout)view.findViewById(R.id.page1);
        page2=(LinearLayout)view.findViewById(R.id.page2);
        tvDetailNotification=(TextView)view.findViewById(R.id.tvDetailNotification);
        tvDetailNotification.setTypeface(Light);


        btnNotificationDetailBack=(Button)view.findViewById(R.id.btnNotificationDetailBack);
        btnNotificationDetailBack.setTypeface(Light);
        btnNotificationDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.GONE);
            }
        });

        itemname = new ArrayList<String>();
        itemlist= new ArrayList<String>();



//        SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        String value             = shared.getString(String_url.notificationClass, "");
//        SharedPreferences.Editor editor = shared.edit();
//
//
//        boolean isFirstRun = shared.getBoolean("FIRSTRUN", true);

     //   if(value.equals("yes")) {

//            if(isFirstRun){
//            float balance=shared.getFloat(String_url.CustomerMainAccountBalance, 0);
//
//
//            itemname.add("Your Account Balance is ");
//            itemname.add("Experience the Shopping Bonanza!!! with our Gift Voucher Facility.Anywhere ,Anytime ");
//            itemname.add("TabCab drivers can pay their Daily rents in any MoneyOnMobile outlet.\n" +
//                    "Send Money with us to get FREE INSURANCE worth 1 LAC.\n" +
//                    "For more Information kindly visit our Website www.money-on-mobile.net ");
//
//            itemlist.add(String.valueOf(balance));
//            itemlist.add("");
//            itemlist.add("");
//
//
//
//           // editor.remove(String_url.notificationClass);
//            editor.putBoolean("FIRSTRUN", false);
//            editor.commit();
//
//        }
      //  else if()

        sql= new SQLiteAdapter(getActivity());
        sql.openToReadLoginDataBase();
        String [] NotificationList=sql.getContent();
        itemname = new ArrayList<String>(Arrays.asList(NotificationList));



//        for(int i=0;i<itemname.size();i++)
//        {
//            Log.e("itemname",itemname.get(i));
//
//
//        }
//
//        for(int i=0;i<NotificationList.length;i++)
//        {
//            Log.e("NotificationList",NotificationList[i]);
//
//
//        }
        sql.openToReadNotificationDataBase();
        getNotificationlist=new HashMap<>();
        getNotificationlist=sql.getContentKeyValue();

        final CustomListNotificationAdapter adapter=new CustomListNotificationAdapter(getActivity(), itemname);

        list.setAdapter(adapter);
        listItem= new ArrayList<>();


        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
               // String Slecteditem= itemname.get(+position);
                //Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();
             //   String s="position".concat(String.valueOf(position));

//                itemname.remove(position);
//                itemlist.remove(position);

           //     adapter.notifyDataSetChanged();

               // adapter.notifyDataSetChanged();

//                if(!listItem.isEmpty())
//                {
//                    if(position==listItem.get(position))
//                    {
//                        Toast.makeText(getActivity(),"Already Exist",Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(),"Clicked first time",Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else
//                {
//
//                    Toast.makeText(getActivity(),"list is empty",Toast.LENGTH_SHORT).show();
//
//
//                }
//                listItem.add(position);
//                Log.e("position",""+position);





//                if(mapPositonClick.get(s)== position)
//                {
//
//                    Toast.makeText(getActivity(),"Already available",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getActivity(),"First Time Click",Toast.LENGTH_SHORT).show();
//                }
//
//
//
//                mapPositonClick.put(s,position);

               // adapter.toggleItem(position);

                adapter.readNotification(position);
                showInDetail(position);



            }
        });

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshContent();
//            }
//        });
//        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);

        return view;

    }

//    private void refreshContent(){
//
//
//
//                    new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//                        @Override
//                        public void run() {
//                            try {
//
//
//                                adapter = new CustomListNotificationAdapter(getActivity(), itemname, itemname1);
//                                list = (ListView) getActivity().findViewById(R.id.listview1);
//                                list.setAdapter(adapter);
//                                mSwipeRefreshLayout.setRefreshing(false);
//
//                            }
//                            catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        }
//
//                        ,3000);
//                    }


        private void showInDetail(int position)
        {
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
            String detail=itemname.get(position);
            tvDetailNotification.setText(detail);


        }


}