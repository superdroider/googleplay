package com.superdroid.googleplay.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.BitmapUtils;
import com.superdroid.googleplay.R;
import com.superdroid.googleplay.ui.LoadingDataPage;
import com.superdroid.googleplay.ui.LoadingDataPage.LoadResult;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.ViewUtils;

/**
 * 共有四种页面 加载中 内容为空 错误页面 成功页面 根据当前页面状态来决定纷fragment显示什么页面
 * 
 * @author superdroid
 *
 */
public abstract class BaseFragment extends Fragment {

	private LoadingDataPage loadingDataPage;// 存放四种页面的容器
	protected BitmapUtils bitmapUtils;

	public abstract String getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBitmapUtils();
		loadingDataPage = new LoadingDataPage(getActivity()) {

			@Override
			public LoadResult loadData() {
				return BaseFragment.this.loadData();
			}

			@Override
			public View createSuccessPage() {
				return BaseFragment.this.createSuccessPage();
			}
		};
	}

	/**
	 * 初始化bitmapUtils
	 */
	private void initBitmapUtils() {
		if (bitmapUtils == null) {
			bitmapUtils = BitmapHelper.getBitmapUtils();
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
			bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (loadingDataPage == null) {
			loadingDataPage = new LoadingDataPage(getActivity()) {

				@Override
				public LoadResult loadData() {
					return BaseFragment.this.loadData();
				}

				@Override
				public View createSuccessPage() {
					return BaseFragment.this.createSuccessPage();
				}
			};
		} else {
			ViewUtils.removeParent(loadingDataPage);
		}
		return loadingDataPage;
	}

	public void show() {
		if (loadingDataPage != null) {
			loadingDataPage.show();
		}
	}

	public abstract LoadResult loadData();

	public abstract View createSuccessPage();

	/**
	 * 获取加载数据的状态
	 * 
	 * @return
	 */
	protected <T> LoadResult getLoadingStatus(List<T> list) {
		if (list == null) {
			return LoadResult.error;
		} else if (list.size() == 0) {
			return LoadResult.empty;
		} else {
			return LoadResult.success;
		}
	}
}
