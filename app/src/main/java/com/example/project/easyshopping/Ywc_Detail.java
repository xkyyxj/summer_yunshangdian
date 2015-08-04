package com.example.project.easyshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

/**
 * Created by acer on 2015/7/22.
 */
public class Ywc_Detail extends Activity {
    private LinearLayout ll1;
    private ImageButton back;
    private TextView textView0;
    private TextView ordid;
    private ListView listView;
    private TextView dispriceLabel;
    private TextView disprice;
    private TextView totalpriceLabel;
    private TextView totalprice;
    private View line1;
    private ImageView imageView5;
    private TextView orderdetailLabel;
    private View line2;
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
    private View line3;
    private ImageView imageView6;
    private TextView commentLabel;
    private View line4;
    private ImageView comPic;
    private TextView comment;

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
        line1 = findViewById(R.id.line1);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        orderdetailLabel = (TextView) findViewById(R.id.orderdetail_label);
        line2 = findViewById(R.id.line2);
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
        line3 = findViewById(R.id.line3);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        commentLabel = (TextView) findViewById(R.id.comment_label);
        line4 = findViewById(R.id.line4);
        comPic = (ImageView) findViewById(R.id.com_pic);
        comment = (TextView) findViewById(R.id.comment);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ywc_detail);

        assignViews();
        Intent intent = getIntent();
        mOrd = (Ord) intent.getSerializableExtra("mord");

        initTextView();

    }

    private void initTextView() {

        /**
         * 购物单的listview
         */

        ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
        for (int i = 0; i < mOrd.getPrice().size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("goodsname", mOrd.getGoodsname().get(i));     //商品名称
            map.put("price", "¥ " + mOrd.getPrice().get(i));         //商品单价
            map.put("num", mOrd.getNum().get(i));                 //商品数量

            listItem.add(map);
        }

        listView = (ListView) findViewById(R.id.listView);
        //  listView.setAdapter(new ArrayAdapter<String>(this, R.layout.inf_item, strs));

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem, R.layout.ordlist_item, new String[]{"goodsname", "price", "num"}, new int[]{R.id.goodsname, R.id.price, R.id.num});

        listView.setAdapter(mSimpleAdapter);


        ordid.setText("订单号：" + mOrd.getObjectId());
        disprice.setText("¥ " + MyStore.mShop.getDistributeprice());
        totalprice.setText(mOrd.getTotalprice());
        receiver.setText(mOrd.getReceiver());
        tel.setText(mOrd.getUsertel());
        baddr.setText(mOrd.getUseradd());
        payway.setText(mOrd.getPayway());
        ordtime.setText(mOrd.getOrdtime());
        switch (mOrd.getJystate()) {
            case MyOrders.YWC_2:
                comment.setText(mOrd.getCom());
                if (mOrd.getComm_state() == 1)
                    comPic.setImageDrawable(getResources().getDrawable(R.drawable.nice));
                else if (mOrd.getComm_state() == 2)
                    comPic.setImageDrawable(getResources().getDrawable(R.drawable.blacklow));
                break;
            case MyOrders.YWC_1:
                comment.setText("买家尚未评价");
                break;
            case MyOrders.YWC_3:
                comment.setText("买家已取消订单");
                break;

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
