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
    public class orders{
        public String orderId;//""//订单号
        public String shopName;//""//门店名称
        public List<orderCommodity> orderCommodity;
        public class orderCommodity{
            public String commodityName;//"记录仪"//
            public String commodityIcon;//"http://sadsa.png"
            public String commodityNewPrice;//"799"//商品的单价
            public String commodityBuyNum;//2"//购买商品的数目
        }
    }
}
