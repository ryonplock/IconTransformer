package com.android.iconchanger;

import com.android.iconchanger.IconFragment.IconItemClickListener;
import com.android.iconchanger.indicator.IconPagerAdapter;
import com.android.iconchanger.utils.L;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class IconViewPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    
	protected static final int[] STYLE = new int[] {
    	IconGridViewAdapter.STYLE_MARCE, 
    	IconGridViewAdapter.STYLE_MATT, 
    	IconGridViewAdapter.STYLE_AMBIT };
    
    protected static final int[] ICONS = new int[] {
            R.drawable.marce_phone,
            R.drawable.matt_phone,
            R.drawable.ambit_phone
    };
    
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

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

}