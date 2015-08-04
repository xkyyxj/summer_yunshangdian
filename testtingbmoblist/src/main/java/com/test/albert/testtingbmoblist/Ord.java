package com.test.albert.testtingbmoblist;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;


public class Ord extends BmobObject {

    private Integer ordid;//订单号
    private String username;//用户名
    private String shopname;//店铺名称
    private Integer totalprice;//总价
    private BmobDate ordtime;//订单时间
    private String receiver;//收货人
    private String useradd;//收货地址
    private String usertel; //联系方式
    private String jystate; //交易状态
    private String payway; //支付方式
    private Integer comm_state;//点评态度：0.未点评；1.好评；2：差评
    private String com; //点评
    private ArrayList<String> goodsname;//商品名称
    private ArrayList<Integer> price;//价格
    private ArrayList<Integer> num;//数量
    private String note;//备注

    public String getJystate() {
        return jystate;
    }

    public void setJystate(String jystate) {
        this.jystate = jystate;
    }

    public Integer getComm_state() {
        return comm_state;
    }

    public void setComm_state(Integer comm_state) {
        this.comm_state = comm_state;
    }

    public ArrayList<String> getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(ArrayList<String> goodsname) {
        this.goodsname = goodsname;
    }

    public ArrayList<Integer> getNum() {
        return num;
    }

    public void setNum(ArrayList<Integer> num) {
        this.num = num;
    }

    public ArrayList<Integer> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Integer> price) {
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

    public Integer getOrdid() {
        return ordid;
    }

    public void setOrdid(Integer ordid) {
        this.ordid = ordid;
    }

    public BmobDate getOrdtime() {
        return ordtime;
    }

    public void setOrdtime(BmobDate ordtime) {
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

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
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
