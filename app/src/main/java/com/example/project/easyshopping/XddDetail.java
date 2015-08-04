package com.example.project.easyshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.project.easyshopping.data.Ord;
import com.example.project.easyshopping.data.Shop;
import com.test.albert.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2015/7/21.
 */
public class XddDetail extends Activity{

    private LinearLayout ll1;
    private ImageButton back;
    private TextView textView0;
    private TextView ordid;
    private ListView listView;
    private TextView dispriceLabel;
    private TextView disprice;
    private TextView totalpriceLabel;
    private TextView totalprice;
    private ImageView imageView5;
    private TextView orderdetailLabel;
    private TextView receiverLabel;
    private TextView receiver;
    private TextView telLabel;
    private TextView tel;
    private TextView baddrLabel;
    private TextView baddr;
    private TextView paywayLabel;
    private TextView payway;
    private TextView ordtimeLabel;
    private TextView ordtime;
    private Button take;

    private String goodid;
    private Ord mOrd;
    private Shop mshop;
    private void assignViews() {
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        back = (ImageButton) findViewById(R.id.back);
        textView0 = (TextView) findViewById(R.id.textView0);
        ordid = (TextView) findViewById(R.id.ordid);
        listView = (ListView) findViewById(R.id.listView);
        dispriceLabel = (TextView) findViewById(R.id.disprice_label);
        disprice = (TextView) findViewById(R.id.disprice);
        totalpriceLabel = (TextView) findViewById(R.id.totalprice_label);
        totalprice = (TextView) findViewById(R.id.totalprice);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        orderdetailLabel = (TextView) findViewById(R.id.orderdetail_label);
        receiverLabel = (TextView) findViewById(R.id.receiver_label);
        receiver = (TextView) findViewById(R.id.receiver);
        telLabel = (TextView) findViewById(R.id.tel_label);
        tel = (TextView) findViewById(R.id.tel);
        baddrLabel = (TextView) findViewById(R.id.baddr_label);
        baddr = (TextView) findViewById(R.id.baddr);
        paywayLabel = (TextView) findViewById(R.id.payway_label);
        payway = (TextView) findViewById(R.id.payway);
        ordtimeLabel = (TextView) findViewById(R.id.ordtime_label);
        ordtime = (TextView) findViewById(R.id.ordtime);
        take = (Button) findViewById(R.id.take);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xdd_detail);

        assignViews();
        Intent intent = getIntent();
        mOrd = (Ord) intent.getSerializableExtra("mord");

        initTextView();
        //initSpinner();
        initButton();
    }

    private void initTextView(){

        /**
         * 购物单的listview
         */

       ArrayList<HashMap<String,String>> listItem=new  ArrayList<>();
        for(int i=0;i<mOrd.getPrice().size();i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("goodsname", mOrd.getGoodsname().get(i));     //商品名称
            map.put("price", "¥ "+mOrd.getPrice().get(i));             //商品单价
            map.put("num",  mOrd.getNum().get(i));                 //商品数量

            listItem.add(map);
        }

        listView = (ListView) findViewById(R.id.listView);
        //  listView.setAdapter(new ArrayAdapter<String>(this, R.layout.inf_item, strs));

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,R.layout.ordlist_item,new String[]{"goodsname","price","num"},new int[] {R.id.goodsname,R.id.price,R.id.num});

        listView.setAdapter(mSimpleAdapter);


        ordid.setText("订单号：" + mOrd.getObjectId());
        disprice.setText("¥ " + MyStore.mShop.getDistributeprice());
        totalprice.setText(mOrd.getTotalprice());
        receiver.setText(mOrd.getReceiver());
        tel.setText(mOrd.getUsertel());
        baddr.setText(mOrd.getUseradd());
        payway.setText(mOrd.getPayway());
        ordtime.setText(mOrd.getOrdtime());

    }

    private void initButton(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        take.setOnClickListener(new View.OnClickListener() {
            String id=mOrd.getObjectId();
            @Override
            public void onClick(View v) {
                Ord o=new Ord();
                o.setJystate(MyOrders.DCL_1);
                Log.e("Xdd的initButton()","id:" +id);
                o.update(v.getContext(), id, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        AlertDialog.Builder dialog_1 = new AlertDialog.Builder(XddDetail.this);
                        dialog_1.setTitle("提示");//设置标题
                        dialog_1.setMessage("接单成功！");//设置内容
                        dialog_1.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.cancel();
                                finish();
                            }
                        });
                        dialog_1.show();
                        Xindingdan.mXddAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        AlertDialog.Builder dialog_2 = new AlertDialog.Builder(XddDetail.this);
                        dialog_2.setTitle("提示");//设置标题
                        dialog_2.setMessage("接单失败，请重试！");//设置内容
                        dialog_2.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.cancel();
                            }
                        });
                        dialog_2.show();
                    }
                });


            }
        });

    }
}
