package com.superdroid.googleplay;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.superdroid.googleplay.fragment.BaseFragment;
import com.superdroid.googleplay.fragment.FragmentFactory;
import com.superdroid.googleplay.holder.MenuHolder;
import com.superdroid.googleplay.utils.UIUtils;

public class MainActivity extends BaseActivity {

	private ViewPager mViewPager;
	private FrameLayout mdrawer_layout;
	private String[] tab_names;
	private PagerTabStrip mTabStrip;// 为viewpage设置标题 可交互
	private DrawerLayout drawer_layout;
	private ActionBarDrawerToggle mTogglel;// 控制侧栏菜单

	private MenuHolder menuHolder;

	private BaseFragment frag;

	private boolean isFirst = true;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		mdrawer_layout = (FrameLayout) findViewById(R.id.mdrawer_layout);
		mViewPager = (ViewPager) findViewById(R.id.mvp);
		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		tab_names = UIUtils.getStringArray(R.array.tab_names);
		mTabStrip = (PagerTabStrip) findViewById(R.id.mtabstrip);
		mViewPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager()));
		mTabStrip.setTabIndicatorColor(getResources().getColor(
				R.color.tab_indicator_color));// 设置导航条颜色
		mTabStrip.setBackgroundColor(Color.WHITE);
		mTabStrip.setTextColor(Color.BLACK);
		mViewPager.setOnPageChangeListener(new MyPagerChangeListener());
		menuHolder = new MenuHolder();
		// TODO
		menuHolder.refreshView(null);
		mdrawer_layout.addView(menuHolder.getContentView());

	}

	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		mTogglel = new ActionBarDrawerToggle(this, drawer_layout,
				R.drawable.ic_drawer_am, R.string.drawer_open_desc,
				R.string.drawer_close_desc) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				UIUtils.showToast("close");
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				UIUtils.showToast("open");
			}

		};
		drawer_layout.setDrawerListener(mTogglel);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mTogglel.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mTogglel.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_search:
			startActivity(new Intent(this, DetailActivity.class));
			return true;
		case android.R.id.home:
			return mTogglel.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.isFirst = true;
	}

	/**
	 * ViewPager适配器
	 * 
	 * @author superdroid
	 *
	 */
	class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			return super.instantiateItem(container, position);
		}

		@Override
		public Fragment getItem(int position) {

			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			return tab_names.length;
		}

		/**
		 * 设置viewpager的标题
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}

		/**
		 * 当前显示的页卡 position页卡位置
		 */
		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			super.setPrimaryItem(container, position, object);

			if (position == 0 && isFirst) {
				frag = FragmentFactory.createFragment(position);
				frag.show();
				isFirst = false;
			}
		}
	}

	/**
	 * viewpager页卡改变监听器
	 * 
	 * @author superdroid
	 *
	 */
	class MyPagerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			frag = FragmentFactory.createFragment(position);
			frag.show();
		}

	}

}
