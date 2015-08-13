package com.superdroid.googleplay.fragment;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.superdroid.googleplay.adapter.DefaultAdapter;
import com.superdroid.googleplay.entities.CategoryInfo;
import com.superdroid.googleplay.holder.BaseHolder;
import com.superdroid.googleplay.holder.CategoryContentHolder;
import com.superdroid.googleplay.holder.CategoryTitleHolder;
import com.superdroid.googleplay.protocol.CategoryProtocol;
import com.superdroid.googleplay.ui.BaseListView;
import com.superdroid.googleplay.ui.LoadingDataPage.LoadResult;

public class CategoryFragment extends BaseFragment {
	private List<CategoryInfo> infos;
	private CategoryAdapter adapter;

	@Override
	public View createSuccessPage() {
		BaseListView view = new BaseListView(getActivity());
		adapter = new CategoryAdapter(infos, view);
		view.setAdapter(adapter);
		return view;
	}

	@Override
	public LoadResult loadData() {
		infos = new CategoryProtocol().loadData(0);
		return getLoadingStatus(infos);
	}

	@Override
	public String getName() {
		return "CategoryFragment";
	}

	private class CategoryAdapter extends DefaultAdapter<CategoryInfo> {
		private BaseHolder<CategoryInfo> holder;
		private int position;

		public CategoryAdapter(List<CategoryInfo> datas, ListView listView) {
			super(datas, listView);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.position = position;
			return super.getView(position, convertView, parent);
		}

		@Override
		public int getItemViewTypeInner(int position) {
			CategoryInfo categoryInfo = getDatas().get(position);
			if (categoryInfo.isTitle()) {
				return super.getItemViewTypeInner(position) + 1;
			} else {
				return super.getItemViewTypeInner(position);
			}

		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return super.getViewTypeCount() + 1;
		}

		@Override
		public BaseHolder getHolder() {
			CategoryInfo categoryInfo = getDatas().get(position);
			if (categoryInfo.isTitle()) {
				holder = new CategoryTitleHolder();
			} else {
				holder = new CategoryContentHolder();
			}
			return holder;
		}

		@Override
		public boolean hasMore() {
			return false;
		}

		@Override
		public List<CategoryInfo> onLoadMoreData() {
			return null;
		}
	}
}
