package com.willyan.iconchanger.imageloader;

import java.net.URLDecoder;

import android.content.Context;
import android.os.Environment;

public class FileCache extends AbstractFileCache{
	
	public static String BASEPATH = Environment.getExternalStorageDirectory().getPath() + "/iconchanger/";

	public FileCache(Context context) {
		super(context);
	}

	@Override
	public String getSavePath(String url) {
		String temp = url;
		if (temp.contains("?")) {
			temp = temp.substring(0, temp.indexOf("?"));
		}
		temp = URLDecoder.decode(temp);
		String name = temp.substring(temp.lastIndexOf("/") + 1);
		return getCacheDir() + name;
	}

	@Override
	public String getCacheDir() {
		return BASEPATH +".icon/";
	}

}
