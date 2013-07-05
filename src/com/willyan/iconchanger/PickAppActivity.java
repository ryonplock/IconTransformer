package com.willyan.iconchanger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.willyan.iconchanger.animation.SwingInAnimationAdapter;
import com.willyan.iconchanger.utils.L;

public class PickAppActivity extends Activity {
	
	private ListView listView;
//	private TextView title;
	private RelativeLayout progressBar;
	private ArrayList<AppInfo> list = new ArrayList<AppInfo>();
	private AppListAdapter mAdapter;
	private PackageManager manager;
	private final static int START_LOADING = 0x01;
	private final static int STOP_LOADING = 0x02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickapp);
		manager = getPackageManager();
		listView = (ListView) findViewById(R.id.applist);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String appName = list.get(position).appName;
				String packageName = list.get(position).packageName;
				String activityName = list.get(position).activityName;
				L.i("APP "  + appName + " " + packageName + " " + activityName);
				Intent intent = new Intent();
				intent.putExtra("appName", appName);
				intent.putExtra("packageName", packageName);
				intent.putExtra("activityName", activityName);
				intent.setClass(PickAppActivity.this, PickIconActivity.class);
				startActivity(intent);
			}
		});
//		title = (TextView) findViewById(R.id.title);
//		Typeface font = Typeface.createFromAsset(getAssets(), "Segoe.ttf");
//		title.setTypeface(font);
		progressBar = (RelativeLayout) findViewById(R.id.progress_bar);
		progressBar.setVisibility(View.INVISIBLE);
		loadAppList();
	}
	
	/**
	 * Load installed apps in background.
	 */
	private void loadAppList() {
		new Thread() {
			public void run() {
				handler.sendEmptyMessage(START_LOADING);
				list.clear();
				Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
				mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				final List<ResolveInfo> apps = manager.queryIntentActivities(
						mainIntent, 0);
				Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
				if (apps != null) {
					int count = apps.size();
					for (int i = 0; i < count; i++) {
						AppInfo appInfo = new AppInfo();
						ResolveInfo info = apps.get(i);
						appInfo.appName = String.valueOf(info.loadLabel(manager));
						appInfo.icon = info.activityInfo.loadIcon(manager);
						appInfo.packageName = info.activityInfo.packageName;
						appInfo.activityName = info.activityInfo.name;
						list.add(appInfo);
					}// end for
				}
				handler.sendEmptyMessage(STOP_LOADING);

			}
		}.start();
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START_LOADING:
				progressBar.setVisibility(View.VISIBLE);
				break;
			case STOP_LOADING:
				hideView(progressBar);
				mAdapter = new AppListAdapter();
//				listView.setAdapter(mAdapter);
				SwingInAnimationAdapter swingBottomInAnimationAdapter = new SwingInAnimationAdapter(mAdapter);
				swingBottomInAnimationAdapter.setListView(listView);
				listView.setAdapter(swingBottomInAnimationAdapter);
				break;
			}
		}
	};
	
	/** ListView Adapter */
	public class AppListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				LayoutInflater l = getLayoutInflater();
				convertView = l.inflate(R.layout.item_applist, null);
				holder.appImg = (ImageView) convertView
						.findViewById(R.id.appImg);
				holder.appName = (TextView) convertView
						.findViewById(R.id.appName);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			AppInfo fb = list.get(position);
			holder.appName.setText(fb.appName);
			if (fb.icon != null) {
				holder.appImg.setImageDrawable(fb.icon);
			} else {
				holder.appImg.setImageResource(R.drawable.ic_launcher);
			}
			
			return convertView;
		}
	}
	
	private void hideView(View view) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0);
		AnimatorSet set = new AnimatorSet();
		set.play(animator);
		set.setDuration(300);
		set.start();
	}
	
	public void onMenuClicked(View view){
		//TODO 
	}


	class Holder {
		public ImageView appImg;
		public TextView appName;
		public ImageView next;
	}
	
	class AppInfo {
		public String appName = "";
		public Drawable icon = null;
		public String packageName = "";
		public String activityName = "";
	}

}
