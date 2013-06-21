package com.android.iconchanger;

import com.android.iconchanger.utils.L;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class IconGridViewAdapter extends BaseAdapter {

	private Context mComtext;
	private int mStyle;
	private int[] mRes;
	private Holder holder = null;
	
	public static final int STYLE_MARCE = 2001;
	public static final int STYLE_MATT = 2002;
	public static final int STYLE_AMBIT = 2003;

	public IconGridViewAdapter(Context context, int style) {
		super();
		mComtext = context;
		mStyle = style;
		
		switch(mStyle){
		case STYLE_MARCE:
			mRes = marce_res;
			L.i("switch 1:" + mStyle);
			break;
		case STYLE_MATT:
			mRes = matt_res;
			L.i("switch 2:" + mStyle);
			break;
		case STYLE_AMBIT:
			mRes = ambit_res;
			L.i("switch 3:" + mStyle);
			break;
		default:
			break;
		}
	}

	@Override
	public int getCount() {
		return mRes.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
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
		if (mRes != null && position < mRes.length){
			holder.imageView.setImageResource(mRes[position]);
		}
		return convertView;
	}

	class Holder {
		ImageView imageView;
	}
	
	public static final int[] marce_res = new int[]{R.drawable.marce_appstore,R.drawable.marce_calendar,
		R.drawable.marce_camera,R.drawable.marce_clock,R.drawable.marce_contacts,
		R.drawable.marce_ipod,R.drawable.marce_itunes,R.drawable.marce_mail,
		R.drawable.marce_maps,R.drawable.marce_music,R.drawable.marce_notes,
		R.drawable.marce_phone,R.drawable.marce_photos,R.drawable.marce_safari,
		R.drawable.marce_settings,R.drawable.marce_stocks,R.drawable.marce_text,
		R.drawable.marce_videos,R.drawable.marce_weather,R.drawable.marce_youtube};
	
	public static final int[] matt_res = new int[]{R.drawable.matt_addressbook,R.drawable.matt_appstore,
		R.drawable.matt_calculator,R.drawable.matt_mail,R.drawable.matt_maps,
		R.drawable.matt_messages,R.drawable.matt_mobilecal,R.drawable.matt_mobilestore,
		R.drawable.matt_mobiletimer,R.drawable.matt_notes,R.drawable.matt_phone,
		R.drawable.matt_preferences,R.drawable.matt_safari,R.drawable.matt_stocks,
		R.drawable.matt_winterboard,R.drawable.matt_youtube};
	
	public static final int[] ambit_res = new int[]{R.drawable.ambit_calculator,R.drawable.ambit_calendar,
		R.drawable.ambit_camera,R.drawable.ambit_clock,R.drawable.ambit_cydia,R.drawable.ambit_ifile,
		R.drawable.ambit_itunes,R.drawable.ambit_mail,R.drawable.ambit_maps,R.drawable.ambit_mxtube,
		R.drawable.ambit_netnews,R.drawable.ambit_phone,R.drawable.ambit_stack,R.drawable.ambit_things,
		R.drawable.ambit_twitterrific,R.drawable.ambit_weather,R.drawable.ambit_winterboard,
		R.drawable.ambit_youtube};
	
	/**
	 * icon styles:
	 * marce: 20 Ciceronian @ Marcelo Marfil http://findicons.com/pack/2101/ciceronian
	 * matt: 16 Austerity @ Mattrich http://findicons.com/pack/2206/austerity
	 * ambit: 18 Aluminum @ AmbitDesigns  http://findicons.com/pack/2130/aluminum
	 */
}
