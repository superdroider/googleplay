package com.superdroid.googleplay.entities;

import com.superdroid.googleplay.manager.DownloadManager;
import com.superdroid.googleplay.utils.FileUtils;

public class DownloadInfo {
	private long id;
	private String appName;
	private long appSize;
	private long currentSize;
	private int downloadState;
	private String url;
	private String path;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getAppSize() {
		return appSize;
	}

	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}

	public long getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}

	public int getDownloadState() {
		return downloadState;
	}

	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static DownloadInfo clone(AppInfo appInfo) {
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.id = appInfo.getId();
		downloadInfo.appName = appInfo.getPackageName();
		downloadInfo.appSize = appInfo.getSize();
		downloadInfo.currentSize = 0;
		downloadInfo.downloadState = DownloadManager.STATE_NONE;
		downloadInfo.url = appInfo.getDownloadUrl();
		downloadInfo.path = FileUtils.getDownloadPath()
				+ downloadInfo.getAppName() + ".apk";
		return downloadInfo;
	}
}
