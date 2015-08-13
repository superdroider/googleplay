package com.superdroid.googleplay.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.superdroid.googleplay.adapter.DefaultAdapter;
import com.superdroid.googleplay.entities.SubjectInfo;
import com.superdroid.googleplay.holder.BaseHolder;
import com.superdroid.googleplay.holder.SubjectHolder;
import com.superdroid.googleplay.protocol.SubjectProtocol;
import com.superdroid.googleplay.ui.BaseListView;
import com.superdroid.googleplay.ui.LoadingDataPage.LoadResult;

public class SubjectFragment extends BaseFragment {
	private List<SubjectInfo> subjectInfos;
	private ListView subjectList;

	@Override
	public View createSuccessPage() {
		subjectList = new BaseListView(getActivity());
		subjectList.setAdapter(new SubjectInfoAdapter(subjectInfos,subjectList));
		return subjectList;
	}

	@Override
	public LoadResult loadData() {
		subjectInfos = new SubjectProtocol().loadData(0);
		return getLoadingStatus(subjectInfos);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SubjectFragment";
	}

	class SubjectInfoAdapter extends DefaultAdapter<SubjectInfo> {

		public SubjectInfoAdapter(List<SubjectInfo> list, ListView listView) {
			super(list, listView);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder() {
			return new SubjectHolder();
		}

		@Override
		public List<SubjectInfo> onLoadMoreData() {
			return new SubjectProtocol().loadData(subjectInfos.size());
		}

	}

}
