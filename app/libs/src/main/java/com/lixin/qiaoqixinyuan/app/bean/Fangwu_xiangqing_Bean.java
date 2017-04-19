package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

import static android.R.attr.type;


/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class Fangwu_xiangqing_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public Housdetail0 housdetail0;//{         //type = 0     //出售

    public class Housdetail0 {
        public String type;//0 出售   1 求租
        public String housdetailname;//"红酒"                         //房屋信息名称
        public String housusername;//"张三"        //房间信息用户昵称
        public String housusericon;//"http;////vgisrfbie"     //房屋信息用户头像
        public String housusertime;//"2015-09-15"     //房屋信息发布时间
        public String housprice;//"800"        //价格
        public String housdetail;//"交通方便"      //信息详述
        public String housaddress;//"山西太原"   //地址
        public String houscontact;//"张三"    //联系人姓名
        public String contactphone;//"136542456987"     //联系人联系电话
        public String liuyannum;//"12"     //留言条数
        public String newsuid;//:"12"
        public String lat;//: 34.345345          // 用户的纬度
        public String lon;//：120.23432             // 用户的经度
        public List<Zhaopin_zqiuzhi_Bean.Jobhuntingdetaile1.ImageUrllist> imageUrllist;
    }


}
