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
 * ����UI��ʾ�Ĺ�����
 * 
 * @author superdroid
 *
 */
public class UIUtils {
	/**
	 * ��ȡȫ�������Ķ���
	 * 
	 * @return
	 */
	public static Context getContext() {

		return MyApplication.getApplication();
	}

	/**
	 * ��ʾToast
	 * 
	 * @param strId
	 *            ��ʾ���ݵ�id
	 */
	public static void showToast(int strId) {
		Toast.makeText(getContext(), strId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ��ʾToast
	 * 
	 * @param str
	 *            ����ʾ����
	 */
	public static void showToast(String str) {
		Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * �����Դ
	 * 
	 * @return
	 */
	public static Resources getResource() {
		return getContext().getResources();
	}

	/**
	 * ������Դid�����Դ�ļ��е�string����
	 * 
	 * @param resourceID
	 *            ��Դid
	 * @return
	 */
	public static String[] getStringArray(int resourceID) {
		return getResource().getStringArray(resourceID);
	}

	/**
	 * ������Դid�����Դ�ļ��е�string
	 * 
	 * @param resourceID
	 *            ��Դid
	 * @return
	 */
	public static String getString(int resourceID) {
		return getResource().getString(resourceID);
	}

	/**
	 * ������Դid�����Դ�ļ��е�color
	 * 
	 * @param colorID
	 * @return
	 */
	public static int getCoclor(int colorID) {
		return getResource().getColor(colorID);
	}

	/**
	 * ������Դid����view����
	 * 
	 * @param layoutID
	 * @return
	 */
	public static View inflate(int layoutID) {

		return View.inflate(getContext(), layoutID, null);
	}

	/**
	 * ��һ�����߳����������߳���
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
	 * ������߳��е�handler
	 * 
	 * @return
	 */
	private static Handler getHandler() {
		return MyApplication.getHandler();
	}

	/**
	 * �жϵ�ǰ�߳��Ƿ������߳�
	 * 
	 * @return
	 */
	private static boolean isMainThread() {
		return Process.myTid() == getMainThreadId();
	}

	/**
	 * ������̵߳�ID
	 * 
	 * @return
	 */
	private static int getMainThreadId() {
		return MyApplication.getMainThreadId();
	}

	/**
	 * ��ȡ��ԴͼƬ
	 * 
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(int id) {
		return getResource().getDrawable(id);
	}

	/**
	 * ��dimens�ļ��е�dpת��Ϊpx
	 * 
	 * @param id
	 * @return
	 */
	public static float getDemen(int id) {
		return getResource().getDimension(id);
	}

	 /** 
     * �����ֻ��ķֱ��ʴ� dip �ĵ�λ ת��Ϊ px(����) 
     */  
    public static int dip2px(float dpValue) {  
        final float scale = getResource().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp 
     */  
    public static int px2dip(float pxValue) {  
        final float scale = getResource().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
	
	/**
	 * ��ʱִ���첽����
	 * 
	 * @param runnable
	 * @param delayMillis
	 */
	public static void executeDelayedTask(Runnable runnable, long delayMillis) {
		getHandler().postDelayed(runnable, delayMillis);
	}

	/**
	 * �Ƴ��첽����
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
