package com.willyan.iconchanger;

import com.umeng.analytics.MobclickAgent;
import com.willyan.iconchanger.R;
import com.willyan.iconchanger.IconFragment.IconItemClickListener;
import com.willyan.iconchanger.indicator.PageIndicator;
import com.willyan.iconchanger.indicator.UnderlinePageIndicator;
import com.willyan.iconchanger.utils.L;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PickIconActivity extends FragmentActivity implements
		OnClickListener, IconItemClickListener {
	
	private EditText input;
	private Button btn_pick;
	private ViewPager mViewPager;
	private IconViewPagerAdapter mPagerAdapter;
	private PageIndicator mIndicator;
	private String appName, packageName, activityName;
	private MenuPopupWindows mMenuPopup;
	private Button btn_menu;
	private static final int REQUEST_CODE_SYETEM_GALLERY = 0x1001;
	private static final int REQUEST_CODE_SYETEM_CROP = 0x1002;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_pickicon);
		Intent intent = getIntent();
		appName = intent.getStringExtra("appName");
		packageName = intent.getStringExtra("packageName");
		activityName = intent.getStringExtra("activityName");
		
		input = (EditText) findViewById(R.id.input);
		input.setText(appName);
		btn_pick = (Button) findViewById(R.id.pick);
		btn_pick.setOnClickListener(this);
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new IconViewPagerAdapter(this, getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mViewPager);
		
		btn_menu = (Button) findViewById(R.id.menu);
		mMenuPopup = new MenuPopupWindows(btn_menu, this);
		
		super.onCreate(savedInstanceState);
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == RESULT_OK) {
			Uri uri = null;
			switch(requestCode){
			case REQUEST_CODE_SYETEM_GALLERY:{
				uri = data.getData();
				break;
			}
			case REQUEST_CODE_SYETEM_CROP:{
				Bitmap btm = data.getParcelableExtra("data");
				String input_name = input.getText().toString();
				createShortcut(TextUtils.isEmpty(input_name) ? " " : input_name, packageName, activityName, btm);
				btm.recycle();
				btm = null;
				break;
			}
			default:
				break;
			}
			
			// 
			if(uri != null){
				startSystemCrop(uri);
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pick:{
			startSystemGallery();
			break;
		}
		default:
			break;
		}
	}
	
	public void onMenuClicked(View view){
		//TODO
		mMenuPopup.show();
	}
	
	private void startSystemGallery(){
		Intent in = new Intent(Intent.ACTION_PICK, null);
		in.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(in, REQUEST_CODE_SYETEM_GALLERY);
	}
	
	private void startSystemCrop(Uri data){
		Intent intent1 = new Intent("com.android.camera.action.CROP");
		intent1.setDataAndType(data, "image/*");
		intent1.putExtra("crop", "true");
		intent1.putExtra("aspectX", 1);
		intent1.putExtra("aspectY", 1);
		intent1.putExtra("outputX", 100);
		intent1.putExtra("outputY", 100);
		intent1.putExtra("return-data", true);
		intent1.putExtra("noFaceDetection", true);
		startActivityForResult(intent1, REQUEST_CODE_SYETEM_CROP);
	}
	
	
	/**
	 * Create shortcut in home screen. 
	 * @param appName
	 * @param packageName
	 * @param activityName
	 */
	private void createShortcut(String appName,String packageName,String activityName, Parcelable value){
		try {
			Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
			shortcutIntent.putExtra("duplicate", true); 
			
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setComponent(new ComponentName(packageName,activityName));
			
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
			if (value instanceof Bitmap){
				shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, value);
			} else {
				shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, value);
			}
			sendBroadcast(shortcutIntent);
			// statistic analysis for Shortcut created App
			MobclickAgent.onEvent(getApplicationContext(), "PickedAppName", appName);
			// TODO Change to Dialog
			Toast.makeText(this, "Create Shortcut Success! ", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onIconItemClick(int style, int position) {
		L.i("onIconItemClick: " + style + "/" + position);
		int[] tmp = null;
		switch(style){
		case IconGridViewAdapter.STYLE_SMARTISAN_1:
			tmp = IconGridViewAdapter.smartisan_res_1;
			break;
		case IconGridViewAdapter.STYLE_SMARTISAN_2:
			tmp = IconGridViewAdapter.smartisan_res_2;
			break;
		case IconGridViewAdapter.STYLE_SMARTISAN_3:
			tmp = IconGridViewAdapter.smartisan_res_3;
			break;
		case IconGridViewAdapter.STYLE_SMARTISAN_4:
			tmp = IconGridViewAdapter.smartisan_res_4;
			break;
		case IconGridViewAdapter.STYLE_IPHONE:
			tmp = IconGridViewAdapter.iphone_res;
			break;
		case IconGridViewAdapter.STYLE_COLD_1:
			tmp = IconGridViewAdapter.cold_res_1;
			break;
		default:
			break;
		}
		
		String input_name = input.getText().toString();
		if (tmp != null)
		   createShortcut(TextUtils.isEmpty(input_name) ? " " : input_name, packageName, activityName, 
				Intent.ShortcutIconResource.fromContext(PickIconActivity.this,tmp[position]));
	}
	

}
