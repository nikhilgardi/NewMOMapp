package com.mom.app.retail.Adapters;

        import android.app.Activity;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.graphics.Typeface;
        import android.util.Log;
        import android.util.SparseBooleanArray;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import com.mom.app.retail.R;
        import com.mom.app.retail.String_url;
        import com.mom.app.retail.UpdateNotificationCount;

public class CustomListHelpAdap extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] imgid;
    private SparseBooleanArray enabledItems = new SparseBooleanArray();
    UpdateNotificationCount updateNotificationCount;
    int count;
    private static final String PREFS_NAME = "MyApp_Settings";
    View view;


    public CustomListHelpAdap(Activity context,String[] itemname,String[] imgid) {
        super(context, R.layout.customlist_notification_adapter, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        updateNotificationCount= new UpdateNotificationCount(context,context);
        count=itemname.length;
    }

    public View getView(int position,View convertView,ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.fragment_help_adap, null, true);

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_help_adap, parent, false);

        }
        else {
            view = convertView;
        }

        Typeface Light = Typeface.createFromAsset(context.getAssets(), String_url.OpenSans_Light);
        Typeface Thin = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Normal);
        Typeface Condensed = Typeface.createFromAsset(context.getAssets(),String_url.OpenSans_Semibold);

        TextView txtTitle = (TextView) view.findViewById(R.id.textView1);
        TextView txtTitle1 = (TextView) view.findViewById(R.id.textView2);
        if(itemname!=null && imgid!=null) {
            txtTitle.setText(itemname[position]);
            txtTitle.setTypeface(Condensed);
            txtTitle1.setText(imgid[position]);
            txtTitle1.setTypeface(Thin);
        }
//        if(!isEnabled(position)) {
//        /* change to disabled appearance */
//           //Toast.makeText(context,"Cannot click twice",Toast.LENGTH_SHORT).show();
//
//            rowView.setBackgroundColor(Color.parseColor("#ececec"));
//            Log.e("Cannot click twice", "Cannot click twice");
//
//
//
//
//        }
//        else {
//        /* restore default appearance */
//         // Toast.makeText(context,"clicked first time",Toast.LENGTH_SHORT).show();
//           // view.setBackgroundColor(context.getResources().getColor(R.color.HeaderGray));
//            rowView.setBackgroundColor(Color.parseColor("#f26522"));
//            Log.e("clicked first time","clicked first time");
//        }


        return view;

    }

    @Override
    public boolean areAllItemsEnabled() {
        //   return super.areAllItemsEnabled();
        return false;

    }

    @Override
    public boolean isEnabled(int position) {
//        return super.isEnabled(position,true);
        return enabledItems.get(position, true);
    }

    public void toggleItem(int position) {
        //  boolean state = enabledItems.get(position, true);
        //enabledItems.put(position, !state);
        //updateNotificationCount.decreaseCount();
        //itemname.remove(position);
        decreaseCount();
        notifyDataSetChanged();



    }

    public void  decreaseCount(){

        count--;

        SharedPreferences shared = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(String_url.lastNotificationCount, count);
        Log.e("decreaseCount", "" + count);
        editor.commit();
        TextView textView=(TextView)context.findViewById(R.id.hotlist_hot);
        textView.setText(String.valueOf(count));
    }

}