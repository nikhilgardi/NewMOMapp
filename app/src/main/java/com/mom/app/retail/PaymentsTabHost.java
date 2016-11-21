package com.mom.app.retail;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;


public class PaymentsTabHost extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    Context thiscontext;
    private TabHost mTabHost;
    public ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    private PaymentsTabPagerAdapter mPagerAdapter;
    private ImageView imageView;
    public String billPay;
    public static final int PICK_CONTACT    = 4;
    public static final int PICK_CONTACT_FAV_LIST_UPDATE  = 5;
    public static final int PICK_CONTACT_FAV_LIST_ADD   = 6;
    private TextView tvPaymentText;
    public static final String PREFS_NAME = "MyApp_Settings";

    private static final String TAB_1_TAG = "Home";

    /**
     *
     * @author mwho
     * Maintains extrinsic info of a tab's construct
     */
    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;
        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }
    /**
     * A simple factory that returns dummy views to the Tabhost
     * @author mwho
     */
    class TabFactory implements TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /** (non-Javadoc)
         * @see android.widget.TabHost.TabContentFactory#createTabContent(String)
         */
        public View createTabContent(String tag) {
           View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);

            return v;
        }

    }
    /** (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment_tab_host, container, false);


//        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
//        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
//        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);


        thiscontext = container.getContext();


        tvPaymentText=(TextView)v.findViewById(R.id.tvPaymentText);
        tvPaymentText.setTypeface(Light);


        imageView=(ImageView)v.findViewById(R.id.imageswipe2);
        imageView.setBackgroundResource(R.drawable.allscreen);
        mTabHost = (TabHost)v.findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        PaymentsTabHost.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec(String_url.Tab1).setIndicator(String_url.MOBILE), (tabInfo = new TabInfo(String_url.MOBILE, Mobile_payments.class, savedInstanceState)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        PaymentsTabHost.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec(String_url.Tab2).setIndicator(String_url.DTH), (tabInfo = new TabInfo(String_url.DTH, dth_payment.class, savedInstanceState)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        PaymentsTabHost.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec(String_url.Tab3).setIndicator(String_url.bill_pay), (tabInfo = new TabInfo(String_url.bill_pay, Bill_payments.class, savedInstanceState)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
//        PaymentsTabHost.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec(String_url.Tab4).setIndicator(String_url.LIC), (tabInfo = new TabInfo(String_url.LIC, LIC.class, savedInstanceState)));
//        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        PaymentsTabHost.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec(String_url.Tab5).setIndicator(String_url.tab_cab), (tabInfo = new TabInfo(String_url.tab_cab, Tab_Cabfragment.class, savedInstanceState)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);


        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                .setBackgroundColor(Color.parseColor("#ececec"));

        mTabHost.setOnTabChangedListener(this);
        if (savedInstanceState != null) {

            mTabHost.setCurrentTabByTag(savedInstanceState.getString(String_url.tab)); //set the tab as per the saved state

        }


        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString(String_url.tab)); //set the tab as per the saved state
        }

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(getActivity(), Mobile_payments.class.getName()));
        fragments.add(Fragment.instantiate(getActivity(), dth_payment.class.getName()));
        fragments.add(Fragment.instantiate(getActivity(), Bill_payments.class.getName()));
//        fragments.add(Fragment.instantiate(getActivity(), LIC.class.getName()));
        fragments.add(Fragment.instantiate(getActivity(), Tab_Cabfragment.class.getName()));

        this.mPagerAdapter  = new PaymentsTabPagerAdapter(super.getChildFragmentManager(), fragments);
        this.mViewPager = (ViewPager)v.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String s=settings.getString(String_url.key,null);
       //Toast.makeText(getActivity(),"hi"+s,Toast.LENGTH_LONG).show();
        if(s==null)
        {
            mViewPager.setCurrentItem(0);

        }
        else if(s.equals(String_url.one))
        {
            mViewPager.setCurrentItem(0);

        }


        else if(s.equals(String_url.two))
        {
            mViewPager.setCurrentItem(1);

        }
        else if(s.equals(String_url.three))
        {
            mViewPager.setCurrentItem(2);

        }
        else if(s.equals(String_url.four))
        {
            mViewPager.setCurrentItem(3);

        }
        else if(s.equals(String_url.five))
        {
            mViewPager.setCurrentItem(4);
        }
        editor.remove(String_url.key);
        editor.commit();




        return v;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Toast.makeText(getActivity(), "OnActivityResultPaymentTab", Toast.LENGTH_LONG).show();
//
//        if (requestCode == PICK_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
//            Toast.makeText(getActivity(),"PaymentHome",Toast.LENGTH_SHORT).show();
//
//            Uri contactData = data.getData();
//            Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
//            if (c.moveToFirst()) {
//
//
//                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//                if (hasPhone.equalsIgnoreCase("1")) {
//                    Cursor phones = getActivity().getContentResolver().query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
//                            null, null);
//
//                    phones.moveToFirst();
//
//                    // String   contacts = ("+91" + " " + (phones.getString(phones.getColumnIndex("data1")).replace("+91", "").replace(" ", "")));
//
//                    String contacts = (phones.getString(phones.getColumnIndex("data1")));
//                    String testReplace = null;
//                    String pickupReplacedContact = null;
//
//                    if (contacts.length() >= 10) {
//                        if ((contacts.startsWith("+91") || (contacts.startsWith("0")))) {
//
//
//                            if (contacts.startsWith("0")) {
//                                String string;
//                                //testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                                string = contacts.substring(1);
//                                testReplace = string.replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                            } else if (contacts.startsWith("+91")) {
//                                testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                            }
//
//
//                        } else {
//                            testReplace = contacts.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\p{P}", "");
//                        }
//                    }
//
//                    if (testReplace != null) {
//
//
//                        if (testReplace.length() > 10) {
//                            Log.e("testReplace", testReplace);
//                            testReplace = testReplace.substring(testReplace.length() - 10);
//                            //    adapter.add(pickupReplacedContact + "      " + name);
//
//                        }
//                        Log.e("contactsPAymentTabhost", contacts);
//
//
//                        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//
//                        // Writing data to SharedPreferences
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString(String_url.number, testReplace);
//                        editor.putString(String_url.key, String_url.one);
//                        editor.commit();
//
//
//                        Intent i = new Intent(getActivity(), Tabhost_activity.class);
//                        i.putExtra(String_url.position, 1);
//                        startActivity(i);
//                        getActivity().overridePendingTransition(0, 0);
//                        getActivity().finish();
//
//
//                    }
//
//
//                }
//            }
//        } else if(requestCode == PICK_CONTACT_FAV_LIST_UPDATE && resultCode == Activity.RESULT_OK && data != null){
//
//            Uri contactData = data.getData();
//            Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
//            if (c.moveToFirst()) {
//
//
//                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//                if (hasPhone.equalsIgnoreCase("1")) {
//                    Cursor phones = getActivity().getContentResolver().query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
//                            null, null);
//
//                    phones.moveToFirst();
//
//                    // String   contacts = ("+91" + " " + (phones.getString(phones.getColumnIndex("data1")).replace("+91", "").replace(" ", "")));
//
//                    String contacts = (phones.getString(phones.getColumnIndex("data1")));
//                    String testReplace = null;
//                    String pickupReplacedContact = null;
//
//                    if (contacts.length() >= 10) {
//                        if ((contacts.startsWith("+91") || (contacts.startsWith("0")))) {
//
//
//                            if (contacts.startsWith("0")) {
//                                String string;
//                                //testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                                string = contacts.substring(1);
//                                testReplace = string.replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                            } else if (contacts.startsWith("+91")) {
//                                testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                            }
//
//
//                        } else {
//                            testReplace = contacts.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\p{P}", "");
//                        }
//                    }
//
//                    if (testReplace != null) {
//
//
//                        if (testReplace.length() > 10) {
//                            Log.e("testReplace", testReplace);
//                            testReplace = testReplace.substring(testReplace.length() - 10);
//                            //    adapter.add(pickupReplacedContact + "      " + name);
//
//                        }
//                        Log.e("contacts", contacts);
//
//
//                        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//
//                        // Writing data to SharedPreferences
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString(String_url.number, testReplace);
//                        editor.putString(String_url.key, String_url.one);
//                        editor.putString(String_url.key_Fav,String_url.five);
//                        editor.commit();
//
//
//                        Intent i = new Intent(getActivity(), Tabhost_activity.class);
//                        i.putExtra(String_url.position, 1);
//                        startActivity(i);
//                        getActivity().overridePendingTransition(0, 0);
//                        getActivity().finish();
//
//
//                    }
//
//
//                }
//            }
//        }
//
//        else if(requestCode == PICK_CONTACT_FAV_LIST_ADD && resultCode == Activity.RESULT_OK && data != null){
//
//            Uri contactData = data.getData();
//            Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
//            if (c.moveToFirst()) {
//
//
//                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//                if (hasPhone.equalsIgnoreCase("1")) {
//                    Cursor phones = getActivity().getContentResolver().query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
//                            null, null);
//
//                    phones.moveToFirst();
//
//                    // String   contacts = ("+91" + " " + (phones.getString(phones.getColumnIndex("data1")).replace("+91", "").replace(" ", "")));
//
//                    String contacts = (phones.getString(phones.getColumnIndex("data1")));
//                    String name=(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
//                    String testReplace = null;
//                    String pickupReplacedContact = null;
//
//                    if (contacts.length() >= 10) {
//                        if ((contacts.startsWith("+91") || (contacts.startsWith("0")))) {
//
//
//                            if (contacts.startsWith("0")) {
//                                String string;
//                                //testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                                string = contacts.substring(1);
//                                testReplace = string.replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                            } else if (contacts.startsWith("+91")) {
//                                testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
//                            }
//
//
//                        } else {
//                            testReplace = contacts.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\p{P}", "");
//                        }
//                    }
//
//                    if (testReplace != null) {
//
//
//                        if (testReplace.length() > 10) {
//                            Log.e("testReplace", testReplace);
//                            testReplace = testReplace.substring(testReplace.length() - 10);
//                            //    adapter.add(pickupReplacedContact + "      " + name);
//
//                        }
//                        Log.e("contacts", contacts);
//                        Log.e("NameFromContact",name);
//
//
//                        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//
//                        // Writing data to SharedPreferences
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString(String_url.number, testReplace);
//                        editor.putString(String_url.key, String_url.one);
//                        editor.putString(String_url.key_Fav,String_url.six);
//                        editor.putString(String_url.NameFromContact, name);
//                        editor.commit();
//
//
//                        Intent i = new Intent(getActivity(), Tabhost_activity.class);
//                        i.putExtra(String_url.position, 1);
//                        startActivity(i);
//                        getActivity().overridePendingTransition(0, 0);
//                        getActivity().finish();
//
//
//                    }
//
//
//                }
//            }
//        }
//
//        else if (resultCode == Activity.RESULT_CANCELED) {
//                Toast.makeText(getActivity(), "OnBackPressedPaymentActivity", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "OnBackPressedPaymentActivityDataNull", Toast.LENGTH_SHORT).show();
//            }
//
//    }


    /** (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }






    private static void AddTab(PaymentsTabHost thiscontext, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        String_url String_url1=new String_url();
        // Attach a Tab view factory to the spec
        tabSpec.setContent(thiscontext.new TabFactory(thiscontext.getActivity()));




        View v = LayoutInflater.from(thiscontext.getActivity()).inflate(R.layout.payments_tab_text, null);
        v.setBackgroundColor(Color.parseColor("#ffffff"));
        TextView tv = (TextView)v.findViewById(R.id.txt_tabtxt);
        Typeface Condensed = Typeface.createFromAsset(thiscontext.getActivity().getAssets(),String_url1.OpenSans_Semibold);
        tv.setText(tabInfo.tag);
        tv.setPadding(5,15,5,15);
        tv.setTextColor(Color.parseColor(String_url.spinnerDropDownTextColor));
        tv.setTypeface(Condensed);

       // tabSpec.setIndicator(v);
        tabHost.addTab(tabSpec.setIndicator(v));






    }







    /** (non-Javadoc)
     * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(String)
     */
    public void onTabChanged(String tag) {
        //TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);


            for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    mTabHost.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#ffffff"));//unselected
                    }
                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#ececec")); // selected

//        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("onPageSelected", pos);
//        editor.commit();



    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);

//        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("onPageSelected", position);
//        editor.commit();




    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }




}