package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Shangpin_xiangqing_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public List<Images> imagesList;//[{     //商品图片
    public goodDetail goodDetail;
        public class Images{
            public String imageUrl;// 'http;////sadsfsd'       // 图片
        }
        public class goodDetail{
    public String goodsName;//"红酒"                         //商品名称
    public String goodIntroduce;//：“”                        //商品介绍
    public String goodsPrice;//"50元"                          //商品价格
    public String goodrequirements;//"3小时到达"       //用户须知
    }
 
}
