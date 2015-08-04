package com.example.project.easyshopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.example.project.easyshopping.EditGoods;
import com.example.project.easyshopping.Seller;
import com.example.project.easyshopping.data.ShopG;
import com.test.albert.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2015/7/14.
 */
public class GoodsListAdapter extends BaseAdapter {
    public static final String TAG = "GoodListAdapter:";

    private Context context;                        //运行上下文
    private List<Map<String, Object>> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器

    private ShopG shopG;
    private MyViewOnClickListener li = null;

    private ArrayList<String> ids;
    private List<ShopG> list;

    public static class ViewHolder {                //自定义控件集合
        public LinearLayout ll1;
        public ImageView pic;
        public TextView goodsname;
        public TextView price;
        public TextView sales;
        public TextView id;

        public Button edit;
        public Button state;
        public Button delete;


    }

    /* class TagInfo
     {
         int position;
         String objectId;
     }*/
    public GoodsListAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;
        li = new MyViewOnClickListener();
    }

    @Override
    public int getCount() {
        if (listItems != null) {
            return listItems.size();
        } else
            return 0;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        final int selectID = position;
        TagInfo info = null;
        //自定义视图
        ViewHolder listItemView = null;
        if (convertView == null) {
            Log.e("GoodsListAdapter里的", "getView里创建了新listItemView");
            listItemView = new ViewHolder();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.inf_goods, null, true);
            //获取控件对象
            listItemView.ll1 = (LinearLayout) convertView.findViewById(R.id.ll1);
            listItemView.pic = (ImageView) convertView.findViewById(R.id.pic);
            listItemView.id = (TextView) convertView.findViewById(R.id.tv_id);
            listItemView.goodsname = (TextView) convertView.findViewById(R.id.tv_name);
            listItemView.price = (TextView) convertView.findViewById(R.id.tv_price);
            listItemView.sales = (TextView) convertView.findViewById(R.id.tv_sales);
            listItemView.edit = (Button) convertView.findViewById(R.id.btn_edit);
            listItemView.state = (Button) convertView.findViewById(R.id.btn_state);
            listItemView.delete = (Button) convertView.findViewById(R.id.btn_delete);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ViewHolder) convertView.getTag();
        }
//      Log.e("image", (String) listItems.get(position).get("title"));  //测试
//      Log.e("image", (String) listItems.get(position).get("info"));

        //设置文字和图片 和 id
        //  ids = new ArrayList<String>();

        // ids.add((String) listItems.get(position).get("objid"));
        //  listItemView.pic=(String)listItems.get(position).get("picture");
        listItemView.id.setText((String) listItems.get(position).get("objid"));
        listItemView.goodsname.setText((String) listItems.get(position).get("goodsname"));
        listItemView.price.setText(listItems.get(position).get("price") + "元");
        listItemView.sales.setText("销量(" + listItems.get(position).get("volume").toString() + ")");
        Boolean state=(Boolean)listItems.get(position).get("state");
        if(state==Boolean.TRUE) {
            listItemView.state.setText("下架");
            listItemView.state.setBackgroundColor(Color.LTGRAY);
        }
        else {
            listItemView.state.setText("上架");
            listItemView.state.setBackgroundColor(Color.WHITE);
        }

//加载图片
        loadShopPicImpl(listItemView.pic, (String) listItems.get(position).get("picture"));

        // Log.e("GLadapter ", "ids列表里的数据为" + ids.get(position));

        info = new TagInfo();
        info.position = position;
        info.objectId = (String) listItems.get(position).get("objid");

