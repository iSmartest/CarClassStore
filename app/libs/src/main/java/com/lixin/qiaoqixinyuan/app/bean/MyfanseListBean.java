package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyfanseListBean
 * 类描述：我的关注实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/13 18:14
 */

public class MyfanseListBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public List<MyfanseBean> myfanselist;
        public class MyfanseBean{
        public String myfanseid;//粉丝id
        public String myfanseage;//粉丝的年龄
        public String myfanseicon;//粉丝头像
        public String myfansenick;//粉丝昵称
        public String myfansesex;//性别
        public String myfansesignature;//个人签名
    }
}
