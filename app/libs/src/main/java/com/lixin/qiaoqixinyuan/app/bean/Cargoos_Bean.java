package com.lixin.qiaoqixinyuan.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Cargoos_Bean {
    public String result;//0                            // 0成功 1失败
    public String resultNote;//'失败原因'
    public String shangjiaprice;//"100"     //商品总价格
    public List<CartsGoods> cartsGoods;// [{                          // 购物车商品列表
    public class CartsGoods implements Serializable{
        public String goodsid;// 12                     // 购物车商品id
        public String goodImageUrl;//'http//hshsh'              //图片
        public String goodsName;// '鸡腿饭'              // 购物车商品的名称
        public String goodsPrice;//：14.00              // 购物车商品的单价
        public String goodsNum;//3                      // 购物车商品的数量
    }
}
