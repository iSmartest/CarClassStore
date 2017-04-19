package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class Zhaopin_zhaopin_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public Jobhuntingdetaile0 jobhuntingdetaile0;//{   // jobhuntingtypeid;//"0"   //0 招聘
        public class Jobhuntingdetaile0{
            public List<Zhaopin_zqiuzhi_Bean.Jobhuntingdetaile1.ImageUrllist> imageUrllist;
            public String imageUrl;// 'http;////sadsfsd'      // 招聘公司图片         
            public String jobhuntingname;//"红酒"             //招聘名称
            public String jobhuntingnick;//"张三"        //招聘信息用户昵称
            public String jobhuntingicon;//"http;////vgisrfbie"     //招聘信息用户头像
            public String jobhuntingtime;//"2015-09-15"     //招聘信息发布时间
            public String companyname;//"防盗门"   //公司名称
            public String jobhuntingnum;//"5"   //招聘人数
            public String jobhuntingsex;//"女"    //性别
            public String jobhuntingschooling;//"大专"   //学历
            public String jobexperience;//"2年工作经验"  //工作经验
            public String jobsalary;//"3000-4000"   //薪资
            public String jobcontact;//"张三"    //联系人姓名
            public String contactphone;//"136542456987"     //联系人联系电话
            public String companyaddress;//"山西太原"   //地址
            public String jobdetail;//"交通方便"      //职位详述
            public String liuyannum;//"12"     //留言条数
            public String newsuid;//:"12"
            public String lat;//: 34.345345                 // 用户的纬度
            public String lon;//：120.23432                // 用户的经度
        }
}
