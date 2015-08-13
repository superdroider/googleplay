package com.superdroid.googleplay.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import android.os.SystemClock;
import android.text.TextUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.FileUtils;
import com.superdroid.googleplay.utils.IOUtils;
import com.superdroid.googleplay.utils.LogUtil;

public abstract class BaseProtocol<T> {
	private static final long KEEP_TIME = 60 * 1000;

	/**
	 * ��������
	 * 
	 * @param index
	 */
	public T loadData(int index) {
		SystemClock.sleep(1000);
		// �ӱ��ؼ�������
		String jsonStr = loadDataFromLocal(index);
		if (TextUtils.isEmpty(jsonStr)) {
			// �������������
			jsonStr = loadDataFromNet(index);
			if (TextUtils.isEmpty(jsonStr)) {
				// ���س���
			} else {
				saveDataToLocal(jsonStr, index);
			}
		}
		if (!TextUtils.isEmpty(jsonStr)) {
			return parseJson(jsonStr);
		}
		return null;
	}

	/**
	 * ����json����
	 * 
	 * @param jsonStr
	 * @return
	 */
	public abstract T parseJson(String jsonStr);

	/**
	 * �ӱ��ؼ�������
	 * 
	 * @return
	 */
	public String loadDataFromLocal(int index) {
		String packageName = null;
		if (getParameters() != null && getParameters().contains("packageName")) {
			packageName = getParameters().substring(
					getParameters().indexOf("packageName"),
					getParameters().indexOf("&"));
		}
		String path = FileUtils.getCachePath() + getKey() + "_" + index
				+ packageName;
		File file = new File(path);
		BufferedReader bufferedReader = null;
		if (file.exists()) {
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String keepTimeStr = bufferedReader.readLine();
				if (!TextUtils.isEmpty(keepTimeStr)) {
					long keepTime = Long.parseLong(keepTimeStr);
					String line = null;
					StringBuffer strBuff = new StringBuffer();
					if (System.currentTimeMillis() < keepTime) {
						while ((line = bufferedReader.readLine()) != null) {
							strBuff.append(line);
						}
						LogUtil.i(strBuff.toString());
						return strBuff.toString();
					}
				}
			} catch (Exception e) {
				LogUtil.e(e);
			} finally {
				IOUtils.close(bufferedReader);
			}
		}

		return null;
	}

	/**
	 * �������������
	 * 
	 * @param index
	 *            ��ַ
	 * @return
	 */
	public String loadDataFromNet(int index) {

		HttpUtils httpUtils = new HttpUtils();
		String resultStr = null;
		try {
			LogUtil.d(Constants.BASE_URL + getParameters() + index);
			ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET,
					Constants.BASE_URL + getParameters() + index);
			resultStr = responseStream.readString();
		} catch (Exception e) {
			LogUtil.e(e);
		}
		return resultStr;
	}

	/**
	 * �������ݵ�����
	 * 
	 * @param jsonStr
	 */
	public void saveDataToLocal(String jsonStr, int index) {
		String packageName = null;
		if (getParameters() != null && getParameters().contains("packageName")) {
			packageName = getParameters().substring(
					getParameters().indexOf("packageName"),
					getParameters().indexOf("&"));
		}
		String cachePath = FileUtils.getCachePath();// ����·��
		File file = new File(cachePath + getKey() + "_" + index + packageName);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			long currentTimeMillis = System.currentTimeMillis() + KEEP_TIME;
			fw.write(currentTimeMillis + "\r\n");
			fw.flush();
			fw.write(jsonStr);
			fw.flush();
		} catch (Exception e) {
			LogUtil.e(e);
			IOUtils.close(fw);
		}

	}

	/**
	 * ��������Ĳ���
	 * 
	 * @return
	 */
	public abstract String getParameters();

	/**
	 * ��������key
	 * 
	 * @return
	 */
	public abstract String getKey();
}
