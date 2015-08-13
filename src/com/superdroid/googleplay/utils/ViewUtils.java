package com.superdroid.googleplay.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {
	/**
	 * �Ƴ�view�ĸ�����
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
