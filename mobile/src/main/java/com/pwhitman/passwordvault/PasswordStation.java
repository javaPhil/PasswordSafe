package com.pwhitman.passwordvault;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Philip on 1/4/2015.
 */
public class PasswordStation {

    private static final String TAG = "PasswordStation";
    private static final String FILENAME = "passwords.json";

    private PasswordJSONSerializer mSerializer;

    private static PasswordStation sPasswordStation;
    private Context mAppContext;
    private ArrayList<Password> mPasswords;

    private PasswordStation(Context appContext){

        mAppContext = appContext;
        mSerializer = new PasswordJSONSerializer(mAppContext, FILENAME);
        try{
            mPasswords = mSerializer.loadPasswords();
        }catch(Exception e){
            Log.e(TAG, "Error loading passwords: ", e);
        }
    }

    public void addPassword(Password p){
        mPasswords.add(p);
    }

    public void deletePassword(Password p){
        mPasswords.remove(p);
    }

    public boolean savePasswords(){
        try{
            mSerializer.savePasswords(mPasswords);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Error saving passwords: ",e);
            return false;
        }
    }



    public static PasswordStation get(Context c){
        if(sPasswordStation == null){
            sPasswordStation = new PasswordStation(c.getApplicationContext());
        }
        return sPasswordStation;
    }

    public ArrayList<Password> getPasswords(){
        return mPasswords;
    }

    public Password getPassword(UUID id){
        for(Password p : mPasswords){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
}
