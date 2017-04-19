package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Huodong_xiangqing_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数
    public List<ImagesList> imagesList;//;//[{      //
    public List<Shangjia_shangpin_search_Bean.Goodslist> goodslist;//;//[{      //
    public String huodongtype;//"0" //0距离活动结束时间 1距离活动开始 2活动已结束
    public String huodongtime;//"3天"  //  结束时间  活动开始  已结束  时间
    public String huodongdescribe;//"团购。。"   //活动详情
    public String huodongtitle;//"活动名称"     //活动名称
    public String shangjiaicon;//"78"       //商家图片
    public String startTime;//:"2017-01-02"    //活动开始时间
    public String endTime;//:"2017-01-02"     //活动结束时间
    public class ImagesList{
        public String imageUrl;// 'http;////sadsfsd'       // 图片
    }
}
