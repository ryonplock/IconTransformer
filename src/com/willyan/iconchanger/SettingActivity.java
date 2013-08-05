package com.willyan.iconchanger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends Activity{
	
	private Button btn_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		btn_menu = (Button) findViewById(R.id.menu);
		btn_menu.setVisibility(View.GONE);
	}

}
