package com.superdroid.googleplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.superdroid.googleplay.entities.CategoryInfo;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.LogUtil;

public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>> {

	@Override
	public List<CategoryInfo> parseJson(String jsonStr) {

		try {
			List<CategoryInfo> list = new ArrayList<CategoryInfo>();
			JSONArray array = new JSONArray(jsonStr);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);

				String title = obj.getString("title");
				CategoryInfo info = new CategoryInfo();
				info.setTitle(true);
				info.setTitle(title);
				list.add(info);

				JSONArray infos = obj.getJSONArray("infos");
				for (int j = 0; j < infos.length(); j++) {
					JSONObject category = infos.optJSONObject(j);
					info = new CategoryInfo();
					info.setUrl1(category.optString("url1"));
					info.setUrl2(category.optString("url2"));
					info.setUrl3(category.optString("url3"));
					info.setName1(category.optString("name1"));
					info.setName2(category.optString("name2"));
					info.setName3(category.optString("name3"));
					info.setTitle(false);
					info.setTitle(title);
					list.add(info);
				}
			}
			return list;
		} catch (Exception e) {
			LogUtil.e(e);
		}
		return null;
	}

	@Override
	public String getParameters() {
		return Constants.CATEGORY_DATA + "?index=";
	}

	@Override
	public String getKey() {
		return "category";
	}

}
