package video.app.com.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Adapter used to handle the tabs from the MainMenu
 *
 */
public class TabsAdapter extends FragmentPagerAdapter implements
	ActionBar.TabListener, ViewPager.OnPageChangeListener {
	private final Context mContext;
	private final ActionBar mActionBar;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		
	static final class TabInfo	{
		private final Class<?> clss;
		private final Bundle args;	
		TabInfo(Class<?> _class, Bundle _args) {
		clss = _class;
		args = _args;
		}
	}

	public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager)	{
		super(activity.getSupportFragmentManager());
		mContext = activity;
		mActionBar = activity.getSupportActionBar();
		mViewPager = pager;
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}

	public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
		TabInfo info = new TabInfo(clss, args);
		tab.setTag(info);
		tab.setTabListener(this);
		mTabs.add(info);
		mActionBar.addTab(tab);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
		TabInfo info = mTabs.get(position);
		return Fragment.instantiate(mContext, info.clss.getName(),
			info.args);
	}

	public void onPageScrolled(int position, float positionOffset,
		int positionOffsetPixels) {
	}

	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);
	}

	public void onPageScrollStateChanged(int state) {
		
	}
		
	@Override
	public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
		FragmentTransaction ft) {
		Object tag = tab.getTag();
		for (int i = 0; i < mTabs.size(); i++) {
			if (mTabs.get(i) == tag) {
				mViewPager.setCurrentItem(i);
			}
		}
	}

	@Override
	public void onTabUnselected(com.actionbarsherlock.app.ActionBar.Tab tab,
		FragmentTransaction ft) {
			
	}
		
	@Override
	public void onTabReselected(com.actionbarsherlock.app.ActionBar.Tab tab,
		FragmentTransaction ft) {
		
	}
	
}	
