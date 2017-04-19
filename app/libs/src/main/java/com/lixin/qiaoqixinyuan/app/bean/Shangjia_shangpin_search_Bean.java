package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Shangjia_shangpin_search_Bean {
    public String result;//:"0" //0成功 1失败
    public String resultNote;//:”失败原因”
    public String totalPage;//:"5" //总页数
    public List<Goodslist> goodslist;//:[{          //商品列表
    public List<Shangjialiebiao_Bean.Merchantslist> merchantslist;//[{          //商家列表
    public String huodongtype;//:"0" //0距离活动结束时间 1距离活动开始 2活动已结束
    public String startTime;//:"2017-01-02"    //活动开始时间
    public String endTime;//:"2017-01-02"     //活动结束时间
    public String huodongtime;//:"3天"  //  结束时间  活动开始  已结束  时间
    public String huodongdescribe;//:"团购。。"   //活动详情
    public String huodongtitle;//:"活动名称"     //活动名称
    public String shangjiaicon;//:"78"       //商家图片
    public class Goodslist{
        public String goodsid;//: “12”                    //商品id
        public String shangjiaid;//:"35"              //商家id
        public String goodsimage;//: “http://yuodbuedb”    //商品图片
        public String shangjiajuli;//“680m”       //  商家距离
        public String shangjiascribe;//“走过路过不要错过”    //商品描述
        public String shgangpintype;//"0"    //商品是否参与活动，0活动 1非活动
        public String shangjianame;//"多乐滋蛋糕"   //商家名称
        public String shangpinname;//"多乐滋蛋糕"   //商品名称
        public String yuanprice;//“58”               //商品原价
        public String xianprice;//"78"       //商品现价
        public String clicknumber;//:"20"     //活动点击量
    }
}
