package com.android.iconchanger;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;

public class MainActivity extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		creatShortCut("cn.etouch.ecalendar");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void creatShortCut(String packageName){
		try {
			Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,getResources().getString(R.string.app_name));
			shortcutIntent.putExtra("duplicate", false); // 不允许重复创建
			Intent intent = new Intent(Intent.ACTION_MAIN);
			PackageManager pm = getPackageManager();
			PackageInfo pkgInfo = pm.getPackageInfo(packageName, 0);
			intent.setComponent(new ComponentName(pkgInfo.packageName,""));
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
					Intent.ShortcutIconResource.fromContext(this,R.drawable.ic_launcher));
			sendBroadcast(shortcutIntent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
