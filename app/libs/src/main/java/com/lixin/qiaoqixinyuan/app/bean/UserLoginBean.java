package com.lixin.qiaoqixinyuan.app.bean;
/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：UserLoginBean
 * 类描述：登录返回实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/10 15:16
 */

public class UserLoginBean {
    public String result;//请求结果 0成功 1失败
    public String resultNote;//失败原因
    public UserInfo userInfo;//用户信息
    public class UserInfo {
        public String uid;//用户ID
        public String nickName;//用户昵称
        public String phoneNum;//手机号码
        public String useridentity;
        public String openId;//:""开启即时通信
    }
}
