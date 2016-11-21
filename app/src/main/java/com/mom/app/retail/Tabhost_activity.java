package com.mom.app.retail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Date;


public class Tabhost_activity extends ActionBarActivity   {

    private static final Integer SPLASH_TIME_OUT=1000;
    private static final String TAB_1_TAG = "Home";
    private static final String TAB_2_TAG = "Payment";
    private static final String TAB_3_TAG = "Send_money";
    private static final String TAB_4_TAG = "Gift";
    private static final String TAB_5_TAG = "Ticket";
    private static final String TAB_6_TAG = "Settings";

    public ProgressDialog progress;
    double customeraccntbalance;
    TextView textView1;
    Menu menurefresh;
    View viewmenu;
    private Context context;
    private long lastVisit;

    public static final String PREFS_NAME = "MyApp_Settings";

    public FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       
        getSupportActionBar().setTitle("");
         getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.actionbar_logo);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f1f1ef")));

     //   SharedPreferences auth_key=getApplicationContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
     //   String authentication_key=auth_key.getString(String_url.Authkey, "");
        //    Toast.makeText(getApplicationContext(),"authkey"+authentication_key,Toast.LENGTH_SHORT).show();


        InitView();

        mTabHost.setVisibility(View.GONE);
        Intent m=getIntent();
        int a=m.getIntExtra(String_url.position, 0);


        if(a!=0) {

            if (a == 1 || a == 2 || a == 3 || a == 4 || a == 5) {

                mTabHost.setCurrentTab(1);
                mTabHost.setVisibility(View.VISIBLE);
            } else if (a == 6) {
                mTabHost.setCurrentTab(2);
                mTabHost.setVisibility(View.VISIBLE);
            } else if (a == 11) {
                mTabHost.setCurrentTab(3);
                mTabHost.setVisibility(View.VISIBLE);

            } else if (a == 16 || a == 17 || a == 18) {
                // mTabHost.setCurrentTab(4);
                Toast.makeText(getApplicationContext(), "Ticket", Toast.LENGTH_SHORT).show();
            } else if (a == 26) {
                mTabHost.setCurrentTab(4);
                mTabHost.setVisibility(View.VISIBLE);
            }
            GetBalanceClass getBalanceClass= new GetBalanceClass(this,this);
            getBalanceClass.execute();
        }


        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#f26522")); // selected
        mTabHost.setSelected(true);

        NetworkConnection networkConnection=new NetworkConnection(getApplicationContext());
        boolean isNetworkAvailable=networkConnection.isNetworkAvailable();

        if(!isNetworkAvailable)
        {
            NetworkConnection.ExitAppDialog(Tabhost_activity.this);
        }

//        GetNotification getNotification = new GetNotification(getApplicationContext());
//        GetNotification.GetCustomerNotification getCustomerNotification = getNotification.new GetCustomerNotification();
//        getCustomerNotification.execute();



    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        lastVisit= new Date().getTime();
//        Log.e("lastVisit",""+lastVisit);
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    protected void onResume() {
//        long now = new Date().getTime();
//        Log.e("now",""+now);
//        if (now - lastVisit > 5000) {
//            Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
//            Log.e("LOGOUT","LOGOUT"+now);
//        }
//        super.onResume();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultBus.getInstance().postQueue(
                new ActivityResultEvent(requestCode, resultCode, data));
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    private void InitView(){

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fragmentcontent);




