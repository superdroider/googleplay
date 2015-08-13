package com.superdroid.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.UIUtils;

public class ListBaseHolder extends BaseHolder<AppInfo> {

	private TextView tv_download_status;
	private TextView tv_app_name;
	private TextView tv_app_size;
	private TextView tv_app_desc;
	private ImageView iv_icon;
	private RatingBar rb_starts;
	private RelativeLayout rl_download_status;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.item_list);
		this.tv_download_status = (TextView) view
				.findViewById(R.id.tv_download_status);
		this.tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
		this.tv_app_size = (TextView) view.findViewById(R.id.tv_app_size);
		this.tv_app_desc = (TextView) view.findViewById(R.id.tv_app_desc);
		this.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
		this.rb_starts = (RatingBar) view.findViewById(R.id.rb_starts);
		this.rl_download_status = (RelativeLayout) view
				.findViewById(R.id.rl_download_status);
		return view;
	}

	@Override
	public void refreshView(AppInfo appInfo) {
		// TODO holder.iv_icon.setBackgroundResource(resid);
		// TODO holder.rl_download_status.setOnCLick();
		BitmapHelper.getBitmapUtils().display(this.iv_icon,
				Constants.BASE_URL + Constants.IMG_URL + appInfo.getIconUrl());
		this.tv_download_status.setText("обть");
		this.tv_app_name.setText(appInfo.getName());
		this.tv_app_size.setText(Formatter.formatFileSize(UIUtils.getContext(),
				appInfo.getSize()));
		this.tv_app_desc.setText(appInfo.getDes());
		this.rb_starts.setRating(appInfo.getStars());
	}

}
