package com.lixin.qiaoqixinyuan.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyorderBean
 * 类描述：个人订单实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/11 17:42
 */

public class MyorderBean implements Serializable {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public List<OrderBean> ordersList;
    public class OrderBean implements Serializable {
        public String shangjiaid;//商家id
        public String ordernum;//订单号
        public String cancelorder;//0 未取消  1 已取消
        public String deliverytype;//0 未送货 1已送货(已送货中 有继续付款按钮 参照2.24接口)
        public String orderproductnum;//订单商品数
        public String orderprice;//订单总价
        public String ordertype;// 0 退款中 1 退款成功 2 商家不同意退款
        public String orderpatterns;// 0 送货上门   1 到店消费
        public String talk;// 0 已评价 1 未评价
        public String statustype;//0 进行中 1 已完成
        public String orderVerification;//订单验证码
        public List<ProductBean> productList;
        public String shopName;
        public String shopIcon;
        public String adTime;
        public String goalName;
        public String goalPhone;
        public String goalAddr;
        public String oremark;
        public String sendMoney;
        public class ProductBean implements Serializable {
            public String productId;//商品id
            public String productName;//商品名字
            public String productImage;//商品图片
            public String productprice;//商品价格
            public String productNum;//商品件数

        }
    }
}