//设置ShopG类对象的objectId，用于显示商品列表的时候作为参数传入之，用于区别三个按钮相应
        listItemView.edit.setTag(info.objectId);
        listItemView.state.setTag(info.objectId);
        listItemView.delete.setTag(info);


        /**下面可以写List里每一项控件的监听器
         */





      /*  //final View view=View.inflate(context,R.layout.inf_goods,null);
        listItemView.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Button) v.findViewById(R.id.btn_state)).getText().equals("上架")) {
                    ((Button) v.findViewById(R.id.btn_state)).setText("下架");
                    shopG.setGoodssta(Boolean.TRUE);
                } else {
                    ((Button) v.findViewById(R.id.btn_state)).setText("上架");
                    shopG.setGoodssta(Boolean.FALSE);
                }
                (v.findViewById(R.id.btn_state)).setBackgroundColor(Color.LTGRAY);


                String id=((TextView)(parent.findViewById(R.id.tv_id))).getText().toString();
                shopG.update(context, id, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        toast("已更新!");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("更新失败");
                    }
                });
            }
        });


        listItemView.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String id = ids.get(position);
                String id=((TextView)(parent.findViewById(R.id.tv_id))).getText().toString();
                Intent intent = new Intent(v.getContext(), EditGoods.class);
                intent.putExtra("objid", id);
                v.getContext().startActivity(intent);
            }
        });

        private void setEditOnClick(final ViewHolder lv) {
        lv.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String id=((TextView)(lv.findViewById(R.id.tv_id))).getText().toString();
                Intent intent = new Intent(v.getContext(), EditGoods.class);
                //intent.putExtra("objid", id);
                v.getContext().startActivity(intent);
            }
        });

    }
        */

        // info = new TagInfo();
        // info.position = position;
        // info.objectId =(String)listItems.get(position).get("objid");
        //设置Ord类对象的objectId，用于启动订单详情界面的时候作为参数传入之，用于加载订单详情
        //holder.goodsinfo.setTag(list.get(position).getObjectId());
        //同上，用于加载评论或者确认订单的时候使用
        //holder.confirmgoods.setTag(info);
        // listItemView.rl1.setTag(info);

        listItemView.edit.setOnClickListener(li);
        listItemView.state.setOnClickListener(li);
        listItemView.delete.setOnClickListener(li);


        return convertView;


    }

    //加载店家图片
    private void loadShopPicImpl(ImageView shop_pic, String pic_name) {
        Log.d(TAG, "in function loadShopPicImpl:loading");
        final ImageView view = shop_pic;
        Bitmap shop_bg = null;
        File file = new File(pic_name);
        if (file.exists()) {
            Log.i(TAG, "loading pic local?");
            shop_bg = BitmapFactory.decodeFile(pic_name);
            shop_pic.setImageBitmap(shop_bg);
        } else {
            BmobProFile pro_file = BmobProFile.getInstance(context);
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


    class MyViewOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            shopG = new ShopG();
            int id = v.getId();
            Intent intent = null;
            String ids;
            switch (id) {
                case R.id.btn_state:
                    if (((Button) v).getText().equals("上架")) {
                        ((Button) v).setText("下架");
                        ((Button) v).setClickable(false);
                        (v).setBackgroundColor(Color.LTGRAY);
                        shopG.setGoodssta(Boolean.TRUE);
                    } else {
                        // ((Button) v.findViewById(R.id.btn_state)).setText("上架");
                        ((Button) v).setText("上架");
                        ((Button) v).setClickable(false);
                        (v).setBackgroundColor(Color.WHITE);
                        shopG.setGoodssta(Boolean.FALSE);
                    }

                    ids = (String) v.getTag();
                    shopG.update(context, ids, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            toast("上/下架已更新!");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("更新失败");
                        }
                    });
                    break;

                case R.id.btn_edit:
                    //
                    ids = (String) v.getTag();
                    intent = new Intent(v.getContext(), EditGoods.class);
                    intent.putExtra("objid", ids);
                    intent.putExtra("shopname", Seller.ShopName);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.btn_delete:
                    deleteGoods((TagInfo) v.getTag());
                    break;
            }

        }
    }

    class TagInfo {
        int position;
        String objectId;
    }

    //删除此商品，并刷新页面
    private void deleteGoods(TagInfo info) {
        final int position = info.position;
        ShopG sg = new ShopG();

        sg.setObjectId(info.objectId);
        sg.delete(context, new DeleteListener() {
            @Override
            public void onSuccess() {
                toast("此商品已删除！");
                notifyChangeData(position);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void notifyChangeData(int position) {
       listItems.remove(position);
        notifyDataSetChanged();

    }

    private void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }
}
