package com.superdroid.googleplay.utils;

import java.io.File;

import android.os.Environment;

public class FileUtils {

	private static final String ROOT_DIR = "GooglePlay";
	private static final String FILE_CACHE = "file";
	private static final String IMG_CACHE = "img";
	private static final String DOWNLOAD = "download";

	/**
	 * ��ȡ����·��
	 * 
	 * @return
	 */
	public static String getCachePath() {
		if (isSdAvaliable()) {
			return getSdCachePath(FILE_CACHE);
		} else {
			// ���浽�ڲ��洢
			return getInternalCacheDir(FILE_CACHE);
		}
	}

	/**
	 * ��ȡapk����·��
	 * 
	 * @return
	 */
	public static String getDownloadPath() {
		if (isSdAvaliable()) {
			return getSdCachePath(DOWNLOAD);
		} else {
			return getInternalCacheDir(DOWNLOAD);
		}
	}

	/**
	 * ��ȡͼƬ����·��
	 * 
	 * @return
	 */
	public static String getImgCachePath() {
		if (isSdAvaliable()) {
			return getSdCachePath(IMG_CACHE);
		} else {
			// ���浽�ڲ��洢
			return getInternalCacheDir(IMG_CACHE);
		}
	}

	/**
	 * ����ڲ�����cacheĿ¼
	 * 
	 * @param cacheDir
	 * @return
	 */
	private static String getInternalCacheDir(String cacheDir) {
		StringBuilder builder = new StringBuilder();
		// �ڲ�����cache·��
		String absolutePath = UIUtils.getContext().getCacheDir()
				.getAbsolutePath();
		builder.append(absolutePath);
		builder.append(File.separator);
		builder.append(cacheDir);
		builder.append(File.separator);
		return builder.toString();
	}

	/**
	 * ����SD������·��
	 * 
	 * @param cacheDir
	 *            ��Ż����ļ����ļ���
	 * @return
	 */
	private static String getSdCachePath(String cacheDir) {
		String path;
		// ���浽sd��
		StringBuilder builder = new StringBuilder();
		// sd������·��/mnt/sd
		String absolutePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		builder.append(absolutePath);
		builder.append(File.separator);
		builder.append(ROOT_DIR);
		builder.append(File.separator);
		builder.append(cacheDir);
		builder.append(File.separator);
		path = builder.toString();
		if (createFileDirs(path)) {
			return path;
		} else {
			return "";
		}
	}

	/**
	 * �����ļ�Ŀ¼
	 * 
	 * @param string
	 * @return
	 */
	private static boolean createFileDirs(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}

	/**
	 * �ж�SD���Ƿ����
	 * 
	 * @return
	 */
	private static boolean isSdAvaliable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

}
