package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class Shangpin_all_Bean {
       public String result;//:"0" //0成功 1失败
       public String resultNote;//:”失败原因”
       public List<Classificationlist>classificationlist;//:[{          //商品分类列表
        public class Classificationlist{
        public String classificationId;//:“12”           //商品分类id
        public String classificationame;//:"甜品"   //商品分类名称
    }
}
