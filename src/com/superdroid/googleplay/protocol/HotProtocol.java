package com.superdroid.googleplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.LogUtil;

public class HotProtocol extends BaseProtocol<List<String>> {

	@Override
	public List<String> parseJson(String jsonStr) {

		JSONArray array;
		try {
			List<String> strings = new ArrayList<String>();
			array = new JSONArray(jsonStr);
			for (int i = 0; i < array.length(); i++) {
				String string = array.getString(i);
				strings.add(string);
			}
			return strings;
		} catch (Exception e) {
			LogUtil.e(e);
			return null;
		}
	}

	@Override
	public String getParameters() {
		return Constants.HOT_DATA + "?index=";
	}

	@Override
	public String getKey() {
		return "hot";
	}

}
