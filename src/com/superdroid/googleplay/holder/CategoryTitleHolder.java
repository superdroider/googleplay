package com.superdroid.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.superdroid.googleplay.entities.CategoryInfo;
import com.superdroid.googleplay.utils.UIUtils;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {
	private TextView titileView;

	@Override
	public View initView() {
		titileView = new TextView(UIUtils.getContext());
		return titileView;
	}

	@Override
	public void refreshView(CategoryInfo info) {
		titileView.setText(info.getTitle());
	}

}
