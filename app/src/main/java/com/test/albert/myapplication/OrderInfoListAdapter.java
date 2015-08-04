package com.test.albert.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.example.project.easyshopping.data.Ord;

import java.io.File;
import java.util.List;
import java.util.Map;

import bmobObject.Shop;
import cn.bmob.v3.listener.UpdateListener;

public class OrderInfoListAdapter extends BaseAdapter {

    public static final String TAG = "RUN:";

    public static final int FINISHED_ORDER = 1;
    public static final int WAITING_ORDER = 2;

    private List<Ord> list;//订单信息存储列表
    private Context context = null;
    private LayoutInflater inflate;
    private Map<String,Shop> map = null;//与订单相关的商家信息存储
    private int type = 0;//判定是那种订单类型

    //处理点击跳转到订单详情界面以及收货或者评价按钮的监听
    private MyViewOnClickListener li = null;

    /*
    * 所需的三个参数：
    * 1.上下文
    * 2.OrderInfoListAdapter类的两个参数之一，确定adapter类型
    * 3.Map类型，用于保存与订单相关的商铺信息
    * */
    public OrderInfoListAdapter(Context _context,int _choice,List<Ord> ord_list,Map<String,Shop> _map)
    {
        context = _context;
        inflate = LayoutInflater.from(_context);
        list = ord_list;
        type = _choice;
        map = _map;
        li = new MyViewOnClickListener();
    }

