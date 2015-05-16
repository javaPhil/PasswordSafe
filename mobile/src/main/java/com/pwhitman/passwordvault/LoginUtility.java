package com.pwhitman.passwordvault;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by Philip on 5/13/2015.
 */
public class LoginUtility {
    private static final String TAG = "LoginUtility";

    public static final String PREF_PASSWORD = "password";
    public static final String PREF_USER_ID = "userUUID";
    public static final String PREF_SORT_ALPHA = "sortAlpha";
    public static final String PREF_SORT_ALPHA_REVERSE = "sortAlphaReverse";
    public static final String PREF_SORT_DATE = "sortDate";
    public static final String PREF_SORT_DATE_REVERSE = "sortDateReverse";
    public static final String PREF_SORT_DEFAULT = PREF_SORT_DATE;
    public static final String PREFERENCES = "com.pwhitman.neonpasswordsafe";

    public static String hash(String input){
//        Log.i(TAG, "Input string: " + input);
        final String algorithm = "SHA-256";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(algorithm);
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
//            Log.i(TAG, "Hashed string: " + hexString.toString());
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean preferencesExist(Context c){
        boolean exists = false;

        SharedPreferences prefs = c.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String userId = prefs.getString(PREF_USER_ID, null);
        String pass = prefs.getString(PREF_PASSWORD, null);

        if(userId != null && pass != null){
            exists = true;
        }

        return exists;
    }

    public static String getStoredPass(Context c){
        SharedPreferences prefs = c.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(PREF_PASSWORD, null);
    }

}
