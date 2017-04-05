package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/3/29
 * My mailbox is 1403241630@qq.com
 */

public class StoreBean
{
    private int state;
    private String image;
    private String title;
    private String num;
    private String score;
    private String address;
    public void  StoreBeanList(int state ,String image,String title,String num,String score,
             String address){
        this.state = state;
        this.image = image;
        this.title = title;
        this.num = num;
        this.score = score;
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
