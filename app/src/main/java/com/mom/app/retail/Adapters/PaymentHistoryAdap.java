package com.mom.app.retail.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mom.app.retail.R;
import com.mom.app.retail.String_url;

import java.util.List;

/**
 * Created by Avinashm on 10-May-16.
 */

public class PaymentHistoryAdap extends ArrayAdapter<String> {

    //    private final Activity context;
//
//    private final List<String> setting;
    private final Activity context;
    private final List<String> itemname;
    private final List<String> imgid;
    private final List<String> rs;
    private final List<String> ServiceProviderTypeName_list;

    public PaymentHistoryAdap(Activity context, List<String> itemname, List<String> imgid, List<String> rs,List<String> ServiceProviderTypeName_list) {
        super(context, R.layout.customlist_mobileadapter, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.rs=rs;
        this.ServiceProviderTypeName_list=ServiceProviderTypeName_list;
    }

//    public PaymentHistoryAdap(Activity context, List<String> setting) {
//        super(context, R.layout.customlistadapter_layout_list, setting);
//        // TODO Auto-generated constructor stub
//
//        this.context=context;
//        this.setting=setting;
//    }

//    public View getView(int position,View view,ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.customlistadapter_layout_list, null,true);
//        Typeface Light = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
//        Typeface Thin = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Thin.ttf");
//        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Condensed.ttf");
//
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
//
//        txtTitle.setText(setting.get(position));txtTitle.setTypeface(Condensed);
//
//
//
//
//        return rowView;
//
//    };

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlist_mobileadapter, null,true);

        Typeface Light = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Semibold);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.textView2);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textView3);
        TextView txtTitle3=(TextView) rowView.findViewById(R.id.textView4);


        txtTitle.setText(itemname.get(position));

        if(imgid.get(position).toLowerCase().equals("success"))
        {
            //green
            //   txtTitle1.setTextColor(Color.parseColor("#FF20C220"));
            //txtTitle1.setTextColor(Color.GREEN);

            txtTitle1.setTextColor(Color.parseColor("#008513"));
        }
        if(imgid.get(position).toLowerCase().equals("pending"))
        {
            //yellow
            txtTitle1.setTextColor(Color.parseColor("#f59c1a"));
        }
        if(imgid.get(position).toLowerCase().equals("failed"))
        {
            //red
            txtTitle1.setTextColor(Color.parseColor("#ff5b57"));
        }
        txtTitle1.setText(imgid.get(position));
        txtTitle2.setText(rs.get(position));
        txtTitle3.setText(ServiceProviderTypeName_list.get(position));

        txtTitle.setTypeface(Condensed);
        txtTitle1.setTypeface(Thin);
        txtTitle2.setTypeface(Condensed);
        txtTitle3.setTypeface(Thin);

        return rowView;

    };
}