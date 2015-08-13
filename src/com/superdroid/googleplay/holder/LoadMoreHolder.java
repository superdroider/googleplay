package com.superdroid.googleplay.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.adapter.DefaultAdapter;
import com.superdroid.googleplay.utils.LogUtil;
import com.superdroid.googleplay.utils.UIUtils;

public class LoadMoreHolder<T> extends BaseHolder<Integer> {

	public static final int ERROR_STATUS = 0;
	public static final int HAVE_DATA_STATUS = 1;
	public static final int NO_DATA_STATUS = 2;

	private DefaultAdapter<T> adapter;

	private RelativeLayout rl_loading;
	private RelativeLayout rl_load_error;
	private int status;

	public LoadMoreHolder(DefaultAdapter<T> adapter, boolean hasMore) {
		this.adapter = adapter;
		refreshView(hasMore ? HAVE_DATA_STATUS : NO_DATA_STATUS);
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.item_more);
		rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading);
		rl_load_error = (RelativeLayout) view.findViewById(R.id.rl_load_error);
		rl_load_error.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				refreshView(HAVE_DATA_STATUS);
				adapter.loadMoreData();
			}
		});
		return view;
	}

	@Override
	public void refreshView(Integer info) {
		this.status = info;
		if (info == ERROR_STATUS) {
			rl_loading.setVisibility(View.GONE);
			rl_load_error.setVisibility(View.VISIBLE);
		} else if (info == HAVE_DATA_STATUS) {
			rl_loading.setVisibility(View.VISIBLE);
			rl_load_error.setVisibility(View.GONE);
		} else {
			rl_loading.setVisibility(View.GONE);
			rl_load_error.setVisibility(View.GONE);
		}
	}

	@Override
	public View getContentView() {
		LogUtil.i("==getContentView==");
		if (status != NO_DATA_STATUS) {
			adapter.loadMoreData();
		}
		return super.getContentView();
	}
}
