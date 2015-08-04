package com.example.project.easyshopping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.example.project.easyshopping.data.Shop;
import com.test.albert.myapplication.R;
import com.test.main.CommentActivity;
import com.test.main.LoginActivity;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by acer on 2015/7/10.
 */
public class StoreInf extends Activity {

    private Shop mshop;
    private static final String TAG = "StoreInf";

    private LinearLayout ll1;
    private ImageButton back;
    private TextView textView0;
    private LinearLayout ll2;
    private ImageView head;
    private LinearLayout ll3;
    private ImageView up;
    private TextView textView1;
    private ImageView down;
    private TextView textView2;
    private TextView textView3;
    private LinearLayout ll4;
    private TextView textView4;
    private Button button2;
    private View line1;
    private TextView nameLabel;
    private TextView name;
    private View line2;
    private TextView addrLabel;
    private TextView addr;
    private View line3;
    private TextView teleLabel;
    private TextView tele;
    private TextView timeLabel;
    private TextView time;
    private TextView accMoLabel;
    private TextView accMo;
    private TextView sendMoLabel;
    private TextView sendMo;
    private TextView stateLabel;
    private TextView state;

    private Button button3;

    private void assignViews() {
        MyButtonListener li = new MyButtonListener();
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        back = (ImageButton) findViewById(R.id.back);
        textView0 = (TextView) findViewById(R.id.textView0);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        head = (ImageView) findViewById(R.id.head);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        up = (ImageView) findViewById(R.id.up);
        textView1 = (TextView) findViewById(R.id.textView1);
        down = (ImageView) findViewById(R.id.down);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        textView4 = (TextView) findViewById(R.id.textView4);
        button2 = (Button) findViewById(R.id.button2);
        line1 = findViewById(R.id.line1);
        nameLabel = (TextView) findViewById(R.id.name_label);
        name = (TextView) findViewById(R.id.name);
        line2 = findViewById(R.id.line2);
        addrLabel = (TextView) findViewById(R.id.addr_label);
        addr = (TextView) findViewById(R.id.addr);
        line3 = findViewById(R.id.line3);
        teleLabel = (TextView) findViewById(R.id.tele_label);
        tele = (TextView) findViewById(R.id.tele);
        timeLabel = (TextView) findViewById(R.id.time_label);
        time = (TextView) findViewById(R.id.time);
        accMoLabel = (TextView) findViewById(R.id.acc_mo_label);
        accMo = (TextView) findViewById(R.id.acc_mo);
        sendMoLabel = (TextView) findViewById(R.id.send_mo_label);
        sendMo = (TextView) findViewById(R.id.send_mo);
        stateLabel = (TextView) findViewById(R.id.state_label);
        state = (TextView) findViewById(R.id.state);
        button3 = (Button) findViewById(R.id.button3);
        button2.setOnClickListener(li);
        button3.setOnClickListener(li);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeinf);
        assignViews();

        mshop = MyStore.mShop;

        initImageView();
        initTextView();
        initButton();
    }

    private void initImageView() {
        loadShopPicImpl(head, mshop.getPicture());

    }

    private void initTextView() {
        name.setText(mshop.getShopname());
        addr.setText(mshop.getShopadd());
        tele.setText(mshop.getShoptel());
        time.setText(mshop.getStarttime() + "-" + mshop.getEndtime());
        accMo.setText("¥ " + mshop.getSendprice());
        sendMo.setText("¥ " + mshop.getDistributeprice());
        state.setText(mshop.getOperatesta() == Boolean.TRUE ? "正在营业" : "暂停营业");


    }


    private void initButton() {
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "您点击了退出登录", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //加载店家图片
    private void loadShopPicImpl(ImageView shop_pic, String pic_name) {
        Log.d(TAG, "in function loadShopPicImpl:loading");
        final ImageView view = shop_pic;
        Bitmap shop_bg = null;
        File file = null;
        if(pic_name != null)
            file = new File(pic_name);
        if (file != null && file.exists()) {
            Log.i(TAG, "loading pic local?");
            shop_bg = BitmapFactory.decodeFile(pic_name);
            shop_pic.setImageBitmap(shop_bg);
        } else {
            BmobProFile pro_file = BmobProFile.getInstance(this);
            pro_file.download(pic_name, new DownloadListener() {
                @Override
                public void onSuccess(String s) {
                    setImageBitmap(view, s);
                }

                @Override
                public void onProgress(String s, int i) {

                }

                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "loading pic wrong:" + s);
                }
            });
        }
    }

    private void setImageBitmap(ImageView view, String file_path) {
        Log.i(TAG, "loading success: " + file_path);
        File file = new File(file_path);
        Bitmap bit = null;
        if (view.isShown()) {
            view.setImageResource(0);
            bit = BitmapFactory.decodeFile(file_path);
            if (file.exists())
                Log.e(TAG, "file exists!");
            if (bit == null)
                Log.e(TAG, "WTF NullPointer?");
            else
                Log.i(TAG, "" + bit.getWidth());
            view.setImageBitmap(bit);
            view.invalidate();
            Log.i(TAG, "set to imageView");
        }
        //file.delete();
    }

    private void goLoginActivity()
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private class MyButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            Shop user = BmobUser.getCurrentUser(getApplicationContext(),Shop.class);
            switch(id)
            {
                case R.id.button2:
                    Intent intent = new Intent(StoreInf.this,CommentActivity.class);
                    intent.putExtra("objectId", user.getObjectId());
                    startActivity(intent);
                    break;
                case R.id.button3:
                    BmobUser.logOut(getApplicationContext());
                    goLoginActivity();
            }
        }

    }
}
