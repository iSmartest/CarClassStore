package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：CarplloinglistBean
 * 类描述：拼车列表适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/10 17:01
 */

public class CarplloinglistBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public List<CarpoolingBean>carpoolinglist;
    public class CarpoolingBean{
        public String carpoolingid;//拼车信息id
        public String carpoolingicon;//拼车信息用户头像
        public String carpoolingname;//用户昵称
        public String carpoolingtime;//用户发布信息时间
        public String carpoolingtype;//信息类型
        public String carpoolingaddress;// 发车/乘车 地点
        public String carpoolingdate;//发车、乘车日期
        public String carpoolingnewstime;//发车、乘车时间
        public String carpoolingphone;//联系人电话
        public String carpoolingnote;//备注信息
    }
}
