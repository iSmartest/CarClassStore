package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyBlackListBean
 * 类描述：我的黑名单实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/14 15:09
 */

public class MyBlackListBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public List<MyBlackBean>myblacklist;
    public class MyBlackBean{
        public String blackuserid;//黑名单的人的id
        public String blackuserage;//黑名单的人的年龄
        public String blackusericon;//黑名单的人的头像
        public String blackusernick;//黑名单的人的昵称
        public String blackusersex;//性别
        public String blackusersignature;//个人签名
    }
}
