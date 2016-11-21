package com.mom.app.retail;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;


public class UpdateNotificationCount {

    Context context;
    Activity activity;
    int count=8;

    public UpdateNotificationCount(Context context,Activity activity) {
        this.context=context;
        this.activity=activity;
    }

    public void  decreaseCount(){
        count--;
        //ImageView imageView=(ImageView)activity.findViewById(R.id.hotlist_bell);
        TextView textView=(TextView)activity.findViewById(R.id.hotlist_hot);
      //  imageView.setBackgroundResource(R.drawable.alert);
     //   String count=String.valueOf(4);
        textView.setText(String.valueOf(count));
    }

    public void notificationCount() {
        TextView textView=(TextView)activity.findViewById(R.id.hotlist_hot);
        //  imageView.setBackgroundResource(R.drawable.alert);
        //   String count=String.valueOf(4);
        textView.setText(String.valueOf(count));

    }

}
