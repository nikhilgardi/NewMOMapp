package com.mom.app.retail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;


import java.util.HashMap;
import java.util.Map;


public class SQLiteAdapter extends ActionBarActivity {
    private int lastId;
    public static final String MYDATABASE_NAME = "MY_DATABASE";
    public static final String MYDATABASE_TABLE = "MY_TABLE_DB";
    public static final int MYDATABASE_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_CONTENT = "Content";

    //create table MY_DATABASE (ID integer primary key, Content text not null);
    private static final String SCRIPT_CREATE_DATABASE =
            "create table " + MYDATABASE_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_CONTENT + " text not null"
                   + ");";

    // public static final String LOGININFO_DATABASENAME = "LOGIN_DATABASE";
    public static final String LOGIN_TABLE            = "LOGIN_TABLE";
    public static final String LOGIN_ID               = "_id";
    public static final String LOGIN_CUSTOMER_CODE    = "CUSTOMER_CODE";
    public static final String LOGIN_MPIN             = "M_PIN";
    public static final String LOGIN_DEVICE_PIN       = "DEVICE_PIN";
    public static final String LOGIN_CREATION_DATE    = "CREATION_DATE";
    public static final String LOGIN_EXPIRY_DATE      = "EXPIRY_DATE";

    private static final String LOGIN_CREATE_DATABASE = "create table " + LOGIN_TABLE +
            " (" +LOGIN_ID + " integer primary key autoincrement, " +
                 LOGIN_CUSTOMER_CODE + " text not null, "+
                 LOGIN_MPIN + " text not null, "+
                 LOGIN_DEVICE_PIN + " text not null, "+
                 LOGIN_CREATION_DATE + " text not null, "+
                 LOGIN_EXPIRY_DATE+ " text not null"+ ");";



    public static final String NOTIOFICATION_TABLE   = "NOTIFICATION_TABLE";
    public static final String SNO_ID                = "_id";
    public static final String NOTIFICATION_ID       = "NOTIFICATION_ID";
    public static final String DATE                  = "DATE";
    public static final String CONTENT               = "CONTENT";
    public static final String STATUS                = "STATUS";

    private static final String NOTIFICATION_CREATE_DATABASE = "create table " + NOTIOFICATION_TABLE +
            " (" +SNO_ID + " integer primary key autoincrement, " +
            NOTIFICATION_ID + " integer , "+
            DATE + " text not null, "+
            CONTENT + " text not null, "+
            STATUS + " flag INTEGER DEFAULT 0"+ ");";

    private static final String MAIN_BALANCE_TABLE="MAIN_BALANCE_TABLE";
    public static final String BAL_ID                = "_id";
    public static final String BALANCE                = "BALANCE";

    private static final String BALANCE_CREATE_DATABASE= "create table " + MAIN_BALANCE_TABLE +
            " (" +BAL_ID + " integer primary key autoincrement, " +
            BALANCE + " real "+");";




    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    public SQLiteAdapter(Context c) {
        context = c;
    }

    public SQLiteAdapter openToRead() throws SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWrite() throws SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteHelper.close();
    }

    //contacts

    public long insert(String content) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CONTENT, content);



        return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
    }

    public String[] getContacts() {

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM MY_TABLE_DB ",null);
        String[] array = new String[cursor.getCount()];

        int i = 0;
        while(cursor.moveToNext()){
            String uname = cursor.getString(cursor.getColumnIndex("Content"));
            array[i] = uname;
            i++;
        }
        cursor.close();
        return  array;

    }

    public void delete() {



        sqLiteDatabase.execSQL("delete from MY_TABLE_DB");
    }


    //BALANCE_TABLE


    public SQLiteAdapter openToReadBalanceDataBase() throws SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWriteBalanceDataBase() throws SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public long insertBalance(Double customerMainAccountBalance)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BALANCE, customerMainAccountBalance);
        return sqLiteDatabase.insert(MAIN_BALANCE_TABLE, null, contentValues);
    }

    public double getBalanceFromDB()
    {
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM MAIN_BALANCE_TABLE ",null);
        Double sBal = null;

        if(cursor != null && cursor.moveToFirst()){
            cursor.moveToLast();
            sBal = cursor.getDouble(cursor.getColumnIndex("BALANCE"));
            cursor.close();
            return  sBal;
        }


//        if(sCustomerCode!=null)
//        {
//            return  sCustomerCode;
//        }
        else {
            return -1;
        }

//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE", null);
//        if(cursor != null && cursor.moveToFirst() )
//        {
//            cursor.moveToLast();
//            int id = cursor.getInt(cursor.getColumnIndex("NOTIFICATION_ID"));
//            cursor.close();
//
//
//            return id;
//        }
//        else{
//            return -1;
//        }


    }

    public void deleteBalance() {

        sqLiteDatabase.execSQL("delete from MAIN_BALANCE_TABLE");
    }

