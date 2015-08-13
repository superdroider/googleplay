package com.superdroid.googleplay.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.superdroid.googleplay.holder.BaseHolder;
import com.superdroid.googleplay.holder.LoadMoreHolder;
import com.superdroid.googleplay.manager.ThreadManager;
import com.superdroid.googleplay.utils.LogUtil;
import com.superdroid.googleplay.utils.UIUtils;

public abstract class DefaultAdapter<T> extends BaseAdapter implements
		OnItemClickListener {

	private static final int ITEM_VIEW_TYPE = 0;
	private static final int MORE_VIEW_TYPE = 1;

	private LoadMoreHolder<T> loadMoreHolder;

	private ListView listView;

	private List<T> datas;

	public DefaultAdapter(List<T> datas, ListView listView) {
		this.datas = datas;
		this.listView = listView;
		listView.setOnItemClickListener(this);
		if (datas == null) {
			datas = new ArrayList<T>();
		}
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int headerViewsCount = listView.getHeaderViewsCount();
		int index = position - headerViewsCount;
		itemClick(index);
	}

	protected void itemClick(int index) {
		UIUtils.showToast("--" + index + "--");
	}

	@Override
	public int getItemViewType(int position) {
		if (position < datas.size()) {
			return ITEM_VIEW_TYPE;
		} else {
			return MORE_VIEW_TYPE;
		}
	}

	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;
	}

	public int getItemViewTypeInner(int position) {
		return ITEM_VIEW_TYPE;
	}

	@Override
	public int getCount() {
		return datas.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		if (position < datas.size()) {
			return datas.get(position);
		} else {
			return position;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder<T> holder = null;
		View view = null;
		if (convertView == null) {
			if (getItemViewType(position) == MORE_VIEW_TYPE) {
				holder = (BaseHolder<T>) getLoadMoreHolder();
			} else {
				holder = (BaseHolder<T>) getHolder();
			}
		} else {
			view = convertView;
			holder = (BaseHolder<T>) view.getTag();
		}
		if (getItemViewType(position) == MORE_VIEW_TYPE) {
		} else {
			if (position < datas.size()) {
				holder.refreshView(datas.get(position));
			}
		}
		return holder.getContentView();
	}

	public boolean hasMore() {
		return true;
	}

	private BaseHolder<Integer> getLoadMoreHolder() {
		if (loadMoreHolder == null) {
			loadMoreHolder = new LoadMoreHolder<T>(this, hasMore());
		}
		return loadMoreHolder;
	}

	public abstract BaseHolder<T> getHolder();

	private volatile boolean isLoading = false;

	public void loadMoreData() {
		if (isLoading) {
			return;
		}
		isLoading = true;
		ThreadManager.longTaskExecute(new Runnable() {

			@Override
			public void run() {
				final List<T> newDatas = onLoadMoreData();
				UIUtils.runOnMainThread(new Runnable() {

					@Override
					public void run() {
						// 刷新页面布局
						if (newDatas == null) {
							loadMoreHolder
									.refreshView(LoadMoreHolder.ERROR_STATUS);
						} else if (newDatas.size() < 20) {
							loadMoreHolder
									.refreshView(LoadMoreHolder.NO_DATA_STATUS);
						} else {
							loadMoreHolder
									.refreshView(LoadMoreHolder.HAVE_DATA_STATUS);
						}
						// 刷新数据
						if (newDatas != null && newDatas.size() > 0) {
							datas.addAll(newDatas);
							notifyDataSetChanged();
						}
						isLoading = false;
					}
				});
			}
		});

	}

	/**
	 * 加载更多数据
	 * 
	 * @return
	 */
	public abstract List<T> onLoadMoreData();

}
