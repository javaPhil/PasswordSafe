package com.pwhitman.passwordvault;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

/**
 * Created by Philip on 5/18/2015.
 */
public class TutorialSlidesFragmentAdapter extends FragmentPagerAdapter implements
        IconPagerAdapter {

    private int[] TutorialImages = new int[]{
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fourth,
            R.drawable.fifth,
            R.drawable.sixth
    };

    protected static final int[] ICONS = new int[] {
            R.drawable.vpi__tab_indicator,
            R.drawable.vpi__tab_indicator,
            R.drawable.vpi__tab_indicator,
            R.drawable.vpi__tab_indicator,
            R.drawable.vpi__tab_indicator,
            R.drawable.vpi__tab_indicator, };

    private int mCount = TutorialImages.length;

    public TutorialSlidesFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialFragment.newInstance(TutorialImages[position]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}