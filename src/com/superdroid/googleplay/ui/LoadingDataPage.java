package com.superdroid.googleplay.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.manager.ThreadManager;
import com.superdroid.googleplay.utils.UIUtils;

/**
 * // �������ҳ�������
 * 
 * @author superdroid
 *
 */
public abstract class LoadingDataPage extends FrameLayout {

	// ������ʾ״̬
	private final static int STATE_UNKNOWN = 0;
	private final static int STATE_LOADING = 1;
	private final static int STATE_ERROR = 2;
	private final static int STATE_EMPTY = 3;
	private final static int STATE_SUCCESS = 4;

	private View loadingPage;
	private View errorPage;
	private View emptyPage;
	private View successPage;
	private int currentState = STATE_UNKNOWN;// ҳ�浱ǰ״̬

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
	 * ����״̬����ҳ�����ʾ������
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
	 * ���� �ɹ� ҳ��
	 */
	public abstract View createSuccessPage();

	/**
	 * ���� �հ� ҳ��
	 */
	private View createEmptyPage() {
		return UIUtils.inflate(R.layout.frag_empty_page);
	}

	/**
	 * ���� ���� ҳ��
	 */
	private View createErrorPage() {
		return UIUtils.inflate(R.layout.frag_error_page);
	}

	/**
	 * ���� ������ ҳ��
	 */
	private View createLoadingPage() {
		return UIUtils.inflate(R.layout.frag_loading_page);
	}

	/**
	 * ���ݷ�����״̬�ͷ�����������̬�޸�currentState
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
	 * �����������
	 * 
	 * @return ״̬��
	 */
	public abstract LoadResult loadData();

	/**
	 * ����������������߳�
	 * 
	 * @author superdroid
	 *
	 */
	private class LoadingTask implements Runnable {

		@Override
		public void run() {
			// SystemClock.sleep(2000);
			LoadResult result = loadData();// �����������
			currentState = result.getValue();// ���ݽ�����޸ĵ�ǰҳ��״̬
			UIUtils.runOnMainThread(new Runnable() {
				@Override
				public void run() {
					showPageByState();
				}
			});

		}
	}

	/**
	 * ö��
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
