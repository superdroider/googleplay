package com.superdroid.googleplay.protocol;

import org.json.JSONObject;

import com.superdroid.googleplay.entities.UserInfo;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.LogUtil;

public class UserProtocol extends BaseProtocol<UserInfo> {

	@Override
	public UserInfo parseJson(String jsonStr) {
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			String userName = jsonObject.getString("name");
			String email = jsonObject.getString("email");
			String imgUrl = jsonObject.getString("url");
			return new UserInfo(userName, email, imgUrl);
		} catch (Exception e) {
			LogUtil.e(e);
			return null;
		}
	}

	@Override
	public String getParameters() {
		return Constants.USER_DATA + "?index=";
	}

	@Override
	public String getKey() {
		return "user";
	}

}
