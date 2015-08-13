package com.superdroid.googleplay.holder;

import android.view.View;

public abstract class BaseHolder<T> {
	protected View contentView = null;// 每一个item的view

	public BaseHolder() {
		contentView = initView();
		contentView.setTag(this);
	}

	public View getContentView() {
		return contentView;
	}

	public abstract View initView();

	public abstract void refreshView(T info);
}
