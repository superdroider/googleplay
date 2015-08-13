package com.superdroid.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.SubjectInfo;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.UIUtils;

public class SubjectHolder extends BaseHolder<SubjectInfo> {
	private ImageView iv_url;
	private TextView tv_des;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.subject_item);
		iv_url = (ImageView) view.findViewById(R.id.iv_url);
		tv_des = (TextView) view.findViewById(R.id.tv_des);
		return view;
	}

	@Override
	public void refreshView(SubjectInfo info) {
		BitmapHelper.getBitmapUtils().display(iv_url,
				Constants.BASE_URL + Constants.IMG_URL + info.getUrl());
		tv_des.setText(info.getDes());
	}
}
