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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;
import com.willyan.iconchanger.animation.AlphaInAnimationAdapter;
import com.willyan.iconchanger.utils.L;

public class PickAppActivity extends Activity {
	
	private ListView appList;
	private RelativeLayout progressBar;
	private ArrayList<AppInfo> infoList = new ArrayList<AppInfo>();
	private BaseAdapter mAdapter;
	private PackageManager manager;
	private MenuPopupWindows mMenuPopup;
	private Button btn_menu;
	private TextView tx_title;
	private final static int START_LOADING = 0x01;
	private final static int STOP_LOADING = 0x02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_pickapp);
		super.onCreate(savedInstanceState);
		appList = (ListView) findViewById(R.id.applist);
		appList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String appName = infoList.get(position).appName;
				String packageName = infoList.get(position).packageName;
				String activityName = infoList.get(position).activityName;
				L.i("APP "  + appName + " " + packageName + " " + activityName);
				Intent intent = new Intent();
				intent.putExtra("appName", appName);
				intent.putExtra("packageName", packageName);
				intent.putExtra("activityName", activityName);
				intent.setClass(PickAppActivity.this, PickIconActivity.class);
				startActivity(intent);
				//statistic analysis for Picked App
				MobclickAgent.onEvent(getApplicationContext(), "PickedAppName", appName);
			}
		});
		progressBar = (RelativeLayout) findViewById(R.id.progress_bar);
		progressBar.setVisibility(View.INVISIBLE);
		
		btn_menu = (Button) findViewById(R.id.menu);
		mMenuPopup = new MenuPopupWindows(btn_menu, this);
		
//		tx_title = (TextView) findViewById(R.id.title);
//		Typeface font_source = Typeface.createFromAsset(getResources().getAssets(),"source-code-pro-regular.ttf");
//		tx_title.setTypeface(font_source);
		loadAppList();
	}
	
	/**
	 * Load installed apps in background.
	 */
	private void loadAppList() {
		new Thread() {
			public void run() {
				handler.sendEmptyMessage(START_LOADING);
				infoList.clear();
				Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
				mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				manager = getPackageManager();
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
						infoList.add(appInfo);
//						L.i("> " + i + "<: " + appInfo.toString());
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
				mAdapter = new ListAdapter();
//				listView.setAdapter(mAdapter);
				AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(mAdapter);
				animationAdapter.setAbsListView(appList);
				appList.setAdapter(animationAdapter);
				break;
			default:
				break;
			}
		}
	};
	
	/** ListView Adapter */
	private class ListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infoList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return infoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
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
			AppInfo fb = infoList.get(position);
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
		set.setDuration(200);
		set.start();
	}
	
	public void onMenuClicked(View view){
		//TODO 
		mMenuPopup.show();
	}

	class Holder {
		public ImageView appImg;
		public TextView appName;
	}
	
	class AppInfo {
		public String appName = "";
		public Drawable icon = null;
		public String packageName = "";
		public String activityName = "";
		@Override
		public String toString() {
			return "AppInfo [appName=" + appName + ", packageName=" + packageName + ", activityName="
					+ activityName + "]";
		}
		
	}

}
