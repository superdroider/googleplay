package com.superdroid.googleplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.LogUtil;

public class DetailProtocol extends BaseProtocol<AppInfo> {

	private String packageName;

	public DetailProtocol(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public AppInfo parseJson(String jsonStr) {
		AppInfo appInfo = null;
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			long id = jsonObj.getLong("id");
			String name = jsonObj.getString("name");
			String packageName = jsonObj.getString("packageName");
			String iconUrl = jsonObj.getString("iconUrl");
			float stars = (float) jsonObj.getDouble("stars");
			String downloadNum = jsonObj.getString("downloadNum");
			String version = jsonObj.getString("version");
			String date = jsonObj.getString("date");
			long size = jsonObj.getLong("size");
			String downloadUrl = jsonObj.getString("downloadUrl");
			String des = jsonObj.getString("des");
			String author = jsonObj.getString("author");

			List<String> screen;// 存放应用详情页的图片url
			JSONArray jsonScreenArray = jsonObj.getJSONArray("screen");
			screen = new ArrayList<String>();
			for (int i = 0; i < jsonScreenArray.length(); i++) {
				screen.add(jsonScreenArray.getString(i));
			}
			List<String> safeUrl;// 存放安全信息图片的URL
			List<String> safeDesUrl;//
			List<String> safeDes;// 存放安全信息描述的URL
			List<Integer> safeDesColor;// 存放描述信息字体颜色的状态值

			JSONArray jsonSafeArray = jsonObj.getJSONArray("safe");
			safeUrl = new ArrayList<String>();
			safeDesUrl = new ArrayList<String>();
			safeDes = new ArrayList<String>();
			safeDesColor = new ArrayList<Integer>();
			for (int i = 0; i < jsonSafeArray.length(); i++) {
				safeUrl.add(jsonSafeArray.getJSONObject(i).getString("safeUrl"));
				safeDesUrl.add(jsonSafeArray.getJSONObject(i).getString(
						"safeDesUrl"));
				safeDes.add(jsonSafeArray.getJSONObject(i).getString("safeDes"));
				safeDesColor.add(jsonSafeArray.getJSONObject(i).getInt(
						"safeDesColor"));
			}
			appInfo = new AppInfo(id, name, packageName, iconUrl, stars, size,
					downloadUrl, des, downloadNum, version, date, author,
					screen, safeUrl, safeDesUrl, safeDes, safeDesColor);
			return appInfo;
		} catch (Exception e) {
			LogUtil.e(e);
			return null;
		}
	}

	@Override
	public String getParameters() {
		return Constants.DETAIL_DATA + packageName + "&index=";
	}

	@Override
	public String getKey() {
		return "detail";
	}
}
