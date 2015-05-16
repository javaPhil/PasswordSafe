package com.pwhitman.passwordvault;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Philip on 5/7/2015.
 */
public class PasswordUtility {
    private static final String TAG = "PasswordUtility";

    private static final String KEY = "PasswordGeneratorKey";

    private static final SecureRandom random = new SecureRandom();
    private static SecretKeySpec sks = null;

    private PasswordUtility(){
        try{

            byte[] key = KEY.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest();
            key = Arrays.copyOf(key, 16);
            sks = new SecretKeySpec(key, "AES");
        }catch(Exception e){
            Log.e(TAG, "Error with AES key generation", e);
        }
    }

    public static PasswordUtility getInstance(){
        return new PasswordUtility();
    }

    public static String generateRandomString(){
        return new BigInteger(60, random).toString(32);
    }

    public String encryptString(String input){
        if(input == null  ||  input.isEmpty()) return null;

//        Log.i(TAG, "inputString: " + input);

        byte[] encodedBytes = null;
        try{
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(input.getBytes("UTF-8"));
        }catch(Exception e){
            Log.e(TAG, "AES encryption error", e);
        }
        return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
    }

    public String decryptString(String inputString){
        if(inputString == null || inputString.trim().isEmpty()) return "";
//        Log.i(TAG, "decryptString: " + inputString);
        byte[] decodedBytes = null;
        try{
            if(sks != null){
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.DECRYPT_MODE, sks);
                byte[] inputBytes = inputString.getBytes("UTF-8");
                decodedBytes = c.doFinal(Base64.decode(inputBytes, Base64.DEFAULT));

            }

        }catch(Exception e){
            Log.e(TAG, "AES decryption error: ",e);
        }
        return decodedBytes != null ? new String(decodedBytes) : "";
    }


}
