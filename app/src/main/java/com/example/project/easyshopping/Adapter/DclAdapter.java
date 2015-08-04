package com.example.project.easyshopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project.easyshopping.DclDetail;
import com.example.project.easyshopping.MyOrders;
import com.example.project.easyshopping.data.Ord;
import com.test.albert.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2015/7/16.
 */
public class DclAdapter extends BaseAdapter {
    private Context context;                        //运行上下文
    private List<Map<String, Object>> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器

    public final class ListItemView {                //自定义控件集合

        public TextView ordid;
        public TextView bname;
        public TextView btel;
        public TextView baddr;
        public TextView total;
        public TextView g_numb;
        public TextView tnd;


        public ImageButton detail;
        public Button send;
        public String jystate;

        Ord mOrd = new Ord();
    }

    public DclAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;

    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * ListView Item设置
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final int selectID = position;
        //自定义视图
        ListItemView listItemView = null;
        if (convertView == null) {
            Log.e("DclAdapter里的", "getView里创建了新listItemView");
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.dcl_item, null, true);
            //获取控件对象
            listItemView.ordid = (TextView) convertView.findViewById(R.id.ordid);
            listItemView.bname = (TextView) convertView.findViewById(R.id.bname);
            listItemView.btel = (TextView) convertView.findViewById(R.id.btel);
            listItemView.baddr = (TextView) convertView.findViewById(R.id.baddr);
            listItemView.total = (TextView) convertView.findViewById(R.id.total);
            listItemView.g_numb = (TextView) convertView.findViewById(R.id.g_numb);
            listItemView.tnd = (TextView) convertView.findViewById(R.id.tnd);
            listItemView.detail = (ImageButton) convertView.findViewById(R.id.ibt_detail);
            listItemView.send = (Button) convertView.findViewById(R.id.send);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
//      Log.e("image", (String) listItems.get(position).get("title"));  //测试
//      Log.e("image", (String) listItems.get(position).get("info"));

        //设置文字和图片
        listItemView.ordid.setText((String) listItems.get(position).get("ordid"));
        listItemView.bname.setText((String) listItems.get(position).get("bname"));
        listItemView.btel.setText((String) listItems.get(position).get("btel"));
        listItemView.baddr.setText((String) listItems.get(position).get("baddr"));
        listItemView.total.setText("¥"+(String) listItems.get(position).get("total"));
        listItemView.tnd.setText(listItems.get(position).get("time") + "/ 600米");

        ArrayList<String> goodsname = (ArrayList<String>) listItems.get(position).get("goodsname");
        ArrayList<String> num = (ArrayList<String>) listItems.get(position).get("num");
        ArrayList<String> price = (ArrayList<String>) listItems.get(position).get("price");
        Integer totalnum = 0;
        for (int j = 0; j < num.size(); j++) {
            totalnum += Integer.parseInt(num.get(j));
        }
        listItemView.g_numb.setText("(共" + totalnum + "件商品)"); //计算商品总数

        listItemView.jystate = (String) listItems.get(position).get("jystate");
        if (listItemView.jystate.equals(MyOrders.DCL_1))
            listItemView.send.setText("未配送");
        else {
            listItemView.send.setText("配送中~");
            listItemView.send.setClickable(false);
        }


        //将数据都存到ViewHolder里的Ord对象上
        listItemView.mOrd.setJystate(listItemView.jystate);
        listItemView.mOrd.setObjectId((String) listItems.get(position).get("ordid"));
        listItemView.mOrd.setReceiver((String) listItems.get(position).get("bname"));
        listItemView.mOrd.setUsertel((String) listItems.get(position).get("btel"));
        listItemView.mOrd.setUseradd((String) listItems.get(position).get("baddr"));
        listItemView.mOrd.setTotalprice("¥"+(String) listItems.get(position).get("total"));
        listItemView.mOrd.setNum((ArrayList<String>) listItems.get(position).get("num"));
        listItemView.mOrd.setPrice((ArrayList<String>) listItems.get(position).get("price"));
        listItemView.mOrd.setGoodsname((ArrayList<String>) listItems.get(position).get("goodsname"));
        listItemView.mOrd.setPayway((String) listItems.get(position).get("payway"));
        listItemView.mOrd.setOrdtime((String) listItems.get(position).get("time"));


        listItemView.detail.setTag(listItemView.mOrd);

        /**下面可以写List里每一项控件的监听器
         */


        listItemView.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DclDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mord", (Ord)v.getTag());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                // Toast.makeText(v.getContext(),"你点击订单号为"+v.getTag(),Toast.LENGTH_SHORT).show();

            }
        });


        return convertView;

    }


}

