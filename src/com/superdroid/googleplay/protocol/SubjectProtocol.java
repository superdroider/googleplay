package com.superdroid.googleplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.superdroid.googleplay.entities.SubjectInfo;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.LogUtil;

public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {

	@Override
	public List<SubjectInfo> parseJson(String jsonStr) {
		try {
			List<SubjectInfo> subjectInfos = new ArrayList<SubjectInfo>();
			JSONArray jsonArray = new JSONArray(jsonStr);
			int length = jsonArray.length();
			SubjectInfo subjectInfo = null;
			for (int i = 0; i < length; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String des = jsonObject.getString("des");
				String url = jsonObject.getString("url");
				subjectInfo = new SubjectInfo(des, url);
				subjectInfos.add(subjectInfo);
			}
			return subjectInfos;
		} catch (Exception e) {
			LogUtil.e(e);
			return null;
		}
	}

	@Override
	public String getParameters() {
		return Constants.SUBJECT_DATA;
	}

	@Override
	public String getKey() {
		return "subject";
	}

}
