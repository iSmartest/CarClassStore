package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/3/30
 * My mailbox is 1403241630@qq.com
 */

public class StoreDetailBean {
    protected int id;
    private String imageUrl;//商品图片
    private String shoppingName;//商品名
    private String size;//尺寸
    private int salesvolume;//销量
    private double price;//价格
    public boolean isChoosed;
    public boolean isCheck = false;

    public StoreDetailBean() {

    }

    public StoreDetailBean(int id, String imageUrl, String shoppingName, String size, int salesvolume,
                           double price) {
        this.id = id;
        this.price = price;
        this.shoppingName = shoppingName;
        this.size = size;
        this.salesvolume = salesvolume;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSalesvolume() {
        return salesvolume;
    }

    public void setSalesvolume(int salesvolume) {
        this.salesvolume = salesvolume;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getShoppingName() {
        return shoppingName;
    }

    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
