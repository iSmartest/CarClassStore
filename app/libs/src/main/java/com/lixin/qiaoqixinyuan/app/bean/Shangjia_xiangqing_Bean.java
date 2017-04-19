package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Shangjia_xiangqing_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public List<ImagesList> imagesList;//[{      //
    public Merchantsdetail merchantsdetail;
    public class ImagesList{
        public String imageUrl;// 'http;////sadsfsd'       // 图片
    }
    public class Merchantsdetail
    {
        public String shangjiaName;//"红酒"                         //商家名称
        public String shangjiaimage;//：“http////fddrftgrt”       //商家头像
        public String shangjiadescribe;//"3小时到达"       //商家描述
        public String shangjiaaddress;//"3小时到达"      //商家地址
        public String shangjiaphone;//"12365478985"       //商家电话
        public String shangjiafreight;//"运费3元"       //商家运费设置
        public String shangjiadelivery;//"5公里内可送"       //商家送货范围
        public String lat;
        public String lon;
    }
}
