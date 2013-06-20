package com.android.iconchanger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {

	private Context mComtext;
	private Holder holder = null;

	public GridViewAdapter(Context context) {
		super();
		mComtext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mComtext);
			convertView = inflater.inflate(R.layout.grid_item, null);
			holder = new Holder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}

	class Holder {
		ImageView imageView;
	}
}
