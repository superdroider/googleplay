package com.superdroid.googleplay.holder;

import java.util.List;
import android.annotation.SuppressLint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import com.superdroid.googleplay.R;
import com.superdroid.googleplay.utils.BitmapHelper;
import com.superdroid.googleplay.utils.Constants;
import com.superdroid.googleplay.utils.UIUtils;

@SuppressLint("ClickableViewAccessibility")
public class HomePictureHolder extends BaseHolder<List<String>> {
	private ViewPager viewPager;
	private AutoChangeImageTask mTask;

	@Override
	public View initView() {
		viewPager = new ViewPager(UIUtils.getContext());
		viewPager.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) UIUtils
						.getDemen(R.dimen.item_home_picture_heit)));
		return viewPager;
	}

	@Override
	public void refreshView(List<String> info) {
		viewPager.setAdapter(new HomePictureAdapter(info));
		viewPager.setCurrentItem(100);
		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mTask.stop();
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					mTask.start();
					break;
				}
				return false;
			}
		});
		mTask = new AutoChangeImageTask();
		mTask.start();
	}

	/**
	 * ×Ô¶¯ÇÐ»»Í¼Æ¬
	 * 
	 * @author superdroid
	 *
	 */
	private class AutoChangeImageTask implements Runnable {
		boolean isAutoRun = false;

		public void start() {
			if (!isAutoRun) {
				isAutoRun = true;
				run();
			}
		}

		public void stop() {
			if (isAutoRun) {
				isAutoRun = false;
				UIUtils.cancleTask(this);
			}
		}

		@Override
		public void run() {
			UIUtils.cancleTask(this);
			int currentPosition = viewPager.getCurrentItem();
			currentPosition++;
			viewPager.setCurrentItem(currentPosition);
			UIUtils.executeDelayedTask(this, 3000);
		}
	}

	private class HomePictureAdapter extends PagerAdapter {
		private List<String> info;

		public HomePictureAdapter(List<String> info) {
			this.info = info;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int index = position % info.size();
			ImageView imageView = new ImageView(UIUtils.getContext());
			BitmapHelper.getBitmapUtils().display(imageView,
					Constants.BASE_URL + Constants.IMG_URL + info.get(index));
			container.addView(imageView);
			return imageView;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

	}
}
