package com.android.iconchanger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.android.iconchanger.animation.SwingBottomInAnimationAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AppListActivity extends Activity {
	
	private ListView listView;
	private ArrayList<AppInfo> list = new ArrayList<AppInfo>();
	private ProgressDialog pdialog;
	private AppListAdapter mAdapter;
	private PackageManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.applist);
		manager = getPackageManager();
		Init();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void Init() {
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String appName = list.get(position).appName;
				String packageName = list.get(position).packageName;
				String activityName = list.get(position).activityName;
				Log.i("ShortCut", "APP "  + appName + " " + packageName + " " + activityName);
				creatShortCut(appName, packageName, activityName);
//				Intent in = new Intent();
//				in.putExtra("appName", list.get(position).title);
//				in.putExtra("packageName", list.get(position).packageName);
//				in.putExtra("activityName", list.get(position).ActivityName);
//				MainActivity.this.setResult(RESULT_OK, in);
//				MainActivity.this.finish();
			}
		});
		loadApplications();
	}
	
	private void loadApplications() {
		new Thread() {
			public void run() {
				handler.sendEmptyMessage(1);
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
				handler.sendEmptyMessage(2);

			}
		}.start();
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				pdialog = new ProgressDialog(AppListActivity.this);
				pdialog.setCanceledOnTouchOutside(false);
				pdialog.setMessage(getResources().getString(R.string.loading_app));
				pdialog.show();
				break;
			case 2:
				pdialog.cancel();
				mAdapter = new AppListAdapter();
				SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
				swingBottomInAnimationAdapter.setListView(listView);
				listView.setAdapter(swingBottomInAnimationAdapter);
				break;
			}
		}
	};
	
	public class AppListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				LayoutInflater l = getLayoutInflater();
				convertView = l.inflate(R.layout.applist_item, null);
				holder.appImg = (ImageView) convertView
						.findViewById(R.id.appImg);
				holder.appName = (TextView) convertView
						.findViewById(R.id.appName);
				holder.ckb = (CheckBox) convertView
						.findViewById(R.id.ckb_music);
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
			
			holder.ckb.setChecked(false);
			return convertView;
		}
	}


	public void creatShortCut(String appName,String packageName,String activityName){
		try {
			Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
			shortcutIntent.putExtra("duplicate", false); // 不允许重复创建
			
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setComponent(new ComponentName(packageName,activityName));
			
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
					Intent.ShortcutIconResource.fromContext(this,R.drawable.ic_launcher));
			sendBroadcast(shortcutIntent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class Holder {
		public ImageView appImg;
		public TextView appName;
		public CheckBox ckb;
	}
	
	class AppInfo {
		public String appName = "";
		public Drawable icon = null;
		public String packageName = "";
		public String activityName = "";
	}

}