//        mTabHost.addTab(setIndicator(Tabhost_activity.this, mTabHost.newTabSpec(TAB_1_TAG),
//                R.drawable.home), Home.class, null);

        mTabHost.addTab(setIndicator(Tabhost_activity.this, mTabHost.newTabSpec(TAB_1_TAG),
                R.drawable.home), Home.class, null);

        mTabHost.addTab(setIndicator(Tabhost_activity.this, mTabHost.newTabSpec(TAB_2_TAG),
                R.drawable.payments), PaymentsTabHost.class, null);

        mTabHost.addTab(setIndicator(Tabhost_activity.this, mTabHost.newTabSpec(TAB_3_TAG),
                R.drawable.sendmoney), Sendmoney_listview.class, null);

        mTabHost.addTab(setIndicator(Tabhost_activity.this, mTabHost.newTabSpec(TAB_4_TAG),
                R.drawable.gift), Gift.class, null);

        mTabHost.addTab(setIndicator(Tabhost_activity.this, mTabHost.newTabSpec(TAB_6_TAG),
                R.drawable.settings), Setting.class, null);


        int currentTabNumber = mTabHost.getCurrentTab();






        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {


            public void onTabChanged(String arg0) {



                mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment1 = new Home();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        ft.replace(R.id.fragmentcontent, fragment1);
                        ft.addToBackStack(null);
                        ft.commit();
                        //  mTabHost.setCurrentTab(0);



//                        mTabHost.getTabWidget().getChildAt(0)
//                                .setBackgroundColor(Color.parseColor("#f26522"));
//                        mTabHost.getTabWidget().getChildAt(1)
//                                .setBackgroundColor(Color.parseColor("#0e2f63"));
//                        mTabHost.getTabWidget().getChildAt(2)
//                                .setBackgroundColor(Color.parseColor("#0e2f63"));
//                        mTabHost.getTabWidget().getChildAt(3)
//                                .setBackgroundColor(Color.parseColor("#0e2f63"));
//                        mTabHost.getTabWidget().getChildAt(4)
//                                .setBackgroundColor(Color.parseColor("#0e2f63"));

                        mTabHost.setVisibility(View.GONE);
                        GetBalanceClass getBalanceClass = new GetBalanceClass(getApplicationContext(), Tabhost_activity.this);
                        getBalanceClass.execute();

                    }

                });


                mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mTabHost.setVisibility(View.VISIBLE);

                        Fragment fragment1 = new PaymentsTabHost();

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        ft.replace(R.id.fragmentcontent, fragment1);
                        ft.addToBackStack(null);
                        ft.commit();
                        mTabHost.setCurrentTab(1);


                        mTabHost.getTabWidget().getChildAt(0)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));
                        mTabHost.getTabWidget().getChildAt(1)
                                .setBackgroundColor(Color.parseColor("#f26522"));
                        mTabHost.getTabWidget().getChildAt(2)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));
                        mTabHost.getTabWidget().getChildAt(3)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));
                        mTabHost.getTabWidget().getChildAt(4)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));
                        GetBalanceClass getBalanceClass = new GetBalanceClass(getApplicationContext(), Tabhost_activity.this);
                        getBalanceClass.execute();
                    }


                });


                mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mTabHost.setVisibility(View.VISIBLE);
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentcontent);
                        if (fragment instanceof PaymentTransfer || fragment instanceof LoadMoney || fragment instanceof Moneyorder || fragment instanceof Payment_Request) {

                            Fragment fragment1 = new Sendmoney_listview();

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            ft.replace(R.id.fragmentcontent, fragment1);
                            ft.addToBackStack(null);
                            ft.commit();

                        } else {

                            Fragment fragment1 = new Sendmoney_listview();

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            ft.replace(R.id.fragmentcontent, fragment1);
                            ft.addToBackStack(null);
                            ft.commit();

                            //   mTabHost.setCurrentTab(2);
                            mTabHost.getTabWidget().getChildAt(0)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));
                            mTabHost.getTabWidget().getChildAt(1)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));
                            mTabHost.getTabWidget().getChildAt(2)
                                    .setBackgroundColor(Color.parseColor("#f26522"));
                            mTabHost.getTabWidget().getChildAt(3)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));
                            mTabHost.getTabWidget().getChildAt(4)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));

                            GetBalanceClass getBalanceClass = new GetBalanceClass(getApplicationContext(), Tabhost_activity.this);
                            getBalanceClass.execute();
                        }


                    }
                });




                mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mTabHost.setVisibility(View.VISIBLE);
                        Fragment fragment1 = new Gift();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        ft.replace(R.id.fragmentcontent, fragment1);
                        ft.addToBackStack(null);
                        ft.commit();
                        //  mTabHost.setCurrentTab(3);

                        mTabHost.getTabWidget().getChildAt(0)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));
                        mTabHost.getTabWidget().getChildAt(1)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));
                        mTabHost.getTabWidget().getChildAt(2)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));
                        mTabHost.getTabWidget().getChildAt(3)
                                .setBackgroundColor(Color.parseColor("#f26522"));
                        mTabHost.getTabWidget().getChildAt(4)
                                .setBackgroundColor(Color.parseColor("#0e2f63"));

                        GetBalanceClass getBalanceClass = new GetBalanceClass(getApplicationContext(), Tabhost_activity.this);
                        getBalanceClass.execute();
                    }
                });


                mTabHost.getTabWidget().getChildAt(4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mTabHost.setVisibility(View.VISIBLE);
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentcontent);
                        if (fragment instanceof Change_MPIN || fragment instanceof Change_TPIN || fragment instanceof Notification || fragment instanceof Contact_us || fragment
                                instanceof Help || fragment instanceof Book_complaint || fragment instanceof Payment_history) {
                            Fragment fragment1 = new Setting();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            ft.replace(R.id.fragmentcontent, fragment1);
                            ft.addToBackStack(null);
                            ft.commit();

                            GetBalanceClass getBalanceClass = new GetBalanceClass(getApplicationContext(), Tabhost_activity.this);
                            getBalanceClass.execute();

                        } else {

                            Fragment fragment1 = new Setting();

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            ft.replace(R.id.fragmentcontent, fragment1);
                            ft.addToBackStack(null);
                            ft.commit();
                            //  mTabHost.setCurrentTab(4);

                            mTabHost.getTabWidget().getChildAt(0)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));
                            mTabHost.getTabWidget().getChildAt(1)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));
                            mTabHost.getTabWidget().getChildAt(2)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));
                            mTabHost.getTabWidget().getChildAt(3)
                                    .setBackgroundColor(Color.parseColor("#0e2f63"));
                            mTabHost.getTabWidget().getChildAt(4)
                                    .setBackgroundColor(Color.parseColor("#f26522"));

                            GetBalanceClass getBalanceClass = new GetBalanceClass(getApplicationContext(), Tabhost_activity.this);
                            getBalanceClass.execute();
                        }

                    }
                });


