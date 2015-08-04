package com.example.administrator.myapplication.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.image.ImageLoader;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.IndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/13.
 */
public class HomePictureHolder extends BaseHolder<List<String>> implements ViewPager.OnPageChangeListener {

    private RelativeLayout mHeader;
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private IndicatorView mIndicatorView;
    private AutoPlayRunnable autoPlayRunnable;

    @Override
    public void refreshView() {
        List<String> data = getData();
        mIndicatorView.setCount(data.size());
        mViewPager.setCurrentItem(data.size() * 1000, false);
        autoPlayRunnable.start();
    }

    @Override
    public View initView() {
        mHeader = new RelativeLayout(UIUtils.getContext());
        AbsListView.LayoutParams al = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.getDimens(R.dimen.list_header_hight));
        mHeader.setLayoutParams(al);

        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(rl);
        mPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);

        mIndicatorView = new IndicatorView(UIUtils.getContext());
        mIndicatorView.setInterval(5);
        mIndicatorView.setIndicatorDrawable(UIUtils.getDrawable(R.drawable.indicator));

        rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.setMargins(0, 0, 20, 20);
        mIndicatorView.setLayoutParams(rl);
        mIndicatorView.setSelection(0);

        mViewPager.setOnPageChangeListener(this);

        mHeader.addView(mViewPager);
        mHeader.addView(mIndicatorView);

        autoPlayRunnable = new AutoPlayRunnable();
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    autoPlayRunnable.stop();
                }else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    autoPlayRunnable.start();
                }
                return false;
            }
        });
        return mHeader;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndicatorView.setSelection(position % getData().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPagerAdapter extends PagerAdapter {

        List<ImageView> mViewCache = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView;
            if (mViewCache.size() > 0) {
                imageView = mViewCache.remove(0);
            } else {
                imageView = new ImageView(UIUtils.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            int index = position % (getData().size());
            String url = getData().get(index);
            ImageLoader.load(imageView, url);
            container.addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object != null && object instanceof ImageView) {
                ImageView imageView = (ImageView) object;
                container.removeView(imageView);
                mViewCache.add(imageView);
            }
        }
    }
    private class AutoPlayRunnable implements Runnable{

        private static final long AUTO_PLAY_INTERVAL = 5000;
        private  boolean mAutoPlay;

        public  AutoPlayRunnable(){
            mAutoPlay = false;
        }
        public void start(){
            if(!mAutoPlay){
                mAutoPlay = true;
                UIUtils.removeCallbacks(this);
                UIUtils.postDelayed(this,AUTO_PLAY_INTERVAL);
            }
        }
        public void stop(){
            if (mAutoPlay){
                UIUtils.removeCallbacks(this);
                mAutoPlay = false;
            }
        }
        @Override
        public void run() {
            if (mAutoPlay){
                UIUtils.removeCallbacks(this);
                int position = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(position + 1, true);
                UIUtils.postDelayed(this, AUTO_PLAY_INTERVAL);
            }
        }
    }
}