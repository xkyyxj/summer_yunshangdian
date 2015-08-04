package com.test.albert.testtingbmoblist;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Albert on 2015/7/15.
 */
public class Shop extends BmobObject {

    private String shopname; // ��������
    private String susername; // �̼��û���
    private String suserpwd; // �̼�����
    private String scat; // ���̷���
    private String business; // ��Ӫҵ��
    private String shopadd; // ���̵�ַ
    private String shoptel;// ������ϵ��ʽ
    private boolean operatesta; // Ӫҵ״̬
    private String startTime;
    private String endTime;// Ӫҵʱ��
    private Integer totalgood; // ������
    private BmobGeoPoint slonla; // ���̾�γ��
    private double sendprice; // ���ͼ�
    private double distributeprice; // ���ͼ�
    private String picture; // �̼�ͷ��ͼƬ
    private int checkstate; // ����ע�����״̬

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
