package com.pwhitman.neonpasswordsafe;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Philip on 1/4/2015.
 */
public class PasswordPagerActivity extends FragmentActivity{
    private static final String TAG = "PasswordPagerActivity";

    private CustomViewPager mViewPager;
    private ArrayList<Password> mPasswords;
    private Password mCurrentPass;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mViewPager = new CustomViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        setTitle(R.string.new_password);
        mPasswords = PasswordStation.get(this).getPasswords();
        Log.i(TAG, "onCreate called");

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {

                Password p = mPasswords.get(i);
                Log.i(TAG, "getItem called title: " + p.getTitle());
                return PasswordFragment.newInstance(p.getId());
            }

            @Override
            public int getCount() {
                return mPasswords.size();
            }
        });




        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                Password p = mPasswords.get(i);
                mCurrentPass = p;
                if(p.getTitle() != null){
                    setTitle(p.getTitle());
                }else{
                    setTitle(R.string.new_password);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        UUID passId = (UUID)getIntent().getSerializableExtra(PasswordFragment.EXTRA_PASSWORD_ID);
        for(int i = 0; i <  mPasswords.size(); i++){
            if(mPasswords.get(i).getId().equals(passId)){
                setTitle(mPasswords.get(i).getTitle());
                mViewPager.setCurrentItem(i);
                mCurrentPass = mPasswords.get(i);
                Log.i(TAG, "for loop, setting current item called: title: " + mPasswords.get(i).getTitle());
                break;
            }
        }

        if(mCurrentPass != null && (mCurrentPass.getTitle() == null || mCurrentPass.getTitle().isEmpty())) {
//            Toast.makeText(mViewPager.getContext(), "Please enter a title or delete this password before swiping", Toast.LENGTH_LONG);
            mViewPager.setPagingEnabled(false);
        }else{
            mViewPager.setPagingEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
//        final boolean backPressed = true;
//        if(mCurrentPass != null && (mCurrentPass.getTitle() == null || mCurrentPass.getTitle().trim().isEmpty())){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(R.string.alert_navigate_home_title_empty_title);
//            builder.setMessage(R.string.alert_navigate_home_title_empty_message);
//            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    PasswordStation.get(mViewPager.getContext()).deletePassword(mCurrentPass);
//
//                }
//
//            });
//            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //Do nothing and go back
//                final boolean backPressed = false;
//                }
//            });
//            builder.setIcon(android.R.drawable.ic_dialog_alert);
//            AlertDialog alert = builder.create();
//
//            alert.show();
//
//        }
//        if(backPressed) super.onBackPressed();
        if(mCurrentPass != null && (mCurrentPass.getTitle() == null || mCurrentPass.getTitle().trim().isEmpty()))
            PasswordStation.get(mViewPager.getContext()).deletePassword(mCurrentPass);
        super.onBackPressed();

    }
}
