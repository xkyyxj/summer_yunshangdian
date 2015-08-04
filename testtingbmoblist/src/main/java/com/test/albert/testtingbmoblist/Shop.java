package com.test.albert.testtingbmoblist;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Albert on 2015/7/15.
 */
public class Shop extends BmobObject {

    private String shopname; // 店铺名称
    private String susername; // 商家用户名
    private String suserpwd; // 商家密码
    private String scat; // 店铺分类
    private String business; // 主营业务
    private String shopadd; // 店铺地址
    private String shoptel;// 店铺联系方式
    private boolean operatesta; // 营业状态
    private String startTime;
    private String endTime;// 营业时间
    private Integer totalgood; // 总赞数
    private BmobGeoPoint slonla; // 店铺经纬度
    private double sendprice; // 起送价
    private double distributeprice; // 配送价
    private String picture; // 商家头像图片
    private int checkstate; // 店铺注册审核状态

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public int getCheckstate() {
        return checkstate;
    }

    public void setCheckstate(int checkstate) {
        this.checkstate = checkstate;
    }

    public double getDistributeprice() {
        return distributeprice;
    }

    public void setDistributeprice(double distributeprice) {
        this.distributeprice = distributeprice;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isOperatesta() {
        return operatesta;
    }

    public void setOperatesta(boolean operatesta) {
        this.operatesta = operatesta;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getScat() {
        return scat;
    }

    public void setScat(String scat) {
        this.scat = scat;
    }

    public double getSendprice() {
        return sendprice;
    }

    public void setSendprice(double sendprice) {
        this.sendprice = sendprice;
    }

    public String getShopadd() {
        return shopadd;
    }

    public void setShopadd(String shopadd) {
        this.shopadd = shopadd;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShoptel() {
        return shoptel;
    }

    public void setShoptel(String shoptel) {
        this.shoptel = shoptel;
    }

    public BmobGeoPoint getSlonla() {
        return slonla;
    }

    public void setSlonla(BmobGeoPoint slonla) {
        this.slonla = slonla;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSusername() {
        return susername;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public String getSuserpwd() {
        return suserpwd;
    }

    public void setSuserpwd(String suserpwd) {
        this.suserpwd = suserpwd;
    }

    public Integer getTotalgood() {
        return totalgood;
    }

    public void setTotalgood(Integer totalgood) {
        this.totalgood = totalgood;
    }
}
