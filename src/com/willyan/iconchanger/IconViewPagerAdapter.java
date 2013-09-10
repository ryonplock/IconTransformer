package com.willyan.iconchanger;

import java.util.ArrayList;

import com.willyan.iconchanger.IconFragment.IconItemClickListener;
import com.willyan.iconchanger.utils.L;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

class IconViewPagerAdapter extends FragmentPagerAdapter{
    
	protected static final int[] STYLE = IconGridViewAdapter.STYLE;
    
    private IconItemClickListener mIconClick;
    private final int mCount = STYLE.length;
    private ArrayList<IconFragment> mIconFragmentList = new ArrayList<IconFragment>();

    public IconViewPagerAdapter(IconItemClickListener iconClick, FragmentManager fm) {
        super(fm);
        mIconClick = iconClick;
        
        mIconFragmentList.add(IconFragment.newInstance(STYLE[0 % STYLE.length], mIconClick));
        notifyDataSetChanged();
        
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				for (int i=1 ; i<mCount; i++){
					 mIconFragmentList.add(IconFragment.newInstance(STYLE[i % STYLE.length], mIconClick));
					 notifyDataSetChanged();
				}
			}
		}, 100);
    }

    @Override
    public Fragment getItem(int position) {
    	L.p("IconViewPagerAdapter", "getItem", "position: " + position);
        return mIconFragmentList.get(position);
    }

    @Override
	public void destroyItem(ViewGroup container, int position, Object object) {
    	// TODO remove 
    	L.p("IconViewPagerAdapter", "destroyItem", "position: " + position);
		super.destroyItem(container, position, object);
	}

	@Override
    public int getCount() {
        return mIconFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return "";
    }

}