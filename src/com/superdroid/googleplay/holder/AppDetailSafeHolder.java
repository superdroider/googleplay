package com.superdroid.googleplay.holder;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.superdroid.googleplay.R;
import com.superdroid.googleplay.entities.AppInfo;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.LogUtil;
import com.superdroid.googleplay.utils.UIUtils;

public class AppDetailSafeHolder extends BaseHolder<AppInfo> implements
		OnClickListener {
	private RelativeLayout safe_layout;
	private LinearLayout safe_content;
	private ImageView safe_imgs[];
	private ImageView safe_arrow;
	private LinearLayout safe_des[];
	private ImageView iv_des[];
	private TextView tv_des[];

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_safe);
		safe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);
		safe_content = (LinearLayout) view.findViewById(R.id.safe_content);
		safe_arrow = (ImageView) view.findViewById(R.id.safe_arrow);

		safe_imgs = new ImageView[4];
		safe_imgs[0] = (ImageView) view.findViewById(R.id.safe_img0);
		safe_imgs[1] = (ImageView) view.findViewById(R.id.safe_img1);
		safe_imgs[2] = (ImageView) view.findViewById(R.id.safe_img2);
		safe_imgs[3] = (ImageView) view.findViewById(R.id.safe_img3);

		safe_des = new LinearLayout[4];
		safe_des[0] = (LinearLayout) view.findViewById(R.id.safe_des0);
		safe_des[1] = (LinearLayout) view.findViewById(R.id.safe_des1);
		safe_des[2] = (LinearLayout) view.findViewById(R.id.safe_des2);
		safe_des[3] = (LinearLayout) view.findViewById(R.id.safe_des3);

		iv_des = new ImageView[4];
		iv_des[0] = (ImageView) view.findViewById(R.id.iv_des0);
		iv_des[1] = (ImageView) view.findViewById(R.id.iv_des1);
		iv_des[2] = (ImageView) view.findViewById(R.id.iv_des2);
		iv_des[3] = (ImageView) view.findViewById(R.id.iv_des3);

		tv_des = new TextView[4];
		tv_des[0] = (TextView) view.findViewById(R.id.tv_des0);
		tv_des[1] = (TextView) view.findViewById(R.id.tv_des1);
		tv_des[2] = (TextView) view.findViewById(R.id.tv_des2);
		tv_des[3] = (TextView) view.findViewById(R.id.tv_des3);

		RelativeLayout.LayoutParams params = (LayoutParams) safe_content
				.getLayoutParams();
		params.height = 0;
		safe_content.setLayoutParams(params);
		safe_layout.setOnClickListener(this);

		safe_arrow.setTag(false);// tag��ҪΪ�����ֶ�����(ture)��ر�(false)״̬
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		List<String> safeUrl = info.getSafeUrl();
		List<String> safeDesUrl = info.getSafeDesUrl();
		List<String> safeDes = info.getSafeDes();
		List<Integer> safeDesColor = info.getSafeDesColor();

		for (int i = 0; i < 4; i++) {
			if (i < safeUrl.size() && i < safeDesUrl.size()
					&& i < safeDes.size() && i < safeDesColor.size()) {
				BitmapHelper.getBitmapUtils()
						.display(
								safe_imgs[i],
								Constants.BASE_URL + Constants.IMG_URL
										+ safeUrl.get(i));
				BitmapHelper.getBitmapUtils().display(
						iv_des[i],
						Constants.BASE_URL + Constants.IMG_URL
								+ safeDesUrl.get(i));

				tv_des[i].setText(safeDes.get(i));
				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0);
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				}
				tv_des[i].setTextColor(color);
			} else {
				safe_imgs[i].setVisibility(View.GONE);
				safe_des[i].setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		expand();
	}

	/**
	 * չ��safe_content
	 */
	private void expand() {
		final LayoutParams params = (LayoutParams) safe_content
				.getLayoutParams();
		int height = 0;// �������ָ߶�
		int targetHeight = 0;// ����safe_content�ĸ߶�

		boolean flag = (Boolean) safe_arrow.getTag();
		if (flag) {
			height = getMeasuredHeight();
			targetHeight = 0;
			flag = false;
		} else {
			height = 0;// �������ָ߶�
			targetHeight = getMeasuredHeight();// ����safe_content�ĸ߶�
			flag = true;
		}
		safe_arrow.setTag(flag);

		ValueAnimator animator = ValueAnimator.ofInt(height, targetHeight);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			/**
			 * ������ֵ�����仯ʱ�ص�
			 */
			@Override
			public void onAnimationUpdate(ValueAnimator v) {
				int value = (Integer) v.getAnimatedValue();
				// LogUtil.e("value=" + value + "alpha = "
				// + safe_arrow.getImageAlpha());
				params.height = value;
				safe_content.setLayoutParams(params);
			}
		});
		// ����ִ��ʱ���¼��ص�
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				safe_layout.setEnabled(false);
				LogUtil.i("onAnimationStart");
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				LogUtil.i("onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				LogUtil.i("onAnimationEnd");
				boolean flag = (Boolean) safe_arrow.getTag();
				safe_arrow.setImageResource(flag ? R.drawable.arrow_up
						: R.drawable.arrow_down);
				safe_layout.setEnabled(true);
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
	 * �������ָ߶�
	 * 
	 * @return
	 */
	private int getMeasuredHeight() {
		int heightMax = 1000;// �ؼ�safe_content�����򸸲�����������߶�
		// �ؼ����
		int width = safe_content.getMeasuredWidth();
		// ���ؼ�safe_content�ĸ߶�����ΪWRAP_CONTENT
		safe_content.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;

		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMax,
				MeasureSpec.AT_MOST);// ����һ�������߶ȵĳ��� ���߶Ȳ�����1000
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);// ����һ�������߶ȵĳ��� ��ȴ�СΪ��ȷֵwidth
		// ���²���
		safe_content.measure(widthMeasureSpec, heightMeasureSpec);
		return safe_content.getMeasuredHeight();
	}

}
