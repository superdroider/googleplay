package com.superdroid.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.entities.DownloadInfo;
import com.superdroid.googleplay.manager.DownloadManager;
import com.superdroid.googleplay.manager.DownloadManager.DownloadObserver;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.UIUtils;

public class AppDetailInfoHolder extends BaseHolder<AppInfo> implements
		DownloadObserver {

	private ImageView detail_info_icon;
	private TextView detail_info_appname;
	private RatingBar detail_info_stars;
	private TextView detail_info_downloadnum;
	private TextView detail_info_version;
	private TextView detail_info_date;
	private TextView detail_info_size;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_info);
		detail_info_icon = (ImageView) view.findViewById(R.id.detail_info_icon);
		detail_info_appname = (TextView) view
				.findViewById(R.id.detail_info_appname);
		detail_info_stars = (RatingBar) view
				.findViewById(R.id.detail_info_stars);
		detail_info_downloadnum = (TextView) view
				.findViewById(R.id.detail_info_downloadnum);
		detail_info_version = (TextView) view
				.findViewById(R.id.detail_info_version);
		detail_info_date = (TextView) view.findViewById(R.id.detail_info_date);
		detail_info_size = (TextView) view.findViewById(R.id.detail_info_size);
		DownloadManager.getInstance().registObserver(this);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		BitmapHelper.getBitmapUtils().display(detail_info_icon,
				Constants.BASE_URL + Constants.IMG_URL + info.getIconUrl());
		detail_info_appname.setText(info.getName());
		detail_info_stars.setRating(info.getStars());
		detail_info_downloadnum.setText(UIUtils
				.getString(R.string.detail_download) + info.getDownloadNum());
		detail_info_version.setText(UIUtils.getString(R.string.detail_version)
				+ info.getVersion());
		detail_info_date.setText(UIUtils.getString(R.string.detail_date)
				+ info.getDate());
		detail_info_size
				.setText(UIUtils.getString(R.string.detail_size)
						+ Formatter.formatFileSize(UIUtils.getContext(),
								info.getSize()));
	}

	@Override
	public void notifyStateChanged(DownloadInfo info) {

	}

	@Override
	public void notifyProgressChanged(DownloadInfo info) {

	}

}
