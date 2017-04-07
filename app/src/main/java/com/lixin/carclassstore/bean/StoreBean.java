package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/29
 * My mailbox is 1403241630@qq.com
 */

public class StoreBean {
    public String result; //0成功1失败"0"
    public String resultNote;//"失败原因"
    public String totalPage;//总页数 5
    public List<shopList> shop;
    public class shopList{
        public String shopid;
        public String shopIcon;
        public String shopName;
        public String shopLocaltion;
        public String sellerNum;
        public String shopCommentNum;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopIcon() {
            return shopIcon;
        }

        public void setShopIcon(String shopIcon) {
            this.shopIcon = shopIcon;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopLocaltion() {
            return shopLocaltion;
        }

        public void setShopLocaltion(String shopLocaltion) {
            this.shopLocaltion = shopLocaltion;
        }

        public String getSellerNum() {
            return sellerNum;
        }

        public void setSellerNum(String sellerNum) {
            this.sellerNum = sellerNum;
        }

        public String getShopCommentNum() {
            return shopCommentNum;
        }

        public void setShopCommentNum(String shopCommentNum) {
            this.shopCommentNum = shopCommentNum;
        }
    }

}
