package com.mom.app.retail.Adapters;

import android.app.Activity;
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
 * Created by nikhilg on 7/22/2016.
 */
public class BrowseplaneAdap extends ArrayAdapter<String> {

    //    private final Activity context;
//
//    private final List<String> setting;
    private final Activity context;
    private final List<String> planMRP_List;
    private final List<String> planDescription_List;
    private final List<String> planValidity_List;
    String roundedMrp;

    public BrowseplaneAdap(Activity context, List<String> planMRP_List, List<String> planDescription_List, List<String> planValidity_List) {
        super(context, R.layout.item, planMRP_List);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.planMRP_List=planMRP_List;
        this.planDescription_List=planDescription_List;
        this.planValidity_List=planValidity_List;
    }



    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item, null,true);

        Typeface Light = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Semibold);

        TextView amount = (TextView) rowView.findViewById(R.id.amount);
        TextView validity = (TextView) rowView.findViewById(R.id.validity);
        TextView talktime = (TextView) rowView.findViewById(R.id.talktime);
        TextView tvDescr=(TextView)rowView.findViewById(R.id.tvDescr);

        String[] split = planMRP_List.get(position).split("\\.");
        roundedMrp = split[0];
        String secondSubString = split[1];

        amount.setText(roundedMrp);
        tvDescr.setText(planDescription_List.get(position));

        if((planValidity_List.get(position).equals(""))||(planValidity_List.get(position).equals(null)))
        {
            validity.setText(R.string.na);
            talktime.setText(R.string.na);
        }
        else
        {
            validity.setText(planValidity_List.get(position));
            talktime.setText(planValidity_List.get(position));
        }

        amount.setTypeface(Condensed);
        validity.setTypeface(Thin);
        talktime.setTypeface(Condensed);
        tvDescr.setTypeface(Condensed);

        return rowView;

    };
}
