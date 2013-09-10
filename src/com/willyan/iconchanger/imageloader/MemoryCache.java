package com.willyan.iconchanger.imageloader;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;

import com.willyan.iconchanger.utils.L;

public class MemoryCache {

	private Map<String, Bitmap> cache ;
	private long size = 0;// current allocated size
	private long limit = 1000000;// max memory in bytes

	public MemoryCache() {
		// use 25% of available heap size
		// parameters "true" means LRU (Least Recently Used) in LinkedHashMap
		cache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
		setLimit(Runtime.getRuntime().maxMemory() / 10);
	}

	public void setLimit(long new_limit) {
		limit = new_limit;
	}

	public Bitmap get(String id) {
		try {
			if (!cache.containsKey(id))
				return null;
			return cache.get(id);
		} catch (NullPointerException ex) {
			return null;
		}
	}

	public void put(String id, Bitmap bitmap) {
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id));
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * Control heap size, arithmetic LRU (Least Recently Used)
	 */
	private void checkSize() {
		if (size > limit) {
			// find the Least Recently Used one
			Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();
			Bitmap bitmap;
			while (iter.hasNext()) {
				Entry<String, Bitmap> entry = iter.next();
				size -= getSizeInBytes(entry.getValue());
				bitmap = entry.getValue();
				if (bitmap != null && !bitmap.isRecycled()){
					bitmap.recycle();
					bitmap = null;
				}
				iter.remove();
				if (size <= limit)
					break;
			}
			L.i("Clean cache. New size " + cache.size());
		}
	}

	public void clear() {
		if(cache.isEmpty()){
            return;
        }
		// recycle bitmap
        for(Bitmap bitmap:cache.values()){
            if(bitmap != null && !bitmap.isRecycled()){
                bitmap.recycle();
            }
        }
		cache.clear();
	}

	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
}
