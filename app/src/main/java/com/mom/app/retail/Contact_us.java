package com.mom.app.retail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Contact_us extends Fragment {
    
    private TextView timewz,phonewz,emailwz;
    private TextView timenz,phonenz,emailnz;
    private TextView timeez,phoneez,emailez;
    private TextView timesz,phonesz,emailsz;
    private ImageView imageView;
    private TextView contactsText,contactsImageText,headingNz,headingwz,headingSz,headingEz;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View v=inflater.inflate(R.layout.fragment_contactus,container,false);
//        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
//        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
//        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Condensed.ttf");

//        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
//        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
//        Typeface Condensed = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);


        Typeface Light = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Light);
        Typeface Normal = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Normal);
        Typeface SemiBold = Typeface.createFromAsset(getActivity().getAssets(),String_url.OpenSans_Semibold);


        imageView=(ImageView)v.findViewById(R.id.img_imageimage);
        imageView.setBackgroundResource(R.drawable.allscreen);

        contactsImageText=(TextView)v.findViewById(R.id.tvContactsImageText);
        contactsImageText.setText(R.string.setting);
        contactsImageText.setTypeface(Light);

        contactsText=(TextView)v.findViewById(R.id.tvContactsText);
        contactsText.setText(R.string.contact_us);
        contactsText.setTypeface(Light);


        headingwz=(TextView)v.findViewById(R.id.tv3wz);
        headingwz.setText(R.string.west_zone);
        headingwz.setTypeface(Light);

        headingNz=(TextView)v.findViewById(R.id.tv3nz);
        headingNz.setText(R.string.north_zone);
        headingNz.setTypeface(Light);

        headingSz=(TextView)v.findViewById(R.id.tv3sz);
        headingSz.setText(R.string.south_zone);
        headingSz.setTypeface(Light);

        headingEz=(TextView)v.findViewById(R.id.tv3ez);
        headingEz.setText(R.string.east_zone);
        headingEz.setTypeface(Light);


        timewz=(TextView)v.findViewById(R.id.tv4wz);timewz.setTypeface(Light);
        phonewz=(TextView)v.findViewById(R.id.tv5wz);phonewz.setTypeface(Light);
        emailwz=(TextView)v.findViewById(R.id.tv6wz);emailwz.setTypeface(Light);

        timenz=(TextView)v.findViewById(R.id.tv4nz);timenz.setTypeface(Light);
        phonenz=(TextView)v.findViewById(R.id.tv5nz);phonenz.setTypeface(Light);
        emailnz=(TextView)v.findViewById(R.id.tv6nz);emailnz.setTypeface(Light);

        timeez=(TextView)v.findViewById(R.id.tv4ez);timeez.setTypeface(Light);
        phoneez=(TextView)v.findViewById(R.id.tv5ez);phoneez.setTypeface(Light);
        emailez=(TextView)v.findViewById(R.id.tv6ez);emailez.setTypeface(Light);

        timesz=(TextView)v.findViewById(R.id.tv4sz);timesz.setTypeface(Light);
        phonesz=(TextView)v.findViewById(R.id.tv5sz);phonesz.setTypeface(Light);
        emailsz=(TextView)v.findViewById(R.id.tv6sz);emailsz.setTypeface(Light);
        
        
        
        



        timewz.setText(R.string.timeWest);
        phonewz.setText(R.string.phoneWest);
        emailwz.setText(R.string.emailWest);

        timenz.setText(R.string.timeNorth);
        phonenz.setText(R.string.phoneNorth);
        emailnz.setText(R.string.emailNorth);

        timeez.setText(R.string.timeEast);
        phoneez.setText(R.string.phoneEast);
        emailez.setText(R.string.emailEast);

        timesz.setText(R.string.timeSouth);
        phonesz.setText(R.string.phoneSouth);
        emailsz.setText(R.string.emailSouth);

        
        
        
        
        return v;
    }
}
