package com.willyan.iconchanger.utils;

import android.text.TextUtils;
import android.util.Log;

public class L {
	
	private static final boolean DEBUG = true;
	private static final boolean PROCESS = true;
	private static final String TAG = "ICON_CHANGER";
	
	/**
	 * Log for debug
	 * @param msg
	 */
	public static void i(String msg){
		if (DEBUG)
			Log.i(TAG, msg);
	}
	
	/**
	 * Log for error
	 * @param msg
	 */
	public static void e(String msg){
		if (DEBUG)
			Log.e(TAG, msg);
	}
	
	/**
	 * Log for the working process 
	 * @param classname
	 * @param methodname 
	 * @param msg can be null, for nothing
	 */
	public static void p(String classname, String methodname, String msg){
		if (PROCESS){
			if (TextUtils.isEmpty(msg)){
				Log.d(TAG, ">>>>> " + classname + ": " + methodname);
			} else {
				Log.d(TAG, ">>>>> " + classname + ": " + methodname + " >>> " + msg);
			}
		}
	}

}
