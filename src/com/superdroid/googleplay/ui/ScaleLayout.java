package com.superdroid.googleplay.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ScaleLayout extends FrameLayout {

	private float scale = 2.43f;

	public ScaleLayout(Context context) {
		this(context, null);
	}

	public ScaleLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		scale = attrs.getAttributeFloatValue(
				"http://schemas.android.com/apk/res/com.superdroid.googleplay",
				"scale", 2.43f);
	}

	public ScaleLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
		// MeasureSpec.getSize()的大小取决于MeasureSpec的Mode
		// Mode为EXACTLY时 getSize()得到的大小为当前布局在父布局中的实际宽或高的大小
		// Mode为AT_MOST时getSize()得到的大小为当前布局在父布局中被允许的宽或高的最大值
		int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
				- getPaddingRight();// 获得当前布局的实际宽度
		int height = MeasureSpec.getSize(heightMeasureSpec)
				- getPaddingBottom() - getPaddingTop();// 获得当前布局的实际高度

		if (widthMeasureMode == MeasureSpec.EXACTLY
				&& heightMeasureMode != MeasureSpec.EXACTLY) {
			// 通过比例设置一个精确的高度
			height = (int) (width / scale + 0.5f);// 加0.5方便四舍五入
			// 按照新的高度创建一个测量尺子
			heightMeasureSpec = MeasureSpec
					.makeMeasureSpec(height + getPaddingTop()
							+ getPaddingBottom(), MeasureSpec.EXACTLY);
		} else if (heightMeasureMode == MeasureSpec.EXACTLY
				&& widthMeasureMode != MeasureSpec.EXACTLY) {
			width = (int) (height * scale + 0.5f);
			widthMeasureSpec = MeasureSpec
					.makeMeasureSpec(width + getPaddingLeft()
							+ getPaddingRight(), MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
