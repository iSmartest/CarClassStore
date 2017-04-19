package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class Ping_data_Bean  {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数 
    public List<Carpoolinglist> carpoolinglist;
    public class Carpoolinglist{
        public String carpoolingid;//"12"   //拼车信息id
        public String carpoolingicon;//"http;////adfiohwoeigbfvoiuw"   //拼车信息用户头像
        public String carpoolingname;//"张三"   //用户昵称
        public String carpoolingtime;//"下午1：40"   //用户发布信息时间
        public String carpoolingtype;//"车找人"   //信息类型
        public String carpoolingaddress;//"沁源-长治"   // 发车/乘车 地点   
        public String carpoolingdate;//"今天"    //发车、乘车日期
        public String carpoolingnewstime;//"中午12点-13点"   //发车、乘车时间
        public String carpoolingphone;//"12365265895"   //联系人电话
        public String carpoolingnote;//"宽敞舒适"   //备注信息
        public String openId;
    }
}
