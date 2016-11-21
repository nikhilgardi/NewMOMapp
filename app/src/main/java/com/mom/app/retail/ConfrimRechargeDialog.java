package com.mom.app.retail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ConfrimRechargeDialog{

    Context context;
    Activity activity;
   
    Dialog dialog;

    public ConfrimRechargeDialog(Context context, Activity activity) {
        this.context=context;
        this.activity=activity;
    }





    public void showDialog(int noOfRows,String Title,String MobNoText,String MobNoPara,String OprText,String OprPara,String AmntText,String AmntPara,View.OnClickListener yesListener,View.OnClickListener noListener )
    {

        Typeface Light = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Semibold);


        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confrim_recharge_dialog);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;


        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);

        ((ViewGroup)dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(context,R.anim.shake));



        TextView title,MobText,MobPara,oprtext,oprpara,amnttext,amntpara;
        Button btnok,btncancel;
        LinearLayout MobLayout,OprLayOut,AmntLayOut;


        title=(TextView)dialog.findViewById(R.id.dialogTitle);
        MobText=(TextView)dialog.findViewById(R.id.ServiceIDText);
        MobPara=(TextView)dialog.findViewById(R.id.serviceIDParameter);
        oprtext=(TextView)dialog.findViewById(R.id.operatorText);
        oprpara=(TextView)dialog.findViewById(R.id.operatorParameter);
        amnttext=(TextView)dialog.findViewById(R.id.amountText);
        amntpara=(TextView)dialog.findViewById(R.id.amountParameter);
        btnok=(Button)dialog.findViewById(R.id.btnOk);
        btncancel=(Button)dialog.findViewById(R.id.btnCancel);
        MobLayout=(LinearLayout)dialog.findViewById(R.id.MobLayout);
        OprLayOut=(LinearLayout)dialog.findViewById(R.id.operatorLayOut);
        AmntLayOut=(LinearLayout)dialog.findViewById(R.id.amountLayOut);


        title.setTypeface(Light);
        MobText.setTypeface(Light);
        MobPara.setTypeface(Light);
        oprtext.setTypeface(Light);
        oprpara.setTypeface(Light);
        amnttext.setTypeface(Light);
        amntpara.setTypeface(Light);
        btnok.setTypeface(Normal);
        btncancel.setTypeface(Normal);


        title.setText(Title);

        if(noOfRows==1)
        {
            if(Title.equals(context.getString(R.string.exit)) || Title.equals(context.getString(R.string.logout)) || Title.equals(context.getString(R.string.con_mpin_dialog)) || Title.equals(context.getString(R.string.con_tpin_dialog)))
            {
                btnok.setText(context.getString(R.string.yes));
                btncancel.setText(context.getString(R.string.no));
                MobPara.setVisibility(View.GONE);

            }

            MobText.setText(MobNoText);
            MobPara.setText(MobNoPara);
            OprLayOut.setVisibility(View.GONE);
            AmntLayOut.setVisibility(View.GONE);


        }
        if(noOfRows==2)
        {

            MobText.setText(MobNoText);
            MobPara.setText(MobNoPara);
            oprtext.setText(OprText);
            oprpara.setText(OprPara);
            AmntLayOut.setVisibility(View.GONE);

        }
        if(noOfRows==3)
        {
            MobText.setText(MobNoText);
            MobPara.setText(MobNoPara);
            oprtext.setText(OprText);
            oprpara.setText(OprPara);
            amnttext.setText(AmntText);
            amntpara.setText(AmntPara);
        }

        btnok.setOnClickListener(yesListener);
        btncancel.setOnClickListener(noListener);

        dialog.show();

    }

}
