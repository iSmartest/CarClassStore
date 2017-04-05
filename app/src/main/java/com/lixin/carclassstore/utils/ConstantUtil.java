package com.lixin.carclassstore.utils;

import android.os.Environment;

/**
 * 存放一些静态常量
 * 
 * @author chenheng
 */
public class ConstantUtil {
    /**
     * 默认的字符编码
     */
    public final static String ENCODE = "UTF-8";
	/** 操作成功 */
	public static final int OPERATION_SUCCESS = 10000;
	/** 操作失败 */
	public static final int OPERATION_FAIL = 10001;
    /** 数据改变 */
    public static final int DATA_CHANGE = 10008;

	/**
	 * 在校照片
	 * 
	 */
	public static final String PRE_URL_FOR_SCHOOL_IMAGE = "";


	/**
	 * 文件存储
	 */
	public static final String USER_DATA = "MyLog";


	/**
	 * 定位距离
	 */
	public static final int DISTANCE = 1000;

	/**
	 * 每页显示条数，默认为15
	 */
	public static final int PAGE_SIZE = 15;

	/**
	 * 上传照片：手机拍照临时照片的储存地址
	 */
	public static final String CAMERA_PHONE = Environment
			.getExternalStorageDirectory()
			+ "/"
			+ "ChePinStore_upload.jpg";

    /** 表示上拉获取更多 */
    public static final int PULL_UP_MORE = 10021;
    /** 表示下拉刷新 */
    public static final int PULL_DOWN_REFRESH = 10022;

    /** 分页索取时开始标记 */
    public static final int PAGE_STAR = 1;

    /**联网类型*/
    public static final class NetState {
        public static final int WIFI = 10030;
        public static final int Mobile = 10031;
        public static final int NOWAY = 10032;
    }
}
