package com.superdroid.googleplay.holder;

import java.util.List;

import android.view.View;
import android.widget.ImageView;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.UIUtils;

public class AppDetailScreenHolder extends BaseHolder<AppInfo> {
	private ImageView[] iv_screens;

	@Override
	public View initView() {
		iv_screens = new ImageView[5];
		View view = UIUtils.inflate(R.layout.detail_screen);
		iv_screens[0] = (ImageView) view.findViewById(R.id.screen_0);
		iv_screens[1] = (ImageView) view.findViewById(R.id.screen_1);
		iv_screens[2] = (ImageView) view.findViewById(R.id.screen_2);
		iv_screens[3] = (ImageView) view.findViewById(R.id.screen_3);
		iv_screens[4] = (ImageView) view.findViewById(R.id.screen_4);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		List<String> screens = info.getScreen();
		int size = screens.size();
		int length = iv_screens.length;
		for (int i = 0; i < length; i++) {
			if (i < size) {
				BitmapHelper.getBitmapUtils()
						.display(
								iv_screens[i],
								Constants.BASE_URL + Constants.IMG_URL
										+ screens.get(i));
			} else {
				iv_screens[i].setVisibility(View.GONE);
			}
		}
	}
}
