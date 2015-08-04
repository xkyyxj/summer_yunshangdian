package com.example.project.easyshopping;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.easyshopping.data.Ord;
import com.test.albert.myapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by acer on 2015/7/6.
 */
public class MyOrders extends Fragment {
    public static Xindingdan xdd;
    public static Daichuli dcl;
    public static Yiwancheng ywc;

    public static Ord mOrd;
    public static final String XDD = "0";
    public static final String DCL_1 = "11";//待处理未接单
    public static final String DCL_2 = "12";//待处理已接单
    public static final String YWC_1 = "21";//已完成未评价
    public static final String YWC_2 = "22";//已完成已评价
    public static final String YWC_3 = "23";//已取消订单
    private View mMainView;
    LayoutInflater inflater1;
    private ViewPager viewPager;//页卡内容
    private ImageView imageView;// 动画图片
    private TextView textView0, textView1, textView2, textView3;
    private float offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private float bmpW;// 动画图片宽度
    private View view1, view2, view3;//各个页卡
    private String[] itemname;
    private ImageButton back;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xdd = new Xindingdan();
        dcl = new Daichuli();
        ywc = new Yiwancheng();
        mOrd = new Ord();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mMainView = inflater.inflate(R.layout.orders, container, false);
        inflater1 = inflater;

        InitImageView();
        InitViews();
        InitViewPager();
        InitButton();

        return mMainView;

    }

    private void InitButton() {


    }

    private void InitViewPager() {


       /* viewPager = (ViewPager) mMainView.findViewById(R.id.vPager1);

        views = new ArrayList<View>();



        view1 = inflater1.inflate(R.layout.xindingdan, null);
        view2 = inflater1.inflate(R.layout.daichuli, null);
        view3 = inflater1.inflate(R.layout.yiwancheng, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);


        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());*/

        mViewPager = (ViewPager) mMainView.findViewById(R.id.vPager1);
        //mViewPager.setScanScroll(false);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
/**
 * 下面这句notify能删除吗？！？！？！？！？！？！
 */
        //  mAdapter.notifyDataSetChanged();

        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(0);
       /* mViewPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetBtn();
                switch (position) {
                    case 0:
                        textView1.setBackgroundDrawable(getResources().getDrawable(R.drawable.on));
                        break;
                    case 1:
                        textView2.setBackgroundDrawable(getResources().getDrawable(R.drawable.on));

                        break;
                    case 2:
                        textView3.setBackgroundDrawable(getResources().getDrawable(R.drawable.on));

                        break;
                }
                currentIndex = position;
                mViewPager.setCurrentItem(currentIndex);


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });*/

    }

    /**
     * 初始化头标
     */

    private void InitViews() {
        textView0 = (TextView) mMainView.findViewById(R.id.textView0);
        textView1 = (TextView) mMainView.findViewById(R.id.textView1);
        textView2 = (TextView) mMainView.findViewById(R.id.textView2);
        textView3 = (TextView) mMainView.findViewById(R.id.textView3);

        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));

        itemname = new String[4];
        itemname[0] = (textView1.getText().toString());
        itemname[1] = (textView2.getText().toString());
        itemname[2] = (textView3.getText().toString());
        textView0.setText(itemname[currIndex]);


        mFragments.add(xdd);
        mFragments.add(dcl);
        mFragments.add(ywc);

    }

    /**
     * 2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     * 3
     */

    private void InitImageView() {
        imageView = (ImageView) mMainView.findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置


    }

    /**
     * 头标点击监听 3
     */
    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }

    }

    /*public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }*/

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        float one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        float two = one * 2;// 页卡1 -> 页卡3 偏移量


        @Override
        public void onPageSelected(int position) {
            Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);
            switch (position) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = position;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
            imageView.startAnimation(animation);
            textView0.setText(itemname[currIndex]);
            // Toast.makeText(MyOrders.this, "您选择了" + viewPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
    }


    private void resetBtn() {

        (textView1.findViewById(R.id.textView1)).setBackgroundDrawable(null);

        (textView2.findViewById(R.id.textView2)).setBackgroundDrawable(null);

        (textView3.findViewById(R.id.textView3)).setBackgroundDrawable(null);


    }
}
