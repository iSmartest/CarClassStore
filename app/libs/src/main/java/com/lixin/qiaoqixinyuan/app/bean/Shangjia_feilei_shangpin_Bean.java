package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Shangjia_feilei_shangpin_Bean {
        public String result;//"0" //0成功 1失败
       public String resultNote;//”失败原因”
      public String totalPage;//”失败原因”
      public List<Shangpinlist> shangpinlist;//[{          //商品列表
        public class Shangpinlist{
            public String shangpinId;// '1'                    //商品id
            public String shangpinname;//"多乐滋蛋糕"   //商品名称
            public String shangpinimage;//"http;////gbuwdsbue"   //商品图片
            public String shgangpintype;//"0"    //商品是否参与活动，0活动 1非活动
            public String shangpinscribe;//"好的好的"    //商品描述
            public String shangpinprice;//"86"    //商品价格
            public String shangpindianji;//"20"   //商品点击量
            public String huodongid;//:"12"  //活动id
            public String goodsid;//: '1'            //商品id
            public String shangpinyuanprice;//:"100"   //商品原价
            public String qianggouprice;



        }

    
}
