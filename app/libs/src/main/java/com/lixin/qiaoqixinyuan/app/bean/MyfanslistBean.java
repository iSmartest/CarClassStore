package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyfocuslistBean
 * 类描述：我的关注实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/13 18:01
 */

public class MyfanslistBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public List<Myfanselist> myfocuslist;
        public class Myfanselist{
        public String myfanseid;//关注的人的id
        public String myfanseage;//关注的人的年龄
        public String myfanseicon;//关注的人的头像
        public String myfansenick;//关注的人的昵称
        public String myfansesex;//性别
        public String myfansesignature;//个人签名
    }
}
