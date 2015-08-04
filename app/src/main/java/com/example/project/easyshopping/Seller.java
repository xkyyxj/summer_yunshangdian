package com.example.project.easyshopping;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.easyshopping.data.Shop;
import com.test.albert.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by acer on 2015/7/7.
 */
public class Seller extends FragmentActivity   {
    public static MyOrders myOrders;
    public static MyStore myStore;
    public static Findings findings;

    private static Fragment mContent;
    public static Shop mShop = null;
    private FragmentManager mfragmentMan;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments=new ArrayList<Fragment>();

    //底部三个按钮

    private LinearLayout mystorebtn;
    private LinearLayout myordersbtn;
    private LinearLayout findingsbtn;

    private ImageView imageView1;
    private TextView textView1;

    private ImageView imageView2;
    private TextView textView2;

    private ImageView imageView3;
    private TextView textView3;

    public static String ShopName;

    private void assignViews() {

        mystorebtn = (LinearLayout) findViewById(R.id.mystorebtn);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        textView1 = (TextView) findViewById(R.id.textView1);
        myordersbtn = (LinearLayout) findViewById(R.id.myordersbtn);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        textView2 = (TextView) findViewById(R.id.textView2);
        findingsbtn = (LinearLayout) findViewById(R.id.findingsbtn);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        textView3 = (TextView) findViewById(R.id.textView3);
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller);

        Intent intent=new Intent();
        //mShop=new Shop();
        //mShop = (Shop) intent.getSerializableExtra("Shop");
        mShop = BmobUser.getCurrentUser(getApplicationContext(),Shop.class);

        //ShopName=getIntent().getStringExtra("ShopName");
        ShopName = mShop.getShopname();
        Log.e("Seller类的onCreate()方法","Main传过来的ShopName是"+ShopName);
        mfragmentMan=getSupportFragmentManager();
        myOrders=new MyOrders();
        myStore= new MyStore();
        findings=new Findings();

        initViewPager();
        initViews();
        initButtons();

    }


    private  void initViewPager(){

        mViewPager=(ViewPager)findViewById(R.id.vPager);
        //mViewPager.setScanScroll(false);

        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(0);

        mViewPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetBtn();
                switch (position) {
                    case 0:
                        ((ImageView) imageView1.findViewById(R.id.imageView1))
                                .setImageResource(R.drawable.shop);
                        break;
                    case 1:
                        ((ImageView) imageView2.findViewById(R.id.imageView2))
                                .setImageResource(R.drawable.order_blue);
                        break;
                    case 2:
                        ((ImageView) imageView3.findViewById(R.id.imageView3))
                                .setImageResource(R.drawable.yellow_find);
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
        });


    }

    private void initViews(){
        assignViews();

        mFragments.add(myStore);
        mFragments.add(myOrders);
        mFragments.add(findings);
        mAdapter.notifyDataSetChanged();



    }

    private  void  initButtons(){
        //按钮一的响应
        mystorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) imageView1.findViewById(R.id.imageView1))
                        .setImageResource(R.drawable.shop);
                mViewPager.setCurrentItem(0);

            }
        });

        //按钮二的响应
        myordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) imageView2.findViewById(R.id.imageView2))
                        .setImageResource(R.drawable.order_blue);
                mViewPager.setCurrentItem(1);
            }
        });
        //按钮三的响应
        findingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) imageView3.findViewById(R.id.imageView3))
                        .setImageResource(R.drawable.yellow_find);
                mViewPager.setCurrentItem(2);
            }
        });
    }


    private void resetBtn()
    {
        ((ImageView) imageView1.findViewById(R.id.imageView1))
                .setImageResource(R.drawable.grey_shop);
        ((ImageView) imageView2.findViewById(R.id.imageView2))
                .setImageResource(R.drawable.order);
        ((ImageView) imageView3.findViewById(R.id.imageView3))
                .setImageResource(R.drawable.grey_find);

    }

    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = mfragmentMan.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, R.anim.abc_slide_out_top);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.vPager, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

}