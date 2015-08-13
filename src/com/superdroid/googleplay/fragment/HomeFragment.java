package com.superdroid.googleplay.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.superdroid.googleplay.adapter.ListBaseAdapter;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.holder.HomePictureHolder;
import com.superdroid.googleplay.protocol.HomeProtocol;
import com.superdroid.googleplay.ui.BaseListView;
import com.superdroid.googleplay.ui.LoadingDataPage.LoadResult;
import com.superdroid.googleplay.utils.LogUtil;

/**
 * @author superdroid
 */
public class HomeFragment extends BaseFragment {
	private List<AppInfo> appInfos = null;
	private HomePictureHolder mHomePictureHolder;
	private List<String> pictures;

	@Override
	public View createSuccessPage() {
		ListView appList = new BaseListView(getActivity());
		mHomePictureHolder = new HomePictureHolder();
		View view = mHomePictureHolder.initView();
		if (view != null) {
			appList.addHeaderView(view);
			mHomePictureHolder.refreshView(pictures);
		}
		appList.setAdapter(new HoamAdapter(appInfos, appList));
		appList.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
				true, true));
		return appList;
	}

	@Override
	public LoadResult loadData() {
		HomeProtocol homeProtocol = new HomeProtocol();
		appInfos = homeProtocol.loadData(0);
		pictures = homeProtocol.getPictures();
		return getLoadingStatus(appInfos);
	}

	@Override
	public String getName() {
		return "HomeFragment";
	}

	private class HoamAdapter extends ListBaseAdapter {

		public HoamAdapter(List<AppInfo> datas, ListView listView) {
			super(datas, listView);
		}

		@Override
		public List<AppInfo> onLoadMoreData() {
			LogUtil.i("appInfos.size() = " + appInfos.size());
			return new HomeProtocol().loadData(appInfos.size());
		}

	}
}
