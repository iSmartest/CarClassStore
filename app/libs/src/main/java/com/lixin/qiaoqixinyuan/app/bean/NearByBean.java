package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：NearByBean
 * 类描述：附近的人实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/13 17:04
 */

public class NearByBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public List<NearByUserBean> userlist;
    public class NearByUserBean{
        public String nearuserid;//附近的人的id
        public String nearusericon;//附近的人的头像
        public String nearusernick;//附近的人的昵称
        public String nearusersex;//性别
        public String nearusersignature;//个人签名
        public String nearuserdistance;//用户距离
    }
}
