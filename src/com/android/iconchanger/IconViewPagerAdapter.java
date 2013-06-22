package com.android.iconchanger;

import com.android.iconchanger.IconFragment.IconItemClickListener;
import com.android.iconchanger.utils.L;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class IconViewPagerAdapter extends FragmentPagerAdapter{
    
	protected static final int[] STYLE = IconGridViewAdapter.STYLE;
    
    private IconItemClickListener mIconClick;
    private int mCount = STYLE.length;

    public IconViewPagerAdapter(IconItemClickListener iconClick, FragmentManager fm) {
        super(fm);
        mIconClick = iconClick;
    }

    @Override
    public Fragment getItem(int position) {
    	L.i("IconViewPagerAdapter getItem:"  + position);
        return IconFragment.newInstance(STYLE[position % STYLE.length], mIconClick);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return "";
    }

}