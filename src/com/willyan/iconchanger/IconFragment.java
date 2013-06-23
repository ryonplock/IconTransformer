package com.willyan.iconchanger;

import com.willyan.iconchanger.R;
import com.willyan.iconchanger.utils.L;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public final class IconFragment extends Fragment {

	private int mStyle = -1;
	private GridView iconsView;
	private IconItemClickListener mIconItemClick;
	
    public static IconFragment newInstance(int style, IconItemClickListener iconItemClick) {
    	IconFragment fragment = new IconFragment();
        fragment.mStyle = style;
        fragment.mIconItemClick = iconItemClick;
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View view = inflater.inflate(R.layout.viewpager, null);
    	iconsView = (GridView) view.findViewById(R.id.icons_grid);
		//TODO
    	L.i("onCreateView " + mStyle);
		IconGridViewAdapter adapter = new IconGridViewAdapter(getActivity(), mStyle);
		iconsView.setAdapter(adapter);
		iconsView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				L.e("IconFragment onItemClick " + mStyle + ":" + arg2);
				mIconItemClick.onIconItemClick(mStyle, arg2);
			}

		});

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    /**
     * Callback when icon in grid view is clicked.
     */
    public interface IconItemClickListener{
    	void onIconItemClick(int style, int position);
    } 
}
