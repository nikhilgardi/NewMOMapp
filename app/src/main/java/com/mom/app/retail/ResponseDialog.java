package com.mom.app.retail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ResponseDialog {

    Context context;
    Activity activity;
   
    Dialog dialog;
    LinearLayout service;

    public ResponseDialog(Context context, Activity activity) {
         this.context=context;
        this.activity=activity;

    }

    public void showResponseDialog(int noOfRows,String Title,String MessageText,String MessagePara,String TransactionText,
                                   String TransactionPara,String OneLineResponse ,View.OnClickListener yesListener)
    {

        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Semibold);


        dialog = new Dialog(context);
        //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(Title);
        dialog.setContentView(R.layout.response_dialog);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);

        TextView responseText,responsepara,isoTransactiontext,isoTransactionpara,tvOneLineResposne;
        Button btnok;
        LinearLayout layout1,layout2,layout3;

        layout1=(LinearLayout)dialog.findViewById(R.id.Layout1);
        layout2=(LinearLayout)dialog.findViewById(R.id.Layout2);
        layout3=(LinearLayout)dialog.findViewById(R.id.Layout3);

        responseText=(TextView)dialog.findViewById(R.id.tvResponseText);
        responsepara=(TextView)dialog.findViewById(R.id.tvResponseParameter);
        isoTransactiontext = (TextView)dialog.findViewById(R.id.tvTransactionText);
        isoTransactionpara=(TextView)dialog.findViewById(R.id.tvTranasactionParameter);
        tvOneLineResposne=(TextView)dialog.findViewById(R.id.tvOneLineResponse);


        btnok=(Button)dialog.findViewById(R.id.btnOk);


        if(noOfRows==1)
        {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            tvOneLineResposne.setText(OneLineResponse);
            tvOneLineResposne.setTypeface(Condensed);
            btnok.setText(context.getString(R.string.Ok));
        }

        if(noOfRows==2)
        {
            layout3.setVisibility(View.GONE);
            responseText.setText(MessageText);
            responsepara.setText(MessagePara);
            isoTransactiontext.setText(TransactionText);
            isoTransactionpara.setText(TransactionPara);

            responseText.setTypeface(Condensed);
            responsepara.setTypeface(Condensed);
            isoTransactiontext.setTypeface(Condensed);
            isoTransactionpara.setTypeface(Condensed);

        }

        if(Title.equals(context.getString(R.string.MobilePayment)) ||
                Title.equals(context.getString(R.string.MobileBillPayment)) ||
                Title.equals(context.getString(R.string.DTHPayment)))
        {
            btnok.setText(context.getString(R.string.NewRecharge));
        }

        btnok.setOnClickListener(yesListener);
        dialog.show();


    }



}
