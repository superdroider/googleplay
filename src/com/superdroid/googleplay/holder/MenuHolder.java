package com.superdroid.googleplay.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.UserInfo;
import com.superdroid.googleplay.manager.ThreadManager;
import com.superdroid.googleplay.protocol.UserProtocol;
import com.superdroid.googleplay.utils.ActivityCollector;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.UIUtils;

public class MenuHolder extends BaseHolder<UserInfo> implements OnClickListener {
	private RelativeLayout photo_layout;
	private RelativeLayout home_layout;
	private RelativeLayout setting_layout;
	private RelativeLayout theme_layout;
	private RelativeLayout scan_layout;
	private RelativeLayout feedback_layout;
	private RelativeLayout update_layout;
	private RelativeLayout about_layout;
	private RelativeLayout exit_layout;

	private TextView user_name;
	private TextView email;
	private ImageView image_photo;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.menu);
		image_photo = (ImageView) view.findViewById(R.id.image_photo);
		user_name = (TextView) view.findViewById(R.id.user_name);
		email = (TextView) view.findViewById(R.id.user_email);

		photo_layout = (RelativeLayout) view.findViewById(R.id.photo_layout);
		home_layout = (RelativeLayout) view.findViewById(R.id.home_layout);
		setting_layout = (RelativeLayout) view
				.findViewById(R.id.setting_layout);
		theme_layout = (RelativeLayout) view.findViewById(R.id.theme_layout);
		scan_layout = (RelativeLayout) view.findViewById(R.id.scan_layout);
		feedback_layout = (RelativeLayout) view
				.findViewById(R.id.feedback_layout);
		update_layout = (RelativeLayout) view.findViewById(R.id.update_layout);
		about_layout = (RelativeLayout) view.findViewById(R.id.about_layout);
		exit_layout = (RelativeLayout) view.findViewById(R.id.exit_layout);

		photo_layout.setOnClickListener(this);
		home_layout.setOnClickListener(this);
		setting_layout.setOnClickListener(this);
		theme_layout.setOnClickListener(this);
		scan_layout.setOnClickListener(this);
		feedback_layout.setOnClickListener(this);
		update_layout.setOnClickListener(this);
		about_layout.setOnClickListener(this);
		exit_layout.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView(UserInfo info) {
		if (info != null) {
			user_name.setText(info.getUserName());
			email.setText(info.getEmail());
			BitmapHelper.getBitmapUtils()
					.display(image_photo, info.getImgUrl());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo_layout:
			ThreadManager.longTaskExecute(new Runnable() {
				
				@Override
				public void run() {
					final UserInfo info = new UserProtocol().loadData(0);
					UIUtils.runOnMainThread(new Runnable() {
						@Override
						public void run() {
							refreshView(info);
						}
					});
				}
			});
			break;
		case R.id.home_layout:
			UIUtils.showToast("首页");
			break;
		case R.id.setting_layout:
			UIUtils.showToast("设置");
			break;
		case R.id.theme_layout:
			UIUtils.showToast("主题");
			break;
		case R.id.scan_layout:
			UIUtils.showToast("安装包管理");
			break;
		case R.id.feedback_layout:
			UIUtils.showToast("反馈");
			break;
		case R.id.update_layout:
			UIUtils.showToast("更新");
			break;
		case R.id.about_layout:
			UIUtils.showToast("关于");
			break;
		case R.id.exit_layout:
			ActivityCollector.finishAll();
			break;

		default:
			break;
		}
	}

}
