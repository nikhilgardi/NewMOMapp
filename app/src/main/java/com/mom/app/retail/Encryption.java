package com.mom.app.retail;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;


public class Encryption {

    Context context;

    public Encryption(Context context){
        this.context=context;
    }


    public String encrypt(String s)
    {
        String sEncryptedData=null;
        try
        {
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open("fonts/MomPublicKey.cer");
            } catch (IOException e) {
                e.printStackTrace();
            }



            InputStream caInput = new BufferedInputStream(inputStream);
            X509Certificate cert = null;
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            cert = (X509Certificate) cf.generateCertificate(caInput);
            cert.getSerialNumber();
            PublicKey pk = cert.getPublicKey();

            byte[] byteData = s.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            byte[] encryptedData = cipher.doFinal(byteData);
            sEncryptedData=android.util.Base64.encodeToString(encryptedData,android.util.Base64.DEFAULT);



        } catch(Exception e)
        {
            e.printStackTrace();
        }

        return sEncryptedData;
    }


    public String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String string;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("UTF-8"),0,text.getBytes().length);
        byte[] sha1hash = md.digest();

        string= Base64.encodeToString(sha1hash, Base64.DEFAULT);
        return string;

    }
}
