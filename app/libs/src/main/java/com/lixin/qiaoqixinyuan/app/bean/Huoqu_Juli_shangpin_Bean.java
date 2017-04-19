package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class Huoqu_Juli_shangpin_Bean {
    public String result;//:"0" //0成功 1失败
    public String resultNote;//:”失败原因”
    public String totalPage;//:"5" //总页数
    public List<Merchantslist> merchantslist;//:[{          //商家列表
        class Merchantslist{
            public String shangjiaId;//: '1'                    //商家id
            public String shangjiaimage;//: “http://yuodbuedb”  //商品图片
            public String shangjiajuli;//:“680m”     //  商家距离
            public String shangjiascribe;//:“走过路过不要错过”    //商品描述
            public String biaoqianimage;//:"http://hy9obniih"    //标签
            public String shangjianame;//:"多乐滋蛋糕"   //商家名称
            public String yuanprice;//:“58”               //商品原价
            public String xianprice;//:"78"       //商品现价
        }
}
