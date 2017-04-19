package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/19
 * My mailbox is 1403241630@qq.com
 */

public class ShopBean {
    public String result;//"0" //0成功1失败
    public String resultNote;//"失败原因"
    public String totalPage;//5//总页数
    public List<commoditys> commoditys;

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

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<ShopBean.commoditys> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<ShopBean.commoditys> commoditys) {
        this.commoditys = commoditys;
    }

    public class commoditys{//
        public String commodityid;//"12"//用于跳转到商品详情得到具体商品
        public String commodityBrandid;//""//用于请求购物车参数
        public String commodityShopid;//""//商品对应的门店id
        public String commodityType;//""//商品类型
        public String commodityIcon;//"http://sdsdsa.png"
        public String commodityNewPrice;//"799"
        public String commodityTitle;//"混和机油"
        public String commodityDescription;//"经济实惠"
        public String commodityRecommend;//"0"//0为推荐1为非推荐
        public String commodityCommendNum;//"7837"//商品的评论数
        public String commoditysellerNum;//"324"//商品的售出数目

        public String getCommodityid() {
            return commodityid;
        }

        public void setCommodityid(String commodityid) {
            this.commodityid = commodityid;
        }

        public String getCommodityBrandid() {
            return commodityBrandid;
        }

        public void setCommodityBrandid(String commodityBrandid) {
            this.commodityBrandid = commodityBrandid;
        }

        public String getCommodityShopid() {
            return commodityShopid;
        }

        public void setCommodityShopid(String commodityShopid) {
            this.commodityShopid = commodityShopid;
        }

        public String getCommodityType() {
            return commodityType;
        }

        public void setCommodityType(String commodityType) {
            this.commodityType = commodityType;
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

        public String getCommodityDescription() {
            return commodityDescription;
        }

        public void setCommodityDescription(String commodityDescription) {
            this.commodityDescription = commodityDescription;
        }

        public String getCommodityRecommend() {
            return commodityRecommend;
        }

        public void setCommodityRecommend(String commodityRecommend) {
            this.commodityRecommend = commodityRecommend;
        }

        public String getCommodityCommendNum() {
            return commodityCommendNum;
        }

        public void setCommodityCommendNum(String commodityCommendNum) {
            this.commodityCommendNum = commodityCommendNum;
        }

        public String getCommoditysellerNum() {
            return commoditysellerNum;
        }

        public void setCommoditysellerNum(String commoditysellerNum) {
            this.commoditysellerNum = commoditysellerNum;
        }
    }
}
