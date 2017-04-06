package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/4/6
 * My mailbox is 1403241630@qq.com
 */

public class UserInfo {
    /**
     * 用户Id
     */
    public static String UserId;
    /**
     * 用户名
     */
    public static String UserName;
    /**
     * 用户手机号码
     */
    public static String Telephone;
    /**
     * 用户密码
     */
    public static String Password;
    /**
     * 用户头像
     */
    public static String ImageUrl;
    /**
     * 用户昵称
     */
    public static String Nickname;


    public static String getUserId() {
        return UserId;
    }

    public static void setUserId(String userId) {
        UserId = userId;
    }

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }

    public static String getTelephone() {
        return Telephone;
    }

    public static void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String getImageUrl() {
        return ImageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public static String getNickname() {
        return Nickname;
    }

    public static void setNickname(String nickname) {
        Nickname = nickname;
    }
}
