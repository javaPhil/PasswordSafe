package com.pwhitman.passwordvault;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.pwhitman.passwordvault.R;

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

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {
                Password p = mPasswords.get(i);
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
                if(p.getTitle() != null && !p.getTitle().trim().isEmpty()){
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
                mViewPager.setCurrentItem(i);
                mCurrentPass = mPasswords.get(i);
                if(mCurrentPass.getTitle() != null && !mCurrentPass.getTitle().trim().isEmpty()){
                    setTitle(mCurrentPass.getTitle());
                }else{
                    setTitle(R.string.new_password);
                }
                break;
            }
        }

        if(mCurrentPass != null && (mCurrentPass.getTitle() == null || mCurrentPass.getTitle().isEmpty())) {
            mViewPager.setPagingEnabled(false);
        }else{
            mViewPager.setPagingEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if(mCurrentPass != null && (mCurrentPass.getTitle() == null || mCurrentPass.getTitle().trim().isEmpty()))
            PasswordStation.get(mViewPager.getContext()).deletePassword(mCurrentPass);

        super.onBackPressed();

    }
}
