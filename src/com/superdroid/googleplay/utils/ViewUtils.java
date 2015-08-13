package com.superdroid.googleplay.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {
	/**
	 * ÒÆ³ýviewµÄ¸¸²¼¾Ö
	 * 
	 * @param view
	 */
	public static void removeParent(View view) {
		ViewParent viewParent = view.getParent();
		if (viewParent instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) viewParent;
			viewGroup.removeView(view);
		}
	}

}
