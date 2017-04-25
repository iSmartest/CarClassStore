package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/24
 * My mailbox is 1403241630@qq.com
 */

public class StoreDetailsBean {
    public String result;
    public String resultNote;
    public String sellerNum;
    public String shopDatil;
    public String shopLocaltion;
    public String shopName;
    public String shopTelephone;
    public String shopTime;
    public List<String> rotateShopPics;
    public List<shopCommoditys> shopCommoditys;

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

    public String getSellerNum() {
        return sellerNum;
    }

    public void setSellerNum(String sellerNum) {
        this.sellerNum = sellerNum;
    }

    public String getShopDatil() {
        return shopDatil;
    }

    public void setShopDatil(String shopDatil) {
        this.shopDatil = shopDatil;
    }

    public String getShopLocaltion() {
        return shopLocaltion;
    }

    public void setShopLocaltion(String shopLocaltion) {
        this.shopLocaltion = shopLocaltion;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public String getShopTime() {
        return shopTime;
    }

    public void setShopTime(String shopTime) {
        this.shopTime = shopTime;
    }

    public List<String> getRotateShopPics() {
        return rotateShopPics;
    }

    public void setRotateShopPics(List<String> rotateShopPics) {
        this.rotateShopPics = rotateShopPics;
    }

    public List<StoreDetailsBean.shopCommoditys> getShopCommoditys() {
        return shopCommoditys;
    }

    public void setShopCommoditys(List<StoreDetailsBean.shopCommoditys> shopCommoditys) {
        this.shopCommoditys = shopCommoditys;
    }

    public class shopCommoditys {
        public String commodityType;
        public String id;
        public List<commoditys> commoditys;

        public String getCommodityType() {
            return commodityType;
        }

        public void setCommodityType(String commodityType) {
            this.commodityType = commodityType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<StoreDetailsBean.shopCommoditys.commoditys> getCommoditys() {
            return commoditys;
        }

        public void setCommoditys(List<StoreDetailsBean.shopCommoditys.commoditys> commoditys) {
            this.commoditys = commoditys;
        }

        public class commoditys {
            public String commodityCommendNum;
            public String commodityDescription;
            public String commodityIcon;
            public String commodityNewPrice;
            public String commodityTitle;
            public String commodityid;
            public String commoditysellerNum;

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

            public String getCommodityIcon() {
                return commodityIcon;
            }

            public void setCommodityIcon(String commodityIcon) {
                this.commodityIcon = commodityIcon;
            }

            public String getCommodityNewPrice() {
                return commodityNewPrice;
            }

            public void setCommodityNewPrice(String commodityNewPrice) {
                this.commodityNewPrice = commodityNewPrice;
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
    }
}