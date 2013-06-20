package com.android.iconchanger.utils;

import android.util.Log;

public class L {
	
	private static final boolean DEBUG = true;
	private static final String TAG = "ICON_CHANGER";
	
	public static void d(String msg){
		if(DEBUG)
			Log.d(TAG, msg);
	}
	
	public static void e(String msg){
		if(DEBUG)
			Log.e(TAG, msg);
	}

}
