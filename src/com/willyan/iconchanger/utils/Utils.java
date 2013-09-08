package com.willyan.iconchanger.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Utils {
	
	
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public static void shareAction(Activity activity){
		Intent intent=new Intent(Intent.ACTION_SEND);   
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Shared From IconChanger");   
		intent.putExtra(Intent.EXTRA_TEXT, "Hey, I'm using IconChanger.");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		activity.startActivity(Intent.createChooser(intent, "Share"));
	}

}
