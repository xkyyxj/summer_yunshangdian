package com.example.project.easyshopping.Util;

import android.util.Log;

import com.example.project.easyshopping.data.ShopG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2015/7/17.
 */
public class GetShopGItem {

    public static String[] objid;    //商品ID
    public static String[] shopname;//店铺名称
    public static String[] goodsname;//商品名称
    public static String[] picture;//商品图片
    public static String[] price;//价格
    public static String[] description;//描述
    public static String[] gcat;//商品分类
    public static Boolean[] goodssta;//商品状态
    public static Integer[] volume;//商品销量

    public static List<Map<String, Object>> getShopGItems(List<ShopG> sgl) {
        ArrayList<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        int size = sgl.size();
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = new HashMap<String, Object>();

            objid = new String[size];
            shopname = new String[size];//店铺名称
            goodsname = new String[size];//商品名称
            picture = new String[size];//商品图片
            price = new String[size];//价格
            description = new String[size];//描述
            gcat = new String[size];//商品分类
            goodssta = new Boolean[size];//商品状态
            volume = new Integer[size];//商品销量

            objid[i] = sgl.get(i).getObjectId();
            map.put("objid", objid[i]);      //物品id
            Log.e("MyStored ", "此处的商品id为" + objid[i]);
            shopname[i] = sgl.get(i).getShopname();
            map.put("shopname", shopname[i]);      //店铺名称
            Log.e("MyStored ", "此处的商品id为" + shopname[i]);
            goodsname[i] = sgl.get(i).getGoodsname();
            map.put("goodsname", goodsname[i]);      //商品名称
            Log.e("MyStored ", "此处的商品名为" + goodsname[i]);
            price[i] = sgl.get(i).getPrice();
            map.put("price", price[i]);              //物品单价
            picture[i] = sgl.get(i).getPicture();
            map.put("picture", picture[i]);              //商品图片
            description[i] = sgl.get(i).getDescription();
            map.put("description", description[i]);         //商品描述
            gcat[i] = sgl.get(i).getGcat();
            map.put("gcat", gcat[i]);              //商品分类
            goodssta[i] = sgl.get(i).getGoodssta();
            map.put("goodssta", goodssta[i]);              //商品状态
            volume[i] = sgl.get(i).getVolume();
            if (volume[i] == null)
                map.put("volume", 0);
            else
                map.put("volume", volume[i]);     //物品销量

            listItems.add(map);
        }
        return listItems;
    }
}
