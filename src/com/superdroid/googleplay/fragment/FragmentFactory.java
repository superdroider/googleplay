package com.superdroid.googleplay.fragment;

import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;

/**
 * fragment工厂类 负责fragment的创建
 * 
 * @author superdroid
 *
 */
public class FragmentFactory {

	@SuppressLint("UseSparseArrays")
	public static Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

	/**
	 * 创建fragment
	 * 
	 * @param position
	 * @return
	 */
	public static BaseFragment createFragment(int position) {

		BaseFragment fragment = fragments.get(position);
		if (fragment == null) {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new CategoryFragment();
				break;
			case 5:
				fragment = new RankFragment();
				break;
			}
		}
		fragments.put(position, fragment);
		return fragment;
	}

}
