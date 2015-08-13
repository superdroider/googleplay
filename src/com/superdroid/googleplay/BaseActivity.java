package com.superdroid.googleplay;

import com.superdroid.googleplay.utils.ActivityCollector;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {

	private static Activity activity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		init();
		initView();
		initActionBar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		activity = this;
	}

	@Override
	protected void onPause() {
		super.onPause();
		activity = null;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	/**
	 * 初始化actionbar
	 */
	protected void initActionBar() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化view
	 */
	protected void initView() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化资源
	 */
	protected void init() {
		// TODO Auto-generated method stub

	}

	public static Activity getActivity() {
		return activity;
	}
}
