package com.lixin.qiaoqixinyuan.app.bean;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyPublishJobhuntingBean
 * 类描述：我的发布二手信息
 * 创建人：Tiramisu
 * 创建时间：2017/2/13 15:39
 */

public class MyPublishJobhuntingBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public JobhuntingBean jobhuntinglist;

    public class JobhuntingBean {
        public String jobhuntingtypeid;//0 招聘   1 求职
        public String jobhuntingid;//房屋信息id
        public String secondinnewsimage;//信息图片
        public String secondinnewsitem;//信息名称
        public String secondinnewstype;//房屋类型
        public String secondinnewsdetail;//房屋描述
        public String secondinnewstime;//信息时间
    }
}
