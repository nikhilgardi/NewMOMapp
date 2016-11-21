package com.mom.app.retail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Setting extends Fragment {
    private ImageView imageView;
    private TextView tvsettingImageText,tv_setting;
    private ListView listview1;
    public static final String PREFS_NAME = "MyApp_Settings";
  


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting,container, false);
        NetworkConnection networkConnection = new NetworkConnection(getActivity());
        boolean isNetworkAvailable = networkConnection.isNetworkAvailable();

        if (!isNetworkAvailable) {
            NetworkConnection.ExitAppDialog(getActivity());
        }
        else {
            
            Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
            Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
            Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);
            imageView=(ImageView)view.findViewById(R.id.img_image);
            tvsettingImageText=(TextView)view.findViewById(R.id.tvsettingImageText);
            tv_setting=(TextView)view.findViewById(R.id.tv_setting);
            listview1=(ListView)view.findViewById(R.id.listview1);

            tvsettingImageText.setTypeface(Light);
            tv_setting.setTypeface(Thin);

//            String[] itemlist ={
//                    getString(R.string.change_mpin),
//                    getString(R.string.change_tpin),
//                    getString(R.string.reset_tpin),
//                    getString(R.string.Notification),
//                    getString(R.string.payment_history),
//                    getString(R.string.book_comp),
//                    getString(R.string.contact_us),
//                    getString(R.string.help)
//            };



            String[] itemlist ={
                    getString(R.string.change_mpin),
                    getString(R.string.change_tpin),
                    getString(R.string.reset_tpin),
                 //   getString(R.string.configure_mpos),
                    getString(R.string.Notification),
                    getString(R.string.Transaction_history),
                    getString(R.string.contact_us),
                    getString(R.string.help)

            };


            imageView.setBackgroundResource(R.drawable.allscreen);
            CustomListAdapter adapter=new CustomListAdapter(getActivity(),  itemlist);
            ListView list=(ListView)view.findViewById(R.id.listview1);


            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                    switch(position)
                    {
                        case 0:
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                            Change_MPIN fm=new Change_MPIN();
                            fragmentTransaction.replace(R.id.fragmentcontent,fm).addToBackStack(null);
                            fragmentTransaction.commit();

                            break;
                        case 1:

                            FragmentManager fragmentManager1=getFragmentManager();
                            FragmentTransaction fragmentTransaction1=fragmentManager1.beginTransaction();
                              fragmentTransaction1.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                            Change_TPIN tm=new Change_TPIN();
                            fragmentTransaction1.replace(R.id.fragmentcontent,tm).addToBackStack(null);
                              fragmentTransaction1.commit();
                            break;


                        case 2:
                            FragmentManager fragmentManagerReset_Tpin=getFragmentManager();
                            FragmentTransaction fragmentTransactionReset_Tpin=fragmentManagerReset_Tpin.beginTransaction();
                            fragmentTransactionReset_Tpin.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            ResetTPIN reset_TPIN =new ResetTPIN();
                            fragmentTransactionReset_Tpin.replace(R.id.fragmentcontent, reset_TPIN).addToBackStack(null);
                            fragmentTransactionReset_Tpin.commit();
                            break;

//                        case 3:
//                            FragmentManager fragmentManager7=getFragmentManager();
//                            FragmentTransaction fragmentTransaction7=fragmentManager7.beginTransaction();
//                            fragmentTransaction7.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            MposCofiguration mposCofiguration=new MposCofiguration();
//                            fragmentTransaction7.replace(R.id.fragmentcontent,mposCofiguration).addToBackStack(null);
//                            fragmentTransaction7.commit();
//                            break;
                        case 3:
                            FragmentManager fragmentManager2=getFragmentManager();
                            FragmentTransaction fragmentTransaction2=fragmentManager2.beginTransaction();
                            fragmentTransaction2.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Notification no=new Notification();
                            fragmentTransaction2.replace(R.id.fragmentcontent,no).addToBackStack(null);
                            fragmentTransaction2.commit();
                            break;
                        case 4:
                            FragmentManager fragmentManager3=getFragmentManager();
                            FragmentTransaction fragmentTransaction3=fragmentManager3.beginTransaction();
                            fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Payment_history ph=new Payment_history();
                            fragmentTransaction3.replace(R.id.fragmentcontent,ph).addToBackStack(null);
                            fragmentTransaction3.commit();
                            break;
//                        case 5:
//                            FragmentManager fragmentManager4=getFragmentManager();
//                            FragmentTransaction fragmentTransaction4=fragmentManager4.beginTransaction();
//                            fragmentTransaction4.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                            Book_complaint bk=new Book_complaint();
//                            fragmentTransaction4.replace(R.id.fragmentcontent,bk).addToBackStack(null);
//                            fragmentTransaction4.commit();
//                            break;
                        case 5:
                            FragmentManager fragmentManager5=getFragmentManager();
                            FragmentTransaction fragmentTransaction5=fragmentManager5.beginTransaction();
                            fragmentTransaction5.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Contact_us contact=new Contact_us();
                            fragmentTransaction5.replace(R.id.fragmentcontent,contact).addToBackStack(null);
                            fragmentTransaction5.commit();
                            break;
                        case 6:
                            FragmentManager fragmentManager6=getFragmentManager();
                            FragmentTransaction fragmentTransaction6=fragmentManager6.beginTransaction();
                            fragmentTransaction6.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            Help help=new Help();
                            fragmentTransaction6.replace(R.id.fragmentcontent,help).addToBackStack(null);
                            fragmentTransaction6.commit();
                            break;
                    }
                }
            });

            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            String s=settings.getString(String_url.home,null);



            if(s!=null) {
                if (s.equals(String_url.dashboard)) {
                    FragmentManager fragmentManager3 = getFragmentManager();
                    FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                    fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    Payment_history ph = new Payment_history();
                    fragmentTransaction3.replace(R.id.fragmentcontent, ph).addToBackStack(null);
                    fragmentTransaction3.commit();
                    editor.remove(getString(R.string.home)).apply();

                }
                else if (s.equals(String_url.notification)) {
                    FragmentManager fragmentManager3 = getFragmentManager();
                    FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                    fragmentTransaction3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    Notification ph = new Notification();
                    fragmentTransaction3.replace(R.id.fragmentcontent, ph).addToBackStack(null);
                    fragmentTransaction3.commit();
                    editor.remove(getString(R.string.home)).apply();

                }
            }
        }


        return view;

    }


}