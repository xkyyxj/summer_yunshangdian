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
    //ͨ����ֵ�ж������ز��ҽ�����adapter��1������������2�����������
    private int creating_adapter = -1;

    private List<View> list = null;//
    private List<String> shop_obj_list = null;//���ڲ����̵���Ϣ������洢�˲�ѯ���Ķ����г��ֵĵ�������
    private List<Ord> ord_content = null;//������Ϣ���洢��ѯ���Ķ�������
    private Map<String,Shop> map = null;//�ּ�ֵӳ�䣬�洢��ord_content��ѯ�����̵���Ϣ

    private ViewPager pager = null;
    private LayoutInflater inflate = null;
    //���������Լ�����ɶ�����Ӧ��listview
    private ListView listview1,listview2;
    private Button main_waiting_button,main_finished_button;

    //������adapter:waiting_adapter�������adapter:finished_adapter;
    private OrderInfoListAdapter finished_adapter,waiting_adapter;
    private MyViewPagerAdapter adapter = null;

    private MyButtonListener button_li = null;

    //��UserӦ����һ����BaseActivity����Application���е�һ��User.
    private User user = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_layout);

        Bmob.initialize(getApplicationContext(), BMOB_API_KEY);
        pager = (ViewPager)findViewById(R.id.cus_order_pager);
        initView();
//        loadOrdInfo();
    }

    //���ݵ�ǰ�û�����ѯ������Ϣ
    //state������״̬
    private void loadOrdInfo(int state){
        BmobQuery<Ord> query1 = new BmobQuery<Ord>();
        //�������ˣ���Ӧ����objectId,Ӧ����user.name�ġ���ΪOrd����ֻ�洢��username�ֶΣ�����User��
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

    //��ѯ�̵���Ϣ�����ݶ���������Ʒ�����̼�
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

    //��ʼ�����
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

    //��ʼ������ɶ���Adapter
    private void initFinishedOrdAdapter()
    {
        creating_adapter = 2;
        loadOrdInfo(1);
    }

    //��ʼ����������Adapter
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
                    //TODO �ı�ͼƬ����
                    break;
                case 1:
                    Log.e(MY_TAG,"selected finished order list");
                    initFinishedOrdAdapter();
                    //TODO �ı�ͼƬ����
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
                //TODO �ı䰴ťͼƬ����
            }
            else if(v.getId() == R.id.waiting)
            {
                initWaitingOrdAdapter();
                //TODO �ı䰴ťͼƬ����
            }
        }
    }

}
