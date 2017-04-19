package com.lixin.carclassstore.bean;


import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/13
 * My mailbox is 1403241630@qq.com
 */

public class MyOrderBean {
    public String result;//"0" //0成功1失败
    public String resultNote;//"失败原因"
    public String totalPage;//5//总页数
    public List<orders> orders;

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

    public List<MyOrderBean.orders> getOrders() {
        return orders;
    }

    public void setOrders(List<MyOrderBean.orders> orders) {
        this.orders = orders;
    }

    public class orders{
        public String orderId;//""//订单号
        public String shopName;//""//门店名称
        public String shopid;//""//门店id
        public String oderTotalPrice;//""//订单总价格
        public String orderState;//"0" //1未支付订单,2未收货订单,3未评价订单,4完成订单
        public List<orderCommodity> orderCommodity;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getOderTotalPrice() {
            return oderTotalPrice;
        }

        public void setOderTotalPrice(String oderTotalPrice) {
            this.oderTotalPrice = oderTotalPrice;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        public List<MyOrderBean.orders.orderCommodity> getOrderCommodity() {
            return orderCommodity;
        }

        public void setOrderCommodity(List<MyOrderBean.orders.orderCommodity> orderCommodity) {
            this.orderCommodity = orderCommodity;
        }

        public class orderCommodity{
            public String commodityid;//"12"//用于跳转到商品详情得到具体商品
            public String commodityIcon;//"http://sdsdsa.png"
            public String commodityNewPrice;//"799"
            public String commodityTitle;//混和机油"
            public String commodityDescription;//经济实惠"
            public String commodityBuyNum;//"2"//购买商品的数目

            public String getCommodityid() {
                return commodityid;
            }

            public void setCommodityid(String commodityid) {
                this.commodityid = commodityid;
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

            public String getCommodityBuyNum() {
                return commodityBuyNum;
            }

            public void setCommodityBuyNum(String commodityBuyNum) {
                this.commodityBuyNum = commodityBuyNum;
            }
        }
    }
}
