package com.example.project.easyshopping;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.example.project.easyshopping.Adapter.GoodsListAdapter;
import com.example.project.easyshopping.Util.Util;
import com.example.project.easyshopping.data.Shop;
import com.example.project.easyshopping.data.ShopG;
import com.test.albert.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by acer on 2015/7/6.
 */
public class MyStore extends Fragment {

    private View mMainView;
    private View searchbtn;
    private View setbtn;
    private View addbtn;
    private ImageView edit;
    private ListView listView;


    //店铺信息的View及其控件
    private ImageButton head;
    private TextView tv_shopname;
    private TextView tv_status;
    private TextView tv_num1;
    private TextView tv_num2;
    private TextView tv_sendprice;
    private TextView tv_distprice;
    //图像处理
    private String file_path = null;
    private Bitmap pic_bt = null;
    private FileOutputStream out = null;

    public static Shop mShop = null;
    private String current_cat;

    private String[] objid;
    private String[] goodsNames;
    private String[] price;
    private Integer[] volume;
    private Boolean[] state;
    private String[] picture;


    private String[] cat;
    private String ShopName;
    private Spinner spinner;
    private ArrayAdapter<String> mAdapter;
    private GoodsListAdapter mGLAdapter;
    private static ArrayList<ShopG> sgl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mMainView = inflater.inflate(R.layout.mystore, container, false);

