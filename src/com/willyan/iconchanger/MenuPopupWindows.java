package com.willyan.iconchanger;

import com.willyan.iconchanger.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

public class MenuPopupWindows implements OnClickListener{
	
	private Activity mActivity;
	private PopupWindow mPopWindow=null;
	private View mAttchView;
	private int popup_width, popup_height;

	public MenuPopupWindows(View attchView,Activity activity) {
		mActivity = activity;
		mAttchView = attchView;
		initView();
	}
	
	private void initView(){
		
		View contentView = LayoutInflater.from(mActivity).inflate(R.layout.popup_menu, null);
		popup_width = Utils.dip2px(mActivity, 180);
		popup_height = Utils.dip2px(mActivity, 110);
		mPopWindow = new PopupWindow(contentView, popup_width, popup_height);
		mPopWindow.setFocusable(true);
		mPopWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopWindow.setOutsideTouchable(true);
		contentView.findViewById(R.id.text_share).setOnClickListener(this);
		contentView.findViewById(R.id.text_setting).setOnClickListener(this);
	}
	
	public void show(){
		if (!mPopWindow.isShowing())
			mPopWindow.showAsDropDown(mAttchView);
	}
	
	public void dismiss(){
		if (mPopWindow.isShowing())
			mPopWindow.dismiss();
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.text_share:
			Utils.shareAction(mActivity);
			dismiss();
			break;
		case R.id.text_setting:
			mActivity.startActivity(new Intent(mActivity.getApplicationContext(), SettingActivity.class));
			dismiss();
			break;
		default:
			break;
		}

	}
}
