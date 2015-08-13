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
		// MeasureSpec.getSize()�Ĵ�Сȡ����MeasureSpec��Mode
		// ModeΪEXACTLYʱ getSize()�õ��Ĵ�СΪ��ǰ�����ڸ������е�ʵ�ʿ��ߵĴ�С
		// ModeΪAT_MOSTʱgetSize()�õ��Ĵ�СΪ��ǰ�����ڸ������б�����Ŀ��ߵ����ֵ
		int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
				- getPaddingRight();// ��õ�ǰ���ֵ�ʵ�ʿ��
		int height = MeasureSpec.getSize(heightMeasureSpec)
				- getPaddingBottom() - getPaddingTop();// ��õ�ǰ���ֵ�ʵ�ʸ߶�

		if (widthMeasureMode == MeasureSpec.EXACTLY
				&& heightMeasureMode != MeasureSpec.EXACTLY) {
			// ͨ����������һ����ȷ�ĸ߶�
			height = (int) (width / scale + 0.5f);// ��0.5������������
			// �����µĸ߶ȴ���һ����������
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
