package com.test.albert.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.project.easyshopping.data.Ord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bmobObject.Shop;
import bmobObject.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class MainActivity extends Activity{

    public static final String MY_TAG = "RUN:";

    public static String BMOB_API_KEY = "5eb7b9f34bd4f213f1b9da322b9e5f37";
    //通过该值判定所加载并且建立的adapter：1代表建立待处理，2代表建立已完成
    private int creating_adapter = -1;

    private List<View> list = null;//
    private List<String> shop_obj_list = null;//用于查找商店信息，里面存储了查询到的订单中出现的店铺名称
    private List<Ord> ord_content = null;//订单信息，存储查询到的订单对象
    private Map<String,Shop> map = null;//字键值映射，存储由ord_content查询到的商店信息

    private ViewPager pager = null;
    private LayoutInflater inflate = null;
    //待处理订单以及已完成订单对应的listview
    private ListView listview1,listview2;
    private Button main_waiting_button,main_finished_button;

    //待处理adapter:waiting_adapter，已完成adapter:finished_adapter;
    private OrderInfoListAdapter finished_adapter,waiting_adapter;
    private MyViewPagerAdapter adapter = null;

    private MyButtonListener button_li = null;

    //此User应当是一个在BaseActivity或者Application当中的一个User.
    private User user = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_layout);

        Bmob.initialize(getApplicationContext(), BMOB_API_KEY);
        pager = (ViewPager)findViewById(R.id.cus_order_pager);
        initView();
//        loadOrdInfo();
    }

    //根据当前用户来查询订单信息
    //state代表订单状态
    private void loadOrdInfo(int state){
        BmobQuery<Ord> query1 = new BmobQuery<Ord>();
        //呃，错了，不应该用objectId,应该用user.name的。因为Ord当中只存储了username字段（属于User表）
        query1.addWhereEqualTo("username", "123asdf");
        query1.addWhereEqualTo("jystate", state);
        query1.findObjects(this, new FindListener<Ord>() {
            @Override
            public void onSuccess(List<Ord> list) {
                ord_content = list;
                for(Ord ord:list)
                {
                    shop_obj_list.add(ord.getShopname());
                }
                loadShopInfo(shop_obj_list);
            }

            @Override
            public void onError(int i, String s) {
                Log.e(MY_TAG,"loading_order_info_wrong " + s);
            }
        });
    }

    //查询商店信息，根据订单所定商品所属商家
    private void loadShopInfo(List<String> shop_obj_list) {
        BmobQuery<Shop> query = new BmobQuery<Shop>();
        query.addWhereContainedIn("shopname", shop_obj_list);
        query.findObjects(this, new FindListener<Shop>() {
            @Override
            public void onSuccess(List<Shop> list) {
                for (Shop shop : list) {
                    map.put(shop.getShopname(), shop);
                }
                if (creating_adapter == 1) {
                   // if(waiting_adapter == null) {
                        waiting_adapter = new OrderInfoListAdapter(MainActivity.this, OrderInfoListAdapter.WAITING_ORDER, ord_content, map);
                        listview1.setAdapter(waiting_adapter);
                   // }
                   // else
                     //   waiting_adapter.setAdapterContent(ord_content,map);
                    waiting_adapter.notifyDataSetChanged();
                    listview1.invalidate();
                    Log.e(MY_TAG, "loading info successful!");
                    //adapter.notifyDataSetChanged();
                    pager.setCurrentItem(0);
                    //pager.invalidate();
                }
                else if(creating_adapter == 2) {
                   // if(finished_adapter == null) {
                        finished_adapter = new OrderInfoListAdapter(MainActivity.this, OrderInfoListAdapter.FINISHED_ORDER, ord_content, map);
                        listview2.setAdapter(finished_adapter);
                    //}
                    //else
                     //   finished_adapter.setAdapterContent(ord_content,map);
                    finished_adapter.notifyDataSetChanged();
                    listview1.invalidate();
                    Log.e(MY_TAG, "loading info successful!");
                    //adapter.notifyDataSetChanged();
                    pager.setCurrentItem(1);
                    //pager.invalidate();
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e(MY_TAG,"loading_shop_info_wrong " + s);
            }
        });
    }

    //初始化组件
    private void initView()
    {
        OrderInfoListAdapter adapter1,adapter2;
        View view1,view2;
        list = new ArrayList<View>();
        shop_obj_list = new ArrayList<String>();
        map = new HashMap<String,Shop>();
        inflate = LayoutInflater.from(this);
        view1 = inflate.inflate(R.layout.order_info_layout,null);
        view2 = inflate.inflate(R.layout.order_info_layout,null);
        list.add(view1);
        list.add(view2);
        listview1 = (ListView)view1.findViewById(R.id.order_info);
        listview2 = (ListView)view1.findViewById(R.id.order_info);
        main_waiting_button = (Button)findViewById(R.id.waiting);
        main_finished_button = (Button)findViewById(R.id.finished);
        adapter = new MyViewPagerAdapter(list);
        button_li = new MyButtonListener();
        main_waiting_button.setOnClickListener(button_li);
        main_finished_button.setOnClickListener(button_li);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new MyViewPagerChangeListener());
        adapter.notifyDataSetChanged();
    }

    //初始化已完成订单Adapter
    private void initFinishedOrdAdapter()
    {
        creating_adapter = 2;
        loadOrdInfo(1);
    }

    //初始化待处理订单Adapter
    private void initWaitingOrdAdapter()
    {
        creating_adapter = 1;
        loadOrdInfo(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class MyViewPagerAdapter extends PagerAdapter {
        List<View> list = null;

        public MyViewPagerAdapter(List<View> _list) {
            list = _list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            Log.e(MY_TAG,"running on pager adapter");
            return list.get(position);
        }
    }

    class MyViewPagerChangeListener implements ViewPager.OnPageChangeListener
    {
        public void onPageSelected(int position)
        {
            switch(position)
            {
                case 0:
                    Log.e(MY_TAG,"selected waiting order list");
                    initWaitingOrdAdapter();
                    //TODO 改变图片背景
                    break;
                case 1:
                    Log.e(MY_TAG,"selected finished order list");
                    initFinishedOrdAdapter();
                    //TODO 改变图片背景
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
    }

    class MyButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.finished)
            {
                initFinishedOrdAdapter();
                //TODO 改变按钮图片背景
            }
            else if(v.getId() == R.id.waiting)
            {
                initWaitingOrdAdapter();
                //TODO 改变按钮图片背景
            }
        }
    }

}
