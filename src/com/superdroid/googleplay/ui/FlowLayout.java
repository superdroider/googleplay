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
		// ��view���õ��ܸ߶� ���
		int widthSize = MeasureSpec.getSize(widthMeasureSpec)
				- getPaddingLeft() - getPaddingRight();
		int heightSize = MeasureSpec.getSize(heightMeasureSpec)
				- getPaddingTop() - getPaddingBottom();

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		reset();// onMeasure()�������ε��� ����ÿ�ε���ǰ��֤����Ϊ��
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
			// ʹ��view�����Լ��Ĺ淶���²���
			childView.measure(childWidthMeasureSpec, childHeigthMeasureSpec);
			if (line == null) {
				line = new Line();// ����һ��
				usedWidthSize = 0;
			}

			int measuredWidth = childView.getMeasuredWidth();
			usedWidthSize += measuredWidth;// ���µ�ǰ���Ѿ���ʹ�õĿ��
			if (usedWidthSize < widthSize) {// �����ǰ���Ѿ���ʹ�õĿ��С�ڵ�ǰ�еĿ�������view��ӵ���ǰ��
				line.addView(childView);
				usedWidthSize += hPadding;// ���µ�ǰ���Ѿ���ʹ�õĿ��
				if (usedWidthSize >= widthSize) {// �����ǰ���Ѿ���ʹ�õĿ�ȴ��ڵ��ڵ�ǰ�еĿ����ִ�л��в���
					// TODO ����
					newLine();
					usedWidthSize = 0;// �����ѱ�ʹ�ÿ��
				}
			} else {
				if (line.getChildCount() < 1) {
					line.addView(childView);
				}
				newLine();
			}
		}
		if (line != null && line.getChildCount() > 0 && !lines.contains(line)) {
			lines.add(line);// �����һ����ӵ�������
		}

		int totalWidth = MeasureSpec.getSize(widthMeasureSpec);// ��view���ܿ��
		int totalHeight = 0;// ��view���ܸ߶�
		for (int i = 0; i < lines.size(); i++) {
			totalHeight += lines.get(i).height;
		}
		totalHeight += vPadding * (lines.size() - 1);// �м��
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
		// ��ȡ����ʼ����
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
	 * ����
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
			int count = getChildCount();// ��ȡÿ����view�ĸ���
			// ÿһ�п��Ա�ʹ�õĿ��
			int layoutWidth = getMeasuredWidth() - getPaddingLeft()
					- getPaddingRight();
			// ÿһ�������view���ʣ����ÿ��
			int surplusSpacing = layoutWidth - (width + vPadding * (count - 1));
			if (surplusSpacing >= 0) {
				// ʣ��Ŀռ�ƽ�����䵽��view��
				int splitSpacing = (int) (surplusSpacing / count + 0.5);// ��������
				for (int i = 0; i < count; i++) {
					View view = lines.get(i);
					// ��ȡ��view����ĸߺͿ�
					int childWidth = view.getMeasuredWidth();
					int childHeight = view.getMeasuredHeight();
					// ����view�и߶Ƚ�С�Ĵ�ֱ����
					int topOffset = (int) ((height - childHeight) / 2 + 0.5);
					if (topOffset < 0) {
						topOffset = 0;
					}
					// ���¼��㺢�ӵĿ��
					childWidth += splitSpacing;
					view.getLayoutParams().width = childWidth;
					if (splitSpacing > 0) {
						// ���²���
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
				if (getChildCount() == 1) {// ֻ��һ����view�����
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
