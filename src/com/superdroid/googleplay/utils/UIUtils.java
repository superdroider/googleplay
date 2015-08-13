package com.superdroid.googleplay.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.widget.Toast;

import com.superdroid.googleplay.BaseActivity;
import com.superdroid.googleplay.application.MyApplication;

/**
 * 负责UI显示的工具类
 * 
 * @author superdroid
 *
 */
public class UIUtils {
	/**
	 * 获取全局上下文对象
	 * 
	 * @return
	 */
	public static Context getContext() {

		return MyApplication.getApplication();
	}

	/**
	 * 显示Toast
	 * 
	 * @param strId
	 *            显示内容的id
	 */
	public static void showToast(int strId) {
		Toast.makeText(getContext(), strId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示Toast
	 * 
	 * @param str
	 *            待显示内容
	 */
	public static void showToast(String str) {
		Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 获得资源
	 * 
	 * @return
	 */
	public static Resources getResource() {
		return getContext().getResources();
	}

	/**
	 * 根据资源id获得资源文件中的string数组
	 * 
	 * @param resourceID
	 *            资源id
	 * @return
	 */
	public static String[] getStringArray(int resourceID) {
		return getResource().getStringArray(resourceID);
	}

	/**
	 * 根据资源id获得资源文件中的string
	 * 
	 * @param resourceID
	 *            资源id
	 * @return
	 */
	public static String getString(int resourceID) {
		return getResource().getString(resourceID);
	}

	/**
	 * 根据资源id获得资源文件中的color
	 * 
	 * @param colorID
	 * @return
	 */
	public static int getCoclor(int colorID) {
		return getResource().getColor(colorID);
	}

	/**
	 * 根据资源id创建view对象
	 * 
	 * @param layoutID
	 * @return
	 */
	public static View inflate(int layoutID) {

		return View.inflate(getContext(), layoutID, null);
	}

	/**
	 * 让一个子线程运行在主线程中
	 * 
	 * @param runnable
	 */
	public static void runOnMainThread(Runnable runnable) {
		if (isMainThread()) {
			runnable.run();
		} else {
			getHandler().post(runnable);
		}
	}

	/**
	 * 获得主线程中的handler
	 * 
	 * @return
	 */
	private static Handler getHandler() {
		return MyApplication.getHandler();
	}

	/**
	 * 判断当前线程是否是主线程
	 * 
	 * @return
	 */
	private static boolean isMainThread() {
		return Process.myTid() == getMainThreadId();
	}

	/**
	 * 获得主线程的ID
	 * 
	 * @return
	 */
	private static int getMainThreadId() {
		return MyApplication.getMainThreadId();
	}

	/**
	 * 获取资源图片
	 * 
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(int id) {
		return getResource().getDrawable(id);
	}

	/**
	 * 将dimens文件中的dp转化为px
	 * 
	 * @param id
	 * @return
	 */
	public static float getDemen(int id) {
		return getResource().getDimension(id);
	}

	 /** 
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素) 
     */  
    public static int dip2px(float dpValue) {  
        final float scale = getResource().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(float pxValue) {  
        final float scale = getResource().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
	
	/**
	 * 延时执行异步任务
	 * 
	 * @param runnable
	 * @param delayMillis
	 */
	public static void executeDelayedTask(Runnable runnable, long delayMillis) {
		getHandler().postDelayed(runnable, delayMillis);
	}

	/**
	 * 移除异步任务
	 * 
	 * @param runnable
	 */
	public static void cancleTask(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}

	public static void startActivity(Intent intent) {
		Activity activity = BaseActivity.getActivity();
		if (activity == null) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getContext().startActivity(intent);
		} else {
			activity.startActivity(intent);
		}
	}
}
