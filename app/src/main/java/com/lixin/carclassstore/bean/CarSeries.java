package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 */

public class CarSeries {
    private String image;
    private String name;
    private String price;

    public CarSeries() {

    }
    public CarSeries(String image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
