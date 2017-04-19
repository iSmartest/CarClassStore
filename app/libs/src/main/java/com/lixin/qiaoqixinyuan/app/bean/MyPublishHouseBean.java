package com.lixin.qiaoqixinyuan.app.bean;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyPublishHouseBean
 * 类描述：我的发布房屋信息
 * 创建人：Tiramisu
 * 创建时间：2017/2/13 14:47
 */

public class MyPublishHouseBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public HousinginnewsBean housinginnewslist;

    public class HousinginnewsBean {
        public String type;//0 出售   1 求租
        public String housinginnewsid;//房屋信息id
        public String housclassid;//房屋分类id
        public String housinginnewsitem;//信息名称
        public String housinginnewstype;//房屋类型
        public String housinginnewsdetail;//房屋描述
        public String housinginnewstime;//信息时间
    }
}
