package com.superdroid.googleplay.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * activity管理类
 * 
 * @author superdroid
 *
 */
public class ActivityCollector {
	public static List<Activity> mActivities = new LinkedList<Activity>();

	/**
	 * 添加activity
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		mActivities.add(activity);
	}

	/**
	 * 移除activity
	 * 
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		mActivities.remove(activity);
	}

	/**
	 * 关闭集合中所有activity
	 */
	public static void finishAll() {
		for (Activity activity : mActivities) {
			activity.finish();
		}
	}
}
