package com.superdroid.googleplay.fragment;

import java.util.List;
import java.util.Random;

import com.superdroid.googleplay.protocol.HotProtocol;
import com.superdroid.googleplay.ui.FlowLayout;
import com.superdroid.googleplay.ui.LoadingDataPage.LoadResult;
import com.superdroid.googleplay.utils.DrawableUtil;
import com.superdroid.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RankFragment extends BaseFragment {
	private List<String> data;

	@Override
	public View createSuccessPage() {
		ScrollView view = new ScrollView(getActivity());
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		view.setFillViewport(true);

		FlowLayout linearLayout = new FlowLayout(getActivity());
		linearLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		linearLayout.setOrientation(LinearLayout.VERTICAL);
		int layoutPadding = UIUtils.px2dip(10);
		linearLayout.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding);
		TextView text = null;
		Random random = new Random();
		int rgb;
		int red = 32;
		int green = 32;
		int blue = 32;
		int bgColor = 0xcecece;
		StateListDrawable stateListDrawable = null;
		GradientDrawable normalDrawable = null;
		GradientDrawable pressedDrawable = DrawableUtil.getGradientDrawable(
				bgColor, UIUtils.px2dip(8f), bgColor);
		for (final String string : data) {
			red = 32 + random.nextInt(208);
			green = 32 + random.nextInt(208);
			blue = 32 + random.nextInt(208);
			rgb = Color.rgb(red, green, blue);
			normalDrawable = DrawableUtil.getGradientDrawable(rgb,
					UIUtils.px2dip(8f), rgb);
			text = new TextView(getActivity());
			text.setLayoutParams(new LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			text.setText(string);
			text.setPadding(UIUtils.px2dip(4f), UIUtils.px2dip(2f),
					UIUtils.px2dip(4f), UIUtils.px2dip(2f));
			text.setTextColor(Color.BLACK);
			stateListDrawable = DrawableUtil.getStateListDrawable(
					normalDrawable, pressedDrawable);
			text.setBackgroundDrawable(stateListDrawable);
			text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UIUtils.showToast(string);
				}
			});
			linearLayout.addView(text);
		}
		view.addView(linearLayout);
		return view;
	}

	@Override
	public LoadResult loadData() {
		HotProtocol hotProtocol = new HotProtocol();
		data = hotProtocol.loadData(0);
		return getLoadingStatus(data);
	}

	@Override
	public String getName() {
		return "RankFragment";
	}
}
