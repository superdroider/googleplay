package com.superdroid.googleplay.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
	private Line line = null;
	private List<Line> lines = new ArrayList<FlowLayout.Line>();
	private int usedWidthSize = 0;
	private int vPadding = 20;
	private int hPadding = 20;

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 子view可用的总高度 宽度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec)
				- getPaddingLeft() - getPaddingRight();
		int heightSize = MeasureSpec.getSize(heightMeasureSpec)
				- getPaddingTop() - getPaddingBottom();

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		reset();// onMeasure()方法会多次调用 所以每次调用前保证集合为空
		int count = getChildCount();
		View childView = null;

		for (int i = 0; i < count; i++) {
			childView = getChildAt(i);
			if (childView.getVisibility() == GONE) {
				continue;
			}
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,
					MeasureSpec.AT_MOST);
			int childHeigthMeasureSpec = MeasureSpec.makeMeasureSpec(
					heightSize, MeasureSpec.AT_MOST);
			// 使子view按照自己的规范重新测量
			childView.measure(childWidthMeasureSpec, childHeigthMeasureSpec);
			if (line == null) {
				line = new Line();// 创建一行
				usedWidthSize = 0;
			}

			int measuredWidth = childView.getMeasuredWidth();
			usedWidthSize += measuredWidth;// 更新当前行已经被使用的宽度
			if (usedWidthSize < widthSize) {// 如果当前行已经被使用的宽度小于当前行的宽度则把子view添加到当前行
				line.addView(childView);
				usedWidthSize += hPadding;// 更新当前行已经被使用的宽度
				if (usedWidthSize >= widthSize) {// 如果当前行已经被使用的宽度大于等于当前行的宽度则执行换行操作
					// TODO 换行
					newLine();
					usedWidthSize = 0;// 重置已被使用宽度
				}
			} else {
				if (line.getChildCount() < 1) {
					line.addView(childView);
				}
				newLine();
			}
		}
		if (line != null && line.getChildCount() > 0 && !lines.contains(line)) {
			lines.add(line);// 把最后一行添加到集合中
		}

		int totalWidth = MeasureSpec.getSize(widthMeasureSpec);// 父view的总宽度
		int totalHeight = 0;// 父view的总高度
		for (int i = 0; i < lines.size(); i++) {
			totalHeight += lines.get(i).height;
		}
		totalHeight += vPadding * (lines.size() - 1);// 行间距
		totalHeight += getPaddingBottom() + getPaddingTop();
		setMeasuredDimension(totalWidth, totalHeight);
	}

	private void reset() {
		if (lines != null) {
			lines.clear();
			line = new Line();
			usedWidthSize = 0;
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 获取到起始坐标
		int left = getPaddingLeft();
		int top = getPaddingTop();

		int lineSize = lines.size();
		for (int i = 0; i < lineSize; i++) {
			Line one = lines.get(i);
			one.layout(left, top);
			top = top + one.height + vPadding;
		}
	}

	/**
	 * 换行
	 */
	private void newLine() {
		lines.add(line);
		line = new Line();
		usedWidthSize = 0;
	}

	class Line {
		private int width;
		private int height;
		private List<View> lines;

		public Line() {
			if (lines == null) {
				lines = new ArrayList<View>();
			}
		}

		public void layout(int left, int top) {
			int count = getChildCount();// 获取每行子view的个数
			// 每一行可以被使用的宽度
			int layoutWidth = getMeasuredWidth() - getPaddingLeft()
					- getPaddingRight();
			// 每一行添加子view后的剩余可用宽度
			int surplusSpacing = layoutWidth - (width + vPadding * (count - 1));
			if (surplusSpacing >= 0) {
				// 剩余的空间平均分配到子view上
				int splitSpacing = (int) (surplusSpacing / count + 0.5);// 四舍五入
				for (int i = 0; i < count; i++) {
					View view = lines.get(i);
					// 获取子view本身的高和宽
					int childWidth = view.getMeasuredWidth();
					int childHeight = view.getMeasuredHeight();
					// 让子view中高度较小的垂直居中
					int topOffset = (int) ((height - childHeight) / 2 + 0.5);
					if (topOffset < 0) {
						topOffset = 0;
					}
					// 重新计算孩子的宽度
					childWidth += splitSpacing;
					view.getLayoutParams().width = childWidth;
					if (splitSpacing > 0) {
						// 重新测量
						int widthMeasureSpec = MeasureSpec.makeMeasureSpec(
								childWidth, MeasureSpec.EXACTLY);
						int heightMeasureSpec = MeasureSpec.makeMeasureSpec(
								childHeight, MeasureSpec.EXACTLY);
						view.measure(widthMeasureSpec, heightMeasureSpec);
					}
					view.layout(left, top + topOffset,
							left + view.getMeasuredWidth(), top + topOffset
									+ view.getMeasuredHeight());
					left = left + childWidth + hPadding;
				}
			} else {
				if (getChildCount() == 1) {// 只有一个子view的情况
					View view = lines.get(0);
					view.layout(left, top, left + view.getMeasuredWidth(), top
							+ view.getMeasuredHeight());
				}
			}
		}

		public int getChildCount() {
			return lines.size();
		}

		public void addView(View view) {
			lines.add(view);
			width += view.getMeasuredWidth();
			height = view.getMeasuredHeight() > height ? view
					.getMeasuredHeight() : height;
		}
	}
}