        mShop = new Shop();
     //   mShop = Seller.mShop;
        ShopName = Seller.ShopName;
/**
 * 生成spinner控件
 */
        spinner = (Spinner) mMainView.findViewById(R.id.spinner);
        cat = new String[]{"全部商品", "下架商品", "日用品", "水果", "零食"};
        current_cat = "种类";
        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_item, cat);

        initTextView();


        if (!Util.isNetworkConnected(getActivity()))
            toast("亲, 木有网络 ( ⊙ o ⊙ ) ");

        return mMainView;

    }

    private void initTextView() {
        head = (ImageButton) mMainView.findViewById(R.id.head);
        tv_shopname = (TextView) mMainView.findViewById(R.id.store_name);
        tv_status = (TextView) mMainView.findViewById(R.id.status);
        tv_num1 = (TextView) mMainView.findViewById(R.id.Num1);
        tv_num2 = (TextView) mMainView.findViewById(R.id.Num2);
        tv_sendprice = (TextView) mMainView.findViewById(R.id.Num3);
        tv_distprice = (TextView) mMainView.findViewById(R.id.Num4);

        BmobQuery<Shop> query = new BmobQuery<Shop>();
        /**
         * 下一条需修改店铺名称
         */
        query.addWhereEqualTo("shopname", ShopName);
        query.findObjects(getActivity(), new FindListener<Shop>() {
            @Override
            public void onSuccess(List<Shop> list) {
              
                for (Shop s : list) {
                    mShop = s;

                }
                file_path = getActivity().getFilesDir().getPath() + mShop.getSusername() + ".png";
                tv_shopname.setText(mShop.getShopname());
                tv_status.setText(mShop.getShopadd());
                tv_num1.setText("" + mShop.getTotalgood());
                tv_num2.setText("" + mShop.getTotalbad());
                if (mShop.getSendprice() == null)
                    tv_sendprice.setText("¥ 0.0");
                else
                    tv_sendprice.setText("¥ " + mShop.getSendprice().toString());
                if (mShop.getDistributeprice() == null)
                    tv_distprice.setText("¥ 0.0");
                else
                    tv_distprice.setText("¥ " + mShop.getDistributeprice().toString());

                initSpinner();
                initButton();
                loadpic();
            }

            @Override
            public void onError(int i, String s) {
                toast("加载店铺信息失败！");
            }
        });

    }


    private void initSpinner() {

        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (current_cat.equals(spinner.getSelectedItem().toString())) {
                    Log.e("MyStore里的initSpinner", "current_cat为" + current_cat);
                } else {
                    current_cat = spinner.getSelectedItem().toString();
                    download(mShop.getShopname(), current_cat);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private List<Map<String, Object>> getListItems(List<ShopG> sgl) {
        ArrayList<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        int size = sgl.size();
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = new HashMap<String, Object>();

            objid = new String[size];
            goodsNames = new String[size];
            price = new String[size];
            volume = new Integer[size];
            state = new Boolean[size];
            picture = new String[size];

            objid[i] = sgl.get(i).getObjectId();
            map.put("objid", objid[i]);      //物品id
            Log.e("MyStored ", "此处的商品id为" + objid[i]);

            picture[i] = sgl.get(i).getPicture();
            map.put("picture", picture[i]);      //物品id
            Log.e("MyStored ", "此处的商品图片id为" + picture[i]);

            goodsNames[i] = sgl.get(i).getGoodsname();
            map.put("goodsname", goodsNames[i]);      //物品名称
            Log.e("MyStored ", "此处的商品名为" + goodsNames[i]);
            price[i] = sgl.get(i).getPrice();
            map.put("price", price[i]);              //物品单价

            volume[i] = sgl.get(i).getVolume();
            if (volume[i] == null)
                map.put("volume", 0);
            else
                map.put("volume", volume[i]);     //物品销量

            state[i] = sgl.get(i).getGoodssta();
            map.put("state", state[i]);

            listItems.add(map);
        }
        return listItems;
    }

    private void download(String shopname, String cat) {
        if (shopname == null) {
            toast("您尚未登录！");
        } else {
            sgl = new ArrayList<ShopG>();
            BmobQuery<ShopG> query = new BmobQuery<ShopG>();
            query.addWhereEqualTo("shopname", shopname);
            if ((!cat.equals("全部商品")) && (!cat.equals("下架商品")))
                query.addWhereEqualTo("gcat", cat);
            else if (cat.equals("下架商品"))
                query.addWhereEqualTo("goodssta", Boolean.FALSE);

            query.setLimit(30);
            Log.e("MyStore里的初始化商品信息", "准备好匹配的信息是shopname——" + shopname);

            query.findObjects(getActivity(), new FindListener<ShopG>() {
                @Override
                public void onSuccess(List<ShopG> list) {
                    sgl = (ArrayList<ShopG>) list;
                    if (sgl.size() == 0) {
                        toast("这个类别暂无商品，快去添加吧~");
                        if(mGLAdapter != null)
                            mGLAdapter.notifyDataSetChanged();
                    } else {
                        List<Map<String, Object>> GoodsList = new ArrayList<Map<String, Object>>();
                        GoodsList = getListItems(sgl);
                        listView = (ListView) mMainView.findViewById(R.id.lv_goods);
                        mGLAdapter = new GoodsListAdapter(getActivity(), GoodsList); //创建适配器
                        listView.setAdapter(mGLAdapter);
                        mGLAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("Mystor", "未能成功查询，出现错误：" + s);
                }
            });

        }
    }

    private void initButton() {
        /**店铺编辑响应
         */
        edit = (ImageView) mMainView.findViewById(R.id.imageView2);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetStoreInf.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Shop", mShop);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        /**搜索栏响应
         */
        searchbtn = mMainView.findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MySearch.class);
                intent.putExtra("shopname", mShop.getShopname());
                startActivity(intent);
            }
        });
        /**添加商品btn响应
         */
        addbtn = mMainView.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddGoods.class);
                intent.putExtra("shopname", mShop.getShopname());
                startActivity(intent);
            }
        });
        /**查看店铺信息详情
         */
        setbtn = mMainView.findViewById(R.id.setinf);
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StoreInf.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Shop", mShop);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void loadpic() {

        File file = new File(file_path);
        if (!file.exists())
            try {
                head.setImageDrawable(getResources().getDrawable(R.drawable.head1));
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("on creating file!", "error!!!!!!!");
                e.printStackTrace();
            }
        else {
            pic_bt = BitmapFactory.decodeFile(file_path);
            head.setImageBitmap(pic_bt);
        }
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("on creating :", "error!!!");
            e.printStackTrace();
        }
        String file_na = null;

        file_na = mShop.getPicture();
        if (file_na != null && pic_bt == null) {
            Log.e("MyStore里的loadpic", file_na);
            downloadFile(file_na);
        }
    }

    private void downloadFile(String file_name) {
        BmobProFile.getInstance(getActivity()).download(file_name, new DownloadListener() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.e("MyStore里的downloadFile", arg1 + "error1" + arg0);
            }

            @Override
            public void onProgress(String arg0, int arg1) {
                // TODO Auto-generated method stub
                Log.e("MyStore里的downloadFile", arg0);
            }

            @Override
            public void onSuccess(String full_path) {
                // TODO Auto-generated method stub
                Log.e("MyStore里的downloadFile", "downlaodFile: " + full_path);
                pic_bt = BitmapFactory.decodeFile(full_path);
                head.setImageBitmap(pic_bt);
                saveShopBitmap(pic_bt);
            }
        });

    }

    private void saveShopBitmap(Bitmap photo) {
        BufferedOutputStream buffer_out = new BufferedOutputStream(out);
        photo.compress(Bitmap.CompressFormat.PNG, 100, buffer_out);
        try {
            buffer_out.flush();
            buffer_out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("write bitmap:", "error!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }

    private void toast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

    }

}

