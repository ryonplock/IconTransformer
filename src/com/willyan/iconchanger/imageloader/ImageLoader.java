package com.willyan.iconchanger.imageloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.willyan.iconchanger.utils.L;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView;

public class ImageLoader {
	private MemoryCache memoryCache;
	private AbstractFileCache fileCache;
	private Map<ImageView, String> imageViewMaps;
	private ExecutorService executorService;
	private Context mContext;

	/**
	 * Manage image display, download and recycle.
	 * @param context
	 * @param needThreadPool Display local image doesn't need this
	 */
	public ImageLoader(Context context,boolean needThreadPool) {
		mContext = context;
		memoryCache = new MemoryCache();
		imageViewMaps = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
		if (needThreadPool) {
			fileCache = new FileCache(context);
			executorService = Executors.newFixedThreadPool(5);
		}
	}
	
	/**
	 * Display image from net
	 * @param url
	 * @param imageView
	 * @param isLoadOnlyFromCache
	 */
	public void displayImage(String url, ImageView imageView,
			boolean isLoadOnlyFromCache) {
		imageViewMaps.put(imageView, url);
		
		// firstly, find in cache
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else if (!isLoadOnlyFromCache) {
			// submit a thread into ExecutorService to download
			queuePhoto(url, imageView);
		}
	}
	
	/**
	 * Display image from local
	 * @param resId
	 * @param imageView
	 */
	public void displayImage(int resId, ImageView imageView){
		String id = String.valueOf(resId);
		imageViewMaps.put(imageView,id);
		Bitmap bitmap = memoryCache.get(id);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}else {
//			BitmapFactory.Options opts = new Options();
//			opts.inSampleSize = 2;
//			bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId , opts);
			bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);
			memoryCache.put(id, bitmap);
			imageView.setImageBitmap(bitmap);
		}
	}
	
	
	/**
	 * Download image
	 * @param url
	 */
	public void downloadImg(String url) {
		File f = fileCache.getFile(url);
		// already in file cache
		if (f != null && f.exists()) {
			return;
		}
		// download from net
		try {
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			CopyStream(is, os);
			os.close();
		} catch (Exception ex) {
			L.e("getBitmap catch Exception...\nmessage = "
							+ ex.getMessage());
		}
	}
	
	
	public Bitmap getBitmapFromCache(String url) {
		return memoryCache.get(url);
	}

	private void queuePhoto(String url, ImageView imageView) {
		ImageInfo p = new ImageInfo(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);
		
		// already in file cache
		Bitmap b = null;
		if (f != null && f.exists()) {
			b = decodeFile(f);
		}
		if (b != null) {
			return b;
		}
		// download from net
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Exception ex) {
			L.e("getBitmap catch Exception...\nmessage = " + ex.getMessage());
			return null;
		}
	}
	
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 100;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	private class ImageInfo {
		public String url;
		public ImageView imageView;

		public ImageInfo(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	// Task for the queue
	class PhotosLoader implements Runnable {
		ImageInfo imageInfo;

		PhotosLoader(ImageInfo imageInfo) {
			this.imageInfo = imageInfo;
		}

		@Override
		public void run() {
			if (imageViewReused(imageInfo))
				return;
			Bitmap bmp = getBitmap(imageInfo.url);
			memoryCache.put(imageInfo.url, bmp);
			if (imageViewReused(imageInfo))
				return;
			// run on UI thread
			BitmapDisplayer bd = new BitmapDisplayer(bmp, imageInfo);
			Activity a = (Activity) imageInfo.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	/**
	 * avoid image reused
	 * 
	 * @param imageInfo
	 * @return
	 */
	boolean imageViewReused(ImageInfo imageInfo) {
		String tag = imageViewMaps.get(imageInfo.imageView);
		if (tag == null || !tag.equals(imageInfo.url))
			return true;
		return false;
	}

	class BitmapDisplayer implements Runnable {
		Bitmap bitmap = null;
		ImageInfo imageInfo;

		public BitmapDisplayer(Bitmap b, ImageInfo p) {
			bitmap = b;
			imageInfo = p;
		}

		public void run() {
			if (imageViewReused(imageInfo))
				return;
			if (bitmap != null)
				imageInfo.imageView.setImageBitmap(bitmap);
		}
	}

	public void clearCache() {
		memoryCache.clear();
		imageViewMaps.clear();
		shutdown();
	}
	
	public void shutdown() {
		if (executorService!=null) {
			executorService.shutdown();
			executorService = null;
		}
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
			L.e("CopyStream catch Exception...");
		}
	}
	
	 /**
	  * shuts down an ExecutorService in two phases, first by calling shutdown to reject incoming tasks, 
	  * and then calling shutdownNow, if necessary, to cancel any lingering tasks
	  * @param pool
	  */
/*	void shutdownAndAwaitTermination(ExecutorService pool) {
		   pool.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
		       pool.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!pool.awaitTermination(60, TimeUnit.SECONDS))
		       }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
		     pool.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
	 }*/
	
}