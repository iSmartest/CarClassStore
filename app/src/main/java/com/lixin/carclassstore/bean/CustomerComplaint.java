package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */

public class CustomerComplaint {

    public String result;//"0" //0成功 1失败
    public String resultNote;//"失败原因"
    public List<complains>complains;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public List<CustomerComplaint.complains> getComplains() {
        return complains;
    }

    public void setComplains(List<CustomerComplaint.complains> complains) {
        this.complains = complains;
    }

    public class complains {
        public String complainid;
        public String shopName;//"的就撒看见的"//门店的名字
        public String userName;//""//
        public String commodityIcon;//""//
        public String content;//"很反感" //自己的投诉
        public String replay;//"中级经济师的"//商家的回复
        public String complainTime;//"2011-2-12 4:44"//投诉的时间

        public String getComplainid() {
            return complainid;
        }

        public void setComplainid(String complainid) {
            this.complainid = complainid;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCommodityIcon() {
            return commodityIcon;
        }

        public void setCommodityIcon(String commodityIcon) {
            this.commodityIcon = commodityIcon;
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
}
