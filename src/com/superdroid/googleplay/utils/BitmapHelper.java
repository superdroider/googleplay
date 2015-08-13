package com.superdroid.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

public class BitmapHelper {
	private static BitmapUtils mBitmapUtils = null;

	/**
	 * ���bitmaputil����
	 * 
	 * @return
	 */
	public static BitmapUtils getBitmapUtils() {
		if (mBitmapUtils == null) {
			mBitmapUtils = new BitmapUtils(UIUtils.getContext(),
					FileUtils.getImgCachePath(), 0.3f);
		}
		return mBitmapUtils;
	}
}
