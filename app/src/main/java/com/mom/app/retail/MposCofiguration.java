package com.mom.app.retail;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pnsol.sdk.auth.AccountValidator;
import com.pnsol.sdk.interfaces.PaymentTransactionConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class MposCofiguration extends Fragment implements PaymentTransactionConstants {

    public static final String PREFS_NAME = "MyApp_Settings";
    RelativeLayout macPage2;
    TextView txt_mposException,textView1,textView2,stateBluetooth;
    String mkey;
    ProgressDialog progress;
    private String partnerAPIKey ="B03BB8E22433";
    ListView list;
    int listPosition;
    BluetoothAdapter bluetoothAdapter;
    public int SPLASH_TIME_OUT=1000;

    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mpos_cofiguration,container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        NetworkConnection networkConnection=new NetworkConnection(getActivity());
        boolean isNetworkAvailable=networkConnection.isNetworkAvailable();

        if(!isNetworkAvailable)
        {
            NetworkConnection.ExitAppDialog(getActivity());
        }
        else
        {
            macPage2=(RelativeLayout)v.findViewById(R.id.macPage2);
            txt_mposException=(TextView)v.findViewById(R.id.txt_mposException);
            list=(ListView)v.findViewById(R.id.listview1) ;
            textView1=(TextView)v.findViewById(R.id.textView1);
            textView2=(TextView)v.findViewById(R.id.textView2);
            stateBluetooth=(TextView)v.findViewById(R.id.bluetoothstate);
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            MerchantKeyAuthentication();
        }


        return v;
    }





    public void MerchantKeyAuthentication(){
        txt_mposException.setVisibility(View.GONE);
        macPage2.setVisibility(View.VISIBLE);

        // PRODUCTION
        mkey= "B03BB8E22433";
        // mkey ="2016665A802D";

        if (mkey != null) {
            if (mkey.length() == 12) {
                try {
                    progress = ProgressDialog.show(getActivity()

                            , "", "Searching Bluetooth Devices...", true);
                    AccountValidator validator = new
                            AccountValidator(getActivity());
                    validator.accountActivation(accountValidatorHandler, mkey, partnerAPIKey);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(),"Enter the 12 characters key",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Enter the key", Toast.LENGTH_SHORT).show();
        }
    }

    private Handler accountValidatorHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (msg.what == SUCCESS) {


                Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                CheckBlueToothState();
            }
            if (msg.what == FAIL) {
                txt_mposException.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), (String) msg.obj,Toast.LENGTH_SHORT).show();
                try {
                    String[] test = ((String) msg.obj).split(":");

                    String test1 = test[1];


                    ReturnMPOSResponseMessage returnResponseMessage = new ReturnMPOSResponseMessage();
                    String text = returnResponseMessage.ReturnMPOSResponseMessageData(ReturnMPOSResponseMessage.Errors.valueOf(test1));

                    txt_mposException.setText(text);
                }
                catch(Exception ex){

                    ReturnMPOSResponseMessage returnResponseMessage = new ReturnMPOSResponseMessage();
                    String text = returnResponseMessage.ReturnMPOSResponseMessageData(ReturnMPOSResponseMessage.Errors.DEFAULT_ERROR);

                    txt_mposException.setText(text);
                }




            }
        };
    };


    private void CheckBlueToothState(){
        progress.dismiss();
        if (bluetoothAdapter == null){
            stateBluetooth.setText("Bluetooth NOT support");
        }else{
            if (bluetoothAdapter.isEnabled()){
                if(bluetoothAdapter.isDiscovering()){

                }else{


                    textView1.setText("");
                    textView2.setText("");
                    textView1.append("\nPaired Devices are:");
                    ArrayAdapter<String> btArrayAdapter
                            = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1);

                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();


                    List<String> s = new ArrayList<String>();
                    for(BluetoothDevice bt : devices)
                        s.add(bt.getName());



                    if (devices.size() > 0) {
                        for (BluetoothDevice device : devices) {
                            String deviceBTName = device.getName();
                            String deviceBTAddress = device.getAddress();



                            btArrayAdapter.add(deviceBTName + "\n" + deviceBTAddress);
                            list.setAdapter(btArrayAdapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    listPosition = position;

                                    try {

                                        progress = ProgressDialog.show(getActivity()

                                                , "",
                                                "Loading...", true);

                                        new Handler().postDelayed(new Runnable() {



                                            @Override
                                            public void run() {

                                                Object obj = list.getItemAtPosition(listPosition);

                                                String mac = obj.toString();
                                                StringTokenizer stringTokenizer=new StringTokenizer(mac, "\n");
                                                String first=stringTokenizer.nextToken();
                                                String macaddress=stringTokenizer.nextToken();


                                                SharedPreferences sharedPreferences=getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                                SharedPreferences.Editor shared_mac=sharedPreferences.edit();
                                                shared_mac.putString("shared_mac",macaddress);
                                                shared_mac.commit();
                                                txt_mposException.setVisibility(View.GONE);
                                                macPage2.setVisibility(View.GONE);
                                                //macPage3.setVisibility(View.GONE);
                                                //macPage4.setVisibility(View.GONE);

                                                SharedPreferences sharedPreferences1=getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                                String shared_mac1=sharedPreferences1.getString("shared_mac","");


                                                progress.dismiss();

                                                Toast.makeText(getActivity(),"MPOS sucesfully configured.Now Proceed for payment",Toast.LENGTH_SHORT).show();



                                            }
                                        }, SPLASH_TIME_OUT);



                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();

                                    }






                                }
                            });
                        }
                    }
                    else
                    {
                        textView1.setVisibility(View.GONE);
                        textView2.append("No Paired Device Found.");
                    }

                }
            }else{
                stateBluetooth.setText("Bluetooth is NOT Enabled!");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);


            }
        }

    }

}
