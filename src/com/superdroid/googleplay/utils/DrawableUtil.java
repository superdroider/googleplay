package com.superdroid.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtil {
	/**
	 * 创建Shape图形资源
	 * 
	 * @param color
	 * @param radius
	 * @param stokeColor
	 * @return
	 */
	public static GradientDrawable getGradientDrawable(int color, int radius,
			int stokeColor) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setGradientType(GradientDrawable.RECTANGLE);// 指定类型为矩形
		drawable.setColor(color);
		drawable.setCornerRadius(radius);
		drawable.setStroke(1, stokeColor);
		return drawable;
	}

	/**
	 * 创建状态选择器
	 * 
	 * @param normalDrawable
	 * @param pressDrawable
	 * @return
	 */
	public static StateListDrawable getStateListDrawable(
			Drawable normalDrawable, Drawable pressDrawable) {
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[] { android.R.attr.state_pressed },
				pressDrawable);
		stateListDrawable.addState(new int[] {}, normalDrawable);
		return stateListDrawable;
	}
}
