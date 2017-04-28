package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by AYD on 2016/11/22.
 * <p>
 * 购物车
 */
public class ShoppingCollectionFootBean {

    public String result;//"0" 0成功1失败
    public String resultNote;//"失败原因"
    public List<commoditys> commoditys;
    public String totalPage;
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

    public List<ShoppingCollectionFootBean.commoditys> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<ShoppingCollectionFootBean.commoditys> commoditys) {
        this.commoditys = commoditys;
    }

    public class commoditys{

        public boolean isChoosed;
        public boolean isCheck = false;
        private int count;

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String  commodityid;//"12"用于跳转到商品详情得到具体商品
        public String  commodityBrandid;//""//用于请求购物车参数
        public String  commodityShopid;//""//商品对应的门店id
        public String  commodityIcon;//"http://sdsdsa.png"
        public String  commodityNewPrice;//"799"
        public String  commodityTitle;//"混和机油"
        public String  commodityRecommend;//"0"//0为推荐1为非推荐
        public String  commodityCommendNum;//"7837"//商品的评论数
        public String  commoditysellerNum;//"324"//商品的售出数目
        public String  commodityShooCarNum;//""//商品加入购物车的数目
        public String  commodityDescription;//""//商品加入购物车的数目

        public String getCommodityDescription() {
            return commodityDescription;
        }

        public void setCommodityDescription(String commodityDescription) {
            this.commodityDescription = commodityDescription;
        }

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

        public String getCommodityShooCarNum() {
            return commodityShooCarNum;
        }

        public void setCommodityShooCarNum(String commodityShooCarNum) {
            this.commodityShooCarNum = commodityShooCarNum;
        }
    }
}
