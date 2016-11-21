package com.mom.app.retail.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mom.app.retail.R;
import com.mom.app.retail.SQLiteAdapter;
import com.mom.app.retail.String_url;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomListNotificationAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemname;

    private SparseBooleanArray enabledItems = new SparseBooleanArray();




    private static final String PREFS_NAME = "MyApp_Settings";
    Map<String,Integer> getNotificationlist,UpdatedReadValues;

    public CustomListNotificationAdapter(Activity context,List<String> itemname) {
        super(context, R.layout.customlist_notification_adapter, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlist_notification_adapter,null, true);

        Typeface Light = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Semibold);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);

        if(itemname!=null ) {
            txtTitle.setText(itemname.get(position));
            txtTitle.setTypeface(Condensed);


        }
//        if(!isEnabled(position)) {
//        /* change to disabled appearance */
//           //Toast.makeText(context,"Cannot click twice",Toast.LENGTH_SHORT).show();
//
//            rowView.setBackgroundColor(Color.parseColor("#ececec"));
//            Log.e("Cannot click twice", "Cannot click twice");
//
//
//
//
//
//        }
//        else
//        {
//        /* restore default appearance */
//         // Toast.makeText(context,"clicked first time",Toast.LENGTH_SHORT).show();
//           // view.setBackgroundColor(context.getResources().getColor(R.color.HeaderGray));
//
//            ///Log.e("clicked first time", "clicked first time");
//
//            rowView.setBackgroundColor(Color.parseColor("#FF67C1C2"));
//
//        }



        SQLiteAdapter sqLiteAdapter= new SQLiteAdapter(context);
        sqLiteAdapter.openToReadNotificationDataBase();
        int [] ReadArray=sqLiteAdapter.getReadStatusID();
        getNotificationlist=sqLiteAdapter.getContentKeyValue();


//        if(ReadArray.length!=0) {
//            Log.e("ReadArray!=0", "ReadArray.length!=0");
//            for (int i = 0; i < ReadArray.length; i++) {
//
//                for (int j = 1; j < getNotificationlist.size(); j++) {
//
//                    assert itemname != null;
//                    if(ReadArray[i] == getNotificationlist.get(itemname.get(j)))
//                    {
//                        rowView.setBackgroundColor(Color.parseColor("#f26522"));
//                        Log.e("ReadArray", "" + ReadArray[i]);
//                        Log.e("getNotificationlist",""+getNotificationlist.get(itemname.get(j)));
//                    }
//
//                    else{
//                        rowView.setBackgroundColor(Color.parseColor("#ececec"));
//
//                        Log.e("ReadArrayELSE", "" + ReadArray[i]);
//                        Log.e("getNotificationlistELSE", "" + getNotificationlist.get(itemname.get(j)));
//                    }
//
//                }
//
//            }
//        }


//        if(ReadArray.length!=0) {
//            Log.e("ReadArray!=0", "ReadArray.length!=0");
//            for (int i = 0; i <getNotificationlist.size(); i++) {
//
//                for (int j = 1; j <  ReadArray.length; j++) {
//
//                    assert itemname != null;
//                    if(ReadArray[j] == getNotificationlist.get(itemname.get(i)))
//                    {
//                        rowView.setBackgroundColor(Color.parseColor("#f26522"));
//                        Log.e("ReadArray", "" + ReadArray[i]);
//                        Log.e("getNotificationlist",""+getNotificationlist.get(itemname.get(j)));
//
//                    }
//
//                    else{
//                        rowView.setBackgroundColor(Color.parseColor("#ececec"));
//
//                        Log.e("ReadArrayELSE", "" + ReadArray[i]);
//                        Log.e("getNotificationlistELSE", "" + getNotificationlist.get(itemname.get(j)));
//                    }
//
//                }
//
//            }
//        }



        for(int i=0;i<ReadArray.length;i++)
        {
            Log.e("ReadArray",""+ReadArray[i]);
            assert itemname != null;
            if(ReadArray[i]== getNotificationlist.get(itemname.get(position)))
            {

                rowView.setBackgroundColor(Color.parseColor("#ececec"));
                Log.e("--IF--", "" + ReadArray[i] + "--IF--"+getNotificationlist.get(itemname.get(position)));
              //  Log.e("getNotificationlist", "" + getNotificationlist.get(itemname.get(position)));
            }
//            else {
//                rowView.setBackgroundColor(Color.parseColor("#ececec"));
//                    Log.e("--ELSE---", "" + ReadArray[i] + "--ELSE---"+ getNotificationlist.get(itemname.get(position)));
//                   // Log.e("getNotificationlistELSE", "" + getNotificationlist.get(itemname.get(position)));
//              }

        }



        return rowView;

    }

//    @Override
//    public boolean areAllItemsEnabled() {
//     //   return super.areAllItemsEnabled();
//        return false;
//
//    }
//
//    @Override
//    public boolean isEnabled(int position) {
////        return super.isEnabled(position,true);
//       return enabledItems.get(position, true);
//    }
//
//    public void toggleItem(int position) {
////        boolean state = enabledItems.get(position, true);
////        enabledItems.put(position, !state);
//        //updateNotificationCount.decreaseCount();
//
//        itemname.remove(position);
//        decreaseCount();
//        notifyDataSetChanged();
//        Log.e("Notification", "" + getNotificationlist.get(itemname.get(position)));
//
//
//
//    }
//
//    public void  decreaseCount(){
//
//        count--;
//
//        SharedPreferences shared = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = shared.edit();
//        editor.putInt(String_url.lastNotificationCount, count);
//        Log.e("decreaseCount", "" + count);
//        editor.commit();
//        TextView textView=(TextView)context.findViewById(R.id.hotlist_hot);
//        textView.setText(String.valueOf(count));
//    }

    public void readNotification(int position)
    {
//        boolean state = enabledItems.get(position, true);
//        enabledItems.put(position, !state);

        SQLiteAdapter sqLiteAdapter= new SQLiteAdapter(context);
        getNotificationlist= new HashMap<>();
        sqLiteAdapter.openToReadNotificationDataBase();
        getNotificationlist=sqLiteAdapter.getContentKeyValue();
        int NotificationID = getNotificationlist.get(itemname.get(position));
        Log.e("Notification", "" + getNotificationlist.get(itemname.get(position)));

        sqLiteAdapter.openToWriteNotificationDataBase();
        sqLiteAdapter.updateStatus(NotificationID);
        int newCount=sqLiteAdapter.getUnReadCountValue();
        sqLiteAdapter.close();
        Log.e("NotificationnewCount", "" + newCount);
        TextView textView=(TextView)context.findViewById(R.id.hotlist_hot);

        if(newCount==0)
        {
            textView.setVisibility(View.GONE);

        }
        else {
            textView.setText(String.valueOf(newCount));
        }
       // updateList(position);
        notifyDataSetChanged();

    }



}