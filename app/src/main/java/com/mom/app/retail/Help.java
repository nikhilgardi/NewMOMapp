package com.mom.app.retail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mom.app.retail.Adapters.CustomListHelpAdap;

public class Help extends Fragment {
    private ImageView imageView;
    ListView list;

    private TextView tvHelpImageText,tvHelp;


    String[] itemname ={
            "What is MoneyOnMobile?",
            "How can I use MoneyOnMobile?",
            "What benefits do I get from using this service?",
            "What about security of my transactions and financial details?",
            "How much will it cost me?",
            "Which are the services I can use with MoneyOnMobile?",
            "Will my phone support MoneyOnMobile?",
            "Can I use MoneyOnMobile with my mobile service provider?"
    };


    String[] itemlist ={
            "MoneyOnMobile is a simple, easy to use mobile SMS or smart phone application that lets you connect with your money at the push of a button! Using the innovative m-Wallet from MoneyOnMobile, you can recharge your mobile; pay utility bills; top-up your DTH account; shop for any goods or services; buy travel related serices such as - rail / air / bus / movie tickets and even handle banking transaction all from the comfort of your mobile phone.",
            "Using MoneyOnMobile is very easy. To activate SMS services you will send an SMS to to register and then you are ready to you MoneyOnMobile; For GPRS all you have to do is download the application to your mobile phone, register for a customer account, update your bank and/or credit and debit card details and start making payments with merchants that accept MoneyOnMobile.",
            "Security and Convenience: Consider that you would no longer have to carry around cash, credit or debit cards and you can buy the services you or a friend may need from anywhere at anytime. You can use MoneyOnMobile for any and all goods and services as long as the merchant or service provider is also a MoneyOnMobile registered client. An additional benefit is that you no longer need to queue up to make payments. With this service, you can make your payments at any time and from anywhere.",
            "The MoneyOnMobile payment platform and all the transactions through this service follow the guidelines laid down by PCI-DSS in terms of security. This means the highest possible level of encryption of your personal and bank details as well as of all transactions carried out through the application. The transactions are protected by two factor authentication through MPIN and TPIN. The customer need to enter the MPIN to login and TPIN for authorizing any transaction. The MPIN and TPIN are stored at the server side to prevent misuse of customer data in an event of mobile device theft/loss.",
            "This is the best part about MoneyOnMobile it is totally free to use for end customers. You can freely download and install the application of all your mobile phones. You might incur charges as applied by your mobile service provider for SMS or GPRS data usage, but other than these, you have to pay nothing extra to use this service.",
            "New services, service providers and merchant outlets are being added to the MoneyOnMobile network on a daily basis. At the moment though, you can use the following services with ease:\n" +
                    "\n" +
                    " Recharge your mobile phone\n" +
                    " Top up your DTH account\n" +
                    " Purchase rail / bus / air / movie tickets\n" +
                    " Buy goods and services at partner establishments and outlets\n" +
                    " Pay utility bills including electricity / gas / telephone",
            "As long as it is a mobile phone that has either SMS or GPRS services, your phone will support MoneyOnMobile. We have designed MoneyOnMobile to work across all mobile phone handsets, from the most basic to the most advanced. If you mobile phone supports Java, you can use MoneyOnMobile through the GPRS service of your mobile service provider. If it does not, you can use this service via SMS. Both channels are equally service and easy to use.",
            "MoneyOnMobile has no restrictions in terms of geographyor mobile service provider in greater India. You just need to register once with MoneyOnMobile. Thereafter, you can continue to use the same account even if you change cities or mobile service providers."
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help,container, false);

//        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
//        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
//        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Condensed.ttf");

        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);

        tvHelpImageText=(TextView)view.findViewById(R.id.tvHelpImageText);

        tvHelp=(TextView)view.findViewById(R.id.tv_help);

        tvHelpImageText.setText(R.string.setting);
        tvHelpImageText.setTypeface(Light);

        tvHelp.setText(R.string.help);
        tvHelp.setTypeface(Light);


        imageView=(ImageView)view.findViewById(R.id.img_image);
        imageView.setBackgroundResource(R.drawable.allscreen);
        CustomListHelpAdap adapter=new CustomListHelpAdap(getActivity(), itemname, itemlist);
        list=(ListView)view.findViewById(R.id.listview1);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub


            }
        });



        return view;

    }



}