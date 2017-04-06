package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */

public class CustomerComplaint {

    private String shopName;
    private String content;
    private String replay;
    private String complainTime;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getComplainTime() {
        return complainTime;
    }

    public void setComplainTime(String complainTime) {
        this.complainTime = complainTime;
    }
}
