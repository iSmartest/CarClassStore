package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class Fangwu_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数 
    public List<Housinginnewslist> housinginnewslist;
    public class Housinginnewslist{
            public String type;//"0"   //0 出售   1 求租
            public String housinginnewsid;//"12"   //房屋信息id
            public String housingnewsimage;//yuodbuedb”    //活动图片
            public String housclassid;//"12"   //房屋分类id
            public String housinginnewsitem;//"单间出租"   //信息名称
            public String housinginnewstype;//"出租"    //房屋类型
            public String housinginnewsdetail;//"家电齐全"    //房屋描述
            public String housinginnewstime;//"2015-05-40"      //信息时间
            public String istop;

        }
}

