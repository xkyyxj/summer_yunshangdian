package com.example.project.easyshopping.data;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
public class Shop extends BmobUser {
   // private static final long serialVersionUID = 1L;
    private String shopname;  //店铺名称
    private String susername; //商家用户名
    private String suserpwd; //商家密码
    private String scat; //店铺分类
    private String business; //主营业务
    private String shopadd; //店铺地址
    private String shoptel;//店铺联系方式
    private Boolean operatesta; //营业状态
    private String starttime;//开始时间
    private String endtime;//营业时间
    private Integer totalgood; //总赞数
    private Integer totalbad; //总踩数
    private BmobGeoPoint slonla; //店铺经纬度
    private Double sendprice; //起送价
    private Double distributeprice; //配送价
    private String spicture; //商家头像图片
    private Integer checkstate; //店铺注册审核状态



    public String getShopname() {
        return shopname;
    }

    public String getSusername() {
        return susername;
    }

    public String getSuserpwd() {
        return suserpwd;
    }

    public String getScat() {
        return scat;
    }

    public String getBusiness() {
        return business;
    }

    public String getShopadd() {
        return shopadd;
    }

    public String getShoptel() {
        return shoptel;
    }

    public Boolean getOperatesta() {
        return operatesta;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public Integer getTotalgood() {
        return totalgood;
    }

    public Integer getTotalbad() {
        return totalbad;
    }

    public BmobGeoPoint getSlonla() {
        return slonla;
    }

    public Double getSendprice() {
        return sendprice;
    }

    public Double getDistributeprice() {
        return distributeprice;
    }

    public String getPicture() {
        return spicture;
    }

    public Integer getCheckstate() {
        return checkstate;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public void setSuserpwd(String suserpwd) {
        this.suserpwd = suserpwd;
    }

    public void setScat(String scat) {
        this.scat = scat;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public void setShopadd(String shopadd) {
        this.shopadd = shopadd;
    }

    public void setShoptel(String shoptel) {
        this.shoptel = shoptel;
    }

    public void setOperatesta(Boolean operatesta) {
        this.operatesta = operatesta;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setTotalgood(Integer totalgood) {
        this.totalgood = totalgood;
    }

    public void setTotalbad(Integer totalbad) {
        this.totalbad = totalbad;
    }

    public void setSlonla(BmobGeoPoint slonla) {
        this.slonla = slonla;
    }

    public void setSendprice(Double sendprice) {
        this.sendprice = sendprice;
    }

    public void setDistributeprice(Double distributeprice) {
        this.distributeprice = distributeprice;
    }

    public void setPicture(String picture) {
        this.spicture = picture;
    }

    public void setCheckstate(Integer checkstate) {
        this.checkstate = checkstate;
    }
}