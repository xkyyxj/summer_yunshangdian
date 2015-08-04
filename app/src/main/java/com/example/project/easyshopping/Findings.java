package com.example.project.easyshopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.albert.myapplication.R;

import bmobObject.Shop;
import bmobObject.ShopNews;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by acer on 2015/7/6.
 */
public class Findings extends Fragment{

    private String input_text = null;

    private TextView input_area = null;
    private Button send_button = null;

    private MyButtonListener li = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
        TextView title = (TextView)view.findViewById(R.id.activity_name);
        title.setText(R.string.send_message_fragment_title);
        li = new MyButtonListener();
        input_area = (TextView)view.findViewById(R.id.send_info_area);
        send_button = (Button)view.findViewById(R.id.send_button);
        send_button.setOnClickListener(li);
        return view;
    }

    private void sendMessage(String message)
    {
        Shop shop = null;
        shop = BmobUser.getCurrentUser(getActivity(), Shop.class);
        ShopNews news = new ShopNews();
        if(shop == null)
            Log.e("RUN:","shop is null");
        else
            Log.e("RUN:","" + shop.getShopname());
        news.setShop(shop);
        news.setText(message);
        news.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                toast(R.string.send_successful);
            }

            @Override
            public void onFailure(int i, String s) {
                toast(R.string.send_failed);
            }
        });
    }

    private void toast(int id)
    {
        String str = getActivity().getResources().getString(id);
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }

    class MyButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            input_text = input_area.getText().toString();
            sendMessage(input_text);
        }
    }
}
