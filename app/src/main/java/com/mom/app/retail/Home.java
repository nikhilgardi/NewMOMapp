package com.mom.app.retail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;




    public class Home extends Fragment {
    private static final int CAMERA_REQUEST = 1888;

    private Button btnPaymentHistory,btnLoadMoney;
    Context context;
    public String contacts;
    public Uri selectedImage;
    private long mobileNo;
    private ImageView ivDialogShowImage;
    private static final int CAMERA_PIC_REQUEST = 1111;
    final int PIC_CROP = 3;
    public ImageView iv_profileImg;
    public static final String PREFS_NAME = "MyApp_Settings";
    private TextView tvName,tvBalance;
    double customeraccntbalance;
    public ImageView ivMobilePayments,ivDTH,ivUBP,ivLIC,ivTabCab,ivMoneyTransfer,ivGiftVoucher,ivBus,ivTrain,ivAirLine,ivSettings;
    public TextView tvPaymentsText,tvMoneyTransferText,tvGifttext,tvTickettext,tvSettingstext;
    private String firstName,lastName;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_horizontal_dashboard, container, false);

        NetworkConnection networkConnection=new NetworkConnection(getActivity());
        boolean isNetworkAvailable=networkConnection.isNetworkAvailable();

        if(!isNetworkAvailable)
        {
            NetworkConnection.ExitAppDialog(getActivity());
        }

        else {
            
            Typeface Light      = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Light);
            Typeface Normal       = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Normal);
            Typeface SemiBold  = Typeface.createFromAsset(getActivity().getAssets(), String_url.OpenSans_Semibold);
            tvName              = (TextView)view.findViewById(R.id.tvName);
            btnPaymentHistory   =(Button)view.findViewById(R.id.btn_payment);
            btnLoadMoney        =(Button)view.findViewById(R.id.btn_load_money);
            iv_profileImg       =(ImageView)view.findViewById(R.id.iv_profileImg);
            ivMobilePayments    =(ImageView)view.findViewById(R.id.ivMobilePayments);
            ivDTH               =(ImageView)view.findViewById(R.id.ivDTH);
            ivUBP               =(ImageView)view.findViewById(R.id.ivUBP);

            ivTabCab            =(ImageView)view.findViewById(R.id.ivTabCab);
            ivMoneyTransfer     =(ImageView)view.findViewById(R.id.ivMoneyTransfer);
            ivGiftVoucher       =(ImageView)view.findViewById(R.id.ivGiftVoucher);
            ivBus               =(ImageView)view.findViewById(R.id.ivBus);
            ivTrain             =(ImageView)view.findViewById(R.id.ivTrain);
            ivAirLine           =(ImageView)view.findViewById(R.id.ivAirLine);
            ivSettings          =(ImageView)view.findViewById(R.id.ivSettings);
            tvPaymentsText      =(TextView)view.findViewById(R.id.tvPaymentText);
            tvMoneyTransferText =(TextView)view.findViewById(R.id.tvMoneyTransferText);
            tvGifttext          =(TextView)view.findViewById(R.id.tvGifttext);
            tvTickettext        =(TextView)view.findViewById(R.id.tvTickettext);
            tvSettingstext      =(TextView)view.findViewById(R.id.tvSettingstext);

            btnPaymentHistory.setTypeface(Normal);
            btnLoadMoney.setTypeface(Normal);
            tvName.setTypeface(Light);
            tvMoneyTransferText.setTypeface(Light);
            tvPaymentsText.setTypeface(Light);
            tvGifttext.setTypeface(Light);
            tvTickettext.setTypeface(Light);
            tvSettingstext.setTypeface(Light);


            SharedPreferences CustomerMainAccountBalance = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = CustomerMainAccountBalance.edit();

            SQLiteAdapter sql= new SQLiteAdapter(getActivity());
            sql.openToReadBalanceDataBase();
            double balance = sql.getBalanceFromDB();
            sql.close();
            if (balance < 50) {



                String ApplicationStarted = CustomerMainAccountBalance.getString(String_url.ApplicationStarted, "");
                if (ApplicationStarted.equals("yes")) {

                    AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());
                    alertbox.setTitle(getString(R.string.low_wallet_balance));
                    alertbox.setMessage(getString(R.string.update_wallet_balance));
                    alertbox.setPositiveButton(getString(R.string.Yes), new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent ii = new Intent(getActivity(), Tabhost_activity.class);
                                    ii.putExtra(String_url.position, 6);
                                    startActivity(ii);
                                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    getActivity().finish();


                                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(String_url.SendMoney, String_url.SendMoneyDashBoard);
                                    editor.apply();

                                    arg0.dismiss();

                                }
                            });
                    alertbox.setNegativeButton(getString(R.string.No), new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();
                                }
                            });
                    alertbox.show();

                    editor.remove(String_url.ApplicationStarted);
                    editor.apply();



                }


            }


            SharedPreferences profile_Image = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String My_DisplayImage = profile_Image.getString(String_url.Profile_Image, "");

            File newFolder = new File(Environment.getExternalStorageDirectory(), String_url.DisplayImageFolder);
            SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String sLoginNumber = shared.getString(String_url.Image, "");


            SharedPreferences mobile_number = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            mobileNo = mobile_number.getLong(String_url.MobileNumber, 0);
            firstName   =mobile_number.getString(String_url.firstName,"");
            lastName    =mobile_number.getString(String_url.lastName,"");
            String name =firstName.concat(" ").concat(lastName).toUpperCase();
            tvName.setText(name);

            if (!newFolder.exists()) {
                iv_profileImg.setImageResource(R.drawable.profileimg);
            } else {
                File file[] = newFolder.listFiles();
                for (File aFile : file) {

                    String sfileName = aFile.getName().replace(".png", "");


                    if (sLoginNumber.equals(sfileName)) {

                        FileInputStream streamIn = null;
                        try {
                            streamIn = new FileInputStream(aFile);
                            Bitmap bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image

                            Bitmap dstBmp;
                            if (bitmap.getWidth() >= bitmap.getHeight()) {

                                dstBmp = Bitmap.createBitmap(
                                        bitmap,
                                        bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
                                        0,
                                        bitmap.getHeight(),
                                        bitmap.getHeight()
                                );

                            } else {

                                dstBmp = Bitmap.createBitmap(
                                        bitmap,
                                        0,
                                        0,
                                        bitmap.getWidth(),
                                        bitmap.getWidth()
                                );
                            }
                            streamIn.close();


                            GraphicsUtil graphicUtil = new GraphicsUtil();
                            iv_profileImg.setImageBitmap(graphicUtil.getRoundedShape(dstBmp));
                            iv_profileImg.setBackgroundResource(R.drawable.profileborder);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }


                }

            }

            iv_profileImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectImage();
                }
            });


            btnPaymentHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent ii = new Intent(getActivity(), Tabhost_activity.class);
                    ii.putExtra(String_url.position, 26);
                    startActivity(ii);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    getActivity().finish();


                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                   
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(String_url.home, String_url.dashboard);
                   editor.apply();


                }
            });
            btnLoadMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(getActivity(), Tabhost_activity.class);
                    ii.putExtra(String_url.position, 6);
                    startActivity(ii);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    getActivity().finish();


                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                   
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(String_url.SendMoney, String_url.SendMoneyDashBoard);
                   editor.apply();

                }
            });



            ivMobilePayments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent i = new Intent(getActivity(), Tabhost_activity.class);
                        i.putExtra(String_url.position, 1);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        getActivity().finish();
                        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(String_url.key, String_url.one);
                       editor.apply();

                }
            });
            ivDTH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent i = new Intent(getActivity(), Tabhost_activity.class);
                        i.putExtra(String_url.position, 2);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        getActivity().finish();
                        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(String_url.key, String_url.two);
                       editor.apply();

                }
            });
            ivUBP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent i = new Intent(getActivity(), Tabhost_activity.class);
                        i.putExtra(String_url.position, 3);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        getActivity().finish();
                        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(String_url.key, String_url.three);
                        editor.apply();
                    
                }
            });

            ivTabCab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), Tabhost_activity.class);
                    i.putExtra(String_url.position, 5);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    getActivity().finish();
                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(String_url.key, String_url.five);
                   editor.apply();

                }
            });
            ivMoneyTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent i = new Intent(getActivity(), Tabhost_activity.class);
                        i.putExtra(String_url.position, 6);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        getActivity().finish();

                }
            });
            ivGiftVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), Tabhost_activity.class);
                    i.putExtra(String_url.position, 11);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    getActivity().finish();

                }
            });
            ivSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), Tabhost_activity.class);
                    i.putExtra(String_url.position, 26);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    getActivity().finish();

                }
            });


            ivBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserBusIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mombus.money-on-mobile.net/"));
                    startActivity(browserBusIntent);
                }
            });
            ivTrain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserRailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://momrail.com/"));
                    startActivity(browserRailIntent);
                }
            });
            ivAirLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserAirIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://travel.money-on-mobile.net/"));
                    startActivity(browserAirIntent);
                }
            });



        }
        return view;
    }



    private void selectImage() {

        final CharSequence[] options = { "View Image","Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("View Image")) {
                    try {


                        SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


                        String sLoginNumber = shared.getString(String_url.Image,"");

                        final Dialog dialog1 = new Dialog(getActivity());
                        File newFolder = new File(Environment.getExternalStorageDirectory(), String_url.DisplayImageFolder);

                        if (!newFolder.exists()) {


                            dialog1.setContentView(R.layout.customdialog_view_image);
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            ivDialogShowImage = (ImageView) dialog1.findViewById(R.id.imageDisplay);
                            ivDialogShowImage.setImageResource(R.drawable.profileimg);
                            dialog1.show();
                            ImageView imageViewCancel = (ImageView) dialog1.findViewById(R.id.imageCancel);

                            imageViewCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });

                        }
                        else
                        {
                            File file[] = newFolder.listFiles();
                            for (int i=0; i < file.length; i++)
                            {

                                String sfileName = file[i].getName().replace(".png","");



                                if (sLoginNumber.equals(sfileName))
                                {

                                    FileInputStream streamIn = null;
                                    try {
                                        streamIn = new FileInputStream(file[i]);
                                        Bitmap bitmap = BitmapFactory.decodeStream(streamIn);


                                        streamIn.close();
                                        dialog1.setContentView(R.layout.customdialog_view_image);
                                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



                                        ivDialogShowImage = (ImageView) dialog1.findViewById(R.id.imageDisplay);
                                        ivDialogShowImage.setImageBitmap(bitmap);


                                        dialog1.show();
                                        ImageView imageViewCancel = (ImageView) dialog1.findViewById(R.id.imageCancel);

                                        imageViewCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog1.dismiss();
                                            }
                                        });
                                        break;

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else
                                {

                                    dialog1.setContentView(R.layout.customdialog_view_image);
                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    ivDialogShowImage = (ImageView) dialog1.findViewById(R.id.imageDisplay);
                                    ivDialogShowImage.setImageResource(R.drawable.profileimg);

                                    dialog1.show();
                                    ImageView imageViewCancel = (ImageView) dialog1.findViewById(R.id.imageCancel);

                                    imageViewCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog1.dismiss();
                                        }
                                    });
                                }


                            }

                        }





                    } catch (Exception e) {
                        e.getMessage();
                    }


                } else if (options[item].equals("Take Photo")) {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {



                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
// ******** code for crop image
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 256);
                        intent.putExtra("outputY", 256);

                        try {

                            intent.putExtra("return-data", true);
                            getActivity().startActivityForResult(intent, PIC_CROP);

                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }



                    }
                    else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getActivity().startActivityForResult(intent, 1);
                    }
                }

                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(intent, 2);

                }

                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences shared = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String sLoginNumber = shared.getString(String_url.Image, "");
        if (requestCode == 1 && resultCode== Activity.RESULT_OK && data != null) {

            selectedImage = data.getData();
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");






            Bitmap bitmap= getResizedBitmap(thumbnail,200);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG,100,bytes);
            byte[] bytes1 = bytes.toByteArray();



            Bitmap dstBmp;
            if (thumbnail.getWidth() >= thumbnail.getHeight()){

                dstBmp = Bitmap.createBitmap(
                        bitmap,
                        bitmap.getWidth()/2 - bitmap.getHeight()/2,
                        0,
                        bitmap.getHeight(),
                        bitmap.getHeight()
                );

            }else{

                dstBmp = Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.getWidth(),
                        bitmap.getWidth()
                );
            }



            iv_profileImg.setImageBitmap(getRoundedShape(dstBmp));




            iv_profileImg.setBackgroundResource(R.drawable.profileborder);
            String encodeImage= Base64.encodeToString(bytes1, Base64.DEFAULT);
            SharedPreferences profile_Image1=PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor=profile_Image1.edit();
            editor.putString(String_url.Profile_Image,encodeImage);
           editor.apply();

            File newFolder = new File(Environment.getExternalStorageDirectory(), String_url.DisplayImageFolder);
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            try {
                File file = new File(newFolder, sLoginNumber+".png");
                try {
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    //5
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                System.out.println("ex: " + ex);
            }






        }
        else if (requestCode == 2 && resultCode== Activity.RESULT_OK && data != null ) {

            selectedImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c =getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Bitmap bitmap = getResizedBitmap(thumbnail,200);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            byte[] bytes1=bytes.toByteArray();

            String encodeImage= Base64.encodeToString(bytes1,Base64.DEFAULT);
            SharedPreferences profile_Image1=PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor=profile_Image1.edit();
            editor.putString(String_url.Profile_Image,encodeImage);
           editor.apply();

            Bitmap dstBmp;
            if (bitmap.getWidth() >= bitmap.getHeight()){

                dstBmp = Bitmap.createBitmap(
                        bitmap,
                        bitmap.getWidth()/2 - bitmap.getHeight()/2,
                        0,
                        bitmap.getHeight(),
                        bitmap.getHeight()
                );

            }else{

                dstBmp = Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.getWidth(),
                        bitmap.getWidth()
                );
            }


            iv_profileImg.setImageBitmap(getRoundedShape(dstBmp));

            iv_profileImg.setBackgroundResource(R.drawable.profileborder);

            File newFolder = new File(Environment.getExternalStorageDirectory(), String_url.DisplayImageFolder);
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            try {
                File file = new File(newFolder, sLoginNumber+".png");
                try {
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);

                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                System.out.println("ex: " + ex);
            }

        }




        else if(resultCode == Activity.RESULT_CANCELED){

        }
        else{

        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth;
        int targetHeight;

        if(scaleBitmapImage.getHeight()>scaleBitmapImage.getWidth())
        {
            targetWidth = 450;
            targetHeight = 800;
        }
        else if(scaleBitmapImage.getWidth()>scaleBitmapImage.getHeight())
        {
            targetWidth = 800;
            targetHeight = 450;
        }
        else {
            targetWidth = 400;
            targetHeight = 400;
        }



        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();



        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.max(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);

        return targetBitmap;
    }

}
