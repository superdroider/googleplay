package com.superdroid.googleplay.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.utils.UIUtils;

public class AppDetailBottomHolder extends BaseHolder<AppInfo> implements
		OnClickListener {
	private Button bottom_favorites;
	private Button bottom_share;
	private Button progress_btn;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_bottom);
		bottom_favorites = (Button) view.findViewById(R.id.bottom_favorites);
		bottom_share = (Button) view.findViewById(R.id.bottom_share);
		progress_btn = (Button) view.findViewById(R.id.progress_btn);

		bottom_favorites.setOnClickListener(this);
		bottom_share.setOnClickListener(this);
		progress_btn.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.bottom_favorites:
			UIUtils.showToast(" ’≤ÿ");
			break;
		case R.id.bottom_share:
			UIUtils.showToast("∑÷œÌ");
			break;
		}
	}

}
