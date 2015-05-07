package com.pwhitman.neonpasswordsafe;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Philip on 1/4/2015.
 */
public class PasswordStation {

    private static PasswordStation sPasswordStation;
    private Context mAppContext;
    private ArrayList<Password> mPasswords;

    private PasswordStation(Context appContext){
        mAppContext = appContext;
        mPasswords = new ArrayList<Password>();
        for (int i = 0; i < 100; i++){
            Password p = new Password();
            p.setTitle("Password # " + i);
            //TODO fix this method
            mPasswords.add(p);
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
