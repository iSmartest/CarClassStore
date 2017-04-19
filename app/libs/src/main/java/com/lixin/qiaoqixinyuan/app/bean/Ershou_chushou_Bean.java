package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class Ershou_chushou_Bean {

    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public Secondnewsdetaile0 secondnewsdetaile0;//{   // secondtypeid;//"0"    0 出售
    public class Secondnewsdetaile0 {
        public List<Home_Bean.ImagesList> imagesList;// 'http;////sadsfsd'      // 二手图片
        public String secondnewsname;//"红酒"             //二手信息名称
        public String secondusername;//"张三"        //二手信息用户昵称
        public String secondusericon;//"http;////vgisrfbie"     //二手信息用户头像
        public String secondusertime;//"2015-09-15"     //二手信息发布时间
        public String secondprice;//"800"        //价格
        public String seconddetail;//"交通方便"      //信息详述
        public String secondaddress;//"山西太原"   //地址
        public String secondcontact;//"张三"    //联系人姓名
        public String contactphone;//"136542456987"     //联系人联系电话
        public String liuyannum;//"12"     //留言条数
        public String newsuid;//:"12"
        public String lat;//: 34.345345                 // 用户的纬度
        public String lon;//：120.23432                // 用户的经度
    }

}
