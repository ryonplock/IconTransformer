package com.willyan.iconchanger;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

public class MenuPopupWindows implements OnClickListener{
	
	private Activity mActivity;
	private PopupWindow mPopWindow=null;
	private View mAttchView;

	public MenuPopupWindows(View attchView,Activity activity) {
		mActivity = activity;
		mAttchView = attchView;
		initView();
	}
	
	private void initView(){
		
		View contentView = LayoutInflater.from(mActivity).inflate(R.layout.popup_menu, null);
		mPopWindow = new PopupWindow(contentView);
		mPopWindow.setOutsideTouchable(true);
		contentView.findViewById(R.id.text_share).setOnClickListener(this);
		contentView.findViewById(R.id.text_setting).setOnClickListener(this);
		//TODO
	}
	
	public void show(){
		//TODO
		mPopWindow.showAsDropDown(mAttchView);
	}
	
	public void dismiss(){
		//TODO
		mPopWindow.dismiss();
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.text_share:

			break;
		case R.id.text_setting:

			break;

		default:
			break;
		}

	}
}
