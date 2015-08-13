package com.superdroid.googleplay.holder;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.utils.LogUtil;
import com.superdroid.googleplay.utils.UIUtils;

public class AppDetailDesHolder extends BaseHolder<AppInfo> implements
		OnClickListener {
	private RelativeLayout des_layout;
	private TextView des_content;
	private TextView tv_author;
	private ImageView des_arrow;
	private ScrollView sl_content;

	private AppInfo info;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_des);
		des_layout = (RelativeLayout) view.findViewById(R.id.des_layout);
		des_content = (TextView) view.findViewById(R.id.des_content);
		tv_author = (TextView) view.findViewById(R.id.tv_author);
		des_arrow = (ImageView) view.findViewById(R.id.des_arrow);

		des_layout.setOnClickListener(this);
		des_arrow.setTag(false);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		this.info = info;
		des_content.getLayoutParams().height = getShortMeasuredHeight();
		des_content.setText(info.getDes());
		tv_author.setText(UIUtils.getString(R.string.detail_des_author)
				+ info.getAuthor());
	}

	/**
	 * 测量布局高度
	 * 
	 * @return
	 */
	private int getLongMeasuredHeight() {
		int heightMax = 1000;// 控件safe_content可以向父布局申请的最大高度
		// 控件宽度
		int width = des_content.getMeasuredWidth();
		// 将控件safe_content的高度设置为WRAP_CONTENT
		des_content.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;

		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMax,
				MeasureSpec.AT_MOST);// 制作一个测量高度的尺子 最大高度不超过1000
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);// 制作一个测量高度的尺子 宽度大小为精确值width
		// 重新测量
		des_content.measure(widthMeasureSpec, heightMeasureSpec);
		return des_content.getMeasuredHeight();
	}

	/**
	 * 测量布局高度
	 * 
	 * @return
	 */
	private int getShortMeasuredHeight() {
		int heightMax = 1000;// 控件safe_content可以向父布局申请的最大高度
		// 控件宽度
		int width = des_content.getMeasuredWidth();
		// 将控件safe_content的高度设置为WRAP_CONTENT
		des_content.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;

		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMax,
				MeasureSpec.AT_MOST);// 制作一个测量高度的尺子 最大高度不超过1000
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);// 制作一个测量高度的尺子 宽度大小为精确值width
		if (des_content.getLineCount() > 7) {

		}
		TextView tv = getTextView();
		tv.setMaxLines(7);
		tv.setLines(7);
		// 重新测量
		tv.measure(widthMeasureSpec, heightMeasureSpec);
		return tv.getMeasuredHeight();
	}

	/**
	 * 为了方便测量des_content的高度而克隆出来的TextView
	 * 
	 * @return
	 */
	private TextView getTextView() {
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(info.getDes());
		return tv;
	}

	@Override
	public void onClick(View v) {
		expand();
	}

	/**
	 * 布局的展开和关闭
	 */
	private void expand() {
		int lines = des_content.getLineCount();
		if (lines < 7) {
			return;
		}
		final LayoutParams params = (LayoutParams) des_content
				.getLayoutParams();
		int height = 0;// 测量布局高度
		int targetHeight = 0;// 测量safe_content的高度

		boolean flag = (Boolean) des_arrow.getTag();
		if (flag) {
			height = getLongMeasuredHeight();
			targetHeight = getShortMeasuredHeight();
			flag = false;
		} else {
			height = getShortMeasuredHeight();// 测量布局高度
			targetHeight = getLongMeasuredHeight();// 测量safe_content的高度
			flag = true;
		}
		des_arrow.setTag(flag);

		ValueAnimator animator = ValueAnimator.ofInt(height, targetHeight);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			/**
			 * 动画的值发生变化时回调
			 */
			@Override
			public void onAnimationUpdate(ValueAnimator v) {
				int value = (Integer) v.getAnimatedValue();
				params.height = value;
				des_content.setLayoutParams(params);
				if ((Boolean) des_arrow.getTag()) {
					if (sl_content == null) {
						sl_content = getScrollView();
					}
					sl_content.smoothScrollTo(0, sl_content.getHeight());
				}

			}
		});
		// 动画执行时的事件回调
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				des_content.setEnabled(false);
				LogUtil.i("onAnimationStart");
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				LogUtil.i("onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				LogUtil.i("onAnimationEnd");
				boolean flag = (Boolean) des_arrow.getTag();
				des_arrow.setImageResource(flag ? R.drawable.arrow_up
						: R.drawable.arrow_down);
				des_content.setEnabled(true);

			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				LogUtil.i("onAnimationCancel");
			}
		});
		animator.setDuration(300);
		animator.start();

	}

	/**
	 * 得到父布局中的ScrollView
	 * 
	 * @return
	 */
	private ScrollView getScrollView() {
		ScrollView mScrollView = null;
		View currentView = des_content;
		View parent = null;
		while (true) {
			parent = (View) currentView.getParent();
			if (parent != null && parent instanceof ViewGroup) {
				currentView = parent;
				if (currentView instanceof ScrollView) {
					mScrollView = (ScrollView) currentView;
					break;
				}
			} else {
				break;
			}
		}
		return mScrollView;
	}
}
