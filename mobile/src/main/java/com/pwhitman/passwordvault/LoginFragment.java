package com.pwhitman.passwordvault;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pwhitman.passwordvault.R;

import java.util.UUID;

/**
 * Created by Philip on 5/13/2015.
 */
public class LoginFragment extends Fragment {

    private EditText mMasterPassword, mVerifyPassword;
    private Button mEnterBtn;
    private String masterPass;
    private String verifyPass;
    private boolean exists = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        //Get shared preferences and check to see if the preferences exist
        if(LoginUtility.preferencesExist(getActivity())){
            exists = true;
        }

        mMasterPassword = (EditText)v.findViewById(R.id.login_master_password_edittext);
        mMasterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                masterPass = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //If preferences exist display only master password field
        //If they do not exist, display Master and Verify to create preferences

        if(exists){
            //Hide verify Pass
            mVerifyPassword = (EditText)v.findViewById(R.id.login_verify_password_edittext);
            mVerifyPassword.setVisibility(View.INVISIBLE);

        }else{
            mVerifyPassword = (EditText)v.findViewById(R.id.login_verify_password_edittext);
            mVerifyPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    verifyPass = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        mEnterBtn = (Button)v.findViewById(R.id.login_enter_btn);
        mEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exists){
                    if(masterPass == null){
                        mMasterPassword.setError("Password is empty");
                        return;
                    }
                    //Check the stored password against the user inputted password
                    if(LoginUtility.hash(masterPass).equals(LoginUtility.getStoredPass(getActivity()))){
                        Intent intent = new Intent(getActivity(), PasswordListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        mMasterPassword.setError("Login failed, try again");
                    }
                }else{
                    //Create user preferences and save password
                    if(masterPass == null){
                        mMasterPassword.setError("Password is empty");
                        return;
                    }
                    if(verifyPass == null){
                        mVerifyPassword.setError("Password is empty");
                    }
                    if(masterPass.equals(verifyPass)){
                        SharedPreferences.Editor e = getActivity().getSharedPreferences(LoginUtility.PREFERENCES, Context.MODE_PRIVATE).edit();
                        e.putString(LoginUtility.PREF_USER_ID, UUID.randomUUID().toString());
                        e.putString(LoginUtility.PREF_PASSWORD, LoginUtility.hash(masterPass));
                        e.putBoolean(LoginUtility.PREF_SORT_DEFAULT, true);
                        e.putBoolean(LoginUtility.PREF_SORT_ALPHA, false);
                        e.putBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, false);
                        e.putBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, false);
                        e.commit();
                        Intent intent = new Intent(getActivity(), TutorialActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        mVerifyPassword.setError("Passwords do not match.");
                    }
                }
            }
        });

        return v;
    }
}
