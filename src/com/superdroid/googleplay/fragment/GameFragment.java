package com.superdroid.googleplay.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.superdroid.googleplay.adapter.ListBaseAdapter;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.protocol.GameProtocol;
import com.superdroid.googleplay.ui.BaseListView;
import com.superdroid.googleplay.ui.LoadingDataPage.LoadResult;

public class GameFragment extends BaseFragment {
	private List<AppInfo> appInfos = null;

	@Override
	public View createSuccessPage() {
		ListView appList = new BaseListView(getActivity());
		appList.setAdapter(new GameAdapter(appInfos, appList));
		appList.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
				true, true));
		return appList;
	}

	@Override
	public LoadResult loadData() {
		appInfos = new GameProtocol().loadData(0);
		return getLoadingStatus(appInfos);
	}

	@Override
	public String getName() {
		return "GameFragment";
	}

	private class GameAdapter extends ListBaseAdapter {

		public GameAdapter(List<AppInfo> datas, ListView listView) {
			super(datas, listView);
		}

		@Override
		public List<AppInfo> onLoadMoreData() {
			return new GameProtocol().loadData(appInfos.size());
		}

	}
}
