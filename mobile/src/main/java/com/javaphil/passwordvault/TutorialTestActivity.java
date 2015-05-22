package com.javaphil.passwordvault;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Philip on 5/21/2015.
 */
public class TutorialTestActivity extends Activity{

    ViewPager mViewPager;
    SlideShowPagerAdapter mSlideShowPagerAdapter;
    CirclePageIndicator mIndicator;


    int[] mResources = {
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fourth,
            R.drawable.fifth,
            R.drawable.sixth
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        getActionBar().hide();

        // Instantiate a ViewPager and a PagerAdapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mSlideShowPagerAdapter = new SlideShowPagerAdapter(this);
        mViewPager.setAdapter(mSlideShowPagerAdapter);

    }


    class SlideShowPagerAdapter extends PagerAdapter {

        Activity mActivity;
        LayoutInflater mLayoutInflater;

        public SlideShowPagerAdapter(Activity activity) {
            mActivity = activity;
            mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.fragment_tutorial, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

//            imageView.setImageResource(mResources[position]);
            imageView.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), mResources[position], 350, 350));
//            imageView.setImageDrawable(BitmapUtils.getScaledDrawable(mActivity, mResources[position]));
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

}
