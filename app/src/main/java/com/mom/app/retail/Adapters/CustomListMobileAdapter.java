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

public class CustomListMobileAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] imgid;
    private final String[] rs;

    public CustomListMobileAdapter(Activity context, String[] itemname, String[] imgid, String[] rs) {
        super(context, R.layout.customlist_mobileadapter, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.rs=rs;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlist_mobileadapter, null,true);
        Typeface Light = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Semibold);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.textView2);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textView3);


        txtTitle.setText(itemname[position]);
        txtTitle1.setText(imgid[position]);
        txtTitle2.setText(rs[position]);

        txtTitle.setTypeface(Condensed);
        txtTitle1.setTypeface(Thin);
        txtTitle2.setTypeface(Condensed);

        return rowView;

    };
}