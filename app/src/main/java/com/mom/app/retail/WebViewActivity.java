package com.mom.app.retail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends ActionBarActivity {

    private WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        setTitle(getString(R.string.loadMoney));
        wv1               =(WebView) findViewById(R.id.pageInfo);

        Intent intent = getIntent();

        String sName = intent.getStringExtra(String_url.PARAM_NEW_STR_NAME);
        String sEmail = intent.getStringExtra(String_url.PARAM_NEW_STR_EMAIL);

        String sAmount = intent.getStringExtra(String_url.PARAM_NEW_STR_AMOUNT);
        Long sMobNo    = intent.getLongExtra(String_url.PARAM_NEW_STR_MOBILE_NUMBER_PAY_U,0);

        try {
            //String url = "http://utilities.money-on-mobile.net/payu_web/UpdateWallet.aspx?Name=" + sName + "&Mobile=" + sMobNo + "&Amount=" + sAmount + "&Email=" + sEmail + "";
            String url = "http://192.168.11.10/PayU/UpdateWallet.aspx?Name=" + sName + "&Mobile=" + sMobNo + "&Amount=" + sAmount + "&Email=" + sEmail + "";
            wv1.getSettings().setLoadsImagesAutomatically(true);
            wv1.getSettings().setJavaScriptEnabled(true);
            wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv1.setWebViewClient(new WebViewSampleClient());
            wv1.loadUrl(url);
        }
        catch(Exception ex){
            Log.e("Error", ex.toString());


        }
    }

    private class WebViewSampleClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.e("ErrorLog", " Error occured while loading the web page at Url");
           // Toast.makeText(WebViewActivity.this, "Error occured,please check Network connectivity", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_view, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