// LOGIN DATABSE METHODS

    public SQLiteAdapter openToReadLoginDataBase() throws SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWriteLoginDataBase() throws SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public long insertLoginInfo(String CustomerCode ,String MPIN,String DevicePin, String CreationDate , String ExpiryDate ) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN_CUSTOMER_CODE, CustomerCode);
        contentValues.put(LOGIN_MPIN, MPIN);
        contentValues.put(LOGIN_DEVICE_PIN, DevicePin);
        contentValues.put(LOGIN_CREATION_DATE, CreationDate);
        contentValues.put(LOGIN_EXPIRY_DATE, ExpiryDate);

        return sqLiteDatabase.insert(LOGIN_TABLE, null, contentValues);
    }

    public String getCustomerCode() {

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM LOGIN_TABLE ",null);
        String sCustomerCode = null;

        if(cursor.moveToNext()){
            sCustomerCode = cursor.getString(cursor.getColumnIndex("CUSTOMER_CODE"));

        }
        cursor.close();
        return  sCustomerCode;

    }

    public String getMPIN() {

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM LOGIN_TABLE ",null);
        String sMPIN = null;

        if(cursor.moveToNext()){
            sMPIN = cursor.getString(cursor.getColumnIndex("M_PIN"));

        }
        cursor.close();
        return  sMPIN;

    }

    public String getDevicePin() {

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM LOGIN_TABLE ",null);
            String sDevicePin = null;

        if(cursor.moveToNext()){
           sDevicePin = cursor.getString(cursor.getColumnIndex("DEVICE_PIN"));

        }
        cursor.close();
        return  sDevicePin;

    }

    public String getCreationDate() {

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM LOGIN_TABLE ",null);
        String sCreationDate = null;

        if(cursor.moveToNext()){
            sCreationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"));

        }
        cursor.close();
        return  sCreationDate;

    }

    public String getExpiryDate() {

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM LOGIN_TABLE ",null);
        String sExpiryDate = null;

        if(cursor.moveToNext()){
            sExpiryDate = cursor.getString(cursor.getColumnIndex("EXPIRY_DATE"));

        }
        cursor.close();
        return  sExpiryDate;

    }

    public void deleteLoginTable() {

        sqLiteDatabase.execSQL("delete from LOGIN_TABLE");
    }



    // NOTIFICATION TABLE

    public SQLiteAdapter openToReadNotificationDataBase() throws SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWriteNotificationDataBase() throws SQLException{
        sqLiteHelper = new SQLiteHelper(context,MYDATABASE_NAME,null,MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public long insertNotificationInfo(int NotificationID ,String Date,String Content, Boolean Status ) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIFICATION_ID, NotificationID);
        contentValues.put(DATE, Date);
        contentValues.put(CONTENT, Content);
        contentValues.put(STATUS, Status);
        return sqLiteDatabase.insert(NOTIOFICATION_TABLE, null, contentValues);
    }


    public String[] getContent() {


      //  Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE WHERE STATUS = 1",null);
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE ",null);
        String[] array = new String[cursor.getCount()];

        int i = 0;
        while(cursor.moveToNext()){
            String uname = cursor.getString(cursor.getColumnIndex(CONTENT));
            array[i] = uname;
            i++;
        }
        cursor.close();
        return  array;

    }

    public Map<String,Integer> getContentKeyValue() {


        //  Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE WHERE STATUS = 1",null);
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE ",null);
        String[] array = new String[cursor.getCount()];

        Map<String,Integer> notification= new HashMap<>();

        int i = 0;
        while(cursor.moveToNext()){
            String uname = cursor.getString(cursor.getColumnIndex(CONTENT));
            int id       = cursor.getInt(cursor.getColumnIndex(NOTIFICATION_ID));
            notification.put(uname,id);
            array[i] = uname;

            i++;
        }
        cursor.close();
        return  notification;

    }

