package com.superdroid.googleplay.application;

import android.app.Application;
import android.os.Handler;
import android.os.Process;

public class MyApplication extends Application {
	private static MyApplication application;
	private static int mainThreadId = 0;
	private static Handler handler;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		Process.myTid();
		handler = new Handler();
	}

	public static MyApplication getApplication() {
		return application;
	}

	/**
	 * 获得主线程id
	 * 
	 * @return
	 */
	public static int getMainThreadId() {
		return mainThreadId;
	}

	public static Handler getHandler() {
		return handler;
	}

}
