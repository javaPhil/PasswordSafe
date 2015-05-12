package com.pwhitman.neonpasswordsafe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Philip on 1/4/2015.
 */
public class PasswordPagerActivity extends FragmentActivity{

    private ViewPager mViewPager;
    private ArrayList<Password> mPasswords;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mPasswords = PasswordStation.get(this).getPasswords();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {
                Password password = mPasswords.get(i);
                return PasswordFragment.newInstance(password.getId());
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
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
