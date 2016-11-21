package com.mom.app.retail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Settings_Prepaid_mobile_Response extends Fragment {
    private ImageView imageView;
    private TextView tvTransactionImageText,tv_prepared_mobile_history,tv_transaction_successfull,tv_transaction_id,tv_transaction_id1,
            tv_transaction_type,tv_transaction_type1,tv_mobile_no,tv_mobile_no1,tv_amount,tv_amount1,tv_transaction_time,tv_transaction_time1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings_prepaid_mobile_response,container, false);
        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Semibold);

        imageView=(ImageView)v.findViewById(R.id.img_image);
        imageView.setBackgroundResource(R.drawable.allscreen);

        tvTransactionImageText=(TextView)v.findViewById(R.id.tvTransactionImageText);
        tv_prepared_mobile_history=(TextView)v.findViewById(R.id.tv_prepared_mobile_history);
        tv_transaction_successfull=(TextView)v.findViewById(R.id.tv_transaction_successfull);
        tv_transaction_id=(TextView)v.findViewById(R.id.tv_transaction_id);
        tv_transaction_id1=(TextView)v.findViewById(R.id.tv_transaction_id1);
        tv_transaction_type=(TextView)v.findViewById(R.id.tv_transaction_type);
        tv_transaction_type1=(TextView)v.findViewById(R.id.tv_transaction_type1);
        tv_mobile_no=(TextView)v.findViewById(R.id.tv_mobile_no);
        tv_mobile_no1=(TextView)v.findViewById(R.id.tv_mobile_no1);
        tv_amount=(TextView)v.findViewById(R.id.tv_amount);
        tv_amount1=(TextView)v.findViewById(R.id.tv_amount1);
        tv_transaction_time=(TextView)v.findViewById(R.id.tv_transaction_time);
        tv_transaction_time1=(TextView)v.findViewById(R.id.tv_transaction_time1);

        tvTransactionImageText.setTypeface(Light);
        tv_prepared_mobile_history.setTypeface(Thin);
        tv_transaction_successfull.setTypeface(Condensed);
        tv_transaction_id.setTypeface(Thin);
        tv_transaction_id1.setTypeface(Thin);
        tv_transaction_type.setTypeface(Thin);
        tv_transaction_type1.setTypeface(Thin);
        tv_mobile_no.setTypeface(Thin);
        tv_mobile_no1.setTypeface(Thin);
        tv_amount.setTypeface(Thin);
        tv_amount1.setTypeface(Thin);
        tv_transaction_time.setTypeface(Thin);
        tv_transaction_time1.setTypeface(Thin);
        return v;
    }
}