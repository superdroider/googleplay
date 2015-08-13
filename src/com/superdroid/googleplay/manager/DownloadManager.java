package com.superdroid.googleplay.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Note;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.entities.DownloadInfo;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.UIUtils;

public class DownloadManager {
	public static final int STATE_NONE = 0;
	public static final int STATE_DOWNLOADING = 1;
	public static final int STATE_PUSE = 2;
	public static final int STATE_WATTING = 3;
	public static final int STATE_SUCCESS = 4;
	public static final int STATE_ERROR = 5;

	private static DownloadManager downloadManager;

	private Map<Long, DownloadInfo> downloadInfos;
	private Map<Long, DownloadTask> downloadTasks;

	private List<DownloadObserver> observers;

	public static DownloadManager getInstance() {
		if (downloadManager == null) {
			downloadManager = new DownloadManager();
		}
		return downloadManager;
	}

	private DownloadManager() {
		downloadTasks = new ConcurrentHashMap<Long, DownloadManager.DownloadTask>();
		downloadInfos = new ConcurrentHashMap<Long, DownloadInfo>();
		observers = new ArrayList<DownloadManager.DownloadObserver>();
	}

	/**
	 * 下载应用
	 * 
	 * @param appInfo
	 */
	public synchronized void download(AppInfo appInfo) {
		DownloadInfo downloadInfo = downloadInfos.get(appInfo.getId());
		if (downloadInfo == null) {
			downloadInfo = DownloadInfo.clone(appInfo);
			downloadInfos.put(downloadInfo.getId(), downloadInfo);
		}
		int currentState = downloadInfo.getDownloadState();
		if (currentState == STATE_NONE || currentState == STATE_ERROR
				|| currentState == STATE_PUSE) {
			currentState = STATE_WATTING;
			downloadInfo.setDownloadState(currentState);
			notifyStateChanged(downloadInfo);
			DownloadTask downloadTask = new DownloadTask(downloadInfo);
			downloadTasks.put(downloadInfo.getId(), downloadTask);
			ThreadManager.shortTaskExecute(downloadTask);
		}
	}

	/**
	 * 暂停下载
	 * 
	 * @param appInfo
	 */
	public synchronized void pause(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo downloadInfo = downloadInfos.get(appInfo.getId());
		if (downloadInfo != null) {
			downloadInfo.setDownloadState(STATE_PUSE);
			notifyStateChanged(downloadInfo);
		}
	}

	/**
	 * 取消下载
	 * 
	 * @param appInfo
	 */
	public synchronized void cancle(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo downloadInfo = downloadInfos.get(appInfo.getId());
		if (downloadInfo != null) {
			downloadInfo.setDownloadState(STATE_NONE);
			downloadInfo.setCurrentSize(0);
			notifyStateChanged(downloadInfo);
			File file = new File(downloadInfo.getPath());
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 停止下载
	 * 
	 * @param appInfo
	 */
	private void stopDownload(AppInfo appInfo) {
		DownloadTask task = downloadTasks.remove(appInfo.getId());
		if (task != null) {
			ThreadManager.cancleShortTask(task);
		}
	}

	/**
	 * 安装应用
	 * 
	 * @param appInfo
	 */
	public synchronized void install(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo downloadInfo = downloadInfos.get(appInfo.getId());
		if (downloadInfo != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(
					Uri.parse("file://" + downloadInfo.getPath()),
					"application/vnd.android.package-archive");
			UIUtils.getContext().startActivity(intent);
		}
		notifyStateChanged(downloadInfo);
	}

	/**
	 * 观察者接口
	 * 
	 * @author Administrator
	 *
	 */
	public interface DownloadObserver {
		void notifyStateChanged(DownloadInfo info);

		void notifyProgressChanged(DownloadInfo info);
	}

	/**
	 * 下载状态改变时通知观察者
	 * 
	 * @param info
	 */
	public void notifyStateChanged(DownloadInfo info) {
		synchronized (observers) {
			for (DownloadObserver downloadObserver : observers) {
				downloadObserver.notifyStateChanged(info);
			}
		}
	}

	/**
	 * 下载进度改变时通知观察者
	 * 
	 * @param info
	 */
	public void notifyProgressChanged(DownloadInfo info) {
		synchronized (observers) {
			for (DownloadObserver downloadObserver : observers) {
				downloadObserver.notifyProgressChanged(info);
			}
		}
	}

	/**
	 * 注册观察者
	 * 
	 * @param observer
	 */
	public void registObserver(DownloadObserver observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	/**
	 * 取消注册
	 * 
	 * @param observer
	 */
	public void unRegistObserver(DownloadObserver observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	private class DownloadTask implements Runnable {
		private DownloadInfo downloadInfo;

		public DownloadTask(DownloadInfo downloadInfo) {
			this.downloadInfo = downloadInfo;
		}

		@Override
		public void run() {
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.download(Constants.BASE_URL + downloadInfo.getUrl(),
					downloadInfo.getPath(), new RequestCallBack<File>() {

						@Override
						public void onSuccess(ResponseInfo<File> response) {
						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							downloadInfo.setCurrentSize(current);
							notifyProgressChanged(downloadInfo);
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {

						}
					});
		}
	}
}
