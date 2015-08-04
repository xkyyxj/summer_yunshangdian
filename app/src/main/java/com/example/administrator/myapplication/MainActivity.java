package com.example.administrator.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.myapplication.fragment.BaseFragment;
import com.example.administrator.myapplication.fragment.FragmentFactory;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.PagerTab;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, DrawerLayout.DrawerListener {

    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawer_layout;

    @Override
    protected void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setTitle(UIUtils.getString(R.string.app_name));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer_layout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerShadow(R.drawable.ic_drawer_shadow, GravityCompat.START);
        drawer_layout.setDrawerListener(this);

        PagerTab tabs = (PagerTab) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int
            positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        BaseFragment fragment = FragmentFactory.createFragment(position);
        fragment.show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
            actionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        actionBarDrawerToggle.onDrawerOpened(drawerView);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        actionBarDrawerToggle.onDrawerClosed(drawerView);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        actionBarDrawerToggle.onDrawerStateChanged(newState);
    }
    private class MainAdapter extends FragmentPagerAdapter{

        private final String [] tab_names;
        public MainAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            tab_names = UIUtils.getStringArray(R.array.tab_names);

        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tab_names[position];
        }

        @Override
        public int getCount() {
            return tab_names.length;
        }
    }
}
