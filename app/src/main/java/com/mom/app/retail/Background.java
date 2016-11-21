package com.mom.app.retail;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;


public class Background extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        InsertContact insertContact= new InsertContact();
        insertContact.execute();
    }

    private class InsertContact extends AsyncTask<Void,Void,String> {
        SQLiteAdapter sql = new SQLiteAdapter(Background.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            sql.openToWrite();
            sql.delete();

        }

        @Override
        protected String doInBackground(Void... params) {

            ContentResolver cr = getApplicationContext().getContentResolver(); //Activity/Application android.content.Context
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if(cursor.moveToFirst())
            {

                do
                {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                        while (pCur.moveToNext())
                        {
                            String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String name = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            String contacts=contactNumber;
                            String testReplace=null;
                            if(contacts.length()>=10) {
                                if ((contacts.startsWith("+91") || (contacts.startsWith("0")))) {
                                    if(contacts.startsWith("0"))
                                    {
                                        String string;
                                        string=contacts.substring(1);
                                        testReplace=string.replaceAll("\\s", "").replaceAll("\\p{P}", "");
                                    }
                                    else if(contacts.startsWith("+91")) {
                                        testReplace = contacts.replace("+91", "").replaceAll("\\s", "").replaceAll("\\p{P}", "");
                                    }
                                }
                                else{
                                    testReplace = contacts.replaceAll("\\s","").replaceAll("-", "").replaceAll("\\p{P}","");
                                }
                            }

                            if(testReplace!=null) {
                                if (testReplace.length() > 10) {
                                   // Log.e("testReplace", testReplace);
                                    testReplace = testReplace.substring(testReplace.length() - 10);
                                }
                            }
                            sql.insert(testReplace + "      " + name);
                            break;
                        }
                        pCur.close();


                    }

                } while (cursor.moveToNext()) ;
            }

            cursor.close();


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            sql.close();
           stopSelf();
        }
    }



}
