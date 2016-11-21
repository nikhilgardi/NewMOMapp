package com.mom.app.retail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class ComProgressDialog {

    ProgressDialog progressDialog;

    public Context context;
    public Activity activity;

    public ComProgressDialog(Context context, Activity activity)
    {
        this.context=context;
        this.activity=activity;
    }

    public void showProgress()
    {
        progressDialog= new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();
    }

    public void cancelProgress(){
        progressDialog.cancel();
    }


}