//
//    public Map<String,Integer> getUpdatedReadValues()
//    {
//        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE WHERE STATUS = 0",null);
//        String[] array = new String[cursor.getCount()];
//        Map<String,Integer> UpdatedReadValues= new HashMap<>();
//
//        int i = 0;
//        while(cursor.moveToNext()){
//            String uname = cursor.getString(cursor.getColumnIndex(CONTENT));
//            int id       = cursor.getInt(cursor.getColumnIndex(STATUS));
//            UpdatedReadValues.put(uname,id);
//            array[i] = uname;
//
//            i++;
//        }
//
//        return UpdatedReadValues;
//    }

    public Map<String,Integer> getContentReadKeyValue() {



        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE WHERE STATUS = 0",null);
        String[] array = new String[cursor.getCount()];

        Map<String,Integer> notification= new HashMap<>();

        int i = 0;
        while(cursor.moveToNext()){
            String uname = cursor.getString(cursor.getColumnIndex(CONTENT));
            int id       = cursor.getInt(cursor.getColumnIndex("NOTIFICATION_ID"));
            notification.put(uname,id);
            array[i] = uname;

            i++;
        }
        cursor.close();
        return  notification;

    }

    public int getCountValue(){

       Cursor cursor = sqLiteDatabase.rawQuery("Select COUNT(*)FROM NOTIFICATION_TABLE ",null);
       cursor.moveToFirst();
       int count= cursor.getInt(0);
       cursor.close();
       return count;
   }

    public int getUnReadCountValue(){
        Cursor cursor = sqLiteDatabase.rawQuery("Select COUNT(*)FROM NOTIFICATION_TABLE WHERE STATUS = 0",null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int[] getReadStatusID() {
        Cursor cursor = sqLiteDatabase.rawQuery("Select * FROM NOTIFICATION_TABLE WHERE STATUS = 0 ", null);

        int[] array = new int[cursor.getCount()];

//        int i = 0;
//        if (cursor != null && cursor.moveToFirst()) {
//            while (cursor.moveToNext()) {
//
//                int id = cursor.getInt(cursor.getColumnIndex("NOTIFICATION_ID"));
//                array[i] = id;
//                Log.e("getReadStatusIDSQL",""+id);
//                i++;
//            }
//            cursor.close();
//
//
//        }

        int i = 0;
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(NOTIFICATION_ID));
            array[i] = id;

            i++;
        }
        cursor.close();
        return  array;

    }

    public Boolean getStatus() {

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE",null);
        Boolean sStatus = false  ;

        if(cursor.moveToNext()){
            sStatus = (cursor.getInt(cursor.getColumnIndex(STATUS))==1);
        }
        cursor.close();
        return  sStatus;

    }

    public int lastNotificationID(){

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION_TABLE", null);
        if(cursor != null && cursor.moveToFirst() )
        {
            cursor.moveToLast();
            int id = cursor.getInt(cursor.getColumnIndex("NOTIFICATION_ID"));
            cursor.close();


            return id;
        }
        else{
            return -1;
        }
    }

    public void updateStatus(int NotificationID) {

        ContentValues cv = new ContentValues();
        cv.put(STATUS, true);
        String where = NOTIFICATION_ID + "=" + NotificationID;
        sqLiteDatabase.update(NOTIOFICATION_TABLE, cv, where, null);
    }

    public void deleteNotificationTable() {

        sqLiteDatabase.execSQL("delete from NOTIFICATION_TABLE");
    }

    public class SQLiteHelper extends SQLiteOpenHelper {


            public SQLiteHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }


            @Override
            public void onCreate(SQLiteDatabase db) {
                // TODO Auto-generated method stub


                db.execSQL(SCRIPT_CREATE_DATABASE);
                db.execSQL(LOGIN_CREATE_DATABASE);
                db.execSQL(NOTIFICATION_CREATE_DATABASE);
                db.execSQL(BALANCE_CREATE_DATABASE);

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // TODO Auto-generated method stub

            }

        }




}

