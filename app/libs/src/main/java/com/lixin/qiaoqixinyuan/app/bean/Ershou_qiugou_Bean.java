package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class Ershou_qiugou_Bean {
 
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数
    public Secondnewsdetaile1 secondnewsdetaile1;//{   // secondtypeid;//"1"     1 求购
    public class Secondnewsdetaile1 {
        public String imageUrl;// 'http;////sadsfsd'      // 二手图片
        public String secondnewsname;//"红酒"             //二手信息名称
        public String secondusername;//"张三"        //二手信息用户昵称
        public String secondusericon;//"http;////vgisrfbie"     //二手信息用户头像
        public String secondusertime;//"2015-09-15"     //二手信息发布时间
        public String seconddetail;//"交通方便"      //信息详述
        public String secondcontact;//"张三"    //联系人姓名
        public String contactphone;//"136542456987"     //联系人联系电话
        public String liuyannum;//"12"     //留言条数
        public String newsuid;//:"12"
    }
}
