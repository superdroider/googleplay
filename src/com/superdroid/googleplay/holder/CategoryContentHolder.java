package com.superdroid.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.CategoryInfo;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.UIUtils;

public class CategoryContentHolder extends BaseHolder<CategoryInfo> {
	private RelativeLayout rl_1;
	private RelativeLayout rl_2;
	private RelativeLayout rl_3;

	private ImageView iv_1;
	private ImageView iv_2;
	private ImageView iv_3;

	private TextView tv_1;
	private TextView tv_2;
	private TextView tv_3;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.category_item);
		rl_1 = (RelativeLayout) view.findViewById(R.id.rl_1);
		rl_2 = (RelativeLayout) view.findViewById(R.id.rl_2);
		rl_3 = (RelativeLayout) view.findViewById(R.id.rl_3);

		iv_1 = (ImageView) view.findViewById(R.id.iv_1);
		iv_2 = (ImageView) view.findViewById(R.id.iv_2);
		iv_3 = (ImageView) view.findViewById(R.id.iv_3);

		tv_1 = (TextView) view.findViewById(R.id.tv_1);
		tv_2 = (TextView) view.findViewById(R.id.tv_2);
		tv_3 = (TextView) view.findViewById(R.id.tv_3);
		return view;
	}

	@Override
	public void refreshView(CategoryInfo info) {
		if (TextUtils.isEmpty(info.getUrl1())) {
			iv_1.setImageDrawable(null);
			tv_1.setText("");
		} else {
			BitmapHelper.getBitmapUtils().display(iv_1,
					Constants.BASE_URL + Constants.IMG_URL + info.getUrl1());
			tv_1.setText(info.getName1());
		}
		if (TextUtils.isEmpty(info.getUrl2())) {
			iv_2.setImageDrawable(null);
			tv_3.setText("");
		} else {
			BitmapHelper.getBitmapUtils().display(iv_2,
					Constants.BASE_URL + Constants.IMG_URL + info.getUrl2());
			tv_2.setText(info.getName2());
		}

		if (TextUtils.isEmpty(info.getUrl3())) {
			iv_3.setImageDrawable(null);
			tv_3.setText("");
		} else {
			BitmapHelper.getBitmapUtils().display(iv_3,
					Constants.BASE_URL + Constants.IMG_URL + info.getUrl3());
			tv_3.setText(info.getName3());
		}
	}

}
