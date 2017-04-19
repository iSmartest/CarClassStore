package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class Qiuzhi_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数
    public List<Jobhuntinglist> jobhuntinglist;//[{    //type  = 0  房屋信息
    public class Jobhuntinglist{
        public String jobhuntingtypeid;//"0"   //0 招聘   1 求职   app写死
        public String jobhuntingid;//"12"   //房屋信息id
        public String jobhuntingimage;//"http;////xwsioqu”   //信息图片
        public String jobhuntingitem;//"单间出租"   //信息名称
        public String jobhuntingtype;//"出租"    //房屋类型
        public String jobhuntingdetail;//"家电齐全"    //房屋描述
        public String jobhuntingtime;//"2015-05-40"      //信息时间
    }
    
}
