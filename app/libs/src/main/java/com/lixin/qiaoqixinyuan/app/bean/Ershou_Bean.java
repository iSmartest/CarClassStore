package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class Ershou_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数
    public List<Secondnewslist> secondnewslist;//[{    //type  = 0  房屋信息
    public class Secondnewslist{
        public String secondtypeid;//0 出售   1 求购
        public String secondnewsid;//房屋信息id
        public String secondinnewsimage;//信息图片
        public String secondinnewsitem;//信息名称
        public String secondinnewstype;//房屋类型
        public String secondinnewsdetail;//房屋描述
        public String secondinnewstime;//信息时间
    }
   
        
}
