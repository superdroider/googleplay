package com.superdroid.googleplay.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * activity������
 * 
 * @author superdroid
 *
 */
public class ActivityCollector {
	public static List<Activity> mActivities = new LinkedList<Activity>();

	/**
	 * ���activity
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		mActivities.add(activity);
	}

	/**
	 * �Ƴ�activity
	 * 
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		mActivities.remove(activity);
	}

	/**
	 * �رռ���������activity
	 */
	public static void finishAll() {
		for (Activity activity : mActivities) {
			activity.finish();
		}
	}
}
