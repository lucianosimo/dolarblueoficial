package com.lucianosimo.dolar.pageradapter;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.lucianosimo.dolar.CalcTab;
import com.lucianosimo.dolar.R;
import com.lucianosimo.dolar.ValueTab;

public class TabsViewPagerFragmentActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabsViewPagerFragmentActivity.TabInfo>();
    private PagerAdapter mPagerAdapter;
	
	private class TabInfo {
        private String tag;
        /*private Class<?> clss;
        private Bundle args;
        private Fragment fragment;*/
        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
           /* this.clss = clazz;
            this.args = args;*/
        }
   }
	
	/**
     * A simple factory that returns dummy views to the Tabhost
     * @author mwho
     */
    class TabFactory implements TabContentFactory {
 
        private final Context mContext;
 
        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }
 
        /** (non-Javadoc)
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
 
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout
        setContentView(R.layout.tabs_viewpager_layout);
        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
    }
    
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }
 
    /**
     * Initialise ViewPager
     */
    private void intialiseViewPager() {
 
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, ValueTab.class.getName()));
        fragments.add(Fragment.instantiate(this, CalcTab.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }
 
    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        int image_resource;
        
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.separator);    
        tabInfo = new TabInfo("Tab1", ValueTab.class, args);
        image_resource = R.drawable.value_tab_image;
        setupTab(new TextView(this), "ValueTab", image_resource);
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        
        tabInfo = new TabInfo("Tab1", CalcTab.class, args);
        image_resource = R.drawable.calc_tab_image;
        setupTab(new TextView(this), "CalcTab", image_resource);
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        
        mTabHost.setOnTabChangedListener(this);
    }
 
    public void onTabChanged(String tag) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    	
    }
 
    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }
 
    @Override
    public void onPageScrollStateChanged(int state) {
    	
    }
    
    private void setupTab(final View view, final String tag, int image_resource) {
		View tabview = createTabView(mTabHost.getContext(), image_resource);
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
			public View createTabContent(String tag) {
				return view;
			}
		});
		mTabHost.addTab(setContent);
	}
    
    private static View createTabView(final Context context, int image_resource) {
    	View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
    	ImageView tab_image = (ImageView) view.findViewById(R.id.tab_image);
    	tab_image.setBackgroundResource(image_resource);
    	return view;
    }
 
}
