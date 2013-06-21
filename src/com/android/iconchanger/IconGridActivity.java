package com.android.iconchanger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class IconGridActivity extends Activity implements OnClickListener{
	
	private EditText input;
	private Button btn_pick;
	private GridView iconsView;
	private String appName, packageName, activityName;
	private static final int REQUEST_CODE_SYETEM_GALLERY = 0x1001;
	private static final int REQUEST_CODE_SYETEM_CROP = 0x1002;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.icon_grid);
		Intent intent = getIntent();
		appName = intent.getStringExtra("appName");
		packageName = intent.getStringExtra("packageName");
		activityName = intent.getStringExtra("activityName");
		
		input = (EditText) findViewById(R.id.input);
		input.setText(appName);
		btn_pick = (Button) findViewById(R.id.pick);
		btn_pick.setOnClickListener(this);
		
		iconsView = (GridView) findViewById(R.id.icons_grid);
		//TODO
		GridViewAdapter adapter = new GridViewAdapter(this, GridViewAdapter.STYLE_MATT);
		iconsView.setAdapter(adapter);
		iconsView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int[] tmp = GridViewAdapter.matt_res;
				String input_name = input.getText().toString();
				createShortcut(TextUtils.isEmpty(input_name) ? " " : input_name, packageName, activityName, 
						Intent.ShortcutIconResource.fromContext(IconGridActivity.this,tmp[position]));
			}
		});
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
			shortcutIntent.putExtra("duplicate", false); 
			
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setComponent(new ComponentName(packageName,activityName));
			
			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//			shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//					Intent.ShortcutIconResource.fromContext(this,R.drawable.ic_launcher));
			if (value instanceof Bitmap){
				shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, value);
			} else {
				shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, value);
			}
			sendBroadcast(shortcutIntent);
			Toast.makeText(this, "Create Shortcut Success! ", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
