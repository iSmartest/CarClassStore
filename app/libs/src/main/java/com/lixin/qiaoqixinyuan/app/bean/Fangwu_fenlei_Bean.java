package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

import static android.R.attr.type;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class Fangwu_fenlei_Bean {
    public String result;//:"0" //0成功 1失败
    public String resultNote;//:”失败原因”
    public List<Housclasslist> housclasslist;//:[{
        public class Housclasslist{
            public String type;//:"0"   //0 出售   1 求租
            public String housclassid;//:"12"   //房屋分类id
            public String housclassname;//:"单间出租"   //房屋分类名称
        }
}
