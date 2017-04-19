package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class Shangjialiebiao_Bean {
    public String result;//:"0" //0成功 1失败
    public String resultNote;//:”失败原因”
    public String totalPage;//:"5" //总页数
    public List<Merchantslist> merchantslist;//:[{          //商家列表
    public class Merchantslist{
        public String shangjiaid;//: '1'                    //商家id
        public String shangjiaimage;//: “http://yuodbuedb”    //商品图片
        public String shangjiajuli;//:“680m”       //  商家距离
        public String shangjiascribe;//:“走过路过不要错过”    //商品描述
        public String biaoqianimage;//:"http://hy9obniih"    //标签
        public String shangjianame;//:"多乐滋蛋糕"   //商家名称
        public String yuanprice;//:“58”               //商品原价
        public String xianprice;//:"78"       //商品现价
        public String huodongtype;//:"0"   //  0 全场活动  1 没有全部活动
        public String activityother;//:"0" // 0 满多少减多少 1 全场打5折  不选传-1
        public String activityfullreduction;//:"100-20"   //满100减20   不选传0
        public String shangpinname;//:"多乐滋蛋糕"     //商品名字
        public String shangjiaicon;//:"78"       //商家图片
    }
}
