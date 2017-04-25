package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/20
 * My mailbox is 1403241630@qq.com
 */

public class ShopDetailsBean {
    public String commodityCommendNum;//"0",
    public String commodityDescription;//"0",
    public String commodityNewPrice;//"0",
    public String commodityStarNum;//"0",
    public String commodityTitle;//"0",
    public String commoditysellerNum;//"0",
    public String result;//"0",
    public String resultNote;//"0",
    public String seckillNumber;//"0",
    public String shopTelephone;//"0",
    public List<commodityCommentLists> commodityCommentLists;
    public List<commodityRelateds> commodityRelateds;
    public List<String> rotateCommodityPics;

    public String getCommodityCommendNum() {
        return commodityCommendNum;
    }

    public void setCommodityCommendNum(String commodityCommendNum) {
        this.commodityCommendNum = commodityCommendNum;
    }

    public String getCommodityDescription() {
        return commodityDescription;
    }

    public void setCommodityDescription(String commodityDescription) {
        this.commodityDescription = commodityDescription;
    }

    public String getCommodityNewPrice() {
        return commodityNewPrice;
    }

    public void setCommodityNewPrice(String commodityNewPrice) {
        this.commodityNewPrice = commodityNewPrice;
    }

    public String getCommodityStarNum() {
        return commodityStarNum;
    }

    public void setCommodityStarNum(String commodityStarNum) {
        this.commodityStarNum = commodityStarNum;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public String getCommoditysellerNum() {
        return commoditysellerNum;
    }

    public void setCommoditysellerNum(String commoditysellerNum) {
        this.commoditysellerNum = commoditysellerNum;
    }

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

    public String getSeckillNumber() {
        return seckillNumber;
    }

    public void setSeckillNumber(String seckillNumber) {
        this.seckillNumber = seckillNumber;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public List<ShopDetailsBean.commodityCommentLists> getCommodityCommentLists() {
        return commodityCommentLists;
    }

    public void setCommodityCommentLists(List<ShopDetailsBean.commodityCommentLists> commodityCommentLists) {
        this.commodityCommentLists = commodityCommentLists;
    }

    public List<ShopDetailsBean.commodityRelateds> getCommodityRelateds() {
        return commodityRelateds;
    }

    public void setCommodityRelateds(List<ShopDetailsBean.commodityRelateds> commodityRelateds) {
        this.commodityRelateds = commodityRelateds;
    }
    public class commodityRelateds{
        public String commodityDescription;
        public String commodityIcon;
        public String commodityNewPrice;
        public String commodityShopName;
        public String commodityShopid;
        public String commodityTitle;
        public String commodityid;
        public String commoditysellerNum;

        public String getCommodityIcon() {
            return commodityIcon;
        }

        public void setCommodityIcon(String commodityIcon) {
            this.commodityIcon = commodityIcon;
        }

        public String getCommodityDescription() {
            return commodityDescription;
        }

        public void setCommodityDescription(String commodityDescription) {
            this.commodityDescription = commodityDescription;
        }

        public String getCommodityNewPrice() {
            return commodityNewPrice;
        }

        public void setCommodityNewPrice(String commodityNewPrice) {
            this.commodityNewPrice = commodityNewPrice;
        }

        public String getCommodityShopName() {
            return commodityShopName;
        }

        public void setCommodityShopName(String commodityShopName) {
            this.commodityShopName = commodityShopName;
        }

        public String getCommodityShopid() {
            return commodityShopid;
        }

        public void setCommodityShopid(String commodityShopid) {
            this.commodityShopid = commodityShopid;
        }

        public String getCommodityTitle() {
            return commodityTitle;
        }

        public void setCommodityTitle(String commodityTitle) {
            this.commodityTitle = commodityTitle;
        }

        public String getCommodityid() {
            return commodityid;
        }

        public void setCommodityid(String commodityid) {
            this.commodityid = commodityid;
        }

        public String getCommoditysellerNum() {
            return commoditysellerNum;
        }

        public void setCommoditysellerNum(String commoditysellerNum) {
            this.commoditysellerNum = commoditysellerNum;
        }
    }
    public class commodityCommentLists{

    }
}
