package com.lixin.carclassstore.bean;
/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 * 登录返回实体类
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
