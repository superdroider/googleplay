package com.superdroid.googleplay.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.manager.ThreadManager;
import com.superdroid.googleplay.utils.UIUtils;

/**
 * // 存放四种页面的容器
 * 
 * @author superdroid
 *
 */
public abstract class LoadingDataPage extends FrameLayout {

	// 界面显示状态
	private final static int STATE_UNKNOWN = 0;
	private final static int STATE_LOADING = 1;
	private final static int STATE_ERROR = 2;
	private final static int STATE_EMPTY = 3;
	private final static int STATE_SUCCESS = 4;

	private View loadingPage;
	private View errorPage;
	private View emptyPage;
	private View successPage;
	private int currentState = STATE_UNKNOWN;// 页面当前状态

	public LoadingDataPage(Context context) {
		super(context);
		init();
	}

	private void init() {
		this.setBackgroundColor(UIUtils.getCoclor(R.color.bg_page));
		loadingPage = createLoadingPage();
		if (loadingPage != null) {
			this.addView(loadingPage, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
		errorPage = createErrorPage();
		if (errorPage != null) {
			this.addView(errorPage, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
		emptyPage = createEmptyPage();
		if (emptyPage != null) {
			this.addView(emptyPage, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
		showPageByState();
	}

	/**
	 * 根据状态控制页面的显示和隐藏
	 */
	private void showPageByState() {
		if (loadingPage != null) {
			loadingPage.setVisibility(currentState == STATE_UNKNOWN
					|| currentState == STATE_LOADING ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (errorPage != null) {
			errorPage.setVisibility(currentState == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyPage != null) {
			emptyPage.setVisibility(currentState == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (currentState == STATE_SUCCESS && successPage == null) {
			successPage = createSuccessPage();
			this.addView(successPage, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
	}

	/**
	 * 创建 成功 页面
	 */
	public abstract View createSuccessPage();

	/**
	 * 创建 空白 页面
	 */
	private View createEmptyPage() {
		return UIUtils.inflate(R.layout.frag_empty_page);
	}

	/**
	 * 创建 错误 页面
	 */
	private View createErrorPage() {
		return UIUtils.inflate(R.layout.frag_error_page);
	}

	/**
	 * 创建 加载中 页面
	 */
	private View createLoadingPage() {
		return UIUtils.inflate(R.layout.frag_loading_page);
	}

	/**
	 * 根据服务器状态和返回数据来动态修改currentState
	 */
	public void show() {
		if (currentState == STATE_EMPTY || currentState == STATE_ERROR) {
			currentState = STATE_UNKNOWN;
		}
		if (currentState == STATE_UNKNOWN) {
			currentState = STATE_LOADING;
		}
		ThreadManager.longTaskExecute(new LoadingTask());
		showPageByState();
	}

	/**
	 * 请求加载数据
	 * 
	 * @return 状态码
	 */
	public abstract LoadResult loadData();

	/**
	 * 网络请求加载数据线程
	 * 
	 * @author superdroid
	 *
	 */
	private class LoadingTask implements Runnable {

		@Override
		public void run() {
			// SystemClock.sleep(2000);
			LoadResult result = loadData();// 获得请求结果码
			currentState = result.getValue();// 根据结果码修改当前页面状态
			UIUtils.runOnMainThread(new Runnable() {
				@Override
				public void run() {
					showPageByState();
				}
			});

		}
	}

	/**
	 * 枚举
	 * 
	 * @author superdroid
	 *
	 */
	public enum LoadResult {
		error(STATE_ERROR), empty(STATE_EMPTY), success(STATE_SUCCESS);
		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