//                mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //    Toast.makeText(getApplicationContext(),"first",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//                        i.putExtra("position", 1);
//
//                        startActivity(i);
//                        overridePendingTransition(0, 0);
//                        finish();
//
//
//                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//
//                        // Writing data to SharedPreferences
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString("key", "one");
//                        editor.commit();
//                    }
//                });
//
//                mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        //   Toast.makeText(getApplicationContext(),"zero",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//                        startActivity(i);
//                        overridePendingTransition(0, 0);
//                        finish();
//                    }
//
//                });
//
//                mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //  Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//                        i.putExtra("position", 6);
//
//                        startActivity(i);
//                        overridePendingTransition(0, 0);
//                        finish();
//                    }
//                });
//
//                mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Toast.makeText(getApplicationContext(),"third",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//                        i.putExtra("position", 11);
//
//                        startActivity(i);
//                        overridePendingTransition(0, 0);
//                        finish();
//                    }
//                });
//
//
//
//                mTabHost.getTabWidget().getChildAt(4).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Toast.makeText(getApplicationContext(),"fourth",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//                        i.putExtra("position", 26);
//
//                        startActivity(i);
//                        overridePendingTransition(0, 0);
//                        finish();
//                    }
//                });


                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

                    mTabHost.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#0e2f63")); // unselected
                }
                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#f26522")); // selected


            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.tabhost_menu, menu);


        menurefresh=menu;

        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge);
        viewmenu = MenuItemCompat.getActionView(item);

        ImageView imageView=(ImageView)viewmenu.findViewById(R.id.hotlist_bell);
        TextView textView=(TextView)viewmenu.findViewById(R.id.hotlist_hot);
        imageView.setBackgroundResource(R.drawable.alert);

        textView1=(TextView)viewmenu.findViewById(R.id.tvActionBalance);
        Typeface Condensed = Typeface.createFromAsset(getApplicationContext().getAssets(), String_url.OpenSans_Semibold);
        Typeface Thin = Typeface.createFromAsset(getApplicationContext().getAssets(), String_url.OpenSans_Normal);
        Typeface Light = Typeface.createFromAsset(getApplicationContext().getAssets(), String_url.OpenSans_Light);

        //set font for balance
        textView1.setTypeface(Light);
        SQLiteAdapter sql= new SQLiteAdapter(this);
        sql.openToReadBalanceDataBase();
        customeraccntbalance =sql.getBalanceFromDB();
        sql.close();


        DecimalFormat precision = new DecimalFormat("0.00");

        String dummy_value="10,00,000";

        String balance=getString(R.string.Balance).concat(getString(R.string.Rs)).concat(String.valueOf(precision.format(customeraccntbalance)));


        textView1.setText(balance);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String firstTime                    = sharedPreferences.getString(String_url.NotificationCount, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SQLiteAdapter sqLiteAdapter= new SQLiteAdapter(this);
        sqLiteAdapter.openToReadNotificationDataBase();
       // int count=sqLiteAdapter.getCountValue();
        int count=sqLiteAdapter.getUnReadCountValue();
        if (firstTime.equals("yes")) {

            if(count==0)
            {
                textView.setVisibility(View.GONE);

            }
            else {
                textView.setText(String.valueOf(count));
            }

            textView.setText(String.valueOf(count));
            editor.remove(String_url.NotificationCount);
            editor.commit();

//
//            SQLiteAdapter sql = new SQLiteAdapter(Tabhost_activity.this);
//            sql.openToReadLoginDataBase();
//            String sDevicePin = sql.getDevicePin();
//
//
//            if(sDevicePin!=null)
//            {
//                int count=sharedPreferences.getInt(String_url.lastNotificationCount,-1);
//
//                if(count==-1)
//                {
//                    textView.setText("3");
//                }
//                else {
//                    textView.setText(String.valueOf(count));
//                }
//            }
//            else {
//                textView.setText("3");
//            }
        }

        else {
            int unReadNewCount=sqLiteAdapter.getUnReadCountValue();
            sqLiteAdapter.close();

            if(unReadNewCount==0)
            {
                textView.setVisibility(View.GONE);

            }
            else {
                textView.setText(String.valueOf(unReadNewCount));
            }
        }




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    mTabHost.setVisibility(View.VISIBLE);
                    mTabHost.setCurrentTab(4);
                    FragmentManager fragmentManager3 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                    fragmentTransaction3.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    Notification ph = new Notification();
                    fragmentTransaction3.replace(R.id.fragmentcontent, ph).addToBackStack(null);
                    fragmentTransaction3.commit();

                    mTabHost.getTabWidget().getChildAt(0)
                            .setBackgroundColor(Color.parseColor("#0e2f63"));
                    mTabHost.getTabWidget().getChildAt(1)
                            .setBackgroundColor(Color.parseColor("#0e2f63"));
                    mTabHost.getTabWidget().getChildAt(2)
                            .setBackgroundColor(Color.parseColor("#0e2f63"));
                    mTabHost.getTabWidget().getChildAt(3)
                            .setBackgroundColor(Color.parseColor("#0e2f63"));
                    mTabHost.getTabWidget().getChildAt(4)
                            .setBackgroundColor(Color.parseColor("#f26522"));



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            // Toast.makeText(getApplicationContext(),"Badge",Toast.LENGTH_SHORT).show();
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){

        new android.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.logout)
                .setTitle(getString(R.string.exit))
                .setMessage(getString(R.string.exit_question))
                .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();

                        Intent intent=new Intent(Tabhost_activity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.No), null)
                .show();

