package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class Zhaopin_zqiuzhi_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数
    public Jobhuntingdetaile1 jobhuntingdetaile1;//{   // jobhuntingtypeid;//"0"   //0 招聘
    public class Jobhuntingdetaile1{
        public List<ImageUrllist> imageUrllist;// 'http;////sadsfsd'      // 求职图片
        public class ImageUrllist{
         public String imageurl;//:"http://hikmlool"       // 求职图片
            }
        public String jobhuntingname;//"红酒"             //求职标题
        public String jobhuntingnick;//"张三"        //求职信息用户昵称
        public String jobhuntingicon;//"http;////vgisrfbie"     //求职信息用户头像
        public String jobhuntingtime;//"2015-09-15"     //求职信息发布时间
        public String jobhuntingsex;//"女"    //性别
        public String jobcontact;//"张三"    //联系人姓名
        public String contactphone;//"136542456987"     //联系人联系电话
        public String jobdetail;//"交通方便"      //工作经验
        public String myselfdetail;//"性格开朗"   //自我介绍
        public String liuyannum;//"12"     //留言条数
        public String newsuid;//:"12"
    }
}
