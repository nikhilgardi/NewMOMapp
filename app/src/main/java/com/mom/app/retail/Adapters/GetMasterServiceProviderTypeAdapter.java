package com.mom.app.retail.Adapters;

/**
 * Created by nikhilg on 11/9/2016.
 */

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

public class GetMasterServiceProviderTypeAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final List<String> setting;

    public GetMasterServiceProviderTypeAdapter(Activity context, List<String> setting) {
        super(context, R.layout.customlistadapter_layout_list, setting);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.setting=setting;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();


        View rowView=inflater.inflate(R.layout.customlistadapter_layout_list, null,true);
//        Typeface Light = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Light);
//        Typeface Thin = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Normal);
//        Typeface Condensed = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Semibold);

        Typeface Light = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Semibold);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);

        // txtTitle.setText(setting[position]);




        txtTitle.setText(setting.get(position));
        txtTitle.setTypeface(Light);

        return rowView;

    };
}
