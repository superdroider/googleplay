package com.superdroid.googleplay;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.holder.AppDetailBottomHolder;
import com.superdroid.googleplay.holder.AppDetailDesHolder;
import com.superdroid.googleplay.holder.AppDetailInfoHolder;
import com.superdroid.googleplay.holder.AppDetailSafeHolder;
import com.superdroid.googleplay.holder.AppDetailScreenHolder;
import com.superdroid.googleplay.protocol.DetailProtocol;
import com.superdroid.googleplay.ui.LoadingDataPage;
import com.superdroid.googleplay.ui.LoadingDataPage.LoadResult;
import com.superdroid.googleplay.utils.UIUtils;

public class DetailActivity extends BaseActivity {
	public static final String PACKAGENAME = "packagename";

	private String packageName;
	private AppInfo appInfo;
	private FrameLayout bottom_layout;
	private FrameLayout detail_info;
	private FrameLayout detail_safe;
	private FrameLayout detail_des;
	private HorizontalScrollView detail_screen;

	private AppDetailInfoHolder infoHolder;
	private AppDetailBottomHolder bottomHolder;
	private AppDetailSafeHolder safeHolder;
	private AppDetailDesHolder desHolder;
	private AppDetailScreenHolder screenHolder;

	@Override
	protected void initView() {
		LoadingDataPage loadingDataPage = new LoadingDataPage(this) {

			@Override
			public LoadResult loadData() {
				return DetailActivity.this.loadData();
			}

			@Override
			public View createSuccessPage() {
				return DetailActivity.this.createSuccessPage();
			}
		};
		packageName = getIntent().getStringExtra(PACKAGENAME);
		setContentView(loadingDataPage);
		loadingDataPage.show();
	}

	protected View createSuccessPage() {
		View view = UIUtils.inflate(R.layout.activity_detail);

		bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);
		detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
		detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
		detail_screen = (HorizontalScrollView) view
				.findViewById(R.id.detail_screen);

		bottomHolder = new AppDetailBottomHolder();
		bottomHolder.refreshView(appInfo);
		bottom_layout.addView(bottomHolder.getContentView());

		infoHolder = new AppDetailInfoHolder();
		infoHolder.refreshView(appInfo);
		detail_info.addView(infoHolder.getContentView());

		safeHolder = new AppDetailSafeHolder();
		safeHolder.refreshView(appInfo);
		detail_safe.addView(safeHolder.getContentView());

		desHolder = new AppDetailDesHolder();
		desHolder.refreshView(appInfo);
		detail_des.addView(desHolder.getContentView());

		screenHolder = new AppDetailScreenHolder();
		screenHolder.refreshView(appInfo);
		detail_screen.addView(screenHolder.getContentView());
		return view;
	}

	protected LoadResult loadData() {
		DetailProtocol detailProtocol = new DetailProtocol(packageName);
		appInfo = detailProtocol.loadData(0);
		return getLoadStatus(appInfo);
	}

	private LoadResult getLoadStatus(AppInfo appInfo) {
		if (appInfo == null) {
			return LoadResult.error;
		} else {
			return LoadResult.success;
		}
	}

}