//        final ConfrimRechargeDialog confrimRechargeDialog= new ConfrimRechargeDialog(Tabhost_activity.this,Tabhost_activity.this);
//        confrimRechargeDialog.showDialog(1,
//                getString(R.string.exit),
//                getString(R.string.exit_question),
//                null, null, null, null, null,
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        confrimRechargeDialog.dialog.dismiss();
//                        finish();
//                    }
//                },
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        confrimRechargeDialog.dialog.dismiss();
//                    }
//                }
//
//        );

    }

    private TabHost.TabSpec setIndicator(Context ctx, TabHost.TabSpec spec, int genresIcon) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.tabhost_tab_image, null);
        v.setBackgroundColor(Color.parseColor("#0e2f63"));

        ImageView img = (ImageView)v.findViewById(R.id.img_tabtxt);
        img.setBackgroundResource(genresIcon);

        return spec.setIndicator(v);
    }

    @Override
    public void onBackPressed() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        int a= settings.getInt("onPageSelected",0);


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentcontent);

        // Toast.makeText(getApplicationContext(),"onBackPressed"+fragment,Toast.LENGTH_SHORT).show();
        if (fragment instanceof Home) {
            logout();

        }

//        if(fragment instanceof PaymentsTabHost && a==0)
//        {
////            Toast.makeText(getApplicationContext(),"onPageSelected"+a,Toast.LENGTH_SHORT).show();
// //           mTabHost.setCurrentTab(1);
//            Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//            startActivity(i);
//            overridePendingTransition(0, 0);
//            finish();
//            editor.putInt(String_url.position, 1);
//            editor.putString(String_url.key, String_url.one);
//            editor.commit();
//
//        }
//
//    else    if(fragment instanceof PaymentsTabHost && a==1)
//        {
//   //         Toast.makeText(getApplicationContext(),"onPageSelected"+a,Toast.LENGTH_SHORT).show();
// //           mTabHost.setCurrentTab(1);
//            Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//            startActivity(i);
//            overridePendingTransition(0, 0);
//            finish();
//            editor.putInt(String_url.position, 1);
//            editor.putString(String_url.key, String_url.two);
//            editor.commit();
//        }
//
//    else    if(fragment instanceof PaymentsTabHost && a==2)
//        {
//       //     Toast.makeText(getApplicationContext(),"onPageSelected"+a,Toast.LENGTH_SHORT).show();
//  //          mTabHost.setCurrentTab(1);
//            Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//            startActivity(i);
//            overridePendingTransition(0, 0);
//            finish();
//            editor.putInt(String_url.position, 1);
//            editor.putString(String_url.key, String_url.three);
//            editor.commit();
//        }
//     else   if(fragment instanceof PaymentsTabHost && a==3)
//        {
//     //       Toast.makeText(getApplicationContext(),"onPageSelected"+a,Toast.LENGTH_SHORT).show();
//  //          mTabHost.setCurrentTab(1);
//            Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
//            startActivity(i);
//            overridePendingTransition(0, 0);
//            finish();
//            editor.putInt(String_url.position, 1);
//            editor.putString(String_url.key, String_url.four);
//            editor.commit();
//        }
//      //

        else if (fragment instanceof PaymentsTabHost || fragment instanceof Gift ) {

            Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
            startActivity(i);
            overridePendingTransition(0, 0);
            finish();

        }
        else if (fragment instanceof Change_MPIN ||  fragment instanceof Change_TPIN || fragment instanceof Notification || fragment instanceof Contact_us || fragment
                instanceof Help || fragment instanceof Book_complaint || fragment instanceof Payment_history) {

            Intent ii = new Intent(getApplicationContext(), Tabhost_activity.class);
            ii.putExtra(String_url.position, 26);
            startActivity(ii);overridePendingTransition(0, 0);
            finish();


        }
        else if(fragment instanceof Settings_Prepaid_mobileDetailList || fragment instanceof Settings_Prepaid_mobile_Response)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            Payment_history fm=new Payment_history();
            fragmentTransaction.replace(R.id.fragmentcontent,fm).addToBackStack(null);
            fragmentTransaction.commit();
        }

        else if(fragment instanceof Setting)
        {
            //  Toast.makeText(getApplicationContext(),"Toast",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
            startActivity(i);overridePendingTransition(0, 0);
            finish();
        }
        else if(fragment instanceof Sendmoney_listview)
        {
            // Toast.makeText(getApplicationContext(),"Toast",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Tabhost_activity.class);
            startActivity(i);overridePendingTransition(0, 0);
            finish();
        }
        else if(fragment instanceof PaymentTransfer ||fragment instanceof LoadMoney||fragment instanceof Moneyorder||fragment instanceof Payment_Request)
        {
            Intent ii = new Intent(getApplicationContext(), Tabhost_activity.class);
            ii.putExtra(String_url.position, 6);
            startActivity(ii);overridePendingTransition(0,0);
            finish();
        }
    }

}

