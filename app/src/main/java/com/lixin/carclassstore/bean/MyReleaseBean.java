package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class MyReleaseBean {
    private String content;
    private String time;
    private String comment;
    private String num;
    private int count;

    public MyReleaseBean(){

    }
    public void MyReleaseBean(String content, String time, String comment, String num, int count){
        this.comment = comment;
        this.content = content;
        this.time = time;
        this.num = num;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
