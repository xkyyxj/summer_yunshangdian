package com.example.project.easyshopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project.easyshopping.Adapter.YwcAdapter;
import com.example.project.easyshopping.data.Ord;
import com.test.albert.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by acer on 2015/7/8.
 */
public class Yiwancheng extends Fragment {
    private View mMainView;
    private static ArrayList<Ord> sgl = new ArrayList<Ord>();

    private ListView listView4;
    private YwcAdapter mYwcAdapter;
    private List<Map<String, Object>> listItems;
    private String ShopName;
    private String[] ordid;
    private String[] bname;
    private String[] btel;
    private String[] baddr;
    private String[] total;
    private String[] g_numb;
    private String[] tnd;
    private String[] jystate;
    private String[] payway;
    private String[] com;
    private Integer[] com_state;


    private String[] ordtime;
    private String[] distance;

    private ArrayList<String>[] numlist; //数量列表之列表
    //private ArrayList<Integer> num;             //数量列表
    private ArrayList<String>[] goodsnamelist;       //同一单商品列表
    private ArrayList<String>[] pricelist;           //同一单单价列表

    List<Map<String, Object>> YwcList = new ArrayList<Map<String, Object>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.yiwancheng, container, false);
        Log.e("YWC的onCreateView", "成功了！");
        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
            Log.e("xzy", "fragment新订单-->移出已存在的View");

        }

        ShopName = Seller.ShopName;
        Log.e("Yiwancheng的onStart","ShopNme是"+ShopName);
        download(ShopName);

        return mMainView;
    }

    private void download(String s) {

        Log.e("已完成订单类里的download方法","店铺名是"+s);

        BmobQuery<Ord> query = new BmobQuery<Ord>();
        String[] code={"21","22","23"};
        query.addWhereEqualTo("shopname", s);
        query.addWhereContainedIn("jystate", Arrays.asList(code));
        query.findObjects(getActivity(), new FindListener<Ord>() {
            @Override
            public void onSuccess(List<Ord> list) {
                Toast.makeText(getActivity(), "成功查询" + list.size() + "条已完成订单。", Toast.LENGTH_SHORT).show();
                sgl = (ArrayList<Ord>) list;
                listView4 = (ListView) mMainView.findViewById(R.id.listView4);
                YwcList = getListItems(sgl);
                mYwcAdapter = new YwcAdapter(getActivity(), YwcList); //创建适配器
                mYwcAdapter.notifyDataSetChanged();
                listView4.setAdapter(mYwcAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        Log.e("OnActivitycreat里", "创建了ArrayList<ShopG>");

    }

    /**
     * 初始化商品信息
     */
    private List<Map<String, Object>> getListItems(ArrayList<Ord> sgl) {
        listItems = new ArrayList<Map<String, Object>>();
        int size = sgl.size();
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = new HashMap<String, Object>();

            ordid = new String[size];
            bname = new String[size];
            btel = new String[size];
            baddr = new String[size];
            total = new String[size];
            g_numb = new String[size];
            tnd = new String[size];
            ordtime = new String[size];
            jystate=new String[size];
            payway=new String[size];
            com=new String[size];
            com_state=new Integer[size];

            numlist = new ArrayList[size];
            pricelist = new ArrayList[size];
            goodsnamelist = new  ArrayList[size];

            ordid[i] = sgl.get(i).getObjectId();
            map.put("ordid", ordid[i]);      //收件人姓名
            Log.e("XDD ", "此处的订单号为" + ordid[i]);

            bname[i] = sgl.get(i).getReceiver();
            map.put("bname", bname[i]);      //收件人姓名
            Log.e("XDD ", "此处的收件人为" + bname[i]);
            btel[i] = sgl.get(i).getUsertel();
            map.put("btel", btel[i]);             //联系电话
            Log.e("XDD ", "此处的联系电话为" + btel[i]);
            baddr[i] = sgl.get(i).getUseradd();
            map.put("baddr", baddr[i]);     //收件地址
            Log.e("XDD ", "此处的收件地址为" + baddr[i]);


            numlist[i] = sgl.get(i).getNum();
            map.put("num", numlist[i]);             //商品个数list

            goodsnamelist[i] = sgl.get(i).getGoodsname();    //商品名称list
            map.put("goodsname", goodsnamelist[i]);

            pricelist[i] = sgl.get(i).getPrice();       //商品单价list
            map.put("price", pricelist[i]);

            total[i] = sgl.get(i).getTotalprice();
            map.put("total", total[i]);              //订单总价


            ordtime[i] = sgl.get(i).getCreatedAt();
            tnd[i] = ordtime[i];
            map.put("time", tnd[i] );                  //时间

            jystate[i] = sgl.get(i).getJystate();
            map.put("jystate", jystate[i]);                 //交易状态

            payway[i] = sgl.get(i).getPayway();
            map.put("payway", payway[i] );              //支付方式

            com[i] = sgl.get(i).getCom();
            map.put("com", com[i] );                    //支付方式


            com_state[i] = sgl.get(i).getComm_state()==null? 0:sgl.get(i).getComm_state() ;
            map.put("com_state", com_state[i] );                    //评价状态

            listItems.add(map);


        }
        return listItems;
    }


}