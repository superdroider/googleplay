package com.superdroid.googleplay.adapter;

import java.util.List;

import android.content.Intent;
import android.widget.ListView;

import com.superdroid.googleplay.DetailActivity;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.holder.BaseHolder;
import com.superdroid.googleplay.holder.ListBaseHolder;
import com.superdroid.googleplay.utils.UIUtils;

public abstract class ListBaseAdapter extends DefaultAdapter<AppInfo> {
	private List<AppInfo> datas;

	public ListBaseAdapter(List<AppInfo> datas, ListView listView) {
		super(datas, listView);
		this.datas = datas;
	}

	@Override
	public BaseHolder<AppInfo> getHolder() {
		return new ListBaseHolder();
	}

	@Override
	protected void itemClick(int index) {
		AppInfo appInfo = datas.get(index);
		Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
		intent.putExtra(DetailActivity.PACKAGENAME, appInfo.getPackageName());
		UIUtils.startActivity(intent);
	}
}
