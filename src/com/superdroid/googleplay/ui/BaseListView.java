package com.superdroid.googleplay.ui;

import com.superdroid.googleplay.R;
import com.superdroid.googleplay.utils.UIUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class BaseListView extends ListView {

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public BaseListView(Context context) {
		this(context, null);
	}

	private void init() {
		this.setSelector(UIUtils.getDrawable(R.drawable.nothing));
		this.setCacheColorHint(UIUtils.getCoclor(R.color.bg_page));
		this.setDivider(UIUtils.getDrawable(R.drawable.nothing));
	}

}
