package com.mom.app.retail.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mom.app.retail.R;


public class GridViewAdap extends BaseAdapter {


    Context context;

    View v;
    public GridViewAdap(Context cc)
    {
        context=cc;

    }
    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Condensed.ttf");
        TextView textView;
         String[] mThumbId={
                 "PAYMENTS","","","","",
                 "MONEY TRANSFER","","","","",
                 "GIFT VOUCHERS","","","","",
                 "TICKETS","","","","",
                 "BALANCE TRANSFER","","","","",
                 "SETTINGS","","","",""





         };


        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.gridtext, parent, false);

        }
        else {
            v = convertView;
        }
        textView=(TextView)v.findViewById(R.id.gridtext1);
        textView.setGravity(Gravity.CENTER);


        if(position==7 || position==8 ||position==9
                || position==12 ||position==13 ||position==14 ||position==19
                || position==22 ||position==23 ||position==24 ||position==27 || position==28 ||position==29){
            textView.setBackgroundResource(R.drawable.transparent);

        }

        if(position==0 ||position==5 ||position==10 || position==15 ||position==20 || position==25)
        {
            textView.setBackgroundResource(R.drawable.transparent);
            textView.setTextSize(13);
            textView.setTypeface(Condensed);

        }
        if(position==1){
            textView.setBackgroundResource(R.drawable.mobilepayment);
        }
        if(position==2 ){
            textView.setBackgroundResource(R.drawable.dthpayment);
        }
        if(position==3){
            textView.setBackgroundResource(R.drawable.billpayment);
        }
        if((position==4)) {
            textView.setBackgroundResource(R.drawable.licpayment);
        }
        if(position==6 || position==21){
            textView.setBackgroundResource(R.drawable.moneytransfer);
        }
        if(position==11){
            textView.setBackgroundResource(R.drawable.dash_gift);
        }
        if(position==16){
            textView.setBackgroundResource(R.drawable.bus);
        }
        if(position==17){
            textView.setBackgroundResource(R.drawable.train);
        }
        if(position==18){
            textView.setBackgroundResource(R.drawable.airline);
        }
        if(position==26 ){
            textView.setBackgroundResource(R.drawable.dash_settings);
        }




        textView.setText(mThumbId[position]);


        return v;

    }




    @Override
    public boolean isEnabled(int position) {
        if(position==0 ||position==5 ||position==10 || position==15 ||position==20
                ||position==7 || position==8 || position==9 || position==12
        || position==13 ||position==14 ||position==19 || position==22 ||position==23 || position==24
                ||position==25 ||position==27 || position==28 ||position==29 )
            return false;
        else
            return true;
    }
}

