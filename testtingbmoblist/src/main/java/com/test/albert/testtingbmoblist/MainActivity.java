package com.test.albert.testtingbmoblist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends Activity {

    private Button button,pic_button;
    private EditText shop_name_input = null,order_state_input,comm_state_input;

    public static String BMOB_API_KEY = "5eb7b9f34bd4f213f1b9da322b9e5f37";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getApplicationContext(), BMOB_API_KEY);
        setContentView(R.layout.activity_main);
        shop_name_input = (EditText)findViewById(R.id.shop_name);
        order_state_input = (EditText)findViewById(R.id.order_state_input);
        comm_state_input = (EditText)findViewById(R.id.comm_state);
        button = (Button)findViewById(R.id.button1);
        pic_button = (Button)findViewById(R.id.upload_pic);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int comm_state_number = Integer.parseInt(comm_state_input.getText().toString());
                insertInfo(comm_state_number,order_state_input.getText().toString(), shop_name_input.getText().toString());
            }
        });
        pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void insertInfo(int comm_state,String order_state,String shop_name)
    {
        Shop shop = Creating.createShop(shop_name_input.getText().toString());
        Ord order = Creating.creatingOrder(comm_state,order_state,shop_name_input.getText().toString());
        shop.save(this, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("MainActivity:", "wrong shop insert" + s);
            }
        });
        order.save(this, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("MainActivity:", "wrong order insert" + s);
            }
        });
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
}
