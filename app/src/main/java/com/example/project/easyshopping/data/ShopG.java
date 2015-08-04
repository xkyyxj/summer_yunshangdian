package com.example.project.easyshopping.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by acer on 2015/7/13.
 */
public class ShopG extends BmobObject{
    private String shopname;//店铺名称
    private String goodsname;//商品名称
    private String picture;//商品图片
    private String price;//价格
    private String description;//描述
    private String gcat;//商品分类
    private Boolean goodssta;//商品状态
    private Integer volume;//商品销量

    public String getShopname() {
        return shopname;
    }
    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
    public String getGoodsname() {
        return goodsname;
    }
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getGcat() {
        return gcat;
    }
    public void setGcat(String gcat) {
        this.gcat = gcat;
    }
    public Boolean getGoodssta() {
        return goodssta;
    }
    public void setGoodssta(Boolean goodssta) {
        this.goodssta = goodssta;
    }
    public Integer getVolume() {
        return volume;
    }
    public void setVolume(Integer volume) {
        this.volume = volume;
    }
}