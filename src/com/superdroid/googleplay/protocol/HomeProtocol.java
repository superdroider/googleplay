package com.superdroid.googleplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.LogUtil;

public class HomeProtocol extends BaseProtocol<List<AppInfo>> {
	private List<String> mPictures;

	/**
	 * 解析json数据
	 * 
	 * @param jsonStr
	 * @return
	 */
	@Override
	public List<AppInfo> parseJson(String jsonStr) {
		try {
			mPictures = new ArrayList<String>();
			List<AppInfo> appInfos = new ArrayList<AppInfo>();
			JSONObject obj = new JSONObject(jsonStr);
			JSONArray arrayObj = obj.getJSONArray("list");
			JSONArray arrayPicture = obj.getJSONArray("picture");
			int arrayLength = arrayObj.length();
			AppInfo appInfo;

			for (int i = 0; i < arrayPicture.length(); i++) {
				mPictures.add(arrayPicture.getString(i));
			}

			for (int index = 0; index < arrayLength; index++) {
				JSONObject jsonObj = arrayObj.getJSONObject(index);
				long id = jsonObj.getLong("id");
				String name = jsonObj.getString("name");
				String packageName = jsonObj.getString("packageName");
				String iconUrl = jsonObj.getString("iconUrl");
				float stars = (float) jsonObj.getDouble("stars");
				long size = jsonObj.getLong("size");
				String downloadUrl = jsonObj.getString("downloadUrl");
				String des = jsonObj.getString("des");
				appInfo = new AppInfo(id, name, packageName, iconUrl, stars,
						size, downloadUrl, des);
				appInfos.add(appInfo);

			}
			return appInfos;
		} catch (Exception e) {
			LogUtil.e(e);
			return null;
		}

	}

	@Override
	public String getParameters() {
		return Constants.HOME_DATA;
	}

	@Override
	public String getKey() {
		return "home";
	}

	/**
	 * 获取图片链接
	 * 
	 * @return
	 */
	public List<String> getPictures() {
		return this.mPictures;
	}
}
