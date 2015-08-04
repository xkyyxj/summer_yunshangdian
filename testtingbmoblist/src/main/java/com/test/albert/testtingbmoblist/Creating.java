package com.test.albert.testtingbmoblist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Albert on 2015/7/16.
 */
public class Creating {

    public static Ord order = null;

    public static Ord creatingOrder(int comm_state,String order_state,String shop_name)
    {
        Calendar cl = Calendar.getInstance();
        Date date = cl.getTime();
        BmobDate date1 = new BmobDate(date);
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<Integer> number = new ArrayList<Integer>();
        ArrayList<Integer> price = new ArrayList<Integer>();
        name.add("what");
//        name.add("shenma");
//        name.add("what");
//        name.add("shenma");
//        name.add("what");
//        name.add("shenma");
//        name.add("what");
//        name.add("shenma");
//        name.add("what");
//        name.add("shenma");
//        name.add("what");
//        name.add("shenma");
//        name.add("what");
//        name.add("shenma");
//        name.add("what");
//        name.add("shenma");
        number.add(23);
//        number.add(12);
//        number.add(23);
//        number.add(12);
//        number.add(23);
//        number.add(12);
//        number.add(23);
//        number.add(12);
//        number.add(23);
//        number.add(12);
//        number.add(23);
//        number.add(12);
//        number.add(23);
//        number.add(12);
//        number.add(23);
//        number.add(12);
        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
//        price.add(123);
        order = new Ord();
        order.setComm_state(comm_state);
        order.setCom("what the fuck");
        //order.setObjectId("luanqibalazao");
        order.setGoodsname(name);
        order.setNum(number);
        order.setPrice(price);
        order.setNote("guroan haomafan");
        order.setOrdid(123324534);
        order.setJystate(order_state);
        order.setOrdtime(date1);
        order.setPayway("123123");
        order.setUseradd("12341234");
        order.setShopname(shop_name);
        order.setReceiver("12341234");
        order.setTotalprice(123123);
        order.setUsername("123asdf");
        order.setUsertel("12342433456");
        return order;
    }

    public static Shop createShop(String shop_name)
    {
        Shop shop = new Shop();
        shop.setShopname(shop_name);
        shop.setShoptel("1232345234");
        shop.setDistributeprice(123);
        shop.setPicture("cbe7bebe2dc5408f9c88fa9b37edfc81.png");
        return shop;
    }


}
