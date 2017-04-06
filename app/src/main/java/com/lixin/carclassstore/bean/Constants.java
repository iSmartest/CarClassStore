package com.lixin.carclassstore.bean;

/**
 * 用来存储一些常量
 *
 * @author Administrator
 */
public class Constants {

    public static final String APP_NAME = "tpshop";
    /**
     * Jpush返回参数
     */
    public static final String JPUSH_EXTRA = "jpush";
    /**
     * 操作成功，例如，登录成功，注册成功，绑定成功
     */
    public static final int OPERATION_SUCCESS = 10000;
    /**
     * 退出程序
     */
    public static final int EXIT_APP = -10000;

    /**
     * 操作失败（失败原因为密码错误的那种，而不是连接服务器失败），例如，登录失败，注册失败，绑定失败
     */
    public static final int OPERATION_FAIL = 10001;
    /**
     * 数据改变
     */
    public static final int DATA_CHANGE = 10015;
    /**健康知识数据变化*/
    public static final int KNOWLEDGE_DATA_CHANGE = 10005;
    /**
     * 用户信息改变
     */
    public static final int USERINFO_CHANGE = 10016;
    /** 从SD卡中获取一张图片 */
    public static final int UPLOAD_PHOTO_FROM_SD = 10011;
    /** 使用拍照功能获取一张新图片 */
    public static final int UPLOAD_PHOTO_FROM_CAMERA = 10012;
    /** 裁剪图片 */
    public static final int TAILOR_PHOTO = 10013;


    public static final class LocalStuType {

        /**
         * 退出登录
         */
        public static final int LOGIN_OUT = 12;

    }



    /**
     * 表示客户端保存的SP数据中的字段名称
     */
    public static final class SP {
        /**
         * 第一次打开时间
         */
        public static final String FIRST_OPEN_TIME = "first_open_time";
        /**
         * 版本信息，用于安装统计，根据是否有内容判断是否需要上传统计信息
         */
        public static final String VersionNumber = "versionNumber";
        /**
         * 用户名
         */
        public static final String UserName = "UserName";
        /**
         * 密码
         */
        public static final String Pwd = "Pwd";
        /**保健知识类别获取时间*/
        public static final String KNOWLEDGE_TYPE_QUERYTIME = "knowledge_type_querytime";
        /**获取体质广告数据*/
        public static final String PHYSIQUE_AD_QUERYTIME = "physique_ad_queryTime";
        /**首页广告位获取时间*/
        public static final String HOME_AD_QUERYTIME = "home_ad_queryTime";
        /**健康现状广告位获取时间*/
        public static final String HEALTH_STATUS_AD_QUERYTIME = "health_status_ad_queryTime";
        /**我的页面动态配置获取时间*/
        public static final String MY_PAGE_QUERYTIME = "my_page_queryTime";
        /**我的配置信息获取时间*/
        public static final String MY_CONFIG_QUERYTIME = "my_config_queryTime";
        /**
         * apk下载的id
         */
        public static final String DOWNLOAD_ID = "downloadId";
        /**用户位置信息*/
        public static final String POSITION_LOG_DEVICE = "position_log_device";
    }
}
