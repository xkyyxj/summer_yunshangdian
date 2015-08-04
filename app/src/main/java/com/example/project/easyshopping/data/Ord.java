package com.example.project.easyshopping.data;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;


public class Ord extends BmobObject implements Serializable{

    private String ordid;//订单号
    private String username;//用户名
    private String shopname;//店铺名称
    private String totalprice;//总价
    private String ordtime;//订单时间
    private String receiver;//收货人
    private String useradd;//收货地址
    private String usertel; //联系方式
    private String jystate; //交易状态
    private String payway; //支付方式
    private Integer comm_state;//点评态度：0.未点评；1.好评；2.差评
    private String com; //点评
    private ArrayList<String> goodsname;//商品名称
    private ArrayList<String> price;//价格
    private ArrayList<String> nums;//数量
    private String note;//备注

    public Integer getComm_state() {
        return comm_state;
    }

    public void setComm_state(Integer comm_state) {
        this.comm_state = comm_state;
    }

    public String getJystate() {
        return jystate;
    }

    public void setJystate(String jystate) {
        this.jystate = jystate;
    }

    public ArrayList<String> getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(ArrayList<String> goodsname) {
        this.goodsname = goodsname;
    }

    public ArrayList<String> getNum() {
        return nums;
    }

    public void setNum(ArrayList<String> nums) {
        this.nums = nums;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) { this.ordid = ordid;  }

    public String getOrdtime() {
        return ordtime;
    }

    public void setOrdtime(String ordtime) {
        this.ordtime = ordtime;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getUseradd() {
        return useradd;
    }

    public void setUseradd(String useradd) {
        this.useradd = useradd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertel() {
        return usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }
}