    @Override
    public int getCount() {
        if(list != null) {
            Log.e(MainActivity.MY_TAG,"" + list.size());
            return list.size();
        }
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        TagInfo info = null;
        if(convertView == null)
        {
            convertView = inflate.inflate(R.layout.order_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();
        holder.time.setText(list.get(position).getOrdtime());
        holder.orderid.setText("" + list.get(position).getOrdid());
        holder.shopname.setText(list.get(position).getShopname());
        holder.goodsprice.setText("" + list.get(position).getTotalprice());
        holder.goodsnumber.setText("" + list.get(position).getGoodsname().size());
        //加载商店图片
        Log.d(TAG, "running in getView() function,will load picture of shop");
        loadShopPicImpl(holder.shoppic, map.get(list.get(position).getShopname()).getSpicture());
        holder.shoptele.setText(map.get(list.get(position).getShopname()).getShoptel());
        info = new TagInfo();
        info.position = position;
        info.order_object_id = list.get(position).getObjectId();
        info.shop_object_id = map.get(list.get(position).getShopname()).getObjectId();
        //设置Ord类对象的objectId，用于启动订单详情界面的时候作为参数传入之，用于加载订单详情
        holder.goodsinfo.setTag(list.get(position).getObjectId());
        Log.i("RUN:", "in getView function " + list.get(position).getObjectId());
        //同上，用于加载评论或者确认订单的时候使用
        holder.confirmgoods.setTag(info);
        holder.goodsinfo.setOnClickListener(li);
        holder.confirmgoods.setOnClickListener(li);
        holder.shoptele.setOnClickListener(li);
        String order_state = list.get(position).getJystate();
        if(type == FINISHED_ORDER)
        {
            //根据订单状态判定按钮的背景以及是否可以被点击
            switch(order_state)
            {
                case "21":
                    holder.confirmgoods.setImageResource(R.drawable.waiting_comm);
                    break;
                case "22":
                    holder.confirmgoods.setClickable(false);
                    holder.confirmgoods.setImageResource(R.drawable.had_comm);
                    break;
                case "23":
                    holder.confirmgoods.setClickable(false);
                    holder.confirmgoods.setImageResource(R.drawable.cancelled_order);
                    break;
            }
        }
        else if(type == WAITING_ORDER)
            holder.confirmgoods.setImageResource(R.drawable.confirm_goods);
        return convertView;
    }

    public void setAdapterContent(List<Ord> _list,Map<String,Shop> _map)
    {
        list = _list;
        map = _map;
        notifyDataSetChanged();
    }

    //加载店家图片
    private void loadShopPicImpl(ImageView shop_pic,String pic_name)
    {
        Log.d(TAG,"in function loadShopPicImpl:loading");
        final ImageView view = shop_pic;
        Bitmap shop_bg = null;
        File file = null;
        if(pic_name != null)
            file = new File(pic_name);
        if(file != null && file.exists())
        {
            Log.i(TAG,"loading pic local?");
            shop_bg = BitmapFactory.decodeFile(pic_name);
            shop_pic.setImageBitmap(shop_bg);
        }
        else
        {
            BmobProFile pro_file = BmobProFile.getInstance(context);
            pro_file.download(pic_name, new DownloadListener() {
                @Override
                public void onSuccess(String s) {
                    setImageBitmap(view,s);
                }

                @Override
                public void onProgress(String s, int i) {

                }

                @Override
                public void onError(int i, String s) {
                    Log.e(TAG,"loading pic wrong:" + s);
                }
            });
        }
    }

    private void setImageBitmap(ImageView view,String file_path) {
        Log.i(TAG,"loading success: " + file_path);
        File file = new File(file_path);
        Bitmap bit = null;
        if(view.isShown()) {
            view.setImageResource(0);
            if(file.exists())
                Log.e(TAG,"file exists!");
            bit = BitmapFactory.decodeFile(file_path);
            view.setImageBitmap(bit);
            view.invalidate();
            Log.i(TAG,"set to imageView");
        }
        Log.e(TAG,"file delete!");
        //file.delete();
    }

    //在待处理订单列表中点击确认收货后，用于更新订单状态，改为已完成
    private void finishOrder(TagInfo info)
    {
        final int position = info.position;
        Ord order = new Ord();
        //下面一行中传入的参数应当是预定义的订单状态中的一种：已完成，用户未评价。此值待定，但不应是1
        order.setJystate("21");
        order.update(context, info.order_object_id, new UpdateListener() {
            @Override
            public void onSuccess() {
                notifyChangeData(position);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }


    private void notifyChangeData(int position)
    {
        Ord ord = list.remove(position);
        notifyDataSetChanged();
        ord.setJystate("21");
    }

    public class ViewHolder {
        public final TextView time;
        public final TextView orderid;
        public final ImageView shoppic;
        public final TextView shopname;
        public final TextView goodsprice;
        public final TextView goodsnumber;
        public final RelativeLayout goodsinfo;
        public final TextView shoptele;
        public final ImageButton confirmgoods;
        public final View root;

        public ViewHolder(View root) {
            time = (TextView) root.findViewById(R.id.time);
            orderid = (TextView) root.findViewById(R.id.order_id);
            shoppic = (ImageView) root.findViewById(R.id.shop_pic);
            shopname = (TextView) root.findViewById(R.id.shop_name);
            goodsprice = (TextView) root.findViewById(R.id.goods_price);
            goodsnumber = (TextView) root.findViewById(R.id.goods_number);
            goodsinfo = (RelativeLayout) root.findViewById(R.id.goods_info);
            shoptele = (TextView) root.findViewById(R.id.shop_tele);
            confirmgoods = (ImageButton) root.findViewById(R.id.confirm_goods);
            this.root = root;
        }
    }

    class TagInfo
    {
        int position;
        String shop_object_id;
        String order_object_id;
    }

    class MyViewOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent = null;
            switch(id)
            {
                case R.id.goods_info:
                    if(type == FINISHED_ORDER)
                    {
                        intent = new Intent(context,FinishedOrderItemActivity.class);
                        intent.putExtra("objectId",(String)v.getTag());
                        context.startActivity(intent);
                    }
                    else if(type == WAITING_ORDER)
                    {
                        intent = new Intent(context,WaitingOrderItemActivity.class);
                        intent.putExtra("objectId",(String)v.getTag());
                        context.startActivity(intent);
                    }
                    break;
                case R.id.shop_tele:
                    TextView view = (TextView)v;
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + view.getText()));
                    context.startActivity(intent);
                    break;
                case R.id.confirm_goods:
                    if(type == FINISHED_ORDER)
                    {
                        intent = new Intent(context,AddCommentActivity.class);
                        intent.putExtra("objectId",((TagInfo)v.getTag()).shop_object_id);
                        intent.putExtra("ordObjectId",((TagInfo) v.getTag()).order_object_id);
                        context.startActivity(intent);
                    }
                    else if(type == WAITING_ORDER)
                    {
                        finishOrder((TagInfo)v.getTag());
                    }
                    break;
            }

        }
    }

    public interface FinishOrderListener
    {
        void newOrderFinished(Ord finished_ord);
    }
}