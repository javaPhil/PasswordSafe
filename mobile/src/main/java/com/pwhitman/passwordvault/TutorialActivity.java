package com.pwhitman.passwordvault;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.PageIndicator;

/**
 * Created by Philip on 5/18/2015.
 */
public class TutorialActivity extends FragmentActivity {

    private ViewPager mPager;
    private PageIndicator mIndicator;
    private TutorialSlidesFragmentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();

        mPager = new ViewPager(this);
        mPager.setId(R.id.pager);
        setContentView(mPager);

        FragmentManager fm = getSupportFragmentManager();
        mAdapter = new TutorialSlidesFragmentAdapter(fm);
        mPager.setAdapter(mAdapter);
//        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator = new CirclePageIndicator(this);

        mIndicator.setViewPager(mPager);
        ((CirclePageIndicator)mIndicator).setSnap(true);
        mIndicator
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrolled(int position,
                                               float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });

    }



    @Override
    public void onBackPressed() {
        if(mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }else{
            mPager.setCurrentItem(mPager.getCurrentItem()-1);
        }
    }


}
